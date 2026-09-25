package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.math.BigDecimal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.MovimientoAseguradoParserServiceLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.TipoMovimientoAseguradoParserServiceLocal;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.asegurado.MovimientoAsegurado;
import mx.gob.imss.ctirss.delta.model.asegurado.TipoMovtoAsegurado;
import mx.gob.imss.ctirss.delta.persistence.DicTipoMovtoAsegurado;
import mx.gob.imss.ctirss.delta.persistence.DitMovimientoAsegurado;


@Stateless(name = "movimientoAseguradoDao", mappedName = "movimientoAseguradoDao")
public class MovimientoAseguradoDao extends AbstractServiceEntity implements MovimientoAseguradoDaoLocal{

	@EJB MovimientoAseguradoParserServiceLocal movimientoAseguradoParserServiceLocal;
	@EJB TipoMovimientoAseguradoParserServiceLocal tipoMovimientoAseguradoParserServiceLocal;
	
	@Override
	public MovimientoAsegurado getSalarioBaseUltimoMovBaja(Long idAsignacionNSS) throws DerechohabientesBusinessException,Exception{
	
		DitMovimientoAsegurado movimientoAsegurado = null;
		
		try {
			Query query = em.createNamedQuery("ultimoSalarioBaja");
			query.setParameter("idAsignacionNSS", idAsignacionNSS);

			movimientoAsegurado =  query.getResultList().size() > 0 ? (DitMovimientoAsegurado) query.getResultList().get(0) : null;
			
		} catch (Exception e) {
			log.error("Error - getSalarioBaseUltimoMovBaja", e);
			throw e;
		}				
		
		return movimientoAseguradoParserServiceLocal.persistToModel(movimientoAsegurado);
	}
	
	@Override
	public Double getSalarioMinimoPorDelegacion(Long idDelegacion) throws Exception{
		BigDecimal salario = new BigDecimal("0.0");
		
		try {
			Query query = em.createNamedQuery("salarioMinimoPorDelegacion");
			query.setParameter("idDelegacion", idDelegacion);

			salario =  (BigDecimal) (query.getResultList().size() > 0 ?  query.getResultList().get(0) : new BigDecimal("0.0"));
			
		} catch (Exception e) {
			log.error("Error - getSalarioMinimoPorDelegacion", e);
			throw e;
		}				

		return salario.doubleValue();
	}

	@Override
	public TipoMovtoAsegurado getTipoMovimiento(Long idTipoMovimiento)
			throws DerechohabientesBusinessException {
		DicTipoMovtoAsegurado dicTipo = null;
		TipoMovtoAsegurado tipo = null;
		
		dicTipo = em.find(DicTipoMovtoAsegurado.class, idTipoMovimiento);
		
		if(dicTipo != null) {
			tipo = tipoMovimientoAseguradoParserServiceLocal.persisToModel(dicTipo);
		}
		
		return tipo;
		
	}
	
	
}
