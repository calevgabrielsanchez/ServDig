package mx.gob.imss.ctirss.sso.admonusuarios.dto;

import java.io.Serializable;

public class AdminUserResponseDTO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String codigo;
	
	private String descripcion;
	
	private UsuarioIdentidadDTO usuarioIdentidadDTO;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public UsuarioIdentidadDTO getUsuarioIdentidadDTO() {
		return usuarioIdentidadDTO;
	}

	public void setUsuarioIdentidadDTO(UsuarioIdentidadDTO usuarioIdentidadDTO) {
		this.usuarioIdentidadDTO = usuarioIdentidadDTO;
	}
	
	

}
