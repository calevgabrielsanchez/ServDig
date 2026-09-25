package mx.gob.imss.ctirss.delta.model.enums;

import java.io.Serializable;

public enum EstadoTramiteEnum implements Serializable {
	
	INICIADO(1),CERRADO(2),ESPERA_AUTORIZACION(3),ESPERA_TRAMITADOR(4),ESPERA_DERECHOHABIENTE(5), RECHAZADO(70);
	
	private long id;
	
	EstadoTramiteEnum(long id) {
		this.id=id;
	}
	
	public long getId(){
		return this.id;
	}
}
