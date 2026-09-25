
package mx.gob.imss.digital.modelo.medio.contacto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * cuenta de twitter asociada
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "twitter", namespace = "http://mx.gob.imss.digital.modelo.medio.contacto")
@XmlRootElement(name = "twitter", namespace = "http://mx.gob.imss.digital.modelo.medio.contacto")
public class Twitter extends MedioContacto {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
	/**
	 * Cuenta de twitter
	 */
	private String cuenta;

	/**
	 * @return the cuenta
	 */
	public String getCuenta() {
		return cuenta;
	}

	/**
	 * @param cuenta the cuenta to set
	 */
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	
}
