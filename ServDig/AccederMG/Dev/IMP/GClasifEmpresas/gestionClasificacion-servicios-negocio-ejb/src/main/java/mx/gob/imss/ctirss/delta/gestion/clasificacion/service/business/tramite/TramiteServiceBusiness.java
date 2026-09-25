/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: TramiteServiceBusiness.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.tramite
 *  @Fecha:04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.tramite;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.tramite.TramiteServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.tramite.TramiteServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.sujetoobligado.SujetoObligadoServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;

@Stateless(name="tramiteServiceBusiness", mappedName="tramiteServiceBusiness")
public class TramiteServiceBusiness extends AbstractServiceBusiness implements
 		TramiteServiceBusinessRemote {
	
	@EJB
	private TramiteServiceEntityLocal tramiteEntity;

	@EJB
	private SujetoObligadoServiceUtilityLocal sujetoObligadoUtility;

	@Override
	public List<Tramite> consultaTramitePorSolicitud(Long cveIdSolicitud) throws Exception{
		return tramiteEntity.consultaTramitePorSolicitud(cveIdSolicitud);
	}
	
	@Override
	public List<TipoTramite> consultaTipoTramitePorModulo(Long cveIdModulo) throws Exception{
		return tramiteEntity.consultaTipoTramitePorModulo(cveIdModulo);
	}
	
	@Override
	public List<TipoTramite> consultaTipoTramitePorGrupoAnalisis(Long cveIdGrupo) throws Exception{
		return tramiteEntity.consultaTipoTramitePorGrupoAnalisis(cveIdGrupo);
	}
	
	@Override
	public TramiteSujetoObligado obtenerTramiteSujetoObligado(List<Tramite> tramites, Long tipoSolicitud){
		return sujetoObligadoUtility.obtenerTramiteSujetoObligado(tramites, tipoSolicitud);
	}
	
	@Override
	public Tramite obtenerTramite(List<Tramite> tramites, Long tipoSolicitud){
		return sujetoObligadoUtility.obtenerTramite(tramites, tipoSolicitud);
	}
	
}
