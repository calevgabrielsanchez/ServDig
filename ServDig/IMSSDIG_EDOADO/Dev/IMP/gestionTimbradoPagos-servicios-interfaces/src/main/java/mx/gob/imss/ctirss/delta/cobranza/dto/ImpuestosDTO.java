package mx.gob.imss.ctirss.delta.cobranza.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ImpuestosDTO  implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RetencionesDTO retenciones;
    private TrasladosDTO traslados;
    
    
    protected BigDecimal totalImpuestosRetenidos;
    protected BigDecimal totalImpuestosTrasladados;

    public RetencionesDTO getRetenciones() {
         if(retenciones == null){
            retenciones = new RetencionesDTO();
        }
        return retenciones;
    }

    public void setRetenciones(RetencionesDTO retenciones) {
        this.retenciones = retenciones;
    }

    public TrasladosDTO getTraslados() {
        if(traslados == null){
            traslados = new TrasladosDTO();
        }
        return traslados;
    }

    public void setTraslados(TrasladosDTO traslados) {
        this.traslados = traslados;
    }

    public BigDecimal getTotalImpuestosRetenidos() {
        return totalImpuestosRetenidos;
    }

    public void setTotalImpuestosRetenidos(BigDecimal totalImpuestosRetenidos) {
        this.totalImpuestosRetenidos = totalImpuestosRetenidos;
    }

    public BigDecimal getTotalImpuestosTrasladados() {
        return totalImpuestosTrasladados;
    }

    public void setTotalImpuestosTrasladados(BigDecimal totalImpuestosTrasladados) {
        this.totalImpuestosTrasladados = totalImpuestosTrasladados;
    }
    
    
}
