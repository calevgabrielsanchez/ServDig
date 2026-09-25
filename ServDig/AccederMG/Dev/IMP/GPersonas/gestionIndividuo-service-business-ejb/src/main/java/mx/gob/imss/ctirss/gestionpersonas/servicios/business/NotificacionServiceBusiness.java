package mx.gob.imss.ctirss.gestionpersonas.servicios.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.individuo.NotificacionNoValidaException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.utility.NotificacionServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.ModuloEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Notificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modulo;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCambioInformacionPersona;
import mx.gob.imss.ctirss.gestionpersonas.servicios.entity.NotificacionServiceEntityLocal;

/**
 * @author: Marco Sánchez
 * @fecha: 17/01/2013
 */
@Stateless(name = "notificacionServiceBusiness", mappedName = "notificacionServiceBusiness")
public class NotificacionServiceBusiness extends AbstractServiceBusiness
		implements NotificacionServiceBusinessLocal, NotificacionServiceBusinessRemote {

	@EJB
	private NotificacionServiceEntityLocal notificacionServiceEntity;
	@EJB
	private NotificacionServiceUtilityLocal notificacionServiceUtility;

	@Override
	public void crearNotificacion(TramiteCambioInformacionPersona tramite,
			Modulo moduloOrigen) throws NotificacionNoValidaException {

		/*
		 * Se busca las gestiones a las que se debe notificar, y se crea una
		 * notificación por gestión encontrada
		 */
		List<Modulo> modulosNotificar = obtenerModulosANotificar();
		Notificacion notificacion = null;

		for (Modulo moduloNotificar : modulosNotificar) {
			this.log.debug("Se crea una notificacion para el modulo [idModulo = "
					+ moduloNotificar.getIdModulo() + "]");
			notificacion = new Notificacion();
			notificacion.setTramite(tramite);

			notificacion.setModuloOrigen(moduloOrigen);
			notificacion.setModuloNotificar(moduloNotificar);

			notificacion.setFechaRegistro(new Date());

			this.notificacionServiceEntity.crearNotificacion(notificacion);
		}

	}

	@Override
	public List<Notificacion> obtenerNotificacionesPersonaModulo(
			Persona persona, Modulo modulo) {
		
		List<Notificacion> notificaciones = this.notificacionServiceEntity
				.obtenerNotificacionesPersonaModulo(persona, modulo);
		
		for(Notificacion notificacion : notificaciones){
			notificacion.setDetalleCambio(generarHTMLDetalleCambios(notificacion));
		}
		
		return notificaciones;
	}
	
	@Override
	public int obtenerNumeroNotificacionesPersonaModulo(Persona persona,
			Modulo modulo) {

		return this.notificacionServiceEntity.obtenerNumeroNotificacionesPersonaModulo(persona, modulo);
	}	
	
	@Override
	public List<Notificacion> obtenerNotificacionesPersona(Persona persona) {
		
		List<Notificacion> notificaciones = this.notificacionServiceEntity
				.obtenerNotificacionesPersona(persona);
		
		for(Notificacion notificacion : notificaciones){
			notificacion.setDetalleCambio(generarHTMLDetalleCambios(notificacion));
		}
		
		return notificaciones;
	}
	
	@Override
	public int obtenerNumeroNotificacionesPersona(Persona persona) {

		return this.notificacionServiceEntity.obtenerNumeroNotificacionesPersona(persona);
	}
	
	@Override
	public void expirarNotificaciones(List<Notificacion> notificaciones) {
		
		for (Notificacion notificacion : notificaciones) {
			try {
				this.notificacionServiceEntity.expirarNotificacion(notificacion);
			} catch (NotificacionNoValidaException e) {
				this.log.warn(e);
			}
		}
	}
	
	@Override
	public Notificacion obtenerNotificacion(Long idNotificacion)
			throws NotificacionNoValidaException {
		
		Notificacion notificacion = this.notificacionServiceEntity.obtenerNotificacion(idNotificacion);
		
		notificacion.setDetalleCambio(generarHTMLDetalleCambios(notificacion));
		
		return notificacion;
		
	}
	

	/**
	 * Obtiene la lista de módulos a notificar
	 * 
	 * @return lista con los modulos a notificar encontrados, en caso contrario
	 *         se devuelve una lista vacía
	 */
	private List<Modulo> obtenerModulosANotificar() {

		List<Modulo> modulosNotificar = new ArrayList<Modulo>();

		Modulo modulo = new Modulo();
		modulo.setIdModulo(ModuloEnum.PATRONES.getCodigo().longValue());

		modulosNotificar.add(modulo);

		return modulosNotificar;
	}
	
	/**
	 * Genera el HTML para usar en la vista y mostrar el detalle
	 * de los cambios en la persona
	 * 
	 * @param notificacion
	 * @return
	 */
	private String generarHTMLDetalleCambios(Notificacion notificacion) {
		StringBuffer htmlDetalle = new StringBuffer();

		TramiteCambioInformacionPersona tramite = notificacion.getTramite();
		if (tramite != null) {

			if (tramite.getDatosICA() != null) {
				this.notificacionServiceUtility.generarDetalleCambiosICA(tramite, htmlDetalle);
			} else if (tramite.getDatosModifManual() != null) {
				this.notificacionServiceUtility.generarDetalleCambiosModificacionManual(tramite, htmlDetalle);
			}
		} else {
			htmlDetalle.append("SIN CAMBIOS");
		}

		return htmlDetalle.toString();
	}
}
