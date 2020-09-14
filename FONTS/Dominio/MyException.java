
package Dominio;


public class MyException extends Exception{
    private boolean esPorDefecto;
    /**
     * Metodo constructor por defecto. 
     */
    public MyException() {
    	esPorDefecto = true;
    }
    /**
     * Metodo constructor lanzar un mensaje de error.
     * @param error Texto explicativo del error. 
     */
    public MyException(String error) {
    	super(error);
    	esPorDefecto = false;
    }
	public boolean getEsPorDefecto() {
		return esPorDefecto;
	}
}
