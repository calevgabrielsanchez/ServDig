package mx.gob.imss.ctirss.delta.model.gestion.cobranza;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * Entity DitPago
 * 
 * @author IMSS
 *
 */
public class Pago extends AbstractModel {

	private static final long serialVersionUID = 1L;
	
    private Long cveIdPago;
    private Compra compra;
    private EstadoPago estadoPago;
    private DetallePago detallePago;
    private Date fechaPago;
    private Date fechaLimitePago;
    private Date fechaInicioPeriodo;
    private Date fechaFinPeriodo;
    private String lineaCaptura;
    private Date fechaBaja;
    private Date fechaActualizado;
    private Date fechaAlta;
    
    
    public Pago(){
    	super();
    }
    
    
	public Long getCveIdPago() {
		return cveIdPago;
	}
	public void setCveIdPago(Long cveIdPago) {
		this.cveIdPago = cveIdPago;
	}
	public Compra getCompra() {
		return compra;
	}
	public void setCompra(Compra compra) {
		this.compra = compra;
	}
	public EstadoPago getEstadoPago() {
		return estadoPago;
	}
	public void setEstadoPago(EstadoPago estadoPago) {
		this.estadoPago = estadoPago;
	}
	public DetallePago getDetallePago() {
		return detallePago;
	}
	public void setDetallePago(DetallePago detallePago) {
		this.detallePago = detallePago;
	}
	public Date getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}
	public Date getFechaLimitePago() {
		return fechaLimitePago;
	}
	public void setFechaLimitePago(Date fechaLimitePago) {
		this.fechaLimitePago = fechaLimitePago;
	}
	public Date getFechaInicioPeriodo() {
		return fechaInicioPeriodo;
	}
	public void setFechaInicioPeriodo(Date fechaInicioPeriodo) {
		this.fechaInicioPeriodo = fechaInicioPeriodo;
	}
	public Date getFechaFinPeriodo() {
		return fechaFinPeriodo;
	}
	public void setFechaFinPeriodo(Date fechaFinPeriodo) {
		this.fechaFinPeriodo = fechaFinPeriodo;
	}
	public String getLineaCaptura() {
		return lineaCaptura;
	}
	public void setLineaCaptura(String lineaCaptura) {
		this.lineaCaptura = lineaCaptura;
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
