package mx.gob.imss.ctirss.delta.model.gestion.tramite;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaAutorizada;

@XmlRootElement
public class TramiteBajaPersonaAutorizada extends Tramite {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1649114020606202146L;
	private Fisica fisica;
	private Moral moral;
	List<PersonaAutorizada> personasAutorizadas;
	
	public Fisica getFisica() {
		return fisica;
	}
	
	public void setFisica(Fisica fisica) {
		this.fisica = fisica;
	}
	public Moral getMoral() {
		return moral;
	}
	
	public void setMoral(Moral moral) {
		this.moral = moral;
	}
	public List<PersonaAutorizada> getPersonasAutorizadas() {
		return personasAutorizadas;
	}
	public void setPersonasAutorizadas(List<PersonaAutorizada> personasAutorizadas) {
		this.personasAutorizadas = personasAutorizadas;
	}
	
	
	
}
