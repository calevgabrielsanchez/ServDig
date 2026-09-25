package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the EMPTYFPENROLLMENTEVENTS database table.
 * 
 */
@Entity
@Table(name="EMPTYFPENROLLMENTEVENTS")
@NamedQuery(name="Emptyfpenrollmentevent.findAll", query="SELECT e FROM Emptyfpenrollmentevent e")
public class Emptyfpenrollmentevent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idevent;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal idenrollmentstation;

	private String inputcode;

	private String nss;

	private String outputcode;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to AdtCatCalidad
	@ManyToOne
	@JoinColumn(name="IDQUALITY")
	private AdtCatCalidad adtCatCalidad;

	//bi-directional many-to-one association to Emptyfpenrollmentreasonscat
	@ManyToOne
	@JoinColumn(name="IDREASON")
	private Emptyfpenrollmentreasonscat emptyfpenrollmentreasonscat;

	//bi-directional many-to-one association to Emptyfpenrollmentstatus
	@ManyToOne
	@JoinColumn(name="IDESTATUS")
	private Emptyfpenrollmentstatus emptyfpenrollmentstatus;

	public Emptyfpenrollmentevent() {
	}

	public long getIdevent() {
		return this.idevent;
	}

	public void setIdevent(long idevent) {
		this.idevent = idevent;
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

	public BigDecimal getIdenrollmentstation() {
		return this.idenrollmentstation;
	}

	public void setIdenrollmentstation(BigDecimal idenrollmentstation) {
		this.idenrollmentstation = idenrollmentstation;
	}

	public String getInputcode() {
		return this.inputcode;
	}

	public void setInputcode(String inputcode) {
		this.inputcode = inputcode;
	}

	public String getNss() {
		return this.nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
	}

	public String getOutputcode() {
		return this.outputcode;
	}

	public void setOutputcode(String outputcode) {
		this.outputcode = outputcode;
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

	public AdtCatCalidad getAdtCatCalidad() {
		return this.adtCatCalidad;
	}

	public void setAdtCatCalidad(AdtCatCalidad adtCatCalidad) {
		this.adtCatCalidad = adtCatCalidad;
	}

	public Emptyfpenrollmentreasonscat getEmptyfpenrollmentreasonscat() {
		return this.emptyfpenrollmentreasonscat;
	}

	public void setEmptyfpenrollmentreasonscat(Emptyfpenrollmentreasonscat emptyfpenrollmentreasonscat) {
		this.emptyfpenrollmentreasonscat = emptyfpenrollmentreasonscat;
	}

	public Emptyfpenrollmentstatus getEmptyfpenrollmentstatus() {
		return this.emptyfpenrollmentstatus;
	}

	public void setEmptyfpenrollmentstatus(Emptyfpenrollmentstatus emptyfpenrollmentstatus) {
		this.emptyfpenrollmentstatus = emptyfpenrollmentstatus;
	}

}