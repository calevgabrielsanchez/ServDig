package mx.gob.imss.cit.ws.externo.boveda.documental.utils;

public enum AtributoBovedaEnum {
	
	ATRIBUTO_ID(1,"id"),
	NAME(2,"name"),
	FOLIO_TRAMITE(3,"folioTramite"),
	ID_SOLICITUD(4,"idSolicitud"),
	TIPO_DOCUMENTO(5,"tipoDocumento"),
	FOLIO(6,"folio");
	

	private Integer id;
	private String descripcion;
	
	private AtributoBovedaEnum(Integer id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	
}
