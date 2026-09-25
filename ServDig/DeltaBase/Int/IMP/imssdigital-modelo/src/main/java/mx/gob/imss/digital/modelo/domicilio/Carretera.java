package mx.gob.imss.digital.modelo.domicilio;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "carretera", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
@XmlRootElement(name = "carretera", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
public class Carretera implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5271259719294188679L;
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
