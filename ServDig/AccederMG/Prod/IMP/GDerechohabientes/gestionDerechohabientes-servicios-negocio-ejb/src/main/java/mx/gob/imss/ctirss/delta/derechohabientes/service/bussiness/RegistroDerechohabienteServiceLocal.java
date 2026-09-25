package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;

@Local
public interface RegistroDerechohabienteServiceLocal {
	
	/**
	 * Metodo para crear la solicitud de registro de derechohabientes
	 * @param registro
	 * @param idOrigenSolicitud
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws SolicitudNoValidaException
	 */
	Solicitud registraSolicitud(TramiteRegistroDerechohabiente registro, Long idOrigenSolicitud) throws DerechohabientesBusinessException, SolicitudNoValidaException;
	/**
	 * Metodo que finaliza la solicitud, se pretende eliminar finalizaTramite para no depender ni de 
	 * RegistroDto ni de ValidacionRegDto
	 * @param solicitud
	 * @throws DerechohabientesBusinessException
	 * @throws SolicitudNoValidaException
	 * @throws SolicitudNoEncontradaException
	 * @throws SolicitudException
	 */
	Solicitud finalizarSolicitudRegistro(Solicitud solicitud) throws DerechohabientesBusinessException,
	SolicitudNoValidaException, SolicitudNoEncontradaException, SolicitudException, Exception, ImpactaAlmacenesWSException;
	
	Solicitud finalizarSolicitudRegistroMovil(Solicitud solicitud, CabezaGrupoFamiliar cabeza) throws DerechohabientesBusinessException,
	SolicitudNoValidaException, SolicitudNoEncontradaException, SolicitudException, Exception, ImpactaAlmacenesWSException;
	
	boolean modalidadParentesco(Long idModalidad, Long idParentesco) throws Exception;
	
	/**
	 * Metodo encargado de finalizar la solicitud de TSPI para registro de beneficiarios
	 * @param solicitud
	 * @param cabeza
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws SolicitudNoValidaException
	 * @throws SolicitudNoEncontradaException
	 * @throws SolicitudException
	 * @throws Exception
	 * @throws ImpactaAlmacenesWSException
	 */
	Solicitud finalizarSolicitudRegistroTSPI(Solicitud solicitud,
			CabezaGrupoFamiliar cabeza)
			throws DerechohabientesBusinessException,
			SolicitudNoValidaException, SolicitudNoEncontradaException,
			SolicitudException, Exception, ImpactaAlmacenesWSException;
	
}
