package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;


@XmlRootElement
public class Clase extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5508308555492151863L;
	private Long clave;
	private String descripcion;

	public Long getClave() {
		return clave;
	}

	public void setClave(Long clave) {
		this.clave = clave;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
