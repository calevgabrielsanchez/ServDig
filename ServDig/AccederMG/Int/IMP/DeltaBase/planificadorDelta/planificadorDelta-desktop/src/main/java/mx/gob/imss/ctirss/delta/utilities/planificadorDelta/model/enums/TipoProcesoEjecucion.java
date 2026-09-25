package mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.enums;

public enum TipoProcesoEjecucion {
	PROCESO_DIARIO_CANASE_SINDO(1),
	PROCESO_MENSUAL_IVRO(2);

	private Integer codigo;

	private TipoProcesoEjecucion(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getCodigo() {
		return codigo;
	}
}
