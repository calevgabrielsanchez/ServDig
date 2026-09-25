package mx.imss.ctirss.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.imss.ctirss.framework.base.model.AbstractModel;


@MappedSuperclass
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractDltNroFolio extends AbstractModel {

	private static final long serialVersionUID = 1L;
	
	
	
	@Id
	@SequenceGenerator(name="CVE_PKFOLIO_GENERATOR", sequenceName="SEQ_CVE_PK_FOLIO")
	@GeneratedValue(generator="CVE_PKFOLIO_GENERATOR")
	@Column(name="CVE_PK_FOLIO")
	private Long cvePkFolio;
	
	@Column(name="NUM_ANIO")
	private Long numAnio;
	
	
	@Column(name="NUM_NUMERO")
	private Long numNumero;
	
	
	@Column(name="CVE_MOTIVODENUNCIA")
	private Long cveMotivoDenuncia;


    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHAREG")
	private Date fecFechareg;


	public Long getCvePkFolio() {
		return cvePkFolio;
	}


	public void setCvePkFolio(Long cvePkFolio) {
		this.cvePkFolio = cvePkFolio;
	}


	public Long getNumAnio() {
		return numAnio;
	}


	public void setNumAnio(Long numAnio) {
		this.numAnio = numAnio;
	}


	public Long getNumNumero() {
		return numNumero;
	}


	public void setNumNumero(Long numNumero) {
		this.numNumero = numNumero;
	}


	public Long getCveMotivoDenuncia() {
		return cveMotivoDenuncia;
	}


	public void setCveMotivoDenuncia(Long cveMotivoDenuncia) {
		this.cveMotivoDenuncia = cveMotivoDenuncia;
	}


	public Date getFecFechareg() {
		return fecFechareg;
	}


	public void setFecFechareg(Date fecFechareg) {
		this.fecFechareg = fecFechareg;
	}
	
    
    
}
