package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DetalleNivelEducativo;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleNivelEducativo;

import org.apache.log4j.Logger;

public class DetalleNivelEducativoParser {
	
	private static final Logger logger = Logger.getLogger(DetalleNivelEducativoParser.class);

	

	public static List<DetalleNivelEducativo> persistToModelList(List<DitDetalleNivelEducativo> entradaList) throws DerechohabientesBusinessException {
		List<DetalleNivelEducativo> salidaList=new ArrayList<DetalleNivelEducativo>();
		if(entradaList.size() > 0){
			for(DitDetalleNivelEducativo entrada:entradaList ){
				salidaList.add(persisToModel(entrada));
			}
		}
		
		return salidaList;
		
	}

	public static DetalleNivelEducativo persisToModel(DitDetalleNivelEducativo entrada) throws DerechohabientesBusinessException {
		DetalleNivelEducativo salida=null;
		if(entrada!=null){
			try {
				salida=new DetalleNivelEducativo();
				salida.setIdDetalleNivelEducativo(entrada.getCveIdDetalleNivelEducativo());
				salida.setNivelEducativo(NivelEducativoParser.persisToModel(entrada.getDicNivelEducativo()));
				salida.setTipoNivelEducativo(TipoNivelEducativoParser.persisToModel(entrada.getDicTipoNivelEducativo()));	
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_DETALLE_NIVEL_EDUCATIVO+" | "+e.getMessage());
			}
			
		}
		return salida;
	}

}
