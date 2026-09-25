package mx.gob.imss.csdiss.sdroc.orm.dao;

import mx.gob.imss.csdiss.sdroc.entity.DitLlavePatron;

/**
 * 
 * Interface que contiene la definicion de las operaciones para obtener los
 * parametros del sistema utilizando el patron DAO (Data Access Object).
 * 
 * @author Brian Hernandez Garcia
 * 
 */
public interface DitLlavePatronDao extends AbstractDao<DitLlavePatron, Long> {

	int findbyRpAndCp(String cveRp,String cveCodigoPostal);
	int validCircunscripcionCP(String codigoPostal, Long idDelegacion, Long idSubdelegacion);

}
