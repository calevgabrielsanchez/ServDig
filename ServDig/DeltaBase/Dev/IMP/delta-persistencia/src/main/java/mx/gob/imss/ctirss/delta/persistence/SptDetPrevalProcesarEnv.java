package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import mx.gob.imss.ctirss.delta.persistence.SptEnvComunicEntidade;

@Entity
@Table(name = "SPT_DET_PREVAL_PROCESAR_ENV")
@NamedQuery(name = "SptDetPrevalProcesarEnv.findAll", query = "SELECT s FROM SptDetPrevalProcesarEnv s")
public class SptDetPrevalProcesarEnv implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTDETPREVALPROCESARENV", sequenceName = "SEQ_SPTDETPREVALPROCESARENV")
	@GeneratedValue(generator = "SEQ_SPTDETPREVALPROCESARENV")
	@Column(name = "CVE_DET_PREVAL_PROCESAR_ENV")
	private long cveDetPrevalProcesarEnv;

	@Column(name = "CVE_NSS")
	private String cveNss;
	@Column(name = "NOM_APELLIDO_PATERNO")
	private String nomApellidoPaterno;
	@Column(name = "NOM_APELLIDO_MATERNO")
	private String nomApellidoMaterno;
	@Column(name = "NOM_NOMBRE")
	private String nomNombre;
	@Column(name = "REF_CURP")
	private String curp;
	
	
	@ManyToOne
	@JoinColumn(name = "CVE_ID_ENV_COMUNIC_ENTIDADES")
	private SptEnvComunicEntidade sptEnvComunicEntidade;

	public long getCveDetPrevalProcesarEnv() {
		return cveDetPrevalProcesarEnv;
	}

	public void setCveDetPrevalProcesarEnv(long cveDetPrevalProcesarEnv) {
		this.cveDetPrevalProcesarEnv = cveDetPrevalProcesarEnv;
	}

	public String getCveNss() {
		return cveNss;
	}

	public void setCveNss(String cveNss) {
		this.cveNss = cveNss;
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

	public String getNomNombre() {
		return nomNombre;
	}

	public void setNomNombre(String nomNombre) {
		this.nomNombre = nomNombre;
	}

	public SptEnvComunicEntidade getSptEnvComunicEntidade() {
		return sptEnvComunicEntidade;
	}

	public void setSptEnvComunicEntidade(
			SptEnvComunicEntidade sptEnvComunicEntidade) {
		this.sptEnvComunicEntidade = sptEnvComunicEntidade;
	}
	
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}

}
