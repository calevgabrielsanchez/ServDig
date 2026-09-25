package mx.gob.imss.ctirss.correccion.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.correccion.catalogos.model.SacSubdelegacion;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;


/**
 * Model para la tabla CRT_REVDERIVASUBD
 * @author Saal Rosales Piedragil
 * @since 07/08/2012
 */
@Entity
@Table(name="CRT_REVDERIVASUBD")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrtRevDerivASubd extends AbstractModel{
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="CVE_REVDERIVASUBD", sequenceName="SEQ_CVE_REVDERIVASUBD")
	@GeneratedValue(generator="CVE_REVDERIVASUBD")
	@Column(name="CVE_REVDERIVASUBD")
	public Long cveRevDerivaAsubd;
	
	//bi-directional many-to-one association to AbstractCgcCatsubdelegacion
    @ManyToOne
	@JoinColumn(name="CVE_SOLICITUDCORR")
	private CrtSolicitudcorr solicitudCorr;
	
	@Column(name="NUM_FOLIO_OFICIO")
	private String numFolio;
	
    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHADERIVA")
	private Date fechaDerivacion;    
	
	//bi-directional many-to-one association to AbstractCgcCatsubdelegacion
    @ManyToOne
	@JoinColumn(name="CVE_FK_SUBDELEGDESTINO")
	private SacSubdelegacion subdelegDestino;
	
	//bi-directional many-to-one association to AbstractCgcCatsubdelegacion
    @ManyToOne
	@JoinColumn(name="CVE_FK_SUBDELEGORIGEN")
	private SacSubdelegacion subdelegOrigen;    
	
	//bi-directional many-to-one association to DgDomicilioGeografico
//    @ManyToOne
//	@JoinColumn(name="DOMICILIO_ID")
    @Transient
	private DgDomicilioGeografico domicilio;
    
	@Column(name="NOM_USER_DERIVA")
	private String nomUsuarioDeriva;	
	
	
	@Column(name="DOMICILIO_ID")
	private Long domicilioId;
	
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name="FEC_FECHAREG")
	private Date fechaReg;       
    
	@Column(name="CVE_USUARIO")
	private String claveUsuario;
	
	@Transient
	private String fechaFecDerivacion;

	public CrtSolicitudcorr getSolicitudCorr() {
		return solicitudCorr;
	}

	public void setSolicitudCorr(CrtSolicitudcorr solicitudCorr) {
		this.solicitudCorr = solicitudCorr;
	}


	public Date getFechaDerivacion() {
		return fechaDerivacion;
	}

	public void setFechaDerivacion(Date fechaDerivacion) {
		this.fechaDerivacion = fechaDerivacion;
	}

	public DgDomicilioGeografico getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(DgDomicilioGeografico domicilio) {
		this.domicilio = domicilio;
	}

	public String getNomUsuarioDeriva() {
		return nomUsuarioDeriva;
	}

	public void setNomUsuarioDeriva(String nomUsuarioDeriva) {
		this.nomUsuarioDeriva = nomUsuarioDeriva;
	}

	public String getClaveUsuario() {
		return claveUsuario;
	}

	public void setClaveUsuario(String claveUsuario) {
		this.claveUsuario = claveUsuario;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getCveRevDerivaAsubd() {
		return cveRevDerivaAsubd;
	}

	public void setCveRevDerivaAsubd(Long cveRevDerivaAsubd) {
		this.cveRevDerivaAsubd = cveRevDerivaAsubd;
	}

	public String getNumFolio() {
		return numFolio;
	}

	public void setNumFolio(String numFolio) {
		this.numFolio = numFolio;
	}

	public Date getFechaReg() {
		return fechaReg;
	}

	public void setFechaReg(Date fechaReg) {
		this.fechaReg = fechaReg;
	}

	public SacSubdelegacion getSubdelegDestino() {
		return subdelegDestino;
	}

	public void setSubdelegDestino(SacSubdelegacion subdelegDestino) {
		this.subdelegDestino = subdelegDestino;
	}

	public SacSubdelegacion getSubdelegOrigen() {
		return subdelegOrigen;
	}

	public void setSubdelegOrigen(SacSubdelegacion subdelegOrigen) {
		this.subdelegOrigen = subdelegOrigen;
	}

	public String getFechaFecDerivacion() {
		return fechaFecDerivacion;
	}

	public void setFechaFecDerivacion(String fechaFecDerivacion) {
		this.fechaFecDerivacion = fechaFecDerivacion;
	}

	public Long getDomicilioId() {
		return domicilioId;
	}

	public void setDomicilioId(Long domicilioId) {
		this.domicilioId = domicilioId;
	}	
	
	
}