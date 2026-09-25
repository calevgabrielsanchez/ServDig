package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_ACTA database table.
 * 
 */
@Entity
@Table(name="DIT_ACTA")
public class DitActa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_DOCUMENTO_PROBATORIO", nullable=false, precision=22)
	private Long cveIdDocumentoProbatorio;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_SUCESO", nullable=false)
	private Date fecSuceso;

	@Column(name="NUM_ACTA", nullable=false, length=16)
	private String numActa;

	@Column(name="NUM_FOJA", nullable=false, length=8)
	private String numFoja;

	@Column(name="NUM_LIBRO", nullable=false, length=8)
	private String numLibro;
	
	@Column(name="NUM_JUZGADO", nullable=false, length=8)
	private String numJuzgado;

	//bi-directional one-to-one association to DitDocumentoProbatorio
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_DOCUMENTO_PROBATORIO", nullable=false)
	private DitDocumentoProbatorio ditDocumentoProbatorio;

	
	@Column(name="REF_NUM_TOMO", length=20)
	private String refNumTomo;
	
//	//bi-directional many-to-one association to DicTipoActa
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="CVE_ID_TIPO_ACTA", nullable=false)
//	private DicTipoActa dicTipoActa;

	//A petición de Fernando Arturo Castellanos Vargas  se comenta esta union,
	//para resolver el ticket 4547282, ya que el catalogo de municipios no se actualiza y
	//no permite registrar actas
	//bi-directional many-to-one association to DgCatMunicipio
	//@ManyToOne(fetch=FetchType.LAZY)
	//@JoinColumns({
	//	@JoinColumn(name="CVE_ENT", referencedColumnName="CVE_ENT"),
	//	@JoinColumn(name="CVE_MUN", referencedColumnName="CVE_MUN")
	//	})
	@Transient
	private DgCatMunicipio dgCatMunicipio;



	//bi-directional one-to-one association to DitNacimiento
	@OneToOne(mappedBy="ditActa", fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
	private DitNacimiento ditNacimiento;

    public DitActa() {
    }
    
	public String getNumJuzgado() {
		return numJuzgado;
	}

	public void setNumJuzgado(String numJuzgado) {
		this.numJuzgado = numJuzgado;
	}

	public long getCveIdDocumentoProbatorio() {
		return this.cveIdDocumentoProbatorio;
	}

	public void setCveIdDocumentoProbatorio(long cveIdDocumentoProbatorio) {
		this.cveIdDocumentoProbatorio = cveIdDocumentoProbatorio;
	}

	public Date getFecSuceso() {
		return this.fecSuceso;
	}

	public void setFecSuceso(Date fecSuceso) {
		this.fecSuceso = fecSuceso;
	}

	public String getNumActa() {
		return this.numActa;
	}

	public void setNumActa(String numActa) {
		this.numActa = numActa;
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

	public DitDocumentoProbatorio getDitDocumentoProbatorio() {
		return this.ditDocumentoProbatorio;
	}

	public void setDitDocumentoProbatorio(DitDocumentoProbatorio ditDocumentoProbatorio) {
		this.ditDocumentoProbatorio = ditDocumentoProbatorio;
	}
	
//	public DicTipoActa getDicTipoActa() {
//		return this.dicTipoActa;
//	}
//
//	public void setDicTipoActa(DicTipoActa dicTipoActa) {
//		this.dicTipoActa = dicTipoActa;
//	}
	
	public DgCatMunicipio getDgCatMunicipio() {
		return this.dgCatMunicipio;
	}

	public void setDgCatMunicipio(DgCatMunicipio dgCatMunicipio) {
		this.dgCatMunicipio = dgCatMunicipio;
	}
	

	
	public DitNacimiento getDitNacimiento() {
		return this.ditNacimiento;
	}

	public void setDitNacimiento(DitNacimiento ditNacimiento) {
		this.ditNacimiento = ditNacimiento;
	}

	public String getRefNumTomo() {
		return refNumTomo;
	}

	public void setRefNumTomo(String refNumTomo) {
		this.refNumTomo = refNumTomo;
	}
	
}