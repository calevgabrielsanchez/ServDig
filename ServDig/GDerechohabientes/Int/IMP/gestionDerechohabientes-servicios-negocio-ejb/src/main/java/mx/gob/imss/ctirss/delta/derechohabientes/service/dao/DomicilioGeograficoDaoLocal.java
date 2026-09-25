package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.asegurado.Asegurado;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilio;

/**
 * @author Juan Manuel Marquez
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 11/04/2012
 */
@Local
public interface DomicilioGeograficoDaoLocal {
	
	Domicilio ubicarDomicilioGeografico(TipoDomicilio tipoDomicilio);
	Domicilio getDomicilioGrupoFamiliar(Asegurado asegurado) throws DerechohabientesBusinessException, Exception;
	
}
