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
