package mx.gob.imss.dacvass.scheduler.cron.service.utility;

import javax.ejb.Local;

import mx.gob.imss.dacvass.scheduler.cron.dto.ProcOdiCompFiscDto;
import mx.gob.imss.dacvass.scheduler.cron.entity.ProcOdiCompFiscEntity;

@Local
public interface ProcOdiCompFiscUtilityLocal {
	
	ProcOdiCompFiscDto convertirEntityToModel(ProcOdiCompFiscEntity procOdiCompFiscEntity);
	ProcOdiCompFiscEntity convertirModelToEntity(ProcOdiCompFiscDto procOdiCompFiscDto);

}
