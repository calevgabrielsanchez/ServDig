/**
 * 
 */
package mx.gob.imss.ctirss.delta.cobranza.exception;

/**
 * @author vanderluk
 *
 */
public class ErrorEnGeneracionXMLPagoException extends Exception {
	
	public ErrorEnGeneracionXMLPagoException(){
		 super(); 
    }
	
	public ErrorEnGeneracionXMLPagoException(String error){
		 super(error); 
    }
	
	public ErrorEnGeneracionXMLPagoException(String message, Throwable cause) {
	        super(message, cause);
	}

}
