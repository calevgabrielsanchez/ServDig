package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.persistence.DitGrupoFamiliar;
import mx.gob.imss.ctirss.delta.persistence.DitGrupoFamiliarCL3;

@Local
public interface GrupoFamiliarParserServiceLocal {

	GrupoFamiliar persistToModel(DitGrupoFamiliar entrada) throws DerechohabientesBusinessException, Exception;
	DitGrupoFamiliar modelToPersist(GrupoFamiliar entrada) throws DerechohabientesBusinessException, Exception;
    DitGrupoFamiliar modelToPersist(GrupoFamiliar entrada, boolean afectarDomicilio) throws DerechohabientesBusinessException;
	GrupoFamiliar persistToModelNssParentesco(DitGrupoFamiliar entrada) throws DerechohabientesBusinessException, Exception;
	List<GrupoFamiliar> persistToModelNssParentescoList(List<DitGrupoFamiliar> entrada) throws DerechohabientesBusinessException, Exception;
	List<GrupoFamiliar> persistToModelList(List<DitGrupoFamiliar> entrada) throws DerechohabientesBusinessException, Exception;
	
	GrupoFamiliar persistCL3ToModel(DitGrupoFamiliarCL3 entrada) throws DerechohabientesBusinessException, Exception;
	DitGrupoFamiliarCL3 modelToPersistCL3(GrupoFamiliar entrada) throws DerechohabientesBusinessException, Exception;
	
}


