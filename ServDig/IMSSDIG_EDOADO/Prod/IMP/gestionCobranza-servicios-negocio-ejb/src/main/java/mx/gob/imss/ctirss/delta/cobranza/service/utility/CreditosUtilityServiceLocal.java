package mx.gob.imss.ctirss.delta.cobranza.service.utility;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.cobranza.modelo.Credito;
import mx.gob.imss.ctirss.delta.cobranza.modelo.CreditoRCV;
import mx.gob.imss.ctirss.delta.cobranza.service.entities.HCopCreditosTot;
import mx.gob.imss.ctirss.delta.cobranza.service.entities.HRcvCreditosTot;

@Local
public interface CreditosUtilityServiceLocal {

	Credito convertEntityToModel(HCopCreditosTot hCredito);
	List<Credito> convertListEntiryToListModel(List<HCopCreditosTot> hCreditos);
	
	CreditoRCV convertirEntityToModelRCV(HRcvCreditosTot hRCredito);
	List<CreditoRCV> convertirListEntityToModelRCV(List<HRcvCreditosTot> hRCreditos);
	
}
