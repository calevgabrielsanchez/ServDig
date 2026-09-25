package mx.gob.imss.ctirss.gestionpersonas.servicios.utility;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.individuo.AfectacionDatosPersonaException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.asegurado.integracion.sindo.MovCorreccionesDatosAseguradoType;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

@Remote
public interface AfectarDatosPersonaUtilityRemote {
	
	/**
	 * Metodo encargado de generar el movimiento 06 a SINDO para asegurados
	 * @param asegurado datos de la persona a actualizara
	 * @param folioSolicitud información para asociar el tramite que realiza la modificacíon puede ser nulo si no se requerie seguimiento
	 * @param origenMovimiento para identificar el aplicativo que realiza el tramite puede ser nulo
	 * @return MovCorreccionesDatosAseguradoType con la estructura requerida para encolar el movimiento en OSB
	 */
	MovCorreccionesDatosAseguradoType generarMovimientoActualizacionAseguradoSINDO(
			Fisica asegurado, String folioSolicitud, String origenMovimiento);
	
	MovCorreccionesDatosAseguradoType generarMovimientoActualizacionRegistroAsegurado(GrupoFamiliar grupoFamiliar) throws AfectacionDatosPersonaException;

}
