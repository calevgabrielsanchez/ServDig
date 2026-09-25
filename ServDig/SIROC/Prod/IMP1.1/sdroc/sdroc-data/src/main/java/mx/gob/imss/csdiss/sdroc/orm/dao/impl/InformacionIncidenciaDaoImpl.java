package mx.gob.imss.csdiss.sdroc.orm.dao.impl;

import java.util.Calendar;
import java.util.List;

import mx.gob.imss.csdiss.sdroc.entity.RotInformacionIncidencia;
import mx.gob.imss.csdiss.sdroc.orm.dao.InformacionIncidenciaDao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * Clase que implementa la interface ParametroDao para obtener los parametros
 * del sistema de la BD, mediante la utilizacion del patron DAO (Data Access
 * Object).
 * 
 * @author Brian Hernandez Garcia
 * 
 */
@Repository("InformacionIncidenciaDao")
@Transactional
public class InformacionIncidenciaDaoImpl extends AbstractDaoImpl<RotInformacionIncidencia, Long>
		implements InformacionIncidenciaDao {

	public RotInformacionIncidencia findLastIncidenciaByTipoIncidenciaByCveInformacionObra(Long cveInformacionObra,
			Long cveTipoIncidencia) {

		RotInformacionIncidencia rotInformacionIncidencia = null;

		try {
			String sql = "SELECT inforIncidencia FROM RotInformacionIncidencia inforIncidencia WHERE "
					+ "inforIncidencia.cveInformacionObra = :cveInformacionObra AND inforIncidencia.rocMotivoTipoIncidencia.rocTipoIncidencia.cveTipoIncidencia = :cveTipoIncidencia "
					+ "ORDER BY inforIncidencia.cveInformacionIncidencia DESC";
                    
			Query query = this.getSession().createQuery(sql)
					.setParameter("cveInformacionObra", cveInformacionObra)
					.setParameter("cveTipoIncidencia", cveTipoIncidencia);
			query.setMaxResults(1);
			rotInformacionIncidencia = (RotInformacionIncidencia) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rotInformacionIncidencia;
	}

	public int numReporteBimestralByCveInformacionObra(Long cveInformacionObra) {
		Long numIncumplimientos = null;

		try {
			String sql = "SELECT count(*) FROM RotInformacionIncidencia inforIncidencia  WHERE "
					+ "inforIncidencia.cveInformacionObra = :cveInformacionObra AND inforIncidencia.numAnio = :anio "
					+ "and inforIncidencia.rocMotivoTipoIncidencia.rocTipoIncidencia.cveTipoIncidencia = 6";

			int anio = Calendar.getInstance().get(Calendar.YEAR);

			numIncumplimientos = (Long) this.getSession().createQuery(sql)
					.setParameter("cveInformacionObra", cveInformacionObra).setParameter("anio", anio)
					.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return numIncumplimientos.intValue();
	}

	public RotInformacionIncidencia findLastReporteBimestralByCveInformacionObra(Long cveInformacionObra) {

		RotInformacionIncidencia rotInformacionIncidencia = null;

		// int anio = Calendar.getInstance().get(Calendar.YEAR);

		try {
			String sql = "SELECT inforIncidencia FROM RotInformacionIncidencia inforIncidencia WHERE "
					+ "inforIncidencia.cveInformacionObra = :cveInformacionObra "
					+ "and inforIncidencia.rocMotivoTipoIncidencia.rocTipoIncidencia.cveTipoIncidencia = 6 "
					+ "and inforIncidencia.fecRegistroBaja IS NULL "
					+ "ORDER BY inforIncidencia.cveInformacionIncidencia DESC";

			Query query = this.getSession().createQuery(sql).setParameter("cveInformacionObra",
					cveInformacionObra);
			query.setMaxResults(1);

			rotInformacionIncidencia = (RotInformacionIncidencia) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rotInformacionIncidencia;
	}

	public int numReporBimestralByCveInfoNoReportada(Long cveInformacionObra) {
		Long numIncumplimientos = null;

		try {
			String sql = "SELECT count(*) FROM RotInformacionIncidencia inforIncidencia  WHERE "
					+ "inforIncidencia.cveInformacionObra = :cveInformacionObra AND "
					+ " inforIncidencia.rocMotivoTipoIncidencia.rocTipoIncidencia.cveTipoIncidencia = 6 "
					+ "and  rocTipoRegistro.cveTipoRegistro = 3 " + "and inforIncidencia.cveInformacionIncidencia > ("
					+ "SELECT nvl(MAX(i.cveInformacionIncidencia),0) " + "FROM RotInformacionIncidencia i  WHERE "
					+ "i.cveInformacionObra = :cveInformacionObra AND "
					+ " i.rocMotivoTipoIncidencia.rocTipoIncidencia.cveTipoIncidencia = 6 "
					+ "and  i.rocTipoRegistro.cveTipoRegistro in (1,2) " + ")";

			numIncumplimientos = (Long) this.getSession().createQuery(sql)
					.setParameter("cveInformacionObra", cveInformacionObra).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return numIncumplimientos.intValue();
	}

	public int numReporBimestralByCveInfoPresentadas(Long cveInformacionObra, Long cveBimCalendario, int annio) {
		Long numIncumplimientos = null;

		try {
			String sql = "SELECT count(*) FROM RotInformacionIncidencia inforIncidencia  WHERE "
					+ "inforIncidencia.cveInformacionObra = :cveInformacionObra AND inforIncidencia.numAnio = :anio "
					+ "and inforIncidencia.rocMotivoTipoIncidencia.rocTipoIncidencia.cveTipoIncidencia = 6 "
					+ "and  rocTipoRegistro.cveTipoRegistro in (1,2) "
					+ "and inforIncidencia.rocCalendarioReporte.cveBimCalendario = :cveBimCalendario ";

			// int anio = Calendar.getInstance().get(Calendar.YEAR);

			numIncumplimientos = (Long) this.getSession().createQuery(sql)
					.setParameter("cveInformacionObra", cveInformacionObra).setParameter("anio", annio)
					.setParameter("cveBimCalendario", cveBimCalendario).uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return numIncumplimientos.intValue();
	}

	public RotInformacionIncidencia findLastReporteBimPresenByCveInfoObra(Long cveInformacionObra) {

		RotInformacionIncidencia rotInformacionIncidencia = null;

		try {
			String sql = "SELECT inforIncidencia FROM RotInformacionIncidencia inforIncidencia WHERE "
					+ "inforIncidencia.cveInformacionObra = :cveInformacionObra "
					+ "and inforIncidencia.rocMotivoTipoIncidencia.rocTipoIncidencia.cveTipoIncidencia = 6 "
					+ "and  inforIncidencia.rocTipoRegistro.cveTipoRegistro in (1,2) "
					+ "ORDER BY inforIncidencia.cveInformacionIncidencia DESC";

			Query query = this.getSession().createQuery(sql).setParameter("cveInformacionObra",
					cveInformacionObra);
			query.setMaxResults(1);

			rotInformacionIncidencia = (RotInformacionIncidencia) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rotInformacionIncidencia;
	}

	public List<RotInformacionIncidencia> findAllByCveInformacionObra(String cveInformacionObra) {
		Query query = null;
		try {
			String sql = "SELECT inforIncidencia FROM RotInformacionIncidencia inforIncidencia WHERE "
					+ "inforIncidencia.cveInformacionObra = :cveInformacionObra "
					+ "and inforIncidencia.rocTipoRegistro.cveTipoRegistro != 3"
					+ "ORDER BY inforIncidencia.cveInformacionIncidencia DESC";

			query = getSession().createQuery(sql).setParameter("cveInformacionObra", Long.valueOf(cveInformacionObra));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return this.findMany(query);
	}

	public void eliminaReporteBimByCveInfoObra(Long cveInformacionObra, String fechaFinObra, int annio) {

		try {
			String sql = "DELETE FROM RotInformacionIncidencia " + " WHERE cveInformacionIncidencia IN "
					+ "        (SELECT I.cveInformacionIncidencia "
					+ "           FROM RotInformacionIncidencia I, RotInformacionObra O "
					+ "          WHERE I.cveInformacionIncidencia >= "
					+ "                (SELECT I.cveInformacionIncidencia "
					+ "                   FROM RocCalendarioReporte     C, "
					+ "                        RotInformacionIncidencia I, "
					+ "                        RotInformacionObra       O "
					+ "                  WHERE TO_DATE(TO_CHAR(TO_DATE('" + fechaFinObra
					+ "', 'ddMMYYYY'), 'MM'), 'MM') BETWEEN TO_DATE(TO_CHAR(C.fecIniPerPresentado, 'MM'), 'MM') AND "
					+ "                        TO_DATE(TO_CHAR(C.fecFinPerPresentado, 'MM'), 'MM') "
					+ "                    AND I.rocCalendarioReporte.cveCalendarioRep = C.cveCalendarioRep "
					+ "                    AND I.cveInformacionObra = O.cveInformacionObra "
					+ "                    AND I.numAnio = " + annio
					+ "                    AND I.rocMotivoTipoIncidencia.rocTipoIncidencia.cveTipoIncidencia = 6 "
					+ "                    AND O.cveInformacionObra = :cveInformacionObra) "
					+ "            AND I.numAnio >= " + annio
					+ "            AND I.cveInformacionObra = O.cveInformacionObra "
					+ "            AND I.rocTipoRegistro.cveTipoRegistro = 3 "
					+ "            AND I.rocMotivoTipoIncidencia.rocTipoIncidencia.cveTipoIncidencia = 6 "
					+ "            AND O.cveInformacionObra = :cveInformacionObra) ";

			Query query = getSession().createQuery(sql).setParameter("cveInformacionObra", cveInformacionObra);
			query.executeUpdate();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	public void eliminaReporteBimByCveInfoObraDeclarado(Long cveInformacionObra, String fechaFinObra, int annio) {
		try {
			String sql = "DELETE FROM RotInformacionIncidencia " + " WHERE cveInformacionIncidencia IN "
					+ "        (SELECT I.cveInformacionIncidencia "
					+ "           FROM RotInformacionIncidencia I, RotInformacionObra O " + "          WHERE I.numAnio >= "
					+ annio + "            AND I.cveInformacionObra = O.cveInformacionObra "
					+ "            AND I.rocTipoRegistro.cveTipoRegistro = 3 "
					+ "            AND I.rocMotivoTipoIncidencia.rocTipoIncidencia.cveTipoIncidencia = 6 "
					+ "            AND O.cveInformacionObra = :cveInformacionObra) ";

			Query query = getSession().createQuery(sql).setParameter("cveInformacionObra", cveInformacionObra);
			query.executeUpdate();
		} catch (HibernateException e) {
			e.printStackTrace();
		}

	}

	public void eliminaReportesBimestralesNoPresentados(Long cveInformacionObra) {
		try {
			Long motivoIncidencia = 14L;
			String sql = "DELETE " + "FROM RotInformacionIncidencia "
					+ "WHERE rocMotivoTipoIncidencia.cveMotivoTipoIncidencia  = :cveMotivoIncidencia "
					+ "AND cveInformacionObra = :cveInformacionObra ";
			Query query = getSession().createQuery(sql).setParameter("cveMotivoIncidencia", motivoIncidencia)
					.setParameter("cveInformacionObra", cveInformacionObra);
			query.executeUpdate();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

}
