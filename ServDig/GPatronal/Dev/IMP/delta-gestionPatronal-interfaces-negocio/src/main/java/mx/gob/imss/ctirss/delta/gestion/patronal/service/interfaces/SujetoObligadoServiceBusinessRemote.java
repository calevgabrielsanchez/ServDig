package mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.gestion.patronal.RelacionConRegistroPatronalExisteException;
import mx.gob.imss.ctirss.delta.exception.individuo.AfectacionDatosPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Bien;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EquipoTransporte;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EscrituraConstitutiva;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MaquinariaEquipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MateriaPrima;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modulo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Personal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Proceso;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Producto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RegistroSindicato;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteMoral;
/**
 * 
 * @author Hugo Armando Mart?nez Cham?nica
 *
 */
@Remote
public interface SujetoObligadoServiceBusinessRemote {
	Boolean esRfcPermitido (String rfc);
	
	/**
	 * 
	 * @param sujetoObligado
	 * @param usuario
	 */
	void actualizarDenominacionRazonSocial(SujetoObligado sujetoObligado, Usuario usuario) throws GestionPatronalBusinessException; 
	
	/**
	 * 
	 * @param idSujetoObligado
	 * @param tipoPersona
	 * @return
	 */
	Long consultarClaveDomicilioFiscal(Long idSujetoObligado, TipoPersonaFiscal tipoPersona);
	
	/**
	 * 
	 * @param cveIdPatronSujetoObligado
	 * @return
	 */
	String obtenerDomicilioMigrado(Long cveIdPatronSujetoObligado);
	
	/**
	 * 
	 * @param idSujetoObligado
	 * @return
	 */
	Long consultarClaveDomicilioCentroTrabajo(Long idSujetoObligado);
	
	/**
	 * 
	 * @param domicilio
	 * @return
	 */
	CentroTrabajo convertirDomicilioACentroTrabajo(Domicilio domicilio);
	/**
	 * 
	 * @param domicilio
	 * @return
	 */
	DomicilioFiscal convertDomicilioToDomicilioFiscal(Domicilio domicilio);
	
	/**
	 * 
	 * @param registroPatronal
	 * @param tipoPersona
	 * @return
	 */
	SujetoObligado consultarPorRegistroPatronal(String registroPatronal, TipoPersonaFiscal tipoPersona);
	
	/**
	 * 
	 * @param sujetoObligado
	 * @param usuario
	 */
	void actualizarDenominacionRazonSocial(Tramite tramite, Usuario usuario, boolean notificarSINDO) throws GestionPatronalBusinessException; 
	
	/**
	 * 
	 * @param sujetoObligado
	 * @return
	 */
	SujetoObligado obtenerDetallesRegistroPatronal(SujetoObligado sujetoObligado) throws Exception;
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 22/08/2012
	 * @param escrituraConstitutiva
	 * @param usuario
	 * @return
	 * @throws GestionPatronalBusinessException
	 */
	EscrituraConstitutiva actualizarEscrituraConstitutiva(EscrituraConstitutiva escrituraConstitutiva) throws GestionPatronalBusinessException;
	
	/**
	 * Actualiza la informaci?n de la escritura constitutiva de la persona moral
	 * @author Hugo Martinez
	 * @Date 22/08/2012
	 * @param tramiteMoral
	 * @param usuario
	 * @throws GestionPatronalBusinessException
	 */
	void actualizarEscrituraConstitutiva(Tramite tramite, Usuario usuario) throws GestionPatronalBusinessException;
	
	/**
	 * Actualiza la informaci?n del tramite en base de datos
	 * @param registroSindicato
	 * @return
	 * @throws GestionPatronalBusinessException
	 */
	RegistroSindicato actualizarRegistroSindicato(RegistroSindicato registroSindicato) throws GestionPatronalBusinessException;
	
	/**
	 * Actualiza la informaci?n del tramite en base de datos
	 * @param cenetroTrabajo
	 * @return
	 * @throws GestionPatronalBusinessException
	 */
	
	CentroTrabajo actualizarCentroTrabajo(CentroTrabajo centroTrabajo, Usuario usuario , Subdelegacion subdelegacionDestino , boolean notificarSindo, String idMunicipioIMSS) throws GestionPatronalBusinessException;
	
	boolean validarSubDelOrigenSubDelDestino(long idSubdelegacionOrigen, long idSubdelegacionDestino);
	
	void actualizarNombreComercial(SujetoObligado sujetoObligado);
	
	/**
	 * Actualiza la informaci?n del tramite en base de datos
	 * @author Hugo Martinez
	 * @Date 22/08/2012
	 * @param tramite
	 * @param usuario
	 * @throws GestionPatronalBusinessException
	 */
	void actualizarRegistroSindicato(Tramite tramite, Usuario usuario)throws GestionPatronalBusinessException;
	
	/**
	 * Modifica los datos de contacto (Telefono fijo, telefono movil, correo electronico)
	 * @param sujetoObligado
	 */
	void modificarDatosContacto(SujetoObligado sujetoObligado, Usuario usuario) throws GestionPatronalBusinessException;
	
	/**
	 * A
	 * @author Hugo Martinez
	 * @Date 22/08/2012
	 * @param tramite
	 * @param usuario
	 * @throws GestionPatronalBusinessException
	 */
	void actualizarDatosDeContacto(Tramite tramite, Usuario usuario) throws GestionPatronalBusinessException;
	
	/**
	 * 
	 * @param sujetoObligado
	 * @return
	 * @throws GestionPatronalBusinessException
	 */
	List<SujetoObligado> obtenerDetalleSujetoObligado(SujetoObligado sujetoObligado) throws GestionPatronalBusinessException;
	
	/**
	 * 
	 * @param sujetoObligado
	 * @return
	 * @throws GestionPatronalBusinessException
	 */
	SujetoObligado obtenerDetallePrimerSujetoObligado(Persona persona) throws GestionPatronalBusinessException;
	
	/**
	 * 
	 * @param sujetoObligado
	 * @return
	 */
	SujetoObligado obtenerDatosGeneralesPatron(SujetoObligado sujetoObligado);

    SujetoObligado completarDatosGeneralesPatron(SujetoObligado sujetoObligado);
	
	/**
	 * 
	 * @param sujetoObligado
	 * @return
	 */
	SujetoObligado obtenerDetalleRP(SujetoObligado sujetoObligado);
	/**
	 * 
	 * @param sujetoObligado
	 * @return
	 */
	Long getCvePatronSujetoObligadoPorCveIdPatronGeneral(Long cveIdPatronGeneral);
	
	Long getCvePatronSujetoObligadoPorRP(String nrp);
	
	Modalidad getModalidad(String numModalidad);
	/**
	 * Obtiene el detalle del sujeto obligado con todos los datos
	 * requeridos por el m?dulo de clasificaci?n de empresas
	 * La informaci?n contenida es referente a:
	 * 
	 * Informaci?n general de la persona
	 * RFC, Registro Patronal, Tipo de Sociedad.
	 * 
	 * Domicilio Fiscal - Solo la clave del domicilio, para obtener el domicilio completo
	 * debe emplearse el servicio del m?dulo de domicilios.
	 * 
	 * Escritura Constitutiva
	 * Representante Legal
	 * Clasificaci?n
	 * 
	 * Datos de la Actividad econ?mica como:
	 * 
	 * Lista de productos, materias primas, maquinaria, equipo de transporte, procesos de trabajo
	 * personal y actividades complementarias(Transporte propio o ajeno, es distribuidor,
	 * servicios de instalaci?n o reparaci?n)
	 * 
	 * @param sujetoObligado TipoPersonaFiscal y IdPersona(Fisica/Moral) requeridos
	 * @return SujetoObligado
	 */
	SujetoObligado obtenerDetalleSujetoObligadoActividadEconomica(SujetoObligado sujetoObligado);
	
	/**
	 * 
	 * @param sujetoObligado
	 * @return
	 */
//	SujetoObligado obtenerDetalleSujetoObligadoDictamen(SujetoObligado sujetoObligado);

	/**
	 * 
	 * @param sujetoObligado
	 * @return
	 */
    SujetoObligado obtenerSujetoObligadoActividadEconomica(SujetoObligado sujetoObligado);
	/**
	 * 
	 * @param idPatronSujetoObligado
	 * @return
	 */
	List<Producto> obtenerListaProductoServicios(Long idPatronSujetoObligado);
	/**
	 * 
	 * @param idPatronSujetoObligado
	 * @return
	 */
	List<MateriaPrima> obtenerMateriaPrimaMateriales(Long idPatronSujetoObligado);
	/**
	 * 
	 * @param idPatronSujetoObligado
	 * @return
	 */
	List<MaquinariaEquipo> obtenerMaquinariaEquipo(Long idPatronSujetoObligado);
	
	/**
	 * 
	 * @param idPatronSujetoObligado
	 * @return
	 */
	List<EquipoTransporte> obtenerEquipoTranporte(Long idPatronSujetoObligado);
	
	/**
	 * 
	 * @param idPatronSujetoObligado
	 * @return
	 */
	List<Proceso> obtenerProcesos(Long idPatronSujetoObligado);
	
	/**
	 * 
	 * @param idPatronSujetoObligado
	 * @return
	 */
	List<Personal> obtenerPersonal(Long idPatronSujetoObligado);
	
	/**
	 * Obtiene el detalle B?sico del sujeto obligado, persona, registro patronal
	 * y clasificaci? acorde con el identificador proporcionado
	 * @param cveIdSujetoObligado
	 * @param tipoPersona
	 * @return Sujeto Obligado
	 */
	SujetoObligado obtenerDetalleRegistroPatronalPorClaveTipoPersona(Long cveIdSujetoObligado, TipoPersonaFiscal tipoPersona);
	
	
	
	/**
	 * Retorna la subdelegacion del patron sujeto obligado
	 * @param cveIdSujetoObligado
	 * @return Subdelegacion
	 */
	Subdelegacion obtenerSubdelegacion(Long cveIdSujetoObligado);
	
	/**
	 * Obtiene todos los sujeto obligados de los cuales la persona
	 * es el representante legal y por lo tanto
	 * puede acceder a la informaci?n
	 * @param cveIdRepresentanteLegal
	 * @return List
	 */
	DatosSalidaPaginador<Persona> obtenerPersonasRepresentadasPorRepresentanteLegal(Long cveIdPersona);
	
	/**
	 * Obtiene los datos generales de la persona
	 * @param cveIdPersona
	 * @return Persona
	 */
	Persona obtenerPersonaPorIdentificador(Long cveIdPersona);
	
	/**
	 * Obtiene los datos generales de la persona
	 * @param cveIdPersona
	 * @return Persona
	 */
	Persona obtenerPersonaMoralPorIdentificador(Long cveIdPersona);
	
	
	/**
	 * Obtiene los datos de contacto y los agrega a la persona proporcionada
	 * @param persona Persona(idPersona y tipoPersonaFiscal son requeridos)
	 * @return Persona 
	 */
	Persona obtenerMediosContactoPersona(Persona persona) throws GestionPatronalBusinessException;
	
	/**
	 * Obtiene los datos de bienes y los agrega a la persona proporcionada
	 * @param idPatronSujetoObligado 
	 * @return List<Bien>
	 */
	List<Bien> obtenerBienes(Long idPatronSujetoObligado);


	/**
	 * Obtiene el domicilio fiscal de una persona
	 * @param idPersona
	 * @param tipoPersona
	 * @return DomicilioFiscal
	 */
	Socio obtenerDomicilioFiscal(Long idPersona, String tipoPersona);
	
	/**
	 * Elimina las actas constitutivas de la persona
	 * @author Hugo Martinez
	 * @Date 28/08/2012
	 * @param idPersona
	 */
	void eliminarActaConstitutivaDePersona(Long idPersona);
	
	/**
	 * Elimina los registros de sindicato de la persona
	 * @author Hugo Martinez
	 * @Date 28/08/2012
	 * @param idPersona
	 */
	void eliminarSindicatoDePersona(Long idPersona);
	
	void actualizarRepresentanteLegal(Tramite tramite, Usuario usuario, Long idSolicitud,
			OrigenSolicitudEnum origenSolicitud) throws GestionPatronalBusinessException;

	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 07/09/2012
	 * @param input
	 * @return
	 */
	DatosSalidaPaginador<SujetoObligado> listarRegistrosPatronales(DatosEntradaPaginador<SujetoObligado> input);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 11/10/2012
	 * @param persona
	 * @return
	 */
	DomicilioFiscal obtenerDomicilioFiscalPatron(Persona persona);
	
	/**
	 * Obtiene la informaci?l patron en base al registro patronal 
	 * sin importar si se proporciona el registro patronal a 8, 10 u 11 posiciones
	 * @param numeroRegistroPatronal
	 * @return SujetoObligado
	 */
	SujetoObligado consultarPorNumeroRegistroPatronal(String numeroRegistroPatronal);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 07/05/2013
	 * @param rfc Rgesitro Federal de Causantes
	 * @return SujetoObligado [Informaci&oacute;n fiscal y registros patronales]
	 * @throws GestionPatronalBusinessException Si el rfc no existe o no esta registrado como un patr?n
	 */
	SujetoObligado obtenerDetallePorRFC(String rfc) throws GestionPatronalBusinessException;
	
	/**
	 * Obtiene el domicilio fiscal geografico si existe,
	 * en caso de no existir obtiene el domicilio del SAT
	 * @author Hugo Martinez
	 * @Date 07/05/2013
	 * @param persona Instancia de Fisica o Moral
	 * @return Domicilio
	 */
	Domicilio obtenerDomicilioFiscal(Persona persona);
	
	/**
	 * Obtiene el domicilio de centro de trabajo asociado al identificador del 
	 * registro patronal proporcionado
	 * @author Hugo Martinez
	 * @Date 16/05/2013
	 * @param cveIdSujetoObligado
	 * @return CentroTrabajo
	 */
	CentroTrabajo consultarDomicilioCentroTrabajo(Long cveIdSujetoObligado);
	
	/**
	 * Obtiene las subdelegaciones compatibles para cambio de domicilio, es decir,
	 * todas aquellas subdelegaciones para las cuales el cambio de domicilio no requiere de 
	 * un alta y una baja de registro patronal.
	 * @author Hugo Martinez
	 * @Date 16/05/2013
	 * @param cveIdSubdelegacion Identificador de subdelegaci&oacute;n origen
	 * @return List<Subdelegacion>
	 */
	List<Subdelegacion> obtenerSubdelegacionesCompatibles(Long cveIdSubdelegacion);
	
	/**
	 * Obtiene los registros patronales asociados a la persona cuyo identificador y tipo 
	 * de persona son proporcionados
	 * @param persona Objeto Persona con idPersona y tipoPersona requeridos
	 * @return List<SujetoObligado>
	 * @throws GestionPatronalBusinessException
	 */
	List<SujetoObligado> listarRegistrosPatronalesPorPersona(Persona persona) throws GestionPatronalBusinessException;
	/**
	 * Obtiene los registros patronales asociados a la persona cuyo identificador y tipo 
	 * de persona son proporcionados, solo retorna datos basicos del patron, nrp, cve, digito verificador y nombre comercial
	 * @param persona Objeto Persona con idPersona y tipoPersona requeridos
	 * @return List<SujetoObligado>
	 * @throws GestionPatronalBusinessException
	 */
	List<SujetoObligado> listarRegistrosPatronalesPorPersonaDatosBasicosPatron(Persona persona) throws GestionPatronalBusinessException;
	
	SujetoObligado getSujetoObligadoPorMunImmsDeleSubdeRFC(SujetoObligado sujetoObligado) throws GestionPatronalBusinessException,RelacionConRegistroPatronalExisteException;
	
	void finalizarSolicitudRecuperacionRP(Solicitud solicitud, FirmaElectronica firmaElectronica) throws GestionPatronalBusinessException, SolicitudNoEncontradaException, TramiteNoEncontradoException, SolicitudException;
	/**
	 * 
	 * @param tramiteFisica
	 * @param modulo
	 * @throws AfectacionDatosPersonaException
	 * @throws PersonaNoEncontradaException
	 */
	void afectarDatos(TramiteFisica tramiteFisica, Modulo modulo)throws AfectacionDatosPersonaException,
			PersonaNoEncontradaException;
	/**
	 * 
	 * @param sujetoObligadoPrev
	 * @param subdelegacion
	 * @param nuevaRazonSocial
	 */
	void queueMessageMovimiento05(SujetoObligado sujetoObligadoPrev,Subdelegacion subdelegacion, String nuevaRazonSocial);
	
	/**
	 * 
	 * @param tramite
	 * @param modulo
	 * @throws AfectacionDatosPersonaException
	 * @throws PersonaNoEncontradaException
	 */
	void afectarDatos(TramiteMoral tramite, Modulo modulo)throws AfectacionDatosPersonaException,
			PersonaNoEncontradaException;

    /**
    * @param registroPatronal registro patronal de 11 caracteres
    */
    void reEnviarMovimiento04(String registroPatronal) throws GestionPatronalBusinessException;

    /**
     * @param registroPatronal registro patronal de 11 caracteres
     * @param nuevoNombre nombre que se le cambiara al patron
     */
    void enviarMovimientoCambioNombre(String registroPatronal, String nuevoNombre) throws GestionPatronalBusinessException;

    Integer obtenerNumeroDeRPEnMunicipioIMSSPorFraccion(Long idPersona,
			Long tipoPersona, Long idMunicipio, Long idFraccion, Long idRegistroPatronalActual);
    
    /**
   	 * Metodo Consulta a los sujetos obligados pm o pf que a partir del RFC clase modalidad y municipio
   	 * @param SujetoObligado
   	 * @return List <SujetoObligado> con los registros que cumplan la condicion de busqueda
   	 */
   	List<SujetoObligado> getSujetoObligadoByRfcClaseMunicipioModalidad(
   			SujetoObligado obligado);
   	
   	/**
   	 * Obtiene el detalle de los registros patronales de un patr?asado en el tipo de 
   	 * persona e identificador (cve_id_persona/cve_id_persona_moral seg?n sea el caso)
   	 * @param sujetoObligado
   	 * @return List<SujetoObligado>
   	 * @throws GestionPatronalBusinessException
   	 */
	List<SujetoObligado> obtenerRegistrosPatronalesPrevios(
			SujetoObligado sujetoObligado)
			throws GestionPatronalBusinessException;

	/**
	 * Encola movimiento a sindo
	 * @param persona
	 * @param notificarSindo
	 * @throws GestionPatronalBusinessException
	 * @throws RFCNoLocalizadoEnEntidadExternaException 
	 */
	void enviarMovimientosDeActualizacionDatosGeneralesASindo(
			Persona persona, boolean notificarSindo) throws GestionPatronalBusinessException, RFCNoLocalizadoEnEntidadExternaException;

	/**
	 * Consulta empleada para conocer si existe un registro patronal dentro del municipio proporcionado
	 * asociado al rfc con la misma fracci?se excluye el registro patronal con el que se esta trabajando
	 * y que es proporcionado como par?tro (idRegistroPatronalActual) en caso de enviar nulo en este par?tro
	 * se tomar?en cuenta todos los registros patronales activos asociados a l RFC en la b?squeda.
	 * @param rfc RFC
	 * @param tipoPersona F?ca o Moral
	 * @param idMunicipio Identificador en la base delta del municipio donde se desea verificar la existencia de otro RP con la misma fracci?	 * @param idFraccion Identificador en la base delta de la fracci?
	 * @param idRegistroPatronalActual Identificador del registro patronal a excluir en la b?squeda
	 * @return Detalle de registro patronal.
	 */
	SujetoObligado obtenerRegistroPatronalEnMunicipioIMSSPorFraccion(String rfc,
			Long tipoPersona, Long idMunicipio, Long idFraccion, Long idRegistroPatronalActual);

	
	/**
	 * Consulta empleada para conocer si existe un registro patronal dentro del municipio proporcionado
	 * asociado al rfc y fraccion, se excluye el registro patronal con el que se esta trabajando
	 * y que es proporcionado como parametro (idRegistroPatronalActual) en caso de enviar nulo en este parametro
	 * se tomara en cuenta todos los registros patronales asociados a l RFC en la busqueda.
	 * @param rfc RFC
	 * @param tipoPersona Fisica o Moral
	 * @param idMunicipio Identificador en la base delta del municipio donde se desea verificar la existencia de otro RP con la misma fraccion	 
	 * @param idRegistroPatronalActual Identificador del registro patronal a excluir en la busqueda
	 * @return Detalle de registro patronal.
	 */	
	List<SujetoObligado> obtenerNRPEnMunicipioIMSSPorRFCyFraccion(String rfc,
			Long tipoPersona, Long idMunicipio, Long idFraccion, Long idRegistroPatronalActual);
	
	
	void actualizarMediosContactoCentroTrabajo(CentroTrabajo centroTrabajo);
	
	/**
	 * Obtienes solo la informaci?eneral del patr?n base al nrp
	 * @param registroPatronal
	 * @param tipoPersona
	 * @return
	 */
	SujetoObligado consultarPorRegistroPatronalBasic(String registroPatronal, TipoPersonaFiscal tipoPersona);
	
	/**
	 * Metodo que valida si la cveIdPersona tiene la marca activa RPC
	 * 
	 * @param cveIdPersona
	 * @param cveTipoPersona
	 * @return Integer
	 */
	void validaCveIdPersonaPorRegistroPatronalClaseActivo(String rfc, Integer cveTipoPersona) throws GestionPatronalBusinessException;

	/**
	 * Metodo para obtener las modalidades
	 * @param idPatronSujetoObligado
	 * @return
	 */
	Modalidad getModalidadPatron(Long idPatronSujetoObligado);
	
	/**
	 * 
	 * @param idEscritura
	 * @return
	 */
	EscrituraConstitutiva obtenerEscrituraConstitutivaPorId(Long idEscritura);

	/**
	 * Metodo para obtener los representantes legales asociados.
	 * @param cveIdPersona
	 * @param idTipoPersona
	 * @return
	 */
	List<RepresentanteLegal> obtenerRepresentantesLegales(Long idPersonaFM, Long idTipoPersona);
	
	DatosSalidaPaginador<RepresentanteLegal> paginarRepresentanteLegal(DatosEntradaPaginador<RepresentanteLegal> datatablein);
	/**
	 * Metodo para validar que la persona tenga RL asociados.
	 * @param cveIdPersona
	 * @param idTipoPersona
	 * @throws GestionPatronalBusinessException
	 */
	void validaRepresentanteLegalExistente(Long idPersonaFM, Long idTipoPersona) throws GestionPatronalBusinessException;
	
	/**
	 * Consulta basica para obtener solo los datos basicos del patron
	 * 
	 * Registro Patronal
	 * Modalidad(id, descripcion, num, siglas agregado medico)
	 * Digito Verigicador
	 * Datos de la persona fisica (si aplica)
	 * Razon social (persona modal - Si aplica)
	 * @param idPatronGeneral
	 * @return
	 */
	SujetoObligado getDatosBasicosPatronPorIdPatronGeneral(Long idPatronGeneral) throws GestionPatronalBusinessException;
	
	/**
	 * Consulta basica para obtener solo los datos basicos del patron
	 * 
	 * Registro Patronal
	 * Modalidad(id, descripcion, num, siglas agregado medico)
	 * Digito Verigicador
	 * Datos de la persona fisica (si aplica)
	 * Razon social (persona modal - Si aplica)
	 * @param idPatronSujetoObligado
	 * @return
	 */
	SujetoObligado getDatosBasicosPatronPorIdPatronSujetoObligado(Long idPatronSujetoObligado) throws GestionPatronalBusinessException;
	
	DomicilioFiscal obtenerDomFiscal(Persona persona);
	
	/**
	 * Metodo encargado de recuperar el domicilio del centro de trabajo del patron, si el objeto trae la marca de domiclio Migrado
	 * primero buscara el domicilio de SINDO, en caso de que no venga el valor buscara el domicilio con la norma tecnica
	 * en caso de que no se encuentre buscara nuevamente en el domiclio migrado esto ya que no todos los metodos de consulta setean dicho valor
	 * @param sujetoObligado
	 * @return
	 */
	CentroTrabajo obtenerDomicilioCentroTrabajoNormaTecnicaOMigradoSindo(SujetoObligado sujetoObligado) throws IllegalArgumentException;
	
	List<SujetoObligado> getListaPatronesPorPersona(Persona persona) throws GestionPatronalBusinessException;
	
	
	List<Modalidad> getModalidadades();
	
	Integer consultarMarcaRPC(String rfc, Integer tipoPersona);
	

	/**
	 * 
	 * @param cveIdSujetoObligado
	 * @return
	 */
	CentroTrabajo getCentroTrabajo(Long cveIdSujetoObligado);

	
	/**
	 * Metodo de obtener la prima historica mas cercana a la fecha surte efecto
	 * @param nrp 
	 * @return
	 */
	String consultaPrimaHistorica(String nrp, String fecha);
	
	/**
	 * Consulta los patrones de plataforma por RFC
	 * @param nrp
	 * @return
	 * @throws GestionPatronalBusinessException
	 */
	boolean isPatronPlataforma(String nrp) throws GestionPatronalBusinessException;
	
	
	boolean isPatronListaBlanca(String nrp) throws GestionPatronalBusinessException;
	
	Date obtenerFechaDespliegue(String nrp) throws GestionPatronalBusinessException;
}