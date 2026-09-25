package mx.gob.imss.ctirss.correccion.web.controller.cedulascontruccion;

import java.io.File;
import java.io.IOException;

import mx.gob.imss.reader.service.impl.FileReadImpl;

import org.apache.log4j.Logger;


public class ProcesarListaThread extends Thread{
	static Logger logger = Logger.getLogger(ProcesarListaThread.class);
	File archivo = null;
	String numeroAnexo = null;
	Long id =0L;
	int flag=0;
	String filePath = null;
	
 //   FileRead lector =null;
	//public ProcessAccederCliente(String name, long indexCorrida,ArrayList list, int init, int end, Calendar fechaIN ) {
	public ProcesarListaThread() {
		
	}
	
	public void run(){
	    try {
   //         lector = new FileReadImpl();
            procesa();
        } catch (Exception e) {
            e.printStackTrace();
        }
	   
		
	}
	
	public void procesa(){
	    logger.debug("cargando archivo por medio de la base de datos");
	    FileReadImpl lector;
		try {
			lector = new FileReadImpl();
			lector.procesarArchivos();
		} catch (IOException e) { 
			e.printStackTrace();
		}
		
	}



}
