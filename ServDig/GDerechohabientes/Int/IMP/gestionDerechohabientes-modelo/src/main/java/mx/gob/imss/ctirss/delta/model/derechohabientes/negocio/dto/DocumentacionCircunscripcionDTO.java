package mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto;

import java.io.Serializable;
import java.util.List;

public class DocumentacionCircunscripcionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5156612766145150453L;
	private List<Sav002DTO> datosSav002;
	private List<CartillaSaludDTO> datosCartilla;
	private List<Sav017DTO> datosSav017;
	private List<Sav006DTO> datosSav006;
	private List<Reporte4305A> datos4305A;
	
	
	public List<Reporte4305A> getDatos4305A() {
		return datos4305A;
	}

	public void setDatos4305A(List<Reporte4305A> datos4305a) {
		datos4305A = datos4305a;
	}

	public List<Sav006DTO> getDatosSav006() {
		return datosSav006;
	}

	public void setDatosSav006(List<Sav006DTO> datosSav006) {
		this.datosSav006 = datosSav006;
	}

	public List<Sav002DTO> getDatosSav002() {
		return datosSav002;
	}

	public List<CartillaSaludDTO> getDatosCartilla() {
		return datosCartilla;
	}

	public void setDatosSav002(List<Sav002DTO> datosSav002) {
		this.datosSav002 = datosSav002;
	}

	public void setDatosCartilla(List<CartillaSaludDTO> datosCartilla) {
		this.datosCartilla = datosCartilla;
	}

	public List<Sav017DTO> getDatosSav017() {
		return datosSav017;
	}

	public void setDatosSav017(List<Sav017DTO> datosSav017) {
		this.datosSav017 = datosSav017;
	}

	
}
