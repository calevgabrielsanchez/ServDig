package mx.gob.imss.ctirss.delta.global.model;

import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class PatronTO extends AbstractModel{
	
	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 1807599201647225205L;
	
	private PersonaTO persona;
	private List<RegistroPatronalTO> registrosPatronales;
	
	public PersonaTO getPersona() {
		return persona;
	}
	public void setPersona(PersonaTO persona) {
		this.persona = persona;
	}
	public List<RegistroPatronalTO> getRegistrosPatronales() {
		return registrosPatronales;
	}
	public void setRegistrosPatronales(List<RegistroPatronalTO> registrosPatronales) {
		this.registrosPatronales = registrosPatronales;
	}
	
}
