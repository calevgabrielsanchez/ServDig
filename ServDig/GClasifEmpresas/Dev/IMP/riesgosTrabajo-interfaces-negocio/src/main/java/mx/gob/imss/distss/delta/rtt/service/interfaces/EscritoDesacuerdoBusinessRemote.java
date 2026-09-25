package mx.gob.imss.distss.delta.rtt.service.interfaces;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.riesgosTrabajo.RiesgosTrabajoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.CausaDesacuerdo;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.TramiteEscritoDesacuerdo;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.MotivosDesacuerdo;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

@Remote
public interface EscritoDesacuerdoBusinessRemote {

	Solicitud crearSolicitudEscrito(Solicitud solicitud, Boolean finalizar) throws SolicitudNoValidaException, SolicitudNoEncontradaException;
	Solicitud finalizarSolicitudEscrito(Solicitud solicitud) throws SolicitudNoValidaException, SolicitudNoEncontradaException;
	Solicitud validarTramiteExistente(Long idPatronSO);
	TramiteEscritoDesacuerdo getTramiteEscrito(Long idEscritoDesacuerdo);
	/**
	 * Genera Formato Acuse Escrito Desacuerdo en formato PDF
	 *
	 * @param solicitud
	 * @throws RiesgosTrabajoException
	 * @return byte array del archivo PDF
	 */
    byte[] generarAcuseEscritoDesacuerdoPDF(Solicitud solicitud) throws RiesgosTrabajoException;
    List<CausaDesacuerdo> getCausasDesacuerdo(Long idMateria);
}
