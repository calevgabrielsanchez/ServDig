package mx.gob.imss.dacvass.scheduler.cron;

public class SchedulerService implements ISchedulerService {

	private RunMeTask runMeTask;
	
	public void executeFirstTask() {        
		runMeTask.consultaEstatusCfdi();;
	}

	public RunMeTask getRunMeTask() {
		return runMeTask;
	}

	public void setRunMeTask(RunMeTask runMeTask) {
		this.runMeTask = runMeTask;
	}
	
}