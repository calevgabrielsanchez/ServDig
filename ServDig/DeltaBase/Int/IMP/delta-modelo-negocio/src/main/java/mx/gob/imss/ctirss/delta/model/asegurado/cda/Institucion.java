package mx.gob.imss.ctirss.delta.model.asegurado.cda;

import java.io.Serializable;
import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class Institucion extends AbstractModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idInstitucion;
	private String nombreInstitucion;
	public Integer getIdInstitucion() {
		return idInstitucion;
	}
	public void setIdInstitucion(Integer idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public String getNombreInstitucion() {
		return nombreInstitucion;
	}
	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}
	

}
