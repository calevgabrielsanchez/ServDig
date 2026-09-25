package mx.gob.imss.ctirss.delta.model.gestion.patronal;

public enum TipoPersonaFiscal {
	FISICA(1), MORAL(2);
	
	private Integer codigo;
	
	TipoPersonaFiscal(Integer codigo) {
		this.codigo=codigo;	
	}

	public Integer getCodigo(){
		return this.codigo;
	}
}
