package mx.gob.imss.ctirss.delta.model.enums;

public enum TipoAmbitoEnum {
	CAMPO(1), URBANO(2), MIXTO(3);
	
	private Integer codigo;
	
	private TipoAmbitoEnum(Integer codigo){
		this.codigo=codigo;
	}
	
	public Integer getCodigo(){
		return this.codigo;
	}
}
