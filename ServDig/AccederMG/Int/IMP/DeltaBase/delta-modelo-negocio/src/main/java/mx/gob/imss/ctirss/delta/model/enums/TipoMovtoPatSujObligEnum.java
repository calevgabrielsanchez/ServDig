package mx.gob.imss.ctirss.delta.model.enums;

public enum TipoMovtoPatSujObligEnum {
       ALTA_PATRONAL(1)
     , BAJA_PATRONAL(2)
     , RESTABLECIMIENTO_PATRONAL(3)
     , CAMBIO_DE_DOMICILIO(4)
     , CAMBIO_DE_NOMBRE_O_RAZON_SOCIAL(5)
     , CAMBIO_DE_CLASIFICACION_PATRONAL(6)
     , CAMBIO_DE_TIPO_DE_COTIZACION_PATRONAL(7)
     , IDENTIFICACION_PATRONAL_DE_HUELGA(9)
     , ALTA_DE_NUMERO_RELACION_EMPRESA_MUNICIPIO(10)
     , BAJA_DE_NUMERO_RELACION_EMPRESA_MUNICIPIO(11)
     , IDENTIFICACION_DE_SUBROGACION_DE_SERVICIOS(12);

     private int id;

    private TipoMovtoPatSujObligEnum (int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
