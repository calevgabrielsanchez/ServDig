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

import mx.gob.imss.cit.cda.web.vo.DatosHistoriaLaboralVO;
import mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio;
import mx.gob.imss.cit.cda.web.vo.NSSVO;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.enums.OrigenCapturaCDAEnum;
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
     * Actualiza la informacion del asegurado en el xml  con la informacion de front datosHistorialLaboral
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
            LOGGER.debug("---CDA--- Tramite {}", solicitud.getTramites().get(0)
                    .getTramiteId());
            tramite = (TramiteCorreccionCurp) solicitud.getTramites().get(0);
        }
        tramite.setAsegurado(new Fisica());
        tramite.getAsegurado().setDocumentosProbatorios(registroCorreccionCurpUtil
                .cargarDatosTramiteDocumentAsegurado(tramite,
                        datosHistoriaLaboral));
        solicitudBusiness.actualizarXmlTramite(tramite);
        return solicitud;
    }

    
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
            }
            tramiteInicial.setListaNssCorreccion(listCorreccionNSS);
            LOGGER.error(
                    "---CDA--- tramite tiene correccionNSS {}",
                    tramiteInicial.getListaNssCorreccion());
            try {
                solicitudBusiness.actualizarXmlTramite(tramiteInicial);
            
            } catch (IllegalArgumentException e) {
                LOGGER.error(
                        "---CDA--- Ocurrio un error al actualizar el tramite {}",
                        e);
            } catch (TramiteNoEncontradoException ex) {
               LOGGER.error(
                        "---CDA--- Ocurrio un error al actualizar el tramite {}",
                        ex);
            }
        }

        return solicitud;
    }

    /**
     * Metodo que obtiene los nss del tramitecorrecioncurp 
     * @param solicitud
     * @param datosHistoriaLaboralVO
     * @return
     */
    public DatosHistoriaLaboralVO getPrecargaNssList(Solicitud solicitud,
            DatosHistoriaLaboralVO datosHistoriaLaboralVO) {
        List<NSSVO> listNss = new ArrayList<NSSVO>();
//        Long idDocumento = TipoDocumentoProbatorioEnum.DOCUMENTOS_CON_NSS
//                .getId();
        if (datosHistoriaLaboralVO.getNSSList() == null) {
                TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) solicitud.getTramites().get(0);
                LOGGER.debug("tramite listCORR"+ tramite.getListaNssCorreccion());
                if (tramite !=null && notEmpty(tramite.getListaNssCorreccion())) {
                    for(CorreccionNSS correccionNSS:tramite.getListaNssCorreccion()){
                    NSSVO nss = new NSSVO();
                    nss.setNSS(correccionNSS.getNss());
                    List<DocumentoProbatorio> documentoProbatorioList = new ArrayList<DocumentoProbatorio>();
                    for (mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio documentosprobatorio : correccionNSS.getDocumentosProbatorios()) {
//                        if (documentosprobatorio.getDocumentoPorTipo()
//                                .getTipoDocumentoProbatorio()
//                                .getIdTipoDocumentoProbatorio().intValue() == idDocumento
//                                .intValue()) {
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
//                    }
                    nss.setDocumentoProbatorioList(documentoProbatorioList);
                    
                    listNss.add(nss);
                    }
                }
            }
        LOGGER.error("---CDA--- listNss.size {}", listNss.size());
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
//                LOGGER.debug("---CDA--- Se toma el nss y documnetos del 2 tramite y se pasa al primero");
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
//
//    private Solicitud crearTramiteNuevo(Solicitud solicitud, NSSVO nss) {
//        try {
//            TramiteCorreccionCurp tramite = new TramiteCorreccionCurp();
//            tramite.setListaNSS(new ArrayList<String>());
//            tramite.getListaNSS().add(nss.getNSS());
//            solicitud = solicitudBusiness
//                    .asociarTramiteSolicitudPorEnum(
//                            solicitud,
//                            tramite,
//                            mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum.CORRECCION_DATOS_ASEGURADO,
//                            EstadoTramiteEnum.INICIADO);
//            for (int i = 0; i < solicitud.getTramites().size(); i++) {
//                TramiteCorreccionCurp tramiteAsociado = (TramiteCorreccionCurp) solicitud
//                        .getTramites().get(i);
//                if (tramiteAsociado.getTramiteId() == null) {
//                    LOGGER.debug("---CDA--- Tramite {}", tramiteAsociado);
//                    tramiteAsociado = registroCorreccionCurpUtil
//                            .cargarDatosTramiteNuevo(tramiteAsociado, nss);
//                    solicitud.getTramites()
//                            .set(i,
//                                    solicitudBusiness.crearTramiteASolicitud(
//                                            tramiteAsociado,
//                                            solicitud.getSolicitudId()));
//                }
//            }
//        } catch (SolicitudNoValidaException ex) {
//            LOGGER.error("---CDA--- Ocurrio un error general {}", ex);
//        }
//        return solicitud;
//    }

    /**
     * Valida que nss son nuevos y no se encuentran en un tramite existente
     * 
     * @param solicitud
     * @param listNss
     */
    private List<NSSVO> verificaNuevosNss(Solicitud solicitud,
            List<NSSVO> listNss) {
        List<NSSVO> nuevosNss = new ArrayList<NSSVO>();
        LOGGER.debug("---CDA---verificaNuevosNss tamanio inicio: ",
                listNss.size());
        Boolean bandera = Boolean.FALSE;
        for (NSSVO nss : listNss) {
                TramiteCorreccionCurp tramiteAsociado = (TramiteCorreccionCurp) solicitud.getTramites().get(0);
                if (notEmpty(tramiteAsociado.getListaNssCorreccion())){
                    for(CorreccionNSS correccionNSS : tramiteAsociado.getListaNssCorreccion()){
                        if(nss.getNSS().equals(correccionNSS.getNss())) {
                            LOGGER.debug(
                                    "---CDA--- verificaNuevosNss  el NSS {arg1} ya se encuentra en el tramite {arg2}",
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

    private static Boolean notEmpty(List lista) {
        return lista != null && !lista.isEmpty();
    }

    /**
     * Metodo que convierte objetos DocumentoProbatorio de model y DocumentoProbatorio de vo
     * @param file
     * @param docDescripcion
     * @param idDocporTipo
     * @param cveIdDocumento
     * @param tipoDocumento
     * @param idDocBoveda
     * @return
     */
    public DocumentoProbatorio convertDocumentoProbatorioVo(MultipartFile file,
            String docDescripcion, String idDocporTipo, String cveIdDocumento,
            String tipoDocumento, String idDocBoveda) {

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

}
