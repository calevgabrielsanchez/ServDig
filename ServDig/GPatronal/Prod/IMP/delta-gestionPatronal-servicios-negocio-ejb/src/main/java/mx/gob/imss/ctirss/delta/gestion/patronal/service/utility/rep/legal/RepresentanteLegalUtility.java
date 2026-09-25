package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.rep.legal;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.ServiceUtility;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Facultad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoFacultad;
import mx.gob.imss.ctirss.delta.persistence.DicFacultad;
import mx.gob.imss.ctirss.delta.persistence.DicTipoFacultad;
import mx.gob.imss.ctirss.delta.persistence.DicTipoPoder;
import mx.gob.imss.ctirss.delta.persistence.DitFormaContacto;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;
import mx.gob.imss.ctirss.delta.persistence.DitPersonafContacto;
import mx.gob.imss.ctirss.delta.persistence.DitRepresentanteLegal;
import mx.gob.imss.ctirss.delta.persistence.DitRepresentanteLegalContac;
import mx.gob.imss.ctirss.delta.persistence.DitRlFacultad;
import mx.gob.imss.ctirss.delta.persistence.DitTipoContacto;

@Stateless
public class RepresentanteLegalUtility extends ServiceUtility implements
		RepresentanteLegalUtilityLocal {

	@EJB SujetoObligadoUtilityLocal sujetoObligadoUtilityLocal;
	
	@Override
	public DitRepresentanteLegal convertirModelToEntity(RepresentanteLegal model){
		DitRepresentanteLegal entity = new DitRepresentanteLegal();
		DitPatronSujetoObligado ditPatronSujetoObligado = new DitPatronSujetoObligado();
		
		DitPersona ditPersona = new DitPersona();
		
		if (model.getCveIdRepresentanteLegal() != null) {
			entity.setCveIdRepresentanteLegal(model.getCveIdRepresentanteLegal().longValue());
		}
		 
		
		if (model.getCveIdPatronSujetoObligado() != null && model.getCveIdPatronSujetoObligado() > 0){
			ditPatronSujetoObligado.setCveIdPatronSujetoObligado(model.getCveIdPatronSujetoObligado().longValue());
			entity.setDitPatronSujetoObligado(ditPatronSujetoObligado);
		}
		if (model.getPersonaFisica() != null){
			// es el id persona de la persona fisica para este rep legal, no del representado
			//ditPersona.setCveIdPersona(model.getCveIdPersona().longValue());
			ditPersona.setCveIdPersona(model.getPersonaFisica().getIdPersona());
		}
		
		if(model.getCveIdPersona()!=null){
			if(model.getTipoPersonaRepresentada().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)){
				DitPersonaFisica ditPersonaFisicaRepresentada = new DitPersonaFisica();
				DitPersona persona = new DitPersona();
				persona.setCveIdPersona(model.getCveIdPersona());
				ditPersonaFisicaRepresentada.setDitPersona(ditPersona);
				entity.setDitPersonaFisicaRepresentada(ditPersonaFisicaRepresentada);
			}else{
				DitPersonaMoral ditPersonaMoralRepresentada = new DitPersonaMoral();
				ditPersonaMoralRepresentada.setCveIdPersonaMoral(model.getCveIdPersona());
				entity.setDitPersonaMoralRepresentada(ditPersonaMoralRepresentada);
			}
			
		}
		
		
		
		entity.setDitPersona(ditPersona); 
		entity.setFecRegistroAlta(model.getFecRegistroAlta());
		entity.setFecRegistroActualizado(model.getFecRegistroActualizado());
		entity.setFecRegistroBaja(model.getFecRegistroBaja());
		entity.setIndActAdmonDominio(model.getIndActAdmonDominio());
		// datos de contacto estan relacionados a la persona
		if(model.getCveIdTipoPoder()!=null){
			entity.setDicTipoPoder(new DicTipoPoder());
			entity.getDicTipoPoder().setCveIdTipoPoder(model.getCveIdTipoPoder());
		}
		return entity;
	}

	
	
	@Override
	public RepresentanteLegal enityToModelDatosBasicos(
			DitRepresentanteLegal rL) {
		
		RepresentanteLegal representante = new RepresentanteLegal();
		if(rL.getDitPatronSujetoObligado()!=null)
			representante.setSujetoObligado(sujetoObligadoUtilityLocal.convertirEntityToModelDatosPersona(rL.getDitPatronSujetoObligado(), null));
		representante.setCveIdRepresentanteLegal(rL.getCveIdRepresentanteLegal());
		representante.setFecRegistroAlta(rL.getFecRegistroAlta());
		representante.setFecRegistroActualizado(rL.getFecRegistroActualizado());
		representante.setFecRegistroBaja(rL.getFecRegistroBaja());
		representante.setIndActAdmonDominio(rL.getIndActAdmonDominio());
		
		DitPersona ditPersona = rL.getDitPersona();
		Fisica personaFisica = new Fisica();
		personaFisica.setIdPersona(ditPersona.getCveIdPersona());
		personaFisica.setNombre(ditPersona.getNomNombre());
		personaFisica.setPrimerApellido(ditPersona.getNomPrimerApellido());
		personaFisica.setSegundoApellido(ditPersona.getNomSegundoApellido());
		personaFisica.setRfc(ditPersona.getRfc());
		personaFisica.setCurp(ditPersona.getCurp());
		
		representante.setPersonaFisica(personaFisica);
		
		return representante;
	}



	@Override
	public RepresentanteLegal convertirEntityToModel(
			DitRepresentanteLegal entity) {
		 
		RepresentanteLegal model = new RepresentanteLegal();
		Fisica personaFisica = new Fisica();
		TelefonoFijo telefonoFijo = new TelefonoFijo();
		TelefonoMovil telefonoMovil = new TelefonoMovil();
		CorreoElectronico correoElectronico = new CorreoElectronico();

		DitPersona ditPersona = entity.getDitPersona();
		DitFormaContacto ditFormaContacto = new DitFormaContacto();
		DitTipoContacto ditTipoContacto = new DitTipoContacto();
		
		
		List<MedioContacto> medioContactoList = new ArrayList<MedioContacto>();
		
		personaFisica.setIdPersona(ditPersona.getCveIdPersona());
		personaFisica.setNombre(ditPersona.getNomNombre());
		personaFisica.setPrimerApellido(ditPersona.getNomPrimerApellido());
		personaFisica.setSegundoApellido(ditPersona.getNomSegundoApellido());
		personaFisica.setRfc(ditPersona.getRfc());
		personaFisica.setCurp(ditPersona.getCurp());
		
		personaFisica.setTelefonoMovil(telefonoMovil);
		personaFisica.setCorreoElectronico(correoElectronico);
		personaFisica.setTelefonoFijo(telefonoFijo);
		
		if(entity.getDitRepresentanteLegalContacs()!=null)
			for(DitRepresentanteLegalContac contacto :entity.getDitRepresentanteLegalContacs()){
				MedioContacto medio = new MedioContacto();
				TipoMedioContacto tipoMedioContacto = new TipoMedioContacto();
				tipoMedioContacto.setIdTipoMedioContacto(contacto.getDitFormaContacto().getDitTipoContacto().getCveIdTipoContacto());
				tipoMedioContacto.setDescripcion(contacto.getDitFormaContacto().getDitTipoContacto().getDesTipoContacto());
				medio.setTipoMedioContacto(tipoMedioContacto);
				medio.setClave(contacto.getDitFormaContacto().getCveIdFormaContacto());
				medio.setDesFormaContacto(contacto.getDitFormaContacto().getDesFormaContacto());
				medioContactoList.add(medio);
			}
				
		model.setMediosContacto(medioContactoList);

		if (entity.getDicMandato() != null) {
			model.setCveIdMandato(entity.getDicMandato().getCveIdMandato());
		}

		if (entity.getDitPatronSujetoObligado() != null) {
			model.setSujetoObligado(sujetoObligadoUtilityLocal.convertirEntityToModel(entity.getDitPatronSujetoObligado(), null));
		}

		if (entity.getDitPersona() != null) {
			model.setCveIdPersona(entity.getDitPersona().getCveIdPersona());
		}

		if (entity.getDicTipoPoder() != null) {
			model.setCveIdTipoPoder(entity.getDicTipoPoder()
					.getCveIdTipoPoder());
		}

		model.setCveIdRepresentanteLegal(entity.getCveIdRepresentanteLegal());
		model.setFecRegistroActualizado(entity.getFecRegistroActualizado());
		model.setFecRegistroAlta(entity.getFecRegistroAlta());
		model.setFecRegistroBaja(entity.getFecRegistroBaja());
		model.setIndActAdmonDominio(entity.getIndActAdmonDominio());
		model.setIndEstatus(entity.getIndEstatus());
		model.setRupa(entity.getRupa());		
		model.setPersonaFisica(personaFisica);

		List<Facultad> facultades = Collections.emptyList();
		
		
		if (entity.getDitRlFacultads()!=null && entity.getDitRlFacultads().size() > 0) {
			facultades = new ArrayList<Facultad>();
			for (DitRlFacultad rlFac : entity.getDitRlFacultads()) {
				facultades.add(convertirEntityToModelFacultad(rlFac
						.getDicFacultad()));
			}
		}
		model.setFacultades(facultades);
		
		System.err.println("ditFormaContacto:  " + ditFormaContacto.getDesFormaContacto() + ", ditTipoContacto [des esctructura, des mascara, des tipo contacto]: {" + ditTipoContacto.getDesEstructura() + '-' +ditTipoContacto.getDesMascara() + '-' + ditTipoContacto.getDesTipoContacto() + '}');

		return model;
	}

	@Override
	public List<RepresentanteLegal> convertListOfEntitiesToListOfModel(
			List<DitRepresentanteLegal> origen){
		
		List<RepresentanteLegal> models = new ArrayList<RepresentanteLegal>();
		
		try {			
			if(origen!=null)
				for (DitRepresentanteLegal ditRepresentanteLegal : origen)
					models.add(this.convertirEntityToModel(ditRepresentanteLegal));

		} catch (Exception e) {
			log.error(e.getMessage() , e);
			
		}
		
		return models;
	}

	@Override
	public List<DitRepresentanteLegal> convertListOfModelToListOfEntity(
			List<RepresentanteLegal> origen) throws Exception {

		throw new Exception(">>> Método no implementado aún!.");
	}

	@Override
	public RepresentanteLegal convertirEntityToModelWithPersona(
			DitRepresentanteLegal entity1, DitPersona entity2) throws Exception {
		
		RepresentanteLegal model = new RepresentanteLegal();
		List<DitPersonafContacto> ditPersonafContactos = entity2.getDitPersonafContactos();
		
		System.err.println("num regs. en persona fisica contactos: " + ditPersonafContactos.size());
		
		for (DitPersonafContacto ditPersonafContacto : ditPersonafContactos) {
			DitFormaContacto ditFormaContacto = ditPersonafContacto.getDitFormaContacto();
			DitTipoContacto ditTipoContacto = ditFormaContacto.getDitTipoContacto();
			
			System.err.println("ditFormaContacto:  " + ditFormaContacto.getDesFormaContacto() + ", ditTipoContacto [des esctructura, des mascara, des tipo contacto]: {" + ditTipoContacto.getDesEstructura() + '-' +ditTipoContacto.getDesMascara() + '-' + ditTipoContacto.getDesTipoContacto() + '}');
			
			
			
		}
		
		try {
			this.copyBeans(entity1, model);
			
			Fisica fisica = new Fisica();
			fisica.setRfc(entity2.getRfc());
			fisica.setCurp(entity2.getCurp());
			fisica.setNombre(entity2.getNomNombre() + ' ' + entity2.getNomPrimerApellido() + ' ' + entity2.getNomSegundoApellido());
			
			model.setPersonaFisica(fisica);
			
			
			/*model.setNomNombre(entity2.getNomNombre());
			model.setNomPrimerApellido(entity2.getNomPrimerApellido());
			model.setNomSegundoApellido(entity2.getNomSegundoApellido());
			model.setRfc(entity2.getRfc());
			model.setCurp(entity2.getCurp());*/
			
			
		} catch (IllegalAccessException e) {
			log.error(e.getMessage() , e);
			throw new Exception(e.getMessage());
		} catch (InvocationTargetException e) {
			log.error(e.getMessage() , e);
			throw new Exception(e.getMessage());
		}
		return model;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.rep.legal.RepresentanteLegalUtilityLocal#convertirEntityToModelFacultad(mx.gob.imss.ctirss.delta.persistence.DicFacultad)
	 */
	@Override
	public Facultad convertirEntityToModelFacultad(DicFacultad entity) {
		Facultad model = new Facultad();
		model.setClave(entity.getCveIdFacultad());
		model.setDescripcion(entity.getDesFacultad());
		model.setTipoFacultad(convertirEntityToModelTipoFacultad(entity.getDicTipoFacultad()));
		return model;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.rep.legal.RepresentanteLegalUtilityLocal#convertirEntityToModelTipoFacultad(mx.gob.imss.ctirss.delta.persistence.DicTipoFacultad)
	 */
	@Override
	public TipoFacultad convertirEntityToModelTipoFacultad(
			DicTipoFacultad entity) {
		TipoFacultad model = new TipoFacultad();
		model.setClave(entity.getCveIdTipoFacultad());
		model.setDescripcion(entity.getDesTipoFacultad());
		return model;
	}

	/**
	 * Asigna los valores de las propiedades a actualizar (segun caso de uso correspondiente) al objeto previamente cargado, así evitamos perder datos y 
	 * actualizamos solamente lo requerido.
	 */
	@Override
	public DitRepresentanteLegal asignarValoresParaActualizar(
			RepresentanteLegal representanteLegal,
			DitRepresentanteLegal ditRepresentanteLegal) throws Exception {
		
		//DitPersona ditPersona = ditRepresentanteLegal.getDitPersona();
		ditRepresentanteLegal.getDitPersona();
		
		
		ditRepresentanteLegal.setIndActAdmonDominio(representanteLegal.getIndActAdmonDominio());
		// este dato está como prueba y deberá ser eliminado
		ditRepresentanteLegal.setRupa(representanteLegal.getRupa());
		
		
		return ditRepresentanteLegal;
	}



	@Override
	public RepresentanteLegal entityToModelDatosBasicosRepresentanteYRepresentado(
			DitRepresentanteLegal entity) {
		RepresentanteLegal representante = new RepresentanteLegal();
		if(entity.getDitPatronSujetoObligado()!=null)
			representante.setSujetoObligado(sujetoObligadoUtilityLocal.convertirEntityToModelDatosPersona(entity.getDitPatronSujetoObligado(), null));
		representante.setCveIdRepresentanteLegal(entity.getCveIdRepresentanteLegal());
		representante.setFecRegistroAlta(entity.getFecRegistroAlta());
		representante.setFecRegistroActualizado(entity.getFecRegistroActualizado());
		representante.setFecRegistroBaja(entity.getFecRegistroBaja());
		representante.setIndActAdmonDominio(entity.getIndActAdmonDominio());
		
		DitPersona ditPersona = entity.getDitPersona();
		Fisica personaFisica = new Fisica();
		personaFisica.setIdPersona(ditPersona.getCveIdPersona());
		personaFisica.setNombre(ditPersona.getNomNombre());
		personaFisica.setPrimerApellido(ditPersona.getNomPrimerApellido());
		personaFisica.setSegundoApellido(ditPersona.getNomSegundoApellido());
		personaFisica.setRfc(ditPersona.getRfc());
		personaFisica.setCurp(ditPersona.getCurp());
		
		representante.setPersonaFisica(personaFisica);
		
		if(entity.getDitPersonaFisicaRepresentada()!=null){
			Fisica fisica = convertirDatosBasicoRepresentadoFisico(entity.getDitPersonaFisicaRepresentada());
			representante.setPersonaFisicaRepresentada(fisica);
			TipoPersona tipoPersonaRepresentada = new TipoPersona();
			tipoPersonaRepresentada.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
			representante.setTipoPersonaRepresentada(tipoPersonaRepresentada);
		}else if(entity.getDitPersonaMoralRepresentada()!=null){
			Moral moral = convertirDatosBasicoRepresentadoMoral(entity.getDitPersonaMoralRepresentada());
			representante.setPersonaMoralRepresentada(moral);
			TipoPersona tipoPersonaRepresentada = new TipoPersona();
			tipoPersonaRepresentada.setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
			representante.setTipoPersonaRepresentada(tipoPersonaRepresentada);
		}
		
		
		return representante;

	}
	
	private Fisica convertirDatosBasicoRepresentadoFisico(DitPersonaFisica entity){
		Fisica fisica = new Fisica();
		DitPersona ditPersona = entity.getDitPersona();
		fisica.setIdPersona(ditPersona.getCveIdPersona());
		fisica.setCveFisica(entity.getCveIdPersonaFisica());
		fisica.setNombre(ditPersona.getNomNombre());
		fisica.setPrimerApellido(ditPersona.getNomPrimerApellido());
		fisica.setSegundoApellido(ditPersona.getNomSegundoApellido());
		fisica.setRfc(ditPersona.getRfc());
		fisica.setCurp(ditPersona.getCurp());
		return fisica;
	}
	
	private Moral convertirDatosBasicoRepresentadoMoral(DitPersonaMoral entity){
		Moral moral = new Moral();
		moral.setIdPersona(entity.getCveIdPersonaMoral());
		moral.setRazonSocial(entity.getDenominacionRazonSocial());
		moral.setRfc(entity.getRfc());
		return moral;
	}
}
