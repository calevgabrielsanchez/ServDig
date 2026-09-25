package mx.gob.imss.ctirss.delta.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum TipoBeneficioEnum {

	RIF(1, "ALTA DE RÉGIMEN DE INCORPORACIÓN A LA SEGURIDAD SOCIAL");

	private final static Map<Integer, TipoBeneficioEnum> hashCodes = new HashMap<Integer, TipoBeneficioEnum>();

	static {
		for (TipoBeneficioEnum estado : TipoBeneficioEnum.values()) {
			hashCodes.put(estado.getClave(), estado);
		}
	}

	private int clave;
	private String desc;

	private TipoBeneficioEnum(int clave, String desc) {
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

	public static TipoBeneficioEnum obtenerEnumById(Integer codigo) {
		return hashCodes.get(codigo);
	}
}
