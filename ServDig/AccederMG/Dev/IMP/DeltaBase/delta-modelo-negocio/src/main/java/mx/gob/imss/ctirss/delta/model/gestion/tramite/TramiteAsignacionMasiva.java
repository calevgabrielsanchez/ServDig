package mx.gob.imss.ctirss.delta.model.gestion.tramite;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;

@XmlRootElement
public class TramiteAsignacionMasiva extends TramiteSujetoObligado {

	private static final long serialVersionUID = -3517808268741896196L;

	private String nrp;
	private String fileName;
	private String usuario;
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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public List<CorreoElectronico> getCorreosContacto() {
		return correosContacto;
	}

	public void setCorreosContacto(List<CorreoElectronico> correosContacto) {
		this.correosContacto = correosContacto;
	}
}
