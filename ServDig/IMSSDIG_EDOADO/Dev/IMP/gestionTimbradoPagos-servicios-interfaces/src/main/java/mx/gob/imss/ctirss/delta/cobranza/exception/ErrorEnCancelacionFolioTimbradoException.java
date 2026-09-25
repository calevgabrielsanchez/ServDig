package mx.gob.imss.ctirss.delta.cobranza.exception;

public class ErrorEnCancelacionFolioTimbradoException extends Exception {
	
	public ErrorEnCancelacionFolioTimbradoException(){
		 super(); 
	}
	
    public ErrorEnCancelacionFolioTimbradoException(String error){
		 super(error); 
    }
  
    public ErrorEnCancelacionFolioTimbradoException(String message, Throwable cause) {
      super(message, cause);
   }

}
