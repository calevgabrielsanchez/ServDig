package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the V_RESOLUCION_REPORTE database table.
 * 
 */
@Entity
@Table(name="V_RESOLUCION_REPORTE")
public class VResolucionReporte implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_PENSION_REPORTE")
	private BigDecimal cveIdPensionReporte;

	@Column(name="DES_REPORTE")
	private String desReporte;

	@Column(name="ID_FORMA_PAGO_PENSION")
	private String idFormaPagoPension;

	@Column(name="ID_RAMA")
	private String idRama;

	@Column(name="ID_REGIMEN")
	private String idRegimen;

	@Column(name="IND_CARACTER")
	private String indCaracter;

	@Column(name="IND_PMG")
	private BigDecimal indPmg;

	@Column(name="REF_SIGLA")
	private String refSigla;

    public VResolucionReporte() {
    }

	public BigDecimal getCveIdPensionReporte() {
		return this.cveIdPensionReporte;
	}

	public void setCveIdPensionReporte(BigDecimal cveIdPensionReporte) {
		this.cveIdPensionReporte = cveIdPensionReporte;
	}

	public String getDesReporte() {
		return this.desReporte;
	}

	public void setDesReporte(String desReporte) {
		this.desReporte = desReporte;
	}

	public String getIdFormaPagoPension() {
		return this.idFormaPagoPension;
	}

	public void setIdFormaPagoPension(String idFormaPagoPension) {
		this.idFormaPagoPension = idFormaPagoPension;
	}

	public String getIdRama() {
		return this.idRama;
	}

	public void setIdRama(String idRama) {
		this.idRama = idRama;
	}

	public String getIdRegimen() {
		return this.idRegimen;
	}

	public void setIdRegimen(String idRegimen) {
		this.idRegimen = idRegimen;
	}

	public String getIndCaracter() {
		return this.indCaracter;
	}

	public void setIndCaracter(String indCaracter) {
		this.indCaracter = indCaracter;
	}

	public BigDecimal getIndPmg() {
		return this.indPmg;
	}

	public void setIndPmg(BigDecimal indPmg) {
		this.indPmg = indPmg;
	}

	public String getRefSigla() {
		return this.refSigla;
	}

	public void setRefSigla(String refSigla) {
		this.refSigla = refSigla;
	}

}