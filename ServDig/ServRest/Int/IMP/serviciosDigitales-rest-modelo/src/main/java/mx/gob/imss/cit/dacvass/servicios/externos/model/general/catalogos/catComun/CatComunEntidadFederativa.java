package mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.catComun;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CatComunEntidadFederativa implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6664225832956969950L;
	private String cveEntidadFederativa;
	private String cveAnterior;
	private String descEntidadFederativa;
	private Date expiraVigencia;
	private Date fechaEfectiva;
	private String vigencia;
	
	public String getCveEntidadFederativa() {
		return cveEntidadFederativa;
	}
	public void setCveEntidadFederativa(String cveEntidadFederativa) {
		this.cveEntidadFederativa = cveEntidadFederativa;
	}
	public String getCveAnterior() {
		return cveAnterior;
	}
	public void setCveAnterior(String cveAnterior) {
		this.cveAnterior = cveAnterior;
	}
	public String getDescEntidadFederativa() {
		return descEntidadFederativa;
	}
	public void setDescEntidadFederativa(String descEntidadFederativa) {
		this.descEntidadFederativa = descEntidadFederativa;
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
	public String getVigencia() {
		return vigencia;
	}
	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}
	
	

}
