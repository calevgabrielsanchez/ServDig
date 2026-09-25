package mx.gob.imss.ctirss.delta.model.gestion.nss;

import java.io.Serializable;

public class MovAclaracionNss implements Serializable{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2481547288588825249L;
	
	
	private Long idTipoNssAclaracion;
	private String aclaracion;
	public Long getIdTipoNssAclaracion() {
		return idTipoNssAclaracion;
	}
	public void setIdTipoNssAclaracion(Long idTipoNssAclaracion) {
		this.idTipoNssAclaracion = idTipoNssAclaracion;
	}
	public String getAclaracion() {
		return aclaracion;
	}
	public void setAclaracion(String aclaracion) {
		this.aclaracion = aclaracion;
	}
	
	
	
 

}
