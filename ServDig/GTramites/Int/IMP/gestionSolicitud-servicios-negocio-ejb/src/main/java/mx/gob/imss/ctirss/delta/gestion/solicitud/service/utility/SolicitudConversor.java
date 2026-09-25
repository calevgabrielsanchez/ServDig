package mx.gob.imss.ctirss.delta.gestion.solicitud.service.utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.util.Utilerias;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Turno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSociedad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TramiteSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Certificado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.CitaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.PersonaInteresadaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.RazonCancelacion;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.persistence.DicDelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicModalidad;
import mx.gob.imss.ctirss.delta.persistence.DicOrigenSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DicRazonCancelacion;
import mx.gob.imss.ctirss.delta.persistence.DicRazonResultado;
import mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicTipoSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DicTipoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicTurno;
import mx.gob.imss.ctirss.delta.persistence.DicUmf;
import mx.gob.imss.ctirss.delta.persistence.DitCitaSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaInteresadaSol;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitudDocumento;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitudFirmaDigital;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaMoral;
import mx.gob.imss.ctirss.delta.persistence.DitUmfTurno;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateless
public class SolicitudConversor implements SolicitudConversorLocal {
	protected final Log log = LogFactory.getLog(SolicitudConversor.class);

	@EJB
	private transient TramiteConversorLocal tramiteConversor;

    @Override
    public Solicitud convertirEntityToModel(final DitSolicitud ditSolicitud, boolean obtenerDatosXML) {
        Solicitud solicitud = null; // NOPMD
        if (ditSolicitud != null) {
            solicitud = new Solicitud();
            solicitud.setSolicitudId(ditSolicitud.getCveIdSolicitud());
            solicitud.setFechaSolicitud(ditSolicitud.getFecSolicitud());
            solicitud.setFechaPresentacion(ditSolicitud.getFecPresentacion());
            solicitud.setFechaConclusion(ditSolicitud.getFecConclusion());
            solicitud.setFechaActualizacion(ditSolicitud.getFecRegistroActualizado());
            solicitud.setNoFolioSolicitud(ditSolicitud.getRefFolio());
            solicitud.setObservacion(ditSolicitud.getRefObservacion());
            solicitud = convertirDitSolicitudFirma(ditSolicitud, solicitud);
            convertirEstadosSolicitud(ditSolicitud, solicitud);
            
          //se cambio la forma del setear al usuario ya que ahora no tiene detalle y solo la clave
            //solicitud.setSolicitante(convertirUsuario(ditSolicitud.getDitUsuario()));
            String cveUsuario = ditSolicitud.getCveIdUsuario();
            if(cveUsuario != null && !cveUsuario.isEmpty()){
            	Usuario objUsuario = new Usuario();
            	objUsuario.setUsuario(cveUsuario);
                solicitud.setSolicitante(objUsuario);	
            }
            
            //Se transforma ditPersonaInteresada sol
            List<DitPersonaInteresadaSol> personasInteresadaSols = ditSolicitud.getDitPersonaInteresadaSols();
            if(personasInteresadaSols != null && !personasInteresadaSols.isEmpty()) {
            	DitPersonaInteresadaSol ditPerInt = personasInteresadaSols.get(0);
            	solicitud.setPersonaInteresadaSolicitud(this.convertirPersonaInteresada(ditPerInt));
            }
            
            
            solicitud.setSubdelegacion(convertirSubdelegacion(ditSolicitud.getDicSubdelegacion()));
            
			if (ditSolicitud.getDicOrigenSolicitud() != null
					&& ditSolicitud.getDicOrigenSolicitud()
							.getCveIdOrigenSolicitud() > 0) {
            	solicitud.setOrigenSolicitud(convertirOrigenSolicitud(ditSolicitud.getDicOrigenSolicitud()));
            }
            
            if(ditSolicitud.getDitSolicitudDocumento()!= null){
            	DitSolicitudDocumento ditSolicitudDocumento = ditSolicitud.getDitSolicitudDocumento();
            	solicitud.setDocumentoAcuse(ditSolicitudDocumento.getRefAcuseRecibo());
    			solicitud.setDocumentoComprobante(ditSolicitudDocumento.getRefComprobanteTramite());
            }
            
            if(ditSolicitud.getDitUmfTurno() != null) {
            	log.debug("Se encontraron dados de cita");
            	DitUmfTurno ditUmfTurno = ditSolicitud.getDitUmfTurno();
            	CitaSolicitud cita = new CitaSolicitud();
            	cita.setUmf(this.convertirUmf(ditUmfTurno.getDicUmf()));
            	cita.setTurno(this.convertirTurno(ditUmfTurno.getDicTurno()));
            	cita.setFechaHora(ditSolicitud.getFecCita());
            	
            	solicitud.setCitaSolicitud(cita);
            	solicitud.setFechaCita(cita.getFechaHora());
            }
            
            // CONVERSION DE TRAMITES:
            if (ditSolicitud.getDitTramites() != null) {
				try {
	                for (DitTramite ditTramite : ditSolicitud.getDitTramites()) {
	                    // Recupera al tramite desde el XML:
	                	Tramite tramite = new Tramite();
	                	if(obtenerDatosXML)
	                		tramite = tramiteConversor.convertirEntityToXmlModel(ditTramite);
	                	tramite.setEstadoTramite(convertirEstadoTramite(ditTramite.getDicEstadoTramite()));
	                	
	                	if (ditTramite.getDicTipoTramite() != null && StringUtils.isNotBlank(ditTramite.getDicTipoTramite().getDesTipoTramite())) {
	                		DicTipoTramite dicTipoTram = ditTramite.getDicTipoTramite();
	                		tramite.getTipoTramite().setDescripcion(dicTipoTram.getDesTipoTramite().toUpperCase());
	                		tramite.getTipoTramite().setHomoclave(dicTipoTram.getRefHomoclave());
	                	}
	                	
	                	tramite.getTipoTramite().setIndTipoConclusion(ditTramite.getDicTipoTramite().getIndTipoConclusion());
	                	tramite.setFechaConclusion(ditTramite.getFecConclusion());
	                	tramite.setFechaEfecto(ditTramite.getFecEfecto());
	                	tramite.setFechaPresentacion(ditTramite.getFecPresentacion());
	                	tramite.setFechaTramite(ditTramite.getFecTramite());
	                	tramite.setRazonResultado(convertirRazonResultado(ditTramite.getDicRazonResultado()));
	                	tramite.setObservacion(ditTramite.getRefObservacion());
	                	tramite.setTramiteId(ditTramite.getCveIdTramite());
	                	tramite.setFechaRegistroActualizacion(ditTramite.getFecRegistroActualizado());
	                	solicitud.getTramites().add(tramite);
	                }
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e);
					solicitud.setTramites(null);
				}
            }
            
            TipoSolicitud tipoSol = new TipoSolicitud();
            tipoSol.setIdTipoSolicitud(ditSolicitud.getDicTipoSolicitud().getCveIdTipoSolicitud());
            tipoSol.setDescripcion(ditSolicitud.getDicTipoSolicitud().getDesTipoSolicitud());
            solicitud.setTipoSolicitud(tipoSol);
        }

        return solicitud;
    }
    
    private PersonaInteresadaSolicitud convertirPersonaInteresada(DitPersonaInteresadaSol ditPerInt) {
    	PersonaInteresadaSolicitud personaSol = null;
    	if(ditPerInt!=null) {
    		personaSol = new PersonaInteresadaSolicitud();
    		personaSol.setCveIdPerTramInteresadaSol(ditPerInt.getCveIdPerTramInteresadaSol());
    		personaSol.setPersona(new Persona());
    		personaSol.getPersona().setIdPersona(ditPerInt.getDitPersona().getCveIdPersona());
    	}
    	
    	return personaSol;
    }
    
    private UnidadMedicaFamiliar convertirUmf(DicUmf dicUmf) {
    	UnidadMedicaFamiliar umf = new UnidadMedicaFamiliar();
    	umf.setIdUMF(dicUmf.getCveIdUmf());
		umf.setNombreCorto(dicUmf.getNomCorto());
		umf.setDescripcion(dicUmf.getNomUnidad());
		umf.setNoEconomico(dicUmf.getNumEconom());
		
		if(dicUmf.getDicSubdelegacion() != null) {
			DicSubdelegacion dicSub = dicUmf.getDicSubdelegacion();
			Subdelegacion sub = new Subdelegacion();
			sub.setId(dicSub.getCveIdSubdelegacion());
			sub.setDescripcion(dicSub.getDesSubdelegacion());
			sub.setClave(dicSub.getClaveSubdelegacion());
			
			if(dicSub.getDicDelegacion() != null) {
				DicDelegacion dicDel = dicSub.getDicDelegacion();
				Delegacion del = new Delegacion();
				del.setId(dicDel.getCveIdDelegacion());
				del.setDescripcion(dicDel.getDesDeleg());
				del.setClave(dicDel.getClaveDelegacion());
				
				sub.setDelegacion(del);
			}
			
			umf.setSubdelegacion(sub);
		}

    	
    	return umf;
    }

    private Certificado convertirCertificado(DitSolicitud ditSolicitud){
    	Certificado certificado = new Certificado();
    	DitSolicitudFirmaDigital ditSolicitudFirmaDigital = ditSolicitud.getDitSolicitudFirmaDigitals().get(0);
    	certificado.setClaveSerial(ditSolicitudFirmaDigital.getRefNumSerieCertificado());
    	certificado.setFechaValidaInicio(ditSolicitudFirmaDigital.getFecInicioVigenciaCert());
    	certificado.setFechaValidaFin(ditSolicitudFirmaDigital.getFecFinVigenciaCert());
    	return certificado;
    }

    private Turno convertirTurno(final DicTurno dicTurno) {
        Turno turno = null; // NOPMD
        if (dicTurno != null) {
            turno = new Turno();
            turno.setIdTurno(dicTurno.getCveIdTurno());
            turno.setDescripcion(dicTurno.getDesDescripcion());
            turno.setHoraInicioTurno(dicTurno.getRefHoraInicioTurno());
            turno.setHoraFinTurno(dicTurno.getRefHoraFinTurno());
        }
        return turno;
    }

    private void convertirEstadosSolicitud(final DitSolicitud ditSolicitud, final Solicitud solicitud) {

        if (ditSolicitud.getDicEstadoSolicitud() != null) {
            final EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
            estadoSolicitud.setIdEstadoSolicitud(Utilerias.convertir(ditSolicitud.getDicEstadoSolicitud().getCveIdEstadoSolicitud()));
            estadoSolicitud.setDescripcion(ditSolicitud.getDicEstadoSolicitud().getDesEstadoSolicitud());
            solicitud.setEstadoSolicitud(estadoSolicitud);
        }

        if (ditSolicitud.getDicRazonCancelacion() != null) {
            final RazonCancelacion razonCancelacion = new RazonCancelacion();
            razonCancelacion.setIdRazonCancelacion(ditSolicitud.getDicRazonCancelacion().getCveIdRazonCancelacion());
            razonCancelacion.setDescripcion(ditSolicitud.getDicRazonCancelacion().getDesRazonCancelacion());
            solicitud.setRazonCancelacion(razonCancelacion);
        }

        if (ditSolicitud.getDicTipoSolicitud() != null) {
            final TipoSolicitud tipoSolicitud = new TipoSolicitud();
            tipoSolicitud.setIdTipoSolicitud(ditSolicitud.getDicTipoSolicitud().getCveIdTipoSolicitud());
            tipoSolicitud.setDescripcion(ditSolicitud.getDicTipoSolicitud().getDesTipoSolicitud());
            solicitud.setTipoSolicitud(tipoSolicitud);
        }

    }
    
    /**
     * 
     * @author Hugo Martinez
     * @Date 27/02/2013
     * @param entity
     * @return Subdelegacion model object
     */
    private Subdelegacion convertirSubdelegacion(DicSubdelegacion entity){
    	if(entity==null)
    		return null;
    	Subdelegacion model = new Subdelegacion();
		model.setClave(entity.getClaveSubdelegacion());
		model.setId(entity.getCveIdSubdelegacion());
		model.setDescripcion(entity.getDesSubdelegacion());
		model.setDelegacion(convertirDelegacion(entity.getDicDelegacion()));
		return model;
    }
    
    /**
     * 
     * @author Hugo Martinez
     * @Date 27/02/2013
     * @param entity
     * @return Delegacion model object
     */
    private Delegacion convertirDelegacion(DicDelegacion entity){
    	if(entity==null)
    		return null;
    	Delegacion delegacion = new Delegacion();
		delegacion.setClave(entity.getClaveDelegacion());
		delegacion.setId(entity.getCveIdDelegacion());
		delegacion.setDescripcion(entity.getDesDeleg());
		Integer ciz = entity.getCveCiz() == null ? 1 : entity.getCveCiz();
		delegacion.setCiz(ciz);
		return delegacion;
    }

	@Override
	public Solicitud convertirEntityToModelBasico(DitSolicitud entity) {
		Solicitud solicitud  = new Solicitud();		
		solicitud.setSolicitudId(entity.getCveIdSolicitud());
		solicitud.setEstadoSolicitud(convertirEstadoSolicitud(entity.getDicEstadoSolicitud()));
		solicitud.setFechaSolicitud(entity.getFecSolicitud());
		solicitud.setFechaPresentacion(entity.getFecPresentacion());
		solicitud.setFechaConclusion(entity.getFecConclusion());
		solicitud.setFechaActualizacion(entity.getFecRegistroActualizado());
		solicitud.setNoFolioSolicitud(entity.getRefFolio());
		solicitud.setObservacion(entity.getRefObservacion());
		solicitud.setSubdelegacion(convertirSubdelegacion(
				entity.getDicSubdelegacion()));
		solicitud.setRazonCancelacion(convertirRazonCancelacion(
				entity.getDicRazonCancelacion()));
		solicitud.setTipoSolicitud(convertirTipoSolicitud(entity.getDicTipoSolicitud()) );
		
		if (entity.getDicOrigenSolicitud() != null
				&& entity.getDicOrigenSolicitud()
						.getCveIdOrigenSolicitud() > 0) {
        	solicitud.setOrigenSolicitud(convertirOrigenSolicitud(entity.getDicOrigenSolicitud()));
        }
		
		byte[] documentoAcuse = entity.getDitSolicitudDocumento()!=null ? entity.getDitSolicitudDocumento().getRefAcuseRecibo() : null;
		byte[] documentoComprobante = entity.getDitSolicitudDocumento()!=null ? entity.getDitSolicitudDocumento().getRefAcuseRecibo() : null;
		solicitud.setDocumentoAcuse(documentoAcuse);
		solicitud.setDocumentoComprobante(documentoComprobante);
		System.err.println("Se agregan tramites a la solicitud");
		solicitud.setTramites(convertirTramitesSujetoObligado(entity.getDitTramites()));
		solicitud = agregarSujetoObligadoDeTramiteASolicitud(solicitud);
		System.err.println("Estado solicitud en convertEntityToModelSolicitud "+solicitud.getEstadoSolicitud().getDescripcion());
		return solicitud;
	}
	
	
	/**
	 * Metodo para obtener unicamente los datos de la solicitud, sin parsear los datos de tramite
	 * ni de documentos
	 */
	@Override
	public Solicitud convertirEntityToModelDatosBase(DitSolicitud entity) {
		Solicitud solicitud  = new Solicitud();		
		solicitud.setSolicitudId(entity.getCveIdSolicitud());
		solicitud.setEstadoSolicitud(convertirEstadoSolicitud(entity.getDicEstadoSolicitud()));
		solicitud.setFechaSolicitud(entity.getFecSolicitud());
		solicitud.setFechaPresentacion(entity.getFecPresentacion());
		solicitud.setFechaConclusion(entity.getFecConclusion());
		solicitud.setFechaActualizacion(entity.getFecRegistroActualizado());
		solicitud.setNoFolioSolicitud(entity.getRefFolio());
		solicitud.setObservacion(entity.getRefObservacion());
		solicitud.setRazonCancelacion(convertirRazonCancelacion(
				entity.getDicRazonCancelacion()));
		solicitud.setTipoSolicitud(convertirTipoSolicitud(entity.getDicTipoSolicitud()) );
		
		if (entity.getDicOrigenSolicitud() != null
				&& entity.getDicOrigenSolicitud()
						.getCveIdOrigenSolicitud() > 0) {
        	solicitud.setOrigenSolicitud(convertirOrigenSolicitud(entity.getDicOrigenSolicitud()));
        }
		
		return solicitud;
	}

	private Solicitud agregarSujetoObligadoDeTramiteASolicitud(Solicitud solicitud){
		if(solicitud.getTramites()!=null && solicitud.getTramites().size()>0)
			solicitud.setSujetoObligado(((TramiteSolicitud)solicitud.getTramites().get(0)).getSujetoObligado());
		
		return solicitud;
	}
	
	private EstadoSolicitud convertirEstadoSolicitud(
			DicEstadoSolicitud entity) {		
		EstadoSolicitud model = new EstadoSolicitud();
		model.setIdEstadoSolicitud(entity.getCveIdEstadoSolicitud().intValue());
		String desc = entity.getDesEstadoSolicitud().toString();
		model.setDescripcion(desc);
		return model;
	}
	
	private RazonCancelacion convertirRazonCancelacion(
			DicRazonCancelacion entity) {
		RazonCancelacion model = new RazonCancelacion();
		if(entity== null)
			return null;
		model.setIdRazonCancelacion(entity.getCveIdRazonCancelacion());
		model.setDescripcion(entity.getDesRazonCancelacion());
		return model;
	}
	
	private TipoSolicitud convertirTipoSolicitud(
			DicTipoSolicitud tipoSolicitud) {
		if(tipoSolicitud==null)
			return null;
		TipoSolicitud model = new TipoSolicitud();
		model.setIdTipoSolicitud(tipoSolicitud.getCveIdTipoSolicitud());
		model.setDescripcion(tipoSolicitud.getDesTipoSolicitud());
		
		return model;
	}
	
	private List<Tramite> convertirTramitesSujetoObligado(List<DitTramite> entities){
		List<Tramite> models = new ArrayList<Tramite>();
		if(entities != null){
			for(DitTramite entity : entities){
				models.add(tramiteConversor.convertirTramiteSujetoObligado(entity));
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
			List<DitTramite> tramites = entity.getDitTramites();
					
					
			for(DitTramite tramite : tramites){
				if(tramite.getDitTramitePatSujObligados()!= null && tramite.getDitTramitePatSujObligados().size()>0){//Existe un tramite patronal
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
		so.setModalidad(convertirModalidad(entity.getDicModalidad()));
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
					convertirSubdelegacion(entity.getDitSubdelPatSujOblig().getDicSubdelegacion()));
		
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
		fisica.setCurp(ditPersona.getCurp());
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
	
	private Modalidad convertirModalidad(DicModalidad entity){
		if(entity != null){
			Modalidad model = new Modalidad();
			model.setIdModalidad(entity.getCveIdModalidad());
			model.setNumModalidad(entity.getNumModalidad());
			model.setDescripcion(entity.getDesModalidad());
			return model;
		}
		return null;
	}
	
	private Solicitud convertirDitSolicitudFirma(DitSolicitud ditSolicitud, Solicitud solicitud){
		List<DitSolicitudFirmaDigital> firmaDigitals = ditSolicitud.getDitSolicitudFirmaDigitals();
		if(firmaDigitals !=null && !firmaDigitals.isEmpty()){
			solicitud.setCadenaOriginal(firmaDigitals.get(0).getNumCadenaOriginal());
			solicitud.setSelloDigital(firmaDigitals.get(0).getNumSelloDigital());
			solicitud.setSecuenciaDeNotaria(firmaDigitals.get(0).getNumSecNotaria());
			solicitud.setNumeroSerieCertificado(firmaDigitals.get(0).getRefNumSerieCertificado());
			solicitud.setCertificado(convertirCertificado(ditSolicitud));
			
			
			if(firmaDigitals.size() > 1) {
				solicitud.setCadenaOriginalRepresentado(firmaDigitals.get(1).getNumCadenaOriginal());
				solicitud.setSelloDigitalRepresentado(firmaDigitals.get(1).getNumSelloDigital());
				solicitud.setSecuenciaDeNotariaRepresentado(firmaDigitals.get(1).getNumSecNotaria());
				solicitud.setNumeroSerieCertificadoRepresentado(firmaDigitals.get(1).getRefNumSerieCertificado());
				FirmaElectronica firma = new FirmaElectronica();
				firma.setCadenaOriginal(firmaDigitals.get(1).getNumCadenaOriginal());
				firma.setSecuenciaNotaria(firmaDigitals.get(1).getNumSecNotaria());
				firma.setReciboNotarial(firmaDigitals.get(1).getNumSelloDigital());
				firma.setSerialCertificado(firmaDigitals.get(1).getRefNumSerieCertificado());
				firma.setFinVigenciaCertificado(firmaDigitals.get(1).getFecFinVigenciaCert());
				firma.setIniciaVigenciaCertificado(firmaDigitals.get(1).getFecInicioVigenciaCert());
				solicitud.setFirmaElectronica(firma);
			}
		}	
		return solicitud;
	}
			
	private EstadoTramite convertirEstadoTramite(DicEstadoTramite dicEstadoTramite){
		EstadoTramite estadoTramite = new EstadoTramite();
		estadoTramite.setIdEstadoTramitePersona(dicEstadoTramite.getCveIdEstadoTramite().intValue());
		estadoTramite.setDescripcion(dicEstadoTramite.getDesEstadoTramite());
		
		return estadoTramite;
	}
	
	@Override
	public RazonResultado convertirRazonResultado(DicRazonResultado dicRazonResultado){
		if(dicRazonResultado==null)
			return null;
		RazonResultado razonResultado = new RazonResultado();
		razonResultado.setIdRazonResultado(dicRazonResultado.getCveIdRazonResultado());
		razonResultado.setDescripcion(dicRazonResultado.getDesRazonResultado());
		
		return razonResultado;
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
	
	@Override
	public DicOrigenSolicitud convertirOrigenSolicitud(
			 OrigenSolicitud origenSolicitud) {

		DicOrigenSolicitud dicOrigenSolicitud = new DicOrigenSolicitud();
		
		if (origenSolicitud != null
				&& origenSolicitud.getIdTipoSolicitud() != null
				&& origenSolicitud.getIdTipoSolicitud() > 0) {
			dicOrigenSolicitud.setCveIdOrigenSolicitud(origenSolicitud.getIdTipoSolicitud());
		} else {
			/*
			 * Si no se trae origen de solicitud, se settea por default
			 * el origen INTERNET
			 */
			dicOrigenSolicitud.setCveIdOrigenSolicitud(OrigenSolicitudEnum.INTERNET.getId());
		}
		
		return dicOrigenSolicitud;
		
	}

	@Override
	public CitaSolicitud parserCitaEntityToModel(DitCitaSolicitud citaBd) {
		
		CitaSolicitud cita = new CitaSolicitud();
		cita.setFechaHora(citaBd.getFecCita());
		cita.setRefFolioCita(citaBd.getRefFolioCita());
		cita.setIndCitaActiva(citaBd.getIndCitaActiva());
		cita.setCveIdSolicitud(citaBd.getDitSolicitud().getCveIdSolicitud());
		cita.setSubdelegacion(convertirSubdelegacion(citaBd.getDicSubdelegacion()));
		cita.setIdCitaSolicitud(citaBd.getCveIdCitaSolicitud());
		cita.setNumContadorCambioCita(citaBd.getNumContadorCambioCita());
		
		return cita;
	}
	
	
}
