package mx.gob.imss.ctirss.delta.model.gestion.nss;

import java.util.HashMap;
import java.util.Map;

public enum ModuloOrigenAsignacionEnum {

	VENTANILLA(1L, "VENTANILLA"), INTERNET(2L, "INTERNET"), SIME(3L,
			"MEXICANOS EN EL EXTRANJERO"), SIE(4L, "ESTUDIANTES"), MOVILES(5L,"MOVILES");

	private ModuloOrigenAsignacionEnum(Long id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	private Long id;
	private String desc;

	private static final Map<Long, ModuloOrigenAsignacionEnum> origenes = new HashMap<Long, ModuloOrigenAsignacionEnum>();
	static {
		for (ModuloOrigenAsignacionEnum origen : ModuloOrigenAsignacionEnum.values()) {
			origenes.put(origen.getId(), origen);
		}
	}

	public Long getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}
	
	public static ModuloOrigenAsignacionEnum getById (Long id) {
		return origenes.get(id);
	}

}
