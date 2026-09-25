/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.bandeja.helper;

import mx.gob.imss.cit.cda.core.events.ReadEvent;
import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.bandeja.utils.ReadBitacoraUtils;
import mx.gob.imss.cit.cda.web.bandeja.vo.Bitacora;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.DataPage;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 
 * @author yisus
 */
@Component(BeansConstants.READ_BITACORA_HELPER)
public class ReadBitacoraHelper implements ReadHelper<Bitacora, Bitacora> {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ReadBitacoraUtils readBitacoraUtils;
	
	@Autowired
	@Qualifier("solicitudBusiness")
	private SolicitudBusinessRemote solicitudBusiness;
	
	@SuppressWarnings("unchecked")
	@Override
	public ReadEvent<Bitacora> requestEvent(RequestReadEvent<Bitacora> requestReadEvent) {
		try {
	
			DataPage dataPage = new DataPage();
			dataPage.setPageSize(5);
			dataPage.setCurrentPage(1);
			
			log.info("---CDA--- Consultando la informacion del folio {}",requestReadEvent.getData().getFolio());
			Solicitud solicitud = getSolicitudBusiness().consultarPorFolioSolicitud(requestReadEvent.getData().getFolio());
			
			log.info("---CDA--- se obtuvieron la solicitud {} con {} tramites ",solicitud.getSolicitudId(),solicitud.getTramites().size());
					    
			dataPage.setData(getReadBitacoraUtils().obtenerObservacionesTramite(solicitud.getTramites()));
			Bitacora bitacora = getReadBitacoraUtils().convertBitacora(dataPage);
			bitacora.setFolio(requestReadEvent.getData().getFolio());
			bitacora.setOrigen(requestReadEvent.getData().getOrigen());
			
			return new ReadEvent<Bitacora>(requestReadEvent.getKey(),bitacora);
		} catch (Exception e) {
			log.error("Ocurrio un error al obtener la bitacora {} ",e);
			return ReadEvent.notFound(requestReadEvent.getKey());
		}
	}

    public ReadBitacoraUtils getReadBitacoraUtils() {
        return readBitacoraUtils;
    }

    public SolicitudBusinessRemote getSolicitudBusiness() {
        return solicitudBusiness;
    }
}
