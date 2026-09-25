package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser;

import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoFamiliar;
import mx.gob.imss.ctirss.delta.persistence.DicMedico;
import mx.gob.imss.ctirss.delta.persistence.DitMedicoEspecialidad;

public class MedicoFamiliarDocumentosProbatoriosParser {
	public static DicMedico modelToPersist(MedicoFamiliar entrada){
		DicMedico salida=null;
		if(entrada !=null){
			salida=new DicMedico();
			salida.setCveIdMedico(entrada.getIdMedicoFamiliar());
		}
		
		return salida;	
	}
	
	public static MedicoFamiliar persisToModel(DitMedicoEspecialidad entrada){
		MedicoFamiliar salida=null;
		DicMedico medico = entrada.getDicMedico();
		
		if(entrada!=null){
			salida=new MedicoFamiliar();
			salida.setIdMedicoEspecialidad(entrada.getCveIdMedicoEspecialidad());
			salida.setIdMedicoFamiliar(medico.getCveIdMedico());
			salida.setNoMatricula(medico.getNumMedfamMatricula());
			salida.setNombre(medico.getNomNombre());
			salida.setPrimerApellido(medico.getNomPrimerApellido());
			salida.setSegundoApellido(medico.getNomSegundoApellido());
		}
		return salida;
	}
	
}