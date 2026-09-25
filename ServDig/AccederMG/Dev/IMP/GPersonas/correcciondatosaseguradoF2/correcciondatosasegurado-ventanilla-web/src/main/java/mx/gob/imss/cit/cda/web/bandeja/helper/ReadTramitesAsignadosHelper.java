package mx.gob.imss.cit.cda.web.bandeja.helper;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import mx.gob.imss.cit.cda.core.events.ReadEvent;
import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.bandeja.vo.RequestSolicitudBandejaPage;
import mx.gob.imss.cit.cda.web.bandeja.vo.SolicitudBandeja;
import mx.gob.imss.cit.cda.web.common.helper.ReadTramitesAsignadosCommonHelper;
import mx.gob.imss.cit.cda.web.constants.FlujoTrabajoConstants;
import mx.gob.imss.cit.cda.web.constants.RolUsuarioEnum;
import mx.gob.imss.cit.cda.web.support.model.Page;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.DataPage;

import org.springframework.stereotype.Component;

/**
 *
 * @author yisus
 */
@Component(BeansConstants.READ_TRAMITES_ASIGNADOS_HELPER)
public class ReadTramitesAsignadosHelper extends ReadTramitesAsignadosCommonHelper implements ReadHelper<RequestSolicitudBandejaPage, Page<SolicitudBandeja>> {

    @SuppressWarnings("unchecked")
    @Override
    public ReadEvent<Page<SolicitudBandeja>> requestEvent(RequestReadEvent<RequestSolicitudBandejaPage> requestReadEvent) {

        getLogger().info("[*]---CDA--- Usuario a buscar {}",requestReadEvent.getUserProfile().getPerfil().equals(RolUsuarioEnum.VENTANILLA.getRol()) ? requestReadEvent
                                .getUserProfile().getUsuario(): (FlujoTrabajoConstants.BPM_ADMIN + requestReadEvent.getUserProfile().getIdSubdelegacion()));
        getLogger().info("[*]---CDA--- Rol {}",requestReadEvent.getUserProfile().getPerfil());

        requestReadEvent.getData().getFilter().setCurp(getCurp(requestReadEvent.getData().getFilter().getCurp()));

        DataPage dataPage = getReadTramitesCommonUtils().prepararFiltros( requestReadEvent);

        dataPage = realizarBusqueda(requestReadEvent, dataPage);


        getLogger().debug( "[*] ---CDA--- registros a regresar {}",dataPage != null && dataPage.getData() != null ? dataPage.getData().size() : 0);

        getLogger().debug("[*] realizarBusqueda");

        try {
            Page<SolicitudBandeja> page = getReadTramitesCommonUtils().transformPaginaBandeja(dataPage,requestReadEvent.getUserProfile().getUsuario(),
                                                                                              requestReadEvent.getData().getPantallaConsulta(),
                                                                                              requestReadEvent.getData().getFolioConsulta());

            getLogger().debug("[*] Despues de realizar peticion convertirPaginaBandeja"+page);

            return new ReadEvent<Page<SolicitudBandeja>>(requestReadEvent.getKey(), page);
        } catch (Exception e) {
            getLogger().error("---CDA---", e);
            return ReadEvent.notFound(requestReadEvent.getKey());
        }

    }

    /**
     *
     * Consulta por default o por filtros
     */
    protected DataPage obtenerTareas(RequestReadEvent<RequestSolicitudBandejaPage> requestReadEvent,DataPage dataPage, Long idProceso) {
        DataPage dataPageReturn = null;
        getLogger().debug("[*] ---CDA---Filtros {}",requestReadEvent.getData().getFilter());
        dataPageReturn = getConsultaBandejaBusiness().obtenerTareasCDA(dataPage,requestReadEvent.getUserProfile().getUsuario(),requestReadEvent.getUserProfile().getIdSubdelegacion().toString(), idProceso);
        return dataPageReturn;
    }

}
