package mx.gob.imss.ctirss.correccion.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@Entity
@Table(name="CRT_COPPAGADASANUAL")
public class CrtCopPagadasAnual extends AbstractModel{

	private Long cveAnexoSolCorrPat;
	private Long cveEjercicio;
	private Double imTotCuotaFija;
	private Double imTotCuotaExec3SMGDF;
	private Double imTotCuotaPrestDinero;
	private Double imTotCuotaGtosMedPen;
	private Double imTotCuotaRiesgoTrabajo;
	private Double imTotCuotaInvalidezVia;
	private Double imTotCuotaGuardPrest;
	private Double imTotCuotaRCVRetiro;
	private Double imTotCuotaRVCCesantia;
	private Double nuFactor;
	private Double imBase;
	private Date fecFechareg;
	private String cveUsuario;
	
	@Id
	@Column(name="CVE_ANEXOSOLCORRPAT")
	public Long getCveAnexoSolCorrPat() {
		return cveAnexoSolCorrPat;
	}
	
	public void setCveAnexoSolCorrPat(Long cveAnexoSolCorrPat) {
		this.cveAnexoSolCorrPat = cveAnexoSolCorrPat;
	}
	
	@Id
	@Column(name="CVE_EJERCICIO")
	public Long getCveEjercicio() {
		return cveEjercicio;
	}
	
	public void setCveEjercicio(Long cveEjercicio) {
		this.cveEjercicio = cveEjercicio;
	}
	
	@Column(name="IM_TOT_CUOTA_FIJA")
	public Double getImTotCuotaFija() {
		return imTotCuotaFija;
	}
	
	public void setImTotCuotaFija(Double imTotCuotaFija) {
		this.imTotCuotaFija = imTotCuotaFija;
	}
	
	@Column(name="IM_TOT_CUOTA_EXCED_3_SMGDF")
	public Double getImTotCuotaExec3SMGDF() {
		return imTotCuotaExec3SMGDF;
	}
	
	public void setImTotCuotaExec3SMGDF(Double imTotCuotaExec3SMGDF) {
		this.imTotCuotaExec3SMGDF = imTotCuotaExec3SMGDF;
	}
	
	@Column(name="IM_TOT_CUOTA_PREST_DINERO")
	public Double getImTotCuotaPrestDinero() {
		return imTotCuotaPrestDinero;
	}
	
	public void setImTotCuotaPrestDinero(Double imTotCuotaPrestDinero) {
		this.imTotCuotaPrestDinero = imTotCuotaPrestDinero;
	}
	
	@Column(name="IM_TOT_CUOTA_GTOS_MED_PEN")
	public Double getImTotCuotaGtosMedPen() {
		return imTotCuotaGtosMedPen;
	}
	
	public void setImTotCuotaGtosMedPen(Double imTotCuotaGtosMedPen) {
		this.imTotCuotaGtosMedPen = imTotCuotaGtosMedPen;
	}
	
	@Column(name="IM_TOT_CUOTA_RIESGO_TRABAJO")
	public Double getImTotCuotaRiesgoTrabajo() {
		return imTotCuotaRiesgoTrabajo;
	}
	
	public void setImTotCuotaRiesgoTrabajo(Double imTotCuotaRiesgoTrabajo) {
		this.imTotCuotaRiesgoTrabajo = imTotCuotaRiesgoTrabajo;
	}
	
	@Column(name="IM_TOT_CUOTA_INVALIDEZ_VIDA")
	public Double getImTotCuotaInvalidezVia() {
		return imTotCuotaInvalidezVia;
	}
	
	public void setImTotCuotaInvalidezVia(Double imTotCuotaInvalidezVia) {
		this.imTotCuotaInvalidezVia = imTotCuotaInvalidezVia;
	}
	
	@Column(name="IM_TOT_CUOTA_GUARD_PREST")
	public Double getImTotCuotaGuardPrest() {
		return imTotCuotaGuardPrest;
	}
	
	public void setImTotCuotaGuardPrest(Double imTotCuotaGuardPrest) {
		this.imTotCuotaGuardPrest = imTotCuotaGuardPrest;
	}
	
	@Column(name="IM_TOT_CUOTA_RCV_RETIRO")
	public Double getImTotCuotaRCVRetiro() {
		return imTotCuotaRCVRetiro;
	}
	
	public void setImTotCuotaRCVRetiro(Double imTotCuotaRCVRetiro) {
		this.imTotCuotaRCVRetiro = imTotCuotaRCVRetiro;
	}
	
	@Column(name="IM_TOT_CUOTA_RCV_CESANTIA")
	public Double getImTotCuotaRVCCesantia() {
		return imTotCuotaRVCCesantia;
	}
	
	public void setImTotCuotaRVCCesantia(Double imTotCuotaRVCCesantia) {
		this.imTotCuotaRVCCesantia = imTotCuotaRVCCesantia;
	}
	
	@Column(name="NU_FACTOR")
	public Double getNuFactor() {
		return nuFactor;
	}
	
	public void setNuFactor(Double nuFactor) {
		this.nuFactor = nuFactor;
	}
	
	@Column(name="IM_BASE")
	public Double getImBase() {
		return imBase;
	}
	
	public void setImBase(Double imBase) {
		this.imBase = imBase;
	}
	
	@Column(name="FEC_FECHAREG")
	public Date getFecFechareg() {
		return fecFechareg;
	}
	
	public void setFecFechareg(Date fecFechareg) {
		this.fecFechareg = fecFechareg;
	}
	
	@Column(name="CVE_USUARIO")
	public String getCveUsuario() {
		return cveUsuario;
	}
	
	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}
	
	@Transient
	private Double sumaImpTotGuadPrest;

	@Transient
	public Double getSumaImpTotGuadPrest() {
		return sumaImpTotGuadPrest;
	}

	public void setSumaImpTotGuadPrest(Double sumaImpTotGuadPrest) {
		this.sumaImpTotGuadPrest = sumaImpTotGuadPrest;
	}
	
	
}
