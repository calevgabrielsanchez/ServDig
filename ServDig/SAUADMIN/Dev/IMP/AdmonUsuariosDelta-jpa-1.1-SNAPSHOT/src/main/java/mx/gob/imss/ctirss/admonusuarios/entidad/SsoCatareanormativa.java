package mx.gob.imss.ctirss.admonusuarios.entidad;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.admonusuarios.abstractModel.AbstractModel;

import java.util.Set;


/**
 * The persistent class for the SSO_CATAREANORMATIVA database table.
 * 
 */
@Entity
@Table(name="SSO_CATAREANORMATIVA")
public class SsoCatareanormativa  extends AbstractModel {
	private static final long serialVersionUID = 1L;
	private long cveSsoareanorma;
	private String desAreanorma;
	
    public SsoCatareanormativa() {
    }


	@Id
	@Column(name="CVE_SSOAREANORMA", unique=true, nullable=false)
	public long getCveSsoareanorma() {
		return this.cveSsoareanorma;
	}

	public void setCveSsoareanorma(long cveSsoareanorma) {
		this.cveSsoareanorma = cveSsoareanorma;
	}


	@Column(name="DES_AREANORMA", length=100)
	public String getDesAreanorma() {
		return this.desAreanorma;
	}

	public void setDesAreanorma(String desAreanorma) {
		this.desAreanorma = desAreanorma;
	}


}