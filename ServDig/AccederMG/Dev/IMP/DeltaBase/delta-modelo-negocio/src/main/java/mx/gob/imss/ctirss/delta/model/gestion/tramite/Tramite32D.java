package mx.gob.imss.ctirss.delta.model.gestion.tramite;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;

@XmlRootElement
public class Tramite32D extends Tramite {

	private static final long serialVersionUID = -9090297535005958761L;

	private Persona personaFM;
	private String respuestaOpinion;
	private List<String> patrones;
	private List<String> patronesVigentes;
	private List<String> patronesHuelga;
	private List<String> patronesBaja;
	private int numPatrones;
	private int numPatronesVigentes;
	private int numPatronesHuelga;
	private int numPatronesBaja;
	private int numTrabajadores;
	private boolean tieneAdeudos;

	public Persona getPersonaFM() {
		return personaFM;
	}

	public void setPersonaFM(Persona personaFM) {
		this.personaFM = personaFM;
	}

	public String getRespuestaOpinion() {
		return respuestaOpinion;
	}

	public void setRespuestaOpinion(String respuestaOpinion) {
		this.respuestaOpinion = respuestaOpinion;
	}

	public List<String> getPatrones() {
		return patrones;
	}

	public void setPatrones(List<String> patrones) {
		this.patrones = patrones;
	}

	public List<String> getPatronesVigentes() {
		return patronesVigentes;
	}

	public void setPatronesVigentes(List<String> patronesVigentes) {
		this.patronesVigentes = patronesVigentes;
	}

	public List<String> getPatronesHuelga() {
		return patronesHuelga;
	}

	public void setPatronesHuelga(List<String> patronesHuelga) {
		this.patronesHuelga = patronesHuelga;
	}

	public List<String> getPatronesBaja() {
		return patronesBaja;
	}

	public void setPatronesBaja(List<String> patronesBaja) {
		this.patronesBaja = patronesBaja;
	}

	public int getNumPatrones() {
		return numPatrones;
	}

	public void setNumPatrones(int numPatrones) {
		this.numPatrones = numPatrones;
	}

	public int getNumPatronesVigentes() {
		return numPatronesVigentes;
	}

	public void setNumPatronesVigentes(int numPatronesVigentes) {
		this.numPatronesVigentes = numPatronesVigentes;
	}

	public int getNumPatronesHuelga() {
		return numPatronesHuelga;
	}

	public void setNumPatronesHuelga(int numPatronesHuelga) {
		this.numPatronesHuelga = numPatronesHuelga;
	}

	public int getNumPatronesBaja() {
		return numPatronesBaja;
	}

	public void setNumPatronesBaja(int numPatronesBaja) {
		this.numPatronesBaja = numPatronesBaja;
	}

	public int getNumTrabajadores() {
		return numTrabajadores;
	}

	public void setNumTrabajadores(int numTrabajadores) {
		this.numTrabajadores = numTrabajadores;
	}

	public boolean isTieneAdeudos() {
		return tieneAdeudos;
	}

	public void setTieneAdeudos(boolean tieneAdeudos) {
		this.tieneAdeudos = tieneAdeudos;
	}

}
