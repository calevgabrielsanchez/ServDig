package mx.gob.imss.ctirss.delta.model.gestion.patronal;

public enum TipoSocioEnum {
	
	FISICO(1L),
	MORAL(2L),
	FIDEICOMISO(3L),
	EXTRANJERO(4L);
	
	private Long codigo;
	
	private TipoSocioEnum(Long valor) {
		this.codigo = valor;
	}

	public Long getValor() {
		return codigo;
	}

	public void setValor(Long valor) {
		this.codigo = valor;
	}

}
