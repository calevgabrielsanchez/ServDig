package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;

@Local
public interface AsignacionUMFServiceLocal {

	List<Long> guardarTramiteAsignacionUmfDependiente(GrupoFamiliar afectado, Long idSolicitud, Integer patronIMSS, Boolean registro, Boolean asignacionDomicilio)  throws DerechohabientesBusinessException, Exception;
}
