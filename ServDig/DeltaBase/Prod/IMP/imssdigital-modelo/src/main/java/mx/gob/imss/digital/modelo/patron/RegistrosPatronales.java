/**
 * 
 */
package mx.gob.imss.digital.modelo.patron;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author User
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registroPatronales", namespace = "http://mx.gob.imss.digital.modelo.patron")
@XmlRootElement(name = "registroPatronales", namespace = "http://mx.gob.imss.digital.modelo.patron")
public class RegistrosPatronales implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9215769856370226917L;
	
	private RegistroPatronal[] registrosPatronal;

	public RegistroPatronal[] getRegistrosPatronal() {
		return registrosPatronal;
	}

	public void setRegistrosPatronal(RegistroPatronal[] registrosPatronal) {
		this.registrosPatronal = registrosPatronal != null ? registrosPatronal.clone() : null;
	}

}
