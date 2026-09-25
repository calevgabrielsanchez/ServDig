package mx.gob.imss.ctirss.delta.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum EstadoInconsistenciaVigenciaEnum {

	SIN_INCONSISTENCIA(0),NO_EXISTE_NSS(2),ASEGURADO(3),BENEFICIARIOS(4), ESTUDIANTES(5), ENBDTU(6);
	
	private int id;

	
	private static final Map<Integer, EstadoInconsistenciaVigenciaEnum> estados = new HashMap<Integer, EstadoInconsistenciaVigenciaEnum>();
	
	static {
		for (EstadoInconsistenciaVigenciaEnum estado : EstadoInconsistenciaVigenciaEnum.values()) {
			estados.put(estado.getId(), estado);
		}
	}

	EstadoInconsistenciaVigenciaEnum(int id) {
		this.id=id;
	}
	
	public int getId(){
		return this.id;
	}
	
	
	
	public static EstadoInconsistenciaVigenciaEnum getById (Integer id) {
		return estados.get(id);
	}
	
}
