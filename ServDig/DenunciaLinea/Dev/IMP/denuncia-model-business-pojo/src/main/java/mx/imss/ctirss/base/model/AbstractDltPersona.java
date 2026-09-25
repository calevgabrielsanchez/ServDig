package mx.imss.ctirss.base.model;


import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;

import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;
import mx.imss.ctirss.catalogos.model.DlcTipodenunciante;
import mx.imss.ctirss.catalogos.model.DlcTipodocumento;
import mx.imss.ctirss.catalogos.model.DlcUsuario;
import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.model.DltDenuncia;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;


/**
 * The persistent class for the DLT_PERSONA database table.
 * 
 */
@MappedSuperclass
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractDltPersona extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id 
	@SequenceGenerator(name="DLT_PERSONA_CVEPERSONA_GENERATOR", sequenceName="SEQ_CVE_PERSONA")
	@GeneratedValue( generator="DLT_PERSONA_CVEPERSONA_GENERATOR")
	@Column(name="CVE_PERSONA")
	private Long cvePersona;	
	
	@Column(name="CVE_TIPODENUNCIANTE",insertable=false, updatable=false)
	private Long cveTipodenunciante;

	@Column(name="CVE_TIPODOCUMENTO",insertable=false, updatable=false)
	private Long cveTipodocumento;
	
	@Column(name="CVE_FOLIODENUNCIA",nullable=false)
	private Long cveFoliodenuncia;	

	@Column(name="CVE_CURP")
	private String cveCurp;

	@Column(name="CVE_NSS")
	private String cveNss;

	@Column(name="CVE_RFC")
	private String cveRfc;

	@Column(name="DES_EMAIL")
	private String desEmail;

	@Column(name="DES_MATERNO")
	private String desMaterno;

	@Column(name="DES_NOMBRE")
	private String desNombre;

	@Column(name="DES_PATERNO")
	private String desPaterno;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHAREG")
	private Date fecFechareg;

	@Column(name="NUM_CELULAR")
	private String numCelular;

	@Column(name="NUM_DOCUMENTO")
	private String numDocumento;

	@Column(name="NUM_TELEFONO")
	private String numTelefono;

	//@Column(name="REF_DOCUMENTO")
	//private Blob refDocumento;
	
	@Column(name="NOM_DOCUMENTO")
	private String nomDocumento;
	
		
	@Column(name="CVE_ID_SEXO")
	private Integer sexoTrabajador;

	
	@Column( name = "REF_DOCUMENTO" )
	@Lob
	private byte[] refDocumento;
	
	
	
	@Transient
	private String nombreCompleto;

	//bi-directional many-to-one association to DgDomicilioGeografico
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DOMICILIO_ID")
	private DgDomicilioGeografico dgDomicilioGeografico;

	//bi-directional many-to-one association to DlcTipodenunciante
    @ManyToOne
	@JoinColumn(name="CVE_TIPODENUNCIANTE")
	private DlcTipodenunciante dlcTipodenunciante;

	//bi-directional many-to-one association to DlcTipodocumento
    @ManyToOne
	@JoinColumn(name="CVE_TIPODOCUMENTO")
	private DlcTipodocumento dlcTipodocumento;

	//bi-directional many-to-one association to DltDenuncia
    @ManyToOne
    @JsonIgnore
	@JoinColumn(name="CVE_FOLIODENUNCIA", referencedColumnName="CVE_FOLIODENUNCIA",nullable = false, insertable = false, updatable = false)
	private DltDenuncia dltDenuncia;
	
    
    public AbstractDltPersona() {
    }

	public Long getCvePersona() {
		return this.cvePersona;
	}

	public void setCvePersona(Long cvePersona) {
		this.cvePersona = cvePersona;
	}

	public String getCveCurp() {
		return this.cveCurp==null?"":this.cveCurp.trim();
	}

	public void setCveCurp(String cveCurp) {
		this.cveCurp = cveCurp;
	}

	public String getCveNss() {
		return this.cveNss==null?"":this.cveNss;
	}

	public void setCveNss(String cveNss) {
		this.cveNss = cveNss;
	}

	public String getCveRfc() {
		return this.cveRfc==null?"":this.cveRfc.trim();
	}

	public void setCveRfc(String cveRfc) {
		this.cveRfc = cveRfc;
	}

	public String getDesEmail() {
		return this.desEmail==null?"":this.desEmail.trim();
	}

	public void setDesEmail(String desEmail) {
		this.desEmail = desEmail;
	}

	public String getDesMaterno() {
		return this.desMaterno==null?"":this.desMaterno.trim();
	}

	public void setDesMaterno(String desMaterno) {
		this.desMaterno = desMaterno;
	}

	public String getDesNombre() {
		return this.desNombre==null?"":this.desNombre.trim();
	}

	public void setDesNombre(String desNombre) {
		this.desNombre = desNombre;
	}

	public String getDesPaterno() {
		return this.desPaterno==null?"":this.desPaterno.trim();
	}

	public void setDesPaterno(String desPaterno) {
		this.desPaterno = desPaterno;
	}

	public Date getFecFechareg() {
		return this.fecFechareg;
	}

	public void setFecFechareg(Date fecFechareg) {
		this.fecFechareg = fecFechareg;
	}

	public String getNumCelular() {
		return this.numCelular;
	}

	public void setNumCelular(String numCelular) {
		this.numCelular = numCelular;
	}

	public String getNumDocumento() {
		return this.numDocumento;
	}

	public void setNumDocumento(String numDocumento) {
		this.numDocumento = numDocumento;
	}

	public String getNumTelefono() {
		return this.numTelefono;
	}

	public void setNumTelefono(String numTelefono) {
		this.numTelefono = numTelefono;
	}

	public Integer getSexoTrabajador() {
		return this.sexoTrabajador;
	}

	public void setSexoTrabajador(Integer sexoTrabajador) {
		this.sexoTrabajador = sexoTrabajador;
	}
	
	public DgDomicilioGeografico getDgDomicilioGeografico() {
		return this.dgDomicilioGeografico;
	}

	public void setDgDomicilioGeografico(DgDomicilioGeografico dgDomicilioGeografico) {
		this.dgDomicilioGeografico = dgDomicilioGeografico;
	}
	
	public DlcTipodenunciante getDlcTipodenunciante() {
		return this.dlcTipodenunciante;
	}


	public void setDlcTipodenunciante(DlcTipodenunciante dlcTipodenunciante) {
		this.dlcTipodenunciante = dlcTipodenunciante;
	}

	
	public DlcTipodocumento getDlcTipodocumento() {
		return this.dlcTipodocumento;
	}

	public void setDlcTipodocumento(DlcTipodocumento dlcTipodocumento) {
		this.dlcTipodocumento = dlcTipodocumento;
	}
	
	public DltDenuncia getDltDenuncia() {
		return dltDenuncia;
	}

	public void setDltDenuncia(DltDenuncia dltDenuncia) {
		this.dltDenuncia = dltDenuncia;
	}
	

	public Long getCveTipodenunciante() {
		return cveTipodenunciante;
	}

	public void setCveTipodenunciante(Long cveTipodenunciante) {
		this.cveTipodenunciante = cveTipodenunciante;
	}

	public Long getCveTipodocumento() {
		return cveTipodocumento;
	}

	public void setCveTipodocumento(Long cveTipodocumento) {
		this.cveTipodocumento = cveTipodocumento;
	}

	public Long getCveFoliodenuncia() {
		return cveFoliodenuncia;
	}

	public void setCveFoliodenuncia(Long cveFoliodenuncia) {
		this.cveFoliodenuncia = cveFoliodenuncia;
	}

	public String getNombreCompleto() {
		StringBuilder bu=new StringBuilder();
		
		if(this.getDesNombre()!=null){
			bu.append(this.getDesNombre());
			bu.append(" ");
		}
		
		
		if(this.getDesPaterno()!=null){
			bu.append(this.getDesPaterno());
			bu.append(" ");
		}
		
		
		if(this.getDesMaterno()!=null){
			bu.append(this.getDesMaterno());
			bu.append(" ");
		}
		return bu.toString().toUpperCase();
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public byte[ ]  getRefDocumento() {
		return refDocumento;
	}

	public void setRefDocumento(byte[ ]  refDocumento) {
		this.refDocumento = refDocumento;
	}

	public String getNomDocumento() {
		return nomDocumento;
	}

	public void setNomDocumento(String nomDocumento) {
		this.nomDocumento = nomDocumento;
	}
	
	

}