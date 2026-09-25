/**
 * 
 */
package mx.gob.imss.ctirss.clasificador.model.controller;

import java.io.Serializable;

/**
 * @author lucio
 *
 */
public class LabelValueDataTable implements Serializable {
	
	/**
	 * @param name : 
	 */
	private String  name;
	
	/**
	 * @param value : 
	 */
	private String value;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
