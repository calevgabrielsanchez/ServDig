package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.socios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.exception.medio.contacto.RegistrarMedioContactoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.personas.gp.PersonasGPServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.socios.SocioUtilityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;
import mx.gob.imss.ctirss.delta.persistence.DgCatEstado;
import mx.gob.imss.ctirss.delta.persistence.DicTipoPersona;
import mx.gob.imss.ctirss.delta.persistence.DitSocio;
import mx.gob.imss.ctirss.delta.persistence.DitSocioContacto;
import mx.gob.imss.ctirss.delta.persistence.DitSocioContactoPK;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

@Stateless(name="socioServiceEntity", mappedName="socioServiceEntity")
public class SocioServiceEntity extends AbstractServiceEntity
		implements SocioServiceEntityLocal {
	
	@EJB
	private SocioUtilityLocal socioUtility;	
	@EJB
	private MediosContactoServiceBusinessRemote mediosContactoService;	
	@EJB
	private PersonasGPServiceEntityLocal personasGPServiceEntity;
	

	@SuppressWarnings("unchecked")
	public List<Socio> obtenerSociosPorIdPersonaMoralPatron(Socio socio){
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("SELECT ditSocio FROM DitSocio ditSocio ");
		jpaQuery.append("WHERE ditSocio.fecRegistroBaja IS NULL ");
		jpaQuery.append("AND ditSocio.ditPatron.cveIdPersonaMoral = :idPersonaMoralPatron ");
		
		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("idPersonaMoralPatron", socio.getIdPersonaMoralPatron());
		
		List<DitSocio> listaDitSocios = query.getResultList();
		if(!CollectionUtils.isEmpty(listaDitSocios)){
			List<Socio> listaSocios = new ArrayList<Socio>();
			for(DitSocio ditSocio : listaDitSocios){
				try {
					listaSocios.add(socioUtility.convertirEntityToModelSocio(ditSocio));
				} catch (Exception e) {
					log.error(e);
				}
			}
			return listaSocios;
		}
		return null;
	}

	public Socio altaSocio(Socio socio){
		DitSocio ditsocio = new DitSocio();
		ditsocio=socioUtility.prepararAltaSocio(socio);
		this.em.persist(ditsocio);
		socio.setIdSocio(ditsocio.getCveIdSocio());
		log.info("Id Socios " + socio.getIdSocio());
		return socio;
	}
	
	public void bajaSocio(Socio socio){
		//Obtener socio
		DitSocio eSocio = obtenerDitSocio(socio.getIdSocio());
		//Actualizar fecha de baja
		if(eSocio!=null){
			eSocio.setFecRegistroBaja(new Date());
			this.em.merge(eSocio);
			log.info("Baja Socios " + socio.getIdSocio());
		}		
	}
		
	public TipoPersona getTipoSocio(Long idTipoPersona) {
		TipoPersona tipoSocio = new TipoPersona();		
		DicTipoPersona dicTipoPersona = (DicTipoPersona) this.getSession().get(DicTipoPersona.class, idTipoPersona);		
		tipoSocio.setIdTipoPersona(dicTipoPersona.getCveIdTipoPersona());
		tipoSocio.setDescripcion(dicTipoPersona.getDesTipoPersona());		
		return tipoSocio;
	}
	
	public DgCatEstado getEstado(String cveEnt) {
		DgCatEstado dgCatEstado = null;
		try {
			dgCatEstado = (DgCatEstado) this.getSession().get(DgCatEstado.class, cveEnt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dgCatEstado;
	}
	
	private DitSocio obtenerDitSocio(Long idSocio) {
		DitSocio entity = this.em.find(DitSocio.class, idSocio);
		return entity;
	}
	
	@Deprecated
	public void eliminarSocio(Socio socio) {
		try {
			DitSocio entity = (DitSocio) this.getSession().load(DitSocio.class, socio.getIdSocio());
			entity.setFecRegistroBaja(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Deprecated
	public Socio agregarSocioFisico(Socio model) {
		try {
			log.debug("Agregando socio");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@Deprecated
	public Socio agregarSocioMoral(Socio model) {		
		try {
			DitSocio convertirModelToEntitySocioMoral = socioUtility.convertirModelToEntity(model);
			convertirModelToEntitySocioMoral.setCveIdSocio(0L);
			this.getSession().save(convertirModelToEntitySocioMoral);			
			// Registramos medios de contacto y los asocioamos al socio fisico
			if(model.getMediosContacto() != null && !model.getMediosContacto().isEmpty()){
				try {					
					List<MedioContacto> mediosContanto = this.mediosContactoService.registrarMedioDeContacto(model.getMediosContacto());					
					for (MedioContacto medioContacto : mediosContanto) {
						this.getSession().save(this.asociarMediosContactoSocio(medioContacto, convertirModelToEntitySocioMoral));
					}						
				} catch (RegistrarMedioContactoException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}			
		} catch (Exception t) {
			t.printStackTrace();
			log.error("Error de jdbc al insertar socio: "+t.getMessage());
		}
		return model;
		
	}

	@Deprecated
	public Socio modificarSocioFisico(Socio model) {
		try {
			this.em.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return model;
	}

	@Deprecated
	public Socio modificarSocioMoral(Socio model) {
		try {
			this.em.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@Deprecated
	public Socio getSocioFisico(Socio socio) {
		Socio socioRetorno = new Socio();
		try {		
			DitSocio entity = this.em.find(DitSocio.class, socio.getIdSocio());			
			if (entity!=null) {
				socioRetorno = socioUtility.convertirEntityToModelSocioFisico(entity); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return socioRetorno;
	}

	@Deprecated
	public Socio getSocioMoral(Socio socio) {
		try {
			log.debug("obteniendo socio moral");
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return socio;
	}

	@Deprecated
	public void validaExisteSocioFisico(Socio socio) throws Exception {
//		DitSocioFisico result = null;

//		Criteria criteria = null;
//		Criteria criteria = this.getSession().createCriteria(
//				DitSocioFisico.class);
//		criteria.createAlias("ditPersona", "ditPersona")
//				.add(Restrictions.eq("ditPersona.cveIdPersona",
//						Long.parseLong(socio.getIdPersona().toString())));
//		criteria.createAlias("ditPatronSujetoObligado",
//				"ditPatronSujetoObligado").add(
//				Restrictions.eq(
//						"ditPatronSujetoObligado.cveIdPatronSujetoObligado",
//						Long.parseLong(socio.getCveIdPatronSujetoObligado().toString())));
//		result = criteria.uniqueResult() != null ? (DitSocioFisico) criteria
//				.uniqueResult() : null;

//		if (result != null) {
//			 String msg = "Ya existe esta persona fisica registrada como socio [" +
//			 result.getDitPersona().getCveIdPersona() + "] asociado con el patrón [" +
//			 result.getDitPatronSujetoObligado().getCveIdPatronSujetoObligado() +"]";
//			log.debug(msg);
//			// Si existe un socio fisico entonces no se cumple con la
//			// regla requerida.
//			throw new Exception(msg);
//		}		
	}

	@Deprecated
	public void validaExisteSocioMoral(Socio socio) throws Exception {
//		DitSocioMoral result = null;
//		Criteria criteria = null;
//		Criteria criteria 
//		= this.getSession().createCriteria(
//				DitSocioMoral.class);
//		criteria.createAlias("ditPersonaMoral", "ditPersonaMoral")
//				.add(Restrictions.eq("ditPersonaMoral.cveIdPersonaMoral",
//						Long.parseLong(socio.getIdPersona().toString())));
//		criteria.createAlias("ditPatronSujetoObligado", "ditPatronSujetoObligado")
//				.add(Restrictions.eq("ditPatronSujetoObligado.cveIdPatronSujetoObligado",
//						Long.parseLong(socio.getCveIdPatronSujetoObligado().toString())));
//		result = criteria.uniqueResult() != null ? (DitSocioMoral) criteria
//				.uniqueResult() : null;
//
//		if (result != null) {
//			 String msg = "Ya existe esta persona moral registrada como socio [" +
//			 result.getDitPersonaMoral().getCveIdPersonaMoral() + "] asociado con el patrón [" +
//			 result.getDitPatronSujetoObligado().getCveIdPatronSujetoObligado() +"]";
//			log.debug(msg);
//			// Si existe un socio fisico entonces no se cumple con la
//			// regla requerida.
//			throw new Exception(msg);
//		}		
	}

	@Deprecated
	public List<Socio> consultarSociosPorSujetoObligado(Socio model) {
//		List<DitSocioFisico> socioFisicoEntities = null;
//		List<DitSocioMoral> socioMoralEntities = null;
		
//		List<Socio> socios = Collections.emptyList();
//		Socio socio = null;
//		try {
//			
//			socioFisicoEntities = consultarSociosFisicosPorPatronSujetoObligado(model);
//			socioMoralEntities = consultarSociosMoralesPorPatronSujetoObligado(model);
//			
//			
//			if( socioFisicoEntities!= null && !socioFisicoEntities.isEmpty()){
//				socios = new ArrayList<Socio>();
//				for(DitSocioFisico entity : socioFisicoEntities){
//					System.out.println("Los socios fisicos encontrados son: "+entity.getCveIdSocioFisico());
//					socio = socioUtility.convertirEntityToModelSocioFisico(entity);
//					socios.add(socio);
//				}
//			}else if (socioMoralEntities!= null && !socioMoralEntities.isEmpty()){
//				
//				if (socios == null){
//					socios = new ArrayList<Socio>();
//				}
//				
//				for(DitSocioMoral entity : socioMoralEntities){
//					System.out.println("Los socios morales encontrados son: "+entity.getCveIdSocioMoral());
//					socio = socioUtility.convertirEntityToModelSocioMoral(entity);
//					socios.add(socio);
//				}
//			} else {
//				return null;
//			}
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//		}
		return null;
	}
	
	@Deprecated	
	@SuppressWarnings("unchecked")
	public List<Socio> consultarSociosPorPerosna(Socio socio) {
		List<Socio> sociosReturn = new ArrayList<Socio>();
		
		Criteria criteria = this.getSession().createCriteria(DitSocio.class);
		criteria.createAlias("ditPatron", "ditPatron").add(Restrictions.eq("ditPatron.cveIdPersonaMoral", socio.getCveIdPatronSujetoObligado()));
		criteria.add(Restrictions.isNull("fecRegistroBaja"));
		
		List<DitSocio> sociosDB = criteria.list();
		
		for (DitSocio ditSocio : sociosDB) {
			try {
				sociosReturn.add(this.socioUtility.convertirEntityToModelSocio(ditSocio));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sociosReturn;
	}

	@Deprecated
	public Socio agregarSocio(Socio socio) {
		try {
			
			Fisica fisica = new Fisica();
			
			fisica.setIdPersona(socio.getIdPersona());
			
			if ( !this.personasGPServiceEntity.existePersonaFisica(fisica) ){
				
				System.err.println("No se encuentra la persona fisica proporcionada desde el servico de \"PERSONAS\", se procede a registrarla en Gestion Patronal...");
				fisica.setRfc(socio.getRfc());
				fisica.setFechaRegistro(new Date());
				
				if (socio.getIdPersona().intValue() == 0){
					System.err.println("Registro de la persona fisica con id: " + socio.getIdPersona() + " no puede realizarse por el momento hasta que nuestro analista nos diga que onda con él porque este id viene nulo desde el serv. Continua el proceso de registrar Socio Fisico.");
					return socio;
				}
				
				DitSocio convertirModelToEntity = socioUtility.convertirModelToEntity(socio);
				
				convertirModelToEntity.setDitPersonaFisica(this.personasGPServiceEntity.registrarPersonaFisica(fisica));
				
				convertirModelToEntity.setCveIdSocio(0L);
				
				this.getSession().save(convertirModelToEntity);
				
				// Registramos medios de contacto y los asocioamos al socio fisico
				if(socio.getMediosContacto() != null && !socio.getMediosContacto().isEmpty()){
					try {
						
						List<MedioContacto> mediosContanto = this.mediosContactoService.registrarMedioDeContacto(socio.getMediosContacto());
						
						for (MedioContacto medioContacto : mediosContanto) {
							this.getSession().save(this.asociarMediosContactoSocio(medioContacto, convertirModelToEntity));
						}						
					} catch (RegistrarMedioContactoException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				
				DitSocio convertirModelToEntity = socioUtility.convertirModelToEntity(socio);
				convertirModelToEntity.setCveIdSocio(0L);
				
				if (socio.getTipoSocio().getIdTipoPersona().intValue() == 1){
					convertirModelToEntity.getDitPersonaFisica()
							.setCveIdPersonaFisica(
									this.personasGPServiceEntity
											.buscarPersonaFisicaPorIdPersona(
													fisica)
											.getCveIdPersonaFisica());
				}
				
				this.getSession().save(convertirModelToEntity);
				
				// Registramos medios de contacto y los asocioamos al socio fisico
				if(socio.getMediosContacto() != null && !socio.getMediosContacto().isEmpty()){
					try {
						
						List<MedioContacto> mediosContanto = this.mediosContactoService.registrarMedioDeContacto(socio.getMediosContacto());
						
						for (MedioContacto medioContacto : mediosContanto) {
							this.getSession().save(this.asociarMediosContactoSocio(medioContacto, convertirModelToEntity));
						}						
					} catch (RegistrarMedioContactoException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch(Exception t){
			t.printStackTrace();
		}
		return socio;
	}

	@Deprecated
	private DitSocioContacto asociarMediosContactoSocio(MedioContacto medioContacto,
			DitSocio convertirModelToEntity) {
		
		DitSocioContactoPK ditSocioContactoPK = new DitSocioContactoPK();
		DitSocioContacto ditSocioContacto = new DitSocioContacto();
		
		ditSocioContactoPK.setCveIdFormaContacto(medioContacto.getClave());
		ditSocioContactoPK.setCveIdSocio(convertirModelToEntity.getCveIdSocio());
					
		ditSocioContacto.setId(ditSocioContactoPK);
		ditSocioContacto.setFecRegistroAlta(new Date());
		
		return ditSocioContacto;
	}

	@Deprecated
	public Socio agregarSocioFideicomiso(Socio socio) {
		try {
			DitSocio convertirModelToEntitySocioFideicomiso = socioUtility.convertirModelToEntity(socio);
			convertirModelToEntitySocioFideicomiso.setCveIdSocio(0L);
			this.getSession().save(convertirModelToEntitySocioFideicomiso);
			
			// Registramos medios de contacto y los asocioamos al socio fisico
			if(socio.getMediosContacto() != null && !socio.getMediosContacto().isEmpty()){
				try {
					
					List<MedioContacto> mediosContanto = this.mediosContactoService.registrarMedioDeContacto(socio.getMediosContacto());
					
					for (MedioContacto medioContacto : mediosContanto) {
						this.getSession().save(this.asociarMediosContactoSocio(medioContacto, convertirModelToEntitySocioFideicomiso));
					}						
				} catch (RegistrarMedioContactoException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception t) {
			t.printStackTrace();
		}
		return socio;
	}

	@Deprecated
	public Socio getSocio(Socio socio) {
		
		DitSocio entity = this.em.find(DitSocio.class, socio.getIdSocio());
		
		if (entity!=null) {
			try {
				socio = socioUtility.convertirEntityToModelSocio(entity);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return socio;
	}

	@Deprecated
	public void modificarSocio(Socio socio) {
		try {
			DitSocio entity = socioUtility.convertirModelToEntity(socio);
			if(socio.getMediosContacto() != null && !socio.getMediosContacto().isEmpty()){
				try {
					//Se eliminan los datos de contacto
					DitSocio ditSocioActual = this.em.find(DitSocio.class, socio.getIdSocio());
					
					for(DitSocioContacto contacto :ditSocioActual.getDitSocioContactos()){
						this.em.remove(contacto);
					}
					
					for(MedioContacto contacto :  socio.getMediosContacto())
						contacto.setClave(null);
					
					List<MedioContacto> mediosContacto = this.mediosContactoService.registrarMedioDeContacto(socio.getMediosContacto());
					
					for (MedioContacto medioContacto : mediosContacto) {
						this.getSession().save(this.asociarMediosContactoSocio(medioContacto, entity));
					}						
				} catch (RegistrarMedioContactoException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
		if(!socio.getEsDomicilioNacional() && !socio.getEsNacional())
			this.em.merge(entity);
			
//			this.em.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Deprecated
	public boolean existenAsignacionesAnterioresSocioPatron(Socio socio) {
		boolean result = false;
		
		StringBuffer sbSocioPatron = new StringBuffer();
		
		sbSocioPatron.append("SELECT ditSocio FROM DitSocio ditSocio");
		sbSocioPatron.append(" WHERE ditSocio.fecRegistroBaja IS NULL");
		
		if (socio.getTipoSocio().getIdTipoPersona().intValue() == 1){ // persona fisica
			sbSocioPatron.append(" AND ditSocio.ditPersonaFisica.ditPersona.cveIdPersona = " + socio.getIdPersona());
		} else { // persona moral y fideicomiso
			sbSocioPatron.append(" AND ditSocio.ditPersonaMoral.cveIdPersonaMoral = " + socio.getIdPersona());
		}
		sbSocioPatron.append(" AND ditSocio.ditPatron.cveIdPersonaMoral = " + socio.getIdPersonaMoralPatron());
		
		Query queryTotal = this.em.createQuery(sbSocioPatron.toString());
		
		if (queryTotal.getResultList().size() > 0){
			result = true;
		}
		
		return result;
	}

	@Deprecated
	public Socio agregarSocioExtranjero(Socio socio) {
		DitSocio entity =null;
		try {
			System.err.println("Insertando socio extranjero..");
			entity = socioUtility.convertirModelToEntity(socio);
			entity.setCveIdSocio(0L);
			this.getSession().save(entity);
//			socio.setIdSocio(entity.getCveIdSocio());
		} catch(HibernateException he){
			System.err.println("Hibernate exception");
			he.printStackTrace();
		}catch (Exception e) {
			System.err.println("exception desconocida");
			e.printStackTrace();
		}
		
		return socio;
	}
	
}
