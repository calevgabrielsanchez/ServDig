package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the DIC_FOLIO_NSS database table.
 * 
 */
@Embeddable
public class DicFolioNssPK implements Serializable {
    //default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    @Column(name = "CVE_ID_SERIE")
    private long cveIdSerie;

    @Column(name = "NUM_ANIO_NACIMIENTO")
    private long numAnioNacimiento;

    public DicFolioNssPK() {
    }

    public long getCveIdSerie() {
        return this.cveIdSerie;
    }

    public void setCveIdSerie(long cveIdSerie) {
        this.cveIdSerie = cveIdSerie;
    }

    public long getNumAnioNacimiento() {
        return this.numAnioNacimiento;
    }

    public void setNumAnioNacimiento(long numAnioNacimiento) {
        this.numAnioNacimiento = numAnioNacimiento;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DicFolioNssPK)) {
            return false;
        }
        DicFolioNssPK castOther = (DicFolioNssPK) other;
        return (this.cveIdSerie == castOther.cveIdSerie) && (this.numAnioNacimiento == castOther.numAnioNacimiento);

    }

    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + ((int) (this.cveIdSerie ^ (this.cveIdSerie >>> 32)));
        hash = hash * prime + ((int) (this.numAnioNacimiento ^ (this.numAnioNacimiento >>> 32)));

        return hash;
    }

}