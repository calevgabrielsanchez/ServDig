package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Clase de apoyo de DetalleMoraInfo, para transportar info de BajaMoraUtil
 * 
 * @author mario.marquez
 */

public class PagoVencido {

	private Long idPago;
	
	private BigDecimal monto = BigDecimal.ZERO;
	
	private String lineaCaptura;
	
	private Date fecIniPeriodo;
	
	private Date fecFinPeriodo;

	public Long getIdPago() {
		return idPago;
	}

	public void setIdPago(Long idPago) {
		this.idPago = idPago;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public String getLineaCaptura() {
		return lineaCaptura;
	}

	public void setLineaCaptura(String lineaCaptura) {
		this.lineaCaptura = lineaCaptura;
	}

	@Override
	public String toString() {
		return "DetallesMoraPagoVencido [idPago=" + idPago 
				+ ", monto=" + monto 
				+ ", lineaCaptura=" + lineaCaptura
				+ "]";
	}

	public Date getFecIniPeriodo() {
		return fecIniPeriodo;
	}

	public void setFecIniPeriodo(Date fecIniPeriodo) {
		this.fecIniPeriodo = fecIniPeriodo;
	}

	public Date getFecFinPeriodo() {
		return fecFinPeriodo;
	}

	public void setFecFinPeriodo(Date fecFinPeriodo) {
		this.fecFinPeriodo = fecFinPeriodo;
	}
	
	

}
