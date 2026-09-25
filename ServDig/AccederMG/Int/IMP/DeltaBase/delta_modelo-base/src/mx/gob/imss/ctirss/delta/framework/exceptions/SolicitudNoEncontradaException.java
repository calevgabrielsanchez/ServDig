/**
 * 
 */
package mx.gob.imss.ctirss.delta.framework.exceptions;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CGM
 * 
 */
public class SolicitudNoEncontradaException  extends AbstractException implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG;

    static {
        LOG = LoggerFactory.getLogger(SolicitudNoEncontradaException.class);
    }

    public SolicitudNoEncontradaException(final Long folioSolicitud) {
        super("No se encontr\u00F3 la solicitud para el folio " + folioSolicitud);
        LOG.debug("No se encontr\u00F3 la solicitud para el folio " + folioSolicitud);
    }

}
