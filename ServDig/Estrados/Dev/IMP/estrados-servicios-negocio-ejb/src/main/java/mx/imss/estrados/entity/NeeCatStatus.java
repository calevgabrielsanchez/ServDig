package mx.imss.estrados.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="NEE_CAT_STATUS")
public class NeeCatStatus implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5363555695115387883L;

	/**
	 * 
	 */
	

	public NeeCatStatus() {
	}
	
	@Id
	@Column(name="CVE_STATUS")
	private Integer cveStatus;
	
	@Column(name="DES_ESTATTUS")
	private String desEstatus;

	public Integer getCveStatus() {
		return cveStatus;
	}

	public void setCveStatus(Integer cveStatus) {
		this.cveStatus = cveStatus;
	}

	public String getDesEstatus() {
		return desEstatus;
	}

	public void setDesEstatus(String desEstatus) {
		this.desEstatus = desEstatus;
	}

}
