package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.registro.sindicato;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RegistroSindicato;

@Local
public interface RegistroSindicatoServiceEntityLocal {
	
	RegistroSindicato actualizarRegistroSindicato(RegistroSindicato registroSindicato) throws GestionPatronalBusinessException;

}
