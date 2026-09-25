package mx.gob.imss.ctirss.delta.model.asegurado.cda;

import java.io.Serializable;

public class TipoAclaracion implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7284732741544523920L;
	private boolean canceladoDup;
	private boolean homonimio;
	private boolean noExisteCanase;
	private boolean otroAsegurado;
	private boolean correccionNombre;
	private boolean correccionEstadis;
	private boolean cuentaIlogica;
	private boolean cuentaIndividual;
	private boolean blanqueoCurp;
	
	public boolean isBlanqueoCurp() {
		return blanqueoCurp;
	}
	public void setBlanqueoCurp(boolean blanqueoCurp) {
		this.blanqueoCurp = blanqueoCurp;
	}
	
	public boolean isCanceladoDup() {
		return canceladoDup;
	}
	public void setCanceladoDup(boolean canceladoDup) {
		this.canceladoDup = canceladoDup;
	}
	public boolean isHomonimio() {
		return homonimio;
	}
	public void setHomonimio(boolean homonimio) {
		this.homonimio = homonimio;
	}
	public boolean isNoExisteCanase() {
		return noExisteCanase;
	}
	public void setNoExisteCanase(boolean noExisteCanase) {
		this.noExisteCanase = noExisteCanase;
	}
	public boolean isOtroAsegurado() {
		return otroAsegurado;
	}
	public void setOtroAsegurado(boolean otroAsegurado) {
		this.otroAsegurado = otroAsegurado;
	}
	

	public boolean isCorreccionNombre() {
		return correccionNombre;
	}
	public void setCorreccionNombre(boolean correccionNombre) {
		this.correccionNombre = correccionNombre;
	}
	public boolean isCorreccionEstadis() {
		return correccionEstadis;
	}
	public void setCorreccionEstadis(boolean correccionEstadis) {
		this.correccionEstadis = correccionEstadis;
	}
	public boolean isCuentaIlogica() {
		return cuentaIlogica;
	}
	public void setCuentaIlogica(boolean cuentaIlogica) {
		this.cuentaIlogica = cuentaIlogica;
	}
	
	public boolean isCuentaIndividual() {
		return cuentaIndividual;
	}
	public void setCuentaIndividual(boolean cuentaIndividual) {
		this.cuentaIndividual = cuentaIndividual;
	}

}
