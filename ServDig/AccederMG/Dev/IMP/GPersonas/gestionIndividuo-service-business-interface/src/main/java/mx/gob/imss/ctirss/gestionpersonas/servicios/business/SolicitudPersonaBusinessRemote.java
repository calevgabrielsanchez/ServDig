/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:SolicitudPersonaBusiness.java
 *  @Paquete:mx.gob.imss.ctirss.gestionpersonas.servicios.business
 *  @Fecha:22/02/2012
 */
package mx.gob.imss.ctirss.gestionpersonas.servicios.business;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.individuo.AfectacionDatosPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ComparacionSinDiferenciasException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.RegistroPersonaFisicaException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.exception.usuario.ActualizaUsuarioEsquemaSeguridadException;
import mx.gob.imss.ctirss.delta.exception.usuario.EsquemaSegurdiadException;
import mx.gob.imss.ctirss.delta.exception.usuario.UsuarioNoRegistradoEnEsquemaDeSeguridadException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.enums.CambioComparacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.MDMDatosEntrada;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;

@Remote
public interface SolicitudPersonaBusinessRemote {

	Solicitud alta(Solicitud solicitud);

	Solicitud getSolicitud(Long idSolicitud)
			throws SolicitudNoEncontradaException;

	void procesarSolicitudesRegistradas() throws SolicitudException;

	void cancelaSolicitudes();

	Solicitud modificar(Solicitud solicitud) throws SolicitudException;

	Solicitud procesarSolicitudNueva(Solicitud solicitud)
			throws SolicitudException;

	Solicitud procesarSolicitudExistente(Solicitud solicitud)
			throws SolicitudException;

	Solicitud procesarTramiteSolicitud(Solicitud solicitudSeleccionada)
			throws SolicitudException;

	/**
	 * 191807 301012 Se setean los identificadoresen caso de haberse localizado
	 * a la persona en alguna entidad externa
	 */
	void agregarIdentificadores(Fisica personaFisicaRenapo);

	/**
	 * Servicio que obtiene una solicitud REGISTRADA del tipo especificado,
	 * también toma en cuenta el tipo de tramite que se le manda.
	 * 
	 * @param idPersona
	 * @param idTipoPersona
	 * @param tipoSolicitud
	 * @param tipoTramite
	 * @return
	 * @throws SolicitudException
	 */
	mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud obtenerSolicitudRegistrada(
			Long idPersona, Long idTipoPersona,
			TipoSolicitudEnum tipoSolicitud, TipoTramiteEnum tipoTramite)
			throws SolicitudException;

	/**
	 * Servicio que obtiene una solicitud EN_PROCESO del tipo especificado,
	 * también toma en cuenta el tipo de tramite que se le manda.
	 * 
	 * @param idPersona
	 * @param idTipoPersona
	 * @param tipoSolicitud
	 * @param tipoTramite
	 * @return
	 * @throws SolicitudException
	 */
	mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud obtenerSolicitudEnProceso(
			Long idPersona, Long idTipoPersona,
			TipoSolicitudEnum tipoSolicitud, TipoTramiteEnum tipoTramite)
			throws SolicitudException;

	/**
	 * Servicio que crea tanto la solicitud como el trámite relacionado a la
	 * actualización de datos de una persona y también realiza la afectación en
	 * la base de datos.
	 * 
	 * @param datosRespuesta
	 * @param Usuario con la clave del usuario que realiza el tramite
	 * @param idPersonaInteresadaSolicitud -  el id de la persona interesara, para el caso del portal persona o cualquier otro diferente
	 * al de asegurado y derechohabiente, sera el mismo que el de la persona afectada, para el caso del portal del asegurado o derechohabiente
	 * sera el id de la persona que corresponde al asegurado o al pensionado, mientras que el id de la persona del tramite sera el beneficiario
	 * y en su caso el asegurado o pensionado. El atributo puede venir nulo y no se guardara la relacion entre DitSolicitud y DitPersonaInteresadaSol
	 * @return
	 * @throws SolicitudNoValidaException
	 * @throws AfectacionDatosPersonaException
	 * @throws PersonaNoEncontradaException
	 * @throws RegistroPersonaFisicaException
	 * @throws ComparacionSinDiferenciasException
	 */
	mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud crearTramiteActualizacionDatosPersona(
			ICADatosRespuesta datosRespuesta, Usuario usuario, Long idPersonaInteresadaSolicitud, OrigenSolicitudEnum origenSolicitud)
			throws SolicitudNoValidaException, AfectacionDatosPersonaException,
			PersonaNoEncontradaException, RegistroPersonaFisicaException,
			ComparacionSinDiferenciasException;

	/**
	 * Servicio que cancela una solicitud, es decir, le cambia el estatus a
	 * cancelada
	 * 
	 * @param idSolicitud
	 * @return mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud
	 * @throws SolicitudNoEncontradaException
	 * @throws TramiteNoEncontradoException
	 */
	mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud cancelarSolicitud(
			Long idSolicitud) throws SolicitudNoEncontradaException,
			TramiteNoEncontradoException;

	/**
	 * Servicio para retomar una solicitud de actualización de datos, busca la
	 * solicitud a través del idSolicitud recibido, valida que la solicitud
	 * encontrada contenga información relacionada al ICA. Si encuentra
	 * información ICA regresa un mapa con el objeto solicitud [llave =
	 * 'solicitud'] y el objeto con la información del ICA [llave = 'datosICA']
	 * (se hace esto para aprovechar que en la validación ya se busca el objeto
	 * ICA); en caso de no encontrar datos ICA se lanza una excepción.
	 * 
	 * @param idSolicitud
	 * @return
	 * @throws SolicitudNoEncontradaException
	 * @throws SolicitudNoValidaException
	 */
	Map<String, Object> retomarSolicitudActualizacionDatos(Long idSolicitud)
			throws SolicitudNoEncontradaException, SolicitudNoValidaException;

	/**
	 * Servicio que guarda el avance de la solicitud de actualización de datos,
	 * es decir, sólo actualiza la inforamción
	 * 
	 * @param solicitud
	 * @param datosRespuesta
	 * @throws SolicitudNoEncontradaException
	 * @throws TramiteNoEncontradoException
	 */
	void guardarSolicitudActualizacionDatos(Long idSolicitud,
			ICADatosRespuesta datosRespuesta)
			throws SolicitudNoEncontradaException, TramiteNoEncontradoException;

	/**
	 * Servicio que finaliza una solicitud de actualización de datos.
	 * 
	 * @param idSolicitud
	 * @throws SolicitudNoEncontradaException
	 * @throws SolicitudNoValidaException
	 * @throws AfectacionDatosPersonaException
	 * @throws PersonaNoEncontradaException
	 * @throws TramiteNoEncontradoException
	 */
	void finalizarSolicitudActualizacionDatos(Long idSolicitud)
			throws SolicitudNoEncontradaException, SolicitudNoValidaException,
			AfectacionDatosPersonaException, PersonaNoEncontradaException,
			TramiteNoEncontradoException;

	/**
	 * Servicio que crea un tramite para la modificación de medios de contacto y
	 * domicilios, tanto fiscales como particulares
	 * 
	 * @param datosEntrada
	 * @return
	 * @throws SolicitudNoValidaException
	 */
	mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud crearTramiteModificacionDatosPersona(
			MDMDatosEntrada datosEntrada, Usuario usuario) throws SolicitudNoValidaException;

	
	/**
	 * Servicio que crea un tramite para la modificación de medios de contacto y
	 * domicilios, tanto fiscales como particulares
	 * 
	 * @param datosEntrada
	 * @return
	 * @throws SolicitudNoValidaException
	 */
	mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud crearTramiteModificacionDatosPersona(
			MDMDatosEntrada datosEntrada, Usuario usuario, Long idPersonaInteresada) throws SolicitudNoValidaException;

	
	/**
	 * Servicio para retomar una solicitud de modificación de datos (medios y
	 * domicilios, tanto particulares como fiscales), busca la solicitud a
	 * través del idSolicitud recibido, valida que la solicitud encontrada
	 * contenga información relacionada a la modificación. Si encuentra
	 * información CA regresa un mapa con el objeto solicitud [llave =
	 * 'solicitud'] y el objeto con la información de la modificación [llave =
	 * 'datosModif'] (se hace esto para aprovechar que en la validación ya se
	 * busca el objeto modificación); en caso de no encontrar datos de la
	 * modificación se lanza una excepción.
	 * 
	 * @param idSolicitud
	 * @return
	 * @throws SolicitudNoEncontradaException
	 * @throws SolicitudNoValidaException
	 */
	Map<String, Object> retomarSolicitudModificacionDatosPersona(
			Long idSolicitud) throws SolicitudNoEncontradaException,
			SolicitudNoValidaException;

	/**
	 * Servicio que guarda el avance de la solicitud de modificación de datos,
	 * es decir, sólo actualiza la inforamción
	 * 
	 * @param solicitud
	 * @param datosRespuesta
	 * @throws SolicitudNoEncontradaException
	 * @throws TramiteNoEncontradoException
	 */
	void guardarSolicitudModificacionDatosPersona(mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud,
			MDMDatosEntrada datosModif) throws SolicitudNoEncontradaException,
			TramiteNoEncontradoException;

	/**
	 * Servicio que finaliza una solicitud de modificación de datos
	 * 
	 * @param idSolicitud
	 * @throws SolicitudNoEncontradaException
	 * @throws SolicitudNoValidaException
	 * @throws AfectacionDatosPersonaException
	 * @throws PersonaNoEncontradaException
	 * @throws TramiteNoEncontradoException
	 * @throws PersonaFisicaNoEncontradaException
	 * @throws ErrorComparacionDatosRENAPOException
	 */
	void finalizarSolicitudModificacionDatosPersona(Long idSolicitud)
			throws SolicitudNoEncontradaException, SolicitudNoValidaException,
			AfectacionDatosPersonaException, PersonaNoEncontradaException,
			TramiteNoEncontradoException, ErrorComparacionDatosRENAPOException,
			PersonaFisicaNoEncontradaException;

	/**
	 * Servicio que finaliza una solicitud de modificación de datos
	 * 
	 * @param idSolicitud
	 * @throws SolicitudNoEncontradaException
	 * @throws SolicitudNoValidaException
	 * @throws AfectacionDatosPersonaException
	 * @throws PersonaNoEncontradaException
	 * @throws TramiteNoEncontradoException
	 * @throws PersonaFisicaNoEncontradaException
	 * @throws ErrorComparacionDatosRENAPOException
	 */
	void finalizarSolicitudModificacionDatosPersona(
			mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud,
			MDMDatosEntrada datosModif,
			FirmaElectronica firmaElectronica) throws SolicitudNoEncontradaException, TramiteNoEncontradoException, SolicitudException;

	/**
	 * Servicio encargado de guardar una solicitud de registros de usuario
	 * 
	 * @param solicitud
	 * @return
	 * @throws SolicitudNoValidaException
	 * @throws IllegalArgumentException
	 */
	mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud generaSolicitudUsuarioSSO(
			mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud)
			throws SolicitudNoValidaException, IllegalArgumentException;

	/**
	 * servicio para dar de alta una cuenta en el SSO y registrar la persona si
	 * no existe y concluir la solicitud y el tramite
	 * 
	 * @param solicitud
	 * @throws DomicilioNoValidoException
	 * @throws SolicitudNoEncontradaException
	 * @throws TramiteNoEncontradoException
	 * @throws RegistroPersonaFisicaException
	 * @throws UsuarioNoRegistradoEnEsquemaDeSeguridadException
	 */
	Fisica creaCuentaUsuararioSSO(
			mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud, FirmaElectronica firmaElectronica)
			throws DomicilioNoValidoException, SolicitudNoEncontradaException,
			TramiteNoEncontradoException, RegistroPersonaFisicaException,
			UsuarioNoRegistradoEnEsquemaDeSeguridadException, PersonaNoEncontradaException;
	
	/**
	 * Metodo para actualizar los datos de registro de usuario en esquema de seguridad
	 * @param objUsuario con los datos seteados a actualizar
	 * @param firmaElectronica con los datos de la firma a guardar como trámite
	 * @throws ActualizaUsuarioEsquemaSeguridadException
	 * @throws EsquemaSegurdiadException
	 */
	void actualizaUsuarioEsquemaSeguridad(Usuario objUsuario,  FirmaElectronica firmaElectronica ) throws ActualizaUsuarioEsquemaSeguridadException, EsquemaSegurdiadException;

	/**
	 * Servicio que obtiene el trámite relacionado a la
	 * actualización de datos de una persona.
	 * 
	 * @param isFisica
	 * @param datosRespuesta
	 * @return
	 */
	mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite getTramiteAsociadoByTipoPersona(
			boolean isFisica, ICADatosRespuesta datosRespuesta);

	/**
	 * Servicio que determina si existen cambios de datos de las personas registradas
	 * @param diferencias
	 * @return
	 */
	boolean existenDiferencias(Map<String, CambioComparacionEnum> diferencias);
	
	/**
	 * Este metodo se encarga de finalizar la solicitud de asignacion de domicilio, la cual consiste en cerrar propiamente la solcitud y el tramite correspondiente
	 * ademas de actualizar el domicilio en el grupo familiar del derechohabiente (DIT_GRUPO_FAMILIAR). 
	 * @author juan.osorioal
	 * @param solicitud Debe ser un objeto de tipo Solicitud con al menos su ID
	 * @return
	 */
	mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud 
		finalizarSolicitudAsignacionDomicilio(mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud)
				throws SolicitudNoEncontradaException, DomicilioNoLocalizadoException;
	
	/**
	 * Este metodo se encarga de finalizar la solicitud de actualizacion de domicilio, la cual consiste en cerrar propiamente la solcitud y el tramite correspondiente
	 * ademas de actualizar la informacion del domicilio geografico al cual esta relacionado el miembro del grupo familiar por medio de la relacion DIT_GRUPO_FAMILIAR, DIT_PERSONAF_DOM
	 * DG_DOMIICLIO_GEOGRAFICO (DG_DOMIICLIO_GEOGRAFICO es donde se actualiza la informacion). 
	 * @author juan.osorioal
	 * @param solicitud Debe ser un objeto de tipo Solicitud con al menos su ID
	 * @return
	 */
	mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud 
		finalizarSolicitudActualizacionDomicilio(mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud, MDMDatosEntrada mdmDatosEntrada)
				throws SolicitudNoEncontradaException, DomicilioNoLocalizadoException;
	
	/**
	 * Este metodo se encarga de finalizar la solcitud de cambio de UMF destino
	 * @param solicitud
	 * @return
	 * @throws SolicitudNoEncontradaException
	 * @throws DomicilioNoLocalizadoException
	 */
	public mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud 
		finalizarSolicitudCambioClinica(mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud)
				throws SolicitudNoEncontradaException, DomicilioNoLocalizadoException;
	
	/**
	 * Este metodo se encarga de actualizar el estatus de los tramites contenidos en la solcitud
	 * @param solicitud
	 * @return
	 */
	mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud 
		actualizarTramites(mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud)throws SolicitudNoEncontradaException, TramiteNoEncontradoException;

	/**
	 * Metodo que verifica si una persona tiene un registro de usuario en IMSS Digital
	 * @param curp
	 * @return
	 */
	boolean existeTramiteRegistroUsuario(String curp);
	
	/**
	 * servicio para dar de alta una cuenta en el SSO y registrar la persona si
	 * no existe y concluir la solicitud y el tramite
	 * 
	 * @param solicitud
	 * @throws DomicilioNoValidoException
	 * @throws SolicitudNoEncontradaException
	 * @throws TramiteNoEncontradoException
	 * @throws RegistroPersonaFisicaException
	 * @throws UsuarioNoRegistradoEnEsquemaDeSeguridadException
	 */
	Fisica creaCuentaUsuararioSSOPass(mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud,Usuario usuario)
			throws DomicilioNoValidoException, SolicitudNoEncontradaException,
			TramiteNoEncontradoException, RegistroPersonaFisicaException,
			UsuarioNoRegistradoEnEsquemaDeSeguridadException, PersonaNoEncontradaException;
	
	List<String> consultarNombresPatrones(List<String> registroPatronal);
	
	List<String> consultarNombrePatronPorRPYModalidad(String registroPatronal, String modalidad);
	
	void insertarDatosCuenta(Usuario usuario, Long idSolicitud);
	
	/**
	 * Metodo complementario para dar de alta a la persona si no exite o actualizar RFC y ditPersonaFisica
	 * para el registro de usuarios
	 * @param fisica
	 * @return
	 * @throws Exception
	 */
	mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica creaActualizaPersonaFisicaRegistroUsuario(
			mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud) throws Exception;
	
	
	

}