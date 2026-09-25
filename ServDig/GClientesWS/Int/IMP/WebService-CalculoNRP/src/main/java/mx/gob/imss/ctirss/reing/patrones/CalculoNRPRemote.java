/**
 * 
 */
package mx.gob.imss.ctirss.reing.patrones;

import javax.ejb.Remote;

/**
 * @author Lucio Duran Silva
 *
 */
@Remote
public interface CalculoNRPRemote {
	
	/**
	 * Metodo para el calculo del NRP 
	 * @param cveMunicipio
	 * @param modalidad
	 * @return
	 */
	String generaNRP(String cveMunicipio, Long modalidad);
	
	/**
     * Este metodo genera el digito verificador de un String con NRP conformado partir de 3 posiciones municipio, 5 posiciones serie 2 modalidad
     * @param nrp El NRP correspondiente
     * @return El digito verificador para ese NRP
     */
	int generaDigitoVerificador(String nrp);
}
