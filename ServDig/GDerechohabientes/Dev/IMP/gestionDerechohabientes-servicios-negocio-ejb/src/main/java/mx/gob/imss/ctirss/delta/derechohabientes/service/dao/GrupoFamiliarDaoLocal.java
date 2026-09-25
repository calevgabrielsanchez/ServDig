package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.AsignacionNSSNoLocalizadoException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.asegurado.ActualizaCorreoIn;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.ActualizaCorreo;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliarTE;
import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaDomicilio;
import mx.gob.imss.ctirss.delta.persistence.DitGrupoFamiliar;

@Local
public interface GrupoFamiliarDaoLocal {
	
	/**
	 * Consulta que obtiene a los integrantes del grupo familiar de acuerdo al id del nss recibiento los siguientes parametros
	 * @param idAsignacionNSS - El id del nss
	 * @param mostrarAsegurado - incluir al asegurado o pensionado(true) o no (false)
	 * @param incluirDatosUmf - incluir los datos de adscripcion, de lo contrario solo regresara los basicos: nombre, idpersona, sexo, curp, fecha de nacimiento o
	 * mes y anio en su caso, idparentesco, parentesco y vigencia 
	 * @return
	 */
	List<GrupoFamiliar> findDatosBasicosIntegrantesGrupoByIdAsignacionNss(Long idAsignacionNSS, Boolean conVigencia,Boolean mostrarAsegurado, Boolean incluirDatosUmf,
			Integer paginarInicio, Integer paginarFin)
	 throws DerechohabientesBusinessException, IllegalArgumentException ;
	/**
	 * Consulta que obtiene a los integrantes del grupo familiar de acuerdo al nss recibiento los siguientes parametros
	 * @param numNSS - el numero nss a 11 posiciones
	 * @param mostrarAsegurado - incluir al asegurado o pensionado(true) o no (false)
	 * @param incluirDatosUmf - incluir los datos de adscripcion, de lo contrario solo regresara los basicos: nombre, idpersona, sexo, curp, fecha de nacimiento o
	 * mes y anio en su caso, idparentesco, parentesco y vigencia 
	 * @return
	 */
	List<GrupoFamiliar> findDatosBasicosIntegrantesGrupoByNumNss(String numNSS, Boolean conVigencia, Boolean mostrarAsegurado, Boolean incluirDatosUmf,
			Integer paginarInicio, Integer paginarFin)
	 throws DerechohabientesBusinessException, IllegalArgumentException ;
	List<GrupoFamiliar> getIntegrantesEnBajaDesdeHaceXAnios(Long idAsignacionNss, List<Long> idParentesco, Integer numeroDeAniosAtras, Long[] tiposDeBaja);
	Long getNumeroRecienNacidos(Long idAsignacionNss);
	List<GrupoFamiliar> getIntegrantesRecienNacidos(Long idAsignacionNss);
	Boolean existeIntegranteRegistrado(Long idAsignacionNss, Long idPersona);
	PersonaDomicilio getPersonaFDom(Long idPersona);
	
	List<GrupoFamiliar> findIntegrantesSinDomicilio(Long idAsignacionNss, List<Long> personasExcluir)  throws Exception ;
	/**
	 * Metodo para obtener a los integrantes del grupo familiar que fueron registrados a traves de un medio
	 * que puede ser internet o ventanilla
	 * @param idAsignacionNss
	 * @param idOrigenSolicitud
	 * @return
	 * @throws Exception
	 */
	List<GrupoFamiliar> findIntegrantesRegistradosByOrigen(Long idAsignacionNss, Long idOrigenSolicitud) throws Exception;
	/**
	 * Obtiene la informacion del medico de un persona dentro del grupo familiar
	 * @param idAsignacionNss
	 * @param idPersona
	 * @return
	 */
	MedicoEnTurno getMedicoEnTurnoPorIntegrante(Long idAsignacionNss, Long idPersona) throws Exception;
	/**
	 * Obtiene cuantos integrantes estan registrados con el parentesco pasado como parametro
	 * el parentesco puede ser nulo, en caso de ser asi se consultara el numero de integrantes
	 * registrados en el grupo familiar
	 * @param idAsignacionNss
	 * @param idParentesco
	 * @return
	 */
	Long getNumeroDeIntegrantesPorParentesco(Long idAsignacionNss, Long idParentesco);
	/**
	 * Obtiene la calidad mas alta que se tiene registrada para un parentesco
	 * @param idAsignacionNss
	 * @param idParentesco
	 * @return
	 */
	Long getCalidadMasAltaRegistradaPorParentesco(Long idAsignacionNss, Long idParentesco);
	List<GrupoFamiliar> getIntegranteGrupoFamiliarEstado(Long idPersona, Long idEstadoDerechohabiente) throws DerechohabientesBusinessException, Exception;
	GrupoFamiliar getIntegranteGrupoFamiliarByAsignacionNss(Long idAsignacionNss,Long idPersona, Long idEstadoDerechohabiente) throws DerechohabientesBusinessException, Exception;
	GrupoFamiliar getIntegranteGrupoFamiliar(Long idAsignacionNss, Long idPersona) throws DerechohabientesBusinessException;
	GrupoFamiliar getCabezaGrupoFamiliar(Long idAsignacionNss, Long parentesco) throws DerechohabientesBusinessException, Exception;
	List<GrupoFamiliar> findGrupoFamiliarCircunscripcion(AsignacionNSS nss, EstadoDerechohabienteEnum estadoD, boolean tiene, boolean circunscripcion) throws DerechohabientesBusinessException, Exception;
	List<GrupoFamiliar> findGrupoFamiliarByNssWS(Long idAsignacionNss) throws DerechohabientesBusinessException, Exception;
	List<GrupoFamiliar> findGrupoFamiliarByNss(Long idAsignacionNss) throws DerechohabientesBusinessException, Exception;
	List<GrupoFamiliar> findGrupoFamiliarByParentescoNss(Long idAsignacionNss,Long parentesco) throws Exception;
	List<GrupoFamiliar> findGrupoFamiliar(Long idAsignacionNss) throws DerechohabientesBusinessException, Exception;
	List<GrupoFamiliar> findGrupoFamiliarByParentesco(Long idAsignacionNss,Long parentesco) throws DerechohabientesBusinessException, Exception;
	List<GrupoFamiliar> findGrupoFamiliarByParentescoWs(Long idAsignacionNss,Long parentesco) throws DerechohabientesBusinessException, Exception;
	List<GrupoFamiliar> findGrupoFamiliarParentescoEstado(Long idAsignacionNss,Long parentesco, Long estado) throws DerechohabientesBusinessException, Exception;
	List<GrupoFamiliar> findGrupoFamiliarPorEstado(Long idAsignacionNss, Long estado) throws DerechohabientesBusinessException, Exception;
	DatosSalidaPaginador<GrupoFamiliar> paginarGrupoFamiliar(DatosEntradaPaginador<AsignacionNSS> entrada, Long idEstado) throws DerechohabientesBusinessException, Exception;
	DatosSalidaPaginador<ActualizaCorreo> paginarActualizaCorreo(DatosEntradaPaginador<ActualizaCorreoIn> entrada) throws  Exception;
	AsignacionNSS getAsignacionNss(String nss, long idPersona) throws DerechohabientesBusinessException, Exception;
	List<AsignacionNSS> getAsignacionNss(Long idPersona) throws DerechohabientesBusinessException;
	AsignacionNSS getAsignacionNss(String nss) throws DerechohabientesBusinessException, AsignacionNSSNoLocalizadoException,  Exception;	
	AsignacionNSS getAsignacionNssByIdAsignacionNss(Long idAsignacionNss) throws DerechohabientesBusinessException;
	List<GrupoFamiliar> findGrupoFamiliarEstadoSubestado(Long idAsignacionNss, Long parentesco, Long estado,Long idSubestado) throws DerechohabientesBusinessException, Exception;
	GrupoFamiliar updateIntegrante(GrupoFamiliar integrante) throws DerechohabientesBusinessException, Exception;
    GrupoFamiliar updateIntegrante(GrupoFamiliar integrante, boolean afectarDomicilio) throws DerechohabientesBusinessException,Exception;
	List<GrupoFamiliar> findGrupoFamiliarByEstado(Long idAsignacionNss, List<Long> estados) throws DerechohabientesBusinessException, Exception;
	List<String> getMedioContacto(long idPersona, long tipoContacto)throws DerechohabientesBusinessException, Exception;
	Parentesco getParentescoIntegrante(String nss, Long idPersona) throws Exception;
	/**
	 * Obtiene un integrante del grupo familiar 
	 * @param idAsignacionNSS
	 * @param estados
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws Exception 
	 */
	GrupoFamiliar getIntegranteGrupoFamiliarByEstados(Long idPersona, List<Long> estados, Long idAsignacionNss) throws DerechohabientesBusinessException, Exception;
	Boolean existeIntegranteGF(Long idPersona, List<Long> parentescos, List<Long> estados) throws DerechohabientesBusinessException, Exception;
	List<DitGrupoFamiliar> findGrupoFamiliarbyAsentamiento(Asentamiento dom) throws Exception;
	
	List<GrupoFamiliar> findGrupoFamiliarSinMedico(String nss) throws DerechohabientesBusinessException, Exception ;
	/**
	 * Metodo para obtener a los integrantes sin umf
	 * @param idAsignacionNss
	 * @param idPersonaExcluir
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws Exception
	 */
	List<GrupoFamiliar> findGrupoFamiliarSinMedico(Long idAsignacionNss, Long idPersonaExcluir, Boolean conVigencia) throws DerechohabientesBusinessException, Exception ;
	
	CabezaGrupoFamiliar getCabezaGrupoFamiliarWS(long idAsignacionNSS) throws  Exception ;
	
	CabezaGrupoFamiliarTE getCabezaGrupoFamiliarWSTE(long idAsignacionNSS) throws  Exception ;
	/**
	 * Metodo encargado de buscar el grupo familiar a partir del NSS que se pasa como par�metro
	 * @param idAsignacionNSS
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	CabezaGrupoFamiliar getCabezaGrupoFamiliar(long idAsignacionNSS) throws  Exception ;
	CabezaGrupoFamiliarTE getCabezaGrupoFamiliarTE(long idAsignacionNSS) throws  Exception ;
	List<GrupoFamiliar> findIntegrantesByParentescoEstado(Long idPersona, Long idParentesco,Long idEstadoDerechohabiente, Long idAsignacionNssExcluir) throws Exception;
	List<GrupoFamiliar> findIntegrantesDuplicados(Long idPersona, List<Long> parentescos,Long idEstadoDerechohabiente) throws Exception;
	/**
	 * Metodo con el que se obtiene el medico que ya existe en la umf y en caso de ser 
	 * @param idAsignacionNss
	 * @param idUmf
	 * @return
	 */
	List<GrupoFamiliar> getMedicoYFechaEnUmf(Long idAsignacionNss, Long idUmf, Boolean fechaNula, Integer maxResults)  throws Exception;
	/**
	 * Metodo para buscar a los integrantes del grupo familiar que estan en una umf sin importar sus estados
	 * @param idAsignacionNss
	 * @param idUmf
	 * @return
	 */
	List<GrupoFamiliar> findIntegrantesEnUmf(Long idAsignacionNss, Long idUmf, Long idPersonaExcluir) throws Exception;
	List<GrupoFamiliar> findIntegrantesPorUmfEstado(Long idAsignacionNss, Long idUmf, List<Long> idEstado, Long idPersonaExcluir, List<Long> idsParentescos) throws Exception;
	List<GrupoFamiliar> findIntegrantesPorUmfEstadoIntegrantes(Long idAsignacionNss, Long idUmf, List<Long> idEstado, List<Long> idPersonasExcluir, List<Long> idsParentescos, Boolean conDomicilio) throws Exception;
	List<GrupoFamiliar> getIntegrantesGrupoFamiliarByAsignacionNss(Long idAsignacionNss,List<Long> idPersona, List<Long> idEstadoDerechohabiente) throws DerechohabientesBusinessException;
	List<GrupoFamiliar> getGruposFamiliaresPorPersona(Long idPersona, String nssActual, Boolean incluirActual)  throws DerechohabientesBusinessException, Exception ;
	
	
	/**
	 * Metodo que cuenta los integrantes de el un grupo familiar en base a lista de parentescos
	 * @param idAsignacionNss
	 * @param idParentesco
	 * @return
	 */
	Long getNumeroDeIntegrantesPorListParentesco(Long idAsignacionNss, List<Long> idParentesco);
	PersonaDomicilio savePersonaDomicilio(PersonaDomicilio miPersonaDomicilio) throws DerechohabientesBusinessException,Exception;
	List<GrupoFamiliar> findGrupoFamiliarPorParentescos(Long idAsignacionNss, List<Long> idParentesco, Boolean consultaVigencia) throws DerechohabientesBusinessException,Exception;
	Boolean validarDomicilioYUmfIntegrante(Long idAsignacionNss, Long idPersona, Boolean validarUmf, Boolean validarDomicilio);
	
	/**
	 * Valida si existe alg�n integrante dentro del grupo familiar con la CURP proporcionada
	 * 
	 * @param idAsignacionNss
	 * @param curp
	 * @return TRUE en caso de que existe un integrante con la curp proporcionada
	 * @throws DerechohabientesBusinessException
	 */
	public boolean existeIntegranteGrupoFamiliarPorCurp(Long idAsignacionNss, String curp) throws DerechohabientesBusinessException; 

	public GrupoFamiliar getIntegranteComplementado(GrupoFamiliar grupoWebService) throws DerechohabientesBusinessException;
	
	/**
	 * Obtiene los integrantes con estado vigente 
	 * 
	 * @param idAsignacionNss
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws Exception
	 */
	public List<GrupoFamiliar> findGrupoFamiliarByEstadoVigente(Long idAsignacionNss) throws DerechohabientesBusinessException,Exception;
	
	/**
	 * 
	 */
	public List<Integer> getEstadosVigenteDerechohabiente();
	
	
	/**
	 * Busca a un grupo de personas por id de asignaci&oacute;n en DitGrupoFamiliar
	 * 
	 * 
	 * @param idPersonas
	 * @param idAsignacionNss
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	public List<GrupoFamiliar> getIntegranteEnLista(List<Long> idPersonas, Long idAsignacionNss) throws DerechohabientesBusinessException;
	
	
	Boolean tienePatronImss(Long idAsignacionNSs) throws DerechohabientesBusinessException, Exception;
	
	GrupoFamiliar getIntegranteSinVigencia(Long idAsignacionNss, Long idPersona) throws Exception;
	
	public GrupoFamiliar getIntegranteComplementadoCL3(Long idAsignacionNss, Long idPersona) throws Exception;
	
	void updateIntegranteCL3(GrupoFamiliar integrante) throws DerechohabientesBusinessException, Exception;
	
	AsignacionNSS getAsignacionNssCL3(String nss) throws DerechohabientesBusinessException, AsignacionNSSNoLocalizadoException, Exception;	
	
	Long getNumeroDeIntegrantesPorListParentescoCL3(Long idAsignacionNss, List<Long> idParentesco);
	
	GrupoFamiliar getIntegranteSinVigenciaCL3(Long idAsignacionNss, Long idPersona) throws Exception;
	
	GrupoFamiliar getIntegranteGrupoFamiliarEstudiante(Long idAsignacionNss, Long idPersona) throws DerechohabientesBusinessException , Exception;
	
	/**
	 * Metodos para actualizar idees
	 * @param numeroFilas
	 * @return
	 */
	Boolean actualizarIDEE(Integer numeroFilas);
	
	Boolean actualizarIDEECL3(Integer numeroFilas);
	
	Boolean actualizarIDEETablaAux(Integer numeroFilas);
	
	GrupoFamiliar getIntegranteWs(Long idAsignacionNSS, Long idPersona) throws DerechohabientesBusinessException ;
	
	List<GrupoFamiliar> getIntegrantePorCurp(Long idAsignacionNSS, String curp) throws DerechohabientesBusinessException;
	
	Long generarArchivoConCurp(Integer numeroFilas,  Long idMinimo);
	
	 /**
	  * Metotodo encargado de validar si existe una persona en algun grupo familiar con la lista de parentescos que se enlista
	  * @param idPersona
	  * @param parentesco
	  * @return
	  * @throws DerechohabientesBusinessException
	  * @throws Exception
	  */
	 boolean validaPersonaExisteEnGruposFamiliaresPorParentesco(Long idPersona,	List<Long> parentesco) throws DerechohabientesBusinessException, Exception;
	
	 void actualizarFechaBaja(Long idAsignacionNSS, Long idPersona, Date fechaBaja) throws Exception;
	 
	 EstadoDerechohabiente getEstadoIntegrante(Long idAsignacionNSS, Long idPersona);
	 
	 /**
	  * Metodo que busca en bdtu el list de integrantes de grupos familiares por personas y list de parentescos, filtra la vigencia de almacenes
	  * del list de estados de vigencia, el cveIdNss es opcional si es diferente de nulo excluye de la busca las persona de ese grupo familiar
	  * @param lstIdPersona
	  * @param lstIdParentesco
	  * @param idEstadoDerechohabiente
	  * @param idAsignacionNssExcluir
	  * @return
	  * @throws Exception
	  */
	 List<GrupoFamiliar> findIntegrantesByParentescoEstado(List<Long> lstIdPersona, List<Long> lstIdParentesco, List<Long> lstIdEstadoDerechohabiente, Long idAsignacionNssExcluir) throws Exception;
	
	 List<GrupoFamiliar> findDatosBasicosintegrantes(String numNss)
			throws DerechohabientesBusinessException, Exception;
		
	 void getNSSActivosConDomicilio();

	/**
	  * Metodo que busca en bdtu si el pensionado tiene una pensión activa
	  * @param numNss = Numero de NSS
	  * @return false = no tiene pensión activa, true = tiene pensión activa
	  * @throws Exception
	  */	
	 Boolean findPensionActiva(String numNss) throws Exception;
	 
	 /**
	  * Metodo que consulta el ws de vigencia completo para buscar el id del integrante y regresar el elemento de servicio medico
	  * @param strNSS
	  * @param cveIdPesonaDerechohab
	  * @return
	  */
	 String getServicioMedicoDerechohabiente(String strNSS, Long cveIdPesonaDerechohab);
}


