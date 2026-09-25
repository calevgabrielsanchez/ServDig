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


/**
 * The persistent class for the DIT_CONSTANCIA_ESTUDIO database table.
 * 
 */
@Entity
@Table(name="DIT_CONSTANCIA_ESTUDIO")
public class DitConstanciaEstudio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_DOCUMENTO_PROBATORIO", unique=true, nullable=false, precision=22)
	private long cveIdDocumentoProbatorio;

	@Column(name="CVE_ESCUELA", nullable=false, length=16)
	private String cveEscuela;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FIN_PERIODO", nullable=false)
	private Date fecFinPeriodo;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INICIO_PERIODO", nullable=false)
	private Date fecInicioPeriodo;

	@Column(name="NOM_ESCUELA", nullable=false, length=255)
	private String nomEscuela;

	@Column(name="NUM_INCORPORACION", length=16)
	private String numIncorporacion;

	@Column(name="REF_GRADO_ESCOLAR", nullable=false, length=16)
	private String refGradoEscolar;
	
	@ManyToOne
	@JoinColumn(name="CVE_ID_DETALLE_NIVEL_EDUCATIVO", nullable=true)
	private DitDetalleNivelEducativo detalleNivelEducativo;

	//bi-directional one-to-one association to ditDocumentoProbatorio
	@OneToOne
	@JoinColumn(name="CVE_ID_DOCUMENTO_PROBATORIO")
	private DitDocumentoProbatorio ditDocumentoProbatorio;

    public DitConstanciaEstudio() {
    }

	public long getCveIdDocumentoProbatorio() {
		return this.cveIdDocumentoProbatorio;
	}

	public void setCveIdDocumentoProbatorio(long cveIdDocumentoProbatorio) {
		this.cveIdDocumentoProbatorio = cveIdDocumentoProbatorio;
	}

	public String getCveEscuela() {
		return this.cveEscuela;
	}

	public void setCveEscuela(String cveEscuela) {
		this.cveEscuela = cveEscuela;
	}

	public Date getFecFinPeriodo() {
		return this.fecFinPeriodo;
	}

	public void setFecFinPeriodo(Date fecFinPeriodo) {
		this.fecFinPeriodo = fecFinPeriodo;
	}

	public Date getFecInicioPeriodo() {
		return this.fecInicioPeriodo;
	}

	public void setFecInicioPeriodo(Date fecInicioPeriodo) {
		this.fecInicioPeriodo = fecInicioPeriodo;
	}

	public String getNomEscuela() {
		return this.nomEscuela;
	}

	public void setNomEscuela(String nomEscuela) {
		this.nomEscuela = nomEscuela;
	}

	public String getNumIncorporacion() {
		return this.numIncorporacion;
	}

	public void setNumIncorporacion(String numIncorporacion) {
		this.numIncorporacion = numIncorporacion;
	}

	public String getRefGradoEscolar() {
		return this.refGradoEscolar;
	}

	public void setRefGradoEscolar(String refGradoEscolar) {
		this.refGradoEscolar = refGradoEscolar;
	}

	/**
	 * @return the ditDocumentoProbatorio
	 */
	public DitDocumentoProbatorio getDitDocumentoProbatorio() {
		return ditDocumentoProbatorio;
	}

	/**
	 * @param ditDocumentoProbatorio the ditDocumentoProbatorio to set
	 */
	public void setDitDocumentoProbatorio(
			DitDocumentoProbatorio ditDocumentoProbatorio) {
		this.ditDocumentoProbatorio = ditDocumentoProbatorio;
	}

	public DitDetalleNivelEducativo getDetalleNivelEducativo() {
		return detalleNivelEducativo;
	}

	public void setDetalleNivelEducativo(
			DitDetalleNivelEducativo detalleNivelEducativo) {
		this.detalleNivelEducativo = detalleNivelEducativo;
	}
	

}