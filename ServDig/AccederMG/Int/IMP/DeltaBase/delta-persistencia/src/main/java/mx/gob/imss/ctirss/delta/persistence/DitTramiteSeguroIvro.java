/**
 * 
 */
package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Clase de relacion entre los seguros ivro y sus tramites 
 * @author NOVUTECK1
 *
 */
@Entity
@Table(name="DIT_TRAMITE_SEGURO_IVRO")
public class DitTramiteSeguroIvro implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private DitTramiteSeguroIvroPk id;
    
    /**
     * Seguro asociado a un tramite
     */
    @ManyToOne
    @JoinColumn(name="CVE_ID_SEGURO_IVRO",insertable=false,updatable=false)
    private DitSeguroIvro ditSeguroIvro;
    
    /**
     * Tramite asociado al seguro
     */
    @ManyToOne
    @JoinColumn(name="CVE_ID_TRAMITE",insertable=false,updatable=false)
    private DitTramite ditTramite;

    

    /**
     * @return the id
     */
    public DitTramiteSeguroIvroPk getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(DitTramiteSeguroIvroPk id) {
        this.id = id;
    }

    /**
     * @return the ditSeguroIvro
     */
    public DitSeguroIvro getDitSeguroIvro() {
        return ditSeguroIvro;
    }

    /**
     * @param ditSeguroIvro the ditSeguroIvro to set
     */
    public void setDitSeguroIvro(DitSeguroIvro ditSeguroIvro) {
        this.ditSeguroIvro = ditSeguroIvro;
    }

    /**
     * @return the ditTramite
     */
    public DitTramite getDitTramite() {
        return ditTramite;
    }

    /**
     * @param ditTramite the ditTramite to set
     */
    public void setDitTramite(DitTramite ditTramite) {
        this.ditTramite = ditTramite;
    }
    
    
    
}
