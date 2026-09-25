package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class CumplimientOutput implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String estatusObra;
	private String fechaRegistroInc;
	
	
	public String getEstatusObra() {
		return estatusObra;
	}
	public void setEstatusObra(String estatusObra) {
		this.estatusObra = estatusObra;
	}
	public String getFechaRegistroInc() {
		return fechaRegistroInc;
	}
	public void setFechaRegistroInc(String fechaRegistroInc) {
		this.fechaRegistroInc = fechaRegistroInc;
	}
	
	
}
