package mx.gob.imss.ctirss.correccion.catalogos.model;



import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.catalogos.base.model.AbstractCrtTramitePresentado;


@Entity
@Table(name="CRT_TRAMITEPRESENTADO")
public class CrtTramitePresentado extends AbstractCrtTramitePresentado {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Transient
	private String nuFolio;
	
	@Transient
	private String desTramite;
	
	
	@Transient
	private String desMensaje;
	

	public String getNuFolio() {
		return nuFolio;
	}

	public void setNuFolio(String nuFolio) {
		this.nuFolio = nuFolio;
	}

	public String getDesTramite() {
		return desTramite;
	}

	public void setDesTramite(String desTramite) {
		this.desTramite = desTramite;
	}

	public String getDesMensaje() {
		return desMensaje;
	}

	public void setDesMensaje(String desMensaje) {
		this.desMensaje = desMensaje;
	}
	
	
	
	
}
