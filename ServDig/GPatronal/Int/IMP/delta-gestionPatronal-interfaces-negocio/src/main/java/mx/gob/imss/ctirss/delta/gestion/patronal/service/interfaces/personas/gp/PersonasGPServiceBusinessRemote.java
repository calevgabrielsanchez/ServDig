package mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.personas.gp;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

@Remote
public interface PersonasGPServiceBusinessRemote {
	
	long registrarPersonaFisica(Fisica fisica);
	
	boolean existePersonaFisica(Fisica fisica);

}
