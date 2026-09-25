package mx.gob.imss.ctirss.delta.model.gestion.solicitud;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoPerInteresadaSol;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;


public class PersonaInteresadaSolicitud  extends AbstractModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7359883984025792699L;
	private Long cveIdPerTramInteresadaSol;
	private Long idSolicitud;
	private Persona persona;
	private TipoPerInteresadaSol tipoPersonaInteresadaSol;

	public Long getCveIdPerTramInteresadaSol() {
		return cveIdPerTramInteresadaSol;
	}

	public void setCveIdPerTramInteresadaSol(Long cveIdPerTramInteresadaSol) {
		this.cveIdPerTramInteresadaSol = cveIdPerTramInteresadaSol;
	}

	

	/**
	 * @return the idSolicitud
	 */
	public Long getIdSolicitud() {
		return idSolicitud;
	}

	/**
	 * @param idSolicitud the idSolicitud to set
	 */
	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona ditPersona) {
		this.persona = ditPersona;
	}

	public TipoPerInteresadaSol getTipoPersonaInteresadaSol() {
		return tipoPersonaInteresadaSol;
	}

	public void setTipoPersonaInteresadaSol(
			TipoPerInteresadaSol tipoPersonaInteresadaSol) {
		this.tipoPersonaInteresadaSol = tipoPersonaInteresadaSol;
	}

}
