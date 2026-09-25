package mx.gob.imss.cit.dacvass.servicios.externos.service.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.ErrorResponseBean;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.DerechohabienteDTO;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.DerechohabienteSinolave;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.PersonaFisicaMoral;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.DatosGrupoFamiliar;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.DatosHistoriaLaboral;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.DatosPersona;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.DatosPersonaFisica;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.DatosPersonaRenapo;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.EntidadFederativa;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.EstadoCivil;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Pais;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Parentesco;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Persona;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Sexo;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Turno;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.UnidadMedicaFamiliar;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.asegurado.DatosGeneralesAseguradoCL;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.asegurado.DatosGeneralesAseguradoMarcaAfiliatoria;
import mx.gob.imss.cit.dacvass.servicios.externos.persistence.vigencia.MgtInfincasegvig;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

@Resource
public class ParserDatosPersonaToRest {

	private static final Logger log = LoggerFactory
            .getLogger(ParserCatalogosServiciosToRest.class);
	
	
	public static  DatosGrupoFamiliar setDatosGrupoFamiliar(GrupoFamiliar gpoFam) {
		DatosGrupoFamiliar dgf = new DatosGrupoFamiliar();
		dgf.setConsultorio(gpoFam.getMedicoEnTurno().getConsultorio().getDescripcion());
		dgf.setCveIdAsignacionNssGrupoFamiliar(gpoFam.getAsignacionNSS().getIdAsignacionNSS());
		dgf.setCveIdPersonaIntegrante(gpoFam.getDerechohabiente().getIdPersona());
		dgf.setUmf(gpoFam.getMedicoEnTurno().getUnidadMedicaFamiliar().getNoEconomico()+"");
		Turno turno = new Turno();
		turno.setIdTurno(gpoFam.getMedicoEnTurno().getTurno().getIdTurno());
		dgf.setTurno(turno);
		Parentesco parentesco = new Parentesco();
		parentesco.setIdParentesco(gpoFam.getParentesco().getIdParentesco());
		parentesco.setDescripcion(gpoFam.getParentesco().getDescripcion());
		
		dgf.setParentesco(parentesco);
		
		return dgf;
		
	}
	
	public static  DatosPersonaFisica setDatosPersonaFisica(Fisica sat) {
		DatosPersonaFisica dpf = new DatosPersonaFisica();
		dpf.setRfc(sat.getRfc());
		dpf.setCveIdPersonaFisica(sat.getCveFisica());
		dpf.setDomicilioFiscalSat(sat.getDomicilioFiscal().getDescripcion());
		dpf.setDatosPersonaRepresentada(null);
		
		return dpf;
		
	}
	
	public static DatosHistoriaLaboral setDatosHistoriaLaboral(SujetoObligado patron) {
			
				DatosHistoriaLaboral hisLab = new DatosHistoriaLaboral();
				//hisLab.setActividadEmpresa(patron.getStringClasificacion());
				/// isLab.setCorreElectronico(patron.get);
				//hisLab.setDomicilioEmpresa(patron.getDomicilioFiscal().getDescripcion());
				//hisLab.setDomicilioIMSS(null);
				//hisLab.setEntidadFederativa(patron.getDomicilioFiscal().getLocalidad().getMunicipio().getEntidadFederativa().getNombre());
				//hisLab.setFechaBaja(null);
				//hisLab.setFechaInscripcion(null);
				
				hisLab.setNombrePatron(patron.getMoral() != null ? patron.getMoral().getRazonSocial() : patron.getFisica().getNombreCompleto());
				hisLab.setNumeroRegistroPatronal(patron.getNumeroRegistroPatronal() + patron.getModalidad().getNumModalidad() + patron.getDigVerificador());
				//hisLab.setrfc
				return hisLab;
		
		
		
	}
	
	public static  Persona setDatosPersona(Fisica persona, Persona objPersonaBdtu) {

		objPersonaBdtu.setCurp(persona.getCurp());
		objPersonaBdtu.setNombre(persona.getNombre());
		objPersonaBdtu.setPrimerApellido(persona.getPrimerApellido());
		objPersonaBdtu.setSegundoApellido(persona.getSegundoApellido());	
		objPersonaBdtu.setFechaDefuncion(persona.getFechaDefuncion());
		objPersonaBdtu.setFechaNacimiento(persona.getFechaNacimiento());
		objPersonaBdtu.setNss(persona.getNss());
		objPersonaBdtu.setCveIdPersona(persona.getIdPersona());
		objPersonaBdtu.setRfc(persona.getRfc());
		
			if(persona.getActaNacimiento()!= null) {
				DatosPersonaRenapo renapo = new DatosPersonaRenapo();
				renapo.setFoja(persona.getActaNacimiento().getNoFoja());
				renapo.setFolio(persona.getActaNacimiento().getNoActa());
				renapo.setLibro(persona.getActaNacimiento().getNoLibro());
				renapo.setTomo(persona.getActaNacimiento().getTomo());
				renapo.setAnio(persona.getActaNacimiento().getAnio()+"");
				if(persona.getActaNacimiento().getMunicipio()!= null) {
					renapo.setMunicipioRegistro(persona.getActaNacimiento().getMunicipio().getNombre());
				}
				objPersonaBdtu.setDatosPersonaRenapo(renapo);
			}
			if(persona.getEstadoCivil() != null) {
				EstadoCivil edoCivil = new EstadoCivil();
				edoCivil.setIdEstadoCivil(persona.getEstadoCivil().getIdEstadoCivil());
				edoCivil.setDescripcion(persona.getEstadoCivil().getDescripcion());
				objPersonaBdtu.setEstadoCivil(edoCivil);
			}
		if(persona.getLugarNacimiento() != null) {
			EntidadFederativa nacimiento = new EntidadFederativa();
			nacimiento.setClave(persona.getLugarNacimiento().getClave());
			//nacimiento.setClaveRenapo(personaRenapo.getLugarNacimiento().getClaveRenapo());
			nacimiento.setNombre(persona.getLugarNacimiento().getNombre());
			objPersonaBdtu.setLugarNacimiento(nacimiento);
		}
		
		if(persona.getPais()!= null) {
			Pais pais = new Pais();
			pais.setDescripcion(persona.getPais().getDescripcion());
			pais.setNacionalidad(persona.getPais().getNacionalidad());
			pais.setIdPais(persona.getPais().getIdPais());
			objPersonaBdtu.setPais(pais);
		}
		
		if(persona.getSexo()!= null) {
			Sexo sexo = new Sexo();
			sexo.setIdSexo(persona.getSexo().getIdSexo().longValue());
			sexo.setDescripcion(persona.getSexo().getDescripcion());
			objPersonaBdtu.setSexo(sexo);
		}
		return objPersonaBdtu;
		
	}
	
	
	
	public static  DerechohabienteDTO setDatosDerechohabiente(Fisica persona) {
		DerechohabienteDTO objPersonaBdtu = new DerechohabienteDTO();
		objPersonaBdtu.setCurp(persona.getCurp());
		objPersonaBdtu.setNombre(persona.getNombre());
		objPersonaBdtu.setPrimerApellido(persona.getPrimerApellido());
		objPersonaBdtu.setSegundoApellido(persona.getSegundoApellido());	
		objPersonaBdtu.setFechaDefuncion(persona.getFechaDefuncion());
		objPersonaBdtu.setFechaNacimiento(persona.getFechaNacimiento());
		objPersonaBdtu.setNss(persona.getNss());
		objPersonaBdtu.setCveIdPersona(persona.getIdPersona());
		objPersonaBdtu.setRfc(persona.getRfc());
		
			if(persona.getEstadoCivil() != null) {
				EstadoCivil edoCivil = new EstadoCivil();
				edoCivil.setIdEstadoCivil(persona.getEstadoCivil().getIdEstadoCivil());
				edoCivil.setDescripcion(persona.getEstadoCivil().getDescripcion());
				objPersonaBdtu.setEstadoCivil(edoCivil);
			}
		if(persona.getLugarNacimiento() != null) {
			EntidadFederativa nacimiento = new EntidadFederativa();
			nacimiento.setClave(persona.getLugarNacimiento().getClave());
			//nacimiento.setClaveRenapo(personaRenapo.getLugarNacimiento().getClaveRenapo());
			nacimiento.setNombre(persona.getLugarNacimiento().getNombre());
			objPersonaBdtu.setLugarNacimiento(nacimiento);
		}
		
		if(persona.getPais()!= null) {
			Pais pais = new Pais();
			pais.setDescripcion(persona.getPais().getDescripcion());
			pais.setNacionalidad(persona.getPais().getNacionalidad());
			pais.setIdPais(persona.getPais().getIdPais());
			objPersonaBdtu.setPais(pais);
		}
		
		if(persona.getSexo()!= null) {
			Sexo sexo = new Sexo();
			sexo.setIdSexo(persona.getSexo().getIdSexo().longValue());
			sexo.setDescripcion(persona.getSexo().getDescripcion());
			objPersonaBdtu.setSexo(sexo);
		}
		return objPersonaBdtu;
		
	}
	
	public static  DatosGeneralesAseguradoMarcaAfiliatoria setDatosDerechohabiente(DatosGeneralesAseguradoCL persona) {
		DatosGeneralesAseguradoMarcaAfiliatoria objPersonaBdtu = new DatosGeneralesAseguradoMarcaAfiliatoria();
		objPersonaBdtu.setCurp(persona.getCurp());
		objPersonaBdtu.setNombre(persona.getNombre());
		objPersonaBdtu.setPrimerApellido(persona.getPrimerApellido());
		objPersonaBdtu.setSegundoApellido(persona.getSegundoApellido());	
		objPersonaBdtu.setNss(persona.getNss());
		objPersonaBdtu.setRfc(persona.getRfc());
		UnidadMedicaFamiliar umf = new UnidadMedicaFamiliar();
		umf.setNumUMF(persona.getNoEconomico() != null ? persona.getNoEconomico().toString() : "");

		objPersonaBdtu.setUmf(umf);
		Subdelegacion subdelegacion = new Subdelegacion();
		Delegacion delegacion = new Delegacion();
		delegacion.setClave(persona.getCveDelegacion() != null ? persona.getCveDelegacion().toString() : "");
		subdelegacion.setDelegacion(delegacion);
		subdelegacion.setId(persona.getCveSubdelegacionBigDEcimal() != null ? persona.getCveSubdelegacionBigDEcimal().longValue() : null);
		subdelegacion.setClave(persona.getCveSubdelegacion() != null ? persona.getCveSubdelegacion() : "");
		objPersonaBdtu.setSubdelegacion(subdelegacion);

		return objPersonaBdtu;
		
	}
	
	
	public static  DatosPersona setDatosPersonaRenapo( Fisica personaRenapo ) {
		DatosPersona objPersonaBdtu = new DatosPersona();
		objPersonaBdtu.setCurp(personaRenapo.getCurp());
		objPersonaBdtu.setNombre(personaRenapo.getNombre());
		objPersonaBdtu.setPrimerApellido(personaRenapo.getPrimerApellido());
		objPersonaBdtu.setSegundoApellido(personaRenapo.getSegundoApellido());	
		objPersonaBdtu.setFechaDefuncion(personaRenapo.getFechaDefuncion());
		objPersonaBdtu.setFechaNacimiento(personaRenapo.getFechaNacimiento());
		
			if(personaRenapo.getActaNacimiento()!= null) {
				DatosPersonaRenapo renapo = new DatosPersonaRenapo();
				renapo.setFoja(personaRenapo.getActaNacimiento().getNoFoja());
				renapo.setFolio(personaRenapo.getActaNacimiento().getNoActa());
				renapo.setLibro(personaRenapo.getActaNacimiento().getNoLibro());
				renapo.setTomo(personaRenapo.getActaNacimiento().getTomo());
				renapo.setAnio(personaRenapo.getActaNacimiento().getAnio()+"");
				if(personaRenapo.getActaNacimiento().getMunicipio()!= null) {
					renapo.setMunicipioRegistro(personaRenapo.getActaNacimiento().getMunicipio().getNombre());
				}
				objPersonaBdtu.setDatosPersonaRenapo(renapo);
			}
			if(personaRenapo.getEstadoCivil() != null) {
				EstadoCivil edoCivil = new EstadoCivil();
				edoCivil.setIdEstadoCivil(personaRenapo.getEstadoCivil().getIdEstadoCivil());
				//edoCivil.setDescripcion(personaRenapo.getEstadoCivil().getDescripcion());
				objPersonaBdtu.setEstadoCivil(edoCivil);
			}
		if(personaRenapo.getLugarNacimiento() != null) {
			EntidadFederativa nacimiento = new EntidadFederativa();
			nacimiento.setClave(personaRenapo.getLugarNacimiento().getClave());
			//nacimiento.setClaveRenapo(personaRenapo.getLugarNacimiento().getClaveRenapo());
			//nacimiento.setNombre(personaRenapo.getLugarNacimiento().getNombre());
			objPersonaBdtu.setLugarNacimiento(nacimiento);
		}
		
		if(personaRenapo.getPais()!= null) {
			Pais pais = new Pais();
			//pais.setDescripcion(personaRenapo.getPais().getDescripcion());
			//pais.setNacionalidad(personaRenapo.getPais().getNacionalidad());
			pais.setIdPais(personaRenapo.getPais().getIdPais());
			objPersonaBdtu.setPais(pais);
		}
		
		if(personaRenapo.getSexo()!= null) {
			Sexo sexo = new Sexo();
			sexo.setIdSexo(personaRenapo.getSexo().getIdSexo().longValue());
			//sexo.setDescripcion(personaRenapo.getSexo().getDescripcion());
			objPersonaBdtu.setSexo(sexo);
		}
		return objPersonaBdtu;
		
	}
	
	public static DerechohabienteDTO setDatosDerechohabiente(GrupoFamiliar gpoFam ) throws Exception{
		try {
		DerechohabienteDTO derechohab = setDatosDerechohabiente(gpoFam.getDerechohabiente());	
		
		
		derechohab.setConsultorio(gpoFam.getMedicoEnTurno().getConsultorio().getDescripcion());
		derechohab.setCveIdAsignacionNssGrupoFamiliar(gpoFam.getAsignacionNSS().getIdAsignacionNSS());
		derechohab.setAgregadoAfiliacion(gpoFam.getAgregadoAfiliacion());
		derechohab.setAgregadoMedico(gpoFam.getAgregadoMedico());
		derechohab.setNssCabezaGrupoFamiliar(gpoFam.getAsignacionNSS().getNss());
		derechohab.setFechaInicioVigencia(gpoFam.getFechaInicioVigencia());
		derechohab.setFechaFinVigencia(gpoFam.getFechaFinVigencia());
		
		
		
		UnidadMedicaFamiliar umf = new UnidadMedicaFamiliar();
		umf.setNumUMF(gpoFam.getMedicoEnTurno().getUnidadMedicaFamiliar().getNoEconomico()+"");
		if(gpoFam.getMedicoEnTurno().getUnidadMedicaFamiliar().getClavePresupuestal() != null)
			umf.setCvePresupuestalUMF(gpoFam.getMedicoEnTurno().getUnidadMedicaFamiliar().getClavePresupuestal().getClavePresupuestal());
		
		umf.setDescUMF(gpoFam.getMedicoEnTurno().getUnidadMedicaFamiliar().getNombreCorto());
		derechohab.setUmf(umf);
		derechohab.setSubdelegacion(gpoFam.getMedicoEnTurno().getUnidadMedicaFamiliar().getSubdelegacion());
		
		
		Turno turno = new Turno();
		turno.setIdTurno(gpoFam.getMedicoEnTurno().getTurno().getIdTurno());
		turno.setDescripcion(gpoFam.getMedicoEnTurno().getTurno().getDescripcion());
		derechohab.setTurno(turno);
		Parentesco parentesco = new Parentesco();
		parentesco.setIdParentesco(gpoFam.getParentesco().getIdParentesco());
		parentesco.setDescripcion(gpoFam.getParentesco().getDescripcion());
		derechohab.setParentesco(parentesco);
		
		derechohab.setEstadoDerechohabiente(gpoFam.getEstadoDerechohabiente());
		derechohab.setSubEstadoDerechohabiente(gpoFam.getSubEstadoDerechohabiente());
		derechohab.setDomicilio(gpoFam.getDomicilio());
		return derechohab;
		}catch(Exception e) {
			e.printStackTrace();
			log.error("ocurio un error en le parse de grupo familiar a derechohabiente"  , e);
			throw e;
		}
		
	}
	
	public static List<DerechohabienteDTO> setDatosDerechohabienteList(List<GrupoFamiliar> gpoFamList ) throws Exception {
		
		if(gpoFamList != null && !gpoFamList.isEmpty()) {
			List<DerechohabienteDTO> lstDerechohabiente = new ArrayList<DerechohabienteDTO>();
			for(GrupoFamiliar registro: gpoFamList) {
				try {
					lstDerechohabiente.add(setDatosDerechohabiente(registro));
				}catch(Exception e) {
					System.out.println("ocurio un error en le parse de grupo familiar a derechohabiente"  + e);
					log.error("ocurio un error en le parse de grupo familiar a derechohabiente", e);
					throw e;
				}
			}
			return lstDerechohabiente;
		}else return null;
		
	}
	
	public static DerechohabienteSinolave setDatosDerechohabienteSinolave(AsignacionNSS asegurado, 
			MgtInfincasegvig vigenciaBDTU, GrupoFamiliar gpoFam) throws Exception, ServiciosRestException{
		DerechohabienteSinolave derechohab = new DerechohabienteSinolave();
		Persona derechohabPersona = new DerechohabienteSinolave();
		
		try {
			derechohabPersona =setDatosPersona(asegurado, derechohabPersona);
			derechohab = (DerechohabienteSinolave)derechohabPersona;
		}catch (Exception e) {
			log.error("ocurrio un erro en parser de los datos de la  persona setDatosDerechohabienteSinolave", e);
			throw e;
		}
	
			setDatosGrupoFamiliarSinolave(derechohab, gpoFam, vigenciaBDTU);
		if(StringUtils.isNotEmpty(vigenciaBDTU.getRegpatron())) {
			derechohab.setRegistroPatronal(vigenciaBDTU.getRegpatron());
			EstadoDerechohabiente estadoDerechohabiente = new EstadoDerechohabiente();
			estadoDerechohabiente.setIdEstadoDerechohabiente(EstadoDerechohabienteEnum.VIGENTE.getId());
			estadoDerechohabiente.setDescripcion(EstadoDerechohabienteEnum.VIGENTE.name());
			derechohab.setEstadoDerechohabiente(estadoDerechohabiente);
		}
			
		return derechohab;
	}
	
	public static void  setDatosGrupoFamiliarSinolave(DerechohabienteSinolave derechohab, GrupoFamiliar gpoFam,
			MgtInfincasegvig vigenciaBDTU) throws Exception, ServiciosRestException{
		try {
			if(gpoFam != null) {
				
				derechohab.setConsultorio(gpoFam.getMedicoEnTurno().getConsultorio().getDescripcion());
				derechohab.setCveIdAsignacionNssGrupoFamiliar(gpoFam.getAsignacionNSS().getIdAsignacionNSS());
				derechohab.setAgregadoAfiliacion(gpoFam.getAgregadoAfiliacion());
				derechohab.setAgregadoMedico(gpoFam.getAgregadoMedico());
				derechohab.setNssCabezaGrupoFamiliar(gpoFam.getAsignacionNSS().getNss());
				derechohab.setFechaInicioVigencia(gpoFam.getFechaInicioVigencia());
				derechohab.setFechaFinVigencia(gpoFam.getFechaFinVigencia());
				
				UnidadMedicaFamiliar umf = new UnidadMedicaFamiliar();
				umf.setNumUMF(gpoFam.getMedicoEnTurno().getUnidadMedicaFamiliar().getNoEconomico()+"");
				if(gpoFam.getMedicoEnTurno().getUnidadMedicaFamiliar().getClavePresupuestal() != null)
					umf.setCvePresupuestalUMF(gpoFam.getMedicoEnTurno().getUnidadMedicaFamiliar().getClavePresupuestal().getClavePresupuestal());
				umf.setDescUMF(gpoFam.getMedicoEnTurno().getUnidadMedicaFamiliar().getNombreCorto());
				derechohab.setUmf(umf);
				derechohab.setSubdelegacion(gpoFam.getMedicoEnTurno().getUnidadMedicaFamiliar().getSubdelegacion());
				Turno turno = new Turno();
				turno.setIdTurno(gpoFam.getMedicoEnTurno().getTurno().getIdTurno());
				turno.setDescripcion(gpoFam.getMedicoEnTurno().getTurno().getDescripcion());
				derechohab.setTurno(turno);
				Parentesco parentesco = new Parentesco();
				parentesco.setIdParentesco(gpoFam.getParentesco().getIdParentesco());
				parentesco.setDescripcion(gpoFam.getParentesco().getDescripcion());
				derechohab.setParentesco(parentesco);
				derechohab.setEstadoDerechohabiente(gpoFam.getEstadoDerechohabiente());
				derechohab.setSubEstadoDerechohabiente(gpoFam.getSubEstadoDerechohabiente());
				derechohab.setIdee(gpoFam.getDerechohabiente().getExpedienteElectronico());
			}else {
				setDatosGrupoFamiliarVigenciaBDTUSinolave(derechohab, vigenciaBDTU);
			}
			
		}catch (Exception e) {
			log.error("ocurrio en error en el parser de  setDatosGrupoFamiliarSinolave" ,e );
			throw e;
		}
	}
	
	public static void  setDatosGrupoFamiliarVigenciaBDTUSinolave(DerechohabienteSinolave derechohab, MgtInfincasegvig vigenciaBDTU) 
			throws Exception, ServiciosRestException{
		try {
			
			Delegacion delegacion = new Delegacion();
			Subdelegacion subdelegacion = new Subdelegacion();
			if(vigenciaBDTU.getCiz() != null) 
				delegacion.setCiz(vigenciaBDTU.getCiz().intValue());
			
			if(vigenciaBDTU.getCveDeleg() != null)
				delegacion.setClave(vigenciaBDTU.getCveDeleg()+"");
			else
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo409, ErrorResponseBean.codigo409Descripcion,
						"El asegurado no cuenta con delegacion  NSS :" +  derechohab.getNss() , 
						"El asegurado no cuenta con delegacion  NSS :" +  derechohab.getNss()));
			
			subdelegacion.setDelegacion(delegacion);
			
			if(vigenciaBDTU.getCveSubdeleg()!= null)	
				subdelegacion.setClave(vigenciaBDTU.getCveSubdeleg()+"");
			else
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo409, ErrorResponseBean.codigo409Descripcion,
					"El asegurado no cuenta con subdelegacion NSS :" +  derechohab.getNss() , 
					"El asegurado no cuenta con subdelegacion NSS :" +  derechohab.getNss()));
			
			derechohab.setSubdelegacion(subdelegacion);
			
			if(vigenciaBDTU.getCveUmf() != null) {
				UnidadMedicaFamiliar umf = new UnidadMedicaFamiliar();
				umf.setNumUMF(vigenciaBDTU.getCveUmf()+"");
				derechohab.setUmf(umf);
			}else
				throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo409, ErrorResponseBean.codigo409Descripcion,
						"El asegurado no cuenta con UMF NSS :" +  derechohab.getNss() , 
						"El asegurado no cuenta con UMF NSS :" +  derechohab.getNss()));
			
			Parentesco parentesco = new Parentesco();
			parentesco.setIdParentesco(ParentescoEnum.ASEGURADO.getId());
			parentesco.setDescripcion("ASEGURADO");
			derechohab.setParentesco(parentesco);
		}catch (ServiciosRestException e){
			throw e;
		}catch (Exception e) {
			log.error("ocurrio en error en el parser de  setDatosGrupoFamiliarSinolave" ,e );
			throw e;
		}
	}
	
	public static PersonaFisicaMoral setDatosPersonaFisicaCompleto(Fisica fisica)throws ServiciosRestException{
		PersonaFisicaMoral personaFM = null;
		try {
			if(fisica != null) {
				personaFM = new PersonaFisicaMoral();
				setDatosPersona(fisica, personaFM);
				personaFM.setCveIdPersonaFisicaMoral(fisica.getCveFisica());
				TipoPersona tipoPersona = new TipoPersona();
				tipoPersona.setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
				tipoPersona.setDescripcion(TipoPersonaEnum.FISICA.name());
				personaFM.setTipoPersona(tipoPersona);
			}
			return personaFM;
		}catch(Exception e) {
			log.error("ocurrio un error en el parser setDatosPersonsaFisica ", e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error en el parser setDatosPersonsaFisica  :" +  e.getMessage(), 
					"ocurrio un error en el parser setDatosPersonsaFisica  :" +  e.getMessage()));
		}
		
	}
	
	public static PersonaFisicaMoral setDatosPersonaMoral(Moral moral)throws ServiciosRestException{
		PersonaFisicaMoral personaFM = null;
		try {
			if(moral!= null) {
				personaFM = new PersonaFisicaMoral();
				personaFM.setRfc(moral.getRfc());
				personaFM.setNombre(moral.getRazonSocial());
				personaFM.setTipoSociedad(moral.getTipoSociedad());
				personaFM.setCveIdPersonaFisicaMoral(moral.getCveMoral());
				TipoPersona tipoPersona = new TipoPersona();
				tipoPersona.setIdTipoPersona(TipoPersonaEnum.MORAL.getId());
				tipoPersona.setDescripcion(TipoPersonaEnum.MORAL.name());
				personaFM.setTipoPersona(tipoPersona);
			}
			return personaFM;
		}catch(Exception e) {
			log.error("ocurrio un error en el parser setDatosPersonsaMoral ", e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error en el parser setDatosPersonsaMoral  :" +  e.getMessage(), 
					"ocurrio un error en el parser setDatosPersonsaMoral  :" +  e.getMessage()));
		}
		
	}
	
}
