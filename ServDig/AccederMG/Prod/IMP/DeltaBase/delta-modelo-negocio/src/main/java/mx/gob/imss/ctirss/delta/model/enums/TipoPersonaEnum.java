package mx.gob.imss.ctirss.delta.model.enums;

import java.io.Serializable;

public enum TipoPersonaEnum implements Serializable{

	FISICA(1),MORAL(2);
	
	private long id;
	
	TipoPersonaEnum(long id) {
		this.id=id;
	}
	
	public long getId(){
		return this.id;
	}
}
