package mx.gob.imss.ctirss.delta.model.enums;

import java.io.Serializable;

public enum UsuarioEnum implements Serializable{
	
	TRAMITADOR(1),DERECHO(2),JEFE(3);
	
	private long id;
	
	UsuarioEnum(long id) {
		this.id=id;
	}
	
	public long getId(){
		return this.id;
	}
}
