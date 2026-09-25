/*
 * En esta interface se definen las firmas de los metodos que consumiran los web services externos
 * del modulo de derechohabientes.
 * Los web services devuelven unicamente listas de llaves de las entidades que el metodo indique, la implementacion de esta
 * interfaz debera contener la logica necesaria para devolver objetos del tipo que el metodo indique
 */
package mx.gob.imss.ctirss.delta.derechohabientes.ws.bussiness;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesWebSserviceException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.DetallePeriodoMovimientoAfiliatorioPatron;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

@Remote
public interface DerechohabienteWSClientRemote {

	/**
	 * 
	 * @param idAsignacionNSS
	 * @param cveEstadoDerechohabiente
	 */
	List<GrupoFamiliar> getGrupoFamiliarPorEstado(Long idAsignacionNSS,
			Integer cveEstadoDerechohabiente)
			throws DerechohabientesWebSserviceException;

	/**
	 * 
	 * @param idAsignacionNSS
	 * @param listCveEstadoDerechohabiente
	 */
	List<GrupoFamiliar> getGrupoFamiliarPorEstados(Long idAsignacionNSS,
			List<Integer> listCveEstadoDerechohabiente)
			throws DerechohabientesWebSserviceException;

	/**
	 * 
	 * @param idAsignacionNSS
	 * @param cveEstadoDerechohabiente
	 * @param cveSubEstadoDerechohabiente
	 */
	List<GrupoFamiliar> getGrupoFamiliarPorEstadoySubestado(Long idAsignacionNSS,
			Integer cveEstadoDerechohabiente,
			Integer cveSubEstadoDerechohabiente)
			throws DerechohabientesWebSserviceException;

	/**
	 * 
	 * @param idAsignacionNSS
	 * @param cveEstadoDerechohabiente
	 */
	GrupoFamiliar getGrupoFamiliarPorDerechohabiente(Long idAsignacionNSS,
			Integer cveEstadoDerechohabiente)
			throws DerechohabientesWebSserviceException;

	/**
	 * 
	 * @param idAsignacionNSS
	 * @param cveIdCalidadParentesco
	 */
	List<GrupoFamiliar> getGrupoFamiliarPorParentesco(Long idAsignacionNSS,
			Integer cveIdCalidadParentesco)
			throws DerechohabientesWebSserviceException;

	/**
	 * 
	 * @param idAsignacionNSS
	 * @param cveIdCalidadParentesco
	 * @param cveEstadoDerechohabiente
	 */
	List<GrupoFamiliar> getGrupoFamiliarPorParentescoYEstado(Long idAsignacionNSS,
			Integer cveIdCalidadParentesco, Integer cveEstadoDerechohabiente)
			throws DerechohabientesWebSserviceException;
	
	/**
	 * 
	 * @param idAsignacionNSS
	 * @throws DerechohabientesWebSserviceException
	 */
	CabezaGrupoFamiliar obtieneInfoCabGpoFam(Long idAsignacionNSS, Boolean objetoCompleto)throws DerechohabientesWebSserviceException;

	/**
	 * 
	 * @param idAsignacionNSS
	 */
	List<SujetoObligado> getPatronesVigentesPorAsignacionNSS(Long idAsignacionNSS)
			throws DerechohabientesWebSserviceException;

	/**
	 * Este metodo devuelve una lista de objetos de tipo SujetoObligado, con al
	 * menos cveIdPatron y cveIdModalidad
	 * 
	 * @return
	 * @throws DerechohabientesWebSserviceException
	 */
	// listPatronesVigentesbyNSS
	List<Long> getIDsPatronesActivosPorAsignacionNSS(Long idAsignacionNSS)
			throws DerechohabientesWebSserviceException;
	
    /**
     * Servicio que consulta en almacenes los ultimos 3 movimientos de los asegurados
     * acutalmente solo movimientos de baja
     * @param strNss String con el NSS a 11 posiciones
     * @return List<DetallePeriodoMovimientoAfiliatorioPatron> con el detalle del movimiento y el patron
     * @throws DerechohabientesWebSserviceException
     */
	List<DetallePeriodoMovimientoAfiliatorioPatron>   getUltimosMovimientosPatronesAsegurado (String strNss)
			throws DerechohabientesWebSserviceException;
	
}
