package mx.gob.imss.ctirss.delta.exception.gestion.asegurado;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class WsAntecedentesAseguradoExcpetion extends AbstractException{

	private static final long serialVersionUID = -3288570647623273063L;
	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("No se pudo consultar la informacion en WS de antecedentes - paso al - cambio al.");
	
	/**
	 * Constructor por omision
	 */
	public WsAntecedentesAseguradoExcpetion(){
		super(situacion , codigo);
	}
	
	public WsAntecedentesAseguradoExcpetion(String mensaje) {
		super(mensaje);
	}
	
	public WsAntecedentesAseguradoExcpetion(String mensaje, Integer codigo) {
		super(mensaje,codigo);
	}
}
