package mx.gob.imss.cit.dacvass.servicios.externos.model.services.clasificacion;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

@XmlRootElement
public class ActualizacionClasificaionPatronalBdtuSindoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 205243753223519823L;
	
	private String cveUsuario;
	private Integer cveCausa;
	private SujetoObligado sujetoObligado;
	private Integer tipoMovimiento;
	private String cveFolioPac;
	private Date fecSurteEfecto;
	private Integer cveIdSubdelegacionUsuario;
	
	
	
	
	
	public Integer getCveIdSubdelegacionUsuario() {
		return cveIdSubdelegacionUsuario;
	}
	public void setCveIdSubdelegacionUsuario(Integer cveIdSubdelegacionUsuario) {
		this.cveIdSubdelegacionUsuario = cveIdSubdelegacionUsuario;
	}
	public Date getFecSurteEfecto() {
		return fecSurteEfecto;
	}
	public void setFecSurteEfecto(Date fecSurteEfecto) {
		this.fecSurteEfecto = fecSurteEfecto;
	}
	public String getCveFolioPac() {
		return cveFolioPac;
	}
	public void setCveFolioPac(String cveFolioPac) {
		this.cveFolioPac = cveFolioPac;
	}
	public String getCveUsuario() {
		return cveUsuario;
	}
	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}
	
	
	public Integer getCveCausa() {
		return cveCausa;
	}
	public void setCveCausa(Integer cveCausa) {
		this.cveCausa = cveCausa;
	}
	public SujetoObligado getSujetoObligado() {
		return sujetoObligado;
	}
	public void setSujetoObligado(SujetoObligado sujetoObligado) {
		this.sujetoObligado = sujetoObligado;
	}
	public Integer getTipoMovimiento() {
		return tipoMovimiento;
	}
	public void setTipoMovimiento(Integer tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}
	
	
	

}
