package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNss;
@Local
public interface AsignacionNssDaoLocal {
    
	public abstract boolean existSolicitudRegistrobyNSS(String nss) throws Exception;

	DitAsignacionNss getAsignacionNSSbyNSS(String nss) throws DerechohabientesBusinessException, Exception;
	
	public AsignacionNSS getAsignacionNSSbyIdPersona(Long idPersona) throws DerechohabientesBusinessException, Exception;
	
	public AsignacionNSS getAsignacionNSS(Long idAsignacionNSS) throws DerechohabientesBusinessException;
	
	public AsignacionNSS getAsignacionNSSCL3(Long idAsignacionNSS) throws DerechohabientesBusinessException;

}