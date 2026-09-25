package mx.gob.imss.ctirss.delta.model.gestion.patronal;

public enum TipoSociedadEnum {
	COOPERATIVA_ESCOLAR_LIMITADA(19L), SA_DE_CV(74L), SA_DE_RL(85L);

	private TipoSociedadEnum(Long valor) {
		this.codigo = valor;
	}

	private Long codigo;

	public Long getValor() {
		return codigo;
	}

	public void setValor(Long valor) {
		this.codigo = valor;
	}
}
