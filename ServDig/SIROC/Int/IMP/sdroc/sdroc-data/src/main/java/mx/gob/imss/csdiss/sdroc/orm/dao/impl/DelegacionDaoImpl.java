package mx.gob.imss.csdiss.sdroc.orm.dao.impl;



import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.imss.csdiss.sdroc.entity.RocDelegacion;
import mx.gob.imss.csdiss.sdroc.orm.dao.DelegacionDao;

/**
 * 
 * Clase que implementa la interface ParametroDao para obtener los parametros
 * del sistema de la BD, mediante la utilizacion del patron DAO (Data Access
 * Object).
 * 
 * @author Brian Hernandez Garcia
 * 
 */
@Repository("DelegacionDao")
@Transactional
public class DelegacionDaoImpl extends AbstractDaoImpl<RocDelegacion, Long> implements DelegacionDao {


}
