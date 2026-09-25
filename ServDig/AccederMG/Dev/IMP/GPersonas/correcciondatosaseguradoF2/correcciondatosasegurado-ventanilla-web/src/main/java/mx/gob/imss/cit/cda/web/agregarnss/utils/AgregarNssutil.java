package mx.gob.imss.cit.cda.web.agregarnss.utils;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.cit.cda.service.interfaces.ConsultaSolicitudRemote;
import mx.gob.imss.cit.cda.web.app.responsable.model.Documento;
import mx.gob.imss.cit.cda.web.app.responsable.model.DocumentosNss;
import mx.gob.imss.cit.cda.web.support.model.Page;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.model.enums.OrigenCapturaCDAEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoProbatorioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class AgregarNssutil {
    
    private final Logger log = LoggerFactory.getLogger(AgregarNssutil.class);
    
    @Autowired
    @Qualifier("consultaSolicitudBusiness")
    private ConsultaSolicitudRemote consultaSolicitudBusiness;
    
    public List<Page<DocumentosNss>> actualizarModeloDocumentoNss(
            Solicitud solicitud) {
        List<Page<DocumentosNss>> gridsDocumentosNssOrigen = new ArrayList<Page<DocumentosNss>>();

        try {

            for (Tramite tramite : solicitud.getTramites()) {
                TramiteCorreccionCurp tramiteCurp = (TramiteCorreccionCurp) tramite;
                gridsDocumentosNssOrigen.add(getDocumentosProbatoriosNssOrigen(
                        tramiteCurp, solicitud.getSolicitudId().toString(),
                        solicitud.getNoFolioSolicitud()));
            }
        } catch (DocumentoProbatorioException e) {
            log.debug(
                    "---DocumentoProbatorioException--- DocumentosProbatorios por NSS y Origen ",
                    e.getMessage());
        }

        return gridsDocumentosNssOrigen;
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
        String origen = consultaSolicitudBusiness
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
        documentosNss.setNss(tramite.getListaNSS().get(0));
        documentosNss.setDocumentos(listaDocumentos);
        documentosProbatorios.add(documentosNss);

        gridDocumentosNSS.setData(documentosProbatorios);
        gridDocumentosNSS.setTotalOfRecords(documentosProbatorios.size());
        return gridDocumentosNSS;

    }

    private Documento armarDocumento(DocumentoProbatorio documentoProbatorio,
            String solicitudId, String numeroFolio) {
        Documento documento = new Documento();
        documento.setIdPersona(solicitudId);

        String[] n = documentoProbatorio.getNomNombreDocumento().split("\\.");
        documento.setExtension(n[n.length - 1]);
        documento.setNombreArchivo(documentoProbatorio.getNomNombreDocumento());
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

}
