package mx.gob.imss.ctirss.delta.cobranza.exception;

public class ErrorEnGeneracionComprobanteException extends Exception{
	
   public ErrorEnGeneracionComprobanteException(){
		 super(); 
   }
	
   public ErrorEnGeneracionComprobanteException(String error){
		 super(error); 
   }
   
   public ErrorEnGeneracionComprobanteException(String message, Throwable cause) {
       super(message, cause);
   }

}
