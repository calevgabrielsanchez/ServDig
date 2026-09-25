package mx.gob.imss.csdiss.sdroc.orm.dao.impl;



import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.imss.csdiss.sdroc.entity.RocMotivoIncidencia;
import mx.gob.imss.csdiss.sdroc.orm.dao.MotivoTipoIncidenciaDao;

/**
 * 
 * Clase que implementa la interface ParametroDao para obtener los parametros
 * del sistema de la BD, mediante la utilizacion del patron DAO (Data Access
 * Object).
 * 
 * @author Brian Hernandez Garcia
 * 
 */
@Repository("MotivoTipoIncidenciaDao")
@Transactional
public class MotivoTipoIncicenciaDaoImpl extends AbstractDaoImpl<RocMotivoIncidencia, Long> implements MotivoTipoIncidenciaDao {

	public RocMotivoIncidencia findMotivoIncidenciaByMotivoTipoIncidencia(Long cveMotivo, Long cveTipoIncidencia) {

		
		RocMotivoIncidencia rocMotivoIncidencia = null;
				
		String sql = "SELECT motivoIncidencia FROM RocMotivoIncidencia motivoIncidencia WHERE "
				+ "motivoIncidencia.rocTipoIncidencia.cveTipoIncidencia = :cveTipoIncidencia AND motivoIncidencia.rocMotivo.cveMotivo = :cveMotivo ";
		
			Query query = this.getSession().createQuery(sql).setParameter("cveTipoIncidencia", cveTipoIncidencia).setParameter("cveMotivo", cveMotivo);		
			query.setMaxResults(1);			
			rocMotivoIncidencia = (RocMotivoIncidencia) query.uniqueResult();		
			
			return rocMotivoIncidencia;
	}

}
