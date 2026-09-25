package mx.gob.imss.ctirss.delta.model.gestion.tramite;

/**
 * 
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Autor: Cesar Garcia Mauricio
 * @Proyecto: delta
 * @Archivo: RazonResultadoEnum.java
 * @Paquete: mx.gob.imss.ctirss.delta.model.gestion.tramite
 * @Fecha: 26 Junio 2012 11:58
 */
public enum RazonResultadoEnum {
    DOCUMENTOS_PROBATORIOS_INCOMPLETOS(1, "Documentos probatorios incompletos")
    , DOCUMENTOS_APOCRIFOS(2, "Documentos ap\u00F3crifos")
    , IMPROCEDENCIA(3, "Improcedencia")
    , CONVIVENCIA_DEPENDENCIA_NO_COMPROBADAS(4, "Convivencia-dependencia no comprobadas")
    , SOLICITUD_CANCELADA(5, "Solicitud cancelada")
    , POR_LAUDO(6, "Por laudo")
    , POR_ACUERDO_HCCD_HCT(7, "Por acuerdo hccd/hct")
    , NORMAL(8, "Normal");

    private Integer codigo;
    private String descripcion;

    private RazonResultadoEnum(final Integer codigo, final String descripcion) {
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
