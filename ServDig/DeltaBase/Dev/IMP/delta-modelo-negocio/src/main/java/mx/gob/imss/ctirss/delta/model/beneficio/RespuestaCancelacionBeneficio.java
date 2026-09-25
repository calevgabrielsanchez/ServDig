/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.beneficio;

import java.io.Serializable;

/**
 * @author NOVUTECK1
 *
 */
public class RespuestaCancelacionBeneficio implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4872479625145465500L;
	
	private Integer exito;
	private Integer claveError;
	private String descripcion;
	
	public Integer getExito() {
		return exito;
	}
	public void setExito(Integer exito) {
		this.exito = exito;
	}
	public Integer getClaveError() {
		return claveError;
	}
	public void setClaveError(Integer claveError) {
		this.claveError= claveError;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
