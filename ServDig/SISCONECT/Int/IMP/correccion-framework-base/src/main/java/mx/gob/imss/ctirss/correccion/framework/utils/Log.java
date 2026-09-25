package mx.gob.imss.ctirss.correccion.framework.utils;

import java.text.MessageFormat;

import org.apache.commons.logging.LogFactory;

/**
 * Logger para la bitacora de actividades optimizada
 *
 * @author <a href="mailto:sergio.castillo@tavtta-it.com">Sergio Castillo Saldierna</a>
 * @since Jul 11, 2009
 * @since 0.1
 */
public class Log {

	/**
	 *
	 */
	private org.apache.commons.logging.Log logger;

	/**
	 *
	 * @param name
	 */
	protected Log(String name) {
		logger = LogFactory.getLog(name);
	}

	/**
	 *
	 * @param name
	 * @return
	 */
	public static final Log getLog(Class<?> name) {
		return new Log(name.getName());
	}

	/**
	 *
	 * @param name
	 * @return
	 */
	public static final Log getLog(String name) {
		return new Log(name);
	}

	/**
	 *
	 * @param message
	 * @param err
	 */
	public void debug(Object message, Throwable err) {
		if(logger.isDebugEnabled()) {
			logger.debug(message, err);
		}
	}

	/**
	 *
	 * @param message
	 */
	public void debug(Object message, Object... args) {
		if(logger.isDebugEnabled()) {
			if(args != null && args.length > 0) {
				logger.debug(MessageFormat.format(message.toString(), args));
			}
			else {
				logger.debug(message);
			}
		}
	}

	/**
	 *
	 * @param message
	 * @param err
	 */
	public void error(Object message, Throwable err) {
		if(logger.isErrorEnabled()) {
			logger.error(message, err);
		}
	}

	/**
	 *
	 * @param message
	 * @param args
	 */
	public void error(Object message, Object... args) {
		if(logger.isErrorEnabled()) {
			if(args != null && args.length > 0) {
				logger.error(MessageFormat.format(message.toString(), args));
			}
			else {
				logger.error(message);
			}
		}
	}

	/**
	 *
	 * @param message
	 * @param err
	 */
	public void fatal(Object message, Throwable err) {
		if(logger.isFatalEnabled()) {
			logger.fatal(message, err);
		}
	}

	/**
	 *
	 * @param message
	 * @param args
	 */
	public void fatal(Object message, Object... args) {
		if(logger.isFatalEnabled()) {
			if(args != null && args.length > 0) {
				logger.fatal(MessageFormat.format(message.toString(), args));
			}
			else {
				logger.fatal(message);
			}
		}
	}

	/**
	 *
	 * @param message
	 * @param err
	 */
	public void info(Object message, Throwable err) {
		if(logger.isInfoEnabled()) {
			logger.info(message, err);
		}
	}

	/**
	 *
	 * @param message
	 * @param args
	 */
	public void info(Object message, Object... args) {
		if(logger.isInfoEnabled()) {
			if(args != null && args.length > 0) {
				logger.info(MessageFormat.format(message.toString(), args));
			}
			else {
				logger.info(message);
			}
		}
	}

	/**
	 *
	 * @param message
	 * @param err
	 */
	public void warn(Object message, Throwable err) {
		if(logger.isWarnEnabled()) {
			logger.warn(message, err);
		}
	}

	/**
	 *
	 * @param message
	 * @param args
	 */
	public void warn(Object message, Object... args) {
		if(logger.isWarnEnabled()) {
			if(args != null && args.length > 0) {
				logger.warn(MessageFormat.format(message.toString(), args));
			}
			else {
				logger.warn(message);
			}
		}
	}

}
