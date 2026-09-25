package mx.gob.imss.ctirss.delta.gestion.domicilio.service.utility;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.persistence.DgCatEstado;

@Local
public interface EntidadFederativaUtilityLocal {

	DgCatEstado modelToPersist(EntidadFederativa entrada);
	EntidadFederativa persisToModel(DgCatEstado entrada);
	List<EntidadFederativa> persistToModelList(List<DgCatEstado> entrada);
	
}
