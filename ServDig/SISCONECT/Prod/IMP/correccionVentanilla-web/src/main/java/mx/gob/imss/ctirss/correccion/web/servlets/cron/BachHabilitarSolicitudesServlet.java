package mx.gob.imss.ctirss.correccion.web.servlets.cron;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import mx.gob.imss.ctirss.correccion.timer.service.interfaces.IniciarBatchService;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class BachHabilitarSolicitudesServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger
			.getLogger(BachHabilitarSolicitudesServlet.class);
	private static IniciarBatchService timerService;

	/**
	 * Constructor of the object.
	 */
	public BachHabilitarSolicitudesServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		logger.info("Deteniendo Timer");
		if (timerService != null) {
			timerService.stopTimer();
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occure
	 */
	public void init() throws ServletException {
		logger.info("Inicianto Timer");
		try {
			WebApplicationContext wac = WebApplicationContextUtils
					.getRequiredWebApplicationContext(getServletContext());

			timerService = wac.getBean(IniciarBatchService.class);
			timerService.startTimer();
		} catch (Throwable e) {
			logger.error("Error inicializando cron: " + e.getMessage(), e);
		}
		return;
	}

}
