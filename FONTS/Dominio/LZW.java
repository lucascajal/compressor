package Dominio;
import java.util.*;
import java.io.IOException;


/**
 * LZW es una clase Singleton que implementa las deficiciones de la clase Algoritmo (que es una interficie)
 * @author: Mois�s Balcells
 */
public class LZW implements Algoritmo{
    
    
    private static LZW instance = null;
    
    /**
     * Constructora privada de la clase LZW
     */
    /*constructor, es privado por lo tanto puede ser accesible des de la propia clase*/
    private LZW(){
    }
    
    /*incializacion lazy del singleton*/
    /**
     * M�todo que inicializa de la forma lazy el singleton.
     * @return Una instancia de la clase LZW
     */
    public static LZW getInstances(){
        if(instance == null)
            instance = new LZW();
        return instance;
    }
    
    
    /*Para Comprimir, devuelve un pair*/
    /**
     * M�todo que realiza la compresi�n de un texto.
     * @param origen El par�metro origen es un objeto de la clase MyByte Collecition y esta contiene un ArrayList<Byte> como atributo nos indica el contenido que queremos comprimir.
     * @return Un MyByteCollection, en este objeto se encontrara la compresi�n
     */
    @Override
    public MyByteCollection comprimir(MyByteCollection origen)throws IOException{
        
    	MyByteCollection destino = new MyByteCollection();
        destino.writeByte((byte)'W'); //Se pone ultimo car�cater de mi algoritmo en este caso la W, porque a la hora de descomprimir tenemos que saber que algoritmo ha comprimido
        short dictSize = 256;
        Map<String,Short> dictionary = new HashMap<String,Short>();
        
        for (short i = 0; i < 256; i++){ //se llena el diccionario con todo los caracters completos 
        	//(los numeros y las letras no se ponen as� que si quieres comprimir un archivo con acentos o n�meros fallar� la compresi�n
            dictionary.put("" + (char)i, i);
        }
        
        String wc, w = "";
        
        int tamano = (int)origen.getSize(); //tama�o del fichero a descomprimir
        char c;
        int i = 0;
        while(i < tamano){
            byte valor = origen.readByte(); //lees un byte que es un char
            c = (char)valor;
            
            wc = w + c;   //empiezas a realizar la compresi�n del algoritmo lzw 
            if(dictionary.containsKey(wc)){
                w = wc;
            }
            else {
                destino.writeShort(dictionary.get(w));
                dictionary.put(wc, dictSize++);
                w = "" + c;
            }
            i++;
        }
        destino.writeShort(dictionary.get(w));
        return destino;
    }
    
    
    
    
    /**
     * M�todo que realiza la compresi�n de una imagen.
     * @param origen El par�metro origen es un objeto de la clase MyByte Collecition y esta contiene un ArrayList<Byte> como atributo nos indica el contenido que queremos comprimir.
     * @param calidad  El p�rametro calidad define el valor de calidad de la descompresi�n de la imagen.
     * @param dSampling El par�metro dSampling nos indica el factor de Sampling de la imagen.
     * @return Un MyByteCollection, en este objeto se encontrara la compresi�n
     */
    /*No hace nada, es para el algoritmo JPEG*/
    @Override
    public MyByteCollection comprimir(MyByteCollection origen,int calidad,int dSampling) throws  IOException{
        return null;
    }
    
    
    /**
     * M�todo que realiza la descompresi�n de un fichero.
     * @param origen El par�metro origen es un objeto de la clase MyByte Collecition y esta contiene un ArrayList<Byte> como atributo nos indica el contenido que queremos comprimir.
     * @return retorna un ArrayList<Byte> el cual contiene los datos descompridos.
     */
    @Override
    public ArrayList<Byte> descomprimir(MyByteCollection origen)throws IOException{
        int pos_inicial = 0;
        int pos_final = origen.getSize();
        if(((char)origen.readByte())!= 'W') {
            System.out.println("El documento no fue comprimido con el algoritmo LZW.");
            return null;
        }
        ++pos_inicial;
        ArrayList<String> result = new ArrayList<String>(); //creas un array de strings donde meter�s el resultado de la descompresi�n
        short dictSize = 256;
        Map<Short,String> dictionary = new HashMap<Short,String>();
        for (short i = 0; i < 256; i++){ //se llena el diccionario con todo los caracters completos 
        	//(los numeros y las letras no se ponen as� que si quieres descomprimir un archivo con acentos o n�meros fallar� la descompresi�n
            dictionary.put(i, "" + (char)i);
        }
        
        
        String w = "" + (char)origen.readShort(); //lees el primer caracter comprimido
        pos_inicial += 2; //sumas dos porque lees un short y ocupa dos bytes
        result.add(w);
        String entry;
        short valor;
        
        while(pos_inicial < pos_final){ //vas leyendo valores del fichero des de la posici�n inicial que es donde empieza la compresi�n del fichero
        	//hasta la posici�n final que es donde termina
            valor = origen.readShort();
            if (dictionary.containsKey(valor)){ //empiezas a realizar la descompresi�n del algoritmo lzw
                entry = dictionary.get(valor);
            }
            else if (valor == dictSize){
                entry = w + w.charAt(0);
            }
            else{
                throw new IllegalArgumentException("Mala compresion valor: " + valor);
            }
            
            result.add(entry);
            dictionary.put(dictSize++, w + entry.charAt(0));
            w = entry;
            pos_inicial += 2; //sumas dos porque lees un short y ocupa dos bytes
        }
        
        ArrayList<Byte> resultado = new ArrayList<Byte>();
        for(int i = 0; i < result.size(); i++){
            String cadena = result.get(i);
            byte[] bytes = cadena.getBytes();
            for(int j = 0; j < bytes.length; j++)resultado.add(bytes[j]);
        }
        return resultado;
    }
    
    /**
     * M�todo que retorna el nombre del Algortimo.
     * @return retorna el nombre del Algortimo.
     */
    @Override
    public String getNombre() {
        return "LZW";
    }
}