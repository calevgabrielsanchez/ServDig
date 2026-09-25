package mx.gob.imss.ctirss.delta.model.enums;

public enum SindoMovimientoEnum {
	TIPO_MOV_ACT_CLASIFICACION(06), ORIGEN_MOVIMIENTO_CAMBIO_CLASIFICACION(02);
	
	private Integer codigo;
	private SindoMovimientoEnum(Integer code){
		this.codigo = code;
	}
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	
}
