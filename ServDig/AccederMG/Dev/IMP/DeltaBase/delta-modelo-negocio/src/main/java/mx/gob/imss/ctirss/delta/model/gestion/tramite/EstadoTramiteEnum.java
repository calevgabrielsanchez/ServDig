package mx.gob.imss.ctirss.delta.model.gestion.tramite;

/**
 * 
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Autor: Cesar Garcia Mauricio
 * @Proyecto: delta
 * @Archivo: EstadoTramiteEnum.java
 * @Paquete: mx.gob.imss.ctirss.delta.model.gestion.tramite
 * @Fecha: 06 Junio 2012 17:39
 */
public enum EstadoTramiteEnum {
	INICIADO(1, "Iniciado"), CERRADO(2, "Cerrado"), EN_ESPERA_AUTORIZACION(3, "En espera de autorizaci\u00F3n"), 
    EN_ESPERA_TRAMITADOR(4, "En espera por tramitador"), EN_ESPERA_DERECHOHABIENTE(5, "En espera por derechohabiente"), ACTIVO(6,"Activo"), CANCELADO(7,"Cancelado"), 
    BAJA_IMPROCEDENCIA(9,"Baja por improcedencia"), ENVIA_CERTIFICACION_SINDO(37,"ENVIA CERTIFICACI\u00d3N CON SINDO"), EN_ANALISIS(58,"En análisis"), RECHAZADO(70,"Rechazado"), 
    ANALISIS_COMPLETADO(75,"An\u00e1lisis Completado"), ERROR_SINDO(85,"Error SINDO"), SIN_RESPONSABLE(87,"SIN RESPONSABLE"),PROCESADO_SINDO(88,"PROCESADO SINDO"),
	VENCIDA(86,"Vencido"),EN_ESPERA_DERECHOHABIENTE_ATENDIDA(89, "Atendida por derechohabiente");

    private Integer codigo;
    private String descripcion;

    private EstadoTramiteEnum(final Integer codigo, final String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

}
