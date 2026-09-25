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
 
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.clasificacion.actividad.economica.ActividadEcServiceRemote;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.persistence.DicDivision;
import mx.gob.imss.ctirss.delta.persistence.DicFraccion;
import mx.gob.imss.ctirss.delta.persistence.DicGrupo;
import mx.gob.imss.ctirss.delta.persistence.DitAnalisisCe;
import mx.gob.imss.ctirss.delta.persistence.DitClasificacionPropuesta;

@Stateless
public class ClasificacionPropuestaServiceEntity extends AbstractServiceEntity implements ClasificacionPropuestaServiceEntityLocal{

    @EJB
    private ActividadEcServiceRemote clasificacionActividadEconomicaService;

	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patron.clasificacion.entity.ClasificacionPropuestaEntityLocal#elimina(mx.gob.imss.ctirss.delta.gestion.patron.model.Clasificacion)
	 */
	@Override
	public void elimina(long cveIdAnalisis) throws PersistenceException {
		DitClasificacionPropuesta entity = null;
		String qlString = "from DitClasificacionPropuesta c where c.ditAnalisisCe.cveIdAnalisis = :cveIdAnalisis";

		try {
			Query query = em.createQuery(qlString);
			query.setParameter("cveIdAnalisis", cveIdAnalisis);
			
			try{
				entity = (DitClasificacionPropuesta)query.getSingleResult();
			} catch (Exception e) {
				log.debug("**** No se encontro la clasificacion propuesta a borrar");
			}
		
			if(entity != null){
				log.debug("***** Borrando clasificacion propuesta");
				em.remove(entity);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("ERROR - [ClasificacionPropuestaEntity-elimina]: " + e.getMessage());
			throw new PersistenceException(e);
		}
	}

	@SuppressWarnings("null")
	@Override
	public Boolean agrega(AnalisisClasificacionEmpresas model) throws PersistenceException{
		Boolean agregarClas=false;
		try{
			DitClasificacionPropuesta entity=new DitClasificacionPropuesta();
			DitAnalisisCe analisis=new DitAnalisisCe();
			analisis.setCveIdAnalisis(Long.parseLong(String.valueOf(model.getCveIdAnalisis())));
			entity.setDitAnalisisCe(analisis);
			DicGrupo dicGrupo = new DicGrupo();
			dicGrupo.setCveIdGrupo(model.getClasificacionPropuesta().getFraccion().getGrupo().getId());
			DicDivision dicDivision = new DicDivision();
			dicDivision.setCveIdDivision(model.getClasificacionPropuesta().getFraccion().getGrupo().getDivision().getId());
			List<DicGrupo> dicGrupos = new ArrayList<DicGrupo>();
			dicGrupos.add(dicGrupo);
			dicDivision.setDicGrupos(dicGrupos);
			DicFraccion dicFraccion = new DicFraccion();
			dicFraccion.setCveIdFraccion(model.getClasificacionPropuesta().getFraccion().getId());
			dicFraccion.setDicGrupo(dicGrupo);
			entity.setDicDivision(dicDivision);
			entity.setDicGrupo(dicGrupo);
			entity.setDicFraccion(dicFraccion);
			entity.setDesActividadDetectada(model.getActividadDetectada());
			entity.setPrimaSugerida(model.getPrimaSugerida());
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

	@Override
	public Boolean actualiza(AnalisisClasificacionEmpresas model) throws PersistenceException{
		Boolean modifyClas=false;
		try{
			DitClasificacionPropuesta entity=new DitClasificacionPropuesta();
			String qlString = "from DitClasificacionPropuesta p where p.ditAnalisisCe.cveIdAnalisis = :cveIdAnalisis";
			Query query = em.createQuery(qlString);
			query.setParameter("cveIdAnalisis", model.getCveIdAnalisis());
			entity=(DitClasificacionPropuesta)query.getSingleResult();
			DitAnalisisCe analisis=new DitAnalisisCe();
			analisis.setCveIdAnalisis(Long.parseLong(String.valueOf(model.getCveIdAnalisis())));
			entity.setDitAnalisisCe(analisis);
			DicGrupo dicGrupo = new DicGrupo();
			dicGrupo.setCveIdGrupo(model.getClasificacionPropuesta().getFraccion().getGrupo().getId());
			DicDivision dicDivision = new DicDivision();
			dicDivision.setCveIdDivision(model.getClasificacionPropuesta().getFraccion().getGrupo().getDivision().getId());
			List<DicGrupo> dicGrupos = new ArrayList<DicGrupo>();
			dicGrupos.add(dicGrupo);
			dicDivision.setDicGrupos(dicGrupos);
			DicFraccion dicFraccion = new DicFraccion();
			dicFraccion.setCveIdFraccion(model.getClasificacionPropuesta().getFraccion().getId());
			dicFraccion.setDicGrupo(dicGrupo);
			entity.setDicDivision(dicDivision);
			entity.setDicGrupo(dicGrupo);
			entity.setDicFraccion(dicFraccion);
			entity.setDesActividadDetectada(model.getActividadDetectada());
			entity.setPrimaSugerida(model.getPrimaSugerida());
			modifyClas=true;
			em.persist(entity);
		}catch(Exception e){
			modifyClas=false;
			log.info("************************** Errrrr:: " + e);
			//log.error("************************** " + e.getMessage());
			log.info("\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n");
			e.printStackTrace();
		}
		return modifyClas;
	}

	@Override
	public AnalisisClasificacionEmpresas consultaPorIdAnalisis(long cveIdAnalisis) 
			throws PersistenceException{
		log.info("******" + "\n******");
		log.info("*********************************** cveIdAnalisis:: " + cveIdAnalisis);
		log.info("******" + "\n******");
		AnalisisClasificacionEmpresas response = null;
		Query query = null;
		DitClasificacionPropuesta entity = null;
		String qlString = "from DitClasificacionPropuesta c where c.ditAnalisisCe.cveIdAnalisis = :cveIdAnalisis";
		try{
			query = em.createQuery(qlString);
			query.setParameter("cveIdAnalisis", cveIdAnalisis);
			try{
				entity = (DitClasificacionPropuesta)query.getResultList().get(0);
			}catch(NullPointerException ex){
				return null;
			}
			if(entity != null){
				response = new AnalisisClasificacionEmpresas();
				response.setCveIdAnalisis(Long.valueOf(cveIdAnalisis));
				Clasificacion clasificacionPropuesta=new Clasificacion();

                DicFraccion dicFraccion = entity.getDicFraccion();
				Fraccion fraccion = clasificacionActividadEconomicaService.obtenerFraccionClaseActiva(dicFraccion.getCveIdFraccion());

                clasificacionPropuesta.setFraccion(fraccion);
				clasificacionPropuesta.setGiro(entity.getDesActividadDetectada());
				clasificacionPropuesta.setPrimaSugerida(entity.getPrimaSugerida());		
				response.setClasificacionPropuesta(clasificacionPropuesta);
			}
		}
        catch(NoResultException e){
			log.debug("No se encontro un analisis para la solicitud " + cveIdAnalisis);
		}catch (Exception exc){
			log.error("Error en m\u00E9todo consultaPorIdAnalisis: "+exc.getMessage());
		}
		return response;
	}

} 
