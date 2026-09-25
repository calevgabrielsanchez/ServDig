package mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RespuestaPensionesRest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5627992968878189988L;
	
	private Long codigoError;
	private String mensajeError;
	private List<ResolucionPension> resolucionPension;
	
	
	public Long getCodigoError() {
		return codigoError;
	}
	public void setCodigoError(Long codigoError) {
		this.codigoError = codigoError;
	}
	public String getMensajeError() {
		return mensajeError;
	}
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}
	public List<ResolucionPension> getResolucionPension() {
		return resolucionPension;
	}
	public void setResolucionPension(List<ResolucionPension> resolucionPension) {
		this.resolucionPension = resolucionPension;
	}
	

}
