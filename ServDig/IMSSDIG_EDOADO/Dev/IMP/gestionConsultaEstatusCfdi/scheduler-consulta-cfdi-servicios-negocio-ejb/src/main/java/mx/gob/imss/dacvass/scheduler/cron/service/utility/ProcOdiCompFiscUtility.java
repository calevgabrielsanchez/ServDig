package mx.gob.imss.dacvass.scheduler.cron.service.utility;

import javax.ejb.Stateless;

import mx.gob.imss.dacvass.scheduler.cron.dto.ProcOdiCompFiscDto;
import mx.gob.imss.dacvass.scheduler.cron.entity.ProcOdiCompFiscEntity;

@Stateless(name="procOdiCompFiscUtility", mappedName="procOdiCompFiscUtility")
public class ProcOdiCompFiscUtility implements ProcOdiCompFiscUtilityLocal {

	@Override
	public ProcOdiCompFiscDto convertirEntityToModel(ProcOdiCompFiscEntity procOdiCompFiscEntity) {
		ProcOdiCompFiscDto procOdiCompFiscDto = new ProcOdiCompFiscDto();
		
		procOdiCompFiscDto.setCveRegistro(procOdiCompFiscEntity.getCveRegistro());
		procOdiCompFiscDto.setNrp(procOdiCompFiscEntity.getNrp());
		procOdiCompFiscDto.setRfc(procOdiCompFiscEntity.getRfc());
		procOdiCompFiscDto.setNombre(procOdiCompFiscEntity.getNombre());
		procOdiCompFiscDto.setSubToImss(procOdiCompFiscEntity.getSubToImss());
		procOdiCompFiscDto.setRecImss(procOdiCompFiscEntity.getRecImss());
		procOdiCompFiscDto.setActImss(procOdiCompFiscEntity.getActImss());
		procOdiCompFiscDto.setSubToRcv(procOdiCompFiscEntity.getSubToRcv());
		procOdiCompFiscDto.setRecRcv(procOdiCompFiscEntity.getRecRcv());
		procOdiCompFiscDto.setActRcv(procOdiCompFiscEntity.getActRcv());
		procOdiCompFiscDto.setCfdiXml(procOdiCompFiscEntity.getCfdiXml());
		procOdiCompFiscDto.setUuid(procOdiCompFiscEntity.getUuid());
		procOdiCompFiscDto.setOdiEstatus(procOdiCompFiscEntity.getOdiEstatus());
		procOdiCompFiscDto.setFecProceso(procOdiCompFiscEntity.getFecProceso());
		procOdiCompFiscDto.setFecArchivo(procOdiCompFiscEntity.getFecArchivo());
		procOdiCompFiscDto.setFecInicio(procOdiCompFiscEntity.getFecInicio());
		procOdiCompFiscDto.setFecFin(procOdiCompFiscEntity.getFecFin());
		procOdiCompFiscDto.setFecRegistro(procOdiCompFiscEntity.getFecRegistro());
		procOdiCompFiscDto.setCveCodigoRespuesta(procOdiCompFiscEntity.getCveCodigoRespuesta());
		
		return procOdiCompFiscDto;
	}
	
	@Override
	public ProcOdiCompFiscEntity convertirModelToEntity(ProcOdiCompFiscDto procOdiCompFiscDto) {
		ProcOdiCompFiscEntity procOdiCompFiscEntity = new ProcOdiCompFiscEntity();
		
		procOdiCompFiscEntity.setCveRegistro(procOdiCompFiscDto.getCveRegistro());
		procOdiCompFiscEntity.setNrp(procOdiCompFiscDto.getNrp());
		procOdiCompFiscEntity.setRfc(procOdiCompFiscDto.getRfc());
		procOdiCompFiscEntity.setNombre(procOdiCompFiscDto.getNombre());
		procOdiCompFiscEntity.setSubToImss(procOdiCompFiscDto.getSubToImss());
		procOdiCompFiscEntity.setRecImss(procOdiCompFiscDto.getRecImss());
		procOdiCompFiscEntity.setActImss(procOdiCompFiscDto.getActImss());
		procOdiCompFiscEntity.setSubToRcv(procOdiCompFiscDto.getSubToRcv());
		procOdiCompFiscEntity.setRecRcv(procOdiCompFiscDto.getRecRcv());
		procOdiCompFiscEntity.setActRcv(procOdiCompFiscDto.getActRcv());
		procOdiCompFiscEntity.setCfdiXml(procOdiCompFiscDto.getCfdiXml());
		procOdiCompFiscEntity.setUuid(procOdiCompFiscDto.getUuid());
		procOdiCompFiscEntity.setOdiEstatus(procOdiCompFiscDto.getOdiEstatus());
		procOdiCompFiscEntity.setFecProceso(procOdiCompFiscDto.getFecProceso());
		procOdiCompFiscEntity.setFecArchivo(procOdiCompFiscDto.getFecArchivo());
		procOdiCompFiscEntity.setFecInicio(procOdiCompFiscDto.getFecInicio());
		procOdiCompFiscEntity.setFecFin(procOdiCompFiscDto.getFecFin());
		procOdiCompFiscEntity.setFecRegistro(procOdiCompFiscDto.getFecRegistro());
		procOdiCompFiscEntity.setCveCodigoRespuesta(procOdiCompFiscDto.getCveCodigoRespuesta());
		
		return procOdiCompFiscEntity;
	}

}
