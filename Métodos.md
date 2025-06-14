# Resumen de métodos de las clases e interfaces de **Lanterna**

Para más detalles, ver la [documentación oficial](https://mabe02.github.io/lanterna/apidocs/3.1/overview-summary.html).

## `Terminal`

La mayoría de los métodos arrojan `IOException` en caso de falla.

[Documentación](https://mabe02.github.io/lanterna/apidocs/3.1/com/googlecode/lanterna/terminal/Terminal.html).

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

## `TerminalPosition`

Representa una ubicación en el plano. Los índices comienzan en cero. Son **inmutables**.

[Documentación](https://mabe02.github.io/lanterna/apidocs/3.1/com/googlecode/lanterna/TerminalPosition.html).

* `OFFSET_1x1` - Constante para un movimiento de (+1, +1).
* `TOP_LEFT_CORNER` - Constante para la esquina superior izquierda (0, 0).
* `TerminalPosition(int columna, int fila)` - Constructor.

Estos métodos devuelven fila y columna.

* `int getColumn​()` 
* `int getRow​()`

Estos métodos devuelven un `TerminalPosition` operando el actual con otro.

Las operaciones matemáticas se aplican tanto a columna como a fila. Por ejemplo, `A.divide(B)` devuelve un `TerminalPosition` cuya columna es el cociente entre columna de `A` y columna de `B`, y cuya fila es el cociente entre fila de `A` y fila de `B`.

* `TerminalPosition max​(TerminalPosition posicion)` - Devuelve el máximo.
* `TerminalPosition min​(TerminalPosition posicion)` - Devuelve el mínimo.
* `TerminalPosition plus​(TerminalPosition posicion)` - Devuelve la suma.
* `TerminalPosition minus​(TerminalPosition posicion)` - Devuelve la resta.
* `TerminalPosition multiply​(TerminalPosition posicion)` - Devuelve el producto.
* `TerminalPosition divide​(TerminalPosition denominador)` - Devuelve el cociente.

Estos métodos devuelven una copia con una característica modificada.

* `TerminalPosition	with​(TerminalPosition posicion)` - Devuelve sí mismo si ya es igual a `posicion`, si no devuelve `posicion`.
* `TerminalPosition withColumn​(int columna)` - Cambia la columna por `columna`.
* `TerminalPosition withRow​(int fila)` - Cambia la fila por `fila`.
* `TerminalPosition withRelative​(int deltaColumna, int deltaFila)` - Mueve la posición `(deltaColumna, deltaFila)` lugares.
* `TerminalPosition withRelative​(TerminalPosition trasladar)` - Equivalente a `plus​(TerminalPosition trasladar)`.
* `TerminalPosition withRelativeColumn​(int delta)` - Suma `delta` lugares a la columna.
* `TerminalPosition withRelativeRow​(int delta)` - Suma `delta` lugares a la fila.

## `TerminalSize`

Representa el tamaño de una terminal medido en número columnas y filas. Es **inmutable**, por lo que sus métodos devuelven copias.

[Documentación](https://mabe02.github.io/lanterna/apidocs/3.1/com/googlecode/lanterna/TerminalSize.html).

* `TerminalSize(int columnas, int filas)` - Constructor.
* `int 	getColumns​()` - Devuelve el número de columnas.
* `int 	getRows​()` - Devuelve el número de filas.
* `TerminalSize max​(TerminalSize otro)` - Devuelve el máximo tamaño.
* `TerminalSize min​(TerminalSize otro)` - Devuelve el mínimo tamaño.
* `TerminalSize with​(TerminalSize size)` - Devuelve a sí mismo si son iguales, si no devuelve `size`.
* `TerminalSize withColumns​(int columnas)` - Devuelve un nuevo `TerminalSize` con las columnas indicadas.
* `TerminalSize withRows​(int filas)` - Devuelve un nuevo `TerminalSize` con las filas indicadas.
* `TerminalSize withRelative​(int deltaColumnas, int deltaFilas)` - Devuelve un nuevo `TerminalSize` sumando `(deltaColumnas, deltaFilas)`.
* `TerminalSize withRelative​(TerminalSize delta)` - Devuelve un nuevo `TerminalSize` sumando `delta`.
* `TerminalSize withRelativeColumns​(int delta)` - Devuelve un nuevo `TerminalSize` sumando `delta` columnas.
* `TerminalSize withRelativeRows​(int delta)` - Devuelve un nuevo `TerminalSize`  sumando `delta` filas.

## `SGR`

No todos los estilos funcionan en todas las consolas.

[Documentación](https://mabe02.github.io/lanterna/apidocs/3.1/com/googlecode/lanterna/SGR.html)

* `BLINK` - Texto que se prende y apaga.
* `BOLD` - Negrita.
* `BORDERED` - Dibuja un borde alrededor del texto.
* `CIRCLED` - Dibuja un círculo alrededor del texto.
* `CROSSED_OUT` - Tachado.
* `FRAKTUR` - Tipo de texto Fraktur.
* `ITALIC` - Itálicas.
* `REVERSE` - Intercambia el color del texto con el color de fondo.
* `UNDERLINE` - Subrayado.

## `TextColor`

Esta interfaz define colores. No todas las consolas soportan todos los modos de color.

Ver [documentación](https://mabe02.github.io/lanterna/apidocs/3.1/com/googlecode/lanterna/TextColor.html) para listas de colores válidos y más información.

Las clases estáticas contenidas son:
* `TextColor.ANSI` - Representa colores `ANSI`, es compatible con la mayor parte de las terminales.
* `TextColor.Factory` - Permite crear colores a partir de un `String`, como por ejemplo "blue", "#17" o "#1a1a1a".
* `TextColor.Indexed` - Representa colores indexados en formato XTerm 256.
* `TextColor.RGB` - Representa un color RGB con 8-bit por canal.

Tiene también estos métodos para obtener los valores de los canales RGB:
* `int getRed()`
* `int getBlue()`
* `int getGreen()`

## `TextGraphics`

Si no se indica el tipo de retorno, es del tipo `TextGraphics`.

Los métodos que requieren un `char` pueden tener una sobrecarga que permite usar `TextCharacter` en cambio.

Los métodos que tienen posiciones con `x` e `y` pueden tener sobrecargas que aceptan `TerminalPosition` en cambio.

[Documentación](https://mabe02.github.io/lanterna/apidocs/3.1/com/googlecode/lanterna/graphics/TextGraphics.html)

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

## `TextCharacter`

Representa un caracter con color y estilos. Es **inmutable**.

[Documentación](https://mabe02.github.io/lanterna/apidocs/3.1/com/googlecode/lanterna/TextCharacter.html)

* `DEFAULT_CHARACTER` - Constante que contiene el caracter por defecto.

Los siguientes métodos estáticos devuelven un arreglo `TextCharacter[]` desde varias fuentes.

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

## `TextImage`

Representa una "imagen" compuesta de caracteres.

[Documentación](https://mabe02.github.io/lanterna/apidocs/3.1/com/googlecode/lanterna/graphics/TextImage.html)

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

## `Screen`

Capa con doble buffer. Existe por sobre la capa `Terminal`. 

[Documentación](https://mabe02.github.io/lanterna/apidocs/3.1/com/googlecode/lanterna/screen/Screen.html)

* `DEFAULT_CHARACTER` - Constante que contiene el caracter por defecto.
* `void startScreen​()` - Inicializa la pantalla.
* `void stopScreen​()` - Cierra la pantalla y sale de modo privado.
* `void clear​()` - Borra la pantalla.
* `void close​()` - Equivalente a stopScreen().
* `TerminalSize doResizeIfNecessary​()` - Actualiza el tamaño de pantalla si fue cambiado.
* `TextCharacter getBackCharacter​(int columna, int fila)` - Devuelve el caracter trasero en la posicion indicada.
* `TextCharacter getBackCharacter​(TerminalPosition posicion)` - Igual al anterior.
* `void setCharacter​(int columna, int fila, TextCharacter caracter)` - Establece un caracter trasero.
* `void setCharacter​(TerminalPosition posicion, TextCharacter caracter)` - Igual al anterior.
* `TerminalPosition getCursorPosition​()` - Devuelve la posición del cursor.
* `void setCursorPosition​(TerminalPosition posicion)` - Establece la posición del cursor.
* `TextCharacter getFrontCharacter​(int columna, int fila)` - Devuelve el caracter frontal en la posicion indicada.
* `TextCharacter getFrontCharacter​(TerminalPosition posicion)` - Igual al anterior.
* `TabBehaviour getTabBehaviour​()` - Devuelve el comportamiento de tabulación.
* `void setTabBehaviour​(TabBehaviour comportamientoTab)` - Establece el comportamiento de tabulación.
* `TerminalSize getTerminalSize​()` - Devuelve el tamaño de la pantalla.
* `TextGraphics newTextGraphics​()` - Crea un `TextGraphics` para esta pantalla.
* `void refresh​()` - Mueve el buffer trasero al frontal.
* `void refresh​(Screen.RefreshType tipo)` - Mueve el buffer trasero al frontal indicando el tipo de refresco.
* `void scrollLines​(int primeraLinea, int ultimaLinea, int distancia)` - Desplaza un rango de lineas.
