package mx.imss.estrados.web.cron;

public class SchedulerService implements ISchedulerService {

	private RunMeTask runMeTask;
	
	public void executeFirstTask() {        
		runMeTask.modificaNotificacionesRegistradas();
	}
	
	public void executeSecondTask() {        
		runMeTask.modificaNotificacionesPublicadas();
		
	}

	public RunMeTask getRunMeTask() {
		return runMeTask;
	}

	public void setRunMeTask(RunMeTask runMeTask) {
		this.runMeTask = runMeTask;
	}
}