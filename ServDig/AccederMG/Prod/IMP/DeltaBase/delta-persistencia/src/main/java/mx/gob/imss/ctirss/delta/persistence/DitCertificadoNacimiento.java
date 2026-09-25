package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_CERTIFICADO_NACIMIENTO database table.
 * 
 */
@Entity
@Table(name="DIT_CERTIFICADO_NACIMIENTO")
public class DitCertificadoNacimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_DOCUMENTO_PROBATORIO", nullable=false, precision=22)
	private long cveIdDocumentoProbatorio;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_ALUMBRAMIENTO", nullable=false)
	private Date fecAlumbramiento;

	@Column(name="REF_FOLIO", nullable=false, length=8)
	private String refFolio;

	//bi-directional many-to-one association to DicSexo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_SEXO")
	private DicSexo dicSexo;

	
	//bi-directional one-to-one association to DitDocumentoProbatorio
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_DOCUMENTO_PROBATORIO", nullable=false)
	private DitDocumentoProbatorio ditDocumentoProbatorio;

	
	@Column(name="DES_LUGAR_ALUMBRAMIENTO", nullable=false, length=150)
	private String desLugarAlumbramiento;

	
	public DitDocumentoProbatorio getDitDocumentoProbatorio() {
		return ditDocumentoProbatorio;
	}

	public void setDitDocumentoProbatorio(
			DitDocumentoProbatorio ditDocumentoProbatorio) {
		this.ditDocumentoProbatorio = ditDocumentoProbatorio;
	}

    public DitCertificadoNacimiento() {
    }

	public long getCveIdDocumentoProbatorio() {
		return this.cveIdDocumentoProbatorio;
	}

	public void setCveIdDocumentoProbatorio(long cveIdDocumentoProbatorio) {
		this.cveIdDocumentoProbatorio = cveIdDocumentoProbatorio;
	}

	public Date getFecAlumbramiento() {
		return this.fecAlumbramiento;
	}

	public void setFecAlumbramiento(Date fecAlumbramiento) {
		this.fecAlumbramiento = fecAlumbramiento;
	}

	public String getRefFolio() {
		return this.refFolio;
	}

	public void setRefFolio(String refFolio) {
		this.refFolio = refFolio;
	}

	public DicSexo getDicSexo() {
		return this.dicSexo;
	}

	public void setDicSexo(DicSexo dicSexo) {
		this.dicSexo = dicSexo;
	}
	
	public String getDesLugarAlumbramiento() {
		return this.desLugarAlumbramiento;
	}

	public void setDesLugarAlumbramiento(String desLugarAlumbramiento) {
		this.desLugarAlumbramiento = desLugarAlumbramiento;
	}

	
}