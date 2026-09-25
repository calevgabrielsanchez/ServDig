package mx.gob.imss.ctirss.delta.model.enums;

import java.io.Serializable;

public enum MedicoEspecialidadEnum implements Serializable{

	GENERAL(1),TRAUMATOLOGIA(2),CIRUJANO(3);
	
	private long id;
	
	MedicoEspecialidadEnum(long id) {
		this.id=id;
	}
	
	public long getId(){
		return this.id;
	}
}