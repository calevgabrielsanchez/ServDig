package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;

@Local
public interface ConsumidorServiciosMediosContactoLocal {

	void procesaActualizacionEnMedios( Fisica fisica);
	void procesarMediosContactoCorreccion(TramiteCorreccionDerechohabiente correccion);
}
