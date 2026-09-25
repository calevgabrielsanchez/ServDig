package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CGT_COREDISINADI database table.
 * 
 */
@Entity
@Table(name="CGT_COREDISINADI")
public class CgtCoredisinadi implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CgtCoredisinadiPK id;

	@Column(name="CVE_TPOAVISO", precision=22)
	private BigDecimal cveTpoaviso;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_APLICACEDREV")
	private Date fhAplicacedrev;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_ASIGNAREVCASO")
	private Date fhAsignarevcaso;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_CONCLUSION_RI")
	private Date fhConclusionRi;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_CONCLUYECEDREV")
	private Date fhConcluyecedrev;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_ENVIOAUDITORIA")
	private Date fhEnvioauditoria;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_FECHA_CARGA")
	private Date fhFechaCarga;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_PAGO")
	private Date fhPago;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_PRESENTADICTAMEN")
	private Date fhPresentadictamen;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_RI0_NOTIFICA")
	private Date fhRi0Notifica;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_RI0_RESP")
	private Date fhRi0Resp;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_RI1_NOT_CTRL")
	private Date fhRi1NotCtrl;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_RI1_NOTIFICA")
	private Date fhRi1Notifica;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_RI1_PRORROGA")
	private Date fhRi1Prorroga;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_RI1_RESP")
	private Date fhRi1Resp;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_RI2_NOT_CTRL")
	private Date fhRi2NotCtrl;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_RI2_NOTIFICA")
	private Date fhRi2Notifica;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_RI2_PRORROGA")
	private Date fhRi2Prorroga;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_RI2_RESP")
	private Date fhRi2Resp;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_RI3_NOT_CTRL")
	private Date fhRi3NotCtrl;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_RI3_NOTIFICA")
	private Date fhRi3Notifica;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_RI3_RESP")
	private Date fhRi3Resp;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_RI4_NOT_CTRL")
	private Date fhRi4NotCtrl;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_RI4_NOTIFICA")
	private Date fhRi4Notifica;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_RI4_RESP")
	private Date fhRi4Resp;

	@Column(name="IM_ACTUALIZACION", precision=12, scale=2)
	private BigDecimal imActualizacion;

	@Column(name="IM_MULTASPAG", precision=12, scale=2)
	private BigDecimal imMultaspag;

	@Column(name="IM_PENDIENTEPAGAR", precision=12, scale=2)
	private BigDecimal imPendientepagar;

	@Column(name="IM_RECARGOS", precision=12, scale=2)
	private BigDecimal imRecargos;

	@Column(name="IM_SUERTEPRIN", precision=12, scale=2)
	private BigDecimal imSuerteprin;

	@Column(name="IM_TOTAL", precision=12, scale=2)
	private BigDecimal imTotal;

	@Column(name="IM_TOTALAPAGAR", precision=12, scale=2)
	private BigDecimal imTotalapagar;

	@Column(name="NU_AVIBAJAPRES_RI", precision=22)
	private BigDecimal nuAvibajapresRi;

	@Column(name="NU_AVIDEROTRASUB", length=100)
	private String nuAviderotrasub;

	@Column(name="NU_AVIDESCOR1ER_RI", precision=22)
	private BigDecimal nuAvidescor1erRi;

	@Column(name="NU_AVIINSBAJAS_RI", precision=22)
	private BigDecimal nuAviinsbajasRi;

	@Column(name="NU_AVIMODSALASC_RI", precision=22)
	private BigDecimal nuAvimodsalascRi;

	@Column(name="NU_AVIMODSALDESC_RI", precision=22)
	private BigDecimal nuAvimodsaldescRi;

	@Column(name="NU_AVIRECFECPOST_RI", precision=22)
	private BigDecimal nuAvirecfecpostRi;

	@Column(name="NU_AVIRECOTRASUB", length=100)
	private String nuAvirecotrasub;

	@Column(name="NU_AVITRABNOINSC_RI", precision=22)
	private BigDecimal nuAvitrabnoinscRi;

	@Column(name="NU_EJERPERIODO", length=50)
	private String nuEjerperiodo;

	@Column(name="NU_NUMPARCIA", precision=22)
	private BigDecimal nuNumparcia;

	@Column(name="NU_NUMPARCIADE", precision=22)
	private BigDecimal nuNumparciade;

	@Column(name="NU_PORRAZONA", precision=8, scale=4)
	private BigDecimal nuPorrazona;

	@Column(name="NU_RCV", precision=12, scale=2)
	private BigDecimal nuRcv;

	@Column(name="NU_REG_CP", precision=9)
	private BigDecimal nuRegCp;

	@Column(name="NU_REGPATRONALES", precision=22)
	private BigDecimal nuRegpatronales;

	@Column(name="NU_RI0_ENTREGODOC", precision=22)
	private BigDecimal nuRi0Entregodoc;

	@Column(name="NU_RI1_ACLARO", precision=22)
	private BigDecimal nuRi1Aclaro;

	@Column(name="NU_RI1_ENTREGODOC", precision=22)
	private BigDecimal nuRi1Entregodoc;

	@Column(name="NU_RI2_ACLARO", precision=22)
	private BigDecimal nuRi2Aclaro;

	@Column(name="NU_RI2_ENTREGODOC", precision=22)
	private BigDecimal nuRi2Entregodoc;

	@Column(name="NU_RI3_ACLARO", precision=22)
	private BigDecimal nuRi3Aclaro;

	@Column(name="NU_RI4_ACLARO", precision=22)
	private BigDecimal nuRi4Aclaro;

	@Column(name="NU_TRABREGULA", precision=22)
	private BigDecimal nuTrabregula;

	@Column(name="TX_FOLIOCMAA", length=15)
	private String txFoliocmaa;

	@Column(name="TX_FOLIOSUA", length=255)
	private String txFoliosua;

	@Column(name="TX_NOMRAZONSOCIAL", length=200)
	private String txNomrazonsocial;

	@Column(name="TX_OBS_RI0", length=150)
	private String txObsRi0;

	@Column(name="TX_OBS_RI1", length=150)
	private String txObsRi1;

	@Column(name="TX_OBS_RI2", length=150)
	private String txObsRi2;

	@Column(name="TX_OBS_RI3", length=150)
	private String txObsRi3;

	@Column(name="TX_OBS_RI4", length=150)
	private String txObsRi4;

	@Column(name="TX_OBSERVACIONES", length=300)
	private String txObservaciones;

	@Column(name="TX_REGPATRONAL", length=11)
	private String txRegpatronal;

	@Column(name="TX_RFCAUDITOR", length=13)
	private String txRfcauditor;

	@Column(name="TX_RFCAUDITOR2", length=13)
	private String txRfcauditor2;

	@Column(name="TX_RFCPATRON", length=13)
	private String txRfcpatron;

	@Column(name="TX_RFCSUPERVISOR", length=13)
	private String txRfcsupervisor;

	@Column(name="TX_RFCSUPERVISOR2", length=13)
	private String txRfcsupervisor2;

	//bi-directional many-to-one association to CgcRazona
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_RAZONA")
	private CgcRazona cgcRazona;

	//bi-directional many-to-one association to CgcFormapago
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_FORMAPAGO")
	private CgcFormapago cgcFormapago;

	//bi-directional many-to-one association to FdtPatron
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL"),
		@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON")
		})
	private FdtPatron fdtPatron;

	//bi-directional one-to-one association to CgtGestionsinadi
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_DELEG_ORIG", referencedColumnName="CVE_DELEG_ORIG", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="SDELEG_ORIG", referencedColumnName="SDELEG_ORIG", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="TX_NUMAVISO", referencedColumnName="TX_NUMAVISO", nullable=false, insertable=false, updatable=false)
		})
	private CgtGestionsinadi cgtGestionsinadi;

    public CgtCoredisinadi() {
    }

	public CgtCoredisinadiPK getId() {
		return this.id;
	}

	public void setId(CgtCoredisinadiPK id) {
		this.id = id;
	}
	
	public BigDecimal getCveTpoaviso() {
		return this.cveTpoaviso;
	}

	public void setCveTpoaviso(BigDecimal cveTpoaviso) {
		this.cveTpoaviso = cveTpoaviso;
	}

	public Date getFhAplicacedrev() {
		return this.fhAplicacedrev;
	}

	public void setFhAplicacedrev(Date fhAplicacedrev) {
		this.fhAplicacedrev = fhAplicacedrev;
	}

	public Date getFhAsignarevcaso() {
		return this.fhAsignarevcaso;
	}

	public void setFhAsignarevcaso(Date fhAsignarevcaso) {
		this.fhAsignarevcaso = fhAsignarevcaso;
	}

	public Date getFhConclusionRi() {
		return this.fhConclusionRi;
	}

	public void setFhConclusionRi(Date fhConclusionRi) {
		this.fhConclusionRi = fhConclusionRi;
	}

	public Date getFhConcluyecedrev() {
		return this.fhConcluyecedrev;
	}

	public void setFhConcluyecedrev(Date fhConcluyecedrev) {
		this.fhConcluyecedrev = fhConcluyecedrev;
	}

	public Date getFhEnvioauditoria() {
		return this.fhEnvioauditoria;
	}

	public void setFhEnvioauditoria(Date fhEnvioauditoria) {
		this.fhEnvioauditoria = fhEnvioauditoria;
	}

	public Date getFhFechaCarga() {
		return this.fhFechaCarga;
	}

	public void setFhFechaCarga(Date fhFechaCarga) {
		this.fhFechaCarga = fhFechaCarga;
	}

	public Date getFhPago() {
		return this.fhPago;
	}

	public void setFhPago(Date fhPago) {
		this.fhPago = fhPago;
	}

	public Date getFhPresentadictamen() {
		return this.fhPresentadictamen;
	}

	public void setFhPresentadictamen(Date fhPresentadictamen) {
		this.fhPresentadictamen = fhPresentadictamen;
	}

	public Date getFhRi0Notifica() {
		return this.fhRi0Notifica;
	}

	public void setFhRi0Notifica(Date fhRi0Notifica) {
		this.fhRi0Notifica = fhRi0Notifica;
	}

	public Date getFhRi0Resp() {
		return this.fhRi0Resp;
	}

	public void setFhRi0Resp(Date fhRi0Resp) {
		this.fhRi0Resp = fhRi0Resp;
	}

	public Date getFhRi1NotCtrl() {
		return this.fhRi1NotCtrl;
	}

	public void setFhRi1NotCtrl(Date fhRi1NotCtrl) {
		this.fhRi1NotCtrl = fhRi1NotCtrl;
	}

	public Date getFhRi1Notifica() {
		return this.fhRi1Notifica;
	}

	public void setFhRi1Notifica(Date fhRi1Notifica) {
		this.fhRi1Notifica = fhRi1Notifica;
	}

	public Date getFhRi1Prorroga() {
		return this.fhRi1Prorroga;
	}

	public void setFhRi1Prorroga(Date fhRi1Prorroga) {
		this.fhRi1Prorroga = fhRi1Prorroga;
	}

	public Date getFhRi1Resp() {
		return this.fhRi1Resp;
	}

	public void setFhRi1Resp(Date fhRi1Resp) {
		this.fhRi1Resp = fhRi1Resp;
	}

	public Date getFhRi2NotCtrl() {
		return this.fhRi2NotCtrl;
	}

	public void setFhRi2NotCtrl(Date fhRi2NotCtrl) {
		this.fhRi2NotCtrl = fhRi2NotCtrl;
	}

	public Date getFhRi2Notifica() {
		return this.fhRi2Notifica;
	}

	public void setFhRi2Notifica(Date fhRi2Notifica) {
		this.fhRi2Notifica = fhRi2Notifica;
	}

	public Date getFhRi2Prorroga() {
		return this.fhRi2Prorroga;
	}

	public void setFhRi2Prorroga(Date fhRi2Prorroga) {
		this.fhRi2Prorroga = fhRi2Prorroga;
	}

	public Date getFhRi2Resp() {
		return this.fhRi2Resp;
	}

	public void setFhRi2Resp(Date fhRi2Resp) {
		this.fhRi2Resp = fhRi2Resp;
	}

	public Date getFhRi3NotCtrl() {
		return this.fhRi3NotCtrl;
	}

	public void setFhRi3NotCtrl(Date fhRi3NotCtrl) {
		this.fhRi3NotCtrl = fhRi3NotCtrl;
	}

	public Date getFhRi3Notifica() {
		return this.fhRi3Notifica;
	}

	public void setFhRi3Notifica(Date fhRi3Notifica) {
		this.fhRi3Notifica = fhRi3Notifica;
	}

	public Date getFhRi3Resp() {
		return this.fhRi3Resp;
	}

	public void setFhRi3Resp(Date fhRi3Resp) {
		this.fhRi3Resp = fhRi3Resp;
	}

	public Date getFhRi4NotCtrl() {
		return this.fhRi4NotCtrl;
	}

	public void setFhRi4NotCtrl(Date fhRi4NotCtrl) {
		this.fhRi4NotCtrl = fhRi4NotCtrl;
	}

	public Date getFhRi4Notifica() {
		return this.fhRi4Notifica;
	}

	public void setFhRi4Notifica(Date fhRi4Notifica) {
		this.fhRi4Notifica = fhRi4Notifica;
	}

	public Date getFhRi4Resp() {
		return this.fhRi4Resp;
	}

	public void setFhRi4Resp(Date fhRi4Resp) {
		this.fhRi4Resp = fhRi4Resp;
	}

	public BigDecimal getImActualizacion() {
		return this.imActualizacion;
	}

	public void setImActualizacion(BigDecimal imActualizacion) {
		this.imActualizacion = imActualizacion;
	}

	public BigDecimal getImMultaspag() {
		return this.imMultaspag;
	}

	public void setImMultaspag(BigDecimal imMultaspag) {
		this.imMultaspag = imMultaspag;
	}

	public BigDecimal getImPendientepagar() {
		return this.imPendientepagar;
	}

	public void setImPendientepagar(BigDecimal imPendientepagar) {
		this.imPendientepagar = imPendientepagar;
	}

	public BigDecimal getImRecargos() {
		return this.imRecargos;
	}

	public void setImRecargos(BigDecimal imRecargos) {
		this.imRecargos = imRecargos;
	}

	public BigDecimal getImSuerteprin() {
		return this.imSuerteprin;
	}

	public void setImSuerteprin(BigDecimal imSuerteprin) {
		this.imSuerteprin = imSuerteprin;
	}

	public BigDecimal getImTotal() {
		return this.imTotal;
	}

	public void setImTotal(BigDecimal imTotal) {
		this.imTotal = imTotal;
	}

	public BigDecimal getImTotalapagar() {
		return this.imTotalapagar;
	}

	public void setImTotalapagar(BigDecimal imTotalapagar) {
		this.imTotalapagar = imTotalapagar;
	}

	public BigDecimal getNuAvibajapresRi() {
		return this.nuAvibajapresRi;
	}

	public void setNuAvibajapresRi(BigDecimal nuAvibajapresRi) {
		this.nuAvibajapresRi = nuAvibajapresRi;
	}

	public String getNuAviderotrasub() {
		return this.nuAviderotrasub;
	}

	public void setNuAviderotrasub(String nuAviderotrasub) {
		this.nuAviderotrasub = nuAviderotrasub;
	}

	public BigDecimal getNuAvidescor1erRi() {
		return this.nuAvidescor1erRi;
	}

	public void setNuAvidescor1erRi(BigDecimal nuAvidescor1erRi) {
		this.nuAvidescor1erRi = nuAvidescor1erRi;
	}

	public BigDecimal getNuAviinsbajasRi() {
		return this.nuAviinsbajasRi;
	}

	public void setNuAviinsbajasRi(BigDecimal nuAviinsbajasRi) {
		this.nuAviinsbajasRi = nuAviinsbajasRi;
	}

	public BigDecimal getNuAvimodsalascRi() {
		return this.nuAvimodsalascRi;
	}

	public void setNuAvimodsalascRi(BigDecimal nuAvimodsalascRi) {
		this.nuAvimodsalascRi = nuAvimodsalascRi;
	}

	public BigDecimal getNuAvimodsaldescRi() {
		return this.nuAvimodsaldescRi;
	}

	public void setNuAvimodsaldescRi(BigDecimal nuAvimodsaldescRi) {
		this.nuAvimodsaldescRi = nuAvimodsaldescRi;
	}

	public BigDecimal getNuAvirecfecpostRi() {
		return this.nuAvirecfecpostRi;
	}

	public void setNuAvirecfecpostRi(BigDecimal nuAvirecfecpostRi) {
		this.nuAvirecfecpostRi = nuAvirecfecpostRi;
	}

	public String getNuAvirecotrasub() {
		return this.nuAvirecotrasub;
	}

	public void setNuAvirecotrasub(String nuAvirecotrasub) {
		this.nuAvirecotrasub = nuAvirecotrasub;
	}

	public BigDecimal getNuAvitrabnoinscRi() {
		return this.nuAvitrabnoinscRi;
	}

	public void setNuAvitrabnoinscRi(BigDecimal nuAvitrabnoinscRi) {
		this.nuAvitrabnoinscRi = nuAvitrabnoinscRi;
	}

	public String getNuEjerperiodo() {
		return this.nuEjerperiodo;
	}

	public void setNuEjerperiodo(String nuEjerperiodo) {
		this.nuEjerperiodo = nuEjerperiodo;
	}

	public BigDecimal getNuNumparcia() {
		return this.nuNumparcia;
	}

	public void setNuNumparcia(BigDecimal nuNumparcia) {
		this.nuNumparcia = nuNumparcia;
	}

	public BigDecimal getNuNumparciade() {
		return this.nuNumparciade;
	}

	public void setNuNumparciade(BigDecimal nuNumparciade) {
		this.nuNumparciade = nuNumparciade;
	}

	public BigDecimal getNuPorrazona() {
		return this.nuPorrazona;
	}

	public void setNuPorrazona(BigDecimal nuPorrazona) {
		this.nuPorrazona = nuPorrazona;
	}

	public BigDecimal getNuRcv() {
		return this.nuRcv;
	}

	public void setNuRcv(BigDecimal nuRcv) {
		this.nuRcv = nuRcv;
	}

	public BigDecimal getNuRegCp() {
		return this.nuRegCp;
	}

	public void setNuRegCp(BigDecimal nuRegCp) {
		this.nuRegCp = nuRegCp;
	}

	public BigDecimal getNuRegpatronales() {
		return this.nuRegpatronales;
	}

	public void setNuRegpatronales(BigDecimal nuRegpatronales) {
		this.nuRegpatronales = nuRegpatronales;
	}

	public BigDecimal getNuRi0Entregodoc() {
		return this.nuRi0Entregodoc;
	}

	public void setNuRi0Entregodoc(BigDecimal nuRi0Entregodoc) {
		this.nuRi0Entregodoc = nuRi0Entregodoc;
	}

	public BigDecimal getNuRi1Aclaro() {
		return this.nuRi1Aclaro;
	}

	public void setNuRi1Aclaro(BigDecimal nuRi1Aclaro) {
		this.nuRi1Aclaro = nuRi1Aclaro;
	}

	public BigDecimal getNuRi1Entregodoc() {
		return this.nuRi1Entregodoc;
	}

	public void setNuRi1Entregodoc(BigDecimal nuRi1Entregodoc) {
		this.nuRi1Entregodoc = nuRi1Entregodoc;
	}

	public BigDecimal getNuRi2Aclaro() {
		return this.nuRi2Aclaro;
	}

	public void setNuRi2Aclaro(BigDecimal nuRi2Aclaro) {
		this.nuRi2Aclaro = nuRi2Aclaro;
	}

	public BigDecimal getNuRi2Entregodoc() {
		return this.nuRi2Entregodoc;
	}

	public void setNuRi2Entregodoc(BigDecimal nuRi2Entregodoc) {
		this.nuRi2Entregodoc = nuRi2Entregodoc;
	}

	public BigDecimal getNuRi3Aclaro() {
		return this.nuRi3Aclaro;
	}

	public void setNuRi3Aclaro(BigDecimal nuRi3Aclaro) {
		this.nuRi3Aclaro = nuRi3Aclaro;
	}

	public BigDecimal getNuRi4Aclaro() {
		return this.nuRi4Aclaro;
	}

	public void setNuRi4Aclaro(BigDecimal nuRi4Aclaro) {
		this.nuRi4Aclaro = nuRi4Aclaro;
	}

	public BigDecimal getNuTrabregula() {
		return this.nuTrabregula;
	}

	public void setNuTrabregula(BigDecimal nuTrabregula) {
		this.nuTrabregula = nuTrabregula;
	}

	public String getTxFoliocmaa() {
		return this.txFoliocmaa;
	}

	public void setTxFoliocmaa(String txFoliocmaa) {
		this.txFoliocmaa = txFoliocmaa;
	}

	public String getTxFoliosua() {
		return this.txFoliosua;
	}

	public void setTxFoliosua(String txFoliosua) {
		this.txFoliosua = txFoliosua;
	}

	public String getTxNomrazonsocial() {
		return this.txNomrazonsocial;
	}

	public void setTxNomrazonsocial(String txNomrazonsocial) {
		this.txNomrazonsocial = txNomrazonsocial;
	}

	public String getTxObsRi0() {
		return this.txObsRi0;
	}

	public void setTxObsRi0(String txObsRi0) {
		this.txObsRi0 = txObsRi0;
	}

	public String getTxObsRi1() {
		return this.txObsRi1;
	}

	public void setTxObsRi1(String txObsRi1) {
		this.txObsRi1 = txObsRi1;
	}

	public String getTxObsRi2() {
		return this.txObsRi2;
	}

	public void setTxObsRi2(String txObsRi2) {
		this.txObsRi2 = txObsRi2;
	}

	public String getTxObsRi3() {
		return this.txObsRi3;
	}

	public void setTxObsRi3(String txObsRi3) {
		this.txObsRi3 = txObsRi3;
	}

	public String getTxObsRi4() {
		return this.txObsRi4;
	}

	public void setTxObsRi4(String txObsRi4) {
		this.txObsRi4 = txObsRi4;
	}

	public String getTxObservaciones() {
		return this.txObservaciones;
	}

	public void setTxObservaciones(String txObservaciones) {
		this.txObservaciones = txObservaciones;
	}

	public String getTxRegpatronal() {
		return this.txRegpatronal;
	}

	public void setTxRegpatronal(String txRegpatronal) {
		this.txRegpatronal = txRegpatronal;
	}

	public String getTxRfcauditor() {
		return this.txRfcauditor;
	}

	public void setTxRfcauditor(String txRfcauditor) {
		this.txRfcauditor = txRfcauditor;
	}

	public String getTxRfcauditor2() {
		return this.txRfcauditor2;
	}

	public void setTxRfcauditor2(String txRfcauditor2) {
		this.txRfcauditor2 = txRfcauditor2;
	}

	public String getTxRfcpatron() {
		return this.txRfcpatron;
	}

	public void setTxRfcpatron(String txRfcpatron) {
		this.txRfcpatron = txRfcpatron;
	}

	public String getTxRfcsupervisor() {
		return this.txRfcsupervisor;
	}

	public void setTxRfcsupervisor(String txRfcsupervisor) {
		this.txRfcsupervisor = txRfcsupervisor;
	}

	public String getTxRfcsupervisor2() {
		return this.txRfcsupervisor2;
	}

	public void setTxRfcsupervisor2(String txRfcsupervisor2) {
		this.txRfcsupervisor2 = txRfcsupervisor2;
	}

	public CgcRazona getCgcRazona() {
		return this.cgcRazona;
	}

	public void setCgcRazona(CgcRazona cgcRazona) {
		this.cgcRazona = cgcRazona;
	}
	
	public CgcFormapago getCgcFormapago() {
		return this.cgcFormapago;
	}

	public void setCgcFormapago(CgcFormapago cgcFormapago) {
		this.cgcFormapago = cgcFormapago;
	}
	
	public FdtPatron getFdtPatron() {
		return this.fdtPatron;
	}

	public void setFdtPatron(FdtPatron fdtPatron) {
		this.fdtPatron = fdtPatron;
	}
	
	public CgtGestionsinadi getCgtGestionsinadi() {
		return this.cgtGestionsinadi;
	}

	public void setCgtGestionsinadi(CgtGestionsinadi cgtGestionsinadi) {
		this.cgtGestionsinadi = cgtGestionsinadi;
	}
	
}