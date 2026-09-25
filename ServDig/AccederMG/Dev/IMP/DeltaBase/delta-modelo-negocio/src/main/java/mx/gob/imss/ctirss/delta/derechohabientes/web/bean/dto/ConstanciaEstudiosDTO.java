package mx.gob.imss.ctirss.delta.derechohabientes.web.bean.dto;

import java.io.Serializable;

public class ConstanciaEstudiosDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4832850113198888490L;
	private String nombreEscuela;
	private String claveEscuela;
	private String noIncorporacion;
	private String gradoEscolar;
	private String fechaInicioPeriodo;
	private String fechaFinPeriodo;
	private String idTipoNivelEducativo;
	private String idNivelEducativo; 
	private String fechaExpedicionString;
	
	
	
	public String getFechaExpedicionString() {
		return fechaExpedicionString;
	}
	public void setFechaExpedicionString(String fechaExpedicionString) {
		this.fechaExpedicionString = fechaExpedicionString;
	}
	public String getIdTipoNivelEducativo() {
		return idTipoNivelEducativo;
	}
	public void setIdTipoNivelEducativo(String idTipoNivelEducativo) {
		this.idTipoNivelEducativo = idTipoNivelEducativo;
	}
	public String getIdNivelEducativo() {
		return idNivelEducativo;
	}
	public void setIdNivelEducativo(String idNivelEducativo) {
		this.idNivelEducativo = idNivelEducativo;
	}
	/**
	 * @return the nombreEscuela
	 */
	public String getNombreEscuela() {
		return nombreEscuela;
	}
	/**
	 * @param nombreEscuela the nombreEscuela to set
	 */
	public void setNombreEscuela(String nombreEscuela) {
		this.nombreEscuela = nombreEscuela;
	}
	/**
	 * @return the claveEscuela
	 */
	public String getClaveEscuela() {
		return claveEscuela;
	}
	/**
	 * @param claveEscuela the claveEscuela to set
	 */
	public void setClaveEscuela(String claveEscuela) {
		this.claveEscuela = claveEscuela;
	}
	/**
	 * @return the noIncorporacion
	 */
	public String getNoIncorporacion() {
		return noIncorporacion;
	}
	/**
	 * @param noIncorporacion the noIncorporacion to set
	 */
	public void setNoIncorporacion(String noIncorporacion) {
		this.noIncorporacion = noIncorporacion;
	}
	/**
	 * @return the gradoEscolar
	 */
	public String getGradoEscolar() {
		return gradoEscolar;
	}
	/**
	 * @param gradoEscolar the gradoEscolar to set
	 */
	public void setGradoEscolar(String gradoEscolar) {
		this.gradoEscolar = gradoEscolar;
	}
	/**
	 * @return the fechaInicioPeriodo
	 */
	public String getFechaInicioPeriodo() {
		return fechaInicioPeriodo;
	}
	/**
	 * @param fechaInicioPeriodo the fechaInicioPeriodo to set
	 */
	public void setFechaInicioPeriodo(String fechaInicioPeriodo) {
		this.fechaInicioPeriodo = fechaInicioPeriodo;
	}
	/**
	 * @return the fechaFinPeriodo
	 */
	public String getFechaFinPeriodo() {
		return fechaFinPeriodo;
	}
	/**
	 * @param fechaFinPeriodo the fechaFinPeriodo to set
	 */
	public void setFechaFinPeriodo(String fechaFinPeriodo) {
		this.fechaFinPeriodo = fechaFinPeriodo;
	}
	
	
	
	
}
