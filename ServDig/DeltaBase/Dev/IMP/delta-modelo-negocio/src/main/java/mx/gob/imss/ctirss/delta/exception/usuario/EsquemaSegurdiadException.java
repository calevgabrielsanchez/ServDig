package mx.gob.imss.ctirss.delta.exception.usuario;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class EsquemaSegurdiadException  extends AbstractException {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6352254770067229513L;


	private static final Integer codigo = new Integer(0);
	
	private static final String situacion = new String("Ocurrio un error al realizar las consultas en el esquema de seguridad ");
	
	
	public EsquemaSegurdiadException(){
		super(situacion , codigo);
	}
	
	/**
	 * Recibe la situacion que origino el problema
	 * @param situacion
	 */
	public EsquemaSegurdiadException(String situacion){
		super(situacion , codigo);
	}

}
