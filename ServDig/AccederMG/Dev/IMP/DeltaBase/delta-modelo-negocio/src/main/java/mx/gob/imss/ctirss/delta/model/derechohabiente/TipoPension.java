/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.derechohabiente;

import java.io.Serializable;
import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * @author JUAN MANUEL MÁRQUEZ  Novutek
 * Fecha. 09/07/2012
 */
public class TipoPension extends AbstractModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Long idTipoPension;
	protected String marcaPension;
	protected String desTipoPension;
	protected Date fechaAlta;
	protected Date fechaBaja;
	protected Date fechaModificacion;
	
	/**
	 * @return the idTipoPension
	 */
	public Long getIdTipoPension() {
		return idTipoPension;
	}
	/**
	 * @param idTipoPension the idTipoPension to set
	 */
	public void setIdTipoPension(Long idTipoPension) {
		this.idTipoPension = idTipoPension;
	}
	/**
	 * @return the marcaPension
	 */
	public String getMarcaPension() {
		return marcaPension;
	}
	/**
	 * @param marcaPension the marcaPension to set
	 */
	public void setMarcaPension(String marcaPension) {
		this.marcaPension = marcaPension;
	}
	/**
	 * @return the desTipoPension
	 */
	public String getDesTipoPension() {
		return desTipoPension;
	}
	/**
	 * @param desTipoPension the desTipoPension to set
	 */
	public void setDesTipoPension(String desTipoPension) {
		this.desTipoPension = desTipoPension;
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
	 * @return the fechaModificacion
	 */
	public Date getFechaModificacion() {
		return fechaModificacion;
	}
	/**
	 * @param fechaModificacion the fechaModificacion to set
	 */
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}	
}
