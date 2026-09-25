package mx.gob.imss.ctirss.delta.gestion.domicilio.web.dto;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;

public class InitDomicilioRecortadoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5190042967402797223L;
	private boolean bloquearFormulario = false;
	private boolean setDomicilio = false;
	private boolean bootsTrapHabilitado = true;
	private boolean mostrarTitulosDialogs = false;
	private boolean mostrarMesajeRequeridos = true;
	private boolean habilitarTooltips = true;
	private String classInputs= "";//cssClass que tendran los select, cajas de texto
	private String classTabla= ""; //cssClas que tendra la tabla
	private String classButtonAceptar= ""; //cssClass del boton aceptar que iniciara la busqueda por CP
	private String classButtonLimpiar= "";
	private Domicilio domicilio;
	
	
	public boolean isBloquearFormulario() {
		return bloquearFormulario;
	}
	
	public void setBloquearFormulario(boolean bloquearFormulario) {
		this.bloquearFormulario = bloquearFormulario;
	}
	
	public boolean isSetDomicilio() {
		return setDomicilio;
	}
	
	public void setSetDomicilio(boolean setDomicilio) {
		this.setDomicilio = setDomicilio;
	}
	
	public Domicilio getDomicilio() {
		return domicilio;
	}
	
	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}

	public boolean isBootsTrapHabilitado() {
		return bootsTrapHabilitado;
	}

	public void setBootsTrapHabilitado(boolean bootsTrapHabilitado) {
		this.bootsTrapHabilitado = bootsTrapHabilitado;
	}

	public String getClassInputs() {
		return classInputs;
	}

	public void setClassInputs(String classInputs) {
		this.classInputs = classInputs;
	}

	public String getClassTabla() {
		return classTabla;
	}

	public void setClassTabla(String classTabla) {
		this.classTabla = classTabla;
	}

	public String getClassButtonAceptar() {
		return classButtonAceptar;
	}

	public void setClassButtonAceptar(String classButtonAceptar) {
		this.classButtonAceptar = classButtonAceptar;
	}

	public String getClassButtonLimpiar() {
		return classButtonLimpiar;
	}

	public void setClassButtonLimpiar(String classButtonLimpiar) {
		this.classButtonLimpiar = classButtonLimpiar;
	}

	public boolean isMostrarTitulosDialogs() {
		return mostrarTitulosDialogs;
	}

	public void setMostrarTitulosDialogs(boolean mostrarTitulosDialogs) {
		this.mostrarTitulosDialogs = mostrarTitulosDialogs;
	}

	public boolean isMostrarMesajeRequeridos() {
		return mostrarMesajeRequeridos;
	}

	public void setMostrarMesajeRequeridos(boolean mostrarMesajeRequeridos) {
		this.mostrarMesajeRequeridos = mostrarMesajeRequeridos;
	}

	public boolean isHabilitarTooltips() {
		return habilitarTooltips;
	}

	public void setHabilitarTooltips(boolean habilitarTooltips) {
		this.habilitarTooltips = habilitarTooltips;
	}
}