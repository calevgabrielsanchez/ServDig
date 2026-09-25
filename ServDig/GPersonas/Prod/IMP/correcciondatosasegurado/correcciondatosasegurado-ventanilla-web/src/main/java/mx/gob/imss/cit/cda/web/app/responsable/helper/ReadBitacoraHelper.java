/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.responsable.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.cda.core.events.ReadEvent;
import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.Bitacora;
import mx.gob.imss.cit.cda.web.utils.ReadBitacoraUtils;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.DataPage;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

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
			
			log.info("---CDA--- Consultando la informacion del Tramite {}",requestReadEvent.getData().getIdTramite());
			Solicitud sol = solicitudBusiness.consultarPorIdTramite(Long.parseLong(requestReadEvent.getData().getIdTramite()));			
			log.info("---CDA--- se obtuvieron la solicitud {} con {} tramites ",sol.getSolicitudId(),sol.getTramites().size());
			log.info("---CDA--- Fecha de Actualizacion Tramite {}",sol.getTramites().get(0).getFechaRegistroActualizacion());
			dataPage.setData(((TramiteCorreccionCurp) sol.getTramites().get(0)).getObservacionesSubdelegacion());
			
			Bitacora bitacora = readBitacoraUtils.convertBitacora(dataPage);
			bitacora.setFolio(requestReadEvent.getData().getFolio());
			bitacora.setOrigen(requestReadEvent.getData().getOrigen());
			
			return new ReadEvent<Bitacora>(requestReadEvent.getKey(),bitacora);
		} catch (Exception e) {
			log.error("Ocurrio un error al obtener la bitacora {} ",e);
			return ReadEvent.notFound(requestReadEvent.getKey());
		}
	}
}
