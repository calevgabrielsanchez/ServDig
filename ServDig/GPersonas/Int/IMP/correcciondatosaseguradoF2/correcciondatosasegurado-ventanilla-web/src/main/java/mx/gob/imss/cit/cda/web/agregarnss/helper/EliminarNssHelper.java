package mx.gob.imss.cit.cda.web.agregarnss.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mx.gob.imss.cit.cda.core.events.UpdateEvent;
import mx.gob.imss.cit.cda.core.events.UpdatedEvent;
import mx.gob.imss.cit.cda.core.helper.UpdateHelper;
import mx.gob.imss.cit.cda.service.interfaces.BovedaRemote;
import mx.gob.imss.cit.cda.web.agregarnss.exception.AgregarNssException;
import mx.gob.imss.cit.cda.web.agregarnss.utils.AgregarNssutil;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.NSSAdicional;
import mx.gob.imss.cit.cda.web.utils.TramiteCorreccionUtil;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.BovedaCDAException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.enums.MensajesBovedaCDAEnum;
//import mx.gob.imss.cit.cda.web.vo.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component(BeansConstants.ELIMINAR_NSS_HELPER)
public class EliminarNssHelper implements
        UpdateHelper<NSSAdicional, mx.gob.imss.cit.cda.web.app.responsable.model.Solicitud> {

    private final Logger log = LoggerFactory.getLogger(CrearNssHelper.class);

    @Autowired
    @Qualifier("bovedaBusiness")
    private BovedaRemote bovedaBusiness;

    @Autowired
    private TramiteCorreccionUtil tramiteCorreccionUtil;

    @Autowired
    private SolicitudBusinessRemote solicitudBusiness;

    @Autowired
    @Qualifier("documentoProbatorioServiceBusiness")
    private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;

    @Override
    public UpdatedEvent<mx.gob.imss.cit.cda.web.app.responsable.model.Solicitud> requestEvent(
            UpdateEvent<NSSAdicional> requestUpdateEvent) {

        log.debug("******* Eliminar nss ********");
        AgregarNssutil agregarNssutil = new AgregarNssutil();
        Solicitud solicitud;
        try {
            solicitud = obtenerSolicitud(requestUpdateEvent.getData()
                    .getFolio());

            List<DocumentoProbatorio> documentos = new ArrayList<DocumentoProbatorio>();
            requestUpdateEvent.getData().getDocumentosProbatorios();

            Iterator<DocumentoProbatorio> iteradorDocumentos = documentos
                    .iterator();
            while (iteradorDocumentos.hasNext()) {
                DocumentoProbatorio documentoProbatorio = iteradorDocumentos
                        .next();
                try {
                    String confirmacion = bovedaBusiness
                            .eliminarDocumento(documentoProbatorio
                                    .getBovedaDocId());
                    if (!confirmacion.contains(MensajesBovedaCDAEnum.MSJ_BP5001
                            .getCodigo())
                            || !confirmacion
                                    .contains(MensajesBovedaCDAEnum.MSJ_BP5004
                                            .getCodigo())) {

                    }
                } catch (BovedaCDAException bce) {
                    log.error("---CDA-- Ocurrio un error {}", bce);
                }
                documentoProbatorioServiceBusinessRemote
                        .eliminarDocumentoProbatorio(documentoProbatorio);
                iteradorDocumentos.remove();
            }
//            if (documentos.isEmpty()) {
//                tramiteCorreccionUtil.cancelarNssTramiteXml(solicitud,
//                        requestUpdateEvent.getData().getNss());
                mx.gob.imss.cit.cda.web.app.responsable.model.Solicitud solicitudModel = new mx.gob.imss.cit.cda.web.app.responsable.model.Solicitud();
//                solicitudModel.setGridsDocumentosNssOrigen(agregarNssutil.actualizarModeloDocumentoNss(solicitud));
                return new UpdatedEvent<mx.gob.imss.cit.cda.web.app.responsable.model.Solicitud>(requestUpdateEvent.getKey(),
                        solicitudModel);
                
//            }
        } catch (AgregarNssException e) {
            
            log.error("Error EliminarNSSHelper requestEvent {}" + e);
        }
        return new UpdatedEvent<mx.gob.imss.cit.cda.web.app.responsable.model.Solicitud>(requestUpdateEvent.getKey(),
                new mx.gob.imss.cit.cda.web.app.responsable.model.Solicitud());
    }

    private Solicitud obtenerSolicitud(String folio) throws AgregarNssException {
        try {
            return solicitudBusiness.consultarPorFolioSolicitud(folio);
        } catch (SolicitudNoEncontradaException e) {
            log.error("CDA Error al obtener la solicitud", e);
            
            throw new AgregarNssException(e);
        }
    }
}
