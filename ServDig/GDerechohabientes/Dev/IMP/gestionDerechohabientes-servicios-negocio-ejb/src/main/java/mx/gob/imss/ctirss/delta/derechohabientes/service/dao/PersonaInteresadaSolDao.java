package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.PersonaInteresadaSolParserLocal;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.RegistroDto;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.RequisitosDTO;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.PersonaInteresadaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;
import mx.gob.imss.ctirss.delta.model.util.Constants;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaInteresadaSol;
import mx.gob.imss.ctirss.delta.persistence.DitRegistroDerechohabiente;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;

import org.apache.log4j.Logger;
import org.hibernate.Query;

@Stateless(name="personaInteresadaSolDao", mappedName="personaInteresadaSolDao")

public class PersonaInteresadaSolDao extends AbstractServiceEntity implements PersonaInteresadaSolDaoLocal{
	
	private static final Logger logger = Logger.getLogger(PersonaInteresadaSolDao.class);
	
	
	@EJB
	private transient PersonaInteresadaSolParserLocal personaInteresadaSolParser;
	@EJB
	private RegistroDerechohabientesDaoLocal registroDerechohabientesDaoLocal;


	
	@Override
	public PersonaInteresadaSolicitud getPersonaInteresada(Long idPersona, Long idSolicitud) throws DerechohabientesBusinessException,Exception{
		DitPersonaInteresadaSol personaInteresada = null;
		try {
			
			
			String strQuery = "select p from DitPersonaInteresadaSol p " +
							"where p.ditSolicitud.cveIdSolicitud=:idSolicitud and p.ditPersona.cveIdPersona=:idPersona";
			Query query = this.getSession().createQuery(strQuery);
			query.setParameter("idPersona", idPersona);
			query.setParameter("idSolicitud", idSolicitud);
			
			personaInteresada =(DitPersonaInteresadaSol)query.uniqueResult();
		} catch(NoResultException e){
			personaInteresada = null;
		}catch (Exception e) {
			logger.error("Error - getPersonaInteresada", e);
			throw e;
		}
		
		PersonaInteresadaSolicitud salida = personaInteresadaSolParser.persisToModel(personaInteresada);
		
		return salida;
	}

	@Override
	public Long getIdPersonaInteresadaSol(Long idSolicitud) throws Exception {
		DitPersonaInteresadaSol personaInteresada = null;
		List<DitPersonaInteresadaSol> ditListaPerIntSol = null;
		try {
			
			
			String strQuery = "select p from DitPersonaInteresadaSol p " +
								"where p.ditSolicitud.cveIdSolicitud=:idSolicitud";				
			Query query = this.getSession().createQuery(strQuery);
			query.setParameter("idSolicitud", idSolicitud);
			
			ditListaPerIntSol = query.list();
			if(ditListaPerIntSol != null && !ditListaPerIntSol.isEmpty()){
				personaInteresada = ditListaPerIntSol.get(0);
			}
		} catch(NoResultException e){
			return null;
		} catch (Exception e) {
			logger.error("Error - getIdPersonaInteresadaSol", e);
			throw e;
		}
				
		return personaInteresada.getDitPersona().getCveIdPersona();
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PersonaInteresadaSolicitud> getListPerssonaInteresadaSolicitudbyPersona(long idPersona) throws DerechohabientesBusinessException,Exception {
		List<DitPersonaInteresadaSol> ditListaPerIntSol = null;
		List<PersonaInteresadaSolicitud> listaPerIntSol = null;		
		try {
			
			String	strQuery = "select p from DitPersonaInteresadaSol p where p.ditPersona.cveIdPersona=:idPersona";
			Query query = this.getSession().createQuery(strQuery);
			query.setParameter("idPersona", idPersona);
			ditListaPerIntSol = query.list();
			listaPerIntSol = personaInteresadaSolParser.persisToModelList(ditListaPerIntSol);
						
		} catch (Exception e) {
			log.error("Error - tramitesRechazados", e);
			throw e;
		}
		
		return listaPerIntSol;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PersonaInteresadaSolicitud> getListSolicitudbyPersonaInteresadayEstado(long idPersona, long  idEstadoSolicitud) throws Exception{
		List<DitPersonaInteresadaSol> ditListaPerIntSol = null;
		List<PersonaInteresadaSolicitud> listaPerIntSol = null;		
		try {
			
			StringBuffer strQuery = new StringBuffer();   
			strQuery.append("select p from DitPersonaInteresadaSol p join p.ditSolicitud as solicitud ");
			strQuery.append("where p.ditPersona.cveIdPersona=:idPersona ");
			strQuery.append(" and solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud = :idEstadoSolicitud");
			Query query = this.getSession().createQuery(strQuery.toString());
			query.setParameter("idPersona", idPersona);
			query.setParameter("idEstadoSolicitud", idEstadoSolicitud);
			ditListaPerIntSol = query.list();
			listaPerIntSol = personaInteresadaSolParser.persisToModelList(ditListaPerIntSol);
						
		} catch (Exception e) {
			log.error("Error - al realizar la busqueda por estado y persona interesada sol", e);
			
		}
		
		return listaPerIntSol;
	}
	
	
	
	
	private List<DitSolicitud> getLstSolicitudEntitybyPersonaInteresadayEstado(long idPersona, long  idEstadoSolicitud) throws Exception{
		List<DitSolicitud> ditListaPerIntSol = null;
		
		try {
			log.debug("El id de la persona interesada es: " + idPersona);
			log.debug("El estado de la solicitud que se esta buscando es: " + idEstadoSolicitud);
			StringBuffer strQuery = new StringBuffer();   
			strQuery.append("select solicitud from DitPersonaInteresadaSol p join p.ditSolicitud as solicitud ");
			strQuery.append("where p.ditPersona.cveIdPersona=:idPersona ");
			strQuery.append(" and solicitud.dicEstadoSolicitud.cveIdEstadoSolicitud = :idEstadoSolicitud");
			Query query = this.getSession().createQuery(strQuery.toString());
			query.setParameter("idPersona", idPersona);
			query.setParameter("idEstadoSolicitud", idEstadoSolicitud);
			ditListaPerIntSol = query.list();
			
						
		} catch (Exception e) {
			log.error("Error - al realizar la busqueda por estado y persona interesada sol", e);
			
		}
		
		return ditListaPerIntSol;
	}
	
	
	
	
	
	
	@Override
	public RequisitosDTO validaTramiteRegistroConcubanaPadresPendiente(RequisitosDTO requisitos, Long idParentesco, Long sexoIntegrante, AsignacionNSS asignacionNss) throws DerechohabientesBusinessException, Exception{
		if(requisitos.getAprobado() == Constants.APROBADO){
			if(idParentesco == ParentescoEnum.PADRES.getId() || idParentesco == ParentescoEnum.CONCUBINARIO.getId()){				
				List<DitSolicitud> ditSolicitudes = this.getLstSolicitudEntitybyPersonaInteresadayEstado(asignacionNss.getIdPersona(), 
						EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo());
				if(ditSolicitudes.size() > 0){
					for(DitSolicitud ditSol : ditSolicitudes){	
						if(ditSol.getFecRegistroBaja() == null){
							if(ditSol.getDitTramites().size() > 0){
								for(DitTramite ditTramite : ditSol.getDitTramites()){
									if(ditTramite.getDicTipoTramite().getCveIdTipoTramite().longValue() == TipoTramiteEnum.REGISTRO_CONCUBINA_RIO.getCodigo().longValue()
											|| ditTramite.getDicTipoTramite().getCveIdTipoTramite().longValue() == TipoTramiteEnum.REGISTRO_PADRES.getCodigo().longValue()){
										if(idParentesco == ParentescoEnum.PADRES.getId()){
											
											TramiteRegistroDerechohabiente tramiteRegistro = registroDerechohabientesDaoLocal.getRegistroDerechohabiente(ditTramite.getCveIdTramite());
											
											
											if(tramiteRegistro.getParentesco().getIdParentesco().equals(ParentescoEnum.PADRES.getId())){
												RegistroDto registroTemp = new RegistroDto();
												registroTemp.setTramiteRegistro(new TramiteRegistroDerechohabiente());
												registroTemp.getTramiteRegistro().setTramiteId(ditTramite.getCveIdTramite());
												//registroTemp = recuperaTramite(registroTemp);												
												if(registroTemp.getTramiteRegistro().getFisica().getSexo().getIdSexo().longValue() == sexoIntegrante.longValue()){
													requisitos.setAprobado(Constants.NO_APROBADO);
													requisitos.setMotivo(Constants.SOL_PEND_APR);
												}
												
											}
										}else{
											requisitos.setAprobado(Constants.NO_APROBADO);
											requisitos.setMotivo(Constants.SOL_PEND_APR);
										}
									}
								}
							}
						}
					}
				}
			}	
		}
		return requisitos;		
	}
	
	
	
	
	
}
