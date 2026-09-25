/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.responsable.helper;

import mx.gob.imss.cit.cda.core.events.ReadEvent;
import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.Solicitud;
import mx.gob.imss.cit.cda.web.utils.ReadSolicitudUtils;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author antonio
 */
@Component(BeansConstants.READ_SOLICITUD_HELPER)
public class ReadSolicitudHelper implements ReadHelper<Solicitud, Solicitud> {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private SolicitudBusinessRemote solicitudBusiness;

	@Autowired
	private PersonaBusinessRemote personaBusiness;

	@Autowired
	private ReadSolicitudUtils readSolicitudUtils;
	
	@SuppressWarnings("unchecked")
	@Override
	public ReadEvent<Solicitud> requestEvent(
			RequestReadEvent<Solicitud> requestReadEvent) {
		log.debug("---CDA Ventanilla--- obtener solicitud por idTramite: {}",
				requestReadEvent.getData().getIdTramite());
		try {
			
			mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud sol = solicitudBusiness
					.consultarPorIdTramite(Long.parseLong(requestReadEvent
							.getData().getIdTramite()));			
			
			log.debug("---CDA--- Solicitud[{}]",sol.getSolicitudId());
			
			Solicitud solicitud = readSolicitudUtils.convertSol(sol);
			
			solicitud.setEsPropietario(requestReadEvent.getData().getEsPropietario());
			
			log.debug("---CDA Ventanilla--- solicitud encontrada para el tramite: {}", requestReadEvent.getData().getIdTramite());
			
			return new ReadEvent<Solicitud>(requestReadEvent.getKey(), solicitud);
			
		} catch (Exception e) {
			log.debug("---CDA Ventanilla--- error al leer la solicitud con idTramite: "
							+ requestReadEvent.getData().getIdTramite(), e);
			return ReadEvent.notFound(requestReadEvent.getKey());
		}
	}
  
}
