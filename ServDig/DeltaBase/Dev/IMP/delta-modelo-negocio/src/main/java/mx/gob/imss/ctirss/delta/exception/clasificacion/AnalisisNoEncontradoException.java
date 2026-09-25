/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.clasificacion;

import javax.ejb.ApplicationException;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author Joaquin Guevara
 *
 */
@ApplicationException(rollback=true)
public class AnalisisNoEncontradoException extends AbstractException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4790352691129504847L;
	
	private static String situacion = "field.clem.error.analisis";
	private static Integer codigo = 999; 

	public AnalisisNoEncontradoException() {
		super(situacion , codigo);
	}
	
	
	public AnalisisNoEncontradoException(String situacion, Integer codigo) {
		super(situacion , codigo);
	}

}
