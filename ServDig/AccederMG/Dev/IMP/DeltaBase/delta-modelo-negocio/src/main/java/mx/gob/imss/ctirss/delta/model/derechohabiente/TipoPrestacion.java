package mx.gob.imss.ctirss.delta.model.derechohabiente;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class TipoPrestacion extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8632124754477593886L;
	
	private Long idTipoPrestacion;
	private String descTipoPrestacion;
	
	public Long getIdTipoPrestacion() {
		return idTipoPrestacion;
	}
	
	public void setIdTipoPrestacion(Long idTipoPrestacion) {
		this.idTipoPrestacion = idTipoPrestacion;
	}
	
	public String getDescTipoPrestacion() {
		return descTipoPrestacion;
	}
	
	public void setDescTipoPrestacion(String descTipoPrestacion) {
		this.descTipoPrestacion = descTipoPrestacion;
	}
}