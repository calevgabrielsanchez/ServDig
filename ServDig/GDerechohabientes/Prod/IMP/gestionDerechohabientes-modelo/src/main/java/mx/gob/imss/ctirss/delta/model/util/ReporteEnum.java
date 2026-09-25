package mx.gob.imss.ctirss.delta.model.util;

public enum ReporteEnum {

    CONYUGES("{ CALL MGPBDTU9X.PACK_REPORTE_CONYUGE.EXECUTE_RPORT_CONYUGES_P(?,?,?,?,?,?,?,?,?,?) }",
            "{ CALL MGPBDTU9X.PACK_REPORTE_CONYUGE.GET_ESTATUS_FOLIO_P(?,?,?,?) }",
            "{ CALL MGPBDTU9X.PACK_REPORTE_CONYUGE.GET_ARCHIVO_FOLIO_P(?,?,?,?,?) }",
            "{ CALL MGPBDTU9X.PACK_REPORTE_CONYUGE.DSCARGA_BORRAR_ARCHIVO_P(?,?,?,?) }"),

    UNION_CIVIL("{ CALL MGPBDTU9X.PACK_REPORTE_REG_UNION_CIVIL.EXECUTE_RPORT_UNIONCIVIL_P(?,?,?,?,?,?,?,?,?,?) }",
            "{ CALL MGPBDTU9X.PACK_REPORTE_REG_UNION_CIVIL.GET_ESTATUS_FOLIO_P(?,?,?,?) }",
            "{ CALL MGPBDTU9X.PACK_REPORTE_REG_UNION_CIVIL.GET_ARCHIVO_FOLIO_P(?,?,?,?,?) }",
            "{ CALL MGPBDTU9X.PACK_REPORTE_REG_UNION_CIVIL.DSCARGA_BORRAR_ARCHIVO_P(?,?,?,?) }");

    private final String spGenera;
    private final String spEstatus;
    private final String spDescarga;
    private final String spElimina;

    ReporteEnum(String spGenera, String spEstatus, String spDescarga, String spElimina) {

        this.spGenera = spGenera;
        this.spEstatus = spEstatus;
        this.spDescarga = spDescarga;
        this.spElimina = spElimina;
    }

    public String getSpGenera() {

        return spGenera;
    }

    public String getSpEstatus() {

        return spEstatus;
    }

    public String getSpDescarga() {

        return spDescarga;
    }

    public String getSpElimina() {

        return spElimina;
    }
}
