package mx.gob.imss.ctirss.delta.model.enums;

public enum TipoDoctoOrigSolicitanteCDAEnum {
    ASEGURADO(1L, "Asegurado"), BENEFICIARIO(2L, "Beneficiario"), REPRESENTANTE_LEGAL(3L, "Representante Legal"), NSS(4L, "NSS");

    private final Long clave;
    private final String descripcion;
    
    private TipoDoctoOrigSolicitanteCDAEnum(Long clave, String descripcion) {
        this.clave = clave;
        this.descripcion = descripcion;
    }

    public Long getClave() {
        return clave;
    }

    public String getDescripcion() {
        return descripcion;
    }
    
    
}
