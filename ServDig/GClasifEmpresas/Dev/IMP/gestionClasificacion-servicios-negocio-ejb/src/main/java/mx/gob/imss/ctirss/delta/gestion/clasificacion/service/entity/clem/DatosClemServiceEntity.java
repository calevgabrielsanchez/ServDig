/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Héctor Lara Andrés
 *  @Proyecto: delta
 *  @Archivo:DatosClemServiceEntity.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clem
 *  @Fecha:17/08/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.SQLQuery;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.EjercicioDictamen;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.FirmaClemDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.clem.DatosClemServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.clasificacion.DatosClem;
import mx.gob.imss.ctirss.delta.model.clasificacion.SubdelegacionRimss;
import mx.gob.imss.ctirss.delta.persistence.DicSubdelegacionRimss;
import mx.gob.imss.ctirss.delta.persistence.DitDatosClem;
import mx.gob.imss.ctirss.delta.persistence.DitHistDatosClem;
import mx.gob.imss.ctirss.delta.persistence.DitSubdelPatSujOblig;

@Stateless
public class DatosClemServiceEntity  extends AbstractServiceEntity  implements DatosClemServiceEntityLocal{
	
	@EJB
	private DatosClemServiceUtilityLocal datosClemUtility; 
	
	@Override
	public DatosClem actualiza(DatosClem model) throws PersistenceException{
		try{
			// TODO Auto-generated method stub
			DitDatosClem entity = datosClemUtility.convertirModelToEntity(model);
			log.error("DatosClem a actualizar:"+entity.toString());
			log.error("DatosClem a pdf:"+ entity.getRefDocumento().length);
			em.merge(entity);
			model = datosClemUtility.convertirEntityToModel(entity);
		}catch (Exception e){
			e.printStackTrace();
			log.error("ERROR - [DatosClemEntity-actualizar]: " + e.getMessage());
			throw new PersistenceException(e);
		}			
		return model;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DatosClem consultaPorClave(DatosClem model)
			throws PersistenceException {
		Query qry = null;
		try{
			qry = em.createQuery("from DitDatosClem d where d.ditAnalisisCe.cveIdAnalisis = :idAnalisis and d.fecRegistroBaja is null");
			qry.setParameter("idAnalisis", model.getCveAnalisis().longValue());
			
			List<DitDatosClem> rows = qry.getResultList();
			if (!rows.isEmpty()) {
				DitDatosClem entity = (DitDatosClem) rows.get(0);
				log.debug("Se encontro el registro:"+ entity.getCveIdClem());
				model = datosClemUtility.convertirEntityToModel(entity);
				return model;
			}
		}catch(Exception exc){
			log.error("Error al recuperar la clem:"+exc.getMessage());
			exc.printStackTrace();
			throw new PersistenceException(exc);
		}
		return null;
	}

	@Override
	public DatosClem crear(DatosClem model, Boolean insert) throws PersistenceException {
		DitDatosClem datosClem=new DitDatosClem();
		try{
			//log.info("Recibido: " + model.toString());
			datosClem = datosClemUtility.convertirModelToEntity(model);
			log.info("Creando - [DitDatosClem-crear]: " + datosClem.toString());
			if(insert){
				em.persist(datosClem);
			}else{
				datosClem.setFecRegistroAlta(model.getFecRegistroAlta());
				em.merge(datosClem);
			}
			model  = datosClemUtility.convertirEntityToModel(datosClem);
		} catch (Exception e) {
			log.error("ERROR - [DatosClemEntity-crear]: " + e.getMessage());
			e.printStackTrace();			
			throw new PersistenceException(e);
		}
		//log.info("Clem insertada 1:: " + model);
		return model;
	}

	@Override
	public DatosClem actualizaEstadosClem(DatosClem model) throws PersistenceException {
		DitDatosClem entity= null;
		DatosClem response=null;

		try{	
			String qlString = "from DitDatosClem d where d.ditAnalisisCe.cveIdAnalisis = :idAnalisis and d.fecRegistroBaja is null";
			Query query = em.createQuery(qlString);
			query.setParameter("idAnalisis", model.getCveAnalisis().longValue());			
			entity = (DitDatosClem)query.getSingleResult();			
			this.log.debug("Clem encontrado [" + entity +"]");			 
			 if(entity != null){
				/***entity.setUltFechaActualizacion(new Date());
				entity.setIndActivo(model.getIndActivo());***/
				 this.log.debug("Se actualizo el estado del clem");
			 }else{
				 /*Lanzar una exception de negocio*/
				 throw new PersistenceException("No se encontro el clem");
			 }
		}catch(Exception exc){
			log.error("Error al recuperar la clem:"+exc.getMessage());
			throw new PersistenceException(exc);
		}
		try{
			response  = datosClemUtility.convertirEntityToModel(entity);		
		}catch (Exception e) {
			 log.error("Error al actualizar el analisis: "+ e.getMessage());
			 throw new PersistenceException(e);
		}
		return response;
	}	
	
	@Override
	public void elimina(Long cveIdClem) throws PersistenceException {
		DitDatosClem entity = null;

		String qlString = "from DitDatosClem dc where dc.cveIdClem = :id and dc.fecRegistroBaja is null";
		
		try{
			Query query = em.createQuery(qlString);
			query.setParameter("id", cveIdClem);
			
			try{
				entity = (DitDatosClem)query.getSingleResult();
			} catch (Exception e) {
				log.debug("**** No se encontro el CLEM a borrar");
			}
		
			if(entity != null){
				entity.setFecRegistroBaja(new Date());
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error("ERROR - [DatosClemServiceEntity-elimina]: " + e.getMessage());
			throw new PersistenceException(e);
		}
		
	}

	@Override
	public DatosClem modificaClem(DatosClem model) throws PersistenceException {
		DitDatosClem datosClem=new DitDatosClem();
		try{
			datosClem = datosClemUtility.convertirModelToEntity(model);
			em.merge(datosClem);
			model  = datosClemUtility.convertirEntityToModel(datosClem);
			
		} catch (Exception e) {
			log.error("ERROR - [DatosClemEntity-crear]: " + e.getMessage());
			e.printStackTrace();			
			throw new PersistenceException(e);
		}
		log.info("Clem insertada 1:: " + model);
		return model;
	}
	
	/**
	 * Método para guardar en el Histórico de CLEM
	 * @param datosClem, cveIdTipoCausa
	 * @return
	 */
	@Override
	public void generaHistDatosClem(DatosClem datosClem, Long cveIdTipoCausa){
		DitHistDatosClem ditHistDatosClem=null;
		ditHistDatosClem = datosClemUtility.convertirModelToEntityHistoricoCLEM(datosClem, cveIdTipoCausa);
		em.persist(ditHistDatosClem);
	}
	
	@Override
	public void actualizaDatosFirmaClem(FirmaClemDTO firmaClemDTO) throws PersistenceException {
		DitDatosClem entity= null;
		try{	
			String qlString = "from DitDatosClem d where d.ditAnalisisCe.cveIdAnalisis = :idAnalisis and d.fecRegistroBaja is null";
			Query query = em.createQuery(qlString);
			query.setParameter("idAnalisis", firmaClemDTO.getCveAnalisis());			
			entity = (DitDatosClem)query.getSingleResult();			
			this.log.debug("Clem encontrado [" + entity +"]");			 
			 if(entity != null){
				entity.setRfc(firmaClemDTO.getRfc());
				entity.setFolio(firmaClemDTO.getFolio());
				entity.setAcuse(firmaClemDTO.getAcuse());
				entity.setFirma(firmaClemDTO.getFirma());
				entity.setCadori(firmaClemDTO.getCadori());
				entity.setFecRegistroActualizado(new Date());
				this.log.debug("Se actualizo la clem: " + firmaClemDTO.getCveAnalisis().toString() + ", folio: " + entity.getFolio());
			 }else{
				 throw new PersistenceException("No se encontro el clem");
			 }
		}catch(Exception exc){
			log.error("Error al recuperar la clem:"+exc.getMessage());
			throw new PersistenceException(exc);
		}
	}
	
	@Override
	public SubdelegacionRimss consultaSubdelegacionRIMSS(
			SubdelegacionRimss model) {
		SubdelegacionRimss response = new SubdelegacionRimss();
		try{	
			String query = "SELECT sr.CVE_DELEGACION, sr.CVE_SUBDELEGACION, sr.DES_DELEG, sr.DES_SUBDELEGACION, sr.FEC_REGISTRO_ACTUALIZADO, sr.FEC_REGISTRO_BAJA, sr.FEC_REGISTRO_ALTA, sr.TIPO " +
	                 "FROM DIC_DELEGACION d " +
	                 "JOIN DIC_SUBDELEGACION sd ON d.CVE_ID_DELEGACION = sd.CVE_ID_DELEGACION " +
	                 "JOIN DIC_SUBDELEGACION_RIMSS sr ON sr.CVE_DELEGACION = d.CLAVE_DELEGACION AND sr.CVE_SUBDELEGACION = sd.CLAVE_SUBDELEGACION " + 
	                "WHERE d.CVE_ID_DELEGACION = " + model.getCveDelegacion() + " " +
	                  "AND sd.CVE_ID_SUBDELEGACION = " + model.getCveSubdelegacion() + " " +
	                  "AND sr.FEC_REGISTRO_BAJA IS NULL";
			SQLQuery queryRimss = this.getSession().createSQLQuery(query);
			List<Object[]> resultado = (List<Object[]>)queryRimss.list();
			if(!resultado.isEmpty()) {
				for(Object[] res: resultado) {
					response.setCveDelegacion(((BigDecimal)res[0]).longValue());
					response.setCveSubdelegacion(((BigDecimal)res[1]).longValue());
					response.setDescDelegacion((String) res[2]);
					response.setDescSubDelegacion((String) res[3]);
					response.setFecRegistroActualizado((Date) res[4]);
					response.setFecRegistroBaja((Date) res[5]);
					response.setFecRegistroAlta((Date) res[6]);
					response.setTipo((String) res[7]);			
				}
			}
			this.log.debug("::: Respuesta de catalogo RIMSS");
			this.log.debug(response.toString());			
		}catch (Exception e) {
			log.error("Error al buscar catalogo RIMSS: "+ e.getMessage());
			e.printStackTrace();
			return null;
		}

		return response;
	}			
		
//	@Override
//	public SubdelegacionRimss consultaSubdelegacionRIMSS(
//			SubdelegacionRimss model) {
//		
//		DicSubdelegacionRimss entity = null;
//		SubdelegacionRimss response = new SubdelegacionRimss();
//
//		try{	                                                                                
//			String qlString = "from DicSubdelegacionRimss d where d.cveDelegacion=:cveDel and d.cveSubDelegacion=:cveSubDel and d.fecRegistroBaja is null";
//			Query query = em.createQuery(qlString);
//			query.setParameter("cveDel", model.getCveDelegacion());			
//			query.setParameter("cveSubDel", model.getCveSubdelegacion());
//			entity = (DicSubdelegacionRimss)query.getSingleResult();			
//			this.log.debug("Registro RIMSS encontrado [" + entity +"]");			 
//
//			if(entity != null){
//				response.setCveDelegacion(entity.getCveDelegacion());
//				response.setCveSubdelegacion(entity.getCveSubDelegacion());
//				response.setDescDelegacion(entity.getDesDeleg());
//				response.setDescSubDelegacion(entity.getDesSubDelegacion());
//				response.setFecRegistroActualizado(entity.getFecRegistroActualizado());
//				response.setFecRegistroBaja(entity.getFecRegistroBaja());
//				response.setFecRegistroAlta(entity.getFecRegistroAlta());
//				response.setTipo(entity.getTipo());			
//			}else{
//				return null;
//			}
//			
//		}catch (Exception e) {
//			e.printStackTrace();
//			log.error("Error al actualizar el analisis: "+ e.getMessage());
//			return null;
//		}
//		
//		return response;
//
//	}		
	
}