package mx.gob.imss.ctirss.delta.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum EstadoRegistroSIMEEnum {

	EXITO(1, "EXITO"), ERROR(2, "ERROR"), PENDIENTE(3, "PENDIENTE"), POR_PROCESAR(4, "POR PROCESAR");

	private final static Map<Integer, EstadoRegistroSIMEEnum> hashCodes = new HashMap<Integer, EstadoRegistroSIMEEnum>();

	static {
		for (EstadoRegistroSIMEEnum estado : EstadoRegistroSIMEEnum.values()) {
			hashCodes.put(estado.getClave(), estado);
		}
	}

	private int clave;
	private String desc;

	private EstadoRegistroSIMEEnum(int clave, String desc) {
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

	public static EstadoRegistroSIMEEnum obtenerEnumById(Integer codigo) {
		return hashCodes.get(codigo);
	}
}
