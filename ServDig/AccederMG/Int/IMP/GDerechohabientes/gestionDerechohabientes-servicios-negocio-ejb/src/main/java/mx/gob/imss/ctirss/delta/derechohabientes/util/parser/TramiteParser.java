package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleTramite;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaFisicaPK;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

import org.apache.log4j.Logger;

public class TramiteParser {
	
	private static final Logger logger = Logger.getLogger(TramiteParser.class);


	public static DitTramitePersonaFisica modelToPersistPartial(Tramite entrada) throws DerechohabientesBusinessException {
		DitTramitePersonaFisica salida = null;
		
		if(entrada != null) {
			try {
				salida= new DitTramitePersonaFisica();
				
				salida.setId(new DitTramitePersonaFisicaPK());
				
				salida.getId().setCveIdPersona(entrada.getPersona().getIdPersona());
				salida.getId().setCveIdTramite(entrada.getTramiteId());
				
				salida.setDitPersona(new DitPersona());
				salida.getDitPersona().setCveIdPersona(entrada.getPersona().getIdPersona());
				
				salida.setDitTramite(new DitTramite());
				salida.getDitTramite().setCveIdTramite(entrada.getTramiteId());
				
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_TRAMITE, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_TRAMITE+" | "+e.getMessage());
			}
			
		}
		
		return salida;
	}
	
	public static DitDetalleTramite modelToPersistDetalle(Tramite entrada) throws DerechohabientesBusinessException {
		DitDetalleTramite salida = null;
		
		if(entrada != null) {
			try {
				salida= new DitDetalleTramite();				
				salida.setCveIdTramite(entrada.getTramiteId());
				salida.setRefDatosTramiteXml(entrada.getDetalleTramiteXml());
				salida.setFecRegistroAlta(entrada.getFechaPresentacion());
				salida.setFecRegistroBaja(entrada.getFechaConclusion());
				salida.setFecRegistroActualizado(entrada.getFechaRegistroActualizacion());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_TRAMITE, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_TRAMITE+" | "+e.getMessage());
			}			
		}		
		return salida;
	}
	public static DitTramitePersonaFisica modelToPersist(Tramite entrada) throws DerechohabientesBusinessException{
		DitTramitePersonaFisica salida=null;
		if(entrada!=null){
			try {
				salida= new DitTramitePersonaFisica();
				
				salida.setId(new DitTramitePersonaFisicaPK());
				
				salida.getId().setCveIdPersona(entrada.getPersona().getIdPersona());
				salida.getId().setCveIdTramite(entrada.getTramiteId());
				
				salida.setDitPersona(new DitPersona());
				salida.getDitPersona().setCveIdPersona(entrada.getPersona().getIdPersona());
				
				salida.setDitTramite(TramiteSimpleParser.modelToPersist(entrada));	
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_TRAMITE, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_TRAMITE+" | "+e.getMessage());
			}
										
		}

		return salida;
	}
	
	public static Tramite persistTomodelSimple(DitTramite entrada) throws DerechohabientesBusinessException{
		Tramite salida=null;
		if(entrada!=null){
			try {
				salida= new  Tramite();
				salida = TramiteSimpleParser.persistToModel(entrada);
				
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_TRAMITE+" | "+e.getMessage());
			}
			
			
		}

		return salida;
	}
	
	public static Tramite persistTomodel(DitTramitePersonaFisica entrada) throws DerechohabientesBusinessException{
		Tramite salida=null;
		if(entrada!=null){
			try {
				salida= new  Tramite();
				salida = TramiteSimpleParser.persistToModel(entrada.getDitTramite());
				if(entrada.getDitPersona() != null){
					Fisica persona = FisicaParser
							.persisToModel(entrada.getDitPersona());
					salida.setPersona(persona);
				}
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_TRAMITE+" | "+e.getMessage());
			}
			
			
		}

		return salida;
	}
	
	public static Tramite persistTomodelCompleto(DitTramitePersonaFisica entrada) throws DerechohabientesBusinessException{
		Tramite salida=null;
		if(entrada!=null){
			try {
				salida= new  Tramite();
				
				salida = TramiteSimpleParser.persistToModel(entrada.getDitTramite());
				
				Fisica persona = FisicaParser.persisToModel(entrada.getDitPersona());			
				salida.setPersona(persona);
			
				//salida.setSolicitud(SolicitudParser.persisToModelPartial(entrada.getDitTramite().getDitSolicitud()));
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_TRAMITE+" | "+e.getMessage());
			}
			
		}
		
		return salida;
	}
	
	public static Tramite persistTomodelCompleto(DitTramite entrada) throws DerechohabientesBusinessException{
		Tramite salida=null;
		if(entrada!=null){
			try {
				salida= new  Tramite();
				
				salida = TramiteSimpleParser.persistToModel(entrada);
				//salida.setSolicitud(SolicitudParser.persisToModelPartial(entrada.getDitSolicitud()));
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_TRAMITE+" | "+e.getMessage());
			}
			
		}
		
		return salida;
	}
	
	public static List<Tramite> persistTomodelList(List<DitTramitePersonaFisica> entrada) throws DerechohabientesBusinessException{

		List<Tramite> salida = new ArrayList<Tramite>();
		if (!entrada.isEmpty()) {
			for (DitTramitePersonaFisica tramitePersona : entrada)
				salida.add(TramiteParser.persistTomodel(tramitePersona));
		}

		return salida;
	}
	public static List<Tramite> persisToModelListPendAut(List<DitTramite> entrada) throws DerechohabientesBusinessException{
		List<Tramite> salida = new ArrayList<Tramite>();
		if (!entrada.isEmpty()) {
			for (DitTramite tramitePersona : entrada)
				salida.add(TramiteParser.persistTomodelPendAut(tramitePersona));
		}

		return salida;
	}
	
	public static Tramite persistTomodelPendAut(
			DitTramite entrada) throws DerechohabientesBusinessException {
		Tramite salida= null;
		
		if (entrada!=null){
			try {
				salida=new Tramite();
				salida.setTramiteId(entrada.getCveIdTramite());
				if(entrada.getDicTipoTramite()!=null){
					salida.setTipoTramite(TipoTramiteParser.persisToModel(entrada.getDicTipoTramite()));
					salida.setEstadoTramite(EstadoTramiteParser.persisToModel(entrada.getDicEstadoTramite()));
					if(entrada.getDitTramitePersonaFisica() != null){
						if(entrada.getDitTramitePersonaFisica().size() > 0)
							salida.setPersona(FisicaParser.persisToModel(entrada.getDitTramitePersonaFisica().get(0).getDitPersona()));
					}
				}
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_TRAMITE, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_TRAMITE+" | "+e.getMessage());
			}
		}
		return salida;
	}

}
