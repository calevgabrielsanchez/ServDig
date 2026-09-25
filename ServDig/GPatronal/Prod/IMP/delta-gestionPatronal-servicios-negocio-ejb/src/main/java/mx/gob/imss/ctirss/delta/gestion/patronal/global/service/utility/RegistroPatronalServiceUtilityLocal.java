package mx.gob.imss.ctirss.delta.gestion.patronal.global.service.utility;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.global.model.RegistroPatronalTO;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

/**
 * 
 * @author Hugo Martinez
 *
 */
@Local
public interface RegistroPatronalServiceUtilityLocal {
	
	SujetoObligado convertirRegistroPatronalASujetoObligado(RegistroPatronalTO registroPatronal);
	
}
