# Terminal parte 1
En este primer tutorial veremos como utilizar el primer nivel de interfaz gráfica, `Terminal`.

## Código de ejemplo

Aquí analizaremos la clase usada como ejemplo

```java
import com.googlecode.lanterna.*;  
import com.googlecode.lanterna.terminal.*;  
  
import java.io.IOException;  
  
public class Main {  
  public static void main(String[] args) throws IOException {  
```          
`DefaultTerminalFactory` decide cuál tipo de terminal usar (UNIX, Telnet, AWT, etc.) de manera automática. Puede configurarse, pero en este ejemplo usamos el constructor básico.

Declaramos un `Terminal` nulo, y lo inicializamos dentro de un bloque `try-catch` para manejar el `IOException` declarado. En lugar del `try-catch` puede declararse la excepción en la firma del método.
```java
    DefaultTerminalFactory factory = new DefaultTerminalFactory();  
    Terminal terminal = null;  
    try {  
      terminal = factory.createTerminal();  
```
`putCharacter(char c)` envía `char` a consola secuencialmente. El primer char se imprime en la posición del cursor, que se inicializa en columna cero, fila cero, es decir en la esquina superior izquierda.

Al finalizar las operaciones, en varias terminales es necesario usar el método `flush()` para actualizar la ventana y mostrar los caracteres.

También agregamos un `Thread.sleep(2000)` para tener tiempo de ver lo que ocurre. Este método nos obliga a manejar también la excepción `InterruptedException`.
```java              
      terminal.putCharacter('H');  
      terminal.putCharacter('o');  
      terminal.putCharacter('l');  
      terminal.putCharacter('a');  
      terminal.putCharacter('\n');  
      terminal.flush();  
      Thread.sleep(2000);  
```
Para manipular la posición del cursor, creamos un objeto de la clase `TerminalPosition`. Luego cambiamos el cursor usando métodos de esta clase.
```java
      TerminalPosition posicionInicial = terminal.getCursorPosition();  
      terminal.setCursorPosition(posicionInicial.withRelativeColumn(3).withRelativeRow(2));  
      terminal.flush();  
      Thread.sleep(2000);  
```
Ahora probamos cambiar el color y fondo de los caracteres.
```java
      terminal.setBackgroundColor(TextColor.ANSI.BLUE);  
      terminal.setForegroundColor(TextColor.ANSI.YELLOW);  
      terminal.putCharacter('A');  
      terminal.putCharacter('m');  
      terminal.putCharacter('a');  
      terminal.putCharacter('r');  
      terminal.putCharacter('i');  
      terminal.putCharacter('l');  
      terminal.putCharacter('l');  
      terminal.putCharacter('o');  
      terminal.putCharacter(' ');  
      terminal.putCharacter('e');  
      terminal.putCharacter('n');  
      terminal.putCharacter(' ');  
      terminal.putCharacter('a');  
      terminal.putCharacter('z');  
      terminal.putCharacter('u');  
      terminal.putCharacter('l');  
      terminal.flush();  
      terminal.setCursorPosition(posicionInicial.withRelativeColumn(3).withRelativeRow(3));  
      terminal.flush();  
      Thread.sleep(2000);  
```
El `enum SGR` contiene opciones que permiten cambiar el estilo de texto. En este caso usamos negritas. El estilo se aplica al texto que se escribe a partir de la llamada al método `enableSGR()` hasta la llamada a los métodos `disableSGR()` o `resetColorAndSGR()`.
```java
      terminal.enableSGR(SGR.BOLD);  
      terminal.putCharacter('A');  
      terminal.putCharacter('m');  
      terminal.putCharacter('a');  
      terminal.putCharacter('r');  
      terminal.putCharacter('i');  
      terminal.putCharacter('l');  
      terminal.putCharacter('l');  
      terminal.putCharacter('o');  
      terminal.putCharacter(' ');  
      terminal.putCharacter('e');  
      terminal.putCharacter('n');  
      terminal.putCharacter(' ');  
      terminal.putCharacter('a');  
      terminal.putCharacter('z');  
      terminal.putCharacter('u');  
      terminal.putCharacter('l');  
      terminal.flush();  
      Thread.sleep(2000);  
  
      terminal.resetColorAndSGR();  
      terminal.setCursorPosition(terminal.getCursorPosition().withColumn(0).withRelativeRow(1));  
      terminal.putCharacter('L');  
      terminal.putCharacter('i');  
      terminal.putCharacter('s');  
      terminal.putCharacter('t');  
      terminal.putCharacter('o');  
      terminal.putCharacter('\n');  
      terminal.flush();  
      Thread.sleep(2000);  
```
Finalmente, `bell()` envía un caracter especial a consola (`0x7`) que provoca un sonido de "alerta".
```java
      terminal.bell();  
      terminal.flush();  
      Thread.sleep(200);  
    }
    catch (IOException e) {  
      System.out.println("Error de Entrada/Salida: " + e.getMessage());  
    }
    catch (InterruptedException e) {  
      System.out.println("Error de interrupción: " + e.getMessage());  
    }
    finally {  
      if (terminal != null) {  
        terminal.close();  
      }  
    }  
  }  
}
```

## Clases, Interfaces y Enumeraciones

### `Terminal`

La mayoría de los métodos arrojan `IOException` en caso de falla.

* `void enterPrivateMode()` - Entra en *modo privado*, un buffer separado para texto sin scrolling.
* `void exitPrivateMode()` - Sale de *modo privado*.
* `void clearScreen()` - Elimina caracteres y colores de la terminal.
* `void setCursorPosition(int columna, int fila)` - Mueve el cursor a la posición indicando columna y fila.
* `void setCursorPosition(TerminalPosition var1)` - Mueve el cursor a la posición indicada por `TerminalPosition`.
* `TerminalPosition getCursorPosition()` - Devuelve un `TerminalPosition` con la ubicación del cursor.
* `void setCursorVisible(boolean visible)` - Esconde o muestra el cursor. No funciona en todas las terminales.
* `void putCharacter(char c)` - Imprime el carácter `c` en la posición del cursor.
* `void putString(String cadena)` - Imprime `cadena` en la posición del cursor.
* `TextGraphics newTextGraphics()` - Crea un `TextGraphics` para funciones avanzadas. Es mejor usar un `screen`.
* `void enableSGR(SGR sgr)` - Activa un `SGR` (Selected Graphic Rendition) que permite cambiar el estilo del texto.
* `void disableSGR(SGR sgr)` - Desactiva un `SGR`.
* `void resetColorAndSGR()` - Resetea colores y estilos.
* `void setForegroundColor(TextColor color)` - Elige el color del texto.
* `void setBackgroundColor(TextColor var1)` - Elige el color de fondo.
* `void addResizeListener(TerminalResizeListener trl)` - Agrega un observador que se activa ante el cambio de tamaño de la terminal.
* `void removeResizeListener(TerminalResizeListener var1)` - Elimina el observador.
* `TerminalSize getTerminalSize()` - Obtiene el tamaño de la terminal.
* `byte[] enquireTerminal(int timeOut, TimeUnit unidad)` - Recibe información de la terminal. Ver [Enquiry Character](https://en.wikipedia.org/wiki/Enquiry_character).
* `void bell()` - Imprime el carácter `bell` que produce un sonido de alerta.
* `void flush()`  - Llama al método `flush()` del stream de salida.
* `void close()` - Cierra la terminal.


### `SGR`

No todos los estilos funcionan en todas las consolas.

* `BLINK` - Texto que se prende y apaga.
* `BOLD` - Negrita.
* `BORDERED` - Dibuja un borde alrededor del texto.
* `CIRCLED` - Dibuja un círculo alrededor del texto.
* `CROSSED_OUT` - Tachado.
* `FRAKTUR` - Tipo de texto Fraktur.
* `ITALIC` - Itálicas.
* `REVERSE` - Intercambia el color del texto con el color de fondo.
* `UNDERLINE` - Subrayado.

### `TextColor`

Esta interfaz define colores. No todas las consolas soportan todos los modos de color.

Ver [TextColor](https://mabe02.github.io/lanterna/apidocs/3.1/com/googlecode/lanterna/TextColor.html) para listas de colores válidos y más información.

Las clases estáticas contenidas son:
* `TextColor.ANSI` - Representa colores `ANSI`, es compatible con la mayor parte de las terminales.
* `TextColor.Factory` - Permite crear colores a partir de un `String`, como por ejemplo "blue", "#17" o "#1a1a1a".
* `TextColor.Indexed` - Representa colores indexados en formato XTerm 256.
* `TextColor.RGB` - Representa un color RGB con 8-bit por canal.

Tiene también estos métodos para obtener los valores de los canales RGB:
* `int getRed()`
* `int getBlue()`
* `int getGreen()`
