package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.domicilio.MunicipioIMSS;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EscrituraConstitutiva;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RegistroSindicato;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSociedad;
import mx.gob.imss.ctirss.delta.persistence.DicDelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicModalidad;
import mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicTipoSociedad;
import mx.gob.imss.ctirss.delta.persistence.DitFormaContacto;
import mx.gob.imss.ctirss.delta.persistence.DitMunicipioPatSujOblig;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;
import mx.gob.imss.ctirss.delta.persistence.DitPersonafContacto;
import mx.gob.imss.ctirss.delta.persistence.DitPersonamContacto;
import mx.gob.imss.ctirss.delta.persistence.PatronesTempInc;

@Local
public interface SujetoObligadoUtilityLocal {
	
	/**
	 * 
	 * @author Hugo Armando Mart�nez Cham�nica
	 * @param entity
	 * @param tipoPersonaFiscal
	 * @return
	 * SujetoObligado
	 */
	SujetoObligado convertirEntityToModel(DitPatronSujetoObligado entity, TipoPersonaFiscal tipoPersonaFiscal);
	SujetoObligado convertirEntityToModelDictamen(DitPatronSujetoObligado entity, TipoPersonaFiscal tipoPersonaFiscal);
    SujetoObligado convertirEntityToModelBasic(DitPatronSujetoObligado entity, TipoPersonaFiscal tipoPersonaFiscal);
	SujetoObligado convertirEntityToModelDatosPersona(DitPatronSujetoObligado entity, TipoPersonaFiscal tipoPersonaFiscal);
	SujetoObligado convertirEntityToModelSinPersona(DitPatronSujetoObligado entity, PatronesTempInc patronesTempInc);
	SujetoObligado llenarDatosPersonaMoral(SujetoObligado sujetoObligado, EscrituraConstitutiva escrituraConstitutiva, RegistroSindicato registroSindicato);
	
	/**
	 * 
	 * @param entity
	 * @param tipoPersonaFiscal
	 * @return
	 */
	SujetoObligado convertirEntityToModelBasicDictamen(DitPatronSujetoObligado entity, TipoPersonaFiscal tipoPersonaFiscal);
	
		
	/**
	 * 
	 * @author Hugo Armando Mart�nez Cham�nica
	 * @param domicilio
	 * @return
	 * DomicilioFiscal
	 */
	DomicilioFiscal convertDomicilioToDomicilioFiscal(Domicilio domicilio);
	
	/**
	 * 
	 * @author Hugo Armando Mart�nez Cham�nica
	 * @param medioContacto
	 * @param persona
	 * @return
	 * DitPersonafContacto
	 */
	DitPersonafContacto construirPersonaFisicaContactoEntity(MedioContacto medioContacto, Persona persona);
	
	/**
	 * 
	 * @author Hugo Armando Mart�nez Cham�nica
	 * @param medioContacto
	 * @param persona
	 * @return
	 * DitPersonamContacto
	 */
	DitPersonamContacto construirPersonaMoralContactoEntity(MedioContacto medioContacto, Persona persona);
	
	/**
	 * 
	 * @author Hugo Armando Mart�nez Cham�nica
	 * @param entity
	 * @return Subdelegacion
	 */
	Subdelegacion convertirEntityToModelSubdelegacion(DicSubdelegacion entity);
	
	/**
	 * 
	 * @author Hugo Armando Mart�nez Cham�nica
	 * @param entity
	 * @return Subdelegacion
	 */
	Delegacion convertirEntityToModelDelegacion(DicDelegacion entity);
	
	/**
	 * 
	 * @author Hugo Armando Mart�nez Cham�nica
	 * @param entity
	 * @return
	 * Persona
	 */
	Persona convertirEntityToModelPersona(DitPersona entity);
	
	Fisica convertirDitPersonaToModelFisica(List<DitPersona> ditPersonaL);
	
	/**
	 * 
	 * @author Hugo Armando Mart�nez Cham�nica
	 * @param domicilio
	 * @return CentroTrabajo
	 */
	CentroTrabajo convertirDomicilioACentroTrabajo(Domicilio domicilio);
	
	/**
	 * Popula la siguiente informaci�n de la persona f�sica:
	 * IdPersona, Nombre, Primer Apellido, Segundo Apellido
	 * RFC y Nombre Comercial
	 * @author Hugo Armando Mart�nez Cham�nica
	 * @param personaFisica
	 * @return Fisica
	 */
	Fisica convertirEntityToModelPersonaFisica(
			DitPersonaFisica personaFisica);

    Fisica convertirEntityToModelPersonaFisicaBasic(DitPersonaFisica personaFisica);
	
	/**
	 * Popula la siguiente informaci�n de la persona Moral:
	 * IdPersonaMoral, Nombre Comercial, RFC, Raz�n Social
	 * y Tipo de Sociedad
	 * @author Hugo Armando Mart�nez Cham�nica
	 * @param personaMoral
	 * @return Moral
	 */
	
	Moral convertirEntityToModelPersonaMoral(
			DitPersonaMoral personaMoral);
	
	Modalidad convertirEntityToModelModalidad(DicModalidad entity);
	
	TipoSociedad convertirEntityToModelTipoSociedad(DicTipoSociedad entity);
	
	MunicipioIMSS convertiyEntityToModelMunicipioImss(DitMunicipioPatSujOblig dicMunicipio);
	
	MedioContacto convertirEntityToModelContacto(DitFormaContacto entity);

	/**
	 * @param personaFisica
	 * @return Fisica
	 */
	Fisica convertirEntityToModelPFDatosBasicos(DitPersonaFisica personaFisica);
	
	/**
	 * @param personaMoral
	 * @return Moral
	 */
	Moral convertirEntityToModelPMDatosBasicos(DitPersonaMoral personaMoral);
	
}
