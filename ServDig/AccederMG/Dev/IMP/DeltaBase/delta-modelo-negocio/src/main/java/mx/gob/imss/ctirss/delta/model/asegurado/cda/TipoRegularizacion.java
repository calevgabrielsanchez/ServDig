package mx.gob.imss.ctirss.delta.model.asegurado.cda;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TipoRegularizacion extends AbstractModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long idTipoRegularizacion;
	private String desTipoRegularizacion;

	public Long getIdTipoRegularizacion() {
		return idTipoRegularizacion;
	}

	public void setIdTipoRegularizacion(Long idTipoRegularizacion) {
		this.idTipoRegularizacion = idTipoRegularizacion;
	}

	public String getDesTipoRegularizacion() {
		return desTipoRegularizacion;
	}

	public void setDesTipoRegularizacion(String desTipoRegularizacion) {
		this.desTipoRegularizacion = desTipoRegularizacion;
	}

}
