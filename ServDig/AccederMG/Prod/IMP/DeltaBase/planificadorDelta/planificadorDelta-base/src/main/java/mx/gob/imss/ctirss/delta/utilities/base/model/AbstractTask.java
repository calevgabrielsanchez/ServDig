package mx.gob.imss.ctirss.delta.utilities.base.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class AbstractTask {
	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	protected MessageSource messageSource;
}
