import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Random;

/**
 * Tercer tutorial, introduciendo la interfaz Screen.
 */
public class Tutorial3 {
  public static void main(String[] args) {
    DefaultTerminalFactory factory = new DefaultTerminalFactory();
    Screen screen = null;
    try {
      Terminal terminal = factory.createTerminal();
      screen = new TerminalScreen(terminal);
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
      while(true) {
        KeyStroke tecla = screen.pollInput();
        if(tecla != null && (tecla.getKeyType() == KeyType.Escape || tecla.getKeyType() == KeyType.EOF)) {
          break;
        }
        TerminalSize nuevaDimension = screen.doResizeIfNecessary();
        if(nuevaDimension != null) {
          dimensiones = nuevaDimension;
        }

        final int caracteresAModificarPorBucle = 1;
        for(int i = 0; i < caracteresAModificarPorBucle; i++) {
          TerminalPosition celdaAModificar = new TerminalPosition(
                  random.nextInt(dimensiones.getColumns()),
                  random.nextInt(dimensiones.getRows()));
          TextColor.ANSI color = TextColor.ANSI.values()[random.nextInt(TextColor.ANSI.values().length)];
          TextCharacter caracterEnBuffer = screen.getBackCharacter(celdaAModificar);
          caracterEnBuffer = caracterEnBuffer.withBackgroundColor(color);
          caracterEnBuffer = caracterEnBuffer.withCharacter(' ');
          screen.setCharacter(celdaAModificar, caracterEnBuffer);
        }
        String sizeLabel = "Dimensiones: " + dimensiones;
        TerminalPosition cajaArribaIzquierda = new TerminalPosition(1, 1);
        TerminalSize cajaDimensiones = new TerminalSize(sizeLabel.length() + 2, 3);
        TerminalPosition cajaArribaDerecha = cajaArribaIzquierda.withRelativeColumn(cajaDimensiones.getColumns() - 1);
        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.fillRectangle(cajaArribaIzquierda, cajaDimensiones, ' ');
        textGraphics.drawLine(
                cajaArribaIzquierda.withRelativeColumn(1),
                cajaArribaIzquierda.withRelativeColumn(cajaDimensiones.getColumns() - 2),
                Symbols.DOUBLE_LINE_HORIZONTAL);
        textGraphics.drawLine(
                cajaArribaIzquierda.withRelativeRow(2).withRelativeColumn(1),
                cajaArribaIzquierda.withRelativeRow(2).withRelativeColumn(cajaDimensiones.getColumns() - 2),
                Symbols.DOUBLE_LINE_HORIZONTAL);
        textGraphics.setCharacter(cajaArribaIzquierda, Symbols.DOUBLE_LINE_TOP_LEFT_CORNER);
        textGraphics.setCharacter(cajaArribaIzquierda.withRelativeRow(1), Symbols.DOUBLE_LINE_VERTICAL);
        textGraphics.setCharacter(cajaArribaIzquierda.withRelativeRow(2), Symbols.DOUBLE_LINE_BOTTOM_LEFT_CORNER);
        textGraphics.setCharacter(cajaArribaDerecha, Symbols.DOUBLE_LINE_TOP_RIGHT_CORNER);
        textGraphics.setCharacter(cajaArribaDerecha.withRelativeRow(1), Symbols.DOUBLE_LINE_VERTICAL);
        textGraphics.setCharacter(cajaArribaDerecha.withRelativeRow(2), Symbols.DOUBLE_LINE_BOTTOM_RIGHT_CORNER);
        textGraphics.putString(cajaArribaIzquierda.withRelative(1, 1), sizeLabel);
        screen.refresh();
        Thread.yield();
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
