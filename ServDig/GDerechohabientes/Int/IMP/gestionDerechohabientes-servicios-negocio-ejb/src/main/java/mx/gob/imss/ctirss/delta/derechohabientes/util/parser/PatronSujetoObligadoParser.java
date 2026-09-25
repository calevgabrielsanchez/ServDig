package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.persistence.DitPatronGeneral;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;

public class PatronSujetoObligadoParser {
	private static final Logger logger = Logger.getLogger(PatronSujetoObligadoParser.class);

	public static DitPatronSujetoObligado modelToPersist(SujetoObligado entrada) throws DerechohabientesBusinessException{
		DitPatronSujetoObligado salida = null;
		if(entrada != null){
			try {
				salida=new DitPatronSujetoObligado();
				salida.setDicModalidad(ModalidadParser.modelToPersist(entrada.getModalidad()));
				salida.setCveIdPatronSujetoObligado(entrada.getCveIdSujetoObligado());
				salida.setFecRegistroAlta(entrada.getFechaAlta());	
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_PATRON_SUJETO_OBLIGADO, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_PATRON_SUJETO_OBLIGADO+" | "+e.getMessage());
			}
				
		}
		return salida;
	}
	
	public static SujetoObligado persisToModel(DitPatronSujetoObligado entrada) throws DerechohabientesBusinessException{
		SujetoObligado salida= null;
		if(entrada != null){
			try {
				salida=new SujetoObligado();
				salida.setModalidad(ModalidadParser.persisToModel(entrada.getDicModalidad()));
				salida.setCveIdSujetoObligado(entrada.getCveIdPatronSujetoObligado());
				salida.setFechaAlta(entrada.getFecRegistroAlta());
				if(entrada.getDitPatronGenerals().size() > 0){
					for(DitPatronGeneral registroPatronal : entrada.getDitPatronGenerals()){
						salida.setNumeroRegistroPatronal(registroPatronal.getRegPatron());
					}
				}
				if(entrada.getDitPersonaFisica() != null)			
					salida.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
				if(entrada.getDitPersonaMoral() != null)			
					salida.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_PATRON_SUJETO_OBLIGADO+" | "+e.getMessage());
			}
			
			
		}
		return salida;
	}
	
}