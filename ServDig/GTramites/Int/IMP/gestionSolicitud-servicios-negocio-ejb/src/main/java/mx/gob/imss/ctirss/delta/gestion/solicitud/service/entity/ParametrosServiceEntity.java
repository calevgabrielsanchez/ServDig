package mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.persistence.DicParametros;

@Stateless
public class ParametrosServiceEntity extends AbstractServiceEntity implements
		ParametrosServiceEntityLocal {

	@Override
	public String obtenerValorDeParametroPorLlave(String llave) {

		String resultado = "";

		String sqlQuery = "select param from DicParametros param where param.desLlaveParametro = :llave";
		
		Query query = em.createQuery(sqlQuery);
		query.setParameter("llave", llave);

		try {
			DicParametros parametro = (DicParametros) query.getSingleResult();
			resultado = parametro.getDesValorParametro();
		}catch (NoResultException noResultException){
			log.info("Parametro del sistema no configurado: "+llave);
		}

		return resultado;
	}
	
	
	@Override
	public String obtenerValorDeParametroDeDelegacionCDA(String idDelegacion) {

		String resultado = "";

		String sqlQuery = "select param from DicParametros param where param.desLlaveParametro = :llave";
		
		Query query = em.createQuery(sqlQuery);
		query.setParameter("llave", "PARAMETROS_SUBDELEGACION_CDAV2_"+idDelegacion);

		try {
			DicParametros parametro = (DicParametros) query.getSingleResult();
			resultado = parametro.getDesValorParametro();
		}catch (NoResultException noResultException){
			log.info("Parametro del sistema no configurado: "+" PARAMETROS_SUBDELEGACION_CDAV2_"+idDelegacion);
		}

		return resultado;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, String> obtenerGrupoParametrosPorLlave(List<String> llaves) {
		
		String sqlQuery = "select param from DicParametros param where param.desLlaveParametro in (:llaves)";
		
		Query query = em.createQuery(sqlQuery);
		query.setParameter("llaves", llaves);
		
		List<DicParametros> parametrosTmp = query.getResultList();
		
		Map<String, String> parametros = null;
		
		if (parametrosTmp != null && !parametrosTmp.isEmpty()) {
			parametros = new HashMap<String, String>();
			
			for (DicParametros parametro : parametrosTmp) {
				parametros.put(parametro.getDesLlaveParametro(), parametro.getDesValorParametro());
			}
		}
		
		return parametros;
	}

}
