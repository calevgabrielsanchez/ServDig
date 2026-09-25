package mx.gob.imss.csdiss.sdroc.orm.dao.impl;



import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.imss.csdiss.sdroc.entity.RocTipoObra;
import mx.gob.imss.csdiss.sdroc.orm.dao.TipoObraDao;

/**
 * 
 * Clase que implementa la interface ParametroDao para obtener los parametros
 * del sistema de la BD, mediante la utilizacion del patron DAO (Data Access
 * Object).
 * 
 * @author Brian Hernandez Garcia
 * 
 */
@Repository("TipoObraDao")
@Transactional
public class TipoObraDaoImpl extends AbstractDaoImpl<RocTipoObra, Long> implements TipoObraDao,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6476282401113892541L;

	public List<RocTipoObra> findByCveClasificacionObra(Long cveClasificacionObra) {
		List<RocTipoObra> listRocTipoObra = null; 
		
		
		String sql = "SELECT tipoObra FROM RocTipoObra tipoObra WHERE tipoObra.rocClasificacionObra.cveClasificacionObra = :cveClasificacionObra "
				+ " AND LOWER(tipoObra.desTipoObra) NOT LIKE \'%sin tipo%\'";
		Query query = getSession().createQuery(sql).setParameter("cveClasificacionObra", cveClasificacionObra); 
		
		listRocTipoObra = this.findMany(query); 
		return listRocTipoObra;
	}


}
