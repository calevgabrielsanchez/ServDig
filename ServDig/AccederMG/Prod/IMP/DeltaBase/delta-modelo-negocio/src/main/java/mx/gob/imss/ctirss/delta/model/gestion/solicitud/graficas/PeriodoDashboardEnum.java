package mx.gob.imss.ctirss.delta.model.gestion.solicitud.graficas;

public enum PeriodoDashboardEnum {

	HOY(1), SEMANAL(2), MENSUAL(3), ANUAL(4), RANGO_FECHAS(5);

	private int id;

	private PeriodoDashboardEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
