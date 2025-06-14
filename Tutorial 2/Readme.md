# Terminal parte 2

En este tutorial veremos funcionalidades más avanzadas de `Terminal`.

## Código de ejemplo

Empezamos similarmente al tutorial anterior.
```java
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalResizeListener;

import java.io.IOException;

public class Tutorial2 {
  public static void main(String[] args) {
    DefaultTerminalFactory factory = new DefaultTerminalFactory();
    Terminal terminal = null;
    try {
      terminal = factory.createTerminal();
```
La mayoría de las terminales soportan el "modo privado", el cual es un buffer separado para texto que no permite scroll. Frecuentemente usado por aplicaciones de texto que permiten "pantalla completa" como **nano** y **vi**.

Se entra a este modo con `enterPrivateMode()` y se sale con `exitPrivateMode()` o al cerrar la terminal con `close()`. Al dejar de usar este modo, se recomienda salir del modo para no dejar la consola en un estado indefinido.

El contenido de la terminal debería limpiarse al cambiar de modo, pero en caso de que esto no ocurra, lo limpiamos con `clearScreen()`. La limpieza también devuelve colores y estilos a su forma base.

```java
      terminal.enterPrivateMode();
      terminal.clearScreen();
      terminal.setCursorVisible(false);
```
La interfaz `TextGraphics` permite "dibujar" gráficos usando caracteres de texto. Tiene implementaciones para `Terminal`, `Screen` y `TextGUI`.

El concepto básico de `TextGraphics` es que permite guardar colores de texto y fondo, estilos y comportamiento de tabulaciones.
```java
      final TextGraphics textGraphics = terminal.newTextGraphics();
      textGraphics.setForegroundColor(TextColor.ANSI.CYAN_BRIGHT);
      textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
```
`putString(...)` tiene varias sobrecargas, pero en general permite escribir un `String` en un punto dado de la terminal.
```java
      textGraphics.putString(2, 1, "Tutorial 2 - Presione ESC para salir", SGR.BLINK,
              SGR.ITALIC);
      textGraphics.setForegroundColor(TextColor.ANSI.DEFAULT);
      textGraphics.setBackgroundColor(TextColor.ANSI.DEFAULT);
      textGraphics.putString(5, 3, "Dimensiones de la Terminal: ", SGR.BOLD);
      textGraphics.putString(5 + "Dimensiones de la Terminal: ".length(), 3, terminal.getTerminalSize().toString());
      terminal.flush();
```
Puede agregarse un observador de cambio de dimensiones, que llamará a un método cuando esto ocurra. No todas las terminales soportan esto. Esto puede implementarse con una clase interna anónima.
```java
      terminal.addResizeListener(new TerminalResizeListener() {
        @Override
        public void onResized(Terminal terminal, TerminalSize nuevasDimensiones) {
          textGraphics.drawLine(5, 3, nuevasDimensiones.getColumns() - 1, 3, ' ');
          textGraphics.putString(5, 3, "Dimensiones: ", SGR.BOLD);
          textGraphics.putString(5 + "Dimensiones: ".length(), 3,
                  nuevasDimensiones.toString());
          try {
            terminal.flush();
          } catch (IOException e) {
            System.out.println("Error de Entrada/Salida: " + e.getMessage());
          }
        }
      });

      textGraphics.putString(5, 4, "Tecla ingresada: ", SGR.BOLD);
      textGraphics.putString(5 + "Tecla ingresada: ".length(), 4, "<Pendiente>");
      terminal.flush();
```
Para leer input, hay dos métodos: `readInput()` que bloquea ejecución hasta que se ingresa algo, y `pollInput()` que no bloquea ejecución, y devuelve `null` si no detecta nada.

La clase KeyStroke tiene varios métodos para determinar el input. Ciertas teclas no pueden detectarse solas, como los modificadores **CTRL**, **ALT** y **SHIFT**.

En general las teclas especiales tienen un `KeyType` específico, mientras que las teclas regulares están todas en `KeyType.Character`. **Tab** y **Enter** son `KeyType.Tab` y `KeyType.Enter` respectivamente.
```java
      KeyStroke tecla = terminal.readInput();
      while (tecla.getKeyType() != KeyType.Escape) {
        textGraphics.drawLine(5, 4, terminal.getTerminalSize().getColumns() - 1, 4, ' ');
        textGraphics.putString(5, 4, "Tecla ingresada: ", SGR.BOLD);
        textGraphics.putString(5 + "Tecla ingresada: ".length(), 4, tecla.toString());
        terminal.flush();
        tecla = terminal.readInput();
      }

    } catch (IOException e) {
      System.out.println("Error de Entrada/Salida: " + e.getMessage());
    } finally {
      if (terminal != null) {
        try {
          terminal.close();
        } catch (IOException e) {
          System.out.println("Error al cerrar la terminal:" + e.getMessage());
        }
      }
    }
  }
}
```

## Clases e Interfaces

### `TextGraphics`

Si no se indica el tipo de retorno, es del tipo `TextGraphics`.

Los métodos que requieren un `Char` pueden tener una sobrecarga que permite usar `TextCharacter` en cambio.

Los métodos que tienen posiciones con `x` e `y` pueden tener sobrecargas que aceptan `TerminalPosition` en cambio.

* `newTextGraphics​(TerminalPosition arribaIzquierda, TerminalSize size)` - Crea un `TextGraphics` del mismo tipo que este.

* `drawImage​(TerminalPosition arribaIzquierda, TextImage imagen)` - Recibe un `TextImage` y lo dibuja en la posición inicial indicada.
* `drawImage​(TerminalPosition arribaIzquierda, TextImage imagen, TerminalPosition imagenArribaIzquierda, TerminalSize imagenSize)` - Igual al anterior, especificando posición inicial y tamaño de la imagen.

* `drawLine​(int desdeX, int desdeY, int hastaX, int hastaY, char caracter)` - Dibuja una linea entre los puntos especificados con el caracter indicado.
* `drawRectangle​(TerminalPosition arribaIzquieda, TerminalSize size, char caracter)` - Dibuja el borde de un rectángulo con la posición inicial, tamaño y caracter especificados.
* `drawTriangle​(TerminalPosition p1, TerminalPosition p2, TerminalPosition p3, char caracter)` - Dibuja el borde de un triángulo.
* `fillRectangle​(TerminalPosition arribaIzquierda, TerminalSize size, char caracter)` - Dibuja un rectángulo relleno.
* `fillTriangle​(TerminalPosition p1, TerminalPosition p2, TerminalPosition p3, char caracter)` - Dibuja un triángulo relleno.
* `fill​(char c)` - Llena toda el área escribible con el caracter, manteniendo los colores y estilos.

* `TextCharacter getCharacter​(int columna, int fila)` - Devuelve el caracter en la posición indicada.
* `setCharacter​(int columna, int fila, char caracter)` - Cambia el `char` en la posición especificada por el indicado.
  
* `TerminalSize	getSize​()` - Devuelve el tamaño del área escribible.

* `TabBehaviour	getTabBehaviour​()` - Devuelve el comportamiento de las tabulaciones, que `TextGraphics` usa para convertir '\t' en espacios.
* `setTabBehaviour​(TabBehaviour tabBehaviour)` - Determina el comportamiento de las tabulaciones.
  
* `putCSIStyledString​(int column, int row, String string)` - Escribe un `String` con los colores y estilos ANSI actuales.
* `putString​(int columna, int fila, String string)` - Escribe un `String` en la posición indicada.
* `putString​(int columna, int fila, String string, SGR estilo, SGR... estilosExtra)` - Escribe un `String` con los estilos indicados.
* `putString​(int columna, int fila, String string, Collection<SGR> estilos)` - Igual al anterior.



### `TextCharacter`

Representa un caracter con color y estilos. Es inmutable.

Estos métodos estáticos devuelven un `TextCharacter[]` desde varias fuentes.

* `fromCharacter​(char c)`
* `fromCharacter​(char c, TextColor colorTexto, TextColor colorFondo, SGR... estilos)`
* `fromString​(String string)`
* `fromString​(String string, TextColor colorTexto, TextColor colorFondo, SGR... estilos)`
* `fromString​(String string, TextColor colorTexto, TextColor colorFondo, EnumSet<SGR> estilos)`

Estos métodos booleanos determinan si el `TectCharacter` tiene la característica indicada.

* `s​(char otroCaracter)` - Igual a otro.
* `isBlinking​()` - Parpadeo.
* `isBold​()` - Negrita.
* `isBordered​()` - Con borde.
* `isCrossedOut​()` - Tachado.
* `isDoubleWidth​()` - Doble ancho.
* `isItalic​()` - Itálica.
* `isReversed​()` - Reverso.
* `isUnderlined​()` - Subrayado.

Estos métodos devuelven las características del `TextCharacter`.

* `TextColor getBackgroundColor​()` - Color de fondo.
* `TextColor getForegroundColor​()` - Color de texto.
* `EnumSet<SGR> getModifiers​()` - Estilos.
* `String getCharacterString​()` - Devuelve un `String` con el caracter.

Estos métodos devuelven una copia del `TextCharacter` agregando la característica indicada.

* `withBackgroundColor​(TextColor colorFondo)` - Color de fondo.
* `withCharacter​(char caracter)` - Caracter.
* `withForegroundColor​(TextColor colorTexto)` - Color de texto.
* `withModifier​(SGR estilo)` - Estilo.
* `withModifiers​(Collection<SGR> estilos)` - Colección de estilos.
* `withoutModifier​(SGR estilo)` - Remueve estilo.

### `TextImage`

Representa una "imagen" compuesta de caracteres.

* `void copyTo​(TextImage destino)` - Copia esta `TextImage` en otra.
* `void copyTo​(TextImage destino, int filaInicial, int filas, int columnaInicial, int columnas, int destinoOffsetFila, int destinoOffsetColumna)` - Copia parte de esta `TextImage` en otra.
* `TextCharacter getCharacterAt​(int columna, int fila)` - Devuelve el caracter en la posición indicada.
* `TextCharacter getCharacterAt​(TerminalPosition posicion)` - Igual al anterior.
* `TerminalSize getSize​()` - Devuelve el tamaño de la `TextImage`.
* `TextGraphics newTextGraphics​()` - Constructor.
* `TextImage resize​(TerminalSize nuevoSize, TextCharacter relleno)` - Cambia el tamaño de la `TextImage` rellenando los espacios vacíos si los hubiera.
* `void scrollLines​(int primeraLinea, int ultimaLinea, int distancia)` - Desplaza un rango de filas según la distancia indicada.
* `void setAll​(TextCharacter caracter)` - Rellena la imagen con el caracter indicado.
* `void setCharacterAt​(int columna, int fila, TextCharacter caracter)` - Establece el caracter en la posición indicada.
* `void setCharacterAt​(TerminalPosition posicion, TextCharacter caracter)` - Igual al anterior.
