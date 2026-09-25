package mx.gob.imss.ctirss.delta.model.gestion.individuo;

public enum CalificacionEnum {
    VALIDADO_RENAPO(1, "Validado por RENAPO"),
    VALIDADO_SAT(2, "Validado por SAT"),
    VALIDADO_IMSS(3, "Validado por IMSS"),
    NO_VALIDADO(4, "No validado"),
    ENCONTRADO_IMSS(5, "Encontrado en IMSS"), 
    SIN_CALIFICACION(6, "Sin calificacion")
    ;

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    private Integer codigo;
    private String descripcion;

    CalificacionEnum(final Integer codigo, final String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public static CalificacionEnum valueIntOf(final Integer idCalificacion) {
        CalificacionEnum calificacionEnum = null; // NOPMD
        switch (idCalificacion) {
        case VALIDADO_RENAPO_:
            calificacionEnum = VALIDADO_RENAPO;
            break;
        case VALIDADO_SAT_:
            calificacionEnum = VALIDADO_SAT;
            break;
        case VALIDADO_IMSS_:
            calificacionEnum = VALIDADO_IMSS;
            break;
        case NO_VALIDADO_:
            calificacionEnum = NO_VALIDADO;
            break;
        case ENCONTRADO_IMSS_:
            calificacionEnum = ENCONTRADO_IMSS;
            break;
            
        case SIN_CALIFICACION_:
            calificacionEnum = SIN_CALIFICACION;
            break;

        default:
            //LOG.warn("Calificaci\u00F3n no valida, la calificaci\u00F3n es " + idCalificacion);
            break;
        }
        
        return calificacionEnum;
    }

    private static final int VALIDADO_RENAPO_ = 1;
    private static final int VALIDADO_SAT_ = 2;
    private static final int VALIDADO_IMSS_ = 3;
    private static final int NO_VALIDADO_ = 4;
    private static final int ENCONTRADO_IMSS_ = 5;
    private static final int SIN_CALIFICACION_ = 6;
    

}
