package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ENROLLMENTIMAGEICAOVALUES database table.
 * 
 */
@Embeddable
public class EnrollmentimageicaovaluePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long idenrolimageicao;

	@Column(insertable=false, updatable=false)
	private long idenrol;

	@Column(insertable=false, updatable=false)
	private long idenrollmentstation;

	@Column(insertable=false, updatable=false)
	private long idenrolimagedetails;

	@Column(insertable=false, updatable=false)
	private long idimgtype;

	public EnrollmentimageicaovaluePK() {
	}
	public long getIdenrolimageicao() {
		return this.idenrolimageicao;
	}
	public void setIdenrolimageicao(long idenrolimageicao) {
		this.idenrolimageicao = idenrolimageicao;
	}
	public long getIdenrol() {
		return this.idenrol;
	}
	public void setIdenrol(long idenrol) {
		this.idenrol = idenrol;
	}
	public long getIdenrollmentstation() {
		return this.idenrollmentstation;
	}
	public void setIdenrollmentstation(long idenrollmentstation) {
		this.idenrollmentstation = idenrollmentstation;
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EnrollmentimageicaovaluePK)) {
			return false;
		}
		EnrollmentimageicaovaluePK castOther = (EnrollmentimageicaovaluePK)other;
		return 
			(this.idenrolimageicao == castOther.idenrolimageicao)
			&& (this.idenrol == castOther.idenrol)
			&& (this.idenrollmentstation == castOther.idenrollmentstation)
			&& (this.idenrolimagedetails == castOther.idenrolimagedetails)
			&& (this.idimgtype == castOther.idimgtype);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idenrolimageicao ^ (this.idenrolimageicao >>> 32)));
		hash = hash * prime + ((int) (this.idenrol ^ (this.idenrol >>> 32)));
		hash = hash * prime + ((int) (this.idenrollmentstation ^ (this.idenrollmentstation >>> 32)));
		hash = hash * prime + ((int) (this.idenrolimagedetails ^ (this.idenrolimagedetails >>> 32)));
		hash = hash * prime + ((int) (this.idimgtype ^ (this.idimgtype >>> 32)));
		
		return hash;
	}
}