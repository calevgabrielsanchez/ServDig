package mx.gob.imss.ctirss.delta.model.enums;

import java.io.Serializable;

public enum TipoDomicilioEnum implements Serializable{
	
	// Para persona fisica
	FISCAL(1),RECIBIR_NOTIFICACIONES(2),CENTRO_TRABAJO(3),PARTICULAR(4),
	// Para persona moral
	FISCAL_MORAL(5),RECIBIR_NOTIFICACIONES_MORAL(6),CENTRO_TRABAJO_MORAL(7),PARTICULAR_MORAL(8);
	
	private long id;
	
	TipoDomicilioEnum(long id) {
		this.id = id;
	}
	
	public long getId() {
		return this.id;
	}
	
}
