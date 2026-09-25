package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * The primary key class for the DIT_DOCUMENTACION_TRAMITE database table.
 * 
 */
@Embeddable
public class DitDocumentacionTramitePK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Column(name="CVE_ID_DOCUMENTO_PROBATORIO")
	private Long cveIdDocumentoProbatorio;

	@Column(name="CVE_ID_TRAMITE")
	private Long cveIdTramite;

	public Long getCveIdDocumentoProbatorio() {
		return cveIdDocumentoProbatorio;
	}

	public void setCveIdDocumentoProbatorio(Long cveIdDocumentoProbatorio) {
		this.cveIdDocumentoProbatorio = cveIdDocumentoProbatorio;
	}

	public Long getCveIdTramite() {
		return cveIdTramite;
	}

	public void setCveIdTramite(Long cveIdTramite) {
		this.cveIdTramite = cveIdTramite;
	}

   
    public boolean equals(Object o) {
        if (o == null) { return false; }
        if (o == this) { return true; }
        if (this.getClass() != o.getClass()) { return false; }
        DitDocumentacionTramitePK other  = (DitDocumentacionTramitePK)o;
        return new EqualsBuilder()
                .append(this.cveIdDocumentoProbatorio, other.cveIdDocumentoProbatorio)
                .append(this.cveIdTramite, other.cveIdTramite)
                .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(7, 3)
                .append(cveIdDocumentoProbatorio)
                .append(cveIdTramite)
                .hashCode();
    }

    public String toString() {
        return new ToStringBuilder(this)
                .append("cveIdDocumentoProbatorio", cveIdDocumentoProbatorio)
                .append("cveIdTramite", cveIdTramite)
                .toString();
    }

}
