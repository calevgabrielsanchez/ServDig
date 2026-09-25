package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import javax.xml.bind.annotation.XmlType;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

@XmlType(name="EstadoTramitePatronal" , namespace="http://www.mx.gob.imss.ctirss.delta.model.gestion.patronal/EstadoTramitePatronal")
public class EstadoTramite extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8425660602694845212L;
	private Long idEstadoTramitePersona;
	private String descripcion;

	public Long getIdEstadoTramitePersona() {
		return idEstadoTramitePersona;
	}

	public void setIdEstadoTramitePersona(Long idEstadoTramitePersona) {
		this.idEstadoTramitePersona = idEstadoTramitePersona;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
