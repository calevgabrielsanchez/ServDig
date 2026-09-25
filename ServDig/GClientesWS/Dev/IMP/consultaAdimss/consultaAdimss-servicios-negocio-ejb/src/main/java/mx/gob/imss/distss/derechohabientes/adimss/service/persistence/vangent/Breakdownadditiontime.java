package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the BREAKDOWNADDITIONTIME database table.
 * 
 */
@Entity
@NamedQuery(name="Breakdownadditiontime.findAll", query="SELECT b FROM Breakdownadditiontime b")
public class Breakdownadditiontime implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idbreakdownaddittime;

	@Temporal(TemporalType.DATE)
	private Date bdatfrom;

	@Temporal(TemporalType.DATE)
	private Date bdatfrom2;

	@Temporal(TemporalType.DATE)
	private Date bdatto;

	@Temporal(TemporalType.DATE)
	private Date bdatto2;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	@Temporal(TemporalType.DATE)
	@Column(name="\"DAY\"")
	private Date day;

	private String motive;

	private BigDecimal numberhours;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Additionaltime
	@ManyToOne
	@JoinColumn(name="IDADDITIONALTIME")
	private Additionaltime additionaltime;

	public Breakdownadditiontime() {
	}

	public long getIdbreakdownaddittime() {
		return this.idbreakdownaddittime;
	}

	public void setIdbreakdownaddittime(long idbreakdownaddittime) {
		this.idbreakdownaddittime = idbreakdownaddittime;
	}

	public Date getBdatfrom() {
		return this.bdatfrom;
	}

	public void setBdatfrom(Date bdatfrom) {
		this.bdatfrom = bdatfrom;
	}

	public Date getBdatfrom2() {
		return this.bdatfrom2;
	}

	public void setBdatfrom2(Date bdatfrom2) {
		this.bdatfrom2 = bdatfrom2;
	}

	public Date getBdatto() {
		return this.bdatto;
	}

	public void setBdatto(Date bdatto) {
		this.bdatto = bdatto;
	}

	public Date getBdatto2() {
		return this.bdatto2;
	}

	public void setBdatto2(Date bdatto2) {
		this.bdatto2 = bdatto2;
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

	public Date getDay() {
		return this.day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public String getMotive() {
		return this.motive;
	}

	public void setMotive(String motive) {
		this.motive = motive;
	}

	public BigDecimal getNumberhours() {
		return this.numberhours;
	}

	public void setNumberhours(BigDecimal numberhours) {
		this.numberhours = numberhours;
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

	public Additionaltime getAdditionaltime() {
		return this.additionaltime;
	}

	public void setAdditionaltime(Additionaltime additionaltime) {
		this.additionaltime = additionaltime;
	}

}