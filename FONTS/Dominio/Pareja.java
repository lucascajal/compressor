
package Dominio;

import java.io.IOException;
import java.util.ArrayList;

public class Pareja {

    private ArrayList<Integer> primero = new ArrayList<>();
    private ArrayList<Byte> segundo = new ArrayList<>();

    public Pareja() {}

    public ArrayList<Integer> getPrimero() {
        return primero;
    }
    public Pareja(ArrayList<Integer> primero,ArrayList<Byte> segundo){
        this.primero = primero;
        this.segundo = segundo;
    
    }
    
    public ArrayList<Byte> getSegundo() {
        return segundo;
    }

    public void setPrimero(ArrayList<Integer> primero) {
        this.primero = primero;
    }

    public void setSegundo(ArrayList<Byte> segundo) {
        this.segundo = segundo;
    }

    
    public void addPrimero(ArrayList<Integer> primero) {
        this.primero.addAll(primero);
    }

    public void addSegundo(ArrayList<Byte> segundo) {
        this.segundo.addAll(segundo);
    }
    
    public void addPrimeroInteger(int i){
        this.primero.add(Integer.valueOf(i));
    }
    
    public void addSegundoByte(Byte b){
        this.segundo.add(b);
    
    }
    public void addSegundo(Integer v) throws IOException {
        Byte b;
        for (int i = 24; i >=0 ; i = i-8 ){
            b = (byte)(v >>> i);
            this.segundo.add(b);
        }
    }
    
    public void addSegundoShort(Short v) throws IOException {
        Byte b;
        for (int i = 8; i >=0 ; i = i-8 ){
            b = (byte)(v >>> i);
            this.segundo.add(b);
        }
    }
    public int sizePrimero(){
        return this.primero.size();
    }
    
     public int sizeSegundo(){
        return this.segundo.size();
    }
     
    
    
}   
    
