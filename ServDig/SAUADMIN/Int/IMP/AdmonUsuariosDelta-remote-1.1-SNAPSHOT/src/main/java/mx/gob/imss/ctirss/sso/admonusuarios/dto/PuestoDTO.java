/**
 * 
 */
package mx.gob.imss.ctirss.sso.admonusuarios.dto;

import java.io.Serializable;

/**
 * @author Alan Garcia
 *
 */

public class PuestoDTO  implements Serializable{
	
	private static final long serialVersionUID = 7909142984660178222L;
	private Integer cveArea;
	private Integer cveDepartamento;
	/** cve puesto*/
	private long cvePuesto;
	/** Nombre del puesto */
	private String nombrePuesto;
	private String nombreArea;
	private String nombreDepartamento;
	private String defaultRol = "";
	private boolean nuevoReg = true;

	public PuestoDTO ()
	{
		
	}
	
	public PuestoDTO (Integer cveArea, String nombreArea, Integer cveDepartamento, String nombreDepartamento, Integer cvePuesto, String nombrePuesto, String defaultRol)
	{
		this.cveArea=cveArea;
		this.nombreArea=nombreArea;
		this.cveDepartamento=cveDepartamento;
		this.nombreDepartamento=nombreDepartamento;
		this.cvePuesto=cvePuesto;
	    this.nombrePuesto=nombrePuesto;
	    this.defaultRol = defaultRol;
	}
	
	/** area normativa */
	private DepartamentoDTO departamento;

	public long getCvePuesto() {
		return cvePuesto;
	}

	public void setCvePuesto(long cvePuesto) {
		this.cvePuesto = cvePuesto;
	}

	public String getNombrePuesto() {
		return nombrePuesto;
	}

	public void setNombrePuesto(String nombrePuesto) {
		this.nombrePuesto = nombrePuesto;
	}

	public DepartamentoDTO getDepartamento() {
		return departamento;
	}

	public void setDepartamento(DepartamentoDTO departamento) {
		this.departamento = departamento;
	}

	public Integer getCveArea() {
		return cveArea;
	}

	public void setCveArea(Integer cveArea) {
		this.cveArea = cveArea;
	}

	public Integer getCveDepartamento() {
		return cveDepartamento;
	}

	public void setCveDepartamento(Integer cveDepartamento) {
		this.cveDepartamento = cveDepartamento;
	}

	public String getNombreArea() {
		return nombreArea;
	}

	public void setNombreArea(String nombreArea) {
		this.nombreArea = nombreArea;
	}

	public String getNombreDepartamento() {
		return nombreDepartamento;
	}

	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
	}

	public String getDefaultRol() {
		return defaultRol;
	}

	public void setDefaultRol(String defaultRol) {
		this.defaultRol = defaultRol;
	}

	public boolean isNuevoReg() {
		return nuevoReg;
	}

	public void setNuevoReg(boolean nuevoReg) {
		this.nuevoReg = nuevoReg;
	}	
	
}
