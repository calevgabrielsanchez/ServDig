package mx.gob.imss.ctirss.delta.model.asegurado;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import java.util.Date;

public class SolicitudNssConfirmacion extends AbstractModel {

	private static final long serialVersionUID = -566262188266363336L;
	
	private long id;
	private CorreoElectronico correo;
	private String curp;
	private String token;
	private Long cveIdTipoSolicitud;
	private Integer vigente;
	private Date fechaTokenAcutalizacion;

	public Integer getVigente() {
		return vigente;
	}

	public void setVigente(Integer vigente){

		this.vigente = vigente;
	}

	public Date getFechaTokenActualizacion(){
		return fechaTokenAcutalizacion;
	}

	public void setFechaTokenActualizacion(Date fechaTokenAcutalizacion){
		this.fechaTokenAcutalizacion = fechaTokenAcutalizacion;
	}

	public CorreoElectronico getCorreo() {
		return correo;
	}
	public void setCorreo(CorreoElectronico correo) {
		this.correo = correo;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getCveIdTipoSolicitud() {
		return cveIdTipoSolicitud;
	}
	public void setCveIdTipoSolicitud(Long cveIdTipoSolicitud) {
		this.cveIdTipoSolicitud = cveIdTipoSolicitud;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

}
