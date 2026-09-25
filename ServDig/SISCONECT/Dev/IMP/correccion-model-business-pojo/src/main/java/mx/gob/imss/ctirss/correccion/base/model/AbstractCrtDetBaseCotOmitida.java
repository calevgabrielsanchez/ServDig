package mx.gob.imss.ctirss.correccion.base.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

/**
 * The persistent class for the CRT_DETBASECOT_OMITIDA database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractCrtDetBaseCotOmitida extends AbstractModel{

	private static final long serialVersionUID = 10012L;
	
	private BigDecimal cveDetBaseCotOmit;
	private BigDecimal cveAnexoSolCorrPat;
	private BigDecimal cveEjercicio;
	private BigDecimal impSueldoBalanzaComp;
	private BigDecimal impSueldoDelAnualISR;
	private BigDecimal impVarMasSextoBim;
	private BigDecimal impVarMenosSextoBim;
	private Date fecFechaRegistro;
	private String cveUsuario;
	

	@Id
	@SequenceGenerator(name="CVE_DETBASECOTOMITIDA_GENERATOR", sequenceName="CRS_CVE_CORRPROMINVITA")
	@GeneratedValue( generator="CVE_DETBASECOTOMITIDA_GENERATOR")
	@Column(name="CVE_DETBASECOTOMIT")
	public BigDecimal getCveDetBaseCotOmit() {
		return cveDetBaseCotOmit;
	}
	
	public void setCveDetBaseCotOmit(BigDecimal cveDetBaseCotOmit) {
		this.cveDetBaseCotOmit = cveDetBaseCotOmit;
	}
	
	@Column(name = "CVE_ANEXOSOLCORRPAT")
	public BigDecimal getCveAnexoSolCorrPat() {
		return cveAnexoSolCorrPat;
	}

	public void setCveAnexoSolCorrPat(BigDecimal cveAnexoSolCorrPat) {
		this.cveAnexoSolCorrPat = cveAnexoSolCorrPat;
	}
	
	@Column(name = "CVE_EJERCICIO")
	public BigDecimal getCveEjercicio() {
		return cveEjercicio;
	}

	public void setCveEjercicio(BigDecimal cveEjercicio) {
		this.cveEjercicio = cveEjercicio;
	}

	@Column(name = "IMP_SUELDOBALANZACOMP")
	public BigDecimal getImpSueldoBalanzaComp() {
		return impSueldoBalanzaComp;
	}
	
	public void setImpSueldoBalanzaComp(BigDecimal impSueldoBalanzaComp) {
		this.impSueldoBalanzaComp = impSueldoBalanzaComp;
	}
	
	@Column(name = "IMP_SUELDODELANUALISR")
	public BigDecimal getImpSueldoDelAnualISR() {
		return impSueldoDelAnualISR;
	}
	
	public void setImpSueldoDelAnualISR(BigDecimal impSueldoDelAnualISR) {
		this.impSueldoDelAnualISR = impSueldoDelAnualISR;
	}
	
	@Column(name = "IMP_VARMASSEXTOBIM")
	public BigDecimal getImpVarMasSextoBim() {
		return impVarMasSextoBim;
	}
	
	public void setImpVarMasSextoBim(BigDecimal impVarMasSextoBim) {
		this.impVarMasSextoBim = impVarMasSextoBim;
	}
	
	@Column(name = "IMP_VARMENOSSEXTOBIM")
	public BigDecimal getImpVarMenosSextoBim() {
		return impVarMenosSextoBim;
	}
	
	public void setImpVarMenosSextoBim(BigDecimal impVarMenosSextoBim) {
		this.impVarMenosSextoBim = impVarMenosSextoBim;
	}
    @Temporal( TemporalType.TIMESTAMP)
	@Column(name = "FEC_FECHAREGISTRO")
	public Date getFecFechaRegistro() {
		return fecFechaRegistro;
	}

	public void setFecFechaRegistro(Date fecFechaRegistro) {
		this.fecFechaRegistro = fecFechaRegistro;
	}

	@Column(name = "CVE_USUARIO")
	public String getCveUsuario() {
		return cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}
	
	

	
}
