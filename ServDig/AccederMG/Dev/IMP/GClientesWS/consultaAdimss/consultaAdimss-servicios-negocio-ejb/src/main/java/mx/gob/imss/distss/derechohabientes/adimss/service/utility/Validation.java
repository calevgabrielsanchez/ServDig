package mx.gob.imss.distss.derechohabientes.adimss.service.utility;

import java.io.IOException;
import java.net.URL;

public class Validation {

	/**
	 * Metodo que se encarga de validar que el campo no se encuentre vacio, y
	 * que el tamaño de la cadena no sea menor que el especificado
	 * 
	 * @param campo
	 *            String : cadena a comparar
	 * @param longitud
	 *            int : tamaño de la cadena
	 * @return
	 */
	public static boolean validarCampo(String campo, int longitud) {
		if (campo != null && !campo.equals("") && !campo.equals("null")
				&& !campo.equals("0") && campo.length() >= longitud) {
			return true;
		} else
			return false;
	}

	public static boolean validarCampo(String campo) {
		if (campo != null && !campo.equals("") && !campo.equals("null")
				&& !campo.equals("0") && !campo.equals("00")) {
			return true;
		} else
			return false;
	}

	/**
	 * Metodo encargado de verificar la conexion
	 * 
	 * @param wsdl
	 *            String
	 * @return true: Conexion correcta, false: Conexion erronea
	 */
	public static boolean checkWSDL(String wsdl) {
		try {
			URL url = new URL(wsdl);
			url.getContent();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

}
