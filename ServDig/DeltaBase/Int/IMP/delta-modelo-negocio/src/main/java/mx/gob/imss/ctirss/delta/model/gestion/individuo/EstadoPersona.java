package mx.gob.imss.ctirss.delta.model.gestion.individuo;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class EstadoPersona extends AbstractModel {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -7801323804620310543L;
	private Long idEstadoPersona;
	private String descripcion;

	/**
	 * @return the idEstadoPersona
	 */
	public Long getIdEstadoPersona() {
		return idEstadoPersona;
	}

	/**
	 * @param idEstadoPersona
	 *            the idEstadoPersona to set
	 */
	public void setIdEstadoPersona(Long idEstadoPersona) {
		this.idEstadoPersona = idEstadoPersona;
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
