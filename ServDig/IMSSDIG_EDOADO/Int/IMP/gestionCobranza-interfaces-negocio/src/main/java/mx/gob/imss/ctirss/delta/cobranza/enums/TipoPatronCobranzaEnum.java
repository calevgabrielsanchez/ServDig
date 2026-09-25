package mx.gob.imss.ctirss.delta.cobranza.enums;

public enum TipoPatronCobranzaEnum {

	INDIVIDUAL("1"),CORPORATIVO("2"),RPU("3");
	
	private String id;

	TipoPatronCobranzaEnum(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
