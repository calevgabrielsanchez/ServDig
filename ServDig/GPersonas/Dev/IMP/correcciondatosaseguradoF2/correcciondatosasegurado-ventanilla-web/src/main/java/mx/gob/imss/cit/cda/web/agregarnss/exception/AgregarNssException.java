package mx.gob.imss.cit.cda.web.agregarnss.exception;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class AgregarNssException extends AbstractException {

    private static final long serialVersionUID = 1L;

    private static String mensaje = "Error al guardar el nuevo nss";
    private static Integer codigo = 12345;

    public AgregarNssException() {
        super(mensaje, codigo);
    }
    
    public AgregarNssException(Throwable e) {
        super(e.getMessage(), codigo);
    }
}
