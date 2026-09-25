/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception;

import javax.xml.ws.WebFault;

/**
 * @author NOVUTECK1
 *
 */
@WebFault 
public class SUAException extends Exception  {
	
	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = 1L;
		
		
	/** 
	 * Fault Code 
	 */
	private String faultcode; 
	
	/**
     * Constructor para un error en el motor de calculo
     * 
     * @param message
     */
    public SUAException(String message) {  
        super(message);
        this.setFaultcode("");
    }
    
	/**
	 * Constructor para un error en el motor de calculo
	 * @param code
	 * @param message
	 */
	public SUAException(String code, String message) {	
		super(message);
		this.setFaultcode(code);
	}


	/**
	 * Constructor si ocurre un error en acciones del motor sua
	 * @param code
	 * @param message
	 * @param ex
	 */
	public SUAException(String code, String message, Throwable ex) {		
		super(message, ex);
		this.setFaultcode(code);		
	}


	/**
	 * @return the faultcode
	 */
	public String getFaultcode() {
		return faultcode;
	}


	/**
	 * @param faultcode the faultcode to set
	 */
	public void setFaultcode(String faultcode) {
		this.faultcode = faultcode;
	}	
	
}
