package mx.gob.imss.ctirss.delta.cobranza.service.utility;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.cobranza.modelo.Factor;
import mx.gob.imss.ctirss.delta.cobranza.service.entities.DCopFactor;

@Stateless(name = "factorUtilityService", mappedName = "factorUtilityService")
public class FactorUtilityService implements FactorUtilityServiceLocal {

	@Override
	public Factor converEntityToModel(DCopFactor dCopFactor) {
		Factor factor = null;
		
		if(dCopFactor != null) {
			factor = new Factor();
			factor.setFacAct(dCopFactor.getFacAct().toString());
			factor.setFacInt(dCopFactor.getFacInt().toString());
			factor.setPeriodo(""+dCopFactor.getId().getPeriodo());
			factor.setRecargos(dCopFactor.getRecargos().toString());
			factor.setInpc(dCopFactor.getInpc().toString());
		}
		
		return factor;
	}

	@Override
	public List<Factor> convertirListEntityToListModel(
			List<DCopFactor> dCopFactors) {
		List<Factor> factores= null;
		
		if(dCopFactors != null && !dCopFactors.isEmpty()) {
			factores = new ArrayList<Factor>();
			
			for(DCopFactor dfactor: dCopFactors) {
				Factor fact = this.converEntityToModel(dfactor);
				factores.add(fact);
			}
		}
		return factores;
	}
}
