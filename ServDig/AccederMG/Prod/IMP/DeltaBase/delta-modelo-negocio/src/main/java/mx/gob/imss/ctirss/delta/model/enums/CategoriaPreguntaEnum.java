package mx.gob.imss.ctirss.delta.model.enums;

public enum CategoriaPreguntaEnum {
	ASCENDIENTE(1),CONCUBINARIO(2);
	
    private long id;
	
	CategoriaPreguntaEnum(long id) {
		this.id=id;
	}
	
	public long getId(){
		return this.id;
	}
}