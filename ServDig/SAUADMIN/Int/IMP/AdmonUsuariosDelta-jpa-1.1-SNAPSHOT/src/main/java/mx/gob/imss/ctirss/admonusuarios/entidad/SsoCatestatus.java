package mx.gob.imss.ctirss.admonusuarios.entidad;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.admonusuarios.abstractModel.AbstractModel;

import java.util.Set;


/**
 * The persistent class for the SSO_CATESTATUS database table.
 * 
 */
@Entity
@Table(name="SSO_CATESTATUS")
public class SsoCatestatus  extends AbstractModel {
	private static final long serialVersionUID = 1L;
	private long cveSsoestatus;
	private String desEstatus;
	

    public SsoCatestatus() {
    }

    public SsoCatestatus(long clave) {
    	cveSsoestatus = clave;
    }

	@Id
	@Column(name="CVE_SSOESTATUS", unique=true, nullable=false, updatable=false)
	public long getCveSsoestatus() {
		return this.cveSsoestatus;
	}

	public void setCveSsoestatus(long cveSsoestatus) {
		this.cveSsoestatus = cveSsoestatus;
	}


	@Column(name="DES_ESTATUS", length=50)
	public String getDesEstatus() {
		return this.desEstatus;
	}

	public void setDesEstatus(String desEstatus) {
		this.desEstatus = desEstatus;
	}


	
	
}