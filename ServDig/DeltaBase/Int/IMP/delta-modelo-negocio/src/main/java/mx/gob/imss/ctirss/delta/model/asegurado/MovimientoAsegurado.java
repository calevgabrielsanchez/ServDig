package mx.gob.imss.ctirss.delta.model.asegurado;

import java.io.Serializable;
import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class MovimientoAsegurado  extends AbstractModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idMovimientoAsegurado;
	private Date fechaMovimiento;
	private Asegurado asegurado;
	private TipoMovtoAsegurado tipoMovtoAsegurado;
	private MovtoAsegAbierto movtoAsegAbierto;
	/**
	 * @return the idMovimientoAsegurado
	 */
	public Long getIdMovimientoAsegurado() {
		return idMovimientoAsegurado;
	}
	/**
	 * @param idMovimientoAsegurado the idMovimientoAsegurado to set
	 */
	public void setIdMovimientoAsegurado(Long idMovimientoAsegurado) {
		this.idMovimientoAsegurado = idMovimientoAsegurado;
	}
	/**
	 * @return the fechaMovimiento
	 */
	public Date getFechaMovimiento() {
		return fechaMovimiento;
	}
	/**
	 * @param fechaMovimiento the fechaMovimiento to set
	 */
	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}
	/**
	 * @return the asegurado
	 */
	public Asegurado getAsegurado() {
		return asegurado;
	}
	/**
	 * @param asegurado the asegurado to set
	 */
	public void setAsegurado(Asegurado asegurado) {
		this.asegurado = asegurado;
	}
	/**
	 * @return the tipoMovtoAsegurado
	 */
	public TipoMovtoAsegurado getTipoMovtoAsegurado() {
		return tipoMovtoAsegurado;
	}
	/**
	 * @param tipoMovtoAsegurado the tipoMovtoAsegurado to set
	 */
	public void setTipoMovtoAsegurado(TipoMovtoAsegurado tipoMovtoAsegurado) {
		this.tipoMovtoAsegurado = tipoMovtoAsegurado;
	}
	/**
	 * @return the movtoAsegAbierto
	 */
	public MovtoAsegAbierto getMovtoAsegAbierto() {
		return movtoAsegAbierto;
	}
	/**
	 * @param movtoAsegAbierto the movtoAsegAbierto to set
	 */
	public void setMovtoAsegAbierto(MovtoAsegAbierto movtoAsegAbierto) {
		this.movtoAsegAbierto = movtoAsegAbierto;
	}
	
	
}
