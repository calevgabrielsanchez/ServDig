package mx.gob.imss.ctirss.delta.derechohabientes.web.bean.dto;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class BusquedaDelegacion extends AbstractModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idDelegacion;
	private Long idEstado;
	private Long idMunicipio;
	private Long idAsentamiento;
	
	public Long getIdDelegacion() {
		return idDelegacion;
	}
	public void setIdDelegacion(Long idDelegacion) {
		this.idDelegacion = idDelegacion;
	}
	public Long getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(Long idEstado) {
		this.idEstado = idEstado;
	}
	public Long getIdMunicipio() {
		return idMunicipio;
	}
	public void setIdMunicipio(Long idMunicipio) {
		this.idMunicipio = idMunicipio;
	}
	public Long getIdAsentamiento() {
		return idAsentamiento;
	}
	public void setIdAsentamiento(Long idAsentamiento) {
		this.idAsentamiento = idAsentamiento;
	}
	
	
}
