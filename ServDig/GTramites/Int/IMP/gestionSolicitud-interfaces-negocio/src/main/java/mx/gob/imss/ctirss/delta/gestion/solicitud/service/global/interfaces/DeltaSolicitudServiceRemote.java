package mx.gob.imss.ctirss.delta.gestion.solicitud.service.global.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.global.model.SolicitudTO;
import mx.gob.imss.ctirss.delta.global.model.TipoTramiteTO;
import mx.gob.imss.ctirss.delta.global.model.TramiteTO;

@Remote
public interface DeltaSolicitudServiceRemote {
	
	SolicitudTO consultarDatosGenerales(String folioSolicitud) throws SolicitudNoEncontradaException;
	void testOne(TramiteTO tramite);
	void testTwo(TipoTramiteTO tipoTramite);
	void testThree(SolicitudTO solicitud);
	void notificarErrorProcesamiento(Long idSolicitud, String folio, String mensajeError);
	void actualizarMensajeNotificacion(String folio, String mensaje);
}
