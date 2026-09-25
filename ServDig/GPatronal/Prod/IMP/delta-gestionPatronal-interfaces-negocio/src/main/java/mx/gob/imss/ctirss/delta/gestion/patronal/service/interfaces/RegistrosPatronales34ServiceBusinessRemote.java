package mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;
import mx.gob.imss.digital.modelo.patron.RegistroPatronal;
import mx.gob.imss.digital.modelo.persona.Persona;


@Remote
public interface RegistrosPatronales34ServiceBusinessRemote {

	/**
	 * Obtiene los registros patronales modalidad 34
	 * con su domicilio del centro de trabajo.
	 * 
	 * @param persona
	 * @return
	 */
	Persona obtenerNRPs34(Persona persona);
	
	/**
	 * Obtiene el domicilio del NRP domestico si existe.
	 * 
	 * @param persona
	 * @param domicilio
	 * @return
	 * @throws GestionPatronalBusinessException
	 */
	RegistroPatronal obtenerNRPDomesticoXDomicilioCT(Persona persona, Domicilio domicilio) 
		throws GestionPatronalBusinessException;
	
}
