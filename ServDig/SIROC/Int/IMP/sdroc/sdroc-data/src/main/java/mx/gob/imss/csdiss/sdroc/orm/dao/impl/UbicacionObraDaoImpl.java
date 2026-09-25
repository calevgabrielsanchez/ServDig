package mx.gob.imss.csdiss.sdroc.orm.dao.impl;



import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.imss.csdiss.sdroc.entity.RocTipoPersona;
import mx.gob.imss.csdiss.sdroc.entity.RotUbicacionObra;
import mx.gob.imss.csdiss.sdroc.orm.dao.TipoPersonaDao;
import mx.gob.imss.csdiss.sdroc.orm.dao.UbicacionObraDao;

/**
 * 
 * Clase que implementa la interface ParametroDao para obtener los parametros
 * del sistema de la BD, mediante la utilizacion del patron DAO (Data Access
 * Object).
 * 
 * @author Brian Hernandez Garcia
 * 
 */
@Repository("UbicacionObraDao")
@Transactional
public class UbicacionObraDaoImpl extends AbstractDaoImpl<RotUbicacionObra, Long> implements UbicacionObraDao {


}
