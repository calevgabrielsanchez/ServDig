package mx.gob.imss.ctirss.delta.cobranza.service.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * The primary key class for the D_COP_FACTOR database table.
 * 
 */
@Embeddable
public class DCopFactorPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Long periodo;

	@Column(name="CVE_CONCEPTO")
	private String cveConcepto;

    @Temporal( TemporalType.DATE)
	@Column(name="FECHA_APL")
	private Date fechaApl;

    public DCopFactorPK() {
    }
	public long getPeriodo() {
		return this.periodo;
	}
	public void setPeriodo(long periodo) {
		this.periodo = periodo;
	}
	public String getCveConcepto() {
		return this.cveConcepto;
	}
	public void setCveConcepto(String cveConcepto) {
		this.cveConcepto = cveConcepto;
	}
	public Date getFechaApl() {
		return this.fechaApl;
	}
	public void setFechaApl(Date fechaApl) {
		this.fechaApl = fechaApl;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DCopFactorPK)) {
			return false;
		}
		DCopFactorPK castOther = (DCopFactorPK)other;
		return 
			(this.periodo == castOther.periodo)
			&& this.cveConcepto.equals(castOther.cveConcepto)
			&& this.fechaApl.equals(castOther.fechaApl);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.periodo ^ (this.periodo >>> 32)));
		hash = hash * prime + this.cveConcepto.hashCode();
		hash = hash * prime + this.fechaApl.hashCode();
		
		return hash;
    }
}