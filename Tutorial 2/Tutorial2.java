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

/**
 * Segundo tutorial, usando funciones más avanzadas de Terminal.
 */
public class Tutorial2 {
  public static void main(String[] args) {
    DefaultTerminalFactory factory = new DefaultTerminalFactory();
    Terminal terminal = null;
    try {
      terminal = factory.createTerminal();
      terminal.enterPrivateMode();
      terminal.clearScreen();
      terminal.setCursorVisible(false);
      
      final TextGraphics textGraphics = terminal.newTextGraphics();
      textGraphics.setForegroundColor(TextColor.ANSI.CYAN_BRIGHT);
      textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
      textGraphics.putString(2, 1, "Tutorial 2 - Presione ESC para salir", SGR.BLINK,
              SGR.ITALIC);
      textGraphics.setForegroundColor(TextColor.ANSI.DEFAULT);
      textGraphics.setBackgroundColor(TextColor.ANSI.DEFAULT);
      textGraphics.putString(5, 3, "Dimensiones de la Terminal: ", SGR.BOLD);
      textGraphics.putString(5 + "Dimensiones de la Terminal: ".length(), 3, terminal.getTerminalSize().toString());
      terminal.flush();

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
