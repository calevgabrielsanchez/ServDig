/**
*
*
**/
package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.clasificacion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.SujetoObligadoServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.clasificacion.actividad.economica.ClasificacionActividadEconomicaServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.enums.CausaBajaPatronEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.BuzonClasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.persistence.DicFraccion;
import mx.gob.imss.ctirss.delta.persistence.DicModalidad;
import mx.gob.imss.ctirss.delta.persistence.DitBuzonClasificacion;
import mx.gob.imss.ctirss.delta.persistence.DitClasificacion;
import mx.gob.imss.ctirss.delta.persistence.DitPatronGeneral;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitReintentoRPC;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitud;
import mx.gob.imss.ctirss.delta.persistence.PatronesTempInc;

/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Hugo Armando Martínez Chamónica
 *  @Proyecto: delta
 *  @Archivo: ClaseServiceEntity.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.clasificacion
 *  @Fecha: 12:01:07
 */
@Stateless
public class ClasificacionServiceEntity extends AbstractServiceEntity implements ClasificacionServiceEntityLocal {
	
	@EJB
	SujetoObligadoUtilityLocal sujetoObligadoUtility;
	
	@EJB
	SujetoObligadoServiceEntityLocal sujetoObligadoServiceEntityLocal;
	
	@EJB
	ClasificacionActividadEconomicaServiceUtilityLocal clasificacionUtlity;
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.clasificacion.ClaseServiceEntityLocal#consultarClaseExistentePorRFC(java.lang.String, java.lang.Long, mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Clasificacion> consultarClasesExistentesPorRFC(String RFC, 
			TipoPersonaFiscal tipoPersona, String numeroRegistroPatronal) {
		System.err.println("QUERY RPC");
		
		Criteria criteria = super.getSession().createCriteria(DitClasificacion.class);
		criteria.createAlias("ditPatronSujetoObligado", "ditPatronSujetoObligado");
		BigDecimal valor = BigDecimal.ONE;
		criteria.add(Restrictions.eq("indRegPatClase", valor));
		
		if(tipoPersona.equals(TipoPersonaFiscal.FISICA)){
			criteria.createAlias("ditPatronSujetoObligado.ditPersonaFisica", "ditPersonaFisica");
			criteria.add(Restrictions.eq("ditPersonaFisica.rfc", RFC));
		}else if(tipoPersona.equals(TipoPersonaFiscal.MORAL)){
			criteria.createAlias("ditPatronSujetoObligado.ditPersonaMoral", "ditPersonaMoral");
			criteria.add(Restrictions.eq("ditPersonaMoral.rfc", RFC));
		}
		criteria.createAlias("ditPatronSujetoObligado.ditPatronGenerals", "patronGeneral");
//		criteria.add(Restrictions.ne("patronGeneral.regPatron", numeroRegistroPatronal));
		
		
		List<Clasificacion> clasesActualesPorPatron = Collections.emptyList();
		List<DitClasificacion> clasificaciones = criteria.list();
		if(!clasificaciones.isEmpty()){
			clasesActualesPorPatron = new ArrayList<Clasificacion>();
			for(DitClasificacion clasificacion : clasificaciones){
				if(clasificacionCorrespondeAlRP(numeroRegistroPatronal, clasificacion))
					continue;
				System.err.println("DicFraccionClase: "+clasificacion.getDicFraccionClase());
				if(clasificacion.getDicFraccionClase()!=null)
					System.err.println("CLASE EXISTENTE: "+ clasificacion.getDicFraccionClase().getDicClase().getCveIdClase());
				System.err.println("IndRegPatClase: "+ clasificacion.getIndRegPatClase());
				Clasificacion newClasificacion = clasificacionUtlity.convertirEntityToModelClasificacion(clasificacion);
				System.err.println("CLASE AGREGADA: "+ newClasificacion);
				clasesActualesPorPatron.add(newClasificacion);
			}
		}
		return clasesActualesPorPatron;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Clasificacion> consultarClasesExistentesPorRFC(String RFC, TipoPersonaFiscal tipoPersona) {
		System.err.println("QUERY RPC");
		
		Criteria criteria = super.getSession().createCriteria(DitClasificacion.class);
		criteria.createAlias("ditPatronSujetoObligado", "ditPatronSujetoObligado");
		BigDecimal valor = BigDecimal.ONE;
		criteria.add(Restrictions.eq("indRegPatClase", valor));

		if(tipoPersona.equals(TipoPersonaFiscal.FISICA)){
			criteria.createAlias("ditPatronSujetoObligado.ditPersonaFisica", "ditPersonaFisica");
			criteria.add(Restrictions.eq("ditPersonaFisica.rfc", RFC));
		}else if(tipoPersona.equals(TipoPersonaFiscal.MORAL)){
			criteria.createAlias("ditPatronSujetoObligado.ditPersonaMoral", "ditPersonaMoral");
			criteria.add(Restrictions.eq("ditPersonaMoral.rfc", RFC));
		}
		criteria.createAlias("ditPatronSujetoObligado.ditPatronGenerals", "patronGeneral");

		List<Clasificacion> clasesActualesPorPatron = Collections.emptyList();
		List<DitClasificacion> clasificaciones = criteria.list();
		if(!clasificaciones.isEmpty()){
			clasesActualesPorPatron = new ArrayList<Clasificacion>();
			for(DitClasificacion clasificacion : clasificaciones){
				System.err.println("DicFraccionClase: "+clasificacion.getDicFraccionClase());
				if(clasificacion.getDicFraccionClase()!=null)
					System.err.println("CLASE EXISTENTE: "+ clasificacion.getDicFraccionClase().getDicClase().getCveIdClase());
				System.err.println("IndRegPatClase: "+ clasificacion.getIndRegPatClase());
				Clasificacion newClasificacion = clasificacionUtlity.convertirEntityToModelClasificacion(clasificacion);
				System.err.println("CLASE AGREGADA: "+ newClasificacion);
				clasesActualesPorPatron.add(newClasificacion);
			}
		}

		return clasesActualesPorPatron;
	}

	private boolean clasificacionCorrespondeAlRP(String numeroRegistroPatronal, DitClasificacion clasificacion){
		String regPatronSubdelegacion= numeroRegistroPatronal.substring(0, 8);
		String numModalidad = numeroRegistroPatronal.substring(8,10);
		String digitoVerificador = null;
		if(numeroRegistroPatronal.length()==11)
			numeroRegistroPatronal.substring(10);
		
		System.err.println("RP corto: "+regPatronSubdelegacion);
		System.err.println("RP modalidad: "+numModalidad);
		System.err.println("RP digVer: "+digitoVerificador);
		
		DitPatronSujetoObligado pso = clasificacion.getDitPatronSujetoObligado();
		
		if(numeroRegistroPatronal.length()==11){
			if(pso.getDitPatronGenerals().get(0).getRegPatron().equals(regPatronSubdelegacion)
					&& pso.getDicModalidad().getNumModalidad().equals(numModalidad)
					&& pso.getDitPatronGenerals().get(0).getDigVer().equals(digitoVerificador))
				return true;
		}else if (pso.getDitPatronGenerals().get(0).getRegPatron().equals(regPatronSubdelegacion)
				&& pso.getDicModalidad().getNumModalidad().equals(numModalidad)){
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unused")
	@Override
	public Clasificacion consultarClasificacionPorRegistroPatronal(String numeroRegistroPatronal){
		Criteria criteria = super.getSession().createCriteria(DitClasificacion.class);
		criteria.createAlias("ditPatronSujetoObligado", "ditPatronSujetoObligado");
		
		String regPatronal = numeroRegistroPatronal.substring(0,8);
		
		log.debug("Registro Patronal a 8 posiciones: "+regPatronal);
		criteria.createAlias("ditPatronSujetoObligado.ditPatronGenerals", "patronGeneral");
		criteria.add(Restrictions.eq("patronGeneral.regPatron", regPatronal));
		
		if(numeroRegistroPatronal.length()>8){
			String modalidad = numeroRegistroPatronal.substring(8,10);
			log.debug("modalidad: "+modalidad);
			criteria.createAlias("ditPatronSujetoObligado.dicModalidad", "modalidad");
			criteria.add(Restrictions.eq("modalidad.numModalidad", modalidad));
		}
		
		if(numeroRegistroPatronal.length()==11){
			log.debug("Digito verificador: "+numeroRegistroPatronal.length());
			criteria.add(Restrictions.eq("patronGeneral.digVer", numeroRegistroPatronal.substring(numeroRegistroPatronal.length()-1)));
		}
		
		criteria.addOrder(Order.desc("cveIdClasificacion"));
		
		//DitClasificacion clasificacion = (DitClasificacion)criteria.uniqueResult();
		DitClasificacion clasificacion = null;
		
		List<DitClasificacion> listDitClasificacion = criteria.list();
		clasificacion = listDitClasificacion.get(0);
		
		Clasificacion newClasificacion = clasificacionUtlity.convertirEntityToModelClasificacion(clasificacion);
		return newClasificacion;
	}

	@Override
	public void crearActualizarReintentoRPC(Solicitud solicitud) {
		DitReintentoRPC entity = em.find(DitReintentoRPC.class, solicitud.getSolicitudId());
		if(entity!= null){
			entity.setIndReintentoRpc(solicitud.isIndReintentoRpc() ? 1 : 0);
			entity.setIndRpcInvalido(solicitud.isIndRpcInvalido() ? 1 : 0);
			entity.setFecRegistroActualizado(Calendar.getInstance().getTime());
			//em.refresh(entity);
		}else{
			entity = new DitReintentoRPC();
			entity.setCveIdSolicitud(solicitud.getSolicitudId());
			entity.setIndReintentoRpc(solicitud.isIndReintentoRpc() ? 1 : 0);
			entity.setIndRpcInvalido(solicitud.isIndRpcInvalido() ? 1 : 0);
			entity.setFecRegistroAlta(Calendar.getInstance().getTime());
			em.persist(entity);
		}
	}

	@Override
	public Solicitud consultarReintentoRPC(Long idSolicitud) {
		DitReintentoRPC entity = em.find(DitReintentoRPC.class, idSolicitud);
		
		if(entity!=null){
			Solicitud solicitud = new Solicitud();
			solicitud.setSolicitudId(idSolicitud);
			solicitud.setIndReintentoRpc(entity.getIndReintentoRpc().intValue()==1 ? true : false);
			solicitud.setIndRpcInvalido(entity.getIndRpcInvalido().intValue()==1 ? true : false);
			return solicitud;
		}
		
		return null;
	}

	@Override
	public Fraccion consultarFraccionPorId(Long idFraccion) {
		DicFraccion entity = em.find(DicFraccion.class, idFraccion); 
		return clasificacionUtlity.convertirEntityToModelFraccion(entity);
	}
	
	
	public List<Fraccion> obtenerFraccionesPreviasPorRfcPatron(String rfc){
		StringBuffer qFr = new StringBuffer();
		qFr.append("select clasificacion from DitClasificacion clasificacion ");
		qFr.append("join clasificacion.ditPatronSujetoObligado pso join pso.ditPatronGenerals pg ");
		qFr.append("join pso.ditPersonaMoral moral ");
		qFr.append("left outer join pg.ditDtsExtraPatron dep ");
		qFr.append("where pso.ditPersonaMoral.rfc =:rfc ");
		qFr.append("and ((pso.fecRegistroBaja is null and dep.cveTipoMovto is null) or (pso.fecRegistroBaja is null and dep.cveTipoMovto is not null and dep.cveTipoMovto !=:idBaja)) ");
		
		Query query =this.em.createQuery(qFr.toString());
		query.setParameter("rfc", rfc);
		query.setParameter("idBaja", new BigDecimal(CausaBajaPatronEnum.BAJA.getClave()));
		@SuppressWarnings("unchecked")
		List<DitClasificacion> clasificaciones = query.getResultList();
		List<Fraccion> fracciones=new ArrayList<Fraccion>();
		
		if(clasificaciones!=null && !clasificaciones.isEmpty()){
			for(DitClasificacion cl : clasificaciones){
				DicFraccion dicFraccion = cl.getDicFraccionClase().getDicFraccion();
				Fraccion fraccion=clasificacionUtlity.convertirEntityToModelFraccion(dicFraccion);
				fracciones.add(fraccion);
			}
		}
		return fracciones;
	}
	
	public List<Fraccion> obtenerFraccionesPreviasPorRfcPatronMunicipioIMSS(String rfc, String cvecMunicipioSINDO){
		StringBuffer qFr = new StringBuffer();
		qFr.append("select clasificacion from DitClasificacion clasificacion ");
		qFr.append("join clasificacion.ditPatronSujetoObligado pso join pso.ditPatronGenerals pg ");
		qFr.append("join pso.ditPersonaMoral moral ");
		qFr.append("join pso.ditMunicipioPatSujOblig dm ");
		qFr.append("join dm.dicMunicipioImss dcm ");
		qFr.append("left outer join pg.ditDtsExtraPatron dep ");
		qFr.append("where pso.ditPersonaMoral.rfc =:rfc ");
		qFr.append("and ((pso.fecRegistroBaja is null and dep.cveTipoMovto is null) or (pso.fecRegistroBaja is null and dep.cveTipoMovto is not null and dep.cveTipoMovto !=:idBaja)) " +
				"and dcm.cveMunicipio =:cveIdMunicipioImss ");
		
		Query query =this.em.createQuery(qFr.toString());
		query.setParameter("rfc", rfc);
		query.setParameter("idBaja", new BigDecimal(CausaBajaPatronEnum.BAJA.getClave()));
		query.setParameter("cveIdMunicipioImss", cvecMunicipioSINDO);
		@SuppressWarnings("unchecked")
		List<DitClasificacion> clasificaciones = query.getResultList();
		List<Fraccion> fracciones=new ArrayList<Fraccion>();
		
		if(clasificaciones!=null && !clasificaciones.isEmpty()){
			for(DitClasificacion cl : clasificaciones){
				DicFraccion dicFraccion = cl.getDicFraccionClase().getDicFraccion();
				Fraccion fraccion=clasificacionUtlity.convertirEntityToModelFraccion(dicFraccion);
				fracciones.add(fraccion);
			}
		}
		return fracciones;
	}
	
	public String obtenerNrpPrimaRiesgoMayorPersonaFisica(String rfc) {
//		StringBuffer query = new StringBuffer();

		String msgError = "No tiene registros patronales asociados";
		
		try {
			
			StringBuffer queryBaja = new StringBuffer();
			queryBaja.append("select registroPatronal from DitPatronSujetoObligado registroPatronal");
			queryBaja.append(" join registroPatronal.ditPersonaFisica fisica");
			queryBaja.append(" join registroPatronal.dicModalidad modalidad");
			queryBaja.append(" join registroPatronal.ditPatronGenerals pg");
			queryBaja.append(" join registroPatronal.ditClasificacions clasificacion");
			queryBaja.append(" join pg.ditDtsExtraPatron dep");
			queryBaja.append(" WHERE fisica.rfc = :rfcFiltro ");	
			queryBaja.append(" AND fisica.fecRegistroBaja IS NULL ");
			queryBaja.append(" AND registroPatronal.fecRegistroBaja IS NULL ");		
			queryBaja.append(" AND modalidad.numModalidad in (10,13) ");
			queryBaja.append(" AND ( dep.cveTipoMovto IS NULL OR dep.cveTipoMovto <> :claveBaja ) ");			
			queryBaja.append(" ORDER BY clasificacion.numPrimaPago desc");			
			
			
			
			Query query = this.em.createQuery(queryBaja.toString());		
			query.setParameter("rfcFiltro", rfc);
			query.setParameter("claveBaja", CausaBajaPatronEnum.BAJA.getClave());
			
			@SuppressWarnings("unchecked")
			List<DitPatronSujetoObligado> registrosPatronales = query.getResultList();
			
			if(registrosPatronales==null || (registrosPatronales!=null && registrosPatronales.isEmpty()))
				return msgError;
				
			
			for(DitPatronSujetoObligado ditPso :registrosPatronales){
				DitPatronGeneral pg = ditPso.getDitPatronGenerals().get(0);
				//DitDtsExtraPatron dep = pg.getDitDtsExtraPatron();
				Integer numTrabajadores = 0;
				
				Modalidad modalidad = sujetoObligadoUtility.convertirEntityToModelModalidad(ditPso.getDicModalidad());
				
				PatronesTempInc patronesTempInc = sujetoObligadoServiceEntityLocal.getPatronesTempInc(
						pg.getRegPatron(), 
						String.valueOf(modalidad.getNumModalidad()), 
						pg.getDigVer());
				
				if(patronesTempInc != null){
					numTrabajadores = patronesTempInc.getNumTraVigPerm() !=null ? patronesTempInc.getNumTraVigPerm().intValue() : 0;
					numTrabajadores += patronesTempInc.getNumTraVigEven()!=null ? patronesTempInc.getNumTraVigEven().intValue() : 0;
					numTrabajadores += patronesTempInc.getNumTraVigCons()!=null ? patronesTempInc.getNumTraVigCons().intValue() : 0;
					numTrabajadores += patronesTempInc.getNumTraMexExtr()!=null ? patronesTempInc.getNumTraMexExtr().intValue() : 0;
				}
				
				if(numTrabajadores>0){
					DicModalidad ml = ditPso.getDicModalidad();
					String nrp = pg.getRegPatron() + ml.getNumModalidad() + pg.getDigVer();
					return nrp;
				}
			}
			
//			DitPatronSujetoObligado nrpPrimaMayor = registrosPatronales.get(0);
//			
//			DitPatronGeneral pg = nrpPrimaMayor.getDitPatronGenerals().get(0);
//			DicModalidad ml = nrpPrimaMayor.getDicModalidad();
//			
//			String nrp = nrpPrimaMayor.getDitPatronGenerals().get(0).getRegPatron() + ml.getNumModalidad() + pg.getDigVer();
//			
//			DitDtsExtraPatron dep = nrpPrimaMayor.getDitPatronGenerals().get(0).getDitDtsExtraPatron();
//			
//			Integer numTrabajadores = 0;
//			
//			if(dep!=null){
//				numTrabajadores = dep.getCanTrabVigPer()!=null ? dep.getCanTrabVigCons() : 0;
//				numTrabajadores += dep.getCanTrabVigEve()!=null ? dep.getCanTrabVigEve() :0;
//				numTrabajadores += dep.getCanTrabVigCons()!=null ? dep.getCanTrabVigCons() : 0;
//				numTrabajadores += dep.getCanTrabVigMexExtran()!=null ? dep.getCanTrabVigMexExtran() : 0;
//			}
			
			
//			if(numTrabajadores>0)
//				return nrp;
//			else
				return msgError;
						
//			query.append("SELECT pg.REG_PATRON || dm.NUM_MODALIDAD || pg.DIG_VER nrp ");
//			query.append("FROM DIT_PERSONA_FISICA pf ");
//			query.append("JOIN DIT_PATRON_SUJETO_OBLIGADO pso ON pso.cve_id_persona_fisica = pf.cve_id_persona_fisica ");
//			query.append("JOIN DIT_PATRON_GENERAL pg ON pso.cve_id_patron_sujeto_obligado = pg.cve_id_patron_sujeto_obligado ");
//			query.append("JOIN DIT_CLASIFICACION dc ON dc.cve_id_patron_sujeto_obligado = pso.cve_id_patron_sujeto_obligado ");
//			query.append("JOIN DIC_MODALIDAD dm ON dm.cve_id_modalidad = pso.cve_id_modalidad ");
//			
//			//Realiza la busqueda por RFC
//			query.append("WHERE pf.RFC = '" + rfc +"'");
//			//Con modalidad 10 y 13
//			query.append("AND dm.NUM_MODALIDAD IN (10,13) order by dc.NUM_PRIMA_PAGO desc ");
			
//			Query emQuery = em.createNativeQuery(query.toString());
//			@SuppressWarnings("unchecked")
//			List<String> lstRP = emQuery.getResultList();
//			
//			if (lstRP!=null && lstRP.size()>0) {
//				 response = lstRP.get(0);
//			} else {
//				response = msgError;
//			}
//			return  response;

		} catch (NoResultException e) {
			
			return msgError;
		}
	}

	public List<Fraccion> obtenerFraccionesPreviasPorRfcPatronMunicipioIMSSRPC(String rfc, String cvecMunicipioSINDO){
		StringBuffer qFr = new StringBuffer();
		qFr.append("select clasificacion from DitClasificacion clasificacion ");
		qFr.append("join clasificacion.ditPatronSujetoObligado pso join pso.ditPatronGenerals pg ");
		qFr.append("join pso.ditPersonaMoral moral ");
		qFr.append("join pso.ditMunicipioPatSujOblig dm ");
		qFr.append("join dm.dicMunicipioImss dcm ");
		qFr.append("left outer join pg.ditDtsExtraPatron dep ");
		qFr.append("where pso.ditPersonaMoral.rfc =:rfc ");
		qFr.append("and ((pso.fecRegistroBaja is null and dep.cveTipoMovto is null) or (pso.fecRegistroBaja is null and dep.cveTipoMovto is not null and dep.cveTipoMovto !=:idBaja)) " +
				"and dcm.cveMunicipio =:cveIdMunicipioImss ");

		Query query =this.em.createQuery(qFr.toString());
		query.setParameter("rfc", rfc);
		query.setParameter("idBaja", new BigDecimal(CausaBajaPatronEnum.BAJA.getClave()));
		query.setParameter("cveIdMunicipioImss", cvecMunicipioSINDO);
		@SuppressWarnings("unchecked")
		List<DitClasificacion> clasificaciones = query.getResultList();
		List<Fraccion> fracciones=new ArrayList<Fraccion>();

		if(clasificaciones!=null && !clasificaciones.isEmpty()){
			for(DitClasificacion cl : clasificaciones){
				DicFraccion dicFraccion = cl.getDicFraccionClase().getDicFraccion();
				Fraccion fraccion=clasificacionUtlity.convertirEntityToModelFraccion(dicFraccion);
				fraccion.setIndRPC(cl.getIndRegPatClase());
				fracciones.add(fraccion);
			}
		}
		return fracciones;
	}

	@Override
	public List<String> buscaSolicitudesSimilares(String nrp, String cveIdTipoTramite, String fechaSurteEfecto) {

		this.log.debug(" Buscando solicitudes similares ::: nrp: " + nrp + ", cveIdTipoTramite: " + cveIdTipoTramite
				+ ", fechaSurteEfecto: " + fechaSurteEfecto);
		List<String> folios = new ArrayList<String>();
		try {
			StringBuffer bfr = new StringBuffer();
			bfr.append(" Select solicitud from DitTramitePatSujObligado tso ");
			bfr.append(" join tso.ditPatronSujetoObligado pso ");
			bfr.append(" join tso.ditTramite as tramite ");
			bfr.append(" join tramite.ditSolicitud as solicitud ");
			bfr.append(" join pso.ditPatronGenerals patronGeneral ");
			bfr.append(" where patronGeneral.regPatron = :nrp ");
			bfr.append(" and tramite.dicTipoTramite.cveIdTipoTramite = :idTipoTramite ");
			bfr.append(" and to_date(tramite.fecEfecto) = TO_DATE(:fechaSurteEfecto ,'dd/MM/yyyy') "); 
			bfr.append(" and solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud = :idEstadoSolicitud ");
			
			Query query = this.em.createQuery(bfr.toString());
			query.setParameter("nrp", nrp);
			query.setParameter("idTipoTramite", cveIdTipoTramite);
			query.setParameter("fechaSurteEfecto", fechaSurteEfecto);
			query.setParameter("idEstadoSolicitud", EstadoSolicitudEnum.ATENDIDA.getCodigo().longValue());
			
			@SuppressWarnings("unchecked")
			List<DitSolicitud> solicitudes = query.getResultList();

			if(solicitudes!=null && !solicitudes.isEmpty()){
				for(DitSolicitud sol : solicitudes){
					folios.add(sol.getRefFolio());
				}
			}
		}catch(Exception e) {
			log.debug("Error al buscar solicitudes similares, nrp: "+nrp+", " + e.getMessage());
			e.printStackTrace();
			return new ArrayList<String>();
		}

		return folios;
	}
	
	@Override
	public void insertaMensajeBuzon(String nrp, String cveIdTipoTramite, String folio, String mensaje) {
		
		try {
			log.debug(":::: Guardando mensaje en buzon de clasificacion, folio: " + folio);

			if(mensaje.length() > 500) {
				mensaje = mensaje.substring(0,500);
			}
			
			if(nrp.length() > 8) {
				nrp = nrp.substring(0, 8);
			}

			DitBuzonClasificacion entity= null;
			try{
				String qlString = "from DitBuzonClasificacion d where d.regpatron = :nrp";
				Query query = em.createQuery(qlString);
				query.setParameter("nrp", nrp);		
				try {
					entity = (DitBuzonClasificacion)query.getSingleResult();			
				}catch(NoResultException e) {
					log.debug(":::No se encontro mensaje en el buzon: nrp: "+nrp+", " + e.getMessage());
					entity = null;
				}				
				if(entity != null){
					log.debug("::: Se actualizara mensaje de buzon para el patron: " + nrp);
					entity.setCveIdTipoTramite(new Long(cveIdTipoTramite));
					entity.setFecRegistroAlta(Calendar.getInstance().getTime());
					entity.setMensaje(mensaje);
					entity.setRefFolio(folio);
				}else {
					log.debug("::: Se insertara mensaje de buzon para el patron: " + nrp);
					entity = new DitBuzonClasificacion();
					entity.setCveIdTipoTramite(new Long(cveIdTipoTramite));
					entity.setFecRegistroAlta(Calendar.getInstance().getTime());
					entity.setMensaje(mensaje);
					entity.setRefFolio(folio);
					entity.setRegpatron(nrp);
					em.persist(entity);					
				}
			}catch(Exception exc){
				log.error("Error al consultar buzon: "+exc.getMessage());
				exc.printStackTrace();
			}

		}catch(Exception e) {
			log.debug("Error al insertar mensaje en buzon de clasificacion: "+ e.getMessage());
			e.printStackTrace();
		}
		
	}
		
	@Override
	public BuzonClasificacion consultaMensajeBuzon(String nrp){
		BuzonClasificacion buzon = new BuzonClasificacion();
		try {			
			if(nrp.length() > 8) {
				nrp = nrp.substring(0, 8);
			}
			DitBuzonClasificacion entity= null;
			String qlString = "from DitBuzonClasificacion d where d.regpatron = :nrp";
			Query query = em.createQuery(qlString);
			query.setParameter("nrp", nrp);			
			entity = (DitBuzonClasificacion)query.getSingleResult();			
			if(entity != null){
				if(entity.getMensaje() != null) {
					buzon.setId(entity.getCveIdBuzon());
					buzon.setRegPatron(entity.getRegpatron());
					buzon.setIdTipoTramite(entity.getCveIdTipoTramite());
					buzon.setRefFolio(entity.getRefFolio());
					buzon.setMensaje(entity.getMensaje());
					buzon.setFecPresentacion(entity.getFecRegistroAlta());
				}
			}
		}catch(Exception e) {
			log.debug("Error al consultar mensaje en buzon de clasificacion: "+ e.getMessage());
			e.printStackTrace();
			return null;
		}
		return buzon;
	}		
	
}
