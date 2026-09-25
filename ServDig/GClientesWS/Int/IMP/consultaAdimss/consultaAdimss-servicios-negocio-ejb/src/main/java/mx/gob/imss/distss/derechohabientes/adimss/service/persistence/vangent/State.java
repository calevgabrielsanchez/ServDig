package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the STATES database table.
 * 
 */
@Entity
@Table(name="STATES")
@NamedQuery(name="State.findAll", query="SELECT s FROM State s")
public class State implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idstate;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String statename;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Companymanager
	@OneToMany(mappedBy="state")
	private List<Companymanager> companymanagers;

	//bi-directional many-to-one association to Operatorsemployed
	@OneToMany(mappedBy="state")
	private List<Operatorsemployed> operatorsemployeds;

	public State() {
	}

	public long getIdstate() {
		return this.idstate;
	}

	public void setIdstate(long idstate) {
		this.idstate = idstate;
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

	public String getStatename() {
		return this.statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
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

	public List<Companymanager> getCompanymanagers() {
		return this.companymanagers;
	}

	public void setCompanymanagers(List<Companymanager> companymanagers) {
		this.companymanagers = companymanagers;
	}

	public Companymanager addCompanymanager(Companymanager companymanager) {
		getCompanymanagers().add(companymanager);
		companymanager.setState(this);

		return companymanager;
	}

	public Companymanager removeCompanymanager(Companymanager companymanager) {
		getCompanymanagers().remove(companymanager);
		companymanager.setState(null);

		return companymanager;
	}

	public List<Operatorsemployed> getOperatorsemployeds() {
		return this.operatorsemployeds;
	}

	public void setOperatorsemployeds(List<Operatorsemployed> operatorsemployeds) {
		this.operatorsemployeds = operatorsemployeds;
	}

	public Operatorsemployed addOperatorsemployed(Operatorsemployed operatorsemployed) {
		getOperatorsemployeds().add(operatorsemployed);
		operatorsemployed.setState(this);

		return operatorsemployed;
	}

	public Operatorsemployed removeOperatorsemployed(Operatorsemployed operatorsemployed) {
		getOperatorsemployeds().remove(operatorsemployed);
		operatorsemployed.setState(null);

		return operatorsemployed;
	}

}