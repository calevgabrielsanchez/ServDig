/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: TramiteServiceEntity.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.tramite
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.tramite;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.tramite.TramiteServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.ModuloEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.persistence.DicModulo;
import mx.gob.imss.ctirss.delta.persistence.DicTipoTramite;
import mx.gob.imss.ctirss.delta.persistence.DitGrupoTramiteAnalisisCe;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;

@Stateless
public class TramiteServiceEntity extends AbstractServiceEntity implements TramiteServiceEntityLocal {
	
	@EJB
	private TramiteServiceUtilityLocal tramiteUtility;

	@SuppressWarnings("unchecked")
	@Override
	public List<Tramite> consultaTramitePorSolicitud(Long cveIdSolicitud) throws Exception{
		List<DitTramite> listaEntitie = new ArrayList<DitTramite>();
		List<Tramite> listaModel = new ArrayList<Tramite>();
		
		Query query = null;
		query = em.createQuery("from DitTramite t " +
								"where t.ditSolicitud.cveIdSolicitud= :id " +
								"and t.dicTipoTramite.cveIdTipoTramite in (" +
									"select tt.cveIdTipoTramite from DicTipoTramite tt, DicModulo m "
									+ "where m.cveIdModulo = :modulo and m in elements(tt.dicModulos)) ");
		query.setParameter("id", cveIdSolicitud);
		query.setParameter("modulo", ModuloEnum.GESTION_CLASIFICACION.getCodigo());
		listaEntitie = query.getResultList();
		
		if(listaEntitie != null){
			for (DitTramite ditTramite : listaEntitie) {
				Tramite tramite = new Tramite();
				tramite = tramiteUtility.convertirEntityToModel(ditTramite);
				listaModel.add(tramite);
			}
		}

		return listaModel;
	}
	
	@Override
	public List<TipoTramite> consultaTipoTramitePorModulo(Long cveIdModulo) throws Exception{
		List<DicTipoTramite> listaEntitie = new ArrayList<DicTipoTramite>();
		List<TipoTramite> listaModel = new ArrayList<TipoTramite>();
		
		Query query = null;
		query = em.createQuery("from DicTipoTramite tipoTramite " +
								"where tipoTramite.cveIdTipoTramite in (" +
									"select tt.cveIdTipoTramite from DicTipoTramite tt, DicModulo m " +
									"where m.cveIdModulo = :id and m in elements(tt.dicModulos)) " +
								"order by tipoTramite.desTipoTramite asc");
		
		//select order from ORDER as order,ITEM as item where item.itemID like 'ITM_01' and item in elements(order.items) ----t.ditSolicitud.cveIdSolicitud= :id
		
		query.setParameter("id", cveIdModulo);
		//query.setParameter("modulo", Constantes.MODULO_CLASIFICACION_EMPRESAS);
		listaEntitie = query.getResultList();
		
		if(listaEntitie != null){
			for (DicTipoTramite dicTipoTramite : listaEntitie) {
				TipoTramite tipoTramite = new TipoTramite();
				tipoTramite.setIdTipoTramite(dicTipoTramite.getCveIdTipoTramite());
				tipoTramite.setDescripcion(dicTipoTramite.getDesTipoTramite());
				listaModel.add(tipoTramite);
			}
		}

		return listaModel;
	}
	
	@Override
	public List<TipoTramite> consultaTipoTramitePorGrupoAnalisis(Long cveIdGrupo) throws Exception{
		List<DicTipoTramite> listaEntitie = new ArrayList<DicTipoTramite>();
		List<TipoTramite> listaModel = new ArrayList<TipoTramite>();

		StringBuilder hql = new StringBuilder(16);
		hql.append("select dtt from ")
		   .append(DitGrupoTramiteAnalisisCe.class.getName())
		   .append(" dgtac, ")
		   .append(DicModulo.class.getName())
		   .append(" dm ")
		   .append("inner join dm.dicTipoTramites dtt ")
		   .append("where dgtac.id.cveIdModulo = dm.cveIdModulo ")
		   .append("and dgtac.id.cveIdTipoTramite = dtt.cveIdTipoTramite ")
		   .append("and dgtac.id.cveIdModulo = :idModulo ")
		   .append("and dgtac.id.cveIdGrupoAnalisisCe = :idGrupo ");
		
		Query query = em.createQuery(hql.toString());
		query.setParameter("idModulo", new Long(ModuloEnum.GESTION_CLASIFICACION.getCodigo()));
		query.setParameter("idGrupo", cveIdGrupo);
		
		listaEntitie = query.getResultList();
		
		if(listaEntitie != null){
			for (DicTipoTramite dicTipoTramite : listaEntitie) {
				TipoTramite tipoTramite = new TipoTramite();
				tipoTramite.setIdTipoTramite(dicTipoTramite.getCveIdTipoTramite());
				tipoTramite.setDescripcion(dicTipoTramite.getDesTipoTramite());
				listaModel.add(tipoTramite);
			}
		}
		
		return listaModel;
	}

	@Override
	public Long consultaGrupoAnalisisPorTipoTramite(Long cveIdTipoTramite){
		DitGrupoTramiteAnalisisCe ditGrupoTramiteAnalisisCe=new DitGrupoTramiteAnalisisCe();
		Long idGrupoAnalisis=null;
		String sql="from DitGrupoTramiteAnalisisCe g where g.id.cveIdTipoTramite=:cveIdTipoTramite";
		Query query=em.createQuery(sql);
		query.setParameter("cveIdTipoTramite", cveIdTipoTramite);
		
		if(query.getSingleResult()!=null){
			ditGrupoTramiteAnalisisCe=(DitGrupoTramiteAnalisisCe)query.getSingleResult();
			idGrupoAnalisis=ditGrupoTramiteAnalisisCe.getId().getCveIdGrupoAnalisisCe();
		}
		return idGrupoAnalisis;
	}
}