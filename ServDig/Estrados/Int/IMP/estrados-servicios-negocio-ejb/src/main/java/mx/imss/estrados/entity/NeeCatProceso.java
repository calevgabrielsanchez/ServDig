package mx.imss.estrados.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="NEE_CAT_PROCESO")
public class NeeCatProceso implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4381311585311760013L;

	/**
	 * 
	 */
	

	public NeeCatProceso() {
    }
	
	@Id
	@Column(name="CVE_PROCESO")
	private Integer cveProceso;
	
	@Column(name="DES_PROCESO")
	private String desProceso;

	public Integer getCveProceso() {
		return cveProceso;
	}

	public void setCveProceso(Integer cveProceso) {
		this.cveProceso = cveProceso;
	}

	public String getDesProceso() {
		return desProceso;
	}

	public void setDesProceso(String desProceso) {
		this.desProceso = desProceso;
	}

}
