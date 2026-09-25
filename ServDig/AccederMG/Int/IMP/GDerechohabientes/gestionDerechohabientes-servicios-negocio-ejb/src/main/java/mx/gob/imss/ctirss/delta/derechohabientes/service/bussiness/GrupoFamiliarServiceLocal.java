package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesWebSserviceException;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos.PrestacionesDTO;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.DetallePeriodoMovimientoAfiliatorioPatron;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

@Local
public interface GrupoFamiliarServiceLocal {
	
	
	Boolean tienePatronImss(Long idAsignacionNSS) throws DerechohabientesBusinessException;
	Boolean esAsegurado(Long idPersona) throws DerechohabientesBusinessException, Exception ;
	Boolean esPatron(Long idPersona) throws DerechohabientesBusinessException, Exception ;
	Boolean esRepresentanteLegal(Long idPersona) throws DerechohabientesBusinessException, Exception ;
	GrupoFamiliar getIntegranteEnUmf(Long idUmf, Long idAsignacionNss, Long idPersonaAsegurado) throws DerechohabientesBusinessException;
	GrupoFamiliar getCabezaGrupoFamiliar(Long idAsignacionNss) throws DerechohabientesBusinessException, Exception;
	List<Fisica> buscarPersonaPorCurpValidaExistenciaEnGrupo(Long idAsignacionNss, Fisica fisica) throws DerechohabientesBusinessException;
	/**
	 * Busca la cabeza de grupo familiar.
	 * @param idAsignacionNss
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws Exception 
	 */
	CabezaGrupoFamiliar cabezaGrupoFamiliar(Long idAsignacionNss) throws DerechohabientesBusinessException, Exception;
	/**
	 * Obtiene los patrones activos del asegurado, consulta el webservice de patrones activos
	 * @param nss
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws Exception
	 */
	List<SujetoObligado> getPatronesAsegurado(AsignacionNSS nss) throws DerechohabientesBusinessException, Exception;
	
	/**
	 * Metodo que devuelve un grupo familiar en base al asignacion que recibe y setea atributo para indicar si ya esta
	 * registrado en BD
	 * @param idAsignacion
	 * @return GrupoFamiliar 
	 * @throws DerechohabientesBusinessException
	 * @throws Exception
	 */
	GrupoFamiliar getCabezaGrupaFamilarRegistrada(Long idAsignacion) throws DerechohabientesBusinessException, Exception;
	
	GrupoFamiliar getCabezaGrupaFamilarRegistrada(AsignacionNSS asignacionNss, CabezaGrupoFamiliar cabeza) throws DerechohabientesBusinessException ;
	/**
	 * Metodo para obtener las modalidades activas de un asignacionNss
	 * @param idAsignacionNss
	 * @return
	 */
	List<Modalidad> getModalidadesActivas(Long idAsignacionNss) throws DerechohabientesBusinessException;
	Map<String, Boolean> getRolesPorPersona(Long idPersona, Boolean parentescoAsegurado) throws DerechohabientesBusinessException, Exception ;
	
	/**
	 * Metodo que recupera las prestaciones de un asegurado en base a a su modalidad
	 * @param idAsignacionNss
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws Exception
	 */
	List<PrestacionesDTO> getPrestacionesAsegurado(Long idAsignacionNss) throws DerechohabientesBusinessException, Exception;
	
	
	List<GrupoFamiliar> obtenerIntegrantesEnLista(List<Long> idPersonas, Long idAsignacionNss) throws DerechohabientesBusinessException;
	List<GrupoFamiliar> findGrupoFamiliarPorParentescos(
			Long idAsignacionNss, List<Long> idParentescos,
			Boolean consultaVigencia) throws DerechohabientesBusinessException,
			Exception;
	
	List<GrupoFamiliar> getIntegrantesPorCurp(Long idAsignacionNSS, String curp, Boolean consultarPorDatosBasicosRenapo)  throws DerechohabientesBusinessException ;
	
	/**
	  * Metotodo encargado de validar si existe una persona en algun grupo familiar con la lista de parentescos que se enlista
	  * @param idPersona
	  * @param parentesco
	  * @return
	  * @throws DerechohabientesBusinessException
	  * @throws Exception
	  */
	 boolean validaPersonaExisteEnGruposFamiliaresPorParentesco(Long idPersona,	List<Long> parentesco) throws DerechohabientesBusinessException, Exception;
	 
	 /**
	  * Metodo para obtener el objeto asignacionNss a partir del id
	  * @param idAsignacionNss
	  * @return
	  * @throws DerechohabientesBusinessException
	  */
	 AsignacionNSS getAsignacionNssByIdAsignacion(Long idAsignacionNss) throws DerechohabientesBusinessException;
	 
	 List<AsignacionNSS> getAsignacionNss(long idPersona) throws DerechohabientesBusinessException;
	 
	 GrupoFamiliar getIntegranteGrupoFamiliarSinVigencia(Long idAsignacionNss, Long idPersona) throws Exception;
	 /**
	  * Obtiene el numero de integrantes registrados dentro de un grupo familiar por parentesco
	  * o si el parametro de parentesco va nulo, muestra el numero total de integrantes registrados en el grupo
	  * @param idAsignacionNss
	  * @param idParentesco - puede ir nulo y no se filtrarta por parentesco
	  * @return
	  */
	 Long getNumeroIntegrantesRegistrsdosPorParentesco(Long idAsignacionNss, Long idParentesco);
	 Long getNumeroDeIntegrantesPorListParentesco(Long idAsignacionNss, List<Long> idParentesco);
	 
	 Map<String,Object> buscarMedicoEnTurnoActivo(GrupoFamiliar grupoFamiliar);
	 
	 GrupoFamiliar getIntegranteGrupoFamiliarPorIdPersona(Long idAsignacionNss, Long idPersona) throws DerechohabientesBusinessException;
	 
	 
	 /**
	     * Servicio que consulta en almacenes los ultimos 3 movimientos de los asegurados
	     * devuelve las fechas del movimiento y los datos basicos del patron
	     * acutalmente solo movimientos de baja
	     * @param strNss String con el NSS a 11 posiciones
	     * @return List<DetallePeriodoMovimientoAfiliatorioPatron> con el detalle del movimiento y el patron
	     * @throws DerechohabientesWebSserviceException
	     */
	List<DetallePeriodoMovimientoAfiliatorioPatron> getUltimosMovimientosPatronesAsegurado (String strNss)throws  DerechohabientesBusinessException, Exception;
			
}