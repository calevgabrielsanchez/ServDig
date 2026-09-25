package mx.gob.imss.ctirss.delta.model.derechohabiente;
//solicitud
import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class TipoPerInteresadaSol  extends AbstractModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5990774702373039547L;
	private Long cveTipoInteresadaSol;
	private String desTipoInteresadoSolicitud;

	public Long getCveTipoInteresadaSol() {
		return cveTipoInteresadaSol;
	}

	public void setCveTipoInteresadaSol(Long cveTipoInteresadaSol) {
		this.cveTipoInteresadaSol = cveTipoInteresadaSol;
	}

	public String getDesTipoInteresadoSolicitud() {
		return desTipoInteresadoSolicitud;
	}

	public void setDesTipoInteresadoSolicitud(String desTipoInteresadoSolicitud) {
		this.desTipoInteresadoSolicitud = desTipoInteresadoSolicitud;
	}

}
