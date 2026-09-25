package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business;

import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.IDSEQueueProducerRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.test.EjbLocator;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

// TODO Remember profiler aspect
public class SolicitudBusinessActualizarTramitesTest {

    private static final Logger LOG = LoggerFactory.getLogger(SolicitudBusinessActualizarTramitesTest.class);
    private transient final SolicitudBusinessRemote solicitudBusiness = EjbLocator.getSolicitudBusiness();
    private transient final IDSEQueueProducerRemote idseQProducerBusiness = EjbLocator.getIDSEQueueProducerRemote();
    
    private transient Long solicitudId;
    private transient Long tramiteId;

    // TEST FIXTURE:
    @Before
    public void setUp() {
        solicitudId = 982L;
        tramiteId = 520L;
    }
    
    @Test(expected = SolicitudNoEncontradaException.class)
    public void actualizarSolicitudNoExistente() throws SolicitudNoEncontradaException, TramiteNoEncontradoException {
        final Solicitud solicitud = new Solicitud();
        solicitud.setSolicitudId(-11L);
        solicitudBusiness.actualizarTramites(solicitud);
    }

    @Test
    public void actualizarEstadoTramiteExistenteSolicitud() throws SolicitudNoEncontradaException, TramiteNoEncontradoException {
        final Solicitud solicitud = new Solicitud();
        solicitud.setSolicitudId(solicitudId);
        final Tramite tramite = new Tramite();
        tramite.setTramiteId(tramiteId);
        final EstadoTramite estadoTramite = new EstadoTramite();
        estadoTramite.setIdEstadoTramitePersona(2);
        tramite.setEstadoTramite(estadoTramite);
        solicitud.getTramites().add(tramite);
        final Solicitud solicitudOriginal = solicitudBusiness.consultar(solicitud);
        final Long iniL = new Date().getTime();
        LOG.trace("INICIO " + iniL);
        // Este metodo tarda aprox 2 segundos para 1 tramite y solo uno de los
        // cuales se actualiza su estado.
        final Solicitud solicitudActualizada = solicitudBusiness.actualizarTramites(solicitud);
        final Long finL = new Date().getTime();
        LOG.trace("FINAL " + finL);
        LOG.trace("Tarda (mseg) " + (finL - iniL));
        if (solicitudOriginal.getTramites() != null) {
            LOG.trace("Size original: " + solicitudOriginal.getTramites().size());
            // RECUPERA AL TRAMITE QUE SE ESTA ACTUALIZANDO:
            Tramite tramiteOriginal = null; // NOPMD
            for (Tramite tramiteOriginalIter : solicitudOriginal.getTramites()) {
                if (tramiteId.equals(tramiteOriginalIter.getTramiteId())) {
                    tramiteOriginal = tramiteOriginalIter;
                    break;
                }
            }
            if (tramiteOriginal == null || tramiteOriginal.getEstadoTramite() == null) {
                LOG.trace("No se encontro el tramite en la lista de tramites original");
            } else {
                LOG.trace("Tramite original estado: id {} desc {}", tramiteOriginal.getEstadoTramite().getIdEstadoTramitePersona(), tramiteOriginal.getEstadoTramite().getDescripcion());
            }
        }
        if (solicitudActualizada.getTramites() != null) {
            LOG.trace("Size actualizado: " + solicitudActualizada.getTramites().size());
            final Tramite tramiteActualizado = solicitudActualizada.getTramites().get(0);
            LOG.trace("First TramiteId " + tramiteActualizado.getTramiteId());
            LOG.trace("Tramite actualizado estado: id {} desc {}", tramiteActualizado.getEstadoTramite().getIdEstadoTramitePersona(), tramiteActualizado.getEstadoTramite().getDescripcion());
        }

    }

    @Test
    public void actualizarTipoTramiteExistente() throws SolicitudNoEncontradaException, TramiteNoEncontradoException {
        final Solicitud solicitud = new Solicitud();
        solicitud.setSolicitudId(solicitudId);

        final TipoTramite tipoTramite = new TipoTramite();
        tipoTramite.setIdTipoTramite(1);

        final Tramite tramite = new Tramite();
        tramite.setTramiteId(tramiteId);
        tramite.setTipoTramite(tipoTramite);

        solicitud.getTramites().add(tramite);

        solicitudBusiness.actualizarTramites(solicitud);
    }
    
    //@Test
    public void procesarSolicitudesIDSE() throws SolicitudNoEncontradaException, TramiteNoEncontradoException {

    	List<Solicitud> listaSolicitudes = solicitudBusiness.obtenerSolicitudes();
    	if(!CollectionUtils.isEmpty(listaSolicitudes)){
    		for(Solicitud solicitud : listaSolicitudes){
    			LOG.trace("INICIO " + solicitud.getNoFolioSolicitud());
    			idseQProducerBusiness.encolarMensajeIDSE(solicitud.getNoFolioSolicitud());
    		}
    	}
    }

}
