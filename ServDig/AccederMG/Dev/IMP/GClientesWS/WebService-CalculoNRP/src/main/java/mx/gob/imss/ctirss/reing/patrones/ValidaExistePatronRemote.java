/**
 * 
 */
package mx.gob.imss.ctirss.reing.patrones;

import javax.ejb.Remote;

/**
 * @author jonathan sanchez montiel
 *
 */
@Remote
public interface ValidaExistePatronRemote {
	
	/**
	 * Metodo para validar si existe un patron  
	 * si regresa true el patron ya existe en reing
	 * @param nombreAlta
	 * @param apPaternoAlta
	 * @param apMaternoAlta
	 * @param descSociedadAlta
	 * @param municipioImssAlta
	 * @param tipoPersonaAlta
	 * @param divisionAlta
	 * @param grupoAlta
	 * @param fraccionAlta
	 * @param modalidadAlta
	 * @param clase
	 * @param marcaClaseAlta
	 * @param rfcAlta
	 * @return
	 */
	boolean validaExistePatron(String nombreAlta, String apPaternoAlta,
			String apMaternoAlta, String descSociedadAlta,
			String municipioImssAlta, int tipoPersonaAlta, int divisionAlta,
			int grupoAlta, int fraccionAlta, int modalidadAlta, int clase,
			boolean marcaClaseAlta, String rfcAlta);

}
