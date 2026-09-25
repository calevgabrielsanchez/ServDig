package mx.imss.estrados.web.cron;

import javax.ejb.EJB;

import mx.imss.estrados.cron.EjecutaTareasCronServiceRemote;


public class RunMeTask {
	
	@EJB
	private EjecutaTareasCronServiceRemote ejecutaTareasCron;
	
	public void modificaNotificacionesRegistradas() {
		ejecutaTareasCron.modificaNotificacionesRegistradas();
	}
	
	public void modificaNotificacionesPublicadas() {
		ejecutaTareasCron.modificaNotificacionesPublicadas();
	}
}

