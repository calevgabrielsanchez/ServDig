package mx.gob.imss.ctirss.delta.model.enums;

import java.io.Serializable;

public enum TipoContactoEnum implements Serializable{
	
	CORREO_ELECTRONICO(new Long(1)),TELEFONO_FIJO(new Long(2)), TELEFONO_MOVIL(new Long(3));
	
	private Long id;
	
	TipoContactoEnum(Long id) {
		this.id=id;
	}
	
	public Long getId(){
		return this.id;
	}
}
