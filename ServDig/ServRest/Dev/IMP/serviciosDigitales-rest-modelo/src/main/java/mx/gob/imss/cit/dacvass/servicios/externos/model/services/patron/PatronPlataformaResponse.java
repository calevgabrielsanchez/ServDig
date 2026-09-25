package mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PatronPlataformaResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4256790156121109519L;
	
	private boolean patronPlataforma;
	private String fechaRegistroAlta;
	
	private String codigoRespuesta;
	private String detalleRespuesta;
	
	public String getCodigoRespuesta() {
		return codigoRespuesta;
	}
	public void setCodigoRespuesta(String codigoRespuesta) {
		this.codigoRespuesta = codigoRespuesta;
	}
	public String getDetalleRespuesta() {
		return detalleRespuesta;
	}
	public void setDetalleRespuesta(String detalleRespuesta) {
		this.detalleRespuesta = detalleRespuesta;
	}
	
	public boolean isPatronPlataforma() {
		return patronPlataforma;
	}
	public void setPatronPlataforma(boolean patronPlataforma) {
		this.patronPlataforma = patronPlataforma;
	}
	public String getFechaRegistroAlta() {
		return fechaRegistroAlta;
	}
	public void setFechaRegistroAlta(String fechaRegistroAlta) {
		this.fechaRegistroAlta = fechaRegistroAlta;
	}
	
	

}
