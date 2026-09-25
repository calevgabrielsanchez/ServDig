package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.actividad.economica;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.Bien;
import mx.gob.imss.ctirss.delta.persistence.DitBiene;

@Local
public interface BienesServiceUtilityLocal {
	
	DitBiene convertirModelToEntity(Bien model);
	
	Bien convertirEntityToModel(DitBiene entity);
	
	List <Bien> convertListOfEntitiesToListOfModel(List <DitBiene> origen);
	
	List <DitBiene> convertListOfModelToListOfEntity(List <Bien> origen);


}
