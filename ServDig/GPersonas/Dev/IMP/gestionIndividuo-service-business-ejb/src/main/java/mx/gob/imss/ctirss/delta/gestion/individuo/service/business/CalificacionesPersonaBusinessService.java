package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.springframework.util.CollectionUtils;

import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.CalificacionesNoExistentesException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.utility.CalificacionesPersonaUtilityServiceLocal;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.CalificacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.persistence.DitHistPersonaCalificacion;
import mx.gob.imss.ctirss.delta.persistence.DitHistPersonaCalificacionPK;
import mx.gob.imss.ctirss.delta.persistence.DitHistPersonaMoralCalific;
import mx.gob.imss.ctirss.delta.persistence.DitHistPersonaMoralCalificPK;
import mx.gob.imss.ctirss.gestionpersonas.servicios.entity.CalificacionesPersonaEntityServiceLocal;

@Stateless(name = "calificacionesPersonaBusinessService", mappedName = "calificacionesPersonaBusinessService")
public class CalificacionesPersonaBusinessService extends
		AbstractServiceBusiness implements
		CalificacionesPersonaBusinessServiceRemote {

	@EJB
	CalificacionesPersonaEntityServiceLocal calificacionesPersonaEntityService;

	@EJB
	CalificacionesPersonaUtilityServiceLocal calificacionesPersonaUtilityService;

	@Override
	public void registrar(Fisica fisica)
			throws CalificacionesNoExistentesException {

		List<PersonaCalificacion> personaCalificaciones = fisica
				.getPersonaCalificaciones();
		
		if (personaCalificaciones == null) {
			throw new CalificacionesNoExistentesException(
					"No se recibieron calificaciones a registrar para la persona fisica");
		}

		for (PersonaCalificacion personaCalificacion : personaCalificaciones) {
			DitHistPersonaCalificacion ditHistPersonaCalificacion = calificacionesPersonaUtilityService
					.transformarAEntidad(personaCalificacion, fisica);
			calificacionesPersonaEntityService
					.registrar(ditHistPersonaCalificacion);
		}

	}
	
	@Override
	public void registrar(Moral moral)
			throws CalificacionesNoExistentesException {

		List<PersonaCalificacion> personaCalificaciones = moral
				.getPersonaCalificaciones();
		
		if (personaCalificaciones == null) {
			throw new CalificacionesNoExistentesException(
					"No se recibieron calificaciones a registrar para la persona moral");
		}

		for (PersonaCalificacion personaCalificacion : personaCalificaciones) {
			DitHistPersonaMoralCalific ditHistPersonaMoralCalific = calificacionesPersonaUtilityService
					.transformarAEntidad(personaCalificacion, moral);
			calificacionesPersonaEntityService
					.registrar(ditHistPersonaMoralCalific);
		}
		
	}

	@Override
	public void expirar(Fisica fisica, PersonaCalificacion personaCalificacion) {
		
		DitHistPersonaCalificacion ditHistPersonaCalificacion = this.calificacionesPersonaUtilityService
				.transformarAEntidad(personaCalificacion, fisica);
		
		this.calificacionesPersonaEntityService.expirar(ditHistPersonaCalificacion);
	}
	
	@Override
	public void expirar(Moral moral, PersonaCalificacion personaCalificacion) {
		
		DitHistPersonaMoralCalific ditHistPersonaMoralCalific = this.calificacionesPersonaUtilityService
				.transformarAEntidad(personaCalificacion, moral);
		
		this.calificacionesPersonaEntityService.expirar(ditHistPersonaMoralCalific);
		
	}

	@Override
	public List<PersonaCalificacion> obtenerCalificacionesVigentes(Fisica fisica)
			throws PersonaSinCalificacionesException {

		return this.obtenerCalificacionesCommon(fisica, true);
		
	}
	
	@Override
	public List<PersonaCalificacion> obtenerCalificacionesVigentes(Moral moral)
			throws PersonaSinCalificacionesException {
		
		return this.obtenerCalificacionesCommon(moral, true);
	}

	@Override
	public List<PersonaCalificacion> obtenerCalificaciones(Fisica fisica)
			throws PersonaSinCalificacionesException {

		return this.obtenerCalificacionesCommon(fisica, false);
		
	}
	
	@Override
	public List<PersonaCalificacion> obtenerCalificaciones(Moral moral)
			throws PersonaSinCalificacionesException {
		
		return this.obtenerCalificacionesCommon(moral, false);
		
	}

	@Override
	public void calificarIMSS(Fisica fisica)
			throws PersonaSinCalificacionesException {

		if (fisica.getIdPersona() == null) {
			throw new PersonaSinCalificacionesException(
					"El id de la persona fisica es necesario para dar de alta la calificacion IMSS");
		}

		// Primero se obtienen todas las calificaciones de la persona
		List<DitHistPersonaCalificacion> califPersona = this.calificacionesPersonaEntityService
				.consultarCalificacionesPersona(fisica, false);

		/*
		 * Se checa si dentro de las calificaciones se encuentra la
		 * calificación IMSS, de ser así se actualiza la fecha, en caso
		 * contrario se crea desde cero y, para ambos casos, las otras
		 * calificaciones se expiran
		 */
		if (califPersona != null && !califPersona.isEmpty()) {
			boolean cuentaConCalifIMSS = false;

			for (DitHistPersonaCalificacion ditHistPersonaCalificacion : califPersona) {

				if (ditHistPersonaCalificacion.getId().getCveIdCalificacion()
						.longValue() == CalificacionEnum.VALIDADO_IMSS
						.getCodigo().longValue()) {
					cuentaConCalifIMSS = true;

					if (ditHistPersonaCalificacion.getFecRegistroBaja() != null) {
						// Se reactiva la calificación
						this.calificacionesPersonaEntityService
								.reactivar(ditHistPersonaCalificacion);
					} else {
						// Se actualiza la fecha para la calificación RENAPO
						this.calificacionesPersonaEntityService
								.actualizar(ditHistPersonaCalificacion);
					}
				} else {
					// No es calificación IMSS se debe expirar
					if (ditHistPersonaCalificacion.getFecRegistroBaja() == null) {
						this.calificacionesPersonaEntityService
								.expirar(ditHistPersonaCalificacion);
					}
				}
			}

			if (!cuentaConCalifIMSS) {
				// Se da de alta la nueva calificación IMSS
				DitHistPersonaCalificacion ditHistPersonaCalificacion = crearCalificacionAuxCommon(fisica,
						CalificacionEnum.VALIDADO_IMSS);
				this.calificacionesPersonaEntityService
						.registrar(ditHistPersonaCalificacion);
			}
		} else {
			/*
			 * No se tienen calificaciones, por lo tanto, se da de alta
			 * la calificación IMSS
			 */
			DitHistPersonaCalificacion ditHistPersonaCalificacion = crearCalificacionAuxCommon(fisica,
					CalificacionEnum.VALIDADO_IMSS);
			this.calificacionesPersonaEntityService
					.registrar(ditHistPersonaCalificacion);
		}
	}
	
	@Override
	public void calificarIMSS(Moral moral)
			throws PersonaSinCalificacionesException {

		if (moral.getCveMoral() == null) {
			throw new PersonaSinCalificacionesException(
					"El id de la persona moral es necesario para consultar las calificaciones");
		}

		// Primero se obtienen todas las calificaciones de la persona
		List<DitHistPersonaMoralCalific> califPersona = this.calificacionesPersonaEntityService
				.consultarCalificacionesPersonaMoral(moral, false);

		/*
		 * Se checa si dentro de las calificaciones se encuentra la
		 * calificación IMSS, de ser así se actualiza la fecha, en caso
		 * contrario se crea desde cero y, para ambos casos, las otras
		 * calificaciones se expiran
		 */
		if (califPersona != null && !califPersona.isEmpty()) {
			boolean cuentaConCalifIMSS = false;

			for (DitHistPersonaMoralCalific ditHistPersonaMoralCalific : califPersona) {

				if (ditHistPersonaMoralCalific.getId().getCveIdCalificacion() == CalificacionEnum.VALIDADO_IMSS
						.getCodigo().longValue()) {
					cuentaConCalifIMSS = true;

					if (ditHistPersonaMoralCalific.getFecRegistroBaja() != null) {
						// Se reactiva la calificación
						this.calificacionesPersonaEntityService
								.reactivar(ditHistPersonaMoralCalific);
					} else {
						// Se actualiza la fecha para la calificación RENAPO
						this.calificacionesPersonaEntityService
								.actualizar(ditHistPersonaMoralCalific);
					}
				} else {
					// No es calificación IMSS se debe expirar
					if (ditHistPersonaMoralCalific.getFecRegistroBaja() == null) {
						this.calificacionesPersonaEntityService
								.expirar(ditHistPersonaMoralCalific);
					}
				}
			}

			if (!cuentaConCalifIMSS) {
				// Se da de alta la nueva calificación IMSS
				DitHistPersonaMoralCalific ditHistPersonaMoralCalific = crearCalificacionAuxCommon(moral,
						CalificacionEnum.VALIDADO_IMSS);
				this.calificacionesPersonaEntityService.registrar(ditHistPersonaMoralCalific);
			}
		} else {
			/*
			 * No se tienen calificaciones, por lo tanto, se da de alta
			 * la calificación IMSS
			 */
			DitHistPersonaMoralCalific ditHistPersonaMoralCalific = crearCalificacionAuxCommon(moral,
					CalificacionEnum.VALIDADO_IMSS);
			this.calificacionesPersonaEntityService.registrar(ditHistPersonaMoralCalific);
		}
	}

	@Override
	public void calificarRENAPO(Fisica fisica)
			throws PersonaSinCalificacionesException {

		if (fisica.getIdPersona() == null) {
			throw new PersonaSinCalificacionesException(
					"El id de la persona es necesario para dar de alta la calificacion RENAPO");
		}

		// Primero se obtienen todas las calificaciones de la persona
		List<DitHistPersonaCalificacion> califPersona = this.calificacionesPersonaEntityService
				.consultarCalificacionesPersona(fisica, false);
		
		/*
		 * Se checa si dentro de las calificaciones se encuentra la
		 * calificación RENAPO, de ser así se actualiza la fecha, en caso
		 * contrario se crea desde cero y, para ambos casos, las otras
		 * calificaciones se deben expirar excepto la calificación SAT
		 */
		if (califPersona != null && !califPersona.isEmpty()) {
			boolean cuentaConCalifRENAPO = false;

			for (DitHistPersonaCalificacion ditHistPersonaCalificacion : califPersona) {

				if (ditHistPersonaCalificacion.getId().getCveIdCalificacion()
						.longValue() == CalificacionEnum.VALIDADO_RENAPO
						.getCodigo().longValue()) {
					cuentaConCalifRENAPO = true;
					
					if (ditHistPersonaCalificacion.getFecRegistroBaja() != null) {
						// Se reactiva la calificación
						this.calificacionesPersonaEntityService
								.reactivar(ditHistPersonaCalificacion);
					} else {
						// Se actualiza la fecha para la calificación RENAPO
						this.calificacionesPersonaEntityService
								.actualizar(ditHistPersonaCalificacion);
					}
				} else if (ditHistPersonaCalificacion.getId().getCveIdCalificacion()
						.longValue() != CalificacionEnum.VALIDADO_SAT
						.getCodigo().longValue()) {

					// No es calificación RENAPO ni SAT, se debe expirar
					if (ditHistPersonaCalificacion.getFecRegistroBaja() == null) {
						this.calificacionesPersonaEntityService
								.expirar(ditHistPersonaCalificacion);
					}
				}
			}

			if (!cuentaConCalifRENAPO) {
				// Se da de alta la nueva calificación RENAPO
				DitHistPersonaCalificacion ditHistPersonaCalificacion = crearCalificacionAuxCommon(fisica,
						CalificacionEnum.VALIDADO_RENAPO);
				this.calificacionesPersonaEntityService
						.registrar(ditHistPersonaCalificacion);
			}
		} else {
			/*
			 * No se tienen calificaciones, por lo tanto, se da de alta
			 * la calificación RENAPO
			 */
			DitHistPersonaCalificacion ditHistPersonaCalificacion = crearCalificacionAuxCommon(fisica,
					CalificacionEnum.VALIDADO_RENAPO);
			this.calificacionesPersonaEntityService
					.registrar(ditHistPersonaCalificacion);
		}
	}

	@Override
	public void calificarSAT(Fisica fisica)
			throws PersonaSinCalificacionesException {

		if (fisica.getIdPersona() == null) {
			throw new PersonaSinCalificacionesException(
					"El id de la persona es necesario para dar de alta la calificacion SAT");
		}

		// Primero se obtienen todas las calificaciones de la persona
		List<DitHistPersonaCalificacion> califPersona = this.calificacionesPersonaEntityService
				.consultarCalificacionesPersona(fisica, false);

		/*
		 * Se checa si dentro de las calificaciones se encuentra la
		 * calificación SAT, de ser así se actualiza la fecha, en caso
		 * contrario se crea desde cero y, para ambos casos, las otras
		 * calificaciones se deben expirar excepto la calificación RENAPO
		 */
		if (califPersona != null && !califPersona.isEmpty()) {
			boolean cuentaConCalifSAT = false;

			for (DitHistPersonaCalificacion ditHistPersonaCalificacion : califPersona) {

				if (ditHistPersonaCalificacion.getId().getCveIdCalificacion()
						.longValue() == CalificacionEnum.VALIDADO_SAT
						.getCodigo().longValue()) {
					cuentaConCalifSAT = true;

					if (ditHistPersonaCalificacion.getFecRegistroBaja() != null) {
						// Se reactiva la calificación
						this.calificacionesPersonaEntityService
								.reactivar(ditHistPersonaCalificacion);
					} else {
						// Se actualiza la fecha para la calificación RENAPO
						this.calificacionesPersonaEntityService
								.actualizar(ditHistPersonaCalificacion);
					}
				} else if (ditHistPersonaCalificacion.getId()
						.getCveIdCalificacion().longValue() != CalificacionEnum.VALIDADO_RENAPO
						.getCodigo().longValue()) {
					// No es calificación RENAPO ni SAT, se debe expirar
					if (ditHistPersonaCalificacion.getFecRegistroBaja() == null) {
						this.calificacionesPersonaEntityService
								.expirar(ditHistPersonaCalificacion);
					}

				}
			}

			if (!cuentaConCalifSAT) {
				// Se da de alta la nueva calificación SAT
				DitHistPersonaCalificacion ditHistPersonaCalificacion = crearCalificacionAuxCommon(fisica,
						CalificacionEnum.VALIDADO_SAT);
				this.calificacionesPersonaEntityService
						.registrar(ditHistPersonaCalificacion);
			}
		} else {
			/*
			 * No se tienen calificaciones, por lo tanto, se da de alta
			 * la calificación SAT
			 */
			DitHistPersonaCalificacion ditHistPersonaCalificacion = crearCalificacionAuxCommon(fisica,
					CalificacionEnum.VALIDADO_SAT);
			this.calificacionesPersonaEntityService
					.registrar(ditHistPersonaCalificacion);
		}
	}
	
	@Override
	public void calificarSAT(Moral moral) throws PersonaSinCalificacionesException {
	
		if (moral.getCveMoral() == null) {
			throw new PersonaSinCalificacionesException(
					"El id de la persona moral es necesario para consultar las calificaciones");
		}

		// Primero se obtienen todas las calificaciones de la persona
		List<DitHistPersonaMoralCalific> califPersona = this.calificacionesPersonaEntityService
				.consultarCalificacionesPersonaMoral(moral, false);

		/*
		 * Se checa si dentro de las calificaciones se encuentra la
		 * calificación SAT, de ser así se actualiza la fecha, en caso
		 * contrario se crea desde cero y, para ambos casos, las otras
		 * calificaciones se deben expirar excepto la calificación RENAPO
		 */
		if (califPersona != null && !califPersona.isEmpty()) {
			boolean cuentaConCalifSAT = false;

			for (DitHistPersonaMoralCalific ditHistPersonaMoralCalific : califPersona) {

				if (ditHistPersonaMoralCalific.getId().getCveIdCalificacion() == CalificacionEnum.VALIDADO_SAT
						.getCodigo().longValue()) {
					cuentaConCalifSAT = true;

					if (ditHistPersonaMoralCalific.getFecRegistroBaja() != null) {
						// Se reactiva la calificación
						this.calificacionesPersonaEntityService
								.reactivar(ditHistPersonaMoralCalific);
					} else {
						// Se actualiza la fecha para la calificación RENAPO
						this.calificacionesPersonaEntityService
								.actualizar(ditHistPersonaMoralCalific);
					}
				} else {
					// No es calificación SAT, se debe expirar
					if (ditHistPersonaMoralCalific.getFecRegistroBaja() == null) {
						this.calificacionesPersonaEntityService
								.expirar(ditHistPersonaMoralCalific);
					}

				}
			}

			if (!cuentaConCalifSAT) {
				// Se da de alta la nueva calificación SAT
				DitHistPersonaMoralCalific ditHistPersonaMoralCalific = crearCalificacionAuxCommon(moral,
						CalificacionEnum.VALIDADO_SAT);
				this.calificacionesPersonaEntityService
						.registrar(ditHistPersonaMoralCalific);
			}
		} else {
			/*
			 * No se tienen calificaciones, por lo tanto, se da de alta
			 * la calificación SAT
			 */
			DitHistPersonaMoralCalific ditHistPersonaMoralCalific = crearCalificacionAuxCommon(moral,
					CalificacionEnum.VALIDADO_SAT);
			this.calificacionesPersonaEntityService
					.registrar(ditHistPersonaMoralCalific);
		}
		
	}
	
	@Override
	public void calificarRENAPOySAT(Fisica fisica)
			throws PersonaSinCalificacionesException {

		if (fisica.getIdPersona() == null) {
			throw new PersonaSinCalificacionesException(
					"El id de la persona es necesario para dar de alta la calificacion RENAPO");
		}

		// Primero se obtienen todas las calificaciones de la persona
		List<DitHistPersonaCalificacion> califPersona = this.calificacionesPersonaEntityService
				.consultarCalificacionesPersona(fisica, false);
		
		/*
		 * Se checa si dentro de las calificaciones se encuentra la
		 * calificación RENAPO y/o SAT, de ser así se actualiza la fecha, en caso
		 * contrario se crea desde cero y, para ambos casos, las otras
		 * calificaciones se deben expirar
		 */
		if (califPersona != null && !califPersona.isEmpty()) {
			boolean cuentaConCalifRENAPO = false;
			boolean cuentaConCalifSAT = false;

			for (DitHistPersonaCalificacion ditHistPersonaCalificacion : califPersona) {

				long tipoCalif = ditHistPersonaCalificacion.getId().getCveIdCalificacion()
						.longValue();
				
				if (tipoCalif == CalificacionEnum.VALIDADO_RENAPO.getCodigo().longValue()
						|| tipoCalif == CalificacionEnum.VALIDADO_SAT.getCodigo().longValue()) {
					
					if (tipoCalif == CalificacionEnum.VALIDADO_RENAPO.getCodigo().longValue()) {
						cuentaConCalifRENAPO = true;
					}
					
					if (tipoCalif == CalificacionEnum.VALIDADO_SAT.getCodigo().longValue()) {
						cuentaConCalifSAT = true;
					}
					
					if (ditHistPersonaCalificacion.getFecRegistroBaja() != null) {
						// Se reactiva la calificación
						this.calificacionesPersonaEntityService
								.reactivar(ditHistPersonaCalificacion);
					} else {
						// Se actualiza la fecha para la calificación
						this.calificacionesPersonaEntityService
								.actualizar(ditHistPersonaCalificacion);
					}
				} else {
					// No es calificación RENAPO ni SAT, se debe expirar
					if (ditHistPersonaCalificacion.getFecRegistroBaja() == null) {
						this.calificacionesPersonaEntityService
								.expirar(ditHistPersonaCalificacion);
					}
				}
			}

			if (!cuentaConCalifRENAPO) {
				// Se da de alta la nueva calificación RENAPO
				DitHistPersonaCalificacion ditHistPersonaCalificacion = crearCalificacionAuxCommon(fisica,
						CalificacionEnum.VALIDADO_RENAPO);
				this.calificacionesPersonaEntityService
						.registrar(ditHistPersonaCalificacion);
			}
			
			if (!cuentaConCalifSAT) {
				// Se da de alta la nueva calificación SAT
				DitHistPersonaCalificacion ditHistPersonaCalificacion = crearCalificacionAuxCommon(fisica,
						CalificacionEnum.VALIDADO_SAT);
				this.calificacionesPersonaEntityService
						.registrar(ditHistPersonaCalificacion);
			}
			
		} else {
			/*
			 * No se tienen calificaciones, por lo tanto, se da de alta
			 * la calificación RENAPO
			 */
			DitHistPersonaCalificacion ditHistPersonaCalificacion = crearCalificacionAuxCommon(fisica,
					CalificacionEnum.VALIDADO_RENAPO);
			this.calificacionesPersonaEntityService
					.registrar(ditHistPersonaCalificacion);
			
			/*
			 * No se tienen calificaciones, por lo tanto, se da de alta
			 * la calificación SAT
			 */
			ditHistPersonaCalificacion = crearCalificacionAuxCommon(fisica,
					CalificacionEnum.VALIDADO_SAT);
			this.calificacionesPersonaEntityService
					.registrar(ditHistPersonaCalificacion);
		}
	}
	
	@Override
	public PersonaCalificacion obtenerCalificacionEspecifica(Fisica fisica,
			CalificacionEnum tipoCalificacion) {
		
		PersonaCalificacion calificacion = null;
		
		try {
			List<PersonaCalificacion> califVigentes = this.obtenerCalificacionesVigentes(fisica);
			
			for(PersonaCalificacion calif : califVigentes) {
				if (calif.getCalificacion().getIdCalificacion()
						.intValue() == tipoCalificacion.getCodigo().intValue()) {
					calificacion = calif;
					break;
				}
			}
			
		} catch (PersonaSinCalificacionesException e) {
			this.log.warn(e);
		}
				
		return calificacion;
	}
	
	@Override
	public boolean tieneCalificacionEspecifica(Fisica fisica,
			CalificacionEnum tipoCalificacion) {
		
		this.log.debug("Se busca si la persona " + fisica.getIdPersona()
				+ " cuenta con calificacion "
				+ tipoCalificacion.getDescripcion() + " vigente");
		
		boolean tieneCalificacion = false;
		
		PersonaCalificacion calificacion = this.obtenerCalificacionEspecifica(fisica, tipoCalificacion);
		
		if (calificacion != null) {
			tieneCalificacion = true;
		}
			
		this.log.debug("La persona " + fisica.getIdPersona()
				+ " cuenta con calificacion "
				+ tipoCalificacion.getDescripcion() + " vigente: "
				+ tieneCalificacion);
		
		return tieneCalificacion;
	}

	private DitHistPersonaCalificacion crearCalificacionAuxCommon(
			Fisica fisica, CalificacionEnum tipoCalif) {
		DitHistPersonaCalificacion ditCalificacion = new DitHistPersonaCalificacion();

		DitHistPersonaCalificacionPK pk = new DitHistPersonaCalificacionPK();
		pk.setCveIdCalificacion(tipoCalif.getCodigo().longValue());
		pk.setCveIdPersona(fisica.getIdPersona());

		ditCalificacion.setId(pk);
		ditCalificacion.setFecRegistroActualizado(null);
		ditCalificacion.setFecRegistroAlta(new Date());
		ditCalificacion.setFecRegistroBaja(null);

		return ditCalificacion;
	}
	
	private DitHistPersonaMoralCalific crearCalificacionAuxCommon(
			Moral moral, CalificacionEnum tipoCalif) {
		DitHistPersonaMoralCalific ditCalificacion = new DitHistPersonaMoralCalific();

		DitHistPersonaMoralCalificPK pk = new DitHistPersonaMoralCalificPK();
		pk.setCveIdCalificacion(tipoCalif.getCodigo().longValue());
		pk.setCveIdPersonaMoral(moral.getCveMoral());

		ditCalificacion.setId(pk);
		ditCalificacion.setFecRegistroActualizado(null);
		ditCalificacion.setFecRegistroAlta(new Date());
		ditCalificacion.setFecRegistroBaja(null);

		return ditCalificacion;
	}
	
	private List<PersonaCalificacion> obtenerCalificacionesCommon(
			Fisica fisica, boolean getVigentes)
			throws PersonaSinCalificacionesException {

		if (fisica.getIdPersona() == null) {
			throw new PersonaSinCalificacionesException(
					"El id de la persona fisica es necesario para consultar las calificaciones"
							+ (getVigentes ? " vigentes" : ""));
		}

		List<PersonaCalificacion> calificaciones = null;

		List<DitHistPersonaCalificacion> califEntity = this.calificacionesPersonaEntityService
				.consultarCalificacionesPersona(fisica, getVigentes);

		// Se checa si se obtuvieron resultados
		if (califEntity == null || califEntity.isEmpty()) {
			throw new PersonaSinCalificacionesException("La persona fisica ("
					+ fisica.getIdPersona() + ") no cuenta con calificaciones"
					+ (getVigentes ? " vigentes" : ""));
		} else {
			calificaciones = new ArrayList<PersonaCalificacion>();
			PersonaCalificacion calificacion = null;

			for (DitHistPersonaCalificacion entity : califEntity) {
				try {
					calificacion = this.calificacionesPersonaUtilityService.transformarAModelo(entity);
					calificaciones.add(calificacion);
				} catch (TransformacionException e) {
					this.log.error(e);
				}
			}
		}

		return calificaciones;
	}
	
	private List<PersonaCalificacion> obtenerCalificacionesCommon(
			Moral moral, boolean getVigentes)
			throws PersonaSinCalificacionesException {

		if (moral.getCveMoral() == null) {
			throw new PersonaSinCalificacionesException(
					"El id de la persona moral es necesario para consultar las calificaciones"
							+ (getVigentes ? " vigentes" : ""));
		}

		List<PersonaCalificacion> calificaciones = null;

		List<DitHistPersonaMoralCalific> califEntity = this.calificacionesPersonaEntityService
				.consultarCalificacionesPersonaMoral(moral, getVigentes);

		// Se checa si se obtuvieron resultados
		if (CollectionUtils.isEmpty(califEntity)) {
			throw new PersonaSinCalificacionesException("La persona moral ("
					+ moral.getCveMoral() + ") no cuenta con calificaciones"
					+ (getVigentes ? " vigentes" : ""));
		} else {
			calificaciones = new ArrayList<PersonaCalificacion>();
			PersonaCalificacion calificacion = null;

			for (DitHistPersonaMoralCalific entity : califEntity) {
				try {
					calificacion = this.calificacionesPersonaUtilityService.transformarAModelo(entity);
					calificaciones.add(calificacion);
				} catch (TransformacionException e) {
					this.log.error(e);
				}
			}
		}

		return calificaciones;
	}
}
