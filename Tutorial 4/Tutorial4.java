import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

/**
 * Cuarto tutorial, introduciendo GUI.
 */
public class Tutorial4 {
  public static void main(String[] args) {
    DefaultTerminalFactory factory = new DefaultTerminalFactory();
    Screen pantalla = null;

    try {
      pantalla = factory.createScreen();
      pantalla.startScreen();
      final WindowBasedTextGUI textoGUI = new MultiWindowTextGUI(pantalla);
      final Window ventana = new BasicWindow("Ventana raiz");
      Panel panelContenido = new Panel(new GridLayout(2));
      GridLayout arregloGrilla = (GridLayout)panelContenido.getLayoutManager();
      arregloGrilla.setHorizontalSpacing(3);

      Label titulo = new Label("Etiqueta que ocupa dos columnas");
      titulo.setLayoutData(GridLayout.createLayoutData(
              GridLayout.Alignment.BEGINNING, GridLayout.Alignment.BEGINNING, 
              true, false, 2, 1));
      panelContenido.addComponent(titulo);

      panelContenido.addComponent(new Label("Text Box (alineado a la izquierda)"));
      panelContenido.addComponent(
              new TextBox()
                      .setLayoutData(GridLayout
					  .createLayoutData(GridLayout.Alignment.BEGINNING,
					  GridLayout.Alignment.CENTER)));

      panelContenido.addComponent(new Label("Password Box (alineado a la derecha"));
      panelContenido.addComponent(
              new TextBox()
                      .setMask('*')
                      .setLayoutData(GridLayout
					  .createLayoutData(GridLayout.Alignment.END,
					  GridLayout.Alignment.CENTER)));

      panelContenido.addComponent(new Label("Combo Box de solo lectura (tamaño fijo)"));
      List<String> zonasHorarias = new ArrayList<>(Arrays.asList(TimeZone.getAvailableIDs()));
      ComboBox<String> comboBoxSoloLectura = new ComboBox<>(zonasHorarias);
      comboBoxSoloLectura.setReadOnly(true);
      comboBoxSoloLectura.setPreferredSize(new TerminalSize(20, 1));
      panelContenido.addComponent(comboBoxSoloLectura);

      panelContenido.addComponent(new Label("Combo Box editable (lleno)"));
      panelContenido.addComponent(
              new ComboBox<>("Item 1", "Item 2", "Item 3", "Item 4")
                      .setReadOnly(false)
                      .setLayoutData(GridLayout.createHorizontallyFilledLayoutData(1)));

      panelContenido.addComponent(new Label("Boton (centrado)"));
      panelContenido.addComponent(new Button("Boton",
              () -> MessageDialog
			  .showMessageDialog(textoGUI, "MessageBox",
			  "Este es un Message Box", MessageDialogButton.OK))
			  .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER,
														GridLayout.Alignment.CENTER)));

      panelContenido.addComponent(
              new EmptySpace()
                      .setLayoutData(
                              GridLayout.createHorizontallyFilledLayoutData(2)));

      panelContenido.addComponent(
              new Separator(Direction.HORIZONTAL)
                      .setLayoutData(
                              GridLayout.createHorizontallyFilledLayoutData(2)));

      panelContenido.addComponent(
              new Button("Cerrar", ventana::close).setLayoutData(
                      GridLayout.createHorizontallyEndAlignedLayoutData(2)));

      ventana.setComponent(panelContenido);
      textoGUI.addWindowAndWait(ventana);
    }
    catch (IOException e) {
      System.out.println(e.getMessage());;
    }
    finally {
      if(pantalla != null) {
        try {
          pantalla.close();
        }
        catch(IOException e) {
          System.out.println(e.getMessage());;
        }
      }
    }
  }
}
