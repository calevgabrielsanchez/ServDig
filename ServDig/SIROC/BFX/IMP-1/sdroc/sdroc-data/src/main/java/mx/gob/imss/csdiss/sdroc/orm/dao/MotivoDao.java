package mx.gob.imss.csdiss.sdroc.orm.dao;


import java.util.List;

import mx.gob.imss.csdiss.sdroc.entity.RocMotivo;

/**
 * 
 * Interface que contiene la definicion de las operaciones para obtener los
 * parametros del sistema utilizando el patron DAO (Data Access Object).
 * 
 * @author Brian Hernandez Garcia
 * 
 */
public interface MotivoDao extends AbstractDao<RocMotivo, Long> {
	
	 public List<RocMotivo> findByTipoIncidencia(Long cveTipoIncidencia);
	

}
