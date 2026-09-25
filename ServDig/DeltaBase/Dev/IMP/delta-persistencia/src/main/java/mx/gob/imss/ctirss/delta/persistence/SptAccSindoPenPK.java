package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the SPT_ACC_SINDO_PEN database table.
 * 
 */
@Embeddable
public class SptAccSindoPenPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_DELEGACION")
	private String cveDelegacion;

	@Column(name="CVE_SUBDELEGACION")
	private String cveSubdelegacion;

	@Column(name="ID_TIPO_PENSION", insertable=false, updatable=false)
	private String idTipoPension;

	public SptAccSindoPenPK() {
	}
	public String getCveDelegacion() {
		return this.cveDelegacion;
	}
	public void setCveDelegacion(String cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}
	public String getCveSubdelegacion() {
		return this.cveSubdelegacion;
	}
	public void setCveSubdelegacion(String cveSubdelegacion) {
		this.cveSubdelegacion = cveSubdelegacion;
	}
	public String getIdTipoPension() {
		return this.idTipoPension;
	}
	public void setIdTipoPension(String idTipoPension) {
		this.idTipoPension = idTipoPension;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SptAccSindoPenPK)) {
			return false;
		}
		SptAccSindoPenPK castOther = (SptAccSindoPenPK)other;
		return 
			this.cveDelegacion.equals(castOther.cveDelegacion)
			&& this.cveSubdelegacion.equals(castOther.cveSubdelegacion)
			&& this.idTipoPension.equals(castOther.idTipoPension);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cveDelegacion.hashCode();
		hash = hash * prime + this.cveSubdelegacion.hashCode();
		hash = hash * prime + this.idTipoPension.hashCode();
		
		return hash;
	}
}