package mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.gestion.asegurado.integracion.sindo.MovCorreccionesDatosAseguradoType;

@Remote
public interface Movimiento06CorreccionBusinessRemote {

	void encolarMovimiento06CorrecconAsegurado(
			MovCorreccionesDatosAseguradoType movCorreccionesDatosAseguradoType);
}
