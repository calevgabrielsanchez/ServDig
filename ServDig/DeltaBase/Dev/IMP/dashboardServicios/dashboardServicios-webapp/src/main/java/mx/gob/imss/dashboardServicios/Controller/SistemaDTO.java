package mx.gob.imss.dashboardServicios.Controller;

import java.io.Serializable;
import java.util.List;

import mx.gob.imss.ctirss.infraestructura.dashboardservicios.modelo.negocio.Sistema;



/**
 * @author Josué Hernández Ramírez
 * @company IMSS (Instituto Mexicano del Seguro Social
 *	23/01/2012
 */
public class SistemaDTO implements Serializable {

	private static final long serialVersionUID = -2383613960397927489L;
	private List<Sistema> lstResultados;
	private Sistema sistemaTemp;
	private String descripcionSistema;
	/**
	 * @return the descSis
	 */
	public String getDescripcionSistema() {
		return descripcionSistema;
	}
	/**
	 * @param descSis the descSis to set
	 */
	public void setDescripcionSistema(String descripcionSistema) {
		System.out.println("****** seteando el valor de: " + descripcionSistema);
		this.descripcionSistema = descripcionSistema;
	}
	/**
	 * @return the lstResultados
	 */
	public List<Sistema> getLstResultados() {
		return lstResultados;
	}
	/**
	 * @param lstResultados the lstResultados to set
	 */
	public void setLstResultados(List<Sistema> lstResultados) {
		this.lstResultados = lstResultados;
	}
	/**
	 * @return the sistemaTemp
	 */
	public Sistema getSistemaTemp() {
		return sistemaTemp;
	}
	/**
	 * @param sistemaTemp the sistemaTemp to set
	 */
	public void setSistemaTemp(Sistema sistemaTemp) {
		this.sistemaTemp = sistemaTemp;
	}
	
	

}
