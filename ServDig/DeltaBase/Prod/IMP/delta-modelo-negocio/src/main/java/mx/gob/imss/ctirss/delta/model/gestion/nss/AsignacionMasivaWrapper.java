package mx.gob.imss.ctirss.delta.model.gestion.nss;

import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;

public class AsignacionMasivaWrapper extends AbstractModel {

	private static final long serialVersionUID = -1394306939002840832L;

	private String nrp;
	private String fileName;
	private OrigenSolicitud origenSolicitud;
	private Usuario usuario;
	private List<CorreoElectronico> correosContacto;

	public String getNrp() {
		return nrp;
	}

	public void setNrp(String nrp) {
		this.nrp = nrp;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public OrigenSolicitud getOrigenSolicitud() {
		return origenSolicitud;
	}

	public void setOrigenSolicitud(OrigenSolicitud origenSolicitud) {
		this.origenSolicitud = origenSolicitud;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public List<CorreoElectronico> getCorreosContacto() {
		return correosContacto;
	}

	public void setCorreosContacto(List<CorreoElectronico> correosContacto) {
		this.correosContacto = correosContacto;
	}
}