package mx.gob.imss.ctirss.delta.framework.exceptions;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CGM
 * 
 */
public class TramiteNoEncontradoException extends AbstractException implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG;

    static {
        LOG = LoggerFactory.getLogger(TramiteNoEncontradoException.class);
    }

    public TramiteNoEncontradoException(final Long tramiteId) {
        super("No se encontr\u00F3 el tr\u00E1mite para el id " + tramiteId);
        LOG.debug("No se encontr\u00F3 el tr\u00E1mite para el id " + tramiteId);
    }

}
