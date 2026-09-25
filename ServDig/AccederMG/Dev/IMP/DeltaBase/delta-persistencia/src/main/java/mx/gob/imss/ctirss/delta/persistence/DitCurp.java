package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the DIT_CURP database table.
 * 
 */
@Entity
@Table(name="DIT_CURP")
public class DitCurp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_DOCUMENTO_PROBATORIO", unique=true, nullable=false, precision=22)
	private long cveIdDocumentoProbatorio;

	@Column(name="NUM_TIPO_DOC_RENAPO", nullable=false)
	private Long numTipoDocRenapo;
	
	@Column(name="CVE_CRIP", length=18)
	private String cveCrip;

	@Column(name="CVE_CURP", nullable=false, length=18)
	private String cveCurp;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INSCRIPCION", nullable=true)
	private Date fecInscripcion;

	@Column(name="NUM_ACTA", nullable=true, length=8)
	private String numActa;

	@Column(name="NUM_ANIO_REGISTRO")
	private BigDecimal numAnio;

	@Column(name="NUM_FOJA", nullable=true, length=8)
	private String numFoja;

	@Column(name="NUM_LIBRO", nullable=true, length=8)
	private String numLibro;

	@Column(name="NUM_TOMO", length=8)
	private String numTomo;

	@Column(name="REF_FOLIO", nullable=true, length=16)
	private String refFolio;
	
	
	@Column(name="NUM_FOLIO_EXTRANJERO", nullable=true, length=20)
	private String numFolioExtranjero;

	//bi-directional one-to-one association to ditDocumentoProbatorio
	@OneToOne
	@JoinColumn(name="CVE_ID_DOCUMENTO_PROBATORIO")
	private DitDocumentoProbatorio ditDocumentoProbatorio;
	
	  /*bi-directional many-to-one association to DgCatEstado
    @ManyToOne
	@JoinColumn(name="CVE_ENT" , insertable=false ,updatable=false)
	private DgCatEstado  dgCatEstado;
    */
	
	//bi-directional many-to-one association to DgCatMunicipio
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_ENT", referencedColumnName="CVE_ENT"),
		@JoinColumn(name="CVE_MUN", referencedColumnName="CVE_MUN")
		})
	private DgCatMunicipio dgCatMunicipio;

	
	
    public DgCatMunicipio getDgCatMunicipio() {
		return dgCatMunicipio;
	}

	public void setDgCatMunicipio(DgCatMunicipio dgCatMunicipio) {
		this.dgCatMunicipio = dgCatMunicipio;
	}
/*
	public DgCatEstado getDgCatEstado() {
		return dgCatEstado;
	}

	public void setDgCatEstado(DgCatEstado dgCatEstado) {
		this.dgCatEstado = dgCatEstado;
	}
*/
	public DitCurp() {
    }

	public long getCveIdDocumentoProbatorio() {
		return this.cveIdDocumentoProbatorio;
	}

	public void setCveIdDocumentoProbatorio(long cveIdDocumentoProbatorio) {
		this.cveIdDocumentoProbatorio = cveIdDocumentoProbatorio;
	}

	public String getCveCrip() {
		return this.cveCrip;
	}

	public void setCveCrip(String cveCrip) {
		this.cveCrip = cveCrip;
	}

	public String getCveCurp() {
		return this.cveCurp;
	}

	public void setCveCurp(String cveCurp) {
		this.cveCurp = cveCurp;
	}

	public Date getFecInscripcion() {
		return this.fecInscripcion;
	}

	public void setFecInscripcion(Date fecInscripcion) {
		this.fecInscripcion = fecInscripcion;
	}

	public String getNumActa() {
		return this.numActa;
	}

	public void setNumActa(String numActa) {
		this.numActa = numActa;
	}



	public BigDecimal getNumAnio() {
		return numAnio;
	}

	public void setNumAnio(BigDecimal numAnio) {
		this.numAnio = numAnio;
	}

	public String getNumFoja() {
		return this.numFoja;
	}

	public void setNumFoja(String numFoja) {
		this.numFoja = numFoja;
	}

	public String getNumLibro() {
		return this.numLibro;
	}

	public void setNumLibro(String numLibro) {
		this.numLibro = numLibro;
	}

	public String getNumTomo() {
		return this.numTomo;
	}

	public void setNumTomo(String numTomo) {
		this.numTomo = numTomo;
	}

	public String getRefFolio() {
		return this.refFolio;
	}

	public void setRefFolio(String refFolio) {
		this.refFolio = refFolio;
	}
	
	

	public Long getNumTipoDocRenapo() {
		return numTipoDocRenapo;
	}

	public void setNumTipoDocRenapo(Long numTipoDocRenapo) {
		this.numTipoDocRenapo = numTipoDocRenapo;
	}
	
	

	public String getNumFolioExtranjero() {
		return numFolioExtranjero;
	}

	public void setNumFolioExtranjero(String numFolioExtranjero) {
		this.numFolioExtranjero = numFolioExtranjero;
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

}