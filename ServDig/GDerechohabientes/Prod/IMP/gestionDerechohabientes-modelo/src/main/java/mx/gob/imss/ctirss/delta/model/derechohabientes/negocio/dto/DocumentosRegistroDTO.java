package mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto;

import java.io.Serializable;
import java.util.List;

public class DocumentosRegistroDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4063458195246823055L;
	
	private List<Reporte4305A> datos4305A;
	private List<Sav002DTO> datosSav002;
	private List<Sav005DTO> datosSav005;
	private List<Sav017DTO> datosSav017;
	private List<CartillaSaludDTO> datosCartilla;

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
	/**
	 * @return the datosSav005
	 */
	public List<Sav005DTO> getDatosSav005() {
		return datosSav005;
	}
	/**
	 * @param datosSav005 the datosSav005 to set
	 */
	public void setDatosSav005(List<Sav005DTO> datosSav005) {
		this.datosSav005 = datosSav005;
	}
	/**
	 * @return the datosSav017
	 */
	public List<Sav017DTO> getDatosSav017() {
		return datosSav017;
	}
	/**
	 * @param datosSav017 the datosSav017 to set
	 */
	public void setDatosSav017(List<Sav017DTO> datosSav017) {
		this.datosSav017 = datosSav017;
	}

}
