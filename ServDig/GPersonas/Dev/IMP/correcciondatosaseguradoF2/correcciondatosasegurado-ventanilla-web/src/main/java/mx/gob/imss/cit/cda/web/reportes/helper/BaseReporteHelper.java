package mx.gob.imss.cit.cda.web.reportes.helper;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseReporteHelper implements Serializable {

    private static final long serialVersionUID = 4330307139775775854L;
    private final transient Logger logger = LoggerFactory.getLogger(getClass());

    public Logger getLogger() {

        return logger;
    }
}