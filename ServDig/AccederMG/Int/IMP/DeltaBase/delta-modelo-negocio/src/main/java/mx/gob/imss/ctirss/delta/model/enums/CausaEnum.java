package mx.gob.imss.ctirss.delta.model.enums;

public enum CausaEnum {
	CLAUSURA(1), SUSTITUCION_PATRONAL(2), DUPLICIDAD(3), 
	OTRAS_CAUSAS(4), SUSPENSION_ACTIVIDADES(5), SIN_TRAB_X_6_MESES(6),
	POR_ART_251_LEY_IMSS(7), SUSTITUCION_PATRONAL_SUBCONTRATACION(15);
	
	private CausaEnum(Integer code){
		this.codigo=code;
	}
	
	private Integer codigo;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	
}
