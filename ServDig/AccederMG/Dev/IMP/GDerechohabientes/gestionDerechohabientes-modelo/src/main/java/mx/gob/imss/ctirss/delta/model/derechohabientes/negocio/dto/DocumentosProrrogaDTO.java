package mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto;

import java.io.Serializable;
import java.util.List;

public class DocumentosProrrogaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4953737459918388391L;

	private List<Sav007DTO> datosSav007;
	private List<CartillaSaludDTO> datosCartilla;

	public List<Sav007DTO> getDatosSav007() {
		return datosSav007;
	}

	public void setDatosSav007(List<Sav007DTO> datosSav007) {
		this.datosSav007 = datosSav007;
	}

	public List<CartillaSaludDTO> getDatosCartilla() {
		return datosCartilla;
	}

	public void setDatosCartilla(List<CartillaSaludDTO> datosCartilla) {
		this.datosCartilla = datosCartilla;
	}

}
