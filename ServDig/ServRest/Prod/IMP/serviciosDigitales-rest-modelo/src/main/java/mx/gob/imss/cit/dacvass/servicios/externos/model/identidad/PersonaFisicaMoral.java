package mx.gob.imss.cit.dacvass.servicios.externos.model.identidad;

import java.io.Serializable;

import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSociedad;

public class PersonaFisicaMoral extends Persona implements Serializable   {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8103902967822917914L;
	
	private TipoSociedad tipoSociedad;
	private TipoPersona tipoPersona;
	private Long  cveIdPersonaFisicaMoral;
	
	public TipoSociedad getTipoSociedad() {
		return tipoSociedad;
	}
	public void setTipoSociedad(TipoSociedad tipoSociedad) {
		this.tipoSociedad = tipoSociedad;
	}
	public TipoPersona getTipoPersona() {
		return tipoPersona;
	}
	public void setTipoPersona(TipoPersona tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
	public Long getCveIdPersonaFisicaMoral() {
		return cveIdPersonaFisicaMoral;
	}
	public void setCveIdPersonaFisicaMoral(Long cveIdPersonaFisicaMoral) {
		this.cveIdPersonaFisicaMoral = cveIdPersonaFisicaMoral;
	}
	
	

}
