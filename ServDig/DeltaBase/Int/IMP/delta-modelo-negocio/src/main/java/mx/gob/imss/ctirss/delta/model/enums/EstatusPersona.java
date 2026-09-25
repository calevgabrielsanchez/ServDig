package mx.gob.imss.ctirss.delta.model.enums;

public enum EstatusPersona {
	
	Inexistente(0),Sin_Registros_Patronales(1), Existe_en_bdtu(2);
	
	EstatusPersona(Integer code){
		this.codigo=code;
	}
	
	private Integer codigo;

	public Integer getCodigo() {
		return codigo;
	}

	protected void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	
	
}
