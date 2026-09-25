package mx.gob.imss.ctirss.delta.exception.usuario;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class ActualizaUsuarioEsquemaSeguridadException  extends AbstractException {
	
	
	
	/**
	 * 
	 */
private static final long serialVersionUID = 2198485443488496431L;

private static final Integer codigo = new Integer(0);
	
	private static final String situacion = new String("Ocurrio un error al realizar la actualizacion en el esquema de seguridad");
	
	
	public ActualizaUsuarioEsquemaSeguridadException(){
		super(situacion , codigo);
	}
	
	/**
	 * Recibe la situacion que origino el problema
	 * @param situacion
	 */
	public ActualizaUsuarioEsquemaSeguridadException(String situacion){
		super(situacion , codigo);
	}

}
