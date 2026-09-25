package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.FisicaParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;

@Stateless( name = "fisicaParserService", mappedName = "fisicaParserService")
public class FisicaParserService implements FisicaParserServiceLocal{

	@EJB PersonaDomicilioParserServiceLocal personaDomicilioParserServiceLocal;
	
	@Override
	public DitPersona modelToPersist(Fisica entrada) throws DerechohabientesBusinessException {
		// TODO Auto-generated method stub
		DitPersona salida=null;
		if(entrada !=null){
			salida = FisicaParser.modelToPersist(entrada);
		}
		
		return salida;
	}

	@Override
	public Fisica persisToModel(DitPersona entrada) throws Exception {
		// TODO Auto-generated method stub
		Fisica salida=null;
		if(entrada!=null){
			salida = FisicaParser.persisToModel(entrada);
			salida.setPersonaDomicilio(personaDomicilioParserServiceLocal.persistToModelList(entrada.getDitPersonafDoms()));
		}
		return salida;
	}

}
