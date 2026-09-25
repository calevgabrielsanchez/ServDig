package mx.gob.imss.ctirss.delta.gestion.patronal.service.exception;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class RepresentanteLegalInvalidoException extends AbstractException{

	private static final long serialVersionUID = 1L;
	private static final String situacion = "El producto o servicio es inv\u00E1lido";
	private static final Integer codigo = new Integer (110);
	
	public RepresentanteLegalInvalidoException(){
		super(situacion , codigo);
	}
	
	public RepresentanteLegalInvalidoException(String message){
		super(situacion , codigo);
	}

}
