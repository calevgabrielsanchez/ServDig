package mx.gob.imss.csdiss.sdroc.orm.dao.impl;



import java.io.Serializable;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.imss.csdiss.sdroc.entity.RocEstatusObra;
import mx.gob.imss.csdiss.sdroc.orm.dao.EstatusObraDao;

/**
 * 
 * Clase que implementa la interface ParametroDao para obtener los parametros
 * del sistema de la BD, mediante la utilizacion del patron DAO (Data Access
 * Object).
 * 
 * @author Brian Hernandez Garcia
 * 
 */
@Repository("EstatusObraDao")
@Transactional
public class EstatusObraDaoImpl extends AbstractDaoImpl<RocEstatusObra, Long> implements EstatusObraDao,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8438368413381694426L;




}
