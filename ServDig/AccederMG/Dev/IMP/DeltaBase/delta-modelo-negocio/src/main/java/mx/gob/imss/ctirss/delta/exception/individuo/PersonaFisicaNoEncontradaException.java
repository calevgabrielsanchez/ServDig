/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.individuo;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author SRG
 * 
 */
public class PersonaFisicaNoEncontradaException extends AbstractException{

    private static final long serialVersionUID = 1L;
    
    public PersonaFisicaNoEncontradaException(final Long idPersona) {
        super("No se encontr\u00F3 la persona f\u00edsica con el ID: " + idPersona);
    }
    
    public PersonaFisicaNoEncontradaException(final Long idPersona, final String mensaje) {
        super("No se encontr\u00F3 la persona f\u00edsica con el ID: " + idPersona + " debido a: " + mensaje);
    }

}
