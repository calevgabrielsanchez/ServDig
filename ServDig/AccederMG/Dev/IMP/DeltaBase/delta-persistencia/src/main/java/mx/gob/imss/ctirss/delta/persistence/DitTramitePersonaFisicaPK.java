package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * The primary key class for the DIT_TRAMITE_PERSONA_FISICA database table.
 * 
 */
@Embeddable
public class DitTramitePersonaFisicaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_ID_PERSONA")
	private long cveIdPersona;

	@Column(name="CVE_ID_TRAMITE")
	private long cveIdTramite;

	public long getCveIdPersona() {
		return cveIdPersona;
	}

	public void setCveIdPersona(long cveIdPersona) {
		this.cveIdPersona = cveIdPersona;
	}

	public long getCveIdTramite() {
		return cveIdTramite;
	}

	public void setCveIdTramite(long cveIdTramite) {
		this.cveIdTramite = cveIdTramite;
	}
	
   
    public boolean equals(Object o) {
        if (o == null) { return false; }
        if (o == this) { return true; }
        if (this.getClass() != o.getClass()) { return false; }
        DitTramitePersonaFisicaPK other = (DitTramitePersonaFisicaPK)o;
        return new EqualsBuilder()
                .append(this.cveIdPersona, other.cveIdPersona)
                .append(this.cveIdTramite, other.cveIdTramite)
                .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(7, 3)
                .append(cveIdPersona)
                .append(cveIdTramite)
                .hashCode();
    }

    public String toString() {
        return new ToStringBuilder(this)
                .append("cveIdPersona", cveIdPersona)
                .append("cveIdTramite", cveIdTramite)
                .toString();
    }

}
