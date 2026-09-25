package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;



import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.derechohabientes.util.DateUtils;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;

public class FisicaParser {
	private static final Logger logger = Logger.getLogger(FisicaParser.class);

	public static DitPersona modelToPersist(Fisica entrada) throws DerechohabientesBusinessException{
		DitPersona salida=null;
		if(entrada !=null){
			try {
				salida=new DitPersona();
				salida.setCveIdPersona(entrada.getIdPersona());
				salida.setCurp(entrada.getCurp());
				salida.setFecDefuncion(entrada.getFechaDefuncion());
				salida.setFecNacimiento(entrada.getFechaNacimiento());
				salida.setNomNombre(entrada.getNombre());
				salida.setNomPrimerApellido(entrada.getPrimerApellido());
				salida.setNomSegundoApellido(entrada.getSegundoApellido());
				salida.setDgCatEstado(EntidadFederativaParser.modelToPersist(entrada.getLugarNacimiento()));
				salida.setDicEstadoCivil(EstadoCivilParser.modelToPersist(entrada.getEstadoCivil()));
				salida.setDicSexo(SexoParser.modelToPersist(entrada.getSexo()));
				salida.setNumMesNacReg(entrada.getMesRegistroNac());
				salida.setNumAnioNacReg(entrada.getAnioRegistroNac());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_FISICA, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_FISICA+" | "+e.getMessage());
			}
			
		}
		
		return salida;	
	}
	
	public static Fisica persisToModel(DitPersona entrada) throws DerechohabientesBusinessException{
		Fisica salida=null;
		if(entrada!=null){
			try {
				salida=new Fisica();
				salida.setIdPersona(entrada.getCveIdPersona());
				salida.setCurp(entrada.getCurp());
				salida.setFechaDefuncion(entrada.getFecDefuncion());
				salida.setFechaNacimiento(entrada.getFecNacimiento());
				if(salida.getFechaNacimiento()!= null) {
					salida.setFechaNacimientoFormateada(DateUtils.dateFormat(entrada.getFecNacimiento()));
				}
				salida.setNombre(entrada.getNomNombre());
				salida.setPrimerApellido(entrada.getNomPrimerApellido());
				salida.setSegundoApellido(entrada.getNomSegundoApellido());
				salida.setFechaDefuncion(entrada.getFecDefuncion());
				salida.setLugarNacimiento(EntidadFederativaParser.persisToModel(entrada.getDgCatEstado()));
				salida.setEstadoCivil(EstadoCivilParser.persisToModel(entrada.getDicEstadoCivil()));												
				salida.setSexo(SexoParser.persisToModel(entrada.getDicSexo()));
				salida.setMesRegistroNac(entrada.getNumMesNacReg());
				salida.setAnioRegistroNac(entrada.getNumAnioNacReg());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_FISICA+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
	
}