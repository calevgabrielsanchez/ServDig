package mx.gob.imss.ctirss.delta.model.domicilio;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class DomicilioCamino extends AbstractModel {
	
	private static final long serialVersionUID = 1L;
	
	
	private TipoTerminoGeneral terminoGeneral;
	private TipoMargen margen;
	private String origen;
	private String destino;
	private String nombreVialidad;
	private String cadenamiento;
	
	
	public TipoTerminoGeneral getTerminoGeneral() {
		return terminoGeneral;
	}
	public void setTerminoGeneral(TipoTerminoGeneral terminoGeneral) {
		this.terminoGeneral = terminoGeneral;
	}
	public TipoMargen getMargen() {
		return margen;
	}
	public void setMargen(TipoMargen margen) {
		this.margen = margen;
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
	
	

}
