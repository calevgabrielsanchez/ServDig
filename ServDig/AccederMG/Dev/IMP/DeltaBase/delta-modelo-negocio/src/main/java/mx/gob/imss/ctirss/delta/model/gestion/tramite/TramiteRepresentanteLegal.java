package mx.gob.imss.ctirss.delta.model.gestion.tramite;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

@XmlRootElement
public class TramiteRepresentanteLegal extends Tramite implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Fisica fisica;
	private SujetoObligado sujetoObligado;
	private Fisica fisicaRepresentada;
	private Moral moralRepresentada;
	private FirmaElectronica firmaElectronica;
	
	public Fisica getFisica() {
		return fisica;
	}
	
	public void setFisica(Fisica fisica) {
		this.fisica = fisica;
	}
	
	public SujetoObligado getSujetoObligado() {
		return sujetoObligado;
	}
	
	public void setSujetoObligado(SujetoObligado sujetoObligado) {
		this.sujetoObligado = sujetoObligado;
	}

	public FirmaElectronica getFirmaElectronica() {
		return firmaElectronica;
	}

	public void setFirmaElectronica(FirmaElectronica firmaElectronica) {
		this.firmaElectronica = firmaElectronica;
	}

	public Fisica getFisicaRepresentada() {
		return fisicaRepresentada;
	}

	public void setFisicaRepresentada(Fisica fisicaRepresentada) {
		this.fisicaRepresentada = fisicaRepresentada;
	}

	public Moral getMoralRepresentada() {
		return moralRepresentada;
	}

	public void setMoralRepresentada(Moral moralRepresentada) {
		this.moralRepresentada = moralRepresentada;
	}

}
