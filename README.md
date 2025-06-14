# Intro
[Lanterna](https://github.com/mabe02/lanterna) es una librería de Java para escribir interfaces gráficas simples en modo texto. Es compatible con varios tipos de terminales y emuladores de terminal, como UNIX, Swing, putty, y muchas más.
Es útil para reemplazar la salida a consola estándar de Gradle, que tiene muy poca capacidad de personalización y pobre interacción.
Lanterna tiene tres niveles de interacción:
1) Una interfaz de bajo nivel. Permite manipular la posición del cursor, cambiar el color del texto y del fondo, y escribir caracteres en la posición indicada. El paquete que incluye estas clases es `terminal`.
2) Un buffer de pantalla, que permite preparar de antemano lo que se enviará a pantalla para luego enviar toda esa información a la pantalla. Estas clases están el el paquete `screen`.
3) Un grupo de herramientas para hacer un GUI completo con ventanas, botones, labels y otros componentes. Estas clases están en el paquete `gui2`.

# Consideraciones iniciales
Para utilizar Lanterna en un proyecto de Java con Gradle, primero hay que declarar la dependencia a esta librería en el archivo de configuración de Gradle, `build.gradle`, de esta manera (para la versión 3.1.2 en este caso):
```
dependencies {  
    //otras dependencias aquí
    implementation 'com.googlecode.lanterna:lanterna:3.1.2'
}
```
Luego de modificar este archivo, aparecerá este icono en la esquina superior derecha, al presionarlo se reconstruye el proyecto permitiéndonos utilizar esta librería:

![image](https://github.com/user-attachments/assets/8f2bdc32-3e69-4792-8278-da45fecc57d4)

## Tutoriales

1. [Tutorial 1](<Tutorial 1/Readme.md>) - Terminal parte 1
2. [Tutorial 2](<Tutorial 2/Readme.md>) - Terminal parte 2
3. [Tutorial 3](<Tutorial 3/Readme.md>) - Screen
4. [Tutorial 4](<Tutorial 4/Readme.md>) - GUI
