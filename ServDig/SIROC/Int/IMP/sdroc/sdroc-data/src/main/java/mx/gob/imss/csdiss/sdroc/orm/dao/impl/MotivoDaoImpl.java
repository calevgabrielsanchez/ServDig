package mx.gob.imss.csdiss.sdroc.orm.dao.impl;



import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.imss.csdiss.sdroc.entity.RocMotivo;
import mx.gob.imss.csdiss.sdroc.orm.dao.MotivoDao;

/**
 * 
 * Clase que implementa la interface ParametroDao para obtener los parametros
 * del sistema de la BD, mediante la utilizacion del patron DAO (Data Access
 * Object).
 * 
 * @author Brian Hernandez Garcia
 * 
 */
@Repository("MotivoDao")
@Transactional
public class MotivoDaoImpl extends AbstractDaoImpl<RocMotivo, Long> implements MotivoDao {

	 public List<RocMotivo> findByTipoIncidencia(Long cveTipoIncidencia) { 
		 
		List<RocMotivo> listRocMotivo = null; 
		String sql = "SELECT motivo FROM RocMotivo motivo, RocMotivoIncidencia motivoTipoIncidencia WHERE "
				+ "motivo.cveMotivo = motivoTipoIncidencia.rocMotivo.cveMotivo and motivoTipoIncidencia.rocTipoIncidencia.cveTipoIncidencia = :cveTipoIncidencia";		
		Query query = getSession().createQuery(sql).setParameter("cveTipoIncidencia", cveTipoIncidencia); 
		listRocMotivo = this.findMany(query); 
		return listRocMotivo; 
		
	 } 


}
