package mx.gob.imss.ctirss.delta.exception.usuario;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class UsuarioNoRegistradoEnEsquemaDeSeguridadException extends AbstractException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6012095928117170336L;

	private static final Integer codigo = new Integer(0);
	
	private static final String situacion = new String("El usuario no pudo registarse en el esquema de seguridad");
	
	
	public UsuarioNoRegistradoEnEsquemaDeSeguridadException(){
		super(situacion , codigo);
	}
	
	/**
	 * Recibe la situacion que origino el problema
	 * @param situacion
	 */
	public UsuarioNoRegistradoEnEsquemaDeSeguridadException(String situacion){
		super(situacion , codigo);
	}

}
