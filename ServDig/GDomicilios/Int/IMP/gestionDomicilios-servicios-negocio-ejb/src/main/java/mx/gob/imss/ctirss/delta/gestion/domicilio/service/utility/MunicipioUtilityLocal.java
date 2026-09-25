package mx.gob.imss.ctirss.delta.gestion.domicilio.service.utility;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.persistence.DgCatMunicipio;

@Local
public interface MunicipioUtilityLocal {

	Municipio persistToModel(DgCatMunicipio entrada);
	List<Municipio> persistToModelList(List<DgCatMunicipio> entrada);
	
}
