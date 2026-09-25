package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the UMFS database table.
 * 
 */
@Entity
@Table(name="UMFS")
@NamedQuery(name="Umf.findAll", query="SELECT u FROM Umf u")
public class Umf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idumf;

	private String amsupervisor;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	@Column(name="CVE_DELEGACION")
	private BigDecimal cveDelegacion;

	private String enrollmentstationdescription;

	private String enrollmentstationipaddress;

	private String enrollmentstationname;

	private String idareasupervisor;

	private BigDecimal idenrollmentlocation;

	private BigDecimal idenrollmentstation;

	private BigDecimal idscysenrollments;

	private BigDecimal idzone;

	@Column(name="NUM_ECONOMICO")
	private BigDecimal numEconomico;

	@Column(name="NUM_NIVEL_ATENCION")
	private BigDecimal numNivelAtencion;

	private String pmsupervisor;

	private String umfaddress;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Inventory
	@OneToMany(mappedBy="umf")
	private List<Inventory> inventories;

	//bi-directional many-to-one association to Movement
	@OneToMany(mappedBy="umf")
	private List<Movement> movements;

	//bi-directional many-to-one association to Scysenrollment
	@OneToMany(mappedBy="umf")
	private List<Scysenrollment> scysenrollments;

	public Umf() {
	}

	public long getIdumf() {
		return this.idumf;
	}

	public void setIdumf(long idumf) {
		this.idumf = idumf;
	}

	public String getAmsupervisor() {
		return this.amsupervisor;
	}

	public void setAmsupervisor(String amsupervisor) {
		this.amsupervisor = amsupervisor;
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

	public String getEnrollmentstationdescription() {
		return this.enrollmentstationdescription;
	}

	public void setEnrollmentstationdescription(String enrollmentstationdescription) {
		this.enrollmentstationdescription = enrollmentstationdescription;
	}

	public String getEnrollmentstationipaddress() {
		return this.enrollmentstationipaddress;
	}

	public void setEnrollmentstationipaddress(String enrollmentstationipaddress) {
		this.enrollmentstationipaddress = enrollmentstationipaddress;
	}

	public String getEnrollmentstationname() {
		return this.enrollmentstationname;
	}

	public void setEnrollmentstationname(String enrollmentstationname) {
		this.enrollmentstationname = enrollmentstationname;
	}

	public String getIdareasupervisor() {
		return this.idareasupervisor;
	}

	public void setIdareasupervisor(String idareasupervisor) {
		this.idareasupervisor = idareasupervisor;
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

	public BigDecimal getIdscysenrollments() {
		return this.idscysenrollments;
	}

	public void setIdscysenrollments(BigDecimal idscysenrollments) {
		this.idscysenrollments = idscysenrollments;
	}

	public BigDecimal getIdzone() {
		return this.idzone;
	}

	public void setIdzone(BigDecimal idzone) {
		this.idzone = idzone;
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

	public String getPmsupervisor() {
		return this.pmsupervisor;
	}

	public void setPmsupervisor(String pmsupervisor) {
		this.pmsupervisor = pmsupervisor;
	}

	public String getUmfaddress() {
		return this.umfaddress;
	}

	public void setUmfaddress(String umfaddress) {
		this.umfaddress = umfaddress;
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

	public List<Inventory> getInventories() {
		return this.inventories;
	}

	public void setInventories(List<Inventory> inventories) {
		this.inventories = inventories;
	}

	public Inventory addInventory(Inventory inventory) {
		getInventories().add(inventory);
		inventory.setUmf(this);

		return inventory;
	}

	public Inventory removeInventory(Inventory inventory) {
		getInventories().remove(inventory);
		inventory.setUmf(null);

		return inventory;
	}

	public List<Movement> getMovements() {
		return this.movements;
	}

	public void setMovements(List<Movement> movements) {
		this.movements = movements;
	}

	public Movement addMovement(Movement movement) {
		getMovements().add(movement);
		movement.setUmf(this);

		return movement;
	}

	public Movement removeMovement(Movement movement) {
		getMovements().remove(movement);
		movement.setUmf(null);

		return movement;
	}

	public List<Scysenrollment> getScysenrollments() {
		return this.scysenrollments;
	}

	public void setScysenrollments(List<Scysenrollment> scysenrollments) {
		this.scysenrollments = scysenrollments;
	}

	public Scysenrollment addScysenrollment(Scysenrollment scysenrollment) {
		getScysenrollments().add(scysenrollment);
		scysenrollment.setUmf(this);

		return scysenrollment;
	}

	public Scysenrollment removeScysenrollment(Scysenrollment scysenrollment) {
		getScysenrollments().remove(scysenrollment);
		scysenrollment.setUmf(null);

		return scysenrollment;
	}

}