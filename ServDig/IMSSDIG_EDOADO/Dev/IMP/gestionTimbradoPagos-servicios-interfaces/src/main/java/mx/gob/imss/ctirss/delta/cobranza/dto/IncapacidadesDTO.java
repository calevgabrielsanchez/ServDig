package mx.gob.imss.ctirss.delta.cobranza.dto;

import java.io.Serializable;

public class IncapacidadesDTO implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected IncapacidadDTO[] incapacidad;

    public  IncapacidadDTO[] getIncapacidad() {
        return incapacidad;
    }

    public void setIncapacidad( IncapacidadDTO[] incapacidad) {
        this.incapacidad = incapacidad;
    }
    
    
}
