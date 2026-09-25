package mx.gob.imss.ctirss.delta.derechohabientes.web.bean.dto;

import java.io.Serializable;

public class CertificadoNacimientoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String noFolio;
	private String fechaAlumbramiento;
	private String idSexo;
	private String fechaExpedicion;
	
	 private String desLugarAlumbramiento;
	
	
	
	public String getDesLugarAlumbramiento() {
		return desLugarAlumbramiento;
	}
	public void setDesLugarAlumbramiento(String desLugarAlumbramiento) {
		this.desLugarAlumbramiento = desLugarAlumbramiento;
	}
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
		
	
}
