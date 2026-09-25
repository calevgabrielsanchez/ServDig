package mx.gob.imss.distss.gestion.cuestionario.modelo;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class Pregunta extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private int clave;
	private String inciso;
	private String descripcion;
	private TipoRespuesta tipoRespuesta;
	private List<Opcion> opciones;
	private boolean isObligatoria;

	public int getClave() {
		return clave;
	}

	public void setClave(int clave) {
		this.clave = clave;
	}

	public String getInciso() {
		return inciso;
	}

	public void setInciso(String inciso) {
		this.inciso = inciso;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public TipoRespuesta getTipoRespuesta() {
		return tipoRespuesta;
	}

	public void setTipoRespuesta(TipoRespuesta tipoRespuesta) {
		this.tipoRespuesta = tipoRespuesta;
	}

	public List<Opcion> getOpciones() {
		
		if (opciones == null) {
			opciones = new ArrayList<Opcion>();
		}
		
		return opciones;
	}

	public void setOpciones(List<Opcion> opciones) {
		this.opciones = opciones;
	}

	public boolean isObligatoria() {
		return isObligatoria;
	}

	public void setObligatoria(boolean isObligatoria) {
		this.isObligatoria = isObligatoria;
	}

}
