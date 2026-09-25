package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCircunscripcionForanea;
import mx.gob.imss.ctirss.delta.persistence.DitCircunscripcionForanea;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;

@Stateless(name = "circunscripcionForaneaParserService", mappedName = "circunscripcionForaneaParserService")
public class CircunscripcionForaneaParserService extends AbstractServiceUtility implements
		CircunscripcionForaneaParserServiceLocal {

	@EJB(mappedName = "domicilioServiceBusiness") 
	DomicilioServiceBusinessRemote domicilioServiceBusinessRemote;
	@EJB
	MedicoEnTurnoParserServiceLocal medicoEnTurnoParserServiceLocal;
	
	@Override
	public DitCircunscripcionForanea modelToPersist(TramiteCircunscripcionForanea entrada) throws DerechohabientesBusinessException {

		DitCircunscripcionForanea salida = null;
		
		if(entrada != null) {
			try {
				salida = new DitCircunscripcionForanea();
				log.debug("Este es el id de la circunscripcion: " + entrada.getCveCircunscripcion());
				if(entrada.getCveCircunscripcion() != null) {
					salida.setCveIdCircunscripcion(entrada.getCveCircunscripcion());
				}
				salida.setDitUmfConsTurnoMedico2(medicoEnTurnoParserServiceLocal.modelToPersist(entrada.getMedicoEnTurnoOrigen()));
				salida.setDitUmfConsTurnoMedico1(medicoEnTurnoParserServiceLocal.modelToPersist(entrada.getMedicoEnTurnoDestino()));
				
				log.debug("Este es el id del tramite de autrizacion: " + entrada.getTramiteId());
				salida.setDitTramite(new DitTramite());
				salida.getDitTramite().setCveIdTramite(entrada.getTramiteId());
				
				if(entrada.getTramiteSuspension() != null)
				{
					log.debug("Trae tramite de suspension: " + entrada.getTramiteSuspension().getTramiteId());
					salida.setDitTramiteSuspension(new DitTramite());
					salida.getDitTramiteSuspension().setCveIdTramite(entrada.getTramiteSuspension().getTramiteId());
				}
				
				salida.setFecFinCircunscripcion(entrada.getFecFinCircunscripcion());
				salida.setFecInicioCircunscripcion(entrada.getFecInicioCircunscripcion());
				salida.setFecRegistroActualizado(entrada.getFechaPresentacion());
				salida.setIndCircunscripcionActiva(entrada.getIndCircunscripcionActiva());
				
				// ----------------------------------------------------------------------------
				// Si no tenía domicilio origen, se asigna el destino por que la base de
				// datos no acepta nulos
				// ----------------------------------------------------------------------------
				if( (entrada.getDomicilioOrigen() != null) && (entrada.getDomicilioOrigen().getClave() != null) )
					salida.setDomicilioIdOrigen(entrada.getDomicilioOrigen().getClave().longValue());
				//else
					//salida.setDomicilioIdOrigen(entrada.getDomicilioDestino().getClave().longValue());
				
				
				salida.setDomicilioIdDestino(entrada.getDomicilioDestino().getClave().longValue());
			} catch (Exception e) {
				log.error(ExceptionMessages.ERROR_PARSER_CIRCUNSCRIPCION_FORANEA, e);
				throw new DerechohabientesBusinessException(e.getMessage(),ExceptionMessages.ERROR_PARSER_CIRCUNSCRIPCION_FORANEA);
			}
			
		}
		return salida;
	}

	@Override
	public TramiteCircunscripcionForanea persistToModel(DitCircunscripcionForanea entrada) throws DerechohabientesBusinessException,Exception {
		
		TramiteCircunscripcionForanea salida = null;
		
		if(entrada != null) {
			try {
				salida = new TramiteCircunscripcionForanea();
				salida.setCveCircunscripcion(entrada.getCveIdCircunscripcion());
				salida.setMedicoEnTurnoOrigen(medicoEnTurnoParserServiceLocal.persisToModel(entrada.getDitUmfConsTurnoMedico2()));
				salida.setMedicoEnTurnoDestino(medicoEnTurnoParserServiceLocal.persisToModel(entrada.getDitUmfConsTurnoMedico1()));
				salida.setTramiteId(entrada.getDitTramite().getCveIdTramite());
				salida.setPersona(new Fisica());
				salida.getPersona().setIdPersona(entrada.getDitTramite().getDitTramitePersonaFisica().get(0).getDitPersona().getCveIdPersona());
				salida.setFecInicioCircunscripcion(entrada.getFecInicioCircunscripcion());
				salida.setFecFinCircunscripcion(entrada.getFecFinCircunscripcion());
				salida.setIndCircunscripcionActiva(entrada.getIndCircunscripcionActiva());
				salida.setObservacion(entrada.getDitTramite().getRefObservacion());
				if(entrada.getDitTramiteSuspension() != null) {
					salida.setTramiteSuspension(new Tramite());
					salida.getTramiteSuspension().setTramiteId(entrada.getDitTramiteSuspension().getCveIdTramite());
					salida.getTramiteSuspension().setObservacion(entrada.getDitTramiteSuspension().getRefObservacion());
				}
				
				try {
					
					Domicilio origen = new Domicilio();
					origen.setClave(entrada.getDomicilioIdOrigen().intValue());
					origen = domicilioServiceBusinessRemote.consultarDomicilio(origen);
					salida.setDomicilioOrigen(origen);
				} catch (Exception e) {
					
					// -----------------------------------------------------------------------
					// El domicilio origen puede estar nulo.
					// Antes se asignaba el domicilio destino en caso de no traer origen
					// -----------------------------------------------------------------------
					
					//log.error(ExceptionMessages.DOMICILIO_CONSULTA, e);
					//throw e;
				}
				Domicilio destino = new Domicilio();
				destino.setClave(entrada.getDomicilioIdDestino().intValue());
				try {
					destino = domicilioServiceBusinessRemote.consultarDomicilio(destino);
					salida.setDomicilioDestino(destino);
				} catch (Exception e) {
					throw e;
				}				
			} catch (Exception e) {
				log.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(e.getMessage(),ExceptionMessages.ERROR_PARSER_CIRCUNSCRIPCION_FORANEA);
			}
			
		}
		return salida;
	}

}
