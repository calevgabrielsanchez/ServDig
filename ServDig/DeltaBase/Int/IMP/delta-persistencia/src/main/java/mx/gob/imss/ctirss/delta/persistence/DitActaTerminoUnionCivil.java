package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "DIT_ACTA_TERMINO_UNION_CIVIL")
public class DitActaTerminoUnionCivil implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_DOCUMENTO_PROBATORIO", unique=true, nullable=false, precision=22)
	private long cveIdDocumentoProbatorio;
	
	@Column(name = "LUGAR_EMISION")
	private String lugarEmision;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_EMISION", nullable=false)
	private Date fecEmision;
	
	@ManyToOne(targetEntity = mx.gob.imss.ctirss.delta.persistence.DicAutoridadEmisora.class)
	@JoinColumn(name = "CVE_ID_AUTORIDAD_EMISORA")
	private DicAutoridadEmisora dicAutoridadEmisora;
	
	@ManyToOne
	@JoinColumn(name = "CVE_ENT")
	private DgCatEstado dgCatEstado;
	
	@Column(name = "NO_REFERENCIA")
	private String noReferencia;
	
	
	//bi-directional one-to-one association to ditDocumentoProbatorio
	@OneToOne
	@JoinColumn(name="CVE_ID_DOCUMENTO_PROBATORIO")
	private DitDocumentoProbatorio ditDocumentoProbatorio;


	public long getCveIdDocumentoProbatorio() {
		return cveIdDocumentoProbatorio;
	}


	public void setCveIdDocumentoProbatorio(long cveIdDocumentoProbatorio) {
		this.cveIdDocumentoProbatorio = cveIdDocumentoProbatorio;
	}


	public String getLugarEmision() {
		return lugarEmision;
	}


	public void setLugarEmision(String lugarEmision) {
		this.lugarEmision = lugarEmision;
	}


	public Date getFecEmision() {
		return fecEmision;
	}


	public void setFecEmision(Date fecEmision) {
		this.fecEmision = fecEmision;
	}


	public DicAutoridadEmisora getDicAutoridadEmisora() {
		return dicAutoridadEmisora;
	}


	public void setDicAutoridadEmisora(DicAutoridadEmisora dicAutoridadEmisora) {
		this.dicAutoridadEmisora = dicAutoridadEmisora;
	}


	public DgCatEstado getDgCatEstado() {
		return dgCatEstado;
	}


	public void setDgCatEstado(DgCatEstado dgCatEstado) {
		this.dgCatEstado = dgCatEstado;
	}


	public String getNoReferencia() {
		return noReferencia;
	}


	public void setNoReferencia(String noReferencia) {
		this.noReferencia = noReferencia;
	}


	public DitDocumentoProbatorio getDitDocumentoProbatorio() {
		return ditDocumentoProbatorio;
	}


	public void setDitDocumentoProbatorio(DitDocumentoProbatorio ditDocumentoProbatorio) {
		this.ditDocumentoProbatorio = ditDocumentoProbatorio;
	}

	
	
}