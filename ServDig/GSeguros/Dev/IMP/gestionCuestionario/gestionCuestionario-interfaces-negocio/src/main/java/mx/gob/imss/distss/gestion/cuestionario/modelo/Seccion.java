package mx.gob.imss.distss.gestion.cuestionario.modelo;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class Seccion extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private int clave;
	private String titulo;
	private String descripcion;
	private List<Pregunta> preguntas;

	public int getClave() {
		return clave;
	}

	public void setClave(int clave) {
		this.clave = clave;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Pregunta> getPreguntas() {
		
		if (preguntas == null) {
			preguntas = new ArrayList<Pregunta>();
		}
		
		return preguntas;
	}

	public void setPreguntas(List<Pregunta> preguntas) {
		this.preguntas = preguntas;
	}

}
