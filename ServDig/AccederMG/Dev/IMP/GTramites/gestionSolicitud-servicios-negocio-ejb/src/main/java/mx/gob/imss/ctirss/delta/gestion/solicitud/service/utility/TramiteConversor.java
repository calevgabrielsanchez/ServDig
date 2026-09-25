package mx.gob.imss.ctirss.delta.gestion.solicitud.service.utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBException;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.framework.util.Utilerias;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Documento;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TramiteSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteInfo;
import mx.gob.imss.ctirss.delta.persistence.DicDocumento;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicRazonResultado;
import mx.gob.imss.ctirss.delta.persistence.DicTipoSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DicTipoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicTramiteInfo;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleTramite;
import mx.gob.imss.ctirss.delta.persistence.DitDoctoReqTramite;
import mx.gob.imss.ctirss.delta.persistence.DitPatronGeneral;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaMoral;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvro;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvroMod33;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvroMod40;
import mx.gob.imss.distss.digital.jaxb.util.JaxbUtil;

@Stateless
@Local(value = TramiteConversorLocal.class)
public class TramiteConversor extends AbstractServiceUtility implements
		TramiteConversorLocal {

	

	public DitTramite convertirModelToEntity(final Tramite tramite) {
		DitTramite ditTramite = null; // NOPMD

		if (tramite != null) {
			ditTramite = new DitTramite();
			ditTramite.setFecTramite(tramite.getFechaTramite());
			ditTramite.setCveIdTramite(tramite.getTramiteId());
			ditTramite.setFecConclusion(tramite.getFechaConclusion());
			ditTramite.setFecEfecto(tramite.getFechaEfecto());
			ditTramite.setFecPresentacion(tramite.getFechaPresentacion());
			if (tramite.getEstadoTramite() != null
					&& Utilerias.isNotBlank(tramite.getEstadoTramite()
							.getIdEstadoTramitePersona())) {
				final DicEstadoTramite dicEstadoTramite = new DicEstadoTramite();
				dicEstadoTramite.setCveIdEstadoTramite(tramite
						.getEstadoTramite().getIdEstadoTramitePersona()
						.longValue());
				dicEstadoTramite.setDesEstadoTramite(tramite.getEstadoTramite()
						.getDescripcion());
				ditTramite.setDicEstadoTramite(dicEstadoTramite);
			}
			if (tramite.getTipoTramite() != null
					&& Utilerias.isNotBlank(tramite.getTipoTramite()
							.getIdTipoTramite())) {
				final DicTipoTramite dicTipoTramite = new DicTipoTramite();
				dicTipoTramite.setCveIdTipoTramite(tramite.getTipoTramite().getIdTipoTramite().longValue());
				dicTipoTramite.setDesTipoTramite(tramite.getTipoTramite().getDescripcion());
				dicTipoTramite.setRefHomoclave(tramite.getTipoTramite().getHomoclave());
				ditTramite.setDicTipoTramite(dicTipoTramite);
			}
			if (tramite.getRazonResultado() != null
					&& Utilerias.isNotBlank(tramite.getRazonResultado()
							.getIdRazonResultado())) {
				final DicRazonResultado dicRazonResultado = new DicRazonResultado();
				dicRazonResultado.setCveIdRazonResultado(tramite
						.getRazonResultado().getIdRazonResultado());
				dicRazonResultado.setDesRazonResultado(tramite
						.getRazonResultado().getDescripcion());
				ditTramite.setDicRazonResultado(dicRazonResultado);
			}
		}

		return ditTramite;
	}

	public Tramite convertirEntityToModel(DitTramite ditTramite) {
		Tramite tramite = null; // NOPMD

		if (ditTramite != null) {
			tramite = new Tramite();
			tramite.setTramiteId(ditTramite.getCveIdTramite());
			tramite.setFechaTramite(ditTramite.getFecTramite());
			if (ditTramite.getDicEstadoTramite() != null) {
				final EstadoTramite estadoTramite = new EstadoTramite();
				estadoTramite.setIdEstadoTramitePersona(ditTramite
						.getDicEstadoTramite().getCveIdEstadoTramite()
						.intValue());
				estadoTramite.setDescripcion(ditTramite.getDicEstadoTramite()
						.getDesEstadoTramite());
				tramite.setEstadoTramite(estadoTramite);
			}
			if (ditTramite.getDicTipoTramite() != null) {
				final TipoTramite tipoTramite = new TipoTramite();
				tipoTramite.setIdTipoTramite(ditTramite.getDicTipoTramite()
						.getCveIdTipoTramite().intValue());
				tipoTramite.setDescripcion(ditTramite.getDicTipoTramite()
						.getDesTipoTramite().toUpperCase());
				// TODO faltan algunos campos... tipoTramite.set
				tramite.setTipoTramite(tipoTramite);
			}
			if (ditTramite.getDicRazonResultado() != null) {
				final RazonResultado razonResultado = new RazonResultado();
				razonResultado.setIdRazonResultado(ditTramite
						.getDicRazonResultado().getCveIdRazonResultado());
				razonResultado.setDescripcion(ditTramite.getDicRazonResultado()
						.getDesRazonResultado());
				tramite.setRazonResultado(razonResultado);
			}
			if (ditTramite.getDitDetalleTramite() != null) {
				tramite.setDetalleTramiteXml(ditTramite.getDitDetalleTramite()
						.getRefDatosTramiteXml());
			}
		}

		return tramite;
	}

	public Tramite convertirEntityToXmlModel(final DitTramite ditTramite) {
		Tramite tramite = null; // NOPMD

		if (ditTramite != null) {
			Long idTipoTramite = ditTramite.getDicTipoTramite().getCveIdTipoTramite();
			if(idTipoTramite.equals(TipoTramiteEnum.COMPRA_SEGURO_INDIVIDUAL.getCodigo().longValue())
					|| idTipoTramite.equals(TipoTramiteEnum.RENOVACION_SEGURO_INDIVIDUAL.getCodigo().longValue())
					|| idTipoTramite.equals(TipoTramiteEnum.COMPRA_SEGURO_DOMESTICO.getCodigo().longValue())
					|| idTipoTramite.equals(TipoTramiteEnum.RENOVACION_SEGURO_DOMESTICO.getCodigo().longValue())
					|| idTipoTramite.equals(TipoTramiteEnum.COMPRA_SEGURO_FAMILIAR.getCodigo().longValue())
                    || idTipoTramite.equals(TipoTramiteEnum.RENOVACION_SEGURO_FAMILIAR.getCodigo().longValue())
					|| idTipoTramite.equals(TipoTramiteEnum.COMPRA_CONTINUACION_VOLUNTARIA.getCodigo().longValue())
					|| idTipoTramite.equals(TipoTramiteEnum.RENOVACION_CONTINUACION_VOLUNTARIA.getCodigo().longValue())) {

			    mx.gob.imss.digital.modelo.tramite.Tramite tramiteImssDigital;
			    try {
			    	String detallexml = ditTramite.getDitDetalleTramite().getRefDatosTramiteXml();
			    	
			    	log.debug("detalle a transformar: "+ detallexml!=null ? detallexml : "");
    			    tramiteImssDigital = JaxbUtil.unmarshaller(ditTramite
                            .getDitDetalleTramite().getRefDatosTramiteXml(), mx.gob.imss.digital.modelo.tramite.Tramite.class);
    				tramite = convertirTramiteIDModeloATramiteModeloNegocio(tramiteImssDigital, idTipoTramite.intValue());
    				tramite.setDetalleTramiteXml(ditTramite.getDitDetalleTramite()!=null ? 
                    		ditTramite.getDitDetalleTramite().getRefDatosTramiteXml() : null);
    				log.debug("finaliza transformacion tramite seguro ivro a (mx.gob.imss.digital.modelo.tramite.Tramite)" );
			    } catch (JAXBException e) {
			        try {
						log.warn("El tipo de tramite recibido no es del tipo esperado (mx.gob.imss.digital.modelo.tramite.Tramite) se procede a intentar transformarlo a (mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvro)");
                        tramiteImssDigital = JaxbUtil.unmarshaller(ditTramite
                                .getDitDetalleTramite().getRefDatosTramiteXml(), mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvro.class);
                        tramite = convertirTramiteIDModeloATramiteModeloNegocio(tramiteImssDigital, idTipoTramite.intValue());
                        tramite.setDetalleTramiteXml(ditTramite.getDitDetalleTramite()!=null ? 
                        		ditTramite.getDitDetalleTramite().getRefDatosTramiteXml() : null);
                        log.debug("finaliza transformacion tramite seguro ivro a (mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvro)" );
                    } catch (JAXBException e1) {
                    	try {
                    		log.warn("El tipo de tramite recibido no es del tipo esperado (mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvro) se procede a intentar transformarlo a (mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvroMod33)");
	                        tramiteImssDigital = JaxbUtil.unmarshaller(ditTramite
	                                .getDitDetalleTramite().getRefDatosTramiteXml(), mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvroMod33.class);
	                        tramite = convertirTramiteIDModeloATramiteModeloNegocio(tramiteImssDigital, idTipoTramite.intValue());
	                        tramite.setDetalleTramiteXml(ditTramite.getDitDetalleTramite()!=null ? 
	                        		ditTramite.getDitDetalleTramite().getRefDatosTramiteXml() : null);
	                        log.debug("finaliza transformacion tramite seguro ivro a (mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvroMod33)" );
                    	} catch (JAXBException e2) {
                        	try {
                        		log.warn("El tipo de tramite recibido no es del tipo esperado (mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvroMod33) se procede a intentar transformarlo a (mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvroMod40)");
    	                        tramiteImssDigital = JaxbUtil.unmarshaller(ditTramite
    	                                .getDitDetalleTramite().getRefDatosTramiteXml(), mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvroMod40.class);
    	                        tramite = convertirTramiteIDModeloATramiteModeloNegocio(tramiteImssDigital, idTipoTramite.intValue());
    	                        tramite.setDetalleTramiteXml(ditTramite.getDitDetalleTramite()!=null ? 
    	                        		ditTramite.getDitDetalleTramite().getRefDatosTramiteXml() : null);
    	                        log.debug("finaliza transformacion tramite seguro ivro a (mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvroMod40)" );
                        	} catch (Exception e3) {
                        		log.error("El tipo de tramite recibido no fue de ninguno de los tipos esperados", e3);
							}
                        }   
                    }                    
                }
			}else{
				tramite = (Tramite) mx.gob.imss.distss.digital.jaxb.util.JaxbUtil.xmlToObject(ditTramite
						.getDitDetalleTramite().getRefDatosTramiteXml());
			}
		}
		log.debug("Termine convertirEntityToXmlModel ");
		return tramite;
	}
	
	private Tramite convertirTramiteIDModeloATramiteModeloNegocio(mx.gob.imss.digital.modelo.tramite.Tramite tramiteIDModelo, Integer idTipoTramite){
		Tramite tramite = new Tramite();
		tramite.setTramiteId(tramiteIDModelo.getTramiteId());
		log.debug("convertirTramiteIDModeloATramiteModeloNegocio");
		TipoTramite tt = new TipoTramite();
		tt.setIdTipoTramite(idTipoTramite);
		
		tramite.setTipoTramite(tt);
		tramite.setDetalleTramiteXml(tramiteIDModelo.getDetalleTramiteXml());
		if(tramiteIDModelo instanceof TramiteSeguroIvro){
			log.debug("instancia de ivro: " + tramiteIDModelo);
			TramiteSeguroIvro tsi = (TramiteSeguroIvro)tramiteIDModelo;
			Fisica personaAfectadaTramite = new Fisica();
			personaAfectadaTramite.setIdPersona(tsi.getPersona().getIdPersona());
			personaAfectadaTramite.setTipoPersona(new TipoPersona());
			personaAfectadaTramite.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
			personaAfectadaTramite.setNombre(tsi.getPersona().getNombre());
			personaAfectadaTramite.setPrimerApellido(tsi.getPersona().getPrimerApellido());
			personaAfectadaTramite.setSegundoApellido(tsi.getPersona().getSegundoApellido());
			tramite.setPersona(personaAfectadaTramite);
		} else if(tramiteIDModelo instanceof TramiteSeguroIvroMod33){
			log.debug("instancia de ivro: " + tramiteIDModelo);
			TramiteSeguroIvroMod33 tsi = (TramiteSeguroIvroMod33)tramiteIDModelo;
			Fisica personaAfectadaTramite = new Fisica();
			personaAfectadaTramite.setIdPersona(tsi.getPersona().getIdPersona());
			personaAfectadaTramite.setTipoPersona(new TipoPersona());
			personaAfectadaTramite.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
			personaAfectadaTramite.setNombre(tsi.getPersona().getNombre());
			personaAfectadaTramite.setPrimerApellido(tsi.getPersona().getPrimerApellido());
			personaAfectadaTramite.setSegundoApellido(tsi.getPersona().getSegundoApellido());
			tramite.setPersona(personaAfectadaTramite);
		} else if(tramiteIDModelo instanceof TramiteSeguroIvroMod40){
			log.debug("instancia de ivro: " + tramiteIDModelo);
			TramiteSeguroIvroMod40 tsi = (TramiteSeguroIvroMod40)tramiteIDModelo;
			Fisica personaAfectadaTramite = new Fisica();
			personaAfectadaTramite.setIdPersona(tsi.getPersona().getIdPersona());
			personaAfectadaTramite.setTipoPersona(new TipoPersona());
			personaAfectadaTramite.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
			personaAfectadaTramite.setNombre(tsi.getPersona().getNombre());
			personaAfectadaTramite.setPrimerApellido(tsi.getPersona().getPrimerApellido());
			personaAfectadaTramite.setSegundoApellido(tsi.getPersona().getSegundoApellido());
			tramite.setPersona(personaAfectadaTramite);
		}
		
		return tramite;
	}
	
	/**
	 * @author Hugo Martinez
	 * @param tramite
	 * @param ditTramite
	 * @param fechaAlta
	 * @return DitDetalleTramite
	 */
	public DitDetalleTramite construirEntityDetalleTramite(
			final Tramite tramite, final DitTramite ditTramite,
			final Date fechaAlta) {

		final DitDetalleTramite ditDetalleTramite = new DitDetalleTramite();
		ditDetalleTramite.setFecRegistroActualizado(fechaAlta);
		ditDetalleTramite.setFecRegistroAlta(fechaAlta);
		ditDetalleTramite.setDitTramite(ditTramite);
		ditDetalleTramite.setCveIdTramite(ditTramite.getCveIdTramite());
		ditDetalleTramite.setRefDatosTramiteXml(mx.gob.imss.distss.digital.jaxb.util.JaxbUtil.objectToXml(tramite));

		return ditDetalleTramite;
	}

	@Override
	public TramiteSolicitud convertirTramiteSujetoObligado(DitTramite tramite) {
		TramiteSolicitud model = new TramiteSolicitud();
		System.err.println("convirtiendo tramites");
		model.setTramiteId(tramite.getCveIdTramite());
		model.setEstadoTramite(convertirEstadoTramite(tramite.getDicEstadoTramite()));
		model.setFechaTramite(tramite.getFecTramite());
		model.setFechaConclusion(tramite.getFecConclusion());
		model.setFechaPresentacion(tramite.getFecPresentacion());
		model.setFechaEfecto(tramite.getFecEfecto());
		model.setObservacion(tramite.getRefObservacion());
		model.setRazonResultado(convertirRazonResultado(tramite.getDicRazonResultado()));
		model.setIndRatificado(tramite.getIndRatificado());
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(tramite.getDitSolicitud().getCveIdSolicitud());
		solicitud.setNoFolioSolicitud(tramite.getDitSolicitud().getRefFolio());
		solicitud.setFechaSolicitud(tramite.getDitSolicitud().getFecSolicitud());
		EstadoSolicitud estadoSol = new EstadoSolicitud();
		estadoSol.setIdEstadoSolicitud(tramite.getDitSolicitud().getDicEstadoSolicitud().getCveIdEstadoSolicitud().intValue());
		estadoSol.setDescripcion(tramite.getDitSolicitud().getDicEstadoSolicitud().getDesEstadoSolicitud());
		solicitud.setEstadoSolicitud(estadoSol);
		solicitud.setTipoSolicitud(convertirTipoSolicitud(tramite.getDitSolicitud().getDicTipoSolicitud()));
		model.setSolicitud(solicitud);
		System.err.println("Se agregara el tipo de tramite");
		model.setTipoTramite(convertirTipoTramite(tramite.getDicTipoTramite()));
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
	
	private EstadoTramite convertirEstadoTramite(DicEstadoTramite entity) {
		EstadoTramite model = new EstadoTramite();
		model.setIdEstadoTramitePersona(entity.getCveIdEstadoTramite().intValue());
		model.setDescripcion(entity.getDesEstadoTramite());
		return model;
	}
	
	private RazonResultado convertirRazonResultado(
			DicRazonResultado entity) {
		if(entity== null)
			return null;
		RazonResultado model = new RazonResultado();
		model.setIdRazonResultado(entity.getCveIdRazonResultado());
		model.setDescripcion(entity.getDesRazonResultado());
		
		return model;
	}

	private TipoTramite convertirTipoTramite(DicTipoTramite entity) {
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
				documentos.add(convertirDocumento(docTramite.getDitDocumentoPorTipo().getDicDocumento()));
			}
			model.setDocumentos(documentos);
		}
		
		return model;
	}
	
	private TipoSolicitud convertirTipoSolicitud(DicTipoSolicitud entity){
		TipoSolicitud model = new TipoSolicitud();
		if(entity == null)
			return model;
		
		model.setDescripcion(entity.getDesTipoSolicitud());
		model.setIdTipoSolicitud(entity.getCveIdTipoSolicitud());
		return model;
	}
	
	public Documento convertirDocumento(DicDocumento entity) {
		Documento model = new Documento();
		if(entity == null)
			return null;
		model.setCveIdDocumento(entity.getCveIdDocumento());
		model.setDesDocumento(entity.getDesDocumento());
		return model;
	}
	
	@Override
	public TramiteInfo convertirEntityToModel(DicTramiteInfo entity) {
		
		TramiteInfo model = new TramiteInfo();
		
		model.setTramiteInfoId(entity.getCveIdTramiteInfo());
		model.setUrlWizard(entity.getDesUrlWizard());
		model.setDescripcionTramite(entity.getDesTramite());
		model.setInstrucciones(entity.getDesInstrucciones());
		
		OrigenSolicitud origen = new OrigenSolicitud();
		origen.setIdTipoSolicitud(entity.getDicOrigen()
				.getCveIdOrigenSolicitud());
		model.setOrigen(origen);
		
		TipoTramite tipoTramite = new TipoTramite();
		tipoTramite.setIdTipoTramite(entity.getDicTipoTramite()
				.getCveIdTipoTramite().intValue());
		tipoTramite.setDescripcion(entity.getDicTipoTramite()
				.getDesTipoTramite());
		model.setTipoTramite(tipoTramite);
				
		return model;
	}

}
