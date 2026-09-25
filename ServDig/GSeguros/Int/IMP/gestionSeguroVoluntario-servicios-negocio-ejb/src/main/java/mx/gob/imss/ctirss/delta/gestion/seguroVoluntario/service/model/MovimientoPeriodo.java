package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.model;

import java.math.BigDecimal;
import java.util.Date;

public class MovimientoPeriodo {
	private Integer orden;
	private Date fechaInicio;
	private Date fechaFin;
	private BigDecimal salarioCotizacion;

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public BigDecimal getSalarioCotizacion() {
		return salarioCotizacion;
	}

	public void setSalarioCotizacion(BigDecimal salarioCotizacion) {
		this.salarioCotizacion = salarioCotizacion;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}
}
