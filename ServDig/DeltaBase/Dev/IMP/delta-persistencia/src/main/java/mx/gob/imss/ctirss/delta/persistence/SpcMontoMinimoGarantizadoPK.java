package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The primary key class for the SPC_MONTO_MINIMO_GARANTIZADO database table.
 * 
 */
@Embeddable
public class SpcMontoMinimoGarantizadoPK implements Serializable {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 1L;

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
	 * Default Constructor
	 */
	public SpcMontoMinimoGarantizadoPK() {
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
		if (!(other instanceof SpcMontoMinimoGarantizadoPK)) {
			return false;
		}
		SpcMontoMinimoGarantizadoPK castOther = (SpcMontoMinimoGarantizadoPK)other;
		return 
			this.fecInicioAjusteLimIni.equals(castOther.fecInicioAjusteLimIni)
			&& this.fecInicioAjusteLimFin.equals(castOther.fecInicioAjusteLimFin);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.fecInicioAjusteLimIni.hashCode();
		hash = hash * prime + this.fecInicioAjusteLimFin.hashCode();
		return hash;
	}

}
