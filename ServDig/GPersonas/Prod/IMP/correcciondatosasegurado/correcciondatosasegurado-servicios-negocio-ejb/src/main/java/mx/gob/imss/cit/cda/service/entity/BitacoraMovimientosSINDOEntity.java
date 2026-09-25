package mx.gob.imss.cit.cda.service.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.BitacoraMovimientoSindoCDA;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class BitacoraMovimientosSINDOEntity extends AbstractServiceEntity
		implements BitacoraMovimientosSINDOLocal {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, List<BitacoraMovimientoSindoCDA>> obtenerBitacoraMovimientos(
			Date fechaMovimientos) {
		// leer tabla y generar cadenas
		Map<String, List<BitacoraMovimientoSindoCDA>> bitacora = new HashMap<String, List<BitacoraMovimientoSindoCDA>>();
		StringBuffer jpaQuery = new StringBuffer();
		Query query = null;
		List<Object[]> resultList = null;
		logger.debug("obtener bitacora del dia {}", fechaMovimientos);

		jpaQuery.append("SELECT DISTINCT ");
		jpaQuery.append("bitacora.REF_Observacion, ");
		jpaQuery.append("bitacora.REF_FOLIO_SOLICITUD, ");
		jpaQuery.append("bitacora.NUM_NSS, ");
		jpaQuery.append("bitacora.CVE_ID_ORIGEN_RES_MVTO, ");
		jpaQuery.append("bitacora.CVE_ID_RESULTADO ");
		jpaQuery.append("FROM DIT_BITACORA_PROC_SINDO_CDA bitacora ");
		jpaQuery.append("WHERE ");
		jpaQuery.append("bitacora.FEC_REGISTRO_ALTA >= :fechaMovimientos ");
		jpaQuery.append("GROUP BY ");
		jpaQuery.append("bitacora.REF_FOLIO_SOLICITUD, ");
		jpaQuery.append("bitacora.CVE_ID_ORIGEN_RES_MVTO, ");
		jpaQuery.append("bitacora.NUM_NSS, ");
		jpaQuery.append("bitacora.CVE_ID_RESULTADO, ");
		jpaQuery.append("bitacora.REF_Observacion ");
		jpaQuery.append("ORDER BY ");
		jpaQuery.append("bitacora.REF_FOLIO_SOLICITUD, ");
		jpaQuery.append("bitacora.REF_Observacion ");
		jpaQuery.append("DESC");


		logger.debug("bitacora query [{}]", jpaQuery.toString());
		query = this.em.createNativeQuery(jpaQuery.toString());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaMovimientos);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		query.setParameter("fechaMovimientos", calendar.getTime());
		resultList = query.getResultList();
		for (Object[] result : resultList) {
			BitacoraMovimientoSindoCDA movimiento = getMovimientoSindoCDA(result);
			if (bitacora.containsKey(result[1])) {
				bitacora.get(result[1]).add(movimiento);
			} else {
				List<BitacoraMovimientoSindoCDA> registro = new ArrayList<BitacoraMovimientoSindoCDA>();
				registro.add(movimiento);
				bitacora.put(result[1].toString(), registro);
			}
		}
		return bitacora;
	}
	
	private BitacoraMovimientoSindoCDA getMovimientoSindoCDA(Object[] result){
		
		BitacoraMovimientoSindoCDA movimiento = new BitacoraMovimientoSindoCDA();

		movimiento.setFolio(result[1].toString());
		movimiento.setNss(result[2].toString());
		movimiento.setObservacion(result[0] != null ? result[0].toString(): null);
		movimiento.setOrigen(Integer.valueOf(result[3].toString()));
		movimiento.setResultado(result[4].toString());
		
		return movimiento;
	}

}
