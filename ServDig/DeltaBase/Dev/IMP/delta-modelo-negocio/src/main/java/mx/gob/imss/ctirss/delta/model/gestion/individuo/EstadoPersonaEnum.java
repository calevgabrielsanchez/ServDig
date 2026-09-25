package mx.gob.imss.ctirss.delta.model.gestion.individuo;

public enum EstadoPersonaEnum {
	REGISTRADO(3), VALIDADO(2), ENRROLADO(4), INACTIVO(5); //, ELIMINADO(?);

	// TODO Unificar la infraestructura de enums en clase base que declare el codigo y su getter.
    private Integer codigo;

    private EstadoPersonaEnum(Integer codigo) {
        this.codigo = codigo;
	}
	
    public Integer getCodigo() {
        return codigo;
	}

}
