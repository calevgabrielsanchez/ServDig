package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;


import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DetalleNivelEducativo;

@Local
public interface DetalleNivelEdicativoDaoLocal {
	/**
	 * 
	 * @return
	 * @throws DerechohabientesBusinessException 
	 * @throws Exception 
	 */
	DetalleNivelEducativo find(Long idTipoNivel,Long idNivel) throws DerechohabientesBusinessException, Exception;
} 