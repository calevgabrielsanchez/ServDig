package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import org.apache.log4j.Logger;

public class RespuestaParser {
	private static final Logger logger = Logger.getLogger(RespuestaParser.class);
/*
	public static DicRespuesta modelToPersist(Respuesta entrada) throws DerechohabientesBusinessException{
		DicRespuesta salida=null;
		if(entrada!=null){
			try {
				salida = new DicRespuesta();
				 salida.setDesRespuesta(entrada.getDescripcion()) ;
				 salida.setDitPregunta(PreguntaParser.modelToPersist(entrada.getPregunta()));
				 salida.setId(new DicRespuestaPK());
				 salida.getId().setCveIdPregunta(entrada.getPregunta().getIdPregunta());
				 salida.getId().setCveIdRespuesta(entrada.getIdRespuesta());
				 if(entrada.getValor()!=null)
				 salida.setNumValor(new BigDecimal(entrada.getValor()));
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_RESPUESTA, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_RESPUESTA+" | "+e.getMessage());
			}
			 
		}
		
		return salida;	
	}
	
	public static Respuesta persisToModel(DicRespuesta entrada) throws DerechohabientesBusinessException{
		Respuesta salida=null;
		if(entrada!=null){
			try {
				salida=new Respuesta();
				salida.setDescripcion(entrada.getDesRespuesta());
				salida.setIdRespuesta(entrada.getId().getCveIdRespuesta());
				salida.setPregunta(new Pregunta());
				salida.getPregunta().setIdPregunta(entrada.getId().getCveIdPregunta());
				salida.setValor(entrada.getNumValor().intValue());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_RESPUESTA+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
	public static Respuesta persisToModelSinPregunta(DicRespuesta entrada) throws DerechohabientesBusinessException{
		Respuesta salida=null;
		if(entrada!=null){
			try {
				salida=new Respuesta();
				salida.setDescripcion(entrada.getDesRespuesta());
				salida.setIdRespuesta(entrada.getId().getCveIdRespuesta());
				salida.setValor(Integer.valueOf(entrada.getNumValor().intValue()));
				salida.setPregunta(new Pregunta());
				salida.getPregunta().setIdPregunta(entrada.getId().getCveIdPregunta());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_RESPUESTA+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
	
	public static List<Respuesta> persisToModelList(List<DicRespuesta> entrada) throws DerechohabientesBusinessException{
		
		List<Respuesta> respuestas = new ArrayList<Respuesta>();
		if(entrada.size() > 0){
			for (DicRespuesta ditRespuesta : entrada) {
				respuestas.add(persisToModelSinPregunta(ditRespuesta));
			}
		}
		return respuestas;
	}
	
	
*/	
}
