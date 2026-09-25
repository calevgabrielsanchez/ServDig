package mx.gob.imss.ctirss.delta.model.enums;

import java.io.Serializable;

public enum EstadoSolicitudEnum implements Serializable{

	REGISTRADA(new Long(1)),
	ATENDIDA(new Long(2)),
	CANCELADA(new Long(3)),
	VALIDADA(new Long(4)),
	PENDIENTE_AUTORIZACION(new Long(5)),
	RECHAZADA(new Long(11));
	
	private Long id;
	
	EstadoSolicitudEnum(Long id) {
		this.id=id;
	}
	
	public Long getId(){
		return this.id;
	}
}