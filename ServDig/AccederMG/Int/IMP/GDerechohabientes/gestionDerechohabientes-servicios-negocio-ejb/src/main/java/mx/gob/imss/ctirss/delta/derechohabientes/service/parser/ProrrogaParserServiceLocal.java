package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteProrroga;
import mx.gob.imss.ctirss.delta.persistence.DitProrroga;

@Local
public interface ProrrogaParserServiceLocal {

	DitProrroga modelToPersist(TramiteProrroga entrada) throws DerechohabientesBusinessException;
	TramiteProrroga persisToModel(DitProrroga entrada) throws DerechohabientesBusinessException;
	List<TramiteProrroga> persistToModelList(List<DitProrroga> ditProrrogas) throws DerechohabientesBusinessException;
	Long getTipoProrrogaPorTipoTramite(Integer idTipoTramite);
}
