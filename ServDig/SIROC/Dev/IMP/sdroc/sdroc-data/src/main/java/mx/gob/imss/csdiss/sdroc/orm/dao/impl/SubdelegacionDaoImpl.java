package mx.gob.imss.csdiss.sdroc.orm.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.imss.csdiss.sdroc.entity.RocSubdelegacion;
import mx.gob.imss.csdiss.sdroc.orm.dao.SubdelegacionDao;

/**
 * 
 * Clase que implementa la interface ParametroDao para obtener los parametros
 * del sistema de la BD, mediante la utilizacion del patron DAO (Data Access
 * Object).
 * 
 * @author Brian Hernandez Garcia
 * 
 */
@Repository("SubdelegacionDao")
@Transactional
public class SubdelegacionDaoImpl extends AbstractDaoImpl<RocSubdelegacion, Long> implements SubdelegacionDao {

	public RocSubdelegacion findByCveCodigoSubdelegacion(Long cveCodigo) {
		RocSubdelegacion rocSubdelegacion = null;
		String sql = "SELECT subdelegacion FROM RocSubdelegacion subdelegacion WHERE subdelegacion.cveCodigo = :cveCodigo";
		Query query = getSession().createQuery(sql).setParameter("cveCodigo", cveCodigo);
		rocSubdelegacion = this.findOne(query);
		return rocSubdelegacion;
	}

	public List<Object[]> findSubdelegacionImssByCp(String cveCodigoPostal) {

		List<Object[]> resultado;

		String sql = "select distinct sdel.cveSubdelegacion, sdel.nomSubdelegacion, delegacion.cveDelegacion, delegacion.nomDelegacion" 
				+ " from RocSubdelegacion sdel,"
				+ " DitMunicipioSubdelegacion mpiosd, DicMunicipioImss mpioimss, "
				+ " DitMunicipioImssInegi mpioimsing, DgCodigosPostales dgcp "
				+ " inner join sdel.rocDelegacion delegacion"
				+ " where sdel.cveSubdelegacion    = mpiosd.cveIdSubdelegacion"
				+ " and mpiosd.cveIdMunicipioImss = mpioimss.cveIdMunicipioImss "
				+ " and mpioimss.cveIdMunicipioImss = mpioimsing.cveIdMunicipioImss "
				+ " and mpioimsing.cveMun = dgcp.cveMun " + " and mpioimsing.cveEnt = dgcp.cveEnt "
				+ " and dgcp.codigo = :cveCodigoPostal";

		Query query = this.getSession().createQuery(sql).setParameter("cveCodigoPostal", cveCodigoPostal);

		resultado = this.findManyObject(query);

		return resultado;
	}

}
