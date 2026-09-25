package mx.gob.imss.ctirss.delta.exception.gestion.asegurado;

import javax.ejb.ApplicationException;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

@ApplicationException(rollback=true)
public class SujetoObligadoInexistente extends AbstractException {
	private static final long serialVersionUID = -8464354869306167610L;
	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("No se localizo a ningun Sujeto Obligado con los datos proporcionados.");

	public SujetoObligadoInexistente() {
		super(situacion, codigo);
	}
}
