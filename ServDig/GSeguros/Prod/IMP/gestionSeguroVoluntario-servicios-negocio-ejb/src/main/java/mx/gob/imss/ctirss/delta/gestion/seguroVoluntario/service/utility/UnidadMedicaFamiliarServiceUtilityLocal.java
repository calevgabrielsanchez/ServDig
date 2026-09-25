package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.utility;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.persistence.DicUmf;

@Local
public interface UnidadMedicaFamiliarServiceUtilityLocal {
	UnidadMedicaFamiliar convertirEntityToModel(DicUmf dicUmf) throws Exception;
}
