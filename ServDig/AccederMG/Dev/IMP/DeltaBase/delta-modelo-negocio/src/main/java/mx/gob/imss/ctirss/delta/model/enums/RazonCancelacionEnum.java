package mx.gob.imss.ctirss.delta.model.enums;

import java.io.Serializable;

public enum RazonCancelacionEnum implements Serializable{
	
	PETICION_DERECHOHABIENTE(1),INASISTENCIA(2),PETICION_TRAMITADOR(3);
	
	private long id;
	
	RazonCancelacionEnum(long id) {
		// TODO Auto-generated constructor stub
		this.id = id;
	}
	
	public long getId(){
		return this.id;
	}
}
