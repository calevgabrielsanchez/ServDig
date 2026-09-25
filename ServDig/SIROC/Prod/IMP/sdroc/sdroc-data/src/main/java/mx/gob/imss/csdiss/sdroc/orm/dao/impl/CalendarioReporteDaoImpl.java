package mx.gob.imss.csdiss.sdroc.orm.dao.impl;



import mx.gob.imss.csdiss.sdroc.dto.CalendarioReporteDTO;
import mx.gob.imss.csdiss.sdroc.entity.RocCalendarioReporte;
import mx.gob.imss.csdiss.sdroc.orm.dao.CalendarioReporteDao;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.imss.csdiss.sdroc.entity.RocCalendarioReporte;
import mx.gob.imss.csdiss.sdroc.orm.dao.CalendarioReporteDao;

/**
 * 
 * Clase que implementa la interface ParametroDao para obtener los parametros
 * del sistema de la BD, mediante la utilizacion del patron DAO (Data Access
 * Object).
 * 
 * @author Brian Hernandez Garcia
 * 
 */
@Repository("CalendarioReporteDao")
@Transactional
public class CalendarioReporteDaoImpl extends AbstractDaoImpl<RocCalendarioReporte, Long> implements CalendarioReporteDao {

	
	@Override
	public CalendarioReporteDTO findMesPresentacionDto(String mes) {
		CalendarioReporteDTO calendario = null;
		String query = "select "+
           "roccalenda0_.CVE_BIM_CALENDARIO "+
//           "roccalenda0_.FEC_FIN_PER_DECLARADO, "+
//           "roccalenda0_.FEC_FIN_PER_PRESENTADO, "+
//           "roccalenda0_.FEC_INI_PER_DECLARADO, "+
//           "roccalenda0_.FEC_INI_PER_PRESENTADO, "+
	       "from ROC_CALENDARIO_REPORTE roccalenda0_ "+
	       "where to_date('"+mes+"', 'MM') between to_date(to_char(roccalenda0_.FEC_INI_PER_PRESENTADO, 'MM'), 'MM')  "+
	       	"and to_date(to_char(roccalenda0_.FEC_FIN_PER_PRESENTADO, 'MM'), 'MM')";
		
		Object resultado = this.getSession().createSQLQuery(query).uniqueResult();
		if(resultado != null) {
			calendario = new CalendarioReporteDTO();
			calendario.setCveBimCalendario(((BigDecimal) resultado).longValue());
		}
		
		return calendario;
	}

	public RocCalendarioReporte findMesPresentacion(String mes) {
		
		RocCalendarioReporte rocCalendarioReporte = null;	
		
		String sql = " SELECT calendarioReporte FROM RocCalendarioReporte calendarioReporte WHERE "
				+ "to_date ('" + mes + "','MM') between "
				+ "to_date(to_char(calendarioReporte.fecIniPerPresentado, 'MM'), 'MM') and  to_date(to_char(calendarioReporte.fecFinPerPresentado, 'MM'), 'MM')";
		
		Query query = this.getSession().createQuery(sql);
		
		rocCalendarioReporte = (RocCalendarioReporte) query.uniqueResult();
		
		return rocCalendarioReporte;
	}
	
	public RocCalendarioReporte findMesDeclarar(String mes) {
		
		RocCalendarioReporte rocCalendarioReporte = null;	
		
		String sql = " SELECT calendarioReporte FROM RocCalendarioReporte calendarioReporte WHERE "
				+ "to_date ('" + mes + "','MM') between "
				+ "to_date(to_char(calendarioReporte.fecIniPerDeclarado, 'MM'), 'MM') and  to_date(to_char(calendarioReporte.fecFinPerDeclarado, 'MM'), 'MM')";
		
		Query query = this.getSession().createQuery(sql);
		
		rocCalendarioReporte = (RocCalendarioReporte) query.uniqueResult();
		
		return rocCalendarioReporte;
	}

	public List<RocCalendarioReporte> calendariosOrdenadosAsc() {
		
		List<RocCalendarioReporte> rocCalendarioReporte = null;	
		
		String sql = " SELECT calendarioReporte FROM RocCalendarioReporte calendarioReporte ORDER by 1 ";
		
		Query query = this.getSession().createQuery(sql);
		
		rocCalendarioReporte = findMany(query);
		
		return rocCalendarioReporte;
	}

	public RocCalendarioReporte findCveBimCalendario(Long cveBimCalendario) {
		
		RocCalendarioReporte rocCalendarioReporte = null;	
		
		String sql = " SELECT calendarioReporte FROM RocCalendarioReporte calendarioReporte WHERE "
				+ "cveBimCalendario = :cveBimCalendario";
		
		Query query = getSession().createQuery(sql).setParameter("cveBimCalendario", cveBimCalendario);

		rocCalendarioReporte = this.findOne(query);
		
		return rocCalendarioReporte;
	}
	
	
}
