package mx.imss.estrados.web.cron;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class JobNotificacionesRegistradas extends QuartzJobBean {
	
//	private RunMeTask runMeTask;
	 private ISchedulerService schedulerService;
	 
	 private JobExecutionContext jobExecutionContext;
 
	protected void executeInternal(JobExecutionContext context)
		throws JobExecutionException {
		setJobExecutionContext(context);
		getSchedulerService().executeFirstTask();
	}



	public ISchedulerService getSchedulerService() {
		return schedulerService;
	}


	public void setSchedulerService(ISchedulerService schedulerService) {
		this.schedulerService = schedulerService;
	}


	public JobExecutionContext getJobExecutionContext() {
		return jobExecutionContext;
	}


	public void setJobExecutionContext(JobExecutionContext jobExecutionContext) {
		this.jobExecutionContext = jobExecutionContext;
	}
}