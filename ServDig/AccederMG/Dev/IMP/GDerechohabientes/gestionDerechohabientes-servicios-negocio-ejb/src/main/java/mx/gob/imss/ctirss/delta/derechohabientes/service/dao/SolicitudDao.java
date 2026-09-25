package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.PersonaInteresadaSolParserLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.SolicitudParserLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.util.DateUtils;
import mx.gob.imss.ctirss.delta.derechohabientes.util.DeltaUtils;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.TramiteParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.TramiteSimpleParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.SolicitudesAtendidasDto;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.SolicitudDto;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.SolicitudesPendientesAutorizacionDto;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.enums.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.CitaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.PersonaInteresadaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoTramite;
import mx.gob.imss.ctirss.delta.persistence.DitBitacoraSegTramite;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleTramite;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaInteresadaSol;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaFisicaPK;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;


/**
 * @author Mario Teran Blanco,VictorCamacho
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 10/04/2012
 */
@Stateless(name = "solicitudDao", mappedName = "solicitudDao")

public class SolicitudDao extends AbstractServiceEntity implements SolicitudDaoLocal {

	private static final Logger log = Logger.getLogger(SolicitudDao.class);
	
	@EJB
	private PersonaInteresadaSolParserLocal  personaInteresadaSolParserLocal;
	@EJB
	private SolicitudParserLocal solicitudParserLocal;

	/**
	 * metodo para buscar una solicitud proporcionando el folio de la misma
	 * @param folio de la solicitud a buscar
	 * @return solicitud encontrada de acuerdo al folio indicado
	 * @throws DerechohabientesBusinessException 
	 */
	
	
	
	@Override
	public Solicitud getSolicitudByFolio(String folio) throws DerechohabientesBusinessException,Exception {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<DitSolicitud> cQuery = cb.createQuery(DitSolicitud.class);
		Root<DitSolicitud> root = cQuery.from(DitSolicitud.class);
		DitSolicitud ditSolicitud = null;
		try {
			cQuery.select(root);
			Predicate conjunction = cb.conjunction();//se crea unoa conjuntion para poder hacer and
			conjunction.getExpressions().add(cb.equal(root.get("refFolio"), folio));
			conjunction.getExpressions().add(cb.isNull(root.get("fecRegistroBaja")));
			cQuery.where(conjunction);
					
			ditSolicitud = em.createQuery(cQuery).getSingleResult();
		}catch (NoResultException e) {
			return null;
		}catch (Exception e) {
			log.error("getSolicitudByFolio", e);
			throw e;
		}
				
		Solicitud solicitud = solicitudParserLocal.persisToModel(ditSolicitud);
		if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.ATENDIDA.getId().longValue())){
			throw new DerechohabientesBusinessException(ExceptionMessages.FOLIO_SOLICITUD_ATENDIDA);
		}
		if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.CANCELADA.getId().intValue())){
			throw new DerechohabientesBusinessException(ExceptionMessages.FOLIO_SOLICITUD_CANCELADA);
		}
		if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(EstadoSolicitudEnum.VALIDADA.getId().intValue())){
			throw new DerechohabientesBusinessException(ExceptionMessages.FOLIO_SOLICITUD_ATENDIDA);
		}
		return solicitud;
	}

	 
	
	/**
	 * @author Mario Teran Blanco
	 * metodo para obtener una solicitud de acuerdo a su id
	 * @param idSolicitud id de la solicitud a buscar
	 * @return solicitud encontrada
	 */
	
	@Override
	public Solicitud getSolicitudById(Long idSolicitud) throws DerechohabientesBusinessException,Exception {
		Solicitud solicitud = null;
		DitSolicitud ditSolicitud = null;
		
		try {
			ditSolicitud = (DitSolicitud) em.find(DitSolicitud.class, idSolicitud);			
		} catch(NoResultException e){
			ditSolicitud = null;
		} catch (Exception e) {
			log.error("getSolicitudById", e);
			throw e;
		}
		
		solicitud = solicitudParserLocal.persisToModel(ditSolicitud);	
		return solicitud;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Tramite> findTramites(Long idSolicitud) throws DerechohabientesBusinessException,Exception{
		List<DitTramitePersonaFisica> ditTramites= new ArrayList<DitTramitePersonaFisica>();
		List<Tramite> tramites = new ArrayList<Tramite>();
		try {
			Query query = em.createNamedQuery("findTramitesBySolicitud");
			query.setParameter("idSolicitud",idSolicitud);
			ditTramites = query.getResultList();
		} catch (Exception e) {
			log.error("findTramites", e);
			throw e;
		}
		
		tramites = TramiteParser.persistTomodelList(ditTramites);
		return tramites;
	}
	
	@Override
	public Solicitud saveSolicitud(Solicitud solicitud) throws DerechohabientesBusinessException,Exception{
		
		DitSolicitud ditSolicitud =solicitudParserLocal.modelToPersist(solicitud);		
		try {
			ditSolicitud.setRefFolio("1234");
			em.persist(ditSolicitud);
			
			String folio = generarFolioSolicitud(ditSolicitud.getDicTipoSolicitud().getCveIdTipoSolicitud(), ditSolicitud.getCveIdSolicitud());		
			ditSolicitud.setRefFolio(folio);		
			solicitud.setSolicitudId(ditSolicitud.getCveIdSolicitud());
			solicitud.setNoFolioSolicitud(folio);
			em.flush();	
		} catch (Exception e) {
			log.error("saveSolicitud", e);
			throw e;
		}
			
		return solicitud;	
	}
    
    
	
	/**
	 * @author Mario Teran Blanco
	 * Metodo para guardar la solicitud y sus tramites relacionado
	 * @param solicitud - Solicitud que sera guardada
	 * @throws DerechohabientesBusinessException 
	 */
	
	
	@Override
	public Solicitud updateSolicitudSaveTramite(Solicitud solicitud) throws DerechohabientesBusinessException,Exception {
		// TODO Auto-generated method stub
		//actualizamos la solicitud que en este punto ya se guardo el id de la solicitud
		DitSolicitud ditSolicitud = solicitudParserLocal.modelToPersist(solicitud);
		try {
			em.merge(ditSolicitud);
			em.flush();
			//recorremos cada tramite de la solicitud para guardarlo
			for(Tramite tramite: solicitud.getTramites()) {
				//Le ponemos al tramite la solicitud con la cual ira ligada
				//TODO verificar solicitud
				//tramite.setSolicitud(new Solicitud());
				//tramite.getSolicitud().setIdSolicitud(solicitud.getIdSolicitud());
				//guardamos el tramite sin persona
				DitTramite ditTramite = TramiteSimpleParser.modelToPersist(tramite);
				em.persist(ditTramite);
				em.flush();
				//una vez que ha sido guardado obtenemos el id del tramite que se le asigno 
				//al nuevo tramite
				tramite.setTramiteId(ditTramite.getCveIdTramite());
				//ahora guardamos dittramitePErsonaFisica
				em.persist(TramiteParser.modelToPersistPartial(tramite));
				em.flush();
				if(tramite.getDetalleTramiteXml()!=null){
					DitDetalleTramite detalleTramite = new DitDetalleTramite();
					detalleTramite.setCveIdTramite(ditTramite.getCveIdTramite());
					detalleTramite.setFecRegistroAlta(new Date());
					detalleTramite.setRefDatosTramiteXml(tramite.getDetalleTramiteXml());
					em.persist(detalleTramite);
					em.flush();
				}
			}
		} catch (Exception e) {
			log.error("Error - updateSolicitudSaveTramite", e);
			throw e;
		}
		
		
		return solicitud;
	}
	
	
	/**
	 * @author Mario Teran Blanco
	 * Metodo para guardar la solicitud y sus tramites relacionado
	 * @param solicitud - Solicitud que sera guardada
	 * @throws DerechohabientesBusinessException 
	 */
	
	
	@Override
	public Solicitud updateSolicitudSaveTramite(Solicitud solicitud,List<GrupoFamiliar> integrantes) throws DerechohabientesBusinessException,Exception {
		// TODO Auto-generated method stub
		//actualizamos la solicitud que en este punto ya se guardo el id de la solicitud
		DitSolicitud ditSolicitud = solicitudParserLocal.modelToPersist(solicitud);
		try {
			em.merge(ditSolicitud);
			em.flush();
			//recorremos cada tramite de la solicitud para guardarlo
			for(Tramite tramite: solicitud.getTramites()) {
				//Le ponemos al tramite la solicitud con la cual ira ligada
				//TODO verificar solicitu
				//tramite.setSolicitud(new Solicitud());
				//tramite.getSolicitud().setIdSolicitud(solicitud.getIdSolicitud());
				//guardamos el tramite sin persona
				DitTramite ditTramite = TramiteSimpleParser.modelToPersist(tramite);
				ditTramite.setDitSolicitud(new DitSolicitud());
				ditTramite.getDitSolicitud().setCveIdSolicitud(solicitud.getSolicitudId());
				em.persist(ditTramite);
				em.flush();
				//una vez que ha sido guardado obtenemos el id del tramite que se le asigno 
				//al nuevo tramite
				tramite.setTramiteId(ditTramite.getCveIdTramite());
				//ahora guardamos dittramitePErsonaFisica
				for(GrupoFamiliar integrante: integrantes) {
					tramite.setPersona(new Fisica());
					tramite.getPersona().setIdPersona(integrante.getDerechohabiente().getIdPersona());
					em.persist(TramiteParser.modelToPersistPartial(tramite));
				}
				
				em.flush();
				if(tramite.getDetalleTramiteXml()!=null){
					DitDetalleTramite detalleTramite = new DitDetalleTramite();
					detalleTramite.setCveIdTramite(ditTramite.getCveIdTramite());
					detalleTramite.setFecRegistroAlta(new Date());
					detalleTramite.setRefDatosTramiteXml(tramite.getDetalleTramiteXml());
					em.persist(detalleTramite);
					em.flush();
				}
			}
		} catch (Exception e) {
			log.error("Error - updateSolicitudSaveTramite", e);
			throw e;
		}
		
		
		return solicitud;
	}
	
	private static String generarFolioSolicitud(Long tipoSolicitud,Long idSolicitud) throws DerechohabientesBusinessException{
		Calendar hoy = new GregorianCalendar();
		String resultado =""+tipoSolicitud;
		String solicitud =idSolicitud.toString();
		String ceros="";
	    try {
	    	int dia=hoy.get(Calendar.DATE);
		    int mes =hoy.get(Calendar.MONTH)+1;
		    int anio =hoy.get(Calendar.YEAR); 
		    
			for(int i=0;i<(9-solicitud.length());i++){
				ceros+="0";
			}
			
			if(dia<10){
				resultado+="0"; 
			}
			
			resultado+=dia;
			
			if(mes<10){
				resultado+="0"; 
			}
			resultado+=mes+""+anio+ceros +idSolicitud;
		} catch (Exception e) {
			throw new DerechohabientesBusinessException("generarFolioSolicitud");
		}
	    
	    		
		return resultado;
	}
	
	@Override
	public void updateSolicitud(Solicitud solicitud) throws DerechohabientesBusinessException,Exception{
		DitSolicitud ditSolicitud =solicitudParserLocal.modelToPersist(solicitud);
		try {
			em.merge(ditSolicitud);
			em.flush();
		} catch (Exception e) {
			log.error("updateSolicitud", e);
			throw e;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DitSolicitud> solicitudesPendientesAut(Long idPersona) throws Exception {
		List<DitPersonaInteresadaSol> solicitudes = null;
		List<DitSolicitud> solPendAut = new ArrayList<DitSolicitud>();
		try {
			Query query = em.createNamedQuery("getSolicitudesDerechohabiente");
			query.setParameter("idPersona",idPersona);
			solicitudes = query.getResultList();
			if(solicitudes.size() > 0){
				for(DitPersonaInteresadaSol pi : solicitudes){
					if(pi.getDitSolicitud() != null){
						if(pi.getDitSolicitud().getDicEstadoSolicitud().getCveIdEstadoSolicitud().longValue() == EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getId().longValue()){
							solPendAut.add(pi.getDitSolicitud());
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("findTramites", e);
			throw e;
		}
				
		return solPendAut;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DitSolicitud> solicitudesPendientesDeAutorizacion(
			SolicitudesPendientesAutorizacionDto solicitudesPenAutDto) throws Exception {
//		Long idPersona=null;
		List<DitSolicitud> solicituds=null;
		//Path<DitSolicitud> path=null;
		Long totalSize=null;
		try {
			
			Criteria querySolPen = this.getSession().createCriteria(DitSolicitud.class);
			querySolPen.createAlias("dicEstadoSolicitud", "estado");
			querySolPen.add(Restrictions.eq("estado.cveIdEstadoSolicitud", EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getId()));
			querySolPen.add(Restrictions.isNull("fecRegistroBaja"));
			Criteria umfCita = querySolPen.createCriteria("ditUmfTurno");
			umfCita.createAlias("dicUmf", "umf");
			umfCita.add(Restrictions.eq("umf.cveIdUmf", solicitudesPenAutDto.getIdUmf()));
			
			
			querySolPen.addOrder(Order.asc("fecRegistroActualizado"));
			
			querySolPen.setProjection(Projections.rowCount());
			totalSize = (Long)querySolPen.uniqueResult();
			
			querySolPen.setProjection(null);
			querySolPen.setResultTransformer(Criteria.ROOT_ENTITY);
			querySolPen.setFirstResult(solicitudesPenAutDto.getPagStar().intValue());
			querySolPen.setMaxResults(solicitudesPenAutDto.getPagEnd().intValue());
		
			solicituds = querySolPen.list();
			solicitudesPenAutDto.setDatosTotales(totalSize);
			solicitudesPenAutDto.setDatosMostrados(totalSize);
		} catch (Exception e) {
			log.error("Error - solicitudesPendientesDeAutorizacion", e);
			throw e;
		}
		
		return solicituds;
	}
	
	
	
	/**
	 * @author Mario Teran Blanco
	 * Metodo para actualizar una solicitud y los tramites relacionadosa ella
	 * @param Solicitud que se actualizara
	 * @return void
	 * @throws Exception 
	 */
	
	
	@Override
	public void updateSolicitudTramites(Solicitud solicitud, Fisica personaUsuario) throws Exception {
		// TODO Auto-generated method stub
		try {
			DitSolicitud ditSolicitud = solicitudParserLocal.modelToPersist(solicitud);
			em.merge(ditSolicitud);		
			
			for(Tramite tramite : solicitud.getTramites()){
				DitTramite original = em.find(DitTramite.class,tramite.getTramiteId());
				DitTramite ditTramite = TramiteSimpleParser.modelToPersist(tramite);
				List<DitDocumentoProbatorio> documentos = original.getDitDocumentoProbatorios();
				
				if(documentos != null && documentos.size() >0)
					ditTramite.setDitDocumentoProbatorios(documentos);
				
				ditTramite.setDitDetalleTramite(original.getDitDetalleTramite());
				
				
				em.merge(ditTramite);
			    
				if(original.getDitDetalleTramite() != null) {
					original.getDitDetalleTramite().setRefDatosTramiteXml(tramite.getDetalleTramiteXml());
					original.getDitDetalleTramite().setFecRegistroActualizado(new Date());
					em.merge(original.getDitDetalleTramite());
				}
				tramite.setPersona(personaUsuario);
				insertBitacoraSegTramite(tramite);
			}
		} catch (Exception e) {
			log.error("updateSolicitudTramites", e);
			throw e;
		}
		
	}
	
	public Long countSolicitudesFechaTurnoUmf(CitaSolicitud cita) throws Exception {
		em.flush();
		SimpleDateFormat ojbFormat = new SimpleDateFormat("yyyyMMdd");
		Long resultado = null;
		try {
			String queryS="SELECT count(s.cveIdSolicitud) FROM DitSolicitud s WHERE  to_char(s.fecCita, 'yyyyMMdd') =:fechaConsulta " +
			"AND s.ditUmfTurno.id.cveIdTurno=:cveIdTurno" +
			" AND s.dicEstadoSolicitud.cveIdEstadoSolicitud=:cveIdEstadoSolicitud"+
			"  AND s.ditUmfTurno.id.cveIdUmf=:cveIdUmf";

		      final TypedQuery<Long> query = em.createQuery(queryS, 
		    		  	 Long.class).setParameter("fechaConsulta", ojbFormat.format(cita.getFechaHora()) )
		    		  	.setParameter("cveIdTurno", cita.getTurno().getIdTurno())
		    		  	.setParameter("cveIdUmf" , cita.getUmf().getIdUMF())
		    		  	.setParameter("cveIdEstadoSolicitud", EstadoSolicitudEnum.REGISTRADA.getId());
		      			resultado = query.getSingleResult();
		} catch(NoResultException e){
			resultado = null;
		} catch (Exception e) {
			log.error("Error -countSolicitudesFechaTurnoUmf", e);
			throw e;
		}
	      return  resultado;
  		
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DitSolicitud> solicitudesDerechohabientes(SolicitudDto solicitudesDto) throws Exception{
		List<DitSolicitud> solicituds=null;
		String igualOrigen = solicitudesDto.getExluirOrigenes() ? "!=" : "=";
		String incluirOrigenes = solicitudesDto.getExluirOrigenes() ? "not in" : "in";
		
		Long totalSize=null;
		
		log.debug("Entramos en la consulta de solicitudes de registro de derechohabiente");
		if(solicitudesDto == null
					|| solicitudesDto.getEstadosSolicitud() == null || solicitudesDto.getNumNss() == null 
					|| solicitudesDto.getCveModulo() == null){
			log.debug("los parametros para la consulta son incorrectos");
			throw new IllegalArgumentException();
		}
		//origenes de la solicitud
		List<Long> origenes = solicitudesDto.getCveOrigenesSol();
		List<Long> idEstadosSolicitud = solicitudesDto.getEstadosSolicitud();
		
		//definimos lo que tienen en comun el query de solicitudes y el count
		StringBuffer queryComun = new StringBuffer();
		queryComun.append("FROM DitSolicitud s, DitPersonaInteresadaSol pi, DitAsignacionNss anss, ");
		queryComun.append("DitTramite t, DicTipoTramite tt JOIN tt.dicModulos m ");
		if(idEstadosSolicitud != null && !idEstadosSolicitud.isEmpty()) {
			if(idEstadosSolicitud.size()==1) {
				queryComun.append("WHERE s.dicEstadoSolicitud.cveIdEstadoSolicitud = :idEstadosSolicitud ");
			} else {
				queryComun.append("WHERE s.dicEstadoSolicitud.cveIdEstadoSolicitud in (:idEstadosSolicitud) ");
			}
		}
		if(origenes != null && !origenes.isEmpty()) {
			if(origenes.size() == 1){ 
				queryComun.append("AND s.dicOrigenSolicitud.cveIdOrigenSolicitud "+igualOrigen+" :origenVentanilla ");
			} else {
				queryComun.append("AND s.dicOrigenSolicitud.cveIdOrigenSolicitud "+incluirOrigenes+" (:origenVentanilla) ");
			}
		}
		queryComun.append("AND t.ditSolicitud.cveIdSolicitud = s.cveIdSolicitud ");
		queryComun.append("and t.dicTipoTramite.cveIdTipoTramite = tt.cveIdTipoTramite and m.cveIdModulo = :moduloDerecho ");
		queryComun.append("AND pi.ditSolicitud.cveIdSolicitud=s.cveIdSolicitud " );
		queryComun.append("AND pi.ditPersona.cveIdPersona=anss.ditPersona.cveIdPersona ");
		queryComun.append("AND anss.cveIdAsignacionNss = :idAsignacionNSS");
		
		//definimos el query que traera las solicitudes
		StringBuffer strQuery = new StringBuffer();
		strQuery.append("SELECT DISTINCT s ");
		strQuery.append(queryComun);
		strQuery.append(" ORDER BY s.fecSolicitud desc");
		log.debug("la consulta de las solicitudes es: " + strQuery.toString());
		//definimos el query del count de solicitudes
		StringBuffer strQueryCunt = new StringBuffer();
		strQueryCunt.append("SELECT count(DISTINCT s.cveIdSolicitud) ");
		strQueryCunt.append(queryComun);
		log.debug("La consulta para el count es: " + strQueryCunt.toString());
		
		try {
			
			Query query = em.createQuery(strQuery.toString());
			if(origenes != null && !origenes.isEmpty()) {
				query.setParameter("origenVentanilla", origenes.size() == 1 ? origenes.get(0) : origenes);
			}
			if(idEstadosSolicitud != null && !idEstadosSolicitud.isEmpty()) {
				query.setParameter("idEstadosSolicitud", idEstadosSolicitud.size() == 1 ? idEstadosSolicitud.get(0) : idEstadosSolicitud);
			}
			query.setParameter("moduloDerecho", solicitudesDto.getCveModulo().longValue());
			query.setParameter("idAsignacionNSS", solicitudesDto.getCveIdNSS());
		
			log.debug("Se establecen los limites de la consulta de solicitudes y se ejecuta la consulta de solicitudes");
			solicituds=query.setFirstResult(solicitudesDto.getPaginacionDto().getPagStar().intValue()).setMaxResults(solicitudesDto.getPaginacionDto().getPagEnd().intValue()).getResultList();
			
			//Contar
			TypedQuery<Long> countQ=em.createQuery(strQueryCunt.toString() ,Long.class);
			if(origenes != null && !origenes.isEmpty()) {
				countQ.setParameter("origenVentanilla", origenes.size() == 1 ? origenes.get(0) : origenes);
			}
			if(idEstadosSolicitud != null && !idEstadosSolicitud.isEmpty()) {
				countQ.setParameter("idEstadosSolicitud", idEstadosSolicitud.size() == 1 ? idEstadosSolicitud.get(0) : idEstadosSolicitud);
			}
			countQ.setParameter("moduloDerecho", solicitudesDto.getCveModulo().longValue());
			countQ.setParameter("idAsignacionNSS", solicitudesDto.getCveIdNSS());

			
			log.debug("Se ejecuta la consulta del count de solicitudes");
			totalSize =countQ.getSingleResult();
			
			solicitudesDto.getPaginacionDto().setDatosTotales(totalSize);
			//yo lo arregle el mario nada que ver solo fue mi secretario
			solicitudesDto.getPaginacionDto().setDatosMostrados(totalSize);
		} catch (Exception e) {
			log.error("solicitudesRegistradas", e);
			throw e;
		}
		
		return solicituds;
	}
	
	
		
	
	
	@Override
	public void savePersonaInteresadaSolicitud(
			PersonaInteresadaSolicitud miPersonaInteresada) throws DerechohabientesBusinessException,Exception {
		DitPersonaInteresadaSol miDitPersonaIntSol = new DitPersonaInteresadaSol();
		miDitPersonaIntSol = personaInteresadaSolParserLocal.modelToPersist(miPersonaInteresada);
		try {
			em.persist(miDitPersonaIntSol);
			em.flush();
		} catch (Exception e) {
			log.error("savePersonaInteresadaSolicitud", e);
			throw e;
		}
		
	}

	
	
	/**
	 * Metodo para obtener un tramite junto con su persona
	 * recibiendo los siguientes parametros
	 * @param idTramite el id del tramite
	 * @param idPersona el id de la persona
	 * @return Tramite el tramite encontrado
	 * @throws DerechohabientesBusinessException 
	 */
	
	
	@Override
	public Tramite getTramite(Long idTramite, Long idPersona) throws DerechohabientesBusinessException,Exception {
		// TODO Auto-generated method stub
		Tramite tramite = null;
		DitTramitePersonaFisica ditTramite = null;
		try {
			//Creamos la llave con los parametros recibidos
			DitTramitePersonaFisicaPK llave = new DitTramitePersonaFisicaPK();
			llave.setCveIdTramite(idTramite);
			llave.setCveIdPersona(idPersona);
			
			//buscamos el tramite
			ditTramite = em.find(DitTramitePersonaFisica.class, llave);
		}catch (NoResultException e){
			ditTramite = null;
		}catch (Exception e) {
			log.error("getTramite", e);
			throw e;
		}
		
		tramite = TramiteParser.persistTomodelCompleto(ditTramite);
		
		return tramite;
	}
	
	@Override
	public Tramite getTramite(Long idTramite) throws DerechohabientesBusinessException,Exception {
		// TODO Auto-generated method stub
		Tramite tramite = null;
		DitTramite ditTramite = null;
		try {
			//buscamos el tramite
			ditTramite = em.find(DitTramite.class, idTramite);			
		} catch (NoResultException e){
			ditTramite = null;
		} catch (Exception e) {
			log.error("getTramite", e);
			throw e;
		}
		tramite = TramiteParser.persistTomodelCompleto(ditTramite);
		
		return tramite;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PersonaInteresadaSolicitud> tramitesRechazados(long idPersona) throws DerechohabientesBusinessException,Exception {
		List<DitPersonaInteresadaSol> ditListaPerIntSol = null;
		List<PersonaInteresadaSolicitud> listaPerIntSol = null;		
		try {
			
			 
			String	strQuery = "select p from DitPersonaInteresadaSol p where p.ditPersona.cveIdPersona=:idPersona";
			Query query = em.createQuery(strQuery);
			query.setParameter("idPersona", idPersona);
			ditListaPerIntSol = query.getResultList();
			listaPerIntSol = personaInteresadaSolParserLocal.persisToModelList(ditListaPerIntSol);
						
		} catch (Exception e) {
			log.error("Error - tramitesRechazados", e);
			throw e;
		}
		
		return listaPerIntSol;
	}
	
	
	@Override
	public Long updateCitaSolicitudesPorCambioMasivoClinica(
			Asentamiento asentamiento,MedicoEnTurno medicoEnTurno,Date fechaCita) throws Exception{
		Long numColumnasAfectadas=null;
		try {
			String query="Update DIT_SOLICITUD sol set sol.CVE_ID_UMF=" + medicoEnTurno.getUnidadMedicaFamiliar().getIdUMF().intValue() +
			",sol.CVE_ID_TURNO="+ medicoEnTurno.getTurno().getIdTurno().intValue() +
			",sol.FEC_CITA =to_date("+DateUtils.dateFormatCustom(fechaCita, "yyyyMMdd") + ",'yyyyMMdd')"+ 
			" where exists ("+
							" select sol.rowid from  DIT_GRUPO_FAMILIAR gru, DG_DOMICILIO_GEOGRAFICO dom, DIT_PERSONA_INTERESADA_SOL perIntSol"+
							" where perIntSol.CVE_ID_SOLICITUD =sol.CVE_ID_SOLICITUD "+
							" and sol.FEC_REGISTRO_BAJA IS NULL"+
							" and sol.CVE_ID_ESTADO_SOLICITUD= 1"+
							//la fecha de cita sea mayor al dia de hoy
							" and to_date(to_char(sol.FEC_CITA,'yyyyMMdd'),'yyyyMMdd')> to_date(to_char(sysdate,'yyyyMMdd'),'yyyyMMdd')"+
							" and gru.CVE_ID_PERSONA_INTEGRANTE= perIntSol.CVE_ID_PERSONA"+
							" and dom.DOMICILIO_ID = gru.DOMICILIO_ID"+
							" and dom.CVE_MUN = "+ asentamiento.getLocalidad().getMunicipio().getClave() +
							" and dom.CVE_LOC = "+	asentamiento.getLocalidad().getClave() +
							" and dom.CVE_ASEN = "+  asentamiento.getClave() +
							" and dom.CVE_ENT = "+ asentamiento.getLocalidad().getMunicipio().getEntidadFederativa().getClave() +
							") ";
			Session session = em.unwrap(Session.class);
			SQLQuery q = session.createSQLQuery(query);
			numColumnasAfectadas=new Long(q.executeUpdate());
		} catch (Exception e) {
			log.error("Error - updateCitaSolicitudesPorCambioMasivoClinica", e);
			throw e;
		}
			
			return numColumnasAfectadas;
	}

	@Override
	public void insertBitacoraSegTramite(Tramite tramite) throws Exception {
		try {
			DitBitacoraSegTramite unDitBitSegTramite = new DitBitacoraSegTramite();
			DitTramite unDitTramite = new DitTramite();
			unDitTramite.setCveIdTramite(tramite.getTramiteId());				
			DicEstadoTramite unDicEstadoTramite = new DicEstadoTramite();
			unDicEstadoTramite.setCveIdEstadoTramite(tramite.getEstadoTramite().getIdEstadoTramitePersona().longValue());		
			DitPersona unDitPersona = new DitPersona();		
			unDitPersona.setCveIdPersona(tramite.getPersona().getIdPersona());
			
			unDitBitSegTramite.setDitTramite(unDitTramite);
			unDitBitSegTramite.setDicEstadoTramite(unDicEstadoTramite);
			unDitBitSegTramite.setDitPersona(unDitPersona);
			unDitBitSegTramite.setCuentaUsuario("");
			unDitBitSegTramite.setFecRegistroAlta(new Date());
//			unDitBitSegTramite.setRefDatosTramiteXml(tramite.getDetalleTramiteXml());
			unDitBitSegTramite.setIpTramite("");
			unDitBitSegTramite.setRefObservaciones(tramite.getObservacion());
			
			em.persist(unDitBitSegTramite);
			em.flush();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DitSolicitud> consultaSolAtendidasByUsuario(
			SolicitudesAtendidasDto solicitudesAtendidasDto) throws Exception {
		Long idPersona=solicitudesAtendidasDto.getIdPersona();
		List<DitSolicitud> solicituds=null;		
		Long totalSize=null;
		try{
			Criteria querySolAtendidas = this.getSession().createCriteria(DitSolicitud.class);
			
			Criteria queryTramite = querySolAtendidas.createCriteria("ditTramites").createCriteria("ditBitacoraSegTramite");
			queryTramite.createAlias("ditPersona", "persona");
			queryTramite.createAlias("dicEstadoTramite", "estado");
			queryTramite.add(Restrictions.eq("persona.cveIdPersona", idPersona));
			queryTramite.add(Restrictions.eq("estado.cveIdEstadoTramite", EstadoTramiteEnum.CERRADO.getId()));
			querySolAtendidas.addOrder(Order.desc("fecRegistroActualizado"));
			
			querySolAtendidas.setProjection(Projections.countDistinct("cveIdSolicitud"));
			totalSize = (Long) querySolAtendidas.uniqueResult();
			
			querySolAtendidas.setProjection(null);
			querySolAtendidas.setResultTransformer(Criteria.ROOT_ENTITY);
			querySolAtendidas.setFirstResult(solicitudesAtendidasDto.getPagStar().intValue());
			querySolAtendidas.setMaxResults(solicitudesAtendidasDto.getPagEnd().intValue());
			
			solicituds = querySolAtendidas.list();
			solicitudesAtendidasDto.setDatosTotales(totalSize);
			solicitudesAtendidasDto.setDatosMostrados(totalSize);

		} catch (Exception e) {
			log.error("consultaSolAtendidasByUsuario", e);
			throw e;
		}

		return solicituds;
	}

	@Override
	public Tramite saveTramite(Tramite tramite) throws Exception {
	
		DitTramite ditTramite = TramiteSimpleParser.modelToPersist(tramite);
		em.persist(ditTramite);
		em.flush();
		//una vez que ha sido guardado obtenemos el id del tramite que se le asigno 
		//al nuevo tramite
		tramite.setTramiteId(ditTramite.getCveIdTramite());
		//ahora guardamos dittramitePErsonaFisica
		em.persist(TramiteParser.modelToPersistPartial(tramite));
		em.flush();
		if(tramite.getDetalleTramiteXml()!=null){
			DitDetalleTramite detalleTramite = new DitDetalleTramite();
			detalleTramite.setCveIdTramite(ditTramite.getCveIdTramite());
			detalleTramite.setFecRegistroAlta(new Date());
			detalleTramite.setRefDatosTramiteXml(tramite.getDetalleTramiteXml());
			em.persist(detalleTramite);
			em.flush();
		}
			
		return tramite;
	}



	@Override
	public List<Tramite> getTramitesBySolicitud(Long idSolicitud) throws Exception {
		List<Tramite> tramites = new ArrayList<Tramite>();
		List<DitTramite> ditTramites = null;		
		try {
			
			 
			String	strQuery = "select p from DitTramite p where p.ditSolicitud.cveIdSolicitud=:idSolicitud";
			Query query = em.createQuery(strQuery);
			query.setParameter("idSolicitud", idSolicitud);
			ditTramites = query.getResultList();
			if(ditTramites != null){
				for (DitTramite ditTramite : ditTramites) {
					tramites.add(TramiteParser.persistTomodelCompleto(ditTramite));
				}
			}
		} catch (Exception e) {
			log.error("Error - busqueda tramites pro solicitud", e);
			throw e;
		}
		
		return tramites;
	}	
	
	 /**
     * Metodo para consultar si un NSS a realizado tramites de derechohabientes en IMSS digital
     * @param strNSS
     * @return int con valor 0 si no tiene tramites y 1 si tiene uno o mas tramites el NSS
     */
    public int consultaTramitesDerechohabientePorNSS (String strNSS){
	
    	
    if(strNSS != null && strNSS.length() == 10){
    	strNSS += DeltaUtils.generaDigitoVerificador(strNSS);
    }
    
    int tramites = 0;
    String resultado;	
    StringBuffer strQuery = new StringBuffer();
    strQuery.append("select NUM_NSS ");
    strQuery.append("from dit_persona_interesada_sol pis, dit_asignacion_nss nss, dit_solicitud sol, ");
    strQuery.append("dit_tramite t,  dic_modulo_tipo_tramite md ");
    strQuery.append("where pis.cve_id_solicitud = sol.cve_id_solicitud ");
    strQuery.append("and sol.cve_id_estado_solicitud = 2 ");
    strQuery.append("and nss.cve_id_persona = pis.cve_id_persona ");
    strQuery.append("and t.cve_id_tipo_tramite = md.cve_id_tipo_tramite ");
    strQuery.append("and md.cve_id_modulo = 4 ");
    strQuery.append("and t.cve_id_solicitud = sol.cve_id_solicitud ");
    strQuery.append("and nss.num_nss = '" + strNSS +"' ");
    strQuery.append("group by NUM_NSS ");
    
    	Session session = this.getSession();
		SQLQuery q = session.createSQLQuery(strQuery.toString());
		List lstNSS = q.list();
		
		if(lstNSS != null && !lstNSS.isEmpty()) {
			for(Object nss: lstNSS) {
				String existe = (String)nss;
				
				 if(!StringUtils.isEmpty(existe)){
				    	tramites = 1;
				    	log.debug("regresando como respuesta [" + tramites +"]");
				    	return tramites;
				    }
			}
		}
    log.debug("regresando como respuesta [" + tramites +"]");
    return tramites;
	
    }
	
}
