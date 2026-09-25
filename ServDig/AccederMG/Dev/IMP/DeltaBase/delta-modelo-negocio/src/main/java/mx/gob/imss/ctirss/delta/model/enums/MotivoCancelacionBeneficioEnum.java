package mx.gob.imss.ctirss.delta.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum MotivoCancelacionBeneficioEnum {

	POR_SAT(1, "BAJA SAT"),
	POR_INFONAVIT(2, "BAJA INFONAVIT"),
	POR_ADEUDO_IMSS(3, "BAJA POR EL IMSS"),
	A_SOLICITUD_INTERESADO(4, "BAJA A SOLICITUD DEL INTERESADO"),
    POR_FALTA_PAGO_IVRO(5,"BAJA RISS ASEGURADO POR FALTA DE PAGO OPORTUNO"),
	NO_CUMPLEN_SUPUESTOS_IMSS(6, "BAJA RISS ASEGURADO POR RENOVACIÓN EXTEMPORANEA");
	

	private final static Map<Integer, MotivoCancelacionBeneficioEnum> hashCodes = new HashMap<Integer, MotivoCancelacionBeneficioEnum>();

	static {
		for (MotivoCancelacionBeneficioEnum estado : MotivoCancelacionBeneficioEnum.values()) {
			hashCodes.put(estado.getClave(), estado);
		}
	}

	private int clave;
	private String desc;

	private MotivoCancelacionBeneficioEnum(int clave, String desc) {
		this.clave = clave;
		this.desc = desc;
	}
	
	public int getClave() {
		return clave;
	}

	public String getDesc() {
		return desc;
	}

	public static MotivoCancelacionBeneficioEnum obtenerEnumById(Integer codigo) {
		return hashCodes.get(codigo);
	}
}
