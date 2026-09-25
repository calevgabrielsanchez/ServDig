package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;



import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;
import mx.gob.imss.ctirss.delta.persistence.DgDomicilioGeografico;

public class DomicilioParser {
	private static final Logger logger = Logger.getLogger(DomicilioParser.class);
	
	public static DgDomicilioGeografico modelToPersist(Domicilio entrada) throws DerechohabientesBusinessException{
		DgDomicilioGeografico salida=null;
		salida=new DgDomicilioGeografico();
		
		if(entrada !=null){
			try {				
				salida.setDgAsentamiento(AsentamientoParser.modelToPersist(entrada.getAsentamiento()));
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_DOMICILIO, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_DOMICILIO+" | "+e.getMessage());
			}			
		}
		return salida;
	}
	
	public static Domicilio persisToModel(DgDomicilioGeografico entrada) throws DerechohabientesBusinessException{
		Domicilio salida=null;
		if(entrada!=null){
			try {
				salida= new Domicilio();
				salida.setAsentamiento(AsentamientoParser.persisToModel(entrada.getDgAsentamiento()));
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_DOMICILIO+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
	public static Domicilio persisToModelCompleto(DgDomicilioGeografico entrada) throws DerechohabientesBusinessException{
		Domicilio salida=null;
		if(entrada!=null){
			try {
				salida= new Domicilio();
				salida.setAsentamiento(AsentamientoParser.persisToModel(entrada.getDgAsentamiento()));
				salida.setCalle(entrada.getDescripc());
				salida.setClave(entrada.getDomicilioId().intValue());
				salida.setCodigoPostal(CodigoPostalParser.persistToModel(entrada.getDgCodigosPostale()));
				salida.setColonia(entrada.getDgAsentamiento().getNomAsen());
				salida.setNumExterior1(entrada.getNumextnum());
				salida.setNumInterior(entrada.getNumintnum());
				if(entrada.getDgCatTipoDom()!=null){
					salida.setTipoDomicilio(new TipoDomicilio());
					salida.getTipoDomicilio().setClave(entrada.getDgCatTipoDom().getCveTipoDom());
					salida.getTipoDomicilio().setDescripcion(entrada.getDgCatTipoDom().getDescripcion());
				}
				if(entrada.getDgVialidadByCveViaPrin()!=null){
					salida.setVialidadPrimaria(new Vialidad());
					salida.getVialidadPrimaria().setClave(entrada.getDgVialidadByCveViaPrin().getCveVia());
					salida.getVialidadPrimaria().setNombre(entrada.getDgVialidadByCveViaPrin().getNomVia());
				}
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_DOMICILIO+" | "+e.getMessage());
			}
		
			
		}
		return salida;
	}

	public static String persisToModelDesDireccion(
			DgDomicilioGeografico entrada) throws DerechohabientesBusinessException {
		StringBuffer desDireccion=new StringBuffer("");
		
		try {
			String calle="";
			String numero="";
			String colAsent="";
			String  cp="";
			String delegacion="";
			String entidadFederativa="";
			String sl=" ";
			
			if(entrada!=null){
				try{
					calle=entrada.getDgVialidadByCveViaPrin().getNomVia();
				}catch(Exception e){
					
				}
				if(entrada.getNumextnum()!=null){
					numero=entrada.getNumextnum().toString();
				}
				if(entrada.getDgAsentamiento()!=null){
					colAsent=entrada.getDgAsentamiento().getNomAsen();
					try{
						
						delegacion=entrada.getDgAsentamiento().getDgCatMunicipio().getNomMun();
						if(entrada.getDgAsentamiento().getDgCatMunicipio().getDgCatEstado()!=null){
							entidadFederativa=entrada.getDgAsentamiento().getDgCatMunicipio().getDgCatEstado().getNomEnt();
						}
					
					}catch(Exception e){
						
					}
				}
				if(entrada.getDgCodigosPostale()!=null){
					cp=entrada.getDgCodigosPostale().getId().getCodigo();
				}
				
			}
			desDireccion.append("Calle:");
			desDireccion.append(calle);
			desDireccion.append(" Num.");
			desDireccion.append(numero);
			desDireccion.append(sl);
			desDireccion.append("Colonia/Asentamiento:");
			desDireccion.append(colAsent);
			desDireccion.append(sl);
			desDireccion.append("CP:");
			desDireccion.append(cp);
			desDireccion.append(sl);
			desDireccion.append("Delegacion:");
			desDireccion.append(delegacion);
			desDireccion.append(sl);
			desDireccion.append("EntidadFederativa:");
			desDireccion.append(entidadFederativa);
		} catch (Exception e) {
			logger.error(ExceptionMessages.ERROR_DATOS, e);
			throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_DOMICILIO+" | "+e.getMessage());
		}
		
		
		return desDireccion.toString();
	}
	
	
}
