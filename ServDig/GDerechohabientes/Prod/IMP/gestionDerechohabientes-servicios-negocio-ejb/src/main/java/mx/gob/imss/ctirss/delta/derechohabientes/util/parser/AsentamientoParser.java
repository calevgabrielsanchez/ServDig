package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;



import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.persistence.DgAsentamiento;
import mx.gob.imss.ctirss.delta.persistence.DgAsentamientoPK;
import mx.gob.imss.ctirss.delta.persistence.DgCatLocalidad;
import mx.gob.imss.ctirss.delta.persistence.DgCatLocalidadPK;



public class AsentamientoParser {
	private static final Logger logger = Logger.getLogger(AsentamientoParser.class);
	
	public static DgAsentamiento modelToPersist(Asentamiento entrada) throws DerechohabientesBusinessException{
		DgAsentamiento salida=null;
		if(entrada !=null){
			salida=new DgAsentamiento();
			try {
				//obtener asentamiento
				DgAsentamientoPK asentamientoPK=new DgAsentamientoPK();
				asentamientoPK.setCveAsen(getCveAsen(entrada));
				asentamientoPK.setCveEnt(getCveEnt(entrada));
				asentamientoPK.setCveMun(getCveMun(entrada));
				//asentamientoPK.setCveLoc(getCveLoc(entrada));
				//asentamientoPK.setCvePeriodo(entrada.getPeriodo());
				/*
				salida.setDgCatLocalidad(new DgCatLocalidad());
				salida.getDgCatLocalidad().setId(new DgCatLocalidadPK());
				salida.getDgCatLocalidad().getId().setCveLoc(getCveLoc(entrada));
				salida.getDgCatLocalidad().getId().setCveEnt(getCveEnt(entrada));
				salida.getDgCatLocalidad().getId().setCveMun(getCveMun(entrada));
				salida.getDgCatLocalidad().getId().setCvePeriodo(entrada.getPeriodo());*/
				salida.setId(asentamientoPK);
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_ASENTAMIENTO, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_ASENTAMIENTO+" | "+e.getMessage());
			}
			
		
		}
		
		return salida;	
	}
	
	public static Asentamiento persisToModel(DgAsentamiento entrada) throws DerechohabientesBusinessException{
		Asentamiento salida=null;
		if(entrada!=null){
			try {
				salida=new Asentamiento();
				salida.setClave(entrada.getId().getCveAsen());
				salida.setNombre(entrada.getNomAsen());
				Localidad localidad = new Localidad();
				/*localidad.setClave(entrada.getDgCatLocalidad().getId().getCveLoc());
				localidad.setNombre(entrada.getDgCatLocalidad().getNomLoc());*/
				Municipio municipio = new Municipio();
				municipio.setClave(entrada.getDgCatMunicipio().getId().getCveMun());
				municipio.setNombre(entrada.getDgCatMunicipio().getNomMun());
				EntidadFederativa entidadFederativa = new EntidadFederativa();
				entidadFederativa = EntidadFederativaParser.persisToModel(entrada.getDgCatMunicipio().getDgCatEstado());
				municipio.setEntidadFederativa(entidadFederativa);
				localidad.setMunicipio(municipio);
				salida.setLocalidad(localidad);				
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_ASENTAMIENTO+" | "+e.getMessage());
			}
			
			
		}
		return salida;
	}
	
	private static String getCveAsen(Asentamiento entrada){
		String resp;
		try{
			resp=entrada.getClave().toString();
		}catch(NullPointerException e){
			resp=null;
		}
		return resp;
	}
	private static String getCveEnt(Asentamiento entrada){
		String resp;
		try{
			resp=entrada.getLocalidad().getMunicipio().getEntidadFederativa().getClave().toString();
		}catch(NullPointerException e){
			resp=null;
		}
		return resp;
	}
	private static String getCveLoc(Asentamiento entrada){
		String resp;
		try{
			resp=entrada.getLocalidad().getClave().toString();
		}catch(NullPointerException e){
			resp=null;
		}
		return resp;
	}
	private static String getCveMun(Asentamiento entrada){
		String resp;
		try{
			resp=entrada.getLocalidad().getMunicipio().getClave().toString();
		}catch(NullPointerException e){
			resp=null;
		}
		return resp;
	}
	
	
}
