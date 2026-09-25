package mx.gob.imss.ctirss.delta.gestion.domicilio.service.utility;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.derechohabiente.ClavePresupuestal;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.persistence.DicClavePresupuestal;
import mx.gob.imss.ctirss.delta.persistence.DicUmf;

@Local
public interface UMFUtilityLocal {

	DicUmf modelToPersist(UnidadMedicaFamiliar entrada);
	UnidadMedicaFamiliar persisToModel(DicUmf entrada);
	List<UnidadMedicaFamiliar> persisToModelList(List<DicUmf> entrada);
	ClavePresupuestal convertEntityToModelClavePresupuestal(DicClavePresupuestal entity);
}
