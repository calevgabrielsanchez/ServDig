package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNss;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNssCL3;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;

public class AsignacionNSSParser {
	private static final Logger logger = Logger.getLogger(AsignacionNSSParser.class);
	
	public static DitAsignacionNss modelToPersist(AsignacionNSS entrada) throws DerechohabientesBusinessException{
		DitAsignacionNss salida=null;
		
		if(entrada != null){
			try {
				salida=new DitAsignacionNss();
				salida.setCveIdAsignacionNss(entrada.getIdAsignacionNSS());
				salida.setDitPersona(new DitPersona());
				salida.getDitPersona().setCveIdPersona(entrada.getIdPersona());
				salida.getDitPersona().setNomNombre(entrada.getNombre());
				salida.getDitPersona().setNomPrimerApellido(entrada.getPrimerApellido());
				salida.getDitPersona().setNomSegundoApellido(entrada.getSegundoApellido());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_ASIGNACION_NSS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_ASIGNACION_NSS+" | "+e.getMessage());
			}
			
		}
		
		return salida;	
	}
	
	public static AsignacionNSS persisToModel(DitAsignacionNss entrada) throws DerechohabientesBusinessException{
		AsignacionNSS salida=null;
		
		if(entrada !=null){
			try {
				salida=new AsignacionNSS();
				salida.setIdAsignacionNSS(entrada.getCveIdAsignacionNss());
				salida.setNss(entrada.getNumNss());
				salida.setNssStr(entrada.getNumNss());
				DitPersona persona = entrada.getDitPersona();
				if(!StringUtils.isBlank(persona.getNomNombre())) {
					salida.setNombre(persona.getNomNombre());//.toUpperCase().replace('#', '\u00D1'));
				}
				
				if(!StringUtils.isBlank(persona.getNomPrimerApellido())) {
					salida.setPrimerApellido(persona.getNomPrimerApellido());//.toUpperCase().replace('#', '\u00D1'));
				}
				
				if(!StringUtils.isBlank(persona.getNomSegundoApellido())) {
					salida.setSegundoApellido(persona.getNomSegundoApellido());//.toUpperCase().replace('#', '\u00D1'));
				}
				salida.setIdPersona(persona.getCveIdPersona());
				salida.setCurp(persona.getCurp());
				salida.setRfc(persona.getRfc());
				salida.setSexo(SexoParser.persisToModel(persona.getDicSexo()));			
				salida.setEstadoCivil(EstadoCivilParser.persisToModel(entrada.getDitPersona().getDicEstadoCivil()));
				salida.setLugarNacimiento(EntidadFederativaParser.persisToModel(entrada.getDitPersona().getDgCatEstado()));
				salida.setFechaNacimiento(entrada.getDitPersona().getFecNacimiento());
				salida.setMesRegistroNac(entrada.getDitPersona().getNumMesNacReg());
				salida.setAnioRegistroNac(entrada.getDitPersona().getNumAnioNacReg());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS,e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_ASIGNACION_NSS+" | "+e.getMessage());
			}
			
		}
		
		return salida;
	}
	
	public static AsignacionNSS persistToModelDatosBasicos(DitAsignacionNss entrada) throws DerechohabientesBusinessException{
		AsignacionNSS salida=null;
		
		if(entrada !=null){
			try {
				salida=new AsignacionNSS();
				DitPersona ditPersona = entrada.getDitPersona();
				
				salida.setIdAsignacionNSS(entrada.getCveIdAsignacionNss());
				salida.setIdPersona(ditPersona.getCveIdPersona());
				salida.setNombre(ditPersona.getNomNombre());
				salida.setPrimerApellido(ditPersona.getNomPrimerApellido());
				salida.setSegundoApellido(ditPersona.getNomSegundoApellido());
				salida.setCurp(ditPersona.getCurp());
				
				salida.setNss(entrada.getNumNss());
				salida.setNssStr(entrada.getNumNss());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS,e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_ASIGNACION_NSS+" | "+e.getMessage());
			}
			
		}
		
		return salida;
	}
	public static List<AsignacionNSS> persistToModelList(List<DitAsignacionNss> entradaList) throws DerechohabientesBusinessException{
		List<AsignacionNSS> salidaList= new ArrayList<AsignacionNSS>();
		try {
			for(DitAsignacionNss salida:entradaList){
				salidaList.add(persisToModel(salida));
			}
		} catch (Exception e) {
			logger.error(ExceptionMessages.ERROR_DATOS, e);
			throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_DATOS+" | "+e.getMessage());
		}
		
		return salidaList;
	}
	
	
	public static DitAsignacionNssCL3 modelToPersistCL3(AsignacionNSS entrada) throws DerechohabientesBusinessException{
		DitAsignacionNssCL3 salida=null;
		
		if(entrada != null){
			try {
				salida=new DitAsignacionNssCL3();
				salida.setCveIdAsignacionNss(entrada.getIdAsignacionNSS());
				salida.setDitPersona(new DitPersona());
				salida.getDitPersona().setCveIdPersona(entrada.getIdPersona());
				salida.getDitPersona().setNomNombre(entrada.getNombre());
				salida.getDitPersona().setNomPrimerApellido(entrada.getPrimerApellido());
				salida.getDitPersona().setNomSegundoApellido(entrada.getSegundoApellido());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_ASIGNACION_NSS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_ASIGNACION_NSS+" | "+e.getMessage());
			}
			
		}
		
		return salida;	
	}
	
	public static AsignacionNSS persisCL3ToModel(DitAsignacionNssCL3 entrada) throws DerechohabientesBusinessException{
		AsignacionNSS salida=null;
		
		if(entrada !=null){
			try {
				salida=new AsignacionNSS();
				salida.setIdAsignacionNSS(entrada.getCveIdAsignacionNss());
				salida.setNss(entrada.getNumNss());
				salida.setNssStr(entrada.getNumNss());
				DitPersona persona = entrada.getDitPersona();
				if(!StringUtils.isBlank(persona.getNomNombre())) {
					salida.setNombre(persona.getNomNombre());//.toUpperCase().replace('#', '\u00D1'));
				}
				
				if(!StringUtils.isBlank(persona.getNomPrimerApellido())) {
					salida.setPrimerApellido(persona.getNomPrimerApellido());//.toUpperCase().replace('#', '\u00D1'));
				}
				
				if(!StringUtils.isBlank(persona.getNomSegundoApellido())) {
					salida.setSegundoApellido(persona.getNomSegundoApellido());//.toUpperCase().replace('#', '\u00D1'));
				}
				salida.setIdPersona(persona.getCveIdPersona());
				salida.setCurp(persona.getCurp());
				salida.setRfc(persona.getRfc());
				salida.setSexo(SexoParser.persisToModel(persona.getDicSexo()));			
				salida.setEstadoCivil(EstadoCivilParser.persisToModel(entrada.getDitPersona().getDicEstadoCivil()));
				salida.setLugarNacimiento(EntidadFederativaParser.persisToModel(entrada.getDitPersona().getDgCatEstado()));
				salida.setFechaNacimiento(entrada.getDitPersona().getFecNacimiento());
				salida.setMesRegistroNac(entrada.getDitPersona().getNumMesNacReg());
				salida.setAnioRegistroNac(entrada.getDitPersona().getNumAnioNacReg());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS,e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_ASIGNACION_NSS+" | "+e.getMessage());
			}
			
		}
		
		return salida;
	}
	
	
}