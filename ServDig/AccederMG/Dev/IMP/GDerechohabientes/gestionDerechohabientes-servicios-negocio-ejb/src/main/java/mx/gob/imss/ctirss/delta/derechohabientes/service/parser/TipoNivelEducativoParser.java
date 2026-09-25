package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoNivelEducativo;
import mx.gob.imss.ctirss.delta.persistence.DicTipoNivelEducativo;

import org.apache.log4j.Logger;


public class TipoNivelEducativoParser {
	
	private static final Logger logger = Logger.getLogger(TipoNivelEducativoParser.class);

	public static List<TipoNivelEducativo> persisToModelList(List<DicTipoNivelEducativo> entradaList) throws DerechohabientesBusinessException {
		List<TipoNivelEducativo> salidaList=new ArrayList<TipoNivelEducativo>();
		for(DicTipoNivelEducativo entrada:entradaList ){
			salidaList.add(persisToModel(entrada));
		}
		return salidaList;
	}
	

	public static TipoNivelEducativo persisToModel(DicTipoNivelEducativo entrada) throws DerechohabientesBusinessException {
		TipoNivelEducativo salida=null;
		if(entrada!=null){
			try {
				salida=new TipoNivelEducativo();
				
				salida.setDesNivelEducativo(entrada.getDesTipoNivelEducativo());
				salida.setIdNivelEducativo(entrada.getCveIdTipoNivelEducativo());
				salida.setNivelEducativos(NivelEducativoParser.persisToModelList(entrada.getDicNivelEducativo()));
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_TIPO_NIVEL_EDUCATIVO_SERVICE+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
}
