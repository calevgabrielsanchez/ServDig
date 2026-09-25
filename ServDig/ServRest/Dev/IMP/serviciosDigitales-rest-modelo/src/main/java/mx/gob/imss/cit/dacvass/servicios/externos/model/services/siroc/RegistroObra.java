package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RegistroObra extends UbicacionObra implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8717651929674727394L;
	
	private String numRegistroObra;
	private Date fechaRegistro;
	
	
	public String getNumRegistroObra() {
		return numRegistroObra;
	}
	public void setNumRegistroObra(String numRegistroObra) {
		this.numRegistroObra = numRegistroObra;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	

}
