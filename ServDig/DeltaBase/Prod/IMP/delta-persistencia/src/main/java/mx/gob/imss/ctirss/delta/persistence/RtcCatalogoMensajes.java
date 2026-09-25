package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RTC_MENSAJES")
public class RtcCatalogoMensajes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6662609996555671377L;

	@Id
	@Column(name = "CVE_ID_MENSAJE")
	private Integer cveIdMensaje;

	@Column(name = "DES_MENSAJE")
	private String desMensaje;

	@Column(name = "DES_MODULO")
	private String desModulo;

	public Integer getCveIdMensaje() {
		return cveIdMensaje;
	}

	public void setCveIdMensaje(Integer cveIdMensaje) {
		this.cveIdMensaje = cveIdMensaje;
	}

	public String getDesMensaje() {
		return desMensaje;
	}

	public void setDesMensaje(String desMensaje) {
		this.desMensaje = desMensaje;
	}

	public String getDesModulo() {
		return desModulo;
	}

	public void setDesModulo(String desModulo) {
		this.desModulo = desModulo;
	}

}
