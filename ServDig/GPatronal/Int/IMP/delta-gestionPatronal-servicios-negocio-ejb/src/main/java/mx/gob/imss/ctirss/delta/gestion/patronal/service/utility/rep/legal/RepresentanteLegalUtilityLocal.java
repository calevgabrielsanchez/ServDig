package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.rep.legal;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.Facultad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoFacultad;
import mx.gob.imss.ctirss.delta.persistence.DicFacultad;
import mx.gob.imss.ctirss.delta.persistence.DicTipoFacultad;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitRepresentanteLegal;

@Local
public interface RepresentanteLegalUtilityLocal {
	
	DitRepresentanteLegal convertirModelToEntity(RepresentanteLegal model);
	
	RepresentanteLegal convertirEntityToModel(DitRepresentanteLegal entity);
	
	RepresentanteLegal enityToModelDatosBasicos(DitRepresentanteLegal entity);
	
	RepresentanteLegal convertirEntityToModelWithPersona(DitRepresentanteLegal entity1, DitPersona entity2) throws Exception;
	
	List <RepresentanteLegal> convertListOfEntitiesToListOfModel(List <DitRepresentanteLegal> origen);
	
	List <DitRepresentanteLegal> convertListOfModelToListOfEntity(List <RepresentanteLegal> origen) throws Exception;
	
	Facultad convertirEntityToModelFacultad(DicFacultad entity);
	
	TipoFacultad convertirEntityToModelTipoFacultad(DicTipoFacultad entity);
	
	DitRepresentanteLegal asignarValoresParaActualizar(RepresentanteLegal representanteLegal, DitRepresentanteLegal ditRepresentanteLegal)throws Exception;
	
	RepresentanteLegal entityToModelDatosBasicosRepresentanteYRepresentado(DitRepresentanteLegal entity);
}
