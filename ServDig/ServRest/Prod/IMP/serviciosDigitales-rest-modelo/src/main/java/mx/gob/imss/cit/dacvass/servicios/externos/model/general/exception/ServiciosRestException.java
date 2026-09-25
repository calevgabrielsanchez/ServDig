package mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception;

import java.io.Serializable;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.ErrorResponseBean;

public class ServiciosRestException  extends Exception implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -638071695629564491L;
	
	private ErrorResponseBean errorBean;

	public ErrorResponseBean getErrorBean() {
		return errorBean;
	}

	public void setErrorBean(ErrorResponseBean errorBean) {
		this.errorBean = errorBean;
	}
	
	public ServiciosRestException(ErrorResponseBean errorBean) {
		this.errorBean = errorBean;
	}
	public ServiciosRestException(ErrorResponseBean errorBean, Exception e) {
		super(e);
		this.errorBean = errorBean;
		
	}
	
	 
	
	
	

}
