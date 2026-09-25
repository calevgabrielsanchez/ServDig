package mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.enums;

public enum TipoCorteEnum {
	CORTE_TOTAL_DIA_ANTERIOR(0),
	CORTE_PARCIAL_DIA_ACTUAL(1),
	CORTE_TOTAL_RANGO(2);

	private Integer codigo;

	private TipoCorteEnum(Integer valor) {
		this.codigo = valor;
	}

	public Integer getCodigo() {
		return codigo;
	}
}
