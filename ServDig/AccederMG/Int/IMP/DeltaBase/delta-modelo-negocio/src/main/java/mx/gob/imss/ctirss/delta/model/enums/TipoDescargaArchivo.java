package mx.gob.imss.ctirss.delta.model.enums;

public enum TipoDescargaArchivo {
	PDF(1), XML(2), ZIP(3), XLS(4);

	private long id;

	TipoDescargaArchivo(long id) {
		this.id = id;
	}

	public long getId() {
		return this.id;
	}
}
