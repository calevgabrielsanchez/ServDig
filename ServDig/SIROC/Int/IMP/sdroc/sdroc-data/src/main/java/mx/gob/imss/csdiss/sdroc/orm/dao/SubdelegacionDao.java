package mx.gob.imss.csdiss.sdroc.orm.dao;

import java.util.List;

import mx.gob.imss.csdiss.sdroc.entity.RocSubdelegacion;

/**
 * 
 * Interface que contiene la definicion de las operaciones para obtener los
 * parametros del sistema utilizando el patron DAO (Data Access Object).
 * 
 * @author Brian Hernandez Garcia
 * 
 */
public interface SubdelegacionDao extends AbstractDao<RocSubdelegacion, Long> {
	
	public RocSubdelegacion findByCveCodigoSubdelegacion(Long cveCodigo);
	
	public List<Object[]> findSubdelegacionImssByCp(String cveCodigoPostal);	

}
