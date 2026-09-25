package mx.gob.imss.ctirss.delta.model.asegurado.cda;

import java.io.Serializable;
import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class TipoNSSAclaracion extends AbstractModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idTipoNSSAclaracion;
	private String desTipoNSSAclaracion;
	
	
	
	public Integer getIdTipoNSSAclaracion() {
		return idTipoNSSAclaracion;
	}
	public void setIdTipoNSSAclaracion(Integer idTipoNSSAclaracion) {
		this.idTipoNSSAclaracion = idTipoNSSAclaracion;
	}
	public String getDesTipoNSSAclaracion() {
		return desTipoNSSAclaracion;
	}
	public void setDesTipoNSSAclaracion(String desTipoNSSAclaracion) {
		this.desTipoNSSAclaracion = desTipoNSSAclaracion;
	}
	
	

}
