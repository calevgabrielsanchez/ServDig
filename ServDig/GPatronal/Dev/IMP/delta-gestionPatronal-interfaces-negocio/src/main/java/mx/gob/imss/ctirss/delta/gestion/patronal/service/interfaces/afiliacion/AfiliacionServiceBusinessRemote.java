package mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;

/**
 * 
 * @author Hugo Martinez
 * Subiendo por problemas con tfs
 */
@Remote
public interface AfiliacionServiceBusinessRemote {
	
	/**
	 * 
	 * Gestiona los tramites de actualizacion de datos afiliatorios.
	 * - Verifica si existe una solicitud activa, es decir, con estatus de registrada. Si no existe ninguna la crea.
	 * - Se verifica si ya existe un tr�mite del mismo tipo que el porporcionado por el parametro tipoTramite. 
	 * 	 Si existe se actualiza la informaci�n del tr�mite con la informaci�n proporcinada en el parametro sujetoTr�mite.
	 * 	 Si no existe un tramite de ese tipo se agrega el tr�mite a la solicitud.
	 * 
	 * 
	 * @author Hugo Martinez
	 * @Date 25/07/2012
	 * @param sujetoTramite Informaci�n del tramite
	 * @param tipoTramite Tipo de tramite a gestion
	 * @param indRatificado Indica si la informacion del tramite  fue ratificada, es decir no hay cambios
	 */
	Map<String, Object> gestionarTramiteActualizacionAfiliacion(Long idSolicitud,
			SujetoObligado sujetoTramite, 
			TipoTramiteEnum tipoTramite, 
			Usuario usuario, 
			boolean indRatificado) throws GestionPatronalBusinessException;
	
	
	/**
	 * Obtiene la solicitud activa.
	 * Si existe una soliciud activa busca si tiene asociado un tipo de tramite igual al enviado como parametro.
	 * Si existe un tramite del mismo tipo asociado devuelve un objeto SujetoObligado con la informaci�n del 
	 * almacenada para dicho tramite.
	 * En caso de no existir ninguna solicitud activa o bien ning�n tramite del mismo tipo el servicio retorna un 
	 * valor nulo
	 * @author Hugo Martinez
	 * @Date 27/07/2012
	 * @param sujetoObligado
	 * @param tipoTramite
	 * @return SujetoObligado
	 */
	Map<String, Object> gestionarDatosDeTramite(SujetoObligado sujetoObligado, TipoTramiteEnum tipoTramite, Usuario usuario, boolean esNuevaSolicitud);
	
	/**
	 * Obtiene los datos fiscales de la persona en base al tipo de persona y al identificador de la misma
	 * 
	 * @author Hugo Martinez
	 * @Date 27/07/2012
	 * @param persona
	 * @return SujetoObligado
	 */
	SujetoObligado obtenerDatosFiscales(Persona persona);
	
	
	/**
	 * Actualiza el estatus de la solicitud activa a un valor determinado
	 * por el tipo de tr�mites contenidos en la solicitud
	 * con el fin de que el usuario de ventanilla pueda retomar el tramite
	 * para su conclusi�n.
	 * 
	 * Los valores posibles son: PRESENTARSE_EN_VENTANILLA o PARA_PROCESAR_BACKOFFICE
	 * 
	 * En caso de que la solicitud sea firmada electr�nicamente y el tr�mite
	 * pueda concluirse en linea se afecta la BDTU y se asigna el estatus de ATENDIDA
	 * 
	 * @author Hugo Martinez
	 * @Date 30/07/2012
	 * @param sujetoTramite
	 * 
	 */
	Solicitud enviarSolicitudAlInstituto(SujetoObligado sujetoTramite, TipoSolicitudEnum tipoSolicitud, FirmaElectronica datosFirma ) throws GestionPatronalBusinessException;
	
	/**
	 * Retorna el c�dido de operacion a realizar y los par�metros del mensaje a desplegar
	 * @author Hugo Martinez
	 * @Date 02/08/2012
	 * @param sujetoTramite
	 * @return 
	 */
	Map<String, Object> validarTramiteAfiliacionActivo(Long idSolicitud, SujetoObligado sujetoTramite, TipoTramiteEnum tipoTramite, Usuario usuario);
	
	
	/**
	 * Retorna la solicitud en proceso
	 * @author Hugo Martinez
	 * @Date 07/08/2012
	 * @param sujetoObligado
	 * @return Solicitud
	 */
	Solicitud obtenerSolicitudEnProceso(SujetoObligado sujetoObligado, TipoSolicitudEnum tipoSolicitud);
	
	/**
	 * Obtiene las solicitudes en proceso para el sujeto obligado y/o pesona
	 * @author Hugo Martinez
	 * @Date 09/08/2012
	 * @param sujetoObligado
	 * @return List<Solicitud>
	 */
	List<Solicitud> listarSolicitudesEnProceso(SujetoObligado sujetoObligado, boolean esTramitador);
	
	/**
	 * Actualiza el estatus de la solicitud a atendida y modifica el estatus de todos
	 * los tr�mites relacionados con el valor CERRADO.
	 * 
	 * 
	 * @author Hugo Martinez
	 * @Date 10/08/2012
	 * @param tipoSolicitud
	 */
	void concluirSolicitud(Long idSolcitud, SujetoObligado sujetoTramite, Usuario usuario) throws GestionPatronalBusinessException;
	
	/**
	 * Finaliza la solicitud proporcionada. Este metodo considera unicamente solicitudes que contengan los siguientes tramites:
	 * ACTUALIZACION_DENOMINACION_RAZON_SOCIAL
	 * ESCRITURA_CONSTITUTIVA
	 * REGISTRO_SINDICATO
	 * ACTUALIZACION DE DATOS DE CONTACTO
	 * ACTUALIZACION DE SOCIOS
	 * ACTUALIZACION DE REPRESENTANTES LEGALES
	 * 
	 * 
	 * @param idSolcitud
	 * @throws GestionPatronalBusinessException
	 */
	void concluirSolicitudAfiliacion(Long idSolcitud) throws GestionPatronalBusinessException;
	
		
	String validarMediosContacto(List<MedioContacto> mediosContacto);
	
	void validarSubdOrigenSubDestino(SujetoObligado sujetoObligado) throws GestionPatronalBusinessException;
	
	/**
	 * Obtiene el detalle general del Registro Patronal: Actividad Economica y Centro de Trabajo, Domicilio Fiscal, etc.
	 * @author Hugo Martinez
	 * @Date 15/08/2012
	 * @param sujetoObligado
	 * @return SujetoObligado
	 */
	SujetoObligado obtenerDetalleDeRegistroPatronal(SujetoObligado sujetoObligado);
	
	/**
	 * TRUE si hay una solicitud en estatus POR_PRESENTARSE_EN_VENTANILLA o PARA_PROCESAR EN BACK_OFFICE
	 * @author Hugo Martinez
	 * @Date 06/09/2012
	 * @return BOOLEAN
	 */
	boolean existeSolicitudPendienteDeAsignacion(SujetoObligado sujetoObligado, TipoSolicitudEnum tipoSolicitud);
	
	/**
	 * Actualiza los estatus de la solicitud y el tr�mite a cancelado
	 * @author Hugo Martinez
	 * @Date 10/09/2012
	 * @param solicitud
	 */
	Solicitud cancelarSolicitud(Solicitud solicitud);
	
	
	/**
	 * Actualiza los estatus de la solicitud a rechazado y el tr�mite a cerrado
	 * @author Hugo Martinez
	 * @Date 10/09/2012
	 * @param solicitud
	 */
	Solicitud rechazarSolicitud(Solicitud solicitud);
	
	/**
	 * Valida si el rp proporcionado pertenece al rp
	 * con el cu�l se esta trabajando, de ser as� se retorna true
	 * de lo contrario false
	 * @author Hugo Martinez
	 * @Date 10/09/2012
	 * @param sujetoObligado
	 * @return Boolean
	 */
	Map<String, Object> validaRegistroPatronalValidoPorRFC(SujetoObligado sujetoObligado, Usuario usuario);
	
	/**
	 * Obtiene la solicitud en estatus POR PRESENTARSE EN VENTANILLA O POR PROCESAR
	 * EN BACKOFFICE
	 * @author Hugo Martinez
	 * @Date 12/09/2012
	 * @param sujetoObligado
	 * @param tipoSolicitud
	 * @return
	 */
	Solicitud obtenerSolicitudPendientePorAsignar(SujetoObligado sujetoObligado, TipoSolicitudEnum tipoSolicitud);
	
	/**
	 * Obtiene la informaci�n de todos los tr�mites asociados a la
	 * solicitud cuyo identificador ha sido proporcionado.
	 * El Map contiene los siguiente valores:
	 *    KEY                         --->  VALOR
	 * solicitudData                        Objeto SujetoObligado con la informaci�n concentrada de todos los tr�mites
	 * isTramiteDenominacion				Boolean indica si la solicitud contiene el tr�mite de Raz�n Denominaci�n Social	
	 * isTramiteMedioContacto				Boolean indica si la solicitud contiene el tr�mite de Medio Contacto
	 * isTramiteRepresentanteLegal			Boolean indica si la solicitud contiene el tr�mite de Representante Legal
	 * isTramiteSocio						Boolean indica si la solicitud contiene el tr�mite de Socio
	 * isTramiteActaConstitutiva			Boolean indica si la solicitud contiene el tr�mite de Acta Constitutiva
	 * isTramiteRegistroSindicato			Boolean indica si la solicitud contiene el tr�mite de Registro Sindicato
	 * isTramiteCentroTrabajo				Boolean indica si la solicitud contiene el tr�mite de Centro de Trabajo
	 * 
	 * @author Hugo Martinez
	 * @Date 03/10/2012
	 * @param idSolicitud
	 * @return Map
	 */
	Map<String, Object> obtenerInformacionDeSolicitud(Long idSolicitud);
	
	/**
	 * 
	 * Retorna la subdelegaci�n en base al c�digo postal y codigo de asentamiento del domicilio proporcionado
	 * 
	 * Si alguno de los parametros no es proporcionado se env�a el c�digo de error correspondiente:
	 * parametro.codigo.postal.requerido � parametro.clave.asentamiento.requerido
	 * asegurese de tener una descripci�n internacionalizada para este codigo de error.
	 * 
	 * Si no existe un municipio inegi para el c�digo postal y cve de asentamiento 
	 * proporcionado se envia el c�digo: municipio.inegi.inexistente
	 * 
	 * Si no existe un municipio imss asociado al municipio inegi relacionado al cp proporcionado
	 * se envia el mensaje de error: municipio.imss.inexistente
	 * 
	 * Si no existe una subdelegaci�n configurada para el municipio imss relacionado con el municipio
	 * inegi al cual pertenece el codigo postal proporcionado se env�a el mensaje de error: subdelegacion.inexistente
	 * 
	 * @author Hugo Martinez
	 * @Date 11/10/2012
	 * @param domicilio Se requiere el c�digo Postal y la clave de asentamiento
	 * @return Subdelegacion
	 * @throws GestionPatronalBusinessException 
	 */
	Subdelegacion obtenerSubdelegacionPorDomicilio(Domicilio domicilio) throws GestionPatronalBusinessException;
	
	/**
	 * Env�a el acuse asociado a la solicitud proporcionada al correo electr�nico que tiene
	 * almacenado la persona como dato de contacto personal.
	 * @author Hugo Martinez
	 * @Date 12/10/2012
	 * @param sujetoTramite informaci�n general del patr�n: idPersona, TipoPersonaFiscal son datos requeridos
	 * @param idSolicitud Identificador de la solicitud
	 * @throws GestionPatronalBusinessException
	 */
	void notificarPorCorreoElectronico(SujetoObligado sujetoTramite, Long idSolicitud, Integer indFileToAttach) throws GestionPatronalBusinessException;
	
	void validarSolicitudCompletes(SujetoObligado sujetoTramite,
			TipoSolicitudEnum tipoSolicitud)
			throws GestionPatronalBusinessException;
	
	/**
	 * Datos acerca de si existe un tramite activo, los datos del tr�mite y si este est� ratificado o no
	 * @author Hugo Martinez
	 * @Date 22/11/2012
	 * @param tipoTramite
	 * @param usuario
	 * @param idSolicitud
	 * @return Map<String, Object> 
	 */
	Map<String, Object> cargarDatosDeTramite(TipoTramiteEnum tipoTramite, Persona persona, Long idSolicitud);

	/**
	 * Valida la condiciones para guardar/enviar la solicitud
	 * @author Hugo Martinez
	 * @Date 30/01/2013
	 * @param idSolicitud
	 * @param usuario
	 * @throws GestionPatronalBusinessException
	 */
	void validaCondicionesDeActualizacionDeSolicitud(Long idSolicitud,
			Usuario usuario, String rfc) throws GestionPatronalBusinessException;

	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 07/02/2013
	 * @param tramite
	 * @return
	 */
	TramiteSujetoObligado inicializarInformacionSujetoTramite(Tramite tramite);
		
	/**
	 * Crea una nueva solicitud con un tramite de alta patronal
	 * @author Hugo Martinez
	 * @Date 12/02/2013
	 * @param Solicitud
	 */
	Solicitud crearSolicitudDeAltaPatronal(SujetoObligado sujetoTramite, Usuario usuario, OrigenSolicitudEnum origenSolicitud)throws GestionPatronalBusinessException;
	
	/**
	 * Crea una nueva solicitud con un tramite de alta patronal
	 * @author Hugo Martinez
	 * @Date 12/02/2013
	 * @param Solicitud
	 */
	Solicitud crearSolicitudDeAltaPatronalConEstado(SujetoObligado sujetoTramite,EstadoSolicitudEnum estadoSol, Usuario usuario, OrigenSolicitudEnum origenSolicitud)throws GestionPatronalBusinessException;
	
	/**
	 * Actualiza la informaci�n de la solicitud
	 * @author Hugo Martinez
	 * @Date 12/02/2013
	 * @param solicitud
	 * @return Solicitud
	 */
	Solicitud actualizarSolicitudDeAltaPatronal(Solicitud solicitud, SujetoObligado sujetoTramite)throws GestionPatronalBusinessException;
	
	void finalizarSolicitudDeAltaPatronal(Solicitud solicitud,
			SujetoObligado sujetoTramite, FirmaElectronica firmaElectronica, boolean encolarSolicitud)
			throws GestionPatronalBusinessException,
			SolicitudNoEncontradaException, TramiteNoEncontradoException, SolicitudException;

	/**
	 * Crea de forma automatica una solicitud de baja con la informaci�n de la persona
	 * y la actividad econ�mica que esta registrada en el momento de la solicitud.
	 * 
	 * @author Hugo Martinez
	 * @Date 10/04/2013
	 * @param numeroRegistroPatronal
	 * @param idCausa Causa que origina la baja (CausaEnum.java)
	 * @param usuario usuario que solicita el movimiento de baja
	 * @return Solicitud
	 */
	Solicitud crearSolicitudAutomaticaDeBaja(String numeroRegistroPatronal, Integer idCausa, Usuario usuario)throws GestionPatronalBusinessException;
	
	/**
	 * Obtiene los datos de la subdelegacion
	 * @param cveIdSubdelegacion Identificador de subdelegacion
	 * @return Subdelegacion
	 */
	Subdelegacion obtenerSubdelegacion(Long cveIdSubdelegacion);

	/**
	 * Valida si la subdelegacion de origen es copatible de acuerdo con la reglas del imss
	 * con la subdelegaci�n destino.
	 * @param idSubdelegacionOrigen
	 * @param centroTrabajo
	 * @throws GestionPatronalBusinessException
	 */
	void validarSubdelegacion(Long idSubdelegacionOrigen, CentroTrabajo centroTrabajo)
			throws GestionPatronalBusinessException;
	
	/**
	 * Metodo que actualiza la informaci�n del centro de trabajo del registro patronal
	 * en base a la informaci�n del tr�mite
	 * @param sujetoObligado Informacion del centro de trabajo
	 * @param tramite Datos del tr�mite
	 * @param usuario usuario que solicita la modificacion
	 */
	void actualizarCentroTrabajo(SujetoObligado sujetoObligado,Tramite tramite, Usuario usuario, boolean notificarSindo);
	
	/**
	 * Genera el documento de acuse y aviso de datos patronales
	 * @param idSolicitud Identificador de la solicitud en la base de datos de delta
	 * @return byte[] PDF
	 */
	byte[] generarDocumentoModificacionDatosPatronales(Long idSolicitud, Integer idTipoDocumento);
	
	/**
	 * Genera el documento de acuse y aviso de datos patronales
	 * @param idSolicitud Identificador de la solicitud en la base de datos de delta
	 * @return byte[] PDF
	 */
	byte[] generarDocumentoModificacionDatosPatronales(Solicitud solicitud, Integer idTipoDocumento);
	
	/**
	 * 
	 * @param solicitud
	 * @param idTipoDocumento
	 * @return
	 * @throws GestionPatronalBusinessException
	 */
	byte[] obtenerDocumentoModificacionDatosPatronales(Long idSolicitud, Integer idTipoDocumento) throws GestionPatronalBusinessException;
	
	/**
	 * 
	 * @param solicitud
	 * @param idTipoDocumento
	 * @return
	 */
	byte[] obtenerDocumentoModificacionDatosPatronales(Solicitud solicitud, Integer idTipoDocumento) throws GestionPatronalBusinessException;
	
	/**
	 * 
	 * @param sujetoObligado
	 * @throws GestionPatronalBusinessException
	 */
	void asociarRepresentantesLegalesANuevoRegistroPatronal(SujetoObligado nuevoRegistroPatronal) throws GestionPatronalBusinessException;
	
	
	/**
	 * Este servicio valida que no exista ninguna baja por causa 251 dentro del grupo de registros 
	 * patronales pertenecientes al RFC de la persona f�sica proporcionado como par�metro de entrada.
	 * En caso de existir un registro patronal cuyo �ltimo movimiento sea baja por causa 251(falta de pago)
	 * se propaga una excepci�n indicando el registro patronal que se encuentra en esta condici�n.
	 * @param RFC Registro Federal de Contribuyente de la persona f�sica a verificar
	 * @return 
	 */
	void validarNoAdedudoPorBaja251(String RFC) throws GestionPatronalBusinessException;
	
	/**
	 * Este servicio valida que no exista ninguna baja por causa 251 dentro del grupo de registros 
	 * patronales pertenecientes al PATRON f�sica/moral.
	 * En caso de existir un registro patronal cuyo �ltimo movimiento sea baja por causa 251(falta de pago)
	 * se propaga una excepci�n indicando el registro patronal que se encuentra en esta condici�n.
	 * 
	 * @param SujetoObligado patron fisico/moral
	 * @return 
	 */
	void validarNoAdedudoPorBaja251(SujetoObligado sujetoObligado) throws GestionPatronalBusinessException;
	
	/**
	 * Contiene la l�gica para checar si el adeudo es por baja 251, recibe la
	 * lista de patrones, el rfc y una bandera para saber si debe de lanzar una
	 * excepci�n o en su defecto devolver la lista de patrones con baja 251
	 * 
	 * @param rfc
	 * @param registrosPatronalesConBaja
	 * @param lanzarError
	 * @return
	 * @throws GestionPatronalBusinessException
	 */
	List<SujetoObligado> validarNoAdedudoPorBaja251(String rfc,
			List<SujetoObligado> registrosPatronalesConBaja, boolean lanzarError)
			throws GestionPatronalBusinessException;
	
	/**
	 * Este servicio obtiene los patrones con baja por causa 251 dentro del
	 * grupo de registros patronales pertenecientes al PATRON f�sica/moral. En
	 * caso de existir se regresan en la lista en caso contrario la lista
	 * estar� vac�a.
	 * 
	 * @param SujetoObligado
	 *            patron fisico/moral
	 * @return
	 */
	List<SujetoObligado> obtenerPatronesConAdeudoPorBaja251(
			SujetoObligado sujetoObligado);
	
	
	/**
	 * Por medio de este servicio se genera una solicitud con la copia de toda la informaci�n del registro patronal base modalidad 30
	 * y se asigna la modalidad 14 para su posterior encolamiento y atenci�n por medio del osb.
	 * 
	 * Condiciones de Excepci�n:
	 * 1. La modalidad del registro patronal proporcionado es distinta a 30.
	 * 2. Longitud del n�mero de registro patronal erroneo (solo se permiten 10 u 11 digitos)
	 * 
	 * 
	 * @param numeroRegistroPatronalOrigen NRP origen a 11 digitos con modalidad 30
	 * @throws GestionPatronalBusinessException
	 */
	Solicitud generarSolicitudDeAltaPatronalParaRegistroDeEventualesCaneros(String numeroRegistroPatronalOrigen, Long tipoPersona, Usuario usuario)throws GestionPatronalBusinessException;
	


	/**
	 * Este servicio obtiene los registros patronales modalidad 30 que no cuentan con un registro patronal 14 asociado.
	 * 
	 * Solo aplica b�squeda para personas morales
	 * 
	 * @param cveIdPersonaMoral Identificador de la persona moral
	 * @return List<SujetoObligado>
	 */
	List<SujetoObligado> obtenerNRPCanerosCandidatosParaAmpliacionEventuales(Long cveIdPersonaMoral);
	
	/**
	 * Elimina todos los identificadores de los objetos de clasificacion y actividad economica para que el proceso de culminaci�n
	 * de alta patronal pueda realizar la inserci�n de la informaci�n de forma correcta.
	 * @param so
	 * @return SujetoObligado
	 */
	SujetoObligado obtenerClonDeSujetoObligadoParaAlta(SujetoObligado sujetoTramite)throws GestionPatronalBusinessException;
	
	/**
	 * Genera un objeto solicitud con la informaci�n proporcionada y le agregfa un tr�mite de alta
	 * @param estadoSolicitud
	 * @param usuario
	 * @param sujetoTramite
	 * @param origenSolicitud
	 * @return
	 */
	Solicitud inicializarSolicitudAlta(EstadoSolicitudEnum estadoSolicitud, Usuario usuario, 
			SujetoObligado sujetoTramite, OrigenSolicitudEnum origenSolicitud);


	/**
	 * Obtiene los datos basicos de la persona con base al tipo de persona y al identificador de la misma
	 * 
	 * @param persona
	 * @return persona
	 */
	Persona obtenerDatosBasicosPersona(Persona persona);

}
