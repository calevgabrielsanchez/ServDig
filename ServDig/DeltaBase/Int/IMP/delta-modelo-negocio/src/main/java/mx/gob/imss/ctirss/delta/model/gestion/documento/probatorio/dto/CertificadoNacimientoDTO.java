package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto;

public class CertificadoNacimientoDTO {

	private String noFolio;
	private String fechaAlumbramiento;
	private String idSexo;
	private String descripcionSexo;
	private String desLugarAlumbramiento;
	private String fechaExpedicion;
	
	
	
	public String getFechaExpedicion() {
		return fechaExpedicion;
	}
	public void setFechaExpedicion(String fechaExpedicion) {
		this.fechaExpedicion = fechaExpedicion;
	}
	/**
	 * @return the noFolio
	 */
	public String getNoFolio() {
		return noFolio;
	}
	/**
	 * @param noFolio the noFolio to set
	 */
	public void setNoFolio(String noFolio) {
		this.noFolio = noFolio;
	}
	/**
	 * @return the fechaAlumbramiento
	 */
	public String getFechaAlumbramiento() {
		return fechaAlumbramiento;
	}
	/**
	 * @param fechaAlumbramiento the fechaAlumbramiento to set
	 */
	public void setFechaAlumbramiento(String fechaAlumbramiento) {
		this.fechaAlumbramiento = fechaAlumbramiento;
	}
	/**
	 * @return the idSexo
	 */
	public String getIdSexo() {
		return idSexo;
	}
	/**
	 * @param idSexo the idSexo to set
	 */
	public void setIdSexo(String idSexo) {
		this.idSexo = idSexo;
	}
	/**
	 * @return the desLugarAlumbramiento
	 */
	public String getDesLugarAlumbramiento() {
		return desLugarAlumbramiento;
	}
	/**
	 * @param value the desLugarAlumbramiento to set
	 */
	public void setDesLugarAlumbramiento(String value) {
		this.desLugarAlumbramiento = value;
	}
	
	public String getDescripcionSexo() {
		return descripcionSexo;
	}
	public void setDescripcionSexo(String descripcionSexo) {
		this.descripcionSexo = descripcionSexo;
	}
	
	
}
