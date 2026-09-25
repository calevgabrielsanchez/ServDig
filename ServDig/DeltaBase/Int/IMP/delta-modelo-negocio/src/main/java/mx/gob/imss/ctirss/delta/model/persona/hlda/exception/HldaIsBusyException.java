package mx.gob.imss.ctirss.delta.model.persona.hlda.exception;

public class HldaIsBusyException extends RuntimeException implements java.io.Serializable {

    private static final long serialVersionUID = 7731563576630954709L;

    private String mensaje;

    public HldaIsBusyException() {
        mensaje = "La transacci\u00f3n no se encuentra disponible, favor de volver a intentar";
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
