package mx.gob.imss.ctirss.correccion.model;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

public class ErrorValidation extends AbstractModel {
	private static final long serialVersionUID = 1L;
	private String error;
	private boolean isError;
	
	public ErrorValidation() { }
	
	public ErrorValidation(String error, boolean isError) {
		super();
		this.error = error;
		this.isError = isError;
	}

	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public boolean isError() {
		return isError;
	}
	public void setError(boolean isError) {
		this.isError = isError;
	}
}
