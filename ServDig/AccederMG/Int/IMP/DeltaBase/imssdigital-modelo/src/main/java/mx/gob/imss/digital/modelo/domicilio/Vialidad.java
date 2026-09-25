package mx.gob.imss.digital.modelo.domicilio;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "vialidad", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
@XmlRootElement(name = "vialidad", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
public class Vialidad implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3279428547986857278L;

	private Integer clave;
	
	private String nombre;
		
	private TipoVialidad tipoVialidad;

	public Integer getClave() {
		return clave;
	}

	public void setClave(Integer clave) {
		this.clave = clave;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public TipoVialidad getTipoVialidad() {
		return tipoVialidad;
	}

	public void setTipoVialidad(TipoVialidad tipoVialidad) {
		this.tipoVialidad = tipoVialidad;
	}
	
	
	
	
}
