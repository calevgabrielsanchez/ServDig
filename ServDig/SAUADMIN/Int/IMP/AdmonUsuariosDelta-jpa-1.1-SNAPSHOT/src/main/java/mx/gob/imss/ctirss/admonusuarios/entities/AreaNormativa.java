package mx.gob.imss.ctirss.admonusuarios.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Clase de entidad para representar una Area Normativa
 * @author Alan Rene Garcia Rico
 * @version 1.0
 *
 */
@Entity
@Table(name="SSO_CATAREANORMATIVA")
@NamedQueries(
    {
    	@NamedQuery(name = "AreaNormativa.findAll", query = "select a from AreaNormativa a"),
    	@NamedQuery(name = "AreaNormativa.findById", query = "select a from AreaNormativa a where a.idAreaNormativa = :id")
    }
)
public class AreaNormativa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "CVE_SSOAREANORMA", nullable = false, updatable = false)
    private Long idAreaNormativa;
    
    @Column(name = "DES_AREANORMA", nullable = true, length = 255)
    private String descripcionAreaNormativa;
    
	public Long getIdAreaNormativa() {
		return idAreaNormativa;
	}

	public void setIdAreaNormativa(Long idAreaNormativa) {
		this.idAreaNormativa = idAreaNormativa;
	}

	public String getDescripcionAreaNormativa() {
		return descripcionAreaNormativa;
	}

	public void setDescripcionAreaNormativa(String descripcionAreaNormativa) {
		this.descripcionAreaNormativa = descripcionAreaNormativa;
	}

	
	@Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AreaNormativa other = (AreaNormativa) obj;
        if (this.idAreaNormativa != other.idAreaNormativa && (this.idAreaNormativa == null || !this.idAreaNormativa.equals(other.idAreaNormativa))) {
            return false;
        }
        if ((this.descripcionAreaNormativa == null) ? (other.descripcionAreaNormativa != null) : !this.descripcionAreaNormativa.equals(other.descripcionAreaNormativa)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.idAreaNormativa != null ? this.idAreaNormativa.hashCode() : 0);
        hash = 97 * hash + (this.descripcionAreaNormativa != null ? this.descripcionAreaNormativa.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "mx.gob.imss.ctirss.admonusuarios.entities[ idAreaNormativa=" + idAreaNormativa + " ]";
    }
    
}
