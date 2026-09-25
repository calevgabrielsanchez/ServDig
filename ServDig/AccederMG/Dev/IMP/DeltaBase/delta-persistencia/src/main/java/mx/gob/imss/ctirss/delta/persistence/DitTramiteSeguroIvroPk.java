/**
 * 
 */
package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Lavve primaria en la relacion de tramite y seguro
 * @author NOVUTECK1
 *
 */
@Embeddable
public class DitTramiteSeguroIvroPk implements Serializable {

    /**
     * Serial version uid 
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID del tramite
     */
    @Column(name="CVE_ID_TRAMITE", insertable=false, updatable=false)
    private long cveIdTramite;

    /**
     * Id del seguro ivro
     */
    @Column(name="CVE_ID_SEGURO_IVRO", insertable=false, updatable=false)
    private long cveIdSeguroIvro;

    /**
     * Consructor de la clase
     */
    public DitTramiteSeguroIvroPk() {
    }   

    /**
     * @return the cveIdTramite
     */
    public long getCveIdTramite() {
        return cveIdTramite;
    }



    /**
     * @param cveIdTramite the cveIdTramite to set
     */
    public void setCveIdTramite(long cveIdTramite) {
        this.cveIdTramite = cveIdTramite;
    }



    /**
     * @return the cveIdSeguroIvro
     */
    public long getCveIdSeguroIvro() {
        return cveIdSeguroIvro;
    }



    /**
     * @param cveIdSeguroIvro the cveIdSeguroIvro to set
     */
    public void setCveIdSeguroIvro(long cveIdSeguroIvro) {
        this.cveIdSeguroIvro = cveIdSeguroIvro;
    }



    /**
     * equals
     */
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DitTramiteSeguroIvroPk)) {
            return false;
        }
        DitTramiteSeguroIvroPk castOther = (DitTramiteSeguroIvroPk)other;
        return 
            (this.cveIdTramite == castOther.cveIdTramite)
            && (this.cveIdSeguroIvro == castOther.cveIdSeguroIvro);
    }

    /**
     * HashCode
     */
    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + ((int) (this.cveIdTramite ^ (this.cveIdTramite >>> 32)));
        hash = hash * prime + ((int) (this.cveIdSeguroIvro ^ (this.cveIdSeguroIvro >>> 32)));
        
        return hash;
    }

}
