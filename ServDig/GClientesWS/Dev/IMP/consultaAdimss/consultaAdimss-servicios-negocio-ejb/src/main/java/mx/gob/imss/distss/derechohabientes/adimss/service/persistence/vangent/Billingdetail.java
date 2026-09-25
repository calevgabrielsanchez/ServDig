package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the BILLINGDETAILS database table.
 * 
 */
@Entity
@Table(name="BILLINGDETAILS")
@NamedQuery(name="Billingdetail.findAll", query="SELECT b FROM Billingdetail b")
public class Billingdetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BillingdetailPK id;

	private String comprobante;

	private String curp;

	private String domicilio;

	private String firma;

	private String foto;

	private String identidad;

	private BigDecimal images;

	private String nacionalidad;

	private String recibo;

	private String slap1;

	private String slap2;

	private String slap3;

	private String slap4;

	@Column(name="TANULAR_DERECHO")
	private String tanularDerecho;

	@Column(name="TANULAR_IZQUIERDO")
	private String tanularIzquierdo;

	@Column(name="TINDICE_DERECHO")
	private String tindiceDerecho;

	@Column(name="TINDICE_IZQUIERDO")
	private String tindiceIzquierdo;

	@Column(name="TMEDIO_DERECHO")
	private String tmedioDerecho;

	@Column(name="TMEDIO_IZQUIERDO")
	private String tmedioIzquierdo;

	@Column(name="TMENIQUE_DERECHO")
	private String tmeniqueDerecho;

	@Column(name="TMENIQUE_IZQUIERDO")
	private String tmeniqueIzquierdo;

	@Column(name="TPULGAR_DERECHO")
	private String tpulgarDerecho;

	@Column(name="TPULGAR_IZQUIERDO")
	private String tpulgarIzquierdo;

	@Column(name="WANULAR_DERECHO")
	private String wanularDerecho;

	@Column(name="WANULAR_IZQUIERDO")
	private String wanularIzquierdo;

	@Column(name="WINDICE_DERECHO")
	private String windiceDerecho;

	@Column(name="WINDICE_IZQUIERDO")
	private String windiceIzquierdo;

	@Column(name="WMEDIO_DERECHO")
	private String wmedioDerecho;

	@Column(name="WMEDIO_IZQUIERDO")
	private String wmedioIzquierdo;

	@Column(name="WMENIQUE_DERECHO")
	private String wmeniqueDerecho;

	@Column(name="WMENIQUE_IZQUIERDO")
	private String wmeniqueIzquierdo;

	@Column(name="WPULGAR_DERECHO")
	private String wpulgarDerecho;

	@Column(name="WPULGAR_IZQUIERDO")
	private String wpulgarIzquierdo;

	public Billingdetail() {
	}

	public BillingdetailPK getId() {
		return this.id;
	}

	public void setId(BillingdetailPK id) {
		this.id = id;
	}

	public String getComprobante() {
		return this.comprobante;
	}

	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
	}

	public String getCurp() {
		return this.curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getDomicilio() {
		return this.domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getFirma() {
		return this.firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public String getFoto() {
		return this.foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getIdentidad() {
		return this.identidad;
	}

	public void setIdentidad(String identidad) {
		this.identidad = identidad;
	}

	public BigDecimal getImages() {
		return this.images;
	}

	public void setImages(BigDecimal images) {
		this.images = images;
	}

	public String getNacionalidad() {
		return this.nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getRecibo() {
		return this.recibo;
	}

	public void setRecibo(String recibo) {
		this.recibo = recibo;
	}

	public String getSlap1() {
		return this.slap1;
	}

	public void setSlap1(String slap1) {
		this.slap1 = slap1;
	}

	public String getSlap2() {
		return this.slap2;
	}

	public void setSlap2(String slap2) {
		this.slap2 = slap2;
	}

	public String getSlap3() {
		return this.slap3;
	}

	public void setSlap3(String slap3) {
		this.slap3 = slap3;
	}

	public String getSlap4() {
		return this.slap4;
	}

	public void setSlap4(String slap4) {
		this.slap4 = slap4;
	}

	public String getTanularDerecho() {
		return this.tanularDerecho;
	}

	public void setTanularDerecho(String tanularDerecho) {
		this.tanularDerecho = tanularDerecho;
	}

	public String getTanularIzquierdo() {
		return this.tanularIzquierdo;
	}

	public void setTanularIzquierdo(String tanularIzquierdo) {
		this.tanularIzquierdo = tanularIzquierdo;
	}

	public String getTindiceDerecho() {
		return this.tindiceDerecho;
	}

	public void setTindiceDerecho(String tindiceDerecho) {
		this.tindiceDerecho = tindiceDerecho;
	}

	public String getTindiceIzquierdo() {
		return this.tindiceIzquierdo;
	}

	public void setTindiceIzquierdo(String tindiceIzquierdo) {
		this.tindiceIzquierdo = tindiceIzquierdo;
	}

	public String getTmedioDerecho() {
		return this.tmedioDerecho;
	}

	public void setTmedioDerecho(String tmedioDerecho) {
		this.tmedioDerecho = tmedioDerecho;
	}

	public String getTmedioIzquierdo() {
		return this.tmedioIzquierdo;
	}

	public void setTmedioIzquierdo(String tmedioIzquierdo) {
		this.tmedioIzquierdo = tmedioIzquierdo;
	}

	public String getTmeniqueDerecho() {
		return this.tmeniqueDerecho;
	}

	public void setTmeniqueDerecho(String tmeniqueDerecho) {
		this.tmeniqueDerecho = tmeniqueDerecho;
	}

	public String getTmeniqueIzquierdo() {
		return this.tmeniqueIzquierdo;
	}

	public void setTmeniqueIzquierdo(String tmeniqueIzquierdo) {
		this.tmeniqueIzquierdo = tmeniqueIzquierdo;
	}

	public String getTpulgarDerecho() {
		return this.tpulgarDerecho;
	}

	public void setTpulgarDerecho(String tpulgarDerecho) {
		this.tpulgarDerecho = tpulgarDerecho;
	}

	public String getTpulgarIzquierdo() {
		return this.tpulgarIzquierdo;
	}

	public void setTpulgarIzquierdo(String tpulgarIzquierdo) {
		this.tpulgarIzquierdo = tpulgarIzquierdo;
	}

	public String getWanularDerecho() {
		return this.wanularDerecho;
	}

	public void setWanularDerecho(String wanularDerecho) {
		this.wanularDerecho = wanularDerecho;
	}

	public String getWanularIzquierdo() {
		return this.wanularIzquierdo;
	}

	public void setWanularIzquierdo(String wanularIzquierdo) {
		this.wanularIzquierdo = wanularIzquierdo;
	}

	public String getWindiceDerecho() {
		return this.windiceDerecho;
	}

	public void setWindiceDerecho(String windiceDerecho) {
		this.windiceDerecho = windiceDerecho;
	}

	public String getWindiceIzquierdo() {
		return this.windiceIzquierdo;
	}

	public void setWindiceIzquierdo(String windiceIzquierdo) {
		this.windiceIzquierdo = windiceIzquierdo;
	}

	public String getWmedioDerecho() {
		return this.wmedioDerecho;
	}

	public void setWmedioDerecho(String wmedioDerecho) {
		this.wmedioDerecho = wmedioDerecho;
	}

	public String getWmedioIzquierdo() {
		return this.wmedioIzquierdo;
	}

	public void setWmedioIzquierdo(String wmedioIzquierdo) {
		this.wmedioIzquierdo = wmedioIzquierdo;
	}

	public String getWmeniqueDerecho() {
		return this.wmeniqueDerecho;
	}

	public void setWmeniqueDerecho(String wmeniqueDerecho) {
		this.wmeniqueDerecho = wmeniqueDerecho;
	}

	public String getWmeniqueIzquierdo() {
		return this.wmeniqueIzquierdo;
	}

	public void setWmeniqueIzquierdo(String wmeniqueIzquierdo) {
		this.wmeniqueIzquierdo = wmeniqueIzquierdo;
	}

	public String getWpulgarDerecho() {
		return this.wpulgarDerecho;
	}

	public void setWpulgarDerecho(String wpulgarDerecho) {
		this.wpulgarDerecho = wpulgarDerecho;
	}

	public String getWpulgarIzquierdo() {
		return this.wpulgarIzquierdo;
	}

	public void setWpulgarIzquierdo(String wpulgarIzquierdo) {
		this.wpulgarIzquierdo = wpulgarIzquierdo;
	}

}