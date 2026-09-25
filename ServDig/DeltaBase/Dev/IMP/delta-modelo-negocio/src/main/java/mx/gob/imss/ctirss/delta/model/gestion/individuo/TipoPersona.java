package mx.gob.imss.ctirss.delta.model.gestion.individuo;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

@XmlRootElement
public class TipoPersona extends AbstractModel {
	
	
	public static Long TIPO_PERSONA_FISICA = new Long(1);
	
	public static Long TIPO_PERSONA_MORAL = new Long(2);
	
	/**
	 * Solo aplica para gestion patronal
	 */
	public static Long TIPO_PERSONA_FIDEICOMISO = new Long(3);
	/**
	 * 
	 */
	private static final long serialVersionUID = 5833606607972097549L;
	private Long idTipoPersona;
	private String descripcion;

	public Long getIdTipoPersona() {
		return idTipoPersona;
	}

	public void setIdTipoPersona(Long idTipoPersona) {
		this.idTipoPersona = idTipoPersona;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
