package mx.gob.imss.ctirss.delta.comet.model.enums;

public enum WidgetEnum {
	ID_WIDGET_IDENTIDAD_PERSONA("personaIdentidadWidget"),
	ID_WIDGET_FISCALES_PERSONA("personaIdentidadFiscalWidget"),
	ID_WIDGET_CENTRO_TRABAJO("centroTrabajoWidget"),
	ID_WIDGET_BENEFICIOS("beneficiosWidget"),
	ID_WIDGET_IVRO("personaIvroIndivWidget");

	private String codigo;

	private WidgetEnum(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}
