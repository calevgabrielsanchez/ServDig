package mx.gob.imss.ctirss.delta.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum EstadoBeneficioEnum {

	ACTIVO(1, "ACTIVO"), SUSPENDIDO(2, "SUSPENDIDO"), CANCELADO(3, "CANCELADO");

	private final static Map<Integer, EstadoBeneficioEnum> hashCodes = new HashMap<Integer, EstadoBeneficioEnum>();

	static {
		for (EstadoBeneficioEnum estado : EstadoBeneficioEnum.values()) {
			hashCodes.put(estado.getClave(), estado);
		}
	}

	private int clave;
	private String desc;

	private EstadoBeneficioEnum(int clave, String desc) {
		this.clave = clave;
		this.desc = desc;
	}

	/**
	 * @return the clave
	 */
	public int getClave() {
		return clave;
	}

	public String getDesc() {
		return desc;
	}

	public static EstadoBeneficioEnum obtenerEnumById(Integer codigo) {
		return hashCodes.get(codigo);
	}
}
