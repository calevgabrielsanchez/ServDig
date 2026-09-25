/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.gestion.asegurado;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

/**
 * @author Lucio Duran Silva
 *
 */
public class GenerarNSSException extends AbstractException {

	
	
	private static final long serialVersionUID = -3288570647623273063L;
	private static final Integer CODIGO = new Integer(0);
	private static final String SITUACION_BASE = new String("Ocurrió un error al generar el NSS: ");
	
	private Fisica fisica;
	
	/**
	 * Constructor por omision
	 */
	public GenerarNSSException () {
		super(SITUACION_BASE, CODIGO);
	}
	
	public GenerarNSSException(String situacion){
		super(situacion , CODIGO);
	}
	
	public GenerarNSSException (String situacion, Fisica fisica) {
		super(situacion , CODIGO);
		this.fisica = fisica;
	}

	public Fisica getFisica() {
		return fisica;
	}

	public void setFisica(Fisica fisica) {
		this.fisica = fisica;
	}
}
