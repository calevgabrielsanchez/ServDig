package mx.gob.imss.ctirss.delta.exception.clasificacion;

import javax.ejb.ApplicationException;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author Lucio Duran Silva
 *
 */
@ApplicationException(rollback=true)
public class PatronNoEncontradoException extends AbstractException {
	
	
	private static final long serialVersionUID = -4790352691129504847L;
	
	private static final String situacion = "NRP con formato incorrecto.";
	
	private static final Integer codigo = new Integer (110);
	
	
	public PatronNoEncontradoException(){
		super(situacion , codigo);
	}

}
