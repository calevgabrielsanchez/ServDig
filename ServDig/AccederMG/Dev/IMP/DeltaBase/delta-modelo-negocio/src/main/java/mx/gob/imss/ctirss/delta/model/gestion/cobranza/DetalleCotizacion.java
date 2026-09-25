package mx.gob.imss.ctirss.delta.model.gestion.cobranza;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * Entity DitDetalleCotizacion
 * 
 * @author IMSS
 *
 */
public class DetalleCotizacion extends AbstractModel {
	
	private static final long serialVersionUID = 1L;

	private Long cveIdDetalle;
    private String detalleXml;
    //private Cotizacion cotizacion;
    private Date fechaBaja;
    private Date fechaActualizado;
    private Date fechaAlta;
    
    
    public DetalleCotizacion(){
    	super();
    }
    
    
	public Long getCveIdDetalle() {
		return cveIdDetalle;
	}
	public void setCveIdDetalle(Long cveIdDetalle) {
		this.cveIdDetalle = cveIdDetalle;
	}
	public String getDetalleXml() {
		return detalleXml;
	}
	public void setDetalleXml(String detalleXml) {
		this.detalleXml = detalleXml;
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
