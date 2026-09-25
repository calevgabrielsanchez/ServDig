package mx.gob.imss.ctirss.delta.cobranza.service.utility;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.cobranza.modelo.Factor;
import mx.gob.imss.ctirss.delta.cobranza.service.entities.DCopFactor;

@Local
public interface FactorUtilityServiceLocal {

	Factor converEntityToModel(DCopFactor dCopFactor);
	List<Factor> convertirListEntityToListModel(List<DCopFactor> dCopFactors);
}
