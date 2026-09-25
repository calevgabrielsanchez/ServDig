package mx.gob.imss.distss.portal.vigencia.grupo.controller;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GuardaSolicitudConsultaAsinc extends AbstractController {

	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	
	@Transactional
	//@Async
	public void guardaSolicitudConsulta(AsignacionNSS asignacion, Usuario usuario) {
		log.debug("Se guarda asincronamente la solicitud de consulta");
		try {

			// ----------------------------------------------------
			// Si tiene inconsistencias no se guarda la solicitud
			// ----------------------------------------------------
			if(asignacion.getIdPersona() != null  && asignacion.getIdAsignacionNSS() != null) {
				grupoFamiliarServiceRemote.guardarSolicitudConsultaVigencia(asignacion, usuario);
			}

		} catch(Exception e) {
			log.error("Ocurrio error al generar la solicitud de consulta de segundo y tercer nivel");
		}
		log.debug("saliendo de la consulta asicrona");
	}
}
