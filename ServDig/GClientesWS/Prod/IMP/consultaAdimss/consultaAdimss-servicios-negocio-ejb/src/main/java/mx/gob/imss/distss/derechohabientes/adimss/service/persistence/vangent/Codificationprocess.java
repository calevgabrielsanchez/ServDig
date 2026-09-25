package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CODIFICATIONPROCESS database table.
 * 
 */
@Entity
@NamedQuery(name="Codificationprocess.findAll", query="SELECT c FROM Codificationprocess c")
public class Codificationprocess implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idcodprocess;

	@Temporal(TemporalType.DATE)
	private Date datemonitored;

	private BigDecimal diff;

	private BigDecimal enrolpending;

	private BigDecimal fingerscodified;

	private BigDecimal fingersempty;

	private BigDecimal fingerserror;

	public Codificationprocess() {
	}

	public long getIdcodprocess() {
		return this.idcodprocess;
	}

	public void setIdcodprocess(long idcodprocess) {
		this.idcodprocess = idcodprocess;
	}

	public Date getDatemonitored() {
		return this.datemonitored;
	}

	public void setDatemonitored(Date datemonitored) {
		this.datemonitored = datemonitored;
	}

	public BigDecimal getDiff() {
		return this.diff;
	}

	public void setDiff(BigDecimal diff) {
		this.diff = diff;
	}

	public BigDecimal getEnrolpending() {
		return this.enrolpending;
	}

	public void setEnrolpending(BigDecimal enrolpending) {
		this.enrolpending = enrolpending;
	}

	public BigDecimal getFingerscodified() {
		return this.fingerscodified;
	}

	public void setFingerscodified(BigDecimal fingerscodified) {
		this.fingerscodified = fingerscodified;
	}

	public BigDecimal getFingersempty() {
		return this.fingersempty;
	}

	public void setFingersempty(BigDecimal fingersempty) {
		this.fingersempty = fingersempty;
	}

	public BigDecimal getFingerserror() {
		return this.fingerserror;
	}

	public void setFingerserror(BigDecimal fingerserror) {
		this.fingerserror = fingerserror;
	}

}