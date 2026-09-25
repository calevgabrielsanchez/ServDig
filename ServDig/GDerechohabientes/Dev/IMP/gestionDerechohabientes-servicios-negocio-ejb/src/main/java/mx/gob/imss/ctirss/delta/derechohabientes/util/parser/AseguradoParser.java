package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.asegurado.Asegurado;
import mx.gob.imss.ctirss.delta.persistence.DitAsegurado;
import mx.gob.imss.ctirss.delta.persistence.DitAutorizacionPermte;

import org.apache.log4j.Logger;

public class AseguradoParser {
	private static final Logger logger = Logger.getLogger(AseguradoParser.class);
	
	public static DitAsegurado modelToPersist(Asegurado entrada) throws DerechohabientesBusinessException{
		DitAsegurado salida=null;
		if(entrada !=null){
			try {
				salida=new DitAsegurado();
				salida.setDitAsignacionNss(AsignacionNSSParser.modelToPersist(entrada.getAsignacionNSS()));			
				salida.setCveIdAsegurado(entrada.getIdAsegurado());
				salida.setDitPatronSujetoObligado(PatronSujetoObligadoParser.modelToPersist(entrada.getSujetoObligado()));
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_ASEGURADO, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_ASEGURADO+" | "+e.getMessage());
			}
						
		}
		
		return salida;	
	}
	
	public static Asegurado persisToModel(DitAsegurado entrada) throws DerechohabientesBusinessException{
		Date fecha = new Date();
		Asegurado salida=null;
		if(entrada!=null){
			try {
				salida=new Asegurado();			
				salida.setAsignacionNSS(AsignacionNSSParser.persisToModel(entrada.getDitAsignacionNss()));			
				salida.setIdAsegurado(entrada.getCveIdAsegurado());
				salida.setFechaBaja(entrada.getFecBaja());
				salida.setSujetoObligado(PatronSujetoObligadoParser.persisToModel(entrada.getDitPatronSujetoObligado()));						
				if(!entrada.getDitAutorizacionPermtes().isEmpty()){
					for(DitAutorizacionPermte autP :entrada.getDitAutorizacionPermtes()){
						if(autP.getFecRegistroBaja() == null){
							if(autP.getFecFinServ() != null){
								if(fecha.after(autP.getFecFinServ())){
									salida.setAutorizacionPermanente(false);
								}else{
									salida.setAutorizacionPermanente(true);
								}
							}else{
								salida.setAutorizacionPermanente(false);
							}
						}
					}			
				}else{
					salida.setAutorizacionPermanente(false);
				}
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS,e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_ASEGURADO+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
	
	public static List<Asegurado> persisToModelList(List<DitAsegurado> entradaList) throws DerechohabientesBusinessException{
		List<Asegurado> salidaList= new ArrayList<Asegurado>();
		if(entradaList.size() > 0){
			for(DitAsegurado salida:entradaList){
				salidaList.add(persisToModel(salida));
			}
		}		
		return salidaList;		
	}
	
}