package mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.catComun;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CatComunDelegacion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4377769359053306609L;
	private String cveDelegacion;
	private String cveAnterior;
	private BigDecimal cveCiz;
	private String cveEntidadFederativa;
	private String cveRegion;
	private String descDelegacion;
	private String descDelegacionRep;
	private String emailDelegado;
	private Date expiraVigencia;
	private Date fechaEfectiva;
	private String nombreDelegado;
	private String vigencia;
	public String getCveDelegacion() {
		return cveDelegacion;
	}
	public void setCveDelegacion(String cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}
	public String getCveAnterior() {
		return cveAnterior;
	}
	public void setCveAnterior(String cveAnterior) {
		this.cveAnterior = cveAnterior;
	}
	public BigDecimal getCveCiz() {
		return cveCiz;
	}
	public void setCveCiz(BigDecimal cveCiz) {
		this.cveCiz = cveCiz;
	}
	public String getCveEntidadFederativa() {
		return cveEntidadFederativa;
	}
	public void setCveEntidadFederativa(String cveEntidadFederativa) {
		this.cveEntidadFederativa = cveEntidadFederativa;
	}
	public String getCveRegion() {
		return cveRegion;
	}
	public void setCveRegion(String cveRegion) {
		this.cveRegion = cveRegion;
	}
	public String getDescDelegacion() {
		return descDelegacion;
	}
	public void setDescDelegacion(String descDelegacion) {
		this.descDelegacion = descDelegacion;
	}
	public String getDescDelegacionRep() {
		return descDelegacionRep;
	}
	public void setDescDelegacionRep(String descDelegacionRep) {
		this.descDelegacionRep = descDelegacionRep;
	}
	public String getEmailDelegado() {
		return emailDelegado;
	}
	public void setEmailDelegado(String emailDelegado) {
		this.emailDelegado = emailDelegado;
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
	public String getNombreDelegado() {
		return nombreDelegado;
	}
	public void setNombreDelegado(String nombreDelegado) {
		this.nombreDelegado = nombreDelegado;
	}
	public String getVigencia() {
		return vigencia;
	}
	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}
	

}
