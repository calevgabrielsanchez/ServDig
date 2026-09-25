package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CANCELCREDENTIALS database table.
 * 
 */
@Embeddable
@Table(name="CANCELCREDENTIALS")
@NamedQuery(name="Cancelcredential.findAll", query="SELECT c FROM Cancelcredential c")
public class Cancelcredential implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String folioanterior;

	private String foliocredencialanterior;

	private BigDecimal idcalidad;

	private BigDecimal idenrol;

	private BigDecimal idenrollmentstation;

	private BigDecimal idtiporeexpedicion;

	private String nombredh;

	private String nss;

	private String referenciaboucher;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	public Cancelcredential() {
	}

	public BigDecimal getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(BigDecimal createdby) {
		this.createdby = createdby;
	}

	public Date getCreatedon() {
		return this.createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public String getFolioanterior() {
		return this.folioanterior;
	}

	public void setFolioanterior(String folioanterior) {
		this.folioanterior = folioanterior;
	}

	public String getFoliocredencialanterior() {
		return this.foliocredencialanterior;
	}

	public void setFoliocredencialanterior(String foliocredencialanterior) {
		this.foliocredencialanterior = foliocredencialanterior;
	}

	public BigDecimal getIdcalidad() {
		return this.idcalidad;
	}

	public void setIdcalidad(BigDecimal idcalidad) {
		this.idcalidad = idcalidad;
	}

	public BigDecimal getIdenrol() {
		return this.idenrol;
	}

	public void setIdenrol(BigDecimal idenrol) {
		this.idenrol = idenrol;
	}

	public BigDecimal getIdenrollmentstation() {
		return this.idenrollmentstation;
	}

	public void setIdenrollmentstation(BigDecimal idenrollmentstation) {
		this.idenrollmentstation = idenrollmentstation;
	}

	public BigDecimal getIdtiporeexpedicion() {
		return this.idtiporeexpedicion;
	}

	public void setIdtiporeexpedicion(BigDecimal idtiporeexpedicion) {
		this.idtiporeexpedicion = idtiporeexpedicion;
	}

	public String getNombredh() {
		return this.nombredh;
	}

	public void setNombredh(String nombredh) {
		this.nombredh = nombredh;
	}

	public String getNss() {
		return this.nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
	}

	public String getReferenciaboucher() {
		return this.referenciaboucher;
	}

	public void setReferenciaboucher(String referenciaboucher) {
		this.referenciaboucher = referenciaboucher;
	}

	public BigDecimal getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(BigDecimal updatedby) {
		this.updatedby = updatedby;
	}

	public Date getUpdatedon() {
		return this.updatedon;
	}

	public void setUpdatedon(Date updatedon) {
		this.updatedon = updatedon;
	}

}