package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_AUTORIZACION_PERMTE database table.
 * 
 */
@Entity
@Table(name="DIT_AUTORIZACION_PERMTE")
public class DitAutorizacionPermte implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Temporal( TemporalType.DATE)
	@Column(name="CVE_ID_AUTORIZACION_PERMTE", nullable=false)
	private Date cveIdAutorizacionPermte;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FIN_REPORTADO")
	private Date fecFinReportado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FIN_SERV")
	private Date fecFinServ;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INI_REPORTADO")
	private Date fecIniReportado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INI_SERV")
	private Date fecIniServ;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitAsegurado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_ASEGURADO")
	private DitAsegurado ditAsegurado;

	//bi-directional many-to-one association to DicSubdelegacion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_SUBDELEGACION")
	private DicSubdelegacion dicSubdelegacion;

    public DitAutorizacionPermte() {
    }

	public Date getCveIdAutorizacionPermte() {
		return this.cveIdAutorizacionPermte;
	}

	public void setCveIdAutorizacionPermte(Date cveIdAutorizacionPermte) {
		this.cveIdAutorizacionPermte = cveIdAutorizacionPermte;
	}

	public Date getFecFinReportado() {
		return this.fecFinReportado;
	}

	public void setFecFinReportado(Date fecFinReportado) {
		this.fecFinReportado = fecFinReportado;
	}

	public Date getFecFinServ() {
		return this.fecFinServ;
	}

	public void setFecFinServ(Date fecFinServ) {
		this.fecFinServ = fecFinServ;
	}

	public Date getFecIniReportado() {
		return this.fecIniReportado;
	}

	public void setFecIniReportado(Date fecIniReportado) {
		this.fecIniReportado = fecIniReportado;
	}

	public Date getFecIniServ() {
		return this.fecIniServ;
	}

	public void setFecIniServ(Date fecIniServ) {
		this.fecIniServ = fecIniServ;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public DitAsegurado getDitAsegurado() {
		return this.ditAsegurado;
	}

	public void setDitAsegurado(DitAsegurado ditAsegurado) {
		this.ditAsegurado = ditAsegurado;
	}
	
	public DicSubdelegacion getDicSubdelegacion() {
		return this.dicSubdelegacion;
	}

	public void setDicSubdelegacion(DicSubdelegacion dicSubdelegacion) {
		this.dicSubdelegacion = dicSubdelegacion;
	}
	
}