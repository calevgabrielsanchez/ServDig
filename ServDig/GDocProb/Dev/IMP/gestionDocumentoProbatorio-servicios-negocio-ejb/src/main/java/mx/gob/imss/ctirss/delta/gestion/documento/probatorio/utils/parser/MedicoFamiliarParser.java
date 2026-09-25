package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoFamiliar;
import mx.gob.imss.ctirss.delta.persistence.DicMedico;

public class MedicoFamiliarParser {
	private static final Logger logger = Logger.getLogger(MedicoFamiliarParser.class);

	public static DicMedico modelToPersist(MedicoFamiliar entrada) throws DerechohabientesBusinessException{
		DicMedico salida=null;
		if(entrada !=null){
			try {
				salida=new DicMedico();
				salida.setCveIdMedico(entrada.getIdMedicoFamiliar());
				salida.setNumMedfamMatricula(entrada.getNoMatricula());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_MEDICO_FAMILIAR, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_MEDICO_FAMILIAR+" | "+e.getMessage());
			}
			
		}
		
		return salida;	
	}
	
	public static MedicoFamiliar persisToModel(DicMedico entrada) throws DerechohabientesBusinessException{
		MedicoFamiliar salida=null;
		if(entrada!=null){
			try {
				salida=new MedicoFamiliar();
				salida.setIdMedicoFamiliar(entrada.getCveIdMedico());
				salida.setNoMatricula(entrada.getNumMedfamMatricula());
				salida.setNombre(entrada.getNomNombre());
				salida.setPrimerApellido(entrada.getNomPrimerApellido());
				salida.setSegundoApellido(entrada.getNomSegundoApellido());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_MEDICO_FAMILIAR+" | "+e.getMessage());
			}
			
		}
		return salida;
	}

	public static List<MedicoFamiliar> persisToModelList(List<DicMedico> entradaList) throws DerechohabientesBusinessException {
		List<MedicoFamiliar> salidaList=new ArrayList<MedicoFamiliar>();
		if(entradaList.size() > 0){
			for(DicMedico entrada:entradaList ){
				salidaList.add(persisToModel(entrada));
			}
		}
		
		return salidaList;
	}
	
}