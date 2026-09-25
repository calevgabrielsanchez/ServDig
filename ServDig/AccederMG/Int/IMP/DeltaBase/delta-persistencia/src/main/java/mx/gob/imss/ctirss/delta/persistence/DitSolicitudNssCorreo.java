package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the DIT_SOLICITUD_NSS_CORREO database table.
 * 
 */
@Entity
@Table(name = "DIT_SOLICITUD_NSS_CORREO")
@NamedQueries({
    @NamedQuery(name="findByCorreo",
                query="FROM DitSolicitudNssCorreo dit WHERE dit.pk.refCorreoElectronico = :correo")
}) 
public class DitSolicitudNssCorreo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	DitSolicitudNssCorreoPK pk;

	@Column(name = "REF_CURP")
	private String refCurp;

	@Column(name = "NUM_CONTEO_SOLICITUD_PERIODO")
	private Long numConteoSolicitudPeriodo;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_CONSULTA")
	private Date fecConsulta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
	
	
	public DitSolicitudNssCorreo() {
	}


	public DitSolicitudNssCorreoPK getPk() {
		return pk;
	}


	public void setPk(DitSolicitudNssCorreoPK pk) {
		this.pk = pk;
	}


	/**
	 * @return the refCurp
	 */
	public String getRefCurp() {
		return refCurp;
	}

	/**
	 * @param refCurp
	 *            the refCurp to set
	 */
	public void setRefCurp(String refCurp) {
		this.refCurp = refCurp;
	}

	/**
	 * @return the numConteoSolicitudPeriodo
	 */
	public Long getNumConteoSolicitudPeriodo() {
		return numConteoSolicitudPeriodo;
	}

	/**
	 * @param numConteoSolicitudPeriodo
	 *            the numConteoSolicitudPeriodo to set
	 */
	public void setNumConteoSolicitudPeriodo(
			Long numConteoSolicitudPeriodo) {
		this.numConteoSolicitudPeriodo = numConteoSolicitudPeriodo;
	}

	/**
	 * @return the fecConsulta
	 */
	public Date getFecConsulta() {
		return fecConsulta;
	}

	/**
	 * @param fecConsulta
	 *            the fecConsulta to set
	 */
	public void setFecConsulta(Date fecConsulta) {
		this.fecConsulta = fecConsulta;
	}

	/**
	 * @return the fecRegistroAlta
	 */
	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	/**
	 * @param fecRegistroAlta
	 *            the fecRegistroAlta to set
	 */
	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	/**
	 * @return the fecRegistroBaja
	 */
	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}

	/**
	 * @param fecRegistroBaja
	 *            the fecRegistroBaja to set
	 */
	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}
}