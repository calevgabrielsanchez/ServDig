package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.Personal;
import mx.gob.imss.ctirss.delta.persistence.DitPersonal;

@Local
public interface PersonalServiceUtilityLocal {
	
	DitPersonal convertirModelToEntity(Personal model);
	
	Personal convertirEntityToModel(DitPersonal entity) throws Exception;
	
	List <Personal> convertListOfEntitiesToListOfModel(List <DitPersonal> origen) throws Exception;
	
	List <DitPersonal> convertListOfModelToListOfEntity(List <Personal> origen);

}
