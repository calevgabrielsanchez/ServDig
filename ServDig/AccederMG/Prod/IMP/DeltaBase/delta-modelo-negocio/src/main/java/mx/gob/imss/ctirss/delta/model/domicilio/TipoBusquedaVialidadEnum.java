package mx.gob.imss.ctirss.delta.model.domicilio;

public enum TipoBusquedaVialidadEnum {

	VIALIDAD(1),
	VIALIDAD_NO_LOCALIZADA(2),
	CARRETERA(3),
	CAMINO(4);
	
	private Integer codigo;
	
	TipoBusquedaVialidadEnum(Integer codigo) {
		this.codigo = codigo;
	}
	
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	
	
}
