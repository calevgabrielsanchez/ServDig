package mx.gob.imss.ctirss.delta.model.enums;

public enum SindoAplicacionEnum {
	GESTION_PATRONAL(1), GESTION_ANALISIS_DE_CLASIFICACION(2);
	
	private Integer codigo;

	private SindoAplicacionEnum(Integer valor){
		this.codigo=valor;
		
	}
	
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	
	
	
	
}
