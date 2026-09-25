package mx.gob.imss.ctirss.idse.utility;

import javax.ejb.Local;

import mx.gob.imss.ctirss.idse.model.RegistroPatronal;
import mx.gob.imss.ctirss.idse.model.RequerimientoIDSEBean;
import mx.gob.imss.ctirss.idse.persistencia.Patrones;
import mx.gob.imss.ctirss.idse.persistencia.PatronesIdse;

@Local
public interface IDSEUtilityLocal {
	
	Patrones convertirModelToEntityPatrones(RegistroPatronal registroPatronal);
	PatronesIdse convertirModelToEntityPatronesIdse(RegistroPatronal registroPatronal);
	PatronesIdse convertirModelToEntityPatronesIdse(RequerimientoIDSEBean reqIdse); 
	Patrones convertirModelToEntityPatrones(RequerimientoIDSEBean reqIdse);
}
