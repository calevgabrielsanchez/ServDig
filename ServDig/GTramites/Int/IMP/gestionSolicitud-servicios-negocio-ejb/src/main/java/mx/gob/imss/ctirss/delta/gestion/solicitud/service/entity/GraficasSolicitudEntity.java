package mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity;

import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.graficas.GraficaRequest;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.graficas.GraficaResponse;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.util.CollectionUtils;

@Stateless(mappedName = "graficasSolicitudEntity")
public class GraficasSolicitudEntity extends AbstractServiceEntity implements
		GraficasSolicitudEntityLocal {

	@SuppressWarnings("unchecked")
	@Override
	public List<GraficaResponse> getCifrasGraficaBarras(GraficaRequest request) {
				
		StringBuffer sql = new StringBuffer();
		if (request.getTipoAgrupacion().equals("porOrigen")) {
			sql.append("SELECT cve_id_origen_solicitud AS origen, ");
		} else if (request.getTipoAgrupacion().equals("porOrigenTramite")) {
			sql.append("SELECT cve_id_origen_solicitud AS origen, ");
			sql.append("cve_id_tipo_tramite AS tramite, ");
		}
		sql.append("SUM(total_solic) as total ");
		sql.append("FROM MV_GRAFICA_SOLICITUDES ");
		sql.append("WHERE TO_DATE(fec_conclusion, 'yyyy-mm-dd') BETWEEN ");
		sql.append("TRUNC(:fechaInicio) AND TRUNC(:fechaFin) ");
		sql.append("AND cve_id_origen_solicitud IN :origenes ");
		sql.append("AND cve_id_tipo_tramite IN :tramites "); 
		sql.append("AND cve_id_estado_solicitud IN :edoSolic ");
		if (request.getTipoAgrupacion().equals("porOrigen")) {
			sql.append("GROUP BY cve_id_origen_solicitud ");
		} else if (request.getTipoAgrupacion().equals("porOrigenTramite")) {
			sql.append("GROUP BY cve_id_origen_solicitud, cve_id_tipo_tramite ");
			sql.append("ORDER BY 2, 1");
		}	 
		
		SQLQuery query = this.getSession().createSQLQuery(sql.toString());
		if (request.getTipoAgrupacion().equals("porOrigen")) {
			query.addScalar("origen", IntegerType.INSTANCE);
		} else if (request.getTipoAgrupacion().equals("porOrigenTramite")) {
			query.addScalar("origen", IntegerType.INSTANCE);
			query.addScalar("tramite", IntegerType.INSTANCE);
		}
		query.addScalar("total", LongType.INSTANCE);
		query.setParameter("fechaInicio", request.getFechaInicio());
		query.setParameter("fechaFin", request.getFechaFin());
		query.setParameterList("origenes", request.getOrigenes());
		query.setParameterList("tramites", request.getTramites());
		query.setParameterList("edoSolic", request.getEstados());
		query.setResultTransformer(Transformers.aliasToBean(GraficaResponse.class));

		List<GraficaResponse> list = query.list();
		
		this.log.debug("Se encontraron " + list.size() + " resultados para gráfica de barras " + request.getIdGrafica());
		
		return list;		
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GraficaResponse> getCifrasGraficaLineas(
			GraficaRequest request) {
							
		StringBuffer sql = new StringBuffer();
		if (request.getTipoAgrupacion().equals("mesHistorico")) {
			sql.append("SELECT to_char(TO_DATE(fec_conclusion, 'yyyy-mm-dd'), 'YYYY-MM') AS fecha, ");
		} else {
			sql.append("SELECT fec_conclusion AS fecha, ");
		}
		if (!request.getTipoAgrupacion().equals("diaHistoricoTotalTramtiesAgrupados")) {
			sql.append("cve_id_origen_solicitud AS origen, ");
			sql.append("cve_id_tipo_tramite AS tramite, ");
			sql.append("cve_id_estado_solicitud AS estado, ");
		}
		sql.append("sum(total_solic) AS total ");
		sql.append("FROM MV_GRAFICA_SOLICITUDES ");
		sql.append("WHERE TO_DATE(fec_conclusion, 'yyyy-mm-dd') ");
		sql.append("BETWEEN TRUNC(:fechaInicio) AND TRUNC(:fechaFin) ");
		if (!CollectionUtils.isEmpty(request.getOrigenes())) {
			sql.append("AND cve_id_origen_solicitud IN :origenes ");
		}
		sql.append("AND cve_id_tipo_tramite IN :tramites ");
		sql.append("AND cve_id_estado_solicitud IN :estados ");
		if (request.getTipoAgrupacion().equals("mesHistorico")) {
			sql.append("GROUP BY to_char(TO_DATE(fec_conclusion, 'yyyy-mm-dd'), 'YYYY-MM'), ");
		} else {
			sql.append("GROUP BY fec_conclusion ");
			if (!request.getTipoAgrupacion().equals("diaHistoricoTotalTramtiesAgrupados")) {
				sql.append(", ");
			}
		}
		if (!request.getTipoAgrupacion().equals("diaHistoricoTotalTramtiesAgrupados")) {
			sql.append("cve_id_origen_solicitud, cve_id_tipo_tramite, ");
			sql.append("cve_id_estado_solicitud ");
		}
		sql.append("ORDER BY 1, 2");
		
		SQLQuery query = this.getSession().createSQLQuery(sql.toString());
		query.addScalar("fecha", StringType.INSTANCE);
		if (!request.getTipoAgrupacion().equals("diaHistoricoTotalTramtiesAgrupados")) {
			query.addScalar("origen", IntegerType.INSTANCE);
			query.addScalar("tramite", IntegerType.INSTANCE);
			query.addScalar("estado", IntegerType.INSTANCE);
		}
		query.addScalar("total", LongType.INSTANCE);
		query.setParameter("fechaInicio", request.getFechaInicio());
		query.setParameter("fechaFin", request.getFechaFin());
		if (!CollectionUtils.isEmpty(request.getOrigenes())) {
			query.setParameterList("origenes", request.getOrigenes());
		}
		query.setParameterList("tramites", request.getTramites());
		query.setParameterList("estados", request.getEstados());
		query.setResultTransformer(Transformers.aliasToBean(GraficaResponse.class));
		
		List<GraficaResponse> list = query.list();
		
		this.log.debug("Se encontraron " + list.size() + " resultados para gráfica de líneas " + request.getIdGrafica());
		
		return list;
	}
}