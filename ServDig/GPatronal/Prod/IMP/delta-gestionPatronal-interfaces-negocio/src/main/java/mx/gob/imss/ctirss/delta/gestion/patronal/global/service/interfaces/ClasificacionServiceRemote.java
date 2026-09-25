package mx.gob.imss.ctirss.delta.gestion.patronal.global.service.interfaces;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.global.model.RegistroPatronalTO;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Documento;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;


@Remote
public interface ClasificacionServiceRemote {
	
	/**
	 * Actualiza la informaci?n de la actividad econ?mica as? como la fracci?n asignada al
	 * registro patronal proporcionado.
	 * 
	 * Este servicio se encarga tambi?n de encolar la solicitud de sincronizaci?n de este
	 * movimiento en SINDO.
	 * 
	 * 
	 * @param registroPatronal
	 */
	void actualizarClasificacionYActividadEconomica(RegistroPatronalTO registroPatronal, Long idSolicitud) throws GestionPatronalBusinessException;
	
	/**
	 * Almacena en base de datos toda la informaci?n de la nueva clasificaci?n
	 * incluyendo la actividad economica contenida en la solicitud correspondiente
	 * al identificador proporcionado
	 * @param idSolicitud Identificador de la solicitud
	 * @throws GestionPatronalBusinessException
	 */
	void finalizarModificacionSRT(Long idSolicitud) throws GestionPatronalBusinessException;
	
	/**
	 * Finaiza el tramite desde ventanilla de clasificacion incluyendo el movimienot a SINDO e impacto en
	 * base de datos y finalizaci�n del tr�mite y solicitud.
	 * @param idSolicitud
	 * @throws GestionPatronalBusinessException
	 */
	byte[] finalizarModificacionSRTVentanilla(Solicitud solicitud) throws GestionPatronalBusinessException;
	
	/**
	 * Guarda la solictid clasificacion por internet genera cita 
	 * @param solicitud
	 * @param documentosReq
	 * @return
	 * @throws GestionPatronalBusinessException
	 */
	Solicitud guardaCitaModificacionSRTVInternet(Solicitud solicitud, SujetoObligado sujetoObligado) throws GestionPatronalBusinessException;
}
