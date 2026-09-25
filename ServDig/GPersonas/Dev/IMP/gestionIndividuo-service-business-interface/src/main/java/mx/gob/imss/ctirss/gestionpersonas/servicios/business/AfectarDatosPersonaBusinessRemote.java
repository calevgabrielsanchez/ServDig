package mx.gob.imss.ctirss.gestionpersonas.servicios.business;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.individuo.AfectacionDatosPersonaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.AfectarDatosPersonaWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.MDMDatosEntrada;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modulo;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCambioInformacionPersona;

/**
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Autor: Marco Sánchez
 * @Proyecto: delta
 * @Archivo: AfectarDatosPersonaBusinessRemote.java
 * @Paquete: mx.gob.imss.ctirss.gestionpersonas.servicios.business
 * @Fecha: 27/02/2013
 */

@Remote
public interface AfectarDatosPersonaBusinessRemote {

	/**
	 * Método encargado de afectar los datos de la persona
	 * 
	 * @param datosPersona
	 * @param moduloOrigen
	 * @throws AfectacionDatosPersonaException
	 * @throws PersonaNoEncontradaException
	 */
	void afectarDatos(
			TramiteCambioInformacionPersona tramiteCambioInformacionPersona,
			Modulo moduloOrigen) throws AfectacionDatosPersonaException,
			PersonaNoEncontradaException;

	void afectarDatosPersonaFisica(
			AfectarDatosPersonaWrapper datosPersona) throws PersonaNoEncontradaException;
	
	void afectarDatosPersonaMoral(AfectarDatosPersonaWrapper datosPersona);

	/**
	 * Servicio que realiza la afectación de los medios de contacto particulares
	 * de una persona
	 * 
	 * @param fisica con el id de la persona y la lista de medios de contacto
	 *            particulares
	 */
	void modificarMediosContactoPersona(Fisica fisica);

	/**
	 * Método de prueba, para validar que el objeto recibido pueda transformarse
	 * en XML sin problemas
	 * 
	 * @param datosRespuesta
	 * @throws SolicitudNoValidaException
	 */
	void crearSolicitudICA(ICADatosRespuesta datosRespuesta)
			throws SolicitudNoValidaException;

	/**
	 * Método de prueba, para validar que el objeto recibido pueda transformarse
	 * en XML sin problemas
	 * 
	 * @param datosEntrada
	 * @throws SolicitudNoValidaException
	 */
	void crearSolicitudMDM(MDMDatosEntrada datosEntrada)
			throws SolicitudNoValidaException;
}
