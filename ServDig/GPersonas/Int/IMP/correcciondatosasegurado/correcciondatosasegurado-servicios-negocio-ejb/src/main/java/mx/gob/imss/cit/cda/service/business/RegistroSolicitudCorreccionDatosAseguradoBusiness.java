/**
 * 
 */
package mx.gob.imss.cit.cda.service.business;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.entity.CorreccionDatosAseguradoLocal;
import mx.gob.imss.cit.cda.service.entity.DitBitFlujoArchSindoCDALocal;
import mx.gob.imss.cit.cda.service.interfaces.RegistroSolicitudCorreccionDatosAseguradoRemote;
import mx.gob.imss.cit.cda.service.interfaces.ResponsableTareaRemote;
import mx.gob.imss.cit.cda.service.utility.CorreccionDatosAseguradoUtilityLocal;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.EstadoTareaUsuarioNoValidoException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TareaInicialException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TereaSinUsuarioAsignadoException;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.InicioTramite;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.ParticipantesEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.ProcesosNegocioEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.FinalizaSolicitudServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.RegistrarDocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.CorreccionDatosAseguradoException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.NumeroMaximoResultadosSuperadoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.CalificacionesPersonaBusinessServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PortalCiudadanoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration.EMailProducer;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.Movimiento06CorreccionBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudTramiteBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.DatosLaborales;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.ObservacionesSubdelegacion;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoPerInteresadaSol;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaInteresadaSolEnum;
import mx.gob.imss.ctirss.delta.model.firma.RespuestaFirmadoSimple;
import mx.gob.imss.ctirss.delta.model.gestion.asegurado.integracion.sindo.MovCorreccionesDatosAseguradoType;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.PeriodoMovimientoAfiliatorio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.PersonaInteresadaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoTramite;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.AfectarDatosPersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.ComponentesExternosBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.utility.AfectarDatosPersonaUtilityRemote;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servicio para atender el registro de la solicitud de la Correci&oacute;n de Datos del Asegurado. 
 * 
 * @author STK
 * 
 */
@Stateless(name = "registroSolicitudCorreccionDatosAseguradoBusiness", mappedName = "registroSolicitudCorreccionDatosAseguradoBusiness")
public class RegistroSolicitudCorreccionDatosAseguradoBusiness implements RegistroSolicitudCorreccionDatosAseguradoRemote {
	
	@EJB(name = "solicitudBusiness", mappedName = "solicitudBusiness")
    private SolicitudBusinessRemote solicitudBusiness;
	
	@EJB(name = "firmaDigitalBusiness", mappedName = "firmaDigitalBusiness")
	private FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;
	
	@EJB(name = "solicitudTramiteBusiness", mappedName = "solicitudTramiteBusiness")
	private SolicitudTramiteBusinessRemote solicitudTramiteBusinessRemote;
	
	@EJB(name = "localizarPersonaFisicaEnRENAPOServiceBusiness", mappedName = "localizarPersonaFisicaEnRENAPOServiceBusiness")
	private LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote localizarPersonaFisicaEnRENAPOServiceBusiness;
	
	@EJB(name = "personaFisicaServiceBusiness", mappedName = "personaFisicaServiceBusiness")
	private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
	
	@EJB(name = "personaBusiness", mappedName = "personaBusiness")
	private PersonaBusinessRemote personaBusiness;
	
	@EJB(name = "documentoProbatorioServiceBusiness", mappedName = "documentoProbatorioServiceBusiness")
	private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;
	
	@EJB(name = "afectarDatosPersonaBusiness" , mappedName = "afectarDatosPersonaBusiness")
	private transient AfectarDatosPersonaBusinessRemote afectarDatosPersonaBusiness;
	
	@EJB(name = "domicilioServiceBusiness" , mappedName = "domicilioServiceBusiness")
	private DomicilioServiceBusinessRemote domicilioServiceBusiness;
	
	@EJB(name = "componentesExternosBusiness" , mappedName = "componentesExternosBusiness")
	private ComponentesExternosBusinessRemote componentesExternosBusiness;
	
	@EJB(name = "calificacionesPersonaBusinessService", mappedName = "calificacionesPersonaBusinessService")
	private CalificacionesPersonaBusinessServiceRemote calificacionesPersonaBusinessService;
	
	@EJB(mappedName = "EMailQProducer")
	private EMailProducer eMailProducer;
	
	@EJB(mappedName = "movimiento06CorreccionAsegurado")
	private Movimiento06CorreccionBusinessRemote movimiento06Correccion;
	
	@EJB(mappedName = "afectarDatosPersonaUtility")
	private AfectarDatosPersonaUtilityRemote afectarDatosPersonaUtility;
	
	@EJB(mappedName = "portalCiudadanoServiceBusiness")
	private PortalCiudadanoServiceBusinessRemote portalCiudadanoServiceBusiness;
	
	@EJB(mappedName = "grupoFamiliarService")
	private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	
	@EJB(mappedName = "finalizaSolicitud")
	private FinalizaSolicitudServiceRemote finalizaSolicitudServiceRemote;
	
	@EJB(mappedName = "responsableTareaBusiness" ,name="responsableTareaBusiness")
	private ResponsableTareaRemote responsableTareaBusiness;

	@EJB
	private CorreccionDatosAseguradoLocal correccionDatosAseguradoEntity;
	
	@EJB
	private CorreccionDatosAseguradoUtilityLocal correccionDatosAseguradoUtilityLocal;
	
	@EJB(name = "serviceBusiness", mappedName = "serviceBusiness")
	private ServiceBusinessRemote serviceBusiness;

	@EJB(name="sujetoObligadoServiceBusiness", mappedName="sujetoObligadoServiceBusiness")
	private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;
	
	@EJB
	private DitBitFlujoArchSindoCDALocal ditBitFlujoArchSindoCDALocal;
	
	@EJB(mappedName = "flujoTrabajoBusiness" ,name="flujoTrabajoBusiness")
	private FlujoTrabajoRemote flujoTrabajoBusiness;
	
	private final Logger log = LoggerFactory.getLogger(RegistroSolicitudCorreccionDatosAseguradoBusiness.class);
	
	private final String ERROR_SELLADO = "Ocurri\u00F3 un error al intentar sellar el documento.";
	
	private static final String ORIGEN_SOLICITUD_INTERNET = "INTERNET";
	
	private static final String ORIGEN_APLICACION_CDA = "CDA";
	private static final Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	
	private static final String USUARIO_INTERNET_BITACORA ="ASEGURADO";

	@Override
    public List<Solicitud> obtenerSolicitudesPorCurp(String curp) throws CorreccionDatosAseguradoException{
	List<Solicitud> solicitudes = new ArrayList<Solicitud>();
	
	Solicitud sol1 = new Solicitud(123456L);
	solicitudes.add(sol1);
	return solicitudes;
    }
    
    @Override
	public Solicitud crearTramiteCorreccionCurp(Fisica solicitante, OrigenSolicitudEnum origenSolicitud, Usuario usuario) throws SolicitudNoValidaException, SolicitudNoEncontradaException, TramiteNoEncontradoException {

		TipoTramiteEnum tipoTramiteEnum = TipoTramiteEnum.CORRECCION_DATOS_ASEGURADO;
		TipoSolicitudEnum tipoSolicitudInicial = TipoSolicitudEnum.CORRECCION_DATOS_ASEGURADO;
		//Tramite tramite = new Tramite();
		
		TramiteCorreccionCurp tramite = new TramiteCorreccionCurp();
		tramite.setPersonaRENAPO(solicitante);
		
		Solicitud solicitud = solicitudBusiness.crearSolicitudInicialPorEnum(
				EstadoSolicitudEnum.REGISTRADA, tipoSolicitudInicial, origenSolicitud, usuario);
		Date fechaActual = new Date();
		
		solicitud.setFechaPresentacion(fechaActual);
		solicitud.setPersonaInteresada(solicitante);
		solicitud = solicitudBusiness.asociarTramiteSolicitudPorEnum(solicitud, tramite, tipoTramiteEnum, EstadoTramiteEnum.INICIADO);
		
		if(usuario != null && usuario.getCveIdSubdelegacion() != null){
			Subdelegacion subdelegacion = new Subdelegacion();
			subdelegacion.setId(usuario.getCveIdSubdelegacion());
			solicitud.setSubdelegacion(subdelegacion);
		}
		
		if(solicitud.getSolicitudId() != null && solicitud.getSolicitudId().longValue() > 0){
			solicitudBusiness.actualizarTramites(solicitud);
		}else{
			solicitud = this.solicitudBusiness.crear(solicitud);
		}
			TramiteCorreccionCurp tramiteInicial = correccionDatosAseguradoEntity.almacenarTramiteCDA(solicitud.getTramites().get(0).getTramiteId(), tramite.getPersonaRENAPO().getCurp());
			log.debug("Se creo el TramiteCorreccionCurp  para el curp "+ tramiteInicial.getPersonaRENAPO().getCurp());
		
		
//		TODO asignar persona en el momento de la captura de nss
		solicitud.setPersonaInteresadaSolicitud(new PersonaInteresadaSolicitud());
		solicitud.getPersonaInteresadaSolicitud().setPersona(new Persona());
//		solicitud.getPersonaInteresadaSolicitud().getPersona().setIdPersona(solicitante.getIdPersona());
		solicitud.getPersonaInteresadaSolicitud().setTipoPersonaInteresadaSol(new TipoPerInteresadaSol());
		solicitud.getPersonaInteresadaSolicitud().getTipoPersonaInteresadaSol()
			.setCveTipoInteresadaSol(TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.getId());
		
		//TODO revisar cuando asociar persona al tramite 
		tramite.setPersona(solicitante);
		
		
		return solicitud;
	}
    
    @Override
    public void guardarSolicitudActualizacionDatos(Solicitud solicitud, TramiteCorreccionCurp tramite,List<DocumentoProbatorio> documentos, boolean documentosProbatorios, Long origen) throws Exception{
		
    	log.debug("Iniciando Creacion de documentos ");
    	
    	if(documentosProbatorios && documentos != null && !documentos.isEmpty()) {
			
			log.debug("---CDA--- procesando documentos probatorios del tramite: {} ", tramite.getTramiteId());
			
			try {
				documentoProbatorioServiceBusinessRemote.eliminarDocumentosTramite(tramite.getTramiteId());
			} catch(DocumentoProbatorioException ex) {
				log.error(">> CDA >> Error al borrar los documentos existentes", ex);
				throw new RegistrarDocumentoProbatorioException(ex);
			}
			documentoProbatorioServiceBusinessRemote.registrarDocumentos(documentos);
			//no requerimos que los doctos se asocien a la persona por lo tanto se le envia null al metodo 
			documentoProbatorioServiceBusinessRemote.procesaDocumentosGD(tramite.getTramiteId(), null, documentos);
			
			log.info("---CDA--- se finalizo la carga de archivos ");
		}
	
		log.info("---CDA--- se Actualizo solicitud {}",solicitud.getSolicitudId());
		//persistir tramite CDA
		
		log.info("---CDA--- Creando solicitud de CDA con el id del tramite {}",tramite.getTramiteId());
                TramiteCorreccionCurp correccionDatosAseg = correccionDatosAseguradoEntity.almacenarTramiteCDA(tramite.getTramiteId(),
                        tramite.getPersonaRENAPO() != null ? tramite.getPersonaRENAPO().getCurp(): null);
                if(tramite.getPersonaRENAPO() != null && tramite.getDatosLaborales() != null){
                    for(DatosLaborales datosLaborales : tramite.getDatosLaborales()){
                        log.info("---CDA--- Guarda los datos laborales del tramite principal {}", tramite.getTramiteId());
                        correccionDatosAseguradoEntity.almacenaDatosLaborales(correccionDatosAseg.getIdTramiteCorreccionDatosAseg(), datosLaborales);
                    }
                    if(tramite.getPersonas() != null && !tramite.getPersonas().isEmpty()
                            && tramite.getPersonas().get(0) != null && tramite.getPersonas().get(0).getDocumentosProbatorios() != null){
                            documentoProbatorioServiceBusinessRemote.procesaDocumentosGD(tramite.getTramiteId(), null, tramite.getPersonas().get(0).getDocumentosProbatorios());                            
                        }
                    
                }
		log.info("---CDA---se genero el id de la solicitud  {}",correccionDatosAseg.getIdTramiteCorreccionDatosAseg());
		log.info("---CDA--- iniciando el bloqueo del NSS");
		correccionDatosAseguradoEntity.bloquearNSS(tramite.getListaNSS().get(0), correccionDatosAseg.getIdTramiteCorreccionDatosAseg(), origen);
		log.info("---CDA--- Se bloque el nss {}",tramite.getListaNSS().get(0));
		//actualizar la subdelegacion
		correccionDatosAseguradoEntity.actualizarSubdelegacionSolicitud(solicitud);
		log.info("---CDA--- se actualizo la subdelegacion {} de la solicitud {}",solicitud.getSubdelegacion().getId(),solicitud.getSolicitudId());
		if(documentosProbatorios && documentos != null && !documentos.isEmpty()){
			tramite.setDocumentosProbatorios(documentos);
		}
		
	}      
    
    @Override
    public void actualizarSubdelegacionSolicitud(Solicitud solicitud){
    	correccionDatosAseguradoEntity.actualizarSubdelegacionSolicitud(solicitud);
    }
    
    @Override
	public Solicitud cancelarSolicitud(Solicitud solicitud) throws SolicitudNoEncontradaException, TramiteNoEncontradoException {

    	log.debug("---CDA--- cancelando solicitud ",solicitud.getSolicitudId());
		EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
		estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.CANCELADA.getCodigo());
		solicitud.setEstadoSolicitud(estadoSolicitud);		

		if(solicitud.getTramites() != null && !solicitud.getTramites().isEmpty() && solicitud.getTramites().get(0) != null && solicitud.getTramites().get(0).getTramiteId() != null ){
			log.debug("---CDA--- cancelando Tramite ",solicitud.getTramites().get(0).getTramiteId());
			EstadoTramite estadoTramite = new EstadoTramite();
			estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.CANCELADO.getCodigo());
			solicitud.getTramites().get(0).setEstadoTramite(estadoTramite);
			solicitud = this.solicitudBusiness.actualizarEstados(solicitud);
		}else{
			solicitud = new Solicitud();
		}
		
		return solicitud;
	}

	@Override
	public FirmaElectronica selloDigital(Fisica personaCorrecion, Solicitud solicitud) throws CorreccionDatosAseguradoException{
		FirmaElectronica firma = obtenerDatosSellado(personaCorrecion,solicitud);		
		return firma;
	}
	
	@Override
	public Fisica validarAsociacionCURPCorreo(Fisica fisica)throws CorreccionDatosAseguradoException{
		
		//buscar curp y correo 
		List<Fisica> personasIMSS = personaBusiness.buscarPersonaFisicaPorCurpEnImss(fisica.getCurp());
		Comparator<Fisica> comparadorPersona = getFisicaComparator();
		int position = -1;
		if(personasIMSS != null && !personasIMSS.isEmpty()){
		  position = Collections.binarySearch(personasIMSS, fisica, comparadorPersona);
		  if(position < 0){
		      position = 0;
		  }
		}
		if(position >= 0 ){
			log.debug("---CDA--- Persona encontrada en el indice: " + position + 
					", con id: " +  personasIMSS.get(position).getIdPersona());
			fisica.setIdPersona(personasIMSS.get(position).getIdPersona());
		}else{
			//si no hay persona asociada se crea la persona
			try {
				Fisica nueva = personaBusiness.altaPersonaFisica(fisica);
				fisica.setIdPersona(nueva.getIdPersona());
			} catch (DomicilioNoValidoException e) {
				log.error("---CDA--- Error al crear la persona fisica", e);
				throw new CorreccionDatosAseguradoException("Error durante la creacion de persona fisica");
			}
		}
		
		//TODO falta verificar que el correo no este en una persona diferente con un CURP distinto

		return fisica;
		
	}
	
	private Comparator<Fisica> getFisicaComparator(){
		Comparator<Fisica> comparator = new Comparator<Fisica>() {
			@Override
			public int compare(Fisica f1, Fisica f2) {
				boolean same = true; 
				same = same && f1.getCurp().equals(f2.getCurp());
				if(f1.getCorreoElectronico() != null && f1.getCorreoElectronico().getCorreo() != null 
				    && f2.getCorreoElectronico() != null && f2.getCorreoElectronico().getCorreo() != null){
				  log.debug("---CDA--- correo electronico captura:" + f1.getCorreoElectronico().getCorreo());
				  log.debug("---CDA--- correo electronico registrado:" + f2.getCorreoElectronico().getCorreo());
				  same = same && f1.getCorreoElectronico().getCorreo().equals(f2.getCorreoElectronico().getCorreo());
				}else{
				  same = false;
				}
				return same ? 0 : -1;
			}
		};
		
		return comparator;
	}
	
	private FirmaElectronica obtenerDatosSellado(Fisica personaCorrecion, Solicitud solicitud) throws CorreccionDatosAseguradoException {
		FirmaElectronica firmaElectronica = null;
		Locale locMEX = new Locale("es", "MX");
		Date fechaDelReporte = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss", locMEX);
		TramiteCorreccionCurp tramiteCDA = ((TramiteCorreccionCurp)solicitud.getTramites().get(0));
		StringBuffer sbCadenaOriginal = new StringBuffer();
		
		sbCadenaOriginal.append("||Invocante:portalimssdigital").append(StringEscapeUtils.unescapeHtml("|Tipo de tr&aacute;mite:"))
				.append(StringEscapeUtils.unescapeHtml("SOLICITUD DE REGULARIZACI&Oacute;N Y/O CORRECCI&Oacute;N DE DATOS PERSONALES DEL ASEGURADO"))
				.append("|Fecha:").append(sdf.format(fechaDelReporte))
				.append("|Folio:").append(solicitud.getNoFolioSolicitud())
				.append(StringEscapeUtils.unescapeHtml("|Delegaci&oacute;n:"))
		        .append(solicitud.getSubdelegacion().getDelegacion().getClave())
		        .append(StringEscapeUtils.unescapeHtml("-"))
		        .append(solicitud.getSubdelegacion().getDelegacion().getDescripcion())
		        .append(StringEscapeUtils.unescapeHtml("|Subdelegaci&oacute;n:"))
		        .append(solicitud.getSubdelegacion().getClave())
		        .append("-").append(solicitud.getSubdelegacion().getDescripcion())
				.append(StringEscapeUtils.unescapeHtml("|Nombre:"))
				.append(personaCorrecion.getNombreCompleto())
				.append("|CURP:").append(personaCorrecion.getCurp())
				.append(StringEscapeUtils.unescapeHtml("|N&uacute;mero de Seguridad Social:"))
				.append(tramiteCDA.getListaNSS()!= null && !tramiteCDA.getListaNSS().isEmpty() ? tramiteCDA.getListaNSS().get(0):"");
		sbCadenaOriginal.append("||");
		firmaElectronica = crearFirmaElectronica(sbCadenaOriginal); 
		
		if(firmaElectronica == null){
			throw new CorreccionDatosAseguradoException(ERROR_SELLADO);
		}
		
		return firmaElectronica;
	}
	
	@Override
	public Solicitud obtenerSolicitudPorEstado(Long idPersona, EstadoSolicitudEnum estadoSolicitud) throws SolicitudException  {
		return 	obtenerSolicitudAsignacionCommon(idPersona, estadoSolicitud);
	}
	
	@Override
	public Solicitud obtenerUltimaSolicitudSeguimientoCDA(List<String> curps, String origen){
		log.debug("---CDA---  CURPS : {}", curps);
		Long idTramite = correccionDatosAseguradoEntity.getIdTramiteActivo(curps, prepararEstadosValidos(origen));
		Solicitud solicitud = null;
		if(idTramite != null){
			 try {
				 if(idTramite > 0L) {
					 solicitud = solicitudBusiness.consultarPorIdTramite(idTramite);
					 log.debug("---CDA--- Solicitud {}", solicitud.getSolicitudId());
				 }else if(idTramite == 0L){
					 log.debug("---CDA--- Se encontro mas de un resultado");
					 solicitud = new Solicitud();
				 }
			} catch (SolicitudNoEncontradaException e) {
				log.error("---CDA--- Error al obtener la solicitud para las curp: " + curps + " y idTramite:"+ idTramite, e);
				return null;
			}
		}
		
		return solicitud;
	}
	
	@Override
	public Solicitud obtenerUltimaSolicitudRegistradaPorCurp(List<String> curps, List<Integer> estados) {
		Long idTramite = correccionDatosAseguradoEntity.getIdTramiteActivo(curps, estados);
		Solicitud solicitud = null; 
		
		if (idTramite != null) {
			try  {
				solicitud = solicitudBusiness.consultarPorIdTramite(idTramite);
			} catch(SolicitudNoEncontradaException e) {
				log.error(" -- CDA -- No existe una solicitud registrada");
			}
		}
		
		return solicitud;
	}
	
	private List<Integer>prepararEstadosValidos(String origen){
		List<Integer> estadosValidos = new ArrayList<Integer>(Arrays.asList(EstadoTramiteEnum.ACTIVO.getCodigo(),
				EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE.getCodigo(), 
				EstadoTramiteEnum.EN_ESPERA_AUTORIZACION.getCodigo(), 
				EstadoTramiteEnum.EN_ESPERA_TRAMITADOR.getCodigo(),
				EstadoTramiteEnum.RECHAZADO.getCodigo(), 
				EstadoTramiteEnum.INICIADO.getCodigo(),
				EstadoTramiteEnum.ENVIA_CERTIFICACION_SINDO.getCodigo(),
				EstadoTramiteEnum.ERROR_SINDO.getCodigo(),
				EstadoTramiteEnum.PROCESADO_SINDO.getCodigo(),
				EstadoTramiteEnum.SIN_RESPONSABLE.getCodigo()
				));
		
		if(origen.equalsIgnoreCase(ORIGEN_SOLICITUD_INTERNET)){
			estadosValidos.add(EstadoTramiteEnum.BAJA_IMPROCEDENCIA.getCodigo());
			estadosValidos.add(EstadoTramiteEnum.CERRADO.getCodigo());
		}
		
		return estadosValidos;
		
	}
	
	private Solicitud obtenerSolicitudAsignacionCommon(Long idPersona, EstadoSolicitudEnum estadoSolicitud) throws SolicitudException {
		
		Solicitud solicitud = this.solicitudBusiness
				.obtenerSolicitudDePersonaPorTipoSolicitudTramiteyEstado(idPersona, TipoPersonaEnum.FISICA,
						TipoSolicitudEnum.CORRECCION_DATOS_ASEGURADO, TipoTramiteEnum.CORRECCION_DATOS_ASEGURADO, estadoSolicitud);
		
		return solicitud;
	}
	
	/**
	 * Metodo encargado de realizar las acciones correspondientes al finalizar el registro de una solicitud
	 * asocia la solicitud a un responsable y cambia el estado de la solicitud en proceso
	 * @param solicitud
         * @param listaInicioTramite
         * @param usuario
         * @param origen
	 * @throws Exception 
	 */
	@Override
	public void finalizaRegistroCorreccionDatosAsegurados(Solicitud solicitud, List<InicioTramite> listaInicioTramite, String usuario, Long origen) 
                throws Exception{
		
		if(solicitud == null || solicitud.getSolicitudId() == null){
			throw new CorreccionDatosAseguradoException("La solicitud es nula o no tiene id");
		}
		
		if(solicitud.getTramites() == null || solicitud.getTramites().isEmpty() || solicitud.getTramites().get(0) == null
				|| solicitud.getTramites().get(0).getTramiteId() == null){
			throw new CorreccionDatosAseguradoException("La solicitud no tiene tramites asociados o el id es null");
		}
		
                // Se aplica solo para el primer tramite
		TramiteCorreccionCurp tramite = (TramiteCorreccionCurp)solicitud.getTramites().get(0);
		
		if(tramite.getPersonaRENAPO().getDomicilios() == null || tramite.getPersonaRENAPO().getDomicilios().isEmpty()){
			throw new CorreccionDatosAseguradoException("La solicitud no tiene domicilio capturado");
		}
                
		Usuario usuarioSolicitud = new Usuario();
		usuarioSolicitud.setCveIdUsuario(listaInicioTramite.get(0).getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()));
		usuarioSolicitud.setUsuario(listaInicioTramite.get(0).getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()));
		solicitud.setSolicitante(usuarioSolicitud);
		solicitudBusiness.actualizarUsuarioSolicitud(solicitud);	
                
                int i = 0;
                for(mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite t : solicitud.getTramites()) {                      
                    TramiteCorreccionCurp tramiteCDA = (TramiteCorreccionCurp) t;    
                    List<mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio> listDocumetosProb = tramiteCDA.getDocumentosProbatorios();
                    tramiteCDA.setDocumentosProbatorios(null);
                    
                    guardarSolicitudActualizacionDatos(agregarObservacionesSubdelegacion(solicitud,listaInicioTramite.get(i), usuario, tramiteCDA, i), tramiteCDA, listDocumetosProb, true, origen);
                    tramiteCDA.setDocumentosProbatorios(listDocumetosProb);
                    log.debug("---CDA--- LOS DOCUMENTOS DESPUES DE : {}", tramiteCDA.getDocumentosProbatorios());
                    solicitud.getTramites().get(i).setEstadoTramite(asignarEstadoTramite(listaInicioTramite.get(i)));			
                    
                    if(i == 0) {
                        
                        
                        EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
                        estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo());
                        solicitud.setEstadoSolicitud(estadoSolicitud);		
                        this.solicitudBusiness.actualizarEstados(solicitud);
                     
                    }
                    this.solicitudBusiness.actualizaTramite(solicitud, tramiteCDA);
                    
                    try {
			Long idTarea = responsableTareaBusiness.iniciarWorkFlow(
					ProcesosNegocioEnum.CDA.getId(),
					listaInicioTramite.get(i),
					generarSolicitudResponsable(
							listaInicioTramite.get(i).getParticipantes(),
							solicitud.getSolicitudId()));
			log.debug("El id de inicio tarea es {} ", idTarea.toString());
                    } catch (TareaInicialException e) {
			log.error("---CDA--- Error al iniciar el workflow",e);
			throw new CorreccionDatosAseguradoException();
                    } catch (TereaSinUsuarioAsignadoException e) {
			log.error("---CDA--- Error al iniciar el workflow",e);
			throw new CorreccionDatosAseguradoException();
                    }
                    
                    i++;
                }
                
	}
	
	private Solicitud generarSolicitudResponsable(Map <String,String> participantes, Long idSolicitud){
		
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(idSolicitud);
		Usuario usuario = new Usuario();
		usuario.setCveIdUsuario(participantes.get(ParticipantesEnum.RESPONSABLE.getDescripcion()));
		usuario.setUsuario(participantes.get(ParticipantesEnum.RESPONSABLE.getDescripcion()));
		solicitud.setSolicitante(usuario);
		
		
		return solicitud;
		
	}
	
	private Solicitud agregarObservacionesSubdelegacion(Solicitud solicitud, InicioTramite inicioTramite, String usuario, TramiteCorreccionCurp tcc, int pos){		
		TramiteCorreccionCurp tramiteCorreccionCurp = tcc;
    	tramiteCorreccionCurp.setObservacionesSubdelegacion(new ArrayList<ObservacionesSubdelegacion>());
    	ObservacionesSubdelegacion observacionesSubdelegacion = new ObservacionesSubdelegacion();    	
    	observacionesSubdelegacion.setCveEstado(StringUtils.isBlank(inicioTramite.getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()))?EstadoTramiteEnum.SIN_RESPONSABLE.getCodigo():EstadoTramiteEnum.EN_ESPERA_TRAMITADOR.getCodigo());
    	Date fecha = correccionDatosAseguradoUtilityLocal.convertirStringToDateMask(inicioTramite.getFechaSolicitud(),"");    	
    	observacionesSubdelegacion.setFechaActualizacion(fecha);
		observacionesSubdelegacion.setAsignado(StringUtils.isBlank(inicioTramite.getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()))?"":inicioTramite.getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()));
    	observacionesSubdelegacion.setUsuario(StringUtils.isNotBlank(usuario)?usuario:USUARIO_INTERNET_BITACORA);
    	tramiteCorreccionCurp.getObservacionesSubdelegacion().add(observacionesSubdelegacion);
    	log.info("Agregando observaciones al tramite {} ",tramiteCorreccionCurp.getTramiteId());
    	solicitud.getTramites().set(pos, tramiteCorreccionCurp);
    	return solicitud;
    }
	
	private EstadoTramite asignarEstadoTramite(InicioTramite inicioTramite){
		EstadoTramite estadoTramite = new EstadoTramite();
		estadoTramite.setIdEstadoTramitePersona(StringUtils.isBlank(inicioTramite.getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()))?EstadoTramiteEnum.SIN_RESPONSABLE.getCodigo():EstadoTramiteEnum.EN_ESPERA_TRAMITADOR.getCodigo());
		estadoTramite.setDescripcion(StringUtils.isBlank(inicioTramite.getParticipantes().get(ParticipantesEnum.RESPONSABLE.getDescripcion()))?EstadoTramiteEnum.SIN_RESPONSABLE.getDescripcion():EstadoTramiteEnum.EN_ESPERA_TRAMITADOR.getDescripcion());		
		return estadoTramite;
	}	
	
    /**
     * Metodo encargado de ralizar las acciones correspondientes al finalisar el tr&aacute;mite de correccion de datos asegurado
     * actualiza los datos estadisticos del asegurado
     * finaliza la solicitud e impacta los datos del tramite
     * @param tramiteActualizacion
     * @return
     * @throws IllegalArgumentException
     * @throws SolicitudNoValidaException
     * @throws SolicitudNoEncontradaException
     * @throws TramiteNoEncontradoException
     * @throws Exception
     */
	@Override
	public Solicitud finalizaTramiteCorreccionDatosBasicosAsegurado(
			Solicitud sol,String idTarea, String usuario) throws 
			IllegalArgumentException, SolicitudNoValidaException, SolicitudNoEncontradaException, TramiteNoEncontradoException,
			Exception{
		
		TramiteCorreccionCurp tramite = (TramiteCorreccionCurp)sol.getTramites().get(0);
		Solicitud solicitudCorreccion = finalizaTramiteSolicitudCorreccionDatosBasicosAsegurado(sol, idTarea, usuario);
		//seteo de estado de tramite y solicitud
		 EstadoTramite estadoTramite = new EstadoTramite();
		 estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());
		 estadoTramite.setDescripcion(EstadoNegocioEnum.obtenerDescripcionNegocio(EstadoTramiteEnum.CERRADO.getCodigo()));
		 
		 tramite.setEstadoTramite(estadoTramite);
		 
		 List <Tramite> lstTramiteActual = new ArrayList<Tramite>();
		 lstTramiteActual.add(tramite);
		 solicitudCorreccion.getTramites().clear();
		 solicitudCorreccion.setTramites(lstTramiteActual);
		 EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
		 estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.ATENDIDA.getCodigo());
		 solicitudCorreccion.setEstadoSolicitud(estadoSolicitud);
		 
		 
		 if(tramite.getObservacionesSubdelegacion()== null){				 
			 tramite.setObservacionesSubdelegacion(new ArrayList<ObservacionesSubdelegacion>());
		 }		 
	     ObservacionesSubdelegacion obSubdelegacion = new ObservacionesSubdelegacion ();
	     obSubdelegacion.setFechaActualizacion(new Date());
	     obSubdelegacion.setUsuario(usuario);
	     obSubdelegacion.setAsignado(correccionDatosAseguradoEntity.obtenerResponsableTramiteCDA(solicitudCorreccion.getSolicitudId()));
	     obSubdelegacion.setCveEstado(estadoTramite.getIdEstadoTramitePersona());
		 tramite.getObservacionesSubdelegacion().add(obSubdelegacion);
		 
		 
		solicitudBusiness.actualizarXmlTramite(tramite);
		solicitudBusiness.actualizarEstados(solicitudCorreccion);		
		responsableTareaBusiness.autorizarSolicitud(solicitudCorreccion, idTarea, usuario);
		
		return solicitudCorreccion;
		
	}
	private Solicitud finalizaTramiteSolicitudCorreccionDatosBasicosAsegurado(
			Solicitud solicitudCorreccion,String idTarea, String usuario) throws 
			IllegalArgumentException, SolicitudNoValidaException, SolicitudNoEncontradaException, TramiteNoEncontradoException,
			Exception{
		TramiteCorreccionCurp tramite = (TramiteCorreccionCurp)solicitudCorreccion.getTramites().get(0);
		String strNSS = tramite.getListaNSS().get(0);
	
		Fisica fisicaNSS = personaFisicaServiceBusiness.localizarPersonaFisicaPorNss(strNSS);
		Fisica fisicaActualizar = tramite.getPersonaRENAPO();
		fisicaActualizar.setIdPersona(fisicaNSS.getIdPersona());
		fisicaActualizar.setNss(fisicaNSS.getNss());
		
		//se califica a la pesona como renapo
		calificacionesPersonaBusinessService.calificarRENAPO(fisicaActualizar);
		//se actualiza la persona
		tramite.setPersona(fisicaActualizar);
		personaBusiness.actualizarPersona(fisicaActualizar);
		
		//seteo de la relacion de tramite persona fisica y persona interesada solicitud
		 solicitudBusiness.agregarPersonaATramite(tramite.getTramiteId(), fisicaActualizar.getIdPersona());
		 if(solicitudCorreccion.getPersonaInteresadaSolicitud() == null){
			 solicitudBusiness.insertaPersonaInteresadaSolicitud(solicitudCorreccion.getSolicitudId(),
					 fisicaActualizar.getIdPersona(), new Long(TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.getId()));
		 }			
		 Map<String, String> firma = firmaDigitalBusinessRemote.getCadenaOriginalYSelloDigital(solicitudCorreccion, fisicaActualizar, null, fisicaActualizar.getNss());
			
			if(firma != null) {
				String cadenaOriginal = (String) firma.get("cadenaOriginal");
				String sellodigital = (String)firma.get("selloDigital");
				String secuenciaNot = (String)firma.get("tramite");
				String numeroSerie = (String)firma.get("numeroSerie");
				
				log.debug("La secuencia de notaria generada es : " + secuenciaNot);
				FirmaElectronica firmaElectronica = new FirmaElectronica();
				
		        firmaElectronica.setCadenaOriginal(cadenaOriginal);
		        firmaElectronica.setReciboNotarial(secuenciaNot);
		        firmaElectronica.setSecuenciaNotaria(secuenciaNot);
		        firmaElectronica.setSerialCertificado(numeroSerie);
		        firmaElectronica.setRecibo(sellodigital);
		        
		        solicitudCorreccion.setFirmaElectronica(firmaElectronica);
				
		        solicitudCorreccion.setCadenaOriginal(cadenaOriginal);
		        solicitudCorreccion.setSecuenciaDeNotaria(secuenciaNot);
		        solicitudCorreccion.setSelloDigital(sellodigital);
		        solicitudCorreccion.setNumeroSerieCertificado(numeroSerie);
				
				firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitudCorreccion,firmaElectronica);
			}		
		
		return solicitudCorreccion;
	}

	@Override
	public Map<String, Object> personaAutorizadaRegistroCDA(String curp,List<String>curpsHitoricos){
		//buscar fisica asociada a la CURP		
		Map<String, Object> result = null;
		
		List<Fisica> personasIMSS = new ArrayList<Fisica>(); 
		
		personasIMSS.addAll(obtenerRolesPersonasAutorizadaRegistroCDA(curp));
		
		if(curpsHitoricos != null && !curpsHitoricos.isEmpty()){
			for (String curpAutorizada : curpsHitoricos) {
				List<Fisica> personasAux = obtenerRolesPersonasAutorizadaRegistroCDA(curpAutorizada);
				if (personasAux != null) {
					personasIMSS
							.addAll(obtenerRolesPersonasAutorizadaRegistroCDA(curpAutorizada));
				}
			}
		}
		
		if(!personasIMSS.isEmpty() ){
			result = obtenerRoles(personasIMSS);			
		}else{
			log.debug("---CDA--- sin resultados para CURP en validacion de ROL Asegurado: {} , se permite el acceso", 
					curp);
		}
		return result;
	}
	
	private List<Fisica> obtenerRolesPersonasAutorizadaRegistroCDA(String curp){
				Fisica personaBusqueda = new Fisica();
				personaBusqueda.setCurp(curp);
				DatosSalidaPaginador<Fisica> resultado = null;
				try {
					resultado = personaBusiness.buscarPersonaFisicaEnIMSSyEE(personaBusqueda);
					log.debug("---CDA--- resultado de la bsuqueda por CURP:{}, total personas: {} ", 
							curp, resultado!= null ? resultado.getiTotalRecords() : "sin registros");
				} catch (NumeroMaximoResultadosSuperadoException e) {
					log.error("---CDA--- Error al buscar persona en IMSS Digital y EE", e);
				} catch (ClienteWebserviceSatRfcException e) {
					log.error("---CDA--- Error al buscar persona en IMSS Digital y EE", e);
				} catch (ClienteWebserviceRenapoCurpException e) {
					log.error("---CDA--- Error al buscar persona en IMSS Digital y EE", e);
				}
				return resultado != null ?resultado.getAaData():null;
		
	}
	
	private Map<String, Object> obtenerRoles(List<Fisica> personasFisicas){
		Map<String, Object> result = new HashMap<String, Object>();
		int i = 0;
		for (Fisica fisicaEncontrada : personasFisicas) {
			try {
				if(grupoFamiliarServiceRemote.esPatron(fisicaEncontrada.getIdPersona())){
					TipoPersona tipoPersona = new TipoPersona();
					tipoPersona.setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
					fisicaEncontrada.setTipoPersona(tipoPersona);
					try {
						List<SujetoObligado> patrones =  sujetoObligadoServiceBusiness.listarRegistrosPatronalesPorPersona(fisicaEncontrada);
						
						for (SujetoObligado sujetoObligado : patrones) {
							result.put("entry"+i, "Patr\u00f3n, RFC: " + 
								sujetoObligado.getFisica().getRfc() + ", NRP: " + sujetoObligado.getNumeroRegistroPatronal());
							i++;
						}
					} catch (GestionPatronalBusinessException e) {
						log.error("---CDA--- Error al consultar los Registros Patronales de la persona ", e);
					}
				}
				if(grupoFamiliarServiceRemote.esRepresentanteLegal(fisicaEncontrada.getIdPersona()) ||
						personaFisicaServiceBusiness.isSocio(fisicaEncontrada.getIdPersona()) ||
						personaFisicaServiceBusiness.isPersonaAutorizada(fisicaEncontrada.getIdPersona())){
					Fisica fisicaCompleta = personaBusiness.getPersonaFisica(fisicaEncontrada.getIdPersona());
					result.put("entry"+i, "Representante Legal, RFC: " + (fisicaCompleta.getRfc() != null ? fisicaCompleta.getRfc() : "" ));
					i++;
				}
					
			} catch (DerechohabientesBusinessException e) {
				log.error("---CDA--- Error al buscar persona en IMSS Digital y EE", e);
			} catch (Exception e) {
				log.error("---CDA--- Error al buscar persona en IMSS Digital y EE", e);
			}
		}
		return result;
	}

	@Override
	public void enviaCertificacionSINDO(Solicitud solicitud, String idTarea, String usuario) throws IllegalArgumentException,
			SolicitudNoValidaException, SolicitudNoEncontradaException, TramiteNoEncontradoException, Exception {
		
		TramiteCorreccionCurp tramite = (TramiteCorreccionCurp)solicitud.getTramites().get(0);
		String strNss = tramite.getListaNSS().get(0);
		//se esta utilizando la personaRENAPO para guardar los datos capturados por el solicitante
		Fisica fisciaTramite = tramite.getPersonaRENAPO();
		//se consultan los antecedentes para guardarlos en base de datos en el XML de como se encontraba la persona en los origenes

		List<Fisica> fisicasHitoricas = this.serviceBusiness.getAseguradoByNSSLegadosyBDTU(strNss, true);

		fisciaTramite.setNss(strNss);

		//se busca la persona de renapo para recuperar el acta si es que cuenta con una
		Fisica fisicaActualizar = null;
		if(fisciaTramite.getCurp() != null && StringUtils.isNotBlank(fisciaTramite.getCurp())){
			fisicaActualizar = this.localizarPersonaFisicaEnRENAPOServiceBusiness
				.localizarPersonaFisicaEnRENAPOxCURP(fisciaTramite.getCurp());
		}else{
			fisicaActualizar = fisciaTramite;
		}

		//se setea la informacion del tramite e historicos
		fisicaActualizar.setMediosContacto(fisciaTramite.getMediosContacto());
		fisicaActualizar.setNss(strNss);
		
		//No guarda el domicilio de la persona
		fisicaActualizar.setDomicilios(tramite.getPersonaRENAPO().getDomicilios());
		tramite.setPersonaRENAPO(fisicaActualizar);
		
		//eliminar caracteres especiales de las personas a persistir en el detalle tramite
		tramite.setPersonas(normalizarFisicasHistoricas(fisicasHitoricas));

		EstadoTramite estadoTramite = new EstadoTramite();
		estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.ENVIA_CERTIFICACION_SINDO.getCodigo());

		tramite.setEstadoTramite(estadoTramite);
		List <Tramite> lstTramiteActual = new ArrayList<Tramite>();
		lstTramiteActual.add(tramite);

		solicitud.getTramites().clear();
		solicitud.setTramites(lstTramiteActual);
		EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
		estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo());
		solicitud.setEstadoSolicitud(estadoSolicitud);
		
		if(tramite.getObservacionesSubdelegacion()== null){				 
			 tramite.setObservacionesSubdelegacion(new ArrayList<ObservacionesSubdelegacion>());
		 }		 
	     ObservacionesSubdelegacion obSubdelegacion = new ObservacionesSubdelegacion ();
	     obSubdelegacion.setFechaActualizacion(new Date());
	     obSubdelegacion.setUsuario(usuario);
	     obSubdelegacion.setAsignado(correccionDatosAseguradoEntity.obtenerResponsableTramiteCDA(solicitud.getSolicitudId()));
	     obSubdelegacion.setCveEstado(estadoTramite.getIdEstadoTramitePersona());
		 tramite.getObservacionesSubdelegacion().add(obSubdelegacion);
		 

		MovCorreccionesDatosAseguradoType movCorrecion = afectarDatosPersonaUtility
				.generarMovimientoActualizacionAseguradoSINDO(fisicaActualizar, solicitud.getNoFolioSolicitud(), ORIGEN_APLICACION_CDA);
	
		
		
		log.debug("Movimiento 06 generado -> " + movCorrecion);
		this.solicitudBusiness.actualizarXmlTramite(tramite);
		this.solicitudBusiness.actualizarEstados(solicitud);
//		Comentar esta linea en desarrollo para evitar el intento de encolar los mensajes.
		this.movimiento06Correccion.encolarMovimiento06CorrecconAsegurado(movCorrecion);
		responsableTareaBusiness.actualizarBdocInstanciaCertificacion(idTarea, EstadoNegocioEnum.obtenerDescripcionNegocio(EstadoTramiteEnum.ENVIA_CERTIFICACION_SINDO.getCodigo()),usuario);
		
		log.info("se actualizo registro con folio {}",ditBitFlujoArchSindoCDALocal.guardarBitacoraSINDOCDA(solicitud.getNoFolioSolicitud()).getRefFolioSolicitud());
		
}
	
	public boolean obtenerSolicitudInconclusa(Long idTramite){
		boolean solicitudInconclusaActiva = false;
		Solicitud solicitud = null;
		try {
			solicitud = solicitudBusiness.consultarPorIdTramite(idTramite);
		} catch (SolicitudNoEncontradaException e) {
			log.error("---CDA--- Error al obtener uns Solicitud inconclusa", e);
		}
		if(solicitud != null){
			solicitudInconclusaActiva = true;
		}
		
		return solicitudInconclusaActiva;
	}
	
	@Override
	public FirmaElectronica selloDigitalCertificacion(Fisica personaCorrecion, Solicitud solicitud, Set<List<PeriodoMovimientoAfiliatorio>> parametrosCuentas) throws CorreccionDatosAseguradoException{
		FirmaElectronica firma = obtenerDatosSelladoCertificacion(personaCorrecion,solicitud, parametrosCuentas);		
		return firma;
	}
	
	private FirmaElectronica obtenerDatosSelladoCertificacion(Fisica personaCorrecion, Solicitud solicitud, Set<List<mx.gob.imss.ctirss.delta.model.gestion.patronal.PeriodoMovimientoAfiliatorio>> parametrosCuentas) throws CorreccionDatosAseguradoException {
		FirmaElectronica firmaElectronica = null;
		Locale locMEX = new Locale("es", "MX");
		log.debug("---CDA--- Fecha Solicitud {}",solicitud.getFechaConclusion());
		Date fechaDelReporte = solicitud.getFechaConclusion() != null ? solicitud.getFechaConclusion() : new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss", locMEX);
		TramiteCorreccionCurp tramiteCDA = ((TramiteCorreccionCurp)solicitud.getTramites().get(0));
		StringBuffer sbCadenaOriginal = new StringBuffer();
		StringBuffer registrosPatronales = new StringBuffer();
		
		if(parametrosCuentas != null && !parametrosCuentas.isEmpty()){
			List<PeriodoMovimientoAfiliatorio> periodos = (List<PeriodoMovimientoAfiliatorio>)parametrosCuentas.toArray()[0];
			HashSet<String> nrpString = new HashSet<String>();
			if(periodos != null && !periodos.isEmpty()){
				for (PeriodoMovimientoAfiliatorio periodoMovimientoAfiliatorio: periodos) {
					nrpString.add(periodoMovimientoAfiliatorio.getNrp());
				}
					int j=0;
					
					for(String nrpLista : nrpString){
						registrosPatronales.append(nrpLista);
						log.debug("---CDA--- nrpLista {}",nrpLista);
						
						if(j < nrpString.size()-1){
							registrosPatronales.append(", "); 
							j++;
						}
						
					}
					
				
			}
		}
		
		sbCadenaOriginal.append("||Invocante:portalimssdigital").append(StringEscapeUtils.unescapeHtml("|Tipo de tr&aacute;mite:"))
		.append(StringEscapeUtils.unescapeHtml("SOLICITUD DE REGULARIZACI&Oacute;N Y/O CORRECCI&Oacute;N DE DATOS PERSONALES DEL ASEGURADO"))
		.append("|Fecha:").append(sdf.format(fechaDelReporte))
		.append("|Folio:").append(solicitud.getNoFolioSolicitud())
		.append(StringEscapeUtils.unescapeHtml("|Delegaci&oacute;n:"))
		.append(solicitud.getSubdelegacion().getDelegacion().getClave())
		.append(StringEscapeUtils.unescapeHtml("-"))
		.append(solicitud.getSubdelegacion().getDelegacion().getDescripcion())
		.append(StringEscapeUtils.unescapeHtml("|Subdelegaci&oacute;n:"))
		.append(solicitud.getSubdelegacion().getClave())
		.append("-").append(solicitud.getSubdelegacion().getDescripcion())
		.append(StringEscapeUtils.unescapeHtml("|Nombre:"))
		.append(personaCorrecion.getNombreCompleto())
		.append("|CURP:").append(personaCorrecion.getCurp())
		.append(StringEscapeUtils.unescapeHtml("|N&uacute;mero de Seguridad Social:"))
		.append(tramiteCDA.getListaNSS()!= null && !tramiteCDA.getListaNSS().isEmpty() ? tramiteCDA.getListaNSS().get(0):"")
		.append(StringEscapeUtils.unescapeHtml("|N&uacute;meros de Seguridad Social Involucrados:"))
		.append(tramiteCDA.getListaNSS()!= null && !tramiteCDA.getListaNSS().isEmpty() ? tramiteCDA.getListaNSS().get(0):"")
		.append("|Registros Patronales Involucrados: ")
		.append(registrosPatronales.toString());
		sbCadenaOriginal.append("||");
		
		firmaElectronica = crearFirmaElectronica(sbCadenaOriginal); 

		if(firmaElectronica == null){
			throw new CorreccionDatosAseguradoException(ERROR_SELLADO);
		}

		return firmaElectronica;
	}
	
	public EstadoTramite consultarEstadoTramiteById(Long idTramite){
		DicEstadoTramite estadoBd = correccionDatosAseguradoEntity.consultarEstadoTramiteById(idTramite);
		EstadoTramite estado = new EstadoTramite();
		estado.setIdEstadoTramitePersona(estadoBd.getCveIdEstadoTramite().intValue());
		return estado;
	}
	

	private FirmaElectronica crearFirmaElectronica(StringBuffer cadenaOriginal)throws CorreccionDatosAseguradoException {
		FirmaElectronica firmaElectronica = null;
		RespuestaFirmadoSimple selloDigital = firmaDigitalBusinessRemote.getSelloDigital(cadenaOriginal.toString(),null, null);
		if (selloDigital != null) {
			if (StringUtils.isBlank(selloDigital.getSello())) {
				throw new CorreccionDatosAseguradoException(ERROR_SELLADO);
			}
			// Se crea el objeto de firma digital
			firmaElectronica = new FirmaElectronica();
			firmaElectronica.setCadenaOriginal(cadenaOriginal.toString());
			firmaElectronica.setReciboNotarial(selloDigital.getTramite());
			firmaElectronica.setSecuenciaNotaria(selloDigital.getTramite());
			firmaElectronica.setSerialCertificado(selloDigital.getNoSerie());
			firmaElectronica.setRecibo(selloDigital.getSello());
			firmaElectronica.setUrlAcuseFirma("");
			firmaElectronica.setIniciaVigenciaCertificado(new Date());
			firmaElectronica.setFinVigenciaCertificado(new Date());
		} else {
			throw new CorreccionDatosAseguradoException(ERROR_SELLADO);
		}
		return firmaElectronica;
	}
	
	public boolean isNSSBloqueado(String nss){
		boolean bloqueado = false;
		if(correccionDatosAseguradoEntity.consultarBloqueoNSS(nss) != null){
			bloqueado = true;
		}
		return bloqueado;
	}

	public void bloquearNSS(String nss, Long IdTramite)throws CorreccionDatosAseguradoException{
		
	}
	
	public void desbloquearNSS(String nss, Long idTramite)throws CorreccionDatosAseguradoException{
		
	}

	@Override
	public void agregarObservacionesSubdelegacion(Long idTramite,String usuario, String idTarea) throws SolicitudNoEncontradaException, TramiteNoEncontradoException, IllegalArgumentException {
		
		Solicitud sol = solicitudBusiness.consultarPorIdTramite(idTramite);
		
		log.debug("---CDA--- Responsable {}",correccionDatosAseguradoEntity.obtenerResponsableTramiteCDA(sol.getSolicitudId()));
		log.debug("---CDA--- Autorizador {}",usuario);
		
		if(sol != null){
			TramiteCorreccionCurp tramite = (TramiteCorreccionCurp)sol.getTramites().get(0);
			if(tramite.getObservacionesSubdelegacion()== null){				 
				 tramite.setObservacionesSubdelegacion(new ArrayList<ObservacionesSubdelegacion>());
			 }		 
		     ObservacionesSubdelegacion obSubdelegacion = new ObservacionesSubdelegacion ();			 
		     obSubdelegacion.setFechaActualizacion(tramite.getFechaRegistroActualizacion());	     
		     obSubdelegacion.setUsuario(usuario);
		     obSubdelegacion.setAsignado(correccionDatosAseguradoEntity.obtenerResponsableTramiteCDA(sol.getSolicitudId()));
		     obSubdelegacion.setCveEstado(tramite.getEstadoTramite().getIdEstadoTramitePersona());
		     obSubdelegacion.setDetalle(sol.getObservacion());
		     
		     if(!validarObservaciones(tramite.getObservacionesSubdelegacion(),obSubdelegacion)){
		    	 tramite.getObservacionesSubdelegacion().add(obSubdelegacion);
		    	 solicitudBusiness.actualizarXmlTramite(tramite);
		     
			 try {
				responsableTareaBusiness.actualizarEstadoBdocInstancia(idTarea, EstadoNegocioEnum.obtenerDescripcionNegocio(tramite.getEstadoTramite().getIdEstadoTramitePersona()),tramite.getFechaRegistroActualizacion());
			} catch (NoExisteTareaUsuarioException e) {
				log.error("Error al Actualizar el Estado {}",e);
			} catch (EstadoTareaUsuarioNoValidoException e) {
				log.error("Error al Actualizar estado invalido {}",e);
			}
			}
		}		
	}
	
	
	private boolean validarObservaciones(List<ObservacionesSubdelegacion> observaciones, final ObservacionesSubdelegacion observacion){
		return CollectionUtils.exists(observaciones, new Predicate() {
			   @Override	
			   public boolean evaluate(Object o1) {
				   return CompareToBuilder.reflectionCompare(o1, observacion) ==0;
				   }
				});
	}
	
	private List<Fisica> normalizarFisicasHistoricas(List<Fisica> fisicasHistoricas){
		if(fisicasHistoricas != null){
			for (Fisica fisica : fisicasHistoricas) {
				if(fisica.getNombre() != null){
					fisica.setNombre(normalizarCadenas(fisica.getNombre()).replace("\u001A", "#"));
				}
				if(fisica.getPrimerApellido() != null){
					fisica.setPrimerApellido(normalizarCadenas(fisica.getPrimerApellido()).replace("\u001A", "#"));
				}
				if(fisica.getSegundoApellido() != null){
					fisica.setSegundoApellido(normalizarCadenas(fisica.getSegundoApellido()).replace("\u001A", "#"));
				}
			}
		}
		
		return fisicasHistoricas;
	}
	
	private String normalizarCadenas(Object cadena){
		if(cadena != null){
			return pattern.matcher(Normalizer.normalize(cadena.toString(),
				Normalizer.Form.NFD)).replaceAll("");
		}
		return "";
	}
	
}