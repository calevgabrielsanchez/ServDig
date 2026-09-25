package mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.cit.dacvass.servicios.externos.model.services.sat.Actividades;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.sat.Ubicacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;

@XmlRootElement
public class DatosSatDetallePatron  implements Serializable{

	private static final long serialVersionUID = -5618681734412569927L;
	
	private String nombreRazonSocial;
	private String rfc;
	private TipoPersona tipiPersona;
	private List<Actividades> actividad;
	private List<Ubicacion> ubicacion;
	private List <DetallePatronClasifMovPat> lstDetallePatron;
				 
	
	
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getNombreRazonSocial() {
		return nombreRazonSocial;
	}
	public void setNombreRazonSocial(String nombreRazonSocial) {
		this.nombreRazonSocial = nombreRazonSocial;
	}
	
	public TipoPersona getTipiPersona() {
		return tipiPersona;
	}
	public void setTipiPersona(TipoPersona tipiPersona) {
		this.tipiPersona = tipiPersona;
	}
	public List<Actividades> getActividad() {
		return actividad;
	}
	public void setActividad(List<Actividades> actividad) {
		this.actividad = actividad;
	}
	public List<Ubicacion> getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(List<Ubicacion> ubicacion) {
		this.ubicacion = ubicacion;
	}
	public List<DetallePatronClasifMovPat> getLstDetallePatron() {
		return lstDetallePatron;
	}
	public void setLstDetallePatron(List<DetallePatronClasifMovPat> lstDetallePatron) {
		this.lstDetallePatron = lstDetallePatron;
	}
	
	
	

}
