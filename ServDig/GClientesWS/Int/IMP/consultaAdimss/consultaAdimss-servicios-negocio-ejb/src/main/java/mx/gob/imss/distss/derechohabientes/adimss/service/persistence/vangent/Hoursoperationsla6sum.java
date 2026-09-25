package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the HOURSOPERATIONSLA6SUM database table.
 * 
 */
@Entity
@NamedQuery(name="Hoursoperationsla6sum.findAll", query="SELECT h FROM Hoursoperationsla6sum h")
public class Hoursoperationsla6sum implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idhoursoperationsla6sum;

	@Column(name="C_IN")
	private BigDecimal cIn;

	@Column(name="C_OUT")
	private BigDecimal cOut;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String enrollmentcentername;

	@Temporal(TemporalType.DATE)
	private Date hoursoperationsumdate;

	private BigDecimal idenrollmentstation;

	@Temporal(TemporalType.DATE)
	private Date indate;

	private String oficialin;

	private String oficialout;

	@Temporal(TemporalType.DATE)
	private Date outdate;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Cetype
	@ManyToOne
	@JoinColumn(name="IDCETYPE")
	private Cetype cetype;

	public Hoursoperationsla6sum() {
	}

	public long getIdhoursoperationsla6sum() {
		return this.idhoursoperationsla6sum;
	}

	public void setIdhoursoperationsla6sum(long idhoursoperationsla6sum) {
		this.idhoursoperationsla6sum = idhoursoperationsla6sum;
	}

	public BigDecimal getCIn() {
		return this.cIn;
	}

	public void setCIn(BigDecimal cIn) {
		this.cIn = cIn;
	}

	public BigDecimal getCOut() {
		return this.cOut;
	}

	public void setCOut(BigDecimal cOut) {
		this.cOut = cOut;
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

	public String getEnrollmentcentername() {
		return this.enrollmentcentername;
	}

	public void setEnrollmentcentername(String enrollmentcentername) {
		this.enrollmentcentername = enrollmentcentername;
	}

	public Date getHoursoperationsumdate() {
		return this.hoursoperationsumdate;
	}

	public void setHoursoperationsumdate(Date hoursoperationsumdate) {
		this.hoursoperationsumdate = hoursoperationsumdate;
	}

	public BigDecimal getIdenrollmentstation() {
		return this.idenrollmentstation;
	}

	public void setIdenrollmentstation(BigDecimal idenrollmentstation) {
		this.idenrollmentstation = idenrollmentstation;
	}

	public Date getIndate() {
		return this.indate;
	}

	public void setIndate(Date indate) {
		this.indate = indate;
	}

	public String getOficialin() {
		return this.oficialin;
	}

	public void setOficialin(String oficialin) {
		this.oficialin = oficialin;
	}

	public String getOficialout() {
		return this.oficialout;
	}

	public void setOficialout(String oficialout) {
		this.oficialout = oficialout;
	}

	public Date getOutdate() {
		return this.outdate;
	}

	public void setOutdate(Date outdate) {
		this.outdate = outdate;
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

	public Cetype getCetype() {
		return this.cetype;
	}

	public void setCetype(Cetype cetype) {
		this.cetype = cetype;
	}

}