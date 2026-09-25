package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the SPT_DICTAMEN database table.
 * 
 */
@Entity
@Table(name="SPT_DICTAMEN")
@NamedQuery(name="SptDictamen.findAll", query="SELECT s FROM SptDictamen s")
public class SptDictamen implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTDICTAMEN", sequenceName = "SEQ_SPTDICTAMEN")
	@GeneratedValue(generator = "SEQ_SPTDICTAMEN")
	@Column(name="CVE_ID_DICTAMEN")
	private long cveIdDictamen;

	@Column(name="CVE_DELEGACION")
	private String cveDelegacion;

	@Column(name="CVE_REG_PATRONAL")
	private String cveRegPatronal;

	@Column(name="CVE_SUBDELEGACION")
	private String cveSubdelegacion;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_ELABORACION")
	private Date fecElaboracion;

//	@Temporal(TemporalType.DATE)
//	@Column(name="FEC_MOVIMIENTO")
//	private Date fecMovimiento;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="ID_TIPO_DICTAMEN")
	private String idTipoDictamen;

	@Column(name="IND_OFICIO")
	private BigDecimal indOficio;

	//bi-directional many-to-one association to DitAsignacionNss
	@ManyToOne
	@JoinColumn(name="CVE_ID_ASIGNACION_NSS")
	private DitAsignacionNss ditAsignacionNss;

	//bi-directional many-to-one association to SpcCaracter
	@ManyToOne
	@JoinColumn(name="ID_CARACTER")
	private SpcCaracter spcCaracter;

	//bi-directional many-to-one association to SpcEstadoDictamen
	@ManyToOne
	@JoinColumn(name="ID_ESTADO_DICTAMEN")
	private SpcEstadoDictamen spcEstadoDictamen;

	//bi-directional many-to-one association to SpcTipoFormato
	@ManyToOne
	@JoinColumn(name="ID_TIPO_FORMATO")
	private SpcTipoFormato spcTipoFormato;

	//bi-directional many-to-one association to SptDictamenCambioEdo
	@OneToMany(mappedBy="sptDictamen")
	private List<SptDictamenCambioEdo> sptDictamenCambioEdos;

	//bi-directional one-to-one association to SptDictamenSt3
	@OneToOne(mappedBy="sptDictamen")
	private SptDictamenSt3 sptDictamenSt3;

	//bi-directional one-to-one association to SptDictamenSt4
	@OneToOne(mappedBy="sptDictamen")
	private SptDictamenSt4 sptDictamenSt4;

	//bi-directional one-to-one association to SptDictamenSt6
//	@OneToOne(mappedBy="sptDictamen")
//	private SptDictamenSt6 sptDictamenSt6;

	//bi-directional many-to-one association to SptIdentificadorDictamen
	@OneToMany(mappedBy="sptDictamen")
	private List<SptIdentificadorDictamen> sptIdentificadorDictamens;

	//bi-directional many-to-one association to SptTramitePensionDictamen
	@OneToMany(mappedBy="sptDictamen")
	private List<SptTramitePensionDictamen> sptTramitePensionDictamens;

	public SptDictamen() {
	}

	public long getCveIdDictamen() {
		return this.cveIdDictamen;
	}

	public void setCveIdDictamen(long cveIdDictamen) {
		this.cveIdDictamen = cveIdDictamen;
	}

	public String getCveDelegacion() {
		return this.cveDelegacion;
	}

	public void setCveDelegacion(String cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}

	public String getCveRegPatronal() {
		return this.cveRegPatronal;
	}

	public void setCveRegPatronal(String cveRegPatronal) {
		this.cveRegPatronal = cveRegPatronal;
	}

	public String getCveSubdelegacion() {
		return this.cveSubdelegacion;
	}

	public void setCveSubdelegacion(String cveSubdelegacion) {
		this.cveSubdelegacion = cveSubdelegacion;
	}

	public Date getFecElaboracion() {
		return this.fecElaboracion;
	}

	public void setFecElaboracion(Date fecElaboracion) {
		this.fecElaboracion = fecElaboracion;
	}

//	public Date getFecMovimiento() {
//		return this.fecMovimiento;
//	}
//
//	public void setFecMovimiento(Date fecMovimiento) {
//		this.fecMovimiento = fecMovimiento;
//	}

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

	public String getIdTipoDictamen() {
		return this.idTipoDictamen;
	}

	public void setIdTipoDictamen(String idTipoDictamen) {
		this.idTipoDictamen = idTipoDictamen;
	}

	public BigDecimal getIndOficio() {
		return this.indOficio;
	}

	public void setIndOficio(BigDecimal indOficio) {
		this.indOficio = indOficio;
	}

	public DitAsignacionNss getDitAsignacionNss() {
		return this.ditAsignacionNss;
	}

	public void setDitAsignacionNss(DitAsignacionNss ditAsignacionNss) {
		this.ditAsignacionNss = ditAsignacionNss;
	}

	public SpcCaracter getSpcCaracter() {
		return this.spcCaracter;
	}

	public void setSpcCaracter(SpcCaracter spcCaracter) {
		this.spcCaracter = spcCaracter;
	}

	public SpcEstadoDictamen getSpcEstadoDictamen() {
		return this.spcEstadoDictamen;
	}

	public void setSpcEstadoDictamen(SpcEstadoDictamen spcEstadoDictamen) {
		this.spcEstadoDictamen = spcEstadoDictamen;
	}

	public SpcTipoFormato getSpcTipoFormato() {
		return this.spcTipoFormato;
	}

	public void setSpcTipoFormato(SpcTipoFormato spcTipoFormato) {
		this.spcTipoFormato = spcTipoFormato;
	}

	public List<SptDictamenCambioEdo> getSptDictamenCambioEdos() {
		return this.sptDictamenCambioEdos;
	}

	public void setSptDictamenCambioEdos(List<SptDictamenCambioEdo> sptDictamenCambioEdos) {
		this.sptDictamenCambioEdos = sptDictamenCambioEdos;
	}

	public SptDictamenCambioEdo addSptDictamenCambioEdo(SptDictamenCambioEdo sptDictamenCambioEdo) {
		getSptDictamenCambioEdos().add(sptDictamenCambioEdo);
		sptDictamenCambioEdo.setSptDictamen(this);

		return sptDictamenCambioEdo;
	}

	public SptDictamenCambioEdo removeSptDictamenCambioEdo(SptDictamenCambioEdo sptDictamenCambioEdo) {
		getSptDictamenCambioEdos().remove(sptDictamenCambioEdo);
		sptDictamenCambioEdo.setSptDictamen(null);

		return sptDictamenCambioEdo;
	}

	public SptDictamenSt3 getSptDictamenSt3() {
		return this.sptDictamenSt3;
	}

	public void setSptDictamenSt3(SptDictamenSt3 sptDictamenSt3) {
		this.sptDictamenSt3 = sptDictamenSt3;
	}

	public SptDictamenSt4 getSptDictamenSt4() {
		return this.sptDictamenSt4;
	}

	public void setSptDictamenSt4(SptDictamenSt4 sptDictamenSt4) {
		this.sptDictamenSt4 = sptDictamenSt4;
	}

//	public SptDictamenSt6 getSptDictamenSt6() {
//		return this.sptDictamenSt6;
//	}
//
//	public void setSptDictamenSt6(SptDictamenSt6 sptDictamenSt6) {
//		this.sptDictamenSt6 = sptDictamenSt6;
//	}

	public List<SptIdentificadorDictamen> getSptIdentificadorDictamens() {
		return this.sptIdentificadorDictamens;
	}

	public void setSptIdentificadorDictamens(List<SptIdentificadorDictamen> sptIdentificadorDictamens) {
		this.sptIdentificadorDictamens = sptIdentificadorDictamens;
	}

	public SptIdentificadorDictamen addSptIdentificadorDictamen(SptIdentificadorDictamen sptIdentificadorDictamen) {
		getSptIdentificadorDictamens().add(sptIdentificadorDictamen);
		sptIdentificadorDictamen.setSptDictamen(this);

		return sptIdentificadorDictamen;
	}

	public SptIdentificadorDictamen removeSptIdentificadorDictamen(SptIdentificadorDictamen sptIdentificadorDictamen) {
		getSptIdentificadorDictamens().remove(sptIdentificadorDictamen);
		sptIdentificadorDictamen.setSptDictamen(null);

		return sptIdentificadorDictamen;
	}

	public List<SptTramitePensionDictamen> getSptTramitePensionDictamens() {
		return this.sptTramitePensionDictamens;
	}

	public void setSptTramitePensionDictamens(List<SptTramitePensionDictamen> sptTramitePensionDictamens) {
		this.sptTramitePensionDictamens = sptTramitePensionDictamens;
	}

	public SptTramitePensionDictamen addSptTramitePensionDictamen(SptTramitePensionDictamen sptTramitePensionDictamen) {
		getSptTramitePensionDictamens().add(sptTramitePensionDictamen);
		sptTramitePensionDictamen.setSptDictamen(this);

		return sptTramitePensionDictamen;
	}

	public SptTramitePensionDictamen removeSptTramitePensionDictamen(SptTramitePensionDictamen sptTramitePensionDictamen) {
		getSptTramitePensionDictamens().remove(sptTramitePensionDictamen);
		sptTramitePensionDictamen.setSptDictamen(null);

		return sptTramitePensionDictamen;
	}

}