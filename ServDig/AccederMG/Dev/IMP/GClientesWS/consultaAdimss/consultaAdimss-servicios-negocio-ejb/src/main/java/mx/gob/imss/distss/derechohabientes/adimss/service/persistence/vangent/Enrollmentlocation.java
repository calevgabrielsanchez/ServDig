package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ENROLLMENTLOCATIONS database table.
 * 
 */
@Entity
@Table(name="ENROLLMENTLOCATIONS")
@NamedQuery(name="Enrollmentlocation.findAll", query="SELECT e FROM Enrollmentlocation e")
public class Enrollmentlocation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idenrollmentlocation;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	@Column(name="CVE_DELEGACION")
	private BigDecimal cveDelegacion;

	private BigDecimal economicnum;

	private String enrollmentlocationdescription;

	private String enrollmentlocationname;

	private BigDecimal idcarelevel;

	private BigDecimal iddelegation;

	private String locationadministratorname;

	@Column(name="NUM_ECONOMICO")
	private BigDecimal numEconomico;

	@Column(name="NUM_NIVEL_ATENCION")
	private BigDecimal numNivelAtencion;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Enrollmentstation
	@OneToMany(mappedBy="enrollmentlocation")
	private List<Enrollmentstation> enrollmentstations;

	public Enrollmentlocation() {
	}

	public long getIdenrollmentlocation() {
		return this.idenrollmentlocation;
	}

	public void setIdenrollmentlocation(long idenrollmentlocation) {
		this.idenrollmentlocation = idenrollmentlocation;
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

	public BigDecimal getEconomicnum() {
		return this.economicnum;
	}

	public void setEconomicnum(BigDecimal economicnum) {
		this.economicnum = economicnum;
	}

	public String getEnrollmentlocationdescription() {
		return this.enrollmentlocationdescription;
	}

	public void setEnrollmentlocationdescription(String enrollmentlocationdescription) {
		this.enrollmentlocationdescription = enrollmentlocationdescription;
	}

	public String getEnrollmentlocationname() {
		return this.enrollmentlocationname;
	}

	public void setEnrollmentlocationname(String enrollmentlocationname) {
		this.enrollmentlocationname = enrollmentlocationname;
	}

	public BigDecimal getIdcarelevel() {
		return this.idcarelevel;
	}

	public void setIdcarelevel(BigDecimal idcarelevel) {
		this.idcarelevel = idcarelevel;
	}

	public BigDecimal getIddelegation() {
		return this.iddelegation;
	}

	public void setIddelegation(BigDecimal iddelegation) {
		this.iddelegation = iddelegation;
	}

	public String getLocationadministratorname() {
		return this.locationadministratorname;
	}

	public void setLocationadministratorname(String locationadministratorname) {
		this.locationadministratorname = locationadministratorname;
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

	public List<Enrollmentstation> getEnrollmentstations() {
		return this.enrollmentstations;
	}

	public void setEnrollmentstations(List<Enrollmentstation> enrollmentstations) {
		this.enrollmentstations = enrollmentstations;
	}

	public Enrollmentstation addEnrollmentstation(Enrollmentstation enrollmentstation) {
		getEnrollmentstations().add(enrollmentstation);
		enrollmentstation.setEnrollmentlocation(this);

		return enrollmentstation;
	}

	public Enrollmentstation removeEnrollmentstation(Enrollmentstation enrollmentstation) {
		getEnrollmentstations().remove(enrollmentstation);
		enrollmentstation.setEnrollmentlocation(null);

		return enrollmentstation;
	}

}