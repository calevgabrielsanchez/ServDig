package mx.gob.imss.ctirss.correccion.web.servlets.cron;

import mx.gob.imss.ctirss.correccion.correccion.service.interfaces.SolicitudService;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
/**
 * @deprecated USar IniciarBatchBean
 *
 */
public class HabilitarTarea implements Job, ApplicationContextAware  {
	private static Logger logger = Logger.getLogger(HabilitarTarea.class);
	
	private static ApplicationContext appContext;
	
	@Autowired
	private SolicitudService solicitudService;

	/**
	 * @deprecated USar IniciarBatchBean
	 *
	 */
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		logger.debug("-----------Inicia batch autorizacion de solicitud de correcion-----------------------");
		try {
			solicitudService = (SolicitudService) appContext.getBean("solicitudService");

			solicitudService.autorizarSolicitudes();
			logger.info("Ejecucion de Autorizacion de avisos");
		} catch (Throwable e) {
			logger.fatal("Error en el proceso de autorizacion de solicitud de correcion: "+e, e);
		}
		logger.debug("-----------Fin batch autorizacion avisos-----------------------");

//		logger.debug("-----------Incia batch prorrogas-----------------------");
//		try {
//			new ProrrogaDictamenModel().autorizarVencidas();
//			logger.info("Ejecucion de Autorizacion de Prorrogas");
//		} catch (Throwable e) {
//			logger.fatal("Error en el proceso de autorizacion de prorrogas: "+e, e);
//		}
//		logger.debug("-----------Fin batch prorrogas-----------------------");
		
	}


	@Override
	public void setApplicationContext(ApplicationContext autoSet)
			throws BeansException {
		appContext = autoSet;
		
	}
} 