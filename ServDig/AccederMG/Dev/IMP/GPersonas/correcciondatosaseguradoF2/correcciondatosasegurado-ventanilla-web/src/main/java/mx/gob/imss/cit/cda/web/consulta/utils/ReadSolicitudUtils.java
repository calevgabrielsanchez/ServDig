package mx.gob.imss.cit.cda.web.consulta.utils;

import java.lang.reflect.Field;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import mx.gob.imss.cit.cda.service.interfaces.ConsultaSolicitudRemote;
import mx.gob.imss.cit.cda.service.interfaces.RegistroSolicitudCorreccionDatosAseguradoRemote;
import mx.gob.imss.cit.cda.web.app.common.model.UserProfile;
import mx.gob.imss.cit.cda.web.app.common.model.enums.TipoSolicitanteEnum;
import mx.gob.imss.cit.cda.web.app.responsable.model.DetalleNssCda;
import mx.gob.imss.cit.cda.web.app.responsable.model.Documento;
import mx.gob.imss.cit.cda.web.app.responsable.model.DocumentosBeneficiario;
import mx.gob.imss.cit.cda.web.app.responsable.model.DocumentosNss;
import mx.gob.imss.cit.cda.web.app.responsable.model.DomicilioParticular;
import mx.gob.imss.cit.cda.web.app.responsable.model.HistoriaLaboral;
import mx.gob.imss.cit.cda.web.app.responsable.model.InformacionBeneficiario;
import mx.gob.imss.cit.cda.web.app.responsable.model.InformacionRENAPO;
import mx.gob.imss.cit.cda.web.app.responsable.model.MotivoAclaracion;
import mx.gob.imss.cit.cda.web.app.responsable.model.NSS;
import mx.gob.imss.cit.cda.web.app.responsable.model.Solicitud;
import mx.gob.imss.cit.cda.web.app.responsable.model.SubDelegacion;
import mx.gob.imss.cit.cda.web.app.responsable.model.TipoRegularizacion;
import mx.gob.imss.cit.cda.web.app.responsable.model.TipoRegularizacionNSS;
import mx.gob.imss.cit.cda.web.constants.TiposAclaracionEnum;
import mx.gob.imss.cit.cda.web.support.model.Page;
import mx.gob.imss.cit.cda.web.utils.DeltaUtils;
import mx.gob.imss.cit.cda.web.utils.TipoRegularizacionNSSUtil;
import mx.gob.imss.cit.cda.web.utils.TipoRegularizacionUtil;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.InicioTramite;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoRemote;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.framework.util.DateUtils;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ParametrosServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.DatosLaborales;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.ObservacionesSubdelegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.enums.OrigenCapturaCDAEnum;
import mx.gob.imss.ctirss.delta.model.enums.OrigenConsultaNssEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoProbatorioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CURP;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Identificador;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.nss.CorreccionNSS;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ReadSolicitudUtils {

    @Autowired
    @Qualifier("consultaSolicitudBusiness")
    private ConsultaSolicitudRemote consultaSolicitudBusiness;
    
    @Autowired
    @Qualifier("flujoTrabajoBusiness")
    private FlujoTrabajoRemote flujoTrabajoBusiness;

    @Autowired
    @Qualifier("registroSolicitudCorreccionDatosAseguradoBusiness")
    private RegistroSolicitudCorreccionDatosAseguradoRemote registroSolicitudCorreccionDatosAseguradoBusiness;

    @Autowired
    private ServiceBusinessRemote serviceBusiness;
    
    @Autowired
    @Qualifier("parametrosServiceBusiness")
    private ParametrosServiceBusinessRemote parametrosServiceBusiness;

    @Autowired
    @Qualifier("documentoProbatorioServiceBusiness")
    private DocumentoProbatorioServiceBusinessRemote deltaDocumentoProbatorioServiceBusiness;

    @Autowired
    private EstadosPantallaResponsableUtil estadosPantallaUtil;

    @Autowired
    private EstadosPantallaAutorizadorUtil estadosPantallaAutorizadorUtil;

    @Autowired
    private TipoRegularizacionUtil tipoRegularizacionUtil;

    @Autowired
    private TipoRegularizacionNSSUtil tipoRegularizacionNSSUtil;

    @Autowired
    private DeltaUtils deltaUtils;

    private static final String SOLICITUD_VENCIDA = " - VENCIDA";
    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

    
    public Solicitud converSolStep01(TramiteCorreccionCurp tramite,
      Solicitud solicitud, mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud sol  
      , NSS nss , String idDelegacion   ){
    
      if (tramite.getPersonaRENAPO() != null) {

                Fisica persona = tramite.getPersonaRENAPO();
                String parametroDelegacion = parametrosServiceBusiness.obtenerParametroDeDelegacionCDA(idDelegacion);
                log.debug("---CDA Ventanilla--- tiene domicilio de persona RENAPO?: {}, {}", persona.getCurp(), !persona.getDomicilios().isEmpty());
                
                StringTokenizer st = new StringTokenizer(parametroDelegacion,"|");
        		String cadena="";
        		while (st.hasMoreTokens())
        		{
        			cadena=st.nextToken();
        			
        			if(cadena.equalsIgnoreCase("PERTENECE_OTRO_ASEGURADO=FALSE")||cadena.equalsIgnoreCase("PERTENECE_OTRO_ASEGURADO=TRUE"))
        			{
        				solicitud.setBloqueoCorrespondeOtroAsegurado(cadena.equalsIgnoreCase("PERTENECE_OTRO_ASEGURADO=FALSE")?false:true);
        			}
        			if(cadena.equalsIgnoreCase("CUENTA_ILOGICA=FALSE")||cadena.equalsIgnoreCase("CUENTA_ILOGICA=TRUE"))
        			{
        				solicitud.setBloqueoCuentaIlogia(cadena.equalsIgnoreCase("CUENTA_ILOGICA=FALSE")?false:true);
        			}        			      			
           		}
                
                solicitud.setDomicilioParticular(armarDomicilio(persona.getDomicilios().get(0)));
                solicitud.setEstatus(tramite.getEstadoTramite().getDescripcion());
                solicitud.setFechaInicio(flujoTrabajoBusiness.obtenerTareaPorIdTramite(tramite.getTramiteId()).getInicioTramite().getFechaSolicitud());
                solicitud.setIdTramite(String.valueOf(tramite.getTramiteId()));
                solicitud.setObservacion(tramite.getObservacion() != null ? tramite.getObservacion().toUpperCase() : "");
                if (null != tramite.getObservacionesSubdelegacion() && !tramite.getObservacionesSubdelegacion().isEmpty()) {
                    solicitud.setObservacionSubdelegacion(formatearObservacionesSubdelegacion(tramite.getObservacionesSubdelegacion()));
                }
                /**
                 * Agregar roles en caso de tenerlos
                 */
                Map<String, Object> roles = registroSolicitudCorreccionDatosAseguradoBusiness.personaAutorizadaRegistroCDA(persona.getCurp(), persona.getCurpsHistoricas());
                solicitud.setObservacionSubdelegacion(getInfoRoles(solicitud, roles));
                log.debug("---CDA Ventanilla--- ROLES********* {}", roles);

                solicitud.setMotivoAclaracion(getMotivoAclaracionSolicitud(tramite.getMotivosAclaracion()));
                solicitud.setGridHistoriaLaboral(getGridHistoriaLaboral(tramite));

                /**
                 * Estados de mostrar pantallas, cambiar al ultimo, recorriendo
                 * todos los tramites y asignarle el predominante o el negativo,
                 * junto al del estado
                 */
                log.debug("---CDA--- Estado {}", tramite.getEstadoTramite()
                        .getDescripcion());
                solicitud.setEstadoAutorizacion(estadosPantallaAutorizadorUtil.obtenerEstadoAutorizador(tramite.getEstadoTramite().getIdEstadoTramitePersona(), sol.getEstadoSolicitud().getIdEstadoSolicitud()));
                solicitud.setEstadoResponsable(estadosPantallaUtil.obtenerEstadoResponsable(tramite.getEstadoTramite().getIdEstadoTramitePersona(), sol.getEstadoSolicitud().getIdEstadoSolicitud(), sol.getSolicitante() != null ? sol.getSolicitante().getUsuario() : null));
                solicitud.setIdEstadoSolicitud(tramite.getEstadoTramite().getIdEstadoTramitePersona().toString());
                solicitud.setDefuncion(tramite.isIndicadorDefuncion());

                //gridDocumentosBeneficiario
                if (tramite.getPersonas() != null) {
                    solicitud.setInformacionBeneficiario(getBeneficiario(tramite.getBeneficiario()));
                    solicitud.setGridDocumentosBeneficiario(getGridDocumentosBeneficiario(tramite, sol.getSolicitudId().toString(), sol.getNoFolioSolicitud()));
                    solicitud.setGridDocumentosBeneficiarioAdicionales(getGridDocumentosBeneficiarioAdicional(tramite, sol.getSolicitudId().toString(), sol.getNoFolioSolicitud()));
                }
                
                
                
                nss.setInformacionRENAPO(getInformacionRenapo(sol, persona));
                solicitud.setInformacionRENAPO(nss.getInformacionRENAPO());
                log.debug("--- INFORMACION RENAPO ********* {}", solicitud.getInformacionRENAPO().getCurp());

            }
    return solicitud;
    }
    
    public Solicitud convertSol(mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud sol, mx.gob.imss.cit.cda.web.app.common.model.UserProfile userProfile) throws DocumentoProbatorioException {
    	String estadov = null;
        log.debug(
                "---CDA Ventanilla--- inicia transofrmacion de solicitud: {}",
                sol.getNoFolioSolicitud());
        Solicitud solicitud = new Solicitud();

        List<Page<NSS>> gridsNSS = new ArrayList<Page<NSS>>();
        List<Page<Documento>> gridsDocumentosNss = new ArrayList<Page<Documento>>();
        List<Page<DocumentosNss>> gridsDocumentosNssOrigen = new ArrayList<Page<DocumentosNss>>();
        List<DocumentosNss> documentosNssAdicional = new ArrayList<DocumentosNss>();

        List<NSS> listNSS;
        List<String> listaNSS = new ArrayList<String>();
        NSS nss;

        TipoRegularizacion tipoRegSol = new TipoRegularizacion();

        for (Tramite tramitecda : sol.getTramites()) {

            TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) tramitecda;

            log.debug("---CDA Ventanilla--- tramite: {}", tramite.getTramiteId());

            listNSS = new ArrayList<NSS>();
            nss = armarNss(tramite, sol.getObservacion());

            /* El tramite principal tiene la persona renapo */
            solicitud = converSolStep01(tramite, solicitud, sol, nss, userProfile.getIdSubdelegacion().toString());

            tramite.setDocumentosProbatorios(obtenerDocumentosProbatorios(tramite.getTramiteId()));

            // Agrega cada NSS
            listNSS.add(nss);
            listaNSS.add(nss.getNss());
            gridsNSS.add(getGridNss(listNSS));
            //Con indInformacionAdicional = 0
            gridsDocumentosNss.add(getGridDocumentosProbatoriosNss(tramite.getDocumentosProbatorios(), sol));
            gridsDocumentosNssOrigen.add(getDocumentosProbatoriosNssOrigen(tramite, sol.getSolicitudId().toString(), sol.getNoFolioSolicitud()));
            //Con indInformacionAdicional = 1
            documentosNssAdicional.add(getDocumentosProbatoriosNssAdicionales(tramite, sol.getSolicitudId().toString(), sol.getNoFolioSolicitud()));

            solicitud.setGridNssSolicitud(getGridNssSolicitud(tramite.getListaNssCorreccion(), true));
            solicitud.setGridNssVentanilla(getGridNssSolicitud(tramite.getListaNssCorreccion(), false));
            //Documentos Asegurado
            solicitud.setGridDocumentos(getGridDocumentos(tramite.getAsegurado().getDocumentosProbatorios(), userProfile, sol, false));
            solicitud.setGridDocumentosAdicionales(getGridDocumentos(tramite.getAsegurado().getDocumentosProbatorios(), userProfile, sol, true));
            //Documentos Beneficiario
            if (tramite.getBeneficiario() != null) {
                solicitud.setInformacionBeneficiario(getBeneficiario(tramite.getBeneficiario()));
                solicitud.setGridDocumentosBeneficiario(getGridDocumentos(tramite.getBeneficiario().getDocumentosProbatorios(), userProfile, sol, false));
                solicitud.setGridDocsBeneficiarioAdicionales(getGridDocumentos(tramite.getBeneficiario().getDocumentosProbatorios(), userProfile, sol, true));
            }
            //Documentos Representante
            if (tramite.getRepresentante() != null) {
                solicitud.setInformacionBeneficiario(getRepresentanteLegal(tramite.getRepresentante()));
                solicitud.setGridDocumentosRepLegal(getGridDocumentos(tramite.getRepresentante().getDocumentosProbatorios(), userProfile, sol, false));
                solicitud.setGridDocumentosRepLegalAdicionales(getGridDocumentos(tramite.getRepresentante().getDocumentosProbatorios(), userProfile, sol, true));
            }
            estadov = tramite.getEstadoTramite().getDescripcion();
        }// Termina de recorrer los tramitenew Page<mx.gob.imss.cit.cda.web.app.responsable.model.Documento>()s de la solicitud

        solicitud.setGridsNSS(gridsNSS);
        solicitud.setGridsDocumentosNss(gridsDocumentosNss);
        solicitud.setGridsDocumentosNssOrigen(gridsDocumentosNssOrigen);
        solicitud.setGridDocumentosNssAdicionales(getGridDocumentosProbatoriosNssAdicionales(documentosNssAdicional)); // documentosNssAdicional Page<DocumentosNss>

        solicitud.setFolio(sol.getNoFolioSolicitud());
//        solicitud.setEstatus(getEstado(sol));-
        solicitud.setEstatus(estadov);
        solicitud.setSubDelegacion(getSubdelegacion(sol));
        solicitud.setResponsable(sol.getSolicitante() != null ? sol.getSolicitante().getUsuario() : "Sin Responsable");
        solicitud.setCurpResponsable(sol.getSolicitante() != null ? sol.getSolicitante().getUsuario() : "Sin Responsable");
        solicitud.setIdSolicitud(String.valueOf(sol.getSolicitudId()));

        log.debug("---CDA--- usuario {}", sol.getEstadoSolicitud().getIdEstadoSolicitud() != null ? sol.getEstadoSolicitud().getIdEstadoSolicitud() : "null");

        solicitud.setTipoRegularizacion(tipoRegSol);

        return solicitud;
    }

    private NSS armarNss(TramiteCorreccionCurp tramite, String observacion) {
        log.debug("---CDA Ventanilla--- NSS asociado al tramite: {}", tramite
                .getListaNssCorreccion().get(0).getNss());
        NSS nss = new NSS();

        nss.setTramiteId(tramite.getTramiteId());
        nss.setNss(tramite.getListaNssCorreccion().get(0).getNss());
        nss.setConvencional(Boolean.TRUE);

        TipoRegularizacionNSS tipoRegNSS = tipoRegularizacionNSSUtil
                .getTipoRegularizacionNSS(tramite);
        nss.setGrupoCorreccion(tipoRegNSS);

        List<Fisica> personasFuenteNSS = serviceBusiness
                .getAseguradoByNSSLegadosyBDTU(tramite.getListaNssCorreccion().get(0).getNss(),
                        true);
        if (personasFuenteNSS != null) {
            log.debug("Total de fuentes {}", personasFuenteNSS.size());
        }
        nss.setInformacionFuentesNSS(getListaPersonasNSS(personasFuenteNSS,
                observacion));

        return nss;
    }

    private Documento armarDocumento(DocumentoProbatorio documentoProbatorio,
            String solicitudId, String numeroFolio) {
        Documento documento = new Documento();
        documento.setIdPersona(solicitudId);

        if (documentoProbatorio.getNomNombreDocumento() != null) {
            String[] n = documentoProbatorio.getNomNombreDocumento().split("\\.");
            documento.setExtension(n[n.length - 1]);
        }

        documento.setNombreArchivo(documentoProbatorio.getNomNombreDocumento() != null ? documentoProbatorio.getNomNombreDocumento() : "Documento probatorio.pdf");
        documento.setTipoDocumento(documentoProbatorio.getDocumentoPorTipo()
                .getDocumento().getDesDocumento());
        documento.setIdDocBoveda(documentoProbatorio.getBovedaDocId());
        log.debug("---CDA Nombre del tipoDocumento adjuntado {}",
                documentoProbatorio.getDocumentoPorTipo()
                        .getTipoDocumentoProbatorio().getDescripcion());
        log.debug("---CDA Nombre del tipoDocumento adjuntado {}",
                documento.getTipoDocumento());
        log.debug("---CDA Nombre del nombreDocumentoAdjuntado {}",
                documento.getNombreArchivo());

        documento.setFolio(numeroFolio);
        return documento;

    }

    private DomicilioParticular armarDomicilio(Domicilio domicilio) {
        DomicilioParticular domicilioParticular = new DomicilioParticular();
        domicilioParticular.setCalle(domicilio.getCalle());
        domicilioParticular
                .setCp(domicilio.getCodigoPostal().getCodigoPostal());
        domicilioParticular.setEntidadFederativa(domicilio.getAsentamiento()
                .getLocalidad().getMunicipio().getEntidadFederativa()
                .getNombre());
        domicilioParticular.setDelegacion(domicilio.getAsentamiento()
                .getLocalidad().getMunicipio().getNombre());
        domicilioParticular.setColonia(domicilio.getAsentamiento().getNombre());
        domicilioParticular.setNumeroExterior(domicilio.getNumExteriorAlf());
        domicilioParticular.setNumeroInterior(domicilio.getNumInteriorAlf());

        return domicilioParticular;
    }

    private Page<DocumentosNss> getDocumentosProbatoriosNssOrigen(
            TramiteCorreccionCurp tramite, String solicitudId,
            String numeroFolio) throws DocumentoProbatorioException {
        log.debug("---CDA Ventanilla--- DocumentosProbatorios por NSS y Origen ");

        Page<DocumentosNss> gridDocumentosNSS = new Page<DocumentosNss>();
        gridDocumentosNSS.setTotalOfRecords(0);
        gridDocumentosNSS.setPageSize(1000);
        gridDocumentosNSS.setCurrentPage(1);

        List<DocumentosNss> documentosProbatorios = new ArrayList<DocumentosNss>();

        String origen = getConsultaSolicitudBusiness()
                .obtenerOrigenNssCapturado(tramite.getTramiteId());

        if (origen.equals(OrigenCapturaCDAEnum.SOLICITUD.getDescripcion())) {
            origen = "Asegurado";
        }

        List<Documento> listaDocumentos = new ArrayList<Documento>();

        for (DocumentoProbatorio documentoProbatorio : tramite
                .getDocumentosProbatorios()) {
            if (documentoProbatorio.getDocumentoPorTipo()
                    .getTipoDocumentoProbatorio()
                    .getIdTipoDocumentoProbatorio() == TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS
                            .getId()) {

                listaDocumentos.add(armarDocumento(documentoProbatorio,
                        solicitudId, numeroFolio));
            }
        }

        DocumentosNss documentosNss = new DocumentosNss();
        documentosNss.setOrigen(origen);
        documentosNss.setNss(tramite.getListaNssCorreccion().get(0).getNss());
        documentosNss.setDocumentos(listaDocumentos);

        documentosProbatorios.add(documentosNss);

        gridDocumentosNSS.setData(documentosProbatorios);
        gridDocumentosNSS.setTotalOfRecords(documentosProbatorios.size());

        return gridDocumentosNSS;

    }

    private DocumentosNss getDocumentosProbatoriosNssAdicionales(TramiteCorreccionCurp tramite, String solicitudId, String numeroFolio) throws DocumentoProbatorioException {
        log.debug("---CDA Ventanilla--- DocumentosProbatorios por NSS y Origen ");

        List<Documento> listaDocumentos = new ArrayList<Documento>();

        for (DocumentoProbatorio documentoProbatorio : tramite.getDocumentosProbatorios()) {
            if (documentoProbatorio.getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio() == TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId()) {
                if (documentoProbatorio.getInformacionAdicional() != null && documentoProbatorio.getInformacionAdicional()) {
                    listaDocumentos.add(armarDocumento(documentoProbatorio, solicitudId, numeroFolio));
                }
            }
        }

        DocumentosNss documentosNss = new DocumentosNss();
        if (!listaDocumentos.isEmpty()) {
            documentosNss.setNss(tramite.getListaNssCorreccion().get(0).getNss());
            documentosNss.setDocumentos(listaDocumentos);
        }
        return documentosNss;
    }

    private List<DocumentoProbatorio> obtenerDocumentosProbatorios(
            Long cveIdTramite) throws DocumentoProbatorioException {
        List<DocumentoProbatorio> documentosProbatorios = new ArrayList<DocumentoProbatorio>();
        for (DocumentoProbatorio documentoProbatorio : deltaDocumentoProbatorioServiceBusiness
                .listaDocumentosProbatoriosActivosTramite(cveIdTramite)) {
            documentoProbatorio = deltaDocumentoProbatorioServiceBusiness
                    .getDocumentoProbatorio(documentoProbatorio
                            .getIdDocumentoProbatorio().longValue());
            log.debug("Nombre {} id {} tramite {}", new Object[]{
                documentoProbatorio.getNomNombreDocumento(),
                documentoProbatorio.getIdDocumentoProbatorio(),
                cveIdTramite});

            documentosProbatorios.add(documentoProbatorio);
        }
        return documentosProbatorios;

    }

    
    private void getInformacionRenapoStep02(InformacionRENAPO informacionRENAPO,
            CorreoElectronico correo, TelefonoFijo telefono, TelefonoMovil telefonoMov ){
      informacionRENAPO.setTelefonoFijo(telefono != null ? telefono
                .getNumero() : "");
        informacionRENAPO.setTelefonoMovil(telefonoMov != null ? telefonoMov
                .getNumero() : "");
        informacionRENAPO.setCorreoElectronico(correo != null ? correo
                .getCorreo() : "");
        informacionRENAPO.setErrorSINDO(false);
    }
    
    private void  getInformacionRenapoStep01(InformacionRENAPO informacionRENAPO,
            Fisica personaRenapo){
      
      CorreoElectronico correo = null;
        TelefonoFijo telefono = null;
        TelefonoMovil telefonoMov = null;

        if (personaRenapo.getMediosContacto() != null
                && !personaRenapo.getMediosContacto().isEmpty()) {
            for (MedioContacto med : personaRenapo.getMediosContacto()) {
                if (med instanceof CorreoElectronico) {
                    correo = (CorreoElectronico) med;
                }

                if (med instanceof TelefonoFijo) {
                    telefono = (TelefonoFijo) med;
                }

                if (med instanceof TelefonoMovil) {
                    telefonoMov = (TelefonoMovil) med;
                }
            }
        }

        getInformacionRenapoStep02(informacionRENAPO, correo, telefono,
                telefonoMov);
    
    }
    
    @SuppressWarnings("unused")
    private InformacionRENAPO getInformacionRenapo(
            mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud sol,
            Fisica personaRenapo) {
        InformacionRENAPO informacionRENAPO = new InformacionRENAPO();
        informacionRENAPO.setFolio(sol.getNoFolioSolicitud());
        informacionRENAPO.setNombre(personaRenapo.getNombre());
        informacionRENAPO.setApellidoPaterno(personaRenapo.getPrimerApellido());
        informacionRENAPO
                .setApellidoMaterno(personaRenapo.getSegundoApellido());
        informacionRENAPO.setCurp(personaRenapo.getCurp());
        informacionRENAPO
                .setSexo(personaRenapo.getSexo() != null
                        && personaRenapo.getSexo().getDescripcion() != null ? personaRenapo
                        .getSexo().getDescripcion() : "");
        informacionRENAPO.setFechaNacimiento(personaRenapo
                .getFechaNacimientoFormateada());
        informacionRENAPO
                .setDatosDocumentoProbatorio(getDatosDoctoProbatorio(personaRenapo));
        informacionRENAPO
                .setLugarNacimiento(personaRenapo.getLugarNacimiento() != null ? personaRenapo
                        .getLugarNacimiento().getNombre() : "");
        informacionRENAPO
                .setNacionalidad(personaRenapo.getPais() != null ? personaRenapo
                        .getPais().getNacionalidad() : "");

        // medio de contacto
        getInformacionRenapoStep01(informacionRENAPO, personaRenapo);

        informacionRENAPO.setCurpsHistoricas(getCurpsHistoricas(personaRenapo
                .getCurpsHistoricas()));
        return informacionRENAPO;
    }

    private InformacionBeneficiario getBeneficiario(mx.gob.imss.ctirss.delta.model.gestion.individuo.Beneficiario persona) {
        InformacionBeneficiario informacionBeneficiario = getRepresentanteLegal(persona);

        if (persona != null && persona.getTipoBeneficiario() != null && (persona.getTipoBeneficiario() != TipoSolicitanteEnum.ASEGURADO_PENSIONADO.getId())) {

            int tipoPersona = persona.getTipoBeneficiario();

            informacionBeneficiario.setParentesco((tipoPersona != (TipoSolicitanteEnum.REPRESENTANTE_LEGAL.getId())) ? "Parentesco: "
                    + getTipoPersona(tipoPersona) : "");
        }

        return informacionBeneficiario;
    }

    private InformacionBeneficiario getRepresentanteLegal(mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica persona) {
        InformacionBeneficiario informacionBeneficiario = new InformacionBeneficiario();

        if( persona != null){
        informacionBeneficiario.setNombre(persona.getNombre());
        informacionBeneficiario.setApellidoPaterno(persona
                .getPrimerApellido());
        informacionBeneficiario.setApellidoMaterno(persona
                .getSegundoApellido());
        informacionBeneficiario.setCurp(persona.getCurp());
        informacionBeneficiario
                .setSexo(persona.getSexo() != null
                        && persona.getSexo().getDescripcion() != null ? persona
                        .getSexo().getDescripcion() : "");
        informacionBeneficiario
                .setCurpsHistoricas(getCurpsHistoricas(persona
                        .getCurpsHistoricas()));

        informacionBeneficiario.setParentesco("Parentesco: " + TipoSolicitanteEnum.REPRESENTANTE_LEGAL.getDescripcion());
        }
        return informacionBeneficiario;
    }

    private String getTipoPersona(int tipoPersona) {
        for (TipoSolicitanteEnum value : TipoSolicitanteEnum.values()) {
            if (value.getId() == tipoPersona) {
                return value.getDescripcion();
            }
        }
        return "";
    }

    private String formatearObservacionesSubdelegacion(
            List<ObservacionesSubdelegacion> observacionesSubdelegacion) {

        StringBuilder str = new StringBuilder();
        for (ObservacionesSubdelegacion ob : observacionesSubdelegacion) {
            if (StringUtils.isNotBlank(ob.getDetalle())
                    || StringUtils.isNotBlank(ob.getResumen())) {
                String resumen = null == ob.getResumen() ? "" : ob.getResumen();
                str.append(ob.getFechaActualizacion() != null ? deltaUtils
                        .convertirDateToStringMask(ob.getFechaActualizacion(),
                                "") : "");
                str.append(" - ");
                if (StringUtils.isNotBlank(resumen)) {
                    str.append("Resumen: ");
                    str.append(resumen.toUpperCase());
                    str.append(" / ");
                }
                str.append("Detalle: ");
                str.append(
                        null == ob.getDetalle() ? "" : ob.getDetalle()
                        .toUpperCase()).append("\n");
            }
        }

        return str.toString();

    }

    private String getInfoRoles(Solicitud sol, Map<String, Object> roles) {

        StringBuilder str = new StringBuilder();

        if (sol.getObservacionSubdelegacion() != null) {
            str.append(sol.getObservacionSubdelegacion());

        }

        if (roles != null && !roles.keySet().isEmpty()) {
            for (String key : roles.keySet()) {
                str.append("\n");
                str.append(roles.get(key));
            }
        }

        return str.toString();
    }
    
    private void getListaPersonasNSSStep01(InformacionRENAPO informacionNSS,
            Fisica personaNSS
            ){
    
      informacionNSS
                    .setNombre(personaNSS.getNombre() != null ? personaNSS
                            .getNombre().trim().toUpperCase() : "");
            informacionNSS
                    .setApellidoPaterno(personaNSS.getPrimerApellido() != null ? personaNSS
                            .getPrimerApellido().trim().toUpperCase()
                            : "");
            informacionNSS
                    .setApellidoMaterno(personaNSS.getSegundoApellido() != null ? personaNSS
                            .getSegundoApellido().trim().toUpperCase()
                            : "");
            informacionNSS.setCurp(personaNSS.getCurp() != null ? personaNSS
                    .getCurp().trim().toUpperCase() : "");
            informacionNSS
                    .setSexo(personaNSS.getSexo() != null
                            && personaNSS.getSexo().getDescripcion() != null ? personaNSS
                            .getSexo().getDescripcion().trim().toUpperCase()
                            : "");
            informacionNSS.setFechaNacimiento(personaNSS
                    .getFechaNacimientoFormateada() != null ? personaNSS
                                    .getFechaNacimientoFormateada().trim() : "");
            

    
    }

    private List<InformacionRENAPO> getListaPersonasNSS(
            List<Fisica> personasFuenteNSS, String observacionSINDO) {
        List<InformacionRENAPO> listaPersonasNSS = new ArrayList<InformacionRENAPO>();

        log.debug("---CDA--- Recorriendo lista {}", personasFuenteNSS);
        for (Fisica personaNSS : personasFuenteNSS) {
            log.debug("---CDA--- Nombre {}", personaNSS.getNombre());
            InformacionRENAPO informacionNSS = new InformacionRENAPO();
            getListaPersonasNSSStep01(informacionNSS, personaNSS);
            
            
            informacionNSS
                    .setDatosDocumentoProbatorio(getDatosDoctoProbatorio(personaNSS));
            informacionNSS.setLugarNacimiento(pattern.matcher(
                    Normalizer.normalize(
                            personaNSS.getLugarNacimiento() != null
                            && personaNSS.getLugarNacimiento()
                                    .getNombre() != null ? personaNSS
                                            .getLugarNacimiento().getNombre().trim()
                                            .toUpperCase() : "", Normalizer.Form.NFD))
                    .replaceAll(""));
            informacionNSS
                    .setNacionalidad(pattern
                            .matcher(
                                    Normalizer.normalize(
                                            personaNSS.getPais() != null
                                            && personaNSS.getPais()
                                                    .getNacionalidad() != null ? personaNSS
                                                            .getPais()
                                                            .getNacionalidad().trim()
                                                            .toUpperCase()
                                                    : "", Normalizer.Form.NFD))
                            .replaceAll(""));
            
            
            for (Identificador identificador : personaNSS.getIdentificadores()) {
                OrigenConsultaNssEnum enumOrigen = OrigenConsultaNssEnum
                        .obtenerEnumById(Long.valueOf(
                                identificador.getIdIdentificador()).intValue());
                if (enumOrigen.getDescripcion().equalsIgnoreCase(
                        identificador.getIdentificadora())) {
                    informacionNSS.setOrigen(identificador.getIdentificadora());
                    informacionNSS.setIdOrigen(String.valueOf(enumOrigen
                            .getClave()));
                    informacionNSS.setErrorSINDO(getErrorFuente(
                            observacionSINDO, enumOrigen.getClave()));
                    break;
                }
            }
            listaPersonasNSS.add(informacionNSS);
        }
        return listaPersonasNSS;
    }

    private Boolean getDefuncionAsegurado(List<Fisica> personas) {
        Boolean defuncion = Boolean.FALSE;
        for (Fisica fisica : personas) {
            if (fisica.getTipoPersona() != null
                    && !fisica.getTipoPersona().getDescripcion().equals("")
                    && !fisica
                            .getTipoPersona()
                            .getDescripcion()
                            .equals(TipoSolicitanteEnum.ASEGURADO_PENSIONADO
                                    .getDescripcion())
                    && fisica.getChecked() != null) {
                defuncion = fisica.getChecked();
                break;
            }
        }

        return defuncion;
    }

    private Page<HistoriaLaboral> getGridHistoriaLaboral(
            TramiteCorreccionCurp tramite) {

        Page<HistoriaLaboral> gridHistoriaLaboral = new Page<HistoriaLaboral>();
        gridHistoriaLaboral.setTotalOfRecords(0);
        gridHistoriaLaboral.setPageSize(1000);
        gridHistoriaLaboral.setCurrentPage(1);

        List<DatosLaborales> datosLaborales = tramite.getDatosLaborales();
        List<HistoriaLaboral> listtHistoria = new ArrayList<HistoriaLaboral>();
        if (datosLaborales != null && !datosLaborales.isEmpty()) {
            for (DatosLaborales d : datosLaborales) {
                HistoriaLaboral historia = new HistoriaLaboral();
                historia.setActividadEmpresa(d.getActividad());
                historia.setDomicilioEmpresa(d.getDomicilio());
                historia.setEntidadFederativa(d.getEntidadFederativa()
                        .getNombre());
                historia.setFechaBaja(d.getFechaBaja());
                historia.setFechaInscripcion(d.getFechaInscripcion());
                historia.setNombrePatron(d.getNombrePatron());
                historia.setNumeroRegistroPatronal(d.getNrp());
                listtHistoria.add(historia);
            }
            gridHistoriaLaboral.setData(listtHistoria);
            gridHistoriaLaboral.setTotalOfRecords(listtHistoria.size());
        }
        return gridHistoriaLaboral;
    }

    
    private void getGridDocumentosProbatoriosStep01(int tipoDocumento,
            boolean esInfoAdicional, DocumentoProbatorio documentoProbatorio,
            List<Documento> listaDocumentosAdicionales, 
            List<Documento> listaDocumentos,
            mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud
            ){
      if (tipoDocumento == TipoDocumentoProbatorioEnum.IDENTIFICACION.getId() || tipoDocumento == TipoDocumentoProbatorioEnum.ACTAS.getId()) {
          if (esInfoAdicional) {
              if (documentoProbatorio.getInformacionAdicional() != null && documentoProbatorio.getInformacionAdicional()) {
                  listaDocumentosAdicionales.add(armarDocumento(documentoProbatorio, solicitud.getSolicitudId().toString(), solicitud.getNoFolioSolicitud()));
              }
          } else {
              listaDocumentos.add(armarDocumento(documentoProbatorio, solicitud.getSolicitudId().toString(), solicitud.getNoFolioSolicitud()));
          }
      }
    }
    
    private void getGridDocumentosProbatoriosStep02(Page<Documento> gridDocumentos,
            List<Documento> listaDocumentos, List<Documento> listaDocumentosAdicionales,
            boolean esInfoAdicional){

      gridDocumentos.setData(esInfoAdicional ? listaDocumentosAdicionales.isEmpty() ? null : listaDocumentosAdicionales : listaDocumentos.isEmpty() ? null : listaDocumentos);
      gridDocumentos.setTotalOfRecords(esInfoAdicional ? listaDocumentosAdicionales.isEmpty() ? 0L : listaDocumentosAdicionales.size() : listaDocumentos.isEmpty() ? 0L : listaDocumentos.size());
    
    }
    
    private Page<Documento> getGridDocumentosProbatorios(List<DocumentoProbatorio> documentosProbatorios, mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud, boolean esInfoAdicional) {

        log.debug("---CDA DOCUMENTOS PROBATORIOS ASEGURADO ADICIONALES{}", esInfoAdicional);

        Page<Documento> gridDocumentos = new Page<Documento>();
        gridDocumentos.setTotalOfRecords(0);
        gridDocumentos.setPageSize(1000);
        gridDocumentos.setCurrentPage(1);
        gridDocumentos.setData(null);

        if (documentosProbatorios != null) {
            List<Documento> listaDocumentos = new ArrayList<Documento>();
            List<Documento> listaDocumentosAdicionales = new ArrayList<Documento>();
            for (DocumentoProbatorio documentoProbatorio : documentosProbatorios) {
                int tipoDocumento = documentoProbatorio.getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio();
                getGridDocumentosProbatoriosStep01(tipoDocumento,
                        esInfoAdicional, documentoProbatorio,
                        listaDocumentosAdicionales, listaDocumentos, solicitud);
            }

            getGridDocumentosProbatoriosStep02(gridDocumentos, listaDocumentos,
                    listaDocumentosAdicionales, esInfoAdicional);
        }
        return gridDocumentos;
    }

    private Page<NSS> getGridNss(List<NSS> listNSS) {
        Page<NSS> gridNSS = new Page<NSS>();
        gridNSS.setData(listNSS);
        gridNSS.setCurrentPage(1);
        gridNSS.setPageSize(10000);
        gridNSS.setTotalOfRecords(1);
        return gridNSS;
    }

    private Page<Documento> getGridDocumentosProbatoriosNss(List<DocumentoProbatorio> documentosProbatorios, mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud) {
        log.debug("---CDA LISTA DE DOCUMENTOS PROBATORIOS POR NSS{}");

        Page<Documento> gridDocumentosNSS = new Page<Documento>();
        gridDocumentosNSS.setTotalOfRecords(0);
        gridDocumentosNSS.setPageSize(1000);
        gridDocumentosNSS.setCurrentPage(1);
        gridDocumentosNSS.setData(null);

        if (documentosProbatorios != null) {
            List<Documento> listaDocumentos = new ArrayList<Documento>();

            for (DocumentoProbatorio documentoProbatorio : documentosProbatorios) {
                if ((documentoProbatorio.getInformacionAdicional() != null) && !documentoProbatorio.getInformacionAdicional()) {
                    if (documentoProbatorio.getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio() == TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS.getId()) {
                        if (documentoProbatorio.getInformacionAdicional() != null && !documentoProbatorio.getInformacionAdicional()) {
                            listaDocumentos.add(armarDocumento(documentoProbatorio, solicitud.getSolicitudId().toString(), solicitud.getNoFolioSolicitud()));
                        }

                    }
                }

            }

            gridDocumentosNSS.setData(listaDocumentos);
            gridDocumentosNSS.setTotalOfRecords(listaDocumentos.size());
        }

        return gridDocumentosNSS;
    }

    private Page<DocumentosNss> getGridDocumentosProbatoriosNssAdicionales(List<DocumentosNss> documentosProbatorios) {
        Page<DocumentosNss> gridDocumentosNSS = new Page<DocumentosNss>();

        if (!documentosProbatorios.isEmpty()) {
            for (DocumentosNss documentosNss : documentosProbatorios) {
                if (documentosNss.getDocumentos() == null) {
                    gridDocumentosNSS.setData(null);
                    gridDocumentosNSS.setTotalOfRecords(0L);
                } else {
                    gridDocumentosNSS.setData(documentosProbatorios);
                    gridDocumentosNSS.setTotalOfRecords(documentosProbatorios.size());
                }
                break;
            }

        }

        gridDocumentosNSS.setTotalOfRecords(0);
        gridDocumentosNSS.setPageSize(1000);
        gridDocumentosNSS.setCurrentPage(1);

        return gridDocumentosNSS;
    }

    /**
     * OBtiene la lista de de NSS que se capturaron en la solicitud
     *
     * @param listaNss
     * @param originadosEnSolicitud Indic si se uieren los de la solicitud o de
     * lo contrario se obtienen los de Ventanilla y de Sistema
     * @return
     */
    public Page<DetalleNssCda> getGridNssSolicitud(List<CorreccionNSS> listaNss,
            boolean originadosEnSolicitud) {
        log.debug("---CDA llenando la lista de nss de solicitud de la vista{}");
        Page<DetalleNssCda> gridDocumentosNSS = new Page<DetalleNssCda>();
        List<DetalleNssCda> listaNssVista = new ArrayList<DetalleNssCda>();

        log.debug("---CDA el modelo tiene {}", listaNss.size());
        for (CorreccionNSS correccionNSS : listaNss) {
            if ((originadosEnSolicitud
                    && correccionNSS.getOrigen().intValue() == OrigenCapturaCDAEnum.SOLICITUD.getClave())
                    || (!originadosEnSolicitud
                    && correccionNSS.getOrigen().intValue() != OrigenCapturaCDAEnum.SOLICITUD.getClave())) {
                DetalleNssCda detalleNssCda = new DetalleNssCda();
                detalleNssCda.setClaveDetalleNssCda(correccionNSS.getClaveDetalleNssCda());
                detalleNssCda.setNss(correccionNSS.getNss());
                detalleNssCda.setIdTipoNss(correccionNSS.getIdTipoNss());
                detalleNssCda.setOrigen(correccionNSS.getOrigen());
                detalleNssCda.setObservaciones(correccionNSS.getObservaciones());

                //llena los documentos probatorios del nss
                List<Documento> documentos = new ArrayList<Documento>();
                if (correccionNSS.getDocumentosProbatorios() != null) {
                    for (DocumentoProbatorio documentoProbatorio : correccionNSS.getDocumentosProbatorios()) {
                        Documento documento = new Documento();
                        documento.setIdDocBoveda(documentoProbatorio.getBovedaDocId());
                        documento.setNombreArchivo(documentoProbatorio.getNomNombreDocumento());
                        documento.setDesDocumento(documentoProbatorio.getDocumentoPorTipo().getDocumento().getDesDocumento());
                        documento.setTipoDocumento(documentoProbatorio.getDocumentoPorTipo().getDocumento().getCveIdDocumento().toString());
                        documentos.add(documento);
                    }
                }
                detalleNssCda.setDocumentosProbatorios(documentos);

                listaNssVista.add(detalleNssCda);
            }
        }

        log.debug("---CDA la lista de la vista tiene {}", listaNssVista.size());
        gridDocumentosNSS.setData(listaNssVista);
        gridDocumentosNSS.setTotalOfRecords(0);
        gridDocumentosNSS.setPageSize(1000);
        gridDocumentosNSS.setCurrentPage(1);

        return gridDocumentosNSS;
    }

    
    private void getGridDocumentosBeneficiarioAdicionalStep01(String tipoPersona,
            Tramite tramite, List<Documento> listaDocsBeneficiaro,
            String solicitudId, String numeroFolio, Page<DocumentosBeneficiario> gridDocumentosBeneficiario ){
      if (!tipoPersona.equals("") && !tipoPersona.equals(TipoSolicitanteEnum.ASEGURADO_PENSIONADO.getDescripcion())) {
          for (DocumentoProbatorio documentoProbatorio : tramite.getPersonas().get(0).getDocumentosProbatorios()) {
              if (documentoProbatorio.getInformacionAdicional() != null && documentoProbatorio.getInformacionAdicional()) {
                  listaDocsBeneficiaro.add(armarDocumento(documentoProbatorio, solicitudId, numeroFolio));
              }
          }

          List<DocumentosBeneficiario> documentosBeneficiarioList = new ArrayList<DocumentosBeneficiario>();
          if (!listaDocsBeneficiaro.isEmpty()) {
              DocumentosBeneficiario documentoBeneficiario = new DocumentosBeneficiario();
              documentoBeneficiario.setDocumentos(listaDocsBeneficiaro);
              documentoBeneficiario.setParentesco(tipoPersona);
              documentosBeneficiarioList.add(documentoBeneficiario);
          }

          gridDocumentosBeneficiario.setData(documentosBeneficiarioList.isEmpty() ? null : documentosBeneficiarioList);
          gridDocumentosBeneficiario.setTotalOfRecords(documentosBeneficiarioList.size());
      }
    }
    
    
    private Page<DocumentosBeneficiario> getGridDocumentosBeneficiarioAdicional(Tramite tramite, String solicitudId, String numeroFolio) {

        Page<DocumentosBeneficiario> gridDocumentosBeneficiario = new Page<DocumentosBeneficiario>();
        gridDocumentosBeneficiario.setTotalOfRecords(0);
        gridDocumentosBeneficiario.setPageSize(1000);
        gridDocumentosBeneficiario.setCurrentPage(1);
        gridDocumentosBeneficiario.setData(null);

        List<Documento> listaDocsBeneficiaro = new ArrayList<Documento>();
        if (tramite != null) {
            for (Fisica fisica : tramite.getPersonas()) {
                if (fisica.getTipoPersona() != null) {
                    String tipoPersona = fisica.getTipoPersona().getDescripcion().toUpperCase();
                    getGridDocumentosBeneficiarioAdicionalStep01(tipoPersona,
                            tramite, listaDocsBeneficiaro, solicitudId,
                            numeroFolio, gridDocumentosBeneficiario);
                    
                    break;
                }
            }
        }
        return gridDocumentosBeneficiario;
    }

    private Page<Documento> getGridDocumentosBeneficiario(Tramite tramite, String solicitudId, String numeroFolio) {

        Page<Documento> gridDocumentosBeneficiario = new Page<Documento>();
        gridDocumentosBeneficiario.setTotalOfRecords(0);
        gridDocumentosBeneficiario.setPageSize(1000);
        gridDocumentosBeneficiario.setCurrentPage(1);
        gridDocumentosBeneficiario.setData(null);

        List<Documento> listaDocsBeneficiaro = new ArrayList<Documento>();

        for (Fisica fisica : tramite.getPersonas()) {
            if (fisica.getTipoPersona() != null) {
                String tipoPersona = fisica.getTipoPersona().getDescripcion().toUpperCase();

                if (!tipoPersona.equals("") && !tipoPersona.equals(TipoSolicitanteEnum.ASEGURADO_PENSIONADO.getDescripcion())) { // && check desactivado
                    for (DocumentoProbatorio documentoProbatorio : tramite.getPersonas().get(0).getDocumentosProbatorios()) {
                        if (documentoProbatorio.getInformacionAdicional() != null && !documentoProbatorio.getInformacionAdicional()) {
                            listaDocsBeneficiaro.add(armarDocumento(documentoProbatorio, solicitudId, numeroFolio));
                        }
                    }

                    gridDocumentosBeneficiario.setData(listaDocsBeneficiaro);
                    gridDocumentosBeneficiario.setTotalOfRecords(listaDocsBeneficiaro.size());
                }
                break;
            }
        }
        return gridDocumentosBeneficiario;
    }
    
    private void getGridDocumentosStep02(Documento doc,List<mx.gob.imss.cit.cda.web.app.responsable.model.Documento> lstDocs){
      if (doc != null && doc.getNombreArchivo() != null) {
                  log.debug(doc.getNombreArchivo());
                  lstDocs.add(doc);
              }
    }
    private Documento getGridDocumentosStep01(Boolean informacionAdicional,
            DocumentoProbatorio docProbatorio, UserProfile userProfile,Documento doc,
            List<mx.gob.imss.cit.cda.web.app.responsable.model.Documento> lstDocs,
            mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud sol){
      if (informacionAdicional) {
          if (docProbatorio.getInformacionAdicional() != null ? docProbatorio.getInformacionAdicional() : false) {
              doc = convertDocProvatorioToDocumento(docProbatorio, userProfile.getIdPersona() != null ? userProfile.getIdPersona().toString() : "0", sol.getNoFolioSolicitud(), informacionAdicional);
              getGridDocumentosStep02(doc, lstDocs);
          }                        
      } else {
          doc = convertDocProvatorioToDocumento(docProbatorio, userProfile.getIdPersona() != null ? userProfile.getIdPersona().toString() : "0", sol.getNoFolioSolicitud(), informacionAdicional);
          log.debug("---------probatorios--------");
          getGridDocumentosStep02(doc, lstDocs);
      }
      return doc;
    }

    private Page<mx.gob.imss.cit.cda.web.app.responsable.model.Documento> getGridDocumentos(List<? extends AbstractModel> listaDocumentos,
            UserProfile userProfile,
            mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud sol,
            Boolean informacionAdicional) {
        log.debug("---getGridDocumentos--- {}");
        List<mx.gob.imss.cit.cda.web.app.responsable.model.Documento> lstDocs = new ArrayList<Documento>();
        Page<Documento> pag = new Page();
        if (listaDocumentos != null) {
            log.debug("---listaDocumentos probatorios valida--- {}");
            for (AbstractModel documento : listaDocumentos) {
                Documento doc = null;
                if (documento instanceof DocumentoProbatorio) {
                    DocumentoProbatorio docProbatorio = (DocumentoProbatorio) documento;

                    doc = getGridDocumentosStep01(informacionAdicional,
                            docProbatorio, userProfile, doc, lstDocs, sol);
                    
                }
            }
        }
        pag.setData(lstDocs);
        pag.setCurrentPage(1);
        pag.setPageSize(1000);
        pag.setTotalOfRecords(lstDocs.size());

        return pag;
    }

    private Documento convertDocProvatorioToDocumento(DocumentoProbatorio docProbatorio, String idPersona, String numeroFolio, Boolean informacionAdicional) {
        if (docProbatorio != null) {
            log.debug("--- informacionAdicional : " + informacionAdicional + "docProbatorio.getInformacionAdicional()" + docProbatorio.getInformacionAdicional());

            Documento documento = new Documento();
            documento.setIdPersona(idPersona);
            documento.setNombreArchivo(docProbatorio.getNomNombreDocumento() != null ? docProbatorio.getNomNombreDocumento() : "Documento probatorio.pdf");

            if (docProbatorio.getNomNombreDocumento() != null) {
                String[] n = docProbatorio.getNomNombreDocumento().split("\\.");
                documento.setExtension(n[n.length - 1]);
            }

            documento.setTipoDocumento(docProbatorio.getDocumentoPorTipo()
                    .getDocumento().getDesDocumento());
            documento.setIdDocBoveda(docProbatorio.getBovedaDocId());
            log.debug("---CDA Nombre del tipoDocumento adjuntado {}",
                    docProbatorio.getDocumentoPorTipo()
                            .getTipoDocumentoProbatorio().getDescripcion());
            log.debug("---CDA Nombre del tipoDocumento adjuntado {}",
                    documento.getTipoDocumento());
            log.debug("---CDA Nombre del nombreDocumentoAdjuntado {}",
                    documento.getNombreArchivo());

            documento.setFolio(numeroFolio);

            return documento;

        }
        return null;
    }
    
  private void getDatosDoctoProbatorioStep01(StringBuffer documento,
          Nacimiento actaNacimiento) {
    documento
            .append("Entidad: ")
            .append(actaNacimiento.getMunicipio()
                    .getEntidadFederativa().getNombre() != null ?
                            normalizarCadenas(actaNacimiento
                                    .getMunicipio().getEntidadFederativa().
                                    getNombre()) :
                            " ");
    documento
            .append("\nMunicipio: ")
            .append(actaNacimiento.getMunicipio().getNombre() != null ?
                    normalizarCadenas(actaNacimiento
                            .getMunicipio().getNombre()) :
                    " ");
    documento
            .append("\nA\u00f1o de registro: ")
            .append(actaNacimiento.getAnio() != 0 ?
                    normalizarCadenas(actaNacimiento
                            .getAnio()) :
                    " ");
    documento
            .append("\nTomo: ")
            .append(!actaNacimiento.getTomo().equals("0") ?
                    normalizarCadenas(actaNacimiento
                            .getTomo()) :
                    "");
    documento
            .append("\nN\u00famero de Acta: ")
            .append(!actaNacimiento.getNoActa().equals("0") ?
                    normalizarCadenas(actaNacimiento
                            .getNoActa()) :
                    "");
    documento
            .append("\nCRIP: ")
            .append(!actaNacimiento.getCrip().equals("0") ?
                    normalizarCadenas(actaNacimiento
                            .getCrip()) :
                    "");

  }

    private void getDatosDoctoProbatorioStep02(Fisica personaRenapo,
      StringBuffer documento ){
    
      for (DocumentoProbatorio documentoProbatorio : personaRenapo
                    .getDocumentosProbatorios()) {
                if (documentoProbatorio instanceof CURP) {
                    if (((CURP) documentoProbatorio).getNumFolioExtranjero() != null) {
                        documento.append(((CURP) documentoProbatorio)
                                .getNumFolioExtranjero());

                    } else {
                        documento.append("N/A");
                    }
                }
            }
    
    }
  
    private String getDatosDoctoProbatorio(Fisica personaRenapo) {

        Nacimiento actaNacimiento = personaRenapo.getActaNacimiento();
        log.debug("---CDA ACTA NACIMIENTO--- {}", actaNacimiento);
        StringBuffer documento = new StringBuffer();
        if (actaNacimiento != null) {
            
          getDatosDoctoProbatorioStep01(documento, actaNacimiento);
          
            documento
                    .append("\nN\u00famero de Libro: ")
                    .append(!actaNacimiento.getNoLibro().equals("0") ? normalizarCadenas(actaNacimiento
                            .getNoLibro()) : "");
            documento
                    .append("\nN\u00famero de Foja: ")
                    .append(!actaNacimiento.getNoFoja().equals("0") ? normalizarCadenas(actaNacimiento
                            .getNoFoja()) : "");
        } else if (personaRenapo.getDocumentosProbatorios() != null) {
            getDatosDoctoProbatorioStep02(personaRenapo, documento);
        }

        return documento.toString();

    }

    private String normalizarCadenas(Object cadena) {
        return pattern.matcher(
                Normalizer.normalize(cadena.toString(), Normalizer.Form.NFD))
                .replaceAll("");
    }
    
    
    private void getMotivoAclaracionSolicitudStep01( mx.gob.imss.ctirss.delta.model.asegurado.cda.MotivoAclaracion motivo
    , TiposAclaracionEnum tipoMotivo, List<TiposAclaracionEnum> motivosSeleccionados, MotivoAclaracion motivoAclaracion
    ){
      if (motivo.getIdMotivoAclaracion() == (tipoMotivo
              .getMotivoAclaracion().getId())) {
          motivosSeleccionados.add(tipoMotivo);
      }
      if (motivo.getIdMotivoAclaracion().longValue() == TiposAclaracionEnum.DESCUENTO_INDEBIDO_CREDITO
              .getMotivoAclaracion().getId()) {
          motivoAclaracion.setNumeroCredito(motivo
                  .getDetalleAclaracion().toUpperCase());
      }
      if (motivo.getIdMotivoAclaracion().longValue() == TiposAclaracionEnum.OTRO
              .getMotivoAclaracion().getId()) {
          motivoAclaracion.setOtroMotivo(motivo
                  .getDetalleAclaracion().toUpperCase());
      }

      if (motivo.getIdMotivoAclaracion().longValue() == TiposAclaracionEnum.ACLARACION_SALDO_SUPUESTA
              .getMotivoAclaracion().getId()) {
          motivoAclaracion
                  .setAclaracionSaldoSupuestaVivienda(true);
      }
    
    }
    
    private void getMotivoAclaracionSolicitudStep02(List<TiposAclaracionEnum> motivosSeleccionados,
            Field[] fields, MotivoAclaracion motivoAclaracion){
      for (TiposAclaracionEnum tipo : motivosSeleccionados) {
                String nombreMotivoSeleccionado = tipo.toString().replace("_",
                        "");
                for (Field campo : fields) {
                    if (campo.getName().equalsIgnoreCase(
                            nombreMotivoSeleccionado)) {
                        try {
                            campo.setAccessible(true);
                            campo.set(motivoAclaracion, true);
                        } catch (IllegalArgumentException e) {
                            log.error("Argumento ilegal", e);
                        } catch (IllegalAccessException e) {
                            log.error("Acceso ilegal", e);
                        }
                    }
                }
            }
    }

    private MotivoAclaracion getMotivoAclaracionSolicitud(
            List<mx.gob.imss.ctirss.delta.model.asegurado.cda.MotivoAclaracion> motivos) {
        MotivoAclaracion motivoAclaracion = new MotivoAclaracion();
        if (motivos != null && !motivos.isEmpty()) {
            log.debug("--MA-- motivos {}", motivos.size());
            Field[] fields = MotivoAclaracion.class.getDeclaredFields();
            TiposAclaracionEnum[] tipoMotivos = TiposAclaracionEnum.values();
            List<TiposAclaracionEnum> motivosSeleccionados = new ArrayList<TiposAclaracionEnum>();
            log.debug("--MA-- campos {}", fields.length);
            for (mx.gob.imss.ctirss.delta.model.asegurado.cda.MotivoAclaracion motivo : motivos) {
                for (TiposAclaracionEnum tipoMotivo : tipoMotivos) {
                    getMotivoAclaracionSolicitudStep01(motivo, tipoMotivo,
                            motivosSeleccionados, motivoAclaracion);
                }
            }
            log.debug("--MA-- motivos seleccionados {}",
                    motivosSeleccionados.size());
            getMotivoAclaracionSolicitudStep02(motivosSeleccionados, fields,
                    motivoAclaracion);
        }
        return motivoAclaracion;
    }

    private SubDelegacion getSubdelegacion(
            mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud sol) {
        SubDelegacion subDelegacion = new SubDelegacion();
        if (sol.getSubdelegacion() != null) {
            subDelegacion.setDescripcion(sol.getSubdelegacion().getClave()
                    + " - " + sol.getSubdelegacion().getDescripcion());
            subDelegacion
                    .setDomicilio(sol.getSubdelegacion().getDelegacion() != null ? sol
                            .getSubdelegacion().getDelegacion()
                            .getDescripcion()
                            : "");
        }
        return subDelegacion;
    }

    private String getCurpsHistoricas(List<String> curpsHistoricas) {
        StringBuilder stb = new StringBuilder("");
        if (curpsHistoricas != null && !curpsHistoricas.isEmpty()) {
            for (String curp : curpsHistoricas) {
                stb.append(curp);
                stb.append("\n");
            }
        }
        return stb.toString();
    }

    private boolean getErrorFuente(String observacionSINDO, int fuente) {
        log.debug("---CDA--- {}, {}", fuente, observacionSINDO);
        return observacionSINDO != null ? observacionSINDO.contains("|"
                + fuente + "|") : false;
    }

    public String getEstado(
            mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud sol) {

        TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) sol
                .getTramites().get(0);
        String estatus;

        if (tramite.getObservacionesSubdelegacion() != null
                && tramite.getObservacionesSubdelegacion().size() > 0) {
            ObservacionesSubdelegacion obs = tramite
                    .getObservacionesSubdelegacion().get(
                            tramite.getObservacionesSubdelegacion().size() - 1);

            estatus = EstadoNegocioEnum.obtenerDescripcionNegocio(obs
                    .getCveEstado());

            InicioTramite inicioTramite = flujoTrabajoBusiness
                    .obtenerTareaPorIdTramite(tramite.getTramiteId())
                    .getInicioTramite();

            if (DateUtils
                    .sumaDias(
                            deltaUtils.convertirStringTodate(inicioTramite != null
                                    && inicioTramite.getFechaSolicitud() != null ? inicioTramite
                                    .getFechaSolicitud() : DateUtils
                                            .dateToStringConFormato(
                                                    sol.getFechaSolicitud(),
                                                    "dd/MM/yyyy HH:mm:ss")), 40).before(
                            new Date())) {
                if (!estatus.equals(EstadoNegocioEnum.ATENDIDA.getDescripcion())
                        && !estatus.equals(EstadoNegocioEnum.ABANDONADA
                                .getDescripcion())) {
                    estatus = estatus + SOLICITUD_VENCIDA;
                }
            }
        } else {
            estatus = EstadoNegocioEnum.ASIGNADA.getDescripcion();
        }

        return estatus;

    }

    public ConsultaSolicitudRemote getConsultaSolicitudBusiness() {
        return consultaSolicitudBusiness;
    }

}
