package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.NivelEducativo;
import mx.gob.imss.ctirss.delta.persistence.DicNivelEducativo;

import org.apache.log4j.Logger;


public class NivelEducativoParser {
	
	private static final Logger logger = Logger.getLogger(NivelEducativoParser.class);

	public static List<NivelEducativo> persisToModelList(List<DicNivelEducativo> entradaList) throws DerechohabientesBusinessException {
		List<NivelEducativo> salidaList=new ArrayList<NivelEducativo>();
		if(entradaList.size() > 0){
			for(DicNivelEducativo entrada:entradaList ){
				salidaList.add(persisToModel(entrada));
			}
		}
		
		return salidaList;
	}
	

	public static NivelEducativo persisToModel(DicNivelEducativo entrada) throws DerechohabientesBusinessException {
		NivelEducativo salida=null;
		if(entrada!=null){
			try {
				salida=new NivelEducativo();				
				salida.setDesNivelEducativo(entrada.getDesNivelEducativo());
				salida.setIdnivelEducativo(entrada.getCveIdNivelEducativo());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_NIVEL_EDUCATIVO_SERVICE+" | "+e.getMessage());
			}
			
		}
		return salida;
	}

}
