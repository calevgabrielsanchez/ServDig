package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class TipoActa extends AbstractModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idTipoActa;
	private String descripcion;

	public Integer getIdTipoActa() {
		return idTipoActa;
	}

	public void setIdTipoActa(Integer idTipoActa) {
		this.idTipoActa = idTipoActa;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "TipoActa [idTipoActa=" + idTipoActa + ", descripcion="
				+ descripcion + "]";
	}

}
