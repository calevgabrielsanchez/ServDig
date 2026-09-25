package mx.gob.imss.ctirss.delta.exception.derechohabiente;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

public class SolicitudesEnProcesoException extends AbstractException implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final String situacion ="La solicitud se encuentra en proceso.";
	private static final Integer codigo = new Integer (9999);
	private Solicitud solicitud = null;
	
	public SolicitudesEnProcesoException() {
	    super(situacion, codigo);
	}
	 
	
	public SolicitudesEnProcesoException(Solicitud solicitud) {
		super(situacion, codigo);
		this.solicitud = solicitud;
	}


	public Solicitud getSolicitud() {
		return this.solicitud;
	}
	
	
	
}
