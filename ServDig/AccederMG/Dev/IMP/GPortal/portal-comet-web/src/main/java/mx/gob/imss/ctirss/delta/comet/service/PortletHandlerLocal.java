package mx.gob.imss.ctirss.delta.comet.service;

import java.io.IOException;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;

public interface PortletHandlerLocal {

	/**
	 * Publica 'modificaciones' a personas fisicas o morales representadas
	 */
	void publicarModificacionRepresentadoLegal(Persona persona)
			throws IOException;

	/**
	 * Publica 'modificaciones' a representantes legales
	 */
	void publicarModificacionRepresentantesLegales(Persona persona)
			throws IOException;

	/**
	 * Publica 'modificaciones' a patrones asociados
	 */
	void publicarModificacionPatronesAsociados(Fisica fisica)
			throws IOException;

	/**
	 * Publica 'patronesClasificacion' a patrones asociados
	 */
	void publicaModificacionClasificacion(String numeroRegistroPatronal)
			throws IOException;

	/**
	 * Publica mensaje para refrescar el portlet de solicitudes de persona
	 */
	void publicarSolicitudesPersona(Persona persona) throws IOException;

	/**
	 * Publica mensaje para refrescar el portlet de solicitudes de patron
	 */
	void publicarSolicitudesPatron(String numeroRegistroPatronal)
			throws IOException;

	/**
	 * Publica mensaje para refrescar el portlet de persona autorizada
	 */
	void publicarModificacionPersonaAutorizada(Persona persona)
			throws IOException;
	
	void publicarIVRO(Persona persona) throws IOException;
	
	/**
	 * Publica mensaje para refrescar el detalle de la identidad en el portal ventanilla
	 * 
	 * @param persona
	 * @throws IOException
	 */
	void publicarDetalleIdentidad(Persona persona) throws IOException;

	/**
	 * Publica mensaje para refrescar el detalle del sujeto en el portal ventanilla
	 * 
	 * @param persona
	 * @throws IOException
	 */	
	void publicarDetalleSujeto(Persona persona) throws IOException;

}
