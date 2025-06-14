# Screen

En este tercer tutorial veremos otra capa de **Lanterna**, la capa `Screen` o "pantalla", que existe por encima de una `Terminal`. Esta capa funciona de modo similar a la memoria de video de doble buffer. Tiene dos superficies que pueden ser accedidas y modificadas, y llamando un método particular el contenido del bufer trasero se copia al frontal.

En lugar de pixeles, un `Screen` trabaja con `char`, con cada uno correspondiendo a una "celda" en la terminal. El buffer trasero puede modificarse libremente, el buffer frontal puede leerse libremente, y el método `refreshScreen` copia el contenido del trasero al frontal.


## Código de ejemplo
```java
public class Tutorial3 {
  public static void main(String[] args) {
    DefaultTerminalFactory factory = new DefaultTerminalFactory();
    Screen screen = null;
    try {
```

Usamos `DefaultTerminalFactory` para crear el `Screen`. Para mostrar que por debajo hay un `Terminal`, en este ejemplo lo crearemos manualmente.

```java
      Terminal terminal = factory.createTerminal();
      screen = new TerminalScreen(terminal);
```

Las pantallas solo funcionan con modo privado, y para mostrarla hay que usar el método `startScreen()`.

Empezamos dibujando cosas aleatorias.

```java
      screen.startScreen();
      screen.setCursorPosition(null);
      Random random = new Random();
      TerminalSize dimensiones = screen.getTerminalSize();
      for(int column = 0; column < dimensiones.getColumns(); column++) {
        for(int row = 0; row < dimensiones.getRows(); row++) {
          screen.setCharacter(column, row, new TextCharacter(
                  ' ',
                  TextColor.ANSI.DEFAULT,
                  TextColor.ANSI.values()[random.nextInt(TextColor.ANSI.values().length)]));
        }
      }
```
Hasta ahora solo hemos modificado el buffer trasero, así que por ahora no veremos nada. Para copiar esta data al buffer frontal usamos `refresh()`.

Esto mostrará celdas de colores aleatorios hasta que el usuario presione una tecla o transcurran dos segundos.
```java
      screen.refresh();
      long tiempoInicio = System.currentTimeMillis();
      while(System.currentTimeMillis() - tiempoInicio < 2000) {
        if(screen.pollInput() != null) {
          break;
        }
        try {
          Thread.sleep(1);
        }
        catch(InterruptedException ignore) {
          break;
        }
      }
```
Ahora hacemos un bucle y seguimos modificando la pantalla hasta que el usuario presione ESCAPE o se cierre la pantalla.
```java
      while(true) {
        KeyStroke tecla = screen.pollInput();
        if(tecla != null && (tecla.getKeyType() == KeyType.Escape || tecla.getKeyType() == KeyType.EOF)) {
          break;
        }
```
Las pantallas detectan automáticamente cambios de tamaño, pero hay que decirle cuando actualizar sus buffers. Usualmente esto se hace al comienzo del ciclo de dibujo si lo hubiera.

El método `doResizeIfNecessary()` verifica si la terminal cambió de tamaño desde su última llamada y cambia el tamaño del buffer.
```java
        TerminalSize nuevaDimension = screen.doResizeIfNecessary();
        if(nuevaDimension != null) {
          dimensiones = nuevaDimension;
        }

        //Podemos modificar esto para subir la velocidad.
        final int caracteresAModificarPorBucle = 1;
        for(int i = 0; i < caracteresAModificarPorBucle; i++) {
```
Elegimos una celda y color al azar, y actualizamos el buffer trasero.
```java
          TerminalPosition celdaAModificar = new TerminalPosition(
                  random.nextInt(dimensiones.getColumns()),
                  random.nextInt(dimensiones.getRows()));
          TextColor.ANSI color = TextColor.ANSI.values()[random.nextInt(TextColor.ANSI.values().length)];
          TextCharacter caracterEnBuffer = screen.getBackCharacter(celdaAModificar);
          caracterEnBuffer = caracterEnBuffer.withBackgroundColor(color);
          caracterEnBuffer = caracterEnBuffer.withCharacter(' ');
          screen.setCharacter(celdaAModificar, caracterEnBuffer);
        }
```
Al igual que con `Terminal`, suele ser más fácil usar `TextGraphics`. Usamos esto para dibujar el tamaño de la terminal.
```java
        String sizeLabel = "Dimensiones: " + dimensiones;
        TerminalPosition cajaArribaIzquierda = new TerminalPosition(1, 1);
        TerminalSize cajaDimensiones = new TerminalSize(sizeLabel.length() + 2, 3);
        TerminalPosition cajaArribaDerecha = cajaArribaIzquierda.withRelativeColumn(cajaDimensiones.getColumns() - 1);
        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.fillRectangle(cajaArribaIzquierda, cajaDimensiones, ' ');
```
Dibujamos las lineas horizontales superiores, luego las inferiores.
```java
        textGraphics.drawLine(
                cajaArribaIzquierda.withRelativeColumn(1),
                cajaArribaIzquierda.withRelativeColumn(cajaDimensiones.getColumns() - 2),
                Symbols.DOUBLE_LINE_HORIZONTAL);
        textGraphics.drawLine(
                cajaArribaIzquierda.withRelativeRow(2).withRelativeColumn(1),
                cajaArribaIzquierda.withRelativeRow(2).withRelativeColumn(cajaDimensiones.getColumns() - 2),
                Symbols.DOUBLE_LINE_HORIZONTAL);
```
Hacemos los bordes manualmente.
```java
        textGraphics.setCharacter(cajaArribaIzquierda, Symbols.DOUBLE_LINE_TOP_LEFT_CORNER);
        textGraphics.setCharacter(cajaArribaIzquierda.withRelativeRow(1), Symbols.DOUBLE_LINE_VERTICAL);
        textGraphics.setCharacter(cajaArribaIzquierda.withRelativeRow(2), Symbols.DOUBLE_LINE_BOTTOM_LEFT_CORNER);
        textGraphics.setCharacter(cajaArribaDerecha, Symbols.DOUBLE_LINE_TOP_RIGHT_CORNER);
        textGraphics.setCharacter(cajaArribaDerecha.withRelativeRow(1), Symbols.DOUBLE_LINE_VERTICAL);
        textGraphics.setCharacter(cajaArribaDerecha.withRelativeRow(2), Symbols.DOUBLE_LINE_BOTTOM_RIGHT_CORNER);
```
Finalmente escribimos el texto y le devolvemos el control del hilo al sistema operativo.
```java
        textGraphics.putString(cajaArribaIzquierda.withRelative(1, 1), sizeLabel);
        screen.refresh();
        Thread.yield();
```
Cada vez que llamamos a `refresh()`, la terminal solo redibuja las celdas que han cambiado desde la última actualización.

Debido a esto, nunca debemos usar la `Terminal`, porque puede caudar cambios que el `Screen` no tiene forma de conocer.
```java
      }
    }
    catch(IOException e) {
      e.printStackTrace();
    }
    finally {
      if(screen != null) {
        try {
          screen.close();
        }
        catch(IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
```

## Clases e Interfaces

### `Screen`
