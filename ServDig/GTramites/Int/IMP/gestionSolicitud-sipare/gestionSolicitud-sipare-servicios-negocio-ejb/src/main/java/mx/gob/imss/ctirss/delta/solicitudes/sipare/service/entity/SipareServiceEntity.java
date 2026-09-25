package mx.gob.imss.ctirss.delta.solicitudes.sipare.service.entity;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.FiltroSolicitud;
import mx.gob.imss.ctirss.delta.solicitudes.sipare.service.model.GraficaSolicitudes;

@Stateless(mappedName = "sipareServiceEntity")
public class SipareServiceEntity extends SipareAbstractEntity implements
		SipareServiceEntityLocal {

	@SuppressWarnings("unchecked")
	@Override
	public List<GraficaSolicitudes> obtenerUsuariosSipare(
			FiltroSolicitud filtros) {

		List<GraficaSolicitudes> movimientos = new ArrayList<GraficaSolicitudes>();

		StringBuffer sqlQuery = new StringBuffer();
		
		if (filtros.isAgruparPorMes()) {
			sqlQuery.append("SELECT 6 AS MES, 2013 AS ANIO, 111451 AS TOTAL "); 
			sqlQuery.append("FROM DUAL UNION ");
			sqlQuery.append("SELECT 7 AS MES, 2013 AS ANIO, 73812 AS TOTAL "); 
			sqlQuery.append("FROM DUAL UNION ");
			sqlQuery.append("SELECT 8 AS MES, 2013 AS ANIO, 40830 AS TOTAL "); 
			sqlQuery.append("FROM DUAL UNION ");
			sqlQuery.append("SELECT 9 AS MES, 2013 AS ANIO, 43341 AS TOTAL "); 
			sqlQuery.append("FROM DUAL UNION ");
			sqlQuery.append("SELECT 10 AS MES, 2013 AS ANIO, 26661 AS TOTAL "); 
			sqlQuery.append("FROM DUAL UNION ");
			sqlQuery.append("SELECT 11 AS MES, 2013 AS ANIO, 21864 AS TOTAL "); 
			sqlQuery.append("FROM DUAL UNION ");
			sqlQuery.append("SELECT 12 AS MES, 2013 AS ANIO, 16207 AS TOTAL "); 
			sqlQuery.append("FROM DUAL UNION ");
			sqlQuery.append("SELECT 01 AS MES, 2014 AS ANIO, 11876 AS TOTAL "); 
			sqlQuery.append("FROM DUAL UNION ");
			sqlQuery.append("SELECT EXTRACT(MONTH FROM SPA.STP_REGISTRO) AS MES, ");
			sqlQuery.append("EXTRACT(YEAR FROM SPA.STP_REGISTRO) AS ANIO, ");
		} else {
			sqlQuery.append("SELECT TO_CHAR(STP_REGISTRO,'yyyy-mm-dd'), ");
		}
		sqlQuery.append("COUNT(*) AS TOTAL ");
		sqlQuery.append("FROM SIPARE_OWNER.SPA_USUARIO SPA ");
		if (filtros.isAgruparPorMes()) {
			sqlQuery.append("WHERE TRUNC(SPA.STP_REGISTRO) > TO_DATE('31-01-2014','dd-mm-yyyy') ");
			sqlQuery.append("GROUP BY EXTRACT(MONTH FROM SPA.STP_REGISTRO), ");
			sqlQuery.append("EXTRACT(YEAR FROM SPA.STP_REGISTRO) ");
		} else {
			sqlQuery.append("WHERE TRUNC(SPA.STP_REGISTRO) BETWEEN ");
			sqlQuery.append("TRUNC(:fechaInicio) AND TRUNC(:fechaFin) ");
			sqlQuery.append("GROUP BY TO_CHAR(STP_REGISTRO,'yyyy-mm-dd') ");
		}
		sqlQuery.append("ORDER BY 1,2");

		Query query = this.em.createNativeQuery(sqlQuery.toString());
		if (!filtros.isAgruparPorMes()) {
			query.setParameter("fechaInicio", filtros.getFechaInicioPresentacion());
			query.setParameter("fechaFin", filtros.getFechaFinPresentacion());
		}

		List<Object[]> resultados = query.getResultList();

		GraficaSolicitudes elemento = null;

		Calendar calendar = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fecha = null;
		int valor = 0;
		
		for (Object[] resultado : resultados) {
			elemento = new GraficaSolicitudes();
			
			if (filtros.isAgruparPorMes()) {
				calendar.set(Calendar.DATE, 1);
				calendar.set(Calendar.MONTH, ((BigDecimal) resultado[0]).intValue() - 1);
				calendar.set(Calendar.YEAR, ((BigDecimal) resultado[1]).intValue());
				
				fecha = df.format(calendar.getTime());
				valor = ((BigDecimal) resultado[2]).intValue();
			} else {
				fecha = resultado[0].toString();
				valor = ((BigDecimal) resultado[1]).intValue();
			}
			
			elemento.setFecha(fecha);
			elemento.setValue(valor);

			movimientos.add(elemento);
		}

		return movimientos;
	}
}
