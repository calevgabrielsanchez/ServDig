/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

/**
 * @author ghdolores
 *
 */
@Stateless(name="respuestaCuestionarioParser", mappedName = "respuestaCuestionarioParser")
public class RespuestaCuestionarioParser  implements RespuestaCuestionarioParserLocal{
	private static final Logger logger = Logger.getLogger(RespuestaCuestionarioParser.class);

	@Override
	public void dummy() {
		// TODO Auto-generated method stub
		
	}
	
	/*
	@EJB
	private transient PersonaInteresadaSolParserLocal personaInteresadaSolParser;


	public  DitRespuestaCuestionario modelToPersist(RespuestaCuestionario  entrada) throws DerechohabientesBusinessException{
		DitRespuestaCuestionario salida=null;
		if(entrada!=null){
			try {
				 salida = new DitRespuestaCuestionario();
				 salida.setDitPersonaInteresadaSol(personaInteresadaSolParser.modelToPersist(entrada.getPersonaInteresadaSol()));
				 //salida.setDitPregunta(PreguntaParser.modelToPersist(entrada.getPregunta()));
				 //salida.setId(new DitRespuestaCuestionarioPK());
				 //salida.getId().setCveIdCuestionarioRespuesta(entrada.getCveIdCuestionario());
				 salida.setDitPregunta(new DitPregunta());
				 salida.getDitPregunta().setCveIdPregunta(entrada.getPregunta().getIdPregunta());
				 salida.setCveIdPregunta(entrada.getPregunta().getIdPregunta());
				 
				 if(entrada.getValorRespuesta()!=null)
					 salida.setNumValorRespuesta(BigDecimal.valueOf(entrada.getValorRespuesta().longValue()));
				 
				 salida.setDitPersonaInteresadaSol(personaInteresadaSolParser.modelToPersist(entrada.getPersonaInteresadaSol()));
				 salida.setDitTramite(TramiteSimpleParser.modelToPersistSimple(entrada.getTramite()));
				  
				 if(entrada.getRespuestaPrimerpersona()!=null){
					 salida.setRespPrimeraPersona(RespuestaParser.modelToPersist(entrada.getRespuestaPrimerpersona()));
				 }
				 if(entrada.getRespuestaTerceraPersona()!=null)
					 salida.setRespTerceraPersona(RespuestaParser.modelToPersist(entrada.getRespuestaTerceraPersona()));
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_RESPUESTA_CUESTIONARIO, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_RESPUESTA_CUESTIONARIO+" | "+e.getMessage());
			}
		}	
		return salida;	
	}
	
	public  RespuestaCuestionario persisToModel(DitRespuestaCuestionario entrada) throws DerechohabientesBusinessException{
		RespuestaCuestionario salida=null;
		
		/*if(entrada!=null){
			try {
				salida=new RespuestaCuestionario();
				
				salida.setCveIdCuestionario(entrada.getCveIdCuestionarioRespuesta()); 
				salida.setPersona(FisicaParser.persisToModel(entrada.getDitPersonaInteresadaSol().getDitPersona()));
				salida.setPregunta(PreguntaParser.persisToModel(entrada.getDitPregunta()));
				salida.setPersonaInteresadaSol(personaInteresadaSolParser.persisToModel(entrada.getDitPersonaInteresadaSol()));
				if(entrada.getNumValorRespuesta()!=null)
				salida.setValorRespuesta(entrada.getNumValorRespuesta().intValue());
				
				salida.setTramite(TramiteSimpleParser.persistToModel(entrada.getDitTramite()));
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_RESPUESTA_CUESTIONARIO+" | "+e.getMessage());
			}

		}
		
		return salida;
	}
	
	
	public  List<DitRespuestaCuestionario> modelToPersistList(List<RespuestaCuestionario>  entrada) throws DerechohabientesBusinessException{
		
		List<DitRespuestaCuestionario> salida=null;
		if(entrada!=null && !entrada.isEmpty()){
			for (RespuestaCuestionario respuestaCuestionario : entrada) {
				salida = new ArrayList<DitRespuestaCuestionario>();
				salida.add(modelToPersist(respuestaCuestionario));
			}
		}
		return salida;	
	}
	
	public  List<RespuestaCuestionario> persisToModelList(List<DitRespuestaCuestionario> entrada) throws DerechohabientesBusinessException{
		
		List<RespuestaCuestionario> salida=null;
		if(entrada!=null && entrada.size() > 0){
			salida= new ArrayList<RespuestaCuestionario>(); 
			for (DitRespuestaCuestionario ditRespuestaCuestionario : entrada) {
				salida.add(persisToModel(ditRespuestaCuestionario));
			}
		}
		return salida;
	}
	*/
}
