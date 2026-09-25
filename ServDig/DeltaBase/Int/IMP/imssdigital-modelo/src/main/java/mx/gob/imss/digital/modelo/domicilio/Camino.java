package mx.gob.imss.digital.modelo.domicilio;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "camino", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
@XmlRootElement(name = "camino", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
public class Camino implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1008848211642112523L;
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
