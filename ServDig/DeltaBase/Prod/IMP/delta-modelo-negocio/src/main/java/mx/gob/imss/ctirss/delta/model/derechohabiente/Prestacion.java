package mx.gob.imss.ctirss.delta.model.derechohabiente;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;


public class Prestacion extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3169512537384671981L;
	private Long idPrestacion;
	private String nomPrestacion;
	private Long indPensionado;
	
	public Long getIdPrestacion() {
		return idPrestacion;
	}
	
	public void setIdPrestacion(Long idPrestacion) {
		this.idPrestacion = idPrestacion;
	}
	
	public String getNomPrestacion() {
		return nomPrestacion;
	}
	
	public void setNomPrestacion(String nomPrestacion) {
		this.nomPrestacion = nomPrestacion;
	}

	public Long getIndPensionado() {
		return indPensionado;
	}

	public void setIndPensionado(Long indPensionado) {
		this.indPensionado = indPensionado;
	}
	
}