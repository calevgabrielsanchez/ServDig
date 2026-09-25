package mx.gob.imss.ctirss.sso.admonusuarios.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResponsablesDelegacionDTO implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int clave;
	private String mensaje;
	private List<ResponsableDTO> responsables;
	
	
	public ResponsablesDelegacionDTO(){
		this.responsables=new ArrayList<ResponsableDTO>();
	}
	
	
	public List<ResponsableDTO> getResponsables() {
		return responsables;
	}


	public void setResponsables(List<ResponsableDTO> responsables) {
		this.responsables = responsables;
	}

	public int getClave() {
		return clave;
	}
	public void setClave(int clave) {
		this.clave = clave;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
	
}
