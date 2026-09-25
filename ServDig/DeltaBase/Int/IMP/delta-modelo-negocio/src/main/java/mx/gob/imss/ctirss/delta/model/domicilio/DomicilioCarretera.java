package mx.gob.imss.ctirss.delta.model.domicilio;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class DomicilioCarretera extends AbstractModel {
	
	private static final long serialVersionUID = 1L;
	
	private TipoTerminoGeneral terminoGeneral;
	private TipoDerechoTransito derechoTransito;
	private TipoAdministracion administracion;
	private String origen;
	private String destino;
	private String nombreVialidad;
	private String cadenamiento;
	private Integer codigoCarretera;
	
	
	
	public TipoTerminoGeneral getTerminoGeneral() {
		return terminoGeneral;
	}
	public void setTerminoGeneral(TipoTerminoGeneral terminoGeneral) {
		this.terminoGeneral = terminoGeneral;
	}
	public TipoDerechoTransito getDerechoTransito() {
		return derechoTransito;
	}
	public void setDerechoTransito(TipoDerechoTransito derechoTransito) {
		this.derechoTransito = derechoTransito;
	}
	public TipoAdministracion getAdministracion() {
		return administracion;
	}
	public void setAdministracion(TipoAdministracion administracion) {
		this.administracion = administracion;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public String getNombreVialidad() {
		return nombreVialidad;
	}
	public void setNombreVialidad(String nombreVialidad) {
		this.nombreVialidad = nombreVialidad;
	}
	public String getCadenamiento() {
		return cadenamiento;
	}
	public void setCadenamiento(String cadenamiento) {
		this.cadenamiento = cadenamiento;
	}
	public Integer getCodigoCarretera() {
		return codigoCarretera;
	}
	public void setCodigoCarretera(Integer codigoCarretera) {
		this.codigoCarretera = codigoCarretera;
	}
	
	
	
	
	

}
