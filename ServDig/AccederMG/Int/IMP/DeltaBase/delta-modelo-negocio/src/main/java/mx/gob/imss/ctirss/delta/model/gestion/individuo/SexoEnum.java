package mx.gob.imss.ctirss.delta.model.gestion.individuo;

public enum SexoEnum {

    HOMBRE(1), MUJER(2);

    private Integer codigo;

    private SexoEnum(final Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

}
