package mx.gob.imss.cit.dacvass.servicios.externos.model.identidad;

import java.util.List;

import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Persona;

public class PersonaSua extends Persona {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3692841839891507795L;
	private List<String>refCorreoElectronico;
	private boolean isUsuarioRegistradoPortalFiel;
	
	public List<String> getRefCorreoElectronico() {
		return refCorreoElectronico;
	}
	public void setRefCorreoElectronico(List<String> refCorreoElectronico) {
		this.refCorreoElectronico = refCorreoElectronico;
	}
	public boolean isUsuarioRegistradoPortalFiel() {
		return isUsuarioRegistradoPortalFiel;
	}
	public void setUsuarioRegistradoPortalFiel(boolean isUsuarioRegistradoPortalFiel) {
		this.isUsuarioRegistradoPortalFiel = isUsuarioRegistradoPortalFiel;
	}
	
	
	

}
