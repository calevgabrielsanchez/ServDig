package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces;

import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.solicitud.Solicitud;

import javax.ejb.Remote;

/**
 * 
 * @author NOVUTECK1
 *
 */
@Remote
public interface SolicitudSeguroIvroRemote {
	Solicitud consultarSolicitudSeguroPorFolio(String folio) throws SolicitudNoEncontradaException;
	boolean existeRechazo(Long idPersona);
    Solicitud consultarSolicitudPorSeguro(SeguroIvro seguro) throws SolicitudNoEncontradaException;
}
