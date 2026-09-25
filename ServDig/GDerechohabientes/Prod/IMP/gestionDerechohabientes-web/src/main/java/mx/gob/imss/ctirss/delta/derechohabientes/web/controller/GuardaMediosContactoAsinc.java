package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.ConsumidorServiciosMediosContactoRemote;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GuardaMediosContactoAsinc extends AbstractController {

	@Autowired
	private ConsumidorServiciosMediosContactoRemote consumidorServiciosMediosContactoRemote;
	
	/**
	 * Metodo asincrono para guardar los medios de contacto de una correccion
	 * @param correccion
	 */
	/*@Async
	@Transactional*/
	public void guardarMediosContactoCorreccion(TramiteCorreccionDerechohabiente correccion) {
		log.debug("Se mandan a guardar los medios de contacto asincronamente");
		consumidorServiciosMediosContactoRemote.procesarMediosContactoCorreccion(correccion);
	}
	
	/*@Async
	@Transactional*/
	public void guardaMediosContactoPersona(Fisica fisica) {
		log.debug("Se actualizaran los medios de contacto para la persona " + fisica.getIdPersona());
		consumidorServiciosMediosContactoRemote.procesaActualizacionEnMedios(fisica);
	}
}