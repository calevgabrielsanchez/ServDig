package mx.imss.ctirss.denuncia.vo;

import java.io.Serializable;
import java.util.Date;

public class DenunciaVODT implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String numClaveFolio;
	private String numFolio;
	private String fechaRegistro;
	private String nombreDenunciante;
	private String nombrePatronDenunciado;
	private String estatus;
	private Long idEstatus;

	//datos de la tabla dltDerivaSub
	private Date fechaPresentacion;
	private Date fechaDerivacion;
	private String desSubdelegacion;
	private Date fechaConclusion;
	private String instrucciones;
	
	public String getNumFolio() {
		return numFolio;
	}
	public void setNumFolio(String numFolio) {
		this.numFolio = numFolio;
	}
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public String getNombreDenunciante() {
		return nombreDenunciante;
	}
	public void setNombreDenunciante(String nombreDenunciante) {
		this.nombreDenunciante = nombreDenunciante;
	}
	public String getNombrePatronDenunciado() {
		return nombrePatronDenunciado;
	}
	public void setNombrePatronDenunciado(String nombrePatronDenunciado) {
		this.nombrePatronDenunciado = nombrePatronDenunciado;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public String getNumClaveFolio() {
		return numClaveFolio;
	}
	public void setNumClaveFolio(String numClaveFolio) {
		this.numClaveFolio = numClaveFolio;
	}
	public Long getIdEstatus() {
		return idEstatus;
	}
	public void setIdEstatus(Long idEstatus) {
		this.idEstatus = idEstatus;
	}
	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}
	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}
	public Date getFechaDerivacion() {
		return fechaDerivacion;
	}
	public void setFechaDerivacion(Date fechaDerivacion) {
		this.fechaDerivacion = fechaDerivacion;
	}
	public String getDesSubdelegacion() {
		return desSubdelegacion;
	}
	public void setDesSubdelegacion(String desSubdelegacion) {
		this.desSubdelegacion = desSubdelegacion;
	}
	public Date getFechaConclusion() {
		return fechaConclusion;
	}
	public void setFechaConclusion(Date fechaConclusion) {
		this.fechaConclusion = fechaConclusion;
	}
	public String getInstrucciones() {
		return instrucciones;
	}
	public void setInstrucciones(String instrucciones) {
		this.instrucciones = instrucciones;
	}
	
	
	
}
