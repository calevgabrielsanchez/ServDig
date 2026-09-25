/**
 * 
 */
package mx.gob.imss.digital.modelo.medio.contacto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Modelo padre de los los medios de contacto
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "medioContacto", namespace = "http://mx.gob.imss.digital.modelo.medio.contacto")
@XmlRootElement(name = "medioContacto", namespace = "http://mx.gob.imss.digital.modelo.medio.contacto")
public class MedioContacto implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Clave del medio de contacto
     */
    private Long clave;
    
    /**
     * El tipo de medio de contacto
     */
    private TipoMedioContacto tipoMedioContacto;
    /**
     * valor del medio de contacto
     */
    private String desFormaContacto;
    /**
     * @return the clave
     */
    public Long getClave() {
        return clave;
    }
    /**
     * @param clave the clave to set
     */
    public void setClave(Long clave) {
        this.clave = clave;
    }
    /**
     * @return the tipoMedioContacto
     */
    public TipoMedioContacto getTipoMedioContacto() {
        return tipoMedioContacto;
    }
    /**
     * @param tipoMedioContacto the tipoMedioContacto to set
     */
    public void setTipoMedioContacto(TipoMedioContacto tipoMedioContacto) {
        this.tipoMedioContacto = tipoMedioContacto;
    }
    /**
     * @return the desFormaContacto
     */
    public String getDesFormaContacto() {
        return desFormaContacto;
    }
    /**
     * @param desFormaContacto the desFormaContacto to set
     */
    public void setDesFormaContacto(String desFormaContacto) {
        this.desFormaContacto = desFormaContacto;
    }
    
    
}
