package mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.utility;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.asegurado.Asegurado;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.EstadoCivil;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.persistence.DgCatEstado;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoCivil;
import mx.gob.imss.ctirss.delta.persistence.DicModalidad;
import mx.gob.imss.ctirss.delta.persistence.DicSexo;
import mx.gob.imss.ctirss.delta.persistence.DitAsegurado;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNss;
import mx.gob.imss.ctirss.delta.persistence.DitPatronGeneral;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;

@Stateless
public class AseguradoUtility extends AbstractServiceUtility implements
		AseguradoUtilityLocal {

	@Override
	public Asegurado convertirEntityToModel(DitAsegurado ditAsegurado) {
		Asegurado asegurado = null;
		
		if(ditAsegurado!=null){
			asegurado = new Asegurado();
			
			asegurado.setAsignacionNSS(convertirAsignacionNSSEntityToModel(
					ditAsegurado.getDitAsignacionNss()));
			asegurado.setIdAsegurado(ditAsegurado.getCveIdAsegurado());
			asegurado.setFechaBaja(ditAsegurado.getFecBaja());
			asegurado.setFechaAlta(ditAsegurado.getFecAlta());
			asegurado.setSujetoObligado(convertirSujetoObligadoEntityToModel(
					ditAsegurado.getDitPatronSujetoObligado()));
		}
		
		return asegurado;
	}

	public AsignacionNSS convertirAsignacionNSSEntityToModel(
			DitAsignacionNss ditAsignacionNss) {
		AsignacionNSS asignacionNSS = null;

		if (ditAsignacionNss != null) {
			asignacionNSS = new AsignacionNSS();

			asignacionNSS.setIdAsignacionNSS(ditAsignacionNss.getCveIdAsignacionNss());
			asignacionNSS.setNssStr(ditAsignacionNss.getNumNss());
			asignacionNSS.setNss(ditAsignacionNss.getNumNss());

			DitPersona persona = ditAsignacionNss.getDitPersona();
			asignacionNSS.setNombre(persona.getNomNombre());
			asignacionNSS.setPrimerApellido(persona.getNomPrimerApellido());
			asignacionNSS.setSegundoApellido(persona.getNomSegundoApellido());
			asignacionNSS.setIdPersona(persona.getCveIdPersona());
			asignacionNSS.setCurp(persona.getCurp());
			asignacionNSS.setFechaNacimiento(persona.getFecNacimiento());
			asignacionNSS.setRfc(persona.getRfc());
			asignacionNSS.setAnioRegistroNac(persona.getNumAnioNacReg());
			asignacionNSS.setMesRegistroNac(persona.getNumMesNacReg());

			DicSexo dicSexo = persona.getDicSexo();
			if (dicSexo != null) {
				Sexo sexo = new Sexo();

				sexo.setIdSexo(dicSexo.getCveIdSexo().intValue());
				sexo.setDescripcion(dicSexo.getDesSexo());
				asignacionNSS.setSexo(sexo);
			}

			DicEstadoCivil dicEstadoCivil = persona.getDicEstadoCivil();
			if (dicEstadoCivil != null) {
				EstadoCivil estadoCivil = new EstadoCivil();

				estadoCivil.setIdEstadoCivil(dicEstadoCivil.getCveIdEstadoCivil().intValue());
				estadoCivil.setDescripcion(dicEstadoCivil.getDesEstadoCivil());
				asignacionNSS.setEstadoCivil(estadoCivil);
			}
			
			TipoPersona objTipoPersona = new TipoPersona();
			objTipoPersona.setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
			
			asignacionNSS.setTipoPersona(objTipoPersona);

			DgCatEstado dgCatEstado = persona.getDgCatEstado();
			if (dgCatEstado != null) {
				EntidadFederativa entidadFederativa = new EntidadFederativa();

				entidadFederativa.setClave(dgCatEstado.getCveEnt());
				entidadFederativa.setNombre(dgCatEstado.getNomEnt());
				asignacionNSS.setLugarNacimiento(entidadFederativa);
			}
		}
		
		return asignacionNSS;
	}

	private SujetoObligado convertirSujetoObligadoEntityToModel(
			DitPatronSujetoObligado ditPatronSujetoObligado) {
		SujetoObligado sujetoObligado = null;

		if (ditPatronSujetoObligado != null) {
			sujetoObligado = new SujetoObligado();

			sujetoObligado.setCveIdSujetoObligado(ditPatronSujetoObligado
					.getCveIdPatronSujetoObligado());
			sujetoObligado.setFechaAlta(ditPatronSujetoObligado
					.getFecRegistroAlta());

			if (ditPatronSujetoObligado.getDitPatronGenerals() != null
					&& ditPatronSujetoObligado.getDitPatronGenerals().size() > 0) {
				for (DitPatronGeneral registroPatronal : ditPatronSujetoObligado
						.getDitPatronGenerals()) {
					sujetoObligado.setNumeroRegistroPatronal(registroPatronal
							.getRegPatron());
				}
			}

			if (ditPatronSujetoObligado.getDitPersonaFisica() != null)
				sujetoObligado.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
			if (ditPatronSujetoObligado.getDitPersonaMoral() != null)
				sujetoObligado.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);

			DicModalidad dicModalidad = ditPatronSujetoObligado
					.getDicModalidad();
			if (dicModalidad != null) {
				Modalidad modalidad = new Modalidad();

				modalidad.setIdModalidad(dicModalidad.getCveIdModalidad());
				modalidad.setDescripcion(dicModalidad.getDesModalidad());
				modalidad.setNumModalidad(dicModalidad.getNumModalidad());
				sujetoObligado.setModalidad(modalidad);
			}
		}

		return sujetoObligado;
	}
	
	@Override
	public DitAsegurado convertirModelToEntity(Asegurado asegurado) {
		DitAsegurado ditAsegurado = null;

		if (asegurado != null) {
			ditAsegurado = new DitAsegurado();

			ditAsegurado.setDitAsignacionNss(convertirAsignacionNSSModelToEntity(
					asegurado.getAsignacionNSS()));
			if (asegurado.getIdAsegurado() != null) {
				ditAsegurado.setCveIdAsegurado(asegurado.getIdAsegurado());
			}
			ditAsegurado.setDitPatronSujetoObligado(convertirSujetoObligadoModelToEntity(
					asegurado.getSujetoObligado()));
			ditAsegurado.setFecAlta(asegurado.getFechaAlta());
			ditAsegurado.setFecRegistroAlta(asegurado.getFechaAlta());
			ditAsegurado.setFecBaja(asegurado.getFechaBaja());
			ditAsegurado.setFecRegistroBaja(asegurado.getFechaBaja());
		}

		return ditAsegurado;
	}

	public DitAsignacionNss convertirAsignacionNSSModelToEntity(
			AsignacionNSS asignacionNSS) {
		DitAsignacionNss ditAsignacionNss = null;

		if (asignacionNSS != null) {
			ditAsignacionNss = new DitAsignacionNss();

			ditAsignacionNss.setCveIdAsignacionNss(asignacionNSS.getIdAsignacionNSS());

			DitPersona ditPersona = new DitPersona();
			ditPersona.setCveIdPersona(asignacionNSS.getIdPersona());
			ditPersona.setNomNombre(asignacionNSS.getNombre());
			ditPersona.setNomPrimerApellido(asignacionNSS.getPrimerApellido());
			ditPersona.setNomSegundoApellido(asignacionNSS.getSegundoApellido());
			ditAsignacionNss.setDitPersona(ditPersona);
		}

		return ditAsignacionNss;
	}

	private DitPatronSujetoObligado convertirSujetoObligadoModelToEntity(
			SujetoObligado sujetoObligado) {
		DitPatronSujetoObligado ditPatronSujetoObligado = null;

		if (sujetoObligado != null) {
			ditPatronSujetoObligado = new DitPatronSujetoObligado();

			ditPatronSujetoObligado.setCveIdPatronSujetoObligado(
					sujetoObligado.getCveIdSujetoObligado());
			ditPatronSujetoObligado.setFecRegistroAlta(
					sujetoObligado.getFechaAlta());

			Modalidad modalidad = sujetoObligado.getModalidad();
			if (modalidad != null) {
				DicModalidad dicModalidad = new DicModalidad();

				dicModalidad.setCveIdModalidad(modalidad.getIdModalidad());
				dicModalidad.setDesModalidad(modalidad.getDescripcion());
				ditPatronSujetoObligado.setDicModalidad(dicModalidad);
			}
		}

		return ditPatronSujetoObligado;
	}
}
