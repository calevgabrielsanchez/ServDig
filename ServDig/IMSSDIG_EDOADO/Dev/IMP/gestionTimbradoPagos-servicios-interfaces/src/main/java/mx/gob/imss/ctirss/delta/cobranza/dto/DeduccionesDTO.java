package mx.gob.imss.ctirss.delta.cobranza.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DeduccionesDTO  implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DeduccionDTO[] deduccion;
    private BigDecimal totalGravado;
    private BigDecimal totalExento;

    public DeduccionDTO[] getDeduccion() {
        
        return deduccion;
    }

    public void setDeduccion(DeduccionDTO[] deduccion) {
        this.deduccion = deduccion;
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
