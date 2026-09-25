package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import java.io.Serializable;
import java.util.Date;

public class MovimientoAfiliatorio implements Serializable{
	
	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1219419126405649136L;
	
	private Long tipoMovimiento;
	private Long cveCausa;
	private Date fechaMovimiento;
	public Long getTipoMovimiento() {
		return tipoMovimiento;
	}
	public void setTipoMovimiento(Long tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}
	public Long getCveCausa() {
		return cveCausa;
	}
	public void setCveCausa(Long cveCausa) {
		this.cveCausa = cveCausa;
	}
	public Date getFechaMovimiento() {
		return fechaMovimiento;
	}
	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}
	
	
	
}
