package mx.gob.imss.ctirss.delta.exception.gestion.patronal;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class RepresentanteLegalYaExisteException extends AbstractException {
	
	private static final long serialVersionUID = 1L;
	private static final String situacion = "Ya existe un Representante Legal con dicha informacion";
	private static final Integer codigo = new Integer (110);
	
	public RepresentanteLegalYaExisteException(){
		super(situacion , codigo);
	}
	
	public RepresentanteLegalYaExisteException(String message){
		super(situacion , codigo);
	}

}
