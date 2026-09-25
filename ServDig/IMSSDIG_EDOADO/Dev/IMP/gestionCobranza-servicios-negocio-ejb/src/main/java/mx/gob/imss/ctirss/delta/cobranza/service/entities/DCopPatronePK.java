package mx.gob.imss.ctirss.delta.cobranza.service.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;


/**
 * The persistent class for the D_COP_PATRONES database table.
 * 
 */
@Embeddable
public class DCopPatronePK implements Serializable {
	private static final long serialVersionUID = 1L;


	@Column(name="CVE_MODALIDAD")
	private String cveModalidad;

	@Column(name="CVE_PATRON")
	private String cvePatron;

	public String getCveModalidad() {
		return cveModalidad;
	}

	public void setCveModalidad(String cveModalidad) {
		this.cveModalidad = cveModalidad;
	}

	public String getCvePatron() {
		return cvePatron;
	}

	public void setCvePatron(String cvePatron) {
		this.cvePatron = cvePatron;
	}

	
    public boolean equals(Object o) {
        if (o == null) { return false; }
        if (o == this) { return true; }
        if (this.getClass() != o.getClass()) { return false; }
        DCopPatronePK other = (DCopPatronePK)o;
        return new EqualsBuilder()
                .append(this.cveModalidad, other.cveModalidad)
                .append(this.cvePatron, other.cvePatron)
                .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(7, 3)
                .append(cveModalidad)
                .append(cvePatron)
                .hashCode();
    }

}
