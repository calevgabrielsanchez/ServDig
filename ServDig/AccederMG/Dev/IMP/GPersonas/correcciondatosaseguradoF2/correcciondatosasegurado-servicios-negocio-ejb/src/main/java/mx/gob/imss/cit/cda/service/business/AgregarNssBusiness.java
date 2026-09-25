package mx.gob.imss.cit.cda.service.business;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.entity.CorreccionDatosAseguradoLocal;
import mx.gob.imss.cit.cda.service.entity.DetalleNssCdaLocal;
import mx.gob.imss.cit.cda.service.entity.DocumentoCdaLocal;
import mx.gob.imss.cit.cda.service.interfaces.AgregarNssRemote;
import mx.gob.imss.cit.cda.service.interfaces.BovedaRemote;
import mx.gob.imss.cit.cda.service.interfaces.ResponsableTareaRemote;
import mx.gob.imss.cit.cda.service.interfaces.ResponsablesDelegacionRemote;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TareaInicialException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TereaSinUsuarioAsignadoException;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.InicioTramite;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.TareaBandeja;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.ParticipantesEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.ProcesosNegocioEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoRemote;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.RegistrarDocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.BovedaCDAException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.enums.OrigenCapturaCDAEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;
import mx.gob.imss.ctirss.delta.model.gestion.nss.CorreccionNSS;
import mx.gob.imss.ctirss.delta.persistence.DitCorreccionDatosAsegurado;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleNss;
import mx.gob.imss.ctirss.delta.persistence.DitDoctoCdaNssBenRep;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoProbatorio;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Stateless(name = "agregarNssBusiness", mappedName = "agregarNssBusiness")
public class AgregarNssBusiness implements AgregarNssRemote {

    @EJB
    private CorreccionDatosAseguradoLocal correccionDatosAseguradoEntity;

    @EJB(name = "documentoProbatorioServiceBusiness", mappedName = "documentoProbatorioServiceBusiness")
    private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;

    @EJB(name = "responsableTareaBusiness", mappedName = "responsableTareaBusiness")
    private ResponsableTareaRemote responsableTareaBusiness;

    @EJB(name = "responsablesDelegacionBusiness", mappedName = "responsablesDelegacionBusiness")
    private ResponsablesDelegacionRemote responsablesDelegacionBusiness;

    @EJB(name = "flujoTrabajoBusiness", mappedName = "flujoTrabajoBusiness")
    private FlujoTrabajoRemote flujoTrabajoBusiness;

    @EJB(name = "solicitudBusiness", mappedName = "solicitudBusiness")
    private SolicitudBusinessRemote solicitudBusiness;
    
    @EJB
    private DocumentoCdaLocal documentoCdaLocal;
    
    @EJB
    private DetalleNssCdaLocal detalleNssCdaLocal;
    
    
    @EJB(name = "bovedaBusiness", mappedName = "bovedaBusiness")
    private BovedaRemote bovedaBusiness;

    private static final String SUBDELEGACION_SIN_RESPONSABLE = "SIN_RESPONSABLE";

    private final Logger log = LoggerFactory
            .getLogger(AgregarNssBusiness.class);

    @Override
    public TramiteCorreccionCurp crearTramite(Solicitud solicitud, Usuario usuarioResponsable,
            Long origen, String nss, List<DocumentoProbatorio> doumentoDocumentoProbatorios, String observacion) throws TramiteNoEncontradoException,
            RegistrarDocumentoProbatorioException, DocumentoProbatorioException, TareaInicialException, TereaSinUsuarioAsignadoException {
        log.debug("---CDA--- documento probatorio {}", doumentoDocumentoProbatorios.size());
        log.debug("---CDA--- es vacio {}", doumentoDocumentoProbatorios.isEmpty());
        solicitud = crearTramiteNuevo(solicitud, nss, doumentoDocumentoProbatorios, observacion);
        Iterator<Tramite> iterador = solicitud.getTramites().iterator();
        TramiteCorreccionCurp tramiteNuevo = new TramiteCorreccionCurp();
        Fisica personaRenapo = null;
        while (iterador.hasNext()) {
            TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) iterador
                    .next();
            if (tramite.getPersonaRENAPO() != null) {
                log.debug("---CDA--- Persona Renapo {}", tramite.getPersonaRENAPO().getCurp());
                personaRenapo = tramite.getPersonaRENAPO();
            }
            log.debug("---CDA--- tramite id {}", tramite.getTramiteId());
            if (tramite.getListaNSS().get(0).equals(nss) && tramite.getTramiteId() != null) {
                log.debug("---CDA--- Este es el tramite creado ", tramite.getTramiteId());
                tramiteNuevo = tramite;
            }
        }
        if (personaRenapo != null && personaRenapo.getCurp() != null && tramiteNuevo.getTramiteId()!=null) {
            log.debug("---CDA--- documento nss ");
            log.debug("---CDA--- Se asocian los documentos al tramite");
            procesaDocumentosGD(tramiteNuevo.getTramiteId(),
                    doumentoDocumentoProbatorios);
            log.debug("---CDA--- Se crea el detalle de nss");
            complementarTramite(solicitud, tramiteNuevo,
                    tramiteNuevo.getDocumentosProbatorios(),
                    OrigenCapturaCDAEnum.RESPONSABLE.getClave(),
                    personaRenapo.getCurp());
            log.debug("---CDA--- Se crea la tarea");
           // crearTarea(solicitud, tramiteNuevo, usuarioResponsable);
            solicitudBusiness.actualizarXmlTramite(tramiteNuevo);
        } else {
            log.error("---CDA--- No se creo el tramite");
            throw new TramiteNoEncontradoException(tramiteNuevo.getTramiteId());
        }
        return tramiteNuevo;
    }

    public TramiteCorreccionCurp complementarTramite(Solicitud solicitud,
            TramiteCorreccionCurp tramite,
            List<DocumentoProbatorio> documentos, Long origen, String refCurp)
            throws TramiteNoEncontradoException,
            RegistrarDocumentoProbatorioException, DocumentoProbatorioException {

        log.debug("---CDA--- correccionNSSCreando solicitud de CDA con el id del tramite {}",
                tramite.getTramiteId());
        TramiteCorreccionCurp correccionDatosAseg = correccionDatosAseguradoEntity
                .almacenarTramiteCDA(tramite.getTramiteId(), refCurp);

        log.debug("---CDA---  tramite de regreso {}", correccionDatosAseg);
        log.debug("---CDA--- iniciando el bloqueo del NSS");
        correccionDatosAseguradoEntity.bloquearNSS(
                tramite.getListaNSS().get(0),
                correccionDatosAseg.getIdTramiteCorreccionDatosAseg(), origen, null);
        log.debug("---CDA--- Se bloque el nss {}", tramite.getListaNSS().get(0));
        return correccionDatosAseg;

    }

    private void crearTarea(Solicitud solicitud, TramiteCorreccionCurp tramite, Usuario usuarioResponsable) throws TareaInicialException, TereaSinUsuarioAsignadoException {
        log.debug("Entra a crearTarea");
        InicioTramite inicioTramite = getInicioTramite(solicitud, usuarioResponsable.getFisica().getCurp(), tramite);
        solicitud.setSolicitante(usuarioResponsable);
        Long idTarea = responsableTareaBusiness.iniciarWorkFlow(
                ProcesosNegocioEnum.CDA.getId(),
                inicioTramite, solicitud);
        log.debug("El id de inicio tarea es {} ", idTarea.toString());

    }


    private void procesaDocumentosGD(Long idTramite,
            List<DocumentoProbatorio> documentosProbatorios)
            throws DocumentoProbatorioException {
        documentoProbatorioServiceBusinessRemote.procesaDocumentosGD(idTramite,
                null, documentosProbatorios);
    }

    private InicioTramite getInicioTramite(Solicitud solicitud,
            String curpResponsable, TramiteCorreccionCurp tramiteCDA) {
        InicioTramite inicioTramite = new InicioTramite();

        TareaBandeja tareaBandeja = flujoTrabajoBusiness
                .getTareaActivaPorIdTramite(tramiteCDA.getTramiteId());
        log.info("Tarea bandeja  {}", tareaBandeja);
        if (tareaBandeja != null && tareaBandeja.getInicioTramite() != null) {
            inicioTramite = tareaBandeja.getInicioTramite();
        }
        Map<String, Object> mapJson = new HashMap<String, Object>();
        mapJson.put("nssInvolucrados", tramiteCDA.getListaNSS().get(0));
        mapJson.put("origen", OrigenSolicitudEnum.VENTANILLA.getDesc());
        mapJson.put("responsableRegistro", curpResponsable);
        mapJson.put("subdelegacion", solicitud.getSubdelegacion().getId()
                .toString());
        mapJson.put(ParticipantesEnum.RESPONSABLE.getDescripcion(),
                curpResponsable);
        inicioTramite.setFolio(solicitud.getNoFolioSolicitud());
        inicioTramite.setIdTramite((tramiteCDA.getTramiteId()).intValue());
        inicioTramite.setData(generarJsonDataWf(mapJson));
        inicioTramite
                .setEstatus(curpResponsable
                        .equals(SUBDELEGACION_SIN_RESPONSABLE) ? EstadoTramiteEnum.SIN_RESPONSABLE
                        .getDescripcion()
                        : EstadoNegocioEnum
                                .obtenerDescripcionNegocio(EstadoTramiteEnum.EN_ESPERA_TRAMITADOR
                                        .getCodigo()));
        inicioTramite.setFechaSolicitud(convertirDateToString(new Date()));
        inicioTramite.setFechaActualizacion(convertirDateToString(new Date()));
        Map<String, String> participantes = new HashMap<String, String>();
        participantes.put(ParticipantesEnum.RESPONSABLE.getDescripcion(),
                curpResponsable.equals(SUBDELEGACION_SIN_RESPONSABLE) ? ""
                : curpResponsable);
        inicioTramite.setParticipantes(participantes);
        return inicioTramite;
    }

    private String generarJsonDataWf(Map<String, Object> data) {
        String jsonParams = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonParams = mapper.writeValueAsString(data);
        } catch (JsonGenerationException e) {
            log.error("Error al parsear el Json del workflow", e);
        } catch (JsonMappingException e) {
            log.error("Error al parsear el Json del workflow", e);
        } catch (IOException e) {
            log.error("Error al parsear el Json del workflow", e);
        }
        return jsonParams;
    }

    public Solicitud crearTramiteNuevo(Solicitud solicitud, String nss, List<DocumentoProbatorio> doumentoDocumentoProbatorios, String observacio) {
        try {
            TramiteCorreccionCurp tramite = new TramiteCorreccionCurp();
            tramite.setListaNSS(new ArrayList<String>());
            tramite.getListaNSS().add(nss);
            tramite.setDocumentosProbatorios(doumentoDocumentoProbatorios);
            tramite.setObservacion(observacio);
            log.debug("Solicitud a agregar el tramite ", solicitud.getSolicitudId());
            log.debug("tramite al que se agregara ", tramite);
            solicitud = solicitudBusiness
                    .asociarTramiteSolicitudPorEnum(
                            solicitud,
                            tramite,
                            mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum.CORRECCION_DATOS_ASEGURADO,
                            EstadoTramiteEnum.INICIADO);
            for (int i = 0; i < solicitud.getTramites().size(); i++) {
                TramiteCorreccionCurp tramiteAsociado = (TramiteCorreccionCurp) solicitud
                        .getTramites().get(i);
                if (tramiteAsociado.getTramiteId() == null) {
                    log.debug("---CDA--- Tramite {}", tramiteAsociado);
                    tramiteAsociado = preparaNuevoTramite(tramiteAsociado, nss, observacio, doumentoDocumentoProbatorios);
                    solicitud.getTramites()
                            .set(i,
                                    solicitudBusiness.crearTramiteASolicitud(
                                            tramiteAsociado,
                                            solicitud.getSolicitudId()));
                }
            }
        } catch (SolicitudNoValidaException ex) {
            log.error("---CDA--- Ocurrio un error general {}", ex);
        }
        return solicitud;
    }

    public TramiteCorreccionCurp preparaNuevoTramite(
            TramiteCorreccionCurp tramite, String nss, String observaciones, List<DocumentoProbatorio> documentoProbatorios) {
        List<String> listNss = new ArrayList<String>();
        listNss.add(nss);
        tramite.setListaNSS(listNss);
        tramite.setDocumentosProbatorios(documentoProbatorios);
        if (observaciones != null) {
            tramite.setObservacion(observaciones);
        }

        return tramite;
    }

    public String convertirDateToString(Date fecha) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String fechaString = df.format(fecha);
        return fechaString;
    }
    
    @Override
    public void actualizarDocumentosNss(Tramite tramite, List<DocumentoProbatorio> doumentoDocumentoProbatorios,
            List<DocumentoProbatorio> doumentoDocumentoProbatoriosEliminados)
            throws DocumentoProbatorioException{

            if(doumentoDocumentoProbatorios != null){
                for (Iterator iterator = doumentoDocumentoProbatorios.iterator(); iterator.hasNext();) {
                     DocumentoProbatorio  next = (DocumentoProbatorio)iterator.next();
                     log.debug("   docto" + next.getIdDocumentoProbatorio() );
                     log.debug("      BovedaDocId   " + next.getBovedaDocId() );
                     log.debug("      NomNombreDocumento" + next.getNomNombreDocumento() );
                     
                     
                }
                
                //Agrega el documento al trámite
                documentoProbatorioServiceBusinessRemote.procesaDocumentosGD(tramite.getTramiteId(), null, doumentoDocumentoProbatorios);
                tramite.getDocumentosProbatorios().addAll(doumentoDocumentoProbatorios);
            }

            log.error("Listado de los documentos que se van a eliminar");
            if(doumentoDocumentoProbatoriosEliminados != null){
                for (Iterator iterator = doumentoDocumentoProbatoriosEliminados.iterator(); iterator.hasNext();) {
                     DocumentoProbatorio next = (DocumentoProbatorio)iterator.next();
                     log.debug("   docto" + next.getIdDocumentoProbatorio() );
                     log.debug("      BovedaDocId" + next.getBovedaDocId() );
                     log.debug("      NomNombreDocumento" + next.getNomNombreDocumento() );
                     
                     //elimina el documento
                     documentoProbatorioServiceBusinessRemote.eliminarDocumentoProbatorioBoveda(next);
                }
            }
            try {
                solicitudBusiness.actualizarXmlTramite(tramite);
            } catch (TramiteNoEncontradoException e) {
                log.error("Error, no se encontro el tramite",e);
                throw new DocumentoProbatorioException();
            } catch (IllegalArgumentException e) {
                log.error("Error, argumentos invalidos",e);
                throw new DocumentoProbatorioException();
            }
    }
    
    public void agregarNss(TramiteCorreccionCurp tramite, CorreccionNSS correccionNSS) throws TramiteNoEncontradoException,
            RegistrarDocumentoProbatorioException, DocumentoProbatorioException  {
        log.debug("---CDA--- agregando NSS {}", correccionNSS.getNss());
        
        
         DitCorreccionDatosAsegurado ditCorreccionDatosAsegurado = 
                 correccionDatosAseguradoEntity.obtenerCorreccionDatosAsegurado(tramite.getTramiteId());
        
        log.info("---CDA--- iniciando el guardado del NSS {}" + 
                ditCorreccionDatosAsegurado.getCveIdCorreccionDatosAsegurado());
        //Verificamos si el NSS ya existe en el tramite de correccion, para determinar
        //si se debe crear o no
        DitDetalleNss ditDetalleNss =  detalleNssCdaLocal.buscarNssEnTramiteCorreccion(ditCorreccionDatosAsegurado.getCveIdCorreccionDatosAsegurado(),
                correccionNSS.getNss());
        
        //Si es nulo, entonces no se encontro y se debe insertr
        if(ditDetalleNss == null){
            
            ditDetalleNss =  correccionDatosAseguradoEntity.bloquearNSS(
                correccionNSS.getNss(), ditCorreccionDatosAsegurado.getCveIdCorreccionDatosAsegurado(), 
                OrigenCapturaCDAEnum.RESPONSABLE.getClave(), correccionNSS.getObservaciones() );
        }else{
            log.info("---CDA--- se encontro el detlle {}" + ditDetalleNss.getCveDetalleNss());
        }
        
        log.info("---CDA--- Se bloque el nss {}",correccionNSS.getNss());
            
        log.info("---CDA--- se almacenan los documentos del nss");
        this.registrarDocumentosDetalleNss(tramite.getTramiteId(), ditDetalleNss,
                   correccionNSS);
        solicitudBusiness.actualizarXmlTramite(tramite);
    }
    
    private void registrarDocumentosDetalleNss( Long idTramite,
            DitDetalleNss ditDetalleNss, CorreccionNSS correccionNSS ) 
            throws TramiteNoEncontradoException,
            RegistrarDocumentoProbatorioException, DocumentoProbatorioException {
        
        DitDoctoCdaNssBenRep  ditDoctoCdaNssBenRep  = null;
        
        log.info("---CDA--- registrando doctos del NSS ");
        if (correccionNSS.getDocumentosProbatorios() != null) {
            log.debug(
                        "---CDA--- doctos a registrar {} ", correccionNSS.getDocumentosProbatorios().size());
            log.debug(
                        "---CDA--- ingresa documentos del nss {} ", ditDetalleNss.getCveDetalleNss());
            for (DocumentoProbatorio documentoProbatorio : correccionNSS.getDocumentosProbatorios()) {
    
                ditDoctoCdaNssBenRep = null;
                //Validamos si el docto ya existe, buscando por id boveda, solo en caso de no existir lo agegamos
                List<DitDocumentoProbatorio> doctos = documentoCdaLocal.obtenerDocProbatorioByIdBoveda(documentoProbatorio.getBovedaDocId());
                if (doctos != null && !doctos.isEmpty()) {
                    log.info("---CDA--- se encontro el CveIdDocumentoProbatorio {}" + doctos.get(0).getCveIdDocumentoProbatorio());
                    ditDoctoCdaNssBenRep
                            = documentoCdaLocal.obtenerDocProbatorioCda(doctos.get(0).getCveIdDocumentoProbatorio());
                    if(ditDoctoCdaNssBenRep != null){
                        log.info("---CDA--- se encontro el CveIdDoctoCdaAseBenRep {}" + 
                            ditDoctoCdaNssBenRep.getCveIdDoctoCdaAseBenRep() );
                    }
                    
                }

                if (ditDoctoCdaNssBenRep == null) {
                    documentoProbatorio = documentoProbatorioServiceBusinessRemote.registraDocumento(documentoProbatorio);

                    log.info("---CDA--- ingresa el detalle de docto de CDA ");
                    documentoCdaLocal.guardarDoctoNss(idTramite, documentoProbatorio,
                            ditDetalleNss.getCorreccionDatosAsegurado().getCveIdCorreccionDatosAsegurado(),
                            ditDetalleNss.getCveDetalleNss(), false);

                }
            }
        }
            log.info("---CDA--- se finalizo la carga de archivos ");
    }
    
    
    public List<CorreccionNSS> elminarDocumento(String folio, String nss, String idBoveda) 
            throws SolicitudNoEncontradaException, BovedaCDAException, TramiteNoEncontradoException {
        List<CorreccionNSS> listaNss = null;
        
        log.debug(" Inicia servicio de negocio de elminarDocumento");
        
        //Obtener la solicitud (xml)
        Solicitud sol = solicitudBusiness.consultarPorFolioSolicitud(folio);
        TramiteCorreccionCurp tramite = (TramiteCorreccionCurp)sol.getTramites().get(0);
        listaNss = tramite.getListaNssCorreccion();
        
        //Eliminar el documento de Bóveda
        String confirmacion = bovedaBusiness.eliminarDocumento(idBoveda);
        
        //Eliminar el documento de BD
        documentoCdaLocal.eliminarDoctoNss(idBoveda);
        
        //Eliminar el documento del xml
        log.debug(" se tienen en el xml estos nss:" + listaNss.size());
        for (int i = 0; i < listaNss.size(); i++) {
            CorreccionNSS correccionNss = listaNss.get(i);
            if(correccionNss.getNss().equals(nss) ){
                log.debug(" se encontro el nss:" + nss );
                List<DocumentoProbatorio> doctos =  correccionNss.getDocumentosProbatorios();
                
                for (int j = 0; j < doctos.size(); j++) {
                    if (((DocumentoProbatorio)doctos.get(j)).getBovedaDocId().equals(idBoveda)){
                        doctos.remove(j);
                    }
                }
                
                //Si era el unico docto se elimina el NSS de BD y del xml
                if(doctos.isEmpty()){
                    documentoCdaLocal.eliminarNss(nss);
                    listaNss.remove(i);
                }
                
                break;
            }
        }
        log.debug(" se finaliza en el xml estos nss:" + listaNss.size());      
        
        //Actualiza el xml en base de datos
        tramite.setListaNssCorreccion(listaNss);
        solicitudBusiness.actualizarXmlTramite(tramite);
        
        log.debug(" se finaliza la actualizacion");      
        return listaNss;
    }

}
