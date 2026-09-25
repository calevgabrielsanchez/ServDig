package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UmfCodigoPostal;
import mx.gob.imss.ctirss.delta.persistence.DitUmfCodPo;

public class UmfCodigoPostalParser {
	
	private static final Logger logger = Logger.getLogger(UmfCodigoPostalParser.class);

	
	public static UmfCodigoPostal persisitToModelCambioMasivo(DitUmfCodPo entrada) throws DerechohabientesBusinessException{
		UmfCodigoPostal salida=null;
		if(entrada!=null){
			try {
				salida=new UmfCodigoPostal();
				salida.setCodigoPostal(CodigoPostalParser.persistToModel(entrada.getDgCodigosPostale()));
				salida.setUnidadMedicaFamiliar(UnidadMedicaFamiliarParser.persisToModel(entrada.getDicUmf()));
				salida.setIdUmfCodigoPostal(entrada.getCveIdUmfCodPos());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_UMF_CODIGO_POSTAL+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
	public static List<UmfCodigoPostal> persistToModelCambioMasivo(
		List<DitUmfCodPo> entradaList) throws DerechohabientesBusinessException {
		List<UmfCodigoPostal> salidaList=new ArrayList<UmfCodigoPostal>();
		
		if(entradaList.size() > 0){			
			for(DitUmfCodPo entrada :entradaList){
				salidaList.add(persisitToModelCambioMasivo(entrada));
			}
		}
		
		
		return salidaList;
	}

}
