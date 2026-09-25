package mx.gob.imss.ctirss.delta.model.gestion.cobranza;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * Entity DitCompra
 * 
 * @author IMSS
 *
 */
public class Compra extends AbstractModel {

	private static final long serialVersionUID = 1L;
	
	private Long cveIdCompra;
	private Date fechaLimitePago;
    private Cotizacion cotizacion;    
    private FormaPago formaPago;   
    private EstadoCompra estadoCompra;    
    private List<Pago> pagos;
    private BigDecimal numMonto;
    private Date fechaBaja;
    private Date fechaActualizado;
    private Date fechaAlta;
    
    
    public Compra(){
    	super();
    }
    
    
	public Long getCveIdCompra() {
		return cveIdCompra;
	}
	public void setCveIdCompra(Long cveIdCompra) {
		this.cveIdCompra = cveIdCompra;
	}
	public Date getFechaLimitePago() {
		return fechaLimitePago;
	}
	public void setFechaLimitePago(Date fechaLimitePago) {
		this.fechaLimitePago = fechaLimitePago;
	}
	public Cotizacion getCotizacion() {
		return cotizacion;
	}
	public void setCotizacion(Cotizacion cotizacion) {
		this.cotizacion = cotizacion;
	}
	public FormaPago getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(FormaPago formaPago) {
		this.formaPago = formaPago;
	}
	public EstadoCompra getEstadoCompra() {
		return estadoCompra;
	}
	public void setEstadoCompra(EstadoCompra estadoCompra) {
		this.estadoCompra = estadoCompra;
	}
	public List<Pago> getPagos() {
		return pagos;
	}
	public void setPagos(List<Pago> pagos) {
		this.pagos = pagos;
	}
	public BigDecimal getNumMonto() {
		return numMonto;
	}
	public void setNumMonto(BigDecimal numMonto) {
		this.numMonto = numMonto;
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
