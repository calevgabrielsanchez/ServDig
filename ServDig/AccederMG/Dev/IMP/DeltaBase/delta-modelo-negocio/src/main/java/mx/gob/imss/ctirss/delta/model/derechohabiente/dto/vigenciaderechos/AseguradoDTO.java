package mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.model.externo.AbstractResponseExterno;

public class AseguradoDTO  extends AbstractResponseExterno implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombreAsegurado;
	private String primerApellidoAsegurado;
	private String segundoApellidoAsegurado;
	private String nss;
	private String consultorio;
	private String turno;
	private String curp;
	private String rfc;
	private String domicilioAsegurado;
	private String registroPatronal;
	private String cveSexoAsegurado;
	private String sexoAsegurado;
	private String cveLugarNacimientoAsegurado;
	private String lugarNacimientoAsegurado;
	private String fechaNacimientoAsegurado;
	private String fechaDefuncionAsegurado;
	private String cveEntidad;
	private String situacion;
	private String fechaHoy;
	private String lugar;

	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombreBeneficiario;
	private String enfermedad;
	private String calidad;
	
	private String cadenaOriginal;
	private String selloDigital;
	private String secuenciaNotarial;
	private String numeroSerie;
	
	private String idee;
	private Integer idPersona;
	
	private String desEstadoCivil;
	private Integer cveIdEstadoCivil;
	
	
	

	public String getDesEstadoCivil() {
		return desEstadoCivil;
	}
	public void setDesEstadoCivil(String desEstadoCivil) {
		this.desEstadoCivil = desEstadoCivil;
	}
	public Integer getCveIdEstadoCivil() {
		return cveIdEstadoCivil;
	}
	public void setCveIdEstadoCivil(Integer cveIdEstadoCivil) {
		this.cveIdEstadoCivil = cveIdEstadoCivil;
	}
	/*
	 * Creado el 21/04/2014 Juan Osorio Alvarez
	 */
	private String agregadoMedico;
	private String servicioMedico;
	private String servicioIncapacidad;
	
	private String fecValidacionRenapo;
	
	public String getNombreAsegurado() {
		return nombreAsegurado;
	}
	public void setNombreAsegurado(String nombreAsegurado) {
		this.nombreAsegurado = nombreAsegurado;
	}
	public String getPrimerApellidoAsegurado() {
		return primerApellidoAsegurado;
	}
	public void setPrimerApellidoAsegurado(String primerApellidoAsegurado) {
		this.primerApellidoAsegurado = primerApellidoAsegurado;
	}
	public String getSegundoApellidoAsegurado() {
		return segundoApellidoAsegurado;
	}
	public void setSegundoApellidoAsegurado(String segundoApellidoAsegurado) {
		this.segundoApellidoAsegurado = segundoApellidoAsegurado;
	}
	public String getNss() {
		return nss;
	}
	public void setNss(String nss) {
		this.nss = nss;
	}
	public String getConsultorio() {
		return consultorio;
	}
	public void setConsultorio(String consultorio) {
		this.consultorio = consultorio;
	}
	public String getTurno() {
		return turno;
	}
	public void setTurno(String turno) {
		this.turno = turno;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getDomicilioAsegurado() {
		return domicilioAsegurado;
	}
	public void setDomicilioAsegurado(String domicilioAsegurado) {
		this.domicilioAsegurado = domicilioAsegurado;
	}
	public String getRegistroPatronal() {
		return registroPatronal;
	}
	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}
	public String getSexoAsegurado() {
		return sexoAsegurado;
	}
	public void setSexoAsegurado(String sexoAsegurado) {
		this.sexoAsegurado = sexoAsegurado;
	}
	public String getLugarNacimientoAsegurado() {
		return lugarNacimientoAsegurado;
	}
	public void setLugarNacimientoAsegurado(String lugarNacimientoAsegurado) {
		this.lugarNacimientoAsegurado = lugarNacimientoAsegurado;
	}
	public String getFechaNacimientoAsegurado() {
		return fechaNacimientoAsegurado;
	}
	public void setFechaNacimientoAsegurado(String fechaNacimientoAsegurado) {
		this.fechaNacimientoAsegurado = fechaNacimientoAsegurado;
	}
	public String getSituacion() {
		return situacion;
	}
	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}
	public String getFechaHoy() {
		return fechaHoy;
	}
	public void setFechaHoy(String fechaHoy) {
		this.fechaHoy = fechaHoy;
	}
	public String getLugar() {
		return lugar;
	}
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public String getNombreBeneficiario() {
		return nombreBeneficiario;
	}
	public void setNombreBeneficiario(String nombreBeneficiario) {
		this.nombreBeneficiario = nombreBeneficiario;
	}
	public String getEnfermedad() {
		return enfermedad;
	}
	public void setEnfermedad(String enfermedad) {
		this.enfermedad = enfermedad;
	}
	public String getCalidad() {
		return calidad;
	}
	public void setCalidad(String calidad) {
		this.calidad = calidad;
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
	public String getIdee() {
		return idee;
	}
	public void setIdee(String idee) {
		this.idee = idee;
	}
	public Integer getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}
	public String getAgregadoMedico() {
		return agregadoMedico;
	}
	public void setAgregadoMedico(String agregadoMedico) {
		this.agregadoMedico = agregadoMedico;
	}
	public String getServicioMedico() {
		return servicioMedico;
	}
	public void setServicioMedico(String servicioMedico) {
		this.servicioMedico = servicioMedico;
	}
	public String getServicioIncapacidad() {
		return servicioIncapacidad;
	}
	public void setServicioIncapacidad(String servicioIncapacidad) {
		this.servicioIncapacidad = servicioIncapacidad;
	}
	public String getCveLugarNacimientoAsegurado() {
		return cveLugarNacimientoAsegurado;
	}
	public void setCveLugarNacimientoAsegurado(String cveLugarNacimientoAsegurado) {
		this.cveLugarNacimientoAsegurado = cveLugarNacimientoAsegurado;
	}
	public String getCveSexoAsegurado() {
		return cveSexoAsegurado;
	}
	public void setCveSexoAsegurado(String cveSexoAsegurado) {
		this.cveSexoAsegurado = cveSexoAsegurado;
	}
	public String getFecValidacionRenapo() {
		return fecValidacionRenapo;
	}
	public void setFecValidacionRenapo(String fecValidacionRenapo) {
		this.fecValidacionRenapo = fecValidacionRenapo;
	}
	public String getFechaDefuncionAsegurado() {
		return fechaDefuncionAsegurado;
	}
	public void setFechaDefuncionAsegurado(String fechaDefuncionAsegurado) {
		this.fechaDefuncionAsegurado = fechaDefuncionAsegurado;
	}
	public String getCveEntidad() {
		return cveEntidad;
	}
	public void setCveEntidad(String cveEntidad) {
		this.cveEntidad = cveEntidad;
	}
	
	 
}
