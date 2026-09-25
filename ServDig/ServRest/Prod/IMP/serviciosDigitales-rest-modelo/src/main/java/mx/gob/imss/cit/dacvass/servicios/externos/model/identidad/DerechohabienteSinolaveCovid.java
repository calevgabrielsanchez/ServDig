package mx.gob.imss.cit.dacvass.servicios.externos.model.identidad;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.DatosEmpresaPermisoConvid;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.DatosGeneralesPatron;

@XmlRootElement
public class DerechohabienteSinolaveCovid extends DerechohabienteSinolave implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6420258316806074752L;

	
	private DatosGeneralesPatron DatosGeneralesPatron;
	private DatosEmpresaPermisoConvid datosEmpresaPermisoConvid;
	
	
	public DatosGeneralesPatron getDatosGeneralesPatron() {
		return DatosGeneralesPatron;
	}
	public void setDatosGeneralesPatron(DatosGeneralesPatron datosGeneralesPatron) {
		DatosGeneralesPatron = datosGeneralesPatron;
	}
	public DatosEmpresaPermisoConvid getDatosEmpresaPermisoConvid() {
		return datosEmpresaPermisoConvid;
	}
	public void setDatosEmpresaPermisoConvid(DatosEmpresaPermisoConvid datosEmpresaPermisoConvid) {
		this.datosEmpresaPermisoConvid = datosEmpresaPermisoConvid;
	}

	
		
		
	

}