package mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.integracion;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.model.gestion.integracion.patronal.DatosAltaIDSE;
import mx.gob.imss.ctirss.delta.model.gestion.integracion.patronal.RegistroIDSE;

@Remote
public interface AltaPatronalIDSEIntegrador {

	/**
	 * 
	 * Servicio dedicado a la recopilación de información para impactar los  
	 * movimientos de ALTA/BAJA PATRONAL, ALTA/BAJA DE REPRESENTANTE LEGAL en IDSE.
	 * 
	 * @param 	folioSolicitud
	 * @return 	RegistroIDSE
	 * @throws 	SolicitudNoEncontradaException
	 */
	RegistroIDSE prepararDatosMovimientosIdse(String folioSolicitud)
		throws SolicitudNoEncontradaException;

	/**
	 * 
	 * @param nrp
	 * @param tipoPersona
	 * @return
	 */
	DatosAltaIDSE obtenerDatosAltaPatronal(String nrp, int tipoPersona);
}