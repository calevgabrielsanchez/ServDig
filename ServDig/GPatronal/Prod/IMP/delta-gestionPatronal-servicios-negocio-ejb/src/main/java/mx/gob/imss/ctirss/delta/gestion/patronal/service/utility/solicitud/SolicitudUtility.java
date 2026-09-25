/**
*
*
**/
package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.solicitud;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSociedad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TramiteSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.RazonCancelacion;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicOrigenSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DicRazonCancelacion;
import mx.gob.imss.ctirss.delta.persistence.DicRazonResultado;
import mx.gob.imss.ctirss.delta.persistence.DicTipoSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DicTipoTramite;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitudFirmaDigital;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaMoral;
import mx.gob.imss.ctirss.delta.persistence.DitUsuario;
import mx.gob.imss.ctirss.delta.tramite.service.utility.TramiteServiceUtilityLocal;

/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Hugo Armando Martínez Chamónica
 *  @Proyecto: delta
 *  @Archivo: SolicitudUtility.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.solicitud
 *  @Fecha: 11:12:43
 */
@Stateless
public class SolicitudUtility extends AbstractServiceUtility  implements SolicitudUtilityLocal {
	
	@EJB
	private TramiteServiceUtilityLocal tramiteServiceUtilityLocal;
	
	@EJB
	SujetoObligadoUtilityLocal sujetoServiceUtilityLocal;
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.solicitud.SolicitudUtilityLocal#convertEntityToModelSolicitud(mx.gob.imss.ctirss.delta.persistence.DitSolicitud)
	 */
	@Override
	public Solicitud convertEntityToModelSolicitud(DitSolicitud entity) {
		Solicitud solicitud  = new Solicitud();		
		solicitud.setSolicitudId(entity.getCveIdSolicitud());
		solicitud.setEstadoSolicitud(convertEntityToModelEstadoSolicitud(entity.getDicEstadoSolicitud()));
		solicitud.setFechaSolicitud(entity.getFecSolicitud());
		solicitud.setFechaPresentacion(entity.getFecPresentacion());
		solicitud.setFechaConclusion(entity.getFecConclusion());
		solicitud.setFechaActualizacion(entity.getFecRegistroActualizado());
		solicitud.setNoFolioSolicitud(entity.getRefFolio());
		solicitud.setObservacion(entity.getRefObservacion());
		solicitud.setSubdelegacion(sujetoServiceUtilityLocal.convertirEntityToModelSubdelegacion(
				entity.getDicSubdelegacion()));
		if (entity.getDicOrigenSolicitud() != null
				&& entity.getDicOrigenSolicitud()
						.getCveIdOrigenSolicitud() > 0) {
        	solicitud.setOrigenSolicitud(convertirOrigenSolicitud(entity.getDicOrigenSolicitud()));
        }
		solicitud.setRazonCancelacion(convertirEntityToModelRazonCancelacion(
				entity.getDicRazonCancelacion()));
		solicitud.setTipoSolicitud(convertirEntityToModelTipoSolicitud(entity.getDicTipoSolicitud()) );
		byte[] documentoAcuse = entity.getDitSolicitudDocumento()!=null ? entity.getDitSolicitudDocumento().getRefAcuseRecibo() : null;
		byte[] documentoComprobante = entity.getDitSolicitudDocumento()!=null ? entity.getDitSolicitudDocumento().getRefAcuseRecibo() : null;
		solicitud.setDocumentoAcuse(documentoAcuse);
		solicitud.setDocumentoComprobante(documentoComprobante);
		System.err.println("Se agregan tramites a la solicitud");
		solicitud.setTramites(convertirEntitiesToModelTramite(entity.getDitTramites()));
		solicitud = agregarSujetoObligadoDeTramiteASolicitud(solicitud);
		System.err.println("Estado solicitud en convertEntityToModelSolicitud "+solicitud.getEstadoSolicitud().getDescripcion());
		
		if(entity.getDitSolicitudFirmaDigitals() != null && !entity.getDitSolicitudFirmaDigitals().isEmpty()) {
			DitSolicitudFirmaDigital firma = entity.getDitSolicitudFirmaDigitals().get(0);
			
			solicitud.setCadenaOriginal(firma.getNumCadenaOriginal());
			solicitud.setFirmadaDigitalmente(true);
			solicitud.setSecuenciaDeNotaria(firma.getNumSecNotaria());
			solicitud.setSelloDigital(firma.getNumSelloDigital());
			solicitud.setUrlAcuseFirma(firma.getRefUrlAcuseFirma());
			
			if(entity.getDitSolicitudFirmaDigitals().size() > 1) {
				firma = entity.getDitSolicitudFirmaDigitals().get(1);
				solicitud.setCadenaOriginalRepresentado(firma.getNumCadenaOriginal());
				solicitud.setSecuenciaDeNotariaRepresentado(firma.getNumSecNotaria());
				solicitud.setSelloDigitalRepresentado(firma.getNumSelloDigital());
				solicitud.setUrlAcuseFirmaRepresentado(firma.getRefUrlAcuseFirma());
			}
		}
		
		if (StringUtils.isNotBlank(entity.getCveIdUsuario())) {
			Usuario usuario = new Usuario();
			usuario.setUsuario(entity.getCveIdUsuario());
			solicitud.setSolicitante(usuario);
		}
		
		return solicitud;
	}
	
	private OrigenSolicitud convertirOrigenSolicitud(
			DicOrigenSolicitud dicOrigenSolicitud) {

		OrigenSolicitud origenSolicitud = new OrigenSolicitud();
		origenSolicitud.setIdTipoSolicitud(dicOrigenSolicitud.getCveIdOrigenSolicitud());
		
		if (StringUtils.isBlank(dicOrigenSolicitud.getDesOrigenSolicitud())) {
			
			OrigenSolicitudEnum origen = OrigenSolicitudEnum.getById(dicOrigenSolicitud.getCveIdOrigenSolicitud());
			
			if (origen != null) {
				origenSolicitud.setDescripcion(origen.getDesc());
			}
		} else {
			origenSolicitud.setDescripcion(dicOrigenSolicitud.getDesOrigenSolicitud());
		}
		
		return origenSolicitud;
		
	}

	private Solicitud agregarSujetoObligadoDeTramiteASolicitud(Solicitud solicitud){
		if(solicitud.getTramites()!=null && solicitud.getTramites().size()>0)
			solicitud.setSujetoObligado(((TramiteSolicitud)solicitud.getTramites().get(0)).getSujetoObligado());
		
		return solicitud;
	}	
	
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.solicitud.SolicitudUtilityLocal#convertEntityToModelSolicitud(mx.gob.imss.ctirss.delta.persistence.DicEstadoSolicitud)
	 */
	@Override
	public EstadoSolicitud convertEntityToModelEstadoSolicitud(
			DicEstadoSolicitud entity) {
		
		EstadoSolicitud model = new EstadoSolicitud();
		model.setIdEstadoSolicitud(entity.getCveIdEstadoSolicitud().intValue());
		String desc = entity.getDesEstadoSolicitud().toString();
		model.setDescripcion(desc);
		System.err.println("Agregando estado de solicitud zero: "+model.getIdEstadoSolicitud()+ " " +entity.getDesEstadoSolicitud());
		System.err.println("Agregando estado de solicitud one: "+model.getIdEstadoSolicitud()+ " " +desc);
		System.err.println("Agregando estado de solicitud: "+model.getIdEstadoSolicitud()+ " " +model.getDescripcion());
		return model;
		
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.solicitud.SolicitudUtilityLocal#convertirEntityToModelRazonCancelacion(mx.gob.imss.ctirss.delta.persistence.DicRazonCancelacion)
	 */
	@Override
	public RazonCancelacion convertirEntityToModelRazonCancelacion(
			DicRazonCancelacion entity) {
		RazonCancelacion model = new RazonCancelacion();
		if(entity== null)
			return null;
		model.setIdRazonCancelacion(entity.getCveIdRazonCancelacion());
		model.setDescripcion(entity.getDesRazonCancelacion());
		return model;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.solicitud.SolicitudUtilityLocal#convertirEntityToModelTipoSolicitud(mx.gob.imss.ctirss.delta.persistence.DicTipoSolicitud)
	 */
	@Override
	public TipoSolicitud convertirEntityToModelTipoSolicitud(
			DicTipoSolicitud tipoSolicitud) {
		if(tipoSolicitud==null)
			return null;
		TipoSolicitud model = new TipoSolicitud();
		model.setIdTipoSolicitud(tipoSolicitud.getCveIdTipoSolicitud());
		model.setDescripcion(tipoSolicitud.getDesTipoSolicitud());
		
		return model;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.solicitud.SolicitudUtilityLocal#convertirEntityToModelTramite(mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaFisica)
	 */
	@Override
	public TramiteSolicitud convertirEntityToModelTramite(DitTramitePersonaFisica entity) {
		TramiteSolicitud model = new TramiteSolicitud();
//		model.setTipoTramite(convertirEntityToModelTipoTramite(entity.getDicTipoTramite()));
//		model.setEstadoTramite(convertirEntityToModelEstadoTramite(entity.getDicEstadoTramite()));
//		model.setFechaTramite(entity.getFecRegistroAlta());
//		model.setObservacion(entity.getRefObservacion());
//		model.setPersonaTramite(convertirEntityToModelPersonaTramite(entity.getDitPersona()));
//		model.setRazonResultado(convertirEntityToModelRazonResultado(entity.getDicRazonResultado()));
//		model.setResultado(entity.getIndResultado().doubleValue()==0d ? false : true);
		
		return model;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.solicitud.SolicitudUtilityLocal#convertirEntityToModelTramite(mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaMoral)
	 */
	@Override
	public TramiteSolicitud convertirEntityToModelTramite(DitTramitePersonaMoral entity) {
		TramiteSolicitud model = new TramiteSolicitud();
//		model.setTipoTramite(convertirEntityToModelTipoTramite(entity.getDicTipoTramite()));
//		model.setEstadoTramite(convertirEntityToModelEstadoTramite(entity.getDicEstadoTramite()));
//		model.setFechaTramite(entity.getFecRegistroAlta());
//		model.setObservacion(entity.getRefObservacion());
//		Persona persona = new Persona();
//		persona.setIdPersona(entity.getId().getCveIdPersonaMoral());
//		model.setPersonaTramite(persona);
//		model.setRazonResultado(convertirEntityToModelRazonResultado(entity.getDicRazonResultado()));
//		model.setResultado(entity.getIndResultado().doubleValue()==0d ? false : true);
//		
		return model;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.solicitud.SolicitudUtilityLocal#convertirEntityToModelTipoTramite(mx.gob.imss.ctirss.delta.persistence.DicTipoTramite)
	 */
	@Override
	public TipoTramite convertirEntityToModelTipoTramite(DicTipoTramite entity) {
		TipoTramite model = new TipoTramite();
		model.setIdTipoTramite(entity.getCveIdTipoTramite().intValue());
		model.setDescripcion(entity.getDesTipoTramite());
		model.setGuiaDetallada(entity.getRefGuiaDetallada());
		model.setGuiaRapida(entity.getRefGuiaRapida());
		return model;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.solicitud.SolicitudUtilityLocal#convertirEntityToModelEstadoTramite(mx.gob.imss.ctirss.delta.persistence.DicEstadoTramite)
	 */
	@Override
	public EstadoTramite convertirEntityToModelEstadoTramite(
			DicEstadoTramite estadoTramite) {
		EstadoTramite model = new EstadoTramite();
		model.setIdEstadoTramitePersona(estadoTramite.getCveIdEstadoTramite().intValue());
		model.setDescripcion(estadoTramite.getDesEstadoTramite());
		return model;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.solicitud.SolicitudUtilityLocal#convertirEntityToModelPersonaTramite(mx.gob.imss.ctirss.delta.persistence.DitPersona)
	 */
	@Override
	public Persona convertirEntityToModelPersonaTramite(DitPersona entity) {
		Persona model = new Persona();
		model.setIdPersona(entity.getCveIdPersona());
		return model;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.solicitud.SolicitudUtilityLocal#convertirEntityToModelRazonResultado(mx.gob.imss.ctirss.delta.persistence.DicRazonResultado)
	 */
	@Override
	public RazonResultado convertirEntityToModelRazonResultado(
			DicRazonResultado entity) {
		if(entity==null)
			return null;
		RazonResultado model = new RazonResultado();
		model.setIdRazonResultado(entity.getCveIdRazonResultado());
		model.setDescripcion(entity.getDesRazonResultado());
		return model;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.solicitud.SolicitudUtilityLocal#convertirModelToEntitySolicitud(mx.gob.imss.ctirss.delta.model.gestion.patronal.Solicitud)
	 */
	@Override
	public DitSolicitud convertirModelToEntitySolicitud(Solicitud model) {
		log.debug("Conviertiendo model to entity solicitud");
		log.debug(model.toString());
		DitSolicitud ditSolicitud = new DitSolicitud();
		ditSolicitud.setDicEstadoSolicitud(convertirModelToEntityEstadoSolicitud(model.getEstadoSolicitud()));
		ditSolicitud.setDicTipoSolicitud(convertirModelToEntityTipoSolicitud(model.getTipoSolicitud() ));
		ditSolicitud.setDicRazonCancelacion(convertirModelToEntityRazonCancelacion(model.getRazonCancelacion()));
		ditSolicitud.setFecRegistroAlta(model.getFechaSolicitud());
		ditSolicitud.setFecRegistroActualizado(new Date());
		ditSolicitud.setFecSolicitud(new Date());
		ditSolicitud.setRefFolio(model.getNoFolioSolicitud());
		//TODO
		//ditSolicitud.setDitSolicitudDetalle(ditSolicitudDetalle);
		//ditSolicitud.setDitSolicitudSeguimientos(ditSolicitudSeguimientos);
		//ditSolicitud.setDitTramitePersonaFisicas(ditTramitePersonaFisicas);
		//ditSolicitud.setDitUmfTurno(ditUmfTurno);
		//ditSolicitud.setDocument(document);
		//ditSolicitud.setName(name);
		//ditSolicitud.setParent(parent);
		
		
		//se cambio el seteo de usuario ya que no viene del una tabla
		//ditSolicitud.setDitUsuario(convertirModelToEntityUsuario(model.getSolicitante()));
		if(model.getSolicitante().getUsuario() != null){
			ditSolicitud.setCveIdUsuario(model.getSolicitante().getUsuario().trim());
		}
		
		ditSolicitud.setRefFolio(model.getNoFolioSolicitud());
		ditSolicitud.setRefObservacion(model.getObservacion());
		
		return ditSolicitud;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.solicitud.SolicitudUtilityLocal#convertirModelToEntityEstadoSolicitud(mx.gob.imss.ctirss.delta.persistence.DicEstadoSolicitud)
	 */
	@Override
	public DicEstadoSolicitud convertirModelToEntityEstadoSolicitud(
			EstadoSolicitud model) {
		DicEstadoSolicitud entity = new DicEstadoSolicitud();
		entity.setCveIdEstadoSolicitud(model.getIdEstadoSolicitud().longValue());
		entity.setDesEstadoSolicitud(model.getDescripcion());
		return entity;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.solicitud.SolicitudUtilityLocal#convertirModelToEntityTipoSolicitud(mx.gob.imss.ctirss.delta.persistence.DicTipoSolicitud)
	 */
	@Override
	public DicTipoSolicitud convertirModelToEntityTipoSolicitud(
			TipoSolicitud model) {
		DicTipoSolicitud entity = new DicTipoSolicitud();
		entity.setCveIdTipoSolicitud(model.getIdTipoSolicitud());
		entity.setDesTipoSolicitud(entity.getDesTipoSolicitud());
		return entity;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.solicitud.SolicitudUtilityLocal#convertirModelToEntityRazonCancelacion(mx.gob.imss.ctirss.delta.model.gestion.patronal.RazonCancelacion)
	 */
	@Override
	public DicRazonCancelacion convertirModelToEntityRazonCancelacion(
			RazonCancelacion model) {
		if(model==null)
			return null;
		DicRazonCancelacion entity = new DicRazonCancelacion();
		entity.setCveIdRazonCancelacion(model.getIdRazonCancelacion());
		entity.setDesRazonCancelacion(model.getDescripcion());
		return entity;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.solicitud.SolicitudUtilityLocal#convertirModelToEntityUsuario(mx.gob.imss.ctirss.delta.model.Usuario)
	 */
	@Override
	public DitUsuario convertirModelToEntityUsuario(Usuario model) {
		if(model==null)
			return null;
		DitUsuario usuario = new DitUsuario();
		usuario.setCveIdUsuario(new Long(model.getCveIdUsuario()).longValue());
		return usuario;
	}	
	
	public List<Tramite> convertirEntitiesToModelTramite(List<DitTramite> entities){
		List<Tramite> models = new ArrayList<Tramite>();
		if(entities != null){
			for(DitTramite entity : entities){
				models.add(tramiteServiceUtilityLocal.convertirEntityToModel(entity));
			}
		}
				
		return models;
	}

	@Override
	public SujetoObligado convertirEntityToModelSujetoObligadoSolicitud(
			DitSolicitud entity) {
		
		if( entity.getDicTipoSolicitud() != null && (entity.getDicTipoSolicitud().getCveIdTipoSolicitud() == TipoSolicitudEnum.ACTUALIZACION_DE_CLASIFICACION.getValor().longValue()
				|| entity.getDicTipoSolicitud().getCveIdTipoSolicitud() == TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO.getValor().longValue() )
				){
			
			DitPatronSujetoObligado ditPatron = entity.getDitTramites().get(0).getDitTramitePatSujObligados().get(0).getDitPatronSujetoObligado();
			
			return convertirEntityToModelPatronBasico(ditPatron);
			
			
		}else if(entity.getDicTipoSolicitud() != null && entity.getDicTipoSolicitud().getCveIdTipoSolicitud() == TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES.getValor().longValue()){
			return convertirEntityToModelPatronPersonaBasico(entity);
		}else if(entity.getDitTramites()!= null && entity.getDitTramites().size()>0 ){
			if(entity.getDitTramites().size()==1 && entity.getDitTramites().get(0).getDitTramitePatSujObligados()!= null
					&& entity.getDitTramites().get(0).getDitTramitePatSujObligados().size()>0){
				DitPatronSujetoObligado ditPatron = entity.getDitTramites().get(0).getDitTramitePatSujObligados().get(0).getDitPatronSujetoObligado();
				return convertirEntityToModelPatronBasico(ditPatron);
			}else if(entity.getDitTramites().size()>1 ){
				
				for(DitTramite tramite:entity.getDitTramites())
					if(tramite.getDitTramitePatSujObligados()!=null && tramite.getDitTramitePatSujObligados().size()>0){
						DitPatronSujetoObligado ditPatron = tramite.getDitTramitePatSujObligados().get(0).getDitPatronSujetoObligado();
						return convertirEntityToModelPatronBasico(ditPatron);
					}
			}
		}
			
		return convertirEntityToModelPatronPersonaBasico(entity);
	}
	
	private SujetoObligado convertirEntityToModelPatronBasico(DitPatronSujetoObligado entity){
		SujetoObligado so = new SujetoObligado();
		so.setNumeroRegistroPatronal(entity.getDitPatronGenerals().get(0).getRegPatron());
		so.setModalidad(sujetoServiceUtilityLocal.convertirEntityToModelModalidad(entity.getDicModalidad()));
		so.setDigVerificador(entity.getDitPatronGenerals().get(0).getDigVer());
		so.setCveIdSujetoObligado(entity.getCveIdPatronSujetoObligado());
		if(entity.getDitPersonaFisica()!=null){ 
			so.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
			so.setFisica(convertirPersonaFisicaBasica(entity.getDitPersonaFisica().getDitPersona()));
		}else if(entity.getDitPersonaMoral()!= null){
			so.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
			so.setMoral(convertirPersonaMoralBasica(entity.getDitPersonaMoral()));
		}
		if(entity.getDitSubdelPatSujOblig()!= null)
			so.setSubdelegacion(
					sujetoServiceUtilityLocal.convertirEntityToModelSubdelegacion(
							entity.getDitSubdelPatSujOblig().getDicSubdelegacion()));
		
		return so;
	}
	
	private SujetoObligado convertirEntityToModelPatronPersonaBasico(DitSolicitud entity){
		SujetoObligado so = new SujetoObligado();
		
		if(entity.getDitTramites() != null && entity.getDitTramites().size() > 0){
			// TODO: LUDS Se modifico esta parte por un cambio en dittramite, en
			// donde se requiere obtener la lista de personas afectadas por un
			// tramite, por lo tanto es una lista
			DitTramitePersonaFisica ditTramiteFisica = null;
			List<DitTramitePersonaFisica> ditTramiteFisicaList = entity.getDitTramites().get(0).getDitTramitePersonaFisica();
			if(ditTramiteFisicaList != null && !ditTramiteFisicaList.isEmpty()){
				 ditTramiteFisica = ditTramiteFisicaList.get(0);	
			}
			
			
			
			
			
			DitTramitePersonaMoral ditTramiteMoral = entity.getDitTramites().get(0).getDitTramitePersonaMoral();
			if(ditTramiteFisica!=null){
				so.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
				so.setFisica(convertirPersonaFisicaBasica(ditTramiteFisica.getDitPersona()));
			}else if(ditTramiteMoral!=null){
				so.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
				so.setMoral(convertirPersonaMoralBasica(ditTramiteMoral.getDitPersonaMoral()));
			}
		}
		return so;
	}
	
	private Fisica convertirPersonaFisicaBasica(DitPersona ditPersona){
		Fisica fisica = new Fisica();
		fisica.setRfc(ditPersona.getRfc());
		fisica.setNombre(ditPersona.getNomNombre());
		fisica.setPrimerApellido(ditPersona.getNomPrimerApellido());
		fisica.setSegundoApellido(ditPersona.getNomSegundoApellido());
		fisica.setIdPersona(ditPersona.getCveIdPersona());
		return fisica;
	}
	
	private Moral convertirPersonaMoralBasica(DitPersonaMoral ditMoral){
		Moral moral = new Moral();
		moral.setCveMoral(ditMoral.getCveIdPersonaMoral());
		moral.setIdPersona(ditMoral.getCveIdPersonaMoral());
		moral.setRfc(ditMoral.getRfc());
		moral.setRazonSocial(ditMoral.getDenominacionRazonSocial());
		moral.setTipoSociedad(new TipoSociedad());
		if(ditMoral.getDicTipoSociedad()!=null){
			moral.getTipoSociedad().setDescripcion(ditMoral.getDicTipoSociedad().getDesTipoSociedad());
			moral.getTipoSociedad().setDescripcionAbreviada(ditMoral.getDicTipoSociedad().getDesTipoSociedadAbrev());
			moral.getTipoSociedad().setIdTipoSociedad(ditMoral.getDicTipoSociedad().getCveIdTipoSociedad().longValue());
		}
		return moral;
	}

}
