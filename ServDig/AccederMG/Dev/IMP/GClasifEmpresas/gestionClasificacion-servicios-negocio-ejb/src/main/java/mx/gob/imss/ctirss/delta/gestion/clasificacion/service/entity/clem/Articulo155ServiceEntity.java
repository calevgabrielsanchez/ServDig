/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Héctor Lara Andrés
 *  @Proyecto: delta
 *  @Archivo:Articulo155ServiceEntity.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clem
 *  @Fecha:17/08/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clem;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.clem.Articulo155ServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.clasificacion.Articulo155;
import mx.gob.imss.ctirss.delta.persistence.DicArticulo155;

@Stateless
public class Articulo155ServiceEntity extends AbstractServiceEntity implements Articulo155ServiceEntityLocal {
	
	@EJB
	Articulo155ServiceUtilityLocal articulo155ServiceUtility;
	
	@Override
	public List<Articulo155> getArticulo155(Long cveIdSubdelegacion){
		List<DicArticulo155> entity=null;
		List<Articulo155> lstArticulo155=new ArrayList<Articulo155>();
		Articulo155 articulo155=new Articulo155();
		Query query=null;
		String sql = "from DicArticulo155 a where a.dicSubdelegacion.cveIdSubdelegacion= :cveIdSubdelegacion";
		query=em.createQuery(sql);
		query.setParameter("cveIdSubdelegacion", cveIdSubdelegacion);
		
		entity=(List<DicArticulo155>)query.getResultList();
		
		if(entity!=null){
			for(DicArticulo155 dArt155:entity){
				articulo155=articulo155ServiceUtility.convertirEntityToModel(dArt155);
				lstArticulo155.add(articulo155);
			}
		}
		return  lstArticulo155;
	}
}