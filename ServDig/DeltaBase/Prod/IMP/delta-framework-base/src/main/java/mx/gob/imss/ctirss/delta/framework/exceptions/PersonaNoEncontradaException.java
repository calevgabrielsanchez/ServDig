/**
 * 
 */
package mx.gob.imss.ctirss.delta.framework.exceptions;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SRG
 * 
 */
public class PersonaNoEncontradaException extends AbstractException implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG;

    static {
        LOG = LoggerFactory.getLogger(PersonaNoEncontradaException.class);
    }

    public PersonaNoEncontradaException(final Long idPersona) {
        super("No se encontr\u00F3 la persona con el ID: " + idPersona);
        LOG.debug("No se encontr\u00F3 la persona con el ID: " + idPersona);
    }
    
    public PersonaNoEncontradaException(final Long idPersona, final String mensaje) {
        super("No se encontr\u00F3 la persona con el ID: " + idPersona + " debido a: " + mensaje);
        LOG.debug("No se encontr\u00F3 la persona con el ID: " + idPersona + " debido a: " + mensaje);
    }

}
