package mx.gob.imss.ctirss.delta.model.dto;


import java.io.Serializable;
import java.util.Date;

public class ObtCifrasSolicitudProceso implements Serializable {
	
	/**
	 * id del obejto al viajar, me va indicar el estado que se queda mi objeto
	 */

	private static final long serialVersionUID = 1L;
	private String tipoSolicitud;
	private String Folio;
	private Date Fecha;
	
	
	public ObtCifrasSolicitudProceso(String tipoSolicitud, String folio,
			Date fecha) {
		super();
		this.tipoSolicitud = tipoSolicitud;
		Folio = folio;
		Fecha = fecha;		
	}
	
	public String getTipoSolicitud() {
		return tipoSolicitud;
	}
	public void setTipoSolicitud(String tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}
	public String getFolio() {
		return Folio;
	}
	public void setFolio(String folio) {
		Folio = folio;
	}
	public Date getFecha() {
		return Fecha;
	}
	public void setFecha(Date fecha) {
		Fecha = fecha;
	}

	

}
