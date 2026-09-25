/**
 * Permite controlar la nevegaci�n de los tabs en pantalla 
 * as� como sus mensajes de exito y/o error.
 * 
 * @author Marco Antonio Nieto Plett
 */

package mx.imss.ctirss.framework.utils;


/**
 * Clase que debe ser extendida por el objeto visual (VO)
 * que controle a los tabs.
 * @author Marco A. Nieto Plett
 * @version 1.0.0
 */
public class ControlTabs {

	/**
	 * Indica desde donde viene la petici�n
	 */
	private int seccionPeticion;
	
	/**
	 * Indica si el tipo de guardado es parcial o final
	 */
	private Integer tipoGuardado;
	
	/**
	 * En caso de que exista un error en el flujo
	 * esta variable le indicar� al usuario
	 * autom�ticamente
	 */
	private String error;
	
	/**
	 * Mensaje de �xito personalizado,
	 * en caso de que no se utilice el 
	 * sistema utilizar� el default.
	 * @author Marco Antonio Nieto Plett
	 */
	private String exito;
	
	/**
	 * Nos permie saber desde donde (TAB) proviene la
	 * petici�n solicitada.
	 * @see ConstantesSeguimiento
	 * @return Secci�n
	 * @author Marco Antonio Nieto Plett
	 */
	public int getSeccionPeticion() {
		return seccionPeticion;
	}

	/**
	 * Permite ingresar la seccion desde donde proviene
	 * la petici�n solicitada.
	 * @param seccionPeticion
	 * @see ConstantesSeguimiento
	 * @author Marco Antonio Nieto Plett
	 */
	public void setSeccionPeticion(int seccionPeticion) {
		this.seccionPeticion = seccionPeticion;
	}

	/**
	 * Obtiene si el tipo de petici�n es de guardado
	 * parcial o final.
	 * 
	 * 1.- Parcial
	 * 2.- Final
	 * 
	 * En caso de que retorne un valor diferente de 1 y 2
	 * se deber� omitir esta variable.
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
	 * y este se mostrar� de fomra autom�tica al usuario
	 * @return error
	 * @author Marco Antonio Nieto Plett
	 */
	public String getError() {
		return error;
	}

	/**
	 * Permite indicar un error personalizado y se
	 * mostrar� de fomra autom�tica al usuario
	 * @param error
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * Permite obtener un mensaje de �xito personalizado,
	 * este se mostrar� al usuario de forma autom�tica.
	 * En caso de que no se utilice el sistema cuenta
	 * con un mensaje por defecto y gen�rico.
	 * @return
	 * @author Marco Antonio Nieto Plett
	 */
	public String getExito() {
		return exito;
	}

	/**
	 * Permite ingresar un mensaje de �xito personalizado,
	 * este se mostrar� autom�ticamente al usuario.
	 * @param exito
	 * @author Marco Antonio Nieto Plett
	 */
	public void setExito(String exito) {
		this.exito = exito;
	}
	
	


}
