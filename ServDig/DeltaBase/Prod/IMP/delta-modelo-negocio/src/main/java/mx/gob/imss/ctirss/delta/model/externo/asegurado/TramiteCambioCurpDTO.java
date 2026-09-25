package mx.gob.imss.ctirss.delta.model.externo.asegurado;

import java.io.Serializable;
import java.util.Date;

public class TramiteCambioCurpDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -504013450338551804L;
	private Long idPersona;
	private String nss;
	private Long idAsignacionNSS;
	private String curpAnterior;
	private String curpNueva;
	private Date fechaNacimientoAnterior;
	private Date fechaNacimientoNueva;
	private boolean actualizaAsignacionCorreo;
	private boolean actualizaPortalCorreo;
	private String correo;
	private String nombre;
	
	
	public TramiteCambioCurpDTO() {
		this.idPersona = null;
		this.nss = null;
		this.idAsignacionNSS = null;
		this.curpAnterior = null;
		this.curpNueva = null;
		this.fechaNacimientoAnterior = null;
		this.fechaNacimientoNueva = null;
		this.actualizaAsignacionCorreo = false;
		this.actualizaPortalCorreo = false;
		this.correo = null;
	}
	
	public Long getIdPersona() {
		return idPersona;
	}
	
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	
	public Long getIdAsignacionNSS() {
		return idAsignacionNSS;
	}
	
	public void setIdAsignacionNSS(Long idAsignacionNSS) {
		this.idAsignacionNSS = idAsignacionNSS;
	}
	
	public String getCurpAnterior() {
		return curpAnterior;
	}
	
	public void setCurpAnterior(String curpAnterior) {
		this.curpAnterior = curpAnterior;
	}
	
	public String getCurpNueva() {
		return curpNueva;
	}
	
	public void setCurpNueva(String curpNueva) {
		this.curpNueva = curpNueva;
	}
	
	public String getNss() {
		return nss;
	}
	
	public void setNss(String nss) {
		this.nss = nss;
	}
	
	public Date getFechaNacimientoAnterior() {
		return fechaNacimientoAnterior;
	}
	
	public void setFechaNacimientoAnterior(Date fechaNacimientoAnterior) {
		this.fechaNacimientoAnterior = fechaNacimientoAnterior;
	}
	
	public Date getFechaNacimientoNueva() {
		return fechaNacimientoNueva;
	}
	
	public void setFechaNacimientoNueva(Date fechaNacimientoNueva) {
		this.fechaNacimientoNueva = fechaNacimientoNueva;
	}

	public boolean isActualizaAsignacionCorreo() {
		return actualizaAsignacionCorreo;
	}

	public void setActualizaAsignacionCorreo(boolean actualizaAsignacionCorreo) {
		this.actualizaAsignacionCorreo = actualizaAsignacionCorreo;
	}

	public boolean isActualizaPortalCorreo() {
		return actualizaPortalCorreo;
	}

	public void setActualizaPortalCorreo(boolean actualizaPortalCorreo) {
		this.actualizaPortalCorreo = actualizaPortalCorreo;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}