package mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.catComun;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CatComunUmf implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3606489510937887447L;
	
	private String cvePrei;

	private String cveCtoCosto;
	
	private String ctaBancaria;

	private String cveAnterior;

	private String cveBanco;

	private String cveCtoCostoReal;

	private String cveCtoCostoSubs;

	private String cveDelegacion;

	private String cveEntidadFederativa;

	private BigDecimal cveNivelAtencion;

	private String cvePreiReal;

	private String cvePresupuestal;

	private BigDecimal cvePsm;

	private String cveSubdelegacion;

	private BigDecimal cveUmf;

	private Date expiraVigencia;

	private Date fechaEfectiva;

	private String nomMunicipio;

	private String nomUnidadMed;

	private BigDecimal switchPagoBanco;

	private BigDecimal tipoUmf;

	private String vigencia;

	public String getCvePrei() {
		return cvePrei;
	}

	public void setCvePrei(String cvePrei) {
		this.cvePrei = cvePrei;
	}

	public String getCveCtoCosto() {
		return cveCtoCosto;
	}

	public void setCveCtoCosto(String cveCtoCosto) {
		this.cveCtoCosto = cveCtoCosto;
	}

	public String getCtaBancaria() {
		return ctaBancaria;
	}

	public void setCtaBancaria(String ctaBancaria) {
		this.ctaBancaria = ctaBancaria;
	}

	public String getCveAnterior() {
		return cveAnterior;
	}

	public void setCveAnterior(String cveAnterior) {
		this.cveAnterior = cveAnterior;
	}

	public String getCveBanco() {
		return cveBanco;
	}

	public void setCveBanco(String cveBanco) {
		this.cveBanco = cveBanco;
	}

	public String getCveCtoCostoReal() {
		return cveCtoCostoReal;
	}

	public void setCveCtoCostoReal(String cveCtoCostoReal) {
		this.cveCtoCostoReal = cveCtoCostoReal;
	}

	public String getCveCtoCostoSubs() {
		return cveCtoCostoSubs;
	}

	public void setCveCtoCostoSubs(String cveCtoCostoSubs) {
		this.cveCtoCostoSubs = cveCtoCostoSubs;
	}

	public String getCveDelegacion() {
		return cveDelegacion;
	}

	public void setCveDelegacion(String cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}

	public String getCveEntidadFederativa() {
		return cveEntidadFederativa;
	}

	public void setCveEntidadFederativa(String cveEntidadFederativa) {
		this.cveEntidadFederativa = cveEntidadFederativa;
	}

	public BigDecimal getCveNivelAtencion() {
		return cveNivelAtencion;
	}

	public void setCveNivelAtencion(BigDecimal cveNivelAtencion) {
		this.cveNivelAtencion = cveNivelAtencion;
	}

	public String getCvePreiReal() {
		return cvePreiReal;
	}

	public void setCvePreiReal(String cvePreiReal) {
		this.cvePreiReal = cvePreiReal;
	}

	public String getCvePresupuestal() {
		return cvePresupuestal;
	}

	public void setCvePresupuestal(String cvePresupuestal) {
		this.cvePresupuestal = cvePresupuestal;
	}

	public BigDecimal getCvePsm() {
		return cvePsm;
	}

	public void setCvePsm(BigDecimal cvePsm) {
		this.cvePsm = cvePsm;
	}

	public String getCveSubdelegacion() {
		return cveSubdelegacion;
	}

	public void setCveSubdelegacion(String cveSubdelegacion) {
		this.cveSubdelegacion = cveSubdelegacion;
	}

	public BigDecimal getCveUmf() {
		return cveUmf;
	}

	public void setCveUmf(BigDecimal cveUmf) {
		this.cveUmf = cveUmf;
	}

	public Date getExpiraVigencia() {
		return expiraVigencia;
	}

	public void setExpiraVigencia(Date expiraVigencia) {
		this.expiraVigencia = expiraVigencia;
	}

	public Date getFechaEfectiva() {
		return fechaEfectiva;
	}

	public void setFechaEfectiva(Date fechaEfectiva) {
		this.fechaEfectiva = fechaEfectiva;
	}

	public String getNomMunicipio() {
		return nomMunicipio;
	}

	public void setNomMunicipio(String nomMunicipio) {
		this.nomMunicipio = nomMunicipio;
	}

	public String getNomUnidadMed() {
		return nomUnidadMed;
	}

	public void setNomUnidadMed(String nomUnidadMed) {
		this.nomUnidadMed = nomUnidadMed;
	}

	public BigDecimal getSwitchPagoBanco() {
		return switchPagoBanco;
	}

	public void setSwitchPagoBanco(BigDecimal switchPagoBanco) {
		this.switchPagoBanco = switchPagoBanco;
	}

	public BigDecimal getTipoUmf() {
		return tipoUmf;
	}

	public void setTipoUmf(BigDecimal tipoUmf) {
		this.tipoUmf = tipoUmf;
	}

	public String getVigencia() {
		return vigencia;
	}

	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}
	

}
