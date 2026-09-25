package mx.gob.imss.dacvass.scheduler.cron;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class JobConsultaEstatusCfdi extends QuartzJobBean {
	
	private ISchedulerService schedulerService;
	private JobExecutionContext jobExecutionContext;
	
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		this.setJobExecutionContext(context);
		this.getSchedulerService().executeFirstTask();
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
