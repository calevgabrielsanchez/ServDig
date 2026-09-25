package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The primary key class for the SPC_MONTO_MINIMO database table.
 * 
 */
@Embeddable
public class SpcMontoMinimoPK implements Serializable {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Fecha Inicio de Ajuste.
	 */
	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INICIO_PENSION_LIM_INI")
	private Date fecInicioPensionLimIni;

	/**
	 * Fecha Fin de Ajuste.
	 */
	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INICIO_PENSION_LIM_FIN")
	private Date fecInicioPensionLimFin;

	/**
	 * Fecha Inicio de Ajuste.
	 */
	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INICIO_AJUSTE_LIM_INI")
	private Date fecInicioAjusteLimIni;

	/**
	 * Fecha Fin de Ajuste.
	 */
	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INICIO_AJUSTE_LIM_FIN")
	private Date fecInicioAjusteLimFin;

	/**
	 * Default constructor
	 */
	public SpcMontoMinimoPK() {
	}

	/**
	 * @return the fecInicioPensionLimIni
	 */
	public Date getFecInicioPensionLimIni() {
		return fecInicioPensionLimIni;
	}

	/**
	 * @param fecInicioPensionLimIni the fecInicioPensionLimIni to set
	 */
	public void setFecInicioPensionLimIni(Date fecInicioPensionLimIni) {
		this.fecInicioPensionLimIni = fecInicioPensionLimIni;
	}

	/**
	 * @return the fecInicioPensionLimFin
	 */
	public Date getFecInicioPensionLimFin() {
		return fecInicioPensionLimFin;
	}

	/**
	 * @param fecInicioPensionLimFin the fecInicioPensionLimFin to set
	 */
	public void setFecInicioPensionLimFin(Date fecInicioPensionLimFin) {
		this.fecInicioPensionLimFin = fecInicioPensionLimFin;
	}

	/**
	 * @return the fecInicioAjusteLimIni
	 */
	public Date getFecInicioAjusteLimIni() {
		return fecInicioAjusteLimIni;
	}

	/**
	 * @param fecInicioAjusteLimIni the fecInicioAjusteLimIni to set
	 */
	public void setFecInicioAjusteLimIni(Date fecInicioAjusteLimIni) {
		this.fecInicioAjusteLimIni = fecInicioAjusteLimIni;
	}

	/**
	 * @return the fecInicioAjusteLimFin
	 */
	public Date getFecInicioAjusteLimFin() {
		return fecInicioAjusteLimFin;
	}

	/**
	 * @param fecInicioAjusteLimFin the fecInicioAjusteLimFin to set
	 */
	public void setFecInicioAjusteLimFin(Date fecInicioAjusteLimFin) {
		this.fecInicioAjusteLimFin = fecInicioAjusteLimFin;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SpcMontoMinimoPK)) {
			return false;
		}
		SpcMontoMinimoPK castOther = (SpcMontoMinimoPK)other;
		return 
			this.fecInicioPensionLimIni.equals(castOther.fecInicioPensionLimIni)
			&& this.fecInicioPensionLimFin.equals(castOther.fecInicioPensionLimFin)
			&& this.fecInicioAjusteLimIni.equals(castOther.fecInicioAjusteLimIni)
			&& this.fecInicioAjusteLimFin.equals(castOther.fecInicioAjusteLimFin);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.fecInicioPensionLimIni.hashCode();
		hash = hash * prime + this.fecInicioPensionLimFin.hashCode();
		hash = hash * prime + this.fecInicioAjusteLimIni.hashCode();
		hash = hash * prime + this.fecInicioAjusteLimFin.hashCode();
		return hash;
	}

}
