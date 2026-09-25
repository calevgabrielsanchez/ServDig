package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="DIT_REACTIVACION_DERECHOHAB")
public class DitReactivacionDerechohab implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 48214627211490577L;

	@Id
	@Column(name = "CVE_ID_REACTIVACION_DERECHOHAB")
	@SequenceGenerator(name = "SEQ_DITREACTIVACIONDERECHOHAB", sequenceName = "SEQ_DITREACTIVACIONDERECHOHAB")
	@GeneratedValue(generator = "SEQ_DITREACTIVACIONDERECHOHAB")
	private Long cveIdReactivacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Column(name = "CVE_ID_TRAMITE")
	private Long cveIdTramite;
	
	@Column(name = "CVE_ID_BAJA")
	private Long cveIdBaja;
	
	@Column(name = "MATRICULA")
	private String matricula;
	
	@Column(name = "MOTIVO")
	private String motivo;
	
	@Column(name = "FUNDAMENTO_LEGAL")
	private String fundamentoLegal;

	public Long getCveIdReactivacion() {
		return cveIdReactivacion;
	}

	public void setCveIdReactivacion(Long cveIdReactivacion) {
		this.cveIdReactivacion = cveIdReactivacion;
	}

	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Long getCveIdTramite() {
		return cveIdTramite;
	}

	public void setCveIdTramite(Long cveIdTramite) {
		this.cveIdTramite = cveIdTramite;
	}

	public Long getCveIdBaja() {
		return cveIdBaja;
	}

	public void setCveIdBaja(Long cveIdBaja) {
		this.cveIdBaja = cveIdBaja;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getFundamentoLegal() {
		return fundamentoLegal;
	}

	public void setFundamentoLegal(String fundamentoLegal) {
		this.fundamentoLegal = fundamentoLegal;
	}
	
	
}
