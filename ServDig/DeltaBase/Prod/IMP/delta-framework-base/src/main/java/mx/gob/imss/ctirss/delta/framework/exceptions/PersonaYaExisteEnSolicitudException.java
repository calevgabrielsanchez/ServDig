package mx.gob.imss.ctirss.delta.framework.exceptions;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class PersonaYaExisteEnSolicitudException extends AbstractException {

    private static final long serialVersionUID = 1L;
    private static Integer code = 1;
    private static String msg = "La persona que ha intentado anexar a la solicitud ya se encontraba registrada";

    public PersonaYaExisteEnSolicitudException() {
        super(msg, code);
    }

}
