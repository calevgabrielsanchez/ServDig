package mx.gob.imss.ctirss.delta.model.enums;

import java.io.Serializable;

public enum TurnoEnum implements Serializable{
	
	MATUTINO(new Long(1)),VESPERTINO(new Long(2));
	
	private Long id;
	
	TurnoEnum(Long id) {
		this.id=id;
	}
	
	public Long getId(){
		return this.id;
	}
}
