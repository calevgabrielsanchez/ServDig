/**
 * 
 */
package mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * 
 */
@XmlRootElement
public class PatronListaBlancaResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	private String fechaRegistroAlta;

	private boolean patronListaBlanca;	
	private String codigoRespuesta;
	private String detalleRespuesta;
	public boolean isPatronListaBlanca() {
		return patronListaBlanca;
	}
	public void setPatronListaBlanca(boolean patronListaBlanca) {
		this.patronListaBlanca = patronListaBlanca;
	}
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
	public String getFechaRegistroAlta() {
		return fechaRegistroAlta;
	}
	public void setFechaRegistroAlta(String fechaRegistroAlta) {
		this.fechaRegistroAlta = fechaRegistroAlta;
	}
	
	
	
}
