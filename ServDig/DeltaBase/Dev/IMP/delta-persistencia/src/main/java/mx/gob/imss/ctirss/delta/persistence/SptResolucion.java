package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the SPT_RESOLUCION database table.
 * 
 */
@Entity
@Table(name="SPT_RESOLUCION")
@NamedQuery(name="SptResolucion.findAll", query="SELECT s FROM SptResolucion s")
public class SptResolucion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTRESOLUCION", sequenceName = "SEQ_SPTRESOLUCION")
	@GeneratedValue(generator = "SEQ_SPTRESOLUCION")
	@Column(name="CVE_ID_RESOLUCION")
	private long cveIdResolucion;

	@Column(name="CVE_ID_CABECERA_SOLIC_PENSION")
	private BigDecimal cveIdCabeceraSolicPension;

	@Column(name="CVE_REGISTRO_PATRONAL")
	private String cveRegistroPatronal;

	@Column(name="CVE_RESOLUCION")
	private String cveResolucion;

	@Column(name="DES_ACUERDO")
	private String desAcuerdo;

	@Column(name="DES_ART_FRACS")
	private String desArtFracs;

	@Column(name="DES_ART_FRACS_514")
	private String desArtFracs514;

	@Column(name="DES_CARGO_RESPONSABLE")
	private String desCargoResponsable;

	@Column(name="DES_INCAPACIDAD")
	private String desIncapacidad;

	@Column(name="DES_LEYENDA_PAGO")
	private String desLeyendaPago;

	@Column(name="DES_RESOLUTIVO_ESPECIAL")
	private String desResolutivoEspecial;

	@Column(name="DESC_ART_RES")
	private String descArtRes;

	@Column(name="DESC_ART_RES_514")
	private String descArtRes514;

	@Column(name="DIR_SUCURSAL_PAGO_FECHA")
	private String dirSucursalPagoFecha;

	@Column(name="FEC_EMISION_RESOLUCION")
	private String fecEmisionResolucion;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_MOVIMIENTO")
	private Date fecMovimiento;

	@Column(name="ID_ANTECEDENTE")
	private String idAntecedente;

	@Column(name="ID_ART_BASE_NEGATIVA_CONS")
	private String idArtBaseNegativaCons;

	@Column(name="ID_RESOLUCION")
	private BigDecimal idResolucion;

	@Column(name="ID_SOLICITUD")
	private String idSolicitud;

	@Column(name="IMP_ANUAL_ASIGNACIONES_FAM")
	private BigDecimal impAnualAsignacionesFam;

	@Column(name="IMP_BASE_PENSION")
	private BigDecimal impBasePension;

	@Column(name="IMP_CUANTIA_PENSION")
	private BigDecimal impCuantiaPension;

	@Column(name="IMP_GRUPO")
	private BigDecimal impGrupo;

	@Column(name="IMP_MENSUAL_ART170")
	private BigDecimal impMensualArt170;

	@Column(name="IMP_MONTO_CTVO_RENTA_VITAL")
	private BigDecimal impMontoCtvoRentaVital;

	@Column(name="IMP_MONTO_CTVO_SEGURO_SOBREVI")
	private BigDecimal impMontoCtvoSeguroSobrevi;

	@Column(name="IMP_MONTO_CTVO_TOTAL")
	private BigDecimal impMontoCtvoTotal;

	@Column(name="IMP_MONTO_CUENTA_INDIVIDUAL")
	private BigDecimal impMontoCuentaIndividual;

	@Column(name="IMP_PENSION_ANUAL")
	private BigDecimal impPensionAnual;

	@Column(name="IMP_PENSION_ANUAL_TOTAL")
	private BigDecimal impPensionAnualTotal;

	@Column(name="IMP_PENSION_ART158")
	private BigDecimal impPensionArt158;

	@Column(name="IMP_PENSION_MENSUAL_TOTAL")
	private BigDecimal impPensionMensualTotal;

	@Column(name="IMP_SALARIO_PROM_INCP")
	private BigDecimal impSalarioPromIncp;

	@Column(name="IMP_SALARIO_RIESGO")
	private BigDecimal impSalarioRiesgo;

	@Column(name="IMP_SUMA_ASEGURADA")
	private BigDecimal impSumaAsegurada;

	@Column(name="IMP_TOTAL_MENSUAL")
	private BigDecimal impTotalMensual;

	@Column(name="NOM_RESPONSABLE_FIRMA")
	private String nomResponsableFirma;

	@Column(name="NOM_SUCURSAL_PAGO")
	private String nomSucursalPago;

	//bi-directional many-to-one association to SptDiagnosticosResolucion
	@OneToMany(mappedBy="sptResolucion")
	private List<SptDiagnosticosResolucion> sptDiagnosticosResolucions;

	//bi-directional many-to-one association to SptTramitePension
	@ManyToOne
	@JoinColumn(name="CVE_ID_TRAMITE_PENSION")
	private SptTramitePension sptTramitePension;

	//bi-directional many-to-one association to SptResolutivosResolucion
	@OneToMany(mappedBy="sptResolucion")
	private List<SptResolutivosResolucion> sptResolutivosResolucions;

	public SptResolucion() {
	}

	public long getCveIdResolucion() {
		return this.cveIdResolucion;
	}

	public void setCveIdResolucion(long cveIdResolucion) {
		this.cveIdResolucion = cveIdResolucion;
	}

	public BigDecimal getCveIdCabeceraSolicPension() {
		return this.cveIdCabeceraSolicPension;
	}

	public void setCveIdCabeceraSolicPension(BigDecimal cveIdCabeceraSolicPension) {
		this.cveIdCabeceraSolicPension = cveIdCabeceraSolicPension;
	}

	public String getCveRegistroPatronal() {
		return this.cveRegistroPatronal;
	}

	public void setCveRegistroPatronal(String cveRegistroPatronal) {
		this.cveRegistroPatronal = cveRegistroPatronal;
	}

	public String getCveResolucion() {
		return this.cveResolucion;
	}

	public void setCveResolucion(String cveResolucion) {
		this.cveResolucion = cveResolucion;
	}

	public String getDesAcuerdo() {
		return this.desAcuerdo;
	}

	public void setDesAcuerdo(String desAcuerdo) {
		this.desAcuerdo = desAcuerdo;
	}

	public String getDesArtFracs() {
		return this.desArtFracs;
	}

	public void setDesArtFracs(String desArtFracs) {
		this.desArtFracs = desArtFracs;
	}

	public String getDesArtFracs514() {
		return this.desArtFracs514;
	}

	public void setDesArtFracs514(String desArtFracs514) {
		this.desArtFracs514 = desArtFracs514;
	}

	public String getDesCargoResponsable() {
		return this.desCargoResponsable;
	}

	public void setDesCargoResponsable(String desCargoResponsable) {
		this.desCargoResponsable = desCargoResponsable;
	}

	public String getDesIncapacidad() {
		return this.desIncapacidad;
	}

	public void setDesIncapacidad(String desIncapacidad) {
		this.desIncapacidad = desIncapacidad;
	}

	public String getDesLeyendaPago() {
		return this.desLeyendaPago;
	}

	public void setDesLeyendaPago(String desLeyendaPago) {
		this.desLeyendaPago = desLeyendaPago;
	}

	public String getDesResolutivoEspecial() {
		return this.desResolutivoEspecial;
	}

	public void setDesResolutivoEspecial(String desResolutivoEspecial) {
		this.desResolutivoEspecial = desResolutivoEspecial;
	}

	public String getDescArtRes() {
		return this.descArtRes;
	}

	public void setDescArtRes(String descArtRes) {
		this.descArtRes = descArtRes;
	}

	public String getDescArtRes514() {
		return this.descArtRes514;
	}

	public void setDescArtRes514(String descArtRes514) {
		this.descArtRes514 = descArtRes514;
	}

	public String getDirSucursalPagoFecha() {
		return this.dirSucursalPagoFecha;
	}

	public void setDirSucursalPagoFecha(String dirSucursalPagoFecha) {
		this.dirSucursalPagoFecha = dirSucursalPagoFecha;
	}

	public String getFecEmisionResolucion() {
		return this.fecEmisionResolucion;
	}

	public void setFecEmisionResolucion(String fecEmisionResolucion) {
		this.fecEmisionResolucion = fecEmisionResolucion;
	}

	public Date getFecMovimiento() {
		return this.fecMovimiento;
	}

	public void setFecMovimiento(Date fecMovimiento) {
		this.fecMovimiento = fecMovimiento;
	}

	public String getIdAntecedente() {
		return this.idAntecedente;
	}

	public void setIdAntecedente(String idAntecedente) {
		this.idAntecedente = idAntecedente;
	}

	public String getIdArtBaseNegativaCons() {
		return this.idArtBaseNegativaCons;
	}

	public void setIdArtBaseNegativaCons(String idArtBaseNegativaCons) {
		this.idArtBaseNegativaCons = idArtBaseNegativaCons;
	}

	public BigDecimal getIdResolucion() {
		return this.idResolucion;
	}

	public void setIdResolucion(BigDecimal idResolucion) {
		this.idResolucion = idResolucion;
	}

	public String getIdSolicitud() {
		return this.idSolicitud;
	}

	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public BigDecimal getImpAnualAsignacionesFam() {
		return this.impAnualAsignacionesFam;
	}

	public void setImpAnualAsignacionesFam(BigDecimal impAnualAsignacionesFam) {
		this.impAnualAsignacionesFam = impAnualAsignacionesFam;
	}

	public BigDecimal getImpBasePension() {
		return this.impBasePension;
	}

	public void setImpBasePension(BigDecimal impBasePension) {
		this.impBasePension = impBasePension;
	}

	public BigDecimal getImpCuantiaPension() {
		return this.impCuantiaPension;
	}

	public void setImpCuantiaPension(BigDecimal impCuantiaPension) {
		this.impCuantiaPension = impCuantiaPension;
	}

	public BigDecimal getImpGrupo() {
		return this.impGrupo;
	}

	public void setImpGrupo(BigDecimal impGrupo) {
		this.impGrupo = impGrupo;
	}

	public BigDecimal getImpMensualArt170() {
		return this.impMensualArt170;
	}

	public void setImpMensualArt170(BigDecimal impMensualArt170) {
		this.impMensualArt170 = impMensualArt170;
	}

	public BigDecimal getImpMontoCtvoRentaVital() {
		return this.impMontoCtvoRentaVital;
	}

	public void setImpMontoCtvoRentaVital(BigDecimal impMontoCtvoRentaVital) {
		this.impMontoCtvoRentaVital = impMontoCtvoRentaVital;
	}

	public BigDecimal getImpMontoCtvoSeguroSobrevi() {
		return this.impMontoCtvoSeguroSobrevi;
	}

	public void setImpMontoCtvoSeguroSobrevi(BigDecimal impMontoCtvoSeguroSobrevi) {
		this.impMontoCtvoSeguroSobrevi = impMontoCtvoSeguroSobrevi;
	}

	public BigDecimal getImpMontoCtvoTotal() {
		return this.impMontoCtvoTotal;
	}

	public void setImpMontoCtvoTotal(BigDecimal impMontoCtvoTotal) {
		this.impMontoCtvoTotal = impMontoCtvoTotal;
	}

	public BigDecimal getImpMontoCuentaIndividual() {
		return this.impMontoCuentaIndividual;
	}

	public void setImpMontoCuentaIndividual(BigDecimal impMontoCuentaIndividual) {
		this.impMontoCuentaIndividual = impMontoCuentaIndividual;
	}

	public BigDecimal getImpPensionAnual() {
		return this.impPensionAnual;
	}

	public void setImpPensionAnual(BigDecimal impPensionAnual) {
		this.impPensionAnual = impPensionAnual;
	}

	public BigDecimal getImpPensionAnualTotal() {
		return this.impPensionAnualTotal;
	}

	public void setImpPensionAnualTotal(BigDecimal impPensionAnualTotal) {
		this.impPensionAnualTotal = impPensionAnualTotal;
	}

	public BigDecimal getImpPensionArt158() {
		return this.impPensionArt158;
	}

	public void setImpPensionArt158(BigDecimal impPensionArt158) {
		this.impPensionArt158 = impPensionArt158;
	}

	public BigDecimal getImpPensionMensualTotal() {
		return this.impPensionMensualTotal;
	}

	public void setImpPensionMensualTotal(BigDecimal impPensionMensualTotal) {
		this.impPensionMensualTotal = impPensionMensualTotal;
	}

	public BigDecimal getImpSalarioPromIncp() {
		return this.impSalarioPromIncp;
	}

	public void setImpSalarioPromIncp(BigDecimal impSalarioPromIncp) {
		this.impSalarioPromIncp = impSalarioPromIncp;
	}

	public BigDecimal getImpSalarioRiesgo() {
		return this.impSalarioRiesgo;
	}

	public void setImpSalarioRiesgo(BigDecimal impSalarioRiesgo) {
		this.impSalarioRiesgo = impSalarioRiesgo;
	}

	public BigDecimal getImpSumaAsegurada() {
		return this.impSumaAsegurada;
	}

	public void setImpSumaAsegurada(BigDecimal impSumaAsegurada) {
		this.impSumaAsegurada = impSumaAsegurada;
	}

	public BigDecimal getImpTotalMensual() {
		return this.impTotalMensual;
	}

	public void setImpTotalMensual(BigDecimal impTotalMensual) {
		this.impTotalMensual = impTotalMensual;
	}

	public String getNomResponsableFirma() {
		return this.nomResponsableFirma;
	}

	public void setNomResponsableFirma(String nomResponsableFirma) {
		this.nomResponsableFirma = nomResponsableFirma;
	}

	public String getNomSucursalPago() {
		return this.nomSucursalPago;
	}

	public void setNomSucursalPago(String nomSucursalPago) {
		this.nomSucursalPago = nomSucursalPago;
	}

	public List<SptDiagnosticosResolucion> getSptDiagnosticosResolucions() {
		return this.sptDiagnosticosResolucions;
	}

	public void setSptDiagnosticosResolucions(List<SptDiagnosticosResolucion> sptDiagnosticosResolucions) {
		this.sptDiagnosticosResolucions = sptDiagnosticosResolucions;
	}

	public SptDiagnosticosResolucion addSptDiagnosticosResolucion(SptDiagnosticosResolucion sptDiagnosticosResolucion) {
		getSptDiagnosticosResolucions().add(sptDiagnosticosResolucion);
		sptDiagnosticosResolucion.setSptResolucion(this);

		return sptDiagnosticosResolucion;
	}

	public SptDiagnosticosResolucion removeSptDiagnosticosResolucion(SptDiagnosticosResolucion sptDiagnosticosResolucion) {
		getSptDiagnosticosResolucions().remove(sptDiagnosticosResolucion);
		sptDiagnosticosResolucion.setSptResolucion(null);

		return sptDiagnosticosResolucion;
	}

	public SptTramitePension getSptTramitePension() {
		return this.sptTramitePension;
	}

	public void setSptTramitePension(SptTramitePension sptTramitePension) {
		this.sptTramitePension = sptTramitePension;
	}

	public List<SptResolutivosResolucion> getSptResolutivosResolucions() {
		return this.sptResolutivosResolucions;
	}

	public void setSptResolutivosResolucions(List<SptResolutivosResolucion> sptResolutivosResolucions) {
		this.sptResolutivosResolucions = sptResolutivosResolucions;
	}

	public SptResolutivosResolucion addSptResolutivosResolucion(SptResolutivosResolucion sptResolutivosResolucion) {
		getSptResolutivosResolucions().add(sptResolutivosResolucion);
		sptResolutivosResolucion.setSptResolucion(this);

		return sptResolutivosResolucion;
	}

	public SptResolutivosResolucion removeSptResolutivosResolucion(SptResolutivosResolucion sptResolutivosResolucion) {
		getSptResolutivosResolucions().remove(sptResolutivosResolucion);
		sptResolutivosResolucion.setSptResolucion(null);

		return sptResolutivosResolucion;
	}

}