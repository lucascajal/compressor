package Dominio;

import java.io.IOException;
import java.util.ArrayList;


public class MyByteCollection {
	
		private ArrayList<Byte> contenido = null ;
		int indice;
	    public MyByteCollection() {
	    	this.contenido = new ArrayList<>();
	    	this.indice = 0;
	    }
	    public MyByteCollection(ArrayList<Byte> contenido) {
	        if(contenido == null) this.contenido = new ArrayList<>();
	    	this.contenido = contenido;
	        this.indice = 0;
	    }
	    
	    public ArrayList<Byte> getContenido() {
	        return contenido;
	    }
	    
	    public void writeByte(Byte b){
	        this.contenido.add(b);
	        ++indice;
	    }
	    public void writeInteger(Integer v) throws IOException {
	        Byte b;
	        for (int i = 24; i >=0 ; i = i-8 ){
	            b = (byte)(v >>> i);
	            this.contenido.add(b);
	        }
	        indice += 4; 
	    }
	    
	    public void writeShort(Short v) throws IOException {
	        Byte b;
	        for (int i = 8; i >=0 ; i = i-8 ){
	            b = (byte)(v >>> i);
	            this.contenido.add(b);
	        }
	        indice +=2;
	    }
	    
	    public byte readByte(){
	    	++indice;
	        return this.contenido.get(indice-1);
	    }
	    public int readInt( ) throws IOException {
	        
	        int valor,aux;
	        valor = 0;
	        for (int i = 24; i >=0  ; i = i-8 ){
	        	aux = (int) this.contenido.get(indice)& (int)0x000000FF;
	            aux = (aux << i);
	            valor = aux |valor;
	            ++indice;
	        }   
	        return valor;
	    }
	    
	    public short readShort() throws IOException {
	        //forma 1
	       int aux,valor;
	       valor = 0;
	        for (int i = 8; i >=0 ; i = i-8 ){
	        	aux = (int) this.contenido.get(indice) & (int)0x000000FF;
	            aux = (aux << i);
	            valor = aux |valor;
	            ++indice;
	        }
	        return (short)valor;
	        //forma 2
	        /*int valor;
	        valor = 0;
	        int alta = this.contenido.get(indice) & (int)0x000000FF;
	        alta = alta << 8;
	        ++indice;
	        int baja =  this.contenido.get(indice) & (int)0x000000FF;
	        ++indice;
	        return (short) (alta | baja);*/
	    }
	    
	    public int readUnsignedShort() throws IOException {
	    	return readShort() & 0x0000FFFF;
	    	
	    }
		public int getSize() {
			return this.contenido.size();
		}
		
		public void setIndice(int indice) {
			if(indice >= 0 && indice < contenido.size()) {
					this.indice = indice;
			}
		}
		
		
		
}