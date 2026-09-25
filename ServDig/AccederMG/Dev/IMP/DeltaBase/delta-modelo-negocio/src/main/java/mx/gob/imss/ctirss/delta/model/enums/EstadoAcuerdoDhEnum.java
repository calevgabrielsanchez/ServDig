package mx.gob.imss.ctirss.delta.model.enums;

import java.io.Serializable;

public enum EstadoAcuerdoDhEnum implements Serializable {
	
	ACTIVO(1),
	EXPIRADO(2),
	CANCELADO(3);
	
	private long id;
	
	EstadoAcuerdoDhEnum(long id) {
		this.id = id;
	}

	public long getId() {
		return this.id;
	}
	
}
