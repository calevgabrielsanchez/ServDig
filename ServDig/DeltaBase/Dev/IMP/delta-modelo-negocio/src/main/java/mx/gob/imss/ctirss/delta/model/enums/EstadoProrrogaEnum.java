package mx.gob.imss.ctirss.delta.model.enums;

import java.io.Serializable;

public enum EstadoProrrogaEnum implements Serializable {
	
	ACTIVA(1),EXPIRADA(2),CANCELADA(3);
	
	private long id;
	
	EstadoProrrogaEnum(long id) {
		this.id=id;
	}
	
	public long getId(){
		return this.id;
	}
}
