package mx.gob.imss.dacvass.scheduler.cron.service.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.dacvass.scheduler.cron.dto.ProcOdiCompFiscDto;

@Local
public interface ConsultaEstatusCfdiEntityLocal {
	
	public List<ProcOdiCompFiscDto> obtenerUuidParaConsultar();
	public void actualizaCodigoRespuesta(ProcOdiCompFiscDto procOdiCompFiscDto);

}
