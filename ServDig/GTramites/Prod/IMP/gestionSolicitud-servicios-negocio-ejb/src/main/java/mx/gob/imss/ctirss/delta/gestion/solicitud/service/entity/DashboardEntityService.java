package mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity;

import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.graficas.GraficaRequest;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.graficas.GraficaResponse;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.graficas.PeriodoDashboardEnum;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;

@Stateless(mappedName = "dashboardEntityService")
public class DashboardEntityService extends AbstractServiceEntity implements
		DashboardEntityServiceLocal {

	@SuppressWarnings("unchecked")
	@Override
	public List<GraficaResponse> getGranTotal() {
		
		StringBuffer sqlQuery = new StringBuffer();
		sqlQuery.append("SELECT DECODE(o.des_origen_solicitud, 'INT PORTAL CIUDADANO', ");
		sqlQuery.append("'P. CIUDADANO', o.des_origen_solicitud) AS descOrigen, ");
		sqlQuery.append("SUM(total_solic) AS total ");
		sqlQuery.append("FROM mv_grafica_solicitudes v ");
		sqlQuery.append("INNER JOIN dic_origen_solicitud o ");
		sqlQuery.append("ON v.cve_id_origen_solicitud = o.cve_id_origen_solicitud ");
		sqlQuery.append("WHERE cve_id_estado_solicitud = :cveAtendida ");
		sqlQuery.append("GROUP BY o.des_origen_solicitud ");
		sqlQuery.append("ORDER BY 2 DESC");
				
		SQLQuery query = this.getSession().createSQLQuery(sqlQuery.toString());
		query.addScalar("descOrigen");
		query.addScalar("total", LongType.INSTANCE);
		query.setParameter("cveAtendida", EstadoSolicitudEnum.ATENDIDA.getCodigo().intValue());
		query.setResultTransformer(Transformers.aliasToBean(GraficaResponse.class));
		
		List<GraficaResponse> contadores = query.list();
		
		this.log.debug("Se encontraron " + contadores.size()
				+ " resultados para dashboard");
		
		return contadores;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GraficaResponse> getRankingTramites(GraficaRequest request, int numResultados) {
		
		StringBuffer sqlQuery = new StringBuffer();
		sqlQuery.append("SELECT * FROM ( ");
		sqlQuery.append("SELECT t.des_tipo_tramite AS descTipoTramite, ");
		sqlQuery.append("SUM(v.total_solic) AS total ");
		sqlQuery.append("FROM mv_grafica_solicitudes v ");
		sqlQuery.append("INNER JOIN dic_tipo_tramite t ");
		sqlQuery.append("ON v.cve_id_tipo_tramite = t.cve_id_tipo_tramite ");
		sqlQuery.append("WHERE cve_id_estado_solicitud = :cveAtendida ");
		if (request != null) {
			if (request.getPeriodo() == PeriodoDashboardEnum.HOY.getId()) {
				sqlQuery.append("AND to_date(fec_conclusion, 'yyyy-mm--dd') = TRUNC(sysdate) ");
			} else if (request.getPeriodo() == PeriodoDashboardEnum.SEMANAL.getId()) {
				sqlQuery.append("AND to_date(fec_conclusion, 'yyyy-mm--dd') ");
				sqlQuery.append("BETWEEN TRUNC(SYSDATE,'IW') AND TRUNC(SYSDATE, 'IW') + 6 ");
			} else if (request.getPeriodo() == PeriodoDashboardEnum.MENSUAL.getId()) { 
				sqlQuery.append("AND to_date(fec_conclusion, 'yyyy-mm--dd') ");
				sqlQuery.append("BETWEEN TRUNC(SYSDATE,'MON') AND TRUNC(SYSDATE) ");
			} else if (request.getPeriodo() == PeriodoDashboardEnum.ANUAL.getId()) {
				sqlQuery.append("AND to_date(fec_conclusion, 'yyyy-mm--dd') ");
				sqlQuery.append("BETWEEN TRUNC(SYSDATE,'YY') AND TRUNC(SYSDATE) ");
			} else if (request.getPeriodo() == PeriodoDashboardEnum.RANGO_FECHAS.getId()) {
				sqlQuery.append("AND to_date(fec_conclusion, 'yyyy-mm--dd') ");
				sqlQuery.append("BETWEEN :fechaInicio AND :fechaFin ");
			}
		}
		sqlQuery.append("GROUP BY t.des_tipo_tramite ");
		sqlQuery.append("ORDER BY 2 DESC");
		sqlQuery.append(") WHERE rownum <= :numResultados");
				
		SQLQuery query = this.getSession().createSQLQuery(sqlQuery.toString());
		query.addScalar("descTipoTramite");
		query.addScalar("total", LongType.INSTANCE);
		query.setParameter("cveAtendida", EstadoSolicitudEnum.ATENDIDA.getCodigo().intValue());
		if (request != null) {
			if (request.getPeriodo() == PeriodoDashboardEnum.RANGO_FECHAS.getId()) {
				query.setParameter("fechaInicio", request.getFechaInicio());
				query.setParameter("fechaFin", request.getFechaFin());
			}
		}
		query.setParameter("numResultados", numResultados);
		query.setResultTransformer(Transformers.aliasToBean(GraficaResponse.class));
		
		List<GraficaResponse> contadores = query.list();
		
		this.log.debug("Se encontraron " + contadores.size()
				+ " resultados para dashboard");
		
		return contadores;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GraficaResponse> getContadores(GraficaRequest request) {
		
		StringBuffer sqlQuery = new StringBuffer();
		sqlQuery.append("SELECT ");
		sqlQuery.append("TO_DATE(v.fec_conclusion, 'yyyy-mm--dd') AS fechaConclusion, ");
		sqlQuery.append("v.cve_id_origen_solicitud AS origen, ");
		sqlQuery.append("v.cve_id_tipo_tramite AS tramite, ");
		sqlQuery.append("t.des_tipo_tramite AS descTipoTramite, ");
		sqlQuery.append("v.cve_id_estado_solicitud AS estado, ");
		sqlQuery.append("v.total_solic AS total ");
		sqlQuery.append("FROM mv_grafica_solicitudes v ");
		sqlQuery.append("INNER JOIN dic_tipo_tramite t ");
		sqlQuery.append("ON v.cve_id_tipo_tramite = t.cve_id_tipo_tramite ");
		sqlQuery.append("WHERE 1 = 1 ");
		if (request.getTramites() != null) {
			sqlQuery.append("AND v.cve_id_tipo_tramite IN (:tiposTramite) ");
		}
		if (request.getPeriodo() == PeriodoDashboardEnum.HOY.getId()) {
			sqlQuery.append("AND to_date(v.fec_conclusion, 'yyyy-mm--dd') = TRUNC(sysdate) ");
		} else if (request.getPeriodo() == PeriodoDashboardEnum.SEMANAL.getId()) {
			sqlQuery.append("AND to_date(v.fec_conclusion, 'yyyy-mm--dd') ");
			sqlQuery.append("BETWEEN TRUNC(SYSDATE,'IW') AND TRUNC(SYSDATE, 'IW') + 6 ");
		} else if (request.getPeriodo() == PeriodoDashboardEnum.MENSUAL.getId()) { 
			sqlQuery.append("AND to_date(v.fec_conclusion, 'yyyy-mm--dd') ");
			sqlQuery.append("BETWEEN TRUNC(SYSDATE,'MON') AND TRUNC(SYSDATE) ");
		} else if (request.getPeriodo() == PeriodoDashboardEnum.ANUAL.getId()) {
			sqlQuery.append("AND to_date(v.fec_conclusion, 'yyyy-mm--dd') ");
			sqlQuery.append("BETWEEN TRUNC(SYSDATE,'YY') AND TRUNC(SYSDATE) ");
		} else if (request.getPeriodo() == PeriodoDashboardEnum.RANGO_FECHAS.getId()) {
			sqlQuery.append("AND to_date(v.fec_conclusion, 'yyyy-mm--dd') ");
			sqlQuery.append("BETWEEN :fechaInicio AND :fechaFin ");
		}
		sqlQuery.append("ORDER BY v.fec_conclusion, 2, 3, 5 ");
		
		SQLQuery query = this.getSession().createSQLQuery(sqlQuery.toString());
		query.addScalar("fechaConclusion");
		query.addScalar("origen", IntegerType.INSTANCE);
		query.addScalar("tramite", IntegerType.INSTANCE);
		query.addScalar("descTipoTramite");
		query.addScalar("estado", IntegerType.INSTANCE);
		query.addScalar("total", LongType.INSTANCE);
		if (request.getTramites() != null) {
			query.setParameterList("tiposTramite", request.getTramites());
		}
		if (request.getPeriodo() == PeriodoDashboardEnum.RANGO_FECHAS.getId()) {
			query.setParameter("fechaInicio", request.getFechaInicio());
			query.setParameter("fechaFin", request.getFechaFin());
		}
		query.setResultTransformer(Transformers.aliasToBean(GraficaResponse.class));
		
		List<GraficaResponse> contadores = query.list();
		
		this.log.debug("Se encontraron " + contadores.size()
				+ " resultados para dashboard");
		
		return contadores;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GraficaResponse> getContadoresPorOrigen(GraficaRequest request) {
		
		StringBuffer sqlQuery = new StringBuffer();
		sqlQuery.append("SELECT ");
		if (request.getTipoAgrupacion().equals("anios")) {
			sqlQuery.append("TO_CHAR(to_date(fec_conclusion, 'yyyy-mm--dd'), 'yyyy') AS fecha, ");
		} else if (request.getTipoAgrupacion().equals("meses")) {
			sqlQuery.append("TO_CHAR(to_date(fec_conclusion, 'yyyy-mm--dd'), 'yyyy-mm') AS fecha, ");
		} else if (request.getTipoAgrupacion().equals("dias")) {
			sqlQuery.append("TO_DATE(fec_conclusion, 'yyyy-mm--dd') AS fechaConclusion, ");
		}
		sqlQuery.append("v.cve_id_origen_solicitud AS origen, ");
		sqlQuery.append("SUM(total_solic) AS total ");
		sqlQuery.append("FROM mv_grafica_solicitudes v ");
		sqlQuery.append("WHERE cve_id_estado_solicitud = :idAtendidas ");
		sqlQuery.append("AND to_date(fec_conclusion, 'yyyy-mm--dd') ");
		if (request.getPeriodo() == PeriodoDashboardEnum.ANUAL.getId()) {
			sqlQuery.append("BETWEEN TRUNC(SYSDATE, 'YY') AND TRUNC(SYSDATE) ");			
		} else if (request.getPeriodo() == PeriodoDashboardEnum.MENSUAL.getId()) {
			sqlQuery.append("BETWEEN TRUNC(SYSDATE, 'mm') AND TRUNC(SYSDATE) ");
		} else if (request.getPeriodo() == PeriodoDashboardEnum.SEMANAL.getId()) {
			sqlQuery.append("BETWEEN TRUNC(SYSDATE, 'iw') AND TRUNC(SYSDATE, 'iw') + 6 ");
		} else if (request.getPeriodo() == PeriodoDashboardEnum.HOY.getId()) {
			sqlQuery.append(" = TRUNC(SYSDATE) ");
		} else if (request.getPeriodo() == PeriodoDashboardEnum.RANGO_FECHAS.getId()) {
			sqlQuery.append("BETWEEN :fechaInicio AND :fechaFin ");
		}
		if (request.getTipoAgrupacion().equals("anios")) {
			sqlQuery.append("GROUP BY TO_CHAR(to_date(fec_conclusion, 'yyyy-mm--dd'), 'yyyy'), ");
			sqlQuery.append("v.cve_id_origen_solicitud ");
		} else if (request.getTipoAgrupacion().equals("meses")) {
			sqlQuery.append("GROUP BY TO_CHAR(to_date(fec_conclusion, 'yyyy-mm--dd'), 'yyyy-mm'), ");
			sqlQuery.append("v.cve_id_origen_solicitud ");
		} else if (request.getTipoAgrupacion().equals("dias")) {
			sqlQuery.append("GROUP BY TO_DATE(fec_conclusion, 'yyyy-mm--dd'), v.cve_id_origen_solicitud ");
		} else if (request.getTipoAgrupacion().equals("dia")) {
			sqlQuery.append("GROUP BY cve_id_origen_solicitud ");
		}
		sqlQuery.append("ORDER BY 1, 2 ");
		
		SQLQuery query = this.getSession().createSQLQuery(sqlQuery.toString());
		if (request.getTipoAgrupacion().equals("anios") 
				|| request.getTipoAgrupacion().equals("meses")) {
			query.addScalar("fecha");
		} else if (request.getTipoAgrupacion().equals("dias")) {
			query.addScalar("fechaConclusion");
		} 
		query.addScalar("origen", IntegerType.INSTANCE);
		query.addScalar("total", LongType.INSTANCE);
		query.setParameter("idAtendidas", EstadoSolicitudEnum.ATENDIDA.getCodigo().intValue());
		if (request.getPeriodo() == PeriodoDashboardEnum.RANGO_FECHAS.getId()) {
			query.setParameter("fechaInicio", request.getFechaInicio());
			query.setParameter("fechaFin", request.getFechaFin());
		}
		query.setResultTransformer(Transformers.aliasToBean(GraficaResponse.class));
		
		List<GraficaResponse> contadores = query.list();
		
		this.log.debug("Se encontraron " + contadores.size()
				+ " resultados para dashboard");
		
		return contadores;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GraficaResponse> getContadoresPorEstado(GraficaRequest request) {
		
		StringBuffer sqlQuery = new StringBuffer();
		sqlQuery.append("SELECT edo.des_estado_solicitud AS descEstadoSolicitud, ");
		sqlQuery.append("SUM(TOTAL_SOLIC) AS total ");
		sqlQuery.append("FROM mv_grafica_solicitudes v ");
		sqlQuery.append("INNER JOIN dic_estado_solicitud edo ");
		sqlQuery.append("on v.cve_id_estado_solicitud = edo.cve_id_estado_solicitud ");
		sqlQuery.append("WHERE 1 = 1 ");
		if (request.getPeriodo() == PeriodoDashboardEnum.HOY.getId()) {
			sqlQuery.append("AND to_date(fec_conclusion, 'yyyy-mm--dd') = TRUNC(SYSDATE) ");
		} else if (request.getPeriodo() == PeriodoDashboardEnum.SEMANAL.getId()) {
			sqlQuery.append("AND to_date(fec_conclusion, 'yyyy-mm--dd') ");
			sqlQuery.append("BETWEEN TRUNC(SYSDATE, 'iw') AND TRUNC(SYSDATE, 'iw') + 6 ");
		} else if (request.getPeriodo() == PeriodoDashboardEnum.MENSUAL.getId()) { 
			sqlQuery.append("AND to_date(fec_conclusion, 'yyyy-mm--dd') ");
			sqlQuery.append("BETWEEN TRUNC(SYSDATE,'mm') AND TRUNC(SYSDATE) ");
		} else if (request.getPeriodo() == PeriodoDashboardEnum.ANUAL.getId()) {
			sqlQuery.append("AND to_date(fec_conclusion, 'yyyy-mm--dd') ");
			sqlQuery.append("BETWEEN TRUNC(SYSDATE,'YY') AND TRUNC(SYSDATE) ");
		} else if (request.getPeriodo() == PeriodoDashboardEnum.RANGO_FECHAS.getId()) {
			sqlQuery.append("AND to_date(fec_conclusion, 'yyyy-mm--dd') ");
			sqlQuery.append("BETWEEN :fechaInicio AND :fechaFin ");
		}
		sqlQuery.append("GROUP BY edo.cve_id_estado_solicitud, edo.des_estado_solicitud ");
		sqlQuery.append("ORDER BY edo.cve_id_estado_solicitud, 2");
		
		SQLQuery query = this.getSession().createSQLQuery(sqlQuery.toString());
		query.addScalar("descEstadoSolicitud");
		query.addScalar("total", LongType.INSTANCE);
		if (request.getPeriodo() == PeriodoDashboardEnum.RANGO_FECHAS.getId()) {
			query.setParameter("fechaInicio", request.getFechaInicio());
			query.setParameter("fechaFin", request.getFechaFin());
		}
		query.setResultTransformer(Transformers.aliasToBean(GraficaResponse.class));
		
		List<GraficaResponse> contadores = query.list();
		
		this.log.debug("Se encontraron " + contadores.size()
				+ " resultados para dashboard");
		
		return contadores;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GraficaResponse> getContadoresAtendidasPeriodos(GraficaRequest request) {
		
		StringBuffer sqlQuery = new StringBuffer();
		
		StringBuffer sqlCommon = new StringBuffer();
		sqlCommon.append("DECODE(SUM(total_solic), null, 0, SUM(total_solic)) AS total ");
		sqlCommon.append("FROM mv_grafica_solicitudes ");
		sqlCommon.append("WHERE CVE_ID_ESTADO_SOLICITUD = :edoAtendida ");
		
		sqlQuery.append("SELECT 'ACTUAL' AS periodo, ");
		sqlQuery.append(sqlCommon);
		if (request.getPeriodo() == PeriodoDashboardEnum.HOY.getId()) {
			sqlQuery.append("AND to_date(fec_conclusion, 'yyyy-mm--dd') = TRUNC(SYSDATE) ");
		} else if (request.getPeriodo() == PeriodoDashboardEnum.SEMANAL.getId()) {
			sqlQuery.append("AND to_date(fec_conclusion, 'yyyy-mm--dd') BETWEEN TRUNC(SYSDATE, 'iw') ");
			sqlQuery.append("AND TRUNC(SYSDATE, 'iw') + 6 ");
		} else if (request.getPeriodo() == PeriodoDashboardEnum.MENSUAL.getId()) { 
			sqlQuery.append("AND to_date(fec_conclusion, 'yyyy-mm--dd') BETWEEN TRUNC(SYSDATE, 'mm') ");
			sqlQuery.append("AND TRUNC(SYSDATE) ");
		} else if (request.getPeriodo() == PeriodoDashboardEnum.ANUAL.getId()) {
			sqlQuery.append("AND EXTRACT(YEAR FROM to_date(fec_conclusion, 'yyyy-mm--dd')) = ");
			sqlQuery.append("EXTRACT(YEAR FROM sysdate) ");
		} else if (request.getPeriodo() == PeriodoDashboardEnum.RANGO_FECHAS.getId()) {
			sqlQuery.append("AND to_date(fec_conclusion, 'yyyy-mm--dd') ");
			sqlQuery.append("BETWEEN :fechaInicio AND :fechaFin ");
		}
		sqlQuery.append("UNION ALL ");
		sqlQuery.append("SELECT 'ANTERIOR' AS periodo, ");
		sqlQuery.append(sqlCommon);
		if (request.getPeriodo() == PeriodoDashboardEnum.HOY.getId()) {
			sqlQuery.append("AND to_date(fec_conclusion, 'yyyy-mm--dd') = TRUNC(SYSDATE - 1)");
		} else if (request.getPeriodo() == PeriodoDashboardEnum.SEMANAL.getId()) {
			sqlQuery.append("AND to_date(fec_conclusion, 'yyyy-mm--dd') BETWEEN TRUNC(SYSDATE - 7, 'iw') ");
			sqlQuery.append("AND TRUNC(SYSDATE - 7, 'iw') + 6");
		} else if (request.getPeriodo() == PeriodoDashboardEnum.MENSUAL.getId()) { 
			sqlQuery.append("AND to_date(fec_conclusion, 'yyyy-mm--dd') BETWEEN ");
			sqlQuery.append("TRUNC(ADD_MONTHS(SYSDATE, -1), 'MM') ");
			sqlQuery.append("AND last_Day(ADD_MONTHS(SYSDATE, - 1))");
		} else if (request.getPeriodo() == PeriodoDashboardEnum.ANUAL.getId()) {
			sqlQuery.append("AND EXTRACT(YEAR FROM to_date(fec_conclusion, 'yyyy-mm--dd')) = ");
			sqlQuery.append("EXTRACT(YEAR FROM sysdate) - 1");
		} else if (request.getPeriodo() == PeriodoDashboardEnum.RANGO_FECHAS.getId()) {
			sqlQuery.append("AND to_date(fec_conclusion, 'yyyy-mm--dd') ");
			sqlQuery.append("BETWEEN :fechaInicioAnterior AND :fechaFinAnterior ");
		}
				
		SQLQuery query = this.getSession().createSQLQuery(sqlQuery.toString());
		query.addScalar("periodo");
		query.addScalar("total", LongType.INSTANCE);
		query.setParameter("edoAtendida", EstadoSolicitudEnum.ATENDIDA.getCodigo().intValue());
		if (request.getPeriodo() == PeriodoDashboardEnum.RANGO_FECHAS.getId()) {
			query.setParameter("fechaInicio", request.getFechaInicio());
			query.setParameter("fechaFin", request.getFechaFin());
			query.setParameter("fechaInicioAnterior", request.getFechaInicioAnterior());
			query.setParameter("fechaFinAnterior", request.getFechaFinAnterior());
		}
		query.setResultTransformer(Transformers.aliasToBean(GraficaResponse.class));
		
		List<GraficaResponse> contadores = query.list();
		
		this.log.debug("Se encontraron " + contadores.size()
				+ " resultados para dashboard");
		
		return contadores;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GraficaResponse> getContadoresTipoTramiteOrigen(GraficaRequest request) {
		
		StringBuffer sqlQuery = new StringBuffer();
		sqlQuery.append("SELECT c.des_tipo_tramite AS descTipoTramite, ");
		sqlQuery.append("DECODE(o.des_origen_solicitud, 'INT PORTAL CIUDADANO', 'P. CIUDADANO', ");
		sqlQuery.append("o.des_origen_solicitud) AS descOrigen, ");
		sqlQuery.append("SUM(total_solic) AS total ");
		sqlQuery.append("FROM mv_grafica_solicitudes v ");
		sqlQuery.append("INNER JOIN dic_tipo_tramite c ");
		sqlQuery.append("ON v.cve_id_tipo_tramite = c.cve_id_tipo_tramite ");
		sqlQuery.append("INNER JOIN dic_origen_solicitud o ");
		sqlQuery.append("ON v.cve_id_origen_solicitud = o.cve_id_origen_solicitud ");
		sqlQuery.append("WHERE CVE_ID_ESTADO_SOLICITUD = :edoAtendida ");
		if (request.getPeriodo() == PeriodoDashboardEnum.HOY.getId()) {
			sqlQuery.append("AND to_date(fec_conclusion, 'yyyy-mm--dd') = TRUNC(SYSDATE)");
		} else if (request.getPeriodo() == PeriodoDashboardEnum.SEMANAL.getId()) {
			sqlQuery.append("AND to_date(fec_conclusion, 'yyyy-mm--dd') BETWEEN ");
			sqlQuery.append("TRUNC(SYSDATE, 'iw') AND TRUNC(SYSDATE, 'iw') + 6 ");
		} else if (request.getPeriodo() == PeriodoDashboardEnum.MENSUAL.getId()) { 
			sqlQuery.append("AND to_date(fec_conclusion, 'yyyy-mm--dd') BETWEEN ");
			sqlQuery.append("TRUNC(SYSDATE, 'mm') AND TRUNC(SYSDATE) ");
		} else if (request.getPeriodo() == PeriodoDashboardEnum.ANUAL.getId()) {
			sqlQuery.append("AND to_date(fec_conclusion, 'yyyy-mm--dd') BETWEEN ");
			sqlQuery.append("TRUNC(SYSDATE, 'YY') AND TRUNC(SYSDATE) ");
		} else if (request.getPeriodo() == PeriodoDashboardEnum.RANGO_FECHAS.getId()) {
			sqlQuery.append("AND to_date(fec_conclusion, 'yyyy-mm--dd') ");
			sqlQuery.append("BETWEEN :fechaInicio AND :fechaFin ");
		}
		sqlQuery.append("GROUP BY c.des_tipo_tramite, ");
		sqlQuery.append("o.des_origen_solicitud ");
		sqlQuery.append("ORDER BY 3 DESC");
		
		SQLQuery query = this.getSession().createSQLQuery(sqlQuery.toString());
		query.addScalar("descTipoTramite");
		query.addScalar("descOrigen");
		query.addScalar("total", LongType.INSTANCE);
		query.setParameter("edoAtendida", EstadoSolicitudEnum.ATENDIDA.getCodigo().intValue());
		if (request.getPeriodo() == PeriodoDashboardEnum.RANGO_FECHAS.getId()) {
			query.setParameter("fechaInicio", request.getFechaInicio());
			query.setParameter("fechaFin", request.getFechaFin());
		}
		query.setResultTransformer(Transformers.aliasToBean(GraficaResponse.class));
		
		List<GraficaResponse> contadores = query.list();
		
		this.log.debug("Se encontraron " + contadores.size()
				+ " resultados para dashboard");
		
		return contadores;
	}
}
