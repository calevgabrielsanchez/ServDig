package mx.gob.imss.cdsss.delta.portal.web.bean;


import java.util.List;

public class RespuestaJSON<T> {
	
	T modelo;
	String mensaje;
	List<String> errores;
	private Boolean estado;
	
	public Boolean getEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	public T getModelo() {
		return modelo;
	}
	public void setModelo(T modelo) {
		this.modelo = modelo;
	}
	public List<String> getErrores() {
		return errores;
	}
	public void setErrores(List<String> errores) {
		this.errores = errores;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
	
}

