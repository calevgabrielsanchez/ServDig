/**
 * 
 */
package mx.gob.imss.ctirss.correccion.session;

import java.io.Serializable;


/**
 * @author vaguirre
 *
 */
public class MenuVO implements Serializable{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3060828185964634337L;

	private Long cvePK;
	
	private String desEtiqueta;
	
	private String desURL;
	
	private Integer numOrden;
	
	private Integer cveFkRol;
	
	private String cveFkMenuItem;
	

	public Long getCvePK() {
		return cvePK;
	}

	public void setCvePK(Long cvePK) {
		this.cvePK = cvePK;
	}

	public String getDesEtiqueta() {
		return desEtiqueta;
	}

	public void setDesEtiqueta(String desEtiqueta) {
		this.desEtiqueta = desEtiqueta;
	}

	public String getDesURL() {
		return desURL;
	}

	public void setDesURL(String desURL) {
		this.desURL = desURL;
	}

	public Integer getNumOrden() {
		return numOrden;
	}

	public void setNumOrden(Integer numOrden) {
		this.numOrden = numOrden;
	}

	public Integer getCveFkRol() {
		return cveFkRol;
	}

	public void setCveFkRol(Integer cveFkRol) {
		this.cveFkRol = cveFkRol;
	}

	public String getCveFkMenuItem() {
		return cveFkMenuItem;
	}

	public void setCveFkMenuItem(String cveFkMenuItem) {
		this.cveFkMenuItem = cveFkMenuItem;
	}


}
