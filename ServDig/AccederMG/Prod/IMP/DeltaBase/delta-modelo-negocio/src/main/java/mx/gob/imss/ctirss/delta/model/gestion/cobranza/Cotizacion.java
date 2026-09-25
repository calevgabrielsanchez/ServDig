package mx.gob.imss.ctirss.delta.model.gestion.cobranza;

import java.math.BigDecimal;
import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * Entity DitCotizacion
 * 
 * @author IMSS
 *
 */
public class Cotizacion extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private Long cveIdCotizacion;
    private BigDecimal numTotal;
    private Date fechaVigencia;
    private Concepto concepto;
    private DetalleCotizacion detalleCotizacion;
    private Date fechaBaja;
    private Date fechaActualizado;
    private Date fechaAlta;
    
    
    public Cotizacion(){
    	super();
    }
    
    
	public Long getCveIdCotizacion() {
		return cveIdCotizacion;
	}
	public void setCveIdCotizacion(Long cveIdCotizacion) {
		this.cveIdCotizacion = cveIdCotizacion;
	}
	public BigDecimal getNumTotal() {
		return numTotal;
	}
	public void setNumTotal(BigDecimal numTotal) {
		this.numTotal = numTotal;
	}
	public Date getFechaVigencia() {
		return fechaVigencia;
	}
	public void setFechaVigencia(Date fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}
	public Concepto getConcepto() {
		return concepto;
	}
	public void setConcepto(Concepto concepto) {
		this.concepto = concepto;
	}
	public DetalleCotizacion getDetalleCotizacion() {
		return detalleCotizacion;
	}
	public void setDetalleCotizacion(DetalleCotizacion detalleCotizacion) {
		this.detalleCotizacion = detalleCotizacion;
	}
	public Date getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	public Date getFechaActualizado() {
		return fechaActualizado;
	}
	public void setFechaActualizado(Date fechaActualizado) {
		this.fechaActualizado = fechaActualizado;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
    
}
