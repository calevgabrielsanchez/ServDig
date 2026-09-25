package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the FINGERPRINTSUMMARIES database table.
 * 
 */
@Entity
@Table(name="FINGERPRINTSUMMARIES")
@NamedQuery(name="Fingerprintsummary.findAll", query="SELECT f FROM Fingerprintsummary f")
public class Fingerprintsummary implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idfingerprintsummary;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal finger1;

	private BigDecimal finger10;

	private BigDecimal finger2;

	private BigDecimal finger3;

	private BigDecimal finger4;

	private BigDecimal finger5;

	private BigDecimal finger6;

	private BigDecimal finger7;

	private BigDecimal finger8;

	private BigDecimal finger9;

	@Temporal(TemporalType.DATE)
	private Date fingerprintsummarydate;

	@Column(name="\"MONTH\"")
	private BigDecimal month;

	private BigDecimal trystep1;

	private BigDecimal trystep2;

	private BigDecimal trystep3;

	private BigDecimal trystep4;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	@Column(name="\"YEAR\"")
	private BigDecimal year;

	//bi-directional many-to-one association to Enrollmentstation
	@ManyToOne
	@JoinColumn(name="IDENROLLMENTSTATION")
	private Enrollmentstation enrollmentstation;

	public Fingerprintsummary() {
	}

	public long getIdfingerprintsummary() {
		return this.idfingerprintsummary;
	}

	public void setIdfingerprintsummary(long idfingerprintsummary) {
		this.idfingerprintsummary = idfingerprintsummary;
	}

	public BigDecimal getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(BigDecimal createdby) {
		this.createdby = createdby;
	}

	public Date getCreatedon() {
		return this.createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public BigDecimal getFinger1() {
		return this.finger1;
	}

	public void setFinger1(BigDecimal finger1) {
		this.finger1 = finger1;
	}

	public BigDecimal getFinger10() {
		return this.finger10;
	}

	public void setFinger10(BigDecimal finger10) {
		this.finger10 = finger10;
	}

	public BigDecimal getFinger2() {
		return this.finger2;
	}

	public void setFinger2(BigDecimal finger2) {
		this.finger2 = finger2;
	}

	public BigDecimal getFinger3() {
		return this.finger3;
	}

	public void setFinger3(BigDecimal finger3) {
		this.finger3 = finger3;
	}

	public BigDecimal getFinger4() {
		return this.finger4;
	}

	public void setFinger4(BigDecimal finger4) {
		this.finger4 = finger4;
	}

	public BigDecimal getFinger5() {
		return this.finger5;
	}

	public void setFinger5(BigDecimal finger5) {
		this.finger5 = finger5;
	}

	public BigDecimal getFinger6() {
		return this.finger6;
	}

	public void setFinger6(BigDecimal finger6) {
		this.finger6 = finger6;
	}

	public BigDecimal getFinger7() {
		return this.finger7;
	}

	public void setFinger7(BigDecimal finger7) {
		this.finger7 = finger7;
	}

	public BigDecimal getFinger8() {
		return this.finger8;
	}

	public void setFinger8(BigDecimal finger8) {
		this.finger8 = finger8;
	}

	public BigDecimal getFinger9() {
		return this.finger9;
	}

	public void setFinger9(BigDecimal finger9) {
		this.finger9 = finger9;
	}

	public Date getFingerprintsummarydate() {
		return this.fingerprintsummarydate;
	}

	public void setFingerprintsummarydate(Date fingerprintsummarydate) {
		this.fingerprintsummarydate = fingerprintsummarydate;
	}

	public BigDecimal getMonth() {
		return this.month;
	}

	public void setMonth(BigDecimal month) {
		this.month = month;
	}

	public BigDecimal getTrystep1() {
		return this.trystep1;
	}

	public void setTrystep1(BigDecimal trystep1) {
		this.trystep1 = trystep1;
	}

	public BigDecimal getTrystep2() {
		return this.trystep2;
	}

	public void setTrystep2(BigDecimal trystep2) {
		this.trystep2 = trystep2;
	}

	public BigDecimal getTrystep3() {
		return this.trystep3;
	}

	public void setTrystep3(BigDecimal trystep3) {
		this.trystep3 = trystep3;
	}

	public BigDecimal getTrystep4() {
		return this.trystep4;
	}

	public void setTrystep4(BigDecimal trystep4) {
		this.trystep4 = trystep4;
	}

	public BigDecimal getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(BigDecimal updatedby) {
		this.updatedby = updatedby;
	}

	public Date getUpdatedon() {
		return this.updatedon;
	}

	public void setUpdatedon(Date updatedon) {
		this.updatedon = updatedon;
	}

	public BigDecimal getYear() {
		return this.year;
	}

	public void setYear(BigDecimal year) {
		this.year = year;
	}

	public Enrollmentstation getEnrollmentstation() {
		return this.enrollmentstation;
	}

	public void setEnrollmentstation(Enrollmentstation enrollmentstation) {
		this.enrollmentstation = enrollmentstation;
	}

}