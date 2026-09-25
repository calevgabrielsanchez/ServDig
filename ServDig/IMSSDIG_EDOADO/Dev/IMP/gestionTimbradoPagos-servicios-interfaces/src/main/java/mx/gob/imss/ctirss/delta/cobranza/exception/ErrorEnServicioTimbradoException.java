package mx.gob.imss.ctirss.delta.cobranza.exception;

import java.rmi.RemoteException;

public class ErrorEnServicioTimbradoException extends RemoteException{
	
	  public ErrorEnServicioTimbradoException(){
			 super(); 
	   }
		
	   public ErrorEnServicioTimbradoException(String error){
			 super(error); 
	   }
	   
	   public ErrorEnServicioTimbradoException(String message, Throwable cause) {
	        super(message, cause);
	   }

}
