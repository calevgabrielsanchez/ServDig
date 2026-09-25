package mx.gob.imss.ctirss.admonusuarios.entidad;

import java.util.Date;

import javax.persistence.CascadeType;
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
 * The persistent class for the SSO_ACCESOMODULOS database table.
 * 
 */
@Entity
@Table(name="SSO_ACCESOMODULOS")
public class SsoAccesomodulo  extends AbstractModel {
	private static final long serialVersionUID = 1L;
	private Long cveSsoaccesomodulo;
	private Date fecFechareg;
	private SsoAprobador ssoAprobador;
	private SsoCatdeptomodulo ssoCatdeptomodulo;
	private SsoCatestatus ssoCatestatus;
	private SsoSolicitud ssoSolicitud;

    public SsoAccesomodulo() {
    }


	@Id
	@Column(name="CVE_SSOACCESOMODULO", unique=true, nullable=true)
	public Long getCveSsoaccesomodulo() {
		return this.cveSsoaccesomodulo;
	}

	public void setCveSsoaccesomodulo(Long cveSsoaccesomodulo) {
		this.cveSsoaccesomodulo = cveSsoaccesomodulo;
	}


    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_FECHAREG")
	public Date getFecFechareg() {
		return this.fecFechareg;
	}

	public void setFecFechareg(Date fecFechareg) {
		this.fecFechareg = fecFechareg;
	}


	//bi-directional many-to-one association to SsoAprobador
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_APROBADOR")
	public SsoAprobador getSsoAprobador() {
		return this.ssoAprobador;
	}

	public void setSsoAprobador(SsoAprobador ssoAprobador) {
		this.ssoAprobador = ssoAprobador;
	}
	

	//bi-directional many-to-one association to SsoCatdeptomodulo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_SSODEPTOMODULO")
	public SsoCatdeptomodulo getSsoCatdeptomodulo() {
		return this.ssoCatdeptomodulo;
	}

	public void setSsoCatdeptomodulo(SsoCatdeptomodulo ssoCatdeptomodulo) {
		this.ssoCatdeptomodulo = ssoCatdeptomodulo;
	}
	

	//bi-directional many-to-one association to SsoCatestatus
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_SSOESTATUS")
	public SsoCatestatus getSsoCatestatus() {
		return this.ssoCatestatus;
	}

	public void setSsoCatestatus(SsoCatestatus ssoCatestatus) {
		this.ssoCatestatus = ssoCatestatus;
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
	
}