/**
 * 
 */
package mx.imss.ctirss.session;

/**
 * @author vaguirre
 * 
 */
public enum TipoCertificado {
	SAT, IDSE;
	/**
	 * M�todo para recuperar el tipo del certificado en base a la cadena
	 * recibida desde IDSE.
	 * 
	 * @param tipoCerParam
	 * @return
	 */
	public static TipoCertificado get(String tipoCerParam) {
		if (tipoCerParam != null && tipoCerParam.equals("A")) {
			return SAT;
		}
		return IDSE;
	}
}
