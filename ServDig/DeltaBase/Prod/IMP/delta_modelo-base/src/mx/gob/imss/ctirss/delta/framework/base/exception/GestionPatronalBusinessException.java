package mx.gob.imss.ctirss.delta.framework.base.exception;

public class GestionPatronalBusinessException extends AbstractException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * mensaje por default en caso de no resolverse la propiedad del detalle de error en messages.properties
	 */
	private static final String situacion = "Error a nivel de negocio, verifique bit-cora para mas detalles.";
	private static final Integer codigo = new Integer (501);
	private int code;
	
	public GestionPatronalBusinessException(){
		super(situacion , codigo);
	}
	
	public GestionPatronalBusinessException(String message){
		super(message);
	}
	
	public GestionPatronalBusinessException(String message, int code){
		super(message, code);
	}

	public Integer getCode() {
		return this.code;
	}

}
