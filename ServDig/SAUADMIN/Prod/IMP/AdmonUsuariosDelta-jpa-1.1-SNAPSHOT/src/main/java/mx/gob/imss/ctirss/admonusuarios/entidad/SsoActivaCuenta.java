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

@Entity
@Table(name="SSO_ACTIVACUENTA")
public class SsoActivaCuenta  extends AbstractModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long ssoClaveActiva;
	private SsoSolicitud ssoSolicitud;
	private SsoCatestatus ssoEstatus;
	private String claveMD5;
	private Date fechaRegistro;
	private Date fechaVigencia;
	private Date fechaActiva;

	@Id
	@Column(name="CVE_SSOCLAVEACTIVA", unique=true, nullable=true)
	public Long getSsoClaveActiva() {
		return ssoClaveActiva;
	}
	public void setSsoClaveActiva(Long ssoClaveActiva) {
		this.ssoClaveActiva = ssoClaveActiva;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_SSOSOLICITUD")
	public SsoSolicitud getSsoSolicitud() {
		return ssoSolicitud;
	}
	public void setSsoSolicitud(SsoSolicitud ssoSolicitud) {
		this.ssoSolicitud = ssoSolicitud;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_SSOESTATUS")
	public SsoCatestatus getSsoEstatus() {
		return this.ssoEstatus;
	}
	
	public void setSsoEstatus(SsoCatestatus ssoEstatus) {
		this.ssoEstatus = ssoEstatus;
	}
	
	@Column(name="DES_CLAVEMD5", nullable=false, length=20)
	public String getClaveMD5() {
		return claveMD5;
	}
	public void setClaveMD5(String claveM5) {
		this.claveMD5 = claveM5;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FEC_FECHAREG", nullable=true)
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FEC_FECHAVIGENCIA", nullable=true)
	public Date getFechaVigencia() {
		return fechaVigencia;
	}
	public void setFechaVigencia(Date fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FEC_FECHAACTIVA", nullable=true)
	public Date getFechaActiva() {
		return fechaActiva;
	}
	public void setFechaActiva(Date fecha) {
		this.fechaActiva = fecha;
	}
	
	

}
