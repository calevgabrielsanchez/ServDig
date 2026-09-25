package mx.gob.imss.ctirss.delta.model.enums;

public enum EstadoCivilEnum {
	SOLTERO(1),CASADO(2),DIVORCIADO(3),VIUDO(4),CONCUBINATO(5),PERSONA_EN_UNION_CIVIL(6);
	
	private long id;
	
	EstadoCivilEnum(long id){
		this.id=id;
	}
	
	public long getId() {
		return this.id;
	}
}
