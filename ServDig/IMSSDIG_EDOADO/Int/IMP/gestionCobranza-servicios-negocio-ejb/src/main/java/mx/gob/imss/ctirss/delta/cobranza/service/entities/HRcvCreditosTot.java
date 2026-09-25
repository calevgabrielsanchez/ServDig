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
 * The persistent class for the H_RCV_CREDITOS_TOT database table.
 * 
 */
@Entity
@Table(name="H_RCV_CREDITOS_TOT")
public class HRcvCreditosTot implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private HRcvCreditosTotPK id;
	
	private BigDecimal actua;

	@Column(name="CR_SAL_RET")
	private BigDecimal crSalRet;

	@Column(name = "CREDITO",insertable = false, updatable=false)
	private String credito;

	private BigDecimal cyv;

	@Column(name="FAC_ACT")
	private BigDecimal facAct;

	@Column(name="FAC_REC")
	private BigDecimal facRec;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_ALTA")
	private Date fecAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_NOT")
	private Date fecNot;

	@Column(name="INC_ACT")
	private BigDecimal incAct;

	@Column(name = "MODALIDAD",insertable = false, updatable=false)
	private String modalidad;

	@Column(name="MODALIDAD_COR",insertable = false, updatable=false)
	private String modalidadCor;

	@Column(name = "PERIODO",insertable = false, updatable=false)
	private BigDecimal periodo;

	private BigDecimal recar;

	@Column(name="REG_PATRONAL",insertable = false, updatable=false)
	private String regPatronal;

	@Column(name="REG_PATRONAL_COR",insertable = false, updatable=false)
	private String regPatronalCor;

	@Column(name="SALDO_TOTAL")
	private BigDecimal saldoTotal;

	@Column(name="T_DOCUMENTO")
	private BigDecimal tDocumento;

	@Column(name="T_NOTIFICACION")
	private BigDecimal tNotificacion;

	@Column(name="TOTAL_ADEUDO")
	private BigDecimal totalAdeudo;

    public HRcvCreditosTot() {
    }

    
	public HRcvCreditosTotPK getId() {
		return id;
	}


	public void setId(HRcvCreditosTotPK id) {
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


	public BigDecimal getActua() {
		return this.actua;
	}

	public void setActua(BigDecimal actua) {
		this.actua = actua;
	}

	public BigDecimal getCrSalRet() {
		return this.crSalRet;
	}

	public void setCrSalRet(BigDecimal crSalRet) {
		this.crSalRet = crSalRet;
	}

	public String getCredito() {
		return this.credito;
	}

	public void setCredito(String credito) {
		this.credito = credito;
	}

	public BigDecimal getCyv() {
		return this.cyv;
	}

	public void setCyv(BigDecimal cyv) {
		this.cyv = cyv;
	}

	public BigDecimal getFacAct() {
		return this.facAct;
	}

	public void setFacAct(BigDecimal facAct) {
		this.facAct = facAct;
	}

	public BigDecimal getFacRec() {
		return this.facRec;
	}

	public void setFacRec(BigDecimal facRec) {
		this.facRec = facRec;
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

	public BigDecimal getRecar() {
		return this.recar;
	}

	public void setRecar(BigDecimal recar) {
		this.recar = recar;
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

	public BigDecimal getSaldoTotal() {
		return this.saldoTotal;
	}

	public void setSaldoTotal(BigDecimal saldoTotal) {
		this.saldoTotal = saldoTotal;
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