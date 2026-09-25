package mx.gob.imss.ctirss.delta.model.gestion.individuo;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;


/**
 * 140912
 * @author ICCSRG
 *
 */
public class TipoIdentificador extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4672524475835944865L;
	
	private long idTipoIdentificador;
	private String desIdentificador;
	
	
	/**
	 * @return the idTipoIdentificador
	 */
	public long getIdTipoIdentificador() {
		return idTipoIdentificador;
	}
	
	/**
	 * @param idTipoIdentificador the idTipoIdentificador to set
	 */
	public void setIdTipoIdentificador(long idTipoIdentificador) {
		this.idTipoIdentificador = idTipoIdentificador;
	}
	
	/**
	 * @return the desIdentificador
	 */
	public String getDesIdentificador() {
		return desIdentificador;
	}
	
	/**
	 * @param desIdentificador the desIdentificador to set
	 */
	public void setDesIdentificador(String desIdentificador) {
		this.desIdentificador = desIdentificador;
	}

}