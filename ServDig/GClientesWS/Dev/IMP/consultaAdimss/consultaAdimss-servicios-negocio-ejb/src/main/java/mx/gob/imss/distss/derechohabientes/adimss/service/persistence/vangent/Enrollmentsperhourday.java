package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ENROLLMENTSPERHOURDAY database table.
 * 
 */
@Entity
@NamedQuery(name="Enrollmentsperhourday.findAll", query="SELECT e FROM Enrollmentsperhourday e")
public class Enrollmentsperhourday implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idenrollmentsperhourday;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	@Column(name="\"DAY\"")
	private BigDecimal day;

	@Temporal(TemporalType.DATE)
	private Date enrollmentsperhourdaydate;

	@Column(name="\"MONTH\"")
	private BigDecimal month;

	@Column(name="RANK08_09")
	private BigDecimal rank0809;

	@Column(name="RANK09_10")
	private BigDecimal rank0910;

	@Column(name="RANK10_11")
	private BigDecimal rank1011;

	@Column(name="RANK11_12")
	private BigDecimal rank1112;

	@Column(name="RANK12_13")
	private BigDecimal rank1213;

	@Column(name="RANK13_14")
	private BigDecimal rank1314;

	@Column(name="RANK14_15")
	private BigDecimal rank1415;

	@Column(name="RANK15_16")
	private BigDecimal rank1516;

	@Column(name="RANK16_17")
	private BigDecimal rank1617;

	@Column(name="RANK17_18")
	private BigDecimal rank1718;

	@Column(name="RANK18_19")
	private BigDecimal rank1819;

	@Column(name="RANK19_20")
	private BigDecimal rank1920;

	@Column(name="RANKAFTER_20")
	private BigDecimal rankafter20;


	@Column(name="RANKBEFORE_08")
	private BigDecimal rankbefore08;


	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	@Column(name="\"YEAR\"")
	private BigDecimal year;

	//bi-directional many-to-one association to Enrollmentstation
	@ManyToOne
	@JoinColumn(name="IDENROLLMENTSTATION")
	private Enrollmentstation enrollmentstation;

	public Enrollmentsperhourday() {
	}

	public long getIdenrollmentsperhourday() {
		return this.idenrollmentsperhourday;
	}

	public void setIdenrollmentsperhourday(long idenrollmentsperhourday) {
		this.idenrollmentsperhourday = idenrollmentsperhourday;
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

	public BigDecimal getDay() {
		return this.day;
	}

	public void setDay(BigDecimal day) {
		this.day = day;
	}

	public Date getEnrollmentsperhourdaydate() {
		return this.enrollmentsperhourdaydate;
	}

	public void setEnrollmentsperhourdaydate(Date enrollmentsperhourdaydate) {
		this.enrollmentsperhourdaydate = enrollmentsperhourdaydate;
	}

	public BigDecimal getMonth() {
		return this.month;
	}

	public void setMonth(BigDecimal month) {
		this.month = month;
	}

	public BigDecimal getRank0809() {
		return this.rank0809;
	}

	public void setRank0809(BigDecimal rank0809) {
		this.rank0809 = rank0809;
	}

	public BigDecimal getRank0910() {
		return this.rank0910;
	}

	public void setRank0910(BigDecimal rank0910) {
		this.rank0910 = rank0910;
	}

	public BigDecimal getRank1011() {
		return this.rank1011;
	}

	public void setRank1011(BigDecimal rank1011) {
		this.rank1011 = rank1011;
	}

	public BigDecimal getRank1112() {
		return this.rank1112;
	}

	public void setRank1112(BigDecimal rank1112) {
		this.rank1112 = rank1112;
	}

	public BigDecimal getRank1213() {
		return this.rank1213;
	}

	public void setRank1213(BigDecimal rank1213) {
		this.rank1213 = rank1213;
	}

	public BigDecimal getRank1314() {
		return this.rank1314;
	}

	public void setRank1314(BigDecimal rank1314) {
		this.rank1314 = rank1314;
	}

	public BigDecimal getRank1415() {
		return this.rank1415;
	}

	public void setRank1415(BigDecimal rank1415) {
		this.rank1415 = rank1415;
	}

	public BigDecimal getRank1516() {
		return this.rank1516;
	}

	public void setRank1516(BigDecimal rank1516) {
		this.rank1516 = rank1516;
	}

	public BigDecimal getRank1617() {
		return this.rank1617;
	}

	public void setRank1617(BigDecimal rank1617) {
		this.rank1617 = rank1617;
	}

	public BigDecimal getRank1718() {
		return this.rank1718;
	}

	public void setRank1718(BigDecimal rank1718) {
		this.rank1718 = rank1718;
	}

	public BigDecimal getRank1819() {
		return this.rank1819;
	}

	public void setRank1819(BigDecimal rank1819) {
		this.rank1819 = rank1819;
	}

	public BigDecimal getRank1920() {
		return this.rank1920;
	}

	public void setRank1920(BigDecimal rank1920) {
		this.rank1920 = rank1920;
	}


	public BigDecimal getRankafter20() {
		return this.rankafter20;
	}

	public void setRankafter20(BigDecimal rankafter20) {
		this.rankafter20 = rankafter20;
	}


	public BigDecimal getRankbefore08() {
		return this.rankbefore08;
	}

	public void setRankbefore08(BigDecimal rankbefore08) {
		this.rankbefore08 = rankbefore08;
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