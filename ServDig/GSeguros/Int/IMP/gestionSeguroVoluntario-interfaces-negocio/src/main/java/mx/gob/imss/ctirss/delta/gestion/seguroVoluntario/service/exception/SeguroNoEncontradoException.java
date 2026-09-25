/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception;

/**
 * Error de seguros no encontrados
 * @author NOVUTECK1
 *
 */
public class SeguroNoEncontradoException extends Exception  {
    
    /**
     * Serial version id
     */
    private static final long serialVersionUID = 1L;
        
        
    /** 
     * Fault Code 
     */
    private String faultcode; 
    
    /**
     * Constructor default de la excepcion
     */
    public SeguroNoEncontradoException() {  
        super("No se encontró un seguro asociado.");
        this.setFaultcode("001");
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
