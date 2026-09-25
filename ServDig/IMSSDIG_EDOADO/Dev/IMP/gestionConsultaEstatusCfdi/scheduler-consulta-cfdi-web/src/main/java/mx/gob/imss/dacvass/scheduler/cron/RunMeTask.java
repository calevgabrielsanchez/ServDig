package mx.gob.imss.dacvass.scheduler.cron;

import javax.ejb.EJB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.dacvass.scheduler.cron.remote.ProcesoCronConsultaCfdiServiceRemote;

public class RunMeTask {
	
	private static final Logger log = LoggerFactory.getLogger(RunMeTask.class);
	
	@EJB
	private ProcesoCronConsultaCfdiServiceRemote procesoCronConsultaCfdiService;
	
	public void consultaEstatusCfdi() {
		
		log.info("########## SE INICIA PROCESO BATCH EN LA CLASE RunMeTask ##########");
		procesoCronConsultaCfdiService.consultaEstatusCfdi();
		log.info("########## FINALIZA PROCESO BATCH EN LA CLASE RunMeTask ##########");
	}
	
}
