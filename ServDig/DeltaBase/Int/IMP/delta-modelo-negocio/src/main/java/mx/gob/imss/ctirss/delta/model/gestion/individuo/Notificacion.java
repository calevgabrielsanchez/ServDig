package mx.gob.imss.ctirss.delta.model.gestion.individuo;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modulo;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCambioInformacionPersona;

/**
 * @author Marco Sánchez
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 17/01/2013
 */
public class Notificacion extends AbstractModel {

	private static final long serialVersionUID = 7886346753148508007L;

	private Long idNotificacion;
	private Modulo moduloOrigen;
	private Modulo moduloNotificar;
	private TramiteCambioInformacionPersona tramite;
	private Date fechaRegistro;
	
	/** 
	 * Atributo para usarlo en la vista, contiene el HTML
	 * del detalle de los cambios realizados en la persona. 
	 */
	private String detalleCambio;

	/**
	 * @return the idNotificacion
	 */
	public Long getIdNotificacion() {
		return idNotificacion;
	}

	/**
	 * @param idNotificacion the idNotificacion to set
	 */
	public void setIdNotificacion(Long idNotificacion) {
		this.idNotificacion = idNotificacion;
	}

	/**
	 * @return the moduloOrigen
	 */
	public Modulo getModuloOrigen() {
		return moduloOrigen;
	}

	/**
	 * @param moduloOrigen the moduloOrigen to set
	 */
	public void setModuloOrigen(Modulo moduloOrigen) {
		this.moduloOrigen = moduloOrigen;
	}

	/**
	 * @return the moduloNotificar
	 */
	public Modulo getModuloNotificar() {
		return moduloNotificar;
	}

	/**
	 * @param moduloNotificar the moduloNotificar to set
	 */
	public void setModuloNotificar(Modulo moduloNotificar) {
		this.moduloNotificar = moduloNotificar;
	}

	/**
	 * @return the tramite
	 */
	public TramiteCambioInformacionPersona getTramite() {
		return tramite;
	}

	/**
	 * @param tramite the tramite to set
	 */
	public void setTramite(TramiteCambioInformacionPersona tramite) {
		this.tramite = tramite;
	}

	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * @return the detalleCambio
	 */
	public String getDetalleCambio() {
		return detalleCambio;
	}

	/**
	 * @param detalleCambio the detalleCambio to set
	 */
	public void setDetalleCambio(String detalleCambio) {
		this.detalleCambio = detalleCambio;
	}
	
	@Override
	public String toString() {
		return "Notificacion [idNotificacion=" + idNotificacion
				+ ", moduloOrigen=" + moduloOrigen + ", moduloNotificar="
				+ moduloNotificar + ", tramite=" + tramite + ", fechaRegistro="
				+ fechaRegistro + "]";
	}
}
