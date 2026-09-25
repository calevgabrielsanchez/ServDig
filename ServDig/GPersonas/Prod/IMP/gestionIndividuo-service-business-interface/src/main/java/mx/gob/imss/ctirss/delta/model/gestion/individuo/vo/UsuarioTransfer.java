package mx.gob.imss.ctirss.delta.model.gestion.individuo.vo;

import java.io.Serializable;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

public class UsuarioTransfer implements Serializable {

	
	
	private Fisica fisica;
	private Solicitud solicitud;
	private String password;
	
	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private Object usuarioDTO;

	public Object getUsuarioDTO() {
		return usuarioDTO;
	}

	public void setUsuarioDTO(Object usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

	public Fisica getFisica() {
		return fisica;
	}

	public void setFisica(Fisica fisica) {
		this.fisica = fisica;
	}

	public Solicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}
	
	
	
	
}

