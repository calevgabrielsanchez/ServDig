/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import java.io.Serializable;
import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * @author JUAN MANUEL MÁRQUEZ
 * fecha: 05/07/2012
 *
 */
public class ServicioPrestacion extends AbstractModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Long idServicioDerechohabiente;
	protected Long servicioPension;
	protected String nomServicioDerechohabiente;
	protected Date fechaAlta;
	protected Date fechaBaja;
	protected Date fechaActualizacion;
	
	
	/**
	 * @return the idServicioDerechohabiente
	 */
	public Long getIdServicioDerechohabiente() {
		return idServicioDerechohabiente;
	}
	/**
	 * @param idServicioDerechohabiente the idServicioDerechohabiente to set
	 */
	public void setIdServicioDerechohabiente(Long idServicioDerechohabiente) {
		this.idServicioDerechohabiente = idServicioDerechohabiente;
	}
	/**
	 * @return the servicioPension
	 */
	public Long getServicioPension() {
		return servicioPension;
	}
	/**
	 * @param servicioPension the servicioPension to set
	 */
	public void setServicioPension(Long servicioPension) {
		this.servicioPension = servicioPension;
	}
	/**
	 * @return the nomServicioDerechohabiente
	 */
	public String getNomServicioDerechohabiente() {
		return nomServicioDerechohabiente;
	}
	/**
	 * @param nomServicioDerechohabiente the nomServicioDerechohabiente to set
	 */
	public void setNomServicioDerechohabiente(String nomServicioDerechohabiente) {
		this.nomServicioDerechohabiente = nomServicioDerechohabiente;
	}
	/**
	 * @return the fechaAlta
	 */
	public Date getFechaAlta() {
		return fechaAlta;
	}
	/**
	 * @param fechaAlta the fechaAlta to set
	 */
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	/**
	 * @return the fechaBaja
	 */
	public Date getFechaBaja() {
		return fechaBaja;
	}
	/**
	 * @param fechaBaja the fechaBaja to set
	 */
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	/**
	 * @return the fechaActualizacion
	 */
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	/**
	 * @param fechaActualizacion the fechaActualizacion to set
	 */
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	

}
