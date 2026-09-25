package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser;

import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Obstetrico;
import mx.gob.imss.ctirss.delta.persistence.DitMedicoEspecialidad;
import mx.gob.imss.ctirss.delta.persistence.DitObstetrico;

public class ObstetricoParser {

	public static DitObstetrico modelToPersist(Obstetrico entrada) {
		DitObstetrico salida=null;
		if(entrada!=null){
			salida=new DitObstetrico();
			salida.setFecCertificacionMedica(entrada.getFechaCertificacionMedico());
			salida.setFecParto(entrada.getFechaParto());
//			salida.setDicMedico(new DicMedico());
//			salida.getDicMedico().setCveIdMedico(entrada.getMedicoFamiliar().getIdMedicoFamiliar());
			if(entrada.getMedicoFamiliar() != null) {
				salida.setDitMedicoEspecialidad(new DitMedicoEspecialidad());
				salida.getDitMedicoEspecialidad().setCveIdMedicoEspecialidad(entrada.getMedicoFamiliar().getIdMedicoEspecialidad());
			}
			salida.setFecProbConcepcion(entrada.getFechaProbableConcepcion());
		}
		return salida;
	}
	
	public static Obstetrico persisToModel(DitObstetrico entrada){
		Obstetrico salida=null;
		if(entrada!=null){
			salida=new Obstetrico();
			salida.setFechaCertificacionMedico(entrada.getFecCertificacionMedica());
			salida.setFechaParto(entrada.getFecParto());
//			salida.setMedicoFamiliar(MedicoFamiliarDocumentosProbatoriosParser.persisToModel(entrada.getDicMedico()));
			salida.setMedicoFamiliar(MedicoFamiliarDocumentosProbatoriosParser.persisToModel(entrada.getDitMedicoEspecialidad()));
			salida.setFechaProbableConcepcion(entrada.getFecProbConcepcion());
		}
		return salida;
	}
}
