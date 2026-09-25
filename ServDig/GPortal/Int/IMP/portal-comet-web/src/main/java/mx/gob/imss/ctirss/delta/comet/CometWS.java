package mx.gob.imss.ctirss.delta.comet;

import javax.jws.WebMethod;
import javax.jws.WebService;

import mx.gob.imss.ctirss.delta.comet.model.NotificacionFinSesionCometData;
import mx.gob.imss.ctirss.delta.comet.model.ProcesamientoSolicitudCometData;
import mx.gob.imss.ctirss.delta.comet.model.RefrescarCometData;
import mx.gob.imss.ctirss.delta.comet.service.ActualizarComponentesHandlerLocal;
import mx.gob.imss.ctirss.delta.comet.service.SessionHandlerLocal;
import mx.gob.imss.ctirss.delta.comet.service.SolicitudHandlerLocal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebService(name = "publicarCometService",
		portName = "publicarCometPort",
		serviceName = "publicarCometService",
		targetNamespace = "http://www.serviciosdigitales.imss.gob.mx/comet")
@Component
public class CometWS extends SpringBeanAutowiringSupport {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private ActualizarComponentesHandlerLocal actualizarHandler;
	@Autowired
	private SolicitudHandlerLocal solicitudHandler;
	@Autowired
	private SessionHandlerLocal sessionHandler;

	@WebMethod
	public String iniciaProcesamiento(ProcesamientoSolicitudCometData data) {

		log.debug("Datos recibidos para notificar inicio de procesamiento -> "
				+ data);

		String msg = null;

		try {
			this.solicitudHandler.publicarInicioProcesamientoSolicitud(data);
			msg = "Exito al publicar en iniciaProcesamiento";
		} catch (Exception e) {
			msg = "Error al publicar en iniciaProcesamiento: " + e.getMessage();
			log.error(e);
		}

		return msg;
	}

	@WebMethod
	public String finalizaProcesamiento(ProcesamientoSolicitudCometData data) {

		log.debug("Datos recibidos para notificar fin de procesamiento -> "
				+ data);

		String msg = null;

		try {
			this.solicitudHandler.publicarFinProcesamientoSolicitud(data);
			msg = "Exito al publicar en finalizaProcesamiento";
		} catch (Exception e) {
			msg = "Error al publicar en finalizaProcesamiento: " + e.getMessage();
			log.error(e);
		}

		return msg;
	}

	@WebMethod
	public String refrescar(RefrescarCometData data) {

		log.debug("Datos recibidos para refrescar componentes de vista -> "
				+ data);

		String msg = null;

		try {
			this.actualizarHandler.publicarComet(data);
			msg = "Exito al publicar en refrescar";
		} catch (Exception e) {
			msg = "Error al publicar en refrescar: " + e.getMessage();
			log.error(e);
		}

		return msg;
	}

	public String notificarFinSesion(NotificacionFinSesionCometData data) {

		log.debug("Datos recibidos para notificar fin de sesión -> "
				+ data);

		String msg = null;

		try {
			this.sessionHandler.publicarComet(data);
			msg = "Exito al publicar en notificarFinSesion";
		} catch (Exception e) {
			msg = "Error al publicar en notificarFinSesion: " + e.getMessage();
			log.error(e);
		}

		return msg;
	}
}
