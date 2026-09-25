package mx.gob.imss.ctirss.delta.model.enums;

import java.io.Serializable;

public enum PrestacionesDerechohabienteEnum implements Serializable{
	
	ENFERMEDADES_Y_MATERNIDAD(1),
	INVALIDEZ_Y_VIDA(2),
	RETIRO(3),
	CESANTIA(4),
	VEJEZ(5),
	PRESTACIONES_SOCIALES_Y_GUARDERIAS(6),
	RIESGO_DE_TRABAJO(7);
	
	private long id;
	
	PrestacionesDerechohabienteEnum(long id){
		this.id=id;
	}
	
	public long getId() {
		return this.id;
	}

    public static PrestacionesDerechohabienteEnum fromId(long id) {
    	PrestacionesDerechohabienteEnum[] prestaciones = values();
        for(PrestacionesDerechohabienteEnum prestacion :prestaciones) {
            if (prestacion.getId() == id) {
                return prestacion;
            }
        }
        return null;
    }

}
