package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.EntidadFederativaParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.EstadoCivilParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.EstadoTramiteParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.RazonResultadoParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.SexoParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.TipoTramiteParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.persistence.DgDomicilioGeografico;
import mx.gob.imss.ctirss.delta.persistence.DicCalidadParentesco;
import mx.gob.imss.ctirss.delta.persistence.DitCorreccionDatoDerechohab;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;

@Stateless(name = "correccionDatoDerechohabienteParser", mappedName = "correccionDatoDerechohabienteParser")
public class CorreccionParserService extends AbstractServiceUtility implements CorreccionParserServiceLocal {

	@EJB(mappedName = "domicilioServiceBusiness")  DomicilioServiceBusinessRemote domicilioServiceBusinessRemote;
	@EJB MedicoEnTurnoParserServiceLocal medicoEnTurnoParserServiceLocal;

	@Override
	public DitCorreccionDatoDerechohab modelToPersist(TramiteCorreccionDerechohabiente entrada) throws DerechohabientesBusinessException {
		DitCorreccionDatoDerechohab salida = new DitCorreccionDatoDerechohab();
		if(entrada != null){
			try {
				salida = new DitCorreccionDatoDerechohab();
				salida.setDitTramite(new DitTramite());
				salida.getDitTramite().setCveIdTramite(entrada.getTramiteId());
				salida.setCveCurp(entrada.getCurpCap());
				if(entrada.getEstadoCivil() != null){
					if(entrada.getEstadoCivil().getIdEstadoCivil()!= null && entrada.getEstadoCivil().getIdEstadoCivil() > 0)
						salida.setDicEstadoCivil(EstadoCivilParser.modelToPersist(entrada.getEstadoCivil()));
				}
				salida.setDicSexo(SexoParser.modelToPersist(entrada.getSexo()));
				salida.setNomNombre(entrada.getNombre());
				salida.setNomPrimerApellido(entrada.getPrimerApellido());
				salida.setNomSegundoApellido(entrada.getSegundoApellido());
				salida.setFecNacimiento(entrada.getFechaNacimiento());
				if(entrada.getParentesco()!=null){
					salida.setDicCalidadParentesco(new DicCalidadParentesco());
					salida.getDicCalidadParentesco().setCveIdCalidadParentesco(entrada.getParentesco().getIdParentesco());
				}
				
				if(entrada.getDomicilio() != null && entrada.getDomicilio().getClave() != null){
					salida.setDgDomicilioGeografico(new DgDomicilioGeografico());
					salida.getDgDomicilioGeografico().setDomicilioId(entrada.getDomicilio().getClave().longValue());
				}
				
				if(entrada.getMedicoEnTurno() != null)
					salida.setDitUmfConsTurnoMedico(medicoEnTurnoParserServiceLocal.modelToPersist(entrada.getMedicoEnTurno()));
				if(entrada.getLugarNacimiento() != null && entrada.getLugarNacimiento().getClave() != null && !entrada.getLugarNacimiento().getClave().equals("-1"))
					salida.setDgCatEstado(EntidadFederativaParser.modelToPersist(entrada.getLugarNacimiento()));
				salida.setFecRegistroAlta(new Date());
			} catch (Exception e) {
				log.error(ExceptionMessages.ERROR_PARSER_CORRECCION_DATO_DERECHOHABIENTE, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_CORRECCION_DATO_DERECHOHABIENTE+" | "+e.getMessage());
			}
			
		}
		
		return salida;
	}

	@Override
	public TramiteCorreccionDerechohabiente persistToModel(DitCorreccionDatoDerechohab entrada) throws DerechohabientesBusinessException, Exception {
		TramiteCorreccionDerechohabiente salida = null;
		
		if(entrada != null) {
			try {
				salida = new TramiteCorreccionDerechohabiente();
				salida.setIdPersona(entrada.getDitTramite().getDitTramitePersonaFisica().get(0).getDitPersona().getCveIdPersona());
				salida.setTramiteId(entrada.getDitTramite().getCveIdTramite());
				salida.setTipoTramite(new TipoTramite());
				salida.setTipoTramite(TipoTramiteParser.persisToModel(entrada.getDitTramite().getDicTipoTramite()));
				salida.setEstadoTramite(EstadoTramiteParser.persisToModel(entrada.getDitTramite().getDicEstadoTramite()));
				salida.setRazonResultado(RazonResultadoParser.persisToModel(entrada.getDitTramite().getDicRazonResultado()));
				salida.setNombre(entrada.getNomNombre());
				salida.setPrimerApellido(entrada.getNomPrimerApellido());
				salida.setSegundoApellido(entrada.getNomSegundoApellido());
				salida.setSexo(SexoParser.persisToModel(entrada.getDicSexo()));
				salida.setEstadoCivil(EstadoCivilParser.persisToModel(entrada.getDicEstadoCivil()));
				salida.setPersona(new Fisica());
				salida.setCurpCap(entrada.getCveCurp());
				salida.setFechaNacimiento(entrada.getFecNacimiento());
				salida.setMedicoEnTurno(medicoEnTurnoParserServiceLocal.persisToModel(entrada.getDitUmfConsTurnoMedico()));
				salida.setLugarNacimiento(EntidadFederativaParser.persisToModel(entrada.getDgCatEstado()));
				if(entrada.getDicCalidadParentesco()!=null){
					salida.setParentesco(new Parentesco());
					salida.getParentesco().setIdParentesco(entrada.getDicCalidadParentesco().getCveIdCalidadParentesco());
					salida.getParentesco().setDescripcion(entrada.getDicCalidadParentesco().getDesParentesco());
				}
				
				Domicilio domicilio = new Domicilio();
				if(entrada.getDgDomicilioGeografico() != null) {
					
					domicilio.setClave(entrada.getDgDomicilioGeografico().getDomicilioId().intValue());
					
					try {
						domicilio = domicilioServiceBusinessRemote.consultarDomicilio(domicilio);
						salida.setDomicilio(domicilio);
					} catch (Exception e) {
						log.error(ExceptionMessages.DOMICILIO_CONSULTA, e);
						throw e;
					}
				}
				
			} catch (Exception e) {
				log.error(ExceptionMessages.ERROR_DATOS, e);
				throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_PARSER_CORRECCION_DATOS_DERECHOHAB+" | "+e.getMessage());
			}
			
		}
		return salida;
	}

}
