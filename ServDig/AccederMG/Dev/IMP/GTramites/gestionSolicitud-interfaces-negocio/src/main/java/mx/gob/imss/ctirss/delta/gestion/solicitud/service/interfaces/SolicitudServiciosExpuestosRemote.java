package mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.global.model.SolicitudTO;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.CitaSolicitud;

@Remote
public interface SolicitudServiciosExpuestosRemote {

	SolicitudTO crearSolicitud(SolicitudTO solicitudTO)
			throws SolicitudNoValidaException;

	void cancelarSolicitudPorFolio(String folio);

	void actualizarEstadoMensajeError(String folioSolicitud,
			Integer idEstadoParaAsignar, Integer idEstadoTramiteAsignar,
			String observacion);

	void concluirSolicitudPorFolio(String folio);

	void cancelarTramitePorId(Long idTramite);

	void asociarSolicitudSubDelegacion(Long idSolicitud,Long idSubDelegacion);

	/**
	 * Metodo para guardar una cita 
	 * @param cita
	 * @throws SolicitudException
	 */
	CitaSolicitud guardaCitaSolicitud(CitaSolicitud cita) throws SolicitudException;

	/**
	 * Metodo que actualiza una cita
	 * @param cita
	 * @throws SolicitudException
	 */
	CitaSolicitud actualizaCitaSolicitud(CitaSolicitud cita) throws SolicitudException;

	/**Metodo que claculoa la fecha de la proxima cita basado en los atribuots de busqueda que recibe
	 * 
	 * @param cita
	 * @throws SolicitudException
	 */
	CitaSolicitud calculaFechaCita(CitaSolicitud cita) throws SolicitudException;

	/**Valida si en la fecha que se envia en el objeto cita  es valida para asignar
	 * 
	 * @param cita
	 * @return
	 * @throws SolicitudException
	 */
	boolean validaFechaCita(CitaSolicitud cita) throws SolicitudException;
	
	/**COnsulta la cita ya sea foir folio, id o id solicitud
	 * 
	 * @param cita
	 * @return
	 * @throws SolicitudException
	 */
	CitaSolicitud consultaCItaSOlicitud(CitaSolicitud cita) throws SolicitudException;
}
