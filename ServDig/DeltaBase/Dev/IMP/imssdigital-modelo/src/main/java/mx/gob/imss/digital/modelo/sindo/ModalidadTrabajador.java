/**
 * 
 */
package mx.gob.imss.digital.modelo.sindo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "modalidadTrabajador", namespace = "http://mx.gob.imss.digital.modelo.sindo")
@XmlRootElement(name = "modalidadTrabajador", namespace = "http://mx.gob.imss.digital.modelo.sindo")
public class ModalidadTrabajador implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Numero de modalidad
     */
    private String modalidad;
    /**
     * fecha en formato dd/MM/yyyy
     */
    private String fecha;
    
    /**
     * registroPatronal asociado a la modalidad
     */
    private String registroPatronal;
    
    
    /**
     * @return the modalidad
     */
    public String getModalidad() {
        return modalidad;
    }
    /**
     * @param modalidad the modalidad to set
     */
    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }
    /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }
    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    /**
     * @return the fecha
     */
	public String getRegistroPatronal() {
		return registroPatronal;
	}
	/**
     * @param fecha the fecha to set
     */
	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}    
    
    
}
