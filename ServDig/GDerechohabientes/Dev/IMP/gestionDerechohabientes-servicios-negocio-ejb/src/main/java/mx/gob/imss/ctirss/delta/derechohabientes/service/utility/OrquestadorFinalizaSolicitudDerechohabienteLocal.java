package mx.gob.imss.ctirss.delta.derechohabientes.service.utility;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;


/**
 * Metodo que se encarga a invocar a los diferentes metodos para finalizar solicitudes 
 * de derechohabientes en base al tipo de solicitud.
 * @param String folioSolicitud que sirve par consultar la solicituda finalizar
 * @throws SolicitudNoValidaException
 * @throws SolicitudNoEncontradaException
 * @throws SolicitudException
 * @throws DerechohabientesBusinessException 
 */
@Local
public interface OrquestadorFinalizaSolicitudDerechohabienteLocal {
	Solicitud finalizaSolicitudDerechohabiente(Long solicitudId) throws DerechohabientesBusinessException,
								SolicitudNoValidaException, SolicitudNoEncontradaException, SolicitudException,Exception, ImpactaAlmacenesWSException;
		


	void concluirSolicitudDerechohabientes(Long solicitudId) throws DerechohabientesBusinessException,
	SolicitudNoValidaException, SolicitudNoEncontradaException, SolicitudException, Exception, ImpactaAlmacenesWSException;
}
