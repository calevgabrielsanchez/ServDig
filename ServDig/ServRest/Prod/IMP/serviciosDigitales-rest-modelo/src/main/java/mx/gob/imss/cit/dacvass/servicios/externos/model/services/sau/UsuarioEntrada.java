package mx.gob.imss.cit.dacvass.servicios.externos.model.services.sau;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UsuarioEntrada extends Usuario {

	private static final long serialVersionUID = 1L;

	public String getUidAuditorAsignado() {
		return uidAuditorAsignado;
	}

	public void setUidAuditorAsignado(String uidAuditorAsignado) {
		this.uidAuditorAsignado = uidAuditorAsignado;
	}

	protected String uidAuditorAsignado;

}
