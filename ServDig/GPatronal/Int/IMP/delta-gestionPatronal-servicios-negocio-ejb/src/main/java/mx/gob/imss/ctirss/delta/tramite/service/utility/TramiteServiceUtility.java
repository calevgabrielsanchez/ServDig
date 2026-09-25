/**
*
*
**/
package mx.gob.imss.ctirss.delta.tramite.service.utility;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.ServiceUtility;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Documento;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TramiteSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicDocumento;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicRazonResultado;
import mx.gob.imss.ctirss.delta.persistence.DicTipoSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DicTipoTramite;
import mx.gob.imss.ctirss.delta.persistence.DitDoctoReqTramite;
import mx.gob.imss.ctirss.delta.persistence.DitPatronGeneral;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaMoral;

/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Hugo Armando Martínez Chamónica
 *  @Proyecto: delta
 *  @Archivo: TramiteServiceUtility.java
 *  @Paquete: mx.gob.imss.ctirss.delta.tramite.service.utility
 *  @Fecha: 09:42:23
 */
@Stateless
public class TramiteServiceUtility extends ServiceUtility implements TramiteServiceUtilityLocal {
		
	/**
	 * Default Constructor 
	 */
	public TramiteServiceUtility() {
		super();
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.tramite.service.utility.TramiteServiceUtilityLocal#convertirEntityToModel()
	 */
	@Override
	public TramiteSolicitud convertirEntityToModel(DitTramite tramite) {
		TramiteSolicitud model = new TramiteSolicitud();
		System.err.println("convirtiendo tramites");
		model.setTramiteId(tramite.getCveIdTramite());
		model.setEstadoTramite(convertEntityToModelEstadoTramite(tramite.getDicEstadoTramite()));
		model.setFechaTramite(tramite.getFecTramite());
		model.setFechaConclusion(tramite.getFecConclusion());
		model.setFechaPresentacion(tramite.getFecPresentacion());
		model.setFechaEfecto(tramite.getFecEfecto());
		model.setObservacion(tramite.getRefObservacion());
		model.setRazonResultado(convertirEntityToModelRazonResultado(tramite.getDicRazonResultado()));
		model.setIndRatificado(tramite.getIndRatificado());
		//TODO ver como equivalen los tipos de datos a true o false
//		model.setResultado(tramite.getIndResultado());
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(tramite.getDitSolicitud().getCveIdSolicitud());
		solicitud.setNoFolioSolicitud(tramite.getDitSolicitud().getRefFolio());
		solicitud.setFechaSolicitud(tramite.getDitSolicitud().getFecSolicitud());
		EstadoSolicitud estadoSol = new EstadoSolicitud();
		estadoSol.setIdEstadoSolicitud(tramite.getDitSolicitud().getDicEstadoSolicitud().getCveIdEstadoSolicitud().intValue());
		estadoSol.setDescripcion(tramite.getDitSolicitud().getDicEstadoSolicitud().getDesEstadoSolicitud());
		solicitud.setEstadoSolicitud(estadoSol);
		solicitud.setTipoSolicitud(convertirEntityToModelTipoSolicitud(tramite.getDitSolicitud().getDicTipoSolicitud()));
		model.setSolicitud(solicitud);
		System.err.println("Se agregara el tipo de tramite");
		model.setTipoTramite(convertirEntityToModelTipoTramite(tramite.getDicTipoTramite()));
		SujetoObligado sujetoObligado = new SujetoObligado();
		// TODO: LUDS Se modifico esta parte por un cambio en dittramite, en
		// donde se requiere obtener la lista de personas afectadas por un
		// tramite, por lo tanto es una lista
		List<DitTramitePersonaFisica> tramitePersonaFisicaList = tramite.getDitTramitePersonaFisica();
		DitTramitePersonaFisica tpf = null;
		if(tramitePersonaFisicaList != null && !tramitePersonaFisicaList.isEmpty()){
			 tpf = tramite.getDitTramitePersonaFisica().get(0);	
		}
		DitTramitePersonaMoral tpm = tramite.getDitTramitePersonaMoral();
		if(tpf != null){
			Fisica fisica = new Fisica();
			fisica.setIdPersona(tpf.getDitPersona().getCveIdPersona());
			fisica.setRfc(tpf.getDitPersona().getRfc());
			sujetoObligado.setFisica(fisica);
			sujetoObligado.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
			solicitud.setSujetoObligado(sujetoObligado);
		}
		if(tpm != null){
			Moral moral = new Moral();
			moral.setIdPersona(tpm.getDitPersonaMoral().getCveIdPersonaMoral());
			moral.setRfc(tpm.getDitPersonaMoral().getRfc());
			sujetoObligado.setMoral(moral);
			sujetoObligado.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
			solicitud.setSujetoObligado(sujetoObligado);
		}
		if(tramite.getDitTramitePatSujObligados()!=null && tramite.getDitTramitePatSujObligados().size() > 0){
			DitPatronGeneral pg = tramite.getDitTramitePatSujObligados().get(0).getDitPatronSujetoObligado().getDitPatronGenerals().get(0);
			DitPatronSujetoObligado so = tramite.getDitTramitePatSujObligados().get(0).getDitPatronSujetoObligado();
			sujetoObligado.setNumeroRegistroPatronal(pg.getRegPatron());
			sujetoObligado.setModalidad(new Modalidad());
			sujetoObligado.getModalidad().setIdModalidad(so.getDicModalidad().getCveIdModalidad());
			sujetoObligado.getModalidad().setDescripcion(so.getDicModalidad().getDesModalidad());
			sujetoObligado.setDigVerificador(pg.getDigVer());
		}
		model.setSujetoObligado(sujetoObligado);
		
		return model;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.tramite.service.utility.TramiteServiceUtilityLocal#convertirModelToEntityTramitePersonaFisica(mx.gob.imss.ctirss.delta.model.gestion.patronal.Tramite)
	 */
	@Override
	public DitTramitePersonaFisica convertirModelToEntityTramitePersonaFisica(
			TramiteSolicitud model) {
		DitTramitePersonaFisica tramitePF = new DitTramitePersonaFisica();
//		tramitePF.setDicEstadoTramite(convertirModelToEntityEstadoTramite(model.getEstadoTramite()));
//		if(model.getRazonResultado()!=null)
//			tramitePF.setDicRazonResultado(convertirModelToEntityRazonResultado(model.getRazonResultado()));
//		tramitePF.setDicTipoTramite(convertirModelToEntityTipoTramite(model.getTipoTramite()));
//		tramitePF.setDitPersona(convertirEntityToModelPersona(model.getPersonaTramite()));
//		tramitePF.setFecTramite(model.getFechaTramite());
//		
//		DitTramitePersonaFisicaPK pk = new DitTramitePersonaFisicaPK();
//		pk.setCveIdPersona(model.getPersonaTramite().getIdPersona());
//		pk.setCveIdSolicitud(model.getSolicitud().getId());
//		tramitePF.setId(pk);
		
//		tramitePF.setDitCorreccionDatoDerechohabs(ditCorreccionDatoDerechohabs)
//		tramitePF.setDitDocumentoProbatorios(ditDocumentoProbatorios)
//		tramitePF.setDitProrrogas(ditProrrogas)
//		tramitePF.setDitRegistroDerechohabientes(ditRegistroDerechohabientes)
		
		return tramitePF;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.tramite.service.utility.TramiteServiceUtilityLocal#convertirModelToEntityTramitePersonaMoral(mx.gob.imss.ctirss.delta.model.gestion.patronal.Tramite)
	 */
	@Override
	public DitTramitePersonaMoral convertirModelToEntityTramitePersonaMoral(
			TramiteSolicitud model) {
		DitTramitePersonaMoral tramitePM = new DitTramitePersonaMoral();
		
//		tramitePM.setDicEstadoTramite(convertirModelToEntityEstadoTramite(model.getEstadoTramite()));
//		if(model.getRazonResultado()!=null)
//			tramitePM.setDicRazonResultado(convertirModelToEntityRazonResultado(model.getRazonResultado()));
//		tramitePM.setDicTipoTramite(convertirModelToEntityTipoTramite(model.getTipoTramite()));
//		tramitePM.setFecTramite(model.getFechaTramite());
//		
//		DitTramitePersonaMoralPK pk = new DitTramitePersonaMoralPK();
//		pk.setCveIdPersonaMoral(model.getPersonaTramite().getIdPersona());
//		pk.setCveIdSolicitud(model.getSolicitud().getId());
//		tramitePM.setId(pk);
		return tramitePM;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.tramite.service.utility.TramiteServiceUtilityLocal#convertirModelToEntityEstadoTramite(mx.gob.imss.ctirss.delta.model.gestion.patronal.EstadoTramite)
	 */
	@Override
	public DicEstadoTramite convertirModelToEntityEstadoTramite(
			EstadoTramite model) {
		DicEstadoTramite entity = new DicEstadoTramite();
		entity.setCveIdEstadoTramite(model.getIdEstadoTramitePersona().longValue());
		return entity;
	}

	

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.tramite.service.utility.TramiteServiceUtilityLocal#convertirModelToEntityTipoTramite(mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramite)
	 */
	@Override
	public DicTipoTramite convertirModelToEntityTipoTramite(TipoTramite model) {
		DicTipoTramite entity = new DicTipoTramite();
		entity.setCveIdTipoTramite(model.getIdTipoTramite().longValue());
		
		return entity;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.tramite.service.utility.TramiteServiceUtilityLocal#convertirEntityToModelPersona(mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona)
	 */
	@Override
	public DitPersona convertirEntityToModelPersona(Persona model) {
		DitPersona entity = new DitPersona();
		entity.setCveIdPersona(model.getIdPersona());
		return entity;
	}

	@Override
	public EstadoTramite convertEntityToModelEstadoTramite(DicEstadoTramite entity) {
		EstadoTramite model = new EstadoTramite();
		model.setIdEstadoTramitePersona(entity.getCveIdEstadoTramite().intValue());
		model.setDescripcion(entity.getDesEstadoTramite());
		return model;
	}

	@Override
	public DicRazonResultado convertirModelToEntityRazonResultado(
			RazonResultado model) {
		
		return null;
	}

	@Override
	public RazonResultado convertirEntityToModelRazonResultado(
			DicRazonResultado entity) {
		if(entity== null)
			return null;
		RazonResultado model = new RazonResultado();
		model.setIdRazonResultado(entity.getCveIdRazonResultado());
		model.setDescripcion(entity.getDesRazonResultado());
		
		return model;
	}

	@Override
	public TipoTramite convertirEntityToModelTipoTramite(DicTipoTramite entity) {
		if(entity==null){
			System.err.println("No hay tipo de tramite en el tramite");
			return null;
		}
		TipoTramite model = new TipoTramite();
		model.setIdTipoTramite(entity.getCveIdTipoTramite().intValue());
		System.err.println("Agregando el tipo de tramite: "+entity.getDesTipoTramite());
		model.setDescripcion(entity.getDesTipoTramite());
		model.setIndTipoConclusion(entity.getIndTipoConclusion());
		if(entity.getDitDoctoReqTramites() != null){
			List<Documento> documentos = new ArrayList<Documento>();
			for(DitDoctoReqTramite docTramite :entity.getDitDoctoReqTramites()){
				documentos.add(convertirEntityToModelDocumento(docTramite.getDitDocumentoPorTipo().getDicDocumento()));
			}
			model.setDocumentos(documentos);
		}
		
		return model;
	}

	@Override
	public Documento convertirEntityToModelDocumento(DicDocumento entity) {
		Documento model = new Documento();
		if(entity == null)
			return null;
		model.setCveIdDocumento(entity.getCveIdDocumento());
		model.setDesDocumento(entity.getDesDocumento());
		return model;
	}

	@Override
	public List<Documento> convertirEntitiesToModel(List<DicDocumento> entities) {
		if(entities == null)
			return null;
		
		List<Documento> models = new ArrayList<Documento>();
		
		for(DicDocumento entity :entities){
			models.add( convertirEntityToModelDocumento(entity) );
		}
		
		return models;
	}
	
	
	public TipoSolicitud convertirEntityToModelTipoSolicitud(DicTipoSolicitud entity){
		TipoSolicitud model = new TipoSolicitud();
		if(entity == null)
			return model;
		
		model.setDescripcion(entity.getDesTipoSolicitud());
		model.setIdTipoSolicitud(entity.getCveIdTipoSolicitud());
		return model;
	}
}
