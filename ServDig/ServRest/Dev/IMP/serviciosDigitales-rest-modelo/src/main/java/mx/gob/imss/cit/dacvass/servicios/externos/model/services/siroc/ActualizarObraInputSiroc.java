package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ActualizarObraInputSiroc implements Serializable {

	private static final long serialVersionUID = 152115362055399165L;

	private String numeroRegistroObra;

	private String marcaPrtoObra;

	public String getNumeroRegistroObra() {
		return numeroRegistroObra;
	}

	public void setNumeroRegistroObra(String numeroRegistroObra) {
		this.numeroRegistroObra = numeroRegistroObra;
	}

	public String getMarcaPrtoObra() {
		return marcaPrtoObra;
	}

	public void setMarcaPrtoObra(String marcaPrtoObra) {
		this.marcaPrtoObra = marcaPrtoObra;
	}

}
