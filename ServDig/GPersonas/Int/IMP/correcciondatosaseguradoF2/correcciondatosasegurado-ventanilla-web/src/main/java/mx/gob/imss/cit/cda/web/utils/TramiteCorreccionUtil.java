/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.utils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mx.gob.imss.cit.cda.web.app.common.model.enums.TipoSolicitanteEnum;
import mx.gob.imss.cit.cda.web.vo.DatosHistoriaLaboralVO;
import mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio;
import mx.gob.imss.cit.cda.web.vo.NSSVO;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.enums.OrigenCapturaCDAEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoProbatorioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Beneficiario;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.nss.CorreccionNSS;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author aldair.vidal
 */
@Component
public class TramiteCorreccionUtil {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(TramiteCorreccionUtil.class);

    @Autowired
    private RegistroCorreccionCurpUtil registroCorreccionCurpUtil;

    @Autowired
    @Qualifier("solicitudBusiness")
    private SolicitudBusinessRemote solicitudBusiness;
    
    public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    /**
     * Guarda informacion del documento probatorio de asegurados solo en el xml
     * @param solicitud
     * @param datosHistoriaLaboral
     * @return
     * @throws TramiteNoEncontradoException
     */
    public Solicitud actualizarXmlDocumentosAseg(Solicitud solicitud,
            DatosHistoriaLaboralVO datosHistoriaLaboral)
            throws TramiteNoEncontradoException {
        TramiteCorreccionCurp tramite = new TramiteCorreccionCurp();
        if (solicitud.getTramites() != null) {
            tramite = (TramiteCorreccionCurp) solicitud.getTramites().get(0);
        }
        if(datosHistoriaLaboral !=null){
            if(tramite.getAsegurado()==null){
                tramite.setAsegurado(new Fisica());
            }
            tramite.getAsegurado().setDocumentosProbatorios(registroCorreccionCurpUtil
                    .cargarDatosTramiteDocumentAsegurado(tramite,
                            datosHistoriaLaboral.getDocumentoProbatorioList()));
            tramite=getInteresadoTramite(tramite, datosHistoriaLaboral);
            solicitudBusiness.actualizarXmlTramite(tramite);
        }
        return solicitud;
    }

    /**
     * Crea solicitud 
     * @param solicitud
     * @param datosHistoriaLaboral
     * @return
     * @throws SolicitudNoValidaException
     */
    public Solicitud crearTramiteNss(Solicitud solicitud,
            DatosHistoriaLaboralVO datosHistoriaLaboral)
            throws SolicitudNoValidaException {
        LOGGER.debug("---CDA--- crearTramiteNss ");
        LOGGER.debug("---CDA--- datosHistoriaLaboral {} ",
                datosHistoriaLaboral.getNSSList());
        if (solicitud.getTramites() != null) {
//            List<NSSVO> listNsss = verificaNuevosNss(solicitud,
//                    datosHistoriaLaboral.getNSSList());
            
            List<CorreccionNSS> listCorreccionNSS = new ArrayList<CorreccionNSS>();
            TramiteCorreccionCurp tramiteInicial = (TramiteCorreccionCurp) solicitud
                        .getTramites().get(0);
            for (int i = 0; i < datosHistoriaLaboral.getNSSList().size(); i++) {
                NSSVO nss = datosHistoriaLaboral.getNSSList().get(i);
                CorreccionNSS correccionNSS = new CorreccionNSS();
                correccionNSS.setNss(nss.getNSS());
                correccionNSS.setDocumentosProbatorios(TransformerRegistroUtils.voToModelDocumentoProbatorio(nss.getDocumentoProbatorioList()));
                correccionNSS.setOrigen(OrigenCapturaCDAEnum.SOLICITUD.getClave());
                listCorreccionNSS.add(correccionNSS);
//                tramiteInicial.setListaNssCorreccion(listCorreccionNSS);
            }
            tramiteInicial.setListaNssCorreccion(listCorreccionNSS);
        }

        return solicitud;
    }

    /**
     * Carga la informacion de documentos probatorios del nss del xml de un solo TramiteCorreccionCurp
     * @param solicitud
     * @param datosHistoriaLaboralVO
     * @return
     */
    public DatosHistoriaLaboralVO getPrecargaNssList(Solicitud solicitud,
            DatosHistoriaLaboralVO datosHistoriaLaboralVO) {
        List<NSSVO> listNss = new ArrayList<NSSVO>();
        if (datosHistoriaLaboralVO.getNSSList() == null) {
            TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) solicitud
                    .getTramites().get(0);
            if (notEmpty(tramite.getListaNssCorreccion())) {
                for (CorreccionNSS correccionNSS : tramite
                        .getListaNssCorreccion()) {
                    NSSVO nss = new NSSVO();
                    nss.setNSS(correccionNSS.getNss());
                    List<DocumentoProbatorio> documentoProbatorioList = new ArrayList<DocumentoProbatorio>();
                    for (mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio documentosprobatorio : correccionNSS
                            .getDocumentosProbatorios()) {
                            LOGGER.debug("---CDA--- documentos probatorios por tramite "
                                    + documentosprobatorio
                                            .getDocumentoPorTipo()
                                            .getTipoDocumentoProbatorio()
                                            .getIdTipoDocumentoProbatorio()
                                    + " idtramite");
                            DocumentoProbatorio documento = new DocumentoProbatorio();
                            documento.setIdDocBoveda(documentosprobatorio
                                    .getBovedaDocId());
                            documento.setNombre(documentosprobatorio
                                    .getNomNombreDocumento());
                            documento.setCveIdDocumento(documentosprobatorio
                                    .getDocumentoPorTipo().getDocumento()
                                    .getCveIdDocumento());
                            documento.setDesDocumento(documentosprobatorio
                                    .getDocumentoPorTipo().getDocumento()
                                    .getDesDocumento());
                            documento.setTipoDocumento(documentosprobatorio
                                    .getDocumentoPorTipo()
                                    .getTipoDocumentoProbatorio()
                                    .getIdTipoDocumentoProbatorio());
                            documento
                                    .setIdDocumentoPorTipo(documentosprobatorio
                                            .getDocumentoPorTipo()
                                            .getIdDocumentoPorTipo());
                            documentoProbatorioList.add(documento);
                        }
                    nss.setDocumentoProbatorioList(documentoProbatorioList);
                    listNss.add(nss);
                }
            }
        }
        LOGGER.error("---CDA--- listNss.size", listNss.size());
        if (listNss.size() > 0) {
            datosHistoriaLaboralVO.setNSSList(listNss);
        }
        return datosHistoriaLaboralVO;
    }

    public Solicitud eliminarNssdelTramiteXml(Solicitud solicitud, String nss) {
        LOGGER.debug("---CDA--- Inicia eliminarNssdelTramiteXml");
            TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) solicitud
                    .getTramites().get(0);
            if (tramite.getListaNssCorreccion() != null && !tramite.getListaNssCorreccion().isEmpty()) {
                Iterator<CorreccionNSS> correccionIterator=tramite.getListaNssCorreccion().iterator();
                while (correccionIterator.hasNext()){
                    CorreccionNSS correccionNSS = correccionIterator.next();
                    if (nss.equals(correccionNSS.getNss())) {
                        correccionIterator.remove();
                        LOGGER.debug("---CDA--- Se removio el nss {} del tramite", nss);
                    }
                }
                
            } else {
                LOGGER.error("---CDA--- La lista de nss esta vacia");
            }
        solicitud.getTramites().set(0, tramite);
        try {
            solicitudBusiness.actualizarXmlTramite(tramite);
        } catch (TramiteNoEncontradoException e) {
            LOGGER.error("---CDA--- No se encontro el tramite");
        } catch (IllegalArgumentException e) {
            LOGGER.error("---CDA--- Errores en el tramite a actualizar");
        }
        return solicitud;
    }

//    public TramiteCorreccionCurp cancelarTramiteXml(
//            TramiteCorreccionCurp tramite) {
//        LOGGER.debug("---CDA--- Se actualiza el tramite a cancelado");
//        tramite.getEstadoTramite().setIdEstadoTramitePersona(
//                EstadoTramiteEnum.CANCELADO.getCodigo());
//        try {
//            solicitudBusiness.cancelarTramite(tramite);
//        } catch (TramiteNoEncontradoException e) {
//            LOGGER.error("---CDA--- No se encontro el Tramite {}", e);
//        } catch (IllegalArgumentException e) {
//            LOGGER.error(
//                    "---CDA--- Ocurrio un error al actualizar el tramite {}", e);
//        }
//        return tramite;
//    }

//    public Solicitud editaPrimerTramite(Solicitud solicitud, String nss) {
//        TramiteCorreccionCurp tramiteCurp = (TramiteCorreccionCurp) solicitud
//                .getTramites().get(0);
//        LOGGER.debug("---CDA--- Se elimina el nss del tramite ");
//        tramiteCurp.setListaNSS(null);
//        Iterator<mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio> iterador = tramiteCurp
//                .getDocumentosProbatorios().iterator();
//        LOGGER.debug("---CDA--- Se remueven los documentos probatorios que no son del tipo asegurado");
//        while (iterador.hasNext()) {
//            if (iterador.next().getDocumentoPorTipo()
//                    .getTipoDocumentoProbatorio()
//                    .getIdTipoDocumentoProbatorio() == TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS
//                    .getId()) {
//                iterador.remove();
//            }
//        }
//        try {
//            if (solicitud.getTramites().size() > 1) {
//                LOGGER.debug("---CDA--- Se toma el nss y documentos del 2 tramite y se pasa al primero");
//                TramiteCorreccionCurp tramite2 = (TramiteCorreccionCurp) solicitud
//                        .getTramites().get(1);
//                tramiteCurp.setListaNSS(new ArrayList<String>());
//                tramiteCurp.getListaNSS().add(tramite2.getListaNSS().get(0));
//                tramiteCurp.getDocumentosProbatorios().addAll(
//                        tramite2.getDocumentosProbatorios());
//                solicitud.getTramites().remove(1);
//                LOGGER.debug("---CDA--- Se cancela el segundo tramite");
//                cancelarTramiteXml(tramite2);
//            }
//            solicitudBusiness.actualizarXmlTramite(tramiteCurp);
//        } catch (TramiteNoEncontradoException ex) {
//            java.util.logging.Logger.getLogger(
//                    TramiteCorreccionUtil.class.getName()).log(Level.SEVERE,
//                    null, ex);
//        } catch (IllegalArgumentException ex) {
//            java.util.logging.Logger.getLogger(
//                    TramiteCorreccionUtil.class.getName()).log(Level.SEVERE,
//                    null, ex);
//        }
//
//        return solicitud;
//    }

    public Solicitud crearTramiteNuevo(Solicitud solicitud, NSSVO nss) {
        try {
            TramiteCorreccionCurp tramite = new TramiteCorreccionCurp();
            tramite.setListaNSS(new ArrayList<String>());
            tramite.getListaNSS().add(nss.getNSS());
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
                    LOGGER.debug("---CDA--- Tramite {}", tramiteAsociado);
                    tramiteAsociado = registroCorreccionCurpUtil
                            .cargarDatosTramiteNuevo(tramiteAsociado, nss);
                    solicitud.getTramites()
                            .set(i,
                                    solicitudBusiness.crearTramiteASolicitud(
                                            tramiteAsociado,
                                            solicitud.getSolicitudId()));
                }
            }
        } catch (SolicitudNoValidaException ex) {
            LOGGER.error("---CDA--- Ocurrio un error general {}", ex);
        }
        return solicitud;
    }

    /**
     * Valida que nss son nuevos y no se encuentran en un tramite existente
     * 
     * @param solicitud
     * @param listNss
     */
    private List<NSSVO> verificaNuevosNss(Solicitud solicitud,
            List<NSSVO> listNss) {
        List<NSSVO> nuevosNss = new ArrayList<NSSVO>();
        LOGGER.debug("---CDA---verificaNuevosNss tamanio inicio: ",listNss.size());
        for (NSSVO nss : listNss) {
            Boolean bandera = Boolean.FALSE;
            TramiteCorreccionCurp tramiteAsociado = (TramiteCorreccionCurp) solicitud.getTramites().get(0);
            if (notEmpty(tramiteAsociado.getListaNssCorreccion())) {
                    for (CorreccionNSS correccionNSS : tramiteAsociado.getListaNssCorreccion()) {
                       if(nss.getNSS().equals(correccionNSS.getNss())){
                    LOGGER.debug("---CDA--- verificaNuevosNss  el NSS {arg1} ya se encuentra en el tramite {arg2}",
                            nss, tramiteAsociado.getTramiteId());
                    bandera = Boolean.TRUE;
                    break;
                       }
                }
            }
            if (!bandera) {
                nuevosNss.add(nss);
            }
        }
        LOGGER.debug("---CDA--- tamanio final: ", nuevosNss.size());
        return nuevosNss;
    }
    
    /**
     * Metodo para el recargado o recuperacion del solicitante, 
     * si la informacion del front es igual a la que se encuentra en el tramite, solo se actualiza la bandera de defuncion
     * en caso de que sea nueva se agrega al xml y se borra lo anterior respecto al interezado(informacion del representate/beneficiario)
     * @param tramite
     * @param documenHistoriaLaboralVO
     * @return
     */
    private TramiteCorreccionCurp getInteresadoTramite(TramiteCorreccionCurp tramite,
            DatosHistoriaLaboralVO documenHistoriaLaboralVO) {
        if(documenHistoriaLaboralVO.getTipoSolicitante() !=null){
            if (documenHistoriaLaboralVO.getTipoSolicitante().equals(
                    TipoSolicitanteEnum.BENEFICIARIO.getDescripcion())) {
                    tramite.setBeneficiario(constructBeneficiario(tramite, documenHistoriaLaboralVO));
                    tramite.setRepresentante(null);
            }else if (documenHistoriaLaboralVO.getTipoSolicitante().equals(
                    TipoSolicitanteEnum.REPRESENTANTE_LEGAL.getDescripcion())) {
                tramite.setBeneficiario(null);
                tramite.setRepresentante(constructRepresentanteLeg(tramite));
            }else if (documenHistoriaLaboralVO.getTipoSolicitante().equals(
                    TipoSolicitanteEnum.ASEGURADO_PENSIONADO.getDescripcion())){
                tramite.setBeneficiario(null);
                tramite.setRepresentante(null);
                LOGGER.debug("No se encontro beneficiario ni representante legal en xml, se agrega");
              }
        }else{
            LOGGER.debug("No tiene datos de interesado");
        }
        tramite.setIndicadorDefuncion(documenHistoriaLaboralVO.isDefuncion());
            
        return tramite;
    }
    
    /**
     *  Verifica si el objeto beneficiario en TramiteCorrecionCurp viene vacio
     * @param tramite
     * @param documenHistoriaLaboralVO
     * @return
     */
    private Beneficiario constructBeneficiario(TramiteCorreccionCurp tramite,
            DatosHistoriaLaboralVO documenHistoriaLaboralVO) {
        Beneficiario beneficiario = new Beneficiario();
        if(tramite.getBeneficiario() != null){
            beneficiario=tramite.getBeneficiario();
        }else{
            beneficiario= new Beneficiario();
            if(documenHistoriaLaboralVO.getTipoBeneficiario()!=null){
                beneficiario.setTipoBeneficiario(documenHistoriaLaboralVO.getIdtipoSolicitante());
            }
        }
        return tramite.getBeneficiario() != null ? tramite.getBeneficiario():new Beneficiario();
    }
    
    /**
     * Verifica si el objeto representante en TramiteCorrecionCurp viene vacio
     * @param tramite
     * @return
     */
    private Fisica constructRepresentanteLeg(TramiteCorreccionCurp tramite) {
        return tramite.getRepresentante() != null ? tramite.getRepresentante():new Fisica();
    }

    /**
     * Actualiza el xml despues de agregar los documentos de BENEFICIARIO/REPRESNETANTE LEGAL en temporal
     * @param solicitud
     * @param datosBeneficiario
     * @param persona
     * @return
     * @throws TramiteNoEncontradoException
     */
    public Solicitud actualizarXmlDocumentosBeneficiarioRepresentante(
            Solicitud solicitud, DatosHistoriaLaboralVO datosBeneficiario,
            Fisica persona) throws TramiteNoEncontradoException {
        LOGGER.debug("---CDA---verificaNuevosNss tamanio inicio: ",
                datosBeneficiario.getTipoSolicitante());
        TramiteCorreccionCurp tramite = new TramiteCorreccionCurp();
        if (solicitud.getTramites() != null) {
            tramite = (TramiteCorreccionCurp) solicitud.getTramites().get(0);
        }
        if (datosBeneficiario.getTipoSolicitante().equals(
                TipoSolicitanteEnum.BENEFICIARIO.getDescripcion())) {
            tramite.setBeneficiario(new Beneficiario());
            tramite.getBeneficiario().setCurp(persona.getCurp());
            tramite.getBeneficiario().setNombre(persona.getNombre());
            tramite.getBeneficiario().setPrimerApellido(persona.getPrimerApellido());
            tramite.getBeneficiario().setSegundoApellido(persona.getSegundoApellido());
            tramite.getBeneficiario().setSexo(persona.getSexo());
            tramite.getBeneficiario().setTelefonoFijo(persona.getTelefonoFijo());
            tramite.getBeneficiario().setTelefonoMovil(persona.getTelefonoMovil());
            tramite.getBeneficiario().setCorreoElectronico(persona.getCorreoElectronico());
            tramite.getBeneficiario().setCurpsHistoricas(persona.getCurpsHistoricas());
            tramite.getBeneficiario().setTipoBeneficiario(getidTipoBeneficiario(datosBeneficiario.getTipoBeneficiario()));
            tramite.getBeneficiario().setDocumentosProbatorios(registroCorreccionCurpUtil
                    .cargarDatosTramiteDocumentBeneficiario(datosBeneficiario));
        } else if (datosBeneficiario.getTipoSolicitante().equals(
                TipoSolicitanteEnum.REPRESENTANTE_LEGAL.getDescripcion())) {
            tramite.setRepresentante(persona);
            tramite.getRepresentante().setDocumentosProbatorios(registroCorreccionCurpUtil
                .cargarDatosTramiteDocumentBeneficiario(datosBeneficiario));
        }
        tramite.setIndicadorDefuncion(datosBeneficiario.isDefuncion());
        solicitudBusiness.actualizarXmlTramite(tramite);
        solicitud.getTramites().set(0, tramite);
        return solicitud;
    }

    /**
     * Metodo que obtiene la informacion de temporal del xml del beneficiario/representante
     * @param solicitud
     * @param datosHistoriaLaboralVO
     * @param tipoSolicitante
     * @return
     */
    public DatosHistoriaLaboralVO getPreCargaInteresado(Solicitud solicitud,
            DatosHistoriaLaboralVO datosHistoriaLaboralVO,
            String tipoSolicitante) {
        TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) solicitud
                .getTramites().get(0);
        
        if (tramite.getBeneficiario() != null) {
            if (tipoSolicitante.equals(TipoSolicitanteEnum.BENEFICIARIO.getDescripcion())) {
                if(tramite.getBeneficiario().getTipoBeneficiario()!=null){
                    LOGGER.debug("---CDA--- TipoBeneficiario " + tramite.getBeneficiario().getTipoBeneficiario());
                    if (isTipoBeneficiario(tramite.getBeneficiario().getTipoBeneficiario())) {
                        datosHistoriaLaboralVO
                                .setTipoBeneficiario(getDescripcionTipoBeneficiario(tramite.getBeneficiario().getTipoBeneficiario()));
                    }
                }
                datosHistoriaLaboralVO.setTipoSolicitante(tipoSolicitante);
                datosHistoriaLaboralVO.setDocumentoProbatorioList(obtenerListDocumentos(tramite.getBeneficiario().getDocumentosProbatorios()));
            }
        }else if(tramite.getRepresentante() != null){
            if (tipoSolicitante.equals(TipoSolicitanteEnum.REPRESENTANTE_LEGAL.getDescripcion())) {
                    datosHistoriaLaboralVO.setTipoSolicitante(tipoSolicitante);
                    datosHistoriaLaboralVO.setTipoBeneficiario("");
                    datosHistoriaLaboralVO.setDocumentoProbatorioList(obtenerListDocumentos(tramite.getRepresentante().getDocumentosProbatorios()));
            } 
        }  
        LOGGER.debug("---CDA--- tiposolicitante " + tipoSolicitante);
        return datosHistoriaLaboralVO;
    }

    /**
     * Valida la si el id coincide con algun tipo de beneficiario
     * @param idTipoBeneficiario
     * @return
     */
    private Boolean isTipoBeneficiario(Integer idTipoBeneficiario) {
        return idTipoBeneficiario==TipoSolicitanteEnum.CONCUBINO.getId()
                || idTipoBeneficiario==TipoSolicitanteEnum.CONYUGE.getId()
                || idTipoBeneficiario==TipoSolicitanteEnum.DESCENDIENTE.getId()
                || idTipoBeneficiario==TipoSolicitanteEnum.PADRES.getId();
    }
    
    /**
     * Metodo que obtiene la descripcion del interesado por id de enum
     * @param idTipoBeneficiario
     * @return
     */
    private String getDescripcionTipoBeneficiario(Integer idTipoBeneficiario) {
        TipoSolicitanteEnum tiposolicitante = TipoSolicitanteEnum.getTipoSolicitudEnumById(idTipoBeneficiario);
        return tiposolicitante != null ? tiposolicitante.getDescripcion() :"N/A";
    }
    
    /**
     * Obtiene los documentos del beneficiario/representante
     * @param listDocumentos
     * @return
     */
    private List<DocumentoProbatorio> obtenerListDocumentos(List<mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio> listDocumentos){
        List<DocumentoProbatorio> documentoProbatorioList = new ArrayList<DocumentoProbatorio>();
        for (mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio documentosprobatorio : listDocumentos) {
            DocumentoProbatorio documento = new DocumentoProbatorio();
            documento.setIdDocBoveda(documentosprobatorio
                    .getBovedaDocId());
            documento.setNombre(documentosprobatorio
                    .getNomNombreDocumento());
            documento.setCveIdDocumento(documentosprobatorio
                    .getDocumentoPorTipo().getDocumento()
                    .getCveIdDocumento());
            documento.setDesDocumento(documentosprobatorio
                    .getDocumentoPorTipo().getDocumento()
                    .getDesDocumento());
            documento.setTipoDocumento(documentosprobatorio
                    .getDocumentoPorTipo().getTipoDocumentoProbatorio()
                    .getIdTipoDocumentoProbatorio());
            documento.setIdDocumentoPorTipo(documentosprobatorio
                    .getDocumentoPorTipo().getIdDocumentoPorTipo());
            documentoProbatorioList.add(documento);
        }
        return documentoProbatorioList;
    }

    /**
     * Obtiene el la informacion de renapo del beneficiario/responsable que se almaceno en sesion en la solicitud
     * @param solicitud
     * @return
     */
    public Fisica getCargaCURPInteresado(Solicitud solicitud) {
        TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) solicitud
                .getTramites().get(0);
        LOGGER.debug("getCargaCURPInteresado: ",tramite.getRepresentante());
        LOGGER.debug("getCargaCURPBeneficiario: ",tramite.getBeneficiario());
        return tramite.getRepresentante()!= null ? tramite.getRepresentante(): tramite.getBeneficiario() != null ? tramite.getBeneficiario():null;
    }
    
    /**
     * Metodo que obtiene la descripcion del interesado por id de enum
     * @param idTipoBeneficiario
     * @return
     */
    private Integer getidTipoBeneficiario(String descTipoBeneficiario) {
        TipoSolicitanteEnum tiposolicitante = TipoSolicitanteEnum.getTipoSolicitudEnumByDescripcion(descTipoBeneficiario);
        return tiposolicitante != null ? tiposolicitante.getId() :null;
    }

    private static Boolean notEmpty(List lista) {
        return lista != null && !lista.isEmpty();
    }
    
    /**
     * Convierte un conjunto de datos en un documento probatorio de vo
     * @param file
     * @param docDescripcion
     * @param idDocporTipo
     * @param cveIdDocumento
     * @param tipoDocumento
     * @param idDocBoveda
     * @return
     */
    public DocumentoProbatorio convertDocumentoProbatorioVo(MultipartFile file, String docDescripcion,String idDocporTipo, String cveIdDocumento, 
            String tipoDocumento,String idDocBoveda){
        
        byte[] ptext = docDescripcion.getBytes(ISO_8859_1);
        String value = new String(ptext, UTF_8);

        byte[] conv = file.getOriginalFilename().getBytes(ISO_8859_1);
        String valueConv = new String(conv, UTF_8);
        
        String nombreCompleto = null;
        nombreCompleto = idDocporTipo + "_" + valueConv;
        
        DocumentoProbatorio documento = new DocumentoProbatorio();
        documento.setIdDocBoveda(idDocBoveda);
        documento.setIdDocumentoPorTipo(new Long(idDocporTipo));
        documento.setCveIdDocumento(new Long(cveIdDocumento));
        documento.setNombre(nombreCompleto);
        documento.setDesDocumento(value);
        documento.setTipoDocumento(Integer.valueOf(tipoDocumento));
        return documento;
    }
    
    public DatosHistoriaLaboralVO getPrecargaAsegurado(DatosHistoriaLaboralVO datosHistoriaLaboral,Solicitud solicitud){
        TramiteCorreccionCurp tramiteCurp=null;
        if(solicitud.getTramites().get(0)!=null){
            tramiteCurp = (TramiteCorreccionCurp) solicitud.getTramites().get(0);
        }
        if(tramiteCurp.getAsegurado()!=null){
            LOGGER.debug("--CDA--beneficiario: {}", tramiteCurp.getBeneficiario());
            if(tramiteCurp.getBeneficiario()!=null){
                datosHistoriaLaboral
                                .setTipoSolicitante(TipoSolicitanteEnum.BENEFICIARIO
                                        .getDescripcion());
                       
            }else if(tramiteCurp.getRepresentante() != null){
                datosHistoriaLaboral
                .setTipoSolicitante(TipoSolicitanteEnum.REPRESENTANTE_LEGAL
                        .getDescripcion());
            }else{
                datosHistoriaLaboral
                .setTipoSolicitante(TipoSolicitanteEnum.ASEGURADO_PENSIONADO
                        .getDescripcion());
                
            }
            LOGGER.debug("--CDA--beneficiario: {}", datosHistoriaLaboral
                    .getTipoSolicitante());
            if (tramiteCurp.getAsegurado().getDocumentosProbatorios()!=null) {
                List<DocumentoProbatorio> documentoProbatorioList = new ArrayList<DocumentoProbatorio>();
                for (mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio documentosprobatorio : tramiteCurp.getAsegurado().getDocumentosProbatorios()) {
                    LOGGER.debug("--CDA--tipo DOCUMENTOPROBATORIOS Aseg: {}",
                            documentosprobatorio.getDocumentoPorTipo());
                   
                        DocumentoProbatorio documento = new DocumentoProbatorio();
                        documento.setIdDocBoveda(documentosprobatorio
                                .getBovedaDocId());
                        documento.setNombre(documentosprobatorio
                                .getNomNombreDocumento());
                        documento.setCveIdDocumento(documentosprobatorio
                                .getDocumentoPorTipo().getDocumento()
                                .getCveIdDocumento());
                        documento.setDesDocumento(documentosprobatorio
                                .getDocumentoPorTipo().getDocumento()
                                .getDesDocumento());
                        documento.setTipoDocumento(documentosprobatorio
                                .getDocumentoPorTipo().getTipoDocumentoProbatorio()
                                .getIdTipoDocumentoProbatorio());
                        documento.setIdDocumentoPorTipo(documentosprobatorio
                                .getDocumentoPorTipo().getIdDocumentoPorTipo());
                        documentoProbatorioList.add(documento);
                }
                datosHistoriaLaboral
                        .setDocumentoProbatorioList(documentoProbatorioList);
            }
        }

        datosHistoriaLaboral.setDefuncion(tramiteCurp.isIndicadorDefuncion());
        
        LOGGER.debug("--CDA-- DOCUMENTOPROBATORIOS Aseg: {}",
                datosHistoriaLaboral.getDocumentoProbatorioList());
        return datosHistoriaLaboral;
    }

}
