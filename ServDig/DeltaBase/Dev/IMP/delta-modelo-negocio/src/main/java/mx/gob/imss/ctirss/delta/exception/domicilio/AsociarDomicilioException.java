package mx.gob.imss.ctirss.delta.exception.domicilio;

import javax.ejb.ApplicationException;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

@ApplicationException(rollback = true)
public class AsociarDomicilioException extends AbstractException {

	private static final long serialVersionUID = 1L;

	private static final Integer codigo = new Integer(0);

	private static final String situacion = new String(
			"No se puede guardar la relación domicilio - persona ya que no se cuenta con las claves de ambas entidades");

	public AsociarDomicilioException() {
		super(situacion, codigo);
	}

	/**
	 * 
	 * @param situacion
	 */
	public AsociarDomicilioException(String situacion) {
		super(situacion, codigo);
	}

}
