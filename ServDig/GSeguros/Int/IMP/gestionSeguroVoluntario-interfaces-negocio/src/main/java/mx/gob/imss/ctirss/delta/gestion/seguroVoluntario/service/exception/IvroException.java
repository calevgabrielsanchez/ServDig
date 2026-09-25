/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception;

/**
 * Excepciones genericas del IVRO
 * @author NOVUTECK1
 *
 */
public class IvroException extends Exception  {
    
    /**
     * Serial version id
     */
    private static final long serialVersionUID = 1L;
        
        
    /** 
     * Fault Code 
     */
    private String faultcode; 
    
    /**
     * Constructor para un error en el seguro ivro
     * 
     * @param message
     */
    public IvroException(String message) {  
        super(message);
        this.setFaultcode("");
    }
    
    /**
     * Constructor para un error en el seguro ivro
     * @param code
     * @param message
     */
    public IvroException(String code, String message) {  
        super(message);
        this.setFaultcode(code);
    }


    /**
     * Constructor si ocurre un error en acciones del seguro ivro
     * @param code
     * @param message
     * @param ex
     */
    public IvroException(String code, String message, Throwable ex) {        
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
