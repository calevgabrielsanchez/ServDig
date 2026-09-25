package mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico;

import java.text.SimpleDateFormat;
import java.util.Date;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

/**
 * Objeto visual el cual nos apoya al momento de consultar
 * los periodos presentados de obra
 * 
 * @author Saal Rosales Piedragil
 * @version 1.0.0
 *
 */
public class SegEOPeriodosPresentadosObraVO extends AbstractModel{
	/**
	 * Namero de registro de la obra
	 */
	private String cveNumRegObra;
	
	
	/**
	 * fecha de inicio
	 */
	private String fechaInicial;
	
	/**
	 * fecha de fin
	 */
	private String fechaFinal;
	
	/**
	 * periodo presentado
	 */
	private String numPeriodo;
	
	/**
	 * fecha de presentacion de la relacian
	 */
	private String fechaPresentacion;



	/**
	 * Constructor el cual recibe un arreglo 
	 * tipo Obj[] y este es parseado llenando
	 * asa los atributos del objeto.
	 * 
	 * @param obj
	 */
	public SegEOPeriodosPresentadosObraVO(Object[] obj){
		int i=0;		
		Object object = null;
		Date fecha = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		setCveNumRegObra(String.valueOf(obj[i++]));
		object = obj[i++];
		if(object!=null){
		  fecha = (Date) object;
		  setFechaInicial(sdf.format(fecha));
		}

		object = obj[i++];
		if(object!=null){
		  fecha = (Date) object;
		  setFechaFinal(sdf.format(fecha));
		}
		setNumPeriodo(String.valueOf(obj[i++]));

		object = obj[i++];
		if(object!=null){
		  fecha = (Date) object;
		  setFechaPresentacion(sdf.format(fecha));
		}
		
	}



	public String getCveNumRegObra() {
		return cveNumRegObra;
	}



	public void setCveNumRegObra(String cveNumRegObra) {
		this.cveNumRegObra = cveNumRegObra;
	}



	public String getFechaInicial() {
		return fechaInicial;
	}



	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = fechaInicial;
	}



	public String getFechaFinal() {
		return fechaFinal;
	}



	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}



	public String getNumPeriodo() {
		return numPeriodo;
	}



	public void setNumPeriodo(String numPeriodo) {
		this.numPeriodo = numPeriodo;
	}



	public String getFechaPresentacion() {
		return fechaPresentacion;
	}



	public void setFechaPresentacion(String fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

}
