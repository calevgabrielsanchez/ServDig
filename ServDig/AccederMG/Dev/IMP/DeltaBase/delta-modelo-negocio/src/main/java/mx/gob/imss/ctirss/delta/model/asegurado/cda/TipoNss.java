package mx.gob.imss.ctirss.delta.model.asegurado.cda;

import mx.gob.imss.ctirss.delta.model.asegurado.cda.BaseModel;

public class TipoNss extends BaseModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4729412384312916897L;
	private boolean certificador;
	private boolean asociado;
	private boolean corresOtraPersona;
	private boolean noExisteCanase;
  private String tipo;
	
	public boolean isCertificador() {
		return certificador;
	}
	public void setCertificador(boolean certificador) {
		this.certificador = certificador;
	}
	public boolean isAsociado() {
		return asociado;
	}
	public void setAsociado(boolean asociado) {
		this.asociado = asociado;
	}
	public boolean isCorresOtraPersona() {
		return corresOtraPersona;
	}
	public void setCorresOtraPersona(boolean corresOtraPersona) {
		this.corresOtraPersona = corresOtraPersona;
	}
	public boolean isNoExisteCanase() {
		return noExisteCanase;
	}
	public void setNoExisteCanase(boolean noExisteCanase) {
		this.noExisteCanase = noExisteCanase;
	}

  /**
   * @return the tipo
   */
  public String getTipo() {
    return tipo;
  }

  /**
   * @param tipo the tipo to set
   */
  public void setTipo(String tipo) {
    this.tipo = tipo;
  }
	
	
	

}
