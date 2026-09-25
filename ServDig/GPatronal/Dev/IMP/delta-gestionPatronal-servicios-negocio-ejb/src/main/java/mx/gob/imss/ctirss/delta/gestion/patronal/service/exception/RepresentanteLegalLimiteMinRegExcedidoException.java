package mx.gob.imss.ctirss.delta.gestion.patronal.service.exception;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class RepresentanteLegalLimiteMinRegExcedidoException extends
		AbstractException {
	
	private static final long serialVersionUID = 1L;
	private static final String situacion = "Se debe contar al menos con un registro de Representante Legal";
	private static final Integer codigo = new Integer (110);
	
	public RepresentanteLegalLimiteMinRegExcedidoException(){
		super(situacion , codigo);
	}
	
	public RepresentanteLegalLimiteMinRegExcedidoException(String message){
		super(situacion , codigo);
	}

}
