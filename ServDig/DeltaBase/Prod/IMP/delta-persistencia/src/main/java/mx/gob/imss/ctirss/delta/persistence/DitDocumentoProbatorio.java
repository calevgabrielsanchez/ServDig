package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the DIT_DOCUMENTO_PROBATORIO database table.
 * 
 */
@Entity
@Table(name="DIT_DOCUMENTO_PROBATORIO")
public class DitDocumentoProbatorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIT_DOCUMENTO_PROBATORIO_CVEIDDOCUMENTOPROBATORIO_GENERATOR", sequenceName="SEQ_DITDOCUMENTOPROBATORIO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIT_DOCUMENTO_PROBATORIO_CVEIDDOCUMENTOPROBATORIO_GENERATOR")
	@Column(name="CVE_ID_DOCUMENTO_PROBATORIO")
	private Long cveIdDocumentoProbatorio;

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_EXPEDICION", nullable=false)
	private Date fecExpedicion;

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA", nullable=false)
	private Date fecRegistroAlta;

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

    
    @Column(name="REF_COD_ENCRIPTADO")
	private String refCodEncriptado;
    
    @Lob()
	@Column(name="REF_DOCUMENTO_DIGITALIZADO")
	private byte[] refDocumentoDigitalizado;
    
    @Column(name="REF_BOVEDA_DOC_ID")
    private String refBovedaDocId;

    

	//bi-directional one-to-one association to DitActa
    @OneToOne(mappedBy="ditDocumentoProbatorio", fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
    private DitActa ditActa;
    
    @OneToOne(mappedBy="ditDocumentoProbatorio", fetch=FetchType.LAZY)
    private DitAcuerdo ditAcuerdo;
    
    @OneToOne(mappedBy="ditDocumentoProbatorio", fetch=FetchType.LAZY)
    private DitCartillaMilitar ditCartillaMilitar;
    
    @OneToOne(mappedBy="ditDocumentoProbatorio", fetch=FetchType.LAZY)
    private DitCedulaProfesional ditCedulaProfesional;
    
    @OneToOne(mappedBy="ditDocumentoProbatorio", fetch=FetchType.LAZY)
    private DitNacimiento ditNacimiento;
    
    
    @OneToOne(mappedBy="ditDocumentoProbatorio", fetch=FetchType.LAZY)
    private DitCertificadoSitCritica ditCertificadoSitCritica;
    
    //bi-directional one-to-one association to DitActa
    @OneToOne(mappedBy="ditDocumentoProbatorio", fetch=FetchType.LAZY)
    private DitObstetrico ditObstetrico;
    
    //bi-directional one-to-one association to DitActa
    @OneToOne(mappedBy="ditDocumentoProbatorio", fetch=FetchType.LAZY)
    private DitDictBeneficiarioInca ditDictBeneficiarioInca;
    
    //bi-directional one-to-one association to DitActa
  
    @OneToOne(mappedBy="ditDocumentoProbatorio", fetch=FetchType.LAZY)
    private DitConstanciaEstudio ditConstanciaEstudio;
    
    
  //bi-directional one-to-one association to DitCurp

  	@OneToOne(mappedBy="ditDocumentoProbatorio", cascade = CascadeType.REMOVE)
  	private DitCurp ditCurp;
  	
  	
  	@OneToOne(mappedBy="ditDocumentoProbatorio")
  	private DitCredElector ditCredElector;
  	
	@OneToOne(mappedBy="ditDocumentoProbatorio")
  	private DitCertificadoNacimiento ditCertificadoNacimiento;
	
	@OneToOne(mappedBy="ditDocumentoProbatorio")
  	private DitPasaporte ditPasaporte;
	
	@OneToOne(mappedBy="ditDocumentoProbatorio")
  	private DitVigenciaTemporal ditVigenciaTemporal;
	
	@OneToOne(mappedBy="ditDocumentoProbatorio")
  	private DitComprobanteDomicilio ditComprobanteDomicilio;
    
	@OneToOne(mappedBy="ditDocumentoProbatorio")
  	private DitAdimss ditAdimss;
	
	@OneToOne(mappedBy="ditDocumentoProbatorio", fetch=FetchType.LAZY)
  	private DitMatriculaConsular ditMatriculaConsular;

	@OneToOne(mappedBy="ditDocumentoProbatorio", fetch=FetchType.LAZY) 
  	private DitFormaMigratoria ditFormaMigratoria;
	
	@OneToOne(mappedBy="ditDocumentoProbatorio", fetch=FetchType.LAZY)
  	private DitActaUnionCivil ditActaUnionCivil;

	@OneToOne(mappedBy="ditDocumentoProbatorio", fetch=FetchType.LAZY) 
  	private DitActaTerminoUnionCivil ditActaTerminoUnionCivil;
	
    //bi-directional many-to-many association to DitPersona
    @ManyToMany
    @JoinTable(
        name="DIT_DOCTOS_PERSONA"
        , joinColumns={
            @JoinColumn(name="CVE_ID_DOCUMENTO_PROBATORIO", nullable=false, updatable=false)
            }
        , inverseJoinColumns={
            @JoinColumn(name="CVE_ID_PERSONA", nullable=false, updatable=false)
            }
        )
    private List<DitPersona> ditPersonas = new ArrayList<DitPersona>();

    //bi-directional many-to-many association to DitPersona
    @ManyToMany
    @JoinTable(
        name="DIT_DOCTOS_PERSONA"
        , joinColumns={
            @JoinColumn(name="CVE_ID_DOCUMENTO_PROBATORIO", nullable=false, updatable=false)
            }
        , inverseJoinColumns={
            @JoinColumn(name="CVE_ID_PERSONA", nullable=false, updatable=false)
            }
        )
    private List<DitPersonaView> ditPersonasView = new ArrayList<DitPersonaView>();

    //bi-directional many-to-many association to DitTramite
	@ManyToMany(mappedBy="ditDocumentoProbatorios")
	private List<DitTramite> ditTramites;

	//bi-directional many-to-one association to DitDocumentoPorTipo
    @ManyToOne
	@JoinColumn(name="CVE_ID_DOCTO_PROB_POR_TIPO")
	private DitDocumentoPorTipo ditDocumentoPorTipo;
    
    @Column(name="NOM_NOMBRE_DOCUMENTO")
	private String nomNombreDocumento;
    
        
    public DitDocumentoProbatorio() {
    }

	public Long getCveIdDocumentoProbatorio() {
		return this.cveIdDocumentoProbatorio;
	}

	public void setCveIdDocumentoProbatorio(Long cveIdDocumentoProbatorio) {
		this.cveIdDocumentoProbatorio = cveIdDocumentoProbatorio;
	}

	public Date getFecExpedicion() {
		return this.fecExpedicion;
	}

	public void setFecExpedicion(Date fecExpedicion) {
		this.fecExpedicion = fecExpedicion;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public String getRefCodEncriptado() {
		return this.refCodEncriptado;
	}

	public void setRefCodEncriptado(String refCodEncriptado) {
		this.refCodEncriptado = refCodEncriptado;
	}

	public byte[] getRefDocumentoDigitalizado() {
		return this.refDocumentoDigitalizado;
	}

	public void setRefDocumentoDigitalizado(byte[] refDocumentoDigitalizado) {
		this.refDocumentoDigitalizado = refDocumentoDigitalizado != null ? refDocumentoDigitalizado.clone() : null;
	}

	public List<DitTramite> getDitTramites() {
		return this.ditTramites;
	}

	public void setDitTramites(List<DitTramite> ditTramites) {
		this.ditTramites = ditTramites;
	}
	
	public DitDocumentoPorTipo getDitDocumentoPorTipo() {
		return this.ditDocumentoPorTipo;
	}

	public void setDitDocumentoPorTipo(DitDocumentoPorTipo ditDocumentoPorTipo) {
		this.ditDocumentoPorTipo = ditDocumentoPorTipo;
	}

    public List<DitPersona> getDitPersonas() {
        return ditPersonas;
    }

    public void setDitPersonas(List<DitPersona> ditPersonas) {
        this.ditPersonas = ditPersonas;
    }

    public List<DitPersonaView> getDitPersonasView() {
        return ditPersonasView;
    }

    public void setDitPersonasView(List<DitPersonaView> ditPersonasView) {
        this.ditPersonasView = ditPersonasView;
    }

    public DitActa getDitActa() {
        return ditActa;
    }

    public void setDitActa(DitActa ditActa) {
        this.ditActa = ditActa;
    }

	public DitObstetrico getDitObstetrico() {
		return ditObstetrico;
	}

	public void setDitObstetrico(DitObstetrico ditObstetrico) {
		this.ditObstetrico = ditObstetrico;
	}

	public DitDictBeneficiarioInca getDitDictBeneficiarioInca() {
		return ditDictBeneficiarioInca;
	}

	public void setDitDictBeneficiarioInca(
			DitDictBeneficiarioInca ditDictBeneficiarioInca) {
		this.ditDictBeneficiarioInca = ditDictBeneficiarioInca;
	}

	public DitConstanciaEstudio getDitConstanciaEstudio() {
		return ditConstanciaEstudio;
	}

	public void setDitConstanciaEstudio(DitConstanciaEstudio ditConstanciaEstudio) {
		this.ditConstanciaEstudio = ditConstanciaEstudio;
	}

	/**
	 * @return the ditCurp
	 */
	public DitCurp getDitCurp() {
		return ditCurp;
	}

	/**
	 * @param ditCurp the ditCurp to set
	 */
	public void setDitCurp(DitCurp ditCurp) {
		this.ditCurp = ditCurp;
	}

	public DitAcuerdo getDitAcuerdo() {
		return ditAcuerdo;
	}

	public void setDitAcuerdo(DitAcuerdo ditAcuerdo) {
		this.ditAcuerdo = ditAcuerdo;
	}

	public DitCartillaMilitar getDitCartillaMilitar() {
		return ditCartillaMilitar;
	}

	public void setDitCartillaMilitar(DitCartillaMilitar ditCartillaMilitar) {
		this.ditCartillaMilitar = ditCartillaMilitar;
	}

	public DitCedulaProfesional getDitCedulaProfesional() {
		return ditCedulaProfesional;
	}

	public void setDitCedulaProfesional(DitCedulaProfesional ditCedulaProfesional) {
		this.ditCedulaProfesional = ditCedulaProfesional;
	}

	public DitNacimiento getDitNacimiento() {
		return ditNacimiento;
	}

	public void setDitNacimiento(DitNacimiento ditNacimiento) {
		this.ditNacimiento = ditNacimiento;
	}



	public DitCredElector getDitCredElector() {
		return ditCredElector;
	}

	public void setDitCredElector(DitCredElector ditCredElector) {
		this.ditCredElector = ditCredElector;
	}



	public DitPasaporte getDitPasaporte() {
		return ditPasaporte;
	}

	public void setDitPasaporte(DitPasaporte ditPasaporte) {
		this.ditPasaporte = ditPasaporte;
	}

	public DitVigenciaTemporal getDitVigenciaTemporal() {
		return ditVigenciaTemporal;
	}

	public void setDitVigenciaTemporal(DitVigenciaTemporal ditVigenciaTemporal) {
		this.ditVigenciaTemporal = ditVigenciaTemporal;
	}

	public DitCertificadoSitCritica getDitCertificadoSitCritica() {
		return ditCertificadoSitCritica;
	}

	public void setDitCertificadoSitCritica(
			DitCertificadoSitCritica ditCertificadoSitCritica) {
		this.ditCertificadoSitCritica = ditCertificadoSitCritica;
	}

	public DitCertificadoNacimiento getDitCertificadoNacimiento() {
		return ditCertificadoNacimiento;
	}

	public void setDitCertificadoNacimiento(
			DitCertificadoNacimiento ditCertificadoNacimiento) {
		this.ditCertificadoNacimiento = ditCertificadoNacimiento;
	}

	public DitComprobanteDomicilio getDitComprobanteDomicilio() {
		return ditComprobanteDomicilio;
	}

	public void setDitComprobanteDomicilio(
			DitComprobanteDomicilio ditComprobanteDomicilio) {
		this.ditComprobanteDomicilio = ditComprobanteDomicilio;
	}

	public DitAdimss getDitAdimss() {
		return ditAdimss;
	}

	public void setDitAdimss(DitAdimss ditAdimss) {
		this.ditAdimss = ditAdimss;
	}
	
	public DitMatriculaConsular getDitMatriculaConsular() {
		return ditMatriculaConsular;
	}

	public void setDitMatriculaConsular(DitMatriculaConsular ditMatriculaConsular) {
		this.ditMatriculaConsular = ditMatriculaConsular;
	}

	public DitFormaMigratoria getDitFormaMigratoria() {
		return ditFormaMigratoria;
	}

	public void setDitFormaMigratoria(DitFormaMigratoria ditFormaMigratoria) {
		this.ditFormaMigratoria = ditFormaMigratoria;
	}

	public String getNomNombreDocumento() {
		return nomNombreDocumento;
	}

	public void setNomNombreDocumento(String nomNombreDocumento) {
		this.nomNombreDocumento = nomNombreDocumento;
	}
	
	public String getRefBovedaDocId() {
		return refBovedaDocId;
	}

	public void setRefBovedaDocId(String refBovedaDocId) {
		this.refBovedaDocId = refBovedaDocId;
	}

	public DitActaUnionCivil getDitActaUnionCivil() {
		return ditActaUnionCivil;
	}

	public void setDitActaUnionCivil(DitActaUnionCivil ditActaUnionCivil) {
		this.ditActaUnionCivil = ditActaUnionCivil;
	}

	public DitActaTerminoUnionCivil getDitActaTerminoUnionCivil() {
		return ditActaTerminoUnionCivil;
	}

	public void setDitActaTerminoUnionCivil(DitActaTerminoUnionCivil ditActaTerminoUnionCivil) {
		this.ditActaTerminoUnionCivil = ditActaTerminoUnionCivil;
	}
	
}