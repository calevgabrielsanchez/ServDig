package mx.gob.imss.ctirss.idse.persistencia;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the PATRONES_IDSE database table.
 * 
 */
@Entity
@Table(name="PATRONES_IDSE")
@NamedQuery(name="PatronesIdse.findAll", query="SELECT p FROM PatronesIdse p")
public class PatronesIdse implements Serializable {
	private static final long serialVersionUID = 1L;
		
	@Id
	@Column(name="REG_PATRON")
	private String regPatron;

	@Column(name="DIGESTION")
	private String digestion;

	@Column(name="EMAIL")
	private String email;

	@Column(name="FECHA_ACCESO")
	private Timestamp fechaAcceso;

	@Column(name="FECHA_AVISO")
	private Timestamp fechaAviso;
	
	@Column(name="NOFOLIO")
	private BigDecimal nofolio;

	@Column(name="NPIE")
	private String npie;

	@Column(name="PERIODO_ANUAL_EBA_MAIL")
	private BigDecimal periodoAnualEbaMail;

	@Column(name="PERIODO_ANUAL_EMA_MAIL")
	private BigDecimal periodoAnualEmaMail;

	@Column(name="PERIODO_MENSUAL_EBA_MAIL")
	private BigDecimal periodoMensualEbaMail;

	@Column(name="PERIODO_MENSUAL_EMA_MAIL")
	private BigDecimal periodoMensualEmaMail;

	@Column(name="RECIBO_FIRMA")
	private String reciboFirma;

	@Column(name="REPRESENTANTE_LEGAL")
	private String representanteLegal;

	@Column(name="SSIGN_SEQ_FIRMA")
	private BigDecimal ssignSeqFirma;

	@Column(name="SSIGN_SEQ_NOTARIA")
	private BigDecimal ssignSeqNotaria;

	@Column(name="STATUS_COBRANZA")
	private BigDecimal statusCobranza;

	public PatronesIdse() {
		this.periodoAnualEbaMail=BigDecimal.ZERO;
		this.periodoAnualEmaMail=BigDecimal.ZERO;
		this.periodoMensualEbaMail=BigDecimal.ZERO;
		this.periodoMensualEmaMail=BigDecimal.ZERO;
	}

	public String getRegPatron() {
		return this.regPatron;
	}

	public void setRegPatron(String regPatron) {
		this.regPatron = regPatron;
	}

	public String getDigestion() {
		return this.digestion;
	}

	public void setDigestion(String digestion) {
		this.digestion = digestion;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getFechaAcceso() {
		return this.fechaAcceso;
	}

	public void setFechaAcceso(Timestamp fechaAcceso) {
		this.fechaAcceso = fechaAcceso;
	}

	public Timestamp getFechaAviso() {
		return this.fechaAviso;
	}

	public void setFechaAviso(Timestamp fechaAviso) {
		this.fechaAviso = fechaAviso;
	}

	public BigDecimal getNofolio() {
		return this.nofolio;
	}

	public void setNofolio(BigDecimal nofolio) {
		this.nofolio = nofolio;
	}

	public String getNpie() {
		return this.npie;
	}

	public void setNpie(String npie) {
		this.npie = npie;
	}

	public BigDecimal getPeriodoAnualEbaMail() {
		return this.periodoAnualEbaMail;
	}

	public void setPeriodoAnualEbaMail(BigDecimal periodoAnualEbaMail) {
		this.periodoAnualEbaMail = periodoAnualEbaMail;
	}

	public BigDecimal getPeriodoAnualEmaMail() {
		return this.periodoAnualEmaMail;
	}

	public void setPeriodoAnualEmaMail(BigDecimal periodoAnualEmaMail) {
		this.periodoAnualEmaMail = periodoAnualEmaMail;
	}

	public BigDecimal getPeriodoMensualEbaMail() {
		return this.periodoMensualEbaMail;
	}

	public void setPeriodoMensualEbaMail(BigDecimal periodoMensualEbaMail) {
		this.periodoMensualEbaMail = periodoMensualEbaMail;
	}

	public BigDecimal getPeriodoMensualEmaMail() {
		return this.periodoMensualEmaMail;
	}

	public void setPeriodoMensualEmaMail(BigDecimal periodoMensualEmaMail) {
		this.periodoMensualEmaMail = periodoMensualEmaMail;
	}

	public String getReciboFirma() {
		return this.reciboFirma;
	}

	public void setReciboFirma(String reciboFirma) {
		this.reciboFirma = reciboFirma;
	}

	public String getRepresentanteLegal() {
		return this.representanteLegal;
	}

	public void setRepresentanteLegal(String representanteLegal) {
		this.representanteLegal = representanteLegal;
	}

	public BigDecimal getSsignSeqFirma() {
		return this.ssignSeqFirma;
	}

	public void setSsignSeqFirma(BigDecimal ssignSeqFirma) {
		this.ssignSeqFirma = ssignSeqFirma;
	}

	public BigDecimal getSsignSeqNotaria() {
		return this.ssignSeqNotaria;
	}

	public void setSsignSeqNotaria(BigDecimal ssignSeqNotaria) {
		this.ssignSeqNotaria = ssignSeqNotaria;
	}

	public BigDecimal getStatusCobranza() {
		return this.statusCobranza;
	}

	public void setStatusCobranza(BigDecimal statusCobranza) {
		this.statusCobranza = statusCobranza;
	}

}