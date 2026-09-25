package mx.gob.imss.ctirss.admonusuarios.entidad;

import javax.persistence.*;

import mx.gob.imss.ctirss.admonusuarios.abstractModel.AbstractModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the DIC_DELEGACION database table.
 * 
 */
@Entity
@Table(name="DIC_DELEGACION")
public class DicDelegacion extends AbstractModel {
	private static final long serialVersionUID = 1L;
	private long cveIdDelegacion;
	private String anioIniOper;
	private String claveDelegacion;
	private BigDecimal cveCiz;
	private String desDeleg;
	private String domicilioId;
	private Date fecRegistroActualizado;
	private Date fecRegistroAlta;
	private Date fecRegistroBaja;
	private BigDecimal tipDelegacion;
	private Set<SsoSolicitud> ssoSolicituds;

    public DicDelegacion() {
    }


	@Id
	@Column(name="CVE_ID_DELEGACION", unique=true, nullable=false)
	public long getCveIdDelegacion() {
		return this.cveIdDelegacion;
	}

	public void setCveIdDelegacion(long cveIdDelegacion) {
		this.cveIdDelegacion = cveIdDelegacion;
	}


	@Column(name="ANIO_INI_OPER", length=50)
	public String getAnioIniOper() {
		return this.anioIniOper;
	}

	public void setAnioIniOper(String anioIniOper) {
		this.anioIniOper = anioIniOper;
	}


	@Column(name="CLAVE_DELEGACION", length=100)
	public String getClaveDelegacion() {
		return this.claveDelegacion;
	}

	public void setClaveDelegacion(String claveDelegacion) {
		this.claveDelegacion = claveDelegacion;
	}


	@Column(name="CVE_CIZ")
	public BigDecimal getCveCiz() {
		return this.cveCiz;
	}

	public void setCveCiz(BigDecimal cveCiz) {
		this.cveCiz = cveCiz;
	}


	@Column(name="DES_DELEG", length=255)
	public String getDesDeleg() {
		return this.desDeleg;
	}

	public void setDesDeleg(String desDeleg) {
		this.desDeleg = desDeleg;
	}


	@Column(name="DOMICILIO_ID", length=18)
	public String getDomicilioId() {
		return this.domicilioId;
	}

	public void setDomicilioId(String domicilioId) {
		this.domicilioId = domicilioId;
	}


    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}


    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}


    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}


	@Column(name="TIP_DELEGACION")
	public BigDecimal getTipDelegacion() {
		return this.tipDelegacion;
	}

	public void setTipDelegacion(BigDecimal tipDelegacion) {
		this.tipDelegacion = tipDelegacion;
	}


	//bi-directional many-to-one association to SsoSolicitud
	@OneToMany(mappedBy="dicDelegacion")
	public Set<SsoSolicitud> getSsoSolicituds() {
		return this.ssoSolicituds;
	}

	public void setSsoSolicituds(Set<SsoSolicitud> ssoSolicituds) {
		this.ssoSolicituds = ssoSolicituds;
	}
	
}