package mx.gob.imss.ctirss.gestionpersonas.servicios.utility;

import java.util.Map;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.individuo.AfectacionDatosPersonaException;
import mx.gob.imss.ctirss.delta.model.enums.CambioComparacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.asegurado.integracion.sindo.MovCorreccionesDatosAseguradoType;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.AfectarDatosPersonaWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCambioInformacionPersona;

@Local
public interface AfectarDatosPersonaUtilityLocal {

	AfectarDatosPersonaWrapper crearWrapperDesdeICA(
			TramiteCambioInformacionPersona tramite)
			throws AfectacionDatosPersonaException;
	
	AfectarDatosPersonaWrapper crearWrapperDesdeModificacionManual(
			TramiteCambioInformacionPersona tramite)
			throws AfectacionDatosPersonaException;

	boolean existenDiferencias(Map<String, CambioComparacionEnum> diferencias);

	/**
	 * Metodo encargado de generar el movimiento 06 a SINDO para asegurados
	 * @param asegurado datos de la persona a actualizara
	 * @param folioSolicitud información para asociar el tramite que realiza la modificacíon puede ser nulo si no se requerie seguimiento
	 * @param origenMovimiento para identificar el aplicativo que realiza el tramite puede ser nulo
	 * @return MovCorreccionesDatosAseguradoType con la estructura requerida para encolar el movimiento en OSB
	 */
	MovCorreccionesDatosAseguradoType generarMovimientoActualizacionAseguradoSINDO(
			Fisica asegurado, String folioSolicitud, String origenMovimiento);

}
