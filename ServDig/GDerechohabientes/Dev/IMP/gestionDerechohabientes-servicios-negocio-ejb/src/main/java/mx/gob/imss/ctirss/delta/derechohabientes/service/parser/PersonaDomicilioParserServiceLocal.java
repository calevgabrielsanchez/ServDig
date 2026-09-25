package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaDomicilio;
import mx.gob.imss.ctirss.delta.persistence.DitPersonafDom;

@Local
public interface PersonaDomicilioParserServiceLocal {
	
	public DitPersonafDom modelToPersist(PersonaDomicilio entrada) throws DerechohabientesBusinessException;
	public PersonaDomicilio persistToModel(DitPersonafDom entrada) throws DerechohabientesBusinessException, Exception ;
	public List<PersonaDomicilio> persistToModelList(List<DitPersonafDom> entrada) throws DerechohabientesBusinessException, Exception;
}
