# GUI

En este cuarto tutorial veremos como crear un GUI de varias ventanas, basado en texto. Las clases del GUI viven sobre una capa de `Screen`. Por el mismo motivo que antes, no se debe manipular la `Screen` o la `Terminal` mientras se usa la GUI.

El sistema de GUI está diseñado alrededor de un fondo usualmente estático, pero puede tener componentes y varias ventanas. El comportamiento ideal es hacer las ventanas "modales" y no permitir al usuario cambiar libremente entre ellas, aunque esto puede lograrse. Los componentes se agregan a las ventanas usando un gestor que determina sus posiciones.

```java
public class Tutorial4 {
  public static void main(String[] args) {
    DefaultTerminalFactory factory = new DefaultTerminalFactory();
    Screen pantalla = null;
    try {
```
La clase `DefaultTerminalFactory` no tiene un método directo para crear GUI, por lo que necesitamos crear un `Screen`.

Hay varios constructores para `MultiWindowTextGUI`, pero usaremos el básico en este ejemplo.
```java
      pantalla = factory.createScreen();
      pantalla.startScreen();
      final WindowBasedTextGUI textoGUI = new MultiWindowTextGUI(pantalla);
```
Crear una ventana es sencillo, opcionalmente puede inicializarse con un título.

Al iniciarse, la ventana está vacía, necesitamos `setComponent(...)` para agregarle cosas. Es útil agrupar controles en un `Panel`.

**Lanterna** tiene varios gestores para el arreglo de contenido, siendo el más simple `LinearLayout`, que acomoda componentes horizontal o verticalmente. En este ejemplo usaremos `GridLayout`, basado en el gestor del mismo nombre en **SWT**. Aquí construimos una grilla de dos columnas y luego le configuramos el espaciado.


```java
      final Window ventana = new BasicWindow("Ventana raiz");
      Panel panelContenido = new Panel(new GridLayout(2));
      GridLayout arregloGrilla = (GridLayout)panelContenido.getLayoutManager();
      arregloGrilla.setHorizontalSpacing(3);
```

Uno de los componentes más básicos es `Label`, que simplemente muestra un texto estático. En este ejemplo construimos uno y usamos su método `setLayoutData` para configurarlo.

```java
      Label titulo = new Label("Etiqueta que ocupa dos columnas");
      titulo.setLayoutData(GridLayout.createLayoutData(
              GridLayout.Alignment.BEGINNING, //Alineamliento horizontal si la celda es mayor al componente.
              GridLayout.Alignment.BEGINNING, //Alineamliento vertical si la celda es mayor al componente.
              true,       // Ocupar espacio extra si hay espacio disponible.
              false,        // Ocupar espacio extra si hay espacio disponible.
              2,                  // Tramo horizontal.
              1));                  // Tramo vertical.
      panelContenido.addComponent(titulo);
```

Ya que tenemos dos columnas en la grilla, podemos agregar más componentes.

Agregaremos un `TextBox` común, uno con máscara para ingresar contraseñas, un `ComboBox` de solo lectura y uno editable.

```java
      panelContenido.addComponent(new Label("Text Box (alineado a la izquierda)"));
      panelContenido.addComponent(
              new TextBox()
                      .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING,
                                                                GridLayout.Alignment.CENTER)));


      panelContenido.addComponent(new Label("Password Box (alineado a la derecha"));
      panelContenido.addComponent(
              new TextBox()
                      .setMask('*')
                      .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END,
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

```
Algunos controles como `Button` interactúan usando métodos de llamada. En este ejemplo usamos uno que viene predefinido.
```java
      panelContenido.addComponent(new Label("Boton (centrado)"));
      panelContenido.addComponent(new Button("Boton",
              () -> MessageDialog
                .showMessageDialog(textoGUI, "MessageBox", "Este es un Message Box", MessageDialogButton.OK))
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER)));
```
Cerramos con una fila vacía y un separador, y un botón para cerrar la ventana.
```java
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

```
Ahora tenemos el `Panel` con sus controles. Para terminar hay que agregar el `Panel` a la ventana.
```java
      ventana.setComponent(panelContenido);
      textoGUI.addWindowAndWait(ventana);
```
Al cerrar la ventana, aún contiene el último estado del `TextGUI` aunque no sea visible. Esto nos permitiría en este punto mostrar otra ventana distinta sin inconvenientes. En nuestro caso cerraremos todo, deteniendo la `Screen`.
```java
    }
    catch (IOException e) {
      System.out.println(e.getMessage());
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
