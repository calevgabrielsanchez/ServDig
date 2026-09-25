package mx.gob.imss.ctirss.idse.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.idse.exception.RegistroPatronalIdseException;
import mx.gob.imss.ctirss.idse.model.RegistroPatronal;

@Remote
public interface RegistroPatronalIdseServiceBusinessRemote {

	void altaRegistroPatronal(RegistroPatronal registroPatronal) throws RegistroPatronalIdseException;
	
	void desasociarRegistroPatronal(RegistroPatronal registroPatronal) throws RegistroPatronalIdseException;
	
	void asociarRepresentanteLegal(RegistroPatronal registroPatronal) throws RegistroPatronalIdseException;
	
	void desasociarRepresentanteLegal(RegistroPatronal registroPatronal) throws RegistroPatronalIdseException;
	
}
