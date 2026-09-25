/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import org.apache.log4j.Logger;

/**
 * @author ghdolores
 *
 */
public class PreguntaParser {
	private static final Logger logger = Logger.getLogger(PreguntaParser.class);
/*
	public static DitPregunta modelToPersist(Pregunta entrada) throws DerechohabientesBusinessException{
		DitPregunta salida=null;
		if(entrada!=null){
			try {
				salida = new DitPregunta();
				 salida.setCveIdPregunta(entrada.getIdPregunta());
				 salida.setDesPregPrimeraPersona(entrada.getPreguntaPrimeraPersona());
				 salida.setDesPregTerceraPersona(entrada.getPreguntaTerceraPersona());
				 salida.setDicCategoriaPregunta(CategoriaPreguntaParser.modelToPersist(entrada.getCategoriaPregunta()));
				 salida.setDicTipoCuestionario(TipoCuestionarioParser.modelToPersist(entrada.getTipoCuestionario()));
				// salida.setDicTipoPregunta(TipoPreguntaParser.modelToPersist(entrada.getTipoPregunta()));
				// salida.setDitRespuestaSiNo(ditRespuestaSiNo)
				 salida.setIndObligatorio(new BigDecimal(0));
				 if(entrada.isObligatorio())
					 salida.setIndObligatorio(new BigDecimal(1));
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_PREGUNTA, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_PREGUNTA+" | "+e.getMessage());
			}
			 
			 
		}
		
		return salida;	
	}
	
	public static Pregunta persisToModel(DitPregunta entrada) throws DerechohabientesBusinessException{
		Pregunta salida=null;
		if(entrada!=null){
			try {
				salida=new Pregunta();
				salida.setCategoriaPregunta(CategoriaPreguntaParser.persisToModel(entrada.getDicCategoriaPregunta()));
				salida.setIdPregunta(entrada.getCveIdPregunta());
				salida.setObligatorio(false);
				if(entrada.getIndObligatorio().intValue()==1){
					salida.setObligatorio(true);
				}
				salida.setPreguntaPrimeraPersona(entrada.getDesPregPrimeraPersona());
				salida.setPreguntaTerceraPersona(entrada.getDesPregTerceraPersona());
				salida.setTipoCuestionario(TipoCuestionarioParser.persisToModel(entrada.getDicTipoCuestionario()));
				/*salida.setTipoPregunta(TipoPreguntaParser.persisToModel(entrada.getDicTipoPregunta()));
				*/
	/*
				salida.setRespuestas(RespuestaParser.persisToModelList(entrada.getDitRespuestas()));
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_PREGUNTA+" | "+e.getMessage());
			}

		}
		return salida;
	}
	
	public static Pregunta persisToModelSinREspuesta(DitPregunta entrada) throws DerechohabientesBusinessException{
		Pregunta salida=null;
		if(entrada!=null){
			try {
				salida=new Pregunta();
				salida.setCategoriaPregunta(CategoriaPreguntaParser.persisToModel(entrada.getDicCategoriaPregunta()));
				salida.setIdPregunta(entrada.getCveIdPregunta());
				salida.setObligatorio(false);
				if(entrada.getIndObligatorio().intValue()==1){
					salida.setObligatorio(true);
				}
				salida.setPreguntaPrimeraPersona(entrada.getDesPregPrimeraPersona());
				salida.setPreguntaTerceraPersona(entrada.getDesPregTerceraPersona());
				salida.setTipoCuestionario(TipoCuestionarioParser.persisToModel(entrada.getDicTipoCuestionario()));
				//salida.setTipoPregunta(TipoPreguntaParser.persisToModel(entrada.getDicTipoPregunta()));
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_PREGUNTA+" | "+e.getMessage());
			}

		}
		return salida;
	}
	
	public static List<Pregunta> persisToModelList(List<DitPregunta> entrada) throws DerechohabientesBusinessException{
		
		List<Pregunta> salida=new ArrayList<Pregunta>();
		if(entrada!=null && entrada.size() > 0){
			
			for (DitPregunta ditPregunta : entrada) {
				salida.add(persisToModel(ditPregunta));
			}
			
		}
		return salida;
	}
	
	*/
}
