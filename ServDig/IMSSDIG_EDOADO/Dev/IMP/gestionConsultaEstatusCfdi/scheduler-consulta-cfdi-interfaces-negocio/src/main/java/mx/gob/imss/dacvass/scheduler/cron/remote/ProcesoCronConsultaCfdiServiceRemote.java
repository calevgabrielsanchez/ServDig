package mx.gob.imss.dacvass.scheduler.cron.remote;

import javax.ejb.Remote;

import mx.gob.imss.dacvass.scheduler.cron.dto.ProcOdiCompFiscDto;

@Remote
public interface ProcesoCronConsultaCfdiServiceRemote {

	public void consultaEstatusCfdi();
	
	public String obtenerMonto(ProcOdiCompFiscDto procOdiCompFiscDto);
	
}
