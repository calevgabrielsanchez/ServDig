package mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto;

import java.io.Serializable;

public class Sav001DTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nombreCompleto;
	private String agregadoIdentidad;
	private String mesNacimiento;
	private String vencimiento;
	private String autorizado;
	private String consultorio;
	/**
	 * @return the nombreCompleto
	 */
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	/**
	 * @param nombreCompleto the nombreCompleto to set
	 */
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	/**
	 * @return the agregadoIdentidad
	 */
	public String getAgregadoIdentidad() {
		return agregadoIdentidad;
	}
	/**
	 * @param agregadoIdentidad the agregadoIdentidad to set
	 */
	public void setAgregadoIdentidad(String agregadoIdentidad) {
		this.agregadoIdentidad = agregadoIdentidad;
	}
	/**
	 * @return the mesNacimiento
	 */
	public String getMesNacimiento() {
		return mesNacimiento;
	}
	/**
	 * @param mesNacimiento the mesNacimiento to set
	 */
	public void setMesNacimiento(String mesNacimiento) {
		this.mesNacimiento = mesNacimiento;
	}
	/**
	 * @return the vencimiento
	 */
	public String getVencimiento() {
		return vencimiento;
	}
	/**
	 * @param vencimiento the vencimiento to set
	 */
	public void setVencimiento(String vencimiento) {
		this.vencimiento = vencimiento;
	}
	/**
	 * @return the autorizado
	 */
	public String getAutorizado() {
		return autorizado;
	}
	/**
	 * @param autorizado the autorizado to set
	 */
	public void setAutorizado(String autorizado) {
		this.autorizado = autorizado;
	}
	/**
	 * @return the consultorio
	 */
	public String getConsultorio() {
		return consultorio;
	}
	/**
	 * @param consultorio the consultorio to set
	 */
	public void setConsultorio(String consultorio) {
		this.consultorio = consultorio;
	}
	
	

}
