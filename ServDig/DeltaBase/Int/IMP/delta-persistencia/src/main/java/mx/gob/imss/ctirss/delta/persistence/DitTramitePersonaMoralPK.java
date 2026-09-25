package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The primary key class for the DIT_TRAMITE_PERSONA_MORAL database table.
 * 
 */
@Embeddable
public class DitTramitePersonaMoralPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;



	@Column(name="CVE_ID_PERSONA_MORAL")
	private long cveIdPersonaMoral;
	
	@Column(name="CVE_ID_TRAMITE")
	private long cveIdTramite;



	public long getCveIdTramite() {
		return cveIdTramite;
	}

	public void setCveIdTramite(long cveIdTramite) {
		this.cveIdTramite = cveIdTramite;
	}

	public DitTramitePersonaMoralPK() {
    }
	
	public long getCveIdPersonaMoral() {
		return this.cveIdPersonaMoral;
	}
	public void setCveIdPersonaMoral(long cveIdPersonaMoral) {
		this.cveIdPersonaMoral = cveIdPersonaMoral;
	}


    public boolean equals(Object o) {
        if (o == null) { return false; }
        if (o == this) { return true; }
        if (this.getClass() != o.getClass()) { return false; }
        DitTramitePersonaMoralPK other = (DitTramitePersonaMoralPK)o;
        return new EqualsBuilder()
                .append(this.cveIdPersonaMoral, other.cveIdPersonaMoral)
                .append(this.cveIdTramite, other.cveIdTramite)
                .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(7, 3)
                .append(cveIdPersonaMoral)
                .append(cveIdTramite)
                .hashCode();
    }

    public String toString() {
        return new ToStringBuilder(this)
                .append("cveIdPersonaMoral", cveIdPersonaMoral)
                .append("cveIdTramite", cveIdTramite)
                .toString();
    }

}
