package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class Proceso extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8697307821852847040L;
	
	private Long clave;
	private String desInicial;
	private String desIntermedio;
	private String desFinal;
	
	/**
	 * Este atributo se agrego para poder almacenar la relacion entre el proceso y el sujeto
	 * obligado dueno de este proceso.
	 */
	private SujetoObligado sujetoObligado;
	/**
	 * @return the clave
	 */
	public Long getClave() {
		return clave;
	}
	/**
	 * @param clave the clave to set
	 */
	public void setClave(Long clave) {
		this.clave = clave;
	}
	/**
	 * @return the desInicial
	 */
	public String getDesInicial() {
		return desInicial;
	}
	/**
	 * @param desInicial the desInicial to set
	 */
	public void setDesInicial(String desInicial) {
		this.desInicial = desInicial;
	}
	/**
	 * @return the desIntermedio
	 */
	public String getDesIntermedio() {
		return desIntermedio;
	}
	/**
	 * @param desIntermedio the desIntermedio to set
	 */
	public void setDesIntermedio(String desIntermedio) {
		this.desIntermedio = desIntermedio;
	}
	/**
	 * @return the desFinal
	 */
	public String getDesFinal() {
		return desFinal;
	}
	/**
	 * @param desFinal the desFinal to set
	 */
	public void setDesFinal(String desFinal) {
		this.desFinal = desFinal;
	}
	/**
	 * @return the sujetoObligado
	 */
	public SujetoObligado getSujetoObligado() {
		return sujetoObligado;
	}
	/**
	 * @param sujetoObligado the sujetoObligado to set
	 */
	public void setSujetoObligado(SujetoObligado sujetoObligado) {
		this.sujetoObligado = sujetoObligado;
	}
	
	
	
}
