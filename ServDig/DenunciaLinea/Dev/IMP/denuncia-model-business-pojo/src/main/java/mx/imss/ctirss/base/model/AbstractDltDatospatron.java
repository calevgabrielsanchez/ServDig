package mx.imss.ctirss.base.model;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;
import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.model.DltDenuncia;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DLT_DATOSPATRON database table.
 * 
 */
@MappedSuperclass
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractDltDatospatron extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DLT_DATOSPATRON_CVEDATOSPATRON_GENERATOR", sequenceName="SEQ_CVE_DATOSPATRON")
	@GeneratedValue(generator="DLT_DATOSPATRON_CVEDATOSPATRON_GENERATOR")
	@Column(name="CVE_DATOSPATRON")
	private Long cveDatospatron;

	
	@Column(name="CVE_ACTECONOMICA")
	private Long cveActEconomica;
	
	@Column(name="CVE_FOLIODENUNCIA")
	private Long cveFoliodenuncia;

	@Column(name="CVE_REGPAT")
	private String cveRegpat;

	@Column(name="DES_NOMRAZONSOCIAL")
	private String desNomrazonsocial;

	@Column(name="DES_NOMREPLEGAL")
	private String desNomreplegal;

	@Column(name="DES_OBSERVACIONES")
	private String desObservaciones;

	@Column(name="DES_RFC")
	private String desRfc;

	@Column(name="DOMICILIO_ID")
	private Long domicilioId;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHAREG")
	private Date fecFechareg;

	@Column(name="ID_PATRONPRINCIPAL")
	private BigDecimal idPatronprincipal;

	@Column(name="IND_PATRONDENUNCIADO")
	private BigDecimal indPatrondenunciado;	
	
//	@Column(name="NUM_SUBFOLIODENUNCIA")
//	private String numSubfoliodenuncia;

	@Column(name="NUM_TELEFONO")
	private String numTelefono;

	@Column(name="NUM_TRABAJADORES")
	private BigDecimal numTrabajadores;
	
	@Column(name="IND_RECIBEPAGOTOT")
	private BigDecimal indRecibePagoTot;	
	
	

	

	//bi-directional many-to-one association to DltDenuncia
    @ManyToOne  
    @JsonIgnore
	@JoinColumn(name="CVE_FOLIODENUNCIA",referencedColumnName="CVE_FOLIODENUNCIA",nullable = false, insertable = false, updatable = false)
	private DltDenuncia dltDenuncia;


	public Long getCveDatospatron() {
		return this.cveDatospatron;
	}

	public void setCveDatospatron(Long cveDatospatron) {
		this.cveDatospatron = cveDatospatron;
	}

	public Long getCveFoliodenuncia() {
		return this.cveFoliodenuncia;
	}

	public void setCveFoliodenuncia(Long cveFoliodenuncia) {
		this.cveFoliodenuncia = cveFoliodenuncia;
	}

	public String getCveRegpat() {
		return this.cveRegpat;
	}

	public void setCveRegpat(String cveRegpat) {
		this.cveRegpat = cveRegpat;
	}

	public String getDesNomrazonsocial() {
		return this.desNomrazonsocial;
	}

	public void setDesNomrazonsocial(String desNomrazonsocial) {
		this.desNomrazonsocial = desNomrazonsocial;
	}

	public String getDesNomreplegal() {
		return this.desNomreplegal;
	}

	public void setDesNomreplegal(String desNomreplegal) {
		this.desNomreplegal = desNomreplegal;
	}

	public String getDesObservaciones() {
		return this.desObservaciones;
	}

	public void setDesObservaciones(String desObservaciones) {
		this.desObservaciones = desObservaciones;
	}

	public String getDesRfc() {
		return this.desRfc;
	}

	public void setDesRfc(String desRfc) {
		this.desRfc = desRfc;
	}

	public Long getDomicilioId() {
		return this.domicilioId;
	}

	public void setDomicilioId(Long domicilioId) {
		this.domicilioId = domicilioId;
	}

	public Date getFecFechareg() {
		return this.fecFechareg;
	}

	public void setFecFechareg(Date fecFechareg) {
		this.fecFechareg = fecFechareg;
	}

	public BigDecimal getIdPatronprincipal() {
		return this.idPatronprincipal;
	}

	public void setIdPatronprincipal(BigDecimal idPatronprincipal) {
		this.idPatronprincipal = idPatronprincipal;
	}

	public BigDecimal getIndPatrondenunciado() {
		return this.indPatrondenunciado;
	}

	public void setIndPatrondenunciado(BigDecimal indPatrondenunciado) {
		this.indPatrondenunciado = indPatrondenunciado;
	}

//	public String getNumSubfoliodenuncia() {
//		return this.numSubfoliodenuncia;
//	}
//
//	public void setNumSubfoliodenuncia(String numSubfoliodenuncia) {
//		this.numSubfoliodenuncia = numSubfoliodenuncia;
//	}

	public String getNumTelefono() {
		return this.numTelefono;
	}

	public void setNumTelefono(String numTelefono) {
		this.numTelefono = numTelefono;
	}

	public BigDecimal getNumTrabajadores() {
		return this.numTrabajadores;
	}

	public void setNumTrabajadores(BigDecimal numTrabajadores) {
		this.numTrabajadores = numTrabajadores;
	}

	
	
	public DltDenuncia getDltDenuncia() {
		return this.dltDenuncia;
	}

	public void setDltDenuncia(DltDenuncia dltDenuncia) {
		this.dltDenuncia = dltDenuncia;
	}

	public BigDecimal getIndRecibePagoTot() {
		return indRecibePagoTot;
	}

	public void setIndRecibePagoTot(BigDecimal indRecibePagoTot) {
		this.indRecibePagoTot = indRecibePagoTot;
	}

	public Long getCveActEconomica() {
		return cveActEconomica;
	}

	public void setCveActEconomica(Long cveActEconomica) {
		this.cveActEconomica = cveActEconomica;
	}

	
	
}