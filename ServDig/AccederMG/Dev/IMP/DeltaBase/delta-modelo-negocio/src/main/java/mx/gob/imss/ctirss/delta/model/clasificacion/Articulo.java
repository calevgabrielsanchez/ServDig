/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.ctirss.delta.model.clasificacion;

import java.util.List;

/**
 *
 * @author Jorge A. García
 */
public enum Articulo {
	
	FRACCION(1,"Fraccion") ,
	INCISO(2,"Inciso");
	
	private final int clave;
	private final String descripcion;
	
	private List<AnalisisClasificacionEmpresas> analisisClasificacionEmpresasList;
	
	private Articulo(int clave, String descripcion) {
		this.clave = clave;
		this.descripcion = descripcion;
	}

	String getDescripcion() {
		return descripcion;
	}

	int getClave() {
		return clave;
	}

	List<AnalisisClasificacionEmpresas> getAnalisisClasificacionEmpresasList() {
		return analisisClasificacionEmpresasList;
	}

	void setAnalisisClasificacionEmpresasList(
			List<AnalisisClasificacionEmpresas> analisisClasificacionEmpresasList) {
		this.analisisClasificacionEmpresasList = analisisClasificacionEmpresasList;
	}
	
	
}
