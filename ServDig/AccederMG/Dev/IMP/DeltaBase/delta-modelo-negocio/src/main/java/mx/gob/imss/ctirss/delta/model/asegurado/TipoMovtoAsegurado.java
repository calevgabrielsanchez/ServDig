/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.asegurado;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * @author JUAN MANUEL MÁRQUEZ
 *
 */
public class TipoMovtoAsegurado extends AbstractModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected long idTipoMvtoAsegurado;
	protected String desTipoMvtoAsegurado;
	
	/**
	 * @return the idTipoMvtoAsegurado
	 */
	public long getIdTipoMvtoAsegurado() {
		return idTipoMvtoAsegurado;
	}
	/**
	 * @param idTipoMvtoAsegurado the idTipoMvtoAsegurado to set
	 */
	public void setIdTipoMvtoAsegurado(long idTipoMvtoAsegurado) {
		this.idTipoMvtoAsegurado = idTipoMvtoAsegurado;
	}
	/**
	 * @return the desTipoMvtoAsegurado
	 */
	public String getDesTipoMvtoAsegurado() {
		return desTipoMvtoAsegurado;
	}
	/**
	 * @param desTipoMvtoAsegurado the desTipoMvtoAsegurado to set
	 */
	public void setDesTipoMvtoAsegurado(String desTipoMvtoAsegurado) {
		this.desTipoMvtoAsegurado = desTipoMvtoAsegurado;
	}
	
}
