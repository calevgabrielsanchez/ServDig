package mx.gob.imss.ctirss.delta.model.gestion.solicitud.certificacion.retiro;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

public class SolicitudFolioCertificacion extends AbstractModel {

	private static final long serialVersionUID = -4708119337351174743L;

	private Solicitud solicitud;
	private String folioCertificacion;

	public Solicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	public String getFolioCertificacion() {
		return folioCertificacion;
	}

	public void setFolioCertificacion(String folioCertificacion) {
		this.folioCertificacion = folioCertificacion;
	}

}
