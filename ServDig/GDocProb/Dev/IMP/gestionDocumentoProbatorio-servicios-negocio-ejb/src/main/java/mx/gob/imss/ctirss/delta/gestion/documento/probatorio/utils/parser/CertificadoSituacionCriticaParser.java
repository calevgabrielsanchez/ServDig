package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser;

import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CertificadoSituacionCritica;

import mx.gob.imss.ctirss.delta.persistence.DitCertificadoSitCritica;
import mx.gob.imss.ctirss.delta.persistence.DitMedicoEspecialidad;

public class CertificadoSituacionCriticaParser {

	public static DitCertificadoSitCritica modelToPersist(
			CertificadoSituacionCritica entrada) {
		DitCertificadoSitCritica  salida= null;
		if(entrada!=null){
			salida=new DitCertificadoSitCritica();
			salida.setRefEnfermedadPadecida(entrada.getEnfermedadPadecida());
			salida.setFecTerminoIncapacidad(entrada.getFechaTerminoIncapacidad());
//			salida.setDicMedico(MedicoFamiliarDocumentosProbatoriosParser.modelToPersist(entrada.getMedicoFamiliar()));
			if(entrada.getMedicoFamiliar() != null) {
				salida.setDitMedicoEspecialidad(new DitMedicoEspecialidad());
				salida.getDitMedicoEspecialidad().setCveIdMedicoEspecialidad(entrada.getMedicoFamiliar().getIdMedicoEspecialidad());
			}
			salida.setFecProbInicio(entrada.getFechaProbableInicio());
		
		}
		return salida;
	}
	static public CertificadoSituacionCritica persistToModel(DitCertificadoSitCritica entrada){
		CertificadoSituacionCritica salida=null;
		if(entrada!=null){
			salida=new CertificadoSituacionCritica();
			salida.setIdDocumentoProbatorio(new Long(entrada.getCveIdDocumentoProbatorio()).intValue());
			salida.setEnfermedadPadecida(entrada.getRefEnfermedadPadecida());
			salida.setFechaTerminoIncapacidad(entrada.getFecTerminoIncapacidad());
//			salida.setMedicoFamiliar(MedicoFamiliarDocumentosProbatoriosParser.persisToModel(entrada.getDicMedico()));
			salida.setMedicoFamiliar(MedicoFamiliarDocumentosProbatoriosParser.persisToModel(entrada.getDitMedicoEspecialidad()));
			salida.setFechaProbableInicio(entrada.getFecProbInicio());
		}
		return salida;
	}

}
