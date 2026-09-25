package mx.gob.imss.csdiss.sdroc.orm.dao.impl;



import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.imss.csdiss.sdroc.entity.RocTipoRegistro;
import mx.gob.imss.csdiss.sdroc.orm.dao.TipoRegistroDao;

/**
 * 
 * Clase que implementa la interface ParametroDao para obtener los parametros
 * del sistema de la BD, mediante la utilizacion del patron DAO (Data Access
 * Object).
 * 
 * @author Brian Hernandez Garcia
 * 
 */
@Repository("TipoRegistroDao")
@Transactional
public class TipoRegistroDaoImpl extends AbstractDaoImpl<RocTipoRegistro, Long> implements TipoRegistroDao {


}
