package mx.gob.imss.distss.gestion.cuestionario.service.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.util.CollectionUtils;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.persistence.DicCuestionario;
import mx.gob.imss.distss.gestion.cuestionario.excepcion.CuestionarioNoExisteException;

@Stateless(mappedName = "cuestionarioServiceEntity")
public class CuestionarioServiceEntity extends AbstractServiceEntity implements
		CuestionarioServiceEntityLocal {

	@Override
	public DicCuestionario obtenerCuestionario(int idCuestionario)
			throws CuestionarioNoExisteException {
				
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("select cuestionario from DicCuestionario cuestionario ");
		jpaQuery.append("where cuestionario.cveIdCuestionario = :cveIdCuestionario ");
		
		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("cveIdCuestionario", idCuestionario);
		
		DicCuestionario dicCuestionario = null;
		
		try {
			dicCuestionario = (DicCuestionario) query.getSingleResult();
		} catch (NoResultException e) {
			throw new CuestionarioNoExisteException(
					"El cuestionario para el tipo " + idCuestionario
							+ " no existe");
		}
		
		return dicCuestionario;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> obtenerIdPreguntasDependientesCuestionario(
			int idCuestionario) {
		
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("SELECT DEP.CVE_ID_PREGUNTA_DEP "); 
		jpaQuery.append("FROM DIC_DEPENDENCIA_OPC_PREGUNTA DEP ");
		jpaQuery.append("INNER JOIN DIC_PREGUNTA PREG ");
		jpaQuery.append("ON DEP.CVE_ID_PREGUNTA_DEP = PREG.CVE_ID_PREGUNTA "); 
		jpaQuery.append("INNER JOIN DIC_SECCION SEC ");
		jpaQuery.append("ON PREG.CVE_ID_SECCION = SEC.CVE_ID_SECCION "); 
		jpaQuery.append("INNER JOIN DIC_CUESTIONARIO CUEST ");
		jpaQuery.append("ON SEC.CVE_ID_CUESTIONARIO = CUEST.CVE_ID_CUESTIONARIO "); 
		jpaQuery.append("WHERE CUEST.CVE_ID_CUESTIONARIO = :cveIdCuestionario");
				
		Query query = this.em.createNativeQuery(jpaQuery.toString());
		query.setParameter("cveIdCuestionario", idCuestionario);
		
		List<BigDecimal> result = query.getResultList();
		List<Integer> lstPregDependiente = null;
		
		if (!CollectionUtils.isEmpty(result)){
			lstPregDependiente = new ArrayList<Integer>(result.size());
			for (BigDecimal id : result) {
				lstPregDependiente.add(id.intValue());
			}
		}
		
		return lstPregDependiente;
	}
	
}
