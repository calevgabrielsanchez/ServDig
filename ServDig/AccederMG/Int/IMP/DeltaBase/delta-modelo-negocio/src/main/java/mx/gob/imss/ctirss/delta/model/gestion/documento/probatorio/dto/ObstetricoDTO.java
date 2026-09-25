package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto;

public class ObstetricoDTO {

	
	private String fechaCertificacionMedico;
	private String fechaParto;
	private String medicoFamiliar;
	private String fechaConcepcion;
	private String fechaExpedicion;
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
	/**
	 * @return the fechaCertificacionMedico
	 */
	public String getFechaCertificacionMedico() {
		return fechaCertificacionMedico;
	}
	/**
	 * @param fechaCertificacionMedico the fechaCertificacionMedico to set
	 */
	public void setFechaCertificacionMedico(String fechaCertificacionMedico) {
		this.fechaCertificacionMedico = fechaCertificacionMedico;
	}
	/**
	 * @return the fechaParto
	 */
	public String getFechaParto() {
		return fechaParto;
	}
	/**
	 * @param fechaParto the fechaParto to set
	 */
	public void setFechaParto(String fechaParto) {
		this.fechaParto = fechaParto;
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
	public String getFechaConcepcion() {
		return fechaConcepcion;
	}
	public void setFechaConcepcion(String fechaConcepcion) {
		this.fechaConcepcion = fechaConcepcion;
	}
	public String getFechaExpedicion() {
		return fechaExpedicion;
	}
	public void setFechaExpedicion(String fechaExpedicion) {
		this.fechaExpedicion = fechaExpedicion;
	}
}
