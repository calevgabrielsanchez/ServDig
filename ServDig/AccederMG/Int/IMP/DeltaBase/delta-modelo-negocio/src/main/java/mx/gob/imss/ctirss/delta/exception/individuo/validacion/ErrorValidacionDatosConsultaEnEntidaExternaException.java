package mx.gob.imss.ctirss.delta.exception.individuo.validacion;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author 191807
 * @since 12/12/2012
 * @version 1.0
 * 
 */
public class ErrorValidacionDatosConsultaEnEntidaExternaException extends AbstractException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String situacion = "Error de validaci\u00f3n en datos de consulta en entidades externas";
	private static final String error = "Error de validaci\u00f3n: ";
	private static final Integer codigo = new Integer(10008000);

    public ErrorValidacionDatosConsultaEnEntidaExternaException() {
    	super(situacion, codigo);
    }

    public ErrorValidacionDatosConsultaEnEntidaExternaException(String causa) {
    	super(error + causa, codigo);
    }
    
}
