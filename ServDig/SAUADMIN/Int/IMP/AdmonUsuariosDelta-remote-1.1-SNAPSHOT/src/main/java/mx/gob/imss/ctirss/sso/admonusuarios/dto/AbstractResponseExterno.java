package mx.gob.imss.ctirss.sso.admonusuarios.dto;

import java.io.Serializable;

public abstract class AbstractResponseExterno implements Serializable {
	
	private static final long serialVersionUID = 3613923701733890295L;
	
	private String codigo = "000";
	private String mensaje = "Respuesta exitosa";

	public AbstractResponseExterno() {
		super();
		this.codigo = "000";
		this.mensaje = "Respuesta exitosa";
	}

	public AbstractResponseExterno(String codigo, String mensaje) {
		super();
		this.codigo = codigo;
		this.mensaje = mensaje;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}