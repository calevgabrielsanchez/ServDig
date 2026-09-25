package mx.gob.imss.ctirss.delta.framework.exceptions;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author CGM
 * 18 Junio 2012
 */
public class SolicitudNoValidaException extends AbstractException implements Serializable {

    private static final long serialVersionUID = 1L;

    public SolicitudNoValidaException(final String msgExc) {
        super("Solicitud no valida: " + msgExc);
    }
    
}
