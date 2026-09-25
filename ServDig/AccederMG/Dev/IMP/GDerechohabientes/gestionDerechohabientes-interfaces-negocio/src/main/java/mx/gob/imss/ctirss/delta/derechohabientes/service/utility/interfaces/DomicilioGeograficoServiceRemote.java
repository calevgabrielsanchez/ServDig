package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.asegurado.Asegurado;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilio;


@Remote
public interface DomicilioGeograficoServiceRemote {
	
				
	Domicilio getDomicilioGrupoFam(Asegurado asegurado) throws DerechohabientesBusinessException, Exception;
	Domicilio ubicarDomicilioGeografico(TipoDomicilio tipoDomicilio);
	
}
