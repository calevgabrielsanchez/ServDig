/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo:ArticuloServiceEntity.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.articulo
 *  @Fecha:04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.articulo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.framework.base.exception.ModelAccessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.articulo.ArticuloServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.clasificacion.ArticuloModel;
import mx.gob.imss.ctirss.delta.model.clasificacion.DatosClem;
import mx.gob.imss.ctirss.delta.persistence.DicDelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DitArticulo;
import mx.gob.imss.ctirss.delta.persistence.DitDatosClem;

@Stateless
public class ArticuloServiceEntity extends AbstractServiceEntity implements
		ArticuloServiceEntityLocal {

	@EJB
	ArticuloServiceUtilityLocal articuloUtilityLocal ; 
	
	@Override
	public ArticuloModel buscarPorDelegacionSubdelegacion(ArticuloModel articulo) 
			throws PersistenceException {
		Query query= null;
		DitArticulo response = null;
		try{
			query = 
				em.createQuery(" Select a from DitArticulo a " +
							   " where a.ditDatosClem.dicSubdelegacion.cveIdSubdelegacion = :subdelegacionId " +
							   " and a.ditDatosClem.dicSubdelegacion.dicDelegacion.cveIdDelegacion = :delegacionId " +
							   " and a.numArticulo = :numArticulo");
			query.setParameter("subdelegacionId", articulo.getCveIdSubdelegacion().longValue());
			query.setParameter("delegacionId", articulo.getCveIdDelegacion().longValue());
			query.setParameter("numArticulo", articulo.getNumArticulo());
			
			try {
				response = (DitArticulo)query.getSingleResult();
			} catch (Exception e) {
				log.error(e);
			}
			
			if(response != null){
				articulo = articuloUtilityLocal.convertirEntityToModel(response);
			}

		}catch (Exception exc) {
			log.error("Error en buscarPorDelegacionSebdelegacion:"+exc.getMessage());
			exc.printStackTrace();
			throw new PersistenceException(exc.getMessage());
		}
		return articulo;
	}

	@Override
	public List<ArticuloModel> crearArticulos(DatosClem model) throws PersistenceException{
		List<ArticuloModel> response = new ArrayList<ArticuloModel>();
		List<DitArticulo> listaArticulos = new ArrayList<DitArticulo>();
		ArticuloModel articulo = null;
		DitDatosClem ditDatosClem=new DitDatosClem();
		ditDatosClem.setCveIdClem(model.getCveIdClem().longValue());
		try{	
			DitArticulo ditArticulo = new DitArticulo();
			DicDelegacion dicDelegacion = new DicDelegacion();
			dicDelegacion.setCveIdDelegacion(model.getCveDelegacion().longValue());
			DicSubdelegacion dicSubdelegacion = new DicSubdelegacion();
			dicSubdelegacion.setCveIdSubdelegacion(model.getCveSubdelegacion().longValue());
			dicSubdelegacion.setDicDelegacion(dicDelegacion);
			
			if(model.getIncisoArticulo155() != null){
				ditArticulo = new DitArticulo();
				ditArticulo.setNumArticulo(new BigDecimal(155));
				ditArticulo.setDesInciso(model.getIncisoArticulo155());
				ditArticulo.setDesFraccion(model.getDescFraccion115());
				ditArticulo.setDitDatosClem(ditDatosClem);/*NUEVO*/
				em.persist(ditArticulo);
				listaArticulos.add(ditArticulo);				
			}
			if(model.getCveArticulo20()!=null && !model.getCveArticulo20().isEmpty()){
				ditArticulo = new DitArticulo();
				ditArticulo.setNumArticulo(new BigDecimal(20));
				ditArticulo.setDesFraccion(model.getCveArticulo20());
				ditArticulo.setDitDatosClem(ditDatosClem);/*NUEVO*/
				em.persist(ditArticulo);
				listaArticulos.add(ditArticulo);
			}
			if(model.getCveArticulo26()!=null && !model.getCveArticulo26().isEmpty()){
				ditArticulo = new DitArticulo();
				ditArticulo.setNumArticulo(new BigDecimal(26));
				ditArticulo.setDesFraccion(model.getCveArticulo26());
				ditArticulo.setDitDatosClem(ditDatosClem);/*NUEVO*/
				em.persist(ditArticulo);
				listaArticulos.add(ditArticulo);
			}
			if(model.getCveArticulo28()!=null && !model.getCveArticulo28().isEmpty()){
				ditArticulo = new DitArticulo();
				ditArticulo.setNumArticulo(new BigDecimal(28));
				ditArticulo.setDesFraccion(model.getCveArticulo28());
				ditArticulo.setDitDatosClem(ditDatosClem);/*NUEVO*/
				em.persist(ditArticulo);
				listaArticulos.add(ditArticulo);
			}
 
			for (DitArticulo ditArticulo2 : listaArticulos){
				articulo = new ArticuloModel();
				articulo = articuloUtilityLocal.convertirEntityToModel(ditArticulo2);
				response.add(articulo);
			}

		}catch (Exception exc) {
			log.error("Error al guardar articulos : " + exc.getMessage());
			exc.printStackTrace();
			throw new PersistenceException(exc.getMessage());
		}
		return response;
	}

	@Override
	public List<ArticuloModel> buscarPorIdClem(Long idClem)throws PersistenceException{
		Query query= null;
		List<DitArticulo> lstDitArticulo = new ArrayList<DitArticulo>();
		List<ArticuloModel> lstAticuloModel=new ArrayList<ArticuloModel>();
		query=em.createQuery(" from DitArticulo a where a.ditDatosClem.cveIdClem = :idClem ");
		query.setParameter("idClem", idClem);
		lstDitArticulo=(List<DitArticulo>)query.getResultList();
		try{
			lstAticuloModel=articuloUtilityLocal.convertirEntityToModel(lstDitArticulo);
		}catch(ModelAccessException e){
			e.printStackTrace();
		}
		return lstAticuloModel;
	}

	@Override
	public Boolean actualizarArticulos(List<ArticuloModel> articulos, DatosClem datosClem)throws PersistenceException{
		DitArticulo ditArticulo = new DitArticulo();
		DitDatosClem ditDatosClem=new DitDatosClem();
		try{
			for(ArticuloModel a:articulos){
				ditArticulo.setCveIdArticulo(a.getCveIdArticulo().longValue());
				ditArticulo.setNumArticulo(a.getNumArticulo());
				ditDatosClem.setCveIdClem(a.getCveIdClem().longValue());
				ditArticulo.setDitDatosClem(ditDatosClem);
				switch(a.getNumArticulo().intValue()){
				case 20:
					ditArticulo.setDesFraccion(datosClem.getCveArticulo20());
					ditArticulo.setDesInciso(null);
					break;
				case 26:
					ditArticulo.setDesFraccion(datosClem.getCveArticulo26());
					ditArticulo.setDesInciso(null);
					break;
				case 28:
					ditArticulo.setDesFraccion(datosClem.getCveArticulo28());
					ditArticulo.setDesInciso(null);
					break;
				case 155:
					ditArticulo.setDesFraccion(datosClem.getDescFraccion115());
					ditArticulo.setDesInciso(datosClem.getIncisoArticulo155());
					break;
				}
				em.merge(ditArticulo);
			}
		}catch(Exception e){
			log.error("Error al actualizar Artículos:: ");
			log.error(e);
			return false;
		}
		return true;
	}
}