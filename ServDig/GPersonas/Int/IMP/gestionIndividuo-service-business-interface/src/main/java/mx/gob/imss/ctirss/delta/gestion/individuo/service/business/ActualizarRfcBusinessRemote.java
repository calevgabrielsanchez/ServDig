package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

@Remote
public interface ActualizarRfcBusinessRemote {
	
	Map<String, Object> solicitarActualizarRfc(Fisica fisica);
	
	void actualizarRfc(Fisica fisica) throws ClienteWebserviceSatRfcException, 
		ErrorComparacionDatosSATException, PersonasNoLocalizadasException;
	
}
