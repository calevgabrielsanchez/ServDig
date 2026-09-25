package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the IMAGETYPES database table.
 * 
 */
@Entity
@Table(name="IMAGETYPES")
@NamedQuery(name="Imagetype.findAll", query="SELECT i FROM Imagetype i")
public class Imagetype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idimgtype;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal idexttype;

	private String imgtypedescription;

	private String imgtypename;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Documentuminterface
	@OneToMany(mappedBy="imagetype")
	private List<Documentuminterface> documentuminterfaces;

	//bi-directional many-to-one association to Documentuminterfacesclon
	@OneToMany(mappedBy="imagetype")
	private List<Documentuminterfacesclon> documentuminterfacesclons;

	//bi-directional many-to-one association to Enrollmentimagedetail
	@OneToMany(mappedBy="imagetype")
	private List<Enrollmentimagedetail> enrollmentimagedetails;

	public Imagetype() {
	}

	public long getIdimgtype() {
		return this.idimgtype;
	}

	public void setIdimgtype(long idimgtype) {
		this.idimgtype = idimgtype;
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

	public BigDecimal getIdexttype() {
		return this.idexttype;
	}

	public void setIdexttype(BigDecimal idexttype) {
		this.idexttype = idexttype;
	}

	public String getImgtypedescription() {
		return this.imgtypedescription;
	}

	public void setImgtypedescription(String imgtypedescription) {
		this.imgtypedescription = imgtypedescription;
	}

	public String getImgtypename() {
		return this.imgtypename;
	}

	public void setImgtypename(String imgtypename) {
		this.imgtypename = imgtypename;
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

	public List<Documentuminterface> getDocumentuminterfaces() {
		return this.documentuminterfaces;
	}

	public void setDocumentuminterfaces(List<Documentuminterface> documentuminterfaces) {
		this.documentuminterfaces = documentuminterfaces;
	}

	public Documentuminterface addDocumentuminterface(Documentuminterface documentuminterface) {
		getDocumentuminterfaces().add(documentuminterface);
		documentuminterface.setImagetype(this);

		return documentuminterface;
	}

	public Documentuminterface removeDocumentuminterface(Documentuminterface documentuminterface) {
		getDocumentuminterfaces().remove(documentuminterface);
		documentuminterface.setImagetype(null);

		return documentuminterface;
	}

	public List<Documentuminterfacesclon> getDocumentuminterfacesclons() {
		return this.documentuminterfacesclons;
	}

	public void setDocumentuminterfacesclons(List<Documentuminterfacesclon> documentuminterfacesclons) {
		this.documentuminterfacesclons = documentuminterfacesclons;
	}

	public Documentuminterfacesclon addDocumentuminterfacesclon(Documentuminterfacesclon documentuminterfacesclon) {
		getDocumentuminterfacesclons().add(documentuminterfacesclon);
		documentuminterfacesclon.setImagetype(this);

		return documentuminterfacesclon;
	}

	public Documentuminterfacesclon removeDocumentuminterfacesclon(Documentuminterfacesclon documentuminterfacesclon) {
		getDocumentuminterfacesclons().remove(documentuminterfacesclon);
		documentuminterfacesclon.setImagetype(null);

		return documentuminterfacesclon;
	}

	public List<Enrollmentimagedetail> getEnrollmentimagedetails() {
		return this.enrollmentimagedetails;
	}

	public void setEnrollmentimagedetails(List<Enrollmentimagedetail> enrollmentimagedetails) {
		this.enrollmentimagedetails = enrollmentimagedetails;
	}

	public Enrollmentimagedetail addEnrollmentimagedetail(Enrollmentimagedetail enrollmentimagedetail) {
		getEnrollmentimagedetails().add(enrollmentimagedetail);
		enrollmentimagedetail.setImagetype(this);

		return enrollmentimagedetail;
	}

	public Enrollmentimagedetail removeEnrollmentimagedetail(Enrollmentimagedetail enrollmentimagedetail) {
		getEnrollmentimagedetails().remove(enrollmentimagedetail);
		enrollmentimagedetail.setImagetype(null);

		return enrollmentimagedetail;
	}

}