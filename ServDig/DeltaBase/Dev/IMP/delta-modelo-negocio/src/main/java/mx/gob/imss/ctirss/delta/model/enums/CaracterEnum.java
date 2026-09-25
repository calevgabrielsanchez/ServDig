package mx.gob.imss.ctirss.delta.model.enums;

public enum CaracterEnum {
	PROVISIONAL(1),DEFINITIVO(2);
	
	private long id;

	CaracterEnum(long id) {
		this.id = id;
	}

	public long getId() {
		return this.id;
	}
}
