package mx.gob.imss.ctirss.delta.exception.beneficio;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class PersonaSinBeneficiosException extends AbstractException {

	private static final long serialVersionUID = 1L;

	private static String mensaje = new String(
			"Persona sin beneficios asociados");
	private static Integer codigo = new Integer(6789);

	public PersonaSinBeneficiosException() {
		super(mensaje, codigo);
	}
	
	public PersonaSinBeneficiosException(String msg) {
		super(msg, codigo);
	}
}
