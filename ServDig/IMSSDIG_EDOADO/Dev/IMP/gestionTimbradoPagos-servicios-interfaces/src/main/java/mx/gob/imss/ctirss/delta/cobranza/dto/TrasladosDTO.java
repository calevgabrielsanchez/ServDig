package mx.gob.imss.ctirss.delta.cobranza.dto;

import java.io.Serializable;

public class TrasladosDTO implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TrasladoDTO[] traslados;

    public TrasladoDTO[] getTraslados() {
       
        return traslados;
    }

    public void setTraslados(TrasladoDTO[] traslados) {
        this.traslados = traslados;
    }
        
}
