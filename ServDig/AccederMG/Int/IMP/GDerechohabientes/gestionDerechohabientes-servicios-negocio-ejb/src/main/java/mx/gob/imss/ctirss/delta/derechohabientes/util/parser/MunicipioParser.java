package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.persistence.DgCatMunicipio;

public class MunicipioParser {
	
	private static final Logger logger = Logger.getLogger(MunicipioParser.class);

	public static Municipio persistToModel(DgCatMunicipio entrada) throws DerechohabientesBusinessException {
		Municipio salida = null;
		
		if(entrada != null) {
			try {
				salida = new Municipio();
				salida.setClave(entrada.getId().getCveMun());
				salida.setNombre(entrada.getNomMun());
				salida.setEntidadFederativa(EntidadFederativaParser.persisToModel(entrada.getDgCatEstado()));
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_MUNICIPIO+" | "+e.getMessage());
			}
			
		}
		
		return salida;
	}
	
	public static List<Municipio> persistToModelList(List<DgCatMunicipio> entrada) throws DerechohabientesBusinessException {
		List<Municipio> salida = null;
		
		if(entrada != null && entrada.size() > 0 ) {
			try {
				salida = new ArrayList<Municipio>();
				
				for(DgCatMunicipio municipio: entrada) {
					salida.add(persistToModel(municipio));
				}
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_MUNICIPIO, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_MUNICIPIO+" | "+e.getMessage());
			}			
		}		
		return salida;
	}
}
