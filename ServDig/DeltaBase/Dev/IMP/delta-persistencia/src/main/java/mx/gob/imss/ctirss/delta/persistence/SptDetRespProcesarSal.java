package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SPT_DET_RESP_PROCESAR_SAL database table.
 * 
 */
@Entity
@Table(name="SPT_DET_RESP_PROCESAR_SAL")
@NamedQuery(name="SptDetRespProcesarSal.findAll", query="SELECT s FROM SptDetRespProcesarSal s")
public class SptDetRespProcesarSal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTDETRESPPROCESARSAL", sequenceName = "SEQ_SPTDETRESPPROCESARSAL")
	@GeneratedValue(generator = "SEQ_SPTDETRESPPROCESARSAL")
	@Column(name="CVE_ID_DET_RESP_PROCESAR_SAL")
	private long cveIdDetRespProcesarSal;


	@Column(name="CVE_ADMINISTRADORA")
	private String cveAdministradora;

	@Column(name="CVE_CURP")
	private String cveCurp;

	@Column(name="CVE_EDO_PROCESAR")
	private String cveEdoProcesar;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_MOVIMIENTO")
	private Date fecMovimiento;

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

	@Column(name="ID_DIAGNOSTICO_CTAINDIVIDUAL")
	private String idDiagnosticoCtaindividual;

	@Column(name="ID_LOGIN")
	private String idLogin;

	@Column(name="IMP_AHORRO_SOLIDARIO")
	private BigDecimal impAhorroSolidario;

	@Column(name="IMP_APORTA_COMPLEMENTARIAS")
	private BigDecimal impAportaComplementarias;

	@Column(name="IMP_APORTA_LARGO_PLAZO")
	private BigDecimal impAportaLargoPlazo;

	@Column(name="IMP_APORTA_VOLUNTARIAS")
	private BigDecimal impAportaVoluntarias;

	@Column(name="IMP_CE_VE_CUOTA_ESP_ESTATAL")
	private BigDecimal impCeVeCuotaEspEstatal;

	@Column(name="IMP_CUOTA_SOCIAL")
	private BigDecimal impCuotaSocial;

	@Column(name="IMP_CUOTA_SOCIAL_ISSSTE")
	private BigDecimal impCuotaSocialIssste;

	@Column(name="IMP_CVE_ISSSTE")
	private BigDecimal impCveIssste;

	@Column(name="IMP_FOVISSSTE_2008")
	private BigDecimal impFovissste2008;

	@Column(name="IMP_ISSSTE_BANXICO")
	private BigDecimal impIsssteBanxico;

	@Column(name="IMP_RETIRO_92_IMSS")
	private BigDecimal impRetiro92Imss;

	@Column(name="IMP_RETIRO_92_ISSSTE")
	private BigDecimal impRetiro92Issste;

	@Column(name="IMP_RETIRO_97")
	private BigDecimal impRetiro97;

	@Column(name="IMP_RETIRO_ISSSTE_2008")
	private BigDecimal impRetiroIssste2008;

	@Column(name="IMP_VIVIENDA_92_IMSS")
	private BigDecimal impVivienda92Imss;

	@Column(name="IMP_VIVIENDA_97")
	private BigDecimal impVivienda97;

	@Column(name="IMP_VIVIENDA_FOVISSSTE_92")
	private BigDecimal impViviendaFovissste92;

	@Column(name="IND_ESTATUS_VIVIENDA")
	private String indEstatusVivienda;

	@Column(name="NOM_APELLIDO_MATERNO")
	private String nomApellidoMaterno;

	@Column(name="NOM_APELLIDO_PATERNO")
	private String nomApellidoPaterno;

	@Column(name="NOM_NOMBRE")
	private String nomNombre;

	@Column(name="NUM_CAMBIO_DIAGNOSTICO")
	private BigDecimal numCambioDiagnostico;

	//bi-directional many-to-one association to SptRespComunicEntidade
	@ManyToOne
	@JoinColumn(name="CVE_ID_RESP_COMUNIC_ENTIDADES")
	private SptRespComunicEntidade sptRespComunicEntidade;

	public SptDetRespProcesarSal() {
	}

	public long getCveIdDetRespProcesarSal() {
		return this.cveIdDetRespProcesarSal;
	}

	public void setCveIdDetRespProcesarSal(long cveIdDetRespProcesarSal) {
		this.cveIdDetRespProcesarSal = cveIdDetRespProcesarSal;
	}

	public String getCveAdministradora() {
		return this.cveAdministradora;
	}

	public void setCveAdministradora(String cveAdministradora) {
		this.cveAdministradora = cveAdministradora;
	}

	public String getCveCurp() {
		return this.cveCurp;
	}

	public void setCveCurp(String cveCurp) {
		this.cveCurp = cveCurp;
	}

	public String getCveEdoProcesar() {
		return this.cveEdoProcesar;
	}

	public void setCveEdoProcesar(String cveEdoProcesar) {
		this.cveEdoProcesar = cveEdoProcesar;
	}

	public Date getFecMovimiento() {
		return this.fecMovimiento;
	}

	public void setFecMovimiento(Date fecMovimiento) {
		this.fecMovimiento = fecMovimiento;
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

	public String getIdDiagnosticoCtaindividual() {
		return this.idDiagnosticoCtaindividual;
	}

	public void setIdDiagnosticoCtaindividual(String idDiagnosticoCtaindividual) {
		this.idDiagnosticoCtaindividual = idDiagnosticoCtaindividual;
	}

	public String getIdLogin() {
		return this.idLogin;
	}

	public void setIdLogin(String idLogin) {
		this.idLogin = idLogin;
	}

	public BigDecimal getImpAhorroSolidario() {
		return this.impAhorroSolidario;
	}

	public void setImpAhorroSolidario(BigDecimal impAhorroSolidario) {
		this.impAhorroSolidario = impAhorroSolidario;
	}

	public BigDecimal getImpAportaComplementarias() {
		return this.impAportaComplementarias;
	}

	public void setImpAportaComplementarias(BigDecimal impAportaComplementarias) {
		this.impAportaComplementarias = impAportaComplementarias;
	}

	public BigDecimal getImpAportaLargoPlazo() {
		return this.impAportaLargoPlazo;
	}

	public void setImpAportaLargoPlazo(BigDecimal impAportaLargoPlazo) {
		this.impAportaLargoPlazo = impAportaLargoPlazo;
	}

	public BigDecimal getImpAportaVoluntarias() {
		return this.impAportaVoluntarias;
	}

	public void setImpAportaVoluntarias(BigDecimal impAportaVoluntarias) {
		this.impAportaVoluntarias = impAportaVoluntarias;
	}

	public BigDecimal getImpCeVeCuotaEspEstatal() {
		return this.impCeVeCuotaEspEstatal;
	}

	public void setImpCeVeCuotaEspEstatal(BigDecimal impCeVeCuotaEspEstatal) {
		this.impCeVeCuotaEspEstatal = impCeVeCuotaEspEstatal;
	}

	public BigDecimal getImpCuotaSocial() {
		return this.impCuotaSocial;
	}

	public void setImpCuotaSocial(BigDecimal impCuotaSocial) {
		this.impCuotaSocial = impCuotaSocial;
	}

	public BigDecimal getImpCuotaSocialIssste() {
		return this.impCuotaSocialIssste;
	}

	public void setImpCuotaSocialIssste(BigDecimal impCuotaSocialIssste) {
		this.impCuotaSocialIssste = impCuotaSocialIssste;
	}

	public BigDecimal getImpCveIssste() {
		return this.impCveIssste;
	}

	public void setImpCveIssste(BigDecimal impCveIssste) {
		this.impCveIssste = impCveIssste;
	}

	public BigDecimal getImpFovissste2008() {
		return this.impFovissste2008;
	}

	public void setImpFovissste2008(BigDecimal impFovissste2008) {
		this.impFovissste2008 = impFovissste2008;
	}

	public BigDecimal getImpIsssteBanxico() {
		return this.impIsssteBanxico;
	}

	public void setImpIsssteBanxico(BigDecimal impIsssteBanxico) {
		this.impIsssteBanxico = impIsssteBanxico;
	}

	public BigDecimal getImpRetiro92Imss() {
		return this.impRetiro92Imss;
	}

	public void setImpRetiro92Imss(BigDecimal impRetiro92Imss) {
		this.impRetiro92Imss = impRetiro92Imss;
	}

	public BigDecimal getImpRetiro92Issste() {
		return this.impRetiro92Issste;
	}

	public void setImpRetiro92Issste(BigDecimal impRetiro92Issste) {
		this.impRetiro92Issste = impRetiro92Issste;
	}

	public BigDecimal getImpRetiro97() {
		return this.impRetiro97;
	}

	public void setImpRetiro97(BigDecimal impRetiro97) {
		this.impRetiro97 = impRetiro97;
	}

	public BigDecimal getImpRetiroIssste2008() {
		return this.impRetiroIssste2008;
	}

	public void setImpRetiroIssste2008(BigDecimal impRetiroIssste2008) {
		this.impRetiroIssste2008 = impRetiroIssste2008;
	}

	public BigDecimal getImpVivienda92Imss() {
		return this.impVivienda92Imss;
	}

	public void setImpVivienda92Imss(BigDecimal impVivienda92Imss) {
		this.impVivienda92Imss = impVivienda92Imss;
	}

	public BigDecimal getImpVivienda97() {
		return this.impVivienda97;
	}

	public void setImpVivienda97(BigDecimal impVivienda97) {
		this.impVivienda97 = impVivienda97;
	}

	public BigDecimal getImpViviendaFovissste92() {
		return this.impViviendaFovissste92;
	}

	public void setImpViviendaFovissste92(BigDecimal impViviendaFovissste92) {
		this.impViviendaFovissste92 = impViviendaFovissste92;
	}

	public String getIndEstatusVivienda() {
		return this.indEstatusVivienda;
	}

	public void setIndEstatusVivienda(String indEstatusVivienda) {
		this.indEstatusVivienda = indEstatusVivienda;
	}

	public String getNomApellidoMaterno() {
		return this.nomApellidoMaterno;
	}

	public void setNomApellidoMaterno(String nomApellidoMaterno) {
		this.nomApellidoMaterno = nomApellidoMaterno;
	}

	public String getNomApellidoPaterno() {
		return this.nomApellidoPaterno;
	}

	public void setNomApellidoPaterno(String nomApellidoPaterno) {
		this.nomApellidoPaterno = nomApellidoPaterno;
	}

	public String getNomNombre() {
		return this.nomNombre;
	}

	public void setNomNombre(String nomNombre) {
		this.nomNombre = nomNombre;
	}

	public BigDecimal getNumCambioDiagnostico() {
		return this.numCambioDiagnostico;
	}

	public void setNumCambioDiagnostico(BigDecimal numCambioDiagnostico) {
		this.numCambioDiagnostico = numCambioDiagnostico;
	}

	public SptRespComunicEntidade getSptRespComunicEntidade() {
		return this.sptRespComunicEntidade;
	}

	public void setSptRespComunicEntidade(SptRespComunicEntidade sptRespComunicEntidade) {
		this.sptRespComunicEntidade = sptRespComunicEntidade;
	}

}