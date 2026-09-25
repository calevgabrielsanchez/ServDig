package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;

@Remote
public interface ConsumidorServiciosMediosContactoRemote {

	void procesaActualizacionEnMedios( Fisica fisica);
	void procesarMediosContactoCorreccion(TramiteCorreccionDerechohabiente correccion);
}
