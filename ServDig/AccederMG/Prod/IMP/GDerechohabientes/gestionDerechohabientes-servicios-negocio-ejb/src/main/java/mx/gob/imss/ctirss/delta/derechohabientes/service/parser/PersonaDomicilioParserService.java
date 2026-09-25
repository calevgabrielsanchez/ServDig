package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaDomicilio;
import mx.gob.imss.ctirss.delta.persistence.DgDomicilioGeografico;
import mx.gob.imss.ctirss.delta.persistence.DicTipoDomicilio;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonafDom;

import org.apache.log4j.Logger;

@Stateless(name = "personaDomicilioParserService", mappedName = "personaDomicilioParserService")
public class PersonaDomicilioParserService implements PersonaDomicilioParserServiceLocal{
	
	private static final Logger logger = Logger.getLogger(PersonaDomicilioParserService.class);

	@EJB(mappedName = "domicilioServiceBusiness") 
	DomicilioServiceBusinessRemote domicilioServiceBusinessRemote;
	
	public DitPersonafDom modelToPersist(PersonaDomicilio entrada) throws DerechohabientesBusinessException {
		DitPersonafDom salida = null;
		
		if(entrada!= null)
		{	
			try {
				salida = new DitPersonafDom();
				salida.setCveIdPersonafDom(entrada.getCvePersonaDomicilio());
				salida.setDgDomicilioGeografico(new DgDomicilioGeografico());
				salida.getDgDomicilioGeografico().setDomicilioId(entrada.getDomicilio().getClave().longValue());
				salida.setDicTipoDomicilio(new DicTipoDomicilio());
				salida.getDicTipoDomicilio().setCveIdTipoDomicilio(entrada.getTipoDomicilio().getClave().longValue());
				salida.setDitPersona(new DitPersona());
				salida.getDitPersona().setCveIdPersona(entrada.getPersona().getIdPersona());	
				salida.setFecRegistroAlta(entrada.getFechaRegistroAlta());
				salida.setFecRegistroActualizado(entrada.getFechaRegistroActualizado());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_PERSONA_DOMICILIO_SERVICE, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_PERSONA_DOMICILIO_SERVICE+" | "+e.getMessage());
			}
					
		}
		
		return salida;
	}
	
	
	public PersonaDomicilio persistToModel(DitPersonafDom entrada) throws Exception{
		PersonaDomicilio salida = null;
		
		if(entrada!=null)
		{
			salida = new PersonaDomicilio();
			try {
				salida.setPersona(new Persona());
				salida.setCvePersonaDomicilio(entrada.getCveIdPersonafDom());
				salida.getPersona().setIdPersona(entrada.getDitPersona().getCveIdPersona());
				salida.setTipoDomicilio(new TipoDomicilio());
				salida.getTipoDomicilio().setClave(Integer.parseInt(""+entrada.getDicTipoDomicilio().getCveIdTipoDomicilio()));
				salida.getTipoDomicilio().setDescripcion(entrada.getDicTipoDomicilio().getDesTipoDomicilio());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_PERSONA_DOMICILIO_SERVICE+" | "+e.getMessage());
			}
			
			Domicilio buscar = new Domicilio();
			buscar.setClave(Integer.valueOf(""+entrada.getDgDomicilioGeografico().getDomicilioId()));
			try {
				salida.setDomicilio(domicilioServiceBusinessRemote.consultarDomicilio(buscar));
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw e;
			}
		}
		return salida;
	}
	
	public List<PersonaDomicilio> persistToModelList(List<DitPersonafDom> entrada) throws Exception {
		List<PersonaDomicilio> salida = null;
		
		if(entrada != null)
		{
			salida = new ArrayList<PersonaDomicilio>();
			for(DitPersonafDom personafdom : entrada) {
				salida.add(persistToModel(personafdom));
			}
		}
		return salida;
	}
}
