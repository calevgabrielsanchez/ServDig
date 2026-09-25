
package mx.gob.imss.digital.modelo.medio.contacto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * datos del telefono movil
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "telefonoMovil", namespace = "http://mx.gob.imss.digital.modelo.medio.contacto")
@XmlRootElement(name = "telefonoMovil", namespace = "http://mx.gob.imss.digital.modelo.medio.contacto")
public class TelefonoMovil extends MedioContacto {
	
    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    /**
     * Numero telefonico
     */
    private String numero;
    /**
     * Constructor de la clase
     */
	public TelefonoMovil() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param numero
	 */
	public TelefonoMovil(String numero) {
		this.setTipoMedioContacto(new  TipoMedioContacto());
		this.getTipoMedioContacto().setIdTipoMedioContacto(TipoMedioContacto.TIPO_TELEFONO_MOVIL);
		this.numero=numero;
		this.setDesFormaContacto(numero);
	}

	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * @param numero the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TelefonoMovil [numero=");
		builder.append(numero);
		builder.append("]");
		return builder.toString();
	}
	
	

}
