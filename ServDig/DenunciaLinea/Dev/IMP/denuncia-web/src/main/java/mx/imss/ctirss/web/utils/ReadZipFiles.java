package mx.imss.ctirss.web.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class ReadZipFiles {

	 private String archivoZip;
	    private String elemento;
	    private File zipFile;
	        
	    private ZipFile zf;
	    public ReadZipFiles(String archivoZip,String elemento) throws IOException{
	        this.archivoZip= archivoZip;
	        this.elemento = elemento;
	        zf = new ZipFile(archivoZip);
	    }
	     
	    public ReadZipFiles(String archivoZip) throws IOException{
	        this.archivoZip= archivoZip;
	        zf = new ZipFile(archivoZip);
	    }
	    
	    public ReadZipFiles(File zipFile) throws ZipException, IOException{
	        this.zipFile= zipFile;
	        zf = new ZipFile(zipFile);
	    }
	    
	    public InputStream getFileByName() throws Exception {
	        
	        if(elemento!=null){
	            try {

	                Enumeration entries = zf.entries();
	                
	                while (entries.hasMoreElements()) {
	                  ZipEntry ze = (ZipEntry) entries.nextElement();
	                  
	                  if(ze.getName().equalsIgnoreCase(elemento)){
	                      long size = ze.getSize();
	                      
	                      if (size > 0) {
	                          return zf.getInputStream(ze);
	                      }else throw new Exception("Archivo Vac�o ZIP:"+archivoZip);
	                  }
	                }
	                
	              } catch (IOException e) {
	                e.printStackTrace();
	                throw new Exception(e.getMessage());
	              }
	        
	        }else{
	            throw new Exception("No se ha especificado el archivo que se desea extraer del ZIP:"+archivoZip);
	        }
	        return null;
	  }
	  
	  public InputStream getUniqueFile() throws Exception {
	      try {
	      
	        Enumeration entries = zf.entries();
	        Boolean continuar = false;
	        
	        while (entries.hasMoreElements()) {
	          ZipEntry ze = (ZipEntry) entries.nextElement();
	          
	          try{
	              ZipEntry ze2 = (ZipEntry) entries.nextElement();
	          }catch(Exception e){
	              continuar = true;
	          }
	          
	          if(continuar){
	              long size = ze.getSize();
	              if(size > 0){
	                return zf.getInputStream(ze);
	              }else{
	                  throw new Exception("El archivo ZIP:"+archivoZip+" [Est� Vac�o]");
	              }
	          }else{
	              throw new Exception("El archivo ZIP:"+archivoZip+" [Contiene mas de un elemento]");
	          }
	        }
	      } catch (IOException e) {
	          e.printStackTrace();
	          throw new Exception(e.getMessage());
	        
	      }
	      return null;
	    }
	  
	    public void closeZipFile(){
	        try {
	            zf.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
}
