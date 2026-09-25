package mx.gob.imss.ctirss.delta.model.enums;

public enum RolesEsquemaSeguridadEnum {
	USUARIO_EXTERNO(1);
	private long id;
	
	RolesEsquemaSeguridadEnum(long id){
		this.id=id;
	}
	
	public long getId() {
		return this.id;
	}



}
