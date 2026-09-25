
package mx.gob.imss.cit.dacvass.servicios.externos.model.services.sat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RespuestaWSSat implements Serializable  {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7131350060761855645L;
	
	protected String rfcOriginal;
    protected String rfcSolicitado;
    protected String rfcVigente;
    protected List<Actividades> actividad;
    protected List<Identificacion> identificacion;
    protected List<Mensajes> mensaje;
    protected MensajeControl mensajeControl;
    protected List<Obligaciones> obligacion;
    protected List<Regimenes> regimen;
    protected List<RepLegales> replegal;
    protected List<Roles> rol;
    protected List<Sucursales> sucursal;
    protected List<Ubicacion> ubicacion;
	public String getRfcOriginal() {
		return rfcOriginal;
	}
	public void setRfcOriginal(String rfcOriginal) {
		this.rfcOriginal = rfcOriginal;
	}
	public String getRfcSolicitado() {
		return rfcSolicitado;
	}
	public void setRfcSolicitado(String rfcSolicitado) {
		this.rfcSolicitado = rfcSolicitado;
	}
	public String getRfcVigente() {
		return rfcVigente;
	}
	public void setRfcVigente(String rfcVigente) {
		this.rfcVigente = rfcVigente;
	}
	public List<Actividades> getActividad() {
		return actividad;
	}
	public void setActividad(List<Actividades> actividad) {
		this.actividad = actividad;
	}
	public List<Identificacion> getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(List<Identificacion> identificacion) {
		this.identificacion = identificacion;
	}
	public List<Mensajes> getMensaje() {
		return mensaje;
	}
	public void setMensaje(List<Mensajes> mensaje) {
		this.mensaje = mensaje;
	}
	public MensajeControl getMensajeControl() {
		return mensajeControl;
	}
	public void setMensajeControl(MensajeControl mensajeControl) {
		this.mensajeControl = mensajeControl;
	}
	public List<Obligaciones> getObligacion() {
		return obligacion;
	}
	public void setObligacion(List<Obligaciones> obligacion) {
		this.obligacion = obligacion;
	}
	public List<Regimenes> getRegimen() {
		return regimen;
	}
	public void setRegimen(List<Regimenes> regimen) {
		this.regimen = regimen;
	}
	public List<RepLegales> getReplegal() {
		return replegal;
	}
	public void setReplegal(List<RepLegales> replegal) {
		this.replegal = replegal;
	}
	public List<Roles> getRol() {
		return rol;
	}
	public void setRol(List<Roles> rol) {
		this.rol = rol;
	}
	public List<Sucursales> getSucursal() {
		return sucursal;
	}
	public void setSucursal(List<Sucursales> sucursal) {
		this.sucursal = sucursal;
	}
	public List<Ubicacion> getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(List<Ubicacion> ubicacion) {
		this.ubicacion = ubicacion;
	}

   
}
