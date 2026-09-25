package mx.gob.imss.ctirss.delta.tramite.service.business;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modulo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TramiteSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.tramite.service.entity.TramiteServiceEntityLocal;
import mx.gob.imss.ctirss.delta.tramite.service.interfaces.TramiteServiceBusinessRemote;

/**
 * 
 * @author Hugo Armando Martínez Chamónica
 *
 */
@Stateless(name="tramiteServiceBusiness" ,mappedName="tramiteServiceBusiness")
public class TramiteServiceBusiness extends AbstractServiceBusiness 
	implements  TramiteServiceBusinessRemote, TramiteServiceBusinessLocal{
	
	@EJB
	private  TramiteServiceEntityLocal tramiteServiceEntity;
	
	@Override
	public List<TramiteSolicitud> obtenerTramitesPorTipoPersonaFiscal() {
		
		return tramiteServiceEntity.consultarTramites();
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.tramite.service.interfaces.TramiteServiceBusinessRemote#crearNuevotramite(mx.gob.imss.ctirss.delta.model.gestion.patronal.Tramite)
	 */
	@Override
	public void crearNuevotramite(TramiteSolicitud tramite, TipoPersonaFiscal tipoPersona) {
		tramiteServiceEntity.insertarTramite(tramite, tipoPersona);
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.tramite.service.interfaces.TramiteServiceBusinessRemote#actualizarEstadoDelTramite(java.lang.Long, java.lang.Long, mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal)
	 */
	@Override
	public void actualizarEstadoDelTramite(Long idSolicitud, Long idPersona, Long idNuevoEstado,
			TipoPersonaFiscal tipPersona) {
		tramiteServiceEntity.actualizarEstadoTramite(idSolicitud, idPersona, idNuevoEstado, tipPersona);		
	}

	@Override
	public DatosSalidaPaginador<TramiteSolicitud> obtenerTramitesPorSujetoObligado(
			Long idPatronSujetoObligado) {
		List<TramiteSolicitud> tramites = tramiteServiceEntity.consultarTramitesPorSujetoObligado(idPatronSujetoObligado);
		DatosSalidaPaginador<TramiteSolicitud> response = new DatosSalidaPaginador<TramiteSolicitud>();
		response.setAaData(tramites);
		response.setiTotalDisplayRecords(0);
		response.setiTotalRecords(tramites.size());
		
		return response;
	}

	@Override
	public DatosSalidaPaginador<TramiteSolicitud> obtenerTramitesDeSujetoObligadoPorUsuario(
			Long idPatronSujetoObligado, Long idUsuario) {
		
		List<TramiteSolicitud> tramitesActivos =
				tramiteServiceEntity.consultarTramitesPorSujetoObligado(idPatronSujetoObligado);
		
		List<TramiteSolicitud> tramitesSujetoObligadoUsuario =
				tramiteServiceEntity.consultarTramitesActivosDeSujetoObligado(idPatronSujetoObligado);
		
		//Se incluye el manejo del mapa debido a que los trámites se pueden repetir 
		//pues pueden cumplir con ambas condiciones y se deben eliminar la duplicidad de trámites
		Map<Long, TramiteSolicitud> tramitesIntegrados = new HashMap<Long, TramiteSolicitud>();
		
		if(tramitesActivos!= null )
			for(TramiteSolicitud tramite:tramitesActivos){
				tramitesIntegrados.put(tramite.getTramiteId(), tramite);
			}
		if(tramitesSujetoObligadoUsuario!= null )
			for(TramiteSolicitud tramite:tramitesSujetoObligadoUsuario){
				tramitesIntegrados.put(tramite.getTramiteId(), tramite);
			}
		List<TramiteSolicitud> tramites = Collections.emptyList();
		
		if(tramitesIntegrados.values().size()>0){
			tramites = 
					Arrays.asList(tramitesIntegrados.values().toArray(new TramiteSolicitud[tramitesIntegrados.size()]));
		}
		
		DatosSalidaPaginador<TramiteSolicitud> response = new DatosSalidaPaginador<TramiteSolicitud>();
		response.setAaData(tramites);
		response.setiTotalDisplayRecords(0);
		response.setiTotalRecords(tramites.size());
		
		return response;
	}

	@Override
	public List<TramiteSolicitud> obtenerTramitesActivosPorSujetoObligado(
			Long cveIdPatronSujetoObligado) {
		return tramiteServiceEntity.consultarTramitesActivosDeSujetoObligado(cveIdPatronSujetoObligado);
	}

	@Override
	public DatosSalidaPaginador<TramiteSolicitud> consultarTramitesActivos() {
		
		
		List<TramiteSolicitud> tramitesActivos =
			tramiteServiceEntity.consultarTramitesActivos();
	
		// Se incluye el manejo del mapa debido a que los trámites se pueden
		// repetir
		// pues pueden cumplir con ambas condiciones y se deben eliminar la
		// duplicidad de trámites
		Map<Long, TramiteSolicitud> tramitesIntegrados = new HashMap<Long, TramiteSolicitud>();

		if (tramitesActivos != null)
			for (TramiteSolicitud tramite : tramitesActivos) {
				tramitesIntegrados.put(tramite.getTramiteId(), tramite);
			}

		List<TramiteSolicitud> tramites = Collections.emptyList();

		if (tramitesIntegrados.values().size() > 0) {
			tramites = Arrays.asList(tramitesIntegrados.values().toArray(
					new TramiteSolicitud[tramitesIntegrados.size()]));
		}

		DatosSalidaPaginador<TramiteSolicitud> response = new DatosSalidaPaginador<TramiteSolicitud>();
		response.setAaData(tramites);
		response.setiTotalDisplayRecords(0);
		response.setiTotalRecords(tramites.size());

		return response;
	}

	@Override
	public List<TipoTramite> listarTipoTramitesPorModulo(Long idModulo) {
		return tramiteServiceEntity.listarTipoTramitesPorModulo(idModulo);
	}
	
	
	/**
	 * Metodo que consulta los tramites asociados a una lista de modulos si el modulo es null devuevle
	 * el catalogo de tramites sin filtrar
	 * @param modulos
	 * @return
	 */
	@Override
	public List<TipoTramite> getTramitesByModulos(List<Modulo> modulos){
		return tramiteServiceEntity.getTramitesByModulos(modulos);
	}
	
	
}
