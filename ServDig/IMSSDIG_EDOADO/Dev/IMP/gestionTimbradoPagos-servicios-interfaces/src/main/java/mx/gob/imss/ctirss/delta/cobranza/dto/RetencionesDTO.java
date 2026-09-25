package mx.gob.imss.ctirss.delta.cobranza.dto;

import java.io.Serializable;

public class RetencionesDTO implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RetencionDTO[] retenciones;

    public RetencionDTO[] getRetenciones() {
       
        return retenciones;
    }

    public void setRetenciones(RetencionDTO[] retenciones) {
        this.retenciones = retenciones;
    }
    
    
    
}
