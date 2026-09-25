/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: ClasificacionEntity.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clasificacion
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clasificacion;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import org.hibernate.Query;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.clasificacion.ClasificacionServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicFraccion;
import mx.gob.imss.ctirss.delta.persistence.DicFraccionClase;
import mx.gob.imss.ctirss.delta.persistence.DitClasificacion;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;

@Stateless
public class ClasificacionEntity extends AbstractServiceEntity implements ClasificacionEntityLocal{
	@EJB
	ClasificacionServiceUtilityLocal clasificacionUtility;
	
	@Override
	public Clasificacion consultaPorClave(Clasificacion model)throws PersistenceException{
		DitClasificacion retVal = new DitClasificacion();
		DicFraccionClase dicFraccionClase=null;
		try{
            String q = new StringBuilder()
                .append("select fraccionClase\n")
                .append("from DicFraccionClase fraccionClase\n")
                .append("join fraccionClase.dicFraccion fraccion\n")
                .append("join fraccion.dicGrupo grupo\n")
                .append("join grupo.dicDivision division\n")
                .append("join fraccionClase.dicClase clase\n")
                .append("where fraccion.numFraccion = :numFraccion\n")
                .append("and grupo.numGrupo = :numGrupo\n")
                .append("and division.numDivision = :numDivision\n")
                //.append("and clase.desClase = :clase\n")
                .append("and fraccionClase.fecFin is null")
                .toString();

			Query query = this.getSession().createQuery(q);
			query.setParameter("numFraccion", model.getFraccion().getId());
			query.setParameter("numGrupo", model.getFraccion().getGrupo().getId());
			query.setParameter("numDivision", model.getFraccion().getGrupo().getDivision().getId());
			//query.setParameter("clase", model.getFraccion().getClase().getDescripcion());
			dicFraccionClase=(DicFraccionClase) query.uniqueResult();
			
			retVal.setDicFraccionClase(dicFraccionClase);
            DicFraccion dicFraccion = dicFraccionClase.getDicFraccion();
			if(dicFraccion.getDesFraccion()==null || dicFraccion.getDesFraccion().trim().equals("")){
				log.info("Esto es nulo:: " + model);
				return consultaPorNumFraccion(model);
			}else{
				log.info("Esto no es nulo" + model);
			}
			model = clasificacionUtility.convertirEntityToModel(retVal);
			log.info("Este te muestra todo::");
			log.info(model);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("ERROR - [ClasificacionEntity-consultaPorClave]: " + e.getMessage());
			throw new PersistenceException(e);
		}
		return model;
	}
	
	public void actualizaFraccion(Clasificacion clasificacion, Fraccion fraccion) throws PersistenceException{
		try{
			DitClasificacion entity = new DitClasificacion();
			String qlString = "from DitClasificacion c where c.id = :idClasificacion";
			javax.persistence.Query query = em.createQuery(qlString);
			query.setParameter("idClasificacion", clasificacion.getId());
			
//			try{
				entity = (DitClasificacion)query.getSingleResult();
//			} catch (Exception e) {
//				log.debug("**** No se encontro la clasificacion a actualizar");
//			}
		
			if(entity != null){
				log.debug("***** Actualizando clasificacion");
                DicFraccionClase dicFraccionClase = selectDicFraccionClase(fraccion);
				entity.setCveIdClasificacion(clasificacion.getId());
                entity.setDicFraccionClase(dicFraccionClase);
				em.persist(entity);//
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("ERROR - [ClasificacionEntity-actualizaFraccion]: " + e.getMessage());
			throw new PersistenceException(e);
		}
	}

	
	public void actualizaFraccionyPrima(Clasificacion clasificacion, Fraccion fraccion) throws PersistenceException{
		try{
			DitClasificacion entity = new DitClasificacion();
			String qlString = "from DitClasificacion c where c.id = :idClasificacion";
			javax.persistence.Query query = em.createQuery(qlString);
			query.setParameter("idClasificacion", clasificacion.getId());
			
			entity = (DitClasificacion)query.getSingleResult();
		
			if(entity != null){
				log.debug("***** Actualizando clasificacion y prima");
                DicFraccionClase dicFraccionClase = selectDicFraccionClase(fraccion);
				entity.setCveIdClasificacion(clasificacion.getId());
                entity.setDicFraccionClase(dicFraccionClase);
                entity.setNumPrimaPago(fraccion.getPrimaSRT());
                entity.setFecRegistroActualizado(new Date());
				em.persist(entity);
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("ERROR - [ClasificacionEntity-actualizaFraccionyPrima]: " + e.getMessage());
			throw new PersistenceException(e);
		}
	}	

	
	public void actualizaSolicitudyTramite(String cveIdSolicitud, Long cveIdEstadoSol, Long cveIdEstadoTram) throws PersistenceException{
		try{
			DitSolicitud entitySol = new DitSolicitud();
			String qlString = "from DitSolicitud c where c.cveIdSolicitud = :cveIdSolicitud";
			javax.persistence.Query query = em.createQuery(qlString);
			query.setParameter("cveIdSolicitud", cveIdSolicitud);
			
			entitySol = (DitSolicitud)query.getSingleResult();
		
			if(entitySol != null){
				log.debug("***** Actualizando solicitud " + cveIdSolicitud);
				DicEstadoSolicitud edoSol = new DicEstadoSolicitud();
				edoSol.setCveIdEstadoSolicitud(cveIdEstadoSol);				
                entitySol.setDicEstadoSolicitud(edoSol);
                entitySol.setFecRegistroActualizado(new Date());
                
                List<DitTramite> tramites =  entitySol.getDitTramites();
                for (Iterator<DitTramite> iterator = tramites.iterator(); iterator.hasNext();) {
					DitTramite ditTramite = iterator.next();
					DicEstadoTramite edoTram = new DicEstadoTramite();
					edoTram.setCveIdEstadoTramite(cveIdEstadoTram);
					ditTramite.setDicEstadoTramite(edoTram);
					ditTramite.setFecRegistroActualizado(new Date());
				}                
                entitySol.setDitTramites(tramites);                
				em.persist(entitySol);
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("ERROR - [ClasificacionEntity-actualizaFraccionyPrima]: " + e.getMessage());
			throw new PersistenceException(e);
		}
	}	
	
	
    public DicFraccionClase selectDicFraccionClase(Fraccion fraccion) {
        javax.persistence.Query query = em.createQuery(
                new StringBuilder("from DicFraccionClase fraccionClase\n")
                .append("where fraccionClase.dicFraccion.cveIdFraccion = :idFraccion\n")
                .append("and fraccionClase.dicClase.cveIdClase = :idClase\n")
                .append("and fraccionClase.fecFin is null")
                .toString());
        query.setParameter("idFraccion", fraccion.getId());
        query.setParameter("idClase", fraccion.getClase().getClave());

        return (DicFraccionClase)query.getSingleResult();
    }
	

	public void elimina(long idClasificacion) throws PersistenceException{
		DitClasificacion entity = null;
		String qlString = "from DitClasificacion c where c.id = :idClasificacion";

		try {
			javax.persistence.Query query = em.createQuery(qlString);
			query.setParameter("idClasificacion", idClasificacion);
			
			try{
				entity = (DitClasificacion)query.getSingleResult();
			} catch (Exception e) {
				log.debug("**** No se encontro la clasificacion a borrar");
			}
		
			if(entity != null){
				log.debug("***** Borrando clasificacion");
				em.remove(entity);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("ERROR - [ClasificacionEntity-elimina]: " + e.getMessage());
			throw new PersistenceException(e);
		}
	}
	
	public Clasificacion consultaPorNumFraccion(Clasificacion model){
		DitClasificacion retVal = new DitClasificacion();
		DicFraccionClase dicFraccionClase=null;
		try{
            String q = new StringBuilder()
                .append("select fraccionClase\n")
                .append("from DicFraccionClase fraccionClase\n")
                .append("join fraccionClase.dicFraccion fraccion\n")
                //.append("join fraccion.dicGrupo grupo\n")
                //.append("join grupo.dicDivision\n")
                .append("where fraccion.numFraccion = :numFraccion").toString();

			Query query = this.getSession().createQuery(q);
			query.setParameter("numFraccion", model.getFraccion().getGrupo().getDivision().getId()
					+ model.getFraccion().getGrupo().getId()
					+ model.getFraccion().getId());
			dicFraccionClase=(DicFraccionClase) query.uniqueResult();
			retVal.setDicFraccionClase(dicFraccionClase);
			model = clasificacionUtility.convertirEntityToModel(retVal);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("ERROR - [ClasificacionEntity-consultaPorClave]: " + e.getMessage());
			e.printStackTrace();
		}
		return model;
	}
	
	@Override
	public Clasificacion consultaPorId(Clasificacion model)throws PersistenceException{
		DitClasificacion retVal = new DitClasificacion();
		DicFraccionClase dicFraccionClase=null;
		try{
            String q = new StringBuilder()
                .append("select fraccionClase\n")
                .append("from DicFraccionClase fraccionClase\n")
                .append("join fraccionClase.dicFraccion fraccion\n")
                .append("join fraccion.dicGrupo grupo\n")
                .append("join grupo.dicDivision division\n")
                .append("join fraccionClase.dicClase clase\n")
                .append("where fraccion.cveIdFraccion = :idFraccion\n")
                .append("and grupo.cveIdGrupo = :idGrupo\n")
                .append("and division.cveIdDivision = :idDivision\n")
                //.append("and clase.desClase = :clase\n")
                .append("and fraccionClase.fecFin is null")
                .toString();

			Query query = this.getSession().createQuery(q);
			query.setParameter("idFraccion", model.getFraccion().getId());
			query.setParameter("idGrupo", model.getFraccion().getGrupo().getId());
			query.setParameter("idDivision", model.getFraccion().getGrupo().getDivision().getId());

			dicFraccionClase=(DicFraccionClase) query.uniqueResult();
			
			retVal.setDicFraccionClase(dicFraccionClase);
            DicFraccion dicFraccion = dicFraccionClase.getDicFraccion();
			if(dicFraccion.getDesFraccion()==null || dicFraccion.getDesFraccion().trim().equals("")){
				log.info("Esto es nulo:: " + model);
				return consultaPorNumFraccion(model);
			}else{
				log.info("Esto no es nulo" + model);
			}
			model = clasificacionUtility.convertirEntityToModel(retVal);
			log.info("Este te muestra todo::");
			log.info(model);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("ERROR - [ClasificacionEntity-consultaPorClave]: " + e.getMessage());
			throw new PersistenceException(e);
		}
		return model;
	}
	
}
