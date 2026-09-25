/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:MedioContactoServiceEntity.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.entity
 *  @Fecha:11/05/2012
 */
package mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.springframework.util.CollectionUtils;

import mx.gob.imss.ctirss.delta.exception.medio.contacto.PersonaSinMedioDeContactoException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.RegistrarMedioContactoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.utility.MedioContactoServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modulo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoContacto;
import mx.gob.imss.ctirss.delta.persistence.DitFormaContacto;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFContactoFiscal;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMContactoFiscal;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;
import mx.gob.imss.ctirss.delta.persistence.DitPersonafContacto;
import mx.gob.imss.ctirss.delta.persistence.DitPersonamContacto;
import mx.gob.imss.ctirss.delta.persistence.DitTipoContacto;

/**
 * @author Lucio Duran Silva
 *
 */
@Stateless
public class MedioContactoServiceEntity extends AbstractServiceEntity implements
		MedioContactoServiceEntityLocal {
	
	
	
	@EJB MedioContactoServiceUtilityLocal utility;

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.entity.MedioContactoServiceEntityLocal#registrarMedioDeContacto(java.util.List)
	 */
	@Override
	public List<MedioContacto> registrarMedioDeContacto(
			List<MedioContacto> mediosDeContacto)
			throws RegistrarMedioContactoException {
		
		List<MedioContacto> medios = null;
		this.log.debug(" MedioContactoServiceEntity registrarMedioDeContacto");
		List<DitFormaContacto> entites = null;
		try {
			
			entites = this.utility.transformarMedioContacto(mediosDeContacto);
			
			Iterator<DitFormaContacto> it = entites.iterator();
			
			while(it.hasNext()){
				
				DitFormaContacto ditFormaContacto = it.next();
				
				this.em.persist(ditFormaContacto);
				this.log.debug(" DitFormaContacto clave generada :" + ditFormaContacto.getCveIdFormaContacto() );
				
			}
			
			
		} catch (TransformacionException e) {
			this.log.debug("Error al convertir de modelo a entity");
			throw new RegistrarMedioContactoException();
		}
		
		if(entites != null){
			try {
				medios = this.utility.transformarMedioContactoEntities(entites);
			} catch (TransformacionException e) {
				this.log.debug("Error al convertir de entity a modelo");
				throw new RegistrarMedioContactoException();
			}
		}
		//TODO: Para la parte de la transaccionabilidad
		this.em.flush();
		
		return medios;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.entity.MedioContactoServiceEntityLocal#consultarMediosDePersona(mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<MedioContacto> consultarMediosDePersonaFisica(Persona persona)
			throws PersonaSinMedioDeContactoException {
		
		List<MedioContacto>  medios = null;
		if(persona == null){
			this.log.error("No se recibio la persona fisica");
			throw new PersonaSinMedioDeContactoException();
		}
		
		this.log.debug(" Obteniendo los medios de contacto de la persona :::" + persona.getIdPersona());
		
		StringBuffer bfr = new StringBuffer();
		bfr.append(" Select formaContacto from DitPersonafContacto contacto ");
		bfr.append(" join contacto.ditFormaContacto as formaContacto ");
		bfr.append(" where contacto.ditPersona.cveIdPersona = :cveIdPersona ");
		
		Query query = this.em.createQuery(bfr.toString());
		query.setParameter("cveIdPersona", persona.getIdPersona());
		List entities = query.getResultList();
		
		if(entities != null && !entities.isEmpty()){
			
			try {
				medios = this.utility.transformarMedioContactoEntities(entities);
			} catch (TransformacionException e) {
				this.log.error("Error al convertir de entity a model de medios de contacto en la busqueda.");
				throw new PersonaSinMedioDeContactoException();
			}
			
		}else{
			this.log.warn("La persona no tiene medios de contacto");
			throw new PersonaSinMedioDeContactoException();
		}
		
		return medios;
	}
	
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.entity.MedioContactoServiceEntityLocal#consultarMediosDePersona(mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<MedioContacto> consultarMediosDePersona(Persona persona)
			throws PersonaSinMedioDeContactoException {
		
		List<MedioContacto>  medios = null;
		if(persona == null){
			this.log.error("No se recibio la persona fisica");
			throw new PersonaSinMedioDeContactoException();
		}
		
		this.log.debug(" Obteniendo los medios de contacto de la persona :::" + persona.getIdPersona());
		StringBuffer bfr = new StringBuffer();
		
		if(TipoPersona.TIPO_PERSONA_FISICA.equals(persona.getTipoPersona().getIdTipoPersona().longValue())){
			bfr.append(" Select formaContacto from DitPersonafContacto contacto ");
			bfr.append(" join contacto.ditFormaContacto as formaContacto ");
			bfr.append(" where contacto.ditPersona.cveIdPersona = :cveIdPersona ");
		}else if(TipoPersona.TIPO_PERSONA_MORAL.equals(persona.getTipoPersona().getIdTipoPersona().longValue())){
			bfr.append(" Select formaContacto  from DitPersonamContacto contacto ");
			bfr.append(" join contacto.ditFormaContacto as formaContacto ");
			bfr.append(" where contacto.ditPersonaMoral.cveIdPersonaMoral = :cveIdPersona ");
		}
		
		
		Query query = this.em.createQuery(bfr.toString());
		query.setParameter("cveIdPersona", persona.getIdPersona());
		List entities = query.getResultList();
		
		if(entities != null && !entities.isEmpty()){
			medios = this.utility.transformarEntitiesToModel(entities);
		}else{
			this.log.warn("La persona no tiene medios de contacto");
			throw new PersonaSinMedioDeContactoException();
		}
		
		return medios;
	}

	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.entity.MedioContactoServiceEntityLocal#consultarMediosDePersonaMoral(mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<MedioContacto> consultarMediosDePersonaMoral(Persona persona)
			throws PersonaSinMedioDeContactoException {

		List<MedioContacto>  medios = null;
		if(persona == null){
			this.log.error("No se recibio la persona moral");
			throw new PersonaSinMedioDeContactoException();
		}
		
		this.log.debug(" Obteniendo los medios de contacto de la persona moral :::" + persona.getIdPersona());
		
		StringBuffer bfr = new StringBuffer();
		bfr.append(" Select formaContacto  from DitPersonamContacto contacto ");
		bfr.append(" join contacto.ditFormaContacto as formaContacto ");
		bfr.append(" where contacto.ditPersonaMoral.cveIdPersonaMoral = :cveIdPersonaMoral ");
		
		Query query = this.em.createQuery(bfr.toString());
		query.setParameter("cveIdPersonaMoral", persona.getIdPersona());
		
		List entities = query.getResultList();
		
		if(entities != null && !entities.isEmpty()){
			
			try {
				medios = this.utility.transformarMedioContactoEntities(entities);
			} catch (TransformacionException e) {
				this.log.error("Error al convertir de entity a model de medios de contacto en la busqueda.");
				throw new PersonaSinMedioDeContactoException();
			}
			
		}else{
			this.log.warn("La persona no tiene medios de contacto");
			throw new PersonaSinMedioDeContactoException();
		}
		
		return medios;
	}
	
	
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.entity.MedioContactoServiceEntityLocal#registrarMedioDeContacto(java.util.List)
	 */
	@Override
	public List<MedioContacto> actualizarMedioDeContacto(
			List<MedioContacto> mediosDeContacto)
			throws RegistrarMedioContactoException {
		
		List<MedioContacto> medios = null;
		this.log.debug(" MedioContactoServiceEntity registrarMedioDeContacto");
		List<DitFormaContacto> entites = null;
		try {
			
			entites = this.utility.transformarMedioContacto(mediosDeContacto);
			
			Iterator<DitFormaContacto> it = entites.iterator();
			
			while(it.hasNext()){
				
				DitFormaContacto ditFormaContacto = it.next();
				DitFormaContacto currentDitFormaContacto = (DitFormaContacto)this.em.find(DitFormaContacto.class, ditFormaContacto.getCveIdFormaContacto());
				
				currentDitFormaContacto.setDesFormaContacto(ditFormaContacto.getDesFormaContacto());
				currentDitFormaContacto.setFecRegistroActualizado(new Date());
								
				this.log.debug(" DitFormaContacto clave generada :" + ditFormaContacto.getCveIdFormaContacto() );
				this.log.debug(" Dato actualizado :" + ditFormaContacto.getDesFormaContacto() );
			}
			
			
		} catch (TransformacionException e) {
			this.log.debug("Error al convertir de modelo a entity");
			throw new RegistrarMedioContactoException();
		}
		
		if(entites != null){
			try {
				medios = this.utility.transformarMedioContactoEntities(entites);
			} catch (TransformacionException e) {
				this.log.debug("Error al convertir de entity a modelo");
				throw new RegistrarMedioContactoException();
			}
		}

		this.em.flush();
		
		return medios;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<MedioContacto> consultarMediosSocio(Socio socio) {
		
		StringBuffer bfr = new StringBuffer();
		
		bfr.append(" Select formaContacto from DitSocioContacto relacionContacto ");
		bfr.append(" join relacionContacto.ditFormaContacto as formaContacto ");
		bfr.append(" where relacionContacto.id.cveIdSocio = :cveIdSocio ");
		
		Query query = this.em.createQuery(bfr.toString());
		query.setParameter("cveIdSocio", socio.getIdSocio());
		
		List entities = query.getResultList();
		List<MedioContacto> medios = null;
		
		if(entities != null && !entities.isEmpty()){
				medios = this.utility.transformarEntitiesToModel(entities);
		}else{
			this.log.warn("La persona no tiene medios de contacto");
		}
		
		return medios;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<MedioContacto> consultarMediosRepresentanteLegal(
			RepresentanteLegal representante) {
		StringBuffer bfr = new StringBuffer();
		if(representante.getCveIdRepresentanteLegal()==null){
			return new ArrayList<MedioContacto>();
		}
		bfr.append(" Select formaContacto from DitRepresentanteLegalContac relacionContacto ");
		bfr.append(" join relacionContacto.ditFormaContacto as formaContacto ");
		bfr.append(" where relacionContacto.id.cveIdRepresentanteLegal = :cveIdRepresentanteLegal and ");
		bfr.append(" relacionContacto.ditRepresentanteLegal.ditPatronSujetoObligado.cveIdPatronSujetoObligado = :cveIdPatron");
		
		Query query = this.em.createQuery(bfr.toString());
		query.setParameter("cveIdRepresentanteLegal", representante.getCveIdRepresentanteLegal());
		query.setParameter("cveIdPatron", representante.getCveIdPatronSujetoObligado());
		
		List entities = query.getResultList();
		List<MedioContacto> medios = null;
		
		if(entities != null && !entities.isEmpty()){
				medios = this.utility.transformarEntitiesToModel(entities);
		}else{
			this.log.warn("La persona no tiene medios de contacto");
		}
		
		return medios;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<MedioContacto> consultarMediosCentroTrabajo(
			CentroTrabajo centroTrabajo) {
		StringBuffer bfr = new StringBuffer();
		
		bfr.append(" Select formaContacto from DitCentroTrabajoContacto relacionContacto ");
		bfr.append(" join relacionContacto.ditFormaContacto as formaContacto ");
		bfr.append(" where relacionContacto.ditPatronSujetoObligado.cveIdPatronSujetoObligado = :cveIdPatronSujetoObligado ");
		
		Query query = this.em.createQuery(bfr.toString());
		query.setParameter("cveIdPatronSujetoObligado", centroTrabajo.getCveIdPatronSujetoObligado());
		
		List entities = query.getResultList();
		List<MedioContacto> medios = null;
		try {
			if(entities != null && !entities.isEmpty()){
					medios = this.utility.transformarMedioContactoEntities(entities);
			}else{
				this.log.warn("La persona no tiene medios de contacto");
			}
		} catch (TransformacionException e) {
			e.printStackTrace();
		}
		return medios;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Modulo> consultarModulosdeTramitePorTipo(Long idTipoTramite) {
		StringBuffer bfr = new StringBuffer();
		List<Modulo> modulos = new ArrayList<Modulo>();
		bfr.append(" Select dicModulo from DicTipoTramite tipoTramite ");
		bfr.append(" join tipoTramite.dicModulo as dicModulo ");
		bfr.append(" where tipoTramite.cveIdTipoTramite = :cveIdTipoTramite ");
		
		Query query = this.em.createQuery(bfr.toString());
		query.setParameter("cveIdTipoTramite", idTipoTramite);
		
		List entities = query.getResultList();
		
		if(entities != null && !entities.isEmpty()){
			try {
				modulos = this.utility.transformarModuloEntities(entities);
			} catch (TransformacionException e) {
				this.log.error("Error al convertir de entity a model de modulos en la busqueda.");
			}
		}else{
			this.log.warn("No hay modulos asociados a este trámite");
		}
		
		return modulos;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<TipoContacto> consultarTiposContacto() {
		List<TipoContacto> tipos = new ArrayList<TipoContacto>();
		
		StringBuffer bfr = new StringBuffer();
		bfr.append(" Select tipoContacto from DitTipoContacto tipoContacto ");
		
		Query query = this.em.createQuery(bfr.toString());
		
		
		List entities = query.getResultList();
		
		if(entities != null && !entities.isEmpty()){
				tipos = this.utility.transformarTipoContactoEntities(entities);
		}else{
			this.log.warn("No hay tipos de contacto en la base de datos");
		}
		return tipos;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<MedioContacto> consultarMediosFiscalesPersona(Persona persona)
			throws PersonaSinMedioDeContactoException {
		
		List<MedioContacto>  medios = null;
		
		StringBuffer jpaQuery = new StringBuffer();
		Long cvePersona = null;
		
		if(persona instanceof Fisica){
			
			boolean buscarConIdFiscal = true;
			
			// Se checa con qué id buscar, si con el de la persona física o con el idPersona
			if(((Fisica) persona).getCveFisica() == null) {
				cvePersona = persona.getIdPersona();
				buscarConIdFiscal = false;
			} else {
				cvePersona = ((Fisica) persona).getCveFisica();
			}

			this.log.debug("Obteniendo los medios de contacto de la persona física " + cvePersona);
			
			jpaQuery.append("select contacto.ditFormaContacto ");
			jpaQuery.append("from DitPersonaFContactoFiscal contacto ");
			
			if (buscarConIdFiscal) {
				jpaQuery.append("where contacto.ditPersonaFisica.cveIdPersonaFisica = :cveIdPersona ");
			} else {
				jpaQuery.append("where contacto.ditPersonaFisica.ditPersona.cveIdPersona = :cveIdPersona ");
			}
		}else if(persona instanceof Moral){
		
			// Se checa con qué id buscar, si con el de la persona moral o con el idPersona
			if(((Moral) persona).getCveMoral() == null) {
				cvePersona = persona.getIdPersona();
			} else {
				cvePersona = ((Moral) persona).getCveMoral();
			}			
			this.log.debug("Obteniendo los medios de contacto de la persona moral " + cvePersona);
			
			jpaQuery.append("select contacto.ditFormaContacto ");
			jpaQuery.append("from DitPersonaMContactoFiscal contacto ");
			jpaQuery.append("where contacto.ditPersonaMoral.cveIdPersonaMoral = :cveIdPersona ");
			
		}else{
			return null;
		}
		
		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("cveIdPersona", cvePersona);
		
		List entities = query.getResultList();
		
		if(entities != null && !entities.isEmpty()){
			try {
				medios = this.utility.transformarMedioContactoEntities(entities);
			} catch (TransformacionException e) {
				this.log.error("Error al convertir de entity a model de medios de contacto en la busqueda.");
				throw new PersonaSinMedioDeContactoException();
			}
		}else{
			this.log.warn("La persona no tiene medios de contacto");
			throw new PersonaSinMedioDeContactoException();
		}
		
		return medios;
	}
	
	@Override
	public MedioContacto registrarMedioDeContacto(MedioContacto medioContacto)
			throws RegistrarMedioContactoException {

		this.log.debug(" MedioContactoServiceEntity registrarMedioDeContacto");

		/*
		 * Para no duplicar métodos se reutiliza el método de
		 * registrarMedioDeContacto(List<MedioContacto>) y es por eso que se
		 * crea una lista que sólo incluye el medio de contacto que se recibe
		 */
		List<MedioContacto> medios = new ArrayList<MedioContacto>();
		medios.add(medioContacto);

		medios = registrarMedioDeContacto(medios);

		return medios.get(0);

	}
	
	@Override
	public MedioContacto actualizarMedioDeContacto(MedioContacto medioContacto)
			throws RegistrarMedioContactoException {
		
		this.log.debug("MedioContactoServiceEntity actualizarMedioDeContacto");

		/*
		 * Para no duplicar métodos se reutiliza el método de
		 * actualizarMedioDeContacto(List<MedioContacto>) y es por eso que se
		 * crea una lista que sólo incluye el medio de contacto que se recibe
		 */
		List<MedioContacto> medios = new ArrayList<MedioContacto>();
		medios.add(medioContacto);

		medios = actualizarMedioDeContacto(medios);

		return medios.get(0);
	}
	
	@Override
	public void eliminarMedioDeContacto(MedioContacto medioContacto) {
		
		this.log.debug("MedioContactoServiceEntity eliminarMedioDeContacto");
		
		DitFormaContacto ditFormaContacto = this.em.find(DitFormaContacto.class, medioContacto.getClave());
		this.em.remove(ditFormaContacto);
		
	}
	
	@Override
	public void asociarMedioContactoPersona(Long cveMedioContacto,
			Long cvePersona) throws RegistrarMedioContactoException {
		
		this.log.debug("Se va a asociar el medio de contacto ["
				+ cveMedioContacto + "] a la persona [" + cvePersona + "]");
		
		if(cveMedioContacto != null && cvePersona != null){
			DitPersona ditPersona= new DitPersona();
			ditPersona.setCveIdPersona(cvePersona);

			DitFormaContacto ditFormaContacto = new DitFormaContacto();
			ditFormaContacto.setCveIdFormaContacto(cveMedioContacto);
			
			DitPersonafContacto ditPersonafContacto = new DitPersonafContacto();
			ditPersonafContacto.setDitPersona(ditPersona);
			ditPersonafContacto.setDitFormaContacto(ditFormaContacto);
			ditPersonafContacto.setFecRegistroAlta(new Date());
			
			this.em.persist(ditPersonafContacto);

		}else{
			throw new RegistrarMedioContactoException(
					"No se puede guardar la relación medio contacto - persona ya que no se cuenta con las claves de ambas entidades");
		}
	}

	@Override
	public void asociarMedioContactoPersonaMoral(Long cveMedioContacto,
			Long cvePersonaMoral) throws RegistrarMedioContactoException {
		
		this.log.debug("Se va a asociar el medio de contacto ["
				+ cveMedioContacto + "] a la persona moral [" + cvePersonaMoral + "]");
		
		if(cveMedioContacto != null && cvePersonaMoral != null){
			DitPersonaMoral ditPersonaMoral = new DitPersonaMoral();
			ditPersonaMoral.setCveIdPersonaMoral(cvePersonaMoral);

			DitFormaContacto ditFormaContacto = new DitFormaContacto();
			ditFormaContacto.setCveIdFormaContacto(cveMedioContacto);
			
			DitPersonamContacto ditPersonamContacto = new DitPersonamContacto();
			ditPersonamContacto.setDitPersonaMoral(ditPersonaMoral);
			ditPersonamContacto.setDitFormaContacto(ditFormaContacto);
			ditPersonamContacto.setFecRegistroAlta(new Date());
			
			this.em.persist(ditPersonamContacto);

		}else{
			throw new RegistrarMedioContactoException(
					"No se puede guardar la relación medio contacto - persona moral ya que no se cuenta con las claves de ambas entidades");
		}
		
	}
	
	@Override
	public DitPersonafContacto registrarMedioContactoPersonafContacto(Long cveMedioContacto,
			Long cvePersona) throws RegistrarMedioContactoException {
		
		DitPersonafContacto ditPersonafContacto = null;
		
		this.log.debug("Se va a asociar el medio de contacto ["+ cveMedioContacto + "] a la persona [" + cvePersona + "]");
		
		if(cveMedioContacto != null && cvePersona != null){
			DitPersona ditPersona= new DitPersona();
			ditPersona.setCveIdPersona(cvePersona);

			DitFormaContacto ditFormaContacto = new DitFormaContacto();
			ditFormaContacto.setCveIdFormaContacto(cveMedioContacto);
			
			ditPersonafContacto = new DitPersonafContacto();
			ditPersonafContacto.setDitPersona(ditPersona);
			ditPersonafContacto.setDitFormaContacto(ditFormaContacto);
			ditPersonafContacto.setFecRegistroAlta(new Date());
			
			this.em.persist(ditPersonafContacto);
			this.em.flush();
		}else{
			throw new RegistrarMedioContactoException("No se puede guardar la relación medio contacto - persona ya que no se cuenta con las claves de ambas entidades");
		}
		return ditPersonafContacto;
	}

	@Override
	public DitPersonamContacto registrarMedioContactoPersonamContacto(Long cveMedioContacto,
			Long cvePersonaMoral) throws RegistrarMedioContactoException {
		
		DitPersonamContacto ditPersonamContacto = null;
		
		this.log.debug("Se va a asociar el medio de contacto ["+ cveMedioContacto + "] a la persona moral [" + cvePersonaMoral + "]");
		
		if(cveMedioContacto != null && cvePersonaMoral != null){
			DitPersonaMoral ditPersonaMoral = new DitPersonaMoral();
			ditPersonaMoral.setCveIdPersonaMoral(cvePersonaMoral);

			DitFormaContacto ditFormaContacto = new DitFormaContacto();
			ditFormaContacto.setCveIdFormaContacto(cveMedioContacto);
			
			ditPersonamContacto = new DitPersonamContacto();
			ditPersonamContacto.setDitPersonaMoral(ditPersonaMoral);
			ditPersonamContacto.setDitFormaContacto(ditFormaContacto);
			ditPersonamContacto.setFecRegistroAlta(new Date());
			
			this.em.persist(ditPersonamContacto);
			this.em.flush();
		}else{
			throw new RegistrarMedioContactoException("No se puede guardar la relación medio contacto - persona moral ya que no se cuenta con las claves de ambas entidades");
		}
		return ditPersonamContacto;
	}
	
	@Override
	public void asociarMedioContactoFiscalPersonaFisica(Long cveMedioContacto,
			Long cvePersonaFisica) throws RegistrarMedioContactoException{
		
		this.log.debug("Se va a asociar el medio de contacto fiscal ["
				+ cveMedioContacto + "] a la persona fisica [" + cvePersonaFisica + "]");
		
		if(cveMedioContacto != null && cvePersonaFisica != null){
			DitPersonaFisica ditPersonaFisica = new DitPersonaFisica();
			ditPersonaFisica.setCveIdPersonaFisica(cvePersonaFisica);

			DitFormaContacto ditFormaContacto = new DitFormaContacto();
			ditFormaContacto.setCveIdFormaContacto(cveMedioContacto);
			
			DitPersonaFContactoFiscal ditPersonaFContactoFiscal = new DitPersonaFContactoFiscal();
			ditPersonaFContactoFiscal.setDitPersonaFisica(ditPersonaFisica);
			ditPersonaFContactoFiscal.setDitFormaContacto(ditFormaContacto);
			ditPersonaFContactoFiscal.setFecRegistroAlta(new Date());
			
			this.em.persist(ditPersonaFContactoFiscal);

		}else{
			throw new RegistrarMedioContactoException(
					"No se puede guardar la relación medio contacto fiscal - persona física ya que no se cuenta con las claves de ambas entidades");
		}
		
	}
	
	@Override
	public void asociarMedioContactoFiscalPersonaMoral(Long cveMedioContacto,
			Long cvePersonaMoral) throws RegistrarMedioContactoException{
		
		this.log.debug("Se va a asociar el medio de contacto fiscal ["
				+ cveMedioContacto + "] a la persona moral [" + cvePersonaMoral + "]");
		
		if(cveMedioContacto != null && cvePersonaMoral != null){
			DitPersonaMoral ditPersonaMoral = new DitPersonaMoral();
			ditPersonaMoral.setCveIdPersonaMoral(cvePersonaMoral);

			DitFormaContacto ditFormaContacto = new DitFormaContacto();
			ditFormaContacto.setCveIdFormaContacto(cveMedioContacto);
			
			DitPersonaMContactoFiscal ditPersonaMContactoFiscal = new DitPersonaMContactoFiscal();
			ditPersonaMContactoFiscal.setDitPersonaMoral(ditPersonaMoral);
			ditPersonaMContactoFiscal.setDitFormaContacto(ditFormaContacto);
			ditPersonaMContactoFiscal.setFecRegistroAlta(new Date());
			
			this.em.persist(ditPersonaMContactoFiscal);

		}else{
			throw new RegistrarMedioContactoException(
					"No se puede guardar la relación medio contacto fiscal - persona moral ya que no se cuenta con las claves de ambas entidades");
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean existenciaCorreoPersonaPorId(Long idPersona, String correo){
		StringBuffer bfr = new StringBuffer();
		bfr.append(" SELECT pc.ditFormaContacto FROM DitPersonafContacto pc ");
		bfr.append(" WHERE pc.ditPersona.cveIdPersona = :idPersona ");
		bfr.append(" AND pc.ditFormaContacto.desFormaContacto = :correo  ");
		Query query = this.em.createQuery(bfr.toString());
		query.setParameter("idPersona", idPersona);
		query.setParameter("correo", correo);
		List<DitFormaContacto> listaFormacontactos = (List<DitFormaContacto>)query.getResultList();
		if(!CollectionUtils.isEmpty(listaFormacontactos)){
			return true;
		}
		return false;
	}
	
	@Override
	public void asociarMedioContactoPersona(Long idPersona, 
			String valor, Long tipoMedio){		

		//Forma de Contacto
		DitFormaContacto ditFormaContacto = guardarDitFormaContacto(valor, tipoMedio);
		//Persona
		DitPersona ditPersona= new DitPersona();
		ditPersona.setCveIdPersona(idPersona);
		//Persona-Contacto
		DitPersonafContacto ditPersonafContacto = new DitPersonafContacto();
		ditPersonafContacto.setDitPersona(ditPersona);
		ditPersonafContacto.setDitFormaContacto(ditFormaContacto);
		ditPersonafContacto.setFecRegistroAlta(new Date());
		ditPersonafContacto.setFecRegistroActualizado(new Date());
		
		this.em.persist(ditPersonafContacto);
	}
		
	private DitFormaContacto guardarDitFormaContacto(String valor, Long tipoMedio){
		//Forma de Contacto
		DitFormaContacto ditFormaContacto = new DitFormaContacto();
		ditFormaContacto.setDitTipoContacto(new DitTipoContacto());
		ditFormaContacto.getDitTipoContacto().setCveIdTipoContacto(tipoMedio);
		ditFormaContacto.setDesFormaContacto(valor);
		ditFormaContacto.setFecRegistroAlta(new Date());
		ditFormaContacto.setFecRegistroActualizado(new Date());
		this.em.persist(ditFormaContacto);
		return ditFormaContacto;
	}
	
	
}

