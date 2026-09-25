package mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EstadoCivil implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2817109049071428355L;
	private Integer idEstadoCivil;
	private String descripcion;

	/**
	 * @return the idEstadoCivil
	 */
	public Integer getIdEstadoCivil() {
		return idEstadoCivil;
	}

	/**
	 * @param idEstadoCivil
	 *            the idEstadoCivil to set
	 */
	public void setIdEstadoCivil(Integer idEstadoCivil) {
		this.idEstadoCivil = idEstadoCivil;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
