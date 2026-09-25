package mx.gob.imss.cit.dacvass.servicios.externos.persistence.vigencia;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the MGT_INFINCASEGVIG database table.
 * 
 */
@Entity
@Table(name="MGT_INFINCASEGVIG")
public class MgtInfincasegvig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CIZ")
	private BigDecimal ciz;

	@Column(name="CVE_DELEG")
	private BigDecimal cveDeleg;

	@Column(name="CVE_SUBDELEG")
	private BigDecimal cveSubdeleg;

	@Column(name="CVE_UMF")
	private BigDecimal cveUmf;

	@Column(name="NOM_MATERNO")
	private String nomMaterno;

	@Column(name="NOM_NOMBRE")
	private String nomNombre;

	@Column(name="NOM_PATERNO")
	private String nomPaterno;

	@Column(name="NSS10")
	private String nss10;
	
	@Id	
	@Column(name="NSS11")
	private String nss11;

	@Column(name="REF_CURP")
	private String refCurp;
	
	@Column(name="REGPATRON")
	private String regpatron;

	@Column(name="SEXO")
	private BigDecimal sexo;

	public MgtInfincasegvig() {
	}

	public BigDecimal getCiz() {
		return this.ciz;
	}

	public void setCiz(BigDecimal ciz) {
		this.ciz = ciz;
	}

	public BigDecimal getCveDeleg() {
		return this.cveDeleg;
	}

	public void setCveDeleg(BigDecimal cveDeleg) {
		this.cveDeleg = cveDeleg;
	}

	public BigDecimal getCveSubdeleg() {
		return this.cveSubdeleg;
	}

	public void setCveSubdeleg(BigDecimal cveSubdeleg) {
		this.cveSubdeleg = cveSubdeleg;
	}

	public BigDecimal getCveUmf() {
		return this.cveUmf;
	}

	public void setCveUmf(BigDecimal cveUmf) {
		this.cveUmf = cveUmf;
	}

	public String getNomMaterno() {
		return this.nomMaterno;
	}

	public void setNomMaterno(String nomMaterno) {
		this.nomMaterno = nomMaterno;
	}

	public String getNomNombre() {
		return this.nomNombre;
	}

	public void setNomNombre(String nomNombre) {
		this.nomNombre = nomNombre;
	}

	public String getNomPaterno() {
		return this.nomPaterno;
	}

	public void setNomPaterno(String nomPaterno) {
		this.nomPaterno = nomPaterno;
	}

	public String getNss10() {
		return this.nss10;
	}

	public void setNss10(String nss10) {
		this.nss10 = nss10;
	}

	public String getNss11() {
		return this.nss11;
	}

	public void setNss11(String nss11) {
		this.nss11 = nss11;
	}

	public String getRefCurp() {
		return this.refCurp;
	}

	public void setRefCurp(String refCurp) {
		this.refCurp = refCurp;
	}

	public String getRegpatron() {
		return this.regpatron;
	}

	public void setRegpatron(String regpatron) {
		this.regpatron = regpatron;
	}

	public BigDecimal getSexo() {
		return this.sexo;
	}

	public void setSexo(BigDecimal sexo) {
		this.sexo = sexo;
	}

}