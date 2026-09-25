package mx.gob.imss.ctirss.gestionpersonas.servicios.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.individuo.NotificacionNoValidaException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.utility.NotificacionServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Notificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modulo;
import mx.gob.imss.ctirss.delta.persistence.DitNotificacion;

import org.hibernate.Query;

/**
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Autor: Marco Sánchez
 * @Proyecto: delta
 * @Archivo: NotificacionServiceEntity.java
 * @Paquete: mx.gob.imss.ctirss.gestionpersonas.servicios.entity
 * @Fecha: 05/02/2013
 */

@Stateless(name = "notificacionServiceEntity", mappedName = "notificacionServiceEntity")
public class NotificacionServiceEntity extends AbstractServiceEntity implements
		NotificacionServiceEntityLocal {

	@EJB
	private NotificacionServiceUtilityLocal utility;
	
	private static int MAXIMO_DE_REGISTROS = 5;

	@Override
	public Notificacion crearNotificacion(Notificacion notificacion)
			throws NotificacionNoValidaException {

		try {
			// Se transforma la entidad de negocio al entity
			DitNotificacion ditNotificacion = this.utility
					.transformarNotificacion(notificacion);

			this.em.persist(ditNotificacion);
			this.em.flush();
			notificacion.setIdNotificacion(ditNotificacion
					.getCveIdNotificacion());

		} catch (TransformacionException e) {
			throw new NotificacionNoValidaException(e.getMessage());
		}

		return notificacion;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Notificacion> obtenerNotificacionesPersonaModulo(
			Persona persona, Modulo modulo) {
		
		Query query = crearQueryConsulta(persona, modulo, false, false);
		
		List<DitNotificacion> notificacionesEntity = query.list();
		List<Notificacion> notificaciones = new ArrayList<Notificacion>();
		
		for(DitNotificacion entity : notificacionesEntity){
			try {
				notificaciones.add(this.utility.transformarNotificacion(entity));
			} catch (TransformacionException e) {
				this.log.warn("Error al agregar notificacion encontrada: " + e.getMessage());
			}
		}
		
		return notificaciones;
	}
	
	@Override
	public int obtenerNumeroNotificacionesPersonaModulo(
			Persona persona, Modulo modulo) {
		
		Query query = crearQueryConsulta(persona, modulo, true, false);
		
		Number numNotificaciones = (Number) query.uniqueResult();
				
		return numNotificaciones.intValue();
	}
	
	@Override
	public void expirarNotificacion(Notificacion notificacion)
			throws NotificacionNoValidaException {

		if (notificacion.getIdNotificacion() == null) {
			throw new NotificacionNoValidaException(
					"Para expirar la notificación es necesario contar con su identificador");
		}

		this.log.debug("Se va a expirar la notificacion [idNotificacion = "
				+ notificacion.getIdNotificacion() + "]");

		DitNotificacion ditNotificacion = this.em.find(DitNotificacion.class,
				notificacion.getIdNotificacion().longValue());

		if (ditNotificacion == null) {
			throw new NotificacionNoValidaException(
					"La notificación a expirar no fue encontrada");
		}

		ditNotificacion.setFecRegistroBaja(new Date());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Notificacion> obtenerNotificacionesPersona(Persona persona) {

		Query query = crearQueryConsulta(persona, null, false, true);

		List<DitNotificacion> notificacionesEntity = query.list();
		List<Notificacion> notificaciones = new ArrayList<Notificacion>();
		
		for(DitNotificacion entity : notificacionesEntity){
			try {
				notificaciones.add(this.utility.transformarNotificacion(entity));
			} catch (TransformacionException e) {
				this.log.warn("Error al agregar notificacion encontrada: " + e.getMessage());
			}
		}
		
		return notificaciones;
	}
	
	@Override
	public int obtenerNumeroNotificacionesPersona(Persona persona) {
		
		Query query = crearQueryConsulta(persona, null, true, false);
		
		Number numNotificaciones = (Number) query.uniqueResult();
				
		return numNotificaciones.intValue();
	}
	
	@Override
	public Notificacion obtenerNotificacion(Long idNotificacion)
			throws NotificacionNoValidaException {
		
		Notificacion notificacion = null;
		StringBuffer jpaQuery = new StringBuffer();
		
		jpaQuery.append("select new DitNotificacion(notif.cveIdNotificacion, ");
		jpaQuery.append("notif.ditTramite.cveIdTramite, notif.ditTramite.ditDetalleTramite, ");
		jpaQuery.append("notif.ditTramite.dicTipoTramite.cveIdTipoTramite, ");
		jpaQuery.append("notif.ditTramite.dicTipoTramite.desTipoTramite, ");
		jpaQuery.append("notif.ditTramite.fecPresentacion, notif.ditTramite.fecEfecto, ");
		jpaQuery.append("notif.ditTramite.fecConclusion, ");
		jpaQuery.append("notif.dicModuloOrigen, notif.dicModuloNotificar, ");
		jpaQuery.append("notif.fecRegistroAlta, notif.fecRegistroActualizado, ");
		jpaQuery.append("notif.fecRegistroBaja) ");
		jpaQuery.append("from DitNotificacion as notif ");
		jpaQuery.append("where notif.cveIdNotificacion = :cveNotificacion");
		
		Query query = this.getSession().createQuery(jpaQuery.toString());
		query.setParameter("cveNotificacion", idNotificacion.longValue());
		
		DitNotificacion ditNotificacion = (DitNotificacion) query.uniqueResult();
		
		try {
			notificacion = this.utility.transformarNotificacion(ditNotificacion);
		} catch (TransformacionException e) {
			this.log.error(e);
			throw new NotificacionNoValidaException(e.getMessage());
		}
		
		return notificacion;
	}
	
	private Query crearQueryConsulta(Persona persona,
			Modulo modulo, boolean isCountQuery , boolean limiteMaximo) {
		
		Long idPersona = null;
		
		StringBuffer jpaQuery = new StringBuffer();
		
		if (!isCountQuery) {
			jpaQuery.append("select new DitNotificacion(notif.cveIdNotificacion, ");
			jpaQuery.append("notif.ditTramite.cveIdTramite, notif.ditTramite.ditDetalleTramite, ");
			jpaQuery.append("notif.ditTramite.dicTipoTramite.cveIdTipoTramite, ");
			jpaQuery.append("notif.ditTramite.dicTipoTramite.desTipoTramite, ");
			jpaQuery.append("notif.ditTramite.fecPresentacion, notif.ditTramite.fecEfecto, ");
			jpaQuery.append("notif.ditTramite.fecConclusion, ");
			jpaQuery.append("notif.dicModuloOrigen, notif.dicModuloNotificar, ");
			jpaQuery.append("notif.fecRegistroAlta, notif.fecRegistroActualizado, ");
			jpaQuery.append("notif.fecRegistroBaja) ");
		} else {
			jpaQuery.append("select count(notif.cveIdNotificacion) ");
		}
		
		jpaQuery.append("from DitNotificacion as notif ");
		
		// Se valida que tipo de persona se tiene
		if (persona instanceof Moral){
			jpaQuery.append("where notif.ditTramite.ditTramitePersonaMoral.ditPersonaMoral.cveIdPersonaMoral = :cvePersona ");
			idPersona = ((Moral)persona).getCveMoral();
		} else if (persona.getTipoPersona() != null && persona
						.getTipoPersona().getIdTipoPersona().longValue() == TipoPersonaEnum.MORAL
						.getId()) {
			jpaQuery.append("where notif.ditTramite.ditTramitePersonaMoral.ditPersonaMoral.cveIdPersonaMoral = :cvePersona ");
			idPersona = persona.getIdPersona();
		} else {
			jpaQuery.append("join notif.ditTramite.ditTramitePersonaFisica as tramiteFisica ");
			jpaQuery.append("where tramiteFisica.ditPersona.cveIdPersona = :cvePersona ");
			idPersona = persona.getIdPersona();
		}
		
		if (modulo != null) {
			jpaQuery.append("and notif.dicModuloNotificar.cveIdModulo = :cveModulo ");
		}
		
		jpaQuery.append("and notif.fecRegistroBaja is null ");
		jpaQuery.append("order by notif.fecRegistroAlta desc");
		
		Query query = this.getSession().createQuery(jpaQuery.toString());
		query.setParameter("cvePersona", idPersona);
		
		if(limiteMaximo && !isCountQuery){
			query.setMaxResults(MAXIMO_DE_REGISTROS);
		}
		
		if (modulo != null) {
			query.setParameter("cveModulo", modulo.getIdModulo());
		}
		
		return query;
	}
}
