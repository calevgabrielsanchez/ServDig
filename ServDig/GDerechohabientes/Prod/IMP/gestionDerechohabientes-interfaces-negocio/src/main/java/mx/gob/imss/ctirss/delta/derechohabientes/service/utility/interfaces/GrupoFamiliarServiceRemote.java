package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesWebSserviceException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.ActualizaCorreoIn;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.ActualizaCorreo;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliarTE;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliarTE;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.derechohabiente.SubEstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos.PrestacionesDTO;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos.ServiciosDTO;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.RegistroDto;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaDomicilio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.DetallePeriodoMovimientoAfiliatorioPatron;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

@Remote 
public interface GrupoFamiliarServiceRemote {
	
	/**
	 * Consulta que obtiene a los integrantes del grupo familiar de acuerdo al id del nss recibiento los siguientes parametros
	 * @param idAsignacionNSS - El id del nss
	 * @param mostrarAsegurado - incluir al asegurado o pensionado(true) o no (false)
	 * @param incluirDatosUmf - incluir los datos de adscripcion, de lo contrario solo regresara los basicos: nombre, idpersona, sexo, curp, fecha de nacimiento o
	 * mes y anio en su caso, idparentesco, parentesco y vigencia 
	 * @return
	 */
	List<GrupoFamiliar> findDatosBasicosIntegrantesGrupoByIdAsignacionNss(Long idAsignacionNSS, Boolean conVigencia,Boolean mostrarAsegurado, Boolean incluirDatosUmf)
	 throws DerechohabientesBusinessException, IllegalArgumentException ;
	/**
	 * Consulta que obtiene a los integrantes del grupo familiar de acuerdo al nss recibiento los siguientes parametros
	 * @param numNSS - el numero nss a 11 posiciones
	 * @param mostrarAsegurado - incluir al asegurado o pensionado(true) o no (false)
	 * @param incluirDatosUmf - incluir los datos de adscripcion, de lo contrario solo regresara los basicos: nombre, idpersona, sexo, curp, fecha de nacimiento o
	 * mes y anio en su caso, idparentesco, parentesco y vigencia 
	 * @return
	 */
	List<GrupoFamiliar> findDatosBasicosIntegrantesGrupoByNumNss(String numNSS, Boolean conVigencia, Boolean mostrarAsegurado, Boolean incluirDatosUmf)
	 throws DerechohabientesBusinessException, IllegalArgumentException ;
	CabezaGrupoFamiliar getCabezaWS(Long idAsignacion) throws Exception ;
	Boolean tienePatronImss(Long idAsignacionNSS) throws DerechohabientesBusinessException;
	List<GrupoFamiliar> findGrupoFamiliarPorEstados(Long idAsignacionNss, List<Long> idEstados) throws DerechohabientesBusinessException, Exception;
	List<GrupoFamiliar> findGruposFamiliaresPorPersonaYPersonaInteresada(Long idPersona, Long idPersonaInteresada) throws DerechohabientesBusinessException, IllegalArgumentException, Exception; 
	/**
	 * Se buscara si la persona esta activa o en baja en algun grupo familiar throws {@link DerechohabientesBusinessException}
	 * @param idPersona
	 * @param activo
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	Boolean personaRegistradaComoDerechohabiente(Long idPersona, Boolean activo) throws DerechohabientesBusinessException, IllegalArgumentException, Exception;
	/**
	 * Metodo para obtener los grupos famliares de una persona en donde se encuentra activo o en baja
	 * @param activo si es boolean se buscara al integrante en cualquier grupo en el que no este en baja y si es false se buscara en baja
	 * @param idPersona
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	List<GrupoFamiliar> getGruposFamiliaresPorPersona(Long idPersona, Boolean activo) throws DerechohabientesBusinessException, IllegalArgumentException, Exception;
	/**
	 * Obtiene el numero de integrantes registrados dentro de un grupo familiar por parentesco
	 * o si el parametro de parentesco va nulo, muestra el numero total de integrantes registrados en el grupo
	 * @param idAsignacionNss
	 * @param idParentesco - puede ir nulo y no se filtrarta por parentesco
	 * @return
	 */
	Long getNumeroIntegrantesRegistrsdosPorParentesco(Long idAsignacionNss, Long idParentesco);
	Long getNumeroDeIntegrantesPorListParentesco(Long idAsignacionNss, List<Long> idParentesco);
	
	GrupoFamiliar getIntegranteGrupoFamiliarPorIdPersona(Long idAsignacionNss, Long idPersona) throws DerechohabientesBusinessException;
	/**
	 * Con este metodo se obtendra el medico que se establecera por default cuando se seleccione una umf y ya haya alguien registrado ahi
	 * @param idAsignacionNss
	 * @param idUmf
	 * @return
	 */
	List<GrupoFamiliar> getFechaYMedicoExistenteEnUmf(Long idAsignacionNss, Long idUmf);
	GrupoFamiliar getIntegranteGrupoFamiliarByAsignacionNss(Long idAsignacionNss, Long idPersona,Long idEstadoDerechohabiente) throws DerechohabientesBusinessException,Exception;
	/**
	 * Utilizado para recuperar una lista de integrantes del grupo familiar de un asegurado especifico con un estado especifico
	 * 
	 * @param idPersona
	 * @param idEstadoDerechohabiente
	 * @return Integrantes del grupo familiar de un Asegurado
	 * @throws DerechohabientesBusinessException
	 */
	List<GrupoFamiliar> getIntegranteGrupoFamiliar(Long idPersona,Long idEstadoDerechohabiente) throws DerechohabientesBusinessException,Exception;
	/**
	 * Utilizado para recuperar informacion de la cabeza de grupo familiar, persona asegurado dentro del grupo.
	 * @param idAsignacionNss
	 * @return Informacion de la cabeza de un grupo familiar (Asegurado)
	 * @throws DerechohabientesBusinessException
	 */
	GrupoFamiliar getCabezaGrupoFamiliar(Long idAsignacionNss) throws DerechohabientesBusinessException,Exception;
	/**
	 * Utilizada para recuperar una lista de integrantes de un grupo familiar.
	 * @param idAsignacionNss
	 * @return Lista de integrantes de un GrupoFamiliar
	 * @throws DerechohabientesBusinessException
	 */
	List<GrupoFamiliar> findGrupoFamiliar(Long idAsignacionNss) throws DerechohabientesBusinessException,Exception;
	/**
	 * Utilizada para recuperar una lista de integrantes de un grupo familiar con un parentesco especificos con la cabeza de grupo
	 * (Asegurado)
	 * @param idAsignacionNss
	 * @param parentesco
	 * @return Lista de integrantes de un grupo familiar de un solo parentesco
	 * @throws DerechohabientesBusinessException
	 */
	List<GrupoFamiliar> findGrupoFamiliarByParentesco(Long idAsignacionNss,Long parentesco) throws DerechohabientesBusinessException,Exception;
	/**
	 * Utilizada para recuperar una lista de integrantes del un grupo familiar con un parentesco y estado especifico.
	 * @param idAsignacionNss
	 * @param parentesco
	 * @param estado
	 * @return Lista de integrantes de un grupo familiar de un solo parentesco y un estado
	 * @throws DerechohabientesBusinessException
	 */
	List<GrupoFamiliar> findGrupoFamiliarParentescoEstado(Long idAsignacionNss,Long parentesco, Long estado) throws DerechohabientesBusinessException,Exception;	
//	Boolean getCabezaGrupoFam(Asegurado asegurado);
	/**
	 * Utilizada para recuperar una lista de integrantes de un grupo familiar con un estado especifico 
	 * @param idAsignacionNss
	 * @param estado
	 * @return Lista de integrantes con un estado especfico
	 * @throws DerechohabientesBusinessException
	 */
	List<GrupoFamiliar> findGrupoFamiliarPorEstado(Long idAsignacionNss,Long estado) throws DerechohabientesBusinessException,Exception;
	/**
	 * Utilizada para la paginacion de un data table
	 * @param entrada
	 * @return Contador
	 * @throws DerechohabientesBusinessException
	 */
	DatosSalidaPaginador<GrupoFamiliar> paginarGrupoFamiliar(DatosEntradaPaginador<AsignacionNSS> entrada) throws DerechohabientesBusinessException,Exception;
	/**
	 * Utilizada para la paginacion de un data table para actualizacion de correo
	 * @param entrada
	 * @return Contador
	 * 
	 */
DatosSalidaPaginador<ActualizaCorreo> paginarActualizaCorreo(DatosEntradaPaginador<ActualizaCorreoIn> entrada) throws Exception;

	/**
	 * Utilizado para recuperar los numeros de seguro social permitidos para un usuario
	 * @param nss
	 * @param idPersona
	 * @return AsignacionNSS - numeros de seguro social
	 * @throws DerechohabientesBusinessException
	 */
	AsignacionNSS getAsignacionNss(String nss, long idPersona) throws DerechohabientesBusinessException,Exception;
	
	List<AsignacionNSS> getAsignacionNss(long idPersona) throws DerechohabientesBusinessException;
	
	/**
	 * Metodo para obtener el objeto asignacionNss a partir del id
	 * @param idAsignacionNss
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	AsignacionNSS getAsignacionNssByIdAsignacion(Long idAsignacionNss) throws DerechohabientesBusinessException;
	
	/**
	 * Utilizado para recuperar cualquier nss (Solo Funcionarios)
	 * @param nss
	 * @return AsignacionNSS - numeros de seguro social
	 * @throws DerechohabientesBusinessException
	 */
	AsignacionNSS getAsignacionNssSinPersona(String nss, Boolean consultaSegundoYTercerNivel) throws DerechohabientesBusinessException,Exception;
	
	/**
	 * Utilizado para recuperar cualquier nss de 2 y 3 er nivel
	 * @param nss
	 * @return GrupoFamiliar - grupofamiliar del WS con tiempos de espera
	 * @throws DerechohabientesBusinessException
	 */
	GrupoFamiliarTE getGpoFamSinPersona(String nss) throws DerechohabientesBusinessException,Exception;
	
	
	/**
	 * Utilizado para recuperar una lista de integrantes de un grupo familiar filtrado por un parentesco, estado y subestado.
	 * @param idAsignacionNss
	 * @param parentesco
	 * @param estado
	 * @param idSubestado
	 * @return Lista de integrantes filtrados por parentesco, estado y subestado
	 * @throws DerechohabientesBusinessException
	 */
	List<GrupoFamiliar> findGrupoFamiliarEstadoSubestado(
			Long idAsignacionNss, Long parentesco, Long estado,Long idSubestado) throws DerechohabientesBusinessException,Exception;
	/**
	 * Utilizado para recuperar una lista de integrantes de un grupo familiar filtrado por un parentesco, estado y subestado.
	 * @param idAsignacionNss
	 * @param parentesco
	 * @param estado
	 * @param idSubestado
	 * @return Lista de integrantes filtrados por parentesco, estado y subestado
	 * @throws DerechohabientesBusinessException
	 */
	List<GrupoFamiliar> findGrupoFamiliarProrroga(
			Long idAsignacionNss, Long parentesco, Long estado,Long idSubestado) throws DerechohabientesBusinessException, Exception;
	/**
	 * Busca si existe una solicitud de Registro con el NSS proporcionado
	 * @param idAsignacionNss
	 * @return 
	 */
	boolean existeSolicitudRegistro(String idAsignacionNss)  throws Exception ;
	
	/**
	 * Obtiene una lista de tramites correspondientes a un grupo familiar y un grupo de estados
	 * @param personas
	 * @param estados
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	List<Tramite> getTramitesPorEstadoGrupoFamiliar(AsignacionNSS asignacion, List<Long> estados) throws DerechohabientesBusinessException, Exception;
	
	/**
	 * Busca la cabeza de grupo familiar.
	 * @param idAsignacionNss
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws Exception 
	 */
	CabezaGrupoFamiliar cabezaGrupoFamiliar(Long idAsignacionNss) throws DerechohabientesBusinessException, Exception;
	
	/**
	 * Busca la cabeza de grupo familiar en el nuevo WS de Tiempos de espera.
	 * @param idAsignacionNss
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws Exception 
	 */
	CabezaGrupoFamiliarTE cabezaGrupoFamiliarTE(Long idAsignacionNss) throws DerechohabientesBusinessException, Exception;
	
	
	/**
	 * Obtiene los servicio del asegurado
	 * @param nss
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	List<ServiciosDTO> getServiciosGrupoFamiliar(Long idAsignacionNss) throws DerechohabientesBusinessException, Exception  ;
	
	/**
	 * Obtiene los servicio del asegurado
	 * @param nss
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	List<ServiciosDTO> getServiciosGrupoFamiliarByAsignacionNSS(AsignacionNSS asignacionNSS) throws DerechohabientesBusinessException, Exception  ;

	
	/**
	 * Metodo para obtener las modalidades activas de un asignacionNss
	 * @param idAsignacionNss
	 * @return
	 */
	List<Modalidad> getModalidadesActivas(Long idAsignacionNss) throws DerechohabientesBusinessException;
	/**
	 * Ontine los patrones relacionados a un asegurado
	 * @param nss
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	List<SujetoObligado> getPatronesAsegurado(AsignacionNSS nss) throws DerechohabientesBusinessException, Exception ; 
	
	List<Long> getListaIdsPatrones(AsignacionNSS nss) throws DerechohabientesBusinessException;
	
	/**
	 * 
	 * @param idPersona
	 * @param estados
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	GrupoFamiliar getIntegranteGrupoFamiliarByEstados(Long idPersona, List<Long> estados,Long idAsignacionNss) throws DerechohabientesBusinessException, Exception  ;

	/**
	 * Metodo que regresa a los integrantes del grupo familiar que aun no tengan medico asignado
	 * 
	 * @param idAsignacionNss
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	List<GrupoFamiliar> getIntegrantesSinMedico(String nss) throws DerechohabientesBusinessException, Exception ;
	
	List<Parentesco> getParentescos() throws DerechohabientesBusinessException, Exception ;
	
	/**
	 * Obtiene el ultimo tramite de una persona de una lista de tipos 
	 * @param idPersona
	 * @param tramites
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	Tramite getUltimoTramitePorTipos(Long idPersona, List<Long> tramites) throws DerechohabientesBusinessException, Exception; 
	Boolean existeIntegranteGF(Long idPersona, List<Long> parentescos, List<Long> estados) throws DerechohabientesBusinessException, Exception;
	GrupoFamiliar llenaGrupoFamiliar(Derechohabiente derecho, AsignacionNSS an, RegistroDto registro, EstadoDerechohabiente estadoDer, 
			 SubEstadoDerechohabiente subEstadoDer) throws Exception;
	Fisica validaPersonaIMSS(RegistroDto miRegistro) throws DerechohabientesBusinessException;
	/**
	 * Metodo para obtener a los integrantes de un grupo familiar ativos en una umf
	 * @param idAsignacionNss
	 * @param idUmf
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	List<GrupoFamiliar> findGrupoFamiliarActivoPorUmf(Long idAsignacionNss, Long idUmf) throws DerechohabientesBusinessException;
	Date getFechaCambioMedico(Long idAsignacionNss, Long idUmf) throws DerechohabientesBusinessException;
	List<Fisica> buscarPersonaPorCurpValidaExistenciaEnGrupo(Long idAsignacionNss, Fisica fisica) throws DerechohabientesBusinessException;
	Boolean esAseguradoOPatronORepresentanteLegal(Long idPersona)  throws DerechohabientesBusinessException, Exception ;
	Map<String, Boolean> getRolesPorPersona(Long idPersona, Boolean parentescoAsegurado) throws DerechohabientesBusinessException, Exception ;
	
	/*
	 * metodo que regresa un mapa con los siguientes atributos
	 * isAPRL - booleano que indica si el asegurado es alguno de los tres
	 * rol - String que indica el rol que tiene ya sea asegurado, patron o representante legal
	 */
	Map<String, Object> esAseguradoPatronORLConDescripcion(Long idPersona)  throws DerechohabientesBusinessException, Exception ;
	Boolean esAsegurado(Long idPersona) throws DerechohabientesBusinessException, Exception ;
	Boolean esPatron(Long idPersona) throws DerechohabientesBusinessException, Exception ;
	Boolean esRepresentanteLegal(Long idPersona) throws DerechohabientesBusinessException, Exception ;
	GrupoFamiliar getDatosWidgetVigencia(Long idPersona) throws DerechohabientesBusinessException;
	/**
	 * Busca los datos de vigencia una vez que ya se tiene el nss
	 * @param idPersona
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	GrupoFamiliar getDatosVigenciaPorNss(String nss) throws DerechohabientesBusinessException;
	GrupoFamiliar getDatosVigenciaPorIdAsignacion(Long idAsignacionNSS) throws DerechohabientesBusinessException;
	List<GrupoFamiliar> getGruposFamiliaresPorPersona(Long idPersona, String nssActual, Boolean incluirActual ) throws DerechohabientesBusinessException;
	
	/**
	 * Metodo que devuelve un grupo familiar en base al asignacion que recibe y setea atributo para indicar si ya esta
	 * registrado en BD
	 * @param idAsignacion
	 * @return GrupoFamiliar 
	 * @throws DerechohabientesBusinessException
	 * @throws Exception
	 */
	GrupoFamiliar getCabezaGrupaFamilarRegistrada(Long idAsignacion) throws DerechohabientesBusinessException, Exception;

	Solicitud guardarSolicitudConsultaVigencia(AsignacionNSS nss, Usuario usuario) throws DerechohabientesBusinessException, Exception;
	
	
	/**
	 * Metodo que recupera las prestaciones de un asegurado en base a a su modalidad
	 * @param idAsignacionNss
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws Exception
	 */
	List<PrestacionesDTO> getPrestacionesAsegurado(Long idAsignacionNss) throws DerechohabientesBusinessException, Exception;
	
	Boolean tieneDomicilioYUMF(Long idAsignacionNss, Long idPersona);
	
	/**
	 * Obtiene un parentesco en base al id 
	 * @param idParentesco
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws Exception
	 */
	public Parentesco obtenerParentesco(Long idParentesco) throws DerechohabientesBusinessException, Exception;
	
	
	/**
	 * Busca los integrantes en la base local y la vigencia mediante el WS
	 * 
	 * @param idAsignacionNss
	 * @param idParentescos
	 * @param consultaVigencia
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws Exception
	 */
	public List<GrupoFamiliar> findGrupoFamiliarPorParentescos(
			Long idAsignacionNss, List<Long> idParentescos,
			Boolean consultaVigencia) throws DerechohabientesBusinessException,
			Exception;

	
	/**
	 * Busca a un grupo de personas por id de asignacion en DitGrupoFamiliar
	 * 
	 * @param idPersonas
	 * @param idAsignacionNss
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	public List<GrupoFamiliar> obtenerIntegrantesEnLista(List<Long> idPersonas, Long idAsignacionNss) throws DerechohabientesBusinessException;
	
	
	/**
	 * Valida si el cambio de medico es menor a 365 d&iacute;as para la UMf del grupo familiar
	 * 
	 * @param request
	 * @param grupoFamiliar
	 * @return Map<String, Object> 
	 * 	<ul>
	 * 		<li>error : Boolean</li>
	 * 		<li>encontrado : Boolean</li>
	 *  	<li>medico: MedicoEnTurno </li>
	 *   	<li>fechaCambio: Date</li>
	 *    	<li>cambioPosible: Boolean</li>
	 *    	<li>fechaEncontrada: Boolean</li>
	 *	</ul>
	 */
	public Map<String,Object> buscarMedicoEnTurnoActivo(GrupoFamiliar grupoFamiliar);

	GrupoFamiliar getAseguradoInconsistente(String nss) throws DerechohabientesWebSserviceException;
	GrupoFamiliar getAseguradoInconsistenteTE(String nss) throws DerechohabientesWebSserviceException;
	AsignacionNSS getAsignacionNssSinPersonaCL3(String nss, Boolean consultaSegundoYTercerNivel) throws DerechohabientesBusinessException,Exception;
	Long getNumeroDeIntegrantesPorListParentescoCL3(Long idAsignacionNss, List<Long> idParentesco);
	
	/**
	 * Obtiene un integrante de BDTU sin consumir el WS de vigencia
	 * 
	 * @param idAsignacionNss
	 * @param idPersona
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws Exception
	 */
	public GrupoFamiliar getIntegranteGrupoFamiliarSinVigencia(Long idAsignacionNss, Long idPersona) throws DerechohabientesBusinessException, Exception;
	public GrupoFamiliar getIntegranteGrupoFamiliarSinVigenciaCL3(Long idAsignacionNss, Long idPersona) throws DerechohabientesBusinessException, Exception;
	public GrupoFamiliar getCabezaGrupaFamilarRegistrada(AsignacionNSS asignacionNss, CabezaGrupoFamiliar cabezaGrupoFamiliar) throws DerechohabientesBusinessException, Exception;

	
	/**
	 * Obtiene un grupo familiar con indicador de inconsistencia
	 * 
	 * @param nss
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws Exception
	 */
	public GrupoFamiliar getGrupoFamiliar(String nss, Boolean segundoYTercerNivel)
			throws DerechohabientesBusinessException, Exception; 
	
	public GrupoFamiliar getGrupoFamiliarTE(String nss)
			throws DerechohabientesBusinessException, Exception; 
	
	Boolean actualizarIDEESIncorrectos(Integer numeroFilas);	
	Boolean actualizarIDEESCL3Incorrectos(Integer numeroFilas);	
	Boolean actualizarIDEESAUX(Integer numeroFilas);	
	
	List<GrupoFamiliar> getIntegrantesPorCurp(Long idAsignacionNSS, String curp, Boolean consultarPorDatosBasicosRenapo)  throws DerechohabientesBusinessException ;
	
	Long generarArchivoConCurps(Integer numeroFilas, Long idMinimo);
	
	/**
	  * Metotodo encargado de validar si existe una persona en algun grupo familiar con la lista de parentescos que se enlista
	  * @param idPersona
	  * @param parentesco
	  * @return
	  * @throws DerechohabientesBusinessException
	  * @throws Exception
	  */
	 boolean validaPersonaExisteEnGruposFamiliaresPorParentesco(Long idPersona,	List<Long> parentesco) throws DerechohabientesBusinessException, Exception;
	 
	 GrupoFamiliar getInfoAsegurado(String nss) throws DerechohabientesWebSserviceException;
	 
	 void generarArchivoNSSVigentesDomicilio();
	 
	List<GrupoFamiliar> findDatosBasicosIntegrantesGrupoByNss(String numNSS)throws Exception;
			
	PersonaDomicilio findDomicilioByIdPersona(Long idPersona);
	
	 /**
     * Servicio que consulta en almacenes los ultimos 3 movimientos de los asegurados
     * devuelve las fechas del movimiento y los datos basicos del patron
     * acutalmente solo movimientos de baja
     * @param strNss String con el NSS a 11 posiciones
     * @return List<DetallePeriodoMovimientoAfiliatorioPatron> con el detalle del movimiento y el patron
     * @throws DerechohabientesWebSserviceException
     */
	List<DetallePeriodoMovimientoAfiliatorioPatron> getUltimosMovimientosPatronesAsegurado (String strNss)throws  DerechohabientesBusinessException, Exception;
	
	/**
	 * Metodo que regresa la informacion del integrante del grupo familiar relacionada a la vigencia y si tiene o no servicio medico
	 * @param nss
	 * @param idAsignacionNSS
	 * @param idPersona
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	GrupoFamiliar getVigenciaYservicioMedicoWsIntegranteGf(String nss, Long idAsignacionNSS, Long idPersona) throws DerechohabientesBusinessException;
	
		
}


