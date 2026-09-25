package mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto;

import java.io.Serializable;

public class DatosPersonaRenapo implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 2969971283097505567L;
	private String municipioRegistro;    
    private String foja;
    private String folio;
    private String libro;
    private String tomo;
    private String anio;
    
    
	public String getAnio() {
		return anio;
	}
	public void setAnio(String anio) {
		this.anio = anio;
	}
	public String getMunicipioRegistro() {
		return municipioRegistro;
	}
	public void setMunicipioRegistro(String municipioRegistro) {
		this.municipioRegistro = municipioRegistro;
	}
	public String getFoja() {
		return foja;
	}
	public void setFoja(String foja) {
		this.foja = foja;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public String getLibro() {
		return libro;
	}
	public void setLibro(String libro) {
		this.libro = libro;
	}
	public String getTomo() {
		return tomo;
	}
	public void setTomo(String tomo) {
		this.tomo = tomo;
	}
    
        

}
