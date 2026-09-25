package mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.integration;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.asegurado.AltaDatosAsignacionNSSType;

/**
 * 
 * @author NOVUTEK101
 *
 */
@Remote
public interface AseguradoServiceBusinessRemote {
	
	AltaDatosAsignacionNSSType[] cargarArchivoSIE(String fileName);
	
}
