package mx.gob.imss.ctirss.delta.exception.gestion.asegurado;

import javax.ejb.ApplicationException;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

@ApplicationException(rollback=true)
public class BovedaCDAException extends AbstractException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("No se proces\u00f3 correctamente la solicitud...");
	private String mensajeError;
     
	public String getMensajeError() {
		return mensajeError;
	}
	
	public Integer getCodigo() {
		return codigo;
	}

	/**
	 * Constructor por omision
	 */
	public BovedaCDAException(){
		super(situacion, codigo);
	}
	
	/**
	 * Constructor que recibe un mensaje de error
	 * @param situacion
	 */
	public BovedaCDAException(String situacion){
		super(situacion, codigo);
	}
	
	
	public BovedaCDAException(String situacion, String mensajeError){
		super(situacion, codigo);
		this.mensajeError = mensajeError;
		
	}
}
