package mx.gob.imss.ctirss.delta.global.model;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class TipoTramiteTO extends AbstractModel {
	
	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 6772129885081698630L;
	private Integer idTipoTramite;
    private String descripcion;
	
    public Integer getIdTipoTramite() {
		return idTipoTramite;
	}
	public void setIdTipoTramite(Integer idTipoTramite) {
		this.idTipoTramite = idTipoTramite;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
    
}
