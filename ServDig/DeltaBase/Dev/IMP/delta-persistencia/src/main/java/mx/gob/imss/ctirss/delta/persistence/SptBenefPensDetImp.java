package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SPT_BENEF_PENS_DET_IMPS database table.
 * 
 */
@Entity
@Table(name="SPT_BENEF_PENS_DET_IMPS")
@NamedQuery(name="SptBenefPensDetImp.findAll", query="SELECT s FROM SptBenefPensDetImp s")
public class SptBenefPensDetImp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTBENEFPENSDETIMPS", sequenceName = "SEQ_SPTBENEFPENSDETIMPS")
	@GeneratedValue(generator = "SEQ_SPTBENEFPENSDETIMPS")
	@Column(name="CVE_ID_BENEF_PENS_DET_IMPS")
	private long cveIdBenefPensDetImps;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_BAJA_SUSPENSION")
	private Date fecBajaSuspension;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INICIO_AJUSTE")
	private Date fecInicioAjuste;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REFORMA_LEY")
	private Date fecReformaLey;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_VENCIMIENTO")
	private Date fecVencimiento;

	@Column(name="IMP_AYUDA_ASISTENCIAL")
	private BigDecimal impAyudaAsistencial;

	@Column(name="IMP_CUANTIA_MENSUAL")
	private BigDecimal impCuantiaMensual;

	@Column(name="IMP_REFORMA_LEY")
	private BigDecimal impReformaLey;

	@Column(name="POR_ASIGNACION_LEY")
	private BigDecimal porAsignacionLey;

	@Column(name="POR_AYUDA_ASISTENCIAL")
	private BigDecimal porAyudaAsistencial;

	//bi-directional many-to-one association to SptBeneficiarioPension
	@ManyToOne
	@JoinColumn(name="CVE_ID_BENEFICIARIO_PENSION")
	private SptBeneficiarioPension sptBeneficiarioPension;

	@ManyToOne
	@JoinColumn(name="CVE_ID_BENEFICIARIO_SOLICITUD")
	private SptBeneficiarioSolicitud sptBeneficiarioSolicitud;
	
	public SptBenefPensDetImp() {
	}

	public long getCveIdBenefPensDetImps() {
		return this.cveIdBenefPensDetImps;
	}

	public void setCveIdBenefPensDetImps(long cveIdBenefPensDetImps) {
		this.cveIdBenefPensDetImps = cveIdBenefPensDetImps;
	}

	public Date getFecBajaSuspension() {
		return this.fecBajaSuspension;
	}

	public void setFecBajaSuspension(Date fecBajaSuspension) {
		this.fecBajaSuspension = fecBajaSuspension;
	}

	public Date getFecInicioAjuste() {
		return this.fecInicioAjuste;
	}

	public void setFecInicioAjuste(Date fecInicioAjuste) {
		this.fecInicioAjuste = fecInicioAjuste;
	}

	public Date getFecReformaLey() {
		return this.fecReformaLey;
	}

	public void setFecReformaLey(Date fecReformaLey) {
		this.fecReformaLey = fecReformaLey;
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

	public Date getFecVencimiento() {
		return this.fecVencimiento;
	}

	public void setFecVencimiento(Date fecVencimiento) {
		this.fecVencimiento = fecVencimiento;
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

	public SptBeneficiarioPension getSptBeneficiarioPension() {
		return this.sptBeneficiarioPension;
	}

	public void setSptBeneficiarioPension(SptBeneficiarioPension sptBeneficiarioPension) {
		this.sptBeneficiarioPension = sptBeneficiarioPension;
	}

	public SptBeneficiarioSolicitud getSptBeneficiarioSolicitud() {
		return sptBeneficiarioSolicitud;
	}

	public void setSptBeneficiarioSolicitud(
			SptBeneficiarioSolicitud sptBeneficiarioSolicitud) {
		this.sptBeneficiarioSolicitud = sptBeneficiarioSolicitud;
	}

	
}
