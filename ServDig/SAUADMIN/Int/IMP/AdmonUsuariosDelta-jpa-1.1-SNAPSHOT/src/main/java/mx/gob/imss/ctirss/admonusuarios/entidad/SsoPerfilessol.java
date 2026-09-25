package mx.gob.imss.ctirss.admonusuarios.entidad;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.admonusuarios.abstractModel.AbstractModel;


/**
 * The persistent class for the SSO_PERFILESSOL database table.
 * 
 */
@Entity
@Table(name="SSO_PERFILESSOL")
public class SsoPerfilessol  extends AbstractModel {
	private static final long serialVersionUID = 1L;
	private Long cveSsoperfilessol;
	private SsoCatpuesto ssoCatpuesto;
	private SsoSolicitud ssoSolicitud;
	private Date fecFechaRegistro;
	private String desDefault;
	
    public SsoPerfilessol() {
    }


	@Id
	@Column(name="CVE_SSOPERFILESSOL", unique=true, nullable=true)
	public Long getCveSsoperfilessol() {
		return this.cveSsoperfilessol;
	}

	public void setCveSsoperfilessol(Long cveSsoperfilessol) {
		this.cveSsoperfilessol = cveSsoperfilessol;
	}


	//bi-directional many-to-one association to SsoCatpuesto
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_SSOPUESTO")
	public SsoCatpuesto getSsoCatpuesto() {
		return this.ssoCatpuesto;
	}

	public void setSsoCatpuesto(SsoCatpuesto ssoCatpuesto) {
		this.ssoCatpuesto = ssoCatpuesto;
	}
	

	//bi-directional many-to-one association to SsoSolicitud
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_SSOSOLICITUD")
	public SsoSolicitud getSsoSolicitud() {
		return this.ssoSolicitud;
	}

	public void setSsoSolicitud(SsoSolicitud ssoSolicitud) {
		this.ssoSolicitud = ssoSolicitud;
	}


	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_FECHAREGISTRO")
	public Date getFecFechaRegistro() {
		return fecFechaRegistro;
	}


	public void setFecFechaRegistro(Date fecRegistroBaja) {
		this.fecFechaRegistro = fecRegistroBaja;
	}

	
	@Column(name="DES_DEFAULT", nullable=false, length=3)
	public String getDesDefault() {
		return desDefault;
	}


	public void setDesDefault(String desDefalt) {
		this.desDefault = desDefalt;
	}
	
}