package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.AsignacionNSSParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.CalidadParentescoParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.EntidadFederativaParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.EstadoCivilParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.SexoParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.persistence.DicCalidadParentesco;
import mx.gob.imss.ctirss.delta.persistence.DitGrupoFamiliar;
import mx.gob.imss.ctirss.delta.persistence.DitGrupoFamiliarCL3;
import mx.gob.imss.ctirss.delta.persistence.DitGrupoFamiliarCL3PK;
import mx.gob.imss.ctirss.delta.persistence.DitGrupoFamiliarPK;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaDerechohabiente;
import mx.gob.imss.ctirss.delta.persistence.DitPersonafDom;

import org.apache.commons.lang.StringUtils;

@Stateless( name = "grupoFamiliarParserService", mappedName = "grupoFamiliarParserService" )
public class GrupoFamiliarParserService extends AbstractServiceUtility implements GrupoFamiliarParserServiceLocal{

	@EJB(mappedName = "domicilioServiceBusiness") 
	DomicilioServiceBusinessRemote domicilioServiceBusinessRemote;
	@EJB DerechohabienteParserServiceLocal derechohabienteParserServiceLocal;
	@EJB MedicoEnTurnoParserServiceLocal medicoEnTurnoParserServiceLocal;
	
	@Override
	public GrupoFamiliar persistToModel(DitGrupoFamiliar entrada) throws DerechohabientesBusinessException, Exception{
		GrupoFamiliar salida = null;
		
		if(entrada != null) {
			salida=new GrupoFamiliar();
			salida.setCalidad(new BigDecimal(entrada.getNumCalidad()));
			salida.setAsignacionNSS(AsignacionNSSParser.persisToModel(entrada.getDitAsignacionNss()));			
			salida.setFechaRegistroAlta(entrada.getFecRegistroAlta());
			salida.setFechaRegistroBaja(entrada.getFecRegistroBaja());
			salida.setFechaRegistroActualizacion(entrada.getFecRegistroActualizado());
			salida.setParentesco(CalidadParentescoParser.persisToModel(entrada.getDicCalidadParentesco()));						
			salida.setMedicoEnTurno(medicoEnTurnoParserServiceLocal.persisToModel(entrada.getDitUmfConsTurnoMedico()));
			salida.setFechaCambioTurnoMedico(entrada.getFecCambioTurnoConsultorio());
			salida.setIndSimilarCalDifGpoFam(entrada.getInd_SimilarCalDifGpoFam());
			salida.setDerechohabiente(new Derechohabiente());
			salida.setAgregadoAfiliacion( entrada.getRefAgregadoAfiliacion() );
			salida.setAgregadoMedico( entrada.getRefAgregadoMedico() );
			salida.setDerechohabiente(this.getDerechohabiente(salida, entrada.getDitPersona()));
			if(entrada.getDitPersonafDom() != null) {
				salida.setCvePersonaDomicilio(entrada.getDitPersonafDom().getCveIdPersonafDom());
				Domicilio buscar = new Domicilio();
				buscar.setClave(Integer.valueOf(""+entrada.getDitPersonafDom().getDgDomicilioGeografico().getDomicilioId()));
				try {
					salida.setDomicilio(domicilioServiceBusinessRemote.consultarDomicilio(buscar));
				} catch (Exception e) {
					log.error(ExceptionMessages.DOMICILIO_CONSULTA,e);
					throw e;
				}
			}
			salida.setIndRecienNacido(entrada.getIndRecienNacido());
			
		}
		return salida;
	}

	private Derechohabiente getDerechohabiente(GrupoFamiliar grupoFamiliar, DitPersona persona) throws DerechohabientesBusinessException {
		Derechohabiente der = new Derechohabiente();
		der.setIdPersona(persona.getCveIdPersona());
		
		if(!StringUtils.isBlank(persona.getNomNombre())) {
			der.setNombre(persona.getNomNombre());//.toUpperCase().replace('#', '\u00D1'));
		}
		
		if(!StringUtils.isBlank(persona.getNomPrimerApellido())) {
			der.setPrimerApellido(persona.getNomPrimerApellido());//.toUpperCase().replace('#', '\u00D1'));
		}
		
		if(!StringUtils.isBlank(persona.getNomSegundoApellido())) {
			der.setSegundoApellido(persona.getNomSegundoApellido());//.toUpperCase().replace('#', '\u00D1'));
		}
		
		der.setCurp(persona.getCurp());
		der.setFechaNacimiento(persona.getFecNacimiento());
		der.setAsignacionNSS(grupoFamiliar.getAsignacionNSS());
		der.setSexo(SexoParser.persisToModel(persona.getDicSexo()));
		der.setLugarNacimiento(EntidadFederativaParser.persisToModel(persona.getDgCatEstado()));
		der.setEstadoCivil(EstadoCivilParser.persisToModel(persona.getDicEstadoCivil()));
		List<DitPersonaDerechohabiente> ditPersonaDerechohabientes = persona.getDitPersonaDerechohabientes();
		if(ditPersonaDerechohabientes != null && !ditPersonaDerechohabientes.isEmpty()) {
			DitPersonaDerechohabiente perDer = ditPersonaDerechohabientes.get(0);
			der.setExpedienteElectronico(perDer.getCveExpedienteElectronico());
		}
		
		der.setMesRegistroNac(persona.getNumMesNacReg());
		der.setAnioRegistroNac(persona.getNumAnioNacReg());
		
		// --------------------------------------------------------------------
		// Todos los integrantes del grupo familiar son personas físicas
		// --------------------------------------------------------------------
		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
		der.setTipoPersona(tipoPersona);
		
		return der;
		
	}
	@Override
	public DitGrupoFamiliar modelToPersist(GrupoFamiliar entrada) throws DerechohabientesBusinessException{
        return modelToPersist(entrada, false);
	}

    @Override
    public DitGrupoFamiliar modelToPersist(GrupoFamiliar entrada, boolean afectarDomicilio) throws DerechohabientesBusinessException{
        DitGrupoFamiliar salida = null;

        if(entrada != null) {
            salida=new DitGrupoFamiliar();

            salida.setId(new DitGrupoFamiliarPK());
            salida.getId().setCveIdAsignacionNss(entrada.getAsignacionNSS().getIdAsignacionNSS());
            salida.getId().setCveIdPersonaIntegrante(entrada.getDerechohabiente().getIdPersona());
            salida.setDitUmfConsTurnoMedico(medicoEnTurnoParserServiceLocal.modelToPersist(entrada.getMedicoEnTurno()));
            salida.setNumCalidad(entrada.getCalidad().longValue());
            salida.setDicCalidadParentesco(new DicCalidadParentesco());
            salida.getDicCalidadParentesco().setCveIdCalidadParentesco(entrada.getParentesco().getIdParentesco());
            if(afectarDomicilio && entrada.getCvePersonaDomicilio() == null){
                salida.setDitPersonafDom(null);
            }else if(entrada.getCvePersonaDomicilio() != null){
                salida.setDitPersonafDom(new DitPersonafDom());
                salida.getDitPersonafDom().setCveIdPersonafDom(entrada.getCvePersonaDomicilio());
            }
            salida.setFecRegistroAlta(entrada.getFechaRegistroAlta());
            salida.setFecRegistroActualizado(entrada.getFechaRegistroActualizacion());
            salida.setFecRegistroBaja(entrada.getFechaRegistroBaja());
            salida.setFecCambioTurnoConsultorio(entrada.getFechaCambioTurnoMedico());
            salida.setInd_SimilarCalDifGpoFam(entrada.getIndSimilarCalDifGpoFam());
            salida.setRefAgregadoAfiliacion( entrada.getAgregadoAfiliacion() );
            salida.setRefAgregadoMedico( entrada.getAgregadoMedico() );
            salida.setIndRecienNacido(entrada.getIndRecienNacido());
        }
        return salida;
    }

	@Override
	public List<GrupoFamiliar> persistToModelList(List<DitGrupoFamiliar> entrada) throws Exception {
		
		List<GrupoFamiliar> salida = new ArrayList<GrupoFamiliar>();
		if(entrada.size() > 0){
			for(DitGrupoFamiliar integrante: entrada) {
				salida.add(persistToModel(integrante));
			}
		}				
		return salida;
	}

	@Override
	public GrupoFamiliar persistToModelNssParentesco(DitGrupoFamiliar entrada)
			throws DerechohabientesBusinessException, Exception {
		
		GrupoFamiliar salida = null;
		
		if(entrada != null) {
			salida=new GrupoFamiliar();
			salida.setCalidad(new BigDecimal(entrada.getNumCalidad()));
			salida.setAsignacionNSS(AsignacionNSSParser.persistToModelDatosBasicos(entrada.getDitAsignacionNss()));			
			salida.setFechaRegistroAlta(entrada.getFecRegistroAlta());
			salida.setFechaRegistroBaja(entrada.getFecRegistroBaja());
			salida.setFechaRegistroActualizacion(entrada.getFecRegistroActualizado());
			salida.setParentesco(CalidadParentescoParser.persisToModel(entrada.getDicCalidadParentesco()));					
			salida.setFechaCambioTurnoMedico(entrada.getFecCambioTurnoConsultorio());
			salida.setIndSimilarCalDifGpoFam(entrada.getInd_SimilarCalDifGpoFam());
			salida.setDerechohabiente(this.getDerechohabiente(salida, entrada.getDitPersona()));
		}
		return salida;
	}

	@Override
	public List<GrupoFamiliar> persistToModelNssParentescoList(
			List<DitGrupoFamiliar> entrada)
			throws DerechohabientesBusinessException, Exception {
		List<GrupoFamiliar> salida = new ArrayList<GrupoFamiliar>();
		if(entrada.size() > 0){
			for(DitGrupoFamiliar integrante: entrada) {
				salida.add(persistToModelNssParentesco(integrante));
			}
		}				
		return salida;
	}

	@Override
	public GrupoFamiliar persistCL3ToModel(DitGrupoFamiliarCL3 entrada) throws DerechohabientesBusinessException, Exception {
		GrupoFamiliar salida = null;
		if(entrada != null) {
			salida=new GrupoFamiliar();
			salida.setCalidad(new BigDecimal(entrada.getNumCalidad()));
			salida.setAsignacionNSS(AsignacionNSSParser.persisCL3ToModel(entrada.getDitAsignacionNss()));			
			salida.setFechaRegistroAlta(entrada.getFecRegistroAlta());
			salida.setFechaRegistroBaja(entrada.getFecRegistroBaja());
			salida.setFechaRegistroActualizacion(entrada.getFecRegistroActualizado());
			salida.setParentesco(CalidadParentescoParser.persisToModel(entrada.getDicCalidadParentesco()));						
			salida.setMedicoEnTurno(medicoEnTurnoParserServiceLocal.persisToModel(entrada.getDitUmfConsTurnoMedico()));
			salida.setFechaCambioTurnoMedico(entrada.getFecCambioTurnoConsultorio());
			salida.setIndSimilarCalDifGpoFam(entrada.getInd_SimilarCalDifGpoFam());
			salida.setDerechohabiente(new Derechohabiente());
			salida.setAgregadoAfiliacion( entrada.getRefAgregadoAfiliacion() );
			salida.setAgregadoMedico( entrada.getRefAgregadoMedico() );
			salida.setDerechohabiente(this.getDerechohabiente(salida, entrada.getDitPersona()));
			if(entrada.getDitPersonafDom() != null) {
				salida.setCvePersonaDomicilio(entrada.getDitPersonafDom().getCveIdPersonafDom());
				Domicilio buscar = new Domicilio();
				buscar.setClave(Integer.valueOf(""+entrada.getDitPersonafDom().getDgDomicilioGeografico().getDomicilioId()));
				try {
					salida.setDomicilio(domicilioServiceBusinessRemote.consultarDomicilio(buscar));
				} catch (Exception e) {
					log.error(ExceptionMessages.DOMICILIO_CONSULTA,e);
					throw e;
				}
			}
			salida.setIndRecienNacido(entrada.getIndRecienNacido());
		}
		return salida;
	}

	@Override
	public DitGrupoFamiliarCL3 modelToPersistCL3(GrupoFamiliar entrada) throws DerechohabientesBusinessException, Exception {
		DitGrupoFamiliarCL3 salida = null;
		if(entrada != null) {
			salida = new DitGrupoFamiliarCL3();			
			
			salida.setId(new DitGrupoFamiliarCL3PK());
			salida.getId().setCveIdAsignacionNss(entrada.getAsignacionNSS().getIdAsignacionNSS());
			salida.getId().setCveIdPersonaIntegrante(entrada.getDerechohabiente().getIdPersona());				
			salida.setDitUmfConsTurnoMedico(medicoEnTurnoParserServiceLocal.modelToPersist(entrada.getMedicoEnTurno()));
			salida.setNumCalidad(entrada.getCalidad().longValue());
			salida.setDicCalidadParentesco(new DicCalidadParentesco());
			salida.getDicCalidadParentesco().setCveIdCalidadParentesco(entrada.getParentesco().getIdParentesco());
			if(entrada.getCvePersonaDomicilio() != null) {
				salida.setDitPersonafDom(new DitPersonafDom());
				salida.getDitPersonafDom().setCveIdPersonafDom(entrada.getCvePersonaDomicilio());
			}
			salida.setFecRegistroAlta(entrada.getFechaRegistroAlta());
			salida.setFecRegistroActualizado(entrada.getFechaRegistroActualizacion());
			salida.setFecRegistroBaja(entrada.getFechaRegistroBaja());	
			salida.setFecCambioTurnoConsultorio(entrada.getFechaCambioTurnoMedico());	
			salida.setInd_SimilarCalDifGpoFam(entrada.getIndSimilarCalDifGpoFam());
			salida.setRefAgregadoAfiliacion( entrada.getAgregadoAfiliacion() );
			salida.setRefAgregadoMedico( entrada.getAgregadoMedico() );
			salida.setIndRecienNacido(entrada.getIndRecienNacido());
		}
		return salida;
	}
	
}