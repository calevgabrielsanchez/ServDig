package mx.gob.imss.cit.cda.web.bandeja.helper;

import mx.gob.imss.cit.cda.core.events.ReadEvent;
import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.bandeja.utils.ReadHistoricoSolicitudUtils;
import mx.gob.imss.cit.cda.web.bandeja.vo.FilterBandeja;
import mx.gob.imss.cit.cda.web.bandeja.vo.RequestSolicitudBandejaPage;
import mx.gob.imss.cit.cda.web.bandeja.vo.SolicitudBandeja;
import mx.gob.imss.cit.cda.web.common.helper.ReadTramitesAsignadosCommonHelper;
import mx.gob.imss.cit.cda.web.support.model.Page;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.DataPage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author antonio
 */
@Component(BeansConstants.READ_HISTORICO_SOLICITUDES_HELPER)
public class ReadHistoricoSolicitudesHelper extends ReadTramitesAsignadosCommonHelper implements ReadHelper<RequestSolicitudBandejaPage, Page<SolicitudBandeja>> {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ReadHistoricoSolicitudesHelper.class);

    @Autowired
    private ReadHistoricoSolicitudUtils readHistoricoSolicitudUtils;


    @SuppressWarnings("unchecked")
    @Override
    public ReadEvent<Page<SolicitudBandeja>> requestEvent(RequestReadEvent<RequestSolicitudBandejaPage> requestReadEvent) {
        DataPage dataPage;
        try {
            LOGGER.debug("---CDA--- Rol {}",requestReadEvent.getUserProfile().getPerfil());
            
            requestReadEvent.getData().getFilter().setCurp(getCurp(requestReadEvent.getData().getFilter().getCurp()));
            
            //dataPage = getReadHistoricoSolicitudUtils().prepararFiltros(requestReadEvent);

            dataPage = getReadTramitesCommonUtils().prepararFiltros(requestReadEvent);


            dataPage = realizarBusqueda(requestReadEvent, dataPage);

            Page<SolicitudBandeja> page = getReadTramitesCommonUtils().transformPaginaBandeja(dataPage,requestReadEvent.getUserProfile().getUsuario(),
                                                                                              requestReadEvent.getData().getPantallaConsulta(),
                                                                                              requestReadEvent.getData().getFolioConsulta());

            return new ReadEvent<Page<SolicitudBandeja>>(requestReadEvent.getKey(), page);
        } catch (Exception e) {
            LOGGER.error("Ocurrio un error al consultar tramites historicos {1}", e);
            return ReadEvent.notFound(requestReadEvent.getKey());
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    protected DataPage obtenerTareas(RequestReadEvent<RequestSolicitudBandejaPage> requestReadEvent,DataPage dataPage, Long idProceso) {
        DataPage dataPageReturn;
        LOGGER.debug("---CDA---Filtros Historicos {}",requestReadEvent.getData().getFilter());
        LOGGER.debug("---CDA---Folio {}", requestReadEvent.getData().getFilter().getFolio());

        FilterBandeja filter = requestReadEvent.getData().getFilter();

        if (filter.getFolio().isEmpty() && filter.getFechaSolicitud().isEmpty() && filter.getNss().isEmpty() && filter.getOrigen().equals("-1")
                && filter.getResponsable().equals("-1") && filter.getAutorizo().equals("-1") && filter.getEstado().equals("-1") && filter.getCurp()
                .isEmpty() && filter.getTramite().equals("-1") && filter.getFechaActualizacion().isEmpty()) {
            LOGGER.debug("---CDA--- Agregando un limite de dias a consultar para limitar la consulta");
            List<HashMap<String, String>> data = ((List<HashMap<String, String>>)dataPage.getData());
            HashMap<String, String> filtros = data.get(0);
            filtros.put("filtroRangoFecha", "7"); // Se mandan los dias que se van a consultar
            data.set(0, filtros);
            dataPage.setData(data);
        }

        dataPageReturn = getConsultaBandejaBusiness().obtenerTareasHistCDA(dataPage,requestReadEvent.getUserProfile().getUsuario(),requestReadEvent.getUserProfile().getIdSubdelegacion().toString(), idProceso);
        return dataPageReturn;
    }

    public ReadHistoricoSolicitudUtils getReadHistoricoSolicitudUtils() {
        return readHistoricoSolicitudUtils;
    }

    
}
