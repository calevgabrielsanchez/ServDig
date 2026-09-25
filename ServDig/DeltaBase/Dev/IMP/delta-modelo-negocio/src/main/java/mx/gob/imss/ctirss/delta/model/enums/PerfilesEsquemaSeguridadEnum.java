package mx.gob.imss.ctirss.delta.model.enums;

public enum PerfilesEsquemaSeguridadEnum {
	USUARIO_EXTERNO(1);
	
	private long id;
	
	PerfilesEsquemaSeguridadEnum(long id){
		this.id=id;
	}
	
	public long getId() {
		return this.id;
	}


}
