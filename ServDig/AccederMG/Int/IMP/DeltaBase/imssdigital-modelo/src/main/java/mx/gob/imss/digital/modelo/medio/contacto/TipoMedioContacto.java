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
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoMedioContacto", namespace = "http://mx.gob.imss.digital.modelo.medio.contacto")
@XmlRootElement(name = "tipoMedioContacto", namespace = "http://mx.gob.imss.digital.modelo.medio.contacto")
public class TipoMedioContacto implements Serializable{

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    /**
     * Correo electronico
     */
    public static final Long TIPO_CORREO_ELECTRONICO  = new Long(1);
    /**
     * Telefono fijo
     */
    public static final Long TIPO_TELEFONO_FIJO  = new Long(2);
    /**
     * Telefono movil
     */
    public static final Long TIPO_TELEFONO_MOVIL = new Long(3);
    /**
     * facebook
     */
    public static final Long TIPO_FACEBOOK = new Long(4);
    /**
     * twitter
     */
    public static final Long TIPO_TWITTER = new Long(5);
    /**
     * Identificador del tipo de medio de contacto
     */
    private Long idTipoMedioContacto;
    /**
     * Descripcion del tipo de medio de contacto
     */
    private String descripcion;
    /**
     * @return the idTipoMedioContacto
     */
    public Long getIdTipoMedioContacto() {
        return idTipoMedioContacto;
    }
    /**
     * @param idTipoMedioContacto the idTipoMedioContacto to set
     */
    public void setIdTipoMedioContacto(Long idTipoMedioContacto) {
        this.idTipoMedioContacto = idTipoMedioContacto;
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
