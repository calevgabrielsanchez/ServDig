package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the SPT_PERS_RECIBE_PAGO_PENS_DET database table.
 * 
 */
@Entity
@Table(name="SPT_PERS_RECIBE_PAGO_PENS_DET")
public class SptPersRecibePagoPensDet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id	
	@SequenceGenerator(name = "SEQ_SPTPERSRECIBEPAGOPENSDET", sequenceName = "SEQ_SPTPERSRECIBEPAGOPENSDET")
	@GeneratedValue(generator = "SEQ_SPTPERSRECIBEPAGOPENSDET")	
	@Column(name="CVE_ID_PERS_RECIBE_PAGO_PENS_D")
	private long cveIdPersRecibePagoPensD;

	@Column(name="CVE_CTA_PAGO")
	private String cveCtaPago;

	@Column(name="CVE_CTO_COSTO")
	private String cveCtoCosto;

	@Column(name="CVE_DELEGACION_ORIGEN")
	private String cveDelegacionOrigen;

	@Column(name="CVE_PERIODO_NOMINA")
	private BigDecimal cvePeriodoNomina;

	@Column(name="CVE_PREI")
	private String cvePrei;

	@Column(name="CVE_SUCURSAL_CTA_PAGO")
	private String cveSucursalCtaPago;

	@Column(name="DES_TIPO_CTA_PAGO")
	private String desTipoCtaPago;

	@Column(name="ID_CONVENIO")
	private String idConvenio;

	@Column(name="ID_PERIODO_PAGO")
	private String idPeriodoPago;

	@Column(name="ID_TIPO_CUENTA_PAGO")
	private String idTipoCuentaPago;

	@Column(name="IND_CURP_VALIDA")
	private String indCurpValida;

	@Column(name="IND_DATOS_PERSONALES")
	private String indDatosPersonales;

	@Column(name="IND_DERECHO_SERV_MED")
	private String indDerechoServMed;

	@Column(name="NUM_CLABE")
	private String numClabe;

	//bi-directional many-to-one association to SpcLugarPago
    @ManyToOne
	@JoinColumns({
		@JoinColumn(name="CVE_ID_SUBDELEGACION", referencedColumnName="CVE_ID_SUBDELEGACION"),
		@JoinColumn(name="ID_ENTIDAD_PAGO", referencedColumnName="ID_ENTIDAD_PAGO"),
		@JoinColumn(name="ID_SUCURSAL_LUGAR_PAGO", referencedColumnName="ID_SUCURSAL_LUGAR_PAGO")
		})
	private SpcLugarPago spcLugarPago;

	//bi-directional many-to-one association to SpcTipoMovimiento
//    @ManyToOne
//	@JoinColumn(name="ID_TIPO_MOVIMIENTO")
//	private SpcTipoMovimiento spcTipoMovimiento;

	//bi-directional many-to-one association to SptPersRecibePagoPen
    @ManyToOne
	@JoinColumn(name="CVE_ID_PERS_RECIBE_PAGO_PENS")
	private SptPersRecibePagoPen sptPersRecibePagoPen;

    public SptPersRecibePagoPensDet() {
    }

	public long getCveIdPersRecibePagoPensD() {
		return this.cveIdPersRecibePagoPensD;
	}

	public void setCveIdPersRecibePagoPensD(long cveIdPersRecibePagoPensD) {
		this.cveIdPersRecibePagoPensD = cveIdPersRecibePagoPensD;
	}

	public String getCveCtaPago() {
		return this.cveCtaPago;
	}

	public void setCveCtaPago(String cveCtaPago) {
		this.cveCtaPago = cveCtaPago;
	}

	public String getCveCtoCosto() {
		return this.cveCtoCosto;
	}

	public void setCveCtoCosto(String cveCtoCosto) {
		this.cveCtoCosto = cveCtoCosto;
	}

	public String getCveDelegacionOrigen() {
		return this.cveDelegacionOrigen;
	}

	public void setCveDelegacionOrigen(String cveDelegacionOrigen) {
		this.cveDelegacionOrigen = cveDelegacionOrigen;
	}

	public BigDecimal getCvePeriodoNomina() {
		return this.cvePeriodoNomina;
	}

	public void setCvePeriodoNomina(BigDecimal cvePeriodoNomina) {
		this.cvePeriodoNomina = cvePeriodoNomina;
	}

	public String getCvePrei() {
		return this.cvePrei;
	}

	public void setCvePrei(String cvePrei) {
		this.cvePrei = cvePrei;
	}

	public String getCveSucursalCtaPago() {
		return this.cveSucursalCtaPago;
	}

	public void setCveSucursalCtaPago(String cveSucursalCtaPago) {
		this.cveSucursalCtaPago = cveSucursalCtaPago;
	}

	public String getDesTipoCtaPago() {
		return this.desTipoCtaPago;
	}

	public void setDesTipoCtaPago(String desTipoCtaPago) {
		this.desTipoCtaPago = desTipoCtaPago;
	}

	public String getIdConvenio() {
		return this.idConvenio;
	}

	public void setIdConvenio(String idConvenio) {
		this.idConvenio = idConvenio;
	}

	public String getIdPeriodoPago() {
		return this.idPeriodoPago;
	}

	public void setIdPeriodoPago(String idPeriodoPago) {
		this.idPeriodoPago = idPeriodoPago;
	}

	public String getIdTipoCuentaPago() {
		return this.idTipoCuentaPago;
	}

	public void setIdTipoCuentaPago(String idTipoCuentaPago) {
		this.idTipoCuentaPago = idTipoCuentaPago;
	}

	public String getIndCurpValida() {
		return this.indCurpValida;
	}

	public void setIndCurpValida(String indCurpValida) {
		this.indCurpValida = indCurpValida;
	}

	public String getIndDatosPersonales() {
		return this.indDatosPersonales;
	}

	public void setIndDatosPersonales(String indDatosPersonales) {
		this.indDatosPersonales = indDatosPersonales;
	}

	public String getIndDerechoServMed() {
		return this.indDerechoServMed;
	}

	public void setIndDerechoServMed(String indDerechoServMed) {
		this.indDerechoServMed = indDerechoServMed;
	}

	public String getNumClabe() {
		return this.numClabe;
	}

	public void setNumClabe(String numClabe) {
		this.numClabe = numClabe;
	}

	public SpcLugarPago getSpcLugarPago() {
		return this.spcLugarPago;
	}

	public void setSpcLugarPago(SpcLugarPago spcLugarPago) {
		this.spcLugarPago = spcLugarPago;
	}
	
/*	public SpcTipoMovimiento getSpcTipoMovimiento() {
		return this.spcTipoMovimiento;
	}

	public void setSpcTipoMovimiento(SpcTipoMovimiento spcTipoMovimiento) {
		this.spcTipoMovimiento = spcTipoMovimiento;
	}*/
	
	public SptPersRecibePagoPen getSptPersRecibePagoPen() {
		return this.sptPersRecibePagoPen;
	}

	public void setSptPersRecibePagoPen(SptPersRecibePagoPen sptPersRecibePagoPen) {
		this.sptPersRecibePagoPen = sptPersRecibePagoPen;
	}
	
}
