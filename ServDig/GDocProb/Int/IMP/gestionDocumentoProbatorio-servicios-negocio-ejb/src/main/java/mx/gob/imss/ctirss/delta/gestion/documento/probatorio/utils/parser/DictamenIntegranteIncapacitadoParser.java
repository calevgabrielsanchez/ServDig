package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DictamenIntegranteIncapacitado;
import mx.gob.imss.ctirss.delta.persistence.DicDelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicUmf;
import mx.gob.imss.ctirss.delta.persistence.DitDictBeneficiarioInca;
import mx.gob.imss.ctirss.delta.persistence.DitMedicoEspecialidad;

import org.apache.log4j.Logger;

public class DictamenIntegranteIncapacitadoParser {
	
	private static final Logger logger = Logger.getLogger(DictamenIntegranteIncapacitadoParser.class);

	public static DitDictBeneficiarioInca modelToPersist(
			DictamenIntegranteIncapacitado entrada) throws DocumentoProbatorioException {
		
		DitDictBeneficiarioInca salida=null;
		if(entrada!=null){
			try {
				salida=new DitDictBeneficiarioInca();
				salida.setRefDiagnosticoPadecimiento(entrada.getDiagnosticoPadecimiento());
				salida.setFecInicioEnfermedad(entrada.getFechaInicioEnfermedad());
				
				if(entrada.getMedicoFamiliar() != null) {
					salida.setDitMedicoEspecialidad(new DitMedicoEspecialidad());
					salida.getDitMedicoEspecialidad().setCveIdMedicoEspecialidad(entrada.getMedicoFamiliar().getIdMedicoEspecialidad());
				}
				
				salida.setDicUmf(new DicUmf());
				salida.getDicUmf().setCveIdUmf(entrada.getUnidadMedicaFamiliar().getIdUMF());
				salida.setIndIncapacidadVigente(new Integer(entrada.getExisteEstadoIncapacidad()));
				salida.setNumGradoIncaoacidad(new Integer(entrada.getGradoIncapacidad()));
				if(entrada.getDelegacion()!=null){
					salida.setDicDelegacion(new DicDelegacion());
					salida.getDicDelegacion().setCveIdDelegacion(entrada.getDelegacion().getId());
				}
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_DICTAMEN_INTEGRANTE_INCAPACITADO, e);
				throw new DocumentoProbatorioException("Error al relizar el parser");
			}
			
			
		}
		return salida;
	}
	
	public static DictamenIntegranteIncapacitado persisToModel(DitDictBeneficiarioInca entrada) throws DocumentoProbatorioException{
		DictamenIntegranteIncapacitado salida=null;
		if(entrada!=null){
			try {
				salida=new DictamenIntegranteIncapacitado();
				salida.setDiagnosticoPadecimiento(entrada.getRefDiagnosticoPadecimiento());
				salida.setFechaInicioEnfermedad(entrada.getFecInicioEnfermedad());
				salida.setMedicoFamiliar(MedicoFamiliarDocumentosProbatoriosParser.persisToModel(entrada.getDitMedicoEspecialidad()));
				if(entrada.getDicUmf()!=null){
					salida.setUnidadMedicaFamiliar(new UnidadMedicaFamiliar());
					salida.getUnidadMedicaFamiliar().setNombreCorto(entrada.getDicUmf().getNomCorto());
				}
				salida.setExisteEstadoIncapacidad(entrada.getIndIncapacidadVigente().toString());
				salida.setGradoIncapacidad(entrada.getNumGradoIncaoacidad().toString());
				if(entrada.getDicDelegacion()!=null){
					salida.setDelegacion(new Delegacion());
					salida.getDelegacion().setId(entrada.getDicDelegacion().getCveIdDelegacion());
					salida.getDelegacion().setClave(entrada.getDicDelegacion().getClaveDelegacion());
					salida.getDelegacion().setDescripcion(entrada.getDicDelegacion().getDesDeleg());
				}
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DocumentoProbatorioException("Error al realizar el parser");
			}
			
			
		}
		return salida;
	}

}
