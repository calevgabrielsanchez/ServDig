package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * The persistent class for the DIT_CRED_ELECTOR database table.
 * 
 */
@Entity
@Table(name="DIT_CRED_ELECTOR")
public class DitCredElector implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_DOCUMENTO_PROBATORIO", unique=true, nullable=false, precision=22)
	private long cveIdDocumentoProbatorio;

	@Column(name="CVE_ELECTOR", length=24)
	private String cveElector;

	@Column(name="NUM_ANIO_REGISTRO", nullable=false, precision=22)
	private BigDecimal numAnioRegistro;

	@Column(name="NUM_EMISION", precision=22)
	private BigDecimal numEmision;

	@Column(name="REF_CODIGO_SEGURIDAD", nullable=false, length=16)
	private String refCodigoSeguridad;
	
	@Column(name="REF_FOLIO")
	private String refFolio;
	
	@Column(name="REF_SECCION")
	private String refSeccion;
	
	@Column(name="NUM_ANIO_VIGENCIA")
	private Integer numAnioVigencia;

	//bi-directional many-to-one association to DgCatLocalidad
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_ENT", referencedColumnName="CVE_ENT"),
		@JoinColumn(name="CVE_LOC", referencedColumnName="CVE_LOC"),
		@JoinColumn(name="CVE_MUN", referencedColumnName="CVE_MUN"),
		@JoinColumn(name="CVE_PERIODO", referencedColumnName="CVE_PERIODO")
		})
	private DgCatLocalidad dgCatLocalidad;

  //bi-directional one-to-one association to ditDocumentoProbatorio
	@OneToOne
	@JoinColumn(name="CVE_ID_DOCUMENTO_PROBATORIO")
	private DitDocumentoProbatorio ditDocumentoProbatorio;
	
	@Column(name = "REF_MODELO_CREDENCIAL")
	private String refModeloCredencial;

    public DitCredElector() {
    }

	public long getCveIdDocumentoProbatorio() {
		return this.cveIdDocumentoProbatorio;
	}

	public void setCveIdDocumentoProbatorio(long cveIdDocumentoProbatorio) {
		this.cveIdDocumentoProbatorio = cveIdDocumentoProbatorio;
	}

	public String getCveElector() {
		return this.cveElector;
	}

	public void setCveElector(String cveElector) {
		this.cveElector = cveElector;
	}

	public BigDecimal getNumAnioRegistro() {
		return this.numAnioRegistro;
	}

	public void setNumAnioRegistro(BigDecimal numAnioRegistro) {
		this.numAnioRegistro = numAnioRegistro;
	}

	public BigDecimal getNumEmision() {
		return this.numEmision;
	}

	public void setNumEmision(BigDecimal numEmision) {
		this.numEmision = numEmision;
	}

	public String getRefCodigoSeguridad() {
		return this.refCodigoSeguridad;
	}

	public void setRefCodigoSeguridad(String refCodigoSeguridad) {
		this.refCodigoSeguridad = refCodigoSeguridad;
	}

	public DgCatLocalidad getDgCatLocalidad() {
		return this.dgCatLocalidad;
	}

	public void setDgCatLocalidad(DgCatLocalidad dgCatLocalidad) {
		this.dgCatLocalidad = dgCatLocalidad;
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

	public String getRefFolio() {
		return refFolio;
	}

	public void setRefFolio(String refFolio) {
		this.refFolio = refFolio;
	}

	public String getRefSeccion() {
		return refSeccion;
	}

	public void setRefSeccion(String refSeccion) {
		this.refSeccion = refSeccion;
	}

	public Integer getNumAnioVigencia() {
		return numAnioVigencia;
	}

	public void setNumAnioVigencia(Integer numAnioVigencia) {
		this.numAnioVigencia = numAnioVigencia;
	}

	public String getRefModeloCredencial() {
		return refModeloCredencial;
	}

	public void setRefModeloCredencial(String refModeloCredencial) {
		this.refModeloCredencial = refModeloCredencial;
	}
}