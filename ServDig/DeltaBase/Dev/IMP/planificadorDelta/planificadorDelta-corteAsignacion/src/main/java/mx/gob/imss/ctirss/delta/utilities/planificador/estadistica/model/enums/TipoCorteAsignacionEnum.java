package mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.enums;

public enum TipoCorteAsignacionEnum {
	CORTE_TOTAL_DIA_ANTERIOR("Cifras de Transacciones correspondientes a la Asignacion de NSS (Corte Total)"),
	CORTE_PARCIAL_DIA_ACTUAL("Cifras de Transacciones correspondientes a la Asignacion de NSS"),
	CORTE_TOTAL_RANGO_FECHA("Cifras de Asignaciones enviadas a CANASE");

	private String descripcion;

	private TipoCorteAsignacionEnum(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}
}
