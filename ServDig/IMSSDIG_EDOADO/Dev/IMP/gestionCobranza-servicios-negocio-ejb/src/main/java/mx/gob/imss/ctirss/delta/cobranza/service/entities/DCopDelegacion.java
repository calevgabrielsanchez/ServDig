package mx.gob.imss.ctirss.delta.cobranza.service.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the D_COP_DELEGACION database table.
 * 
 */
@Entity
@Table(name="D_COP_DELEGACION")
public class DCopDelegacion implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long ciz;

	@Id
	@Column(name="CVE_DELEGACION")
	private Long cveDelegacion;

	@Column(name="CVE_REGION")
	private Long cveRegion;

	@Column(name="DESC_DELEGACION")
	private String descDelegacion;

	@Column(name="FOL_ACT")
	private Long folAct;

	@Column(name="FOL_ACT_RCV")
	private Long folActRcv;

    public DCopDelegacion() {
    }

	public Long getCiz() {
		return this.ciz;
	}

	public void setCiz(Long ciz) {
		this.ciz = ciz;
	}

	public Long getCveDelegacion() {
		return this.cveDelegacion;
	}

	public void setCveDelegacion(Long cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}

	public Long getCveRegion() {
		return this.cveRegion;
	}

	public void setCveRegion(Long cveRegion) {
		this.cveRegion = cveRegion;
	}

	public String getDescDelegacion() {
		return this.descDelegacion;
	}

	public void setDescDelegacion(String descDelegacion) {
		this.descDelegacion = descDelegacion;
	}

	public Long getFolAct() {
		return this.folAct;
	}

	public void setFolAct(Long folAct) {
		this.folAct = folAct;
	}

	public Long getFolActRcv() {
		return this.folActRcv;
	}

	public void setFolActRcv(Long folActRcv) {
		this.folActRcv = folActRcv;
	}

}