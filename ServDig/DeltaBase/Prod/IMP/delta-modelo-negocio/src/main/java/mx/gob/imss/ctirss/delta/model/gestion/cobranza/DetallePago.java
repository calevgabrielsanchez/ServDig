package mx.gob.imss.ctirss.delta.model.gestion.cobranza;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * Entity DitDetallePago
 * 
 * @author IMSS
 *
 */
public class DetallePago extends AbstractModel {

	private static final long serialVersionUID = 1L;
	
    private Long cveIdDetallePago;
    private String detalleXml;
    //private Pago Pago;
    private Date fechaBaja;
    private Date fechaActualizado;
    private Date fechaAlta;
    
    
    public DetallePago(){
    	super();
    }
    
    
	public Long getCveIdDetallePago() {
		return cveIdDetallePago;
	}
	public void setCveIdDetallePago(Long cveIdDetallePago) {
		this.cveIdDetallePago = cveIdDetallePago;
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
