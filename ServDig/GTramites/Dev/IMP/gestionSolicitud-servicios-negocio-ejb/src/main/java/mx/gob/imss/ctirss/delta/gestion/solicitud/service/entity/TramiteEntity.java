package mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.exceptions.InfoComplementariaTramiteException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.utility.SolicitudConversorLocal;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.utility.TramiteConversorLocal;
import mx.gob.imss.ctirss.delta.model.enums.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaInteresadaSolEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteInfo;
import mx.gob.imss.ctirss.delta.persistence.DicTramiteInfo;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaFisica;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

@Stateless(name = "tramiteEntity", mappedName = "tramiteEntity")
public class TramiteEntity extends AbstractServiceEntity implements
		TramiteEntityLocal {
	
	@EJB TramiteConversorLocal tramiteConversorLocal;
	@EJB SolicitudConversorLocal solicitudConversor;

	@SuppressWarnings("unchecked")
	@Override
	public List<Tramite> getTramitesAbierto(List<Long> idPersonas, Long idPersonaAseguradoPensionado, List<Long> idModulos) throws Exception {
		List<DitTramitePersonaFisica> tramitesPersona = null;
		List<Tramite> tramites = null;
		//Si no hay personas no ejecuta la consulta
		
		
			try {
				log.info("Consultando tramites abiertos por idPersonaAseguradoPensionado: "+idPersonaAseguradoPensionado);
				
				
				
				Criteria queryTramitePersona = this.getSession().createCriteria(DitTramitePersonaFisica.class);
				queryTramitePersona.createAlias("ditPersona", "persona");
				queryTramitePersona.add(Restrictions.in("persona.cveIdPersona", idPersonas));
				
				List<Long> estadosCerrados = new ArrayList<Long>();
				estadosCerrados.add(EstadoTramiteEnum.CERRADO.getId());
				estadosCerrados.add(7L);
				Criteria queryTramite = queryTramitePersona.createCriteria("ditTramite");
				queryTramite.createAlias("dicEstadoTramite", "estado");
				queryTramite.add(Restrictions.not(Restrictions.in("estado.cveIdEstadoTramite", estadosCerrados)));
				
				Criteria queryPersonaInteresada = queryTramite.createCriteria("ditSolicitud").createCriteria("ditPersonaInteresadaSols");
				queryPersonaInteresada.createAlias("dicTipoPersonaInteresadaSol", "tipoPerInt");
				queryPersonaInteresada.createAlias("ditPersona", "perInt");
				queryPersonaInteresada.add(Restrictions.eq("perInt.cveIdPersona", idPersonaAseguradoPensionado));
				queryPersonaInteresada.add(Restrictions.eq("tipoPerInt.cveTipoInteresadaSol", TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.getId()));
				
				
				
				
				
				
				if(idModulos!=null && !idModulos.isEmpty()){
					Criteria queryTipoTramite = queryTramite.createCriteria("dicTipoTramite");
					queryTipoTramite.createAlias("dicModulos", "modulo");
					queryTipoTramite.add(Restrictions.in("modulo.cveIdModulo", idModulos));
				}
				
				
				
				tramitesPersona = queryTramitePersona.list();
				
				if(tramitesPersona != null && !tramitesPersona.isEmpty()) {
					tramites = new ArrayList<Tramite>();
					
					for(DitTramitePersonaFisica ditTF : tramitesPersona) {
						Tramite tram = tramiteConversorLocal.convertirEntityToModel(ditTF.getDitTramite());
						if(ditTF.getDitPersona() != null) {
							DitPersona ditP = ditTF.getDitPersona();
							tram.setPersona(new Fisica());
							tram.getPersona().setNombre(ditP.getNomNombre());
							tram.getPersona().setPrimerApellido(ditP.getNomPrimerApellido());
							tram.getPersona().setSegundoApellido(ditP.getNomSegundoApellido());
						}
						
						tramites.add(tram);
					}
				}
			} catch (Exception e) {
				log.error("getTramitesByNss", e);
				throw e;
			}
			
			return tramites;
	}
	
	

	@Override
	public void buscarYCancelarTramitesPorIdsPersonasYAsegurado(
		
		List<Long> idPersonas, Long idPersonaAseguradoPensionado,String observaciones) {
		
		
		if (StringUtils.isNotBlank(observaciones)) {
			observaciones = observaciones.length() > 255 ? observaciones.substring(0,250) : observaciones;
		}
		
		String inicioQueryTramite = "select DISTINCT(dtpf.CVE_ID_TRAMITE) "; 
		String inicioQuerySolicitud = "select DISTINCT(sol.CVE_ID_SOLICITUD) ";
		
		String queryBuscaTramite = "from DIT_TRAMITE_PERSONA_FISICA " +
				"dtpf, DIT_TRAMITE tram, DIT_SOLICITUD sol,DIT_PERSONA_INTERESADA_SOL pis, DIC_MODULO_TIPO_TRAMITE dmt , " +
				"DIC_TIPO_TRAMITE tipo where tram.CVE_ID_TRAMITE = dtpf.CVE_ID_TRAMITE and sol.CVE_ID_SOLICITUD = pis.CVE_ID_SOLICITUD " +
				"and sol.CVE_ID_SOLICITUD = tram.CVE_ID_SOLICITUD and tram.CVE_ID_TIPO_TRAMITE = dmt.CVE_ID_TIPO_TRAMITE and " +
				"dmt.CVE_ID_MODULO = 4 and pis.CVE_TIPO_INTERESADA_SOL = "+TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.getId()
				+" and pis.CVE_ID_PERSONA = "+idPersonaAseguradoPensionado+" and " +
				"dtpf.CVE_ID_PERSONA in("+this.getListaPersonasString(idPersonas)+")" +
				" and tram.CVE_ID_ESTADO_TRAMITE not in (2,7) ";
		
		String queryUdateTramite  = "update DIT_TRAMITE set CVE_ID_ESTADO_TRAMITE = 2,CVE_ID_RAZON_RESULTADO = 5," +
				"IND_RESULTADO = 0, FEC_CONCLUSION = sysdate, FEC_REGISTRO_ACTUALIZADO = sysdate,REF_OBSERVACION = '"+observaciones+"'" +
				"where CVE_ID_TIPO_TRAMITE in ("+inicioQueryTramite + "" +queryBuscaTramite+") ";
		
		String queryUpdateSolicitud = "update DIT_SOLICITUD set CVE_ID_ESTADO_SOLICITUD = "+EstadoSolicitudEnum.CANCELADA.getCodigo()+
				",FEC_CONCLUSION = sysdate, " +
				"FEC_REGISTRO_ACTUALIZADO = sysdate,REF_OBSERVACION = '"+observaciones+"' where CVE_ID_SOLICITUD in (" +
				inicioQuerySolicitud + "" + queryBuscaTramite + ")";

		try {
			this.getSession().createSQLQuery(queryUdateTramite).executeUpdate();
			this.getSession().createSQLQuery(queryUpdateSolicitud).executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private String getListaPersonasString(List<Long> idsPersonas) {
		StringBuilder commaSepValueBuilder = new StringBuilder();

		for ( int i = 0; i< idsPersonas.size(); i++){
			commaSepValueBuilder.append(idsPersonas.get(i));
			if ( i != idsPersonas.size()-1){
				commaSepValueBuilder.append(",");
			}
		}
		
		return commaSepValueBuilder.toString();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Solicitud> getTramitePersona(List<Long> idPersonas,
			List<Long> tipoTramites, List<Long> estadoTramites,
			Long indResultado, Long razonResultado, Long idPersonaInt,
			Boolean tramiteEnLista, Integer maxResult, Boolean ordenDescendente) throws Exception {
		
		List<Solicitud> solicitudes =  null;
		List<DitSolicitud> ditSolicitudes = null;
		
			try {
				
				Criteria querySolicitud = this.getSession().createCriteria(DitSolicitud.class);
				Criteria queryPersonaInteresada = querySolicitud.createCriteria("ditPersonaInteresadaSols");
				//queryPersonaInteresada.createAlias("dicTipoPersonaInteresadaSol", "tipoPerInt");
				queryPersonaInteresada.createAlias("ditPersona", "perInt");
				queryPersonaInteresada.add(Restrictions.eq("perInt.cveIdPersona", idPersonaInt));
				//queryPersonaInteresada.add(Restrictions.eq("tipoPerInt.cveTipoInteresadaSol", TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.getId()));
				
				Criteria queryTramite = querySolicitud.createCriteria("ditTramites");
				
				if(tipoTramites != null) {
					queryTramite.createAlias("dicTipoTramite", "tipo");
					if(tramiteEnLista)
						queryTramite.add(Restrictions.in("tipo.cveIdTipoTramite",tipoTramites));
					else
						queryTramite.add(Restrictions.not(Restrictions.in("tipo.cveIdTipoTramite",tipoTramites)));
				}
				
				if(estadoTramites != null) {
					queryTramite.createAlias("dicEstadoTramite", "estado");
					queryTramite.add(Restrictions.in("estado.cveIdEstadoTramite", estadoTramites));
				}
				
				if(indResultado != null) {
					queryTramite.add(Restrictions.eq("indResultado", new BigDecimal(indResultado)));
				}
				
				if(razonResultado != null) {
					queryTramite.createAlias("dicRazonResultado", "razon");
					queryTramite.add(Restrictions.eq("razon.cveIdRazonResultado",razonResultado));
				}
				
				if(idPersonas != null && !idPersonas.isEmpty()) {
					Criteria queryTramitePersona = queryTramite.createCriteria("ditTramitePersonaFisica");
					queryTramitePersona.createAlias("ditPersona", "persona");
					queryTramitePersona.add(Restrictions.in("persona.cveIdPersona", idPersonas));
				}
				
				if(maxResult != null) {
					querySolicitud.setMaxResults(maxResult);
				}
				
				if(ordenDescendente != null) {
					if(ordenDescendente)
						querySolicitud.addOrder(Order.desc("fecRegistroActualizado"));
					else
						querySolicitud.addOrder(Order.asc("fecRegistroActualizado"));
				}
				
				ditSolicitudes = querySolicitud.list();
				
				if(ditSolicitudes != null && !ditSolicitudes.isEmpty()) {
					solicitudes = new ArrayList<Solicitud>();
					
					for(DitSolicitud ditSolicitud : ditSolicitudes) {
						solicitudes.add(solicitudConversor.convertirEntityToModel(ditSolicitud, true));
					}
				}
	
			} catch (Exception e) {
				log.error("getTramitesByNss", e);
				throw e;
			}
			
			
		return solicitudes;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Tramite> findTramitesXPersona(Long idPersona ,List<Long> estadoTramite) throws Exception {
		List<Tramite> tramites =  null;
		
		List<DitTramitePersonaFisica> tramitesPersona = null;
		//Si no hay personas no ejecuta la consulta
			try {
				
				
				Criteria queryTramitePersona = this.getSession().createCriteria(DitTramitePersonaFisica.class);
				queryTramitePersona.createAlias("ditPersona", "persona");
				queryTramitePersona.add(Restrictions.eq("persona.cveIdPersona", idPersona));
				
				if(estadoTramite != null) {
					Criteria queryTramite = queryTramitePersona.createCriteria("ditTramite");
					queryTramite.createAlias("dicEstadoTramite", "estado");
					queryTramite.add(Restrictions.in("estado.cveIdEstadoTramite", estadoTramite));
				}
				
				tramitesPersona = queryTramitePersona.list();
				
				if(tramitesPersona != null && !tramitesPersona.isEmpty()) {
					tramites = new ArrayList<Tramite>();
					
					for(DitTramitePersonaFisica ditTramite: tramitesPersona) {
						tramites.add(tramiteConversorLocal.convertirEntityToModel(ditTramite.getDitTramite()));
					}
				}
				
			} catch (Exception e) {
				log.error("findTramitesXPersona", e);
				throw e;
			}
			
		return tramites;	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Tramite getUltimoTramitePersonaXTipoTramite(Long idPersona,
			List<Long> tiposTramite) throws Exception {

		Tramite tramite = null;
		
		try {
			Criteria queryTramitePersona = this.getSession().createCriteria(DitTramitePersonaFisica.class);
			queryTramitePersona.createAlias("ditPersona", "persona");
			queryTramitePersona.add(Restrictions.eq("persona.cveIdPersona", idPersona));
			
			Criteria queryTramite = queryTramitePersona.createCriteria("ditTramite");
			queryTramite.createAlias("dicTipoTramite", "tipo");
			queryTramite.add(Restrictions.in("tipo.cveIdTipoTramite", tiposTramite));
			
			queryTramite.addOrder(Order.desc("fecTramite"));
			
			queryTramite.setMaxResults(1);
			
			List<DitTramitePersonaFisica> tramites = queryTramitePersona.list();
			
			DitTramitePersonaFisica ditTramitePersonaFisica = null;
			if (tramites != null && tramites.size() > 0) {
			
				ditTramitePersonaFisica = tramites.get(0);
				tramite =tramiteConversorLocal.convertirEntityToModel(ditTramitePersonaFisica.getDitTramite());
	
			} 
		} catch (Exception e) {
			log.error("getUltimoTramitePersona", e);
			throw e;
		}
		return tramite;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Solicitud getUltimaSolicitudPorEstadoTramiteYTipoTramitegetUltimoTramiteByEstado(Long idPersona,
			List<Long> tiposTramite, Long estado) throws Exception{
		
		List<DitSolicitud> ditSolicitudes = null;
		Solicitud solicitud = null;
		
		try {
			
			Criteria querySolicitud = this.getSession().createCriteria(DitSolicitud.class);
			
			Criteria queryTramite = querySolicitud.createCriteria("ditTramites");
			queryTramite.createAlias("dicTipoTramite", "tipo");
			queryTramite.createAlias("dicEstadoTramite", "estado");
			queryTramite.add(Restrictions.in("tipo.cveIdTipoTramite", tiposTramite));
			queryTramite.add(Restrictions.eq("estado.cveIdEstadoTramite", estado));
			queryTramite.addOrder(Order.desc("fecTramite"));
			
			Criteria queryTramitePersona = queryTramite.createCriteria("ditTramitePersonaFisica");
			queryTramitePersona.createAlias("ditPersona", "persona");
			queryTramitePersona.add(Restrictions.eq("persona.cveIdPersona", idPersona));
			
			querySolicitud.setMaxResults(1);
			
			ditSolicitudes = querySolicitud.list();
			
			if (ditSolicitudes != null && ditSolicitudes.size() > 0) {
				solicitud = solicitudConversor.convertirEntityToModel(ditSolicitudes.get(0), false);
			}
		} catch (Exception e) {
			log.error("getUltimoTramite", e);
			throw e;
		}

		return solicitud;
	}
	
	@Override
	public TramiteInfo obtenerInfoComplementariaTramite(long idTipoTramite,
			long idOrigen) throws InfoComplementariaTramiteException {
		
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("from DicTramiteInfo tInfo ");
		jpaQuery.append("where tInfo.dicTipoTramite.cveIdTipoTramite = :idTipoTramite ");
		jpaQuery.append("and tInfo.dicOrigen.cveIdOrigenSolicitud = :idOrigen");
		
		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("idTipoTramite", idTipoTramite);
		query.setParameter("idOrigen", idOrigen);
		
		DicTramiteInfo entity = null;
		TramiteInfo model = null;
		
		try {
			entity = (DicTramiteInfo) query.getSingleResult();
			
			model = this.tramiteConversorLocal.convertirEntityToModel(entity);
			
		} catch (NoResultException e) {
			throw new InfoComplementariaTramiteException(
					"No se encontró datos complementarios para el trámite",
					1000);
		} catch (NonUniqueResultException e) {
			throw new InfoComplementariaTramiteException(
					"Se encontró más de un registro de datos complementarios para el trámite",
					1001);
		}
		
		return model;
	}

	
	/**
	 * Obtiene todos los trámites cuya fecha de conclusión es mayor o igual que la proporcionada
	 * 
	 * @param origenSolicitud Long
	 * @param tipoSolicitud Long 
	 * @param idPersona Long
	 * @param fechaConclusion Date
	 * @return List<Tramite> o null si no encontr&oacute; tramites 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Tramite> obtenerTramitesCerrados( List<Long> origenSolicitud, Integer tipoSolicitud, Long idPersona, Date fechaConclusion, Long tipoTramite ) throws Exception {
	
		try {
			log.info("obtenerTramites -> Consultando tramites abiertos por idPersona: "+idPersona);
			

			// ---------------------------------------------------
			// Trámites de la persona física
			// ---------------------------------------------------
			Criteria queryTramitePersona = this.getSession().createCriteria(DitTramitePersonaFisica.class);
			queryTramitePersona.createAlias("ditPersona", "persona");
			queryTramitePersona.add(Restrictions.eq("persona.cveIdPersona", idPersona));
			
			
			// --------------------------------------------------------------------------------
			// Trámites cerrados y con fecha de conclusión mayor o igual a la indicada
			// Tipo de tr&aacute;mite: Cambio Cl&iacute;nica
			// --------------------------------------------------------------------------------
			Criteria queryTramite = queryTramitePersona.createCriteria("ditTramite");
			queryTramite.createAlias("dicEstadoTramite", "estadoTramite");
			queryTramite.createAlias("dicTipoTramite", "tipoTramite");
			queryTramite.add(Restrictions.eq("estadoTramite.cveIdEstadoTramite", EstadoTramiteEnum.CERRADO.getId()));
			queryTramite.add(Restrictions.eq("tipoTramite.cveIdTipoTramite", tipoTramite));
			
			if( fechaConclusion != null )
				queryTramite.add(Restrictions.ge("fecConclusion", fechaConclusion));
			
			
			// -----------------------------------------------------
			// Solicitudes atendiadas
			// con el origen y tipo indicados
			// -----------------------------------------------------
			Criteria querySolicitud = queryTramite.createCriteria("ditSolicitud");
			querySolicitud.createAlias("dicTipoSolicitud", "tipoSolicitud");
			querySolicitud.createAlias("dicEstadoSolicitud", "estadoSolicitud");
			querySolicitud.createAlias("dicOrigenSolicitud", "origenSolicitud");
			querySolicitud.add(Restrictions.eq("tipoSolicitud.cveIdTipoSolicitud", tipoSolicitud.longValue()));
			querySolicitud.add(Restrictions.eq("estadoSolicitud.cveIdEstadoSolicitud", EstadoSolicitudEnum.ATENDIDA.getCodigo().longValue()));
			//validamos si la lista de solicitudes no es nula y no no es vacia
			if(origenSolicitud!= null && !origenSolicitud.isEmpty()) {
				if(origenSolicitud.size() == 1) {
					querySolicitud.add(Restrictions.eq("origenSolicitud.cveIdOrigenSolicitud", origenSolicitud.get(0)));
				} else {
					querySolicitud.add(Restrictions.in("origenSolicitud.cveIdOrigenSolicitud", origenSolicitud.toArray()));
				}
			}
			
			
			
			// -----------------------------------------------------------
			// Si encontramos trámites en la BDTU los regresamos
			// En caso contrario se regresa NULL
			// -----------------------------------------------------------
			List<DitTramitePersonaFisica> tramitesPersona = queryTramitePersona.list();
			
			if(tramitesPersona != null && !tramitesPersona.isEmpty()) {
				List<Tramite> tramites = new ArrayList<Tramite>();
				
				for(DitTramitePersonaFisica ditTF : tramitesPersona) {
					Tramite tram = tramiteConversorLocal.convertirEntityToModel(ditTF.getDitTramite());
					if(ditTF.getDitPersona() != null) {
						DitPersona ditP = ditTF.getDitPersona();
						tram.setPersona(new Fisica());
						tram.getPersona().setNombre(ditP.getNomNombre());
						tram.getPersona().setPrimerApellido(ditP.getNomPrimerApellido());
						tram.getPersona().setSegundoApellido(ditP.getNomSegundoApellido());
						tram.getPersona().setIdPersona(ditP.getCveIdPersona());
					}
					
					tramites.add(tram);
				}
				
				return tramites;
			}
		} catch (Exception e) {
			log.error("getTramitesByNss", e);
			throw e;
		}
		
		return null;
	
	}
	
	

	@Override
	public Solicitud getUltimaSolicitudPatronPorTipoEstado(
			Long idPatronSujetoObligado, List<Long> tiposTramite, Long estado)
			throws Exception {
		return this.getUltimaSolicitud(idPatronSujetoObligado, tiposTramite, estado, 3L);
	}



	private Solicitud getUltimaSolicitud(Long idPersona,
			List<Long> tiposTramite, Long estado, Long tipoPersona) throws Exception{
		
		List<DitSolicitud> ditSolicitudes = null;
		Solicitud solicitud = null;
		tipoPersona = tipoPersona == null ? 1L: tipoPersona;
		boolean obtenederDetalle = estado != null && estado.equals(EstadoTramiteEnum.INICIADO.getId());
		
		try {
			
			Criteria querySolicitud = this.getSession().createCriteria(DitSolicitud.class);
			
			Criteria queryTramite = querySolicitud.createCriteria("ditTramites");
			queryTramite.createAlias("dicTipoTramite", "tipo");
			queryTramite.createAlias("dicEstadoTramite", "estado");
			queryTramite.add(Restrictions.in("tipo.cveIdTipoTramite", tiposTramite));
			queryTramite.add(Restrictions.eq("estado.cveIdEstadoTramite", estado));
			queryTramite.addOrder(Order.desc("fecTramite"));
			
			//si el tipo de persona es 1 es fisica
			if(tipoPersona.equals(1L)){
				Criteria queryTramitePersona = queryTramite.createCriteria("ditTramitePersonaFisica");
				queryTramitePersona.createAlias("ditPersona", "persona");
				queryTramitePersona.add(Restrictions.eq("persona.cveIdPersona", idPersona));
			} else if(tipoPersona.equals(2L)) {
				Criteria queryTramitePersona = queryTramite.createCriteria("ditTramitePersonaMoral");
				queryTramitePersona.createAlias("ditPersonaMoral", "persona");
				queryTramitePersona.add(Restrictions.eq("persona.cveIdPersonaMoral", idPersona));
			} else if(tipoPersona.equals(3L)) {
				Criteria queryTramitePersona = queryTramite.createCriteria("ditTramitePatSujObligados");
				queryTramitePersona.createAlias("ditPatronSujetoObligado", "persona");
				queryTramitePersona.add(Restrictions.eq("persona.cveIdPatronSujetoObligado", idPersona));
			}
			
			querySolicitud.setMaxResults(1);
			
			ditSolicitudes = querySolicitud.list();
			
			
			if (ditSolicitudes != null && ditSolicitudes.size() > 0) {
				solicitud = solicitudConversor.convertirEntityToModel(ditSolicitudes.get(0), obtenederDetalle);
			}
		} catch (Exception e) {
			log.error("getUltimoTramite", e);
			throw e;
		}

		return solicitud;
	}

    @Override
    @SuppressWarnings("unchecked")
    public List<Long> encontrarSolicitudesCDAPorEstadoYCurp(List<Long> idsEstadoTramite, String curp) {

        log.error("obtener tramites para la persona con curp: " + curp + ", estados de tramite:" + idsEstadoTramite);

        StringBuilder strQuery = new StringBuilder();
        strQuery.append(" SELECT sol.CVE_ID_SOLICITUD ");
        strQuery.append(" FROM DIT_CORRECCION_DATOS_ASEG corr ");
        strQuery.append(" INNER JOIN DIT_TRAMITE tra ON tra.CVE_ID_TRAMITE = corr.CVE_ID_TRAMITE ");
        strQuery.append(" INNER JOIN DIT_SOLICITUD sol ON sol.CVE_ID_SOLICITUD = tra.CVE_ID_SOLICITUD ");
        strQuery.append(" INNER JOIN DIT_DETALLE_TRAMITE dt ON dt.CVE_ID_TRAMITE = tra.CVE_ID_TRAMITE ");
        strQuery.append(" WHERE TRA.CVE_ID_ESTADO_TRAMITE IN (:estadosTramite) ");
        strQuery.append(" AND(CORR.REF_CURP IN( :curp )) ");
        strQuery.append(" AND(TRA.CVE_ID_TIPO_TRAMITE=139 ");
        strQuery.append(" AND CORR.CVE_ID_TRAMITE=TRA.CVE_ID_TRAMITE) ");
        strQuery.append(" ORDER BY tra.FEC_REGISTRO_ALTA DESC ");

        try {
            SQLQuery sqlQuery = getSession().createSQLQuery(strQuery.toString());
            sqlQuery.setParameter("curp", curp);
            sqlQuery.setParameterList("estadosTramite", idsEstadoTramite);
            List<Long> lstResultado = (List<Long>) sqlQuery.list();
            if (lstResultado != null && !lstResultado.isEmpty()) {
                return lstResultado;
            }
        } catch (Exception e) {
            log.error("ocurio un erro al consular los tramies de CDA para el asegurado con curp" + curp, e);
        }
        return null;
    }
	
}
