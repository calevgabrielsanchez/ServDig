package mx.gob.imss.ctirss.delta.model.enums;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;


public enum OrigenConsultaNssEnum {
	

	SINDO_CIZ1(1, "SINDO CIZ 1"), SINDO_CIZ2(2, "SINDO CIZ 2"), SINDO_CIZ3(3,
			"SINDO CIZ 3"), HISTORICO_CENTRAL(4, "HIST&Oacute;RICO CENTRAL" ), CANASE(
			5, "CANASE"), BDTU(6, "BDTU");

	
	
	private int clave;
	private String descripcion;
	


	private final static Map<Integer, OrigenConsultaNssEnum> hashCodes = new HashMap<Integer, OrigenConsultaNssEnum>();

	static {
		for (OrigenConsultaNssEnum origenNSS : OrigenConsultaNssEnum.values()) {
			hashCodes.put(origenNSS.getClave(), origenNSS);
		}
	}

	private OrigenConsultaNssEnum(int clave, String descripcion) {
		this.clave = clave;
		this.descripcion = descripcion;
	}

	public int getClave() {
		return clave;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public static OrigenConsultaNssEnum obtenerEnumById(Integer codigo) {
		return hashCodes.get(codigo);
	}

}
