/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos.BeneficiarioDTO;

/**
 * @author ghdolores
 * 
 */
public class Reporte4305A implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3121954310784829022L;
	private String apellidoPaternoAsegurado;
	private String apellidoMaternoAsegurado;
	private String nombreAsegurado;
	private String nss;
	private String domicilio;
	private String consultorioTurno;
	private String curpAsegurado;
	private String calidad;
	private String nombreBeneficiario;
	private String sexoBeneficiario;
	private String mesAsegurado;
	private String anioBeneficiario;
	private String expedienteBeneficiario;
	private String observaciones;
	private List<BeneficiarioDTO> beneficiarios;
	private Date fechaExpedicion;
	
	private String cadenaOriginal;
	private String selloDigital;
	private String secuenciaNotarial;
	private String numeroSerie;

	public String getApellidoPaternoAsegurado() {
		return apellidoPaternoAsegurado;
	}

	public void setApellidoPaternoAsegurado(String apellidoPaternoAsegurado) {
		this.apellidoPaternoAsegurado = apellidoPaternoAsegurado;
	}

	public String getApellidoMaternoAsegurado() {
		return apellidoMaternoAsegurado;
	}

	public void setApellidoMaternoAsegurado(String apellidoMaternoAsegurado) {
		this.apellidoMaternoAsegurado = apellidoMaternoAsegurado;
	}

	public String getNombreAsegurado() {
		return nombreAsegurado;
	}

	public void setNombreAsegurado(String nombreAsegurado) {
		this.nombreAsegurado = nombreAsegurado;
	}

	public String getNss() {
		return nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getConsultorioTurno() {
		return consultorioTurno;
	}

	public void setConsultorioTurno(String consultorioTurno) {
		this.consultorioTurno = consultorioTurno;
	}

	public String getCurpAsegurado() {
		return curpAsegurado;
	}

	public void setCurpAsegurado(String curpAsegurado) {
		this.curpAsegurado = curpAsegurado;
	}

	public String getCalidad() {
		return calidad;
	}

	public void setCalidad(String calidad) {
		this.calidad = calidad;
	}

	public String getNombreBeneficiario() {
		return nombreBeneficiario;
	}

	public void setNombreBeneficiario(String nombreBeneficiario) {
		this.nombreBeneficiario = nombreBeneficiario;
	}

	public String getSexoBeneficiario() {
		return sexoBeneficiario;
	}

	public void setSexoBeneficiario(String sexoBeneficiario) {
		this.sexoBeneficiario = sexoBeneficiario;
	}

	public String getAnioBeneficiario() {
		return anioBeneficiario;
	}

	public void setAnioBeneficiario(String anioBeneficiario) {
		this.anioBeneficiario = anioBeneficiario;
	}

	public String getExpedienteBeneficiario() {
		return expedienteBeneficiario;
	}

	public void setExpedienteBeneficiario(String expedienteBeneficiario) {
		this.expedienteBeneficiario = expedienteBeneficiario;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getMesAsegurado() {
		return mesAsegurado;
	}

	public void setMesAsegurado(String mesAsegurado) {
		this.mesAsegurado = mesAsegurado;
	}

	public List<BeneficiarioDTO> getBeneficiarios() {
		return beneficiarios;
	}

	public Date getFechaExpedicion() {
		return fechaExpedicion;
	}

	public void setFechaExpedicion(Date fechaExpedicion) {
		this.fechaExpedicion = fechaExpedicion;
	}

	public void setBeneficiarios(List<BeneficiarioDTO> beneficiarios) {
		this.beneficiarios = beneficiarios;
	}

	public String getCadenaOriginal() {
		return cadenaOriginal;
	}

	public void setCadenaOriginal(String cadenaOriginal) {
		this.cadenaOriginal = cadenaOriginal;
	}

	public String getSelloDigital() {
		return selloDigital;
	}

	public void setSelloDigital(String selloDigital) {
		this.selloDigital = selloDigital;
	}

	public String getSecuenciaNotarial() {
		return secuenciaNotarial;
	}

	public void setSecuenciaNotarial(String secuenciaNotarial) {
		this.secuenciaNotarial = secuenciaNotarial;
	}

	public String getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	
}
