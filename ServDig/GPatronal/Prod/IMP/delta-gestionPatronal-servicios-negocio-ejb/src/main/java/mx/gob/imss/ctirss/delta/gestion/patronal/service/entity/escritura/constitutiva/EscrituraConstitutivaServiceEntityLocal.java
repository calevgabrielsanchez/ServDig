package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.escritura.constitutiva;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EscrituraConstitutiva;

@Local
public interface EscrituraConstitutivaServiceEntityLocal {
	
	EscrituraConstitutiva actualizarEscrituraConstitutiva(EscrituraConstitutiva escrituraConstitutiva) throws GestionPatronalBusinessException;
	EscrituraConstitutiva consultarEscritura(Long idActa);
	
}
