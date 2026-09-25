package mx.gob.imss.ctirss.delta.model.gestion.individuo;

import java.util.Map;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * Clase que representa cada error en la validación de la identidad contra el
 * trámite.
 * 
 */
public class ErrorValidacionIdentidadTramite extends AbstractModel {

	private static final long serialVersionUID = 1L;
	
	public ErrorValidacionIdentidadTramite() {
		super();
	}

	public ErrorValidacionIdentidadTramite(String nivelError, String mensaje,
			int tramiteSolucion) {
		super();
		this.nivelError = nivelError;
		this.mensaje = mensaje;
		this.tramiteSolucion = tramiteSolucion;
	}

	public ErrorValidacionIdentidadTramite(String nivelError, String mensaje,
			int tramiteSolucion, Map<String, Object> parametros) {
		super();
		this.nivelError = nivelError;
		this.mensaje = mensaje;
		this.tramiteSolucion = tramiteSolucion;
		this.parametros = parametros;
	}

	/**
	 * Qué grado tiene el error (INFO, WARNING, ERROR)
	 */
	private String nivelError;

	/**
	 * Representa el mensaje que se mostrará en pantalla
	 */
	private String mensaje;

	/**
	 * Representa el id del trámite que darí solución al error
	 */
	private int tramiteSolucion;

	/**
	 * Contiene parámetros extras para enviar info complementaria del error
	 */
	private Map<String, Object> parametros;

	public String getNivelError() {
		return nivelError;
	}

	public void setNivelError(String nivelError) {
		this.nivelError = nivelError;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public int getTramiteSolucion() {
		return tramiteSolucion;
	}

	public void setTramiteSolucion(int tramiteSolucion) {
		this.tramiteSolucion = tramiteSolucion;
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public void setParametros(Map<String, Object> parametros) {
		this.parametros = parametros;
	}

}
