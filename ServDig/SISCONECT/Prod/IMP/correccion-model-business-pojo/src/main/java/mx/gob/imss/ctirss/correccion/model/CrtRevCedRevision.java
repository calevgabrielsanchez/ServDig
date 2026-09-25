package mx.gob.imss.ctirss.correccion.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;



/**
 * Model para la tabla CRT_REVCEDREVISION
 * @author Jorge Hernandez Almazan
 * @since 07/08/2012
 */
@Entity
@Table(name="CRT_REVCEDREVISION")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrtRevCedRevision extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@SequenceGenerator(name="CVE_REVCEDREVISION", sequenceName="SEQ_CVE_REVCEDREVISION")
	@GeneratedValue(generator="CVE_REVCEDREVISION")
	@Column(name="CVE_REVCEDREVISION")
	public Long cveRevCedRevision;
	
	
	@Column(name="CVE_ANEXOSOLCORRPAT")
	private Integer cveAnexoSolicitudCorrPat;
	
	@Column(name = "CVE_EJERCICIO")
	private Long cveEjercicio;
	
	
	@Column(name = "CVE_PRESENTACORR")
	private Long cvePresentaCorr;
	
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHAREG")
	private Date fechaReg;       
	    
	
	@Column(name="CVE_USUARIO")
	private String claveUsuario;
	
	
	
	@Column(name = "IMP_BASECOTPAGIMSS")
	private BigDecimal importeBaseCotPagImss;
	
		
	@Column(name = "IMP_BASECOTPATRON")
	private BigDecimal importeBaseCotPatron;


	@Column(name = "IMP_DIFBASECOTPAGIMSS")
	private BigDecimal importeDIfBaseCotPagImss;
	
	
	
	@Column(name = "IMP_DIFBASECOTPATRON")
	private BigDecimal importeDifBaseCotPatron;
	
	
	@Column(name ="IND_AUTORIZA_BASECOTPAGIMSS")
	private Integer indAutorizaBaseCotPagImss;
	
	@Column(name ="IND_AUTORIZA_DIFCOTPAGIMSS")
	private Integer indAutorizaDifCotPagImss;
	
	
	@Column(name ="IND_RAZONABLE")
	private Integer indRazonable;
	
	@Transient
	private String registroPatronal;
	
	
	public Long getCveRevCedRevision() {
		return cveRevCedRevision;
	}


	public void setCveRevCedRevision(Long cveRevCedRevision) {
		this.cveRevCedRevision = cveRevCedRevision;
	}


	public Integer getCveAnexoSolicitudCorrPat() {
		return cveAnexoSolicitudCorrPat;
	}


	public void setCveAnexoSolicitudCorrPat(Integer cveAnexoSolicitudCorrPat) {
		this.cveAnexoSolicitudCorrPat = cveAnexoSolicitudCorrPat;
	}


	public Long getCveEjercicio() {
		return cveEjercicio;
	}


	public void setCveEjercicio(Long cveEjercicio) {
		this.cveEjercicio = cveEjercicio;
	}


	public BigDecimal getImporteBaseCotPatron() {
		return importeBaseCotPatron;
	}


	public void setImporteBaseCotPatron(BigDecimal importeBaseCotPatron) {
		this.importeBaseCotPatron = importeBaseCotPatron;
	}


	public Long getCvePresentaCorr() {
		return cvePresentaCorr;
	}


	public void setCvePresentaCorr(Long cvePresentaCorr) {
		this.cvePresentaCorr = cvePresentaCorr;
	}


	public Date getFechaReg() {
		return fechaReg;
	}


	public void setFechaReg(Date fechaReg) {
		this.fechaReg = fechaReg;
	}


	public String getClaveUsuario() {
		return claveUsuario;
	}


	public void setClaveUsuario(String claveUsuario) {
		this.claveUsuario = claveUsuario;
	}


	public BigDecimal getImporteBaseCotPagImss() {
		return importeBaseCotPagImss;
	}


	public void setImporteBaseCotPagImss(BigDecimal importeBaseCotPagImss) {
		this.importeBaseCotPagImss = importeBaseCotPagImss;
	}


	public BigDecimal getImporteDIfBaseCotPagImss() {
		return importeDIfBaseCotPagImss;
	}


	public void setImporteDIfBaseCotPagImss(BigDecimal importeDIfBaseCotPagImss) {
		this.importeDIfBaseCotPagImss = importeDIfBaseCotPagImss;
	}


	public BigDecimal getImporteDifBaseCotPatron() {
		return importeDifBaseCotPatron;
	}


	public void setImporteDifBaseCotPatron(BigDecimal importeDifBaseCotPatron) {
		this.importeDifBaseCotPatron = importeDifBaseCotPatron;
	}


	public String getRegistroPatronal() {
		return registroPatronal;
	}


	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}


	public Integer getIndAutorizaBaseCotPagImss() {
		return indAutorizaBaseCotPagImss;
	}


	public void setIndAutorizaBaseCotPagImss(Integer indAutorizaBaseCotPagImss) {
		this.indAutorizaBaseCotPagImss = indAutorizaBaseCotPagImss;
	}


	public Integer getIndAutorizaDifCotPagImss() {
		return indAutorizaDifCotPagImss;
	}


	public void setIndAutorizaDifCotPagImss(Integer indAutorizaDifCotPagImss) {
		this.indAutorizaDifCotPagImss = indAutorizaDifCotPagImss;
	}


	public Integer getIndRazonable() {
		return indRazonable;
	}


	public void setIndRazonable(Integer indRazonable) {
		this.indRazonable = indRazonable;
	}
	
	
	

}
