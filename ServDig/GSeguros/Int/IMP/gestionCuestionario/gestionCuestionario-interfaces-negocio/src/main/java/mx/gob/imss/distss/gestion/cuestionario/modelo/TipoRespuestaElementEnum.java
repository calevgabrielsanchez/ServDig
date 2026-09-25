/**
 * 
 */
package mx.gob.imss.distss.gestion.cuestionario.modelo;

/**
 * @author vanderluk
 * 
 */
public enum TipoRespuestaElementEnum {

	BOOLEANA("checkbox", 1), MULTIPLE_BOOLEANA("radio", 2), 
	MULTIPLE_PLURAL("checkbox", 3), SELECCION("select", 4);

	private TipoRespuestaElementEnum(String elemento, int clave) {
		this.elemento = elemento;
		this.clave = clave;
	}

	private String elemento;

	private int clave;

	/**
	 * @return the elemento
	 */
	public String getElemento() {
		return elemento;
	}

	/**
	 * @return the clave
	 */
	public int getClave() {
		return clave;
	}

}
