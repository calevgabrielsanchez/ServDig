package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicUmf;
import mx.gob.imss.ctirss.delta.persistence.DitUmfCodPo;


public class UnidadMedicaFamiliarParser {
	
	private static final Logger logger = Logger.getLogger(UnidadMedicaFamiliarParser.class);

	public static DicUmf modelToPersist(UnidadMedicaFamiliar entrada) throws DerechohabientesBusinessException{
		DicUmf salida=null;
		if(entrada !=null){
			try {
				salida=new DicUmf();
				salida.setCveIdUmf(entrada.getIdUMF());
				salida.setNomUnidad(entrada.getDescripcion());
				salida.setNomCorto(entrada.getNombreCorto());
				salida.setIndGeneracionCita(entrada.isGeneracionCita());
				salida.setNumConsultorio(entrada.getNoConsultorio());
				salida.setNumEconom(entrada.getNoEconomico());
				salida.setAccNivelAtencion(NivelAtencionParser.modelToPersist(entrada.getNivelAtencion()));
				salida.setDicClavePresupuestal(ClavePresupuestalParser.modelToPersist(entrada.getClavePresupuestal()));
				salida.setIndUmfCfe(entrada.getIndUmfCfe());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_UNIDAD_MEDICA_FAMILIAR, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_UNIDAD_MEDICA_FAMILIAR+" | "+e.getMessage());
			}
			
		}
		
		return salida;	
	}
	
	public static UnidadMedicaFamiliar persisToModel(DicUmf entrada) throws DerechohabientesBusinessException{
		UnidadMedicaFamiliar salida=null;
		if(entrada!=null){
			try {
				String direccionUmf = "";
				salida=new UnidadMedicaFamiliar();
				salida.setIdUMF(entrada.getCveIdUmf());
				salida.setDescripcion(entrada.getNomUnidad());
				salida.setNombreCorto(entrada.getNomCorto());
				salida.setGeneracionCita(entrada.getIndGeneracionCita());
				salida.setNoConsultorio(entrada.getNumConsultorio());
				salida.setNoEconomico(entrada.getNumEconom());
				Subdelegacion subdelegacion = SubdelegacionIMSSParser.persisToModel(entrada.getDicSubdelegacion());
				salida.setSubdelegacion(subdelegacion);
				salida.setClavePresupuestal(ClavePresupuestalParser.persisToModel(entrada.getDicClavePresupuestal()));
				salida.setNivelAtencion(NivelAtencionParser.persisToModel(entrada.getAccNivelAtencion()));
				salida.setTipoUMF(TipoUMFParser.persistToModel(entrada.getDicTipoUmf()));
				salida.setDomicilio(DomicilioParser.persisToModelCompleto(entrada.getDgDomicilioGeografico()));
				
				salida.setIndUmfCfe(entrada.getIndUmfCfe());
				if(entrada.getDgDomicilioGeografico() != null) {
					salida.setDesDireccion(DomicilioParser.persisToModelDesDireccion(entrada.getDgDomicilioGeografico()));
				} else {
					direccionUmf = StringUtils.isNotBlank(entrada.getDomCalle()) ? entrada.getDomCalle() : "";
				}
				
				salida.setDesDireccion(StringUtils.isBlank(direccionUmf)? "Direccion no disponible" : direccionUmf);
				salida.setLatitud(entrada.getLatitud());
				salida.setLongitud(entrada.getLongitud());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_UNIDAD_MEDICA_FAMILIAR+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
	
	public static List<UnidadMedicaFamiliar> persisToModelList(List<DicUmf> entrada) throws DerechohabientesBusinessException{
		
		List<UnidadMedicaFamiliar> salida=null;
		UnidadMedicaFamiliar umf = null;
		if(entrada!=null && entrada.size()>0){
			salida = new ArrayList<UnidadMedicaFamiliar>();
			for (DicUmf dicUmf : entrada) {
				umf = persisToModel(dicUmf);
				salida.add(umf);
			}
			
		}
		return salida;
	}

	public static UnidadMedicaFamiliar persisToModel(DitUmfCodPo entrada) throws DerechohabientesBusinessException {
		UnidadMedicaFamiliar salida=null;
		if(entrada!=null && entrada.getDicUmf()!=null){			
			salida = persisToModel(entrada.getDicUmf());			
		}
		return salida;
	}
	
	
	
}