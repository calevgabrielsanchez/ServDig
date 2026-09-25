package mx.gob.imss.ctirss.delta.derechohabientes.util.parser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicRazonResultado;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaFisica;

import org.apache.log4j.Logger;

public class TramiteSimpleParser {
	
	private static final Logger logger = Logger.getLogger(TramiteSimpleParser.class);

	
	public static DitTramite modelToPersist(Tramite entrada) throws DerechohabientesBusinessException {
		
		DitTramite salida = null;
		
		if(entrada != null){
			try {
				salida = new DitTramite();
				
				
				if(entrada.getTramiteId()!=null)
				salida.setCveIdTramite(entrada.getTramiteId());
				
				salida.setDicTipoTramite(TipoTramiteParser.modelToPersist(entrada.getTipoTramite()));
				if(entrada.getRazonResultado()!=null ){
					salida.setDicRazonResultado(new DicRazonResultado());
					salida.getDicRazonResultado().setCveIdRazonResultado(entrada.getRazonResultado().getIdRazonResultado());
				} else {
					salida.setDicRazonResultado(null);
				}
				
				if(entrada.getResultado() != null)
					salida.setIndResultado(entrada.getResultado() ? new BigDecimal(1) : new BigDecimal(0));
				salida.setDicEstadoTramite(new DicEstadoTramite());
				salida.getDicEstadoTramite().setCveIdEstadoTramite(entrada.getEstadoTramite().getIdEstadoTramitePersona().longValue());
				salida.setRefObservacion(entrada.getObservacion());
				salida.setFecTramite(entrada.getFechaTramite());
				salida.setFecTramite(entrada.getFechaTramite());
				salida.setFecPresentacion(entrada.getFechaPresentacion());
				
				if(entrada.getFechaRegistroActualizacion()!=null)
					salida.setFecRegistroActualizado(entrada.getFechaRegistroActualizacion());
				
				if(entrada.getFechaConclusion()!=null)
					salida.setFecRegistroBaja(entrada.getFechaConclusion());									
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_TRAMITE_SIMPLE, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_TRAMITE_SIMPLE+" | "+e.getMessage());
			}
			
		}
		return salida;
	}
	
	public static Tramite persistToModel(DitTramite entrada) throws DerechohabientesBusinessException {
		
		Tramite salida = null;
		
		if(entrada != null) {
			try {
				salida = new Tramite();
				salida.setTramiteId(entrada.getCveIdTramite());
				EstadoTramite estadoTramite = EstadoTramiteParser.persisToModel(entrada.getDicEstadoTramite());
				salida.setEstadoTramite(estadoTramite);
				
				if(entrada.getDicRazonResultado() != null) {
					salida.setRazonResultado(RazonResultadoParser.persisToModel(entrada.getDicRazonResultado()));
				}
				
				TipoTramite tipoTramite  = TipoTramiteParser.persisToModel(entrada.getDicTipoTramite());
				salida.setTipoTramite(tipoTramite);
				
				if(entrada.getIndResultado() != null)
				{
					if(entrada.getIndResultado().equals(new BigDecimal(1))){
						salida.setResultado(true);
					}
					else {
						salida.setResultado(false);
					}
				}
				
				salida.setFechaTramite(entrada.getFecTramite());
				salida.setFechaPresentacion(entrada.getFecPresentacion());
				salida.setFechaRegistroActualizacion(entrada.getFecRegistroActualizado());
				salida.setObservacion(entrada.getRefObservacion());
				if(entrada.getDitTramitePersonaFisica() != null){
					if(entrada.getDitTramitePersonaFisica().size() == 1) {
						Fisica fisica =  new Fisica();
						fisica.setIdPersona(entrada.getDitTramitePersonaFisica().get(0).getDitPersona().getCveIdPersona());
						salida.setPersona(fisica);
					} else {
						List<Fisica> personas = new ArrayList<Fisica>();
						for(DitTramitePersonaFisica ditTramitePersona: entrada.getDitTramitePersonaFisica())
						{
							Fisica fisica =  new Fisica();
							fisica.setIdPersona(ditTramitePersona.getDitPersona().getCveIdPersona());
							personas.add(fisica);
						}
						salida.setPersonas(personas);
					}
				}
				
				if(entrada.getDitDetalleTramite() != null) {
					salida.setDetalleTramiteXml(entrada.getDitDetalleTramite().getRefDatosTramiteXml());
				}
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_TRAMITE_SIMPLE+" | "+e.getMessage());
			}
			
		}
		
		
		return salida;
	}
	
	public static DitTramite modelToPersistSimple(Tramite entrada) throws DerechohabientesBusinessException {
		//comentarios
		DitTramite salida = null;
		
		if(entrada != null){
			try {
				salida = new DitTramite();
				
				if(entrada.getTramiteId()!=null)
				salida.setCveIdTramite(entrada.getTramiteId());
			} catch (Exception e) {
				logger.error(ExceptionMessages.ERROR_PARSER_TRAMITE_SIMPLE, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_TRAMITE_SIMPLE+" | "+e.getMessage());
			}
			
		}
		return salida;
	}

	public static List<Tramite> persistToModelList(List<DitTramite> entrada) throws DerechohabientesBusinessException {
		List<Tramite> salida =null;
		
		if(entrada != null && entrada.size() > 0){
			salida= new ArrayList<Tramite>();
			for (DitTramite tramite : entrada) {
				salida.add(persistToModel(tramite));
			}
			
		}
		
		
		return salida;
	}
	
	
}
