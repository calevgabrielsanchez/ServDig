package mx.gob.imss.distss.gestion.cuestionario.modelo;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class Opcion extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private int clave;	
	private String descripcion;
	private int valor;
	private boolean habilitarDependencia;
	private List<Pregunta> dependencias;

	public int getClave() {
		return clave;
	}

	public void setClave(int clave) {
		this.clave = clave;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public boolean isHabilitarDependencia() {
		return habilitarDependencia;
	}

	public void setHabilitarDependencia(boolean habilitarDependencia) {
		this.habilitarDependencia = habilitarDependencia;
	}

	public List<Pregunta> getDependencias() {
		
		if (dependencias == null) {
			dependencias = new ArrayList<Pregunta>();
		}
		
		return dependencias;
	}

	public void setDependencias(List<Pregunta> dependencias) {
		this.dependencias = dependencias;
	}
}