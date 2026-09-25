package mx.gob.imss.ctirss.delta.model.gestion.tramite;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

@XmlRootElement
public class TramitePersonaAutorizada extends Tramite {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2929374863539803040L;
	
	private Fisica personaAutorizada;
	private Fisica personaFisica;
	private Moral personaMoral;
	private List<SujetoObligado> sujetosObligados;
	
	public Fisica getPersonaAutorizada() {
		return personaAutorizada;
	}
	
	public void setPersonaAutorizada(Fisica personaAutorizada) {
		this.personaAutorizada = personaAutorizada;
	}
	
	public Fisica getPersonaFisica() {
		return personaFisica;
	}
	
	public void setPersonaFisica(Fisica personaFisica) {
		this.personaFisica = personaFisica;
	}
	
	public Moral getPersonaMoral() {
		return personaMoral;
	}
	
	public void setPersonaMoral(Moral personaMoral) {
		this.personaMoral = personaMoral;
	}
	
	public List<SujetoObligado> getSujetosObligados() {
		return sujetosObligados;
	}
	
	public void setSujetosObligados(List<SujetoObligado> sujetosObligados) {
		this.sujetosObligados = sujetosObligados;
	}
	
}
