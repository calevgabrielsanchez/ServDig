package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.registro.sindicato;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.RegistroSindicato;
import mx.gob.imss.ctirss.delta.persistence.DitSindicato;


@Local
public interface RegistroSindicatoServiceUtilityLocal {
	
	DitSindicato convertirModelToEntity(RegistroSindicato model) throws Exception;
	
	RegistroSindicato convertirEntityToModel(DitSindicato entity);
	
	DitSindicato asignarvaloresFaltantes(RegistroSindicato registroSindicato, DitSindicato ditSindicato) throws Exception;

}
