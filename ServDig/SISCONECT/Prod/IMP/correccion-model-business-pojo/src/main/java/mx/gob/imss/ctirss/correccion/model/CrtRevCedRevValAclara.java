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

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

/**
 * Model para la tabla CRT_REVCEDREVISION
 * @author Jorge Hernandez Almazan
 * @since 07/08/2012
 */
@Entity
@Table(name="CRT_REVCEDREVVAL_ACLARA")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrtRevCedRevValAclara extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="CVE_REVCEDREVAL", sequenceName="SEQ_CVE_REVCEDREVAL")
	@GeneratedValue(generator="CVE_REVCEDREVAL")
	@Column(name="CVE_REVCEDREVAL")
	public Long cveRevCedRevAl;
	
	
	
	@Column(name="CVE_ANEXOSOLCORRPAT")
	private Integer cveAnexoSolicitudCorrPat;
	
	
	@Column(name = "CVE_EJERCICIO")
	private Long cveEjercicio;
	
	
	@Column(name = "CVE_PRESENTACORR")
	private Long cvePresentaCorr;
	
	@Column(name="CVE_PERCEPCION")	
	private Integer cvePercepcion;
	
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHAREG")
	private Date fechaReg;       
	    
	
	@Column(name="CVE_USUARIO")
	private String claveUsuario;
	
	
	@Column(name = "IMP_TOTALPERCEPCION")
	private BigDecimal impTotalPercepcion;
	
	
	@Column(name = "IMP_REV_ACLARADO")
	private BigDecimal impRevAclarado;
	
	
	@Column(name = "IMP_REV_PORACLARAR")
	private BigDecimal impRevPorAclarar;
	
	
	@Column(name = "IND_AUTORIZA_REVPORACLARAR")
	private Integer indAutorizaRevPorAclarar;
	
	
	@Column(name = "IMP_VAL_ACLARADO")
	private BigDecimal importeValAclarado;
	
	
	@Column(name = "IND_AUTORIZA_VALACLARA")
	private Integer indAutorizaValAclara;
	
	
	
	@Column(name = "IMP_VAL_ACLARADOOFRESUL")
	private BigDecimal impValAclaradoOfResul;
	
	
	
	@Column(name = "IND_AUTORIZA_ACLARAOFRESUL")
	private Integer indAutorizaAclaraOfResul;
	
	
	
	@Column(name = "IMP_VAL_TOTPAGADO")
	private BigDecimal impValTotPagado;
	
	
	
	@Column(name = "IND_AUTORIZA_TOTPAGADO")
	private Integer indAutorizaTotPagado;



	public Long getCveRevCedRevAl() {
		return cveRevCedRevAl;
	}



	public void setCveRevCedRevAl(Long cveRevCedRevAl) {
		this.cveRevCedRevAl = cveRevCedRevAl;
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



	public Long getCvePresentaCorr() {
		return cvePresentaCorr;
	}



	public void setCvePresentaCorr(Long cvePresentaCorr) {
		this.cvePresentaCorr = cvePresentaCorr;
	}



	public Integer getCvePercepcion() {
		return cvePercepcion;
	}



	public void setCvePercepcion(Integer cvePercepcion) {
		this.cvePercepcion = cvePercepcion;
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



	public BigDecimal getImpTotalPercepcion() {
		return impTotalPercepcion;
	}



	public void setImpTotalPercepcion(BigDecimal impTotalPercepcion) {
		this.impTotalPercepcion = impTotalPercepcion;
	}



	public BigDecimal getImpRevAclarado() {
		return impRevAclarado;
	}



	public void setImpRevAclarado(BigDecimal impRevAclarado) {
		this.impRevAclarado = impRevAclarado;
	}



	public BigDecimal getImpRevPorAclarar() {
		return impRevPorAclarar;
	}



	public void setImpRevPorAclarar(BigDecimal impRevPorAclarar) {
		this.impRevPorAclarar = impRevPorAclarar;
	}



	public Integer getIndAutorizaRevPorAclarar() {
		return indAutorizaRevPorAclarar;
	}



	public void setIndAutorizaRevPorAclarar(Integer indAutorizaRevPorAclarar) {
		this.indAutorizaRevPorAclarar = indAutorizaRevPorAclarar;
	}



	public BigDecimal getImporteValAclarado() {
		return importeValAclarado;
	}



	public void setImporteValAclarado(BigDecimal importeValAclarado) {
		this.importeValAclarado = importeValAclarado;
	}



	public Integer getIndAutorizaValAclara() {
		return indAutorizaValAclara;
	}



	public void setIndAutorizaValAclara(Integer indAutorizaValAclara) {
		this.indAutorizaValAclara = indAutorizaValAclara;
	}



	public BigDecimal getImpValAclaradoOfResul() {
		return impValAclaradoOfResul;
	}



	public void setImpValAclaradoOfResul(BigDecimal impValAclaradoOfResul) {
		this.impValAclaradoOfResul = impValAclaradoOfResul;
	}



	public Integer getIndAutorizaAclaraOfResul() {
		return indAutorizaAclaraOfResul;
	}



	public void setIndAutorizaAclaraOfResul(Integer indAutorizaAclaraOfResul) {
		this.indAutorizaAclaraOfResul = indAutorizaAclaraOfResul;
	}



	public BigDecimal getImpValTotPagado() {
		return impValTotPagado;
	}



	public void setImpValTotPagado(BigDecimal impValTotPagado) {
		this.impValTotPagado = impValTotPagado;
	}



	public Integer getIndAutorizaTotPagado() {
		return indAutorizaTotPagado;
	}



	public void setIndAutorizaTotPagado(Integer indAutorizaTotPagado) {
		this.indAutorizaTotPagado = indAutorizaTotPagado;
	}
	
	
	

	
	
	
	
	
	

}
