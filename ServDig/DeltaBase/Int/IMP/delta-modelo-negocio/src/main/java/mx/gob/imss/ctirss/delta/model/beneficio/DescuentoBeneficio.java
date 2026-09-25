package mx.gob.imss.ctirss.delta.model.beneficio;

import java.math.BigDecimal;
import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class DescuentoBeneficio extends AbstractModel {
	private static final long serialVersionUID = 1L;

	private Long idDescuentoBeneficio;
	private BigDecimal porcentajeDescuento;
	private Date fecInicio;
	private Date fechaFin;
	private int anioFiscal;
	
	public Long getIdDescuentoBeneficio() {
		return idDescuentoBeneficio;
	}
	public void setIdDescuentoBeneficio(Long idDescuentoBeneficio) {
		this.idDescuentoBeneficio = idDescuentoBeneficio;
	}
	public BigDecimal getPorcentajeDescuento() {
		return porcentajeDescuento;
	}
	public void setPorcentajeDescuento(BigDecimal porcentajeDescuento) {
		this.porcentajeDescuento = porcentajeDescuento;
	}
	public Date getFecInicio() {
		return fecInicio;
	}
	public void setFecInicio(Date fecInicio) {
		this.fecInicio = fecInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public int getAnioFiscal() {
		return anioFiscal;
	}
	public void setAnioFiscal(int anioFiscal) {
		this.anioFiscal = anioFiscal;
	}
	
}
