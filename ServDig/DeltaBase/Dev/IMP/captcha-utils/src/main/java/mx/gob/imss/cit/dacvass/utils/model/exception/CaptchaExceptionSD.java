package mx.gob.imss.cit.dacvass.utils.model.exception;

import java.io.Serializable;

public class CaptchaExceptionSD  extends Exception implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -638071695629564491L;
	
	public CaptchaExceptionSD( Exception e) {
		super(e);
	}
	
	public CaptchaExceptionSD( ) {
		
	}
	
	public CaptchaExceptionSD( String error) {
		super(error);
	}
	
	public CaptchaExceptionSD( String error, Exception e) {
		super(error, e);
	}
	
}
