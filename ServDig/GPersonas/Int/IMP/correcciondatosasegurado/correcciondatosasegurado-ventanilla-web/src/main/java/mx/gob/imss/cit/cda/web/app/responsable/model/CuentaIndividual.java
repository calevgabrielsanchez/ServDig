package mx.gob.imss.cit.cda.web.app.responsable.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CuentaIndividual extends BaseModel {

	private static final long serialVersionUID = 1L;

	public String getNssDestino() {
		return nssDestino;
	}

	public void setNssDestino(String nssDestino) {
		this.nssDestino = nssDestino;
	}

	private String registroPatronal;
	
	public String getRegistroPatronal() {
		return registroPatronal;
	}

	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}

	private int claveDelegacionOrigen;
	
	public int getClaveDelegacionOrigen() {
		return claveDelegacionOrigen;
	}

	public void setClaveDelegacionOrigen(int claveDelegacionOrigen) {
		this.claveDelegacionOrigen = claveDelegacionOrigen;
	}

	private int claveCiz;
	
	public int getClaveCiz() {
		return claveCiz;
	}

	public void setClaveCiz(int claveCiz) {
		this.claveCiz = claveCiz;
	}

	private String nssDestino;
	
	private List<PeriodoCuentaIndividual> periodos;

	public List<PeriodoCuentaIndividual> getPeriodos() {
		return periodos;
	}

	public void setPeriodos(List<PeriodoCuentaIndividual> periodos) {
		this.periodos = periodos;
	}

}
