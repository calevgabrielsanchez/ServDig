package mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto;

import java.io.Serializable;
import java.util.List;

public class DocumentosCambioClinicaDTO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6527795432004841449L;
	private List<Reporte4305A> datos4305A;
	private List<Sav002DTO> datosSav002;
	private List<CartillaSaludDTO> datosCartilla;
	private List<Sav005DTO> datosSav005;
	private List<Sav006DTO> datosSav006;
	
	public List<Sav005DTO> getDatosSav005() {
		return datosSav005;
	}
	public void setDatosSav005(List<Sav005DTO> datosSav005) {
		this.datosSav005 = datosSav005;
	}
	public List<Reporte4305A> getDatos4305A() {
		return datos4305A;
	}
	public void setDatos4305A(List<Reporte4305A> datos4305a) {
		datos4305A = datos4305a;
	}
	public List<Sav002DTO> getDatosSav002() {
		return datosSav002;
	}
	public void setDatosSav002(List<Sav002DTO> datosSav002) {
		this.datosSav002 = datosSav002;
	}
	public List<CartillaSaludDTO> getDatosCartilla() {
		return datosCartilla;
	}
	public void setDatosCartilla(List<CartillaSaludDTO> datosCartilla) {
		this.datosCartilla = datosCartilla;
	}
	public List<Sav006DTO> getDatosSav006() {
		return datosSav006;
	}
	public void setDatosSav006(List<Sav006DTO> datosSav006) {
		this.datosSav006 = datosSav006;
	}
}