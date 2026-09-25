package mx.gob.imss.cit.cda.web.app.common.model.enums;

public enum RespuestaPeticionEnum {

    STATUS_ERROR_UPLOAD(1, "error"), STATUS_SUCCESS_UPLOAD(2, "success"), ID_DOCUMENTO_BOVEDA(
            3, "idDocBoveda");

    private long clave;

    private String descripcion;

    private RespuestaPeticionEnum(final long clave, final String descripcion) {
        this.clave = clave;
        this.descripcion = descripcion;
    }

    public long getClave() {
        return clave;
    }

    public String getDescripcion() {
        return descripcion;
    }

}