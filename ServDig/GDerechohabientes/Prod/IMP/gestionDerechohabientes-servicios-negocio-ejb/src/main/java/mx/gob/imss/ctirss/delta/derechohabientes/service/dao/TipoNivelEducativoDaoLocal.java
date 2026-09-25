package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.util.List;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoNivelEducativo;

public interface TipoNivelEducativoDaoLocal {

	List<TipoNivelEducativo> findAll() throws DerechohabientesBusinessException, Exception;

}