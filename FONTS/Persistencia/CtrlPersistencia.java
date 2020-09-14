package Persistencia;

import Dominio.MyException;
import javafx.util.Pair;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.imageio.ImageIO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;




public class CtrlPersistencia {
    
    private RandomAccessFile comprimido;
    private String ruta_comprimida;
    private String fileME = "./medidasEstadisticas.txt";
    
    /**
     * Contiene todos las ayudas almacenadas en el sistema
     */
    private Collection<Ayuda> ayudas;
    
    /*
    Pre: cierto
    Post: crea una instancia  
    */
    public CtrlPersistencia() throws Exception{ 
    	ayudas = new ArrayList<>();
   	    new DatosAyudas(ayudas);
   
    }
    //-------------------------Comprimido
    /*crea el fichero unico donde iremos escibiendo */
    public void crearFicheroDeSalida(String ubicacion) throws IOException {  
        this.ruta_comprimida = ubicacion;
        System.out.println("La ruta pasada es"+ ubicacion);
        crearFichero(ubicacion);
        comprimido = new RandomAccessFile(ubicacion, "rw"); //doy permiso para escribir en el fichero creado
    }
    
    public void escribirShortEnFicheroDeSalida(int n) throws IOException{
        comprimido.writeShort(n);
    }
    
    public void escribirByteEnFicheroDeSalida(int n) throws IOException{
        comprimido.writeByte(n); 
    }
    
    public void escribirIntsEnFicheroDeSalida(ArrayList<Integer> contenido) throws IOException{
    	int size = contenido.size();
    	for(int i=0; i< size; ++i){
    		  comprimido.writeInt(contenido.get(i));
        }
    }
    
    
    public void  escribirStringEnFicheroDeSalida (String s) throws IOException{
        comprimido.writeBytes(s);
    }
    
    public int getTamano() throws IOException{
        return (int)comprimido.length();
    }
    
    public void  escribirBytesEnFicheroDeSalida (ArrayList<Byte> contenido) throws IOException{
    	int size = contenido.size();	
    	for(int i = 0; i< size; i++){
    		comprimido.write(contenido.get(i));
    	}
    }
    
   
    public void cerrarFicheroDeSalida() throws IOException{
        if(comprimido != null) comprimido.close();
    }
    public void borrarFicheroDeSalida(){
    	System.out.println("path_comprimido:" + ruta_comprimida);
    	Path p = Paths.get(ruta_comprimida);
    	try {
			Files.delete(p);
			System.out.println("se borro");
		} catch (IOException e) {
			System.out.println("o o error");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
//-----------------------------------------------------------------------------------    
    
    public void crearFichero(String ubicacion) throws IOException{
    	System.out.println("Intento crear fichero: "+ ubicacion);
        File f = new File(ubicacion);
        f.createNewFile();//creo un fichero en la ubicacion solicitada por el usuario (es un fichero unico donde ire escribiendo toda la informacion, que despues necesitare para descomprimir)
    }
    
    
    public void crearCarpeta(String ubicacion) throws IOException {  
        File  c = new File(ubicacion);
        c.mkdir();
    }
    
    public void escribirDescomprimido(String ruta,ArrayList<Character> d) throws IOException{
        RandomAccessFile descomprimido = new RandomAccessFile(ruta, "rw");
        for (int j = 0; j < d.size(); j++)
            descomprimido.writeByte(d.get(j));
        descomprimido.close();
    }
    
    
    public void guardarMedidaEstadistica(Date fecha, long tiempo, int tamano_inicial, int tamano_final, double ratio_de_compresion, float Velocidad_de_compresion)throws IOException, MyException{
        String s = "Fecha: "+fecha+ " Tiempo: "+tiempo+" Tamaño inicial: "+tamano_inicial+" Tamaño final: "+tamano_final+" Ratio de compresión: "+ratio_de_compresion+" Velocidad de compresión: "+Velocidad_de_compresion;
            RandomAccessFile raf = new RandomAccessFile(fileME, "rw");
            raf.seek(new File(fileME).length());
            raf.writeBytes(s);
            raf.writeBytes("\r\n");
            raf.close();
    }
    
	public boolean comprimidoTieneContrasena(String ruta_compri) throws IOException {
		File f_principal = new File(ruta_compri);
        RandomAccessFile raf = new RandomAccessFile(f_principal, "r");  
        raf.seek(0);
        char c = (char)raf.readByte();
        raf.close();
        return (c == 'Y');
	}    
    
    
	public String devuelveComprimidoContrasena(String ruta_comprimido) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(ruta_comprimido, "r");  
		raf.seek(0);
		String s = new String();
		char c = (char)raf.readByte();
		if(c == 'N') s = "";
		else {
			int tam_contra = (int)raf.readByte(); 
	        for(int i=0; i<tam_contra; ++i) {
	        	s = s.concat(String.valueOf((char)raf.readByte()));
	        }
		}    
        raf.close();
        return s;
	}
	
	public ArrayList<Byte> lectura_fichero_bytes(String ruta) throws IOException {
		RandomAccessFile rf = new RandomAccessFile(ruta, "r");
		ArrayList<Byte> leido = new  ArrayList<>();
		for(int i = 0; i <(int)rf.length(); i++) {
			leido.add(rf.readByte());
		}
		rf.close();
		return leido;
	}

	public void escribeBytes(String ruta, ArrayList<Byte> contenido) throws IOException {
		 RandomAccessFile destino = new RandomAccessFile(ruta, "rw");
	        for (int j = 0; j < contenido.size(); j++)
	            destino.writeByte(contenido.get(j));
	        destino.close();
	}
	public ArrayList<Integer> retornaListaSeparadoresComprimido(String ruta_comprimido) throws IOException {
		RandomAccessFile rf = new RandomAccessFile(ruta_comprimido, "r");
		int tam_ob = (int)rf.length();
        rf.seek(tam_ob-2);
        int n_sep = rf.readShort();
        System.out.println("numero de separadores:" + n_sep);
        rf.seek((tam_ob-2)-(n_sep*4));
		ArrayList<Integer> separadores = new ArrayList<>();
        for(int i=0; i< n_sep; ++i){
            separadores.add(rf.readInt());
        }
        rf.close();
		return separadores;
	}
	public ArrayList<Pair<Integer, String>> leeEstructuraComprimido(String ruta_comprimido) throws Exception {
		// TODO Auto-generated method stub
		RandomAccessFile raf = null;
		try {
			raf= new RandomAccessFile(ruta_comprimido, "r");
			int size_hasta_password = devuelveComprimidoContrasena(ruta_comprimido).length();
			if(size_hasta_password == 0) size_hasta_password++;
			else size_hasta_password += 2;
			raf.seek(size_hasta_password);
			int n_elementos_raiz = raf.readShort();
			ArrayList<Pair<Integer, String>> estructura = new ArrayList<>();
			estructura.add(new Pair<Integer, String>(n_elementos_raiz,""));	//Este primer guarda el numero de clasificadores raiz.
			estructura.addAll(leer_estructura_comprido_recursivo(raf, n_elementos_raiz));
			return estructura;
		}catch (Exception e) {
			throw e;
		}finally {
			if(raf!=null) raf.close();
		}
	}
	
    // retorna la cantidad de posiciones que se leeido dentro del comprimido aparitir de la posicion dada por el parametro pos
	//no hacer raf.close() al interior debe de estar tratado en las funciones que lo llamen.
    private ArrayList<Pair<Integer, String>> leer_estructura_comprido_recursivo(RandomAccessFile raf,int n_elementos) throws FileNotFoundException, IOException, Exception{
    	ArrayList<Pair<Integer, String>> estructura = new ArrayList<>();	
            for(int i=0; i<n_elementos; ++i){
                StringBuffer nombre = new StringBuffer();
                byte b;
                while((char)(b =raf.readByte()) != '.'){
                    nombre.append((char)b);
                }
                System.out.println("nombre:"+ nombre);
                
                //comprobar el tipo (despues del punto que es lo que viene)
                if((char)(b =raf.readByte()) == 'c'){
                    int n_e = (int)raf.readShort();
                    estructura.add(new Pair<Integer, String>(n_e,nombre.toString()));
                    estructura.addAll(leer_estructura_comprido_recursivo(raf,n_e));
                }
                
                //si es un fichero
                else if( ((char)b == 't') || ((char)b == 'p')){
                        nombre.append('.');
                        nombre.append((char)b);   
                        b =raf.readByte();
                        nombre.append((char)b);
                        b =raf.readByte();
                        nombre.append((char)b);
                        estructura.add(new Pair<Integer, String>(-1,nombre.toString()));
                }
                else {
                	throw new MyException("No soy carpeta ni fichero");
                }
            } 
      return estructura;
    }
	  
	public ArrayList<Byte> lectura_fichero_bytes_intervalo(String ruta, int pos_ini, int pos_final) throws Exception {
		ArrayList<Byte> contenido = new ArrayList<>();
		RandomAccessFile raf = null;
		try {
			raf= new RandomAccessFile(ruta, "r");
			raf.seek(pos_ini);
			while(pos_ini < pos_final) {
				contenido.add(raf.readByte());
				pos_ini++;
			}
			return contenido;
		}catch (Exception e) {
			throw e;
		}finally {
			if(raf!=null) raf.close();
		}
	}
	public void borrarRuta(String ruta_raiz) {
		File f = new File(ruta_raiz);
		if(f.exists()) borrado_rec(f);	
	}
	
	private void borrado_rec(File f) {
		if(f.isDirectory()) {
			for(File f2: f.listFiles()) {
				if(f2.isDirectory()) {
					borrado_rec(f2);
				}
				f2.delete();
			}
		}
		f.delete(); 
	}
	public String obtenerContenidoTexto(String ruta) throws IOException {
		// TODO Auto-generated method stub
		
		FileInputStream file = new FileInputStream(ruta);	
		InputStreamReader input = new InputStreamReader(file,"utf-8"); 
		BufferedReader lector = new BufferedReader(input);
		
		
		String texto = new String();
		String aux;
	
		if((aux = lector.readLine()) != null){
			texto = texto.concat(aux);
			while ((aux = lector.readLine()) != null){
				texto = texto.concat("\n" + aux); 
			}
		}			
		System.out.println("tamano de texto:"+ texto.length());
		lector.close();
		return texto;
		
	}
	
	
	
	
	
	/**
	 * Metodo que retorna un byte[] con el contenido de una imagen .ppm convertida a .bpm
	 * @throws IOException 
	 * 
	 * **/
   public byte[] obtenerContenidoImagenParaVisualizar(String ruta) throws IOException {
	   int numOfRows = 0, numOfCols = 0;
       File f = new File(ruta);
       FileInputStream fileReader = new FileInputStream(f);
       BufferedInputStream bufferedReader = new BufferedInputStream(fileReader);
       String magicnum = "";
       while (true) {
           int b = bufferedReader.read();
           if (b != -1) {
               char c = (char) b;
               if (!Character.isWhitespace(c)) {
                   magicnum += c;
               } else {
                   break;
               }
           } else {
               break;
           }
       }
       char tipus1 =0;
       String altu="";
       String anchu="";
       while((tipus1 =(char)bufferedReader.read())!= ' ') {
           altu+=Character.toString(tipus1);
       }
       altu = altu.trim();
       while((tipus1 =(char)bufferedReader.read())!= '\n') {
           anchu+=Character.toString(tipus1);
       }
       anchu = anchu.trim();

       numOfRows = Integer.valueOf(altu);
       numOfCols = Integer.valueOf(anchu);
       bufferedReader.read();
       BufferedImage imatge = new BufferedImage(numOfRows, numOfCols, BufferedImage.TYPE_INT_RGB);
       ByteArrayOutputStream baos = new ByteArrayOutputStream();
       byte[] imageInByte = null;
       if (magicnum.equals("P6")) {

           for (int y = 0; y < numOfCols; y++) {
               for (int x = 0; x < numOfRows; x++) {
                   int[] RGB = new int[3];
                   RGB[0] = bufferedReader.read();
                   RGB[1] = bufferedReader.read();
                   RGB[2] = bufferedReader.read();
                   Color n = new Color(RGB[0], RGB[1], RGB[2],1);

                   imatge.setRGB(x, y, n.getRGB());
               }
           }
           ImageIO.write(imatge, "bmp", baos);
           baos.flush();
           imageInByte = baos.toByteArray();
       }
       bufferedReader.close();
	return imageInByte;
   }
   
   public ArrayList<Byte> LeerImagenPPM(String ruta) {
	   return PPMSaver.readPPM(ruta);
   }
   
   public void GuardarImagenPPM(String ruta, ArrayList<Byte> contenido) {
	   PPMSaver.writePPM(ruta, contenido);
   }
   
   /**
   * Función que lee los datos almacenados en disco
   * @param filename
   * @return retorna json con el contenido del fichero
   * @throws Exception 
   */
  public static JSONObject readJsonFile(String filename) throws Exception {  
      FileReader reader = new FileReader(filename);
      JSONParser jsonParser = new JSONParser();
      return (JSONObject) jsonParser.parse(reader);
  }
  
   /**
    * Retoorna todos las ayudas del sistema
    * @return json ayudas
    */
@SuppressWarnings("unchecked")
public String getAyudas(){
       JSONObject jsonObj = new JSONObject();
       JSONArray jsonArr = new JSONArray();
       for(Ayuda ayud : ayudas){
           JSONObject jsonAyuda = new JSONObject();  
           jsonAyuda = ayud.toJson();
           jsonArr.add(jsonAyuda);
       }
       jsonObj.put("Ayudas",jsonArr);
       return jsonObj.toString();
   }
   
   
   
}