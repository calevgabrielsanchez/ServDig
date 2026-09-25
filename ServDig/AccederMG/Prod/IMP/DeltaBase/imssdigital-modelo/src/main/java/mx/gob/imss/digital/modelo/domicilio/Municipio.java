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
 * MOdelo que representa un municipio de un domicilio
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "municipio", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
@XmlRootElement(name = "municipio", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
public class Municipio implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Clave del municipio
     */
    private String clave;
    /**
     * Nombre del municipio
     */
    private String nombre;
    /**
     * Entidad federativa a la cual pertenece el municipio
     */
    private EntidadFederativa entidadFederativa;
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
    /**
     * @return the entidadFederativa
     */
    public EntidadFederativa getEntidadFederativa() {
        return entidadFederativa;
    }
    /**
     * @param entidadFederativa the entidadFederativa to set
     */
    public void setEntidadFederativa(EntidadFederativa entidadFederativa) {
        this.entidadFederativa = entidadFederativa;
    }
    
    
    
}
