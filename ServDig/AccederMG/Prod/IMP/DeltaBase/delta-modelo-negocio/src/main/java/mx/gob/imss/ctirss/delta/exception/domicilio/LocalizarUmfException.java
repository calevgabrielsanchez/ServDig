package mx.gob.imss.ctirss.delta.exception.domicilio;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class LocalizarUmfException extends AbstractException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 445771302748947890L;
	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String(
			"UMF no  localizada");
	
	
	/**
	 * Constructor por Default
	 */
	
	public LocalizarUmfException() {
		super(situacion, codigo);
		
	}


	public LocalizarUmfException(String message) {
		super(message);
		
	}
	
	

}
