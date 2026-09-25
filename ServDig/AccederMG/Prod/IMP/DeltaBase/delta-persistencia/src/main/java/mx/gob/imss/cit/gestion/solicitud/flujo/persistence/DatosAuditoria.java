package mx.gob.imss.cit.gestion.solicitud.flujo.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class DatosAuditoria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2209921815021193564L;

	/**
	 * Fecha en que se da de baja el registro
	 */
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
	/**
	 * Fecha en la que se actualiza el registro
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado = new Date();

	/**
	 * Fecha en la que se da de alta el registro
	 */
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta = new Date();

	public DatosAuditoria() {
	}

	public DatosAuditoria(Date fecRegistroBaja, Date fecRegistroActualizado, Date fecRegistroAlta) {
		this.fecRegistroBaja = (null == fecRegistroBaja ? null : (Date) fecRegistroBaja.clone());
		this.fecRegistroActualizado = (null == fecRegistroActualizado ? null : (Date) fecRegistroActualizado.clone());
		this.fecRegistroAlta = (null == fecRegistroAlta ? null : (Date) fecRegistroAlta.clone());
	}

	public Date getFecRegistroBaja() {
		return null == this.fecRegistroBaja ? null : (Date) this.fecRegistroBaja.clone();
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = (null == fecRegistroBaja ? null : (Date) fecRegistroBaja.clone());
	}

	public Date getFecRegistroActualizado() {
		return null == this.fecRegistroActualizado ? null : (Date) this.fecRegistroActualizado.clone();
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = (null == fecRegistroActualizado ? null : (Date) fecRegistroActualizado.clone());
	}

	public Date getFecRegistroAlta() {
		return null == this.fecRegistroAlta ? new Date() : (Date) this.fecRegistroAlta.clone();
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = (null == fecRegistroAlta ? null : (Date) fecRegistroAlta.clone());
	}

}
