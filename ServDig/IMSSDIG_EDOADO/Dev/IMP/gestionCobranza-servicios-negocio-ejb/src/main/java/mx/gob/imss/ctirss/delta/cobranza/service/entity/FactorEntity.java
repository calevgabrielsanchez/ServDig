package mx.gob.imss.ctirss.delta.cobranza.service.entity;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.cobranza.modelo.Factor;
import mx.gob.imss.ctirss.delta.cobranza.service.entities.DCopFactor;
import mx.gob.imss.ctirss.delta.cobranza.service.utility.FactorUtilityServiceLocal;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

@Stateless(name = "factorEntity", mappedName = "factorEntity")
public class FactorEntity extends AbstractEntity implements FactorEntityLocal {

	@EJB FactorUtilityServiceLocal factorUtilityServiceLocal;

	@SuppressWarnings("unchecked")
	@Override
	public List<Factor> findFactorAllManMapping() {
		List<Factor> factores = null;
		List<DCopFactor> dFactores = null;
		
		Criteria queryFactor = this.getSession().createCriteria(DCopFactor.class);
		queryFactor.addOrder(Order.asc("id.periodo"));
		
		dFactores = queryFactor.list();
		factores = factorUtilityServiceLocal.convertirListEntityToListModel(dFactores);
		
		return factores;
	}

	@Override
	public Factor getFactorByPeriodo(String periodo) {

		Criteria queryFactor = this.getSession().createCriteria(
				DCopFactor.class);
		queryFactor.add(Restrictions.eq("id.periodo", Long.valueOf(periodo)));

		DCopFactor entity = (DCopFactor) queryFactor.uniqueResult();

		return this.factorUtilityServiceLocal.converEntityToModel(entity);
	}
}

