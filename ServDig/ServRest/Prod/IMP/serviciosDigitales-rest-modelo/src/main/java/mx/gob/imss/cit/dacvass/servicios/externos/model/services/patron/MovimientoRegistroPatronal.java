package mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MovimientoRegistroPatronal implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4237810173750129122L;

	private String registroPatronal;
	private BigDecimal cveIdMovtoPatSujetoObligado;
	private Date fecMovimiento;
	private BigDecimal cveIdTipoMovimiento;
	private BigDecimal cveIdCausa;
	

	public String getRegistroPatronal() {
		return registroPatronal;
	}
	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}
	public BigDecimal getCveIdMovtoPatSujetoObligado() {
		return cveIdMovtoPatSujetoObligado;
	}
	public void setCveIdMovtoPatSujetoObligado(BigDecimal cveIdMovtoPatSujetoObligado) {
		this.cveIdMovtoPatSujetoObligado = cveIdMovtoPatSujetoObligado;
	}
	public Date getFecMovimiento() {
		return fecMovimiento;
	}
	public void setFecMovimiento(Date fecMovimiento) {
		this.fecMovimiento = fecMovimiento;
	}
	public BigDecimal getCveIdTipoMovimiento() {
		return cveIdTipoMovimiento;
	}
	public void setCveIdTipoMovimiento(BigDecimal cveIdTipoMovimiento) {
		this.cveIdTipoMovimiento = cveIdTipoMovimiento;
	}
	public BigDecimal getCveIdCausa() {
		return cveIdCausa;
	}
	public void setCveIdCausa(BigDecimal cveIdCausa) {
		this.cveIdCausa = cveIdCausa;
	}
	

}
