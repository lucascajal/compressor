
package Dominio;

import java.io.File;
import java.util.ArrayList;
import javafx.util.Pair;


abstract public class Clasificador {
    
    private File f;
    private String ruta;
    private String nombre;
    private long tamano;
    
    
     public Clasificador(File f,String ruta, String nombre){
        this.f = f;
        this.ruta = ruta;
        this.nombre = nombre;
        this.tamano = 0;
    }
    
    public Clasificador(File f, String ruta, String nombre, long tamano) {
        this.f = f;
        this.ruta = ruta;
        this.tamano = tamano;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }    
    
    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }


    public long getTamano() {
        return tamano;
    }

    public void setTamano(long tamano) {
        this.tamano = tamano;
    }
    
    
    //ArrayList<Pair<String,Integer>> devolver_organizacion(){
      //  return null;
    //}
}
