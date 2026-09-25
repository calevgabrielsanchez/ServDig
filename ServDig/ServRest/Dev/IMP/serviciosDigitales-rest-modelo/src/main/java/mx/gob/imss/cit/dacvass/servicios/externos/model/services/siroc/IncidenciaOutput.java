package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class IncidenciaOutput implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String tipoIncidencia;
	private String   fechaIncidencia;
	
	public String getTipoIncidencia() {
		return tipoIncidencia;
	}
	public void setTipoIncidencia(String tipoIncidencia) {
		this.tipoIncidencia = tipoIncidencia;
	}
	public String getFechaIncidencia() {
		return fechaIncidencia;
	}
	public void setFechaIncidencia(String fechaIncidencia) {
		this.fechaIncidencia = fechaIncidencia;
	}
	
	
}
