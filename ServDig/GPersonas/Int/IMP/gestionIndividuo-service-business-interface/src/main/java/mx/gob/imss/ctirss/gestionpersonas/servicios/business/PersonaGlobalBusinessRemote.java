package mx.gob.imss.ctirss.gestionpersonas.servicios.business;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.global.model.PersonaTO;

@Remote
public interface PersonaGlobalBusinessRemote {
	PersonaTO evaluarPersonaConInstanciaExternas(PersonaTO persona);
}
