package mx.gob.imss.distss.gestion.cuestionario.modelo;

import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class Respuesta extends AbstractModel {

	private static final long serialVersionUID = 1L;

	// Pregunta que esta respuesta está contestando
	private int cvePregunta;
	private String numPregunta;
	private int numSeccion;
	
	// Lista de los valores seleccionados como respuesta
	private List<Opcion> valores;

	public int getCvePregunta() {
		return cvePregunta;
	}

	public void setCvePregunta(int cvePregunta) {
		this.cvePregunta = cvePregunta;
	}

	public String getNumPregunta() {
		return numPregunta;
	}

	public void setNumPregunta(String numPregunta) {
		this.numPregunta = numPregunta;
	}

	public int getNumSeccion() {
		return numSeccion;
	}

	public void setNumSeccion(int numSeccion) {
		this.numSeccion = numSeccion;
	}

	public List<Opcion> getValores() {
		return valores;
	}

	public void setValores(List<Opcion> valores) {
		this.valores = valores;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cvePregunta;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Respuesta other = (Respuesta) obj;
		if (cvePregunta != other.cvePregunta) {
			return false;
		}
		return true;
	}
}