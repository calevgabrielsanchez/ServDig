package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ENROLLMENTIMAGEICAOVALUES database table.
 * 
 */
@Entity
@Table(name="ENROLLMENTIMAGEICAOVALUES")
@NamedQuery(name="Enrollmentimageicaovalue.findAll", query="SELECT e FROM Enrollmentimageicaovalue e")
public class Enrollmentimageicaovalue implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EnrollmentimageicaovaluePK id;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String parametername;

	private BigDecimal parametervalue;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Enrollmentimagedetail
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="IDENROL", referencedColumnName="IDENROL"),
		@JoinColumn(name="IDENROLIMAGEDETAILS", referencedColumnName="IDENROLIMAGEDETAILS"),
		@JoinColumn(name="IDENROLLMENTSTATION", referencedColumnName="IDENROLLMENTSTATION"),
		@JoinColumn(name="IDIMGTYPE", referencedColumnName="IDIMGTYPE")
		})
	private Enrollmentimagedetail enrollmentimagedetail;

	public Enrollmentimageicaovalue() {
	}

	public EnrollmentimageicaovaluePK getId() {
		return this.id;
	}

	public void setId(EnrollmentimageicaovaluePK id) {
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

	public String getParametername() {
		return this.parametername;
	}

	public void setParametername(String parametername) {
		this.parametername = parametername;
	}

	public BigDecimal getParametervalue() {
		return this.parametervalue;
	}

	public void setParametervalue(BigDecimal parametervalue) {
		this.parametervalue = parametervalue;
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

	public Enrollmentimagedetail getEnrollmentimagedetail() {
		return this.enrollmentimagedetail;
	}

	public void setEnrollmentimagedetail(Enrollmentimagedetail enrollmentimagedetail) {
		this.enrollmentimagedetail = enrollmentimagedetail;
	}

}