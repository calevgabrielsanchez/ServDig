package mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

/**
 * Objeto visual el cual nos apoya al momento de consultar
 * las incidencias de obra
 * 
 * @author Saal Rosales Piedragil
 * @version 1.0.0
 *
 */
public class SegEOIncidenciasObraVO extends AbstractModel{
	/**
	 * clave primaria de obra
	 */
	private String cvePk;
	
	/**
	 * Clave de incidenciA
	 */
	private String cveIncidencia;
	
	/**
	 * Tipo de incidenciA
	 */
	private String tipoIncidencia;
	
	/**
	 * fecha de inicio de incidencia
	 */
	private String fechaInicioIncidencia;
	
	/**
	 * fecha de fin de incidencia
	 */
	private String fechaFinIncidencia;
	
	/**
	 * fecha de presentacion de la incidencia
	 */
	private String fechaPresentacionIncidencia;
	
	public String getCvePk() {
		return cvePk;
	}

	public void setCvePk(String cvePk) {
		this.cvePk = cvePk;
	}

	public String getCveIncidencia() {
		return cveIncidencia;
	}

	public void setCveIncidencia(String cveIncidencia) {
		this.cveIncidencia = cveIncidencia;
	}


	public String getFechaInicioIncidencia() {
		return fechaInicioIncidencia;
	}

	public void setFechaInicioIncidencia(String fechaInicioIncidencia) {
		this.fechaInicioIncidencia = fechaInicioIncidencia;
	}

	public String getFechaFinIncidencia() {
		return fechaFinIncidencia;
	}

	public void setFechaFinIncidencia(String fechaFinIncidencia) {
		this.fechaFinIncidencia = fechaFinIncidencia;
	}

	public String getFechaPresentacionIncidencia() {
		return fechaPresentacionIncidencia;
	}

	public void setFechaPresentacionIncidencia(String fechaPresentacionIncidencia) {
		this.fechaPresentacionIncidencia = fechaPresentacionIncidencia;
	}

	/**
	 * Constructor el cual recibe un arreglo 
	 * tipo Obj[] y este es parseado llenando
	 * asa los atributos del objeto.
	 * 
	 * @param obj
	 */
	public SegEOIncidenciasObraVO(Object[] obj){
		int i=0;		
		Date fecha = null;
		Object object = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		setCvePk(String.valueOf(obj[i++]));
		setCveIncidencia(String.valueOf(obj[i++]));
		setTipoIncidencia(String.valueOf(obj[i++]));
		
		object = obj[i++];
		if(object!=null){
		  fecha = (Date) object;
			setFechaInicioIncidencia(sdf.format(fecha));
		}

		object = obj[i++];
		if(object!=null){
		  fecha = (Date) object;
			setFechaFinIncidencia(sdf.format(fecha));
		}

		object = obj[i++];
		if(object!=null){
		  fecha = (Date) object;
			setFechaPresentacionIncidencia(sdf.format(fecha));	
		}
	}

	public String getTipoIncidencia() {
		return tipoIncidencia;
	}

	public void setTipoIncidencia(String tipoIncidencia) {
		this.tipoIncidencia = tipoIncidencia;
	}
}
