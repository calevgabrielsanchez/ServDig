package mx.gob.imss.ctirss.idse.persistencia;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the PATRONES database table.
 * 
 */
@Entity
@NamedQuery(name="Patrones.findAll", query="SELECT p FROM Patrones p")
public class Patrones implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="REG_PATRON")
	private String regPatron;

	private String actividad;

	@Column(name="CLASE_RT")
	private BigDecimal claseRt;

	@Column(name="CVE_DEL")
	private BigDecimal cveDel;

	@Column(name="CVE_SUB")
	private BigDecimal cveSub;
	
	@Column(name="DOMICILIO")
	private String domicilio;
	
	@Column(name="FRACCION")
	private BigDecimal fraccion;
	
	@Column(name="LOCALIDAD")
	private String localidad;

	@Column(name="MUNICIPIO")
	private String municipio;

	@Column(name="NOMBRE_PATRON")
	private String nombrePatron;

	@Column(name="REG_PATRON_DIG")
	private String regPatronDig;

	@Column(name="REG_PATRON_FIJO")
	private String regPatronFijo;
	
	@Column(name="RFC")
	private String rfc;

	@Column(name="SECTOR")
	private BigDecimal sector;

	@Column(name="TIPO_PATRON")
	private BigDecimal tipoPatron;

	public Patrones() {
	}

	public String getRegPatron() {
		return this.regPatron;
	}

	public void setRegPatron(String regPatron) {
		this.regPatron = regPatron;
	}

	public String getActividad() {
		return this.actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public BigDecimal getClaseRt() {
		return this.claseRt;
	}

	public void setClaseRt(BigDecimal claseRt) {
		this.claseRt = claseRt;
	}

	public BigDecimal getCveDel() {
		return this.cveDel;
	}

	public void setCveDel(BigDecimal cveDel) {
		this.cveDel = cveDel;
	}

	public BigDecimal getCveSub() {
		return this.cveSub;
	}

	public void setCveSub(BigDecimal cveSub) {
		this.cveSub = cveSub;
	}

	public String getDomicilio() {
		return this.domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public BigDecimal getFraccion() {
		return this.fraccion;
	}

	public void setFraccion(BigDecimal fraccion) {
		this.fraccion = fraccion;
	}

	public String getLocalidad() {
		return this.localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getMunicipio() {
		return this.municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getNombrePatron() {
		return this.nombrePatron;
	}

	public void setNombrePatron(String nombrePatron) {
		this.nombrePatron = nombrePatron;
	}

	public String getRegPatronDig() {
		return this.regPatronDig;
	}

	public void setRegPatronDig(String regPatronDig) {
		this.regPatronDig = regPatronDig;
	}

	public String getRegPatronFijo() {
		return this.regPatronFijo;
	}

	public void setRegPatronFijo(String regPatronFijo) {
		this.regPatronFijo = regPatronFijo;
	}

	public String getRfc() {
		return this.rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public BigDecimal getSector() {
		return this.sector;
	}

	public void setSector(BigDecimal sector) {
		this.sector = sector;
	}

	public BigDecimal getTipoPatron() {
		return this.tipoPatron;
	}

	public void setTipoPatron(BigDecimal tipoPatron) {
		this.tipoPatron = tipoPatron;
	}

}