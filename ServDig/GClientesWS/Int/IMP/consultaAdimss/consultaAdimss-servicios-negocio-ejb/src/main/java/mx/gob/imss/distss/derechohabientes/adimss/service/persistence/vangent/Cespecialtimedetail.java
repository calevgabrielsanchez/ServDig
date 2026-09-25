package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CESPECIALTIMEDETAILS database table.
 * 
 */
@Entity
@Table(name="CESPECIALTIMEDETAILS")
@NamedQuery(name="Cespecialtimedetail.findAll", query="SELECT c FROM Cespecialtimedetail c")
public class Cespecialtimedetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idcespecialtimesdet;

	private String comments;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	@Column(name="CVE_DELEGACION")
	private BigDecimal cveDelegacion;

	@Temporal(TemporalType.DATE)
	private Date enddate;

	private String endtime;

	private BigDecimal idenrollmentlocation;

	private BigDecimal idenrollmentstation;

	private BigDecimal ideventtype;

	@Column(name="NUM_ECONOMICO")
	private BigDecimal numEconomico;

	@Column(name="NUM_NIVEL_ATENCION")
	private BigDecimal numNivelAtencion;

	@Temporal(TemporalType.DATE)
	private Date operationdate;

	private String referenceurl;

	@Temporal(TemporalType.DATE)
	private Date startdate;

	private String starttime;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	public Cespecialtimedetail() {
	}

	public long getIdcespecialtimesdet() {
		return this.idcespecialtimesdet;
	}

	public void setIdcespecialtimesdet(long idcespecialtimesdet) {
		this.idcespecialtimesdet = idcespecialtimesdet;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public BigDecimal getCveDelegacion() {
		return this.cveDelegacion;
	}

	public void setCveDelegacion(BigDecimal cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}

	public Date getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getEndtime() {
		return this.endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public BigDecimal getIdenrollmentlocation() {
		return this.idenrollmentlocation;
	}

	public void setIdenrollmentlocation(BigDecimal idenrollmentlocation) {
		this.idenrollmentlocation = idenrollmentlocation;
	}

	public BigDecimal getIdenrollmentstation() {
		return this.idenrollmentstation;
	}

	public void setIdenrollmentstation(BigDecimal idenrollmentstation) {
		this.idenrollmentstation = idenrollmentstation;
	}

	public BigDecimal getIdeventtype() {
		return this.ideventtype;
	}

	public void setIdeventtype(BigDecimal ideventtype) {
		this.ideventtype = ideventtype;
	}

	public BigDecimal getNumEconomico() {
		return this.numEconomico;
	}

	public void setNumEconomico(BigDecimal numEconomico) {
		this.numEconomico = numEconomico;
	}

	public BigDecimal getNumNivelAtencion() {
		return this.numNivelAtencion;
	}

	public void setNumNivelAtencion(BigDecimal numNivelAtencion) {
		this.numNivelAtencion = numNivelAtencion;
	}

	public Date getOperationdate() {
		return this.operationdate;
	}

	public void setOperationdate(Date operationdate) {
		this.operationdate = operationdate;
	}

	public String getReferenceurl() {
		return this.referenceurl;
	}

	public void setReferenceurl(String referenceurl) {
		this.referenceurl = referenceurl;
	}

	public Date getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public String getStarttime() {
		return this.starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
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

}