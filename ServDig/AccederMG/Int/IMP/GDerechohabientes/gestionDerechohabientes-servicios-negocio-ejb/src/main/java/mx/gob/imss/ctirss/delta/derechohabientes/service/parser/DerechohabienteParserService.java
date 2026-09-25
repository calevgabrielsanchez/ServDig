package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.AsignacionNSSParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.EntidadFederativaParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.EstadoCivilParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.SexoParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNss;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaDerechohabiente;

@Stateless(name = "derechohabienteParserService", mappedName = "derechohabienteParserService")
public class DerechohabienteParserService extends AbstractServiceUtility implements DerechohabienteParserServiceLocal{

	@EJB PersonaDomicilioParserServiceLocal personaDomicilioParserServiceLocal;
	
	@Override
	public DitPersonaDerechohabiente modelToPersist(Derechohabiente entrada) throws DerechohabientesBusinessException {
		DitPersonaDerechohabiente salida=null;
		if(entrada != null) {
			salida=new DitPersonaDerechohabiente();
			try {
				salida.setFecRegistroAlta(entrada.getFechaRegistroAlta());
				salida.setFecRegistroBaja(entrada.getFechaRegistroBaja());
				salida.setFecRegistroActualizado(entrada.getFechaRegistroActualizado());	
				salida.setDitPersona(new DitPersona());
				salida.getDitPersona().setCveIdPersona(entrada.getIdPersona());
				salida.setCveExpedienteElectronico(entrada.getExpedienteElectronico());
			} catch (Exception e) {
				log.error(ExceptionMessages.ERROR_PARSER_DERECHOHABIENTE, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_DERECHOHABIENTE+" | "+e.getMessage());
			}	
		}
		return salida;
	}

	@Override
	public Derechohabiente persisToModel(DitPersonaDerechohabiente entrada) throws Exception {
		Derechohabiente salida=null;
		
		if(entrada != null) {
			try {
				salida=new Derechohabiente();
				salida.setIdPersonaDerechohabiente(entrada.getCveIdPerDerechohabiente());
				log.debug("El id de la persona del derechohabiente es: " + entrada.getDitPersona().getCveIdPersona());
				salida.setIdPersona(entrada.getDitPersona().getCveIdPersona());
				log.debug("La persona relacionada al derechohabiente es: " + entrada.getDitPersona());
				salida.setNombre(entrada.getDitPersona().getNomNombre());
				salida.setPrimerApellido(entrada.getDitPersona().getNomPrimerApellido());
				salida.setSegundoApellido(entrada.getDitPersona().getNomSegundoApellido());
				salida.setFechaNacimiento(entrada.getDitPersona().getFecNacimiento());
				salida.setCurp(entrada.getDitPersona().getCurp());
				salida.setLugarNacimiento(EntidadFederativaParser.persisToModel(entrada.getDitPersona().getDgCatEstado()));
				salida.setSexo(SexoParser.persisToModel(entrada.getDitPersona().getDicSexo()));
				if(entrada.getDitPersona().getDicEstadoCivil() != null)
					salida.setEstadoCivil(EstadoCivilParser.persisToModel(entrada.getDitPersona().getDicEstadoCivil()));
				salida.setPersonaDomicilio(personaDomicilioParserServiceLocal.persistToModelList(entrada.getDitPersona().getDitPersonafDoms()));
				salida.setMesRegistroNac(entrada.getDitPersona().getNumMesNacReg());
				salida.setAnioRegistroNac(entrada.getDitPersona().getNumAnioNacReg());
			} catch (Exception e) {
				log.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_DERECHOHABIENTE+" | "+e.getMessage());
			}
			
		}
		
		return salida;
	}

}
