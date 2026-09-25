package mx.imss.estrados.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="NEE_CAT_AREANORMATIVA")
public class NeeCatAreanormativa implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5113089571741696876L;

	/**
	 * 
	 */
	

	public NeeCatAreanormativa() {
	}
	
	@Id
	@Column(name="CVE_AREANORMA")
	private Integer cveAreanorma;
	
	@Column(name="DES_AREANORMA")
	private String desAreanorma;

	public Integer getCveAreanorma() {
		return cveAreanorma;
	}

	public void setCveAreanorma(Integer cveAreanorma) {
		this.cveAreanorma = cveAreanorma;
	}

	public String getDesAreanorma() {
		return desAreanorma;
	}

	public void setDesAreanorma(String desAreanorma) {
		this.desAreanorma = desAreanorma;
	}

}
