package mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron;

import java.io.Serializable;

public class DatosGeneralesPatronPermisoCovid extends DatosGeneralesPatron implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1233205501532993860L;

	
	private DatosEmpresaPermisoConvid datosEmpresaPermisoConvid;

	public DatosEmpresaPermisoConvid getDatosEmpresaPermisoConvid() {
		return datosEmpresaPermisoConvid;
	}

	public void setDatosEmpresaPermisoConvid(DatosEmpresaPermisoConvid datosEmpresaPermisoConvid) {
		this.datosEmpresaPermisoConvid = datosEmpresaPermisoConvid;
	}
	

}
