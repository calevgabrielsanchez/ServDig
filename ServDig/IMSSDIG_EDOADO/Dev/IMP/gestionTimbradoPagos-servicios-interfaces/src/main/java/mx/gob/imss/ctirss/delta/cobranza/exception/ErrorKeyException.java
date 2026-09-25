package mx.gob.imss.ctirss.delta.cobranza.exception;


public class ErrorKeyException extends RuntimeException {

    public ErrorKeyException() {
        super();
    }

    public ErrorKeyException(String message) {
        super(message);
    }

    public ErrorKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorKeyException(Throwable cause) {
        super(cause);
    }
}
