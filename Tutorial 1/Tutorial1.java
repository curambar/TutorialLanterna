import com.googlecode.lanterna.*;  
import com.googlecode.lanterna.terminal.*;  
  
import java.io.IOException;  
  
public class Tutorial1 {  
  public static void main(String[] args) throws IOException {  
          
    DefaultTerminalFactory factory = new DefaultTerminalFactory();  
    Terminal terminal = null;  
    try {  
      terminal = factory.createTerminal();  
      terminal.putCharacter('H');  
      terminal.putCharacter('o');  
      terminal.putCharacter('l');  
      terminal.putCharacter('a');  
      terminal.putCharacter('\n');  
      terminal.flush();  
      Thread.sleep(2000);  
              
      TerminalPosition posicionInicial = terminal.getCursorPosition();  
      terminal.setCursorPosition(posicionInicial.withRelativeColumn(3).withRelativeRow(2));  
      terminal.flush();  
      Thread.sleep(2000);  
            
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
  
      terminal.bell();  
      terminal.flush();  
      Thread.sleep(200);  
    }
    catch (IOException e) {  
      System.out.println(e.getMessage());  
    }
    catch (InterruptedException e) {  
      throw new RuntimeException(e);  
    }
    finally {  
      if (terminal != null) {  
        terminal.close();  
      }  
    }  
  }  
}
