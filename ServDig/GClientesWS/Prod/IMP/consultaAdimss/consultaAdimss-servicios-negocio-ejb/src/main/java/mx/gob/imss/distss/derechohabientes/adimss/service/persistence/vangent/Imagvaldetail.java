package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the IMAGVALDETAILS database table.
 * 
 */
@Entity
@Table(name="IMAGVALDETAILS")
@NamedQuery(name="Imagvaldetail.findAll", query="SELECT i FROM Imagvaldetail i")
public class Imagvaldetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idimgdet;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String imgdetname;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	public Imagvaldetail() {
	}

	public long getIdimgdet() {
		return this.idimgdet;
	}

	public void setIdimgdet(long idimgdet) {
		this.idimgdet = idimgdet;
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

	public String getImgdetname() {
		return this.imgdetname;
	}

	public void setImgdetname(String imgdetname) {
		this.imgdetname = imgdetname;
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