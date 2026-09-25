package mx.gob.imss.ctirss.delta.model.enums;

import java.io.Serializable;

public enum TipoPersonaInteresadaSolEnum implements Serializable{

	ASEGURADO_PENSIONADO(1), CONYUGUE(2), DESCENDIENTE(3), PADRES(4), CONCUBINO(5), REPRESENTANTE_LEGAL(6);
	
	private long id;
	
	TipoPersonaInteresadaSolEnum(long id) {
		this.id=id;
	}
	
	public long getId(){
		return this.id;
	}
}
