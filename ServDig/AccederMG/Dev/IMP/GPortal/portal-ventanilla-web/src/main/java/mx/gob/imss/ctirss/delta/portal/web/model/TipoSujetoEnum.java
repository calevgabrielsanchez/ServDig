package mx.gob.imss.ctirss.delta.portal.web.model;

public enum TipoSujetoEnum {

	ASEGURADO(1), PATRON(2);

	private int id;

	private TipoSujetoEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
