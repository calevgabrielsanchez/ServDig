package mx.gob.imss.ctirss.delta.cobranza.service.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the H_COP_CREDITOS_TOT database table.
 * 
 */
@Entity
@Table(name="H_COP_CREDITOS_TOT")
public class HCopCreditosTot implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private HCopCreditosTotPK id;
	
	@Column(name="ACTU_ACU")
	private BigDecimal actuAcu;

	private BigDecimal actualizacion;

	@Column(name = "CREDITO",insertable = false, updatable=false)
	private String credito;

	@Column(name="FACTOR_ACT")
	private BigDecimal factorAct;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_ALTA")
	private Date fecAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_NOT")
	private Date fecNot;

	@Column(name="INC_ACT")
	private BigDecimal incAct;

	@Column(name="INTE_ACUM")
	private BigDecimal inteAcum;

	private BigDecimal intereses;

	@Column(name = "MODALIDAD",insertable = false, updatable=false)
	private String modalidad;

	@Column(name="MODALIDAD_COR",insertable = false, updatable=false)
	private String modalidadCor;

	@Column(name = "PERIODO",insertable = false, updatable=false)
	private BigDecimal periodo;

	@Column(name="REG_PATRONAL",insertable = false, updatable=false)
	private String regPatronal;

	@Column(name="REG_PATRONAL_COR",insertable = false, updatable=false)
	private String regPatronalCor;

	@Column(name="SAL_EYM_ADY")
	private BigDecimal salEymAdy;

	@Column(name="SAL_EYM_DIN")
	private BigDecimal salEymDin;

	@Column(name="SAL_EYM_FIJA")
	private BigDecimal salEymFija;

	@Column(name="SAL_GUAR")
	private BigDecimal salGuar;

	@Column(name="SAL_IV")
	private BigDecimal salIv;

	@Column(name="SAL_RT")
	private BigDecimal salRt;

	@Column(name="SAL_TOT")
	private BigDecimal salTot;

	@Column(name="SALEYM_PEN")
	private BigDecimal saleymPen;

	@Column(name="T_DOCUMENTO")
	private BigDecimal tDocumento;

	@Column(name="T_NOTIFICACION")
	private BigDecimal tNotificacion;

	@Column(name="TOTAL_ADEUDO")
	private BigDecimal totalAdeudo;
	
    public HCopCreditosTotPK getId() {
		return id;
	}

	public void setId(HCopCreditosTotPK id) {
		this.id = id;
	}

	public BigDecimal gettDocumento() {
		return tDocumento;
	}

	public void settDocumento(BigDecimal tDocumento) {
		this.tDocumento = tDocumento;
	}

	public BigDecimal gettNotificacion() {
		return tNotificacion;
	}

	public void settNotificacion(BigDecimal tNotificacion) {
		this.tNotificacion = tNotificacion;
	}

	public HCopCreditosTot() {
    }

	public BigDecimal getActuAcu() {
		return this.actuAcu;
	}

	public void setActuAcu(BigDecimal actuAcu) {
		this.actuAcu = actuAcu;
	}

	public BigDecimal getActualizacion() {
		return this.actualizacion;
	}

	public void setActualizacion(BigDecimal actualizacion) {
		this.actualizacion = actualizacion;
	}

	public String getCredito() {
		return this.credito;
	}

	public void setCredito(String credito) {
		this.credito = credito;
	}

	public BigDecimal getFactorAct() {
		return this.factorAct;
	}

	public void setFactorAct(BigDecimal factorAct) {
		this.factorAct = factorAct;
	}

	public Date getFecAlta() {
		return this.fecAlta;
	}

	public void setFecAlta(Date fecAlta) {
		this.fecAlta = fecAlta;
	}

	public Date getFecNot() {
		return this.fecNot;
	}

	public void setFecNot(Date fecNot) {
		this.fecNot = fecNot;
	}

	public BigDecimal getIncAct() {
		return this.incAct;
	}

	public void setIncAct(BigDecimal incAct) {
		this.incAct = incAct;
	}

	public BigDecimal getInteAcum() {
		return this.inteAcum;
	}

	public void setInteAcum(BigDecimal inteAcum) {
		this.inteAcum = inteAcum;
	}

	public BigDecimal getIntereses() {
		return this.intereses;
	}

	public void setIntereses(BigDecimal intereses) {
		this.intereses = intereses;
	}

	public String getModalidad() {
		return this.modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public String getModalidadCor() {
		return this.modalidadCor;
	}

	public void setModalidadCor(String modalidadCor) {
		this.modalidadCor = modalidadCor;
	}

	public BigDecimal getPeriodo() {
		return this.periodo;
	}

	public void setPeriodo(BigDecimal periodo) {
		this.periodo = periodo;
	}

	public String getRegPatronal() {
		return this.regPatronal;
	}

	public void setRegPatronal(String regPatronal) {
		this.regPatronal = regPatronal;
	}

	public String getRegPatronalCor() {
		return this.regPatronalCor;
	}

	public void setRegPatronalCor(String regPatronalCor) {
		this.regPatronalCor = regPatronalCor;
	}

	public BigDecimal getSalEymAdy() {
		return this.salEymAdy;
	}

	public void setSalEymAdy(BigDecimal salEymAdy) {
		this.salEymAdy = salEymAdy;
	}

	public BigDecimal getSalEymDin() {
		return this.salEymDin;
	}

	public void setSalEymDin(BigDecimal salEymDin) {
		this.salEymDin = salEymDin;
	}

	public BigDecimal getSalEymFija() {
		return this.salEymFija;
	}

	public void setSalEymFija(BigDecimal salEymFija) {
		this.salEymFija = salEymFija;
	}

	public BigDecimal getSalGuar() {
		return this.salGuar;
	}

	public void setSalGuar(BigDecimal salGuar) {
		this.salGuar = salGuar;
	}

	public BigDecimal getSalIv() {
		return this.salIv;
	}

	public void setSalIv(BigDecimal salIv) {
		this.salIv = salIv;
	}

	public BigDecimal getSalRt() {
		return this.salRt;
	}

	public void setSalRt(BigDecimal salRt) {
		this.salRt = salRt;
	}

	public BigDecimal getSalTot() {
		return this.salTot;
	}

	public void setSalTot(BigDecimal salTot) {
		this.salTot = salTot;
	}

	public BigDecimal getSaleymPen() {
		return this.saleymPen;
	}

	public void setSaleymPen(BigDecimal saleymPen) {
		this.saleymPen = saleymPen;
	}

	public BigDecimal getTDocumento() {
		return this.tDocumento;
	}

	public void setTDocumento(BigDecimal tDocumento) {
		this.tDocumento = tDocumento;
	}

	public BigDecimal getTNotificacion() {
		return this.tNotificacion;
	}

	public void setTNotificacion(BigDecimal tNotificacion) {
		this.tNotificacion = tNotificacion;
	}

	public BigDecimal getTotalAdeudo() {
		return this.totalAdeudo;
	}

	public void setTotalAdeudo(BigDecimal totalAdeudo) {
		this.totalAdeudo = totalAdeudo;
	}

}