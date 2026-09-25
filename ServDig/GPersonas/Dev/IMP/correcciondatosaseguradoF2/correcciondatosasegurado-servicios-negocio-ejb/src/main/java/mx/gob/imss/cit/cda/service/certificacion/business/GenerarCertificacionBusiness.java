package mx.gob.imss.cit.cda.service.certificacion.business;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.certificacion.entity.CertificacionLocal;
import mx.gob.imss.cit.cda.service.certificacion.utility.interfaces.GenerarCertificacionUtilityLocal;
import mx.gob.imss.cit.cda.service.common.business.OperacionesSolicitudBusiness;
import mx.gob.imss.cit.cda.service.entity.CorreccionDatosAseguradoLocal;
import mx.gob.imss.cit.cda.service.interfaces.GenerarCertificacionRemote;
import mx.gob.imss.cit.cda.service.interfaces.ResponsableTareaRemote;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.BPMException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.CorreccionDatosAseguradoException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.NssRelacionadoVariasPersonasException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.CalificacionesPersonaBusinessServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.DetalleNssCda;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaInteresadaSolEnum;
import mx.gob.imss.ctirss.delta.model.firma.RespuestaFirmadoSimple;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.PeriodoMovimientoAfiliatorio;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = "generarCertificacionBusiness", mappedName = "generarCertificacionBusiness")
public class GenerarCertificacionBusiness extends OperacionesSolicitudBusiness implements GenerarCertificacionRemote {

    private final Logger log = LoggerFactory.getLogger(GenerarCertificacionBusiness.class);

    @EJB
    private CorreccionDatosAseguradoLocal correccionDatosAseguradoEntity;

    @EJB(name = "personaFisicaServiceBusiness", mappedName = "personaFisicaServiceBusiness")
    private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;

    @EJB(name = "calificacionesPersonaBusinessService", mappedName = "calificacionesPersonaBusinessService")
    private CalificacionesPersonaBusinessServiceRemote calificacionesPersonaBusinessService;

    @EJB(name = "personaBusiness", mappedName = "personaBusiness")
    private PersonaBusinessRemote personaBusiness;

    @EJB(name = "firmaDigitalBusiness", mappedName = "firmaDigitalBusiness")
    private FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;

    @EJB(mappedName = "responsableTareaBusiness", name = "responsableTareaBusiness")
    private ResponsableTareaRemote responsableTareaBusiness;

    @EJB
    private GenerarCertificacionUtilityLocal generarCertificacionUtility;
    
    @EJB
    private CertificacionLocal certificacionEntity;

    private static final String ERROR_SELLADO = "Ocurri\u00F3 un error al intentar sellar el documento.";

    @Override
    public Solicitud finalizaTramiteCorreccionDatosBasicosAsegurado(Solicitud sol, Map<String, String> tareas, 
            String usuario, TramiteCorreccionCurp tramite, String nssCertificador)
            throws SolicitudNoEncontradaException, TramiteNoEncontradoException, BPMException, NssRelacionadoVariasPersonasException,
            PersonaSinCalificacionesException, PersonaNoEncontradaException, CorreccionDatosAseguradoException {

        log.debug("Entra para finalizar la accion");
        Solicitud solicitudCorreccion = finalizaTramiteSolicitudCorreccionDatosBasicosAsegurado(sol, tramite, nssCertificador);
        // seteo de estado de tramite y solicitud
        generarCertificacionUtility.actualizaEstadosSolicitudCertificacion(solicitudCorreccion, usuario,
                correccionDatosAseguradoEntity.obtenerResponsableTramiteCDA(solicitudCorreccion.getSolicitudId()));

        getSolicitudBusiness().actualizarEstados(solicitudCorreccion);
        
        for (Tramite tramiteNuevo : solicitudCorreccion.getTramites()) {
            TramiteCorreccionCurp tramiteCda = (TramiteCorreccionCurp) tramiteNuevo;
            log.debug("-- CDA -- Actualiza la bitacora {} ", tramiteCda.getObservacionesSubdelegacion().size());
            getSolicitudBusiness().actualizarXmlTramite(tramiteCda);            
        }

        Iterator<Map.Entry<String, String>> entries = tareas.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            String idTarea = entry.getValue();
            responsableTareaBusiness.autorizarSolicitud(solicitudCorreccion,
                idTarea, usuario);
        }
        
        log.debug("Sale de autorizar solicitud");
        return solicitudCorreccion;
    }

    private Solicitud finalizaTramiteSolicitudCorreccionDatosBasicosAsegurado(
            Solicitud solicitudCorreccion, TramiteCorreccionCurp tramite, String nssCertificador)
            throws SolicitudNoEncontradaException, TramiteNoEncontradoException, NssRelacionadoVariasPersonasException,
            PersonaSinCalificacionesException, PersonaNoEncontradaException, CorreccionDatosAseguradoException {

        //Correccion de fix se cambia la manera en que se obtiene el NSS para consultar los datos de la persona
        Fisica fisicaNSS;
        try{
            log.debug("Busca persona por NSS {}", nssCertificador);
            fisicaNSS = personaFisicaServiceBusiness.localizarPersonaFisicaPorNssCertificacion(nssCertificador);
        } catch (PersonasNoLocalizadasException ex) {
            throw new CorreccionDatosAseguradoException("No es posible obtener la certificaci\u00F3n debido a que el NSS " + nssCertificador
                    + " a\u00FAn no se encuentra sincronizado en BDTU. Favor de reintentar el siguiente d\u00EDa h\u00E1bil; si el error persiste notificar mediante el servicio de incidencias.");
        }

        Fisica fisicaActualizar = tramite.getPersonaRENAPO();
        fisicaActualizar.setIdPersona(fisicaNSS.getIdPersona());
        fisicaActualizar.setNss(fisicaNSS.getNss());
        
        log.debug("Va a calificar renapo");
        // se califica a la pesona como renapo
        calificacionesPersonaBusinessService.calificarRENAPO(fisicaActualizar);
        // se actualiza la persona
        tramite.setPersonaRENAPO(fisicaActualizar);
        log.debug("Actualiza persona");
        personaBusiness.actualizarPersona(fisicaActualizar);

        // seteo de la relacion de tramite persona fisica y persona interesada
        // solicitud
        getSolicitudBusiness().agregarPersonaATramite(tramite.getTramiteId(),
                fisicaActualizar.getIdPersona());
        if (solicitudCorreccion.getPersonaInteresadaSolicitud() == null) {
            getSolicitudBusiness().insertaPersonaInteresadaSolicitud(
                    solicitudCorreccion.getSolicitudId(),
                    fisicaActualizar.getIdPersona(),
                    TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.getId());
        }

        return solicitudCorreccion;
    }

    @Override
    public FirmaElectronica selloDigitalCertificacion(Fisica personaCorreccion, Solicitud solicitud,
            Set<List<PeriodoMovimientoAfiliatorio>> parametrosCuentas, TramiteCorreccionCurp tramite) throws CorreccionDatosAseguradoException {

        return obtenerDatosSelladoCertificacion(personaCorreccion, solicitud, parametrosCuentas, tramite);
    }

    private FirmaElectronica obtenerDatosSelladoCertificacion(Fisica personaCorreccion, Solicitud solicitud,
            Set<List<mx.gob.imss.ctirss.delta.model.gestion.patronal.PeriodoMovimientoAfiliatorio>> parametrosCuentas,
            TramiteCorreccionCurp tramiteCDA) throws CorreccionDatosAseguradoException {
        FirmaElectronica firmaElectronica;
        Locale locMEX = new Locale("es", "MX");
        log.debug("---CDA--- Fecha Solicitud {}",
                solicitud.getFechaConclusion());
        Date fechaDelReporte = solicitud.getFechaConclusion() != null ? solicitud
                .getFechaConclusion() : new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(
                "dd 'de' MMMM yyyy, HH:mm:ss", locMEX);
        StringBuffer sbCadenaOriginal = new StringBuffer();
        StringBuffer registrosPatronales = obtenerRegistrosPatronales(parametrosCuentas);
        StringBuffer nssInvolucrados = obtenerNssInvolucrados(tramiteCDA);
        log.debug("---CDA--- Nss involucrados  {}",
                nssInvolucrados.toString());
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
                .append(personaCorreccion.getNombreCompleto())
                .append("|CURP:")
                .append(personaCorreccion.getCurp())
                .append(StringEscapeUtils
                        .unescapeHtml("|N&uacute;mero de Seguridad Social:"))
                .append(tramiteCDA.getListaNSS() != null
                        && !tramiteCDA.getListaNSS().isEmpty() ? tramiteCDA
                        .getListaNSS().get(0) : "")
                .append(StringEscapeUtils
                        .unescapeHtml("|N&uacute;meros de Seguridad Social Involucrados:"))
                .append(nssInvolucrados.toString())
                .append("|Registros Patronales Involucrados: ")
                .append(registrosPatronales.toString());
        sbCadenaOriginal.append("||");

        firmaElectronica = crearFirmaElectronica(sbCadenaOriginal);

        return firmaElectronica;
    }

    @SuppressWarnings("unchecked")
    private StringBuffer obtenerRegistrosPatronales(
            Set<List<mx.gob.imss.ctirss.delta.model.gestion.patronal.PeriodoMovimientoAfiliatorio>> parametrosCuentas) {
        StringBuffer registrosPatronales = new StringBuffer();
        if (parametrosCuentas != null && !parametrosCuentas.isEmpty()) {
            log.debug("-- CDA -- -- CDA -- Actualiza los registros patronales");
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

    private StringBuffer obtenerNssInvolucrados(TramiteCorreccionCurp tramiteCorreccionCurp) {

        StringBuffer nssInvolucrados = new StringBuffer();
        int i = 0;
        for (String nss : tramiteCorreccionCurp.getListaNSS()) {
            nssInvolucrados.append(nss);
            log.debug("-- CDA -- Recorre los nss");
            if (i < tramiteCorreccionCurp.getListaNSS().size() - 1) {
                nssInvolucrados.append(", ");
                i++;
            }
        }
        return nssInvolucrados;
    }

    private FirmaElectronica crearFirmaElectronica(StringBuffer cadenaOriginal)
            throws CorreccionDatosAseguradoException {
        FirmaElectronica firmaElectronica;
        log.debug("-- CDA -- Va a crear la firma");
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
    public void solicitarFirmaDigital(Solicitud solicitudCorreccion, Fisica fisica) {
        Map<String, String> firma = firmaDigitalBusinessRemote
                .getCadenaOriginalYSelloDigital(solicitudCorreccion,
                        fisica, null, fisica.getNss());
        log.debug("-- CDA -- Firma digital {} ", firma.toString());
        if (!firma.isEmpty()) {
            log.debug("-- CDA -- Entra a firmar");
            FirmaElectronica firmaElectronica = generarCertificacionUtility.armarFirma(firma);
            solicitudCorreccion.setFirmaElectronica(firmaElectronica);
            log.debug("-- CDA -- Sale de firmar");
            firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(
                    generarCertificacionUtility.
                            datosFirmaSolicitud(firma, solicitudCorreccion),
                    firmaElectronica);
            log.debug("-- CDA -- Sale de insertar");
        }
    }

    public List<DetalleNssCda> obtenerListaNSS(Long idTramite) {

        return certificacionEntity.obtenerListaNSS(idTramite);
    }
}
