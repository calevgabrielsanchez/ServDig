package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siap;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "NewDataSet")
@XmlType(name = "NewDataSet", propOrder = {
	    "empleadoSiap"
	})
public class RepuestaServicioSiap implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6821403679143592205L;
	@XmlElement(name = "qry")
	private EmpleadoSiap empleadoSiap;
	
	
	public RepuestaServicioSiap() {
	}
	
	public RepuestaServicioSiap(EmpleadoSiap empleadoSiap) {
		super();
		this.empleadoSiap = empleadoSiap;
	}
	
	public EmpleadoSiap getEmpleadoSiap() {
		return empleadoSiap;
	}
	public void setQry(EmpleadoSiap empleadoSiap) {
		this.empleadoSiap = empleadoSiap;
	}
	
	

}
