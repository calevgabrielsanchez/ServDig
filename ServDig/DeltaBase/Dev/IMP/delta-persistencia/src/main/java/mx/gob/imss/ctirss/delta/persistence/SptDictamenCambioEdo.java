package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the SPT_DICTAMEN_CAMBIO_EDO database table.
 * 
 */
@Entity
@Table(name="SPT_DICTAMEN_CAMBIO_EDO")
@NamedQuery(name="SptDictamenCambioEdo.findAll", query="SELECT s FROM SptDictamenCambioEdo s")
public class SptDictamenCambioEdo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SptDictamenCambioEdoPK id;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_CAMBIO")
	private Date fecCambio;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="ID_LOGIN")
	private String idLogin;

	//bi-directional many-to-one association to SpcEstadoDictamen
	@ManyToOne
	@JoinColumn(name="ID_ESTADO_DICTAMEN", insertable=false, updatable=false)
	private SpcEstadoDictamen spcEstadoDictamen;

	//bi-directional many-to-one association to SptDictamen
	@ManyToOne
	@JoinColumn(name="CVE_ID_DICTAMEN", insertable=false, updatable=false)
	private SptDictamen sptDictamen;

	public SptDictamenCambioEdo() {
	}

	public SptDictamenCambioEdoPK getId() {
		return this.id;
	}

	public void setId(SptDictamenCambioEdoPK id) {
		this.id = id;
	}

	public Date getFecCambio() {
		return this.fecCambio;
	}

	public void setFecCambio(Date fecCambio) {
		this.fecCambio = fecCambio;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public String getIdLogin() {
		return this.idLogin;
	}

	public void setIdLogin(String idLogin) {
		this.idLogin = idLogin;
	}

	public SpcEstadoDictamen getSpcEstadoDictamen() {
		return this.spcEstadoDictamen;
	}

	public void setSpcEstadoDictamen(SpcEstadoDictamen spcEstadoDictamen) {
		this.spcEstadoDictamen = spcEstadoDictamen;
	}

	public SptDictamen getSptDictamen() {
		return this.sptDictamen;
	}

	public void setSptDictamen(SptDictamen sptDictamen) {
		this.sptDictamen = sptDictamen;
	}

}