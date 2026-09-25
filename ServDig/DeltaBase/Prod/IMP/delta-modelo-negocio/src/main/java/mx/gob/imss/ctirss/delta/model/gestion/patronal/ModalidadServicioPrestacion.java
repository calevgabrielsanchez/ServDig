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
 */
public class ModalidadServicioPrestacion  extends AbstractModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Long idModalidadServicio;
	protected Long valorServicio;
	protected Date fechaAlta;
	protected Date fechaBaja;
	protected Date fechaActualizacion;
	protected Modalidad modalidad;
	protected ServicioPrestacion servicioPrestacion;
	
	
	/**
	 * @return the idModalidadServicio
	 */
	public Long getIdModalidadServicio() {
		return idModalidadServicio;
	}
	/**
	 * @param idModalidadServicio the idModalidadServicio to set
	 */
	public void setIdModalidadServicio(Long idModalidadServicio) {
		this.idModalidadServicio = idModalidadServicio;
	}
	/**
	 * @return the valorServicio
	 */
	public Long getValorServicio() {
		return valorServicio;
	}
	/**
	 * @param valorServicio the valorServicio to set
	 */
	public void setValorServicio(Long valorServicio) {
		this.valorServicio = valorServicio;
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
	/**
	 * @return the modalidad
	 */
	public Modalidad getModalidad() {
		return modalidad;
	}
	/**
	 * @param modalidad the modalidad to set
	 */
	public void setModalidad(Modalidad modalidad) {
		this.modalidad = modalidad;
	}
	/**
	 * @return the servicioPrestacion
	 */
	public ServicioPrestacion getServicioPrestacion() {
		return servicioPrestacion;
	}
	/**
	 * @param servicioPrestacion the servicioPrestacion to set
	 */
	public void setServicioPrestacion(ServicioPrestacion servicioPrestacion) {
		this.servicioPrestacion = servicioPrestacion;
	}
}
