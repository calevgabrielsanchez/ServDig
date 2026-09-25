package mx.gob.imss.cit.dacvass.servicios.externos.model.identidad;

import java.io.Serializable;
import java.util.List;

import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.DatosGeneralesPatronPermisoCovid;

public class AseguradoVigentePermisoCovid  extends DerechohabienteSinolave implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3096095990912199297L;

	List<DatosGeneralesPatronPermisoCovid> lstDatosGeneralesPatron;

	public List<DatosGeneralesPatronPermisoCovid> getLstDatosGeneralesPatron() {
		return lstDatosGeneralesPatron;
	}

	public void setLstDatosGeneralesPatron(List<DatosGeneralesPatronPermisoCovid> lstDatosGeneralesPatron) {
		this.lstDatosGeneralesPatron = lstDatosGeneralesPatron;
	}
	
	
}
