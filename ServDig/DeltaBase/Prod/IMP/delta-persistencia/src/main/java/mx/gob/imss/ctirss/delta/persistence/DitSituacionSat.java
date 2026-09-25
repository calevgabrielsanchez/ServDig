package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;

/**
 * The persistent class for the DIT_SITUACION_SAT database table.
 * 
 */
@Entity
@Table(name = "DIT_SITUACION_SAT")
public class DitSituacionSat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIT_SITUACION_SAT_CVESITUACIONSAT_GENERATOR", sequenceName="SEQ_DITSITUACIONSAT")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIT_SITUACION_SAT_CVESITUACIONSAT_GENERATOR")
	@Column(name = "CVE_SITUACION_SAT")
	private long cveSituacionSat;

	// bi-directional many-to-one association to DitPersonaFisica
	@ManyToOne
	@JoinColumn(name = "CVE_ID_PERSONA_FISICA")
	private DitPersonaFisica ditPersonaFisica;

	// bi-directional many-to-one association to DitPersonaMoral
	@ManyToOne
	@JoinColumn(name = "CVE_ID_PERSONA_MORAL")
	private DitPersonaMoral ditPersonaMoral;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_SITUACION")
	private Date fecSituacion;

	@Column(name = "REF_DESCRIPCION")
	private String refDescripcion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	public DitSituacionSat() {
	}

	public long getCveSituacionSat() {
		return cveSituacionSat;
	}

	public void setCveSituacionSat(long cveSituacionSat) {
		this.cveSituacionSat = cveSituacionSat;
	}

	public DitPersonaFisica getDitPersonaFisica() {
		return ditPersonaFisica;
	}

	public void setDitPersonaFisica(DitPersonaFisica ditPersonaFisica) {
		this.ditPersonaFisica = ditPersonaFisica;
	}

	public DitPersonaMoral getDitPersonaMoral() {
		return ditPersonaMoral;
	}

	public void setDitPersonaMoral(DitPersonaMoral ditPersonaMoral) {
		this.ditPersonaMoral = ditPersonaMoral;
	}

	public Date getFecSituacion() {
		return fecSituacion;
	}

	public void setFecSituacion(Date fecSituacion) {
		this.fecSituacion = fecSituacion;
	}

	public String getRefDescripcion() {
		return refDescripcion;
	}

	public void setRefDescripcion(String refDescripcion) {
		this.refDescripcion = refDescripcion;
	}

	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

}