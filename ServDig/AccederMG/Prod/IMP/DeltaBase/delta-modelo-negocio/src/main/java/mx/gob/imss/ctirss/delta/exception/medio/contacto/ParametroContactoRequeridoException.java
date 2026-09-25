package mx.gob.imss.ctirss.delta.exception.medio.contacto;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * 
 * @author Hugo Martinez
 *
 */
public class ParametroContactoRequeridoException extends AbstractException  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Integer codigo = new Integer(10000);
	
	private static final String mensaje = new String("No se ha proporcionado un parámetro requerido para " +
			"consultar/almacenar el dato de contacto. Verifique si ha proporcionado el Tipo de Persona y/o " +
			"identificador del propietario.");
	
	
	public ParametroContactoRequeridoException(){
		
		super(mensaje, codigo);
		
	}

	/**
	 * 
	 * @param codigo
	 * @param mensaje
	 */
	public ParametroContactoRequeridoException(Integer codigo, String mensaje){
		super(mensaje, codigo);
	}
}
