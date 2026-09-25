package mx.gob.imss.ctirss.delta.model.enums;

import java.io.Serializable;

public enum TipoPrestacionDerechoHabienteEnum implements Serializable{

	
	ESPECIE(1),DINERO(2);
	
	private long id;
	
	TipoPrestacionDerechoHabienteEnum(long id){
		this.id=id;
	}
	
	public long getId() {
		return this.id;
	}

    public static TipoPrestacionDerechoHabienteEnum fromId(long id) {
    	TipoPrestacionDerechoHabienteEnum[] tipoPrestacion = values();
        for(TipoPrestacionDerechoHabienteEnum prestacion :tipoPrestacion) {
            if (prestacion.getId() == id) {
                return prestacion;
            }
        }
        return null;
    }

}
