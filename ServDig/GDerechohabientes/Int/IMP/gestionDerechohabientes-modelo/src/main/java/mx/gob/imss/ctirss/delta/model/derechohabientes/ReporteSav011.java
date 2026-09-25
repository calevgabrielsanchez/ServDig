package mx.gob.imss.ctirss.delta.model.derechohabientes; 

import java.io.Serializable;
import java.util.Date;

public class ReporteSav011 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5219674938614615067L;
	private Date fechaExpedicion;
	private String nssAsegurado;
	private String nombreAsegurado;
	private String curpAsegurado;
	private String embarazo;
	private String incapacidadFisica;
	private String derechohabiente;
	private String calidadAsegurado;
	private String calidadEsposa;
	private String calidadConcubina;
	private String calidadPadres;
	private String calidadHijos;
	
	public ReporteSav011() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ReporteSav011(Date fechaExpedicion, String nssAsegurado,
			String nombreAsegurado, String curpAsegurado, String embarazo,
			String incapacidadFisica, String derechohabiente,
			String calidadAsegurado, String calidadEsposa,
			String calidadConcubina, String calidadPadres, String calidadHijos) {
		super();
		this.fechaExpedicion = fechaExpedicion;
		this.nssAsegurado = nssAsegurado;
		this.nombreAsegurado = nombreAsegurado;
		this.curpAsegurado = curpAsegurado;
		this.embarazo = embarazo;
		this.incapacidadFisica = incapacidadFisica;
		this.derechohabiente = derechohabiente;
		this.calidadAsegurado = calidadAsegurado;
		this.calidadEsposa = calidadEsposa;
		this.calidadConcubina = calidadConcubina;
		this.calidadPadres = calidadPadres;
		this.calidadHijos = calidadHijos;
	}

	public Date getFechaExpedicion() {
		return fechaExpedicion;
	}

	public void setFechaExpedicion(Date fechaExpedicion) {
		this.fechaExpedicion = fechaExpedicion;
	}

	public String getNssAsegurado() {
		return nssAsegurado;
	}

	public void setNssAsegurado(String nssAsegurado) {
		this.nssAsegurado = nssAsegurado;
	}

	public String getNombreAsegurado() {
		return nombreAsegurado;
	}

	public void setNombreAsegurado(String nombreAsegurado) {
		this.nombreAsegurado = nombreAsegurado;
	}

	public String getCurpAsegurado() {
		return curpAsegurado;
	}

	public void setCurpAsegurado(String curpAsegurado) {
		this.curpAsegurado = curpAsegurado;
	}

	public String getEmbarazo() {
		return embarazo;
	}

	public void setEmbarazo(String embarazo) {
		this.embarazo = embarazo;
	}

	public String getIncapacidadFisica() {
		return incapacidadFisica;
	}

	public void setIncapacidadFisica(String incapacidadFisica) {
		this.incapacidadFisica = incapacidadFisica;
	}

	public String getDerechohabiente() {
		return derechohabiente;
	}

	public void setDerechohabiente(String derechohabiente) {
		this.derechohabiente = derechohabiente;
	}

	public String getCalidadAsegurado() {
		return calidadAsegurado;
	}

	public void setCalidadAsegurado(String calidadAsegurado) {
		this.calidadAsegurado = calidadAsegurado;
	}

	public String getCalidadEsposa() {
		return calidadEsposa;
	}

	public void setCalidadEsposa(String calidadEsposa) {
		this.calidadEsposa = calidadEsposa;
	}

	public String getCalidadConcubina() {
		return calidadConcubina;
	}

	public void setCalidadConcubina(String calidadConcubina) {
		this.calidadConcubina = calidadConcubina;
	}

	public String getCalidadPadres() {
		return calidadPadres;
	}

	public void setCalidadPadres(String calidadPadres) {
		this.calidadPadres = calidadPadres;
	}

	public String getCalidadHijos() {
		return calidadHijos;
	}

	public void setCalidadHijos(String calidadHijos) {
		this.calidadHijos = calidadHijos;
	}
	
	
}
