package mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto;

import java.io.Serializable;


public class BeneficiarioSav005DTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nombreBeneficiario;
	private String curpBeneficiario;
	private String agregado;
	private String dVerificador;
	private String mesNacimiento;
	
	
	public String getNombreBeneficiario() {
		return nombreBeneficiario;
	}
	public void setNombreBeneficiario(String nombreBeneficiario) {
		this.nombreBeneficiario = nombreBeneficiario;
	}
	public String getCurpBeneficiario() {
		return curpBeneficiario;
	}
	public void setCurpBeneficiario(String curpBeneficiario) {
		this.curpBeneficiario = curpBeneficiario;
	}
	public String getAgregado() {
		return agregado;
	}
	public void setAgregado(String agregado) {
		this.agregado = agregado;
	}
	public String getdVerificador() {
		return dVerificador;
	}
	public void setdVerificador(String dVerificador) {
		this.dVerificador = dVerificador;
	}
	public String getMesNacimiento() {
		return mesNacimiento;
	}
	public void setMesNacimiento(String mesNacimiento) {
		this.mesNacimiento = mesNacimiento;
	}
	
	
	

}
