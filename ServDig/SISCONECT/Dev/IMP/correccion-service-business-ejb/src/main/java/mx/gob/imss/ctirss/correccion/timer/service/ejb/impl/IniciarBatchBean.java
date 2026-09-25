/**
 * 
 */
package mx.gob.imss.ctirss.correccion.timer.service.ejb.impl;

import java.util.Calendar;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;

import mx.gob.imss.ctirss.correccion.solicitud.service.ejb.SolicitudServiceRemote;
import mx.gob.imss.ctirss.correccion.timer.service.ejb.IniciarBatchRemote;

import org.apache.log4j.Logger;

/**
 * @author vaguirre
 * 
 */
@Stateless(name = "iniciarBatchService", mappedName = "iniciarBatchService")
public class IniciarBatchBean implements IniciarBatchRemote {
	@EJB
	private SolicitudServiceRemote solicitudService;
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger
			.getLogger(IniciarBatchBean.class);
	@Resource
	private SessionContext ctx;

	private Timer oneTimer = null;

	public void startTimer() {
		logger.info("Iniciando timer");
		final Calendar calActual = Calendar.getInstance();
		final Calendar calPrimeraProgramacion = Calendar.getInstance();

		if (calActual.get(Calendar.HOUR_OF_DAY) >= 23) {
			calPrimeraProgramacion.add(Calendar.DATE, 1);
		}
		calPrimeraProgramacion.set(Calendar.HOUR_OF_DAY, 23);
		calPrimeraProgramacion.set(Calendar.MINUTE, 0);
		calPrimeraProgramacion.set(Calendar.SECOND, 0);
		calPrimeraProgramacion.set(Calendar.MILLISECOND, 0);
		final long minuto = 1000 * 60; 
		final long hora = minuto * 60;
		final long dia = hora * 24;
		logger.debug("Vencimiento :: " + calPrimeraProgramacion.getTime());
		logger.debug("Periodo :: " + dia);

		oneTimer = ctx.getTimerService().createTimer(
				calPrimeraProgramacion.getTime(), dia, null);
		logger.debug("oneTimer.getNextTimeout() :: " + oneTimer.getNextTimeout());
		logger.debug("Timers set");

	}
	/**
	 * Método que se ejecuta en el vencimiento de la programación.<br>
	 * En este método se deben meter todas las invocaciones a los servicios que se deben ejecutar de manera automática.
	 * @param timer
	 */
	@Timeout
	public void handleTimeout(Timer timer) {
		logger.debug("timer.getNextTimeout() :: " + timer.getNextTimeout());
		logger.debug("timer.getTimeRemaining() :: " + timer.getTimeRemaining());
		

		logger.info("-----------Inicia batch autorizacion de solicitud de correcion-----------------------");
		try {
//FIXME VAP
			solicitudService.autorizarSolicitudes();
			logger.info("Ejecucion de Autorizacion de avisos");
		} catch (Throwable e) {
			logger.error(
					"Error en el proceso de autorizacion de solicitud de correcion: "
							+ e, e);
		}
		logger.info("-----------Fin batch autorizacion avisos-----------------------");

		// logger.debug("-----------Incia batch prorrogas-----------------------");
		// try {
		// new ProrrogaDictamenModel().autorizarVencidas();
		// logger.info("Ejecucion de Autorizacion de Prorrogas");
		// } catch (Throwable e) {
		// logger.fatal("Error en el proceso de autorizacion de prorrogas: "+e,
		// e);
		// }
		// logger.debug("-----------Fin batch prorrogas-----------------------");
	}

	@Override
	public void stopTimer() {
		if (oneTimer != null) {
			oneTimer.cancel();
			logger.debug("Timer stopped");
		}

	}
}
