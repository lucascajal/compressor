
package Dominio;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Fichero extends Clasificador{ //la clase contexto del patron estrategia
    
    private String tipo;
    private Algoritmo estrategia;
    private int calidad;
    private int dSampling;
    
    public Fichero(File f) throws MyException{
        super(f, f.getAbsolutePath(),f.getName(), f.length());
        this.calidad = 100;
        this.dSampling = 4;
        if(es_txt()) tipo = ".txt";
        else if(es_ppm()) tipo = ".ppm";
        else  tipo = "";//throw new MyException("El tipo de fichero no es valido."); //el primer mensaje de la array -> que esta en MyException
    }
    
    public String getTipo(){
        return this.tipo;
    }
    
    public void setCalidad(int calidad){
        this.calidad = calidad;
    }
    
    
    private boolean es_txt() throws MyException{
        String r = super.getRuta();
        int n = r.length();
        if(n >= 4)return r.charAt(n-4) == '.' & r.charAt(n-3) == 't' & r.charAt(n-2) == 'x' & r.charAt(n-1) == 't';
        return false;
    }
    
    private boolean es_ppm() throws MyException{
        String r = super.getRuta();
        int n = r.length();
        if(n >= 4) return r.charAt(n-4) == '.' & r.charAt(n-3) == 'p' & r.charAt(n-2) == 'p' & r.charAt(n-1) == 'm';
        return false;
    }
    
    public void setEstrategia(Algoritmo estrategia){
        this.estrategia = estrategia;

    }
    
    private MyByteCollection ejecutar_estrategia_texto(MyByteCollection contenido_fichero) throws IOException{
    	boolean b = !(estrategia == null); 
    	if(b)System.out.println("estrategia es "+ estrategia.getNombre());
    	else System.out.println("no tiene estrategia");
        return estrategia.comprimir(contenido_fichero);
    }
    
    private MyByteCollection ejecutar_estrategia_imagen(MyByteCollection contenido_fichero, int calidad,int dSampling) throws IOException{
        return estrategia.comprimir(contenido_fichero, calidad,dSampling);
    }
    
    
    public ArrayList<Byte> comprimir(ArrayList<Byte> contenido_fichero) throws MyException{
        MyByteCollection comprimido = new MyByteCollection(contenido_fichero);
        if(tipo.equals(".txt")) {
            try {
                comprimido = ejecutar_estrategia_texto(comprimido);
            } catch (IOException ex) {
                throw new MyException("El mensaje de error es :" + ex.getMessage());
            }
        } else if(tipo.equals(".ppm")){
            try {
                comprimido = ejecutar_estrategia_imagen(comprimido,this.calidad,this.dSampling);
            } catch (IOException ex) {
                throw new MyException("El mensaje de error es :" + ex.getMessage());
            }
        }
        return comprimido.getContenido();
    }

	public void setDSampling(int dSampling) {
		this.dSampling = dSampling;
	}
    
}
