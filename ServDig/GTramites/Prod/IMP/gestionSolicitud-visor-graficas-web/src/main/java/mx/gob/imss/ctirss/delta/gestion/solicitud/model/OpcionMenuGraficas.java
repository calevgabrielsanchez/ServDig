package mx.gob.imss.ctirss.delta.gestion.solicitud.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class OpcionMenuGraficas implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String titulo;
	private boolean collapsible;
	private String url;
	private List<OpcionMenuGraficas> dependientes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public boolean isCollapsible() {
		return collapsible;
	}

	public void setCollapsible(boolean collapsible) {
		this.collapsible = collapsible;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<OpcionMenuGraficas> getDependientes() {
		
		if (dependientes == null) {
			dependientes = new LinkedList<OpcionMenuGraficas>();
		}
		
		return dependientes;
	}

	public void setDependientes(List<OpcionMenuGraficas> dependientes) {
		this.dependientes = dependientes;
	}
}
