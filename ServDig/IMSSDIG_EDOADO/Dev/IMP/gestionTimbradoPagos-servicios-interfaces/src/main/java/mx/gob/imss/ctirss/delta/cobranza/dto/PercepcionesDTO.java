package mx.gob.imss.ctirss.delta.cobranza.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PercepcionesDTO implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected PercepcionDTO[] percepcion;
    protected BigDecimal totalGravado;
    protected BigDecimal totalExento;

    public PercepcionDTO[] getPercepcion() {
       
        return percepcion;
    }

    public void setPercepcion(PercepcionDTO[] percepcion) {
        this.percepcion = percepcion;
    }

    public BigDecimal getTotalGravado() {
        return totalGravado;
    }

    public void setTotalGravado(BigDecimal totalGravado) {
        this.totalGravado = totalGravado;
    }

    public BigDecimal getTotalExento() {
        return totalExento;
    }

    public void setTotalExento(BigDecimal totalExento) {
        this.totalExento = totalExento;
    }
    
    
}
