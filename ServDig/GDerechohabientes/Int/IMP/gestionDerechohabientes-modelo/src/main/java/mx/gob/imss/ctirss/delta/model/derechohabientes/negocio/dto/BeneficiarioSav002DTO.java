package mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto;

import java.io.Serializable;

public class BeneficiarioSav002DTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private String agregadoMedico;
	private String mesNacimiento;
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getAgregadoMedico() {
		return agregadoMedico;
	}
	public void setAgregadoMedico(String agregadoMedico) {
		this.agregadoMedico = agregadoMedico;
	}
	
	public String getMesNacimiento() {
		return mesNacimiento;
	}
	public void setMesNacimiento(String mesNacimiento) {
		this.mesNacimiento = mesNacimiento;
	}
	
	
	public BeneficiarioSav002DTO() {
	}
	
	public BeneficiarioSav002DTO(String nombre, String agregadoMedico, String mesNacimiento) {
		super();
		this.nombre = nombre;
		this.agregadoMedico = agregadoMedico;
		this.mesNacimiento = mesNacimiento;
	}
	
}
