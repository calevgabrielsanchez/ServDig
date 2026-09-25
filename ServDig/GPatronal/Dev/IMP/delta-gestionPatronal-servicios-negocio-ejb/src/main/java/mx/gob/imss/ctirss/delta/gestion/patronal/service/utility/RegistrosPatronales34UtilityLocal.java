package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;
import mx.gob.imss.digital.modelo.patron.RegistroPatronal;
import mx.gob.imss.digital.modelo.persona.Persona;

@Local
public interface RegistrosPatronales34UtilityLocal {

	
	public List<Long> getModalidad34();
	
	/**
	 * Agrega los RPs a la persona.
	 * 
	 * @param listaSujetosObligados
	 * 
	 */
	void transformarSujetosObligados(Persona persona, List<SujetoObligado> listaSujetosObligados);
	
	/**
	 * Transforma Sujeto Obligado al modelo Registro Patronal.
	 * 
	 * @param so
	 * @return
	 */
	RegistroPatronal transformarSujetoToRP(SujetoObligado so);
	
	/**
	 * Transforma Municipio al modelo de imss digital.
	 * 
	 * @param municipioHelper
	 * @return
	 */
	Municipio getMunicipioNegocio(mx.gob.imss.digital.modelo.domicilio.Municipio municipioHelper);
	
	/**
	 * Transforma domicilio de imss digital modelo a domicilio de delta modelo negocio
	 * @param domicilioImssDigital
	 * @return
	 */
	mx.gob.imss.ctirss.delta.model.domicilio.Domicilio transformarDomicilioImssDigitalAModeloNegocio(Domicilio domicilioImssDigital);
}
