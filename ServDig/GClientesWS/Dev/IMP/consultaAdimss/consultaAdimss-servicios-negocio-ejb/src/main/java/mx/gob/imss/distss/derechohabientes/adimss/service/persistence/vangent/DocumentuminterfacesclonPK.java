package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the DOCUMENTUMINTERFACESCLON database table.
 * 
 */
@Embeddable
public class DocumentuminterfacesclonPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long iddocumentuminterface;

	@Column(insertable=false, updatable=false)
	private long idenrol;

	private long idenrolimagedetails;

	@Column(insertable=false, updatable=false)
	private long idimgtype;

	@Column(insertable=false, updatable=false)
	private long idenrollmentstation;

	public DocumentuminterfacesclonPK() {
	}
	public long getIddocumentuminterface() {
		return this.iddocumentuminterface;
	}
	public void setIddocumentuminterface(long iddocumentuminterface) {
		this.iddocumentuminterface = iddocumentuminterface;
	}
	public long getIdenrol() {
		return this.idenrol;
	}
	public void setIdenrol(long idenrol) {
		this.idenrol = idenrol;
	}
	public long getIdenrolimagedetails() {
		return this.idenrolimagedetails;
	}
	public void setIdenrolimagedetails(long idenrolimagedetails) {
		this.idenrolimagedetails = idenrolimagedetails;
	}
	public long getIdimgtype() {
		return this.idimgtype;
	}
	public void setIdimgtype(long idimgtype) {
		this.idimgtype = idimgtype;
	}
	public long getIdenrollmentstation() {
		return this.idenrollmentstation;
	}
	public void setIdenrollmentstation(long idenrollmentstation) {
		this.idenrollmentstation = idenrollmentstation;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DocumentuminterfacesclonPK)) {
			return false;
		}
		DocumentuminterfacesclonPK castOther = (DocumentuminterfacesclonPK)other;
		return 
			(this.iddocumentuminterface == castOther.iddocumentuminterface)
			&& (this.idenrol == castOther.idenrol)
			&& (this.idenrolimagedetails == castOther.idenrolimagedetails)
			&& (this.idimgtype == castOther.idimgtype)
			&& (this.idenrollmentstation == castOther.idenrollmentstation);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.iddocumentuminterface ^ (this.iddocumentuminterface >>> 32)));
		hash = hash * prime + ((int) (this.idenrol ^ (this.idenrol >>> 32)));
		hash = hash * prime + ((int) (this.idenrolimagedetails ^ (this.idenrolimagedetails >>> 32)));
		hash = hash * prime + ((int) (this.idimgtype ^ (this.idimgtype >>> 32)));
		hash = hash * prime + ((int) (this.idenrollmentstation ^ (this.idenrollmentstation >>> 32)));
		
		return hash;
	}
}