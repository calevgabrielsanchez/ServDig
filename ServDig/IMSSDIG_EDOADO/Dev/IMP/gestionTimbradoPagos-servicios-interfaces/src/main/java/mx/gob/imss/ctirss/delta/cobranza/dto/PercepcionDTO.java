package mx.gob.imss.ctirss.delta.cobranza.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PercepcionDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int tipoPercepcion;
    private String clave;
    private String concepto;
    private BigDecimal importeGravado;
    private BigDecimal importeExento;

    public int getTipoPercepcion() {
        return tipoPercepcion;
    }

    public void setTipoPercepcion(int tipoPercepcion) {
        this.tipoPercepcion = tipoPercepcion;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public BigDecimal getImporteGravado() {
        return importeGravado;
    }

    public void setImporteGravado(BigDecimal importeGravado) {
        this.importeGravado = importeGravado;
    }

    public BigDecimal getImporteExento() {
        return importeExento;
    }

    public void setImporteExento(BigDecimal importeExento) {
        this.importeExento = importeExento;
    }
    
    
    
}
