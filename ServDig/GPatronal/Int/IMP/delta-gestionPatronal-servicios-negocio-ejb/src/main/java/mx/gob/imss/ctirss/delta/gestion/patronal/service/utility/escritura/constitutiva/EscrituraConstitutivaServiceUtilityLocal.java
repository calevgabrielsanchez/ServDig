package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.escritura.constitutiva;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.EscrituraConstitutiva;
import mx.gob.imss.ctirss.delta.persistence.DitActaConstitutiva;

@Local
public interface EscrituraConstitutivaServiceUtilityLocal {
	
	DitActaConstitutiva convertirModelToEntity(EscrituraConstitutiva model) throws Exception;
	
	EscrituraConstitutiva convertirEntityToModel(DitActaConstitutiva entity);
	
	DitActaConstitutiva asignarvaloresFaltantes(EscrituraConstitutiva escrituraConstitutiva, DitActaConstitutiva ditActaConstitutiva) throws Exception;
	
	

}
