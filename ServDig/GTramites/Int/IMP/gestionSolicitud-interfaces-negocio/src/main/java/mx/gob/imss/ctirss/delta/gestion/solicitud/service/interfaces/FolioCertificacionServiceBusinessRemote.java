package mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.gestion.solicitud.certificacion.retiro.GenerarFolioCertificacionException;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.certificacion.retiro.SolicitudFolioCertificacion;

@Remote
public interface FolioCertificacionServiceBusinessRemote {

	/**
	 * Servicio que contiene la lógica necesaria para generar el folio para la
	 * certificación de derechos
	 * 
	 * @return
	 * @throws GenerarFolioCertificacionException
	 */
	String obtenerFolio() throws GenerarFolioCertificacionException;

	/**
	 * Servicio para guardar la relación entre la solicitud generada y el folio
	 * de certificación
	 * 
	 * @param solicFolioCertificacion
	 */
	void guardarRelacionSolicitudFolioCertificacion(
			SolicitudFolioCertificacion solicFolioCertificacion);

	/**
	 * Servicio para guardar la relación entre la solicitud generada y el folio
	 * de certificación, recibiendo solamente el id de la solicitud y el número
	 * de resolución
	 * 
	 * @param solicFolioCertificacion
	 */
	void guardarRelacionSolicitudFolioCertificacion(Long idSolicitud,
			String numResolucion);

}
