package mx.gob.imss.ctirss.delta.model.persona.hlda.exception;

public class WSHldaFaultException extends RuntimeException implements java.io.Serializable {

    private String mensaje;

    public WSHldaFaultException () {
        super("Error desconcido en la comunicaci\u00F3n con HLDA");
        mensaje = getMessage();
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
