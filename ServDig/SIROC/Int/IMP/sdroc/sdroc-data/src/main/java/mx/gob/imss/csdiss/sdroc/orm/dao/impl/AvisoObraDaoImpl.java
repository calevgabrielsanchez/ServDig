package mx.gob.imss.csdiss.sdroc.orm.dao.impl;

import java.util.List;

import mx.gob.imss.csdiss.sdroc.entity.RotAvisoObra;
import mx.gob.imss.csdiss.sdroc.orm.dao.AvisoObraDao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * Clase que implementa la interface ParametroDao para obtener los parametros
 * del sistema de la BD, mediante la utilizacion del patron DAO (Data Access
 * Object).
 * nextval
 * @author Brian Hernandez Garcia
 * 
 */
@Repository("AvisoObraDao")
@Transactional
public class AvisoObraDaoImpl extends AbstractDaoImpl<RotAvisoObra, Long> implements AvisoObraDao {

	public List<RotAvisoObra> findByRfc(String cveRfc) {
		List<RotAvisoObra> listRotAvisoObra = null;

		String sql = "SELECT avisoObra FROM RotAvisoObra avisoObra WHERE avisoObra.rotInformacionPatron.cveRfc = :cveRfc ";

		Query query = getSession().createQuery(sql).setParameter("cveRfc", cveRfc);

		listRotAvisoObra = this.findMany(query);
		return listRotAvisoObra;
	}
	
	public void updateEstatusAvisoObra(Long cveAvisoObra) {

		String sql = "UPDATE RotAvisoObra set refEstadoReg = 0 " + " WHERE cveAvisoObra = :cveAvisoObra";
		Query query = getSession().createQuery(sql).setParameter("cveAvisoObra", cveAvisoObra);
		query.executeUpdate();

	}
	
	public String getCveAvisoObra() {

		String secuencia = null;

		try {
			String sql = "SELECT LPad(SEQ_ROTAVISOOBRA.NEXTVAL, 8, '0') FROM dual";

			Query query = getSession().createSQLQuery(sql);

			secuencia = (String) this.findOneObjectBySqlQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return secuencia;
	}


	public RotAvisoObra findByCveRegistroAvisoObra(String cveRegistroAvisoObra, String rfc, String registroPatronal) {

		RotAvisoObra rotAvisoObra = null;

		String sql = " SELECT avisoObra "
					+ "FROM RotAvisoObra avisoObra, RotInformacionPatron patron "
					+ "WHERE avisoObra.cveRegistroAvisoObra = :cveRegistroAvisoObra "
					+ "AND avisoObra.refEstadoReg = 1 "
					+ "AND avisoObra.rotInformacionPatron.cveInformacionPatron = patron.cveInformacionPatron "
					+ "AND patron.cveRfc= :rfc "
					+ "AND patron.cveRegPatronal = :registroPatronal";

		Query query = this.getSession().createQuery(sql);
		query.setParameter("cveRegistroAvisoObra", cveRegistroAvisoObra);
		query.setParameter("rfc", rfc);
		query.setParameter("registroPatronal", registroPatronal);
		

		rotAvisoObra = (RotAvisoObra) query.uniqueResult();

		return rotAvisoObra;
	}
	
	public RotAvisoObra findByCveRegistroAvisoObra(String cveRegistroAvisoObra) {

		RotAvisoObra rotAvisoObra = null;

		String sql = " SELECT avisoObra "
					+ "FROM RotAvisoObra avisoObra "
					+ "WHERE avisoObra.cveRegistroAvisoObra = :cveRegistroAvisoObra ";

		Query query = this.getSession().createQuery(sql);
		query.setParameter("cveRegistroAvisoObra", cveRegistroAvisoObra);		

		rotAvisoObra = (RotAvisoObra) query.uniqueResult();

		return rotAvisoObra;
	}


	public RotAvisoObra findByRegistroPatronal(String registroPatronal) {
		RotAvisoObra rotAvisoObra = null;

		String sql = " SELECT avisoObra "
					+ "FROM RotAvisoObra avisoObra, RotInformacionPatron patron "
					+ "WHERE patron.cveRegPatronal = :registroPatronal "
					+ "AND avisoObra.rotInformacionPatron.cveInformacionPatron = patron.cveInformacionPatron ";
					
		Query query = this.getSession().createQuery(sql);
		query.setParameter("registroPatronal", registroPatronal);

		rotAvisoObra = (RotAvisoObra) query.uniqueResult();
		return rotAvisoObra;
	}




	public RotAvisoObra findByEstatus(String estatus) {
		RotAvisoObra rotAvisoObra = null;

		String sql = " SELECT avisoObra "
					+ "FROM RotAvisoObra avisoObra "
					+ "AND avisoObra.refEstadoReg = 1 ";
					
		Query query = this.getSession().createQuery(sql);

		rotAvisoObra = (RotAvisoObra) query.uniqueResult();
		return rotAvisoObra;
	}

}
