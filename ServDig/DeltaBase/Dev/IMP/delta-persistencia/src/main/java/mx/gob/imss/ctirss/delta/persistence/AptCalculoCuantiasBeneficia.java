package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the APT_CALCULO_CUANTIAS_BENEFICIA database table.
 * 
 */
@Entity
@Table(name="APT_CALCULO_CUANTIAS_BENEFICIA")
@NamedQuery(name="AptCalculoCuantiasBeneficia.findAll", query="SELECT a FROM AptCalculoCuantiasBeneficia a")
public class AptCalculoCuantiasBeneficia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_APTCALCULOCUANTIASBENEFICI", sequenceName = "SEQ_APTCALCULOCUANTIASBENEFICI")
	@GeneratedValue(generator = "SEQ_APTCALCULOCUANTIASBENEFICI")
	@Column(name="CVE_ID_CALCULO_CUANTIAS_BENEFI")
	private long cveIdCalculoCuantiasBenefi;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IMP_AYUDA_ASISTENCIAL")
	private BigDecimal impAyudaAsistencial;

	@Column(name="IMP_CUANTIA_MENSUAL")
	private BigDecimal impCuantiaMensual;

	@Column(name="IMP_DIF_MONTO_MIN_VIUDEZ")
	private BigDecimal impDifMontoMinViudez;

	@Column(name="IMP_DIF_MONTO_MINIMO_PMG")
	private BigDecimal impDifMontoMinimoPmg;

	@Column(name="IMP_MONTO_MIN_VIUDEZ")
	private BigDecimal impMontoMinViudez;

	@Column(name="IMP_REFORMA_LEY")
	private BigDecimal impReformaLey;

	@Column(name="POR_ASIGNACION_LEY")
	private BigDecimal porAsignacionLey;

	@Column(name="POR_AYUDA_ASISTENCIAL")
	private BigDecimal porAyudaAsistencial;

	@Column(name="CVE_REGIMEN")
	private String cveRegimen;

	@Column(name="CVE_CUENTA_USUARIO")
	private String cveCuentaUsuario;

	//bi-directional many-to-one association to SptBeneficiarioSolicitud
	@ManyToOne
	@JoinColumn(name="CVE_ID_BENEFICIARIO_SOLICITUD")
	private SptBeneficiarioSolicitud sptBeneficiarioSolicitud;

	public AptCalculoCuantiasBeneficia() {
	}

	public long getCveIdCalculoCuantiasBenefi() {
		return this.cveIdCalculoCuantiasBenefi;
	}

	public void setCveIdCalculoCuantiasBenefi(long cveIdCalculoCuantiasBenefi) {
		this.cveIdCalculoCuantiasBenefi = cveIdCalculoCuantiasBenefi;
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

	public BigDecimal getImpAyudaAsistencial() {
		return this.impAyudaAsistencial;
	}

	public void setImpAyudaAsistencial(BigDecimal impAyudaAsistencial) {
		this.impAyudaAsistencial = impAyudaAsistencial;
	}

	public BigDecimal getImpCuantiaMensual() {
		return this.impCuantiaMensual;
	}

	public void setImpCuantiaMensual(BigDecimal impCuantiaMensual) {
		this.impCuantiaMensual = impCuantiaMensual;
	}

	public BigDecimal getImpDifMontoMinViudez() {
		return this.impDifMontoMinViudez;
	}

	public void setImpDifMontoMinViudez(BigDecimal impDifMontoMinViudez) {
		this.impDifMontoMinViudez = impDifMontoMinViudez;
	}

	public BigDecimal getImpDifMontoMinimoPmg() {
		return this.impDifMontoMinimoPmg;
	}

	public void setImpDifMontoMinimoPmg(BigDecimal impDifMontoMinimoPmg) {
		this.impDifMontoMinimoPmg = impDifMontoMinimoPmg;
	}

	public BigDecimal getImpMontoMinViudez() {
		return this.impMontoMinViudez;
	}

	public void setImpMontoMinViudez(BigDecimal impMontoMinViudez) {
		this.impMontoMinViudez = impMontoMinViudez;
	}

	public BigDecimal getImpReformaLey() {
		return this.impReformaLey;
	}

	public void setImpReformaLey(BigDecimal impReformaLey) {
		this.impReformaLey = impReformaLey;
	}

	public BigDecimal getPorAsignacionLey() {
		return this.porAsignacionLey;
	}

	public void setPorAsignacionLey(BigDecimal porAsignacionLey) {
		this.porAsignacionLey = porAsignacionLey;
	}

	public BigDecimal getPorAyudaAsistencial() {
		return this.porAyudaAsistencial;
	}

	public void setPorAyudaAsistencial(BigDecimal porAyudaAsistencial) {
		this.porAyudaAsistencial = porAyudaAsistencial;
	}

	public String getCveRegimen() {
		return cveRegimen;
	}

	public void setCveRegimen(String cveRegimen) {
		this.cveRegimen = cveRegimen;
	}

	public String getCveCuentaUsuario() {
		return cveCuentaUsuario;
	}

	public void setCveCuentaUsuario(String cveCuentaUsuario) {
		this.cveCuentaUsuario = cveCuentaUsuario;
	}

	public SptBeneficiarioSolicitud getSptBeneficiarioSolicitud() {
		return this.sptBeneficiarioSolicitud;
	}

	public void setSptBeneficiarioSolicitud(SptBeneficiarioSolicitud sptBeneficiarioSolicitud) {
		this.sptBeneficiarioSolicitud = sptBeneficiarioSolicitud;
	}

}
