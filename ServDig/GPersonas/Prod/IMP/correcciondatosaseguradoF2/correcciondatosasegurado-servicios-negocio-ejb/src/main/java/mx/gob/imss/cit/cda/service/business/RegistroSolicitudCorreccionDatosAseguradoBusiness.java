/**
 * 
 */
package mx.gob.imss.cit.cda.service.business;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.entity.CorreccionDatosAseguradoLocal;
import mx.gob.imss.cit.cda.service.entity.DetalleNssCdaLocal;
import mx.gob.imss.cit.cda.service.entity.DocumentoCdaLocal;
import mx.gob.imss.cit.cda.service.interfaces.RegistroSolicitudCorreccionDatosAseguradoRemote;
import mx.gob.imss.cit.cda.service.interfaces.ResponsableTareaRemote;
import mx.gob.imss.cit.cda.service.utility.CorreccionDatosAseguradoUtilityLocal;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.BPMException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.EstadoTareaUsuarioNoValidoException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TareaInicialException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TereaSinUsuarioAsignadoException;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.InicioTramite;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.ParticipantesEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.ProcesosNegocioEnum;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.RegistrarDocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.CorreccionDatosAseguradoException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.NssRelacionadoVariasPersonasException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.NumeroMaximoResultadosSuperadoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.CalificacionesPersonaBusinessServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.DatosLaborales;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.MotivoAclaracion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.ObservacionesSubdelegacion;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoPerInteresadaSol;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.OrigenCapturaCDAEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDoctoOrigSolicitanteCDAEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaInteresadaSolEnum;
import mx.gob.imss.ctirss.delta.model.firma.RespuestaFirmadoSimple;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.nss.CorreccionNSS;
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
import mx.gob.imss.ctirss.delta.persistence.DitDetalleNss;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servicio para atender el registro de la solicitud de la Correci&oacute;n de
 * Datos del Asegurado.
 * 
 * @author STK
 * 
 */
@Stateless(name = "registroSolicitudCorreccionDatosAseguradoBusiness", mappedName = "registroSolicitudCorreccionDatosAseguradoBusiness")
public class RegistroSolicitudCorreccionDatosAseguradoBusiness implements
        RegistroSolicitudCorreccionDatosAseguradoRemote {

	
	
    @EJB(name = "solicitudBusiness", mappedName = "solicitudBusiness")
    private SolicitudBusinessRemote solicitudBusiness;

    @EJB(name = "firmaDigitalBusiness", mappedName = "firmaDigitalBusiness")
    private FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;

    @EJB(name = "personaFisicaServiceBusiness", mappedName = "personaFisicaServiceBusiness")
    private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;

    @EJB(name = "personaBusiness", mappedName = "personaBusiness")
    private PersonaBusinessRemote personaBusiness;

    @EJB(name = "documentoProbatorioServiceBusiness", mappedName = "documentoProbatorioServiceBusiness")
    private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;

    @EJB(name = "calificacionesPersonaBusinessService", mappedName = "calificacionesPersonaBusinessService")
    private CalificacionesPersonaBusinessServiceRemote calificacionesPersonaBusinessService;
    
    @EJB(mappedName = "grupoFamiliarService")
    private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;

    @EJB(mappedName = "responsableTareaBusiness", name = "responsableTareaBusiness")
    private ResponsableTareaRemote responsableTareaBusiness;

    @EJB
    private CorreccionDatosAseguradoLocal correccionDatosAseguradoEntity;
    
    @EJB
    private DocumentoCdaLocal documentoCdaLocal;

    @EJB
    private DetalleNssCdaLocal detalleNssCdaLocal;

    @EJB
    private CorreccionDatosAseguradoUtilityLocal correccionDatosAseguradoUtilityLocal;

    @EJB(name = "sujetoObligadoServiceBusiness", mappedName = "sujetoObligadoServiceBusiness")
    private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;

    private final Logger log = LoggerFactory
            .getLogger(RegistroSolicitudCorreccionDatosAseguradoBusiness.class);

    private static final String ERROR_SELLADO = "Ocurri\u00F3 un error al intentar sellar el documento.";

    private static final String ORIGEN_SOLICITUD_INTERNET = "INTERNET";
    private static final String ORIGEN_SOLICITUD_VENTANILLA = "VENTANILLA";

    private static final String USUARIO_INTERNET_BITACORA = "ASEGURADO";
    private static final Long NUMERO_SOLICITUD = 123456L;
    private static final String ERROR_IMSS_DIGITAL = "Error al buscar persona en IMSS Digital y EE";

    @Override
    public List<Solicitud> obtenerSolicitudesPorCurp(String curp)
            throws CorreccionDatosAseguradoException {
        List<Solicitud> solicitudes = new ArrayList<Solicitud>();

        Solicitud sol1 = new Solicitud(NUMERO_SOLICITUD);
        solicitudes.add(sol1);
        return solicitudes;
    }

    @Override
    public Solicitud crearTramiteCorreccionCurp(Fisica solicitante,
            OrigenSolicitudEnum origenSolicitud, Usuario usuario)
            throws SolicitudNoValidaException, SolicitudNoEncontradaException,
            TramiteNoEncontradoException {

        TipoTramiteEnum tipoTramiteEnum = TipoTramiteEnum.CORRECCION_DATOS_ASEGURADO;
        TipoSolicitudEnum tipoSolicitudInicial = TipoSolicitudEnum.CORRECCION_DATOS_ASEGURADO;

        TramiteCorreccionCurp tramite = new TramiteCorreccionCurp();
        tramite.setPersonaRENAPO(solicitante);

        Solicitud solicitud = solicitudBusiness.crearSolicitudInicialPorEnum(
                EstadoSolicitudEnum.REGISTRADA, tipoSolicitudInicial,
                origenSolicitud, usuario);
        Date fechaActual = new Date();

        solicitud.setFechaPresentacion(fechaActual);
        solicitud.setPersonaInteresada(solicitante);
        solicitud = solicitudBusiness.asociarTramiteSolicitudPorEnum(solicitud,
                tramite, tipoTramiteEnum, EstadoTramiteEnum.INICIADO);

        if (usuario != null && usuario.getCveIdSubdelegacion() != null) {
            Subdelegacion subdelegacion = new Subdelegacion();
            subdelegacion.setId(usuario.getCveIdSubdelegacion());
            solicitud.setSubdelegacion(subdelegacion);
        }

        if (solicitud.getSolicitudId() != null
                && solicitud.getSolicitudId().longValue() > 0) {
            solicitudBusiness.actualizarTramites(solicitud);
        } else {
            solicitud = this.solicitudBusiness.crear(solicitud);
        }
        TramiteCorreccionCurp tramiteInicial = correccionDatosAseguradoEntity
                .almacenarTramiteCDA(solicitud.getTramites().get(0)
                        .getTramiteId(), tramite.getPersonaRENAPO().getCurp());
        log.debug("Se creo el TramiteCorreccionCurp  para el curp "
                + tramiteInicial.getPersonaRENAPO().getCurp());

        // TODO asignar persona en el momento de la captura de nss
        solicitud
                .setPersonaInteresadaSolicitud(new PersonaInteresadaSolicitud());
        solicitud.getPersonaInteresadaSolicitud().setPersona(new Persona());
        solicitud.getPersonaInteresadaSolicitud().setTipoPersonaInteresadaSol(
                new TipoPerInteresadaSol());
        solicitud
                .getPersonaInteresadaSolicitud()
                .getTipoPersonaInteresadaSol()
                .setCveTipoInteresadaSol(
                        TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO
                                .getId());

        // TODO revisar cuando asociar persona al tramite
        tramite.setPersona(solicitante);

        return solicitud;
    }

    /**
     *
     * @param solicitud
     * @param tramite
     * @param documentos
     * @param documentosProbatorios
     * @param origen
     * @param refCurp
     * @throws TramiteNoEncontradoException
     * @throws RegistrarDocumentoProbatorioException
     * @throws DocumentoProbatorioException
     */
    @Override
    public void guardarSolicitudActualizacionDatos(Solicitud solicitud,
            TramiteCorreccionCurp tramite,
            List<DocumentoProbatorio> documentos,
            boolean documentosProbatorios, Long origen, String refCurp)
            throws TramiteNoEncontradoException,
            RegistrarDocumentoProbatorioException, DocumentoProbatorioException {

        log.debug("Iniciando Creacion de documentos ");

        log.info("---CDA--- Creando solicitud de CDA con el id del tramite {}",
                tramite.getTramiteId());
        TramiteCorreccionCurp correccionDatosAseg = correccionDatosAseguradoEntity
                .almacenarTramiteCDA(tramite.getTramiteId(), refCurp);
        
        //agregar documentos del Asegurado
        registrarDocumentosAsegurado(tramite, 
                correccionDatosAseg.getIdTramiteCorreccionDatosAseg());
        
        if (tramite.getPersonaRENAPO() != null) {
            registrarDatosLaborales(tramite,
                    correccionDatosAseg.getIdTramiteCorreccionDatosAseg());
            registrarDocumentosBeneficiario(tramite, correccionDatosAseg.getIdTramiteCorreccionDatosAseg());
            registrarMotivosAclaracion(tramite,
                    correccionDatosAseg.getIdTramiteCorreccionDatosAseg());
        }
        log.info("---CDA--- iniciando el bloqueo del NSS");
        for (Iterator<CorreccionNSS> iterator = tramite.getListaNssCorreccion().iterator(); iterator.hasNext();) {
            CorreccionNSS correccionNSS = (CorreccionNSS)iterator.next();
             DitDetalleNss ditDetalleNss =  correccionDatosAseguradoEntity.bloquearNSS(
                correccionNSS.getNss(),
                correccionDatosAseg.getIdTramiteCorreccionDatosAseg(), origen, null);
            log.info("---CDA--- Se bloque el nss {}",correccionNSS.getNss());
            
            log.info("---CDA--- se almacenan los documentos del nss");
            this.registrarDocumentosDetalleNss(tramite.getTramiteId(), ditDetalleNss,
                   correccionNSS);
            
        }
       
        // actualizar la subdelegacion
        correccionDatosAseguradoEntity
                .actualizarSubdelegacionSolicitud(solicitud);
        log.info(
                "---CDA--- se actualizo la subdelegacion {} de la solicitud {}",
                solicitud.getSubdelegacion().getId(),
                solicitud.getSolicitudId());
        if (documentosProbatorios && documentos != null
                && !documentos.isEmpty()) {
            tramite.setDocumentosProbatorios(documentos);
        }

    }

    
    
    private void registrarDocumentosDetalleNss( Long idTramite,
            DitDetalleNss ditDetalleNss, CorreccionNSS correccionNSS ) 
            throws TramiteNoEncontradoException,
            RegistrarDocumentoProbatorioException, DocumentoProbatorioException {
        
        log.info("---CDA--- registrando doctos del NSS ");
        if (correccionNSS.getDocumentosProbatorios() != null) {
            log.debug(
                        "---CDA--- doctos a registrar {} ", correccionNSS.getDocumentosProbatorios().size());
            log.debug(
                        "---CDA--- ingresa documentos del nss {} ", ditDetalleNss.getCveDetalleNss());
            for (DocumentoProbatorio documentoProbatorio : correccionNSS.getDocumentosProbatorios()) {
                documentoProbatorio = documentoProbatorioServiceBusinessRemote.registraDocumento(documentoProbatorio);

                log.info("---CDA--- ingresa el detalle de docto de CDA ");
                documentoCdaLocal.guardarDoctoNss(idTramite, documentoProbatorio,
                        ditDetalleNss.getCorreccionDatosAsegurado().getCveIdCorreccionDatosAsegurado(),
                        ditDetalleNss.getCveDetalleNss(),false);
            }
        }
            log.info("---CDA--- se finalizo la carga de archivos ");
    }

    private void registrarDocumentosBeneficiario(TramiteCorreccionCurp tramite,
            Long idTramiteCorreccionDatosAseg)
            throws DocumentoProbatorioException {
         if (tramite.getBeneficiario()!= null
                && tramite.getBeneficiario().getDocumentosProbatorios() != null) {
            
          
            log.debug(
                        "---CDA--- doctos a registrar del beneficiario {} ",
                        tramite.getBeneficiario().getDocumentosProbatorios().size());
            
            for (DocumentoProbatorio documentoProbatorio : tramite.getBeneficiario().getDocumentosProbatorios()) {
                documentoProbatorio = documentoProbatorioServiceBusinessRemote.registraDocumento(documentoProbatorio);

                documentoCdaLocal.guardarDocumentacionTramite(tramite.getTramiteId(), documentoProbatorio);
                log.info("---CDA--- ingresa el detalle de docto de beneficiario CDA ");
                documentoCdaLocal.guardarDoctoAseg(tramite.getTramiteId(), documentoProbatorio,
                        idTramiteCorreccionDatosAseg, 
                        TipoDoctoOrigSolicitanteCDAEnum.BENEFICIARIO.getClave(),false);
            }
        }else if (tramite.getRepresentante()!= null
                && tramite.getRepresentante().getDocumentosProbatorios() != null){
            log.debug("---CDA--- doctos a registrar del representante {} ",tramite.getRepresentante().getDocumentosProbatorios().size());
            for (DocumentoProbatorio documentoProbatorio : tramite.getRepresentante().getDocumentosProbatorios()) {
                documentoProbatorio = documentoProbatorioServiceBusinessRemote.registraDocumento(documentoProbatorio);
    
                log.info("---CDA--- ingresa el detalle de docto de beneficiario CDA ");
                documentoCdaLocal.guardarDoctoAseg(tramite.getTramiteId(), documentoProbatorio,
                        idTramiteCorreccionDatosAseg, 
                        TipoDoctoOrigSolicitanteCDAEnum.REPRESENTANTE_LEGAL.getClave(), false);
            }
        }
    }
    
    private void registrarDocumentosAsegurado(TramiteCorreccionCurp tramite, 
        Long idTramiteCorreccionDatosAseg)
            throws DocumentoProbatorioException {
        if (tramite.getAsegurado()!= null
                && tramite.getAsegurado().getDocumentosProbatorios() != null) {
            
          
            log.debug(
                        "---CDA--- doctos a registrar del asegurado {} ",
                        tramite.getAsegurado().getDocumentosProbatorios().size());
            
            for (DocumentoProbatorio documentoProbatorio : tramite.getAsegurado().getDocumentosProbatorios()) {
            	try {
                documentoProbatorio = documentoProbatorioServiceBusinessRemote.registraDocumento(documentoProbatorio);
                documentoCdaLocal.guardarDocumentacionTramite(tramite.getTramiteId(), documentoProbatorio);
                log.info("---CDA--- ingresa el detalle de docto de asegurado CDA ");
                documentoCdaLocal.guardarDoctoAseg(tramite.getTramiteId(), documentoProbatorio,
                        idTramiteCorreccionDatosAseg, 
                        TipoDoctoOrigSolicitanteCDAEnum.ASEGURADO.getClave(),false);
            	}
            	catch (Exception e) {
            		log.error(e.getMessage(), e);
            		throw new DocumentoProbatorioException();
            	}

            }
        }
    }

    private void registrarDatosLaborales(TramiteCorreccionCurp tramite,
            Long idCorreccion) {
        if (notEmpty(tramite.getDatosLaborales())) {
            for (DatosLaborales datosLaborales : tramite.getDatosLaborales()) {
                correccionDatosAseguradoEntity.almacenaDatosLaborales(
                        idCorreccion, datosLaborales);
            }
        }
    }

    private void registrarMotivosAclaracion(TramiteCorreccionCurp tramite,
            Long idCorreccion) {
        if (notEmpty(tramite.getMotivosAclaracion())) {
            for (MotivoAclaracion motivo : tramite.getMotivosAclaracion()) {
                correccionDatosAseguradoEntity.almacenaMotivos(idCorreccion,
                        motivo);
            }
        }
    }

    @Override
    public void actualizarSubdelegacionSolicitud(Solicitud solicitud) {
        correccionDatosAseguradoEntity
                .actualizarSubdelegacionSolicitud(solicitud);
    }

    @Override
    public Solicitud cancelarSolicitud(Solicitud solicitud)
            throws SolicitudNoEncontradaException, TramiteNoEncontradoException {

        log.debug("---CDA--- cancelando solicitud ", solicitud.getSolicitudId());
        EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
        estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.CANCELADA
                .getCodigo());
        solicitud.setEstadoSolicitud(estadoSolicitud);

        if (notEmpty(solicitud.getTramites())
                && solicitud.getTramites().get(0) != null
                && solicitud.getTramites().get(0).getTramiteId() != null) {
            log.debug("---CDA--- cancelando Tramite ", solicitud.getTramites()
                    .get(0).getTramiteId());
            EstadoTramite estadoTramite = new EstadoTramite();
            estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.CANCELADO
                    .getCodigo());
            for (Tramite tramite : solicitud.getTramites()) {
                tramite.setEstadoTramite(estadoTramite);
            }
            this.solicitudBusiness.actualizarEstados(solicitud);
        }
        return solicitud;
    }

    @Override
    public FirmaElectronica selloDigital(Fisica personaCorrecion,
            Solicitud solicitud) throws CorreccionDatosAseguradoException {
        return obtenerDatosSellado(personaCorrecion, solicitud);
    }

    @Override
    public Fisica validarAsociacionCURPCorreo(Fisica fisica)
            throws CorreccionDatosAseguradoException {

        // buscar curp y correo
        List<Fisica> personasIMSS = personaBusiness
                .buscarPersonaFisicaPorCurpEnImss(fisica.getCurp());
        Comparator<Fisica> comparadorPersona = getFisicaComparator();
        int position = -1;
        if (personasIMSS != null && !personasIMSS.isEmpty()) {
            position = Collections.binarySearch(personasIMSS, fisica,
                    comparadorPersona);
            if (position < 0) {
                position = 0;
            }
        }
        if (position >= 0) {
            log.debug("---CDA--- Persona encontrada en el indice: " + position
                    + ", con id: " + personasIMSS.get(position).getIdPersona());
            fisica.setIdPersona(personasIMSS.get(position).getIdPersona());
        } else {
            // si no hay persona asociada se crea la persona
            try {
                Fisica nueva = personaBusiness.altaPersonaFisica(fisica);
                fisica.setIdPersona(nueva.getIdPersona());
            } catch (DomicilioNoValidoException e) {
                log.error("---CDA--- Error al crear la persona fisica", e);
                throw new CorreccionDatosAseguradoException(
                        "Error durante la creacion de persona fisica");
            }
        }

        // TODO falta verificar que el correo no este en una persona diferente
        // con un CURP distinto

        return fisica;

    }

    private Comparator<Fisica> getFisicaComparator() {
        Comparator<Fisica> comparator = new Comparator<Fisica>() {
            @Override
            public int compare(Fisica f1, Fisica f2) {
                boolean same = true;
                same = same && f1.getCurp().equals(f2.getCurp());
                if (f1.getCorreoElectronico() != null
                        && f1.getCorreoElectronico().getCorreo() != null
                        && f2.getCorreoElectronico() != null
                        && f2.getCorreoElectronico().getCorreo() != null) {
                    log.debug("---CDA--- correo electronico captura:"
                            + f1.getCorreoElectronico().getCorreo());
                    log.debug("---CDA--- correo electronico registrado:"
                            + f2.getCorreoElectronico().getCorreo());
                    same = same
                            && f1.getCorreoElectronico()
                                    .getCorreo()
                                    .equals(f2.getCorreoElectronico()
                                            .getCorreo());
                } else {
                    same = false;
                }
                return same ? 0 : -1;
            }
        };

        return comparator;
    }

    private FirmaElectronica obtenerDatosSellado(Fisica personaCorrecion,
            Solicitud solicitud) throws CorreccionDatosAseguradoException {
        FirmaElectronica firmaElectronica = null;
        Locale locMEX = new Locale("es", "MX");
        Date fechaDelReporte = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(
                "dd 'de' MMMM yyyy, HH:mm:ss", locMEX);
        TramiteCorreccionCurp tramiteCDA = ((TramiteCorreccionCurp) solicitud
                .getTramites().get(0));
        StringBuffer sbCadenaOriginal = new StringBuffer();

        sbCadenaOriginal
                .append("||Invocante:portalimssdigital")
                .append(StringEscapeUtils
                        .unescapeHtml("|Tipo de tr&aacute;mite:"))
                .append(StringEscapeUtils
                        .unescapeHtml("SOLICITUD DE REGULARIZACI&Oacute;N Y/O CORRECCI&Oacute;N DE DATOS PERSONALES DEL ASEGURADO"))
                .append("|Fecha:")
                .append(sdf.format(fechaDelReporte))
                .append("|Folio:")
                .append(solicitud.getNoFolioSolicitud())
                .append(StringEscapeUtils.unescapeHtml("|Delegaci&oacute;n:"))
                .append(solicitud.getSubdelegacion().getDelegacion().getClave())
                .append(StringEscapeUtils.unescapeHtml("-"))
                .append(solicitud.getSubdelegacion().getDelegacion()
                        .getDescripcion())
                .append(StringEscapeUtils
                        .unescapeHtml("|Subdelegaci&oacute;n:"))
                .append(solicitud.getSubdelegacion().getClave())
                .append("-")
                .append(solicitud.getSubdelegacion().getDescripcion())
                .append(StringEscapeUtils.unescapeHtml("|Nombre:"))
                .append(personaCorrecion.getNombreCompleto())
                .append("|CURP:")
                .append(personaCorrecion.getCurp())
                .append(StringEscapeUtils
                        .unescapeHtml("|N&uacute;mero de Seguridad Social:"))
                .append(tramiteCDA.getListaNSS() != null
                        && !tramiteCDA.getListaNSS().isEmpty() ? tramiteCDA
                        .getListaNSS().get(0) : "");
        sbCadenaOriginal.append("||");
        firmaElectronica = crearFirmaElectronica(sbCadenaOriginal);

        if (firmaElectronica == null) {
            throw new CorreccionDatosAseguradoException(ERROR_SELLADO);
        }

        return firmaElectronica;
    }

    @Override
    public Solicitud obtenerSolicitudPorEstado(Long idPersona,
            EstadoSolicitudEnum estadoSolicitud) throws SolicitudException {
        return obtenerSolicitudAsignacionCommon(idPersona, estadoSolicitud);
    }

    @Override
    public Solicitud obtenerUltimaSolicitudSeguimientoCDA(List<String> curps,
            String origen) {
        log.debug("---CDA---  CURPS : {}", curps);
        Long idTramite = correccionDatosAseguradoEntity.getIdTramiteActivo(
                curps, prepararEstadosValidos(origen), origen);
        
        Solicitud solicitud = null;
        if (idTramite == null) {
        	List <Object> idTramiteValidacion = correccionDatosAseguradoEntity.ValidacionTramiteExiste(
                    curps);
        	log.debug("---CDA---  CURPS : {}", idTramiteValidacion.toString());
        	String convert;
        	for(Object idtram : idTramiteValidacion )
        	{
        		convert = String.valueOf(idtram);
        		idTramite = Long.parseLong(convert);
        	}        	
        }
        
        if (idTramite != null) {
            try {
                if (idTramite > 0L) {
                    solicitud = solicitudBusiness
                            .consultarPorIdTramite(idTramite);
                    log.debug("---CDA--- Solicitud {}",
                            solicitud.getSolicitudId());
                } else if (idTramite == 0L) {
                    log.debug("---CDA--- Se encontro mas de un resultado");
                    solicitud = new Solicitud();
                }
            } catch (SolicitudNoEncontradaException e) {
                log.error(
                        "---CDA--- Error al obtener la solicitud para las curp: "
                                + curps + " y idTramite:" + idTramite, e);
                return null;
            }
        }

        return solicitud;
    }

    public Solicitud obtenerUltimaSolicitudRegistradaPorCurp(
            List<String> curps, List<Integer> estados) {
        Long idTramite = correccionDatosAseguradoEntity.getIdTramiteActivo(
                curps, estados, "");
        Solicitud solicitud = null;

        if (idTramite != null) {
            try {
                solicitud = solicitudBusiness.consultarPorIdTramite(idTramite);
            } catch (SolicitudNoEncontradaException e) {
                log.error(" -- CDA -- No existe una solicitud registrada");
            }
        }

        return solicitud;
    }

    private List<Integer> prepararEstadosValidos(String origen) {
        List<Integer> estadosValidos = new ArrayList<Integer>(Arrays.asList(
                EstadoTramiteEnum.ACTIVO.getCodigo(),
                EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE.getCodigo(),
                EstadoTramiteEnum.EN_ESPERA_AUTORIZACION.getCodigo(),
                EstadoTramiteEnum.EN_ESPERA_TRAMITADOR.getCodigo(),
                EstadoTramiteEnum.RECHAZADO.getCodigo(),
                EstadoTramiteEnum.INICIADO.getCodigo(),
                EstadoTramiteEnum.ENVIA_CERTIFICACION_SINDO.getCodigo(),
                EstadoTramiteEnum.ERROR_SINDO.getCodigo(),
                EstadoTramiteEnum.PROCESADO_SINDO.getCodigo(),
                EstadoTramiteEnum.SIN_RESPONSABLE.getCodigo(),
                EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE_ATENDIDA.getCodigo())
        );

        if (origen.equalsIgnoreCase(ORIGEN_SOLICITUD_INTERNET)) {
            estadosValidos
                    .add(EstadoTramiteEnum.BAJA_IMPROCEDENCIA.getCodigo());
        //    estadosValidos.add(EstadoTramiteEnum.CERRADO.getCodigo());
        }
        	//Se retira estados validos 7 y 9 de origen ventanilla, ya que desde ventanilla se debe 
            //poder crear una nueva solicitud sin importar que sea menor a 40 dias de haberse cancelado
        /*    if (origen.equalsIgnoreCase(ORIGEN_SOLICITUD_VENTANILLA)) {
        //    estadosValidos.add(EstadoTramiteEnum.CERRADO.getCodigo());
            estadosValidos.add(EstadoTramiteEnum.CANCELADO.getCodigo());
            estadosValidos.add(EstadoTramiteEnum.BAJA_IMPROCEDENCIA.getCodigo());
        }  */

        return estadosValidos;

    }

    private Solicitud obtenerSolicitudAsignacionCommon(Long idPersona,
            EstadoSolicitudEnum estadoSolicitud) throws SolicitudException {

        Solicitud solicitud = this.solicitudBusiness
                .obtenerSolicitudDePersonaPorTipoSolicitudTramiteyEstado(
                        idPersona, TipoPersonaEnum.FISICA,
                        TipoSolicitudEnum.CORRECCION_DATOS_ASEGURADO,
                        TipoTramiteEnum.CORRECCION_DATOS_ASEGURADO,
                        estadoSolicitud);

        return solicitud;
    }

    /**
     * Metodo encargado de realizar las acciones correspondientes al finalizar
     * el registro de una solicitud asocia la solicitud a un responsable y
     * cambia el estado de la solicitud en proceso
     * 
     * @param solicitud
     * @param listaInicioTramite
     * @param usuario
     * @param origen
     * @throws mx.gob.imss.ctirss.delta.exception.gestion.asegurado.CorreccionDatosAseguradoException
     * @throws mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException
     * @throws mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException
     * @throws mx.gob.imss.ctirss.delta.exception.documento.probatorio.RegistrarDocumentoProbatorioException
     * @throws mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException
     */
    @Override
    public void finalizaRegistroCorreccionDatosAsegurados(Solicitud solicitud,
            List<InicioTramite> listaInicioTramite, String usuario, Long origen)
            throws CorreccionDatosAseguradoException,
            SolicitudNoEncontradaException, TramiteNoEncontradoException,
            RegistrarDocumentoProbatorioException, DocumentoProbatorioException {

        if (solicitud == null || solicitud.getSolicitudId() == null) {
            throw new CorreccionDatosAseguradoException(
                    "La solicitud es nula o no tiene id");
        }

        if (isEmpty(solicitud.getTramites())
                || solicitud.getTramites().get(0) == null
                || solicitud.getTramites().get(0).getTramiteId() == null) {
            throw new CorreccionDatosAseguradoException(
                    "La solicitud no tiene tramites asociados o el id es null");
        }

        // Se aplica solo para el primer tramite
        TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) solicitud
                .getTramites().get(0);

        if (isEmpty(tramite.getPersonaRENAPO().getDomicilios())) {
            throw new CorreccionDatosAseguradoException(
                    "La solicitud no tiene domicilio capturado");
        }

        //Correccion de fix para evitar la duplicacion de folios
        if(detalleNssCdaLocal.getCountDetalleNssByIdTramite(tramite.getTramiteId(), tramite.getListaNssCorreccion().get(0).getNss()) > 0) {
            return;
        }

        String refCurp = tramite.getPersonaRENAPO().getCurp();
        Usuario usuarioSolicitud = new Usuario();
        usuarioSolicitud.setCveIdUsuario(listaInicioTramite.get(0)
                .getParticipantes()
                .get(ParticipantesEnum.RESPONSABLE.getDescripcion()));
        usuarioSolicitud.setUsuario(listaInicioTramite.get(0)
                .getParticipantes()
                .get(ParticipantesEnum.RESPONSABLE.getDescripcion()));
        solicitud.setSolicitante(usuarioSolicitud);
                               
        solicitudBusiness.actualizarUsuarioSolicitud(solicitud);
        
        solicitud = obtenerNssAsociados(solicitud);

        guardaDatosTramites(solicitud, listaInicioTramite, usuario, origen,
                refCurp);

    }
    
    
    
    public Solicitud obtenerNssAsociados(Solicitud solicitud) throws SolicitudNoEncontradaException, TramiteNoEncontradoException {
        List<String> listaNssAsig = new ArrayList<String>();
        log.info("-CDA- CONSULTANDO NSS ASOCIADO......");
        TramiteCorreccionCurp tcda = null;
        if(solicitud.getTramites()!= null){
             tcda = (TramiteCorreccionCurp) solicitud.getTramites().get(0);
            if(tcda.getPersonaRENAPO() != null){
                listaNssAsig.addAll(correccionDatosAseguradoEntity.obtenerNssDitAsignacionPorCurp(tcda.getPersonaRENAPO().getCurp()));
            }
        }
        
        
        
        log.info("-CDA- NSS CORRECCION ......"+tcda.getListaNssCorreccion().size());
        
        log.info("-CDA- NSS ASOCIADO AL ASEGURADO......"+listaNssAsig.size());
        CorreccionNSS nssAutomatico = null;
        for(String nssAsig :listaNssAsig){
        	
        	if(!correccionDatosAseguradoUtilityLocal.existeNssSolictud(nssAsig, tcda.getListaNssCorreccion())){
        		nssAutomatico = new CorreccionNSS();
        		nssAutomatico.setNss(nssAsig.trim());
        		nssAutomatico.setOrigen(OrigenCapturaCDAEnum.SISTEMA.getClave());
        		
        		tcda.getListaNssCorreccion().add(nssAutomatico);
        	}
        	
        }      
        
        log.info("-CDA- LISTA FINAL NSS "+listaNssAsig.size());
        
        solicitud.getTramites().set(0, tcda);
        
        solicitudBusiness.actualizarXmlTramite(tcda);
        
        return solicitud;
        
    }


    private void guardaDatosTramites(Solicitud solicitud,
            List<InicioTramite> listaInicioTramite, String usuario,
            Long origen, String refCurp) throws TramiteNoEncontradoException,
            RegistrarDocumentoProbatorioException,
            DocumentoProbatorioException, SolicitudNoEncontradaException,
            CorreccionDatosAseguradoException {
        int i = 0;
        for (mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite t : solicitud
                .getTramites()) {
            TramiteCorreccionCurp tramiteCDA = (TramiteCorreccionCurp) t;
            List<mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio> listDocumetosProb = tramiteCDA
                    .getDocumentosProbatorios();
            tramiteCDA.setDocumentosProbatorios(null);

            guardarSolicitudActualizacionDatos(
                    agregarObservacionesSubdelegacion(solicitud,
                            listaInicioTramite.get(i), usuario, tramiteCDA, i),
                    tramiteCDA, listDocumetosProb, true, origen, refCurp);
            tramiteCDA.setDocumentosProbatorios(listDocumetosProb);
            log.debug("---CDA--- LOS DOCUMENTOS DESPUES DE : {}",
                    tramiteCDA.getDocumentosProbatorios());
            solicitud
                    .getTramites()
                    .get(i)
                    .setEstadoTramite(
                            asignarEstadoTramite(listaInicioTramite.get(i)));

            if (i == 0) {
                EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
                estadoSolicitud
                        .setIdEstadoSolicitud(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION
                                .getCodigo());
                solicitud.setEstadoSolicitud(estadoSolicitud);
                this.solicitudBusiness.actualizarEstados(solicitud);

            }
            this.solicitudBusiness.actualizaTramite(solicitud, tramiteCDA);

            try {
                Long idTarea = responsableTareaBusiness
                        .iniciarWorkFlow(
                                ProcesosNegocioEnum.CDA.getId(),
                                listaInicioTramite.get(i),
                                generarSolicitudResponsable(listaInicioTramite
                                        .get(i).getParticipantes(), solicitud
                                        .getSolicitudId()));
                log.debug("El id de inicio tarea es {} ", idTarea.toString());
            } catch (TareaInicialException e) {
                log.error("---CDA--- Error al iniciar el workflow", e);
                throw new CorreccionDatosAseguradoException();
            } catch (TereaSinUsuarioAsignadoException e) {
                log.error("---CDA--- Error al iniciar el workflow", e);
                throw new CorreccionDatosAseguradoException();
            }
            i++;
        }
    }

    private Solicitud generarSolicitudResponsable(
            Map<String, String> participantes, Long idSolicitud) {

        Solicitud solicitud = new Solicitud();
        solicitud.setSolicitudId(idSolicitud);
        Usuario usuario = new Usuario();
        usuario.setCveIdUsuario(participantes.get(ParticipantesEnum.RESPONSABLE
                .getDescripcion()));
        usuario.setUsuario(participantes.get(ParticipantesEnum.RESPONSABLE
                .getDescripcion()));
        solicitud.setSolicitante(usuario);

        return solicitud;

    }

    private Solicitud agregarObservacionesSubdelegacion(Solicitud solicitud,
            InicioTramite inicioTramite, String usuario,
            TramiteCorreccionCurp tcc, int pos) {
        TramiteCorreccionCurp tramiteCorreccionCurp = tcc;
        tramiteCorreccionCurp
                .setObservacionesSubdelegacion(new ArrayList<ObservacionesSubdelegacion>());
        ObservacionesSubdelegacion observacionesSubdelegacion = new ObservacionesSubdelegacion();
        observacionesSubdelegacion
                .setCveEstado(StringUtils.isBlank(inicioTramite
                        .getParticipantes().get(
                                ParticipantesEnum.RESPONSABLE.getDescripcion())) ? EstadoTramiteEnum.SIN_RESPONSABLE
                        .getCodigo() : EstadoTramiteEnum.EN_ESPERA_TRAMITADOR
                        .getCodigo());
        Date fecha = correccionDatosAseguradoUtilityLocal
                .convertirStringToDateMask(inicioTramite.getFechaSolicitud(),
                        "");
        observacionesSubdelegacion.setFechaActualizacion(fecha);
        observacionesSubdelegacion.setAsignado(StringUtils
                .isBlank(inicioTramite.getParticipantes().get(
                        ParticipantesEnum.RESPONSABLE.getDescripcion())) ? ""
                : inicioTramite.getParticipantes().get(
                        ParticipantesEnum.RESPONSABLE.getDescripcion()));
        observacionesSubdelegacion
                .setUsuario(StringUtils.isNotBlank(usuario) ? usuario
                        : USUARIO_INTERNET_BITACORA);
        tramiteCorreccionCurp.getObservacionesSubdelegacion().add(
                observacionesSubdelegacion);
        log.info("Agregando observaciones al tramite {} ",
                tramiteCorreccionCurp.getTramiteId());
        solicitud.getTramites().set(pos, tramiteCorreccionCurp);
        return solicitud;
    }

    private EstadoTramite asignarEstadoTramite(InicioTramite inicioTramite) {
        EstadoTramite estadoTramite = new EstadoTramite();
        estadoTramite
                .setIdEstadoTramitePersona(StringUtils.isBlank(inicioTramite
                        .getParticipantes().get(
                                ParticipantesEnum.RESPONSABLE.getDescripcion())) ? EstadoTramiteEnum.SIN_RESPONSABLE
                        .getCodigo() : EstadoTramiteEnum.EN_ESPERA_TRAMITADOR
                        .getCodigo());
        estadoTramite
                .setDescripcion(StringUtils.isBlank(inicioTramite
                        .getParticipantes().get(
                                ParticipantesEnum.RESPONSABLE.getDescripcion())) ? EstadoTramiteEnum.SIN_RESPONSABLE
                        .getDescripcion()
                        : EstadoTramiteEnum.EN_ESPERA_TRAMITADOR
                                .getDescripcion());
        return estadoTramite;
    }

    /**
     * Metodo encargado de realizar las acciones correspondientes al finalizar
     * el tr&aacute;mite de correccion de datos asegurado actualiza los datos
     * estadisticos del asegurado finaliza la solicitud e impacta los datos del
     * tramite
     * 
     * @param sol
     * @param idTarea
     * @param usuario
     * @return
     * @throws SolicitudNoValidaException
     * @throws SolicitudNoEncontradaException
     * @throws TramiteNoEncontradoException
     * @throws mx.gob.imss.cit.gestion.solicitud.flujo.exception.BPMException
     * @throws mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException
     * @throws mx.gob.imss.ctirss.delta.exception.gestion.asegurado.NssRelacionadoVariasPersonasException
     * @throws mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException
     * @throws mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException
     */
    @Override
    public Solicitud finalizaTramiteCorreccionDatosBasicosAsegurado(
            Solicitud sol, String idTarea, String usuario)
            throws SolicitudNoValidaException, SolicitudNoEncontradaException,
            TramiteNoEncontradoException, BPMException,
            PersonasNoLocalizadasException,
            NssRelacionadoVariasPersonasException,
            PersonaSinCalificacionesException, PersonaNoEncontradaException {

        TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) sol
                .getTramites().get(0);
        Solicitud solicitudCorreccion = finalizaTramiteSolicitudCorreccionDatosBasicosAsegurado(sol);
        // seteo de estado de tramite y solicitud
        EstadoTramite estadoTramite = new EstadoTramite();
        estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO
                .getCodigo());
        estadoTramite.setDescripcion(EstadoNegocioEnum
                .obtenerDescripcionNegocio(EstadoTramiteEnum.CERRADO
                        .getCodigo()));

        tramite.setEstadoTramite(estadoTramite);

        List<Tramite> lstTramiteActual = new ArrayList<Tramite>();
        lstTramiteActual.add(tramite);
        solicitudCorreccion.getTramites().clear();
        solicitudCorreccion.setTramites(lstTramiteActual);
        EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
        estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.ATENDIDA
                .getCodigo());
        solicitudCorreccion.setEstadoSolicitud(estadoSolicitud);

        if (tramite.getObservacionesSubdelegacion() == null) {
            tramite.setObservacionesSubdelegacion(new ArrayList<ObservacionesSubdelegacion>());
        }
        ObservacionesSubdelegacion obSubdelegacion = new ObservacionesSubdelegacion();
        obSubdelegacion.setFechaActualizacion(new Date());
        obSubdelegacion.setUsuario(usuario);
        obSubdelegacion.setAsignado(correccionDatosAseguradoEntity
                .obtenerResponsableTramiteCDA(solicitudCorreccion
                        .getSolicitudId()));
        obSubdelegacion.setCveEstado(estadoTramite.getIdEstadoTramitePersona());
        tramite.getObservacionesSubdelegacion().add(obSubdelegacion);

        solicitudBusiness.actualizarXmlTramite(tramite);
        solicitudBusiness.actualizarEstados(solicitudCorreccion);
        responsableTareaBusiness.autorizarSolicitud(solicitudCorreccion,
                idTarea, usuario);

        return solicitudCorreccion;

    }

    private Solicitud finalizaTramiteSolicitudCorreccionDatosBasicosAsegurado(
            Solicitud solicitudCorreccion) throws SolicitudNoValidaException,
            SolicitudNoEncontradaException, TramiteNoEncontradoException,
            PersonasNoLocalizadasException,
            NssRelacionadoVariasPersonasException,
            PersonaSinCalificacionesException, PersonaNoEncontradaException {
        TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) solicitudCorreccion
                .getTramites().get(0);
        String strNSS = tramite.getListaNSS().get(0);

        Fisica fisicaNSS = personaFisicaServiceBusiness
                .localizarPersonaFisicaPorNss(strNSS);
        Fisica fisicaActualizar = tramite.getPersonaRENAPO();
        fisicaActualizar.setIdPersona(fisicaNSS.getIdPersona());
        fisicaActualizar.setNss(fisicaNSS.getNss());

        // se califica a la pesona como renapo
        calificacionesPersonaBusinessService.calificarRENAPO(fisicaActualizar);
        // se actualiza la persona
        tramite.setPersona(fisicaActualizar);
        personaBusiness.actualizarPersona(fisicaActualizar);

        // seteo de la relacion de tramite persona fisica y persona interesada
        // solicitud
        solicitudBusiness.agregarPersonaATramite(tramite.getTramiteId(),
                fisicaActualizar.getIdPersona());
        if (solicitudCorreccion.getPersonaInteresadaSolicitud() == null) {
            solicitudBusiness.insertaPersonaInteresadaSolicitud(
                    solicitudCorreccion.getSolicitudId(),
                    fisicaActualizar.getIdPersona(),
                    TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.getId());
        }
        Map<String, String> firma = firmaDigitalBusinessRemote
                .getCadenaOriginalYSelloDigital(solicitudCorreccion,
                        fisicaActualizar, null, fisicaActualizar.getNss());

        if (firma != null) {
            String cadenaOriginal = (String) firma.get("cadenaOriginal");
            String sellodigital = (String) firma.get("selloDigital");
            String secuenciaNot = (String) firma.get("tramite");
            String numeroSerie = (String) firma.get("numeroSerie");

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

            firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(
                    solicitudCorreccion, firmaElectronica);
        }

        return solicitudCorreccion;
    }

    @Override
    public Map<String, Object> personaAutorizadaRegistroCDA(String curp,
            List<String> curpsHitoricos) {
        // buscar fisica asociada a la CURP
        Map<String, Object> result = null;

        List<Fisica> personasIMSS = new ArrayList<Fisica>();

        personasIMSS.addAll(obtenerRolesPersonasAutorizadaRegistroCDA(curp));

        if (curpsHitoricos != null && !curpsHitoricos.isEmpty()) {
            for (String curpAutorizada : curpsHitoricos) {
                List<Fisica> personasAux = obtenerRolesPersonasAutorizadaRegistroCDA(curpAutorizada);
                if (personasAux != null) {
                    personasIMSS
                            .addAll(obtenerRolesPersonasAutorizadaRegistroCDA(curpAutorizada));
                }
            }
        }

        if (!personasIMSS.isEmpty()) {
            result = obtenerRoles(personasIMSS);
        } else {
            log.debug(
                    "---CDA--- sin resultados para CURP en validacion de ROL Asegurado: {} , se permite el acceso",
                    curp);
        }
        return result;
    }

    private List<Fisica> obtenerRolesPersonasAutorizadaRegistroCDA(String curp) {
        Fisica personaBusqueda = new Fisica();
        personaBusqueda.setCurp(curp);
        DatosSalidaPaginador<Fisica> resultado = null;
        try {
            resultado = personaBusiness
                    .buscarPersonaFisicaEnIMSSyEE(personaBusqueda);
            log.debug(
                    "---CDA--- resultado de la bsuqueda por CURP:{}, total personas: {} ",
                    curp, resultado != null ? resultado.getiTotalRecords()
                            : "sin registros");
        } catch (NumeroMaximoResultadosSuperadoException e) {
            log.error(ERROR_IMSS_DIGITAL, e);
        } catch (ClienteWebserviceSatRfcException e) {
            log.error(ERROR_IMSS_DIGITAL, e);
        } catch (ClienteWebserviceRenapoCurpException e) {
            log.error(ERROR_IMSS_DIGITAL, e);
        }
        return resultado != null ? resultado.getAaData() : null;

    }

    private Map<String, Object> obtenerRoles(List<Fisica> personasFisicas) {
        Map<String, Object> result = new HashMap<String, Object>();
        int i = 0;
        for (Fisica fisicaEncontrada : personasFisicas) {
            try {
                if (grupoFamiliarServiceRemote.esPatron(fisicaEncontrada
                        .getIdPersona())) {
                    TipoPersona tipoPersona = new TipoPersona();
                    tipoPersona
                            .setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
                    fisicaEncontrada.setTipoPersona(tipoPersona);
                    try {
                        List<SujetoObligado> patrones = sujetoObligadoServiceBusiness
                                .listarRegistrosPatronalesPorPersona(fisicaEncontrada);

                        for (SujetoObligado sujetoObligado : patrones) {
                            result.put(
                                    "entry" + i,
                                    "Patr\u00f3n, RFC: "
                                            + sujetoObligado.getFisica()
                                                    .getRfc()
                                            + ", NRP: "
                                            + sujetoObligado
                                                    .getNumeroRegistroPatronal());
                            i++;
                        }
                    } catch (GestionPatronalBusinessException e) {
                        log.error(
                                "---CDA--- Error al consultar los Registros Patronales de la persona ",
                                e);
                    }
                }
                if (grupoFamiliarServiceRemote
                        .esRepresentanteLegal(fisicaEncontrada.getIdPersona())
                        || isTipoPersona(fisicaEncontrada)) {
                    Fisica fisicaCompleta = personaBusiness
                            .getPersonaFisica(fisicaEncontrada.getIdPersona());
                    result.put(
                            "entry" + i,
                            "Representante Legal, RFC: "
                                    + (fisicaCompleta.getRfc() != null ? fisicaCompleta
                                            .getRfc() : ""));
                    i++;
                }

            } catch (DerechohabientesBusinessException e) {
                log.error(ERROR_IMSS_DIGITAL, e);
            } catch (Exception e) {
                log.error(ERROR_IMSS_DIGITAL, e);
            }
        }
        return result;
    }

    private Boolean isTipoPersona(Fisica fisicaEncontrada) {
        return personaFisicaServiceBusiness.isSocio(fisicaEncontrada
                .getIdPersona())
                || personaFisicaServiceBusiness
                        .isPersonaAutorizada(fisicaEncontrada.getIdPersona());
    }



    public boolean obtenerSolicitudInconclusa(Long idTramite) {
        boolean solicitudInconclusaActiva = false;
        Solicitud solicitud = null;
        try {
            solicitud = solicitudBusiness.consultarPorIdTramite(idTramite);
        } catch (SolicitudNoEncontradaException e) {
            log.error("---CDA--- Error al obtener uns Solicitud inconclusa", e);
        }
        if (solicitud != null) {
            solicitudInconclusaActiva = true;
        }

        return solicitudInconclusaActiva;
    }

    @Override
    public FirmaElectronica selloDigitalCertificacion(Fisica personaCorrecion,
            Solicitud solicitud,
            Set<List<PeriodoMovimientoAfiliatorio>> parametrosCuentas)
            throws CorreccionDatosAseguradoException {
        return obtenerDatosSelladoCertificacion(personaCorrecion, solicitud,
                parametrosCuentas);
    }

    private FirmaElectronica obtenerDatosSelladoCertificacion(
            Fisica personaCorrecion,
            Solicitud solicitud,
            Set<List<mx.gob.imss.ctirss.delta.model.gestion.patronal.PeriodoMovimientoAfiliatorio>> parametrosCuentas)
            throws CorreccionDatosAseguradoException {
        FirmaElectronica firmaElectronica = null;
        Locale locMEX = new Locale("es", "MX");
        log.debug("---CDA--- Fecha Solicitud {}",
                solicitud.getFechaConclusion());
        Date fechaDelReporte = solicitud.getFechaConclusion() != null ? solicitud
                .getFechaConclusion() : new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(
                "dd 'de' MMMM yyyy, HH:mm:ss", locMEX);
        TramiteCorreccionCurp tramiteCDA = ((TramiteCorreccionCurp) solicitud
                .getTramites().get(0));
        StringBuffer sbCadenaOriginal = new StringBuffer();
        StringBuffer registrosPatronales = obtenerRegistrosPatronales(parametrosCuentas);

        sbCadenaOriginal
                .append("||Invocante:portalimssdigital")
                .append(StringEscapeUtils
                        .unescapeHtml("|Tipo de tr&aacute;mite:"))
                .append(StringEscapeUtils
                        .unescapeHtml("SOLICITUD DE REGULARIZACI&Oacute;N Y/O CORRECCI&Oacute;N DE DATOS PERSONALES DEL ASEGURADO"))
                .append("|Fecha:")
                .append(sdf.format(fechaDelReporte))
                .append("|Folio:")
                .append(solicitud.getNoFolioSolicitud())
                .append(StringEscapeUtils.unescapeHtml("|Delegaci&oacute;n:"))
                .append(solicitud.getSubdelegacion().getDelegacion().getClave())
                .append(StringEscapeUtils.unescapeHtml("-"))
                .append(solicitud.getSubdelegacion().getDelegacion()
                        .getDescripcion())
                .append(StringEscapeUtils
                        .unescapeHtml("|Subdelegaci&oacute;n:"))
                .append(solicitud.getSubdelegacion().getClave())
                .append("-")
                .append(solicitud.getSubdelegacion().getDescripcion())
                .append(StringEscapeUtils.unescapeHtml("|Nombre:"))
                .append(personaCorrecion.getNombreCompleto())
                .append("|CURP:")
                .append(personaCorrecion.getCurp())
                .append(StringEscapeUtils
                        .unescapeHtml("|N&uacute;mero de Seguridad Social:"))
                .append(tramiteCDA.getListaNSS() != null
                        && !tramiteCDA.getListaNSS().isEmpty() ? tramiteCDA
                        .getListaNSS().get(0) : "")
                .append(StringEscapeUtils
                        .unescapeHtml("|N&uacute;meros de Seguridad Social Involucrados:"))
                .append(tramiteCDA.getListaNSS() != null
                        && !tramiteCDA.getListaNSS().isEmpty() ? tramiteCDA
                        .getListaNSS().get(0) : "")
                .append("|Registros Patronales Involucrados: ")
                .append(registrosPatronales.toString());
        sbCadenaOriginal.append("||");

        firmaElectronica = crearFirmaElectronica(sbCadenaOriginal);

        if (firmaElectronica == null) {
            throw new CorreccionDatosAseguradoException(ERROR_SELLADO);
        }

        return firmaElectronica;
    }

    private StringBuffer obtenerRegistrosPatronales(
            Set<List<mx.gob.imss.ctirss.delta.model.gestion.patronal.PeriodoMovimientoAfiliatorio>> parametrosCuentas)
            throws CorreccionDatosAseguradoException {
        StringBuffer registrosPatronales = new StringBuffer();
        if (parametrosCuentas != null && !parametrosCuentas.isEmpty()) {
            List<PeriodoMovimientoAfiliatorio> periodos = (List<PeriodoMovimientoAfiliatorio>) parametrosCuentas
                    .toArray()[0];
            HashSet<String> nrpString = new HashSet<String>();
            if (periodos != null && !periodos.isEmpty()) {
                for (PeriodoMovimientoAfiliatorio periodoMovimientoAfiliatorio : periodos) {
                    nrpString.add(periodoMovimientoAfiliatorio.getNrp());
                }
                int j = 0;

                for (String nrpLista : nrpString) {
                    registrosPatronales.append(nrpLista);
                    log.debug("---CDA--- nrpLista {}", nrpLista);

                    if (j < nrpString.size() - 1) {
                        registrosPatronales.append(", ");
                        j++;
                    }
                }
            }
        }
        return registrosPatronales;
    }

    @Override
    public EstadoTramite consultarEstadoTramiteById(Long idTramite) {
        DicEstadoTramite estadoBd = correccionDatosAseguradoEntity
                .consultarEstadoTramiteById(idTramite);
        EstadoTramite estado = new EstadoTramite();
        estado.setIdEstadoTramitePersona(estadoBd.getCveIdEstadoTramite()
                .intValue());
        return estado;
    }

    private FirmaElectronica crearFirmaElectronica(StringBuffer cadenaOriginal)
            throws CorreccionDatosAseguradoException {
        FirmaElectronica firmaElectronica = null;
        RespuestaFirmadoSimple selloDigital = firmaDigitalBusinessRemote
                .getSelloDigital(cadenaOriginal.toString(), null, null);
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

    @Override
    public boolean isNSSBloqueado(String nss) {
        boolean bloqueado = false;
        if (correccionDatosAseguradoEntity.consultarBloqueoNSS(nss) != null) {
            bloqueado = true;
        }
        return bloqueado;
    }

    @Override
    public void bloquearNSS(String nss, Long idTramite)
            throws CorreccionDatosAseguradoException {

    }

    @Override
    public void desbloquearNSS(String nss, Long idTramite)
            throws CorreccionDatosAseguradoException {

    }

    @Override
    public void agregarObservacionesSubdelegacion(Long idTramite,
            String usuario, String idTarea)
            throws SolicitudNoEncontradaException, TramiteNoEncontradoException {

        Solicitud sol = solicitudBusiness.consultarPorIdTramite(idTramite);

        log.debug("---CDA--- Autorizador {}", usuario);

        if (sol != null) {
            TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) sol
                    .getTramites().get(0);
            if (tramite.getObservacionesSubdelegacion() == null) {
                tramite.setObservacionesSubdelegacion(new ArrayList<ObservacionesSubdelegacion>());
            }
            ObservacionesSubdelegacion obSubdelegacion = new ObservacionesSubdelegacion();
            obSubdelegacion.setFechaActualizacion(tramite
                    .getFechaRegistroActualizacion());
            obSubdelegacion.setUsuario(usuario);
            obSubdelegacion.setAsignado(correccionDatosAseguradoEntity
                    .obtenerResponsableTramiteCDA(sol.getSolicitudId()));
            obSubdelegacion.setCveEstado(tramite.getEstadoTramite()
                    .getIdEstadoTramitePersona());
            obSubdelegacion.setDetalle(sol.getObservacion());

            if (!validarObservaciones(tramite.getObservacionesSubdelegacion(),
                    obSubdelegacion)) {
                tramite.getObservacionesSubdelegacion().add(obSubdelegacion);
                solicitudBusiness.actualizarXmlTramite(tramite);

                try {
                    responsableTareaBusiness.actualizarEstadoBdocInstancia(
                            idTarea, EstadoNegocioEnum
                                    .obtenerDescripcionNegocio(tramite
                                            .getEstadoTramite()
                                            .getIdEstadoTramitePersona()),
                            tramite.getFechaRegistroActualizacion());
                } catch (NoExisteTareaUsuarioException e) {
                    log.error("Error al Actualizar el Estado {}", e);
                } catch (EstadoTareaUsuarioNoValidoException e) {
                    log.error("Error al Actualizar estado invalido {}", e);
                }
            }
        }
    }

    private boolean validarObservaciones(
            List<ObservacionesSubdelegacion> observaciones,
            final ObservacionesSubdelegacion observacion) {
        return CollectionUtils.exists(observaciones, new Predicate() {
            @Override
            public boolean evaluate(Object o1) {
                return CompareToBuilder.reflectionCompare(o1, observacion) == 0;
            }
        });
    }


    private static Boolean isEmpty(List lista) {
        return lista == null || lista.isEmpty();
    }

    private static Boolean notEmpty(List lista) {
        return lista != null && !lista.isEmpty();
    }
    
  

}
