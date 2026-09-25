/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: FraccionEntity.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clasificacion
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clasificacion;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.clasificacion.FraccionServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clase;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;
import mx.gob.imss.ctirss.delta.persistence.DicClase;
import mx.gob.imss.ctirss.delta.persistence.DicDivision;
import mx.gob.imss.ctirss.delta.persistence.DicFraccion;
import mx.gob.imss.ctirss.delta.persistence.DicFraccionClase;
import mx.gob.imss.ctirss.delta.persistence.DicGrupo;
import mx.gob.imss.ctirss.delta.persistence.DitClasificacion;
import mx.gob.imss.ctirss.delta.persistence.DitHistEstatusAnalisis;

@Stateless
public class FraccionEntity extends AbstractServiceEntity implements FraccionEntityLocal{
	@EJB
	FraccionServiceUtilityLocal fraccionUtility;
	
	@Override	
	public Fraccion consultaPorId(Long cveIdFraccion) throws PersistenceException{
		DicFraccion entity = null;
		Fraccion model = new Fraccion();
		
		try{
			StringBuffer hql = new StringBuffer();
			hql.append(" from DicFraccion f where f.cveIdFraccion = :cveIdFraccion ");
 			
			Query query = this.getSession().createQuery(hql.toString());
			query.setParameter("cveIdFraccion", cveIdFraccion);
			entity = (DicFraccion) query.uniqueResult();
			model = fraccionUtility.convertirEntityToModel(entity);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("ERROR - [FraccionEntity-consultaPorId]: " + e.getMessage());
			throw new PersistenceException(e);
		}
		return model;
		}

	@Override
	public Fraccion consultarFraccionEquivalente(Fraccion fraccion)throws PersistenceException, Exception {
		log.info("hla::: entra a fraccionEquivalente");
		log.info("::::::::::::::::::: fraccion.getGrupo().getDivision().getId(): " + fraccion.getGrupo().getDivision().getId().toString());
		log.info("::::::::::::::::::: fraccion.getGrupo().getId(): " + fraccion.getGrupo().getId().toString());
		log.info("::::::::::::::::::: fraccion.getId(): " + fraccion.getId().toString());

		Criteria criteria = this.getSession().createCriteria(DicFraccion.class);
		criteria.add(Restrictions.eq("numFraccion", fraccion.getId().toString()));
		criteria.createAlias("dicGrupo", "grupo").add(Restrictions.eq("grupo.numGrupo", fraccion.getGrupo().getId().toString()));
		criteria.createAlias("grupo.dicDivision", "division").add(Restrictions.eq("division.numDivision", fraccion.getGrupo().getDivision().getId().toString()));
		DicFraccion entity = (DicFraccion)criteria.uniqueResult();
		Fraccion fraccionEquivalente = null;
		
		if(entity != null){
			log.info("Encontre la fraccion::: if fraccionequivalente");
			fraccionEquivalente = fraccionUtility.convertirEntityToModel(entity);			
		}else{
			log.info("No encontre la fraccion paso la que viene::: else fraccionequivalente");
			fraccionEquivalente = fraccion;
		}
		log.info("hla::: fin fraccionequivalente");
		
		return fraccionEquivalente;
	}
	
	@Override
	public Fraccion consultarFracEqPorNumero(Fraccion fraccion)throws PersistenceException, Exception {
		log.info("hla::: entra a fraccionEquivalente");
		log.info("::::::::::::::::::: fraccion.getGrupo().getDivision().getNumDivision(): " + fraccion.getGrupo().getDivision().getNumDivision());
		log.info("::::::::::::::::::: fraccion.getGrupo().getNumGrupo(): " + fraccion.getGrupo().getNumGrupo());
		log.info("::::::::::::::::::: fraccion.getNumFraccion(): " + fraccion.getNumFraccion());

		Criteria criteria = this.getSession().createCriteria(DicFraccion.class);
		criteria.add(Restrictions.eq("numFraccion", fraccion.getNumFraccion()));
		criteria.createAlias("dicGrupo", "grupo").add(Restrictions.eq("grupo.numGrupo", fraccion.getGrupo().getNumGrupo()));
		criteria.createAlias("grupo.dicDivision", "division").add(Restrictions.eq("division.numDivision", fraccion.getGrupo().getDivision().getNumDivision()));
		DicFraccion entity = (DicFraccion)criteria.uniqueResult();
		Fraccion fraccionEquivalente = null;
		
		if(entity != null){
			log.info("Encontre la fraccion::: if fraccionequivalente");
			fraccionEquivalente = fraccionUtility.convertirEntityToModel(entity);			
		}else{
			log.info("No encontre la fraccion paso la que viene::: else fraccionequivalente");
			fraccionEquivalente = fraccion;
		}
		log.info("hla::: fin fraccionequivalente");
		
		return fraccionEquivalente;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Fraccion obtenerFotoClasificacionActual(final long cveIdAnalisis) {
		Fraccion fraccion = null;
		final StringBuilder hql = new StringBuilder(16);
		hql.append("select df")
		   .append(" from ")
		   .append(DitHistEstatusAnalisis.class.getName())
		   .append(" dhea ")
		   .append("inner join dhea.dicFraccionDec df ")
		   .append("where dhea.ditAnalisisCe.cveIdAnalisis = :cveIdAnalisis ")
		   .append("and dhea.dicEstatusAnalisisCe.cveIdEstatusAnalisis = :cveIdEstatusAnalisis");
		
		final javax.persistence.Query query = em.createQuery(hql.toString());
		query.setParameter("cveIdAnalisis", cveIdAnalisis);
		query.setParameter("cveIdEstatusAnalisis", EstatusAnalisisEnum.PENDIENTE_DE_ANALISIS.getClave());
		final List<DicFraccion> fracciones = query.getResultList();
		
		if (!fracciones.isEmpty()) {
			fraccion = fraccionUtility.convertirEntityToModelFraccion(fracciones.get(0));
		}
		
		return fraccion;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Clasificacion obtenerClasificacionActual(
			final long cveIdPatronSujetoObligado) {
		Clasificacion clasificacion = null;
		final Criteria criteria = this.getSession().createCriteria(DitClasificacion.class);
		criteria.createAlias("ditPatronSujetoObligado", "ditPatronSujetoObligado")
		        .add(Restrictions.eq("ditPatronSujetoObligado.cveIdPatronSujetoObligado", cveIdPatronSujetoObligado));
		
		final List<DitClasificacion> clasificaciones = criteria.list();
		
		if (!clasificaciones.isEmpty()) {
			clasificacion = fraccionUtility.convertirEntityToModel(clasificaciones.get(0));
		}
		
		return clasificacion;
	}
	
	/**
	 * servicio encargado de recuperar el catalogo de clases de clasiificacion
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Clase> consultaCatalogoClase() throws PersistenceException{
		final Criteria criteria = this.getSession().createCriteria(DicClase.class);
		criteria.add(Restrictions.isNull("fecRegistroBaja"));	
		List<DicClase> lstDitClase = criteria.list();
		List<Clase> lstClase = new ArrayList<Clase>();
		if(!lstDitClase.isEmpty()) {
			for(DicClase clase : lstDitClase) {
				lstClase.add(fraccionUtility.convertirEntityToModelClase(clase));
			}
			
		}
		return lstClase;
	}
	
	
	
	/**
	 * servicio encargado de recuperar el catalogo de fracciones de clasiificacion por clase
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List <Fraccion> consultaCatalogoFraccionByIdClase(Long idClase) throws PersistenceException{
		
		final Criteria criteria = this.getSession().createCriteria(DicFraccionClase.class);
		criteria.createAlias("dicClase", "dicClase").add(Restrictions.eq("dicClase.cveIdClase", idClase));
		criteria.add(Restrictions.isNull("dicClase.fecRegistroBaja"));
		final List<DicFraccionClase> lstDitFraccionClase = criteria.list();
		List<Fraccion> lstFraccion = new ArrayList<Fraccion>();
		if(!lstDitFraccionClase.isEmpty()) {
			for(DicFraccionClase fraccionClase : lstDitFraccionClase) {
				lstFraccion.add(fraccionUtility.convertirEntityToModelFraccion(fraccionClase));
			}
			
		}
		return lstFraccion;
		
	}
	
	
	@Override
	public Clase consultaCatalogoClaseById(Long idClase) throws PersistenceException {
		//DicClase  dicClase =  em.find(DicClase.class, idClase);
		Criteria query = this.getSession().createCriteria(DicClase.class);
		DicClase  dicClase = (DicClase) query.uniqueResult();
		return fraccionUtility.convertirEntityToModelClase(dicClase);
		
		
		
	}
	
	/**
	 * servicio encargado de recuperar el catalogo de divisiones
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List <Division> consultaCatalogoDivision() throws PersistenceException{
		
		Criteria query = this.getSession().createCriteria(DicDivision.class);
		query.add(Restrictions.isNull("fecRegistroBaja"));
		List<DicDivision> lstDicDivision = query.list();
		
		List<Division> lstDivision = new ArrayList<Division>();
		if(!lstDicDivision.isEmpty()) {
			for(DicDivision dicDivsion : lstDicDivision) {
				lstDivision.add(fraccionUtility.convertirEntityToModelDivision(dicDivsion));
			}
		}
		return lstDivision;
		
	}
	
	/**
	 * servicio encargado de recuperar el catalogo de gropo by cveDivsion
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List <Grupo> consultaCatalogoGrupoByIdDivision(Long idDivision) throws PersistenceException{
		Criteria query = this.getSession().createCriteria(DicGrupo.class);
		query.createAlias("dicDivision", "dicDivision").add(Restrictions.eq("dicDivision.cveIdDivision", idDivision));
		query.add(Restrictions.isNull("fecRegistroBaja"));
		List<DicGrupo> lstDicGrupo = query.list();

		List<Grupo> lstGrupo = new ArrayList<Grupo>();
		if(!lstDicGrupo.isEmpty()) {
			for(DicGrupo dicGrupo : lstDicGrupo) {
				lstGrupo.add(fraccionUtility.convertirEntityToModelGrupo(dicGrupo));
			}
		}
		return lstGrupo;
	}
	
	/**
	 * servicio encargado de recuperar el catalogo de fracciones por grupo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List <Fraccion> consultaCatalogoFraccionByIdGrupo(Long idGrupo) throws PersistenceException{
		
		Criteria query = this.getSession().createCriteria(DicFraccion.class);
		query.createAlias("dicGrupo", "dicGrupo").add(Restrictions.eq("dicGrupo.cveIdGrupo", idGrupo));
		query.add(Restrictions.isNull("fecRegistroBaja"));
		List<DicFraccion> lstDicFraccion = query.list();

		List<Fraccion> lstFraccion = new ArrayList<Fraccion>();
		if(!lstDicFraccion.isEmpty()) {
			for(DicFraccion dicFracion : lstDicFraccion) {
				lstFraccion.add(fraccionUtility.convertirEntityToModelFraccion(dicFracion));
			}
		}
		return lstFraccion;

	}
	
	/**
	 * servicio encargado de recuperar el catalogo de fracciones por grupo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List <Fraccion> consultaCatalogoFraccionConClaseActivasByIdGrupo(Long idGrupo) throws PersistenceException{
		
		Criteria query = this.getSession().createCriteria(DicFraccion.class);
		query.createAlias("dicGrupo", "dicGrupo").add(Restrictions.eq("dicGrupo.cveIdGrupo", idGrupo));
		query.createAlias("dicFraccions", "dicFraccions").add(Restrictions.isNull("dicFraccions.fecFin"));
		query.add(Restrictions.isNull("fecRegistroBaja"));
		List<DicFraccion> lstDicFraccion = query.list();
		
		List<Fraccion> lstFraccion = new ArrayList<Fraccion>();
		if(!lstDicFraccion.isEmpty()) {
			for(DicFraccion dicFracion : lstDicFraccion) {
				lstFraccion.add(fraccionUtility.convertirEntityToModelFraccionClaseActiva(dicFracion));
			}
		}
		return lstFraccion;

	}
	
	
} 
