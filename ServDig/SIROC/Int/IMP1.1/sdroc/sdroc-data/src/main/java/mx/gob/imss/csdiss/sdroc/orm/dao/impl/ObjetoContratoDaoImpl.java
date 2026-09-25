package mx.gob.imss.csdiss.sdroc.orm.dao.impl;



import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.imss.csdiss.sdroc.entity.RocObjetoContrato;
import mx.gob.imss.csdiss.sdroc.orm.dao.ObjetoContratoDao;


/**
 * 
 * Clase que implementa la interface ParametroDao para obtener los parametros
 * del sistema de la BD, mediante la utilizacion del patron DAO (Data Access
 * Object).
 * 
 * @author Brian Hernandez Garcia
 * 
 */
@Repository("ObjetoContratoDao")
@Transactional
public class ObjetoContratoDaoImpl extends AbstractDaoImpl<RocObjetoContrato, Long> implements ObjetoContratoDao {


}
