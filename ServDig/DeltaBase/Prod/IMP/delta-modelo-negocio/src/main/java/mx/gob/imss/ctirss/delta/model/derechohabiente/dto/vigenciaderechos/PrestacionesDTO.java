package mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos;


import java.io.Serializable;

public class PrestacionesDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4353137903609270181L;
	
	private long idPrestacion;
	private String nomPrestacion;
	private String prestacionEspecie;
	private String prestacionDinero;
	
	
	
	
	public long getIdPrestacion() {
		return idPrestacion;
	}
	public void setIdPrestacion(long idPrestacion) {
		this.idPrestacion = idPrestacion;
	}
	public String getNomPrestacion() {
		return nomPrestacion;
	}
	public void setNomPrestacion(String nomPrestacion) {
		this.nomPrestacion = nomPrestacion;
	}
	public String getPrestacionEspecie() {
		return prestacionEspecie;
	}
	public void setPrestacionEspecie(String prestacionEspecie) {
		this.prestacionEspecie = prestacionEspecie;
	}
	public String getPrestacionDinero() {
		return prestacionDinero;
	}
	public void setPrestacionDinero(String prestacionDinero) {
		this.prestacionDinero = prestacionDinero;
	}
	
	
	

}