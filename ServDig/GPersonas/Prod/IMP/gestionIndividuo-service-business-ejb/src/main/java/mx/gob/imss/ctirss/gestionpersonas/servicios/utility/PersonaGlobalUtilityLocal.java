package mx.gob.imss.ctirss.gestionpersonas.servicios.utility;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.global.model.PersonaTO;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;

@Local
public interface PersonaGlobalUtilityLocal {
	PersonaTO convertirPersonaFiscaAPersonaGlobal(Fisica fisica);
	PersonaTO convertirPersonaMoralAPersonaGlobal(Moral moral);
	String quitarCaracteresEspeciales(String str);
}
