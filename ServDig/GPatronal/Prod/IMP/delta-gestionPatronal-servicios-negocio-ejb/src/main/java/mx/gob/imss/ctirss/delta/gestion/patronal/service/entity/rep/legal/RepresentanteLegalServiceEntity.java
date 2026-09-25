package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.rep.legal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

import mx.gob.imss.ctirss.delta.exception.gestion.patronal.RepresentanteLegalInvalidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.patronal.RepresentanteLegalLimiteMinRegExcedidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.patronal.RepresentanteLegalYaExisteException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.RegistrarMedioContactoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.exception.ParametrosInvalidosException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.CalificacionesPersonaBusinessServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.SujetoObligadoServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.personas.gp.PersonasGPServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.rep.legal.RepresentanteLegalUtilityLocal;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DicTipoPoder;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;
import mx.gob.imss.ctirss.delta.persistence.DitRepresentanteLegal;
import mx.gob.imss.ctirss.delta.persistence.DitRepresentanteLegalContac;
import mx.gob.imss.ctirss.delta.persistence.DitRepresentanteLegalContacPK;

@Stateless(name="representanteLegalServiceEntity", mappedName="representanteLegalServiceEntity")
public class RepresentanteLegalServiceEntity extends AbstractServiceEntity
		implements RepresentanteLegalServiceEntityLocal {
	
	@EJB
	private RepresentanteLegalUtilityLocal representanteLegalUtility;
	
	@EJB
	private SujetoObligadoUtilityLocal sujetoObligadoUtilityLocal;
	
	@EJB
	private SujetoObligadoServiceEntityLocal sujetoObligadoServiceEntity;
		
	@EJB
	private MediosContactoServiceBusinessRemote mediosContactoService;
	
	@EJB
	private PersonasGPServiceEntityLocal personasGPServiceEntity;
	
	@EJB
	private CalificacionesPersonaBusinessServiceRemote calificacionesPersonaBusinessService;


	@Override
	public DatosSalidaPaginador<RepresentanteLegal> paginar(
			DatosEntradaPaginador<RepresentanteLegal> params) {
		DatosSalidaPaginador<RepresentanteLegal> response = new DatosSalidaPaginador<RepresentanteLegal>();
		ejecutarConsulta(params, response);
		return response;
	}

	@SuppressWarnings("unchecked")
	private void ejecutarConsulta(
			DatosEntradaPaginador<RepresentanteLegal> params, DatosSalidaPaginador<RepresentanteLegal> response) {
		
		List<RepresentanteLegal> representanteLegalList = new ArrayList<RepresentanteLegal>();
		RepresentanteLegal param = params.getModelo();
		
		StringBuffer strBfrSujObligMuestra = new StringBuffer();
		if(param.getTipoPersonaRepresentada().getIdTipoPersona().equals(TipoPersonaEnum.FISICA.getId())){
			strBfrSujObligMuestra.append("select patron.cveIdPatronSujetoObligado from DitPatronSujetoObligado patron where patron.ditPersonaFisica.ditPersona.cveIdPersona = :cveIdPersona");
		}else if(param.getTipoPersonaRepresentada().getIdTipoPersona().equals(TipoPersonaEnum.MORAL.getId())){
			strBfrSujObligMuestra.append("select patron.cveIdPatronSujetoObligado from DitPatronSujetoObligado patron where patron.ditPersonaMoral.cveIdPersonaMoral = :cveIdPersona");
		}
		Query queryPatron = this.em.createQuery(strBfrSujObligMuestra.toString());
		queryPatron.setParameter("cveIdPersona", param.getCveIdPersona());
		
		List<Long> idsPatron = queryPatron.getResultList();
		Long cveIdPatronSujetoObligado = null;
		if(idsPatron!=null &&  idsPatron.size()>0){
			cveIdPatronSujetoObligado = idsPatron.get(0);
		}
		Integer totalresult = 0;
		
		if(cveIdPatronSujetoObligado!=null){
			StringBuffer strBfr = new StringBuffer();
			strBfr.append("select ditRepLegal from DitRepresentanteLegal ditRepLegal ");
			strBfr.append("where ditRepLegal.fecRegistroBaja is null and ditRepLegal.ditPatronSujetoObligado.cveIdPatronSujetoObligado = :cveIdPatronSujetoObligado");
					  
			Query query = this.em.createQuery(strBfr.toString());
			Query queryTotal = this.em.createQuery(strBfr.toString());
			
			query.setParameter("cveIdPatronSujetoObligado", cveIdPatronSujetoObligado);
			queryTotal.setParameter("cveIdPatronSujetoObligado", cveIdPatronSujetoObligado);
			
			List<DitRepresentanteLegal> ditRepresentanteLegalTotalList = queryTotal.getResultList();
			
			totalresult = ditRepresentanteLegalTotalList.size();
			
			query.setFirstResult(params.getiDisplayStart());
			query.setMaxResults(params.getiDisplayLength());
			
			List<DitRepresentanteLegal> ditRepresentanteLegalList = query.getResultList();
			
			for (DitRepresentanteLegal ditRepresentanteLegal1 : ditRepresentanteLegalList) {
				try {
					RepresentanteLegal repLegal = this.representanteLegalUtility.convertirEntityToModel(ditRepresentanteLegal1);
					repLegal.setCveIdPersona(param.getCveIdPersona());
					repLegal.setTipoPersonaRepresentada(param.getTipoPersonaRepresentada());
					representanteLegalList.add(repLegal);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		response.setAaData(representanteLegalList);
		response.setiTotalRecords(totalresult);
		response.setiTotalDisplayRecords(totalresult);
		
	}

	

	@Override
	public RepresentanteLegal get(RepresentanteLegal representanteLegal)
			throws Exception {
		
		representanteLegal = representanteLegalUtility.convertirEntityToModel((DitRepresentanteLegal) this.getSession().load(DitRepresentanteLegal.class, representanteLegal.getCveIdRepresentanteLegal()));

		return representanteLegal;
	}

	public RepresentanteLegal persistir(RepresentanteLegal model)
			throws Exception {
		DitRepresentanteLegal	entity = null;
		SujetoObligado sujetoObligado = null;
		Fisica fisica = null;
		Moral moral = null;
		List<MedioContacto> mediosContanto = new ArrayList<MedioContacto>();
			
		//Se registran los medios de contacto
		if(model.getMediosContacto() != null && !model.getMediosContacto().isEmpty()){
			try {
				mediosContanto = this.mediosContactoService.registrarMedioDeContacto(model.getMediosContacto());
			} catch (RegistrarMedioContactoException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			
		if (model.getTipoPersonaRepresentada().getIdTipoPersona().intValue() == TipoPersona.TIPO_PERSONA_FISICA.intValue()){
			sujetoObligado = new SujetoObligado();
			fisica = new Fisica();
			fisica.setIdPersona(model.getCveIdPersona());
			fisica.setRfc(model.getPersonaFisica().getRfc());
			fisica.setFechaRegistro(model.getFecRegistroAlta());
			sujetoObligado.setFisica(fisica);
			// verificamos si existe el rep legal como persona fisica en Gestion Patronal
			DitPersonaFisica registrarPersonaFisica = null;
			if ( !this.personasGPServiceEntity.existePersonaFisica(fisica) ){
				registrarPersonaFisica = this.personasGPServiceEntity.registrarPersonaFisica(fisica);
			}
				
			SujetoObligado sujetoFisico = this.sujetoObligadoServiceEntity.consultarDetalleSujetoObligadoIdPersonaFisica(sujetoObligado);
			List<Long> cvesPatrones = this.sujetoObligadoServiceEntity
				.consultarIdsPatronesSOPorRfcTipoPersona(sujetoFisico.getFisica().getRfc(), TipoPersonaEnum.FISICA);
			/*
			// Consultamos RP para agregar este rep legal
			List<SujetoObligado> sujetosObligados = (List<SujetoObligado>) this.sujetoObligadoServiceEntity
				.consultarDetalleSujetoObligadoRFCFisica(this.sujetoObligadoServiceEntity
					.consultarDetalleSujetoObligadoIdPersonaFisica(sujetoObligado));*/
			for (Long cvePatronSO : cvesPatrones) {
				model.setCveIdPatronSujetoObligado(cvePatronSO);
				entity = representanteLegalUtility.convertirModelToEntity(model);
				if(registrarPersonaFisica != null){
					DitPersona ditPersona = new DitPersona();
					ditPersona.setCveIdPersona(registrarPersonaFisica.getDitPersona().getCveIdPersona());
					entity.setDitPersona(ditPersona);
				}
				entity.setCveIdRepresentanteLegal(0);
				this.getSession().save(entity);
				// relacionamos este rep legal al cada rp recuperado
				//this.agregarRepLegalEnRP(sujetoObligado2, entity);
					
				//Se asocian los medios de contacto del representante a cada replegal asociado al rp del rfc(persona)
				for (MedioContacto medioContacto : mediosContanto) {
					this.getSession().save(this.asociarMediosContactoRepLegal(medioContacto, entity));
				}
			}
		} else if (model.getTipoPersonaRepresentada().getIdTipoPersona().intValue() == TipoPersona.TIPO_PERSONA_MORAL.intValue()){
			sujetoObligado = new SujetoObligado();
			moral = new Moral();
			moral.setIdPersona(model.getCveIdPersona());
			sujetoObligado.setMoral(moral);
			SujetoObligado sujetoMoral = this.sujetoObligadoServiceEntity.consultarDetalleSujetoObligadoIdPersonaMoral(sujetoObligado);
			List<Long> cvesPatrones = this.sujetoObligadoServiceEntity
				.consultarIdsPatronesSOPorRfcTipoPersona(sujetoMoral.getMoral().getRfc(), TipoPersonaEnum.MORAL);
				
			for (Long cvePatronSO : cvesPatrones) {
				// relacionamos este rep legal al patron
				model.setCveIdPatronSujetoObligado(cvePatronSO);
				entity = representanteLegalUtility.convertirModelToEntity(model);
				entity.setCveIdRepresentanteLegal(0);
				this.getSession().save(entity);
				// relacionamos este rep legal al cada rp recuperado
				//this.agregarRepLegalEnRP(sujetoObligado2, entity);
				//Se asocian los medios de contacto del representante a cada replegal asociado al rp del rfc(persona)
				for (MedioContacto medioContacto : mediosContanto) {
					this.getSession().save(this.asociarMediosContactoRepLegal(medioContacto, entity));
				}
			}
		}
		return model;
	}
	
	@Override
	public RepresentanteLegal asociarRepresentanteLegal(RepresentanteLegal model)
			throws Exception {
		DitRepresentanteLegal	entity = null;
		Fisica fisica = null;
		Moral moralCalif = null;
		List<MedioContacto> mediosContanto = new ArrayList<MedioContacto>();
		//Se registran los medios de contacto
		if(model.getMediosContacto() != null && !model.getMediosContacto().isEmpty()){
			try {
				mediosContanto = this.mediosContactoService.registrarMedioDeContacto(model.getMediosContacto());
			} catch (RegistrarMedioContactoException e) {
				//e.printStackTrace();
				log.error("SIN MEDIOS DE CONTACTO");
			} catch (Exception e) {
				log.error("OCURRIO UN ERROR AL CONSULTAR MEDIOS" , e);
			}
		}
		
		log.debug("PASE LOS MEDIO DE CONTACTO ******");
		entity = representanteLegalUtility.convertirModelToEntity(model);
		DicTipoPoder dicTipoPoder=(DicTipoPoder)this.getSession()
			.get(DicTipoPoder.class, model.getCveIdTipoPoder());
		DitPersona personaRepresentante=(DitPersona)this.getSession()
			.get(DitPersona.class, model.getPersonaFisica().getIdPersona());
		entity.setDicTipoPoder(dicTipoPoder);
		entity.setDitPersona(personaRepresentante);
		if (model.getTipoPersonaRepresentada().getIdTipoPersona().intValue() == TipoPersona.TIPO_PERSONA_FISICA.intValue()){
			fisica = new Fisica();
			fisica.setIdPersona(model.getCveIdPersona());
			fisica.setFechaRegistro(model.getFecRegistroAlta());
			// verificamos si existe el rep legal como persona fisica en Gestion Patronal
			if ( !this.personasGPServiceEntity.existePersonaFisica(fisica) ){
				DitPersona ditPersonaRepresentada = (DitPersona)this.getSession().get(DitPersona.class, model.getCveIdPersona());
				fisica.setRfc(ditPersonaRepresentada.getRfc());
				DitPersonaFisica ditPersonaFisicaRepresentada = this.personasGPServiceEntity.registrarPersonaFisica(fisica);
				entity.setDitPersonaFisicaRepresentada(ditPersonaFisicaRepresentada);
				//Seteo de la propiedad para hacer la insercion en la calificacion
				fisica.setCveFisica(ditPersonaFisicaRepresentada.getCveIdPersonaFisica());
			}else{
				final Criteria criteria = this.getSession().createCriteria(DitPersonaFisica.class);
				criteria.createAlias("ditPersona", "ditPersona").add(Restrictions.eq("ditPersona.cveIdPersona", fisica.getIdPersona()));
				DitPersonaFisica ditPersonaFisica = (DitPersonaFisica) criteria.list().get(0);
				entity.setDitPersonaFisicaRepresentada(ditPersonaFisica);
				fisica.setCveFisica(ditPersonaFisica.getCveIdPersonaFisica());
			
			}
			
	
			model.setCveIdRepresentanteLegal(0l);
			//guardado de la relacion de el representante legal
			this.getSession().save(entity);
			this.getSession().flush();
			
			for (MedioContacto medioContacto : mediosContanto) {
				this.getSession().save(this.asociarMediosContactoRepLegal(medioContacto, entity));
			}
			
			//SE manda a calificar a la persona fisica asociada al representane
			try{
				log.debug("se va a hacer la llamda para guardar la calificacion fisica *******");
				calificacionesPersonaBusinessService.calificarSAT(fisica);
				log.debug("finaliza la llamada al guardado de la calificacion *fisica ******");
			}catch(Exception e){
				log.error("OCURRIO UN ERROR AL CALIFIFAR A LA PERSONA FISICA *******", e);
			}
			
		} else if (model.getTipoPersonaRepresentada().getIdTipoPersona().intValue() == TipoPersona.TIPO_PERSONA_MORAL.intValue()){
			moralCalif = new Moral();
			model.setCveIdRepresentanteLegal(0l);
			DitPersonaMoral ditPersonaMoralRepresentada = (DitPersonaMoral)this.getSession()
				.get(DitPersonaMoral.class, model.getCveIdPersona());
			
			entity.setDitPersonaMoralRepresentada(ditPersonaMoralRepresentada);
			moralCalif.setCveMoral(ditPersonaMoralRepresentada.getCveIdPersonaMoral());
			
			
			this.getSession().save(entity);
			this.getSession().flush();
			
			for (MedioContacto medioContacto : mediosContanto) {
				this.getSession().save(this.asociarMediosContactoRepLegal(medioContacto, entity));
			}
			

			try{
				log.debug("se va a hacer la llamda para guardar la calificacion moral*******");
				calificacionesPersonaBusinessService.calificarSAT(moralCalif);
				log.debug("finaliza la llamada al guardado de la calificacion moral *******");
			}catch(Exception e){
				log.error("OCURRIO UN ERROR AL CALIFIFAR A LA PERSONA MORAL *******", e);
			}
			
		}		
		return model;
	}

	

	private DitRepresentanteLegalContac asociarMediosContactoRepLegal(
			MedioContacto registrarMedioDeContacto,
			DitRepresentanteLegal entity) {
		
		DitRepresentanteLegalContacPK ditRepresentanteLegalContacPK = new DitRepresentanteLegalContacPK();
		DitRepresentanteLegalContac ditRepresentanteLegalContac = new DitRepresentanteLegalContac();
		ditRepresentanteLegalContacPK.setCveIdFormaContacto(registrarMedioDeContacto.getClave());
		ditRepresentanteLegalContacPK.setCveIdRepresentanteLegal(entity.getCveIdRepresentanteLegal());
		ditRepresentanteLegalContac.setId(ditRepresentanteLegalContacPK);
		ditRepresentanteLegalContac.setFecRegistroAlta(new Date());
		return ditRepresentanteLegalContac;		
	}

	@SuppressWarnings("unused")
	private void agregarRepLegalEnRP(
			SujetoObligado consultarDetalleSujetoObligadoIdPersonaMoral,
			DitRepresentanteLegal entity) {
		
		DitPatronSujetoObligado object = (DitPatronSujetoObligado) this.getSession().get(DitPatronSujetoObligado.class, consultarDetalleSujetoObligadoIdPersonaMoral.getCveIdSujetoObligado());
		
		object.getDitRepresentanteLegals().add(entity);
	}

	/**
	 * solo se actualiza la fecha de baja, no se realiza la eliminacion del registro
	 */
	@Override
	public void borrar(RepresentanteLegal instance) throws Exception {
		Long idPersonaRepresentada = instance.getCveIdPersona();
		
		StringBuffer strBfr = new StringBuffer();
		strBfr.append("select ditPatron from DitPatronSujetoObligado ditPatron where ");
		if(instance.getTipoPersonaRepresentada().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA))
			strBfr.append("ditPatron.ditPersonaFisica.ditPersona.cveIdPersona=:idPersonaRepresentada");
		else if(instance.getTipoPersonaRepresentada().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_MORAL))
			strBfr.append("ditPatron.ditPersonaMoral.cveIdPersonaMoral=:idPersonaRepresentada");
		else
			log.error("Tipo Persona inválido");

		Query query = this.em.createQuery(strBfr.toString());
		query.setParameter("idPersonaRepresentada", idPersonaRepresentada);
			
		@SuppressWarnings("unchecked")
		List<DitPatronSujetoObligado> rpsAsociadosAPersona= query.getResultList();
		for(DitPatronSujetoObligado rp: rpsAsociadosAPersona){
			log.debug("Validando el rp: "+rp.getCveIdPatronSujetoObligado());
			List<DitRepresentanteLegal> representantesDelRP = rp.getDitRepresentanteLegals();
			for(DitRepresentanteLegal rl :representantesDelRP){
				if(rl.getDitPersona().getCveIdPersona().equals(instance.getPersonaFisica().getIdPersona())){
					log.debug("Se actualiza el RL: "+rl.getCveIdRepresentanteLegal()+" del rp "+rp.getCveIdPatronSujetoObligado());
					rl.setFecRegistroBaja(Calendar.getInstance().getTime());
				}
			}
		}
	}

	@Override
	public void validaLimMinRegRepresentanteLegal(
			RepresentanteLegal representanteLegal)
			throws RepresentanteLegalLimiteMinRegExcedidoException, Exception {
		
	}

	@Override
	public void validaExisteRepresentanteLegal(
			RepresentanteLegal representanteLegal)
			throws RepresentanteLegalInvalidoException,
			RepresentanteLegalYaExisteException {
		
		DitRepresentanteLegal result = null;
		try {
			@SuppressWarnings("unused")
			DitRepresentanteLegal convertirModelToEntity = representanteLegalUtility.convertirModelToEntity(representanteLegal);
			result = consultarRelacionRepresentanteLegalPorIdentificadores(representanteLegal); //consultarRepresentanteLegalPorPatronSujetoObligado(representanteLegal);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RepresentanteLegalInvalidoException(e.getMessage());
		}
	
		if(result != null){
			log.debug("Ya existe este Representante Legal [" + result.getDitPersona() + "] asociado con el patrón [" + result.getCveIdRepresentanteLegal() +"]");
			//Si existe un Representante Legal entonces no se cumple con la regla.
			throw new RepresentanteLegalYaExisteException();
		}		
		
	}

	@Override
	public void validaBorrarRepresentanteLegal(
			RepresentanteLegal representanteLegal)
			throws RepresentanteLegalInvalidoException,
			RepresentanteLegalLimiteMinRegExcedidoException {
		
	}
	
	@Override
	public RepresentanteLegal actualizar(RepresentanteLegal instance)
			throws Exception {

		//Se insertan los nuevos medios de contacto
		List<MedioContacto> mediosTramite = instance.getMediosContacto();
		if(!mediosTramite.isEmpty()){
			try {
				for(MedioContacto medio :mediosTramite){
					//Se inicializa a null
					medio.setClave(null);
				}
				mediosTramite = mediosContactoService.registrarMedioDeContacto(mediosTramite);
			}catch (RegistrarMedioContactoException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Long idPersonaRepresentada = instance.getCveIdPersona();
		StringBuffer strBfr = new StringBuffer();
		strBfr.append("select ditPatron from DitPatronSujetoObligado ditPatron where ");
		if(instance.getTipoPersonaRepresentada().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA))
			strBfr.append("ditPatron.ditPersonaFisica.ditPersona.cveIdPersona=:idPersonaRepresentada");
		else if(instance.getTipoPersonaRepresentada().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_MORAL))
			strBfr.append("ditPatron.ditPersonaMoral.cveIdPersonaMoral=:idPersonaRepresentada");
		else
			log.debug("Tipo Persona inválido");

		Query query = this.em.createQuery(strBfr.toString());
		query.setParameter("idPersonaRepresentada", idPersonaRepresentada);
		
		@SuppressWarnings("unchecked")
		List<DitPatronSujetoObligado> rpsAsociadosAPersona= query.getResultList();
		for(DitPatronSujetoObligado rp: rpsAsociadosAPersona){
			log.debug("Validando el rp: "+rp.getCveIdPatronSujetoObligado());
			List<DitRepresentanteLegal> representantesDelRP = rp.getDitRepresentanteLegals();
			for(DitRepresentanteLegal rl :representantesDelRP){
				if(rl.getDitPersona().getCveIdPersona().equals(instance.getPersonaFisica().getIdPersona())){
					log.debug("Se actualiza el RL: "+rl.getCveIdRepresentanteLegal()+" del rp "+rp.getCveIdPatronSujetoObligado());
					rl = this.representanteLegalUtility.asignarValoresParaActualizar(instance,rl);
					this.getSession().saveOrUpdate(rl);
					//Se eliminan los medios de contacto actuales
					for(DitRepresentanteLegalContac contacto : rl.getDitRepresentanteLegalContacs()){
						this.getSession().delete(contacto);
					}
					//Se asocian los nuevos datos previamente insertados
					for(MedioContacto medio :mediosTramite){
						this.getSession().save( asociarMediosContactoRepLegal(medio, rl) );
					}
				}
			}
		}			
		return instance;
	}	
	
	
	@SuppressWarnings("unused")
	private DitRepresentanteLegal consultarRepresentanteLegalPorPatronSujetoObligado(RepresentanteLegal representanteLegal){
		Criteria criteria =  this.getSession().createCriteria(DitRepresentanteLegal.class);
		criteria.createAlias("ditPersona", "ditPersona").add(Restrictions.eq("ditPersona.cveIdPersona", representanteLegal.getCveIdPersona()));
		criteria.createAlias("ditPatronSujetoObligado", "ditPatronSujetoObligado").add(
				Restrictions.eq("ditPatronSujetoObligado.cveIdPatronSujetoObligado", representanteLegal.getCveIdPatronSujetoObligado()));
		criteria.add(Restrictions.isNull("fecRegistroBaja"));
		DitRepresentanteLegal repLegal = criteria.uniqueResult()!= null ? (DitRepresentanteLegal)criteria.uniqueResult(): null;
		return repLegal;
		
	}
	
	
	@Override
	public DitRepresentanteLegal consultarRelacionRepresentanteLegalPorIdentificadores(RepresentanteLegal representanteLegal){
		
		DitRepresentanteLegal repLegal = null;
		
		StringBuffer query = new StringBuffer();
		
		if(representanteLegal.getTipoPersonaRepresentada().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)){
			query.append("select rl from DitRepresentanteLegal rl ");
			query.append(" join rl.ditPersona representante ");
			query.append(" join rl.ditPersonaFisicaRepresentada fisicaRepresentada ");
			query.append(" where representante.cveIdPersona = ").append(representanteLegal.getPersonaFisica().getIdPersona());
			query.append(" and fisicaRepresentada.ditPersona.cveIdPersona = ").append(representanteLegal.getCveIdPersona());
			query.append(" and rl.fecRegistroBaja is null ");
			
			Query consulta = this.em.createQuery(query.toString());
			try{
				repLegal = (DitRepresentanteLegal)consulta.getSingleResult();
			}catch(NoResultException nre){
				log.error("No existe relación de representante entre estas personas");
				return null;
			}
			
		}else if(representanteLegal.getTipoPersonaRepresentada().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_MORAL)){
			query.append("select rl from DitRepresentanteLegal rl ");
			query.append(" join rl.ditPersona representante ");
			query.append(" join rl.ditPersonaMoralRepresentada moralRepresentada ");
			query.append(" where representante.cveIdPersona = ").append(representanteLegal.getPersonaFisica().getIdPersona());
			query.append(" and moralRepresentada.cveIdPersonaMoral = ").append(representanteLegal.getCveIdPersona());
			query.append(" and rl.fecRegistroBaja is null ");
			
			Query consulta = this.em.createQuery(query.toString());
			try{
				repLegal = (DitRepresentanteLegal)consulta.getSingleResult();
			}catch(NoResultException nre){
				log.error("No existe relación de representante entre estas personas");
				return null;
			}
		}
		return repLegal;
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DitRepresentanteLegal> consultaPorPatronSujetoObligado(
			RepresentanteLegal representanteLegal) throws Exception {
		
		String sqlQuery = "select rl from DitRepresentanteLegal rl where rl.ditPatronSujetoObligado.cveIdPatronSujetoObligado =:cveIdRegistroPatronal and rl.fecRegistroBaja is null" ;
		Query query = em.createQuery(sqlQuery);
		query.setParameter("cveIdRegistroPatronal", representanteLegal.getCveIdPatronSujetoObligado());
		
		List <DitRepresentanteLegal> result = query.getResultList();
		//Se cambia al uso de EM para que funcione correctamente la transacción global de alta patronal.
//		Criteria criteria =  this.getSession().createCriteria(DitRepresentanteLegal.class);
//		criteria.add(Restrictions.isNull("fecRegistroBaja"));
//		criteria.createAlias("ditPatronSujetoObligado", "ditPatronSujetoObligado").add(
//				Restrictions.eq("ditPatronSujetoObligado.cveIdPatronSujetoObligado", representanteLegal.getCveIdPatronSujetoObligado()));
//		List <DitRepresentanteLegal> result = criteria.list();

		return result;

	}

	@Override
	public int consultarNumRegistros(Long someId) {
		
		Criteria criteria =  this.getSession().createCriteria(DitRepresentanteLegal.class);
		criteria.add(Restrictions.eq("ditPatronSujetoObligado.cveIdPatronSujetoObligado", someId));
		criteria.add(Restrictions.isNull("fecRegistroBaja"));
		
		return criteria.list().size();
	}
	
	/**
	 * 
	 */
	@Override
	public List<RepresentanteLegal> consultarRepresentanteLegalPorSujetoObligado(RepresentanteLegal representanteLegal){
		List<DitRepresentanteLegal> entities = null;
		List<RepresentanteLegal> repLegales = Collections.emptyList();
		try {
			entities = consultaPorPatronSujetoObligado(representanteLegal);
			if( entities!= null && !entities.isEmpty()){
				repLegales = new ArrayList<RepresentanteLegal>();
				for(DitRepresentanteLegal entity : entities){
					log.debug("Los representantes encontrados son: "+entity.getCveIdRepresentanteLegal());
					representanteLegal = representanteLegalUtility.convertirEntityToModel(entity);
					repLegales.add(representanteLegal);
				}
			}else
				return null;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return repLegales;
	}

	@Override
	public void actualizarRepresentantes(List<RepresentanteLegal> representantes) {
		Long idSujetoObligado = null;
		List<DitRepresentanteLegal> representantesLegales = new ArrayList<DitRepresentanteLegal>();
		for(RepresentanteLegal model:representantes){
			idSujetoObligado = model.getCveIdPatronSujetoObligado();
			DitRepresentanteLegal entity = representanteLegalUtility.convertirModelToEntity(model);
			representantesLegales.add(entity);
		}
		
		DitPatronSujetoObligado sujetoObligado = 
				(DitPatronSujetoObligado)this.getSession().load(
						DitPatronSujetoObligado.class, idSujetoObligado);
		
		//Se eliminan los representantes actuales
		for(DitRepresentanteLegal representante : 
			sujetoObligado.getDitRepresentanteLegals()){
			getSession().delete(representante);
		}
		
		//Se agregan los nuevos representantes
		for(DitRepresentanteLegal entity:representantesLegales){
			Long idSujeto = entity.getDitPatronSujetoObligado().getCveIdPatronSujetoObligado();
			DitPatronSujetoObligado so = 
					(DitPatronSujetoObligado)this.getSession().load(
							DitPatronSujetoObligado.class, idSujeto);
			DitPersona persona = 
					(DitPersona)this.getSession().load(
							DitPersona.class, entity.getDitPersona().getCveIdPersona());
			entity.setDitPatronSujetoObligado(so);
			entity.setDitPersona(persona);
			this.getSession().persist(entity);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public DatosSalidaPaginador<RepresentanteLegal> paginarParaManejoDeTramite(
			DatosEntradaPaginador<RepresentanteLegal> params) {
		
		DatosSalidaPaginador<RepresentanteLegal> response = new DatosSalidaPaginador<RepresentanteLegal>();
		List<RepresentanteLegal> representanteLegalList = new ArrayList<RepresentanteLegal>();
		
		RepresentanteLegal param = params.getModelo();
		StringBuffer strBfr = new StringBuffer();
		strBfr.append("select ditRepLegal from DitRepresentanteLegal ditRepLegal ");
		strBfr.append("where ditRepLegal.fecRegistroBaja is null and ditRepLegal.ditPatronSujetoObligado.cveIdPatronSujetoObligado in (");
		if(param.getTipoPersonaRepresentada().getIdTipoPersona().equals(TipoPersonaEnum.FISICA.getId())){
			strBfr.append("select patron.cveIdPatronSujetoObligado from DitPatronSujetoObligado patron where patron.ditPersonaFisica.ditPersona.cveIdPersona = :cveIdPersona");
		}else if(param.getTipoPersonaRepresentada().getIdTipoPersona().equals(TipoPersonaEnum.MORAL.getId())){
			strBfr.append("select patron.cveIdPatronSujetoObligado from DitPatronSujetoObligado patron where patron.ditPersonaMoral.cveIdPersonaMoral = :cveIdPersona");
		}
		strBfr.append(")");
				  
		Query query = this.em.createQuery(strBfr.toString());
		
		query.setParameter("cveIdPersona", param.getCveIdPersona());
		
		List<DitRepresentanteLegal> ditRepresentanteLegalList = query.getResultList();
		for (DitRepresentanteLegal ditRepresentanteLegal1 : ditRepresentanteLegalList) {
			try {
				RepresentanteLegal repLegal = this.representanteLegalUtility.convertirEntityToModel(ditRepresentanteLegal1);
				repLegal.setCveIdPersona(param.getCveIdPersona());
				repLegal.setTipoPersonaRepresentada(param.getTipoPersonaRepresentada());
				representanteLegalList.add(repLegal);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		response.setAaData(representanteLegalList);
		
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RepresentanteLegal> obtenerRepresentantesPorPersona(
			Long cveIdPersona, TipoPersonaEnum tipoPersona) {
		
		cveIdPersona = ( cveIdPersona != null ? cveIdPersona : 0L);		
		List<RepresentanteLegal> representanteLegalList = new ArrayList<RepresentanteLegal>();
		StringBuffer strRepresentantes = new StringBuffer();
		
		if(tipoPersona.equals(TipoPersonaEnum.FISICA)){
			strRepresentantes.append("select rL from DitRepresentanteLegal rL" +
					" where rL.ditPersonaFisicaRepresentada.cveIdPersonaFisica = :cveIdPersona and rL.fecRegistroBaja is null");
			//strBfrSujObligMuestra.append("select patron.cveIdPatronSujetoObligado from DitPatronSujetoObligado patron where patron.ditPersonaFisica.cveIdPersonaFisica = :cveIdPersona");
		}else if(tipoPersona.equals(TipoPersonaEnum.MORAL)){
			strRepresentantes.append("select rL from DitRepresentanteLegal rL" +
			" where rL.ditPersonaMoralRepresentada.cveIdPersonaMoral = :cveIdPersona and rL.fecRegistroBaja is null");
			//strBfrSujObligMuestra.append("select patron.cveIdPatronSujetoObligado from DitPatronSujetoObligado patron where patron.ditPersonaMoral.cveIdPersonaMoral = :cveIdPersona");
		}
		Query queryPatron = this.em.createQuery(strRepresentantes.toString());
		queryPatron.setParameter("cveIdPersona", cveIdPersona);
		
		List<DitRepresentanteLegal> representantes = queryPatron.getResultList();
		
		for (DitRepresentanteLegal ditRepresentanteLegal1 : representantes) {
			try {
				RepresentanteLegal repLegal = this.representanteLegalUtility.enityToModelDatosBasicos(ditRepresentanteLegal1);
				repLegal.setCveIdPersona(cveIdPersona);
				TipoPersona tipoPersonaObj = new TipoPersona();
				tipoPersonaObj.setIdTipoPersona(tipoPersona.getId());
				repLegal.setTipoPersonaRepresentada(tipoPersonaObj);
				representanteLegalList.add(repLegal);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		
//		StringBuffer strBfrSujObligMuestra = new StringBuffer();
//		if(tipoPersona.equals(TipoPersonaEnum.FISICA)){
//			strBfrSujObligMuestra.append("select rL.ditPatronSujetoObligado.cveIdPatronSujetoObligado from DitRepresentanteLegal rL" +
//					" where rL.ditPatronSujetoObligado.ditPersonaFisica.cveIdPersonaFisica = :cveIdPersona and rL.fecRegistroBaja is null");
//			//strBfrSujObligMuestra.append("select patron.cveIdPatronSujetoObligado from DitPatronSujetoObligado patron where patron.ditPersonaFisica.cveIdPersonaFisica = :cveIdPersona");
//		}else if(tipoPersona.equals(TipoPersonaEnum.MORAL)){
//			strBfrSujObligMuestra.append("select rL.ditPatronSujetoObligado.cveIdPatronSujetoObligado from DitRepresentanteLegal rL" +
//			" where rL.ditPatronSujetoObligado.ditPersonaMoral.cveIdPersonaMoral = :cveIdPersona and rL.fecRegistroBaja is null");
//			//strBfrSujObligMuestra.append("select patron.cveIdPatronSujetoObligado from DitPatronSujetoObligado patron where patron.ditPersonaMoral.cveIdPersonaMoral = :cveIdPersona");
//		}
//		Query queryPatron = this.em.createQuery(strBfrSujObligMuestra.toString());
//		queryPatron.setParameter("cveIdPersona", cveIdPersona);
//		
//		List<Long> idsPatron = queryPatron.getResultList();
//		Long cveIdPatronSujetoObligado = null;
//		if(idsPatron!=null &&  idsPatron.size()>0){
//			cveIdPatronSujetoObligado = idsPatron.get(0);
//		}
//		
//		
//		if(cveIdPatronSujetoObligado!=null){
//			StringBuffer strBfr = new StringBuffer();
//			strBfr.append("select ditRepLegal from DitRepresentanteLegal ditRepLegal ");
//			strBfr.append("where ditRepLegal.fecRegistroBaja is null and ditRepLegal.ditPatronSujetoObligado.cveIdPatronSujetoObligado = :cveIdPatronSujetoObligado");
//			
//			Query query = this.em.createQuery(strBfr.toString());
//			query.setParameter("cveIdPatronSujetoObligado", cveIdPatronSujetoObligado);
//			List<DitRepresentanteLegal> ditRepresentanteLegalList = query.getResultList();
//			for (DitRepresentanteLegal ditRepresentanteLegal1 : ditRepresentanteLegalList) {
//				try {
//					RepresentanteLegal repLegal = this.representanteLegalUtility.enityToModelDatosBasicos(ditRepresentanteLegal1);
//					repLegal.setCveIdPersona(cveIdPersona);
//					TipoPersona tipoPersonaObj = new TipoPersona();
//					tipoPersonaObj.setIdTipoPersona(tipoPersona.getId());
//					repLegal.setTipoPersonaRepresentada(tipoPersonaObj);
//					representanteLegalList.add(repLegal);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
		return representanteLegalList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Fisica getPersonaByCurp(String curp){
		this.log.debug("CONSULTANDO INFORMACION POR LA CURP:::: "+curp);
		String strConsultaJpa = null;
		strConsultaJpa = "Select ditPersona from DitPersona ditPersona " +
				"where ditPersona.curp = :curp and ditPersona.fecRegistroBaja is null ";
				
		Query query = this.em.createQuery(strConsultaJpa);
		
		query.setParameter("curp", curp);
		this.log.debug("haciendo el query con la entidad [" + strConsultaJpa+ "]");
		
		List<DitPersona> lstPersona = query.getResultList();
		this.log.debug("CANTIDAD ENCONTRADA DE PERSONAS POR CURP "+curp+":: "+lstPersona.size());
		Fisica fisica = sujetoObligadoUtilityLocal.convertirDitPersonaToModelFisica(lstPersona);
		return fisica;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RepresentanteLegal> obtenerRepresentantesPorRFCMoral(
			String rfc) {
		
		log.debug("::: INGRESANDO A LA CONSULTA ::::: ");
		List<RepresentanteLegal> representanteLegalList = new ArrayList<RepresentanteLegal>();
		StringBuffer strRepresentantes = new StringBuffer();
		log.debug("::: INGRESANDO A LA CONSULTA ::::: ");
		strRepresentantes.append("select DRL from DitRepresentanteLegal DRL "+
		"WHERE DRL.ditPersonaMoralRepresentada.rfc = :RFC AND DRL.fecRegistroBaja is null ");
		log.debug("::: INGRESANDO A LA CONSULTA ::::: "+this.getSession());
		Query queryPatron = this.em.createQuery(strRepresentantes.toString());
		queryPatron.setParameter("RFC", rfc);
		log.debug("::: INGRESANDO A LA CONSULTA ::::: ");
		List<DitRepresentanteLegal> representantes = queryPatron.getResultList();
		log.debug("::: INGRESANDO A LA CONSULTA ::::: ");
		
		for (DitRepresentanteLegal ditRepresentanteLegal1 : representantes) {
			try {
				RepresentanteLegal repLegal = this.representanteLegalUtility.enityToModelDatosBasicos(ditRepresentanteLegal1);
				repLegal.setCveIdPersona(ditRepresentanteLegal1.getDitPersona().getCveIdPersona());
				TipoPersona tipoPersonaObj = new TipoPersona();
				tipoPersonaObj.setIdTipoPersona(TipoPersonaEnum.MORAL.getId());
				repLegal.setTipoPersonaRepresentada(tipoPersonaObj);
				representanteLegalList.add(repLegal);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return representanteLegalList;
	}
	
	
	/*
	private List<SujetoObligado> consultarSujetosRepresentadosPorRepresentanteLegalPatronMoral(
			Long cveIdPersonaMoral) {
		Query query = em.createQuery("SELECT S from DitPatronSujetoObligado S "
				+ "join S.ditRepresentanteLegals RL " + "join RL.ditPersona P "
				+ "where P.cveIdPersona = " + cveIdPersona);
		List<SujetoObligado> sujetos = new ArrayList<SujetoObligado>();
		List<DitPatronSujetoObligado> ditSujetos = (List<DitPatronSujetoObligado>) query
				.getResultList();
		for (DitPatronSujetoObligado entity : ditSujetos) {
			TipoPersonaFiscal tipoPersonaFiscal = null;
			if (entity.getDitPersonaFisica() != null)
				tipoPersonaFiscal = TipoPersonaFiscal.FISICA;
			if (entity.getDitPersonaMoral() != null)
				tipoPersonaFiscal = TipoPersonaFiscal.MORAL;
			sujetos.add(sujetoObligadoUtility.convertirEntityToModel(entity,
					tipoPersonaFiscal));
		}

		return sujetos;
	}*/

	@Override
	public void eliminarMediosContactoDeRepresentante(
			Long cveIdRepresentanteLegal) {
		
		DitRepresentanteLegal entity = em.find(DitRepresentanteLegal.class, cveIdRepresentanteLegal);
		entity.setDitRepresentanteLegalContacs(null);
		
	}

	@Override
	public void asociarMediosContactoARepresentante(List<MedioContacto> medios,
			Long cveIdRepresentanteLegal) {
		DitRepresentanteLegal entity = em.find(DitRepresentanteLegal.class, cveIdRepresentanteLegal);
		for (MedioContacto medioContacto : medios) {
			this.getSession().save(this.asociarMediosContactoRepLegal(medioContacto, entity));
		}	
	}

	
	@Override
	public void actualizarRepresentanteLegal(RepresentanteLegal representante)  throws ParametrosInvalidosException{
		if(representante.getPersonaFisica().getIdPersona() == null) {
			throw new ParametrosInvalidosException("El representante debe contar con id de persona");
		}
		if(representante.getSujetoObligado().getCveIdSujetoObligado() == null) {
			throw new ParametrosInvalidosException("El representante debe contar con id de sujeto obligado");
		} 
		if(representante.getCveIdRepresentanteLegal() == null) {
			throw new ParametrosInvalidosException("El representante debe contar con id de representante legal");
		}
		
		DitRepresentanteLegal ditRepresentanteLegal = new DitRepresentanteLegal();
		ditRepresentanteLegal.setCveIdRepresentanteLegal(representante.getCveIdRepresentanteLegal());
		ditRepresentanteLegal.setDitPatronSujetoObligado(new DitPatronSujetoObligado());
		ditRepresentanteLegal.getDitPatronSujetoObligado().setCveIdPatronSujetoObligado(representante.getSujetoObligado().getCveIdSujetoObligado());
		ditRepresentanteLegal.setDitPersona(new DitPersona());
		ditRepresentanteLegal.getDitPersona().setCveIdPersona(representante.getPersonaFisica().getIdPersona());
		ditRepresentanteLegal.setFecRegistroActualizado(representante.getFecRegistroActualizado());
		ditRepresentanteLegal.setFecRegistroAlta(representante.getFecRegistroAlta());
		ditRepresentanteLegal.setFecRegistroBaja(representante.getFecRegistroBaja());
		
		this.em.merge(ditRepresentanteLegal);
		
	}

	@Override
	public List<RepresentanteLegal> obtenerRLsporIdPersonaFisica(
			Long cveIdPersona) throws Exception {
		Criteria queryRL = this.getSession().createCriteria(DitRepresentanteLegal.class);
		queryRL.createAlias("ditPersona", "persona");
		queryRL.add(Restrictions.eq("persona.cveIdPersona", cveIdPersona));
		queryRL.add(Restrictions.isNull("fecRegistroBaja"));
		
		@SuppressWarnings("unchecked")
		List<DitRepresentanteLegal> listaRl = queryRL.list();
		List<RepresentanteLegal> listaRepl = null;
		
		if(!listaRl.isEmpty()) {
			listaRepl = new ArrayList<RepresentanteLegal>();
			
			for(DitRepresentanteLegal rL : listaRl){
				RepresentanteLegal representante = new RepresentanteLegal();
				representante.setSujetoObligado(sujetoObligadoUtilityLocal.convertirEntityToModelDatosPersona(rL.getDitPatronSujetoObligado(), null));
				representante.setCveIdRepresentanteLegal(rL.getCveIdRepresentanteLegal());
				representante.setPersonaFisica(new Fisica());
				representante.getPersonaFisica().setIdPersona(rL.getDitPersona().getCveIdPersona());
				representante.setFecRegistroAlta(rL.getFecRegistroAlta());
				representante.setFecRegistroActualizado(rL.getFecRegistroActualizado());
				representante.setFecRegistroBaja(rL.getFecRegistroBaja());
				
				listaRepl.add(representante);
			}
		}
		
		return listaRepl;
	}
	
	@Override
	public boolean esRepresentanteDeLaPersona(Long cveIdPersonaRepresentante, Long cveIdPersonaRepresentada, TipoPersonaEnum tipoPersonaRepresentada){
		StringBuffer bfrPatronesDePersona = new StringBuffer();
		bfrPatronesDePersona.append("select pso2.cveIdPatronSujetoObligado from DitPatronSujetoObligado pso2 ");
		if(tipoPersonaRepresentada.equals(TipoPersonaEnum.FISICA))
			bfrPatronesDePersona.append("where pso2.ditPersonaFisica.ditPersona.cveIdPersona =:idPersonaRepresentada");
		else if(tipoPersonaRepresentada.equals(TipoPersonaEnum.MORAL))
			bfrPatronesDePersona.append("where pso2.ditPersonaMoral.cveIdPersonaMoral =:idPersonaRepresentada ");
		
		
		StringBuffer bfrConsultaRL = new StringBuffer();
		bfrConsultaRL.append("from DitRepresentanteLegal drl ");
		bfrConsultaRL.append("where drl.ditPatronSujetoObligado.cveIdPatronSujetoObligado in ( ");
		bfrConsultaRL.append(bfrPatronesDePersona.toString());
		bfrConsultaRL.append(" ) ");
		bfrConsultaRL.append("and drl.fecRegistroBaja is null and drl.ditPersona.cveIdPersona =:idPersonaRpresentante");
		
		Query query = this.em.createQuery(bfrConsultaRL.toString());
		
		query.setParameter("idPersonaRepresentada", cveIdPersonaRepresentada);
		query.setParameter("idPersonaRpresentante", cveIdPersonaRepresentante);
		
		@SuppressWarnings("unchecked")
		List<DitRepresentanteLegal> representantesObtenidos = query.getResultList();
		
		if(representantesObtenidos==null || (representantesObtenidos!=null && representantesObtenidos.size()==0))
			return false;
		
		
		return true;
	}

	@Override
	public List<RepresentanteLegal> obtenerRepresentantesConActosAdmonPorPersona(
			Long cveIdPersona, TipoPersonaEnum tipoPersona) {
		List<RepresentanteLegal> representanteLegalList = new ArrayList<RepresentanteLegal>();
		
		StringBuffer strBfrSujObligMuestra = new StringBuffer();
		if(tipoPersona.equals(TipoPersonaEnum.FISICA)){
			strBfrSujObligMuestra.append("select patron.cveIdPatronSujetoObligado from DitPatronSujetoObligado patron where patron.ditPersonaFisica.ditPersona.cveIdPersona = :cveIdPersona");
		}else if(tipoPersona.equals(TipoPersonaEnum.MORAL)){
			strBfrSujObligMuestra.append("select patron.cveIdPatronSujetoObligado from DitPatronSujetoObligado patron where patron.ditPersonaMoral.cveIdPersonaMoral = :cveIdPersona");
		}
		Query queryPatron = this.em.createQuery(strBfrSujObligMuestra.toString());
		queryPatron.setParameter("cveIdPersona", cveIdPersona);
		
		@SuppressWarnings("unchecked")
		List<Long> idsPatron = queryPatron.getResultList();
		Long cveIdPatronSujetoObligado = null;
		if(idsPatron!=null &&  idsPatron.size()>0){
			cveIdPatronSujetoObligado = idsPatron.get(0);
		}
		
		
		if(cveIdPatronSujetoObligado!=null){
			StringBuffer strBfr = new StringBuffer();
			strBfr.append("select ditRepLegal from DitRepresentanteLegal ditRepLegal ");
			strBfr.append("where ditRepLegal.fecRegistroBaja is null " );
			strBfr.append("and ditRepLegal.ditPatronSujetoObligado.cveIdPatronSujetoObligado = :cveIdPatronSujetoObligado ");
			strBfr.append("and ditRepLegal.indActAdmonDominio =:indActosAdmon");
			
			Query query = this.em.createQuery(strBfr.toString());
			query.setParameter("cveIdPatronSujetoObligado", cveIdPatronSujetoObligado);
			query.setParameter("indActosAdmon", BigDecimal.ONE);
			@SuppressWarnings("unchecked")
			List<DitRepresentanteLegal> ditRepresentanteLegalList = query.getResultList();
			for (DitRepresentanteLegal ditRepresentanteLegal1 : ditRepresentanteLegalList) {
				try {
					RepresentanteLegal repLegal = this.representanteLegalUtility.convertirEntityToModel(ditRepresentanteLegal1);
					repLegal.setCveIdPersona(cveIdPersona);
					TipoPersona tipoPersonaObj = new TipoPersona();
					tipoPersonaObj.setIdTipoPersona(tipoPersona.getId());
					repLegal.setTipoPersonaRepresentada(tipoPersonaObj);
					representanteLegalList.add(repLegal);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return representanteLegalList;
	}

	@Override
	public void asociarRepresentanteLegalARegistroPatronal(
			RepresentanteLegal representanteLegal) {
		//En caso de que se tenga un idRepresentanteLegal se elimina pues se realizara una nueva asociación con el rp
		representanteLegal.setCveIdRepresentanteLegal(null);
		DitRepresentanteLegal ditRepresentante = representanteLegalUtility.convertirModelToEntity(representanteLegal);
		this.em.persist(ditRepresentante);
		
	}

	@Override
	public void asociarRepresentantesLegalesARegistroPatronal(
			List<RepresentanteLegal> representantesLegales) {
		for(RepresentanteLegal repLegal : representantesLegales){
			repLegal.setCveIdRepresentanteLegal(null);
			DitRepresentanteLegal ditRepresentante = representanteLegalUtility.convertirModelToEntity(repLegal);
			this.em.persist(ditRepresentante);
		}
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<RepresentanteLegal> getRfcRepresentados(
			Long idPersonaRepresentante) {
		
		List<RepresentanteLegal> representados = new ArrayList<RepresentanteLegal>();
		
		String query = "seledct distinct mo.rfc rfc from dit_representante_legal repL, dit_patron_sujeto_obligado so, dit_persona_moral mo"+
		" where so.cve_id_patron_sujeto_obligado = repl.cve_id_patron_sujeto_obligado and so.cve_id_persona_moral = mo.cve_id_persona_moral" +
		" and repl.cve_id_persona = "+idPersonaRepresentante+"  and repL.fec_registro_baja is null union "+
		"select fi.rfc rfc from dit_representante_legal repLF, dit_patron_sujeto_obligado sof, dit_persona_fisica fi" +
		" where replf.cve_id_patron_sujeto_obligado = sof.cve_id_patron_sujeto_obligado and sof.cve_id_persona_fisica = fi.cve_id_persona_fisica" +
		" and replf.cve_id_persona = "+idPersonaRepresentante+"  and repLF.fec_registro_baja is null";
		
		SQLQuery sqlQuery = this.getSession().createSQLQuery(query);		
		List rfcs = sqlQuery.list();
		
		if(rfcs != null && !rfcs.isEmpty()) {
			representados = new ArrayList<RepresentanteLegal>();
			
			for(Object rfc: rfcs) {
				RepresentanteLegal rep = this.getRepresentanteByIdPersonaRepresentanteYRfcRepresentado(idPersonaRepresentante, (String) rfc);
				if(rep != null) {
					representados.add(rep);
				}
			}
		}
		
		return representados;
	}
	
	@Override
	public List<RepresentanteLegal> getRepresentadPorIdPersonaRepresentante(
			Long idPersonaRepresentante) {
		
		List<RepresentanteLegal> representados = new ArrayList<RepresentanteLegal>();
		
		StringBuffer query = new StringBuffer(); 
		query.append(" select rl from DitRepresentanteLegal rl ");
		query.append(" join rl.ditPersona representante");
		query.append(" join rl.ditPersonaFisicaRepresentada personaRepresentada");
		query.append(" join personaRepresentada.ditPersona persona");
		query.append(" where rl.ditPersona.cveIdPersona = "+idPersonaRepresentante);
		query.append(" and rl.fecRegistroBaja is null");
		Query sqlQuery = this.em.createQuery(query.toString());
		@SuppressWarnings("unchecked")
		List<DitRepresentanteLegal> ditRepresentadosFisicos = sqlQuery.getResultList();
		
		StringBuffer queryMoral = new StringBuffer(); 
		queryMoral.append(" select rl from DitRepresentanteLegal rl ");
		queryMoral.append(" join rl.ditPersona representante");
		queryMoral.append(" join rl.ditPersonaMoralRepresentada personaRepresentada");
		queryMoral.append(" where rl.ditPersona.cveIdPersona = "+idPersonaRepresentante);
		queryMoral.append(" and rl.fecRegistroBaja is null"); 
		Query sqlQueryMoral = this.em.createQuery(queryMoral.toString());
		@SuppressWarnings("unchecked")
		List<DitRepresentanteLegal> ditRepresentadosMorales = sqlQueryMoral.getResultList();
		
		List<DitRepresentanteLegal> ditRepresentados = new ArrayList<DitRepresentanteLegal>();
		
		ditRepresentados.addAll(ditRepresentadosFisicos);
		ditRepresentados.addAll(ditRepresentadosMorales);
		
		for(DitRepresentanteLegal ditRepresentante : ditRepresentados){
			RepresentanteLegal representante = representanteLegalUtility.entityToModelDatosBasicosRepresentanteYRepresentado(ditRepresentante);
			representados.add(representante);
		}
		
		return representados;
	}
	

	@Override
	public RepresentanteLegal getRepresentanteByIdPersonaRepresentanteYRfcRepresentado(
			Long idPersona, String rfc) {
		
		RepresentanteLegal representante = null;
		DitRepresentanteLegal ditRepresentante = null;
		Criteria queryRL = this.getSession().createCriteria(DitRepresentanteLegal.class);
		queryRL.createAlias("ditPersona", "persona");
		queryRL.add(Restrictions.eq("persona.cveIdPersona", idPersona));
		queryRL.add(Restrictions.isNull("fecRegistroBaja"));
		queryRL.setMaxResults(1);
		
		//se compara que el rfc no sea nulo
		if(rfc != null) {
			Criteria queryPatron = queryRL.createCriteria("ditPatronSujetoObligado");
			if(rfc.trim().length() == 12) {
				queryPatron.createAlias("ditPersonaMoral", "moral");
				queryPatron.add(Restrictions.eq("moral.rfc", rfc));
			} else {
				queryPatron.createAlias("ditPersonaFisica", "fisica");
				queryPatron.add(Restrictions.eq("fisica.rfc", rfc));
			}
			
			
			
			ditRepresentante = (DitRepresentanteLegal) queryRL.uniqueResult();
			
			if(ditRepresentante != null) {
				representante = representanteLegalUtility.enityToModelDatosBasicos(ditRepresentante);
			}
		}
		
		return representante;
	}

	@Override
	public void borrar(SujetoObligado representado, Long idPersonaRepresentante, Boolean porIdPersona) {
		
		String query = "UPDATE DIT_REPRESENTANTE_LEGAL SET FEC_REGISTRO_BAJA = SYSDATE "+
			"where CVE_ID_PERSONA = " + idPersonaRepresentante + " "+
			"and CVE_ID_PATRON_SUJETO_OBLIGADO in ("+
			"SELECT PAT.CVE_ID_PATRON_SUJETO_OBLIGADO "+
			"from DIT_PATRON_SUJETO_OBLIGADO pat";
		
		if(representado.getMoral() != null) {
			if(!porIdPersona) {
				query += ",DIT_PERSONA_MORAL mor ";
				query += "WHERE mor.CVE_ID_PERSONA_MORAL  = pat.CVE_ID_PERSONA_MORAL ";
				query += " and mor.RFC = '"+representado.getMoral().getRfc()+"') ";
			} else{
				query += " where pat.CVE_ID_PERSONA_MORAL = "+representado.getMoral().getCveMoral()+") ";
			}
		} else {
			if(!porIdPersona) {
				query += ",DIT_PERSONA_FISICA fis ";
				query += "WHERE fis.CVE_ID_PERSONA_FISICA  = pat.CVE_ID_PERSONA_FISICA ";
				query += " and fis.RFC = '"+representado.getFisica().getRfc()+"') ";
			} else {
				query += " where pat.CVE_ID_PERSONA_FISICA= "+representado.getFisica().getCveFisica()+") ";
			}
		}
		
		SQLQuery queryActualizar = this.getSession().createSQLQuery(query);
		queryActualizar.executeUpdate();
		
	}
	
	
	
	
	@Override
	public void registrarBajaRepresentante(RepresentanteLegal representante) {
		String query = "";
		if(representante.getTipoPersonaRepresentada().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)){
			
			query = "UPDATE DIT_REPRESENTANTE_LEGAL SET FEC_REGISTRO_BAJA = SYSDATE "+
			"where CVE_ID_PERSONA = " + representante.getPersonaFisica().getIdPersona() + " "+
			"and CVE_ID_PERSONA_FISICA = "+representante.getPersonaFisicaRepresentada().getCveFisica();
		}else if (representante.getTipoPersonaRepresentada().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_MORAL)){
			query = "UPDATE DIT_REPRESENTANTE_LEGAL SET FEC_REGISTRO_BAJA = SYSDATE "+
					"where CVE_ID_PERSONA = " + representante.getPersonaFisica().getIdPersona() + " "+
					"and CVE_ID_PERSONA_MORAL = "+representante.getPersonaMoralRepresentada().getIdPersona();
		}
		
		SQLQuery queryActualizar = this.getSession().createSQLQuery(query);
		queryActualizar.executeUpdate();
		
	}
	
	@Override
	public void altaRepresentanteLegal(RepresentanteLegal representanteLegal){
		DitRepresentanteLegal ditRepresentanteLegal = new DitRepresentanteLegal();
		
		DicTipoPoder dicTipoPoder=(DicTipoPoder)this.getSession()
			.get(DicTipoPoder.class, representanteLegal.getCveIdTipoPoder());		
		DitPersona personaRepresentante=(DitPersona)this.getSession()
			.get(DitPersona.class, representanteLegal.getPersonaFisica().getIdPersona());
			
		ditRepresentanteLegal.setDicTipoPoder(dicTipoPoder);
		ditRepresentanteLegal.setDitPersona(personaRepresentante);
		ditRepresentanteLegal.setFecRegistroAlta(new Date());
		ditRepresentanteLegal.setFecRegistroActualizado(new Date());
		
		if (representanteLegal.getTipoPersonaRepresentada().getIdTipoPersona().intValue() 
				== TipoPersona.TIPO_PERSONA_FISICA.intValue()){
			final Criteria criteria = this.getSession().createCriteria(DitPersonaFisica.class);
			criteria.createAlias("ditPersona", "ditPersona")
				.add(Restrictions.eq("ditPersona.cveIdPersona", representanteLegal.getCveIdPersona()));
			DitPersonaFisica ditPersonaFisica = (DitPersonaFisica) criteria.list().get(0);
			ditRepresentanteLegal.setDitPersonaFisicaRepresentada(ditPersonaFisica);
			this.getSession().save(ditRepresentanteLegal);
			
			//Se maneja en un try catch por que es una parte del proceso opcional que si no se ejecuta debe continuar el flujo bae
			try{
				Fisica fisica= new Fisica();
				fisica.setCveFisica(ditPersonaFisica.getCveIdPersonaFisica());
				fisica.setIdPersona(ditPersonaFisica.getDitPersona().getCveIdPersona());
				
				calificacionesPersonaBusinessService.calificarSAT(fisica);
			}catch(Exception e){
				log.error("OCURRIO UN ERROR AL CALIFIFAR A LA PERSONA FISICA *******", e);
			}
			
		} else if (representanteLegal.getTipoPersonaRepresentada().getIdTipoPersona().intValue() 
				== TipoPersona.TIPO_PERSONA_MORAL.intValue()){
			DitPersonaMoral ditPersonaMoralRepresentada = (DitPersonaMoral)this.getSession()
				.get(DitPersonaMoral.class, representanteLegal.getCveIdPersona());
			ditRepresentanteLegal.setDitPersonaMoralRepresentada(ditPersonaMoralRepresentada);
			this.getSession().save(ditRepresentanteLegal);
			//Se maneja en un try catch por que es una parte del proceso opcional que si no se ejecuta debe continuar el flujo bae
			try{
				Moral moral = new Moral();
				moral.setCveMoral(ditPersonaMoralRepresentada.getCveIdPersonaMoral());
				calificacionesPersonaBusinessService.calificarSAT(moral);
			}catch(Exception e){
				log.error("OCURRIO UN ERROR AL CALIFIFAR A LA PERSONA MORAL *******", e);
			}
		}
			
	}
	
}
