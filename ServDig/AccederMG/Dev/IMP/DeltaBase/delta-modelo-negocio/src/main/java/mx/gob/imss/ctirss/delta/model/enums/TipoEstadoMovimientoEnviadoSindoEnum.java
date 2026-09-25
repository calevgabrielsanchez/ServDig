package mx.gob.imss.ctirss.delta.model.enums;

public enum TipoEstadoMovimientoEnviadoSindoEnum {
    
    ENVIO_SINDO(1L, "ENVIO SINDO"),
    ERROR_SINDO(2L,"ERROR SINDO"),
	PROCESADO(3L, "PROCESADO");    
    
    private Long id;
    private String descripcion;
    
    private TipoEstadoMovimientoEnviadoSindoEnum(Long id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }
    
    public Long getId() {
        return this.id;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public static TipoEstadoMovimientoEnviadoSindoEnum fromId(long id) {
        TipoEstadoMovimientoEnviadoSindoEnum[] estados = values();
        for(TipoEstadoMovimientoEnviadoSindoEnum estado : estados) {
            if (estado.getId() == id) {
                return estado;
            }
        }
        return null;
    }

}
