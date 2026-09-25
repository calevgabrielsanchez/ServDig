package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.PersonaParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.TipoPerInteresadaSolParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.PersonaInteresadaSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DicTipoPerInteresadaSol;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaInteresadaSol;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitud;

import org.apache.log4j.Logger;

@Stateless(name="personaInteresadaSolParser", mappedName = "personaInteresadaSolParser")
public class PersonaInteresadaSolParser implements PersonaInteresadaSolParserLocal {
	private static final Logger logger = Logger.getLogger(PersonaInteresadaSolParser.class);

@Override
	public  DitPersonaInteresadaSol modelToPersist(PersonaInteresadaSolicitud entrada) throws DerechohabientesBusinessException{
		DitPersonaInteresadaSol salida=null;
		if(entrada!=null){
			try {
				salida=new DitPersonaInteresadaSol();
				if(entrada.getCveIdPerTramInteresadaSol()!=null){
					salida.setCveIdPerTramInteresadaSol(entrada.getCveIdPerTramInteresadaSol());
				}
				salida.setDitSolicitud(new DitSolicitud());
				salida.getDitSolicitud().setCveIdSolicitud(entrada.getIdSolicitud());
				salida.setDitPersona(new DitPersona());
				salida.getDitPersona().setCveIdPersona(entrada.getPersona().getIdPersona());
				salida.setDicTipoPersonaInteresadaSol(new DicTipoPerInteresadaSol());
				salida.getDicTipoPersonaInteresadaSol().setCveTipoInteresadaSol(entrada.getTipoPersonaInteresadaSol().getCveTipoInteresadaSol());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_PERSONA_INTERESADA_SOL, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_PERSONA_INTERESADA_SOL+" | "+e.getMessage());
			}	
						
		}
		return salida;
		
	}
	
@Override
	public  PersonaInteresadaSolicitud persisToModel(DitPersonaInteresadaSol entrada) throws DerechohabientesBusinessException{
		
		PersonaInteresadaSolicitud  salida=null;
		if(entrada!=null){
			try {
				salida=new PersonaInteresadaSolicitud();
				salida.setCveIdPerTramInteresadaSol(entrada.getCveIdPerTramInteresadaSol());
				salida.setTipoPersonaInteresadaSol(TipoPerInteresadaSolParser.persisToModel(entrada.getDicTipoPersonaInteresadaSol()));
				salida.setPersona(PersonaParser.persisToModel(entrada.getDitPersona()));
				salida.setIdSolicitud(entrada.getDitSolicitud().getCveIdSolicitud());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_PERSONA_INTERESADA_SOL+" | "+e.getMessage());
			}	
			
		
		}
		
		return salida;
		
	}
	
@Override
	public  List<PersonaInteresadaSolicitud> persisToModelList(List<DitPersonaInteresadaSol> entrada) throws DerechohabientesBusinessException{
		
		List<PersonaInteresadaSolicitud>  salida= new ArrayList<PersonaInteresadaSolicitud>();
		if(entrada.size() > 0){
			try {				
				for(DitPersonaInteresadaSol interesadaSol: entrada) {
					salida.add(persisToModel(interesadaSol));
				}
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_PERSONA_INTERESADA_SOL+" | "+e.getMessage());
			}	
					
		}
		
		return salida;
		
	}
}
