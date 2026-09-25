package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.personas.gp;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

@Local
public interface PersonasGPServiceBusinessLocal {
	
	long registrarPersonaFisica(Fisica fisica);
	
	boolean existePersonaFisica(Fisica fisica);

}