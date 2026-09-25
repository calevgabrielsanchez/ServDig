package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 *  DTO para transportar info de BajaMoraUtil
 * 
 */

public class DetalleMoraInfo {
	private Integer mesesMora;
	private BigDecimal montoTotal;
	private Date fecUltimoPago;
	private Date fecPrimerVencimiento;
	private Date fecUltimoVencimiento;
	private String periodosCsv;
	private BigDecimal recargos;
	private BigDecimal actualizaciones;
	private List<PagoVencido> pagosVencidos;
	
	// id baja
	private long cveIdBaja;
	
	//id baja mora detalles
	private Long cveIdDetalle;

	
	
	public Integer getMesesMora() {
		return mesesMora;
	}

	public void setMesesMora(Integer mesesMora) {
		this.mesesMora = mesesMora;
	}

	public BigDecimal getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}

	public Date getFecUltimoPago() {
		return fecUltimoPago;
	}

	public void setFecUltimoPago(Date fecUltimoPago) {
		this.fecUltimoPago = fecUltimoPago;
	}

	public Date getFecPrimerVencimiento() {
		return fecPrimerVencimiento;
	}

	public void setFecPrimerVencimiento(Date fecPrimerVencimiento) {
		this.fecPrimerVencimiento = fecPrimerVencimiento;
	}

	public Date getFecUltimoVencimiento() {
		return fecUltimoVencimiento;
	}

	public void setFecUltimoVencimiento(Date fecUltimoVencimiento) {
		this.fecUltimoVencimiento = fecUltimoVencimiento;
	}

	public String getPeriodosCsv() {
		return periodosCsv;
	}

	public void setPeriodosCsv(String periodosCsv) {
		this.periodosCsv = periodosCsv;
	}

	public BigDecimal getRecargos() {
		return recargos;
	}

	public void setRecargos(BigDecimal recargos) {
		this.recargos = recargos;
	}

	public BigDecimal getActualizaciones() {
		return actualizaciones;
	}

	public void setActualizaciones(BigDecimal actualizaciones) {
		this.actualizaciones = actualizaciones;
	}

	public List<PagoVencido> getPagosVencidos() {
		if(pagosVencidos == null) {
			pagosVencidos = new ArrayList<PagoVencido>();
		}
		return pagosVencidos;
	}

	public void setPagosVencidos(List<PagoVencido> pagosVencidos) {
		this.pagosVencidos = pagosVencidos;
	}

	public Long getCveIdDetalle() {
		return cveIdDetalle;
	}

	public void setCveIdDetalle(Long cveIdDetalle) {
		this.cveIdDetalle = cveIdDetalle;
	}

	public long getCveIdBaja() {
		return cveIdBaja;
	}

	public void setCveIdBaja(long cveIdBaja) {
		this.cveIdBaja = cveIdBaja;
	}



}



