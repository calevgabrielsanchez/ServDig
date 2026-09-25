package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.delta.persistence.SptRespComunicEntidade;

@Entity
@Table(name = "SPT_DET_PREVAL_PROCESAR_SAL")
@NamedQuery(name = "SptDetPrevalProcesarSal.findAll", query = "SELECT s FROM SptDetPrevalProcesarSal s")
public class SptDetPrevalProcesarSal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTDETPREVALPROCESARSAL", sequenceName = "SEQ_SPTDETPREVALPROCESARSAL")
	@GeneratedValue(generator = "SEQ_SPTDETPREVALPROCESARSAL")
	@Column(name = "CVE_DET_PREVAL_PROCESAR_SAL")
	private long cveDetPrevalProcesarSal;

	@Column(name = "CVE_DIAGNOSTICO")
	private String cveDiagnostico;

	@Column(name = "NOM_NOMBRE")
	private String nomNombre;

	@Column(name = "NOM_APELLIDO_PATERNO")
	private String nomApellidoPaterno;

	@Column(name = "NOM_APELLIDO_MATERNO")
	private String nomApellidoMaterno;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@ManyToOne
	@JoinColumn(name = "CVE_ID_RESP_COMUNIC_ENTIDADES")
	private SptRespComunicEntidade sptRespComunicEntidade;

	public long getCveDetPrevalProcesarSal() {
		return cveDetPrevalProcesarSal;
	}

	public void setCveDetPrevalProcesarSal(long cveDetPrevalProcesarSal) {
		this.cveDetPrevalProcesarSal = cveDetPrevalProcesarSal;
	}

	public String getCveDiagnostico() {
		return cveDiagnostico;
	}

	public void setCveDiagnostico(String cveDiagnostico) {
		this.cveDiagnostico = cveDiagnostico;
	}

	public String getNomNombre() {
		return nomNombre;
	}

	public void setNomNombre(String nomNombre) {
		this.nomNombre = nomNombre;
	}

	public String getNomApellidoPaterno() {
		return nomApellidoPaterno;
	}

	public void setNomApellidoPaterno(String nomApellidoPaterno) {
		this.nomApellidoPaterno = nomApellidoPaterno;
	}

	public String getNomApellidoMaterno() {
		return nomApellidoMaterno;
	}

	public void setNomApellidoMaterno(String nomApellidoMaterno) {
		this.nomApellidoMaterno = nomApellidoMaterno;
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

	public SptRespComunicEntidade getSptRespComunicEntidade() {
		return sptRespComunicEntidade;
	}

	public void setSptRespComunicEntidade(
			SptRespComunicEntidade sptRespComunicEntidade) {
		this.sptRespComunicEntidade = sptRespComunicEntidade;
	}

}
