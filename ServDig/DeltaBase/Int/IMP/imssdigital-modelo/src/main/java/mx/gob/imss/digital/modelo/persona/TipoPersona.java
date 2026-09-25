/**
 * 
 */
package mx.gob.imss.digital.modelo.persona;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Tipos de personas existentes 
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoPersona", namespace = "http://mx.gob.imss.digital.modelo.persona")
@XmlRootElement(name = "tipoPersona", namespace = "http://mx.gob.imss.digital.modelo.persona")
public class TipoPersona implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    /**
     * Persona fisica
     */
    public static Long TIPO_PERSONA_FISICA = new Long(1);
    /**
     * PErsona moral
     */
    public static Long TIPO_PERSONA_MORAL = new Long(2);
    
    /**
     * Solo aplica para gestion patronal
     */
    public static Long TIPO_PERSONA_FIDEICOMISO = new Long(3);
    /**
     * Identificador del tipo de persona
     */
    private Long idTipoPersona;
    /**
     * Descripcion del tipo de persona
     */
    private String descripcion;
    /**
     * @return the idTipoPersona
     */
    public Long getIdTipoPersona() {
        return idTipoPersona;
    }
    /**
     * @param idTipoPersona the idTipoPersona to set
     */
    public void setIdTipoPersona(Long idTipoPersona) {
        this.idTipoPersona = idTipoPersona;
    }
    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }
    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
