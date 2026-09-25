package mx.gob.imss.ctirss.delta.model.enums;

import java.io.Serializable;

public enum SexoEnum implements Serializable{

	MUJER(2),HOMBRE(1);
	
	private long id;
	
	SexoEnum(long id){
		this.id=id;
	}
	
	public long getId() {
		return this.id;
	}
}
