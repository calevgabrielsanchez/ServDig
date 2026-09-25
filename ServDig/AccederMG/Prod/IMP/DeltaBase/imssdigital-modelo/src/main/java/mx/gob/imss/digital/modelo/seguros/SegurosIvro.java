/**
 * 
 */
package mx.gob.imss.digital.modelo.seguros;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Agrupador de seguros
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "segurosIvro", namespace = "http://mx.gob.imss.digital.modelo.seguros")
@XmlRootElement(name = "segurosIvro", namespace = "http://mx.gob.imss.digital.modelo.seguros")
public class SegurosIvro implements Serializable {

    /**
     * Serial version 
     */
    private static final long serialVersionUID = 1L;
    /**
     * Lista de seguros ivro
     */
    private SeguroIvro[] seguroIvro;
    /**
     * Origen de los seguros
     */
    private Long origen;
    /**
     * @return the seguroIvro
     */
    public SeguroIvro[] getSeguroIvro() {
        return seguroIvro;
    }
    /**
     * @param seguroIvro the seguroIvro to set
     */
    public void setSeguroIvro(SeguroIvro[] seguroIvro) {
        this.seguroIvro = seguroIvro != null ? seguroIvro.clone() : null;
    }
    /**
     * @return the origen
     */
    public Long getOrigen() {
        return origen;
    }
    /**
     * @param origen the origen to set
     */
    public void setOrigen(Long origen) {
        this.origen = origen;
    }    
    
    
}
