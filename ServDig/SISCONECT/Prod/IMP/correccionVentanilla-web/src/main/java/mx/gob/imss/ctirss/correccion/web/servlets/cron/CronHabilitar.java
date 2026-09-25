package mx.gob.imss.ctirss.correccion.web.servlets.cron;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @deprecated USar IniciarBatchBean
 *
 */
public class CronHabilitar {
	
	private static Logger logger = Logger.getLogger(CronHabilitar.class);
	
	public CronHabilitar ()throws Exception {
		SchedulerFactory sf= new StdSchedulerFactory();
	    Scheduler sched=sf.getScheduler();
//	    ResourceBundle props = ResourceBundle.getBundle("conf");
	    JobDetail jd = new JobDetail("job1","group1",HabilitarTarea.class);
	    String scheduler = "0 59 23 * * ? *";//props.getString("scheduler");
	    //CronTrigger ct = new CronTrigger("cronTrigger","group2","0 15 09 * * ?");
	    logger.info("Programando tarea quartz con cadena "+scheduler);
	    CronTrigger ct = new CronTrigger("cronTrigger","group2",scheduler);
	    sched.scheduleJob(jd,ct);
	    sched.start();
	}
}