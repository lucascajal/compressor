# Compressor
A text and image compressor written in Java.

## File types and algorithms
This program can compress both `.txt` files and `.ppm` files. For the text files, there's three avaliable algorithms: `LZ78`, `LZSS` and `LZW`. For the image files, the program uses the `JPEG` algorithm.

## Execution
In order to execute the program, download the `EXE` folder and execute
```bash
> java -jar compresor.jar
```
There is a folder with test cases (named `Juegos de prueba`) with both `.txt` and `.ppm` files to test the compressor.

## Documentation
Inside the `DOC` folder there's a detailed explanation of how each compression algorithm works, a user manual (in spanish) and the UML diagrams.
