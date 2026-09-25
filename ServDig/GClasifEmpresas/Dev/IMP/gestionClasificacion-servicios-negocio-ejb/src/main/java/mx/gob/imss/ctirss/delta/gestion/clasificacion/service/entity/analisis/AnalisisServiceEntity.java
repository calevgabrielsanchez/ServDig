/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo:AnalisisServiceEntity.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.analisis
 *  @Fecha:04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.analisis;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.exception.clasificacion.AnalisisNoEncontradoException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.PatronNoEncontradoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.bitacora.BitacoraServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.analisis.AnalisisServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.bitacora.BitacoraServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.sujetoobligado.SujetoObligadoServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisEnum;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisModel;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DicEstatusAnalisisCe;
import mx.gob.imss.ctirss.delta.persistence.DicGrupoAnalisisCe;
import mx.gob.imss.ctirss.delta.persistence.DicTipoCausaAnalisis;
import mx.gob.imss.ctirss.delta.persistence.DitAnalisisCe;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;

@Stateless
public class AnalisisServiceEntity extends AbstractServiceEntity implements AnalisisServiceEntityLocal{

	@EJB
	private AnalisisServiceUtilityLocal analisisMovimientoUtility;
	
	@EJB
	private BitacoraServiceEntityLocal bitacoraEntity;

	@EJB
	private SujetoObligadoServiceUtilityLocal sujetoObligadoUtility;
	
	@EJB
	private BitacoraServiceUtilityLocal bitacoraUtility;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EstatusAnalisisModel> consultaEstatusAnalisisPorGrupoAnalisis(Long cveIdGrupo) throws Exception{
		List<EstatusAnalisisModel> listaModel = new ArrayList<EstatusAnalisisModel>();
		
		Query query = em.createNativeQuery("select eace.cve_id_estatus_analisis, eace.des_causas_analisis " +
								 "from dic_estatus_analisis_ce eace, dic_estatus_grupo_analisis_ce egace " +
								 "where egace.cve_id_grupo_analisis_ce = :idGrupo " +
								 "and eace.fec_registro_baja is null " +
								 "and egace.cve_id_estatus_analisis = eace.cve_id_estatus_analisis " +
								 "order by eace.des_causas_analisis");
		
		query.setParameter("idGrupo", cveIdGrupo);
		List<Object[]>lista = (List<Object[]>)query.getResultList();
		
		if(!lista.isEmpty() && lista.get(0) != null){
			for (Object[] obj : lista) {
				EstatusAnalisisModel estatusAnalisis = new EstatusAnalisisModel();
				estatusAnalisis.setCveIdEstatus(((BigDecimal)obj[0]).longValue());
				estatusAnalisis.setDesEstatus((String)obj[1]);
				listaModel.add(estatusAnalisis);
			}
		}
		
		return listaModel;
	}
	
	@Override
	public AnalisisClasificacionEmpresas agregaAnalisis(AnalisisClasificacionEmpresas model) 
			throws PersistenceException {
		try {
			
			DitAnalisisCe entity = null;
			EstatusAnalisisModel estatusAnalisisModel = new EstatusAnalisisModel();
			String qlString = "from DitAnalisisCe a where a.cveIdSolicitud = :cveIdSolicitud";
			
			Query query = em.createQuery(qlString);
			query.setParameter("cveIdSolicitud", new BigDecimal(model.getSolicitud().getId().toString()));
			
			try{
                entity = (DitAnalisisCe)query.getSingleResult();
            }catch(NoResultException e){
				log.error(e.getMessage());
            }

			if(entity != null){
				//si la peticion viene desde el portal de MAC(debe entrar aqui) ya existe un analisis creado previamente desde GESTION PATRONAL
				//por lo que MAC ya no da de alta el analisis(esqueleto)
				//En este caso el estatus que se debe asignar es con la clave 2 que es el segundo paso para el analisis de un tramite en CE
				//lo que hay que hacer es asignar dicho analisis al usuario que realizo la peticion
				
				 log.debug("******************* Analisis encontrado [" + entity.getCveIdAnalisis() +"]");
				 model.setCveIdEstatus(Long.valueOf(EstatusAnalisisEnum.PENDIENTE_DE_ANALISIS.getClave()));
				 model.setEstatus(EstatusAnalisisEnum.PENDIENTE_DE_ANALISIS);
					
				 DicEstatusAnalisisCe cveIdEstatusAnalisis = new DicEstatusAnalisisCe();
				 cveIdEstatusAnalisis.setCveIdEstatusAnalisis(Long.valueOf(EstatusAnalisisEnum.PENDIENTE_DE_ANALISIS.getClave()));
				 entity.setDicEstatusAnalisisCe(cveIdEstatusAnalisis);
				 if(model.getClaveUsuarioAsignado() != null)
					 entity.setCveIdUsuarioSso(model.getClaveUsuarioAsignado()); //asignacion del usuario al analisis
				 
				 entity.setFecAnalisis(new Date());
				 entity.setIndActivo(true);
				 log.debug("******************* Se actualizo el estado del analisis");				 
			 }else{				 
				 //cuando entra a esta opcion(no existe analisis) la peticion debe venir desde GESTION PATRONAL
				 //por lo que se crea un analisis con estatus 1 que es el primer paso para analizar un tramite en CE
				 //no se asigna un usuario ya que solo es el alta del analisis(esqueleto)
				 //esta opcion no debe entrar desde el portal de MAC ya que GP es la encargada de crear dicho registro
				 
				 log.debug("******************* El analisis no existe se crea uno nuevo");				 

				 model.setCveIdEstatus(Long.valueOf(EstatusAnalisisEnum.PENDIENTE_DE_ANALISIS.getClave()));
				 model.setEstatus(EstatusAnalisisEnum.PENDIENTE_DE_ANALISIS);

				/*Negocio*/
				entity = new DitAnalisisCe();
				DicEstatusAnalisisCe dicEstatusAnalisisCe = new DicEstatusAnalisisCe();				
				dicEstatusAnalisisCe.setCveIdEstatusAnalisis(EstatusAnalisisEnum.PENDIENTE_DE_ANALISIS.getClave());
				
				entity.setDicEstatusAnalisisCe(dicEstatusAnalisisCe);
				
				entity.setCveIdSolicitud(new BigDecimal(model.getSolicitud().getId()));
				entity.setFecAnalisis(new Date());
				entity.setIndModAut(BigDecimal.ZERO);
				DicGrupoAnalisisCe dicGrupoAnalisisCe = new DicGrupoAnalisisCe();
				dicGrupoAnalisisCe.setCveIdGrupoAnalisisCe(model.getCveIdGrupoAnalisisCe());
				entity.setDicGrupoAnalisisCe(dicGrupoAnalisisCe);
				entity.setIndActivo(true);
				em.persist(entity);
			 }
			
			model.setCveIdAnalisis(new Long(entity.getCveIdAnalisis()));
			
			estatusAnalisisModel = bitacoraUtility.armaBitacora(model, "");
			
			bitacoraEntity.guardaBitacora(estatusAnalisisModel);
			
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new PersistenceException(e);
		}
		return model;
	}

	@Override
	public AnalisisClasificacionEmpresas consultaPorIdAnalisis(long cveIdAnalisis) 
			throws PersistenceException {
		AnalisisClasificacionEmpresas response = null;
		Query query = null;
		DitAnalisisCe entity = null; 
		String qlString = "from DitAnalisisCe a where a.cveIdAnalisis = :cveIdAnalisis";
		try{
			query = em.createQuery(qlString);
			query.setParameter("cveIdAnalisis", cveIdAnalisis);
			entity = (DitAnalisisCe)query.getSingleResult();
			if(entity != null){
				response = new AnalisisClasificacionEmpresas();
				response = analisisMovimientoUtility.convertirEntityToModel(entity);
			}		
		} catch (Exception exc) {
				log.error("Error en m\u00E9todo consultaPorIdAnalisis: "+exc.getMessage());
				throw new PersistenceException(exc);		
		}
		return response;
	}
	
	@Override
	public boolean validaEstatusMovimiento(long cveIdAnalisis, String cveIdEstatus) 
			throws PersistenceException {
		Query query = null;
		String qlString = "from DitAnalisisCe a " +
						  " where a.cveIdAnalisis = :cveIdAnalisis " +
						  " and a.dicEstatusAnalisisCe.cveIdEstatusAnalisis in (" + cveIdEstatus + ")";
		try{
			query = em.createQuery(qlString);
			query.setParameter("cveIdAnalisis", cveIdAnalisis);
			
			if(!query.getResultList().isEmpty()){
				return true;
			}		
		} catch (Exception exc) {
				log.error("Error en m\u00E9todo validaEstatusMovimiento: "+exc.getMessage());
				exc.printStackTrace();
				throw new PersistenceException(exc);		
		}
		return false;
	}

	@Override
	public AnalisisClasificacionEmpresas consultaDetalleAnalisis(BigDecimal idSolicitud) throws Exception {
		AnalisisClasificacionEmpresas response = null;
		Query query = null;
		DitAnalisisCe entity = null;
		String qlString = "from DitAnalisisCe a where a.cveIdSolicitud = :cveIdSolicitud";
		try{
			query = em.createQuery(qlString);
			query.setParameter("cveIdSolicitud", idSolicitud);
			entity = (DitAnalisisCe)query.getSingleResult();
			if(entity != null){
				response = new AnalisisClasificacionEmpresas();
				response = analisisMovimientoUtility.convertirEntityToModelDetalle(entity);
			}
		}catch(NoResultException e){
			log.debug("No se encontro un analisis para la solicitud " + idSolicitud);
			return null;
		}catch (Exception exc) {
			log.error("Error en m\u00E9todo consultaDetalleAnalisis: "+exc.getMessage());
			throw new Exception(exc);		
		}
		return response;
	}

	@Override
	public AnalisisClasificacionEmpresas actualizaEstado(AnalisisClasificacionEmpresas model) 
			throws AnalisisNoEncontradoException {
		this.log.debug("Actualizando el estado, analisis  [ " + model.getCveIdAnalisis() +"] , estatus [" +model.getEstatus() +"]");
		
		String qlString = "from DitAnalisisCe a where a.cveIdAnalisis = :cveIdAnalisis";
		Query query = em.createQuery(qlString);
		 query.setParameter("cveIdAnalisis", model.getCveIdAnalisis().longValue());
		 DitAnalisisCe entity = (DitAnalisisCe)query.getSingleResult();
		 this.log.debug("Analisis encontrado [" + entity +"]");
		 
		 if(entity != null){
			DicEstatusAnalisisCe dicEstatusAnalisisCe = new DicEstatusAnalisisCe();
			dicEstatusAnalisisCe.setCveIdEstatusAnalisis(model.getEstatus().getClave());
			entity.setDicEstatusAnalisisCe(dicEstatusAnalisisCe);
			entity.setCveIdUsuarioSso(model.getClaveUsuarioAsignado());
			entity.setFecAnalisis(new Date());
			if(model.getTipoCausaAnalisis() != null){
				DicTipoCausaAnalisis dicTipoCausaAnalisis = em.find(
						DicTipoCausaAnalisis.class,
						new Long(model.getTipoCausaAnalisis()).longValue());
				entity.setDicTipoCausaAnalisi(dicTipoCausaAnalisis);
			}
			if(dicEstatusAnalisisCe.getCveIdEstatusAnalisis() == EstatusAnalisisEnum.RATIFICADO_RECHAZADO.getClave()
				|| dicEstatusAnalisisCe.getCveIdEstatusAnalisis() == EstatusAnalisisEnum.RECTIFICADO_RECHAZADO.getClave()
				|| dicEstatusAnalisisCe.getCveIdEstatusAnalisis() == EstatusAnalisisEnum.RATIFICADO_AUTORIZADO_Y_RECHAZADO.getClave()
				|| dicEstatusAnalisisCe.getCveIdEstatusAnalisis() == EstatusAnalisisEnum.RECTIFICADO_AUTORIZADO_Y_RECHAZADO.getClave()){
					entity.setIndModAut(BigDecimal.ONE);
			}			
    		this.log.debug("Se actualizo el estado del analisis");
		 }else{
			 /*Lanzar una exception de negocio*/
			 throw new AnalisisNoEncontradoException("No se encontr\u00F3 el An\u00E1lisis.", 1300);
		 }
		 
		 try{
			 model = analisisMovimientoUtility.convertirEntityToModel(entity);
		 }catch (Exception e) {
			 log.error("Error al actualizar el analisis: "+ e.getMessage());
			 throw new PersistenceException(e);
		}
		 
		return model;
	}
	

	@SuppressWarnings("unchecked")
	public SujetoObligado consultarSujetoObligado(AnalisisClasificacionEmpresas model, int tipoPersona) 
			throws PersistenceException{
		SujetoObligado sujetoObligado = null;		
		DitPatronSujetoObligado ditPatronSujetoObligado = null;
		String querySO = "select pso "+
				" from DitAnalisisCe a, DitTramite t, ";
		querySO += (model.getCveIdGrupoAnalisisCe() == null || !model.getCveIdGrupoAnalisisCe().equals(3L) ? "DitTramitePatSujObligado" : "DitTramiteDictamen");
		querySO +="  tpso, DitPatronSujetoObligado pso " +
				" where a.cveIdAnalisis = " + model.getCveIdAnalisis() +
				" and t.ditSolicitud.cveIdSolicitud = a.cveIdSolicitud " +
				" and tpso.id.cveIdTramite = t.cveIdTramite " + 
				" and pso.cveIdPatronSujetoObligado = tpso.ditPatronSujetoObligado.cveIdPatronSujetoObligado";
		try{
			
			Query query = em.createQuery(querySO);
			
			List<DitPatronSujetoObligado>lista = (List<DitPatronSujetoObligado>)query.getResultList();
			if(lista != null && lista.size() > 0)
				ditPatronSujetoObligado = (DitPatronSujetoObligado)lista.get(0);
			
			if(ditPatronSujetoObligado!=null){
				sujetoObligado = sujetoObligadoUtility.convertEntityToModel(ditPatronSujetoObligado, tipoPersona);
			}else{
				log.error("no hay registros");
				throw new PatronNoEncontradoException();
			}
		}catch (Exception exc){
			log.error("Error en m\u00E9todo consultarSujetoObligado");
			log.error(exc.getMessage());
			throw new PersistenceException(exc);
		}
		return sujetoObligado;
	}
		
	/**
	 * Metodo para agregar un Análisis por motivo de cancelación por Registro Patronal
	 * @param model
	 * @return
	 */
	@Override
	public Long generaAnalisisCancelacion(Long cveIdSolicitud, Long cveIdGrupoAnalisis){
		DitAnalisisCe entity = new DitAnalisisCe();
		DicEstatusAnalisisCe dicEstatusAnalisisCe = new DicEstatusAnalisisCe();
		DicGrupoAnalisisCe dicGrupoAnalisisCe=new DicGrupoAnalisisCe();
		dicEstatusAnalisisCe.setCveIdEstatusAnalisis(EstatusAnalisisEnum.PENDIENTE_DE_ANALISIS.getClave());
		dicGrupoAnalisisCe.setCveIdGrupoAnalisisCe(cveIdGrupoAnalisis);
		entity.setDicEstatusAnalisisCe(dicEstatusAnalisisCe);
		entity.setCveIdSolicitud(new BigDecimal(cveIdSolicitud));
		entity.setFecAnalisis(new Date());
		entity.setIndModAut(BigDecimal.ZERO);
		entity.setDicGrupoAnalisisCe(dicGrupoAnalisisCe);
		em.persist(entity);
		return entity.getCveIdAnalisis();
	}
	
	@Override
	public void actualizaTipoCausa(Long cveIdAnalisis, Long cveIdTipoCausa) {
		DitAnalisisCe ditAnalisisCe=new DitAnalisisCe();
		String sql="from DitAnalisisCe a where a.cveIdAnalisis = :cveIdAnalisis";
		Query query=em.createQuery(sql);
		query.setParameter("cveIdAnalisis", cveIdAnalisis);
		ditAnalisisCe = (DitAnalisisCe)query.getSingleResult();
		DicTipoCausaAnalisis dicTipoCausaAnalisis=new DicTipoCausaAnalisis();
		dicTipoCausaAnalisis.setCveIdTipoCausa(cveIdTipoCausa);
		ditAnalisisCe.setDicTipoCausaAnalisi(dicTipoCausaAnalisis);
		
		em.merge(ditAnalisisCe);
	}

	@Override
	public void actualizaIndCausa(AnalisisClasificacionEmpresas model, boolean activo) {
		DitAnalisisCe ditAnalisisCe=new DitAnalisisCe();
		String sql="from DitAnalisisCe a where a.cveIdAnalisis = :cveIdAnalisis";
		Query query=em.createQuery(sql);
		query.setParameter("cveIdAnalisis", model.getCveIdAnalisis());
		ditAnalisisCe = (DitAnalisisCe)query.getSingleResult();
		
		ditAnalisisCe.setIndRegistraCausa(activo);
		
		em.merge(ditAnalisisCe);
	}

	@Override
	public void actualizaAnalisisDesechar(AnalisisClasificacionEmpresas model, boolean indRegCausa) throws AnalisisNoEncontradoException {
		this.log.debug("Actualizando el estado, analisis  [ " + model.getCveIdAnalisis() +"] , estatus [" +model.getEstatus() +"]");
		
		String qlString = "from DitAnalisisCe a where a.cveIdAnalisis = :cveIdAnalisis";
		Query query = em.createQuery(qlString);
		query.setParameter("cveIdAnalisis", model.getCveIdAnalisis().longValue());
		DitAnalisisCe entity = (DitAnalisisCe)query.getSingleResult();
		this.log.debug("Analisis encontrado [" + entity +"]");
		 
		if(entity != null){
			DicEstatusAnalisisCe dicEstatusAnalisisCe = new DicEstatusAnalisisCe();
			dicEstatusAnalisisCe.setCveIdEstatusAnalisis(model.getEstatus().getClave());
			entity.setDicEstatusAnalisisCe(dicEstatusAnalisisCe);
			entity.setCveIdUsuarioSso(model.getClaveUsuarioAsignado());
			entity.setFecAnalisis(new Date());
			if(model.getTipoCausaAnalisis() != null){
				DicTipoCausaAnalisis dicTipoCausaAnalisis = em.find(
						DicTipoCausaAnalisis.class,
						new Long(model.getTipoCausaAnalisis()).longValue());
				entity.setDicTipoCausaAnalisi(dicTipoCausaAnalisis);
			}
			
			entity.setIndRegistraCausa(indRegCausa);
						
    		this.log.debug("Se actualizo el estado del analisis");
		 }else{
			 /*Lanzar una exception de negocio*/
			 throw new AnalisisNoEncontradoException("No se encontr\u00F3 el An\u00E1lisis.", 1300);
		 }
		 
	}
	
}