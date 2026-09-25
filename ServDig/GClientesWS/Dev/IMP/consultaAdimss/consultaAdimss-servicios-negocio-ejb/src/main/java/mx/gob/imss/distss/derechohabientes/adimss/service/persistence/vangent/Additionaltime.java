package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ADDITIONALTIME database table.
 * 
 */
@Entity
@NamedQuery(name="Additionaltime.findAll", query="SELECT a FROM Additionaltime a")
public class Additionaltime implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idadditionaltime;

	private String acertafolio;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	@Temporal(TemporalType.DATE)
	private Date fromdate;

	private String makeby;

	@Temporal(TemporalType.DATE)
	private Date makedate;

	private BigDecimal paid;

	@Temporal(TemporalType.DATE)
	private Date requestdate;

	private BigDecimal satauthorization;

	@Temporal(TemporalType.DATE)
	private Date todate;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	private String vangentfolio;

	//bi-directional many-to-one association to Operatordetail
	@ManyToOne
	@JoinColumn(name="IDOPERATORDETAIL")
	private Operatordetail operatordetail;

	//bi-directional many-to-one association to Breakdownadditiontime
	@OneToMany(mappedBy="additionaltime")
	private List<Breakdownadditiontime> breakdownadditiontimes;

	public Additionaltime() {
	}

	public long getIdadditionaltime() {
		return this.idadditionaltime;
	}

	public void setIdadditionaltime(long idadditionaltime) {
		this.idadditionaltime = idadditionaltime;
	}

	public String getAcertafolio() {
		return this.acertafolio;
	}

	public void setAcertafolio(String acertafolio) {
		this.acertafolio = acertafolio;
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

	public Date getFromdate() {
		return this.fromdate;
	}

	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}

	public String getMakeby() {
		return this.makeby;
	}

	public void setMakeby(String makeby) {
		this.makeby = makeby;
	}

	public Date getMakedate() {
		return this.makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}

	public BigDecimal getPaid() {
		return this.paid;
	}

	public void setPaid(BigDecimal paid) {
		this.paid = paid;
	}

	public Date getRequestdate() {
		return this.requestdate;
	}

	public void setRequestdate(Date requestdate) {
		this.requestdate = requestdate;
	}

	public BigDecimal getSatauthorization() {
		return this.satauthorization;
	}

	public void setSatauthorization(BigDecimal satauthorization) {
		this.satauthorization = satauthorization;
	}

	public Date getTodate() {
		return this.todate;
	}

	public void setTodate(Date todate) {
		this.todate = todate;
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

	public String getVangentfolio() {
		return this.vangentfolio;
	}

	public void setVangentfolio(String vangentfolio) {
		this.vangentfolio = vangentfolio;
	}

	public Operatordetail getOperatordetail() {
		return this.operatordetail;
	}

	public void setOperatordetail(Operatordetail operatordetail) {
		this.operatordetail = operatordetail;
	}

	public List<Breakdownadditiontime> getBreakdownadditiontimes() {
		return this.breakdownadditiontimes;
	}

	public void setBreakdownadditiontimes(List<Breakdownadditiontime> breakdownadditiontimes) {
		this.breakdownadditiontimes = breakdownadditiontimes;
	}

	public Breakdownadditiontime addBreakdownadditiontime(Breakdownadditiontime breakdownadditiontime) {
		getBreakdownadditiontimes().add(breakdownadditiontime);
		breakdownadditiontime.setAdditionaltime(this);

		return breakdownadditiontime;
	}

	public Breakdownadditiontime removeBreakdownadditiontime(Breakdownadditiontime breakdownadditiontime) {
		getBreakdownadditiontimes().remove(breakdownadditiontime);
		breakdownadditiontime.setAdditionaltime(null);

		return breakdownadditiontime;
	}

}