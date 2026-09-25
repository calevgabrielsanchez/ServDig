package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the OPERATORATTENDANCES database table.
 * 
 */
@Entity
@Table(name="OPERATORATTENDANCES")
@NamedQuery(name="Operatorattendance.findAll", query="SELECT o FROM Operatorattendance o")
public class Operatorattendance implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OperatorattendancePK id;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	@Temporal(TemporalType.DATE)
	private Date endattendance;

	private String messagegenerated;

	@Temporal(TemporalType.DATE)
	private Date startattendance;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="IDENROLLMENTSTATION", referencedColumnName="IDENROLLMENTSTATION"),
		@JoinColumn(name="IDUSERS", referencedColumnName="IDUSERS")
		})
	private User user;

	public Operatorattendance() {
	}

	public OperatorattendancePK getId() {
		return this.id;
	}

	public void setId(OperatorattendancePK id) {
		this.id = id;
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

	public Date getEndattendance() {
		return this.endattendance;
	}

	public void setEndattendance(Date endattendance) {
		this.endattendance = endattendance;
	}

	public String getMessagegenerated() {
		return this.messagegenerated;
	}

	public void setMessagegenerated(String messagegenerated) {
		this.messagegenerated = messagegenerated;
	}

	public Date getStartattendance() {
		return this.startattendance;
	}

	public void setStartattendance(Date startattendance) {
		this.startattendance = startattendance;
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

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}