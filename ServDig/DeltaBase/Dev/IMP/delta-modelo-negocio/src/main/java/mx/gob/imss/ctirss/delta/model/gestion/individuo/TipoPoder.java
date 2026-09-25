package mx.gob.imss.ctirss.delta.model.gestion.individuo;

import java.io.Serializable;

public class TipoPoder implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4115481909886627700L;
	private Integer idTipoPoder;
	private String desTipoPoder;
	
	public Integer getIdTipoPoder() {
		return idTipoPoder;
	}
	public void setIdTipoPoder(Integer idTipoPoder) {
		this.idTipoPoder = idTipoPoder;
	}
	public String getDesTipoPoder() {
		return desTipoPoder;
	}
	public void setDesTipoPoder(String desTipoPoder) {
		this.desTipoPoder = desTipoPoder;
	}
	
}
