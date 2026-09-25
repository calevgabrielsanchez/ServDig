package mx.gob.imss.ctirss.delta.derechohabientes.web.bean.dto;

public class CertificadoSitCriticaDTO {

	
	private String enfermedadPadecida;
	private String fechaTerminoIncapacidad;
	private  String fechaProbableInicio;
	private String fechaExpedicionString;
	private String medicoFamiliar;
	private String nombreMed;
	private String primerApeidoMed;
	private String segundoApeidoMed;
	private String matriculaMed;
	private String idMedicoEspecialidad;
	
	
	
	public String getIdMedicoEspecialidad() {
		return idMedicoEspecialidad;
	}
	public void setIdMedicoEspecialidad(String idMedicoEspecialidad) {
		this.idMedicoEspecialidad = idMedicoEspecialidad;
	}
	
	
	public String getNombreMed() {
		return nombreMed;
	}
	public void setNombreMed(String nombreMed) {
		this.nombreMed = nombreMed;
	}
	public String getPrimerApeidoMed() {
		return primerApeidoMed;
	}
	public void setPrimerApeidoMed(String primerApeidoMed) {
		this.primerApeidoMed = primerApeidoMed;
	}
	public String getSegundoApeidoMed() {
		return segundoApeidoMed;
	}
	public void setSegundoApeidoMed(String segundoApeidoMed) {
		this.segundoApeidoMed = segundoApeidoMed;
	}
	public String getMatriculaMed() {
		return matriculaMed;
	}
	public void setMatriculaMed(String matriculaMed) {
		this.matriculaMed = matriculaMed;
	}
	public String getFechaProbableInicio() {
		return fechaProbableInicio;
	}
	public void setFechaProbableInicio(String fechaProbableInicio) {
		this.fechaProbableInicio = fechaProbableInicio;
	}
	public String getFechaExpedicionString() {
		return fechaExpedicionString;
	}
	public void setFechaExpedicionString(String fechaExpedicionString) {
		this.fechaExpedicionString = fechaExpedicionString;
	}
	/**
	 * @return the enfermedadPadecida
	 */
	public String getEnfermedadPadecida() {
		return enfermedadPadecida;
	}
	/**
	 * @param enfermedadPadecida the enfermedadPadecida to set
	 */
	public void setEnfermedadPadecida(String enfermedadPadecida) {
		this.enfermedadPadecida = enfermedadPadecida;
	}
	/**
	 * @return the fechaTerminoIncapacidad
	 */
	public String getFechaTerminoIncapacidad() {
		return fechaTerminoIncapacidad;
	}
	/**
	 * @param fechaTerminoIncapacidad the fechaTerminoIncapacidad to set
	 */
	public void setFechaTerminoIncapacidad(String fechaTerminoIncapacidad) {
		this.fechaTerminoIncapacidad = fechaTerminoIncapacidad;
	}
	/**
	 * @return the medicoFamiliar
	 */
	public String getMedicoFamiliar() {
		return medicoFamiliar;
	}
	/**
	 * @param medicoFamiliar the medicoFamiliar to set
	 */
	public void setMedicoFamiliar(String medicoFamiliar) {
		this.medicoFamiliar = medicoFamiliar;
	}
				
}
