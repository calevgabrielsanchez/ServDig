package mx.gob.imss.ctirss.delta.derechohabientes.ws.bussiness;

import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesWebSserviceException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.DetallePeriodoMovimientoAfiliatorioPatron;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.service.ConsultaCabezaGFWSClient;
import mx.gob.imss.service.vigenciaderechohab.ConsultaVigenciaPorDhabienteWSClient;
import mx.gob.imss.service.vigenciagrupofamparen.ConsultaGFPorParentescoWSClient;
import mx.gob.imss.service.vigenciagrupofamparen.estado.ConsultaGFPorParentescoYEstadoWSClient;
import mx.gob.imss.services.ConsultaGFPorEstadosWSClient;
import mx.gob.imss.ultimospatrones.ws.InfoUltimosPatronesAseguradoWSClient;
import mx.gob.imss.webservices.patrones.servicio.ConsultaVigenciaPatronesWSClient;

@Stateless(name = "derechohabienteWSClientService", mappedName = "derechohabienteWSClientService")
public class DerechohabienteWSClientService extends AbstractServiceBusiness implements
		DerechohabienteWSClientRemote{
	private static final String LOG_WS = "Mensaje WS: ";
	@Override
	public List<GrupoFamiliar> getGrupoFamiliarPorEstado(Long idAsignacionNSS,
			Integer cveEstadoDerechohabiente)
			throws DerechohabientesWebSserviceException {
		
		log.debug(LOG_WS + " Consulta por estado " + cveEstadoDerechohabiente+ ", idAsignacionNSS: " + idAsignacionNSS + " ");
		return new ConsultaGFPorEstadosWSClient().getGrupoFamiliarPorEstado(
				idAsignacionNSS, cveEstadoDerechohabiente);

	}

	@Override
	public List<GrupoFamiliar> getGrupoFamiliarPorEstados(Long idAsignacionNSS,
			List<Integer> listCveEstadoDerechohabiente)
			throws DerechohabientesWebSserviceException {
		log.debug(LOG_WS + " Consulta por estados " + listCveEstadoDerechohabiente+ ", idAsignacionNSS: " + idAsignacionNSS + " ");
		
		return new ConsultaGFPorEstadosWSClient().getGrupoFamiliarPorEstados(
				idAsignacionNSS, listCveEstadoDerechohabiente);
	}

	@Override
	public List<GrupoFamiliar> getGrupoFamiliarPorEstadoySubestado(
			Long idAsignacionNSS, Integer cveEstadoDerechohabiente,
			Integer cveSubEstadoDerechohabiente)
			throws DerechohabientesWebSserviceException {
		
		log.debug(LOG_WS + " Consulta por estado , subestado. Estado" + cveEstadoDerechohabiente+ ", subestado:" +cveSubEstadoDerechohabiente+ " idAsignacionNSS: " + idAsignacionNSS + " ");
		
		return new ConsultaGFPorEstadosWSClient()
				.getGrupoFamiliarPorEstadoySubestado(idAsignacionNSS,
						cveEstadoDerechohabiente, cveSubEstadoDerechohabiente);
	}

	@Override
	public GrupoFamiliar getGrupoFamiliarPorDerechohabiente(
			Long idAsignacionNSS, Integer cveEstadoDerechohabiente)
			throws DerechohabientesWebSserviceException {
		log.debug(LOG_WS + " Consulta por  derechohabiente, idPersona: " + cveEstadoDerechohabiente+ ", idAsignacionNSS: " + idAsignacionNSS + " ");
		
		return new ConsultaVigenciaPorDhabienteWSClient()
				.getGrupoFamiliarPorDerechohabiente(idAsignacionNSS,
						cveEstadoDerechohabiente);
	}

	@Override
	public List<GrupoFamiliar> getGrupoFamiliarPorParentesco(
			Long idAsignacionNSS, Integer cveIdCalidadParentesco)
			throws DerechohabientesWebSserviceException {
		log.debug(LOG_WS + " Consulta por parentesco. Parentesco " + cveIdCalidadParentesco+ ", idAsignacionNSS: " + idAsignacionNSS + " ");
		
		return new ConsultaGFPorParentescoWSClient()
				.getGrupoFamiliarPorParentesco(idAsignacionNSS,
						cveIdCalidadParentesco);
	}

	@Override
	public List<GrupoFamiliar> getGrupoFamiliarPorParentescoYEstado(
			Long idAsignacionNSS, Integer cveIdCalidadParentesco,
			Integer cveEstadoDerechohabiente)
			throws DerechohabientesWebSserviceException {
		
		log.debug(LOG_WS + " Consulta por parentesco, estado. Parentesco " + cveIdCalidadParentesco+ " ,estado "+cveEstadoDerechohabiente+", idAsignacionNSS: " + idAsignacionNSS + " ");
		
		return new ConsultaGFPorParentescoYEstadoWSClient()
				.getGrupoFamiliarPorParentescoYEstado(idAsignacionNSS,
						cveIdCalidadParentesco, cveEstadoDerechohabiente);
	}

	@Override
	public CabezaGrupoFamiliar obtieneInfoCabGpoFam(Long idAsignacionNSS,
			Boolean objetoCompleto) throws DerechohabientesWebSserviceException {
		
		log.debug(LOG_WS + " Consulta cabeza GF. IdAsignacionNSS: " + idAsignacionNSS + " ");
		
		return new ConsultaCabezaGFWSClient().obtieneInfoCabGpoFam(
				idAsignacionNSS, objetoCompleto);
	}

	@Override
	public List<SujetoObligado> getPatronesVigentesPorAsignacionNSS(
			Long idAsignacionNSS) throws DerechohabientesWebSserviceException {
		log.debug(LOG_WS + " Consulta patrones. IdAsignacionNSS: " + idAsignacionNSS + " ");
		
		return new ConsultaVigenciaPatronesWSClient()
				.getPatronesVigentesPorAsignacionNSS(idAsignacionNSS);
	}


	@Override
	public List<Long> getIDsPatronesActivosPorAsignacionNSS(Long idAsignacionNSS)
			throws DerechohabientesWebSserviceException {
		
		log.debug(LOG_WS + " Consulta ids Patrones.  IdAsignacionNSS: " + idAsignacionNSS + " ");
		
		return new ConsultaVigenciaPatronesWSClient()
				.getIDsPatronesActivosPorAsignacionNSS(idAsignacionNSS);
	}
	
	 /**
     * Servicio que consulta en almacenes los ultimos 3 movimientos de los asegurados
     * acutalmente solo movimientos de baja
     * @param strNss String con el NSS a 11 posiciones
     * @return List<DetallePeriodoMovimientoAfiliatorioPatron> con el detalle del movimiento y el patron
     * @throws DerechohabientesWebSserviceException
     */
	@Override
	public List<DetallePeriodoMovimientoAfiliatorioPatron>   getUltimosMovimientosPatronesAsegurado (String strNss)
			throws DerechohabientesWebSserviceException{
		log.debug("entre al metodo para recuperar los movimientos patronales" + strNss);
		 return new InfoUltimosPatronesAseguradoWSClient().consultaUltimosPatronesAsegurado(strNss);

	}
	
}
