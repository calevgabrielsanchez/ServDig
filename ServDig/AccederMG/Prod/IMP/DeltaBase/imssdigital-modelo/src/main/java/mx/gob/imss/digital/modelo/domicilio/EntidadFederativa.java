/**
 * 
 */
package mx.gob.imss.digital.modelo.domicilio;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * MOdelo de las entidades federativas a las cuales pertenece un domicilio
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "entidadFederativa", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
@XmlRootElement(name = "entidadFederativa", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
public class EntidadFederativa implements Serializable {

    /**
     * Serial version uid
     */
    private static final long serialVersionUID = 1L;
    /**
     * Clave de la entidad federativa
     */
    private String clave;
    /**
     * Nombre de la entidad federativa
     */
    private String nombre;
    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }
    /**
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
