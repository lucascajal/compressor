
package Dominio;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Esta interficie define los m�todos de los algortimos usados por el compresor.
 * @author: Kenny Alejandro
 */
public interface Algoritmo {
     /**
     * M�todo que realiza la compresi�n de un texto.
     * @param contenido_fichero El par�metro f nos indica el fichero de tipo texto(.txt) a comprimir.
	 * @return retorna los datos compridos y los deja en MyByteCollection   
     */
    public MyByteCollection comprimir(MyByteCollection contenido_fichero) throws IOException;
     
     /**
     * Metodo que realiza la compresion de una imagen.
     * @param contenido_fichero El parámetro f nos indica el fichero de tipo imagen(.ppm) a comprimir.
	 * @param calidad  El p�rametro calidad define el valor de calidad de la descompresión de la imagen.
	 * @param dSampling El parametro downsampling define la resolucion grafica.
	 * @return retorna los datos compridos y los deja en MyByteCollection 
     */
    public MyByteCollection comprimir(MyByteCollection contenido_fichero, int calidad,int dSampling) throws IOException;
    
    /**
     * M�todo que realiza la descompresi�n de un fichero.
     * @param contenido_fichero El par�metro f nos indica un fichero de comprimido.
	 * @return retorna los datos descompridos.
     */
    public ArrayList<Byte> descomprimir(MyByteCollection contenido_fichero) throws IOException, ClassNotFoundException; 
    
    /**
     * M�todo que retorna el nombre del Algortimo.
	 * @return retorna el nombre del Algortimo.
     */
    public String getNombre();
}