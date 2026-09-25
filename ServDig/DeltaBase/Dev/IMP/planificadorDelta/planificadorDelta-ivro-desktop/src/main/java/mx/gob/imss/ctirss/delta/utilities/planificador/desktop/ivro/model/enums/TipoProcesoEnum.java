package mx.gob.imss.ctirss.delta.utilities.planificador.desktop.ivro.model.enums;

public enum TipoProcesoEnum {
	PROCESO_ORDINARIO_IVRO("1"),
	PROCESO_VACIO_IVRO("2");

	private String codigo;

	private TipoProcesoEnum(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}
}
