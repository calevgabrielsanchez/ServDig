/**
 * Permite controlar la nevegacian de los tabs en pantalla 
 * asa como sus mensajes de exito y/o error.
 * 
 * @author Marco Antonio Nieto Plett
 */

package mx.gob.imss.ctirss.correccion.framework.utils;


/**
 * Clase que debe ser extendida por el objeto visual (VO)
 * que controle a los tabs.
 * @author Marco A. Nieto Plett
 * @version 1.0.0
 */
public abstract class ControlTabs {

	/**
	 * Indica desde donde viene la petician
	 */
	private int seccionPeticion;
	
	/**
	 * Indica si el tipo de guardado es parcial o final
	 */
	private Integer tipoGuardado;
	
	/**
	 * En caso de que exista un error en el flujo
	 * esta variable le indicara al usuario
	 * automaticamente
	 */
	private String error;
	
	/**
	 * Mensaje de axito personalizado,
	 * en caso de que no se utilice el 
	 * sistema utilizara el default.
	 * @author Marco Antonio Nieto Plett
	 */
	private String exito;
	
	/**
	 * Nos permie saber desde donde (TAB) proviene la
	 * petician solicitada.
	 * @see ConstantesSeguimiento
	 * @return Seccian
	 * @author Marco Antonio Nieto Plett
	 */
	public int getSeccionPeticion() {
		return seccionPeticion;
	}

	/**
	 * Permite ingresar la seccion desde donde proviene
	 * la petician solicitada.
	 * @param seccionPeticion
	 * @see ConstantesSeguimiento
	 * @author Marco Antonio Nieto Plett
	 */
	public void setSeccionPeticion(int seccionPeticion) {
		this.seccionPeticion = seccionPeticion;
	}

	/**
	 * Obtiene si el tipo de petician es de guardado
	 * parcial o final.
	 * 
	 * 1.- Parcial
	 * 2.- Final
	 * 
	 * En caso de que retorne un valor diferente de 1 y 2
	 * se debera omitir esta variable.
	 * 
	 * @see ConstantesSeguimiento
	 * @return
	 * @author Marco Antonio Nieto Plett
	 */
	public Integer getTipoGuardado() {
		return tipoGuardado;
	}

	/**
	 * Permite ingresar el tipo de guardado.
	 * 
	 * 1.- Parcial
	 * 2.- Final
	 * 
	 * @see ConstantesSeguimiento
	 * @param tipoGuardado
	 * @author Marco Antonio Nieto Plett
	 */
	public void setTipoGuardado(Integer tipoGuardado) {
		this.tipoGuardado = tipoGuardado;
	}

	/**
	 * Indica un error controlado por el programador
	 * y este se mostrara de fomra automatica al usuario
	 * @return error
	 * @author Marco Antonio Nieto Plett
	 */
	public String getError() {
		return error;
	}

	/**
	 * Permite indicar un error personalizado y se
	 * mostrara de fomra automatica al usuario
	 * @param error
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * Permite obtener un mensaje de axito personalizado,
	 * este se mostrara al usuario de forma automatica.
	 * En caso de que no se utilice el sistema cuenta
	 * con un mensaje por defecto y genarico.
	 * @return
	 * @author Marco Antonio Nieto Plett
	 */
	public String getExito() {
		return exito;
	}

	/**
	 * Permite ingresar un mensaje de axito personalizado,
	 * este se mostrara automaticamente al usuario.
	 * @param exito
	 * @author Marco Antonio Nieto Plett
	 */
	public void setExito(String exito) {
		this.exito = exito;
	}
	
	


}
