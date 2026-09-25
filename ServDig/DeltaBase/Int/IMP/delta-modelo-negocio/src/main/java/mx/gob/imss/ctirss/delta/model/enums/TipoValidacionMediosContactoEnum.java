package mx.gob.imss.ctirss.delta.model.enums;

public enum TipoValidacionMediosContactoEnum {
	SOLO_PARTICULAR(0), SOLO_FISCAL(1), PARTICULAR_FISCAL(2), CENTRO_TRABAJO(3);

	private Integer codigo;

	private TipoValidacionMediosContactoEnum(Integer value) {
		this.codigo = value;
	}

	public Integer getCodigo() {
		return this.codigo;
	}
}
