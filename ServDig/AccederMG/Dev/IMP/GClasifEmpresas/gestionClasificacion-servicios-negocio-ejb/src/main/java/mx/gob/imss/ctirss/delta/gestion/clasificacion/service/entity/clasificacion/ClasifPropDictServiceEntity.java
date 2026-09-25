/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: ClasificacionPropuestaServiceEntity.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clasificacion
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clasificacion;
 
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.DictamenClasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.persistence.DicFraccionClase;
import mx.gob.imss.ctirss.delta.persistence.DitClasifPropuestaDictamen;
import mx.gob.imss.ctirss.delta.persistence.DitClasificacion;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;

@Stateless
public class ClasifPropDictServiceEntity extends AbstractServiceEntity implements ClasifPropDictServiceEntityLocal{

    @EJB
    private ClasificacionEntityLocal clasificacionEntity;
    
	@SuppressWarnings("null")
	@Override
	public Boolean agrega(Long cveIdSujOb, Clasificacion model) throws PersistenceException{
		Boolean agregarClas=false;
		DitClasifPropuestaDictamen entity = null;
		String qlString = "from DitClasifPropuestaDictamen c where c.cveIdPatronSujetoObligado = :cveIdSujOb";		
		try{
			Query query = em.createQuery(qlString);
			query.setParameter("cveIdSujOb", cveIdSujOb);
			try{
				entity = (DitClasifPropuestaDictamen)query.getSingleResult();		
			}catch(Exception e){
				entity = null;
				log.error("::: No se encontro la clasificacion de dictamen");
			}
			if(entity == null){
				log.debug(":::Agregando en DitClasifPropuestaDictamen - :" + cveIdSujOb);
				entity = new DitClasifPropuestaDictamen();
			}else{
				log.debug(":::El sujeto obligado "+cveIdSujOb+" ya existe en DitClasifPropuestaDictamen");
			}
			entity.setCveIdPatronSujetoObligado(cveIdSujOb);
			DicFraccionClase dfc = clasificacionEntity.selectDicFraccionClase(model.getFraccion());
			entity.setDicFraccionClase(dfc);
			entity.setFecRegistroActualizado(Calendar.getInstance().getTime());
			entity.setFecRegistroAlta(Calendar.getInstance().getTime());
			entity.setFecRegistroBaja(null);
			entity.setIndDistribucionEntrega(model.getIndDistribuyeEntrega() == null ? BigDecimal.ZERO : new BigDecimal(model.getIndDistribuyeEntrega()));
//			entity.setIndEvaluada(BigDecimal.ZERO);
			if(model.getIndPrestaServicioPersonal() != null){
				entity.setIndPrestaServicioPersonal(new BigDecimal(model.getIndPrestaServicioPersonal()));
			}
			if(model.getIndRegPatClase() != null){
				entity.setIndRegPatClase(new BigDecimal(model.getIndRegPatClase()));
			}
			entity.setIndServicioOtrasPersonas(model.getIndServiciosATerceros() == null ? BigDecimal.ZERO : new BigDecimal(model.getIndServiciosATerceros()));
			entity.setIndTransporteAjeno(model.getIndTransporteAjeno() == null ? BigDecimal.ZERO : new BigDecimal(model.getIndTransporteAjeno()));
			entity.setIndTransportePropio(model.getIndTransportePropio() == null ? BigDecimal.ZERO : new BigDecimal(model.getIndTransportePropio()));
			entity.setManifestacion(model.getGiro() == null ? "" : model.getGiro());
			if(model.getNumCentrosTraba() != null){
				entity.setNumCentrosTraba(model.getNumCentrosTraba());
			}
			entity.setNumPrimaPago(model.getFraccion().getPrimaSRT() == null ? BigDecimal.ZERO : model.getFraccion().getPrimaSRT());
			agregarClas=true;
			em.persist(entity);
		}catch(Exception e){
			agregarClas=false;
			log.error("************************** " + e.getMessage());
			log.info("\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n");
			e.printStackTrace();
		}
		return agregarClas;
	}    
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patron.clasificacion.entity.ClasificacionPropuestaEntityLocal#elimina(mx.gob.imss.ctirss.delta.gestion.patron.model.Clasificacion)
	 */
	@Override
	public void elimina(long cveIdSujOb) throws PersistenceException {
		DitClasifPropuestaDictamen entity = null;
		String qlString = "from DitClasifPropuestaDictamen c where c.cveIdPatronSujetoObligado = :cveIdSujOb";

		try {
			Query query = em.createQuery(qlString);
			query.setParameter("cveIdSujOb", cveIdSujOb);
			
			entity = (DitClasifPropuestaDictamen)query.getSingleResult();
		
			if(entity != null){
				log.debug("***** Borrando clasificacion propuesta el dictamen");
				em.remove(entity);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("ERROR - [ClasifPropDictServiceEntity-elimina]: " + e.getMessage());
			throw new PersistenceException(e);
		}
	}

	
	@Override
	public void actualizarClasificacion(List<DictamenClasificacion> clasDtmL) {
		
		for (Iterator<DictamenClasificacion> iterator = clasDtmL.iterator(); iterator.hasNext();) {
			DictamenClasificacion clasDtm = iterator.next();
			log.debug("::Buscando la clasificacion con id: " + clasDtm.getCveIdClasificacion());
			DitClasificacion entity = (DitClasificacion)this.em.find(DitClasificacion.class, clasDtm.getCveIdClasificacion());
			log.debug("Encontre la clasificacion " + clasDtm.getCveIdClasificacion());
			entity.setManifestacion(clasDtm.getManifestacion());
			entity.setNumCentrosTraba(clasDtm.getNumCentrosTraba());
			entity.setIndPrestaServicioPersonal(clasDtm.getIndServicioOtrasPersonas());
			entity.setIndRegPatClase(clasDtm.getIndRegPatClase());
			entity.setIndTransportePropio(clasDtm.getIndTransportePropio());
			entity.setIndTransporteAjeno(clasDtm.getIndTransporteAjeno());
			entity.setIndDistribucionEntrega(clasDtm.getIndDistribucionEntrega());
			entity.setIndServicioOtrasPersonas(clasDtm.getIndServicioOtrasPersonas());
			DitPatronSujetoObligado ditPatronSujetoObligado = new DitPatronSujetoObligado();
			ditPatronSujetoObligado.setCveIdPatronSujetoObligado(clasDtm.getCveIdPatronSujetoObligado());
			entity.setDitPatronSujetoObligado(ditPatronSujetoObligado);
			entity.setIndEvaluada(clasDtm.getIndEvaluada());
			entity.setFecRegistroAlta(clasDtm.getFecRegistroAlta());
			entity.setFecRegistroBaja(clasDtm.getFecRegistroBaja());
			entity.setFecRegistroActualizado(clasDtm.getFecRegistroActualizado());
			entity.setNumPrimaPago(clasDtm.getNumPrimaPago());
			DicFraccionClase dicFraccionClase = new DicFraccionClase();
			dicFraccionClase.setCveIdFraccionClase(clasDtm.getCveIdFraccionClase());
			entity.setDicFraccionClase(dicFraccionClase);
			log.debug("Actualizando la clasificacion " + clasDtm.getCveIdClasificacion());
			this.em.merge(entity);
			log.debug("Clasificacion " + clasDtm.getCveIdClasificacion() + " actualizada");
		}

	}	
	
	@Override
	public List<DictamenClasificacion> consultaClasifOriginal(Long cveIdSO)
			throws PersistenceException{
		log.info("******" + "\n******");
		log.info("*********************************** cveIdSO: " + cveIdSO);
		log.info("******" + "\n******");
		DictamenClasificacion resp = null;
		List<DictamenClasificacion> respL = new ArrayList<DictamenClasificacion>();
		Query query = null;
		List<DitClasificacion> entityL = null;
		String qlString = "from DitClasificacion cl where cl.ditPatronSujetoObligado.cveIdPatronSujetoObligado = :cveIdSO ";

		try{
			query = em.createQuery(qlString);
			query.setParameter("cveIdSO", cveIdSO);
			try{
				entityL = query.getResultList();
			}catch(NullPointerException ex){
				return null;
			}
			for (Iterator<DitClasificacion> iterator = entityL.iterator(); iterator.hasNext();) {
				DitClasificacion entity = iterator.next();
				resp = new DictamenClasificacion();
				resp.setCveIdClasificacion(entity.getCveIdClasificacion());
				resp.setManifestacion(entity.getManifestacion());
				resp.setNumCentrosTraba(entity.getNumCentrosTraba());
				resp.setIndPrestaServicioPersonal(entity.getIndPrestaServicioPersonal());
				resp.setIndRegPatClase(entity.getIndRegPatClase());
				resp.setIndTransportePropio(entity.getIndTransportePropio());
				resp.setIndTransporteAjeno(entity.getIndTransporteAjeno());
				resp.setIndDistribucionEntrega(entity.getIndDistribucionEntrega());
				resp.setIndServicioOtrasPersonas(entity.getIndServicioOtrasPersonas());
				resp.setCveIdPatronSujetoObligado(entity.getDitPatronSujetoObligado().getCveIdPatronSujetoObligado());
				resp.setIndEvaluada(entity.getIndEvaluada());
				resp.setFecRegistroAlta(entity.getFecRegistroAlta());
				resp.setFecRegistroBaja(entity.getFecRegistroBaja());
				resp.setFecRegistroActualizado(entity.getFecRegistroActualizado());
				resp.setNumPrimaPago(entity.getNumPrimaPago());
				resp.setCveIdFraccionClase(entity.getDicFraccionClase().getCveIdFraccionClase());
				respL.add(resp);
			}
		}
        catch(NoResultException e){
			log.debug("No se encontro la clasificacion para " + cveIdSO);
			respL = null;
		}catch (Exception exc){
			exc.printStackTrace();
			log.error("Error en m\u00E9todo consultaClasifOriginal: "+exc.getMessage());
			respL = null;
		}
		log.debug("::: Se encontraron " + respL.size() + " registros en Dit_Clasificacion");
		return respL;
	}	
	

} 
