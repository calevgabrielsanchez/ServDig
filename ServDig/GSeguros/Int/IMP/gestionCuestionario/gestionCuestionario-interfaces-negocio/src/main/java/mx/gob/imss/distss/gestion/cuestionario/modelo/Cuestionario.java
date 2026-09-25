package mx.gob.imss.distss.gestion.cuestionario.modelo;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class Cuestionario extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private int clave;
	private String titulo;
	private String descripcion;
	private TipoCuestionario tipoCuestionario;
	private List<Seccion> secciones;

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

	public TipoCuestionario getTipoCuestionario() {
		return tipoCuestionario;
	}

	public void setTipoCuestionario(TipoCuestionario tipoCuestionario) {
		this.tipoCuestionario = tipoCuestionario;
	}

	public List<Seccion> getSecciones() {
		
		if (secciones == null) {
			secciones = new ArrayList<Seccion>();
		}
		
		return secciones;
	}

	public void setSecciones(List<Seccion> secciones) {
		this.secciones = secciones;
	}

}
