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
