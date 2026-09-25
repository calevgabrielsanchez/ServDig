package mx.gob.imss.ctirss.delta.model.persona.hlda.exception;

public class HldaKnownErrorException extends RuntimeException implements java.io.Serializable {

    private String mensaje;

    public HldaKnownErrorException (String mensaje) {
        super(mensaje);
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
