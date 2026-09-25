package mx.gob.imss.ctirss.admonusuarios.entidad;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.admonusuarios.abstractModel.AbstractModel;


/**
 * The persistent class for the DIC_SUBDELEGACION database table.
 * 
 */
@Entity
@Table(name="DIC_SUBDELEGACION")
public class DicSubdelegacion  extends AbstractModel {
	private static final long serialVersionUID = 1L;
	private long cveIdSubdelegacion;
	private String anioIniOper;
	private String claveSubdelegacion;
	private DicDelegacion dicDelegacion;
	private String desSubdelegacion;
	private String domicilioId;
	private Date fecRegistroActualizado;
	private Date fecRegistroAlta;
	private Date fecRegistroBaja;
	private Set<DicUmf> dicUmfs;
	private Set<SsoSolicitud> ssoSolicituds;

    public DicSubdelegacion() {
    }


	@Id
	@Column(name="CVE_ID_SUBDELEGACION", unique=true, nullable=false)
	public long getCveIdSubdelegacion() {
		return this.cveIdSubdelegacion;
	}

	public void setCveIdSubdelegacion(long cveIdSubdelegacion) {
		this.cveIdSubdelegacion = cveIdSubdelegacion;
	}


	@Column(name="ANIO_INI_OPER", length=50)
	public String getAnioIniOper() {
		return this.anioIniOper;
	}

	public void setAnioIniOper(String anioIniOper) {
		this.anioIniOper = anioIniOper;
	}


	@Column(name="CLAVE_SUBDELEGACION", length=100)
	public String getClaveSubdelegacion() {
		return this.claveSubdelegacion;
	}

	public void setClaveSubdelegacion(String claveSubdelegacion) {
		this.claveSubdelegacion = claveSubdelegacion;
	}


	//bi-directional many-to-one association to DicDelegacion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_DELEGACION")
	public DicDelegacion getDicDelegacion() {
		return this.dicDelegacion;
	}

	public void setDicDelegacion(DicDelegacion dicDelegacion) {
		this.dicDelegacion = dicDelegacion;
	}

	@Column(name="DES_SUBDELEGACION", length=255)
	public String getDesSubdelegacion() {
		return this.desSubdelegacion;
	}

	public void setDesSubdelegacion(String desSubdelegacion) {
		this.desSubdelegacion = desSubdelegacion;
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


	//bi-directional many-to-one association to DicUmf
	@OneToMany(mappedBy="dicSubdelegacion")
	public Set<DicUmf> getDicUmfs() {
		return this.dicUmfs;
	}

	public void setDicUmfs(Set<DicUmf> dicUmfs) {
		this.dicUmfs = dicUmfs;
	}
	

	//bi-directional many-to-one association to SsoSolicitud
	@OneToMany(mappedBy="dicSubdelegacion")
	public Set<SsoSolicitud> getSsoSolicituds() {
		return this.ssoSolicituds;
	}

	public void setSsoSolicituds(Set<SsoSolicitud> ssoSolicituds) {
		this.ssoSolicituds = ssoSolicituds;
	}
	
}